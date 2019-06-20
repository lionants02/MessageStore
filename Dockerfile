FROM gradle
COPY . /home/message/src/
RUN     cd /home/message/src && \
        gradle build --refresh-dependencies --info && \
        gradle build --info && \
        mv ./build/bin/*.jar ..
FROM openjdk:8-jre-slim
COPY --from=0 /home/message/MessageStore.jar /home/message/MessageStore.jar
CMD java -server -jar /home/message/MessageStore.jar
