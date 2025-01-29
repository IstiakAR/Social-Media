package database;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.User;

public class DatabaseUpdate {
	public static void updateUserProfilePicture(int userID, byte[] profilePicture) {
		String sql = "UPDATE users SET profilePicture = ? WHERE userID = ?";

		try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
		pstmt.setBytes(1, profilePicture);
		pstmt.setInt(2, userID);
		pstmt.executeUpdate();
		System.out.println("Profile picture updated.");
		} catch (SQLException e) {
		System.out.println(e.getMessage());
		}
	}
    
  public static void addProfilePicture(String userId, File imageFile) {
    String sql = "UPDATE users SET profilePicture = ? WHERE userId = ?";

    try (Connection conn = Database.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         FileInputStream fis = new FileInputStream(imageFile)) {
        
        pstmt.setBinaryStream(1, fis, (int) imageFile.length());
        pstmt.setString(2, userId);

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
  
	public static void deleteVote(int postID, int userID) {
		String sql = "DELETE FROM votes WHERE postID = ? AND userID = ?";
		try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
			pstmt.setInt(1, postID);
			pstmt.setInt(2, userID);
			pstmt.executeUpdate();
			System.out.println("Vote deleted.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
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
	public static boolean updateFriendStatus(int userId, int friendId, String status) {
		String sql = "UPDATE friendships SET status = ? WHERE userID = ? AND friendID = ?";
		try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {

			pstmt.setString(1, status);
			pstmt.setInt(2, userId);
			pstmt.setInt(3, friendId);
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			System.out.println("Error updating friend status: " + e.getMessage());
			return false;
		}
	}
    public static void updateUserDetails(User user) {
        System.out.println("Bio" + user.getBio() + ' ' + user.getEducation() + ' ' + user.getWorkplace() + ' ' + user.getEmail());
        String sql = "UPDATE users SET Bio = ?, Education = ?, Workplace = ?, Email = ? WHERE userID = ?";
        try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
            pstmt.setString(1, user.getBio());
            pstmt.setString(2, user.getEducation());
            pstmt.setString(3, user.getWorkplace());
            pstmt.setString(4, user.getEmail());
            pstmt.setInt(5, user.getUserID());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User details updated successfully.");
            } else {
                System.out.println("No user found with the given ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating user details: " + e.getMessage());
        }
    }
}
