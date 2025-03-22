#!/bin/bash
# 运行代理脚本
CONFIG_NAME="work-user-centre-proxy.conf"
sudo cp "$CONFIG_NAME" /etc/nginx/sites-available/
sudo ln -sf /etc/nginx/sites-available/$CONFIG_NAME /etc/nginx/sites-enabled/
sudo systemctl restart nginx
ls -al /etc/nginx/sites-available/
ls -al /etc/nginx/sites-enabled/
echo "脚本结束"
