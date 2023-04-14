package vn.edu.hcmuaf.st.chuyendeweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Facility;

import java.util.List;
import java.util.Optional;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
    Optional<Facility> findById(Long id);

    Optional<Facility> findByFacilityName(String facilityName);

}
