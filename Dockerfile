FROM adoptopenjdk/openjdk8-openj9:alpine-slim
ARG CURRENT_DIR=.
ADD ${CURRENT_DIR}/target/demo-0.0.1-SNAPSHOT.jar demo-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","demo-0.0.1-SNAPSHOT.jar"]