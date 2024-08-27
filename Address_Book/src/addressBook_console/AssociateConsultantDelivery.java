package addressBook_console;

import java.util.List;

public class AssociateConsultantDelivery extends Employee{
    private String GCMLevel;
    private String dassId;
    private List<String> skillSet;
    private String reportsTo;
    private String projectRole;

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

    public List<String> getSkillSet() {
        return skillSet;
    }

    public void setSkillSet(List<String> skillSet) {
        this.skillSet = skillSet;
    }

    public String getReportsTo() {
        return reportsTo;
    }

    public void setReportsTo(String reportsTo) {
        this.reportsTo = reportsTo;
    }

    public String getProjectRole() {
        return projectRole;
    }

    public void setProjectRole(String projectRole) {
        this.projectRole = projectRole;
    }

    public AssociateConsultantDelivery(int empId, String empName, String empCompanyName, String empBloodGroup, Address address, String GCMLevel, String dassId, List<String> skillSet, String reportsTo, String projectRole) {
        super(empId, empName, empCompanyName, empBloodGroup);
        this.GCMLevel = GCMLevel;
        this.dassId = dassId;
        this.skillSet = skillSet;
        this.reportsTo = reportsTo;
        this.projectRole = projectRole;
    }

    @Override
    public String toString() {
        return "AssociateConsultantDelivery{" +
                "empID='" +getEmpId()+'\'' +
                "Name='" +getEmpName()+'\'' +
                "Comapny Name='" +getEmpCompanyName()+'\'' +
                "Blood Group='" +getEmpBloodGroup()+'\'' +
                "Address='" +getAddress()+'\'' +
                "GCMLevel='" + GCMLevel + '\'' +
                ", dassId='" + dassId + '\'' +
                ", skillSet=" + skillSet +
                ", reportsTo='" + reportsTo + '\'' +
                ", projectRole='" + projectRole + '\'' +
                '}';
    }
}
