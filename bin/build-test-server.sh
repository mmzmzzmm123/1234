#!/usr/bin/sh
cd /home/workspace/ShengTangManage
git pull origin master
mvn clean package -Dmaven.test.skip=true -P dev
rm -f /home/api/manage.shengtangdiet.com/test/st-manage.jar
cp -f ./ruoyi-admin/target/stdiet-admin.jar /home/api/manage.shengtangdiet.com/test/st-manage.jar
nohup java -jar /home/api/manage.shengtangdiet.co/test/st-manage.jar