package it.unimol.vino.models.entity;

import it.unimol.vino.models.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;

@Data
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Builder
@Entity(name = "user")
@Table(name = "user")
@EqualsAndHashCode(exclude = "password")
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true)
    private String email;
    private String password;

    @Min(value = 18, message = "l'operaio deve essere maggiorenne")
    @Max(value = 70, message = "l'operaio non puo avere piu di 70 anni")
    private Integer age;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserProgressesProcess> progressedProcesses;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserModifyProcess> modifiedProcesses;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<UserSectorPermission> permissions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void addPermission(@NonNull Sector sector) {
        UserSectorPermission userSectorPermission = UserSectorPermission.builder()
                .user(this)
                .sector(sector)
                .canRead(true)
                .canWrite(this.role.equals(Role.ADMIN))
                .canDelete(this.role.equals(Role.ADMIN))
                .canUpdate(this.role.equals(Role.ADMIN))
                .build();
        this.permissions.add(userSectorPermission);
    }

    public void updatePermission(@NonNull Sector sector, @NonNull UserSectorPermission permissions) {
        this.permissions.stream()
                .filter(userSectorPermission -> userSectorPermission.getSector().equals(sector))
                .findFirst()
                .ifPresent(userSectorPermission -> {
                    userSectorPermission.setCanRead(permissions.getCanRead());
                    userSectorPermission.setCanWrite(permissions.getCanWrite());
                    userSectorPermission.setCanDelete(permissions.getCanDelete());
                    userSectorPermission.setCanUpdate(permissions.getCanUpdate());
                });
    }
}
