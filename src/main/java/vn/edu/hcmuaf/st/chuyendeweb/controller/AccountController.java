package vn.edu.hcmuaf.st.chuyendeweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.st.chuyendeweb.dto.AccountDTO;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Account;
import vn.edu.hcmuaf.st.chuyendeweb.service.impl.AccountService;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/account")
    public AccountDTO addNewAccount(@RequestBody AccountDTO AccountDTO) {
        return accountService.save(AccountDTO);
    }

    @PutMapping("/account/{id}")
    public AccountDTO updateAccount(@RequestBody AccountDTO AccountDTO, @PathVariable("id") long id) {
        AccountDTO.setId(id);
        return accountService.save(AccountDTO);
    }

    @GetMapping("/account")
    public List<Account> getAccount() {
        return accountService.findAllAccount();
    }

    @DeleteMapping("/account")
    public void deleteAccount(@RequestBody Long... ids) {

    }
}
