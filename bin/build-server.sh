#!/usr/bin/sh
cd /home/workspace/ShengTangManage
git pull origin master
mvn clean package -Dmaven.test.skip=true
rm -f /home/api/manage.shengtangdiet.com/st-manage.jar
cp -f ./ruoyi-admin/target/ruoyi-admin.jar /home/api/manage.shengtangdiet.com/st-manage.jar
systemctl stop st-manage.service
systemctl start st-manage.service