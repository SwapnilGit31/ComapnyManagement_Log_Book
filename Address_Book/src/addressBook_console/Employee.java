package addressBook_console;

public class Employee {
    private int empId;
    private String empName;
    private String empCompanyName;
    private String empBloodGroup;
    private Address address;

    public Employee(int empId, String empName, String empCompanyName, String empBloodGroup) {
    }

    public int getempId() {
        return empId;
    }

    public void setempId(int empId) {
        this.empId = empId;
    }

    public String getempName() {
        return empName;
    }

    public void setempName(String empName) {
        this.empName = empName;
    }

    public String getempCompanyName() {
        return empCompanyName;
    }

    public void setempCompanyName(String empCompanyName) {
        this.empCompanyName = empCompanyName;
    }

    public String getempBloodGroup() {
        return empBloodGroup;
    }

    public void setempBloodGroup(String empBloodGroup) {
        this.empBloodGroup = empBloodGroup;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


    public Employee(int empId, String empName, String empCompanyName, String empBloodGroup, Address address) {
        this.empId = empId;
        this.empName = empName;
        this.empCompanyName = empCompanyName;
        this.empBloodGroup = empBloodGroup;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId='" + empId + '\'' +
                ", empName='" + empName + '\'' +
                ", empCompanyName='" + empCompanyName + '\'' +
                ", empBloodGroup='" + empBloodGroup + '\'' +
                ", address=" + address +
                '}';
    }



// Similarly update toString() for ConsultantDelivery and AssociateConsultantDelivery


}