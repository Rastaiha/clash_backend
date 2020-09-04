FROM openjdk:8-jre
RUN mkdir -p /home/app/clash-back/
COPY . /home/app/clash-back
COPY ./build/libs/clash-back.jar /home/app/clash-back/app.jar
WORKDIR /home/app/clash-back
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]