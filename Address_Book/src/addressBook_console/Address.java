
package addressBook_console;
public class Address {
    private String buildingName;
    private String city;
    private String pinCode;
    private String mobNo;

    // Constructor
    public Address(String buildingName, String city, String pinCode, String mobNo) {
        this.buildingName = buildingName;
        this.city = city;
        this.pinCode = pinCode;
        this.mobNo = mobNo;
    }

    // Getters and Setters
    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    @Override
    public String toString() {
        return "Address{" +
                "buildingName='" + buildingName + '\'' +
                ", city='" + city + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", mobNo='" + mobNo + '\'' +
                '}';
    }
}
