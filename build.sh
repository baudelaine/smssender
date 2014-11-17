#!/bin/bash

JAVAC=$(command -v javac)

if [ "${JAVAC}" == "" ]; then
	echo "ERROR !!! javac not found PATH. Exiting..."
	exit 1
fi

javac -cp wlp/*:app/WebContent/WEB-INF/lib/* -d app/WebContent/WEB-INF/classes/ app/src/com/baudelaine/bluemix/sms/*.java

exit 0
