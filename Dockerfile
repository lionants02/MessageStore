FROM openjdk:8-jdk-stretch
COPY . /home/message/src/
RUN     cd /home/message/src && \
        ./gradlew build --refresh-dependencies --info && \
        ./gradlew build --info && \
        mv ./build/bin/*.jar .. && \
        cd .. && \
        rm -rf src && \
        rm -rf /root/.gradle && \
        rm -rf /tmp/*
CMD java -server -jar /home/message/MessageStore.jar
