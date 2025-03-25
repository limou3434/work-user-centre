#!/bin/bash
# 镜像打包脚本
#
# @author <a href="https://github.com/xiaogithuboo">limou3434</a>
sudo docker save -o work-user-centre-frontend-docker-image-0.0.1.tar work-user-centre-frontend:0.0.1
echo "传输镜像文件: \"sudo rsync -avz xxx.tar <user>@<ip>:/home/<user>/\""
echo "加载镜像文件: \"sudo docker load -i xxx.tar\""
echo "脚本结束"
