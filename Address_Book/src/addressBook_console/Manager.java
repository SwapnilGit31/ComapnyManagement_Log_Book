package addressBook_console;

public class Manager extends Employee{
    private String GCMLevel;
    private String dassId;
    private String teamSize;
    private String location;

    public String getGCMLevel() {
        return GCMLevel;
    }

    public void setGCMLevel(String GCMLevel) {
        this.GCMLevel = GCMLevel;
    }

    public String getDassId() {
        return dassId;
    }

    public void setDassId(String dassId) {
        this.dassId = dassId;
    }

    public String getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(String teamSize) {
        this.teamSize = teamSize;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }



    public Manager(int empId, String empName, String empCompanyName, String empBloodGroup, Address address, String GCMLevel, String dassId, String teamSize, String location) {
        super(empId, empName, empCompanyName, empBloodGroup);
        this.GCMLevel = GCMLevel;
        this.dassId = dassId;
        this.teamSize = teamSize;
        this.location = location;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "empID='" +getEmpId()+'\'' +
                "Name='" +getEmpName()+'\'' +
                "Comapny Name='" +getEmpCompanyName()+'\'' +
                "Blood Group='" +getEmpBloodGroup()+'\'' +
                "Address='" +getAddress()+'\'' +
                "GCMLevel='" + GCMLevel + '\'' +
                ", dassId='" + dassId + '\'' +
                ", teamSize='" + teamSize + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}