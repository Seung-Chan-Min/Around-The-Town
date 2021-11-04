package com.prgm.aroundthetown.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Where;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Where(clause = "is_deleted = false")
@DynamicInsert
public @interface SoftDeletableEntity {

}
