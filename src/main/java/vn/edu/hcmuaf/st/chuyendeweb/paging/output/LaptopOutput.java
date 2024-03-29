package vn.edu.hcmuaf.st.chuyendeweb.paging.output;

import lombok.Getter;
import lombok.Setter;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.LaptopDTO;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Laptop;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LaptopOutput {
    private int page;
    private int totalPage;
    private List<LaptopDTO>  listResult = new ArrayList<>();
    private List<Laptop> laptopList = new ArrayList<>();
}
