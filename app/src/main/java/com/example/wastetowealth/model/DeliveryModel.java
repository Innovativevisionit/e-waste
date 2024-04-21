package com.example.wastetowealth.model;

public class DeliveryModel {
    private String name;
    private String age;
    private String location;
    private String contactNo;
    private String status;

    public DeliveryModel() {
    }

    public DeliveryModel(String name, String age, String location, String contactNo, String status) {
        this.name = name;
        this.age = age;
        this.location = location;
        this.contactNo = contactNo;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DeliveryModel{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", location='" + location + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
