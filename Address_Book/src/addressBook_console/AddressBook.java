package addressBook_console;

import addressBook_console.Employee;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


class InvalidIDException extends Exception{
    public InvalidIDException(String msg){
        super(msg);
    }

}

public class AddressBook {
    public static Map<Integer, Employee> addressBook = new HashMap<Integer, Employee>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

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


}

//int choice;
//        do{
//
//            System.out.println("Address Book Menu : ");
//            System.out.println("1. Add Address");
//            System.out.println("2. View All Addresses ");
//            System.out.println("3. Update Address");
//            System.out.println("4. Delete Address");
//            System.out.println("5. Exit");
//            System.out.println("Enter your choice : ");
//            choice=scanner.nextInt();
//
//            switch(choice) {
//                case 1:
//                    addaddress();
//                    break;
//                case 2:
//                    viewAlladdresses();
//                    break;
//                case 3:
//                    updateaddress();
//                    break;
//                case 4:
//                    deleteaddress();
//                    break;
//                case 5:
//                    System.out.println("Exit Program");
//                    break;
//                default:
//                    System.out.println("Invalid Choice. Try Again!!");
//            }
//
//        } while (choice !=5);
//    }
//
//
//
//    private static void addaddress() {
//        System.out.println("Enter ID: ");
//        int id=scanner.nextInt();
//        scanner.nextLine();
//
//        System.out.println("Enter building_Name: ");
//        String building_Name=scanner.nextLine();
//
//        System.out.println("Enter City: ");
//        String city=scanner.nextLine();
//
//        System.out.println("Enter pinCode: ");
//        String pinCode=scanner.nextLine();
//
//        System.out.println("Enter Mobile Number: ");
//        String mobile=scanner.nextLine();
//
//
//        Address Address=new Address(building_Name,city,pinCode,mobile);
//        addressBook.put(id,Address);
//
//        System.out.println("Address Added Successfully");
//    }
//
//
//    private static void viewAlladdresses() {
//        if(addressBook.isEmpty())
//        {
//            System.out.println("No Address available");
//            return;
//        }
//
//        for(Map.Entry<Integer, Address> entry : addressBook.entrySet())
//        {
//            System.out.println("ID: "+entry.getKey());
//            System.out.println(entry.getValue());
//            System.out.println();
//        }
//    }
//        private static void updateaddress(){
//            System.out.println("Enter ID to update: ");
//            int id = scanner.nextInt();
//            scanner.nextLine();
//
//            if(addressBook.containsKey(id)){
//                Address fieldToUpdate=addressBook.get(id);
//                int updateField ;
//                System.out.println("Enter which field you want to update: ");
//                System.out.println("1. building_Name");
//                System.out.println("2. City");
//                System.out.println("3. pinCode");
//                System.out.println("4. Mobile Number");
//                updateField=scanner.nextInt();
//                scanner.nextLine();
//                switch (updateField){
//                    case 1:
//                        System.out.println("Enter New building_Name: ");
//                        String building_Name=scanner.nextLine();
//                        fieldToUpdate.setbuilding_Name(building_Name);
//                        break;
//                    case 2:
//                        System.out.println("Enter New City: ");
//                        String city=scanner.nextLine();
//                        fieldToUpdate.setCity(city);
//
//                        break;
//                    case 3:
//                        System.out.println("Enter New pinCode: ");
//                        String pinCode=scanner.nextLine();
//                        fieldToUpdate.setpinCode(pinCode);
//
//                        break;
//                    case 4:
//                        System.out.println("Enter New Mobile Number: ");
//                        String mobile=scanner.nextLine();
//                        fieldToUpdate.setmobNo(mobile);
//
//                        break;
//                    default:
//                        System.out.println("Invalid Field. Try Again!!");
//
//                }
//                addressBook.put(id,fieldToUpdate);
//                System.out.println("Address Updated Successfully");
//
//
//     To update Complete Record :
////                System.out.println("Enter New building_Name: ");
////                String building_Name=scanner.nextLine();
////                System.out.println("Enter New pinCode: ");
////                String pinCode=scanner.nextLine();
////                System.out.println("Enter New City: ");
////                String city=scanner.nextLine();
////               System.out.println("Enter New Mobile Number: ");
////               String mobile=scanner.nextLine();
////
////               Address updateAddress=new Address(building_Name,pinCode,city,mobile);
////               addressBook.put(id,updateAddress);
////               System.out.println("Address Updated Successfully");
//
//            }
//            else {
//                System.out.println("Address Not Found ");
//            }
//
//
//        }
//
//    private static void deleteaddress() {
//        System.out.println("Enter ID to delete: ");
//        int id = scanner.nextInt();
//        scanner.nextLine();
//        if(addressBook.containsKey(id)){
//            addressBook.remove(id);
//            System.out.println("Address Deleted Successfully");
//        }
//        else {
//            System.out.println("Address Not Found");
//        }
//
//    }