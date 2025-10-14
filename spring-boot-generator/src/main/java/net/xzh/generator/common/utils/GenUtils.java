package net.xzh.generator.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import lombok.extern.slf4j.Slf4j;
import net.xzh.generator.model.ColumnEntity;
import net.xzh.generator.model.TableEntity;

/**
 * 代码生成器工具类
 *
 * @author zlt
 */
@Slf4j
public class GenUtils {
	private GenUtils() {
		throw new IllegalStateException("Utility class");
	}

	private final static String FILE_NAME_MODEL_PO = "ModelPO.java.vm";					// 持久化对象
	private final static String FILE_NAME_MODEL_DTO = "ModelDTO.java.vm";				// 数据传输对象
	
	private final static String FILE_NAME_MODEL_VO = "ModelVO.java.vm";					// 详情数据对象
	private final static String FILE_NAME_MODEL_LIST_VO = "ModelListVO.java.vm";		// 列表数据对象
	private final static String FILE_NAME_MODEL_FORM_VO = "ModelFormVO.java.vm";		// 表单提交对象
	private final static String FILE_NAME_MODEL_QUERY_VO = "ModelQueryVO.java.vm";		// 列表查询条件对象
	private final static String FILE_NAME_MODEL_CONVERT = "ModelConvert.java.vm";		// 数据转换对象

	private final static String FILE_NAME_MAPPER = "Mapper.java.vm";
	private final static String FILE_NAME_MAPPERXML = "Mapper.xml.vm";
	private final static String FILE_NAME_SERVICE = "Service.java.vm";
	private final static String FILE_NAME_SERVICEIMPL = "ServiceImpl.java.vm";
	private final static String FILE_NAME_CONTROLLER = "Controller.java.vm";

	/**
	 * 前端
	 */

	private final static String FILE_NAME_PAGE = "index.html.vm";
	private final static String TEMPLATE_PATH = "template/";

	/**
	 * 全局常量
	 */
	private final static String PACKAGE = "package";
	private final static String MODULE_NAME = "moduleName";

	public static List<String> getTemplates() {
		List<String> templates = new ArrayList<>();
		/**
		 * 后端
		 */
		templates.add(TEMPLATE_PATH + FILE_NAME_MODEL_PO);			// 持久化对象
		templates.add(TEMPLATE_PATH + FILE_NAME_MODEL_DTO);			// 数据传输对象
		templates.add(TEMPLATE_PATH + FILE_NAME_MODEL_VO);			// 详情数据对象
		templates.add(TEMPLATE_PATH + FILE_NAME_MODEL_LIST_VO);		// 列表数据对象
		templates.add(TEMPLATE_PATH + FILE_NAME_MODEL_FORM_VO);		// 表单提交对象
		templates.add(TEMPLATE_PATH + FILE_NAME_MODEL_QUERY_VO);	// 列表查询条件对象
		templates.add(TEMPLATE_PATH + FILE_NAME_MODEL_CONVERT);		// 数据转换对象
		
		templates.add(TEMPLATE_PATH + FILE_NAME_MAPPER);
		templates.add(TEMPLATE_PATH + FILE_NAME_MAPPERXML);
		templates.add(TEMPLATE_PATH + FILE_NAME_SERVICE);
		templates.add(TEMPLATE_PATH + FILE_NAME_SERVICEIMPL);
		templates.add(TEMPLATE_PATH + FILE_NAME_CONTROLLER);
		
		/**
		 * 前端
		 */
		templates.add(TEMPLATE_PATH + FILE_NAME_PAGE);

		return templates;
	}

