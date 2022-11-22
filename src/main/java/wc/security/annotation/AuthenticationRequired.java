package wc.security.annotation;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@PreAuthorize("isAuthenticated()")
@SecurityRequirement(name = "BearerToken")
@Retention(RUNTIME)
@Target(METHOD)
public @interface AuthenticationRequired {
}
