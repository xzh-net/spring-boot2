# Redis

访问地址：http://127.0.0.1:8080/doc.html

1. 五大类型数据测试
2. 发布订阅，自定义key失效事件、全局key失效事件
3. 二进制保存
4. 管道批量操作
5. 缓存注解，自定义不同组别设置不同失效时间
6. redisson分布式锁
7. redisson布隆过滤器
8. lua限流
9. 读写分离
```java
@Bean
public LettuceClientConfigurationBuilderCustomizer clientConfigurationBuilderCustomizer() {
	return new LettuceClientConfigurationBuilderCustomizer() {
		@Override
		public void customize(LettuceClientConfigurationBuilder clientConfigurationBuilder) {
			clientConfigurationBuilder.readFrom(ReadFrom.REPLICA_PREFERRED);
		}
	};
}
```


```bash
mvn clean compile
mvn clean package
```