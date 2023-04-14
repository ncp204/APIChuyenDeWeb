package vn.edu.hcmuaf.st.chuyendeweb.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.FacilityDTO;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Facility;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Role;
import vn.edu.hcmuaf.st.chuyendeweb.repository.FacilityRepository;
import vn.edu.hcmuaf.st.chuyendeweb.repository.RoleRepository;
import vn.edu.hcmuaf.st.chuyendeweb.service.IFacilityService;
import vn.edu.hcmuaf.st.chuyendeweb.service.IRoleService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FacilityService implements IFacilityService {

    private final FacilityRepository facilityRepository;

    @Override
    public Facility addFacility(FacilityDTO facilityDTO) {
        Optional<Facility> optionalFacility = facilityRepository.findByFacilityName(facilityDTO.getFacilityName().trim());
        if (optionalFacility.isPresent()) {
            throw new RuntimeException("Đã tồn tại nhà kho với tên " + facilityDTO.getFacilityName());
        }
        Facility facility = new Facility();
        facility.setFacilityName(facilityDTO.getFacilityName());
        facility = facilityRepository.save(facility);
        return facility;
    }

    @Override
    public List<Facility> getAllFacility() {
        List<Facility> facilities = facilityRepository.findAll();
        return facilities;
    }
}
