package net.xzh.log.aspect;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.NamedThreadLocal;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import net.xzh.log.aspect.annotation.AuditLog;
import net.xzh.log.model.Audit;
import net.xzh.log.properties.AuditLogProperties;
import net.xzh.log.service.IAuditService;
import net.xzh.log.utils.IpUtil;

/**
 * 审计日志拦截器 该类通过AOP方式拦截带有@AuditLog注解的方法或类，记录操作日志。 包括请求参数、返回结果、执行耗时、用户信息、IP地址等。
 * 
 */
@Slf4j
@Aspect
@Component
@EnableConfigurationProperties({AuditLogProperties.class})
@ConditionalOnClass({ HttpServletRequest.class, RequestContextHolder.class })
public class AuditLogAspect {

	private AuditLogProperties auditLogProperties;

	private IAuditService auditService;

	public AuditLogAspect(AuditLogProperties auditLogProperties, IAuditService auditService) {
		this.auditLogProperties = auditLogProperties;
		this.auditService = auditService;
	}

	/**
	 * 计算操作消耗时间
	 */
	private static final ThreadLocal<Long> TIME_THREADLOCAL = new NamedThreadLocal<Long>("Cost Time");

	/**
	 * 用于SpEL表达式解析.
	 */
	private SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

	/**
	 * 用于获取方法参数定义名字.
	 */
	private DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

	@Around("@within(auditLog) || @annotation(auditLog)")
	public Object aroundMethod(ProceedingJoinPoint joinPoint, AuditLog auditLog) throws Throwable {

		// 检查是否启用审计日志
		if (!auditLogProperties.getEnabled()) {
			return joinPoint.proceed();
		}
		// 在执行目标方法前记录开始时间
        TIME_THREADLOCAL.set(System.currentTimeMillis());
		if (auditLog == null) {
			// 获取类上的注解
			auditLog = joinPoint.getTarget().getClass().getDeclaredAnnotation(AuditLog.class);
		}

		Object result = null;
		Throwable exception = null;
		try {
			// 执行目标方法
			result = joinPoint.proceed();
			return result;
		} catch (Throwable throwable) {
			// 捕获方法执行过程中的异常
			exception = throwable;
			throw exception;
		} finally {
			try {
				// 记录审计日志
				Audit audit = getAudit(joinPoint, auditLog, result, exception);
				if (audit != null) {
					auditService.save(audit);
				}
			} catch (Exception logException) {
				log.error("记录审计日志时发生异常", logException);
			} finally {
				// 确保清除ThreadLocal，避免内存泄漏
				TIME_THREADLOCAL.remove();
			}
		}

	}

	private Audit getAudit(ProceedingJoinPoint joinPoint, AuditLog auditLog, Object result, Throwable exception) {
		// 获取当前请求上下文
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attributes == null) {
			log.warn("无法获取请求上下文，审计日志记录失败");
			return null;
		}
		HttpServletRequest request = attributes.getRequest();

		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Object[] args = joinPoint.getArgs();

