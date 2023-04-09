package vn.edu.hcmuaf.st.chuyendeweb.service;

import vn.edu.hcmuaf.st.chuyendeweb.dto.AccountDTO;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Account;

import java.util.List;
import java.util.Optional;

public interface IAccountService {
    Account login(String username, String password);
    boolean logout(String id);
    AccountDTO save(AccountDTO dto);
    Boolean activeAccount(String activation_code);
    Boolean updatePassword(String id, String password);
    Boolean updateEmail(String id, String newEmail);
    Optional<Account> findByEmail(String email);
    Account findById(String id);
    List<Account> findAllAccount();
}
