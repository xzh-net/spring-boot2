package net.xzh.groovy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import net.xzh.groovy.model.User;

@Service
public class UserService {

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

    // 静态脚本，手动New对象
    public static void main(String[] args) {
        Binding groovyBinding = new Binding();
        GroovyShell groovyShell = new GroovyShell(groovyBinding);
        
        String scriptContent = "import net.xzh.groovy.service.UserService; \n"
        + "def query = new UserService().testQuery(100L); \n"
        + "query";
        Script script = groovyShell.parse(scriptContent);
        System.out.println(script.run());
    }
	
    // 静态脚本，从环境变量中获取
    public static void main2(String[] args) {
		Binding groovyBinding = new Binding();
		groovyBinding.setVariable("userService", new UserService());
		GroovyShell groovyShell = new GroovyShell(groovyBinding);

		String scriptContent = "def query = userService.testQuery(200L); \n"
				+ "query";
		Script script = groovyShell.parse(scriptContent);
		System.out.println(script.run());
		
	}
}
