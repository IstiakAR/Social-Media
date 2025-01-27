package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Comment;
import model.Message;
import model.Post;
import model.User;

public class DatabaseInsert {
    public static void insertUser(User user) {
        String sql = "INSERT INTO users(userID, username, password, name, clue, profilePicture) VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
        String sql = "INSERT INTO posts(postID, postContent, userID, creationDate) VALUES(?, ?, ?, ?)";
        System.out.println("Attempting to insert post: " + post.getPostContent()); // Debug
    
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
        String sql = "INSERT INTO comments(commentID, commentText, postID, userID) VALUES(?, ?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, comment.getInteractionID());
            pstmt.setString(2, comment.getCommentText());
            pstmt.setInt(3, comment.getPostID());
            pstmt.setInt(4, comment.getUserID());
            System.out.println(comment.getInteractionID() + ' ' + comment.getCommentText() + ' ' + comment.getPostID() + ' ' + comment.getUserID());
            pstmt.executeUpdate();
            System.out.println("Comment inserted.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void savePost(int userID, int postID) {
        String sql = "INSERT INTO saved_posts(userID, postID) VALUES(?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, friendId);
            stmt.executeUpdate();
            System.out.println("Friend from user " + userId + " to user " + friendId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void updateFriendStatus(int userId, int friendId, String status) {
        String query = "UPDATE Friendships SET status = ? WHERE user_id = ? AND friend_id = ?";
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, userId);
            stmt.setInt(3, friendId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void sendFriendRequest(int userId, int friendId) {
        String query = "INSERT INTO friendships (userID, friendID, status) VALUES (?, ?, 'Pending')";
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, friendId);
            stmt.executeUpdate();
            System.out.println("Friend request sent from user " + userId + " to user " + friendId);
        } catch (SQLException e) {
            System.out.println("Error sending friend request: " + e.getMessage());
        }
    }
    
    // Cancel a sent friend request
    public static void cancelFriendRequest(int userId, int friendId) {
        String query = "DELETE FROM friendships WHERE userID = ? AND friendID = ? AND status = 'Pending'";
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, friendId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Friend request cancelled.");
            } else {
                System.out.println("No pending request found to cancel.");
            }
        } catch (SQLException e) {
            System.out.println("Error canceling friend request: " + e.getMessage());
        }
    }

   public static void sendMessage(int senderId, int receiverId, String content) {
    if (content == null || content.trim().isEmpty()) {
        System.out.println("Message content cannot be empty.");
        return;
    }

    String userCheckQuery = "SELECT COUNT(*) FROM users WHERE userID = ?";
    String insertMessageQuery = "INSERT INTO messages (senderID, receiverID, content) VALUES (?, ?, ?)";

    try (Connection conn = Database.connect();
         PreparedStatement userCheckStmt = conn.prepareStatement(userCheckQuery);
         PreparedStatement insertStmt = conn.prepareStatement(insertMessageQuery)) {

        // Check if sender exists
        userCheckStmt.setInt(1, senderId);
        try (ResultSet senderResult = userCheckStmt.executeQuery()) {
            if (!senderResult.next() || senderResult.getInt(1) == 0) {
                System.out.println("Sender does not exist.");
                return;
            }
        }

        // Check if receiver exists
        userCheckStmt.setInt(1, receiverId);
        try (ResultSet receiverResult = userCheckStmt.executeQuery()) {
            if (!receiverResult.next() || receiverResult.getInt(1) == 0) {
                System.out.println("Receiver does not exist.");
                return;
            }
        }

        // Insert the message into the database
        insertStmt.setInt(1, senderId);
        insertStmt.setInt(2, receiverId);
        insertStmt.setString(3, content);

        int rowsInserted = insertStmt.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Message sent successfully.");
        } else {
            System.out.println("Failed to send the message.");
        }

    } catch (SQLException e) {
        System.out.println("Error sending message: " + e.getMessage());
    }
}
    
public static void saveMessage(Message message) {
    String sql = "INSERT INTO messages (senderID, receiverID, content) VALUES (?, ?, ?)";

    try (Connection conn = Database.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        // Set parameters for the message
        pstmt.setInt(1, message.getSenderId()); // Sender ID
        pstmt.setInt(2, message.getReceiverId()); // Receiver ID
        pstmt.setString(3, message.getContent()); // Message content

        // Execute the insert query
        int rowsInserted = pstmt.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Message saved successfully.");
        } else {
            System.out.println("Failed to save the message.");
        }

    } catch (SQLException e) {
        System.out.println("Error saving message: " + e.getMessage());
        e.printStackTrace();
    }
}

}