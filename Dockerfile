FROM openjdk:8-jre
RUN mkdir -p /home/gradle/clash/
COPY ./build/libs/clash-back.jar /home/gradle/clash/app.jar
WORKDIR /home/gradle/clash
ENTRYPOINT ["java", "-jar", "app.jar"]