#/bin/sh

CF=$(command -v cf)

if [ "${CF}" == "" ]; then
	echo "ERROR !!! cf not found in PATH. Exiting ..."
	exit 1
fi

APP=$1
if [ "$(grep -c '<App Name>' manifest.yml)" == "3" ]; then
	if [ "$APP" == "" ]; then
		echo "ERROR !!! Provide a name to subsitute with <App Name>"
		exit 1
	fi
fi

sed -i -e "s/<App Name>/$APP/g" manifest.yml

SVC=$(cat manifest.yml | awk -F':' '$1 ~ /name/ { gsub("[ ]", "", $2); print $2}')-svc

cf marketplace | grep -i twilio | awk -v svc="$SVC" '{print "cf cs " $1 " " $2 " " svc}' | sh
cf p

exit 0
