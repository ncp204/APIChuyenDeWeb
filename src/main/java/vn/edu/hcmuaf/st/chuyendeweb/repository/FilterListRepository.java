package vn.edu.hcmuaf.st.chuyendeweb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Laptop;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FilterListRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Page<Laptop> findTest(List<String> types, List<String> brands, List<String> chipCpus, int start, int limit) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Laptop> criteriaQuery = criteriaBuilder.createQuery(Laptop.class);
        Root<Laptop> root = criteriaQuery.from(Laptop.class);
        criteriaQuery.select(root);

        List<Predicate> predicates = new ArrayList<>();
        if (!types.isEmpty()) {
            predicates.add(root.get("type").in(types));
        }
        if (!brands.isEmpty()) {
            predicates.add(root.get("brand").in(brands));
        }
        if (!chipCpus.isEmpty()) {
            predicates.add(root.get("chipCpu").in(chipCpus));
        }
        if (!predicates.isEmpty()) {
            criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
        }

        TypedQuery<Laptop> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(start * limit);
        typedQuery.setMaxResults(limit);
        List<Laptop> laptops = typedQuery.getResultList();

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(Laptop.class)));
        if (!predicates.isEmpty()) {
            countQuery.where(predicates.toArray(new Predicate[predicates.size()]));
        }

        Long count = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(laptops, PageRequest.of(start, limit), count);
    }
}