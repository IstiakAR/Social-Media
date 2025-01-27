public class DatabaseConnect {
    private static final String URL = "jdbc:mysql://localhost:3306/messenger_db";
    private static final String USER = "yourUsername";
    private static final String PASSWORD = "yourPassword";
    private Connection connection;

    public Connection connect() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            System.out.println("Connection to database failed: " + e.getMessage());
        }
        return connection;
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Failed to close the connection: " + e.getMessage());
        }
    }
}