#!/usr/bin/env bash
# build frontend
cd ../csvtodxf-ui/
npm run-script build

# build backend
cd ../
mvn clean package

cp ./csvtodxf-service/target/csvtodxf-service-0.0.1-SNAPSHOT.jar .