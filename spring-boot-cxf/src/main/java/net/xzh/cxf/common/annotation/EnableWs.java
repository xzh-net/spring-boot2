package net.xzh.cxf.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@WsComponentScan
public @interface EnableWs {

	@AliasFor(annotation = WsComponentScan.class, attribute = "basePackages")
	String[] scanBasePackages() default {};
}
