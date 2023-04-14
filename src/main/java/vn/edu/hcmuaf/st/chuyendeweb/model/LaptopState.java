package vn.edu.hcmuaf.st.chuyendeweb.model;

import lombok.Getter;

@Getter
public enum LaptopState {
    HOT("HOT", "Hot");

    private String state;
    private String description;

    LaptopState(String state, String description) {
        this.state = state;
        this.description = description;
    }
}
