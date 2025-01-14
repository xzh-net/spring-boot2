# Jenkins 2.464

Jenkins API总共有三种格式，分别为：JSON API、XML API、Python API。

> 以下使用的xml配置模版来自`http://172.17.17.231:8080/job/jobname/config.xml`，而不是`http://172.17.17.231:8080/job/jobname/api/xml`


1. 视图操作
2. 任务操作
3. 编译操作
4. 管理

## 常见问题

### Jenkins 远程触发 403 No valid crumb was included in the request

解决办法：

![](doc/assets/1.png)

Manage Jenkins -> Script Console，脚本命令行输入`hudson.security.csrf.GlobalCrumbIssuerConfiguration.DISABLE_CSRF_PROTECTION = true
`
![](doc/assets/2.png)

![](doc/assets/3.png)

## 编译构建

```bash
mvn clean compile
mvn clean package
```