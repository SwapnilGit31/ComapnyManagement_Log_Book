package addressBook_console;

public class Address {
    private String buildingName;
    private String pinCode;
    private String city;
    private String mobNo;

    public Address(String building_Name, String city, String pinCode, String mobNo) {
        this.buildingName = building_Name;
        this.city = city;
        this.pinCode = pinCode;
        this.mobNo = mobNo;
    }

    public String getbuildingName() {
        return buildingName;
    }

    public void setbuildingName(String building_Name) {
        this.buildingName = building_Name;
    }

    public String getpinCode() {
        return pinCode;
    }

    public void setpinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getmobNo() {
        return mobNo;
    }

    public void setmobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    @Override
    public String toString() {
        return "Address{" +
                "building_Name='" + buildingName + '\'' +
                ", city='" + city + '\'' +
                ", pinCode='" + pinCode + '\''+
                ", mobNo='" + mobNo + '\'' +
                '}';
    }
}