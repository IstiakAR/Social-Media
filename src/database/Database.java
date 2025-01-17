package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.User;

public class Database {
    private static final String URL = "jdbc:sqlite:res/database/social_media.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void createTables() {
        String usersTable = "CREATE TABLE IF NOT EXISTS users ("
                + "userID INTEGER PRIMARY KEY,"
                + "username TEXT NOT NULL,"
                + "password TEXT NOT NULL,"
                + "name TEXT NOT NULL,"
                + "clue TEXT NOT NULL"
                + ");";

        String postsTable = "CREATE TABLE IF NOT EXISTS posts ("
                + "postID INTEGER PRIMARY KEY,"
                + "postContent TEXT NOT NULL,"
                + "userID INTEGER,"
                + "FOREIGN KEY (userID) REFERENCES users(userID)"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(usersTable);
            stmt.execute(postsTable);
            System.out.println("Tables created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertUser(User user) {
        String sql = "INSERT INTO users(userID, username, password, name, clue) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, user.userID);
            pstmt.setString(2, user.username);
            pstmt.setString(3, user.password);
            pstmt.setString(4, user.name);
            pstmt.setString(5, user.clue);
            pstmt.executeUpdate();
            System.out.println("User inserted.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<User> getUsers() {
        String sql = "SELECT * FROM users";
        List<User>users = new ArrayList<>();

        try (Connection conn = connect();
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
}
