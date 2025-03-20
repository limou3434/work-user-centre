# Dockerfile
# @author <a href="https://github.com/xiaogithubooo">limou3434</a>

# 使用官方 Nginx 镜像作为基础镜像
FROM nginx:1.18.0

# 删除默认配置
RUN rm /etc/nginx/conf.d/default.conf

# 复制自定义配置到 Nginx 配置目录
COPY ./work-user-centre-proxy-container.conf /etc/nginx/sites-available/work-user-centre-proxy-container.conf

# 创建 /etc/nginx/sites-enabled/
RUN mkdir -p /etc/nginx/sites-enabled

# 创建软链接到 /etc/nginx/sites-enabled/
RUN ln -s /etc/nginx/sites-available/work-user-centre-proxy-container.conf /etc/nginx/sites-enabled/

# 暴露 Nginx 监听端口
EXPOSE 80

# 启动 Nginx
CMD ["nginx", "-g", "daemon off;"]



