package vn.edu.hcmuaf.st.chuyendeweb.entity;

import vn.edu.hcmuaf.st.chuyendeweb.base.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class Cart extends BaseEntity {
    @OneToOne(cascade = CascadeType.ALL)
    private Account account;
    @Column
    private Date exportDate;
    @Column
    private double totalAmount;
    @OneToMany(mappedBy = "cart")
    private List<CartDetail> cartDetails = new ArrayList<>();

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Date getExportDate() {
        return exportDate;
    }

    public void setExportDate(Date exportDate) {
        this.exportDate = exportDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
