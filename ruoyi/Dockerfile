FROM 172.16.30.92:5000/uv:jdk-1.8.0

# 设置时区
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

VOLUME /tmp
# 将jar包添加到容器中并更名为app.jar

ADD ./target/uv-data-backend.jar app.jar
# 创建日志目录
#RUN mkdir -p /var/log/uv-data/ /opt/sites/collateral/ /opt/sites/calc/ /opt/sites/hanlp-data/

#RUN mkdir -p /opt/sites/collateral/

# 运行jar包
#RUN bash -c 'touch /app.jar'
# -Djava.security.egd为了缩短tomcat启动时间
CMD java -Xms2048m -Xmx2048m -jar /app.jar --spring.profiles.active=prod