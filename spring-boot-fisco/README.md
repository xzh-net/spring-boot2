# Spring Boot基于java-sdk整合Fisco Bcos区块链

## 1. 环境说明

Fisco Bcos使用WeBASE一键搭建，参考地址：https://webasedoc.readthedocs.io/zh-cn/lab/docs/WeBASE/install.html

- Fisco Bcos 2.9.1
- WeBASE 1.5.5
- Console 2.9.2

![](doc/assets/1.png)

客户端版本

```pom
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    <java.version>1.8</java.version>
    <skipTests>true</skipTests>
</properties>
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.4.2</version>
    <relativePath />
</parent>
<dependencies>
    <dependency>
        <groupId>org.fisco-bcos.java-sdk</groupId>
        <artifactId>fisco-bcos-java-sdk</artifactId>
        <version>2.7.2</version>
    </dependency>
</dependencies>
```



## 2. 配置环境

先把Fisco Bcos的密钥复制到resource下的conf文件

Fisco Bcos密钥文件地址为: `nodes/{ip}/sdk/*`下的所有文件，复制完成后就可以进行下一步


## 3. 调用合约

### 3.1 编写合约

### 3.2 部署合约

### 3.3 合约编译成java文件

### 3.4 使用合约代码



访问地址：http://127.0.0.1:8080/doc.html 

```bash
mvn clean compile
mvn clean package
```