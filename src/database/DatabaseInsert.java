package database;

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
    
    public static void addVote(int postId, int userId) {
        String sql = "INSERT INTO votes(postID, userID) VALUES(?, ?)";
        try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
            pstmt.setInt(1, postId);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
            System.out.println("Vote inserted.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	public static boolean toggleVote(int postID, int userID) {
		if (voteExists(postID, userID)) {
			DatabaseUpdate.deleteVote(postID, userID);
			return false;
		} else {
			addVote(postID, userID);
			return true;
		}
	}

	public static boolean voteExists(int postID, int userID) {
		String sql = "SELECT 1 FROM votes WHERE postID = ? AND userID = ?";
		try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
			pstmt.setInt(1, postID);
			pstmt.setInt(2, userID);
			ResultSet rs = pstmt.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

    public static void sendMessage(int senderId, int receiverId, String content) {
        if (content == null || content.trim().isEmpty()) {
            System.out.println("Message content cannot be empty.");
            return;
        }

        String userCheckQuery = "SELECT COUNT(*) FROM users WHERE userID = ?";
        String insertMessageQuery = "INSERT INTO messages (senderID, receiverID, content) VALUES (?, ?, ?)";

        try (PreparedStatement userCheckStmt = Database.connect().prepareStatement(userCheckQuery);
            PreparedStatement insertStmt = Database.connect().prepareStatement(insertMessageQuery)) {

            userCheckStmt.setInt(1, senderId);
            try (ResultSet senderResult = userCheckStmt.executeQuery()) {
                if (!senderResult.next() || senderResult.getInt(1) == 0) {
                    System.out.println("Sender does not exist.");
                    return;
                }
            }

            userCheckStmt.setInt(1, receiverId);
            try (ResultSet receiverResult = userCheckStmt.executeQuery()) {
                if (!receiverResult.next() || receiverResult.getInt(1) == 0) {
                    System.out.println("Receiver does not exist.");
                    return;
                }
            }

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

        try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
            pstmt.setInt(1, message.getSenderId());
            pstmt.setInt(2, message.getReceiverId());
            pstmt.setString(3, message.getContent());

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