#编译
FROM maven AS build
WORKDIR /build/backend
#将本地的maven目录装载到容器中的maven目录下，这样就不用重复下载依赖的jar包了
#VOLUME ~/.m2 /root/.m2
COPY . .
RUN mvn clean package -Dmaven.test.skip=true

#打包
FROM openjdk:8u342-jre
WORKDIR /apps/ruoyi-admin
COPY --from=build /build/backend/ruoyi-admin/target/ruoyi-admin.jar .
EXPOSE 8080
ENTRYPOINT ["java","-jar","ruoyi-admin.jar"]