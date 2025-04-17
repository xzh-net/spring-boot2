package net.xzh.mall.common.component;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import net.xzh.mall.common.model.CommonResult;
import net.xzh.mall.i18n.I18nMessages;

/**
 * HibernateValidator错误结果处理切面 
 */
@Aspect
@Component
@Order(2)
public class BindingResultAspect {
	@Pointcut("execution(public * net.xzh..controller.*.*(..))")
	public void BindingResult() {
	}

	@Around("BindingResult()")
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
		Object[] args = joinPoint.getArgs();
		for (Object arg : args) {
			if (arg instanceof BindingResult) {
				BindingResult result = (BindingResult) arg;
				if (result.hasErrors()) {
					FieldError fieldError = result.getFieldError();
					if (fieldError != null) {
						return CommonResult.validateFailed(I18nMessages.getMessage(fieldError.getDefaultMessage()));
					} else {
						return CommonResult.validateFailed();
					}
				}
			}
		}
		return joinPoint.proceed();
	}
}
