#!/bin/bash
# ./Dockerfile_stop.sh
docker container stop work-user-centre-backend || true
docker container rm work-user-centre-backend || true
docker container logs work-user-centre-frontend
docker container ls -a
