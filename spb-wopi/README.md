# SpringBoot 集成 Collabora Online 实现在线文档编辑

- Collabora Online 官网地址： https://www.collaboraoffice.com/
- SDK官网地址： https://sdk.collaboraonline.com/
- 官方文档： https://sdk.collaboraonline.com/
- GitHub： https://github.com/CollaboraOnline/online
- WOPI 官方文档： https://api.onlyoffice.com/zh/editors/wopi/discovery
- 官方 nodejs demo： https://github.com/CollaboraOnline/collabora-online-sdk-examples//tree/master/webapp/nodejs

## 1. 什么是 Collabora Online？

`Collabora Onlnien` 是一款开源的基于LibreOffice 的云办公套件，它允许用户在浏览器中创建、编辑和协作处理文档、电子表格、演示文稿和绘图。它是由 Collabora Productivity Ltd开发的，这家公司是LibreOffice社区的主要贡献者之一。

### 1.1 拉取镜像

```bash
docker pull collabora/code:latest
```

### 1.2 启动镜像

```bash
docker run -dit --name "collabora" \
-p 0.0.0.0:9980:9980 \
-e "username=admin" \
-e "password=123456" \
--cap-add MKNOD \
--privileged \
collabora/code:latest
```

> --privileged 是让当前容器的用户即使不是 root，也可以执行任何需要root权限的操作。

### 1.3 设置http访问

coolwsd.xml 是Collabora Online的配置文件，它控制着Collabora Online服务中是否启用TSL/SSL 加密、字体资源位置、对特定IP或域名的访问控制等各种参数的设置。

```bash
docker exec -it -u root collabora /bin/bash
apt-get update
apt-get install vim -y

vim /etc/coolwsd/coolwsd.xml
<ssl desc="SSL settings">
<enable type="bool" desc="xxx." default="true">false</enable>

# 重启
docker restart collabora
```

### 1.4 访问地址

- 管理地址：https://172.17.17.160:9980/browser/dist/admin/admin.html
- 配置参数：https://172.17.17.160:9980/hosting/discovery
- 编辑地址：https://172.17.17.160:9980/browser/30822a710f/cool.html?WOPISrc=http://192.168.1.195:8080/wopi/files/92&lang=zh-CN&access_token=123

在线编辑地址后面的 `WOPISrc` 要拼接 WOPI 服务地址的。通过cool.html访问时，后面传输的任何参数，例如 `access_token` 都会被转发到对应的 WOPI 服务的请求上，用于鉴权等操作。

## 2. 什么是WOPI？

WOPI：全称为 Web Office Protocol Interface，是一种由微软提出的协议，旨在允许 Web 应用程序能够无缝地与 Office 文档进行交互。WOPI 主要应用于在线编辑和预览 Microsoft Office 文档的场景，使得用户可以在 Web应用中直接打开、编辑和保存 Office 文档，而无需下载文档到本地或安装 Office 套件。