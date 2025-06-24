FROM tomcat:11.0-jdk21
RUN rm -rf /usr/local/tomcat/webapps/ROOT
COPY target/weather.war /usr/local/tomcat/webapps/ROOT.war