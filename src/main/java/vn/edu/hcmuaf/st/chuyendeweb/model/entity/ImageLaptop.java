package vn.edu.hcmuaf.st.chuyendeweb.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class ImageLaptop extends BaseEntity {
    @Column
    String linkImage;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "laptop_id")
    private Laptop laptop;
}
