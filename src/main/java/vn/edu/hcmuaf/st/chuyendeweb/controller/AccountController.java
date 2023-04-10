package vn.edu.hcmuaf.st.chuyendeweb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.AccountDTO;
import vn.edu.hcmuaf.st.chuyendeweb.dto.response.ResponMessenger;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Account;
import vn.edu.hcmuaf.st.chuyendeweb.service.impl.AccountService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    @ResponseBody
    @PostMapping("/account")
    public ResponseEntity<?> register(@Valid @RequestBody AccountDTO accountDTO) {
        accountService.addAccount(accountDTO);
        return new ResponseEntity<>(new ResponMessenger("Tạo tài khoản thành công"), HttpStatus.OK);
    }

    @PutMapping("/account/{id}")
    public AccountDTO updateAccount(@RequestBody AccountDTO AccountDTO, @PathVariable("id") long id) {
        AccountDTO.setId(id);
        return accountService.update(AccountDTO);
    }

    @GetMapping("/account")
    public List<Account> getAccount() {
        return accountService.findAllAccount();
    }

    @DeleteMapping("/account")
    public void deleteAccount(@RequestBody Long... ids) {

    }
}
