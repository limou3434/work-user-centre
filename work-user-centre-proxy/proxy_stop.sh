#!/bin/bash
# 停止代理脚本
CONFIG_NAME="work-user-centre-proxy.conf"
sudo systemctl stop nginx
sudo rm /etc/nginx/sites-available/$CONFIG_NAME
sudo rm /etc/nginx/sites-enabled/$CONFIG_NAME
ls -al /etc/nginx/sites-available/
ls -al /etc/nginx/sites-enabled/
echo "脚本结束"
