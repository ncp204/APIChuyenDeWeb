package vn.edu.hcmuaf.st.chuyendeweb.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Cart extends BaseEntity {
    @Column
    private Date exportDate;
    @Column
    private double totalAmount;
    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToMany
    @JoinTable(
            name = "cart_laptop",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "laptop_id")
    )
    private List<Laptop> laptops = new ArrayList<>();
}
