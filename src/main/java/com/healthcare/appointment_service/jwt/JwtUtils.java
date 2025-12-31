package com.healthcare.appointment_service.jwt;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.function.Function;

@Component
@Slf4j
public class JwtUtils {

    private static final String SECRET_KEY = "FAF8B138266DBD754B84D21D2287F1A2B3C4D5E6F708192A3B4C5D6E7F8091A2B3C4D5E6F7";

    public String getToken(HttpServletRequest httpServletRequest){
        String bearerToken = httpServletRequest.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);

            return true;
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: {}", e.getMessage());
        }
        return false;
    }


    public String extractUserName(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public String extractRole(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)   // same secret used to sign
                .parseClaimsJws(token)
                .getBody();

        return claims.get("role", String.class);
    }

    private <T> T extractClaim(String token, Function<Claims,T> function){
        final Claims claims = extractAllClaims(token);
        return function.apply(claims);
    }
    private Claims extractAllClaims(String token){

        return
                Jwts
                        .parser()
                        .setSigningKey(SECRET_KEY)
                        .parseClaimsJws(token)
                        .getBody();
    }
}
