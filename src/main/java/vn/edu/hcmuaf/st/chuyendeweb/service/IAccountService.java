package vn.edu.hcmuaf.st.chuyendeweb.service;

import vn.edu.hcmuaf.st.chuyendeweb.dto.request.AccountDTO;
import vn.edu.hcmuaf.st.chuyendeweb.dto.response.JwtResponse;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Account;

import java.util.List;
import java.util.Optional;

public interface IAccountService {
    String getOldToken(String username);

    JwtResponse login(String username, String password);

    boolean logout(String id);

    AccountDTO addAccount(AccountDTO dto);

    void deleteAccountById(Long id);

    AccountDTO update(AccountDTO dto, String token);

    void updateAccountByAdmin(AccountDTO dto, String token);

    Boolean activeAccount(Long id, String activation_code);

    Boolean updatePassword(String id, String password);

    Optional<Account> findByEmail(String email);

    Optional<Account> findByUserName(String username);

    Optional<Account> findById(Long id);

    List<Account> findAllAccount();

    void sendCodeToEmail(String host, String token);

    void processResetPassword(String token, String password);
}
