FROM corretto:17
COPY ./build/libs/*.jar app.jar
ENTRYPOINT ["java","-Dspring.config.location=file:/conf/","-jar","app.jar","--spring.profiles.active=dev"]
