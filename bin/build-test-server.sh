#!/usr/bin/sh
cd /home/workspace/ShengTangManage
git pull
mvn clean package -Dmaven.test.skip=true -P dev
rm -f /home/api/manage.shengtangdiet.com/test/stdiet-admin.jar
cp -f ./stdiet-admin/target/stdiet-admin.jar /home/api/manage.shengtangdiet.com/test/

netstat -nlp | grep :8091 | awk '{print $7}' | awk -F"/" '{ print $1 }' | kill
nohup java -jar /home/api/manage.shengtangdiet.com/test/stdiet-admin.jar