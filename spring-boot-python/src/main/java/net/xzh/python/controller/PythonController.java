package net.xzh.python.controller;

import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/python")
public class PythonController {

	PythonInterpreter interpreter = new PythonInterpreter();
	
	/**
	 * 用于执行‌任意代码块‌（如循环、条件判断、函数定义等），不返回结果
	 * @return
	 */
	@PostMapping("/execute")
	public String executeScript() {
		interpreter.exec("a='hello world'; ");
		interpreter.exec("print a;");
		return "执行成功";
	}
	
	/**
	 * 必须返回表达式的结果。若字符串为空或非表达式，会抛出语法错误‌
	 * @return
	 */
	@PostMapping("/eval")
	public String evaluateExpression() {
		interpreter.execfile("D:\\xzh\\spring-boot2-demo\\spring-boot-python\\src\\main\\resources\\py\\add.py");
		interpreter.set("a", 122);
		interpreter.set("b", 23);
		PyObject eval = interpreter.eval("add(a,b)");
		return "执行成功，结果：" +eval.toString();
	}

	@PostMapping("/call")
	public String callFunction() {
		interpreter.execfile("D:\\xzh\\spring-boot2-demo\\spring-boot-python\\src\\main\\resources\\py\\add.py");
		// 第一个参数为期望获得的函数（变量）的名字，第二个参数为期望返回的对象类型
		PyFunction pyFunction = interpreter.get("add", PyFunction.class);
		int a = 5, b = 10;
		// 调用函数，如果函数需要参数，在Java中必须先将参数转化为对应的“Python类型”
		PyObject pyobj = pyFunction.__call__(new PyInteger(a), new PyInteger(b));
		return "执行成功，结果：" + pyobj;
	}
}