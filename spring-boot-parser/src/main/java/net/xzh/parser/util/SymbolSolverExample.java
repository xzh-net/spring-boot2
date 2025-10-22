package net.xzh.parser.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

import org.springframework.context.annotation.ComponentScan;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ThrowStmt;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.printer.PrettyPrinter;
import com.github.javaparser.resolution.TypeSolver;
import com.github.javaparser.resolution.declarations.ResolvedFieldDeclaration;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;


/**
 * javaparser操作源码生成和解析工具
 * 
 * @author CR7
 *
 */
public class SymbolSolverExample {

	public static void main(String[] args) throws FileNotFoundException {
		// 配置类型解析器
		TypeSolver typeSolver = new CombinedTypeSolver(new ReflectionTypeSolver(), // 解析JDK内置类
				new JavaParserTypeSolver(Paths.get("src/main/java"))); // 解析项目中的类
		// 配置符号解析器
		JavaSymbolSolver symbolSolver = new JavaSymbolSolver(typeSolver);
		StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);

		// 1. 遍历类的声明
//		classDeclaration();
		// 2. 遍历所有成员变量
//		memberVar();
		// 3. 遍历成员方法
//		methodDeclaration();
		// 4. 解析所有方法调用(成员+其他)
//		methodCall();
		// 5. 创建类
//		createClass();
		// 6. 处理导入语句
//		importClass();
		// 7. 遍历所有注释
//		getJavaDoc();
		// 8. 解析泛型类型
//		chuliFanxing();
		// 9. 访问者模式遍历 AST
//		getAST();
		// 10. 解析Lambda 表达式
		getLambda();
		// 11. 解析异常处理
//		getException();

	}
	/**
	 * 解析异常
	 * @throws FileNotFoundException
	 */
	private static void getException() throws FileNotFoundException {
		// 解析Java文件
		CompilationUnit cu = StaticJavaParser.parse(new File("src\\main\\java\\net\\xzh\\parser\\util\\User.java"));
		cu.accept(new ExceptionVisitor(), null);
		
	}
	// 自定义访问者类
    private static class ExceptionVisitor extends VoidVisitorAdapter<Void> {
        // 遍历 try-catch 语句
        @Override
        public void visit(TryStmt tryStmt, Void arg) {
            System.out.println("\n发现 try 语句:");
            System.out.println("位置: " + tryStmt.getRange().orElse(null));
            super.visit(tryStmt, arg); // 递归遍历子节点（如 catch 块）
        }

        // 遍历 catch 子句
        @Override
        public void visit(CatchClause catchClause, Void arg) {
            System.out.println("\n发现 catch 块:");
            System.out.println("捕获异常类型: " + catchClause.getParameter().getType());
            System.out.println("异常变量名: " + catchClause.getParameter().getName());
            super.visit(catchClause, arg); // 遍历 catch 块内的语句（如 throw）
        }

        // 遍历 throw 语句
        @Override
        public void visit(ThrowStmt throwStmt, Void arg) {
            System.out.println("\n发现 throw 语句:");
            System.out.println("抛出对象: " + throwStmt.getExpression());
            System.out.println("位置: " + throwStmt.getRange().orElse(null));
            super.visit(throwStmt, arg);
        }

        // 遍历方法声明中的 throws 子句
        @Override
        public void visit(MethodDeclaration methodDecl, Void arg) {
            if (!methodDecl.getThrownExceptions().isEmpty()) {
                System.out.println("\n发现 throws 声明:");
                System.out.println("方法名: " + methodDecl.getName());
                System.out.println("抛出异常列表: " + methodDecl.getThrownExceptions());
            }
            super.visit(methodDecl, arg);
        }
    }

	/**
	 * 解析Lambda
	 * @throws FileNotFoundException
	 */
	private static void getLambda() throws FileNotFoundException {
		// 解析Java文件
		CompilationUnit cu = StaticJavaParser.parse(new File("src\\main\\java\\net\\xzh\\parser\\util\\User.java"));
		cu.accept(new LambdaVisitor(), null);
	}
	
	// 自定义访问者类
    private static class LambdaVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(LambdaExpr lambdaExpr, Void arg) {
            System.out.println("\n发现 Lambda 表达式:");
            
            // 打印 Lambda 参数
            System.out.println("参数: " + lambdaExpr.getParameters());

            // 打印 Lambda 体（可能是表达式或语句块）
            System.out.println("体: " + lambdaExpr.getBody());

            // 打印 Lambda 的位置（行号）
            System.out.println("位置: " + lambdaExpr.getRange().orElse(null));

            super.visit(lambdaExpr, arg); // 继续遍历子节点
        }
    }

	/**
	 * 访问者模式遍历 AST
	 * @throws FileNotFoundException 
	 */
	private static void getAST() throws FileNotFoundException {
		// 解析Java文件
		CompilationUnit cu = StaticJavaParser.parse(new File("src\\main\\java\\net\\xzh\\parser\\util\\User.java"));
		cu.accept(new IfForVisitor(), null);
	}
	
	// 自定义访问者类
    private static class IfForVisitor extends VoidVisitorAdapter<Void> {
        // 遍历 if 语句
        @Override
        public void visit(IfStmt ifStmt, Void arg) {
            System.out.println("发现 if 语句:");
            System.out.println("条件: " + ifStmt.getCondition());
            System.out.println("位置: " + ifStmt.getRange().orElse(null));
            super.visit(ifStmt, arg); // 继续遍历子节点（例如嵌套的语句）
        }

        // 遍历 for 语句
        @Override
        public void visit(ForStmt forStmt, Void arg) {
            System.out.println("\n发现 for 循环:");
            System.out.println("初始化: " + forStmt.getInitialization());
            System.out.println("条件: " + forStmt.getCompare());
            System.out.println("迭代: " + forStmt.getUpdate());
            super.visit(forStmt, arg); // 继续遍历子节点（例如循环体中的 if 语句）
        }
        
        // 遍历 for 语句
        @Override
        public void visit(ForEachStmt forEachStmt, Void arg) {
            System.out.println("发现 for-each 循环:");
            System.out.println("迭代变量: " + forEachStmt.getVariable());
            System.out.println("集合: " + forEachStmt.getIterable());
            super.visit(forEachStmt, arg);
        }
    }

	/**
	 * 处理泛型
	 * @throws FileNotFoundException
	 */
	private static void chuliFanxing() throws FileNotFoundException {
		// 解析Java文件
		CompilationUnit cu = StaticJavaParser.parse(new File("src\\main\\java\\net\\xzh\\parser\\util\\User.java"));

		cu.findAll(MethodDeclaration.class).forEach(method -> {
		    method.getTypeParameters().forEach(tp -> {
		        System.out.println("泛型参数: " + tp.getName()+" 类型边界: " + tp.getTypeBound());
		    });
		});
		
	}

	/**
	 * 获取JavaDoc
	 * 
	 * @throws FileNotFoundException
	 */
	private static void getJavaDoc() throws FileNotFoundException {
		// 解析Java文件
		CompilationUnit cu = StaticJavaParser.parse(new File("src\\main\\java\\net\\xzh\\parser\\util\\User.java"));

		// 遍历所有类-声明注释
		cu.findAll(ClassOrInterfaceDeclaration.class).forEach(cls -> {
			cls.getJavadoc().ifPresent(javadoc -> {
				System.out.println("类注释: " + javadoc.getDescription());
			});
		});

		// 遍历所有字段声明注释
		cu.findAll(FieldDeclaration.class).forEach(field -> {
			// 获取字段的 Javadoc 注释
			field.getJavadocComment().ifPresent(javadoc -> {
				String comment = javadoc.parse().toText();
				// 4. 获取变量名称（处理多个变量声明，如 int a, b;）
				field.getVariables().forEach(variable -> {
					String varName = variable.getNameAsString();
					System.out.println("变量: " + varName + " 注释: " + comment.trim());
				});
			});

			// 可选：获取行内注释（非标准Javadoc注释不建议使用
			field.getComment().ifPresent(comment -> {
				if (!comment.isJavadocComment()) {
//					System.out.println("变量行内注释: " + comment.getContent().trim());
				}
			});
		});
		
		System.out.println("===========================================");
		
		// 遍历所有方法声明注释
		cu.findAll(MethodDeclaration.class).forEach(method -> {
            // 3. 获取方法的 Javadoc 注释
            method.getJavadocComment().ifPresent(javadoc -> {
                String comment = javadoc.parse().toText();
                System.out.println("方法名: " + method.getName()+" 注释: " + comment.trim());
                // 可选：提取 Javadoc 中的 @param、@return 等标签
                javadoc.parse().getBlockTags().forEach(tag -> {
                    System.out.println("方法返回参数: " + tag.getTagName() + " - " + tag.getName());
                });
            });

            // 4. 获取行内注释（非 Javadoc）
            method.getComment().ifPresent(comment -> {
                if (!comment.isJavadocComment()) {
                    System.out.println("方法行内注释: " + comment.getContent().trim());
                }
            });
        });
	}

	/**
	 * 处理导入语句
	 * 
	 * @throws FileNotFoundException
	 */
	private static void importClass() throws FileNotFoundException {
		// 解析Java文件
		CompilationUnit cu = StaticJavaParser.parse(new File("src\\main\\java\\net\\xzh\\parser\\util\\User.java"));
		// 获取所有导入
		cu.getImports().forEach(imp -> {
			System.out.println("导入: " + imp.getName());
		});
	}

	/**
	 * 创建类 添加字段 添加方法 处理导入
	 */
	private static void createClass() {
		// 1. 创建 CompilationUnit 并设置包名
		CompilationUnit cu = new CompilationUnit();
		cu.setPackageDeclaration("net.xzh.javaparser");

		// 2. 创建类 + 注解
		ClassOrInterfaceDeclaration clazz = cu.addClass("IndexController");
		clazz.setPublic(true);
		clazz.setJavadocComment("这是一个通过javaparser自动生成的源代码");

		// 2.1 无参类注解的3种方式
		clazz.addAnnotation(ComponentScan.class); // 类形式
		clazz.addAnnotation("Slf4j"); // 字符串式
		clazz.addAnnotation(new MarkerAnnotationExpr("RestController")); // 声明式

		// 2.2 有参类注解的2种方式
		// 字符串形式，注意：需要自己控制特殊符号
		NormalAnnotationExpr swagger = new NormalAnnotationExpr();
		swagger.setName("Api");
		swagger.addPair("tags ", "\"测试工具类\"");
		clazz.addAnnotation(swagger);
		// 表达式形式
		NormalAnnotationExpr requestMapping = new NormalAnnotationExpr();
		requestMapping.setName("RequestMapping");
		requestMapping.addPair("value", new StringLiteralExpr("/index"));
		clazz.addAnnotation(requestMapping);

		// 3. 添加字段 + 注解
		clazz.addField(String.class, "userName", Modifier.Keyword.PRIVATE);

		// 3.1 创建普通字段
		Type type = StaticJavaParser.parseType("String");
		VariableDeclarator variable = new VariableDeclarator(type, "server_port");
		FieldDeclaration field = new FieldDeclaration();
		field.setModifiers(Modifier.Keyword.PRIVATE);
		field.addVariable(variable);
		// 普通字段设置注解
		AnnotationExpr markerAnnotation = new MarkerAnnotationExpr("NotEmpty");
		field.addAnnotation(markerAnnotation);

		// 3.2 创建带有参数的注解，例如@SuppressWarnings("unchecked")
		NormalAnnotationExpr normalAnnotation = new NormalAnnotationExpr();
		normalAnnotation.setName("value");
		normalAnnotation.addPair("value", new StringLiteralExpr("${server.port}"));
		field.addAnnotation(normalAnnotation);
		field.setJavadocComment("服务应用端口");
		clazz.addMember(field);

		// 3.3 创建带初始值的字段
		Type type1 = StaticJavaParser.parseType("String");
		Expression initializer1 = StaticJavaParser.parseExpression("\"default\"");
		VariableDeclarator variable1 = new VariableDeclarator(type1, "nickName", initializer1);
		FieldDeclaration field1 = new FieldDeclaration();
		field1.setModifiers(Modifier.Keyword.PRIVATE);
		field1.addVariable(variable1);
		// 为带默认值的字段设置注解
		NormalAnnotationExpr normalAnnotation1 = new NormalAnnotationExpr();
		normalAnnotation1.setName("SuppressWarnings");
		normalAnnotation1.addPair("value", new StringLiteralExpr("unchecked"));
		field1.addAnnotation(normalAnnotation1);
		field1.setJavadocComment("用户昵称");
		clazz.addMember(field1);

		// 3.4 通过字符串解析创建字段（支持完整语法）
		FieldDeclaration field2 = (FieldDeclaration) StaticJavaParser
				.parseBodyDeclaration("private RestTemplate restTemplate;");
		AnnotationExpr autoAnnotation = new MarkerAnnotationExpr("Autowired");
		field2.addAnnotation(autoAnnotation);// 两种方式都可以
//		field2.addAnnotation(Autowired.class);//类型的方式前提必须当前项目下存在
		clazz.addMember(field2);

		// 4. 添加方法 + 注解
		// 4.1 普通方法
		clazz.addMethod("getUserName", Modifier.Keyword.PRIVATE).setType("String").getBody()
				.ifPresent(body -> body.addStatement("return this.userName;"));

		// 4.2 添加方法并添加注解
		MethodDeclaration method = clazz.addMethod("list").setModifiers(Modifier.Keyword.PUBLIC).setType("String");
		method.getBody().ifPresent(body -> body.addStatement("return System.currentTimeMillis() + \"\";"));

		NormalAnnotationExpr methodMapping = new NormalAnnotationExpr();
		methodMapping.setName("RequestMapping");
		methodMapping.addPair("value", new StringLiteralExpr("/list"));
		method.addAnnotation(methodMapping);
		method.setJavadocComment("执行计算操作\n@return 所有结果\n ");

		// 4.3 创建泛型方法：public <T> void genericMethod(T param) { ... }
        MethodDeclaration method1 = new MethodDeclaration();
        method1.setName("genericMethod");
        method1.setModifiers(Modifier.Keyword.PUBLIC);
        // 添加类型参数 <T>
        method1.addTypeParameter(new TypeParameter("T"));
        // 设置返回类型为 void
        method1.setType(void.class);
        // 添加参数 T param
        Parameter param = new Parameter();
        param.setType(new ClassOrInterfaceType("T")); // 参数类型为泛型 T
        param.setName("param");
        method1.addParameter(param);
        // 将方法添加到类中
        clazz.addMember(method1);
		
		
		// 5. 添加新导入
		cu.addImport("org.springframework.web.client.RestTemplate");
		cu.addImport("org.slf4j.Logger");
		cu.addImport("org.slf4j.LoggerFactory");

		// 6. 格式化代码
		PrettyPrinter printer = new PrettyPrinter();
		String code = printer.print(cu);

		System.out.println(cu); // 打印生成的代码

	}

	/**
	 * 解析方法调用
	 * 
	 * @throws FileNotFoundException
	 */
	private static void methodCall() throws FileNotFoundException {
		// 解析Java文件
		CompilationUnit cu = StaticJavaParser.parse(new File("src\\main\\java\\net\\xzh\\parser\\util\\User.java"));
		// 解析方法调用
		cu.findAll(MethodCallExpr.class).forEach(methodCall -> {
			try {
				System.out.println("方法调用: " + methodCall);
				System.out.println("解析类型: " + methodCall.resolve().getReturnType());
				System.out.println("声明类: " + methodCall.resolve().declaringType().getQualifiedName());
			} catch (Exception e) {
				System.out.println("无法解析方法调用: " + methodCall);
			}
			System.out.println("-----");
		});

	}

	/**
	 * 遍历所有成员变量
	 * 
	 * @throws FileNotFoundException
	 */
	private static void memberVar() throws FileNotFoundException {
		// 解析Java文件
		CompilationUnit cu = StaticJavaParser.parse(new File("src\\main\\java\\net\\xzh\\parser\\util\\User.java"));

		// 遍历所有成员变量
		cu.findAll(FieldDeclaration.class).forEach(field -> {
			// 获取变量名
			field.getVariables().forEach(variable -> {
				System.out.println("变量名: " + variable.getNameAsString());

				// 获取类型（包含泛型信息）
				String typeName = field.getElementType().asString();
				System.out.println("声明类型: " + typeName);

				// 解析完整类型（使用符号解析器）
				try {
					ResolvedFieldDeclaration resolvedField = (ResolvedFieldDeclaration) variable.resolve();
					System.out.println("解析类型: " + resolvedField.getType());
				} catch (Exception e) {
					System.out.println("无法解析类型: " + typeName);
				}

				// 获取初始化表达式
				variable.getInitializer().ifPresent(init -> {
					System.out.println("初始化表达式: " + init);
				});

				// 获取修饰符
				System.out.println("修饰符: " + field.getModifiers());
				System.out.println("-----");
			});
		});
//		
	}

	/**
	 * 遍历所有成员方法声明
	 * 
	 * @throws FileNotFoundException
	 */
	private static void methodDeclaration() throws FileNotFoundException {
		// 解析Java文件
		CompilationUnit cu = StaticJavaParser.parse(new File("src\\main\\java\\net\\xzh\\parser\\util\\User.java"));
		// 获取所有方法声明
		cu.findAll(MethodDeclaration.class).forEach(method -> {
			System.out.println("方法名: " + method.getName());
			System.out.println("返回类型: " + method.getType());
			System.out.println("参数列表: " + method.getParameters());
			System.out.println("注解: " + method.getAnnotations());
			System.out.println("-----");
		});
	}

	/**
	 * 遍历类的声明
	 * 
	 * @throws FileNotFoundException
	 */
	private static void classDeclaration() throws FileNotFoundException {
		// 解析Java文件
		CompilationUnit cu = StaticJavaParser.parse(new File("src\\main\\java\\net\\xzh\\parser\\util\\User.java"));

		cu.findAll(ClassOrInterfaceDeclaration.class).forEach(cls -> {
			System.out.println("类名: " + cls.getName());
			System.out.println("是否是接口: " + cls.isInterface());
			System.out.println("修饰符: " + cls.getModifiers());
		});

	}
}
