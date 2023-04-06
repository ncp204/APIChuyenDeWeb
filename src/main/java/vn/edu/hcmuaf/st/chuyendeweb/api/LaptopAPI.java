package vn.edu.hcmuaf.st.chuyendeweb.api;

import org.springframework.web.bind.annotation.*;

@RestController
public class LaptopAPI {
    @PostMapping("/laptop")
    public String addNewLaptop(String newLaptop) {
        return "Thêm laptop thành công";
    }

    @PutMapping("/laptop")
    public String updateLaptop(String newLaptop) {
        return "Sửa laptop thành công";
    }

    @GetMapping("/laptop")
    public String getLaptop(String newLaptop) {
        return "Lấy dữ liệu laptop thành công";
    }

    @DeleteMapping("/laptop")
    public void deleteLaptop(@RequestBody long... ids) {

    }
}
