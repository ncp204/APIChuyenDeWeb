package vn.edu.hcmuaf.st.chuyendeweb.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LaptopAPI {
    @GetMapping("/test")
    public String testAPI() {
        return "success";
    }
}
