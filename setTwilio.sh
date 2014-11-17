#!/bin/bash
APP=$1

if [ -z ${APP} ]; then
	echo "app name missing. Exiting..."
	exit 1
fi  

cf set-env ${APP} TWILIO_ACCOUNT_SID "AC08530a72fd8fdea46400037545033240"
cf set-env ${APP} TWILIO_AUTH_TOKEN "36bc9b67b776da53fd4e3ab119d9741f"
cf set-env ${APP} TWILIO_PHONES "+18443346013"

cf sp ${APP} && cf st ${APP}

exit 0 
