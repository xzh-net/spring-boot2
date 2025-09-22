# 依赖动态权限扩展接口实现的Security整合

访问地址：http://127.0.0.1:8080/doc.html

## 整合思路

动态权限扩展接口（DynamicSecurityService），本身不是Security的标准组件，而是一个常用于自定义、动态权限管理方案中的核心接口，通过调用 loadDataSource 等方法在运行时刷新规则，从而实现无需重启应用即可生效的权限管理

1. 配置DynamicSecurityService，实现loadDataSource方法动态加载规则
2. 实现FilterInvocationSecurityMetadataSource来动态获取权限配置
3. 实现AccessDecisionManager来进行访问决策

