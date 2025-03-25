#!/bin/bash
# 镜像编译脚本
#
# @author <a href="https://github.com/xiaogithuboo">limou3434</a>
npm install
npm run build
npm run package
sudo docker build -t work-user-centre-frontend:0.0.1 .
echo "脚本结束"
