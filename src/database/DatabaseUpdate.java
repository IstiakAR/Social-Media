package database;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseUpdate {
    public static void updateUserProfilePicture(int userID, byte[] profilePicture) {
        String sql = "UPDATE users SET profilePicture = ? WHERE userID = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
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



    
}