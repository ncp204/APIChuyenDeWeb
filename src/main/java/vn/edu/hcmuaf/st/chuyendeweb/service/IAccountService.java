package vn.edu.hcmuaf.st.chuyendeweb.service;

import vn.edu.hcmuaf.st.chuyendeweb.dto.request.AccountDTO;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Account;

import java.util.List;
import java.util.Optional;

public interface IAccountService {
    String login(String username, String password);
    boolean logout(String id);
    AccountDTO addAccount(AccountDTO dto);
    AccountDTO update(AccountDTO dto);
    Boolean activeAccount(String activation_code);
    Boolean updatePassword(String id, String password);
    Boolean updateEmail(String id, String newEmail);
    Optional<Account> findByEmail(String email);
    Optional<Account>  findByUserName(String username);
    Account findById(String id);
    List<Account> findAllAccount();
}
