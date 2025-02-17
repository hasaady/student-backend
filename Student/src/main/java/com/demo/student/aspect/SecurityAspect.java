package com.demo.student.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;

@Aspect
@Component
public class SecurityAspect {

    @Around("@annotation(com.demo.student.aspect.SecureRole)")
    public Object checkUserRole(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SecureRole secureRole = method.getAnnotation(SecureRole.class);

        String requiredRole = secureRole.value();
        String userRole = getUserRole();

        if (!userRole.equals(requiredRole)) {
            throw new SecurityException("Access denied! Required role: " + requiredRole);
        }

        return joinPoint.proceed();
    }

    private String getUserRole() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return userDetails.getAuthorities().iterator().next().getAuthority();
        }
        return "ROLE_ANONYMOUS";
    }
}
