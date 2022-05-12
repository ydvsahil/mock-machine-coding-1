package domain;

import java.util.List;

/**
 * Class for storing user details as consumer or driver.
 */
public class User {
    private String name;
    private Gender gender;
    private Integer age;
    private List<Vehicle> vehicleList;

    public User(String name, Gender gender, Integer age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public Integer getAge() {
        return age;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }
}
