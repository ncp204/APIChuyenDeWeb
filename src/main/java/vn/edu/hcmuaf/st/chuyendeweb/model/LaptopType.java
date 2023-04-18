package vn.edu.hcmuaf.st.chuyendeweb.model;

import lombok.Getter;

@Getter
public enum LaptopType {
    OFFICE("office", "Văn phòng"),
    GAMING("gaming", "Gaming"),
    DEV("dev", "Lập trình"),
    EDIT_VIDEO("edit_video", "Quay dựng video"),
    DESIGN_2D("design_2d", "Thiết kế 2D"),
    DESIGN_3D("design_3d", "Thiết kế 3d");

    private String name;
    private String description;

    LaptopType(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
