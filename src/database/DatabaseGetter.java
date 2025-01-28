package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

        try (Connection conn = Database.connect();
            Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                User user = new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("clue"),
                    rs.getInt("userID"),
                    rs.getBytes("profilePicture")
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

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }
    
    public static Map<Integer, Post> getUserPosts(int givenUserID) {
        String sql = "SELECT * FROM posts";
        Map<Integer, Post> posts = new HashMap<>();

        try (Connection conn = Database.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

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
    
        try (Connection conn = Database.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
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

    public static List<Post> getSavedPosts(int userID) {
        String sql = "SELECT p.* FROM posts p " +
                     "INNER JOIN saved_posts sp ON p.postID = sp.postID " +
                     "WHERE sp.userID = ?";
        List<Post> posts = new ArrayList<>();
    
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();
    
            while (rs.next()) {
                Post post = new Post(
                    rs.getString("postContent"),
                    rs.getInt("postID"),
                    rs.getInt("userID")
                );
                post.setCreationTime(LocalDateTime.parse(rs.getString("creationTime")));
                posts.add(post);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return posts;
    }

    public static Map<Integer, Comment> getCommentsMap() {
        String sql = "SELECT * FROM comments";
        Map<Integer, Comment> commentMap = new HashMap<>();
    
        try (Connection conn = Database.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
        String query = "SELECT * FROM friendships WHERE userID = ? AND friendID = ?";
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, friendId);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Returns true if a friendship record exists
        } catch (SQLException e) {
            System.out.println("Error checking friendship: " + e.getMessage());
            return false;
        }
    }
    public static boolean isConfirm(int userId, int friendId) {
        String query = "SELECT * FROM allfriend WHERE userID = ? AND friendID = ?";
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, friendId);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Returns true if a friendship record exists
        } catch (SQLException e) {
            System.out.println("Error checking allfriend: " + e.getMessage());
            return false;
        }
    }
    public static boolean updateFriendStatus(int userId, int friendId, String status) {
        String query = "UPDATE friendships SET status = ? WHERE userID = ? AND friendID = ?";
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status); // New status (e.g., "Accepted", "Rejected")
            stmt.setInt(2, userId);   // ID of the user who sent the request
            stmt.setInt(3, friendId); // ID of the user receiving the request
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Return true if the update was successful
        } catch (SQLException e) {
            System.out.println("Error updating friend status: " + e.getMessage());
            return false;
        }
    }
    public static List<User> getIncomingRequests(int userId) {
        List<User> requests = new ArrayList<>();
        String query = "SELECT users.* FROM friendships " +
                       "JOIN users ON friendships.userID = users.userID " +
                       "WHERE friendships.friendID = ? AND friendships.status = 'Pending'";
        try (Connection conn = Database.connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("clue"),
                    rs.getInt("userID")
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
        String query = "SELECT users.* FROM allfriend " +
                       "JOIN users ON allfriend.userID = users.userID " +
                       "WHERE allfriend.friendID = ? AND allfriend.status = 'Pending'";
        try (Connection conn = Database.connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            System.out.println("Executing query: " + query + " with userId: " + userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("clue"),
                    rs.getInt("userID")
                );
                requests.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }
    public static Map<Integer, Reaction> getReactionsMap() {
        String sql = "SELECT * FROM votes";
        Map<Integer, Reaction> reactions = new HashMap<>();
    
        try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
    
            while (rs.next()) {
                int userID = rs.getInt("userID");
                Reaction reaction = new Reaction(
                    rs.getInt("vote"),
                    rs.getInt("postID"),
                    userID
                );
                reactions.put(userID, reaction);
                System.out.println("Fetched data from database: vote=" + rs.getInt("vote") + ", postID=" + rs.getInt("postID") + ", userID=" + userID);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reactions;
    }
    public static int getTotalVotes(int postID) {
        String sql = "SELECT totalVote FROM totalVotes WHERE postID = ?";
        try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
            pstmt.setInt(1, postID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("totalVote");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}