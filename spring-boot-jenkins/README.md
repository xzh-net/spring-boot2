# docker-maven-plugin 构建镜像

访问地址：http://127.0.0.1:8080/doc.html 

1. Docker配置

   ```bash\
   vi /etc/docker/daemon.json
   
   {
       "registry-mirrors":["https://docker.mirrors.ustc.edu.cn"],
       "insecure-registries": ["192.168.2.100:88"],
       "exec-opts":["native.cgroupdriver=systemd"],
       "data-root": "/data/docker"
   }
   ```
   ```bash
   docker login 192.168.2.100:88
   ```

2. 安装Harbor

   略

3. Maven配置

   ```conf
   <servers>
       <server>
   		<id>harbor88</id>  
   		<username>admin</username>  
   		<password>Harbor12345</password> 
   		<configuration>  
   			<email>xzh@163.com</email> 
   		</configuration>
       </server>
   </servers>
   ```

4. 推送镜像

   ```bash
   mvn clean package
   ```

5. 镜像拉取 

   ```bash
   docker pull 172.17.17.148:88/ec_platform/spring-boot-jenkins:2.3.0.RELEASE
   ```

6. 运行镜像

   ```bash
    docker run -dit -p 8080:8080 --name spring-boot-jenkins 172.17.17.148:88/ec_platform/spring-boot-jenkins:2.3.0.RELEASE
   ```

   