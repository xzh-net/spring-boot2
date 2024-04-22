# 基于 docker-maven-plugin 构建镜像并推送到Harbor

1. 安装Docker

   ```bash\
   vi /etc/docker/daemon.json
   {
       "registry-mirrors":["https://docker.mirrors.ustc.edu.cn"],
       "insecure-registries": ["192.168.2.100:88"],
       "exec-opts":["native.cgroupdriver=systemd"],
       "data-root": "/data/docker"
   }
   
   vim /usr/lib/systemd/system/docker.service
   # 添加配置文件内容，-H之前是默认参数，追加 -H tcp://0.0.0.0:2375 -H unix:///var/run/docker.sock
   ExecStart=/usr/bin/dockerd  -H tcp://0.0.0.0:2375 -H unix://var/run/docker.sock
   ```
   ```bash
   docker login -u admin -p Harbor12345 http://127.0.0.1:88
   ```

2. 安装Harbor新建项目

   ![](doc/assets/harbor.png)

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
   mvn verify
   ```

5. 镜像拉取 

   ```bash
   docker pull 192.168.2.100:88/ec_platform/spring-boot-harbor:1.0
   ```

6. 运行镜像

   ```bash
    docker run -dit -p 8080:8080 --name spring-boot-harbor 192.168.2.100:88/ec_platform/spring-boot-harbor:1.0
   ```
   
7. 访问地址
   
   - http://192.168.2.200:8080/doc.html 
   - http://192.168.2.200:8080/login