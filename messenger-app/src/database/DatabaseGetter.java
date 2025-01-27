public class DatabaseGetter {
    public static User getUserById(int userId) {
        User user = null;
        String query = "SELECT * FROM users WHERE userID = ?";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new User(
                    rs.getInt("userID"),
                    rs.getString("name"),
                    rs.getString("bio"),
                    rs.getBytes("profilePicture")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving user: " + e.getMessage());
        }
        return user;
    }

    public static List<Message> getMessages(int userId) {
        List<Message> messages = new ArrayList<>();
        String query = "SELECT * FROM messages WHERE senderID = ? OR receiverID = ? ORDER BY timestamp ASC";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Message message = new Message(
                    rs.getInt("senderID"),
                    rs.getInt("receiverID"),
                    rs.getString("content"),
                    rs.getTimestamp("timestamp")
                );
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving messages: " + e.getMessage());
        }
        return messages;
    }

    public static List<User> getAllFriends(int userId) {
        List<User> friends = new ArrayList<>();
        String query = "SELECT u.* FROM users u JOIN friendships f ON u.userID = f.friendID WHERE f.userID = ? AND f.status = 'Confirmed'";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                User friend = new User(
                    rs.getInt("userID"),
                    rs.getString("name"),
                    rs.getString("bio"),
                    rs.getBytes("profilePicture")
                );
                friends.add(friend);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving friends: " + e.getMessage());
        }
        return friends;
    }

    public static boolean isFriend(int userId, int friendId) {
        String query = "SELECT * FROM friendships WHERE userID = ? AND friendID = ? AND status = 'Confirmed'";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, friendId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error checking friendship: " + e.getMessage());
            return false;
        }
    }

    public static boolean isConfirm(int userId, int friendId) {
        String query = "SELECT * FROM friendships WHERE (userID = ? AND friendID = ?) OR (userID = ? AND friendID = ?) AND status = 'Pending'";
        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, friendId);
            pstmt.setInt(3, friendId);
            pstmt.setInt(4, userId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error checking friendship confirmation: " + e.getMessage());
            return false;
        }
    }
}