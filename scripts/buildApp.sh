#!/usr/bin/env bash
# build backend
cd ../
mvn clean package -Pprod

cp ./csvtodxf-service/target/csvtodxf-service-0.0.1-SNAPSHOT.jar .