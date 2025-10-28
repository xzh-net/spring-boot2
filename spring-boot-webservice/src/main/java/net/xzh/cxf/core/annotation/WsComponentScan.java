package net.xzh.cxf.core.annotation;



import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(WsComponentScanRegistrar.class)
public @interface WsComponentScan {

	String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

}