	/**
	 * 生成代码
	 */
	public static void generatorCode(Map<String, String> table, List<Map<String, String>> columns,
			ZipOutputStream zip) {
		// 配置信息
		Configuration config = getConfig();
		boolean hasBigDecimal = false;
		boolean hasDate = false;
		// 表信息
		TableEntity tableEntity = new TableEntity();
		tableEntity.setTableName(table.get("tableName"));
		tableEntity.setComments(table.get("tableComment"));
		// 表名转换成Java类名
		String className = tableToJava(tableEntity.getTableName(), config.getString("tablePrefix"));
		tableEntity.setClassName(className);
		tableEntity.setClassname(StringUtils.uncapitalize(className));

		// 列信息
		List<ColumnEntity> columsList = new ArrayList<>();
		for (Map<String, String> column : columns) {
			ColumnEntity columnEntity = new ColumnEntity();
			columnEntity.setColumnName(column.get("columnName"));
			columnEntity.setDataType(column.get("dataType"));
			columnEntity.setComments(column.get("columnComment"));
			columnEntity.setExtra(column.get("extra"));
			// 字段是否允许为空
			String isNullable = column.get("isNullable");
			if (isNullable != null) {
				columnEntity.setIsNullable("YES".equalsIgnoreCase(isNullable));
			} else {
				columnEntity.setIsNullable(true);
			}

			// 列名转换成Java属性名
			String attrName = columnToJava(columnEntity.getColumnName());
			columnEntity.setAttrName(attrName);
			columnEntity.setAttrname(StringUtils.uncapitalize(attrName));

			// 列的数据类型，转换成Java类型
			String attrType = config.getString(columnEntity.getDataType(), "unknowType");
			columnEntity.setAttrType(attrType);
			if (!hasBigDecimal && "BigDecimal".equals(attrType)) {
				hasBigDecimal = true;
			}
			if (!hasDate && "Date".equals(attrType)) {
				hasDate = true;
			}
			// 是否主键
			if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null) {
				tableEntity.setPk(columnEntity);
			}

			columsList.add(columnEntity);
		}
		tableEntity.setColumns(columsList);

		// 没主键，则第一个字段为主键
		if (tableEntity.getPk() == null) {
			tableEntity.setPk(tableEntity.getColumns().get(0));
		}

		// 设置velocity资源加载器
		Properties prop = new Properties();
		prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		Velocity.init(prop);
		String mainPath = config.getString("mainPath");
		mainPath = StringUtils.isBlank(mainPath) ? "io.renren" : mainPath;
		// 封装模板数据
		Map<String, Object> map = new HashMap<>();
		map.put("tableName", tableEntity.getTableName());
		map.put("comments", tableEntity.getComments());
		map.put("pk", tableEntity.getPk());
		map.put("className", tableEntity.getClassName());
		map.put("classname", tableEntity.getClassname());
		map.put("pathName", tableEntity.getClassname().toLowerCase());
		map.put("columns", tableEntity.getColumns());
		map.put("hasBigDecimal", hasBigDecimal);
		map.put("hasDate", hasDate);
		map.put("mainPath", mainPath);
		map.put(PACKAGE, config.getString(PACKAGE));
		map.put(MODULE_NAME, config.getString(MODULE_NAME));
		map.put("author", config.getString("author"));
		map.put("email", config.getString("email"));
		map.put("datetime", DateUtils.getTime());
		VelocityContext context = new VelocityContext(map);

