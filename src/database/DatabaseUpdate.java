package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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
    
    public static void addProfilePictureColumn() {
        String sql = "ALTER TABLE users ADD COLUMN profilePicture BLOB";

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Profile picture column added.");
        } catch (SQLException e) {
            if (e.getMessage().contains("duplicate column name")) {
                System.out.println("Profile picture column already exists.");
            } else {
                System.out.println(e.getMessage());
            }
        }
    }
}