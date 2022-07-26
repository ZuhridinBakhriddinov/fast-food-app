package uz.pdp.fastfoodapp.annotation;


import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;
import java.lang.annotation.Target;


@Target({ElementType.PARAMETER, ElementType.TYPE,ElementType.ANNOTATION_TYPE,ElementType.FIELD,ElementType.METHOD,ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
public @interface CurrentUser {
}
