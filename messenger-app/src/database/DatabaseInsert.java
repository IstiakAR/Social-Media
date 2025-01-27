public class DatabaseInsert {
    public static void insertUser(User user) {
        // Implementation for inserting a user into the database
    }

    public static void insertPost(Post post) {
        // Implementation for inserting a post into the database
    }

    public static void insertComment(Comment comment) {
        // Implementation for inserting a comment into the database
    }

    public static void savePost(int userID, int postID) {
        // Implementation for saving a post
    }

    public static void addFriend(int userId, int friendId) {
        // Implementation for adding a friend
    }
    
    public static void updateFriendStatus(int userId, int friendId, String status) {
        // Implementation for updating friend status
    }

    public static void sendFriendRequest(int userId, int friendId) {
        String query = "INSERT INTO friendships (userID, friendID, status) VALUES (?, ?, 'Pending')";
        try (Connection conn = DatabaseConnect.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, friendId);
            stmt.executeUpdate();
            System.out.println("Friend request sent from user " + userId + " to user " + friendId);
        } catch (SQLException e) {
            System.out.println("Error sending friend request: " + e.getMessage());
        }
    }
    
    public static void cancelFriendRequest(int userId, int friendId) {
        // Implementation for canceling a friend request
    }

    public static void sendMessage(int senderId, int receiverId, String content) {
        String query = "INSERT INTO messages (senderID, receiverID, content) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnect.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, senderId);
            stmt.setInt(2, receiverId);
            stmt.setString(3, content);
    
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Message sent successfully.");
            } else {
                System.out.println("Failed to send the message.");
            }
        } catch (SQLException e) {
            System.out.println("Error sending message: " + e.getMessage());
        }
    }
}