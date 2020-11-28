#!/usr/bin/sh
cd /home/workspace/ShengTangManage/stdiet-ui
git pull origin master
yarn
yarn build:prod
rm -rf /home/web/manage.shengtangdiet.com/dist
cp -rf ./dist /home/web/manage.shengtangdiet.com/