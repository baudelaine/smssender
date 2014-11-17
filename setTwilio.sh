#!/bin/bash
APP=$1

if [ -z ${APP} ]; then
	echo "app name missing. Exiting..."
	exit 1
fi  

cf set-env ${APP} TWILIO_ACCOUNT_SID " "
cf set-env ${APP} TWILIO_AUTH_TOKEN " "
cf set-env ${APP} TWILIO_PHONES " "

cf sp ${APP} && cf st ${APP}

exit 0 
