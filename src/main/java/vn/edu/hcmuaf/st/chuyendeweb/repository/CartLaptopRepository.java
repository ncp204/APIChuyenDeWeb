package vn.edu.hcmuaf.st.chuyendeweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.CartLaptop;

import java.util.Optional;

public interface CartLaptopRepository extends JpaRepository<CartLaptop, Long> {
    Optional<CartLaptop> findByCartIdAndLaptopId(Long cartId, Long laptopId);
}
