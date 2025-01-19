package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Post;
import model.User;

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
                    rs.getInt("userID")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    public static List<Post> getPosts() {
        String sql = "SELECT * FROM posts";
        List<Post> posts = new ArrayList<>();

        try (Connection conn = Database.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int userID = rs.getInt("userID");
                User user = getUserByID(userID);
                if (user != null) {
                    Post post = new Post(
                            rs.getString("postContent"),
                            rs.getInt("postID"),
                            userID
                    );
                    post.setCreationTime(LocalDateTime.parse(rs.getString("creationDate")));
                    posts.add(post);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return posts;
    }
    
    // public static List<Comment> getComments(int postID) {
    //     String sql = "SELECT * FROM comments WHERE postID = ?";
    //     List<Comment> comments = new ArrayList<>();

    //     try (Connection conn = Database.connect();
    //          PreparedStatement pstmt = conn.prepareStatement(sql)) {
    //         pstmt.setInt(1, postID);
    //         ResultSet rs = pstmt.executeQuery();

    //         while (rs.next()) {
    //             User user = getUserByID(rs.getInt("userID"));
    //             if (user != null) {
    //                 Comment comment = new Comment(
    //                         rs.getString("commentText"),
    //                         new Post(rs.getString("postContent"), rs.getInt("postID"), user),
    //                         user
    //                 );
    //                 comments.add(comment);
    //             }
    //         }
    //     } catch (SQLException e) {
    //         System.out.println(e.getMessage());
    //     }
    //     return comments;
    // }

    public static List<Post> getAllPosts() {
        String sql = "SELECT * FROM posts";
        List<Post> posts = new ArrayList<>();
    
        try (Connection conn = Database.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
    
            while (rs.next()) {
                    Post post = new Post(
                            rs.getString("postContent"),
                            rs.getInt("postID"),
                            rs.getInt("userID")
                    );
                    posts.add(post);
                }
            }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return posts;
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
                        rs.getInt("userID")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
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
                    rs.getInt("postID"),
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
    
}
