import java.sql.*;
import java.util.Scanner;

class HouseRentalSystem {
    private static final String URL = "jdbc:mysql://localhost:3306/houserental";
    private static final String USER = "root"; 
    private static final String PASSWORD = ""; 
    private Connection connection;

    public HouseRentalSystem() {
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
    public void searchHouse() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter location to search: ");
        String location = scanner.nextLine();

        String query = "SELECT * FROM houses WHERE location = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, location);
            ResultSet rs = stmt.executeQuery();

            boolean found = false;
            while (rs.next()) {
                System.out.println("House ID: " + rs.getInt("id") + 
                                   ", Location: " + rs.getString("location") + 
                                   ", Price: rupees " + rs.getDouble("price"));
                found = true;
            }
            if (!found) {
                System.out.println("No houses found in " + location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HouseRentalSystem system = new HouseRentalSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nHouse Rental System");
            System.out.println("1. Search House");
            System.out.println("2. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    system.searchHouse();
                    break;
                case 2:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
