# Groovy脚本动态编程

Groovy是一种基于Java平台的动态语言，支持静态类型和静态编译。广泛应用于脚本编写、领域特定语言（DSL）设计、元编程和函数式编程等领域。


脚本1:手动创建对象调用（main函数）

```
import net.xzh.groovy.service.TestService;
def query = new TestService().testQuery(101L); 
query
```

脚本2:依赖注入变量调用（main函数或动态调用）

```
def query = testService.testQuery(2L); 
query
```

脚本3：使用上下文对象调用（动态调用）

```java
import net.xzh.groovy.model.User;
import net.xzh.groovy.service.TestService;
import java.util.List;

//todo
//TestService testService = SpringContextUtil.getBean(TestService.class);
 
def userList = testService.list;
 
//方法执行
if(userList == null || userList.size() == 0){
	testService.getUser();
	userList =testService.list;
}
 
for (def i = 0; i < userList.size(); i++) {
       	//根据年龄配置随机数
	if(userList.get(i).getAge() < 25){
		userList.get(i).setRandom(new Random().nextInt(10));
	}else{
	   userList.get(i).setRandom(new Random().nextInt(20)+new Random().nextInt(10));
	}
}
testService.sendDataS(userList);
```