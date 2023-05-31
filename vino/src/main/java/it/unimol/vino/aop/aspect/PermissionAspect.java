package it.unimol.vino.aop.aspect;

import it.unimol.vino.aop.annotation.RequirePermissions;
import it.unimol.vino.exceptions.UnauthorizedAccessException;
import it.unimol.vino.models.entity.User;
import it.unimol.vino.models.enums.PermissionType;
import it.unimol.vino.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class PermissionAspect {

    private final UserRepository userRepository;

    @Around("@annotation(permissions)")
    public Object checkPermission(ProceedingJoinPoint joinPoint, RequirePermissions permissions) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        RequirePermissions annotation = signature.getMethod().getAnnotation(RequirePermissions.class);
        List<PermissionType> requiredPermissionType = Arrays.asList(annotation.value());

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Utente non trovato"));

        user.getPermissions().forEach(permission -> {

            if (!permission.getCanUpdate() && requiredPermissionType.contains(PermissionType.UPDATE)) {
                throw new UnauthorizedAccessException(user.getEmail()
                        + " non ha i permessi di aggiornamento per il settore "
                        + annotation.sector().name().toLowerCase()
                );
            }
            if (!permission.getCanDelete() && requiredPermissionType.contains(PermissionType.DELETE)) {
                throw new UnauthorizedAccessException(user.getEmail()
                        + " non ha i permessi di eliminazione per il settore "
                        + annotation.sector().name().toLowerCase()
                );
            }
            if (!permission.getCanUpdate() && requiredPermissionType.contains(PermissionType.WRITE)) {
                throw new UnauthorizedAccessException(user.getEmail()
                        + " non ha i permessi di scrittura per il settore "
                        + annotation.sector().name().toLowerCase()
                );
            }
            if (!permission.getCanRead() && requiredPermissionType.contains(PermissionType.READ)) {
                throw new UnauthorizedAccessException(user.getEmail()
                        + " non ha i permessi di lettura per il settore "
                        + annotation.sector().name().toLowerCase()
                );
            }

        });
        return joinPoint.proceed();
    }
}
