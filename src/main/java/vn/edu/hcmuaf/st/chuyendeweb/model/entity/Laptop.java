package vn.edu.hcmuaf.st.chuyendeweb.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import vn.edu.hcmuaf.st.chuyendeweb.model.CPU;
import vn.edu.hcmuaf.st.chuyendeweb.model.LaptopState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @NotNull
    private String brand;
    @Column
    private double price;
    @Column
    private String cpu;
    @Column
    @NotNull
    private String chipCpu;
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
    @Column
    private String type;
    @Column
    private LaptopState laptopState;
    @Column
    private int quantity;
    @Column
    private String linkAvatar;
    @JsonIgnore
    @ManyToOne
    @NotNull
    @JoinColumn(name = "faility_id")
    private Facility facility;
    @JsonIgnore
    @OneToMany(mappedBy = "laptop")
    private List<ImageLaptop> images = new ArrayList<>();
//    @JsonIgnore
//    @ManyToMany(mappedBy = "laptops")
//    private List<Cart> carts = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "laptop", cascade = CascadeType.ALL)
    private List<CartLaptop> cartLaptops;
}
