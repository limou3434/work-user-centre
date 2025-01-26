# Dockerfile
# @author <a href="https://github.com/xiaogithubooo">limou3434</a>
# @from <a href="https://workhub.com">大数据工作室</a>

# 基于镜像
FROM <镜像名>:<版本号>

# 拷贝文件
COPY <宿主主机文件路径> <容器主机文件绝对路径>

# 运行指令
RUN <指令>

# 暴露端口
EXPOSE <端口号>

# 启动指令
CMD ["xxx", "-xxx", "xxx", "..."]

