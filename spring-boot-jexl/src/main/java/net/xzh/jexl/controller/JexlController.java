package net.xzh.jexl.controller;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.JexlFeatures;
import org.apache.commons.jexl3.MapContext;
import org.apache.commons.jexl3.introspection.JexlPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JexlController {

	@GetMapping("/index")
	public String index() {
		return System.currentTimeMillis() + "";
	}

	public static void main(String[] args) {
		JexlFeatures features = new JexlFeatures().loops(true) // 是否允许使用循环语句，如：for、while
				.sideEffectGlobal(true) // 是否允许修改全局变量
				.sideEffect(true); // 是否允许修改变量
		JexlEngine engine = new JexlBuilder().features(features).strict(true).permissions(JexlPermissions.RESTRICTED)
				.create();

		JexlContext context = new MapContext();
		// 设置测试变量
		context.set("temperature", 25);
		context.set("isRaining", true);
		// 定义包含if-else的脚本
		String script = "temperature > 30 ? '炎热' : " + "(temperature > 20 && isRaining ? '温暖有雨' : "
				+ "(temperature > 20 ? '温暖' : '凉爽'))";

		try {
			JexlExpression expr = engine.createExpression(script);
			String result = (String) expr.evaluate(context);
			System.out.println("天气状态: " + result); // 输出: 温暖
		} catch (JexlException e) {
			e.printStackTrace();
		}

	}
}
