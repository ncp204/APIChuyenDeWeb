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
public class Manufacturer extends BaseEntity {
    @Column
    private String name;
    @Column
    private String slogan;
    @JsonIgnore
    @OneToMany(mappedBy = "manufacturer")
    private List<Laptop> laptops = new ArrayList<>();
}
