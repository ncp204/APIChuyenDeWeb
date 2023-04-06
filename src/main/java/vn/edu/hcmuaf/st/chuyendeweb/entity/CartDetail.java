package vn.edu.hcmuaf.st.chuyendeweb.entity;

import vn.edu.hcmuaf.st.chuyendeweb.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table
public class CartDetail extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
    @Column
    private int total;
}
