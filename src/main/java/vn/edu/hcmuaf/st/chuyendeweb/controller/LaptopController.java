package vn.edu.hcmuaf.st.chuyendeweb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.LaptopDTO;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.LaptopFilter;
import vn.edu.hcmuaf.st.chuyendeweb.dto.response.ResponMessenger;
import vn.edu.hcmuaf.st.chuyendeweb.hash.Hashing;
import vn.edu.hcmuaf.st.chuyendeweb.model.CPU;
import vn.edu.hcmuaf.st.chuyendeweb.model.ImageModel;
import vn.edu.hcmuaf.st.chuyendeweb.model.LaptopType;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Laptop;
import vn.edu.hcmuaf.st.chuyendeweb.paging.output.LaptopOutput;
import vn.edu.hcmuaf.st.chuyendeweb.service.impl.LaptopService;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class LaptopController {
    private final LaptopService laptopService;

    @PostMapping(value = "/laptop", consumes = "multipart/form-data")
    public LaptopDTO addNewLaptop(@RequestParam("laptopDTO")  String laptopDTOJson,
                                  @RequestParam("avatarFile") MultipartFile avatarFile,
                                  @RequestParam("imageFiles") MultipartFile[] imageFiles) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        LaptopDTO laptopDTO = objectMapper.readValue(laptopDTOJson, LaptopDTO.class);

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(laptopDTOJson, JsonObject.class);
        LaptopDTO laptopDTO = gson.fromJson(jsonObject, LaptopDTO.class);
        return laptopService.addLaptop(laptopDTO, avatarFile, imageFiles);
    }

//    @PostMapping("/laptop")
//    public LaptopDTO addNewLaptop(@RequestBody LaptopDTO laptopDTO) {
//        MultipartFile[] files = new MultipartFile[2];
//        MultipartFile file = null;
//        return laptopService.addLaptop(laptopDTO, file, files);
//    }

    @PutMapping("/laptop/{id}")
    public LaptopDTO updateLaptop(@RequestBody LaptopDTO laptopDTO, @PathVariable("id") long id) {
        laptopDTO.setId(id);
        return laptopService.updateLaptop(laptopDTO);
    }

    @GetMapping("/laptop")
    public LaptopOutput getLaptop(@RequestParam("start") int start,
                                  @RequestParam("limit") int limit,
                                  @RequestParam(value = "types", required = false) List<String> types,
                                  @RequestParam(value = "brands", required = false) List<String> brands,
                                  @RequestParam(value = "chipCpus", required = false) List<String> chipCpus) {
        LaptopFilter filter = new LaptopFilter();
        filter.setTypes(types != null && types.size() > 0 ? types : new ArrayList<>());
        filter.setBrands(brands != null && brands.size() > 0 ? brands : new ArrayList<>());
        filter.setChipCpus(chipCpus != null && chipCpus.size() > 0 ? chipCpus : new ArrayList<>());

        Page<Laptop> laptopPage = laptopService.getAllLaptop(filter, start, limit);
        LaptopOutput output = new LaptopOutput();
        output.setTotalPage(laptopPage.getTotalPages());
        output.setPage(laptopPage.getNumber() + 1);
        output.setLaptopList(laptopPage.getContent());
        return output;
    }

    @GetMapping("/laptop/brand")
    public List<String> getAllBrand() {
        return laptopService.getAllBrand();
    }

    @DeleteMapping("/laptop")
    public ResponseEntity<?> deleteLaptop(@RequestBody Long... ids) {
        laptopService.deleteLaptop(ids);
        return new ResponseEntity<>(new ResponMessenger("Xóa laptop thành công"), HttpStatus.OK);
    }

    @GetMapping("/laptop/page")
    public LaptopOutput showLaptop(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        LaptopOutput result = new LaptopOutput();
        result.setPage(page);
        Pageable pageable = PageRequest.of(page - 1, limit);
        result.setListResult(laptopService.findAll(pageable));
        result.setTotalPage((int) Math.ceil((double) (laptopService.totalItem()) / limit));
        return result;
    }

    @GetMapping("/laptop/listType")
    public HashMap<String, String> listLaptopType() {
        HashMap<String, String> listType = new HashMap<>();
        for (LaptopType type : LaptopType.values()) {
            listType.put(type.getName(), type.getDescription());
        }
        return listType;
    }

    @GetMapping("/laptop/listCpu")
    public HashMap<String, String> listCpuLaptop() {
        HashMap<String, String> listType = new HashMap<>();
        CPU[] cpus = CPU.values();
        for (int i = 0; i <= cpus.length - 1; i++) {
            listType.put(cpus[i].getCpu(), cpus[i].getDescription());
        }
        return listType;
    }

    @GetMapping("/laptop/images/{id}")
    public List<String> getImageLinks(@PathVariable("id") Long id) {
        List<String> links = new ArrayList<>();
        links.addAll(laptopService.getImageLinks(id));
        return links;
    }

}
