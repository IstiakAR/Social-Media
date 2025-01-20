package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.*;

public class DatabaseGetter {
        public static List<User> getUsers() {
        String sql = "SELECT * FROM users";
        List<User>users = new ArrayList<>();

        try (Connection conn = Database.connect();
            Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                User user = new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("clue"),
                    rs.getInt("userID"),
                    rs.getBytes("profilePicture")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    public static User getUserByID(int userID) {
        String sql = "SELECT * FROM users WHERE userID = ?";
        User user = null;

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("clue"),
                    rs.getInt("userID"),
                    rs.getBytes("profilePicture")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }
    
    public static Map<Integer, Post> getUserPosts(int givenUserID) {
        String sql = "SELECT * FROM posts";
        Map<Integer, Post> posts = new HashMap<>();

        try (Connection conn = Database.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int userID = rs.getInt("userID");
                if (userID == givenUserID) {
                    Post post = new Post(
                        rs.getString("postContent"),
                        userID
                    );
                    post.setCreationTime(LocalDateTime.parse(rs.getString("creationDate")));
                    posts.put(post.getPostID(), post);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return posts;
    }


    public static Map<Integer, Post>getAllPosts() {
        String sql = "SELECT * FROM posts";
        Map<Integer, Post> posts = new HashMap<>();
    
        try (Connection conn = Database.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
    
            while (rs.next()) {
                    Post post = new Post(
                        rs.getString("postContent"),
                        rs.getInt("userID")
                    );
                    post.setCreationTime(LocalDateTime.parse(rs.getString("creationDate")));
                    posts.put(post.getPostID(), post);
                }
            }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return posts;
    }

    public static List<Post> getSavedPosts(int userID) {
        String sql = "SELECT p.* FROM posts p " +
                     "INNER JOIN saved_posts sp ON p.postID = sp.postID " +
                     "WHERE sp.userID = ?";
        List<Post> posts = new ArrayList<>();
    
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();
    
            while (rs.next()) {
                Post post = new Post(
                    rs.getString("postContent"),
                    rs.getInt("userID")
                );
                post.setCreationTime(LocalDateTime.parse(rs.getString("creationDate")));
                posts.add(post);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return posts;
    }

    public static Map<Integer, Comment> getCommentsMap() {
        String sql = "SELECT * FROM comments";
        Map<Integer, Comment> commentMap = new HashMap<>();
    
        try (Connection conn = Database.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    Comment comment = new Comment(
                        rs.getString("commentText"),
                        rs.getInt("commentID"),
                        rs.getInt("postID"),
                        rs.getInt("userID")
                    );
                    commentMap.put(comment.getInteractionID(), comment); 
                }
            }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return commentMap;
    }
}
