package vn.edu.hcmuaf.st.chuyendeweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.LaptopDTO;
import vn.edu.hcmuaf.st.chuyendeweb.service.impl.LaptopService;

@RestController
public class LaptopController {

    @Autowired
    private LaptopService laptopService;

    @PostMapping("/laptop")
    public LaptopDTO addNewLaptop(@RequestBody LaptopDTO laptopDTO) {
        return laptopService.save(laptopDTO);
    }

    @PutMapping("/laptop/{id}")
    public LaptopDTO updateLaptop(@RequestBody LaptopDTO laptopDTO, @PathVariable("id") long id) {
        laptopDTO.setId(id);
        return laptopService.save(laptopDTO);
    }

    @GetMapping("/laptop")
    public String getLaptop(String newLaptop) {
        return "Lấy dữ liệu laptop thành công";
    }

    @DeleteMapping("/laptop")
    public void deleteLaptop(@RequestBody long... ids) {

    }
}
