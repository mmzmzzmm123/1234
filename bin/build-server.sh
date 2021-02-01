#!/usr/bin/sh
cd /home/workspace/ShengTangManage
git pull
mvn clean package -Dmaven.test.skip=true -P prod
rm -f /home/api/manage.shengtangdiet.com/st-manage.jar
cp -f ./stdiet-admin/target/stdiet-admin.jar /home/api/manage.shengtangdiet.com/st-manage.jar
systemctl reload st-manage.service