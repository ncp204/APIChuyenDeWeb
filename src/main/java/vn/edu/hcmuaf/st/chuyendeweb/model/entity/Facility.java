package vn.edu.hcmuaf.st.chuyendeweb.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Facility extends BaseEntity {
    @Column
    private String facilityName;
    @Column
    private int quantityImport;
    @Column
    private int quantityExport;
    @Column
    private int quantityInventory;
    @JsonIgnore
    @OneToMany(mappedBy = "facility")
    private List<Laptop> laptops = new ArrayList<>();
}
