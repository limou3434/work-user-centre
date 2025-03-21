#!/bin/bash
CONFIG_NAME="work-user-centre-proxy-develop.conf"
sudo cp "$CONFIG_NAME" /etc/nginx/sites-available/
sudo ln -sf /etc/nginx/sites-available/$CONFIG_NAME /etc/nginx/sites-enabled/
sudo systemctl restart nginx
ls -al /etc/nginx/sites-available/
ls -al /etc/nginx/sites-enabled/
echo "Proxy is success."
