package vn.edu.hcmuaf.st.chuyendeweb.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.st.chuyendeweb.converter.AccountConverter;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.AccountDTO;
import vn.edu.hcmuaf.st.chuyendeweb.dto.response.JwtResponse;
import vn.edu.hcmuaf.st.chuyendeweb.evenlistener.AccountCreatedEvent;
import vn.edu.hcmuaf.st.chuyendeweb.exception.AccountException;
import vn.edu.hcmuaf.st.chuyendeweb.exception.ServiceException;
import vn.edu.hcmuaf.st.chuyendeweb.model.RoleType;
import vn.edu.hcmuaf.st.chuyendeweb.model.State;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Account;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Role;
import vn.edu.hcmuaf.st.chuyendeweb.repository.AccountRepository;
import vn.edu.hcmuaf.st.chuyendeweb.security.jwt.JwtTokenProvider;
import vn.edu.hcmuaf.st.chuyendeweb.security.useprincal.UserPrinciple;
import vn.edu.hcmuaf.st.chuyendeweb.service.IAccountService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {
    private final AccountRepository accountRepository;
    private final AccountConverter accountConverter;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ApplicationContext applicationContext;

    //
    private final Map<String, String> tokenLoginMap = new HashMap<>();

    @Override
    public String getOldToken(String username) {
        return tokenLoginMap.get(username);
    }

    @Override
    public JwtResponse login(String username, String password) {
        // Kiểm tra tài khoản có đúng trạng thái hay không
        validateAccount(username);
        // Xác thực username và password
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context

        String tokenUser = tokenLoginMap.get(username);
        if (tokenUser == null || !jwtTokenProvider.validateToken(tokenUser)) {
            tokenUser = jwtTokenProvider.generateToken(authenticate);
            tokenLoginMap.put(username, tokenUser);
        }
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserPrinciple userPrinciple = (UserPrinciple) authenticate.getPrincipal();
        return new JwtResponse(tokenUser, userPrinciple.getUsername(), userPrinciple.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
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
            throw new ServiceException(HttpStatus.FOUND, "Tên tài khoản đã tồn tại");
        }
        if (findByEmail(dto.getEmail().trim()).isPresent()) {
            throw new ServiceException(HttpStatus.FOUND, "Email đã tồn tại");
        }
        List<Role> roles = new ArrayList<>();
        List<Role> roleList = dto.getRoles();
        if (roleList.size() > 0) {
            roleList.forEach(role -> {
                if ("ADMIN".equals(role.getCode())) {
                    Role adminRole = roleService.findByCode(RoleType.ADMIN.getCode()).orElseThrow(
                            () ->  new ServiceException(HttpStatus.NOT_FOUND, "Không tìm thấy quyền " + RoleType.ADMIN.getCode())
                    );
                    roles.add(adminRole);
                } else {
                    Role userRole = roleService.findByCode(RoleType.USER.getCode()).orElseThrow(
                            () -> new ServiceException(HttpStatus.NOT_FOUND, "Không tìm thấy quyền " + RoleType.USER.getCode())
                    );
                    roles.add(userRole);
                }
            });
        } else {
            Role userRole = roleService.findByCode(RoleType.USER.getCode()).orElseThrow(
                    () -> new ServiceException(HttpStatus.NOT_FOUND, "Không tìm thấy quyền " + RoleType.USER.getCode())
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
        if (!optionalAccount.isPresent()) {
            throw new ServiceException(HttpStatus.NOT_FOUND, "Không tìm thấy thông tin tài khoản với id: " + dto.getId());
        }
        Account oldAccount = optionalAccount.get();
        account = accountConverter.toAccount(dto, oldAccount);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
        return accountConverter.toAccountDTO(account);
    }

    @Override
    public Boolean activeAccount(Long id, String activation_code) {
        Optional<Account> optionalAccount = findById(id);
        if (!optionalAccount.isPresent()) {
            throw new AccountException("Tài khoản không tồn tại");
        }
        Account account = optionalAccount.get();

        switch (account.getState()) {
            case PENDING:
                account.setState(State.ACTIVE);
                Account newAccount = accountRepository.save(account);
                applicationContext.publishEvent(new AccountCreatedEvent(newAccount));
                return true;
            case ACTIVE:
                throw new AccountException("Tài khoản đã kích hoạt trước đó");
            case REMOVED:
                throw new AccountException("Tài khoản đã bị xóa");
            case DISABLED:
                throw new AccountException("Tài khoản đã bị khóa");
        }
        return null;
    }

    @Override
    public Boolean updatePassword(String id, String password) {
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
    public Optional<Account> findById(Long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (!optionalAccount.isPresent()) {
            throw new AccountException("Không tìm thấy tài khoản với id: " + id);
        }
        return optionalAccount;
    }

    @Override
    public List<Account> findAllAccount() {
        return accountRepository.findAll();
    }

    private void validateAccount(String username) {
        Optional<Account> optionalAccount = findByUserName(username);
        if (optionalAccount.isEmpty()) {
//            throw new AccountException("Không tìm thấy tài khoản");
            throw new ServiceException(HttpStatus.NOT_FOUND, "Không tìm thấy tài khoản");
        }
        Account account = optionalAccount.get();
        if (account.getState() == State.PENDING) {
            throw new AccountException("Tài khoản chưa được kích hoạt");
        }
        if (account.getState() == State.DISABLED) {
            throw new AccountException("Tài khoản đã bị khóa");
        }
        if (account.getState() == State.REMOVED) {
            throw new AccountException("Tài khoản đã bị xóa");
        }
    }
}
