#!/usr/bin/env bash
# build frontend
cd ../csvtodxf-ui
npm run-script build

# build backend
cd ..
cd csvtodxf-services
mvn clean package