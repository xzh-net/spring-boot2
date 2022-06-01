package net.xzh.cxf.common.annotation;

import java.beans.Introspector;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.SystemPropertyUtils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import net.xzh.cxf.common.domain.SoapParseBean;
import net.xzh.cxf.common.domain.SoapParseBeanMapping;

/**
 * soap自动扫描
 * 
 * @author CR7
 *
 */
public class WsComponentScanRegistrar implements ImportBeanDefinitionRegistrar {

	private static final Logger LOGGER = LoggerFactory.getLogger(WsComponentScanRegistrar.class);

	private static HashMap<String, SoapParseBean> functionMap = new HashMap<String, SoapParseBean>();

	protected static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		Set<String> packagesToScan = getPackagesToScan(importingClassMetadata);
		registerServiceAnnotationBeanPostProcessor(packagesToScan, registry);
	}

	private void registerServiceAnnotationBeanPostProcessor(Set<String> packagesToScan,
			BeanDefinitionRegistry registry) {
		if (!packagesToScan.isEmpty()) {
			for(String path:packagesToScan) {
				loadCheckClassMethods(path);
			}
			AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
			beanDefinition.setBeanClass(SoapParseBeanMapping.class);// 实例化
			beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(functionMap);
			registry.registerBeanDefinition("soapParseBeanMapping", beanDefinition);
		}
	}

	private Set<String> getPackagesToScan(AnnotationMetadata metadata) {
		AnnotationAttributes attributes = AnnotationAttributes
				.fromMap(metadata.getAnnotationAttributes(WsComponentScan.class.getName()));
		String[] basePackages = attributes.getStringArray("basePackages");
		Class<?>[] basePackageClasses = attributes.getClassArray("basePackageClasses");
		String[] value = attributes.getStringArray("value");
		// Appends value array attributes
		Set<String> packagesToScan = new LinkedHashSet<String>(Arrays.asList(value));
		packagesToScan.addAll(Arrays.asList(basePackages));
		for (Class<?> basePackageClass : basePackageClasses) {
			packagesToScan.add(ClassUtils.getPackageName(basePackageClass));
		}
		if (packagesToScan.isEmpty()) {
			return Collections.singleton(ClassUtils.getPackageName(metadata.getClassName()));
		}
		return packagesToScan;
	}

	/**
	 * 根据扫描包的配置 加载需要检查的方法
	 */
	private static void loadCheckClassMethods(String scanPackages) {
		String[] scanPackageArr = scanPackages.split(",");
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
		for (String basePackage : scanPackageArr) {
			if (StringUtils.hasText(basePackage)) {
				String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils
						.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage)) + "/"
						+ DEFAULT_RESOURCE_PATTERN;
				try {
					Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
					for (Resource resource : resources) {
						// 检查resource，这里的resource都是class
						loadClassMethod(metadataReaderFactory, resource);
					}
				} catch (Exception e) {
					LOGGER.error("初始化SensitiveWordInterceptor失败,{}", e);
				}
			}
		}
	}

	/**
	 * 加载资源，判断里面的方法
	 *
	 * @param metadataReaderFactory spring中用来读取resource为class的工具
	 * @param resource              这里的资源就是一个Class
	 * @throws IOException
	 */
	private static void loadClassMethod(MetadataReaderFactory metadataReaderFactory, Resource resource)
			throws Exception {
		if (resource.isReadable()) {
			MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
			if (metadataReader != null) {
				String className = metadataReader.getClassMetadata().getClassName();
				try {
					tryCacheMethod(className);
				} catch (ClassNotFoundException e) {
					LOGGER.error("检查[{}]是否含有需要信息失败,{}", className, e);
				}
			}
		}
	}

	/**
	 * 把action下面的所有method遍历一次，标记他们是否需要进行xxx验证 如果需要，放入cache中
	 *
	 * @param fullClassName
	 */
	private static void tryCacheMethod(String fullClassName) throws ClassNotFoundException {
		Class<?> clz = Class.forName(fullClassName);
		Service annotation = clz.getAnnotation(Service.class);
		// 获取当前类注解名称 为空使用短类名
		Method[] methods = clz.getDeclaredMethods();
		for (Method method : methods) {
			if (method.getModifiers() != Modifier.PUBLIC && clz.isInterface()) {
				continue;
			}
			SoapParseBean soapParseBean = new SoapParseBean();
			SoapMapping sm = AnnotationUtils.findAnnotation(method, SoapMapping.class);
			if (ObjectUtil.isNotNull(sm) && StrUtil.isNotBlank(sm.funcId())) {
				soapParseBean.setFuncId(sm.funcId());
			} else {
				soapParseBean.setFuncId(method.getName());
			}
			if (ObjectUtil.isNotNull(annotation) && StrUtil.isNotBlank(annotation.value())) {
				soapParseBean.setBeanId(annotation.value());
			} else {
				soapParseBean.setBeanId(Introspector.decapitalize(ClassUtils.getShortName(fullClassName)));
			}
			soapParseBean.setBeanMethod(method.getName());
			functionMap.put(soapParseBean.getFuncId(), soapParseBean);
		}
	}
}
