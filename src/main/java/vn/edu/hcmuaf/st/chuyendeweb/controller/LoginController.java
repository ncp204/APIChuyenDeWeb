package vn.edu.hcmuaf.st.chuyendeweb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.AccountDTO;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.LogInForm;
import vn.edu.hcmuaf.st.chuyendeweb.dto.response.JwtResponse;
import vn.edu.hcmuaf.st.chuyendeweb.dto.response.ResponMessenger;
import vn.edu.hcmuaf.st.chuyendeweb.exception.AccountException;
import vn.edu.hcmuaf.st.chuyendeweb.request.LoginRequest;
import vn.edu.hcmuaf.st.chuyendeweb.security.jwt.JwtTokenProvider;
import vn.edu.hcmuaf.st.chuyendeweb.security.useprincal.UserPrinciple;
import vn.edu.hcmuaf.st.chuyendeweb.service.IAccountService;
import vn.edu.hcmuaf.st.chuyendeweb.service.IRoleService;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class LoginController {
    private final IAccountService accountService;
    private final IRoleService roleService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping
    public String showHomePage() {
        return "index";
    }

    @GetMapping("login")
    public String showLogin() {
        return "login";
    }

//    @ResponseBody
//    @PostMapping("login")
//    public String handleLogin(@RequestBody @Valid LoginRequest request) {
//        accountService.login(request.getUsername(), request.getPassword());
//        return "thanh cong";
//    }

    @ResponseBody
    @PostMapping("login")
    public ResponseEntity<?> handleLogin(@Valid @RequestBody LoginRequest loginRequest) {
        accountService.login(loginRequest.getUsername(), loginRequest.getPassword());
//      Xác thực username và password
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(token, userPrinciple.getUsername(), userPrinciple.getAuthorities()));
    }

//    void token() {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
//        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()));
//        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    }

    @GetMapping("register")
    public String showRegister() {
        return "register ";
    }

    @GetMapping("foo")
    public String foo() {
        throw new AccountException("Account not found");
    }
}
