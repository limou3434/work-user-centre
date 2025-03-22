#!/bin/bash
# 镜像终止脚本
#
# @author <a href="https://github.com/xiaogithuboo">limou3434</a>
sudo docker container stop work-user-centre-backend || true
sudo docker container rm work-user-centre-backend || true
sudo docker container logs work-user-centre-frontend
sudo docker container ls -a
echo "脚本结束"
