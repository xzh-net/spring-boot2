# FELK日志

1. 访问地址：http://127.0.0.1:8080/doc.html 

```bash
mvn clean compile
mvn clean package
```

2. 安装filebeat


3. 修改配置

```bash
cd /home/elastic/filebeat-7.6.2
vi filebeat.yml

# 编辑
####################
filebeat.inputs:
- type: log
  enabled: true
  paths:
    - /home/elastic/logs/application/*/*.log
  exclude_lines: ['\sDEBUG\s\d']
  exclude_files: ['sc-admin.*.log$']
  fields:
    docType: sys-log
    project: microservices-platform
  multiline:
    pattern: '^\[\S+:\S+:\d{2,}] '
    negate: true
    match: after
- type: log
  enabled: true
  paths:
    - /home/elastic/logs/point/*.log
  fields:
    docType: point-log
    project: microservices-platform
####################
hosts: ["172.17.17.194:5044"]
bulk_max_size: 2048
```

4. 启动服务

```bash
cd /home/elastic/filebeat-7.6.2
./filebeat -c filebeat.yml -e
```
