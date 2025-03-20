# Dockerfile
# @author <a href="https://github.com/xiaogithubooo">limou3434</a>
# @from <a href="https://workhub.com">大数据工作室</a>

# 使用官方 OpenJDK 镜像作为基础镜像
FROM openjdk:17-jdk-slim

# 使用通配符复制 target 目录下的 JAR 文件
COPY ./work-user-centre-backend/target/*.jar /app.jar

# 设置容器启动时运行的命令
ENTRYPOINT ["java", "-jar", "/app.jar"]

# 容器运行时监听的端口
EXPOSE 8000


