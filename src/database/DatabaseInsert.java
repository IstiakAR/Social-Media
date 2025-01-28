package database;

import java.io.File;
import java.io.FileInputStream;
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


//   public static void addProfilePicture(int userId, File imageFile) {
//     System.out.println("Add Profile picture called");
//     String sql = "UPDATE users SET profilePicture = ? WHERE userId = ?";

//     try (Connection conn = Database.connect();
//          PreparedStatement pstmt = conn.prepareStatement(sql);
//          FileInputStream fis = new FileInputStream(imageFile)) {
        
//         // Set the parameters for the query
//         pstmt.setBinaryStream(6, fis, (int) imageFile.length());
//         pstmt.setInt(1, userId);

//         // Execute the update
//         int rowsUpdated = pstmt.executeUpdate();

//         if (rowsUpdated > 0) {
//             System.out.println("Profile picture updated successfully for user ID: " + userId);
//         } else {
//             System.out.println("User ID not found. No update made.");
//         }

//     } catch (SQLException e) {
//         System.out.println("SQL Error: " + e.getMessage());
//     } catch (Exception e) {
//         System.out.println("Error: " + e.getMessage());
//     }
// }

public static void addProfilePicture(int userId, File imageFile) {
    System.out.println("Add Profile Picture called");
    String sql = "UPDATE users SET profilePicture = ? WHERE userId = ?";

    try (Connection conn = Database.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         FileInputStream fis = new FileInputStream(imageFile)) {

        // Set the parameters for the query
        pstmt.setBinaryStream(6, fis, (int) imageFile.length()); // Set the binary stream for profilePicture
        pstmt.setInt(2, userId); // Set the userId

        // Execute the update
        int rowsUpdated = pstmt.executeUpdate();

        if (rowsUpdated > 0) {
            System.out.println("Profile picture updated successfully for user ID: " + userId);
        } else {
            System.out.println("User ID not found. No update made.");
        }

    } catch (SQLException e) {
        System.out.println("SQL Error: " + e.getMessage());
    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}


}