#!/usr/bin/env bash

mvn clean package -DskipTests
java -jar target/vertx-project-1.0-SNAPSHOT-fat.jar -conf src/main/resources/my-application-conf.json