package vn.edu.hcmuaf.st.chuyendeweb.converter;

import org.springframework.stereotype.Component;
import vn.edu.hcmuaf.st.chuyendeweb.dto.LaptopDTO;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Laptop;

@Component
public class LaptopConverter {

    public Laptop toLaptop(LaptopDTO dto) {
        Laptop laptop = new Laptop();
        laptop.setProductName(dto.getProductName());
        laptop.setBrand(dto.getBrand());
        laptop.setPrice(dto.getPrice());
        laptop.setCpu(dto.getCpu());
        laptop.setRam(dto.getRam());
        laptop.setStorage(dto.getStorage());
        laptop.setDisplay(dto.getDisplay());
        laptop.setGraphics(dto.getGraphics());
        laptop.setColor(dto.getColor());
        laptop.setBattery(dto.getBattery());
        laptop.setWeight(dto.getWeight());
        return laptop;
    }

    public LaptopDTO toLaptopDTO(Laptop laptop) {
        LaptopDTO dto = LaptopDTO.builder().build();
        if (null != laptop.getId()) {
            dto.setId(laptop.getId());
        }
        dto.setProductName(laptop.getProductName());
        dto.setBrand(laptop.getBrand());
        dto.setPrice(laptop.getPrice());
        dto.setCpu(laptop.getCpu());
        dto.setRam(laptop.getRam());
        dto.setStorage(laptop.getStorage());
        dto.setDisplay(laptop.getDisplay());
        dto.setGraphics(laptop.getGraphics());
        dto.setColor(laptop.getColor());
        dto.setBattery(laptop.getBattery());
        dto.setWeight(laptop.getWeight());
        dto.setCreatedDate(laptop.getCreatedDate());
        dto.setCreateBy(laptop.getCreateBy());
        dto.setModifiedBy(laptop.getModifiedBy());
        dto.setModifiedDate(laptop.getModifiedDate());
        return dto;
    }

    public Laptop toLaptop(LaptopDTO dto, Laptop laptop) {
        laptop.setProductName(dto.getProductName());
        laptop.setBrand(dto.getBrand());
        laptop.setPrice(dto.getPrice());
        laptop.setCpu(dto.getCpu());
        laptop.setRam(dto.getRam());
        laptop.setStorage(dto.getStorage());
        laptop.setDisplay(dto.getDisplay());
        laptop.setGraphics(dto.getGraphics());
        laptop.setColor(dto.getColor());
        laptop.setBattery(dto.getBattery());
        laptop.setWeight(dto.getWeight());
        return laptop;
    }
}
