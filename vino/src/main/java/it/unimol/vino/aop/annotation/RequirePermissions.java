package it.unimol.vino.aop.annotation;

import it.unimol.vino.models.enums.PermissionType;
import it.unimol.vino.models.enums.SectorName;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirePermissions {

    PermissionType[] value();

    SectorName sector();
}
