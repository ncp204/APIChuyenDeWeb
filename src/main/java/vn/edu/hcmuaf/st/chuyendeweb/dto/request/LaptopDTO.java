package vn.edu.hcmuaf.st.chuyendeweb.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LaptopDTO extends AbstractDTO<LaptopDTO> {
    private String productName;
    private String brand;
    private double price;
    private String cpu;
    private String ram;
    private String storage;
    private String display;
    private String graphics;
    private String color;
    private String battery;
    private String weight;
}
