package database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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

	public static void addProfilePictureColumn() {
		String sql = "ALTER TABLE users ADD COLUMN profilePicture BLOB";

		try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
		pstmt.execute(sql);
		System.out.println("Profile picture column added.");
		} catch (SQLException e) {
		if (e.getMessage().contains("duplicate column name")) {
			System.out.println("Profile picture column already exists.");
		} else {
			System.out.println(e.getMessage());
		}
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
}
