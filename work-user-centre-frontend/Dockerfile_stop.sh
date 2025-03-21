#!/bin/bash
# ./Dockerfile_stop.sh
sudo docker container stop work-user-centre-backend || true
sudo docker container rm work-user-centre-backend || true
sudo docker container logs work-user-centre-frontend
sudo docker container ls -a
