package addressBook_console;


import java.util.List;

public class ConsultantDelivery extends Employee{
    private String GCMLevel;
    private String dassId;
    private String consultingLevel;
    private List<String> leadProjects;

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

    public String getConsultingLevel() {
        return consultingLevel;
    }

    public void setConsultingLevel(String consultingLevel) {
        this.consultingLevel = consultingLevel;
    }

    public List<String> getLeadProjects() {
        return leadProjects;
    }

    public void setLeadProjects(List<String> leadProjects) {
        this.leadProjects = leadProjects;
    }

    public ConsultantDelivery(int empId, String empName, String empCompanyName, String empBloodGroup, Address address, String GCMLevel, String dassId, String consultingLevel, List<String> leadProjects) {
        super(empId, empName, empCompanyName, empBloodGroup);
        this.GCMLevel = GCMLevel;
        this.dassId = dassId;
        this.consultingLevel = consultingLevel;
        this.leadProjects = leadProjects;
    }

    @Override
    public String toString() {
        return "ConsultantDelivery{" +
                "empID='" +getEmpId()+'\'' +
                "Name='" +getEmpName()+'\'' +
                "Comapny Name='" +getEmpCompanyName()+'\'' +
                "Blood Group='" +getEmpBloodGroup()+'\'' +
                "Address='" +getAddress()+'\'' +
                "GCMLevel='" + GCMLevel + '\'' +
                ", dassId='" + dassId + '\'' +
                ", consultingLevel='" + consultingLevel + '\'' +
                ", leadProjects=" + leadProjects +
                '}';
    }
}
