package vn.edu.hcmuaf.st.chuyendeweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Laptop;

import java.util.List;

public interface LaptopRepository extends JpaRepository<Laptop, Long> {

    @Query("SELECT lt FROM Laptop lt WHERE (:types is null OR lt.type IN :types) AND (:brands is null OR lt.brand IN :brands) AND (:cpus is null OR lt.cpu IN :cpus)")
    List<Laptop> findByFilter(@Param("types") List<String> types, @Param("brands") List<String> brands, @Param("cpus") List<String> cpus);


    @Query("Select distinct l.brand from Laptop l")
    List<String> findAllBrand();
}
