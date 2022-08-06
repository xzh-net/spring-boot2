# pdi-ce-7.1.0.0-12

```bash
mvn clean compile
mvn clean package
```

访问地址：http://127.0.0.1:8080/doc.html，所有任务实例和表结构见：https://www.xuzhihao.net/#/java/tools/etl


## 1. Kettle无法下载jar修改maven配置

```conf
<profile>
      <id>pentaho</id>
      <activation>
        <activeByDefault>true</activeByDefault>
      </activation>
      <repositories>
        <repository>
          <id>pentaho-public</id>
          <name>Pentaho Public</name>
          <url>http://nexus.pentaho.org/content/groups/omni</url>
          <releases>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
          </releases>
          <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
          </snapshots>
        </repository>
      </repositories>
      <pluginRepositories>
        <pluginRepository>
          <id>pentaho-public</id>
          <name>Pentaho Public</name>
          <url>http://nexus.pentaho.org/content/groups/omni</url>
          <releases>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
          </releases>
          <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
          </snapshots>
        </pluginRepository>
      </pluginRepositories>
    </profile>
</profiles>
```

## 2. 无法加载oracle驱动

```bash
mvn install:install-file -Dfile=D:\ojdbc6.jar -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=10.2.0.5.0 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true  
```

```conf
<dependency>
	<groupId>com.oracle</groupId>
	<artifactId>ojdbc6</artifactId>
	<version>10.2.0.5.0</version>
</dependency>
```

## 3. 交互式调用

### 3.1 运行ktr文件

### 3.2 运行kjb文件

## 4. 编程式调用

