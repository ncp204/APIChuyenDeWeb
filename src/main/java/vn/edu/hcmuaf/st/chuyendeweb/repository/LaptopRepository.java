package vn.edu.hcmuaf.st.chuyendeweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Laptop;

public interface LaptopRepository extends JpaRepository<Laptop, Long> {
}
