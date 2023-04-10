package vn.edu.hcmuaf.st.chuyendeweb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.st.chuyendeweb.converter.AccountConverter;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.AccountDTO;
import vn.edu.hcmuaf.st.chuyendeweb.dto.response.ResponMessenger;
import vn.edu.hcmuaf.st.chuyendeweb.exception.AccountException;
import vn.edu.hcmuaf.st.chuyendeweb.model.RoleType;
import vn.edu.hcmuaf.st.chuyendeweb.model.State;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Account;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Role;
import vn.edu.hcmuaf.st.chuyendeweb.repository.AccountRepository;
import vn.edu.hcmuaf.st.chuyendeweb.repository.RoleRepository;
import vn.edu.hcmuaf.st.chuyendeweb.service.IAccountService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {
    private final AccountRepository accountRepository;
    private final AccountConverter accountConverter;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public String login(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return "dang nhap thanh cong";
    }

    @Override
    public boolean logout(String id) {
        return false;
    }

    //   Thêm mới hoặc cập nhật tài khoản
    @Override
    public AccountDTO addAccount(AccountDTO dto) {
        Account account;
        if (dto.getRoles() == null) {
            dto.setRoles(new ArrayList<>());
        }
        if (dto.getState() == null) {
            dto.setState(State.PENDING);
        }
        if (findByUserName(dto.getUserName().trim()).isPresent()) {
            throw new AccountException("Tài khoản đã tồn tại");
        }
        if (findByEmail(dto.getEmail().trim()).isPresent()) {
            throw new AccountException("Email đã tồn tại");
        }
        List<Role> roles = new ArrayList<>();
        List<Role> roleList = dto.getRoles();
        if (roleList.size() > 0) {
            roleList.forEach(role -> {
                if ("ADMIN".equals(role.getCode())) {
                    Role adminRole = roleService.findByCode(RoleType.ADMIN.getCode()).orElseThrow(
                            () -> new RuntimeException("Không tìm thấy quyền " + RoleType.ADMIN.getCode())
                    );
                    roles.add(adminRole);
                } else {
                    Role userRole = roleService.findByCode(RoleType.USER.getCode()).orElseThrow(
                            () -> new RuntimeException("Không tìm thấy quyền " + RoleType.USER.getCode())
                    );
                    roles.add(userRole);
                }
            });
        } else {
            Role userRole = roleService.findByCode(RoleType.USER.getCode()).orElseThrow(
                    () -> new RuntimeException("Không tìm thấy quyền " + RoleType.USER.getCode())
            );
            roles.add(userRole);
        }

        dto.setRoles(roles);
        account = accountConverter.toAccount(dto);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account = accountRepository.save(account);
        return accountConverter.toAccountDTO(account);
    }

    @Override
    public AccountDTO update(AccountDTO dto) {
        Account account;
        Optional<Account> optionalAccount = accountRepository.findById(dto.getId());
        Account oldAccount = optionalAccount.get();
        account = accountConverter.toAccount(dto, oldAccount);
        return accountConverter.toAccountDTO(account);
    }

    @Override
    public Boolean activeAccount(String activation_code) {
        return null;
    }

    @Override
    public Boolean updatePassword(String id, String password) {
        return null;
    }

    @Override
    public Boolean updateEmail(String id, String newEmail) {
        return null;
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public Optional<Account> findByUserName(String username) {
        return accountRepository.findByUserName(username);
    }

    @Override
    public Account findById(String id) {
        return null;
    }

    @Override
    public List<Account> findAllAccount() {
        return accountRepository.findAll();
    }
}
