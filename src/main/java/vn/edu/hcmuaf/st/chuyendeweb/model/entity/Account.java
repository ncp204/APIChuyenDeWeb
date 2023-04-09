package vn.edu.hcmuaf.st.chuyendeweb.model.entity;


import lombok.Getter;
import lombok.Setter;
import vn.edu.hcmuaf.st.chuyendeweb.model.State;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Account extends BaseEntity {
    @Column
    private String userName;
    @Column
    private String password;
    @Column
    private String fullName;
    @Column
    private String phone;
    @Column
    private String email;
    @Column
    private String sex;
    @Column
    private String address;
    @Column
    private String addressDetail;
    @Column
    private Integer status;
    @Column
    private State state;
    @ManyToMany
    @JoinTable(name = "account_role",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();
    @OneToOne(mappedBy = "account")
    private Cart cart;
}
