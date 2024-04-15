# HBase 2.4.11

未整合knife4j，连接2.4.11测试正常，也可以连接docker版本harisekhon/hbase:2.1测试

```bash
mvn clean compile
mvn clean package
```

## windows设置

解压`hadoop-3.1.4_winutils.zip`，设置环境变量

```bash
HADOOP_HOME=D:\tools\hadoop-3.1.4
PATH=%HADOOP_HOME%\bin
```

