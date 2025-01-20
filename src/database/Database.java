package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
            + "clue TEXT NOT NULL,"
            + "profilePicture BLOB"
            + ");";

        String postsTable = "CREATE TABLE IF NOT EXISTS posts ("
            + "postID INTEGER PRIMARY KEY,"
            + "postContent TEXT NOT NULL,"
            + "userID INTEGER,"
            + "creationDate TEXT,"
            + "FOREIGN KEY (userID) REFERENCES users(userID)"
            + ");";

        String savedPostsTable = "CREATE TABLE IF NOT EXISTS saved_posts ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "userID INTEGER,"
            + "postID INTEGER,"
            + "FOREIGN KEY (userID) REFERENCES users(userID),"
            + "FOREIGN KEY (postID) REFERENCES posts(postID)"
            + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(usersTable);
            stmt.execute(postsTable);
            stmt.execute(savedPostsTable);
            System.out.println("Tables created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}