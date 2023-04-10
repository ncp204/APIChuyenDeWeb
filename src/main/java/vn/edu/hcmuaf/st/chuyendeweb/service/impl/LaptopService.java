package vn.edu.hcmuaf.st.chuyendeweb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.st.chuyendeweb.converter.LaptopConverter;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.LaptopDTO;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Laptop;
import vn.edu.hcmuaf.st.chuyendeweb.repository.LaptopRepository;
import vn.edu.hcmuaf.st.chuyendeweb.service.ILaptopService;

import java.util.Optional;

@Service
public class LaptopService implements ILaptopService {

    @Autowired
    private LaptopRepository laptopRepository;

    @Autowired
    private LaptopConverter laptopConverter;

    @Override
    public LaptopDTO save(LaptopDTO laptopDTO) {
        Laptop laptop;
        if (laptopDTO.getId() != null) {
            Optional<Laptop> optionalLaptop = laptopRepository.findById(laptopDTO.getId());
            Laptop oldLaptop = optionalLaptop.get();
            laptop = laptopConverter.toLaptop(laptopDTO, oldLaptop);

        } else {
            laptop = laptopConverter.toLaptop(laptopDTO);
        }
        laptop = laptopRepository.save(laptop);
        return laptopConverter.toLaptopDTO(laptop);
    }
}
