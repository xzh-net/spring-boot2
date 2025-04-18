package net.xzh.parser.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户测试类：成员变量，注释，方法
 */
@Getter
@Setter
public class User implements Serializable {

	private static final long serialVersionUID = -3200103254689137288L;
	private List<String> items = new ArrayList<>();
	public static final int MAX_COUNT = 100;

	/**
	 * 主键
	 */
	private Long id; // 系统自动生成

	/**
	 * 用户名
	 */
	@NotEmpty(message = "用户名不能为空")
	private String username;

	/**
	 * 密码
	 */
	@NotEmpty(message = "密码不能为空")
	private String password;

	/**
	 * 租户id
	 */
	@NotNull(message = "租户id不能为空")
	private Long teamId;

	/**
	 * 添加成员
	 * 
	 * @param username
	 */
	@SuppressWarnings("rawtypes")
	public void add(String username) {
		items.add(username);
	}

	/**
	 * 查询所有成员
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<String> list() {
		for (int i = 0; i < 10; i++) {
			if (i % 2 == 0) {
				System.out.println(i);
			}
		}
		System.out.println(items.size());
		return items;
	}

	public void testLambda() {
		List<String> strings = Arrays.asList("apple", "banana", "cherry", "date");
		// 使用Lambda表达式过滤列表中的元素
		List<String> filtered = null;
		try {
			filtered = strings.stream().filter(s -> s.startsWith("a")) // 过滤以'a'开头的字符串
					.collect(Collectors.toList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		filtered.forEach(System.out::println); // 打印过滤后的结果
	}

	public <T> void genericMethod(T param) {

	}
}
