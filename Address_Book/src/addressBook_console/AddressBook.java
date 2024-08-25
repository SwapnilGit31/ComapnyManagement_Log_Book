package addressBook_console;

import java.util.*;
import java.util.stream.Collectors;



class InvalidIDException extends Exception{
    public InvalidIDException(String msg){
        super(msg);
    }

}

public  class AddressBook {
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


    private static void manageEmployees(String type) {
        int operation;
        do {
            System.out.println("Choose CRUD Operation for "+type+ "Employee: ");
            System.out.println("1. Add Employee");
            System.out.println("2. Display All Employee");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Go back to the main menu");
            operation = scanner.nextInt();

            switch(operation){
                case 1:
                    addEmployee(type);
                    break;
                case 2:
                    displayAllEmployees(type);
                    break;
                case 3:
                    try {
                        updateEmployee(type);
                    }catch (InvalidIDException e){
                        System.out.println("Exception Occured:  "+e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        deleteEmployee(type);
                    }
                    catch (InvalidIDException e){
                        System.out.println("Exception Occured:  "+e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Return to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice");
            }

        }while (operation!=5);

    }


    private static void addEmployee(String type) {
        System.out.println("Enter Employee Details :");
        System.out.println("Enter Employee ID");
        int empId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter Employee Name");
        String empName = scanner.nextLine();

        System.out.println("Enter Employee Company_Name");
        String empCompanyName= scanner.nextLine();

        System.out.println("Enter Employee Blood_Group");
        String empBloodGroup = scanner.nextLine();

        System.out.println("Address Details");


        System.out.println("Enter Building Name :");
        String building_Name = scanner.nextLine();

        System.out.println("Enter City Name :");
        String city = scanner.nextLine();

        System.out.println("Enter pinCode :");
        String pinCode = scanner.nextLine();


        System.out.println("Enter Employee Mobile No : :");
        String mobNo = scanner.nextLine();

        Address address=new Address(building_Name,city,pinCode,mobNo);

        Employee employee = createEmployee(type, empId, empName, empCompanyName, empBloodGroup, address);

        addressBook.put(empId,employee);


        if(employee != null){
            addressBook.put(empId,employee);
            System.out.println(type+ "Employee Added Successfully");
        }
        else {
            System.out.println(type+ " Invalid Employee Type");
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

        return new Manager(empId,empName,empCompanyName,empBloodGroup,address,gcmLevel,dassId,teamSize,location);

    }


    private static Employee createConsultantDelivery(int empId, String empName, String empCompanyName, String empBloodGroup, Address address) {
        System.out.println("Enter GCM level: ");
        String gcmLevel = scanner.nextLine();

        System.out.println("Enter Dass Id : ");
        String dassId = scanner.nextLine();

        System.out.println("Enter consulting level: ");
        String consultingLevel = scanner.nextLine();

        List<String> leadProjects = Arrays.asList(scanner.nextLine().split(","));
        return new ConsultantDelivery(empId,empCompanyName,empName,empBloodGroup,address,gcmLevel,dassId,consultingLevel,leadProjects);


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

        return new AssociateConsultantDelivery(empId,empName,empCompanyName,empBloodGroup,address,gcmLevel,dassId,skillSet,reportsTo,projectRole);

    }



    private static void displayAllEmployees(String type) {
        List<Employee> employees=addressBook.values().stream().filter(emp->isMatchingType(emp,type)).collect(Collectors.toList());
        if (employees.isEmpty()) {
            System.out.println("No Employees found of type: " + type);
        } else {
            employees.forEach(emp -> {
                System.out.println(emp);
                System.out.println("----------");
            });
        }    }


    private static void updateEmployee(String type)throws InvalidIDException{
        System.out.print("\nEnter Employee ID to update: ");
        int empId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Employee employee = Optional.ofNullable(addressBook.get(empId))
                .filter(emp -> isMatchingType(emp, type))
                .orElseThrow(() -> new InvalidIDException("Employee ID does not exist or does not match the selected type."));

        // Update base fields
        System.out.println("\nSelect the field to update:");
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













//For Employee Details
        /*int choice;

        do{
        System.out.println("Employee Details : ");
        System.out.println("1. Add Employee");
        System.out.println("2. Display Employee");
        System.out.println("3. Update Employee");
        System.out.println("4. Delete Employee");
        System.out.println("5. Exit");
        System.out.println("Enter your choice: ");
        choice = scanner.nextInt();

        switch (choice){
            case 1:
                addemployee();
                break;
            case 2:
                viewAllEmployee();
                break;
            case 3:
                try{
                updateEmployee();
                }
                catch (InvalidIDException e) {
                    System.out.println("Exception Occured :"+e.getMessage());
                }
                break;
            case 4:
                try {
                    deleteEmployee();
                }
                catch (InvalidIDException e) {
                    System.out.println("Exception Occured :"+e.getMessage());
                }
                break;
            case 5:
                System.out.println("Exit Program");
                break;
            default:
                System.out.println("Invalid Choice");
        }

        }while (choice != 5);



}
    private static void addemployee() {
        System.out.println("Enter Employee ID");
        int empId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter Employee Name");
        String empName = scanner.nextLine();

        System.out.println("Enter Employee Company_Name");
        String empCompanyName= scanner.nextLine();

        System.out.println("Enter Employee Blood_Group");
        String empBloodGroup = scanner.nextLine();

        System.out.println("Address Details");


        System.out.println("Enter Building Name :");
        String building_Name = scanner.nextLine();

        System.out.println("Enter City Name :");
        String city = scanner.nextLine();

        System.out.println("Enter pinCode :");
        String pinCode = scanner.nextLine();


        System.out.println("Enter Employee Mobile No : :");
        String mobNo = scanner.nextLine();

        Address address=new Address(building_Name,city,pinCode,mobNo);

        Employee employee=new Employee(empId, empName, empCompanyName, empBloodGroup,address);
        addressBook.put(empId,employee);


        System.out.println("Employee Added Successfully");
    }

    private static void viewAllEmployee() {
        if(addressBook.isEmpty())
        {
            System.out.println("No Address available");
            return;
        }


        //Map.entrySet() method returns a collection-view(Set<Map.Entry<K, V>>) of the mappings contained in this map.
        // So we can iterate over key-value pair using getKey() and getValue() methods of Map.Entry<K, V>.
        // This method is most common and should be used if you need both map keys and values in the loop.

        for(Map.Entry<Integer, Employee> entry : addressBook.entrySet())
        {
            System.out.println("ID: "+entry.getKey());
            System.out.println(entry.getValue());
            System.out.println();
        }

    }

    private static void updateEmployee() throws InvalidIDException {

        System.out.println("Enter Employee ID which you want to update");
        int empId = scanner.nextInt();
        scanner.nextLine();

        if (addressBook.containsKey(empId)) {
            Employee fieldToUpdate = addressBook.get(empId);
            int updatefield;

            System.out.println("Enter field you want to update");

            System.out.println("1. Employee Name");
            System.out.println("2. Employee Company_Name");
            System.out.println("3. Employee Blood_Group");
            System.out.println("4. building_Name");
            System.out.println("5. City");
            System.out.println("6. pinCode");
            System.out.println("7. Mobile Number");

            updatefield = scanner.nextInt();
            scanner.nextLine();
            switch (updatefield){
                case 1:
                    System.out.println("Enter Employee Name to update");
                    String empName = scanner.nextLine();
                    fieldToUpdate.setempName(empName);
                    break;
                case 2:
                    System.out.println("Enter Employee Company_Name to update");
                    String empCompanyName = scanner.nextLine();
                    fieldToUpdate.setempCompanyName(empCompanyName);
                    break;
                case 3:
                    System.out.println("Enter Employee Blood_Group to update");
                    String empBloodGroup = scanner.nextLine();
                    fieldToUpdate.setempBloodGroup(empBloodGroup);
                    break;
                case 4:
                    System.out.println("Enter Building Name to update");
                    String building_Name = scanner.nextLine();
                    fieldToUpdate.getAddress().setbuildingName(building_Name);
                    break;
                case 5:
                    System.out.println("Enter City to update");
                    String city = scanner.nextLine();
                    fieldToUpdate.getAddress().setCity(city);
                    break;
                case 6:
                    System.out.println("Enter pinCode to update");
                    String pinCode = scanner.nextLine();
                    fieldToUpdate.getAddress().setpinCode(pinCode);
                    break;
                case 7:
                    System.out.println("Enter Mobile Number to update");
                    String mobNo = scanner.nextLine();
                    fieldToUpdate.getAddress().setmobNo(mobNo);
                    break;
                default:
                    System.out.println("Invalid Field chosen");

            }
            addressBook.put(empId,fieldToUpdate);
            System.out.println("Employee Updated Successfully");
        }
        else{
            throw new InvalidIDException("Employee ID does not exist");
        }

    }

    private static void deleteEmployee() throws InvalidIDException {

        System.out.println("Enter Employee ID which you want to delete");
        int empId = scanner.nextInt();
        scanner.nextLine();
        if (addressBook.containsKey(empId)) {
            addressBook.remove(empId);
            System.out.println("Employee Deleted Successfully");
        }
        else throw new InvalidIDException("Employee ID does not exist");{

        }
    }


}*/





//For Address Details
/*int choice;
        do{

            System.out.println("Address Book Menu : ");
            System.out.println("1. Add Address");
            System.out.println("2. View All Addresses ");
            System.out.println("3. Update Address");
            System.out.println("4. Delete Address");
            System.out.println("5. Exit");
            System.out.println("Enter your choice : ");
            choice=scanner.nextInt();

            switch(choice) {
                case 1:
                    addaddress();
                    break;
                case 2:
                    viewAlladdresses();
                    break;
                case 3:
                    updateaddress();
                    break;
                case 4:
                    deleteaddress();
                    break;
                case 5:
                    System.out.println("Exit Program");
                    break;
                default:
                    System.out.println("Invalid Choice. Try Again!!");
            }

        } while (choice !=5);
    }



    private static void addaddress() {
        System.out.println("Enter ID: ");
        int id=scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter building_Name: ");
        String building_Name=scanner.nextLine();

        System.out.println("Enter City: ");
        String city=scanner.nextLine();

        System.out.println("Enter pinCode: ");
        String pinCode=scanner.nextLine();

        System.out.println("Enter Mobile Number: ");
        String mobile=scanner.nextLine();


        Address Address=new Address(building_Name,city,pinCode,mobile);
        addressBook.put(id,Address);

        System.out.println("Address Added Successfully");
    }


    private static void viewAlladdresses() {
        if(addressBook.isEmpty())
        {
            System.out.println("No Address available");
            return;
        }

        for(Map.Entry<Integer, Address> entry : addressBook.entrySet())
        {
            System.out.println("ID: "+entry.getKey());
            System.out.println(entry.getValue());
            System.out.println();
        }
    }
        private static void updateaddress(){
            System.out.println("Enter ID to update: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            if(addressBook.containsKey(id)){
                Address fieldToUpdate=addressBook.get(id);
                int updateField ;
                System.out.println("Enter which field you want to update: ");
                System.out.println("1. building_Name");
                System.out.println("2. City");
                System.out.println("3. pinCode");
                System.out.println("4. Mobile Number");
                updateField=scanner.nextInt();
                scanner.nextLine();
                switch (updateField){
                    case 1:
                        System.out.println("Enter New building_Name: ");
                        String building_Name=scanner.nextLine();
                        fieldToUpdate.setbuilding_Name(building_Name);
                        break;
                    case 2:
                        System.out.println("Enter New City: ");
                        String city=scanner.nextLine();
                        fieldToUpdate.setCity(city);

                        break;
                    case 3:
                        System.out.println("Enter New pinCode: ");
                        String pinCode=scanner.nextLine();
                        fieldToUpdate.setpinCode(pinCode);

                        break;
                    case 4:
                        System.out.println("Enter New Mobile Number: ");
                        String mobile=scanner.nextLine();
                        fieldToUpdate.setmobNo(mobile);

                        break;
                    default:
                        System.out.println("Invalid Field. Try Again!!");

                }
                addressBook.put(id,fieldToUpdate);
                System.out.println("Address Updated Successfully");


 /*    To update Complete Record :
//                System.out.println("Enter New building_Name: ");
//                String building_Name=scanner.nextLine();
//                System.out.println("Enter New pinCode: ");
//                String pinCode=scanner.nextLine();
//                System.out.println("Enter New City: ");
//                String city=scanner.nextLine();
//               System.out.println("Enter New Mobile Number: ");
//               String mobile=scanner.nextLine();
//
//               Address updateAddress=new Address(building_Name,pinCode,city,mobile);
//               addressBook.put(id,updateAddress);
//               System.out.println("Address Updated Successfully");

            }
            else {
                System.out.println("Address Not Found ");
            }


        }

    private static void deleteaddress() {
        System.out.println("Enter ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        if(addressBook.containsKey(id)){
            addressBook.remove(id);
            System.out.println("Address Deleted Successfully");
        }
        else {
            System.out.println("Address Not Found");
        }

    }*/