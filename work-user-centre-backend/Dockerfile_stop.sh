#!/bin/bash
# 镜像终止脚本
sudo docker container stop work-user-centre-backend || true
sudo docker container rm work-user-centre-backend || true
sudo docker container logs work-user-centre-backend
sudo docker container ls -a
echo "脚本结束"
