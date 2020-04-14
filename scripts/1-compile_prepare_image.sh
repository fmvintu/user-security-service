#!/usr/bin/env bash

echo "Compiling project and preparing image..."
current_path=$(pwd)
cd ..
./mvnw clean package dependency:copy-dependencies -DoutputDirectory=target/dependencies
cd ${current_path}
