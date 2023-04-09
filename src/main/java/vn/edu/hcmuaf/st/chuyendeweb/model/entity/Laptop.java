package vn.edu.hcmuaf.st.chuyendeweb.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
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
    private String battery;
    @Column
    private String weight;
    @ManyToOne
    @JoinColumn(name = "manufacture_id")
    private Manufacturer manufacturer;
    @ManyToOne
    @JoinColumn(name = "faility_id")
    private Facility facility;
    @OneToMany(mappedBy = "laptop")
    private List<ImageLaptop> images = new ArrayList<>();
    @ManyToMany(mappedBy = "laptops")
    private List<Cart> carts = new ArrayList<>();
}
