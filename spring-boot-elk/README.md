# ELK日志

访问地址：http://127.0.0.1:8080/doc.html 

```bash
docker-compose -f docker-compose-env.yml up -d  
docker-compose -f docker-compose-env.yml down
```

1. 安装7.6.2三件套  chmod -R 777 /home/
2. 上传logstash.conf配置文件
3. 访问http://127.0.0.1:5601/ 设置索引

```bash
mvn clean compile
mvn clean package
```