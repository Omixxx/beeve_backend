package it.unimol.vino.aop.aspect;

import it.unimol.vino.aop.annotation.RequirePermissions;
import it.unimol.vino.exceptions.UnauthorizedAccessException;
import it.unimol.vino.models.entity.User;
import it.unimol.vino.models.entity.UserSectorPermission;
import it.unimol.vino.models.enums.PermissionType;
import it.unimol.vino.models.enums.SectorName;
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

        user.getPermissions()
                .stream()
                .filter(permission -> permission.getSector().getSectorName().equals(permissions.sector()))
                .forEach(permission -> {
                    validatePermissions(permissions, requiredPermissionType, email, permission);
                });

        return joinPoint.proceed();
    }

    private void validatePermissions(RequirePermissions permissions, List<PermissionType> requiredPermissionType, String email, UserSectorPermission permission) {
        if (requiredPermissionType.contains(PermissionType.WRITE) && !permission.getCanWrite())
            throwUnauthorizedAccessException(email, permissions.sector());
        if (requiredPermissionType.contains(PermissionType.READ) && !permission.getCanRead())
            throwUnauthorizedAccessException(email, permissions.sector());
        if (requiredPermissionType.contains(PermissionType.UPDATE) && !permission.getCanUpdate())
            throwUnauthorizedAccessException(email, permissions.sector());
        if (requiredPermissionType.contains(PermissionType.DELETE) && !permission.getCanDelete())
            throwUnauthorizedAccessException(email, permissions.sector());
    }

    private void throwUnauthorizedAccessException(String userEmail, SectorName sectorName) {
        throw new UnauthorizedAccessException(userEmail
                + " non ha i permessi di aggiornamento per il settore "
                + sectorName.toString().toLowerCase()
        );
    }
}
