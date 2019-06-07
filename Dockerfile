FROM openjdk:12.0-jdk-oracle
COPY . /home/message/src/
RUN ls -al /home/
RUN     cd /home/message/src && \
        ./gradlew build --refresh-dependencies --info && \
        ./gradlew build --info && \
        mv ./build/bin/*.jar .. && \
        cd .. && \
        rm -rf src && \
        ls -al
CMD java -server -jar /home/message/MessageStore.jar
