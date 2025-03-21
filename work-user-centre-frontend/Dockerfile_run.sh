#!/bin/bash
# ./Dockerfile_run.sh
docker container stop work-user-centre-frontend || true
docker container rm work-user-centre-frontend || true
docker run -d --restart=always --network host --name work-user-centre-frontend work-user-centre-frontend:0.0.1
docker container logs work-user-centre-frontend
docker container ls -a
