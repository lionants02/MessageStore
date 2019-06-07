FROM openjdk:8-jdk-stretch
COPY . /home/message/src/
RUN ls -al /home/
RUN     cd /home/message/src && \
        ./gradlew build --refresh-dependencies --info && \
        ./gradlew build --info && \
        mv ./build/bin/*.jar .. && \
        cd .. && \
        rm -rf src && \
        rm -rf /tmp/* && \
        ls -al
CMD java -server -jar /home/message/MessageStore.jar
