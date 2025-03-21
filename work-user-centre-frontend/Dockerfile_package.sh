#!/bin/bash
# ./Dockerfile_package.sh
sudo docker save -o work-user-centre-frontend-docker-image-0.0.1.tar work-user-centre-frontend:0.0.1
echo "Transfer files: \"rsync -avz work-user-centre-frontend-docker-image-0.0.1.tar <user>@<ip>:/home/<user>/\""
echo "Load the image: \"docker load -i work-user-centre-frontend-docker-image-0.0.1.tar\""