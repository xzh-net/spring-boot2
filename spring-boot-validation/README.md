# 整合Validation处理非空校验拦截（支持国际化）

## 处理的两种方式

1. Controller方法中添加参数BindingResult result，基于拦截处理

```java
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
```

2. Controller不添加参数BindingResult result，基于全局异常捕获

```java
/**
     * 非空校验异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonResult<?> handleValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField()+fieldError.getDefaultMessage();
            }
        }
        return CommonResult.validateFailed(message);
    }
```


> 推荐使用第2种方式，代码简洁。第1种方式除了全局拦截，还可将错误通过每个Controller方法自己处理。



## 自定义注解

状态是否在指定范围内

## 国际化

验证信息增加了i18n的支持，需要在header中传递参数lang=en_US，通过Aspect统一拦截验证实体中的@NotEmpty中消息KEY进行语言转换。

访问地址：http://127.0.0.1:8080/doc.html