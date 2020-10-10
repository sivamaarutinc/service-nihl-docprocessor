FROM adoptopenjdk/openjdk11:jdk-11.0.2.9-alpine 
LABEL maintainer="WSIB" 
 
EXPOSE 8080 
 
ENV USER_NAME wsib 
ENV APP_HOME /home/$USER_NAME/app 
 
RUN adduser -D -u 1000 $USER_NAME 
RUN mkdir $APP_HOME 
 
# The application's jar file 
ARG JAR_FILE=target/service-nihl-docprocessor-0.0.1-SNAPSHOT.jar
 
# Add the application's jar to the container 
ADD ${JAR_FILE} $APP_HOME/service-nihl-docprocessor.jar 
 
RUN chown -R $USER_NAME $APP_HOME && chmod -R 775 $APP_HOME 
 
USER $USER_NAME 
WORKDIR $APP_HOME 
RUN sh -c 'touch service-nihl-docprocessor.jar' 
 
# Run the jar file  
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","service-nihl-docprocessor.jar"] 
