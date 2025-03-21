#!/bin/bash
# 镜像编译脚本
npm install
npm run build
npm run package
sudo docker build -t work-user-centre-frontend:0.0.1 .
echo "脚本结束"
