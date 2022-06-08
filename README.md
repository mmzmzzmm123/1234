<p align="center">
	<img alt="logo" src="https://oscimg.oschina.net/oscnet/up-d3d0a9303e11d522a06cd263f3079027715.png">
</p>
<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">RuoYi v3.8.1</h1>
<h4 align="center">基于SpringBoot+Vue前后端分离的Java快速开发框架</h4>
<p align="center">
	<a href="https://gitee.com/y_project/RuoYi-Vue/stargazers"><img src="https://gitee.com/y_project/RuoYi-Vue/badge/star.svg?theme=dark"></a>
	<a href="https://gitee.com/y_project/RuoYi-Vue"><img src="https://img.shields.io/badge/RuoYi-v3.8.1-brightgreen.svg"></a>
	<a href="https://gitee.com/y_project/RuoYi-Vue/blob/master/LICENSE"><img src="https://img.shields.io/github/license/mashape/apistatus.svg"></a>
</p>

## 平台简介

若依是一套全部开源的快速开发平台，毫无保留给个人及企业免费使用。

* 前端采用Vue、Element UI。
* 后端采用Spring Boot、Spring Security、Redis & Jwt。
* 权限认证使用Jwt，支持多终端认证系统。
* 支持加载动态权限菜单，多方式轻松权限控制。
* 高效率开发，使用代码生成器可以一键生成前后端代码。
* 提供了技术栈（[Vue3](https://v3.cn.vuejs.org) [Element Plus](https://element-plus.org/zh-CN) [Vite](https://cn.vitejs.dev)）版本[RuoYi-Vue3](https://github.com/yangzongzhuan/RuoYi-Vue3)，保持同步更新。
* 提供了单应用版本[RuoYi-Vue-fast](https://github.com/yangzongzhuan/RuoYi-Vue-fast)，Oracle版本[RuoYi-Vue-Oracle](https://github.com/yangzongzhuan/RuoYi-Vue-Oracle)，保持同步更新。
* 不分离版本，请移步[RuoYi](https://gitee.com/y_project/RuoYi)，微服务版本，请移步[RuoYi-Cloud](https://gitee.com/y_project/RuoYi-Cloud)
* 特别鸣谢：[element](https://github.com/ElemeFE/element)，[vue-element-admin](https://github.com/PanJiaChen/vue-element-admin)，[eladmin-web](https://github.com/elunez/eladmin-web)。
* 阿里云折扣场：[点我进入](http://aly.ruoyi.vip)，腾讯云秒杀场：[点我进入](http://txy.ruoyi.vip)&nbsp;&nbsp;
* 阿里云优惠券：[点我领取](https://www.aliyun.com/minisite/goods?userCode=brki8iof&share_source=copy_link)，腾讯云优惠券：[点我领取](https://cloud.tencent.com/redirect.php?redirect=1025&cps_key=198c8df2ed259157187173bc7f4f32fd&from=console)&nbsp;&nbsp;

## 使用 SmartIDE 开发和调试

### CLI 本地模式

1. **安装** 参考 [安装手册](https://smartide.cn/zh/docs/install/cli/) 完成CLI的本地安装，SmartIDE CLI 支持 Windows/MacOS/Linux 三大主流操作系统。

2. **一键启动** 使用以下指令即可启动WebIDE开始开发和调试

```shell
## 使用GitHub
smartide start https://github.com/idcf-boat-house/boathouse-calculator.git
## 使用Gitee
smartide start https://gitee.com/idcf-boat-house/boathouse-calculator.git
```

启动后的效果如下

![](.ide/images/ruoyi-idea-debugging.png)

### CLI 远程主机模式

远程主机模式允许开发者使用一台Linux主机作为SmartIDE的开发资源，将开发环境一键漫游到这台主机上。开发环境主机可以是位于任何位置的物理机或者虚拟机，无需开放除了SSH端口以外的其他网络端口。

主机环境要求：主机上需要需要预装 Docker 环境，请参考 [安装手册](https://smartide.cn/zh/docs/install/docker/linux/)。

远程主机模式的启动指令与本地模式保持一致，只需要添加 --host <主机ID> 参数即可。

运行以下指令添加主机到 SmartIDE CLI 并启动环境。

```shell
## 添加主机
## 如果采用SSH-KEY认证方式，可以省略 <SSH密码> 参数
smartide host add <主机IP或者域名> --username <SSH用户名> --password <SSH密码> --port <SSH端口>

## 执行成功后，CLI会返回 主机ID，也可以使用以下指令查询主机列表
smartide host list

## 使用主机模式启动环境
## 使用GitHub 
smartide start --host <主机ID> https://github.com/idcf-boat-house/boathouse-calculator.git
## 使用Gitee
smartide start --host <主机ID> https://gitee.com/idcf-boat-house/boathouse-calculator.git
```

### Server 模式

SmartIDE Server 为开发团队提供对开发环境的统一在线管理和访问能力，企业管理者可以通过SmartIDE Server为开发团队提供统一的一致的开发环境，大幅降低开发者花费在搭建环境上的时间消耗，让开发者可以将更多的精力投入到业务需求开发和技术方案改进上。更为重要的是，SmartIDE Server提供了完全受控的开发测试环境，管理者可以通过工作区策略对开发环境进行管理。

Server一键启动按钮，点击此按钮即可启动开发调试环境

[![smartide start](.ide/images/smartide-start-badge.png)](http://dev.smartide.cn/#/layout/smartide/workspace/details/157)

- 在线版地址 https://dev.smartide.cn 
- Server版快速启动手册 https://smartide.cn/zh/docs/quickstart/server/ 

SmartIDE Server 已经开放内测，请进入 [官网](https://smartide.cn) 扫描页面底部二维码即可申请加入。


## 内置功能

1.  用户管理：用户是系统操作者，该功能主要完成系统用户配置。
2.  部门管理：配置系统组织机构（公司、部门、小组），树结构展现支持数据权限。
3.  岗位管理：配置系统用户所属担任职务。
4.  菜单管理：配置系统菜单，操作权限，按钮权限标识等。
5.  角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
6.  字典管理：对系统中经常使用的一些较为固定的数据进行维护。
7.  参数管理：对系统动态配置常用参数。
8.  通知公告：系统通知公告信息发布维护。
9.  操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。
10. 登录日志：系统登录日志记录查询包含登录异常。
11. 在线用户：当前系统中活跃用户状态监控。
12. 定时任务：在线（添加、修改、删除)任务调度包含执行结果日志。
13. 代码生成：前后端代码的生成（java、html、xml、sql）支持CRUD下载 。
14. 系统接口：根据业务代码自动生成相关的api接口文档。
15. 服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息。
16. 缓存监控：对系统的缓存信息查询，命令统计等。
17. 在线构建器：拖动表单元素生成相应的HTML代码。
18. 连接池监视：监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。

## 在线体验

- admin/admin123  
- 陆陆续续收到一些打赏，为了更好的体验已用于演示服务器升级。谢谢各位小伙伴。

演示地址：http://vue.ruoyi.vip  
文档地址：http://doc.ruoyi.vip

## 演示图

<table>
    <tr>
        <td><img src="https://oscimg.oschina.net/oscnet/cd1f90be5f2684f4560c9519c0f2a232ee8.jpg"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/1cbcf0e6f257c7d3a063c0e3f2ff989e4b3.jpg"/></td>
    </tr>
    <tr>
        <td><img src="https://oscimg.oschina.net/oscnet/up-8074972883b5ba0622e13246738ebba237a.png"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/up-9f88719cdfca9af2e58b352a20e23d43b12.png"/></td>
    </tr>
    <tr>
        <td><img src="https://oscimg.oschina.net/oscnet/up-39bf2584ec3a529b0d5a3b70d15c9b37646.png"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/up-936ec82d1f4872e1bc980927654b6007307.png"/></td>
    </tr>
	<tr>
        <td><img src="https://oscimg.oschina.net/oscnet/up-b2d62ceb95d2dd9b3fbe157bb70d26001e9.png"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/up-d67451d308b7a79ad6819723396f7c3d77a.png"/></td>
    </tr>	 
    <tr>
        <td><img src="https://oscimg.oschina.net/oscnet/5e8c387724954459291aafd5eb52b456f53.jpg"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/644e78da53c2e92a95dfda4f76e6d117c4b.jpg"/></td>
    </tr>
	<tr>
        <td><img src="https://oscimg.oschina.net/oscnet/up-8370a0d02977eebf6dbf854c8450293c937.png"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/up-49003ed83f60f633e7153609a53a2b644f7.png"/></td>
    </tr>
	<tr>
        <td><img src="https://oscimg.oschina.net/oscnet/up-d4fe726319ece268d4746602c39cffc0621.png"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/up-c195234bbcd30be6927f037a6755e6ab69c.png"/></td>
    </tr>
    <tr>
        <td><img src="https://oscimg.oschina.net/oscnet/b6115bc8c31de52951982e509930b20684a.jpg"/></td>
        <td><img src="https://oscimg.oschina.net/oscnet/up-5e4daac0bb59612c5038448acbcef235e3a.png"/></td>
    </tr>
</table>

## SmartIDE 快速启动

### 1. 本地模式：
使用SmartIDE启动RuoYi的开发调试非常简单，仅需要两个步骤

1. 按照 [安装手册](https://smartide.cn/zh/docs/install/) 完成 SmartIDE 本地命令行工具的安装
2. 使用以下命令一键启动SFDE

```shell
## SmartIDE是一款跨平台开发工具，您可以在Windows或者MacOS上执行同样的指令
smartide start https://gitee.com/SmartIDE/RuoYi-Vue.git
```

### 2. 远程主机模式：
1. 按照 [Docker & Docker-Compose 安装手册 (Linux服务器)](https://smartide.cn/zh/docs/install/docker-install-linux/) 准备好一台远程主机，建议使用Ubuntu 18.04 LTS以上版本的Linux环境。
2. 按照以下指令启动项目

```shell
# 将远程主机添加到SmartIDE中
smartide host add <IpAddress> --username <SSH-UserName> --password <SSH-Password> --port <SSH-Port默认为22>

# 获取主机ID
smartide host list

# 使用远程主机启动项目
smartide start --host <主机ID> https://gitee.com/SmartIDE/RuoYi-Vue.git
```

## 若依前后端分离交流群

QQ群： [![加入QQ群](https://img.shields.io/badge/已满-937441-blue.svg)](https://jq.qq.com/?_wv=1027&k=5bVB1og) [![加入QQ群](https://img.shields.io/badge/已满-887144332-blue.svg)](https://jq.qq.com/?_wv=1027&k=5eiA4DH) [![加入QQ群](https://img.shields.io/badge/已满-180251782-blue.svg)](https://jq.qq.com/?_wv=1027&k=5AxMKlC) [![加入QQ群](https://img.shields.io/badge/已满-104180207-blue.svg)](https://jq.qq.com/?_wv=1027&k=51G72yr) [![加入QQ群](https://img.shields.io/badge/已满-186866453-blue.svg)](https://jq.qq.com/?_wv=1027&k=VvjN2nvu) [![加入QQ群](https://img.shields.io/badge/已满-201396349-blue.svg)](https://jq.qq.com/?_wv=1027&k=5vYAqA05) [![加入QQ群](https://img.shields.io/badge/已满-101456076-blue.svg)](https://jq.qq.com/?_wv=1027&k=kOIINEb5) [![加入QQ群](https://img.shields.io/badge/已满-101539465-blue.svg)](https://jq.qq.com/?_wv=1027&k=UKtX5jhs) [![加入QQ群](https://img.shields.io/badge/264312783-blue.svg)](https://jq.qq.com/?_wv=1027&k=EI9an8lJ) 点击按钮入群。