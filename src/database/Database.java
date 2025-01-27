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
            
            String friendshipsTable = "CREATE TABLE IF NOT EXISTS friendships ("
            + "friendshipID INTEGER PRIMARY KEY," 
            + "userID INTEGER NOT NULL,"        
            + "friendID INTEGER NOT NULL,"       
            + "status TEXT NOT NULL DEFAULT 'Pending'," 
            + "createdAt TEXT DEFAULT CURRENT_TIMESTAMP," 
            + "FOREIGN KEY (userID) REFERENCES users(userID)," 
            + "FOREIGN KEY (friendID) REFERENCES users(userID)" 
            + ");";

            String allfriendTable = "CREATE TABLE IF NOT EXISTS allfriend ("
            + "allfriendID INTEGER PRIMARY KEY, " 
            + "userID INTEGER NOT NULL, "        
            + "friendID INTEGER NOT NULL, "       
            + "status TEXT NOT NULL DEFAULT 'Pending', " 
            + "createdAt TEXT DEFAULT CURRENT_TIMESTAMP, " 
            + "FOREIGN KEY (userID) REFERENCES users(userID), " 
            + "FOREIGN KEY (friendID) REFERENCES users(userID)"
            + ");";
            String messagesTable = "CREATE TABLE IF NOT EXISTS messages (" 
            + "messageID INTEGER PRIMARY KEY AUTOINCREMENT," 
            + "senderID INTEGER NOT NULL," 
            + "receiverID INTEGER NOT NULL," 
            + "content TEXT NOT NULL," 
            + "timestamp TEXT DEFAULT CURRENT_TIMESTAMP," 
            + "status TEXT DEFAULT 'Sent', "  // Status: 'Sent', 'Received', 'Read', etc.
            + "messageType TEXT DEFAULT 'Text', "  // Could be 'Text', 'Image', 'File', etc.
            + "FOREIGN KEY (senderID) REFERENCES users(userID), " 
            + "FOREIGN KEY (receiverID) REFERENCES users(userID), " 
            + "CHECK (LENGTH(content) > 0)" 
            + ");";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(usersTable);
            stmt.execute(postsTable);
            stmt.execute(savedPostsTable);
            stmt.execute(friendshipsTable);
            stmt.execute(allfriendTable);
            stmt.execute(messagesTable);
            System.out.println("Tables created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}