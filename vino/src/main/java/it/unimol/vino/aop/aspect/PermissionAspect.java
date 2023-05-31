package it.unimol.vino.aop.aspect;

import it.unimol.vino.aop.annotation.RequirePermissions;
import it.unimol.vino.models.entity.User;
import it.unimol.vino.repository.SectorRepository;
import it.unimol.vino.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class PermissionAspect {

    private UserRepository userRepository;
    private SectorRepository sectorRepository;


//    @Before("@annotation(it.unimol.vino.aop.annotation.RequirePermissions)")
//    public Object checkPermission(ProceedingJoinPoint joinPoint, RequirePermissions permissions) throws Throwable {
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        RequirePermissions annotation = signature.getMethod().getAnnotation(RequirePermissions.class);
//
//
//        String email = SecurityContextHolder.getContext().getAuthentication().getName();
//        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
//
//        joinPoint.n
//
//
//        return joinPoint.proceed();
//    }
}
