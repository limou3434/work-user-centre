#!/bin/bash
# ./Dockerfile_run.sh
docker container stop work-user-centre-backend || true
docker container rm work-user-centre-backend || true
docker run -d --restart=always --network host --name work-user-centre-backend work-user-centre-backend:0.0.1
docker container logs work-user-centre-backend
docker container ls -a
