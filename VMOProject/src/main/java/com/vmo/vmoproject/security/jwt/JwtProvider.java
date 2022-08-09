package com.vmo.vmoproject.security.jwt;

import com.vmo.vmoproject.security.userprincal.UserPrinciple;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    private final String jwtSecret = "thangHv";

    public String createToken(Authentication authentication) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        int jwtExpiration = 86400;
        return Jwts.builder().setSubject(userPrinciple.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpiration * 1000))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature -> Message : ", e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT format -> Message : ", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT -> Message : ", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT -> Message : ", e);
        } catch (IllegalArgumentException e) {
            logger.error("IllegalArgument JWT -> Message : ", e);
        }
        return false;
    }
    public String getUsernameByToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
}