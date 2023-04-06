package vn.edu.hcmuaf.st.chuyendeweb.entity;

import vn.edu.hcmuaf.st.chuyendeweb.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table
public class Laptop extends BaseEntity {
    @Column
    private String productName;
    @Column
    private String brand;
    @Column
    private double price;
    @Column
    private String cpu;
    @Column
    private String ram;
    @Column
    private String storage;
    @Column
    private String display;
    @Column
    private String graphics;
    @Column
    private String color;
    @Column
    private String pin;
    @Column
    private String weight;
    @ManyToOne
    @JoinColumn(name = "manufacture_id")
    private Manufacturer manufacturer;
    @ManyToOne
    @JoinColumn(name = "faility_id")
    private Facility facility;


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getGraphics() {
        return graphics;
    }

    public void setGraphics(String graphics) {
        this.graphics = graphics;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }
}
