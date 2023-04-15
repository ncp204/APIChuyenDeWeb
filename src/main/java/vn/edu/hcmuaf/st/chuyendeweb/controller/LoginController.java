package vn.edu.hcmuaf.st.chuyendeweb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.st.chuyendeweb.exception.AccountException;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.LoginForm;
import vn.edu.hcmuaf.st.chuyendeweb.security.jwt.JwtTokenProvider;
import vn.edu.hcmuaf.st.chuyendeweb.service.IAccountService;
import vn.edu.hcmuaf.st.chuyendeweb.service.IRoleService;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/")
public class LoginController {
    private final IAccountService accountService;
    private final IRoleService roleService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping
    public String showHomePage() {
        return "index";
    }

    @GetMapping("login-re")
    public String showLogin(@RequestParam(required = false) String username) {
        String token = accountService.getOldToken(username + "");
        if (jwtTokenProvider.validateToken(token)) {
            return "index";
        }
        return "login";
    }

    @ResponseBody
    @PostMapping("auth/login")
    public ResponseEntity<?> handleLogin(@Valid @RequestBody LoginForm loginForm) {
        return ResponseEntity.ok(accountService.login(loginForm.getUsername(), loginForm.getPassword()));
    }

    @GetMapping("register")
    public String showRegister() {
        return "register ";
    }

    @GetMapping("foo")
    public String foo() {
        throw new AccountException("Không tìm thấy tài khoản");
    }

    @GetMapping("test")
    public String test() {
        return "Xin chào NCP ";
    }
}
