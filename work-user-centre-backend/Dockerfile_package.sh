#!/bin/bash
# 镜像打包脚本
sudo docker save -o work-user-centre-backend-docker-image-0.0.1.tar work-user-centre-backend:0.0.1
echo "传输镜像文件: \"rsync -avz xxx.tar <user>@<ip>:/home/<user>/\""
echo "加载镜像文件: \"docker load -i xxx.tar\""
echo "脚本结束"
