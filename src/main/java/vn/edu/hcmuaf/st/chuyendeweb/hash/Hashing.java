package vn.edu.hcmuaf.st.chuyendeweb.hash;

public interface Hashing {
    String hashPassword(String password);
    boolean validatePassword(String originalPassword, String storedPassword);
}
