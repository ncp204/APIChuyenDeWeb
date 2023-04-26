package vn.edu.hcmuaf.st.chuyendeweb.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.LaptopDTO;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.LaptopFilter;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Laptop;

import java.util.List;
import java.util.Optional;

public interface ILaptopService {
    LaptopDTO addLaptop(LaptopDTO laptopDTO,MultipartFile linkAvatar,  MultipartFile[] imageFiles);

    LaptopDTO updateLaptop(LaptopDTO laptopDTO);

    void deleteLaptop(Long... ids);

    LaptopDTO save(LaptopDTO laptopDTO);

    Page<Laptop> getAllLaptop(LaptopFilter filter, int start, int limit);

    List<String> getAllBrand();

    List<String> getAllType();

    List<String> getAllChipCpu();

    List<LaptopDTO> findAll(Pageable pageable);

    LaptopDTO findLaptopById(Long id);

    int totalItem();

    List<String> getImageLinks(Long id);
}
