# ELK日志

1. 访问地址：http://127.0.0.1:8080/doc.html 

```bash
mvn clean compile
mvn clean package
```

2. 安装elk7.6.2

```bash
mkdir /data/elasticsearch/{plugins,data} -p
mkdir /data/logstash
chmod -R 777 /data
docker-compose -f docker-compose-env.yml up -d  
docker-compose -f docker-compose-env.yml down
```

3. 上传logstash.conf配置文件

4. kibana设置索引：http://127.0.0.1:5601/ 

5. 日志默认路径
	- C:\Users\登录用户\AppData\Local\Temp\
	- /tmp
