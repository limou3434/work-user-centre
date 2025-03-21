#!/bin/bash
# Dockerfile_build.sh
npm install
npm run build
npm run package
docker build -t work-user-centre-frontend:0.0.1 .
