# jenkins

访问地址：http://127.0.0.1:8080/doc.html 

1. docker参数设置
	```conf
	vi /etc/docker/daemon.json
	{
	    "registry-mirrors":["https://docker.mirrors.ustc.edu.cn"],
	    "exec-opts":["native.cgroupdriver=systemd"],
	    "insecure-registries": ["172.17.17.148:88"],
	    "data-root": "/home/docker"
	}
	``` 
2. 镜像拉取 docker pull 172.17.17.148:88/ec_platform/spring-boot-jenkins:2.3.0.RELEASE
3. 运行镜像 docker run -dit -p 8080:8080 --name spring-boot-jenkins 172.17.17.148:88/ec_platform/spring-boot-jenkins:2.3.0.RELEASE

```bash
mvn clean compile
mvn clean package
```