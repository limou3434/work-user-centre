#!/bin/bash
# 镜像运行脚本
#
# @author <a href="https://github.com/xiaogithuboo">limou3434</a>
sudo docker container stop work-user-centre-frontend || true
sudo docker container rm work-user-centre-frontend || true
sudo docker run -d --restart=always --network host --name work-user-centre-frontend work-user-centre-frontend:0.0.1
sudo docker container logs work-user-centre-frontend
sudo docker container ls -a
echo "脚本结束"
