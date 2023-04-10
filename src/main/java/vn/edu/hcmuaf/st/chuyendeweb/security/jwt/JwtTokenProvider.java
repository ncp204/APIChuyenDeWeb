package vn.edu.hcmuaf.st.chuyendeweb.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import vn.edu.hcmuaf.st.chuyendeweb.security.useprincal.UserPrinciple;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    private final String JWT_SECRET = "ncphuc";

    //Thời gian có hiệu lực của chuỗi jwt, 86400 = 1 ngày
    private final int JWT_EXPIRATION = 86400;

    // Tạo ra jwt từ thông tin user
    public String generateToken(Authentication authentication) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return Jwts.builder().setSubject(userPrinciple.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + JWT_EXPIRATION * 1000))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    //  Kiểm tra token có hợp lệ hay không
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Token không hợp lệ");
        } catch (ExpiredJwtException ex) {
            log.error("Token đã hết hiệu lực");
        } catch (UnsupportedJwtException ex) {
            log.error("Token không được hỗ trợ");
        } catch (IllegalArgumentException ex) {
            log.error("Chuỗi yêu cầu trống");
        }
        return false;
    }

    // Tạo ra jwt từ thông tin user
    public String generateToken(UserPrinciple userPrinciple) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        // Tạo chuỗi json web token từ id của user.
        return Jwts.builder()
                .setSubject(Long.toString(userPrinciple.getAccount().getId()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    // Lấy thông tin user từ jwt
    public String getUserNameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

}

