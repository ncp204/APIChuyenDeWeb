package vn.edu.hcmuaf.st.chuyendeweb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vn.edu.hcmuaf.st.chuyendeweb.exception.AccountException;
import vn.edu.hcmuaf.st.chuyendeweb.exception.LaptopException;

@ControllerAdvice
public class HandleExceptionController {
    @ExceptionHandler(AccountException.class)
    public ResponseEntity<String> handlerAccountException(AccountException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(LaptopException.class)
    public String handlerLaptopException(LaptopException exception) {
        return "error";
    }
}
