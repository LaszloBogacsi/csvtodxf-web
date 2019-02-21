#!/usr/bin/env bash
# Delete all rows from convert_task table and save the number of deleted rows to a file with the current date
cleanUpDB="DELETE FROM convert_task;"
date >> dailyUsersReport.txt
psql -U csvtodxf -d csvtodxf-prod -c ${cleanUpDB} >> dailyUsersReport.txt