		Audit audit = new Audit();
		// 基本信息设置
		audit.setCreateTime(DateUtil.date().toJdkDate());
		audit.setBusinessType(auditLog.businessType().getType());
		audit.setOperUrl(request.getRequestURI());
		audit.setMethod(buildFullMethodName(methodSignature));
		audit.setRequestMethod(request.getMethod());
		audit.setOperParam(StrUtil.sub(buildOperParam(args, methodSignature), 0, 2000));//vchar2000，根据实际情况修改text
		audit.setJsonResult(StrUtil.sub(buildJsonResult(result), 0, 2000));//vchar2000，根据实际情况修改text
		audit.setCostTime(calculateCostTime());
		audit.setOperIp(IpUtil.getIpAddr());
		audit.setTitle(resolveOperationTitle(auditLog.operation(), methodSignature, args));
		// 异常信息处理
		handleExceptionInfo(audit, exception);
		// 用户信息设置
		setUserInfo(audit);
		return audit;

	}

	/**
	 * 设置用户信息
	 * <p>
	 * 设置操作用户的ID、用户名、部门等信息。
	 * </p>
	 *
	 * @param audit 审计日志实体对象
	 */
	private void setUserInfo(Audit audit) {
		// TODO: 这里可以根据实际业务逻辑调整
		audit.setOperatorType(1);
		audit.setCreateUserId(22222222L);
		audit.setOperUsername("admin");
		audit.setDeptId(11111111L);
		audit.setDeptName("开发部");
	}

	/**
	 * 处理异常信息
	 * <p>
	 * 如果存在异常，则设置操作状态为失败，并记录异常堆栈信息。
	 * </p>
	 *
	 * @param audit     审计日志实体对象
	 * @param exception 方法执行过程中抛出的异常
	 */
	private void handleExceptionInfo(Audit audit, Throwable exception) {
		if (exception != null) {
			// 设置操作状态为失败
			audit.setStatus(1);
			// 记录异常堆栈信息
			String stackTrace = getStackTraceAsString(exception, 2000);
			audit.setErrorMsg(stackTrace);
		} else {
			// 无异常则设置为成功状态
			audit.setStatus(0);
		}
	}

	/**
	 * 将异常堆栈转换为字符串
	 * <p>
	 * 将异常的堆栈信息转换为字符串形式，并限制最大长度。
	 * </p>
	 *
	 * @param exception 异常对象
	 * @param maxLength 最大长度
	 * @return 转换后的字符串
	 */
	private String getStackTraceAsString(Throwable exception, int maxLength) {
		if (exception == null) {
			return "";
		}
		try {
			// 获取堆栈信息
			String stackTrace = Arrays.stream(exception.getStackTrace()).limit(20).map(StackTraceElement::toString)
					.collect(Collectors.joining("\n"));

			// 添加异常消息
			String exceptionInfo = exception.getClass().getName() + ": " + StrUtil.nullToEmpty(exception.getMessage())
					+ "\n" + stackTrace;

			// 限制长度
			return StrUtil.sub(exceptionInfo, 0, maxLength);
		} catch (Exception e) {
			log.warn("获取异常堆栈信息失败", e);
			return "获取异常信息失败: " + e.getMessage();
		}
	}

	/**
	 * 解析操作标题（支持SpEL表达式）
	 * <p>
	 * 如果标题中包含SpEL表达式，则进行解析；否则直接返回原始标题。
	 * </p>
	 *
	 * @param operation       原始标题
	 * @param methodSignature 方法签名
	 * @param args            方法参数
	 * @return 解析后的标题
	 */
	private String resolveOperationTitle(String operation, MethodSignature methodSignature, Object[] args) {
		if (StrUtil.isEmpty(operation) || !operation.contains("#")) {
			return operation;
		}
		try {
			return getValBySpEL(operation, methodSignature, args);
		} catch (Exception e) {
			log.warn("解析SpEL表达式失败: {}", operation, e);
			return operation; // 解析失败时返回原始表达式
		}
	}

	/**
	 * 计算方法执行耗时
	 * <p>
	 * 从ThreadLocal中获取开始时间，并计算当前时间与开始时间的差值。
	 * </p>
	 *
	 * @return 执行耗时（毫秒）
	 */
	private Long calculateCostTime() {
		Long startTime = TIME_THREADLOCAL.get();
		return startTime != null ? System.currentTimeMillis() - startTime : 0;
	}

	/**
	 * 构建返回结果的JSON字符串
	 * <p>
	 * 将方法返回值序列化为JSON字符串。
	 * </p>
	 *
	 * @param result 方法返回值
	 * @return 序列化后的JSON字符串
	 */
	private String buildJsonResult(Object result) {
		if (ObjectUtil.isEmpty(result)) {
			return "";
		}
		try {
			return JSONUtil.toJsonStr(result);
		} catch (Exception e) {
			log.warn("序列化返回结果失败", e);
			return "[返回结果序列化失败]";
		}
	}

	/**
	 * 构建完整的方法名（包含类名）
	 * <p>
	 * 返回方法所属类的全限定名与方法名组成的字符串。
	 * </p>
	 *
	 * @param methodSignature 方法签名
	 * @return 完整的方法名
	 */
	private String buildFullMethodName(MethodSignature methodSignature) {
		return methodSignature.getDeclaringTypeName() + "." + methodSignature.getName();
	}

	/**
	 * 构建操作参数
	 * <p>
	 * 将传入的参数转换为JSON格式字符串。
	 * </p>
	 *
	 * @param args 参数数组
	 * @return 参数的JSON字符串表示
	 */
	private String buildOperParam(Object[] args, MethodSignature methodSignature) {
		if (ObjectUtil.isEmpty(args)) {
			return "";
		}
		String[] paramNames = nameDiscoverer.getParameterNames(methodSignature.getMethod());
		Map<String, Object> paramMap = new LinkedHashMap<>();
		for (int i = 0; i < args.length; i++) {
			paramMap.put(paramNames[i], args[i]);
		}

		try {
			return JSONUtil.toJsonStr(paramMap);
		} catch (Exception e) {
			log.warn("序列化参数结果失败", e);
			return "[序列化参数结果失败]";
		}
	}

	/**
	 * 解析spEL表达式
	 */
	private String getValBySpEL(String spEL, MethodSignature methodSignature, Object[] args) {
		// 获取方法形参名数组
		String[] paramNames = nameDiscoverer.getParameterNames(methodSignature.getMethod());
		if (paramNames != null && paramNames.length > 0) {
			Expression expression = spelExpressionParser.parseExpression(spEL);
			// spring的表达式上下文对象
			EvaluationContext context = new StandardEvaluationContext();
			// 给上下文赋值
			for (int i = 0; i < args.length; i++) {
				context.setVariable(paramNames[i], args[i]);
			}
			return expression.getValue(context).toString();
		}
		return null;
	}
}
