# 设置本镜像需要使用的基础镜像
FROM  java:8

# 把 app.jar 包添加到镜像中
ADD ./target/ruoyi-admin.jar /app.jar

# 镜像暴露的端口
RUN bash -c 'touch /app.jar'

# 容器启动命令
ENTRYPOINT ["java","-jar","/app.jar"]

# 设置时区
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
