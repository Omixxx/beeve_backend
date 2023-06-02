package it.unimol.vino.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import it.unimol.vino.models.entity.User;
import it.unimol.vino.models.entity.UserSectorPermission;
import it.unimol.vino.models.enums.Role;
import it.unimol.vino.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import java.security.Key;
import java.util.*;


@ActiveProfiles("h2")
@Profile("!Production")
@RequiredArgsConstructor
public class AuthToken {
    private final UserRepository userRepository;

    private final String KEY = "3F4428472B4B6250655368566D597133743677397A24432646294A404D635166";

    public String generateToken() {
        User user = new User();
        user.setId(1L);
        user.setRole(Role.ADMIN);
        user.setFirstName("A");
        user.setLastName("B");
        user.setEmail("a@a.com");
        user.setPassword("Abcd9876");
        userRepository.save(user);
        byte[] keyBytes = Decoders.BASE64.decode(KEY);
        Key secretKey = Keys.hmacShaKeyFor(keyBytes);
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

}

