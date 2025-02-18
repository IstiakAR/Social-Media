package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.*;

public class DatabaseGetter {
    public static List<User> getUsers() {
        String sql = "SELECT * FROM users";
        List<User>users = new ArrayList<>();

        try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                User user = new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("clue"),
                    rs.getInt("userID"),
                    rs.getBytes("profilePicture"),
                    rs.getString("Bio"),
                    rs.getString("Education"),
                    rs.getString("Workplace"),
                    rs.getString("Email")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    public static User getUserByID(int userID) {
        String sql = "SELECT * FROM users WHERE userID = ?";
        User user = null;
    
        try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("clue"),
                    rs.getInt("userID"),
                    rs.getBytes("profilePicture")
                );
                System.out.println("User fetched successfully: " + user.getUsername());
            } else {
                System.out.println("No user found with userID: " + userID);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user: " + e.getMessage());
        }
        return user;
    }
    
    public static Map<Integer, Post> getUserPosts(int givenUserID) {
        String sql = "SELECT * FROM posts";
        Map<Integer, Post> posts = new HashMap<>();

        try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int userID = rs.getInt("userID");
                if (userID == givenUserID) {
                    Post post = new Post(
                        rs.getString("postContent"),
                        rs.getInt("postID"),
                        userID
                    );
                    post.setCreationTime(LocalDateTime.parse(rs.getString("creationTime")));
                    posts.put(post.getPostID(), post);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return posts;
    }


    public static Map<Integer, Post>getAllPosts() {
        String sql = "SELECT * FROM posts";
        Map<Integer, Post> posts = new HashMap<>();
    
        try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
    
            while (rs.next()) {
                    Post post = new Post(
                        rs.getString("postContent"),
                        rs.getInt("postID"),
                        rs.getInt("userID")
                    );
                    post.setCreationTime(LocalDateTime.parse(rs.getString("creationTime")));
                    posts.put(post.getPostID(), post);
                }
            }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return posts;
    }

    public static Map<Integer, Comment> getCommentsMap() {
        String sql = "SELECT * FROM comments";
        Map<Integer, Comment> commentMap = new HashMap<>();
    
        try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    Comment comment = new Comment(
                        rs.getString("commentText"),
                        rs.getInt("commentID"),
                        rs.getInt("postID"),
                        rs.getInt("userID"),
                        LocalDateTime.parse(rs.getString("creationTime"))
                    );
                    commentMap.put(comment.getInteractionID(), comment); 
                }
            }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return commentMap;
    }

    public static boolean isFriend(int userId, int friendId) {
        String sql = "SELECT * FROM friendships WHERE userID = ? AND friendID = ?";
        try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, friendId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error checking friendship: " + e.getMessage());
            return false;
        }
    }
    public static boolean isConfirm(int userId, int friendId) {
        String sql = "SELECT * FROM allfriend WHERE userID = ? AND friendID = ?";
        try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, friendId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error checking allfriend: " + e.getMessage());
            return false;
        }
    }
    public static List<User> getIncomingRequests(int userId) {
        List<User> requests = new ArrayList<>();
        String sql = "SELECT users.* FROM friendships " +
                       "JOIN users ON friendships.userID = users.userID " +
                       "WHERE friendships.friendID = ? AND friendships.status = 'Pending'";

            try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("clue"),
                    rs.getInt("userID"),
                    rs.getBytes("profilePicture")
                );
                requests.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return requests;
    }
    public static List<User> getAllfriend(int userId) {
        List<User> requests = new ArrayList<>();
        String sql = "SELECT users.* FROM allfriend " +
                     "JOIN users ON allfriend.userID = users.userID " +
                     "WHERE allfriend.friendID = ? AND allfriend.status = 'Pending'";

            try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            System.out.println("Executing query: " + sql + " with userId: " + userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("clue"),
                    rs.getInt("userID"),
                    rs.getBytes("profilePicture")
                );
                requests.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }
    public static int getTotalVotes(int postID) {
        String sql = "SELECT COUNT(*) FROM votes WHERE postID = ?";
        try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
            pstmt.setInt(1, postID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
    public static List<Message> getSentMessages(int senderId, int friendId) {
        List<Message> sentMessages = new ArrayList<>();
        String sql = "SELECT * FROM messages " +
                   "WHERE (senderID = ? AND receiverID = ?) " +
                   "   OR (senderID = ? AND receiverID = ?) " +
                   "ORDER BY timestamp ASC";
        try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
            pstmt.setInt(1, senderId);
            pstmt.setInt(2, friendId);
            pstmt.setInt(3, friendId);  
            pstmt.setInt(4, senderId); 
            // System.out.println("Executing query: " + sql + " with senderId: " + senderId + " and friendId: " + friendId);
            ResultSet rs = pstmt.executeQuery();
    
            while (rs.next()) {
                Message message = new Message(
                    rs.getInt("senderID"),
                    rs.getInt("receiverID"),
                    rs.getString("content")
                );
                sentMessages.add(message);
            }
    
            if (sentMessages.isEmpty()) {
                System.out.println("No messages sent from user " + senderId + " to user " + friendId + ".");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving sent messages: " + e.getMessage());
        }
        return sentMessages;
    }
}