package net.xzh.cxf.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * 扫描注解
 * @author xzh
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@WsComponentScan
public @interface EnableWs {

	@AliasFor(annotation = WsComponentScan.class, attribute = "basePackages")
	String[] scanBasePackages() default {};
}
