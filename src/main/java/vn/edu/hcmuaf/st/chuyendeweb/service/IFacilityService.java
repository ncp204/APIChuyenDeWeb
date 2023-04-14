package vn.edu.hcmuaf.st.chuyendeweb.service;

import vn.edu.hcmuaf.st.chuyendeweb.dto.request.FacilityDTO;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Facility;

import java.util.List;

public interface IFacilityService {
    Facility addFacility(FacilityDTO facilityDTO);
    List<Facility> getAllFacility();
}