		// 获取模板列表
		List<String> templates = getTemplates();
		for (String template : templates) {
			// 渲染模板
			try (StringWriter sw = new StringWriter()) {
				Template tpl = Velocity.getTemplate(template, "UTF-8");
				tpl.merge(context, sw);

				// 添加到zip
				zip.putNextEntry(new ZipEntry(getFileName(template, tableEntity.getClassName(),
						config.getString(PACKAGE), config.getString(MODULE_NAME))));
				IOUtils.write(sw.toString(), zip, StandardCharsets.UTF_8);
				zip.closeEntry();
			} catch (IOException e) {
				log.error("generatorCode-error", e);
			}
		}
	}

	/**
	 * 列名转换成Java属性名
	 */
	public static String columnToJava(String columnName) {
		return WordUtils.capitalizeFully(columnName, new char[] { '_' }).replace("_", "");
	}

	/**
	 * 表名转换成Java类名
	 */
	public static String tableToJava(String tableName, String tablePrefix) {
		if (StringUtils.isNotBlank(tablePrefix) && tableName.startsWith(tablePrefix)) {
			tableName = tableName.substring(tablePrefix.length());
		}
		return columnToJava(tableName);
	}

	/**
	 * 获取配置信息
	 */
	public static Configuration getConfig() {
		try {
			return new PropertiesConfiguration("generator.properties");
		} catch (ConfigurationException e) {
			throw new RuntimeException("获取配置文件失败，", e);
		}
	}

	/**
	 * 获取文件名
	 */
	public static String getFileName(String template, String className, String packageName, String moduleName) {
		String packagePath = "main" + File.separator + "java" + File.separator;
		if (StringUtils.isNotBlank(packageName)) {
			packagePath += packageName.replace(".", File.separator) + File.separator + moduleName + File.separator;
		}

		/**
		 * 持久化对象
		 */
		if (template.contains(FILE_NAME_MODEL_PO)) {
			return packagePath + "model" + File.separator + "po" + File.separator + className + "PO.java";
		}

		/**
		 * 数据传输对象
		 */
		if (template.contains(FILE_NAME_MODEL_DTO)) {
			return packagePath + "model" + File.separator + "dto" + File.separator + className + "DTO.java";
		}

		/**
		 * 详情数据对象
		 */
		if (template.contains(FILE_NAME_MODEL_VO)) {
			return packagePath + "model" + File.separator + "vo" +File.separator +"view"+ File.separator + className
					+ "VO.java";
		}
		
		/**
		 * 列表数据对象
		 */
		if (template.contains(FILE_NAME_MODEL_LIST_VO)) {
			return packagePath + "model" + File.separator + "vo" +File.separator +"list"+ File.separator + className
					+ "ListVO.java";
		}
		/**
		 * 表单提交对象
		 */
		if (template.contains(FILE_NAME_MODEL_FORM_VO)) {
			return packagePath + "model" + File.separator + "vo" + File.separator + "form" + File.separator + className
					+ "FormVO.java";
		}

		/**
		 * 列表查询条件对象
		 */
		if (template.contains(FILE_NAME_MODEL_QUERY_VO)) {
			return packagePath + "model" + File.separator + "vo" + File.separator + "query" + File.separator + className
					+ "QueryVO.java";
		}

		/**
		 * 对象关系转换映射
		 */
		if (template.contains(FILE_NAME_MODEL_CONVERT)) {
			return packagePath + "model" + File.separator + "convert" + File.separator + className + "Convert.java";
		}

		/**
		 * 数据库访问对象
		 */
		if (template.contains(FILE_NAME_MAPPER)) {
			return packagePath + "mapper" + File.separator + className + "Mapper.java";
		}

		/**
		 * 数据库访问对象XML
		 */
		if (template.contains(FILE_NAME_MAPPERXML)) {
			return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + className
					+ "Mapper.xml";
		}

		/**
		 * 服务接口
		 */
		if (template.contains(FILE_NAME_SERVICE)) {
			return packagePath + "service" + File.separator + className + "Service.java";
		}

		/**
		 * 服务接口实现
		 */
		if (template.contains(FILE_NAME_SERVICEIMPL)) {
			return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
		}

		/**
		 * 控制层
		 */
		if (template.contains(FILE_NAME_CONTROLLER)) {
			return packagePath + "controller" + File.separator + className + "Controller.java";
		}

		/**
		 * 前端layui
		 */
		if (template.contains(FILE_NAME_PAGE)) {
			return "main" + File.separator + "view" + File.separator + "pages" + File.separator + moduleName
					+ File.separator + "index.html";
		}

		return null;
	}
}
