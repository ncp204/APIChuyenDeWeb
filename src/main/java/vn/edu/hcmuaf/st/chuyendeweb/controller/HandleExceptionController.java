package vn.edu.hcmuaf.st.chuyendeweb.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vn.edu.hcmuaf.st.chuyendeweb.exception.AccountException;

@ControllerAdvice
public class HandleExceptionController {
    @ExceptionHandler(AccountException.class)
    public String handlerAccountException(AccountException e, Model model) {
        model.addAttribute("error", e);
        return "error";
    }
}
