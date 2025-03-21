#!/bin/bash
# 镜像运行脚本
sudo docker container stop work-user-centre-backend || true
sudo docker container rm work-user-centre-backend || true
sudo docker run -d --restart=always --network host --name work-user-centre-backend work-user-centre-backend:0.0.1
sudo docker container logs work-user-centre-backend
sudo docker container ls -a
echo "脚本结束"
