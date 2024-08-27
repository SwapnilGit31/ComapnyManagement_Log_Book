package addressBook_console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/companywgs";
    private static final String USER = "root";
    private static final String PASSWORD = "Swapnil@31";

    public static Connection getConnection(String query) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database successfully!");
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
        return connection;
    }

    public static void main(String[] args) {
        String query = "";
        getConnection(query);
    }
}
