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

    public static Map<Integer, Employee> addressBook = new HashMap<Integer, Employee>();
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
        String query = "SELECT * FROM Employee";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

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


                Employee employee = new Employee(empId, empName, empCompanyName, empBloodGroup
                );

                addressBook.put(empId, employee);
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
                        updateEmployee(type);
                    } catch (InvalidIDException e) {
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

        addressBook.put(empId, employee);


        if (employee != null) {
            addressBook.put(empId, employee);
            System.out.println(type + "Employee Added Successfully");
        } else {
            System.out.println(type + " Invalid Employee Type");
        }

        String employeeSQL = "INSERT INTO Employee (empId, empName, empCompanyName, empBloodGroup, gcmLevel, dassId, teamSize, location, consultingLevel, leadProjects, skillSet, reportsTo, projectRole) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


        try (Connection conn = connect();
             PreparedStatement employeeStmt = conn.prepareStatement(employeeSQL)) {

            // Insert into Employee table
            employeeStmt.setInt(1, employee.getempId());
            employeeStmt.setString(2, employee.getempName());
            employeeStmt.setString(3, employee.getempCompanyName());
            employeeStmt.setString(4, employee.getempBloodGroup());

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
            }

            employeeStmt.executeUpdate();

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
        String query = "SELECT * FROM Employee WHERE 1=1";
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

                //  Address address = new Address(/* Set address details from the database if applicable */);

                Employee employee = createEmployeeForm(empId, empName, empCompanyName, empBloodGroup,
                        gcmLevel, dassId, teamSize, location, consultingLevel, leadProjects, skillSet, reportsTo, projectRole);

                System.out.println(employee);
                System.out.println("----------");
                hasResults = true;
            }

            if (!hasResults) {
                System.out.println("No Employees found of type: " + type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private static Employee createEmployeeForm(
            int empId, String empName, String empCompanyName, String empBloodGroup,
            String gcmLevel, String dassId, String teamSize, String location,
            String consultingLevel, String leadProjects, String skillSet,
            String reportsTo, String projectRole) {

        if (gcmLevel != null && dassId != null) {
            if (teamSize != null && location != null) {
                return new Manager(empId, empName, empCompanyName, empBloodGroup,
                        new Address("", "", "", ""), gcmLevel, dassId, teamSize, location);
            } else if (consultingLevel != null && leadProjects != null) {
                List<String> leadProjectsList = Arrays.asList(leadProjects.split(","));
                return new ConsultantDelivery(empId, empCompanyName, empName, empBloodGroup,
                        new Address("", "", "", ""), gcmLevel, dassId, consultingLevel, leadProjectsList);
            } else if (skillSet != null && reportsTo != null && projectRole != null) {
                List<String> skillSetList = Arrays.asList(skillSet.split(","));
                return new AssociateConsultantDelivery(empId, empName, empCompanyName, empBloodGroup,
                        new Address("", "", "", ""), gcmLevel, dassId, skillSetList, reportsTo, projectRole);
            }
        }
        return new Employee(empId, empName, empCompanyName, empBloodGroup, new Address("", "", "", ""));
    }





    private static void updateEmployee(String type)throws InvalidIDException{
        System.out.print("\nEnter Employee ID to update: ");
        int empId = scanner.nextInt();
        scanner.nextLine(); // Consume newline



        Employee employee = Optional.ofNullable(addressBook.get(empId))
                .filter(emp -> isMatchingType(emp, type))
                .orElseThrow(() -> new InvalidIDException("Employee ID does not exist or does not match the selected type."));

        // Update base fields
        System.out.println("\nSelect the field to update:");
        System.out.println("------------------------------");
        System.out.println("1. Employee Name");
        System.out.println("2. Employee Company Name");
        System.out.println("3. Employee Blood Group");
        System.out.println("4. Building Name");
        System.out.println("5. City Name");
        System.out.println("6. Pin Code");
        System.out.println("7. Mobile Number");

        if (employee instanceof Manager) {
            System.out.println("8. GCM Level");
            System.out.println("9. DASS ID");
            System.out.println("10. Team Size");
            System.out.println("11. Location");
        } else if (employee instanceof ConsultantDelivery) {
            System.out.println("8. GCM Level");
            System.out.println("9. DASS ID");
            System.out.println("10. Consulting Level");
            System.out.println("11. Lead Projects (comma-separated)");
        } else if (employee instanceof AssociateConsultantDelivery) {
            System.out.println("8. GCM Level");
            System.out.println("9. DASS ID");
            System.out.println("10. Skill Set (comma-separated)");
            System.out.println("11. Reports To");
            System.out.println("12. Project Role");
        }

        System.out.print("Enter choice: ");
        int updateChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (updateChoice) {
            case 1:
                System.out.print("Enter new Employee Name: ");
                employee.setempName(scanner.nextLine());
                break;
            case 2:
                System.out.print("Enter new Employee Company Name: ");
                employee.setempCompanyName(scanner.nextLine());
                break;
            case 3:
                System.out.print("Enter new Employee Blood Group: ");
                employee.setempBloodGroup(scanner.nextLine());
                break;
            case 4:
                System.out.print("Enter new Building Name: ");
                employee.getAddress().setbuildingName(scanner.nextLine());
                break;
            case 5:
                System.out.print("Enter new City Name: ");
                employee.getAddress().setCity(scanner.nextLine());
                break;
            case 6:
                System.out.print("Enter new Pin Code: ");
                employee.getAddress().setpinCode(scanner.nextLine());
                break;
            case 7:
                System.out.print("Enter new Mobile Number: ");
                employee.getAddress().setmobNo(scanner.nextLine());
                break;
            default:
                updateSpecificFields(employee, updateChoice, scanner);
                break;
        }



        System.out.println("Employee details updated successfully!");


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

    private static void deleteEmployee(String type)throws InvalidIDException{
        System.out.print("Enter Employee ID to delete: ");
        int empId = scanner.nextInt();
        scanner.nextLine();

        Employee employee=Optional.ofNullable(addressBook.get(empId)).filter(emp->isMatchingType(emp,type)).orElseThrow(() -> new InvalidIDException("Employee ID does not exist for the selected type."));
        addressBook.remove(empId);
        System.out.println(type+ "Employee Deleted Successfully");

    }

    private static boolean isMatchingType(Employee emp, String type) {
        switch (type) {
            case "General":
                return emp.getClass() == Employee.class;
            case "Manager":
                return emp instanceof Manager;
            case "Consultant Delivery":
                return emp instanceof ConsultantDelivery;
            case "Associate Consultant Delivery":
                return emp instanceof AssociateConsultantDelivery;
            default:
                return false;
        }
    }
}
