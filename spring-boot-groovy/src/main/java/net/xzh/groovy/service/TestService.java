package net.xzh.groovy.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import net.xzh.groovy.model.User;

@Service
public class TestService {

	public  List<User> list = new ArrayList<>();
	
    public String testQuery(long id){
        return "Test query success, id is " + id;
    }
    
    public List<User> getUser() {
        for (int i = 1; i <= 10; i++) {
            User user = new User();
            user.setId(i);
            user.setName("用户名-" + i);
            user.setAge(20 + i);
            user.setAddress("地址-" + i);
            list.add(user);
        }
        return list;
    }
    public void sendDataS(List<User> objs) {
        System.out.println("数据："+ objs);
    }

	public static void main(String[] args) {
		Binding groovyBinding = new Binding();
		groovyBinding.setVariable("testService", new TestService());
		GroovyShell groovyShell = new GroovyShell(groovyBinding);

		// 静态脚本
		String scriptContent = "import net.xzh.groovy.service.TestService; \n"
				+ "def query = new TestService().testQuery(100L); \n"
				+ "query";

		// 动态脚本，从容器中获取类
//		String scriptContent = "def query = testService.testQuery(200L); \n"
//				+ "query";

		Script script = groovyShell.parse(scriptContent);
		System.out.println(script.run());
		
	}
	
	
}
