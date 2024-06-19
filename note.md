###后端
```
com.renxin     
├── admin                                // api入口
│       └── app.controller               // 移动端api
│             └── course                 // 课程接口
│             └── gauge                  // 测评接口
│             └── images                 // 获取图片
│             └── login                  // 登录
│             └── marketing              // 营销
│             └── order                  // 订单
│             └── psychology             // 咨询
│             └── qr                     // 二维码
│             └── system                 // 系统
│             └── wechat                 // 微信登录,支付,回调
│       └── common                       // 公共
│             └── task                   // mysql备注,日志清理
│       └── web.controller               // 后端api
│             └── common                 // 图片,oss相关
│             └── course                 // 课程
│             └── gauge                  // 测评
│             └── marketing              // 营销
│             └── monitor                // 系统监控,日志
│             └── psychology             // 咨询
│             └── system                 // 系统
├── ruoyi-psychology                     // 服务实现
│       └── psychology                   // 咨询服务
│             └── constant               // 模块常量
│             └── domain                 // 数据实体
│             └── dto                    // 数据流转
│             └── mapper                 // 接口
│             └── request                // 请求参数
│             └── service                // 服务接口
│             └── task                   // 定时任务
│             └── vo                     // 数据流转
│       └── common                       // 公共
│       └── course                       // 课程
│       └── gauge                        // 测评
│       └── login                        // 登录
│       └── user                         // 用户
│       └── wechat                       // 微信
├── common                               // 工具类
│       └── annotation                   // 自定义注解
│       └── config                       // 全局配置
│       └── constant                     // 通用常量
│       └── core                         // 核心控制
│       └── enums                        // 通用枚举
│       └── exception                    // 通用异常
│       └── filter                       // 过滤器处理
│       └── utils                        // 通用类处理
│       └── vo                           // 公共vo
│       └── xss                          // xss
├── framework                            // 框架核心
│       └── aspectj                      // 注解实现
│       └── config                       // 系统配置
│       └── datasource                   // 数据权限
│       └── interceptor                  // 拦截器
│       └── manager                      // 异步处理
│       └── security                     // 权限控制
│       └── web                          // 前端控制
├── ruoyi-generator                      // 代码生成（可移除）
├── ruoyi-quartz                         // 定时任务（可移除）
├── ruoyi-system                         // 系统代码
```

###前端结构ruoyi-ui
```
├── build                      // 构建相关  
├── bin                        // 执行脚本
├── public                     // 公共文件
│   ├── favicon.ico            // favicon图标
│   └── index.html             // html模板
│   └── robots.txt             // 反爬虫
├── src                        // 源代码
│   ├── api                    // 所有请求
│   ├── assets                 // 主题 字体等静态资源
│   ├── components             // 全局公用组件
│   ├── directive              // 全局指令
│   ├── layout                 // 布局
│   ├── plugins                // 通用方法
│   ├── router                 // 路由
│   ├── store                  // 全局 store管理
│   ├── utils                  // 全局公用方法
│   ├── views                  // 页面
│       ├── banner             
│       ├── components         
│       ├── course             
│       ├── dashboard          
│       ├── error              
│       ├── gauge              
│       ├── marketing          
│       ├── monitor            
│       ├── psychology         
│       ├── system             
│       ├── tool               
│   ├── App.vue                // 入口页面
│   ├── main.js                // 入口 加载组件 初始化等
│   ├── permission.js          // 权限管理
│   └── settings.js            // 系统配置
├── .editorconfig              // 编码格式
├── .env.development           // 开发环境配置
├── .env.production            // 生产环境配置
├── .env.staging               // 测试环境配置
├── .eslintignore              // 忽略语法检查
├── .eslintrc.js               // eslint 配置项
├── .gitignore                 // git 忽略项
├── babel.config.js            // babel.config.js
├── package.json               // package.json
└── vue.config.js              // vue.config.js
```

###H5结构
```
├── common                     // 公共脚本
├── components                 // 自定义组件  
│   ├── consult                // 咨询
│   └── course                 // 课程
│   └── evaluation             // 测评
│   └── yz-audio               // 播放器
├── pages                      // 页面
│   ├── consult                // 咨询
│   └── course                 // 课程
│   └── evaluation             // 测评
├── server                     // api 
├── static                     // 静态资源 
├── style                      // 样式 
├── utils                      // 工具类 
├── uni-modules                // uniapp引入组件 
├── App.vue                    // 入口
├── index.html                 // 入口
├── main.js                    // 入口
├── manifest.json              // 环境配置
├── package.json               // package.json
├── pages.json                 // 页面配置
├── uni.css                    // css
```

###发布
1.  前端发布  
    cd ruoyi-ui
    npm run build:prod
    发布完成后，代码包位于 ruoyi-ui/dist
    
2.  移动端发布  
    HBuilderX 打开项目
    点击发行
    选择网站-PC Web或手机H5
    发布完成后，代码包位于 psychology-app/unpackage/dist/build/h5
    
3.  后端发布  
    ide 打开项目
    点击maven 选择 ruoyi > Lifecycle > package
    发布完成后，代码包: ruoyi-admin/target/ruoyi-admin.jar

将对应代码包上传到服务器
发布命令
docker  cp zhiwo  4bc156798800:/usr/share/nginx/html  
docker cp ruoyi  4bc156798800:/usr/share/nginx/html  
docker restart 4bc156798800  

docker cp  ruoyi-admin.jar   32ee57c9bf1b:/app.jar  
docker  restart 32ee57c9bf1b  

docker 日志  
/var/lib/docker/容器Id  

常用docker 命令  
docker run --rm -v /var/run/docker.sock:/var/run/docker.sock cucker/get_command_4_run_container xx   

docker ps  
docker cp xx xx  
docker stop xx  
docker restart xx  
exec  -it xxx  bash  
docker exec  -it xx  bash  
docker inspect xx  
docker logs -f --tail=1000 xx  

咨询订单排班库存锁定: 支付成功, 预约, 转介  
库存释放情况: 转介  
不处理: 下单,再次支付,取消  
