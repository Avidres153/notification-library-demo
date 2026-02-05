#!/bin/bash

################# Build java projects #################
echo "-------> Building projects"
CURRENT_DIR="$(pwd)"
echo "Current dir: $CURRENT_DIR"

EXAMPLE_PROJECT_DIR="$CURRENT_DIR/examples/send-notification"
MAVEN_WRAPPER_DIR="$EXAMPLE_PROJECT_DIR/mvnw"
chmod +x "$MAVEN_WRAPPER_DIR"

PROJECT_DIR="$CURRENT_DIR/universal-notification"
echo "Dir to build library: $PROJECT_DIR"

echo "Building library..."
cd $PROJECT_DIR
"$MAVEN_WRAPPER_DIR" clean package

if [ $? -eq 0 ]; then
    echo "library compilation were successful!!!"
else
    echo "Error to build library."
    exit 1
fi

echo "Building example..."
cd $EXAMPLE_PROJECT_DIR
"$MAVEN_WRAPPER_DIR" clean package

if [ $? -eq 0 ]; then
    echo "example project compilation were successful!!!"
else
    echo "Error to build example."
    exit 1
fi

echo "-------> Copying compiled artifacts to docker directory"
DOCKER_DIR="$CURRENT_DIR/docker"

mkdir -p "$DOCKER_DIR/tmp"

echo "-------> Copying example artifact"
cp "$EXAMPLE_PROJECT_DIR/target/"*.jar "$DOCKER_DIR/tmp"


################# Build docker image #################
echo "-------> Building microservice docker image"

cd $DOCKER_DIR


docker build --build-arg APP_NAME=send-notification-1.0.0-SNAPSHOT.jar -t send-notification-microservice .


################# Starting docker containers #################

echo "-------> Stating docker containers"
docker compose up -d --build

if [ $? -eq 0 ]; then
    echo "-------> Apps are ready to use!!!"
else
    echo "Error to get up Docker compose"
fi