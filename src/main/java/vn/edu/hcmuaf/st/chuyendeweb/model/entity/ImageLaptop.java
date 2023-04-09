package vn.edu.hcmuaf.st.chuyendeweb.model.entity;

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
    @ManyToOne
    @JoinColumn(name = "laptop_id")
    private Laptop laptop;
}
