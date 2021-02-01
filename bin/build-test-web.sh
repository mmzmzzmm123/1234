#!/usr/bin/sh
cd /home/workspace/ShengTangManage/stdiet-ui
git pull
yarn
yarn build:stage
rm -rf /home/web/test.stdiet/dist
cp -rf ./dist /home/web/test.stdiet/