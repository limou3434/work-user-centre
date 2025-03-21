#!/bin/bash
# ./Dockerfile_run.sh
sudo docker container stop work-user-centre-frontend || true
sudo docker container rm work-user-centre-frontend || true
sudo docker run -d -e MODE="release" --restart=always --network host --name work-user-centre-frontend work-user-centre-frontend:0.0.1
sudo docker container logs work-user-centre-frontend
sudo docker container ls -a

# 其中 MODE 可以设置为 "develop"(默认) | "release" | "main"
# 并且允许设置 DEVELOP_HOST 更换内部默认的主机号
# 并且允许设置 RELEASE_HOST 更换内部默认的主机号
# 并且允许设置 MAIN_HOST 更换内部默认的主机号
