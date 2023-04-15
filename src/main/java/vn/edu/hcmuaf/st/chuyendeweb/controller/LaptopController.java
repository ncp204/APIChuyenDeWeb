package vn.edu.hcmuaf.st.chuyendeweb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.LaptopDTO;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.LaptopFilter;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Laptop;
import vn.edu.hcmuaf.st.chuyendeweb.paging.output.LaptopOutput;
import vn.edu.hcmuaf.st.chuyendeweb.service.impl.LaptopService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class LaptopController {
    private final LaptopService laptopService;

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
    public List<Laptop> getLaptop(@RequestBody LaptopFilter filter, @RequestParam("start") int start, @RequestParam("limit") int limit ) {
        Page<Laptop> laptopPage = laptopService.getAllLaptop(filter, start, limit);
        return laptopPage.getContent();
    }

    @GetMapping("/laptop/brand")
    public List<String> getAllBrand() {
        return laptopService.getAllBrand();
    }

    @DeleteMapping("/laptop")
    public void deleteLaptop(@RequestBody long... ids) {

    }

    @GetMapping("/laptop/page")
    public LaptopOutput showLaptop(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        LaptopOutput result = new LaptopOutput();
        result.setPage(page);
        Pageable pageable = PageRequest.of(page -1 , limit);
        result.setListResult(laptopService.findAll(pageable));
        result.setTotalPage((int) Math.ceil((double) (laptopService.totalItem()) / limit));
        return result;
    }
}
