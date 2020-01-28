From tomcat:8.0.51-jre8-alpine
RUN rm -rf /usr/local/tomcat/webapps/*
COPY /springboot-restserve.war /usr/local/tomcat/webapps/ROOT.war
CMD ["catalina.sh","run"]