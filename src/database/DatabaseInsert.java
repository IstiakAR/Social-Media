package database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Comment;
import model.Post;
import model.User;

public class DatabaseInsert {
    public static void insertUser(User user) {
        String sql = "INSERT INTO users(userID, username, password, name, clue, profilePicture) VALUES(?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
            pstmt.setInt(1, user.getUserID());
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getName());
            pstmt.setString(5, user.getClue());
            pstmt.setBytes(6, user.getProfilePicture());
            pstmt.executeUpdate();
            System.out.println("User inserted.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertPost(Post post) {
        String sql = "INSERT INTO posts(postID, postContent, userID, creationTime) VALUES(?, ?, ?, ?)";
        System.out.println("Attempting to insert post: " + post.getPostContent()); // Debug
    
        try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
            pstmt.setInt(1, post.getPostID());
            pstmt.setString(2, post.getPostContent());
            pstmt.setInt(3, post.getUserID());
            pstmt.setString(4, post.getUnformattedCreationTime().toString());
            int result = pstmt.executeUpdate();
            System.out.println("Post insert result: " + result); // Debug
        } catch (SQLException e) {
            System.out.println("Error inserting post: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void insertComment(Comment comment) {
        String sql = "INSERT INTO comments(commentID, commentText, postID, userID, creationTime) VALUES(?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
            pstmt.setInt(1, comment.getInteractionID());
            pstmt.setString(2, comment.getCommentText());
            pstmt.setInt(3, comment.getPostID());
            pstmt.setInt(4, comment.getUserID());
            pstmt.setString(5, comment.getCreationTime().toString());
            System.out.println(comment.getInteractionID() + ' ' + comment.getCommentText() + ' ' + comment.getPostID() + ' ' + comment.getUserID());
            pstmt.executeUpdate();
            System.out.println("Comment inserted.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void savePost(int userID, int postID) {
        String sql = "INSERT INTO saved_posts(userID, postID) VALUES(?, ?)";

        try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            pstmt.setInt(2, postID);
            pstmt.executeUpdate();
            System.out.println("Post saved.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void addFriend(int userId, int friendId) {
        String sql = "INSERT INTO allfriend (userID, friendID, status) VALUES (?, ?, 'Pending')";

        try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, friendId);
            pstmt.executeUpdate();
            System.out.println("Friend from user " + userId + " to user " + friendId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void updateFriendStatus(int userId, int friendId, String status) {
        String sql = "UPDATE Friendships SET status = ? WHERE user_id = ? AND friend_id = ?";
        
        try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, userId);
            pstmt.setInt(3, friendId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void sendFriendRequest(int userId, int friendId) {
        String sql = "INSERT INTO friendships (userID, friendID, status) VALUES (?, ?, 'Pending')";
        try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, friendId);
            pstmt.executeUpdate();
            System.out.println("Friend request sent from user " + userId + " to user " + friendId);
        } catch (SQLException e) {
            System.out.println("Error sending friend request: " + e.getMessage());
        }
    }
    
    public static void cancelFriendRequest(int userId, int friendId) {
        String sql = "DELETE FROM friendships WHERE userID = ? AND friendID = ? AND status = 'Pending'";
        try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, friendId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Friend request cancelled.");
            } else {
                System.out.println("No pending request found to cancel.");
            }
        } catch (SQLException e) {
            System.out.println("Error canceling friend request: " + e.getMessage());
        }
    }

    public static void addVote(int vote, int postId, int userId) {
        String sql = "INSERT INTO votes(vote, postID, userID) VALUES(?, ?, ?)";
        try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
            pstmt.setInt(1, vote);
            pstmt.setInt(2, postId);
            pstmt.setInt(3, userId);
            pstmt.executeUpdate();
            System.out.println("Vote inserted.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addTotalVote(int postId, int totalVote) {
        String sql = "INSERT INTO totalvotes(postID, totalVote) VALUES(?, ?)";
        try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
            pstmt.setInt(1, postId);
            pstmt.setInt(2, totalVote);
            pstmt.executeUpdate();
            System.out.println("Total vote inserted.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}