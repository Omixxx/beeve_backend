package it.unimol.vino.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirePermissions {
    enum PermissionType {
        READ,
        WRITE,
        DELETE
    }

    PermissionType[] value();
}
