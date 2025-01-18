package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Post;
import model.User;

public class DatabaseInsert {
    public static void insertUser(User user) {
        String sql = "INSERT INTO users(userID, username, password, name, clue) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, user.getUserID());
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getName());
            pstmt.setString(5, user.getClue());
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
            pstmt.setString(4, post.getCreationTime().toString());
            int result = pstmt.executeUpdate();
            System.out.println("Post insert result: " + result); // Debug
        } catch (SQLException e) {
            System.out.println("Error inserting post: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
