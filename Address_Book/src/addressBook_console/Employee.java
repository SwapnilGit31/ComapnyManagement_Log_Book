package addressBook_console;

public class Employee {
    private int empId;
    private String empName;
    private String empCompanyName;
    private String empBloodGroup;
    private Address address;
    private String type;

    // Constructor with Address parameter
    public Employee(int empId, String empName, String empCompanyName, String empBloodGroup, Address address) {
        this.empId = empId;
        this.empName = empName;
        this.empCompanyName = empCompanyName;
        this.empBloodGroup = empBloodGroup;
        this.address = address;
        this.type = type;
    }

    // Default constructor
    public Employee(int empId, String empName, String empCompanyName, String empBloodGroup) {
        this(empId, empName, empCompanyName, empBloodGroup, null); // Use default address as null
    }

    // Getters and Setters
    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpCompanyName() {
        return empCompanyName;
    }

    public void setEmpCompanyName(String empCompanyName) {
        this.empCompanyName = empCompanyName;
    }

    public String getEmpBloodGroup() {
        return empBloodGroup;
    }

    public void setEmpBloodGroup(String empBloodGroup) {
        this.empBloodGroup = empBloodGroup;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", empName='" + empName + '\'' +
                ", empCompanyName='" + empCompanyName + '\'' +
                ", empBloodGroup='" + empBloodGroup + '\'' +
                ", address=" + address +
                '}';
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
