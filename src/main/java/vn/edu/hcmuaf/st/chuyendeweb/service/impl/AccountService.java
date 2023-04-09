package vn.edu.hcmuaf.st.chuyendeweb.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.st.chuyendeweb.converter.AccountConverter;
import vn.edu.hcmuaf.st.chuyendeweb.dto.AccountDTO;
import vn.edu.hcmuaf.st.chuyendeweb.exception.AccountException;
import vn.edu.hcmuaf.st.chuyendeweb.hash.Hashing;
import vn.edu.hcmuaf.st.chuyendeweb.model.State;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Account;
import vn.edu.hcmuaf.st.chuyendeweb.repository.AccountRepository;
import vn.edu.hcmuaf.st.chuyendeweb.service.IAccountService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService implements IAccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountConverter accountConverter;
    private Hashing hashing;

    @Override
    public Account login(String username, String password) {
        Optional<Account> optionalAccount = accountRepository.findByUserName(username);
        if (!optionalAccount.isPresent()) {
            throw new AccountException("Account is not found");
        }
        Account account = optionalAccount.get();
//      Kiểm tra tài khoản đã được kich hoạt hay chưa
        if (account.getState() != State.ACTIVE) {
            throw new AccountException("Account is not activated");
        }
        if (password.equalsIgnoreCase(account.getPassword())) {
            return account;
        } else {
            throw new AccountException("Password is incorrect");
        }
    }

    @Override
    public boolean logout(String id) {
        return false;
    }

    //   Thêm mới hoặc cập nhật tài khoản
    @Override
    public AccountDTO save(AccountDTO dto) {
        Account account;
        if (dto.getId() != null) {
            Optional<Account> optionalAccount = accountRepository.findById(dto.getId());
            Account oldAccount = optionalAccount.get();
            account = accountConverter.toAccount(dto, oldAccount);
        } else {
            dto.setState(State.PENDING);
            account = accountConverter.toAccount(dto);
        }
        account = accountRepository.save(account);
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
        return Optional.empty();
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
