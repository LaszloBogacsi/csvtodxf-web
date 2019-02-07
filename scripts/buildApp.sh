#!/usr/bin/env bash
# build backend
cd ../
mvn clean package -P prod

cp ./csvtodxf-service/target/csvtodxf-service-0.0.1-SNAPSHOT.jar .