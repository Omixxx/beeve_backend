package it.unimol.vino.utils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Profile;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Profile("test")
public class AuthToken {

    private static final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public  String generateToken() {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject("PIPPO")
                .setSubject("gfdgdgf")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(secretKey)
                .compact();
    }
    public static boolean validateToken(String token, String username) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

