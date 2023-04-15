package vn.edu.hcmuaf.st.chuyendeweb.controller;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.edu.hcmuaf.st.chuyendeweb.exception.AccountException;
import vn.edu.hcmuaf.st.chuyendeweb.exception.LaptopException;
import vn.edu.hcmuaf.st.chuyendeweb.exception.ServiceException;

@RestControllerAdvice
public class HandleExceptionController {
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler(AccountException.class)
//    public ResponseEntity<String> handlerAccountException(AccountException e) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
//    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServiceException.class)
    public ResponseException handlerServiceException(ServiceException e) {
        return new ResponseException(e.getMessage(), e.getStatus());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handlerAccountException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @Getter
    static class ResponseException {
        String mess;
        HttpStatus status;

        public ResponseException(String mess, HttpStatus status) {
            this.mess = mess;
            this.status = status;
        }
    }

}
