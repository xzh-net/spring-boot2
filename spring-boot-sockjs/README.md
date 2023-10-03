# SockJS 

SockJS 是一种浏览器与服务器之间的通信协议，它可以在浏览器和服务器之间建立一个基于 HTTP 的双向通信通道。SockJS 的主要作用是提供一种 WebSocket 的兼容性解决方案，使得不支持 WebSocket 的浏览器也可以使用 WebSocket。

SockJS 实现了一个 WebSocket 的兼容层，它可以在浏览器和服务器之间建立一个基于 HTTP 的通信通道，然后通过这个通道进行双向通信。当浏览器不支持 WebSocket 时，SockJS 会自动切换到使用轮询（polling）或长轮询（long-polling）的方式进行通信

- 访问地址：http://localhost:8080/?userId=admin
- 参考资料：http://jmesnil.net/stomp-websocket/doc/
- 客戶端调用入口`@MessageMapping("/send")`，也可以使用ajax调用`@GetMapping("/send")`

```bash
mvn clean compile
mvn clean package
```


