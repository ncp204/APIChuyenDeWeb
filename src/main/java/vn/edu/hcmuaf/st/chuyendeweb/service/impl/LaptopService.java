package vn.edu.hcmuaf.st.chuyendeweb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.st.chuyendeweb.converter.LaptopConverter;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.LaptopDTO;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.LaptopFilter;
import vn.edu.hcmuaf.st.chuyendeweb.model.CPU;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Facility;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.ImageLaptop;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Laptop;
import vn.edu.hcmuaf.st.chuyendeweb.repository.FacilityRepository;
import vn.edu.hcmuaf.st.chuyendeweb.repository.LaptopRepository;
import vn.edu.hcmuaf.st.chuyendeweb.repository.FilterListRepository;
import vn.edu.hcmuaf.st.chuyendeweb.service.ILaptopService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LaptopService implements ILaptopService {
    private final LaptopRepository laptopRepository;
    private final LaptopConverter laptopConverter;
    private final FacilityRepository facilityRepository;
    private final FilterListRepository filterListRepository;

    public LaptopDTO save(LaptopDTO laptopDTO) {
        Laptop laptop;
        if (laptopDTO.getId() != null) {
            Optional<Laptop> optionalLaptop = laptopRepository.findById(laptopDTO.getId());
            Laptop oldLaptop = optionalLaptop.get();
            laptop = laptopConverter.toLaptop(laptopDTO, oldLaptop);

        } else {
            laptop = laptopConverter.toLaptop(laptopDTO);
        }
        Optional<Facility> optionalFacility = facilityRepository.findById(laptopDTO.getFacilityId());
        if (!optionalFacility.isPresent()) {
            throw new RuntimeException("Nhà kho không tồn tại hoặc đã xóa");
        }
        laptop.setFacility(optionalFacility.get());
        laptop = laptopRepository.save(laptop);
        List<ImageLaptop> imageLaptops = new ArrayList<>();
        ImageLaptop imageLaptop = null;
        for(String image : laptopDTO.getListImages()) {
            imageLaptop = new ImageLaptop();
            imageLaptop.setLaptop(laptop);
            imageLaptop.setLinkImage(image);
            imageLaptops.add(imageLaptop);
        }

        return laptopConverter.toLaptopDTO(laptop);
    }

    @Override
    public Page<Laptop> getAllLaptop(LaptopFilter filter, int start, int limit) {
        Page<Laptop> laptopPage = filterListRepository.findTest(filter.getTypes(), filter.getBrands(), filter.getChipCpus(), start - 1, limit);
        return laptopPage;
    }

    @Override
    public List<String> getAllBrand() {
        List<String> brands = laptopRepository.findAllBrand();
        return brands;
    }

    @Override
    public List<LaptopDTO> findAll(Pageable pageable) {
        List<LaptopDTO> result = new ArrayList<>();
        List<Laptop> entities = laptopRepository.findAll(pageable).getContent();
        for (Laptop laptop : entities) {
            result.add(laptopConverter.toLaptopDTO(laptop));
        }
        return result;
    }

    @Override
    public int totalItem() {
        return (int) laptopRepository.count();
    }
}
