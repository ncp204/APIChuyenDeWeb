package vn.edu.hcmuaf.st.chuyendeweb.hash;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class MD5 implements Hashing{
    @Override
    public String hashPassword(String password) {
        return null;
    }

    @Override
    public boolean validatePassword(String originalPassword, String storedPassword) {
        return false;
    }
}
