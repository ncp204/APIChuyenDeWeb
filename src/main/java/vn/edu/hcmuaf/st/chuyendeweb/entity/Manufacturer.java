package vn.edu.hcmuaf.st.chuyendeweb.entity;

import vn.edu.hcmuaf.st.chuyendeweb.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Manufacturer extends BaseEntity {
    @Column
    private String name;
    @Column
    private String country;
    @Column
    private String poster;
    @Column
    private String logoSquare;
    @Column
    private String logoLine;
    @Column
    private String slogan;
    @OneToMany(mappedBy = "manufacturer")
    private List<Laptop> laptops = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getLogoSquare() {
        return logoSquare;
    }

    public void setLogoSquare(String logoSquare) {
        this.logoSquare = logoSquare;
    }

    public String getLogoLine() {
        return logoLine;
    }

    public void setLogoLine(String logoLine) {
        this.logoLine = logoLine;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public List<Laptop> getLaptops() {
        return laptops;
    }

    public void setLaptops(List<Laptop> laptops) {
        this.laptops = laptops;
    }
}
