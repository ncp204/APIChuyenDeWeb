package vn.edu.hcmuaf.st.chuyendeweb.service;

import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Role;

import java.util.Optional;

public interface IRoleService {
    Optional<Role> findByCode(String code);
}
