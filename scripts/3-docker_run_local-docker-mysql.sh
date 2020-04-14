#!/usr/bin/env bash

app_version="${1}"

echo "Running docker image to version: [{}]"

current_path=$(pwd)
cd ..
docker run -e DATABASE_SERVICE=mysqlsrv --link mysqlsrv --net usecurityproject_mysql-network -p 8090:8090 --name run-usecsvc user-security-service:${app_version}
cd ${current_path}
