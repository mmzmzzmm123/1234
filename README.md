## 基于RuoYi-VUE v3.8.4【改造】
改造后支持
1. 支持 mybatis-plus 
2. 支持 Lombok 
3. 支持 lock4j-redis-template 分布式锁
4. 支持 账号登录客户端数量限制设置 在系统管理 -> 参数设置 
  * 配置参数名称: 账号自助-账号客户端数量 
  * 配置参数键名: sys.account.loginClientCount
5. 支持 spring-redis-cache 注解配置
6. 修改token登录信息在缓存中的数据结构 
  * 将用户信息/权限/用户token组独立拆开，方便统一管理
7. 调整菜单 把系统管理里的 用户管理/部门管理/岗位管理 分配为 组织管理
8. 站内信/站内信模板 + 推送