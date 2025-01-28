package database;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import main.MainStorage;
import model.Reaction;

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
        
        // Set the parameters for the query
        pstmt.setBinaryStream(1, fis, (int) imageFile.length());
        pstmt.setString(2, userId);

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
    
    public static void updateVote(int vote, int postID, int userID) {
        String sql = "UPDATE votes SET vote = ? WHERE postID = ? AND userID = ?";

        try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
            pstmt.setInt(1, vote);
            pstmt.setInt(2, postID);
            pstmt.setInt(3, userID);
            int updated = pstmt.executeUpdate();
            if (updated == 0) {
                DatabaseInsert.addVote(vote, postID, userID);
            } else {
                System.out.println("Vote updated.");
            }
            MainStorage.getReactions().put(userID, new Reaction(vote, postID, userID));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateTotalVotes(int postID, int vote) {
        String sql = "UPDATE totalVotes SET totalVote = ? WHERE postID = ?";
        try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
            pstmt.setInt(1, postID);
            pstmt.setInt(2, vote);
            pstmt.executeUpdate();
            System.out.println("Total votes updated.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    } catch (SQLException e) {
        System.out.println("SQL Error: " + e.getMessage());
    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
    public static void updateVote(int vote, int postID, int userID) {
        String sql = "UPDATE votes SET vote = ? WHERE postID = ? AND userID = ?";

        try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
            pstmt.setInt(1, vote);
            pstmt.setInt(2, postID);
            pstmt.setInt(3, userID);
            int updated = pstmt.executeUpdate();
            if (updated == 0) {
                DatabaseInsert.addVote(vote, postID, userID);
            } else {
                System.out.println("Vote updated.");
            }
            MainStorage.getReactions().put(userID, new Reaction(vote, postID, userID));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateTotalVotes(int postID, int vote) {
        String sql = "UPDATE totalVotes SET totalVote = ? WHERE postID = ?";
        try (PreparedStatement pstmt = Database.connect().prepareStatement(sql)) {
            pstmt.setInt(1, postID);
            pstmt.setInt(2, vote);
            pstmt.executeUpdate();
            System.out.println("Total votes updated.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
