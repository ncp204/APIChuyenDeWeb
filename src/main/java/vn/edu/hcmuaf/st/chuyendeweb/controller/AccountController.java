package vn.edu.hcmuaf.st.chuyendeweb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.AccountDTO;
import vn.edu.hcmuaf.st.chuyendeweb.dto.response.ResponMessenger;
import vn.edu.hcmuaf.st.chuyendeweb.exception.AccountException;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Account;
import vn.edu.hcmuaf.st.chuyendeweb.service.impl.AccountService;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AccountDTO accountDTO) {
        try {
            accountService.addAccount(accountDTO);
            return new ResponseEntity<>(new ResponMessenger("Tạo tài khoản thành công"), HttpStatus.OK);
        } catch (AccountException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public AccountDTO updateAccount(@RequestBody AccountDTO accountDTO, @PathVariable("id") long id) {
        accountDTO.setId(id);
        return accountService.update(accountDTO);
    }

    @GetMapping("list")
    public List<Account> getAccounts() {
        return accountService.findAllAccount();
    }

    @GetMapping("{id}")
    public Account getAccount(@RequestBody @PathVariable("id") Long id) {
        return accountService.findById(id).get();
    }

    @PutMapping("state/{id}")
    public String activeAccount(@RequestBody @PathVariable("id") Long id) {
        return accountService.activeAccount(id, "") ? "Đã kích hoạt tài khoản, vui lòng đăng nhập" : "Đã xảy ra lỗi";
    }

    @DeleteMapping("")
    public void deleteAccount(@RequestBody Long... ids) {

    }
}
