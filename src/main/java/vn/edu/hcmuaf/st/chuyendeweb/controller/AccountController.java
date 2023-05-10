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
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/account/register")
    public ResponseEntity<?> register(@Valid @RequestBody AccountDTO accountDTO) {
        try {
            accountService.addAccount(accountDTO);
            return new ResponseEntity<>(new ResponMessenger("Tạo tài khoản thành công"), HttpStatus.OK);
        } catch (AccountException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/account/update")
    public AccountDTO updateAccount(@RequestBody AccountDTO accountDTO, @RequestParam("token") String token) {
        return accountService.update(accountDTO, token);
    }

    @GetMapping("/account/list")
    public List<Account> getAccounts() {
        return accountService.findAllAccount();
    }

    @GetMapping("/account/detail")
    public Account getAccount(@RequestParam("token") String token) {
        return accountService.findByUserName(token).get();
    }

    @PutMapping("/account/state/{id}")
    public String activeAccount(@RequestBody @PathVariable("id") Long id) {
        return accountService.activeAccount(id, "") ? "Đã kích hoạt tài khoản, vui lòng đăng nhập" : "Đã xảy ra lỗi";
    }

    @DeleteMapping("/account/delete/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable("id") Long id) {
        accountService.deleteAccountById(id);
        return new ResponseEntity<>(new ResponMessenger("Đã xóa tài khoản thành công"), HttpStatus.OK);
    }

    @PostMapping("/account/changePassword")
    public ResponseEntity<?> changePassword(@RequestParam String host, @RequestParam String username) {
        accountService.sendCodeToEmail(host, username);
        return new ResponseEntity<>(new ResponMessenger("Đã gửi mã xác thực qua email của bạn, vui lòng kiểm tra email"), HttpStatus.OK);
    }

    @PostMapping("/account/reset-password")
    public ResponseEntity<?> processResetPassword(@RequestParam("token") String token, @RequestParam("password") String password) {
        accountService.processResetPassword(token, password);
        return new ResponseEntity<>(new ResponMessenger("Thay đổi mật khẩu thành công"), HttpStatus.OK);
    }
}
