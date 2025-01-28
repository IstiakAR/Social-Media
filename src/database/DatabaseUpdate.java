package database;

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