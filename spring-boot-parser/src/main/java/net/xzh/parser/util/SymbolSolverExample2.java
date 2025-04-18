package net.xzh.parser.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;

/**
 * 1. 批量替换文件中的@NotNull和@NotEmpty中的message信息为规则key
 * 2. 生成i18n资源文件。
 * @author CR7
 *
 */
public class SymbolSolverExample2 {
	
	public static void main(String[] args) throws IOException {
		List<Path> paths = fileLists(
				"C:\\Users\\CR7\\Desktop\\cicd-center\\src\\main\\java\\com\\central\\cicd\\model\\form");
		for (Path dir : paths) {
			System.out.println(dir);
			// 查询成员变量
//			modifyClassAndMethods(dir);
			// 查询变量注解信息
			findFieldsWithAnnotation(dir, "NotNull", "message");
			findFieldsWithAnnotation(dir, "NotEmpty", "message");
		}
	}

	public static List<Path> fileLists(String dir) throws IOException {
		try (Stream<Path> paths = Files.walk(Paths.get(dir))) {
			return paths.filter(Files::isRegularFile).filter(p -> p.toString().endsWith(".java"))
					.collect(Collectors.toList());
		}
	}

	private static void findFieldsWithAnnotation(Path filePath, String targetAnnotation, String key)
			throws IOException {
		
		CompilationUnit cu = StaticJavaParser.parse(filePath);
		cu.findAll(ClassOrInterfaceDeclaration.class).forEach(classDecl -> {
			System.out.println("Class: " + classDecl.getName());
			classDecl.getMembers().stream().filter(member -> member instanceof FieldDeclaration)
					.map(member -> (FieldDeclaration) member).forEach(field -> {
						// 检查字段是否包含目标注解
						boolean hasAnnotation = field.getAnnotations().stream().anyMatch(annotation ->
						// 匹配简单名称（如 MyAnnotation）或全限定名（如 com.example.MyAnnotation）
						annotation.getNameAsString().equals(targetAnnotation)
								|| annotation.getName().getIdentifier().equals(targetAnnotation));
						if (hasAnnotation) {
							String fieldName = field.getVariables().get(0).getNameAsString();
							String type = field.getElementType().asString();
//							System.out.printf("Field [%s %s] has annotation @%s%n", type, fieldName, targetAnnotation);
							field.getAnnotations().forEach(annotation -> {
								if (annotation.getNameAsString().equals(targetAnnotation)) {
									// 获取注解属性（示例）
									annotation.ifNormalAnnotationExpr(normalAnnotation -> {
//										normalAnnotation.getPairs().forEach(pair -> {
//											System.out.println("Attribute: " + pair.getName() + " = " + pair.getValue());
//										});
										NodeList<MemberValuePair> pairs = normalAnnotation.getPairs();
										String content = FileUtil.readString(filePath.toFile(),
												CharsetUtil.charset("UTF-8"));	
										for (MemberValuePair pair : pairs) {
											if (key.equals(pair.getName().toString())) {
//												System.out.println("Attribute: " + pair.getName() + " = " + pair.getValue());
												String properties_key=classDecl.getName()+"."+fieldName+"=";
												String properties_value=pair.getValue().toString().replaceAll("\"", "");
												
												FileUtil.appendString(properties_key+"en_US_"+properties_value+"\r\n", "d:\\messages_en_US.properties", "UTF-8");
												FileUtil.appendString(properties_key+properties_value+"\r\n", "d:\\messages_zn_CH.properties", "UTF-8");
												
												content = content.replaceAll(pair.getValue().toString(),
														"\"" + classDecl.getName() + "." + fieldName + "\"");
												
											}
										}
										try {
											Files.write(filePath, content.getBytes());
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									});
								}
							});

						}
					});
		});
	}

	public static void modifyClassAndMethods(Path filePath) throws IOException {
		// 解析Java文件
		CompilationUnit cu = StaticJavaParser.parse(filePath);
		cu.findAll(ClassOrInterfaceDeclaration.class).forEach(classDecl -> {
			System.out.println("Class: " + classDecl.getName());
			// 遍历类的成员，筛选出字段声明
			classDecl.getMembers().forEach(member -> {
				if (member instanceof FieldDeclaration) {
					FieldDeclaration field = (FieldDeclaration) member;
					// 获取字段修饰符（如 public、private）
					String modifiers = field.getModifiers().toString();
					// 获取字段类型（如 int、String）
					String type = field.getElementType().asString();
					// 遍历字段变量（可能包含多个变量，如 int a, b;）
					field.getVariables().forEach(variable -> {
						String name = variable.getNameAsString();
						System.out.printf("Field: %s %s %s%n", modifiers, type, name);
					});
				}
			});
		});

		// 修改类名（例如在原类名后添加 "New"）
		cu.getClassByName("BuildExecuteForm").ifPresent(c -> c.setName("BuildExecuteForm_New"));

		// 修改方法名：使用Visitor模式
		cu.accept(new VoidVisitorAdapter<Void>() {
			@Override
			public void visit(MethodDeclaration md, Void arg) {
				super.visit(md, arg);
				if (md.getNameAsString().equals("oldMethod")) {
					md.setName("newMethod");
				}
			}
		}, null);
		// 保存修改后的代码
//		Files.write(filePath, cu.toString().getBytes());

	}
}
