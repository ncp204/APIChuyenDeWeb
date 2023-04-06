package vn.edu.hcmuaf.st.chuyendeweb.entity;

import vn.edu.hcmuaf.st.chuyendeweb.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Role extends BaseEntity {
    @Column
    private String code;
    @Column
    private String nameCode;

    @ManyToMany(mappedBy = "roles")
    private List<Account> accounts = new ArrayList<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNameCode() {
        return nameCode;
    }

    public void setNameCode(String nameCode) {
        this.nameCode = nameCode;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
