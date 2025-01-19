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
            + "clue TEXT NOT NULL"
            // + "bio TEXT"
            // + "picturePath TEXT"
            + ");";

        String postsTable = "CREATE TABLE IF NOT EXISTS posts ("
            + "postID INTEGER PRIMARY KEY,"
            + "postContent TEXT NOT NULL,"
            + "userID INTEGER,"
            + "creationDate TEXT,"
            + "FOREIGN KEY (userID) REFERENCES users(userID)"
            + ");";

        String commentsTable = "CREATE TABLE IF NOT EXISTS comments ("
            + "commentID INTEGER PRIMARY KEY,"
            + "commentText TEXT NOT NULL,"
            + "postID INTEGER,"
            + "userID INTEGER,"
            + "FOREIGN KEY (postID) REFERENCES posts(postID),"
            + "FOREIGN KEY (userID) REFERENCES users(userID)"
            + ");";

        String reactionsTable = "CREATE TABLE IF NOT EXISTS reactions ("
            + "reactionID INTEGER PRIMARY KEY,"
            + "reactionType INTEGER NOT NULL,"
            + "postID INTEGER,"
            + "userID INTEGER,"
            + "FOREIGN KEY (postID) REFERENCES posts(postID),"
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
}
