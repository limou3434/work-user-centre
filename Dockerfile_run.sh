#!/bin/bash
docker container stop work-xxx-xxx ||
  docker container rm work-xxx-xxx ||
  docker run -d -p <本地端口号>:<暴露端口号> --name <work--xxx-xxx-x.y.z> <work-xxx-xxx:x.y.z> &&
  docker container logs <work-xxx-xxx> &&
  docker container ls -a

