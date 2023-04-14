package vn.edu.hcmuaf.st.chuyendeweb.repository;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.st.chuyendeweb.model.CPU;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Laptop;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class FilterListRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Laptop> findWithFilter(@Param("types") List<String> types, @Param("brands") List<String> brands, @Param("chipCpus") List<String> chipCpus) {
        StringBuilder sb = new StringBuilder();
        sb.append("Select lt FROM Laptop lt WHERE ");
        boolean hashTypes = (types != null) && (types.size() > 0);
        boolean hashBrands = (brands != null) && (brands.size() > 0);
        boolean hashCpus = (chipCpus != null) && (chipCpus.size() > 0);

        String typeIn = "lt.type IN :types";
        String typeNotIn = "lt.type NOT IN :types";
        String brandIn = "lt.brand IN :brands";
        String brandNotIn = "lt.brand NOT IN :brands";
        String cpuIn = "lt.chipCpu IN :chipCpus";
        String cpuNotIn = "lt.chipCpu NOT IN :chipCpus";

        sb.append(typeIn).append(" AND ").append(brandIn).append(" AND ").append(cpuIn);
        String sql = sb.toString();

        if (!hashTypes) {
            sql = sql.replace(typeIn, typeNotIn);
            types = new ArrayList<>(Collections.singletonList("NULL"));
        }
        if (!hashBrands) {
            sql = sql.replace(brandIn, brandNotIn);
            brands = new ArrayList<>(Collections.singletonList("NULL"));
        }
        if (!hashCpus) {
            sql = sql.replace(cpuIn, cpuNotIn);
            chipCpus = new ArrayList<>(Collections.singletonList("NULL"));
        }

        TypedQuery<Laptop> query = entityManager.createQuery(sql, Laptop.class);
        query.setParameter("types", types);
        query.setParameter("brands", brands);
        query.setParameter("chipCpus", chipCpus);
        return query.getResultList();
    }


}
