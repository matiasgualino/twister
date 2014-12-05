#!/bin/bash

JAR="target/twister-jar-with-dependencies.jar"

if [ ! -f $JAR ]; then
echo -n "Compilando proyecto..."
mvn clean compile assembly:single --quiet > /dev/null 2>&1
echo "Listo."
fi

exec java -jar $JAR "$@"