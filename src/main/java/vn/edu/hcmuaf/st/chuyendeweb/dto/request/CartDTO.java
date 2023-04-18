package vn.edu.hcmuaf.st.chuyendeweb.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CartDTO {
    private List<LaptopDTO> laptopDTOs = new ArrayList<>();
    private double totalPayment = 0;
}
