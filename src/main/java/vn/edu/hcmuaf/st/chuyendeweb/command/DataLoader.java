package vn.edu.hcmuaf.st.chuyendeweb.command;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import vn.edu.hcmuaf.st.chuyendeweb.evenlistener.AccountCreatedEvent;
import vn.edu.hcmuaf.st.chuyendeweb.model.RoleType;
import vn.edu.hcmuaf.st.chuyendeweb.model.State;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Account;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Role;
import vn.edu.hcmuaf.st.chuyendeweb.repository.AccountRepository;
import vn.edu.hcmuaf.st.chuyendeweb.repository.RoleRepository;
import vn.edu.hcmuaf.st.chuyendeweb.service.impl.RoleService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final AccountRepository accountRepository;
    private final ApplicationContext applicationContext;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if(!hashDataRole()) {
            createDefaultRole();
        }
        if(!hashDataAccount()) {
            createDefaultAccount();
        }
    }

    public void createDefaultRole() {
        Role roleUser = new Role();
        roleUser.setCode("ADMIN");
        roleUser.setNameCode("QUẢN TRỊ VIÊN");
        roleUser.setCreateBy("Nguyễn Công Phúc");
        roleRepository.save(roleUser);

        Role roleAdmin = new Role();
        roleAdmin.setCode("USER");
        roleAdmin.setNameCode("NGƯỜI DÙNG");
        roleAdmin.setCreateBy("Nguyễn Công Phúc");
        roleRepository.save(roleAdmin);
    }

    public void createDefaultAccount() {
        Account admin = new Account();
        admin.setUserName("admin");
        admin.setPassword(passwordEncoder.encode("123456"));
        admin.setFullName("QUẢN TRỊ VIÊN");
        admin.setEmail("19130172@st.hcmuaf.edu.vn");
        admin.setState(State.ACTIVE);
        admin.setCreateBy("Nguyễn Công Phúc");
        Role adminRole = roleService.findByCode(RoleType.ADMIN.getCode()).get();
        Role userRole = roleService.findByCode(RoleType.USER.getCode()).get();
        List<Role> roles = new ArrayList<>();
        roles.add(adminRole);
        roles.add(userRole);
        admin.setRoles(roles);

//      Thêm tài khoản vào bảng account
        accountRepository.save(admin);
//      Thêm mới giỏ hàng cho tk admin
        applicationContext.publishEvent(new AccountCreatedEvent(admin));
    }

    private boolean hashDataRole() {
        return !roleRepository.findAll().isEmpty();
    }

    private boolean hashDataAccount() {
        return !accountRepository.findAll().isEmpty();
    }
}