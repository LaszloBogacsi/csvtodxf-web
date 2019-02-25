# CSV to DXF Convert Web Application

### Technologies Used:
- Java Spring Boot - backend
- React js frontend

### Building the app

`./scripts/buildApp.sh` this builds the app and copies the jar to `project root` folder.

### For local development  
`java -jar ./csvtodxf-service-0.0.1-SNAPSHOT.jar` 
visit: `http://localhost:9090/`

### to deploy the app in aws using aws cli

```
aws deploy create-deployment \
 --application-name csvtodxf \
 --deployment-config-name CodeDeployDefault.AllAtOnce \
 --deployment-group-name csvtodxf-codeploy \
 --github-location repository=LaszloBogacsi/csvtodxf-web,commitId=<COMMIT-ID>
 ```
 
 
 #### Server setup
 1. Postgres
    - user: csvtodxf
    - db: csvtodxf-prod
 2. NGINX
    - custom nginx.conf/csvtodxf.conf
 3. Service wrapper
    - start, stop
    - symlink to service name in /etc/init.d/csvtodxf
 4. Cron job
    - clean up db