package vn.edu.hcmuaf.st.chuyendeweb.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Role;
import vn.edu.hcmuaf.st.chuyendeweb.repository.RoleRepository;
import vn.edu.hcmuaf.st.chuyendeweb.service.IRoleService;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;

    @Override
    public Optional<Role> findByCode(String code) {
        return roleRepository.findByCode(code);
    }
}
