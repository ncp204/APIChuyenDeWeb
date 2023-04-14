package vn.edu.hcmuaf.st.chuyendeweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findCartById(Long id);
}
