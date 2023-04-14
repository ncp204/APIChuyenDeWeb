package vn.edu.hcmuaf.st.chuyendeweb.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class FacilityDTO {
    @NotBlank(message = "Tên nhà kho không được để trống")
    private String facilityName;
    private int quantityImport;
    private int quantityExport;
    private int quantityInventory;
}
