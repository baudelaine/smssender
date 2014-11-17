#/bin/sh

CF=$(command -v cf)

if [ "${CF}" == "" ]; then
	echo "ERROR !!! cf not found in PATH. Exiting ..."
	exit 1
fi

cf s | awk '{if (NR > 4) { if (NF == 4) { print "cf us " $4, $1}}}' | sh
cf s | awk '{if (NR > 4) {print "cf ds " $1 " -f"}}' | sh
cf a | awk '{if (NR > 4) {print "cf d " $1 " -f"}}' | sh
cf delete-orphaned-routes -f

exit 0
