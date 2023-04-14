package vn.edu.hcmuaf.st.chuyendeweb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.FacilityDTO;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Facility;
import vn.edu.hcmuaf.st.chuyendeweb.service.impl.FacilityService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FacilityController {
    private final FacilityService facilityService;

    @PostMapping("/facility")
    public Facility addNewFacility(@RequestBody FacilityDTO facilityDTO) {
        return facilityService.addFacility(facilityDTO);
    }

    @GetMapping("/facility")
    public List<Facility> getAllFacility() {
        return facilityService.getAllFacility();
    }
}
