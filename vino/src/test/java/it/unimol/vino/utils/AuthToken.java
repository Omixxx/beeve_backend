package it.unimol.vino.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import it.unimol.vino.models.entity.Sector;
import it.unimol.vino.models.entity.User;
import it.unimol.vino.models.entity.UserSectorPermission;
import it.unimol.vino.models.enums.Role;
import it.unimol.vino.models.enums.SectorName;
import it.unimol.vino.repository.SectorRepository;
import it.unimol.vino.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import java.security.Key;
import java.util.*;
import java.util.stream.Stream;


@ActiveProfiles("h2")
@Profile("!Production")
@RequiredArgsConstructor
public class AuthToken {
    private final SectorRepository sectorRepository;
    private final UserRepository userRepository;
    private User user;

    private final String KEY = "3F4428472B4B6250655368566D597133743677397A24432646294A404D635166";

    public String generateToken() {
        if(userRepository.findAll().isEmpty()){
        Stream.of(SectorName.values()).forEach(sectorName -> {
            Sector permission = new Sector(sectorName);
            if (this.sectorRepository.findSectorBySectorName(permission.getSectorName()).isEmpty())
                this.sectorRepository.save(permission);
        });
        List<UserSectorPermission> list = new ArrayList<UserSectorPermission>();
        UserSectorPermission userSectorPermission = new UserSectorPermission();
        user = new User();
        user.setRole(Role.ADMIN);
        user.setFirstName("A");
        user.setLastName("B");
        user.setEmail("a@a.com");
        user.setPassword("Abcd9876");
        userSectorPermission.setUser(user);
        list.add(userSectorPermission);
        user.setPermissions(list);
        List<Sector> sectors = this.sectorRepository.findAll();
        sectors.forEach(user::addPermission);
        userRepository.save(user);
        }
        user =userRepository.findAll().get(0);
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

    public User getUser() {
        return userRepository.findAll().get(0);
    }
}

