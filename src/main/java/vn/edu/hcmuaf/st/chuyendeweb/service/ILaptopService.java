package vn.edu.hcmuaf.st.chuyendeweb.service;

import org.springframework.data.domain.Pageable;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.LaptopDTO;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.LaptopFilter;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Laptop;

import java.util.List;

public interface ILaptopService {
    LaptopDTO save(LaptopDTO laptopDTO);

    List<Laptop> getAllLaptop(LaptopFilter filter);

    List<String> getAllBrand();

    List<LaptopDTO> findAll(Pageable pageable);

    int totalItem();
}
