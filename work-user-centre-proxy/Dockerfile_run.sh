#!/bin/bash
# ./Dockerfile_run.sh
docker container stop work-user-centre-proxy || true
docker container rm work-user-centre-proxy || true
docker run -d --network host --name work-user-centre-proxy work-user-centre-proxy:0.0.1
docker container logs work-user-centre-proxy
docker container ls -a
