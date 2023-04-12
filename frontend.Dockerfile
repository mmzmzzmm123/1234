# Note：使用ruoyi-ui目录作为构建的上下文
# docker build ./ruoyi-ui -f frontend.Dockerfile -t ruoyi-ui:latest
FROM node:14-alpine AS build
WORKDIR /build/frontend
COPY . .
# 安装依赖并打包到正式环境
RUN npm install --registry=https://registry.npmmirror.com && npm run build:prod

FROM nginx:1.22-alpine
WORKDIR /apps/ruoyi-ui
ADD ./nginx-sample.conf /etc/nginx/conf.d/default.conf
COPY --from=build /build/frontend/dist .
EXPOSE 80