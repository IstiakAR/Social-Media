package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static Connection connection;
    private static final String URL = "jdbc:sqlite:res/database/social_media.db";

    public static Connection connect() {
        connection = null;
        try {
            connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }
    public static void close() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection to SQLite has been closed.");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    public static void createTables() {
        String usersTable = "CREATE TABLE IF NOT EXISTS users ("
            + "userID INTEGER PRIMARY KEY,"
            + "username TEXT NOT NULL,"
            + "password TEXT NOT NULL,"
            + "name TEXT NOT NULL,"
            + "clue TEXT NOT NULL,"
            + "profilePicture BLOB,"
            + "Bio TEXT,"
            + "Education TEXT,"
            + "Workplace TEXT,"
            + "Email TEXT"
            + ");";

        String postsTable = "CREATE TABLE IF NOT EXISTS posts ("
            + "postID INTEGER PRIMARY KEY,"
            + "postContent TEXT NOT NULL,"
            + "userID INTEGER,"
            + "creationTime TEXT,"
            + "FOREIGN KEY (userID) REFERENCES users(userID)"
            + ");";
            
        String friendshipsTable = "CREATE TABLE IF NOT EXISTS friendships ("
            + "friendshipID INTEGER PRIMARY KEY," 
            + "userID INTEGER NOT NULL,"        
            + "friendID INTEGER NOT NULL,"       
            + "status TEXT NOT NULL DEFAULT 'Pending'," 
            + "FOREIGN KEY (userID) REFERENCES users(userID)," 
            + "FOREIGN KEY (friendID) REFERENCES users(userID)" 
            + ");";

        String allfriendTable = "CREATE TABLE IF NOT EXISTS allfriend ("
            + "allfriendID INTEGER PRIMARY KEY, " 
            + "userID INTEGER NOT NULL, "        
            + "friendID INTEGER NOT NULL, "       
            + "status TEXT NOT NULL DEFAULT 'Pending', " 
            + "FOREIGN KEY (userID) REFERENCES users(userID), " 
            + "FOREIGN KEY (friendID) REFERENCES users(userID)"
            + ");";

        String commentTable = "CREATE TABLE IF NOT EXISTS comments ("
            + "commentID INTEGER PRIMARY KEY,"
            + "commentText TEXT NOT NULL,"
            + "postID INTEGER NOT NULL,"
            + "userID INTEGER NOT NULL,"
            + "creationTime TEXT NOT NULL,"
            + "FOREIGN KEY (postID) REFERENCES posts(postID),"
            + "FOREIGN KEY (userID) REFERENCES users(userID)"
            + ");";

        String voteTable = "CREATE TABLE IF NOT EXISTS votes ("
            + "postID INTEGER NOT NULL,"
            + "userID INTEGER NOT NULL,"
            + "PRIMARY KEY (postID, userID),"
            + "FOREIGN KEY (postID) REFERENCES posts(postID) ON DELETE CASCADE,"
            + "FOREIGN KEY (userID) REFERENCES users(userID) ON DELETE CASCADE"
            + ");";

        String messagesTable = "CREATE TABLE IF NOT EXISTS messages (" 
            + "messageID INTEGER PRIMARY KEY AUTOINCREMENT," 
            + "senderID INTEGER NOT NULL," 
            + "receiverID INTEGER NOT NULL," 
            + "content TEXT NOT NULL," 
            + "timestamp TEXT DEFAULT CURRENT_TIMESTAMP," 
            + "status TEXT DEFAULT 'Sent', "
            + "messageType TEXT DEFAULT 'Text', "
            + "FOREIGN KEY (senderID) REFERENCES users(userID), " 
            + "FOREIGN KEY (receiverID) REFERENCES users(userID), " 
            + "CHECK (LENGTH(content) > 0)" 
            + ");";
        
        try (Statement stmt = connect().createStatement()) {
            stmt.execute(usersTable);
            stmt.execute(postsTable);
            stmt.execute(friendshipsTable);
            stmt.execute(allfriendTable);
            stmt.execute(commentTable);
            stmt.execute(voteTable);
            stmt.execute(messagesTable);
            System.out.println("Tables created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}