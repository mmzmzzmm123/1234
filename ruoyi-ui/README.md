## 开发

```bash
# 克隆项目
git clone https://gitee.com/y_project/RuoYi-Vue

# 进入项目目录
cd ruoyi-ui

# 安装依赖
npm install

# 建议不要直接使用 cnpm 安装依赖，会有各种诡异的 bug。可以通过如下操作解决 npm 下载速度慢的问题
npm install --registry=https://registry.npm.taobao.org

如果报错
Step1：npm cache clean --force
Step2：rm -rf node_modules
Step3：rm -rf package-lock.json
Step4：npm install

作者：热爱前端的苏菇凉
链接：https://www.jianshu.com/p/db2ac92842d5
来源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

# 启动服务
npm run dev
```

浏览器访问 http://localhost:80

## 发布

```bash
# 构建测试环境
npm run build:stage

# 构建生产环境
npm run build:prod
```