#!/usr/bin/env bash

app_version="${1}"

echo "Building docker image to version: [${app_version}]"

current_path=$(pwd)
cd ..
docker build -t user-security-service:${app_version} .
cd ${current_path}
