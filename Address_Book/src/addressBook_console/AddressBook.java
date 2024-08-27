package addressBook_console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
class InvalidIDException extends Exception{
    public InvalidIDException(String msg){
        super(msg);
    }
}
public  class AddressBook {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/companywgs";
    private static final String DB_USER = "root";  // Replace with your MySQL username
    private static final String DB_PASSWORD = "Swapnil@31";  // Replace with your MySQL password

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("Select Employee Type");
            System.out.println("------------------------");
            System.out.println("1. General Employee Operations");
            System.out.println("2. Associate Consultant Delivery Operations");
            System.out.println("3. Consultant Delivery Operations");
            System.out.println("4. Manager operations");
            System.out.println("5. Exit");

            System.out.println();

            System.out.println("Enter Your Choice - ");

            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    manageEmployees("General");
                    break;
                case 2:
                    manageEmployees("Associate Consultant Delivery");
                    break;
                case 3:
                    manageEmployees("Consultant Delivery");
                    break;
                case 4:
                    manageEmployees("Manager");
                    break;
                case 5:
                    System.out.println("Exit Program");
                    break;
                default:
                    System.out.println("Invalid choice");
            }

        } while (choice != 5);
    }
    private static void loadAllEmployees() {
        String query = "SELECT e.*, a.buildingName, a.city, a.pinCode, a.mobNo " +
                "FROM Employee e " +
                "JOIN Address a ON e.empId = a.empId";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int empId = rs.getInt("empId");
                String empName = rs.getString("empName");
                String empCompanyName = rs.getString("empCompanyName");
                String empBloodGroup = rs.getString("empBloodGroup");
                String buildingName = rs.getString("buildingName");
                String city = rs.getString("city");
                String pinCode = rs.getString("pinCode");
                String mobNo = rs.getString("mobNo");

                Address address = new Address(buildingName, city, pinCode, mobNo);
                Employee employee = createEmployeeFromResultSet(rs); // This should match the method definition

                employee.setAddress(address);

               // addressBook.put(empId, employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void manageEmployees(String type) {
        int operation;
        do {
            System.out.println("Choose CRUD Operation for " + type + "Employee: ");
            System.out.println("---------------------------------------------");
            System.out.println("1. Add Employee");
            System.out.println("2. Display All Employee");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Go back to the main menu");
            System.out.println();

            System.out.println("Enter Your Choice - ");
            operation = scanner.nextInt();

            switch (operation) {
                case 1:
                    addEmployee(type);
                    break;
                case 2:
                    displayAllEmployees(type);
                    break;
                case 3:
                    try {
                        updateEmployee();
                    } catch (InvalidIDException | SQLException e) {
                        System.out.println("Exception Occured:  " + e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        deleteEmployee(type);
                    } catch (InvalidIDException e) {
                        System.out.println("Exception Occured:  " + e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Return to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice");
            }

        } while (operation != 5);

    }
    private static void addEmployee(String type) {
        System.out.println("Enter Employee Details :");
        System.out.println("-------------------------");
        System.out.println("Enter Employee ID");
        int empId = scanner.nextInt();
        scanner.nextLine();
        String checkQuery = "SELECT COUNT(*) FROM Employee WHERE empId = ?";
        try (Connection conn = connect();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            checkStmt.setInt(1, empId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("Employee with ID " + empId + " already exists. Please use a different ID.");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("Enter Employee Name");
        String empName = scanner.nextLine();

        System.out.println("Enter Employee Company_Name");
        String empCompanyName = scanner.nextLine();

        System.out.println("Enter Employee Blood_Group");
        String empBloodGroup = scanner.nextLine();

        System.out.println("Address Details");
        System.out.println("------------------");

        System.out.println("Enter Building Name :");
        String building_Name = scanner.nextLine();

        System.out.println("Enter City Name :");
        String city = scanner.nextLine();

        System.out.println("Enter pinCode :");
        String pinCode = scanner.nextLine();

        System.out.println("Enter Employee Mobile No : :");
        String mobNo = scanner.nextLine();

        Address address = new Address(building_Name, city, pinCode, mobNo);

        Employee employee = createEmployee(type, empId, empName, empCompanyName, empBloodGroup, address);

        if (employee != null) {
            System.out.println(type + "Employee Added Successfully");
        } else {
            System.out.println(type + " Invalid Employee Type");
        }

        String employeeSQL = "INSERT INTO Employee (empId, empName, empCompanyName, empBloodGroup, gcmLevel, dassId, teamSize, location, consultingLevel, leadProjects, skillSet, reportsTo, projectRole, buildingName, city, pinCode, mobNo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


        try (Connection conn = connect();
             PreparedStatement employeeStmt = conn.prepareStatement(employeeSQL)) {

            // Insert into Employee table
            employeeStmt.setInt(1, employee.getEmpId());
            employeeStmt.setString(2, employee.getEmpName());
            employeeStmt.setString(3, employee.getEmpCompanyName());
            employeeStmt.setString(4, employee.getEmpBloodGroup());

            if (employee instanceof Manager) {
                Manager manager = (Manager) employee;
                employeeStmt.setString(5, manager.getGCMLevel());
                employeeStmt.setString(6, manager.getDassId());
                employeeStmt.setString(7, manager.getTeamSize());
                employeeStmt.setString(8, manager.getLocation());
                employeeStmt.setNull(9, java.sql.Types.VARCHAR); // consultingLevel
                employeeStmt.setNull(10, java.sql.Types.VARCHAR); // leadProjects
                employeeStmt.setNull(11, java.sql.Types.VARCHAR); // skillSet
                employeeStmt.setNull(12, java.sql.Types.VARCHAR); // reportsTo
                employeeStmt.setNull(13, java.sql.Types.VARCHAR); // projectRole
                employeeStmt.setString(14, address.getBuildingName());
                employeeStmt.setString(15, address.getCity());
                employeeStmt.setString(16, address.getPinCode());
                employeeStmt.setString(17, address.getMobNo());
            } else if (employee instanceof ConsultantDelivery) {
                ConsultantDelivery consultantDelivery = (ConsultantDelivery) employee;
                employeeStmt.setString(5, consultantDelivery.getGCMLevel());
                employeeStmt.setString(6, consultantDelivery.getDassId());
                employeeStmt.setNull(7, java.sql.Types.VARCHAR); // teamSize
                employeeStmt.setNull(8, java.sql.Types.VARCHAR); // location
                employeeStmt.setString(9, consultantDelivery.getConsultingLevel());
                employeeStmt.setString(10, String.join(",", consultantDelivery.getLeadProjects()));
                employeeStmt.setNull(11, java.sql.Types.VARCHAR); // skillSet
                employeeStmt.setNull(12, java.sql.Types.VARCHAR); // reportsTo
                employeeStmt.setNull(13, java.sql.Types.VARCHAR); // projectRole
                employeeStmt.setString(14, address.getBuildingName());
                employeeStmt.setString(15, address.getCity());
                employeeStmt.setString(16, address.getPinCode());
                employeeStmt.setString(17, address.getMobNo());
            } else if (employee instanceof AssociateConsultantDelivery) {
                AssociateConsultantDelivery associateConsultantDelivery = (AssociateConsultantDelivery) employee;
                employeeStmt.setString(5, associateConsultantDelivery.getGCMLevel());
                employeeStmt.setString(6, associateConsultantDelivery.getDassId());
                employeeStmt.setNull(7, java.sql.Types.VARCHAR); // teamSize
                employeeStmt.setNull(8, java.sql.Types.VARCHAR); // location
                employeeStmt.setNull(9, java.sql.Types.VARCHAR); // consultingLevel
                employeeStmt.setNull(10, java.sql.Types.VARCHAR); // leadProjects
                employeeStmt.setString(11, String.join(",", associateConsultantDelivery.getSkillSet()));
                employeeStmt.setString(12, associateConsultantDelivery.getReportsTo());
                employeeStmt.setString(13, associateConsultantDelivery.getProjectRole());
                employeeStmt.setString(14, address.getBuildingName());
                employeeStmt.setString(15, address.getCity());
                employeeStmt.setString(16, address.getPinCode());
                employeeStmt.setString(17, address.getMobNo());
            } else {
                // General Employee
                employeeStmt.setNull(5, java.sql.Types.VARCHAR); // gcmLevel
                employeeStmt.setNull(6, java.sql.Types.VARCHAR); // dassId
                employeeStmt.setNull(7, java.sql.Types.VARCHAR); // teamSize
                employeeStmt.setNull(8, java.sql.Types.VARCHAR); // location
                employeeStmt.setNull(9, java.sql.Types.VARCHAR); // consultingLevel
                employeeStmt.setNull(10, java.sql.Types.VARCHAR); // leadProjects
                employeeStmt.setNull(11, java.sql.Types.VARCHAR); // skillSet
                employeeStmt.setNull(12, java.sql.Types.VARCHAR); // reportsTo
                employeeStmt.setNull(13, java.sql.Types.VARCHAR); // projectRole
                employeeStmt.setString(14, address.getBuildingName());
                employeeStmt.setString(15, address.getCity());
                employeeStmt.setString(16, address.getPinCode());
                employeeStmt.setString(17, address.getMobNo());
            }
            employeeStmt.executeUpdate();
            String addressSQL = "INSERT INTO Address (empId, buildingName, city, pinCode, mobNo) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement addressStmt = conn.prepareStatement(addressSQL)) {
                addressStmt.setInt(1, employee.getEmpId());
                addressStmt.setString(2, address.getBuildingName());
                addressStmt.setString(3, address.getCity());
                addressStmt.setString(4, address.getPinCode());
                addressStmt.setString(5, address.getMobNo());
                addressStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static Employee createEmployee(String type, int empId, String empName, String empCompanyName, String empBloodGroup, Address address) {
        switch (type) {
            case "General":
                return new Employee(empId, empName, empCompanyName, empBloodGroup, address);
            case "Manager":
                return createManager(empId, empName, empCompanyName, empBloodGroup, address);
            case "Consultant Delivery":
                return createConsultantDelivery(empId, empName, empCompanyName, empBloodGroup, address);
            case "Associate Consultant Delivery":
                return createAssociateConsultantDelivery(empId, empName, empCompanyName, empBloodGroup, address);
            default:
                return null;
        }
    }
    private static Employee createManager(int empId, String empName, String empCompanyName, String empBloodGroup, Address address) {
        System.out.println("Enter GCM level: ");
        String gcmLevel = scanner.nextLine();

        System.out.println("Enter Dass Id : ");
        String dassId = scanner.nextLine();

        System.out.println("Enter Team size: ");
        String teamSize = scanner.nextLine();

        System.out.println("Enter location of the office where manager is sitting");
        String location = scanner.nextLine();

        return new Manager(empId, empName, empCompanyName, empBloodGroup, new Address("", "", "", ""), gcmLevel, dassId, teamSize, location);

    }
    private static Employee createConsultantDelivery(int empId, String empName, String empCompanyName, String empBloodGroup, Address address) {
        System.out.println("Enter GCM level: ");
        String gcmLevel = scanner.nextLine();

        System.out.println("Enter Dass Id : ");
        String dassId = scanner.nextLine();

        System.out.println("Enter consulting level: ");
        String consultingLevel = scanner.nextLine();

        System.out.println("Enter how many project you have led?");

        List<String> leadProjects = Arrays.asList(scanner.nextLine().split(","));
        return new ConsultantDelivery(empId, empCompanyName, empName, empBloodGroup, new Address("", "", "", ""), gcmLevel, dassId, consultingLevel, leadProjects);
    }
    private static Employee createAssociateConsultantDelivery(int empId, String empName, String empCompanyName, String empBloodGroup, Address address) {
        System.out.println("Enter GCM level: ");
        String gcmLevel = scanner.nextLine();

        System.out.println("Enter Dass Id : ");
        String dassId = scanner.nextLine();

        System.out.println("Enter Skill set : ");
        List<String> skillSet = Arrays.asList(scanner.nextLine().split(","));

        System.out.println("Reports to : ");
        String reportsTo = scanner.nextLine();

        System.out.println("Project Role : ");
        String projectRole = scanner.nextLine();

        return new AssociateConsultantDelivery(empId, empName, empCompanyName, empBloodGroup, new Address("", "", "", ""), gcmLevel, dassId, skillSet, reportsTo, projectRole);
    }
    private static void displayAllEmployees(String type) {
        // Construct base query
        String query = "SELECT e.empId, e.empName, e.empCompanyName, e.empBloodGroup, " +
                "e.gcmLevel, e.dassId, e.teamSize, e.location, e.consultingLevel, " +
                "e.leadProjects, e.skillSet, e.reportsTo, e.projectRole, " +
                "a.buildingName, a.city, a.pinCode, a.mobNo " +
                "FROM employee e " +
                "LEFT JOIN address a ON e.empId = a.empId WHERE 1=1"; // Use WHERE 1=1 to facilitate appending additional conditions

        switch (type) {
            case "General":
                query += " AND teamSize IS NULL AND consultingLevel IS NULL AND skillSet IS NULL";
                break;
            case "Manager":
                query += " AND teamSize IS NOT NULL AND consultingLevel IS NULL AND skillSet IS NULL";
                break;
            case "Consultant Delivery":
                query += " AND teamSize IS NULL AND consultingLevel IS NOT NULL AND skillSet IS NULL";
                break;
            case "Associate Consultant Delivery":
                query += " AND teamSize IS NULL AND consultingLevel IS NULL AND skillSet IS NOT NULL";
                break;
            default:
                System.out.println("Invalid employee type.");
                return;
        }
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            boolean hasResults = false;
            while (rs.next()) {
                int empId = rs.getInt("empId");
                String empName = rs.getString("empName");
                String empCompanyName = rs.getString("empCompanyName");
                String empBloodGroup = rs.getString("empBloodGroup");
                String gcmLevel = rs.getString("gcmLevel");
                String dassId = rs.getString("dassId");
                String teamSize = rs.getString("teamSize");
                String location = rs.getString("location");
                String consultingLevel = rs.getString("consultingLevel");
                String leadProjects = rs.getString("leadProjects");
                String skillSet = rs.getString("skillSet");
                String reportsTo = rs.getString("reportsTo");
                String projectRole = rs.getString("projectRole");
                String buildingName = rs.getString("buildingName");
                String city = rs.getString("city");
                String pinCode = rs.getString("pinCode");
                String mobNo = rs.getString("mobNo");

                Address address = new Address(buildingName, city, pinCode, mobNo);
                Employee employee = new Employee(empId, empName, empCompanyName, empBloodGroup, address);

                System.out.println("------------------------------");
                System.out.println("Employee ID: " + empId);
                System.out.println("Employee Name: " + empName);
                System.out.println("Company Name: " + empCompanyName);
                System.out.println("Blood Group: " + empBloodGroup);
                System.out.println("GCM Level: " + gcmLevel);
                System.out.println("DASS ID: " + dassId);
                System.out.println("Team Size: " + teamSize);
                System.out.println("Location: " + location);
                System.out.println("Consulting Level: " + consultingLevel);
                System.out.println("Lead Projects: " + leadProjects);
                System.out.println("Skill Set: " + skillSet);
                System.out.println("Reports To: " + reportsTo);
                System.out.println("Project Role: " + projectRole);
                System.out.println("Building Name: " + buildingName);
                System.out.println("City: " + city);
                System.out.println("Pin Code: " + pinCode);
                System.out.println("Mobile Number: " + mobNo);
                System.out.println("------------------------------");

                hasResults = true;
            }
            if (!hasResults) {
                System.out.println("No Employees found of type: " + type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static Employee createEmployeeFromResultSet(ResultSet rs) throws SQLException {
        int empId = rs.getInt("empId");
        String empName = rs.getString("empName");
        String empCompanyName = rs.getString("empCompanyName");
        String empBloodGroup = rs.getString("empBloodGroup");

        Address address = getAddressFromResultSet(rs); // Assuming address data is also available

        return new Employee(empId, empName, empCompanyName, empBloodGroup, address);
    }
    // Example usage
    public void fetchEmployees() {
        String query = "SELECT * FROM Employee"; // Adjust query as needed
        try (ResultSet rs = (ResultSet) DatabaseConnection.getConnection(query)) {
            while (rs.next()) {
                Employee employee = createEmployeeFromResultSet(rs);
                // Process employee as needed
                System.out.println(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void updateEmployee() throws InvalidIDException, SQLException {
        System.out.print("\nEnter Employee ID to update: ");
        int empId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Check if the employee exists
        boolean exists = checkEmployeeExists(empId);
        if (!exists) {
            throw new InvalidIDException("Employee ID does not exist.");
        }
        System.out.println("\nSelect the field to update:");
        System.out.println("------------------------------");
        System.out.println("1. Employee Name");
        System.out.println("2. Employee Company Name");
        System.out.println("3. Employee Blood Group");
        System.out.println("4. Building Name");
        System.out.println("5. City Name");
        System.out.println("6. Pin Code");
        System.out.println("7. Mobile Number");

        int updateChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        updateEmployeeInDatabase(empId, updateChoice);
    }

    private static boolean checkEmployeeExists(int empId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM employee WHERE empId = ?";
        try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
            pstmt.setInt(1, empId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
    private static void updateEmployeeFields(Employee employee, int updateChoice) {
        switch (updateChoice) {
            case 1:
                System.out.print("Enter new Employee Name: ");
                employee.setEmpName(scanner.nextLine());
                break;
            case 2:
                System.out.print("Enter new Employee Company Name: ");
                employee.setEmpCompanyName(scanner.nextLine());
                break;
            case 3:
                System.out.print("Enter new Employee Blood Group: ");
                employee.setEmpBloodGroup(scanner.nextLine());
                break;
            case 4:
                if (employee.getAddress() != null) {
                    System.out.print("Enter new Building Name: ");
                    employee.getAddress().setBuildingName(scanner.nextLine());
                } else {
                    System.out.println("Address not found for this employee.");
                }
                break;
            case 5:
                if (employee.getAddress() != null) {
                    System.out.print("Enter new City Name: ");
                    employee.getAddress().setCity(scanner.nextLine());
                } else {
                    System.out.println("Address not found for this employee.");
                }
                break;
            case 6:
                if (employee.getAddress() != null) {
                    System.out.print("Enter new Pin Code: ");
                    employee.getAddress().setPinCode(scanner.nextLine());
                } else {
                    System.out.println("Address not found for this employee.");
                }
                break;
            case 7:
                if (employee.getAddress() != null) {
                    System.out.print("Enter new Mobile Number: ");
                    employee.getAddress().setMobNo(scanner.nextLine());
                } else {
                    System.out.println("Address not found for this employee.");
                }
                break;
            default:
                System.out.println("Invalid choice. Please select a valid option.");
                break;
        }
    }
    private static void updateEmployeeInDatabase(int empId, int updateChoice) throws SQLException {
        String sql = "";
        switch (updateChoice) {
            case 1:
                System.out.print("Enter new Employee Name: ");
                String newName = scanner.nextLine();
                sql = "UPDATE employee SET empName = ? WHERE empId = ?";
                try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
                    pstmt.setString(1, newName);
                    pstmt.setInt(2, empId);
                    pstmt.executeUpdate();
                }
                break;
            case 2:
                System.out.print("Enter new Employee Company Name: ");
                String newCompanyName = scanner.nextLine();
                sql = "UPDATE employee SET empCompanyName = ? WHERE empId = ?";
                try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
                    pstmt.setString(1, newCompanyName);
                    pstmt.setInt(2, empId);
                    pstmt.executeUpdate();
                }
                break;
            case 3:
                System.out.print("Enter new Employee Blood Group: ");
                String newBloodGroup = scanner.nextLine();
                sql = "UPDATE employee SET empBloodGroup = ? WHERE empId = ?";
                try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
                    pstmt.setString(1, newBloodGroup);
                    pstmt.setInt(2, empId);
                    pstmt.executeUpdate();
                }
                break;
            case 4:
                System.out.print("Enter new Building Name: ");
                String newBuildingName = scanner.nextLine();
                sql = "UPDATE address SET buildingName = ? WHERE empId = ?";
                try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
                    pstmt.setString(1, newBuildingName);
                    pstmt.setInt(2, empId);
                    pstmt.executeUpdate();
                }
                break;
            case 5:
                System.out.print("Enter new City Name: ");
                String newCity = scanner.nextLine();
                sql = "UPDATE address SET city = ? WHERE empId = ?";
                try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
                    pstmt.setString(1, newCity);
                    pstmt.setInt(2, empId);
                    pstmt.executeUpdate();
                }
                break;
            case 6:
                System.out.print("Enter new Pin Code: ");
                String newPinCode = scanner.nextLine();
                sql = "UPDATE address SET pinCode = ? WHERE empId = ?";
                try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
                    pstmt.setString(1, newPinCode);
                    pstmt.setInt(2, empId);
                    pstmt.executeUpdate();
                }
                break;
            case 7:
                System.out.print("Enter new Mobile Number: ");
                String newMobNo = scanner.nextLine();
                sql = "UPDATE address SET mobNo = ? WHERE empId = ?";
                try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
                    pstmt.setString(1, newMobNo);
                    pstmt.setInt(2, empId);
                    pstmt.executeUpdate();
                }
                break;
            default:
                System.out.println("Invalid choice. Please select a valid option.");
                break;
        }
    }
    private static Address getAddressFromResultSet(ResultSet rs) throws SQLException {
        // Extract the values from the ResultSet based on the available columns
        String buildingName = rs.getString("buildingName");
        String city = rs.getString("city");
        String pinCode = rs.getString("pinCode");
        String mobNo = rs.getString("mobNo");

        // Assuming Address class has a constructor matching the columns available
        return new Address(buildingName, city, pinCode, mobNo);
    }
    private static void updateSpecificFields(Employee employee, int updateChoice, Scanner scanner) {
        if (employee instanceof Manager) {
            Manager manager = (Manager) employee;
            switch (updateChoice) {
                case 8:
                    System.out.print("Enter new GCM Level: ");
                    manager.setGCMLevel(scanner.nextLine());
                    break;
                case 9:
                    System.out.print("Enter new DASS ID: ");
                    manager.setDassId(scanner.nextLine());
                    break;
                case 10:
                    System.out.print("Enter new Team Size: ");
                    manager.setTeamSize(scanner.nextLine());
                    break;
                case 11:
                    System.out.print("Enter new Location: ");
                    manager.setLocation(scanner.nextLine());
                    break;
                default:
                    System.out.println("Invalid Choice.");
            }
        } else if (employee instanceof ConsultantDelivery) {
            ConsultantDelivery consultantDelivery = (ConsultantDelivery) employee;
            switch (updateChoice) {
                case 8:
                    System.out.print("Enter new GCM Level: ");
                    consultantDelivery.setGCMLevel(scanner.nextLine());
                    break;
                case 9:
                    System.out.print("Enter new DASS ID: ");
                    consultantDelivery.setDassId(scanner.nextLine());
                    break;
                case 10:
                    System.out.print("Enter new Consulting Level: ");
                    consultantDelivery.setConsultingLevel(scanner.nextLine());
                    break;
                case 11:
                    System.out.print("Enter new Lead Projects (comma-separated): ");
                    consultantDelivery.setLeadProjects(Arrays.asList(scanner.nextLine().split(",")));
                    break;
                default:
                    System.out.println("Invalid Choice.");
            }
        } else if (employee instanceof AssociateConsultantDelivery) {
            AssociateConsultantDelivery associateConsultantDelivery = (AssociateConsultantDelivery) employee;
            switch (updateChoice) {
                case 8:
                    System.out.print("Enter new GCM Level: ");
                    associateConsultantDelivery.setGCMLevel(scanner.nextLine());
                    break;
                case 9:
                    System.out.print("Enter new DASS ID: ");
                    associateConsultantDelivery.setDassId(scanner.nextLine());
                    break;
                case 10:
                    System.out.print("Enter new Skill Set (comma-separated): ");
                    associateConsultantDelivery.setSkillSet(Arrays.asList(scanner.nextLine().split(",")));
                    break;
                case 11:
                    System.out.print("Enter new Reports To: ");
                    associateConsultantDelivery.setReportsTo(scanner.nextLine());
                    break;
                case 12:
                    System.out.print("Enter new Project Role: ");
                    associateConsultantDelivery.setProjectRole(scanner.nextLine());
                    break;
                default:
                    System.out.println("Invalid Choice.");
            }
        } else {
            System.out.println("Invalid Choice.");
        }
    }
    private static void deleteEmployee(String type) throws InvalidIDException {
        System.out.print("Enter Employee ID to delete: ");
        int empId = scanner.nextInt();
        scanner.nextLine();
        try {
            String deleteAddressQuery = "DELETE FROM address WHERE empId = ?";
            try (PreparedStatement preparedStatement = connect().prepareStatement(deleteAddressQuery)) {
                preparedStatement.setInt(1, empId);
                preparedStatement.executeUpdate();
            }

            String deleteEmployeeQuery = "DELETE FROM Employee WHERE empId = ?";
            try (PreparedStatement preparedStatement = connect().prepareStatement(deleteEmployeeQuery)) {
                preparedStatement.setInt(1, empId);
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Employee Deleted Successfully");
                } else {
                    System.out.println("Employee ID does not exist.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Exception Occurred: " + e.getMessage());
        }
    }
    private static void deleteEmployeeFromDatabase(int empId) throws SQLException {
        // SQL query to delete employee from the database
        String deleteEmployeeSql = "DELETE FROM employees WHERE empId = ?";

        try (PreparedStatement pstmt = connect().prepareStatement(deleteEmployeeSql)) {
            pstmt.setInt(1, empId);
            pstmt.executeUpdate();

            deleteAddressFromDatabase(empId);
        }
    }
    private static void deleteAddressFromDatabase(int empId) throws SQLException {
        String deleteAddressSql = "DELETE FROM address WHERE empId = ?";

        try (PreparedStatement pstmt = connect().prepareStatement(deleteAddressSql)) {
            pstmt.setInt(1, empId);
            pstmt.executeUpdate();
        }
    }
    private static boolean isMatchingType(Employee employee, String type) {
        return employee.getType().equalsIgnoreCase(type);
    }
}

