#/bin/sh
#
#Take a 4 columns commas separated values CSV file with first row as column name for first parameter.
#
#************** Sample Input CSV ***************
#nom,prenom,email,gsm 
#nöm0,prênom0,EMAIL0@dom.net,33 (6) 12.34.56.78 
#nom1,prénom1,emaïl1@DOM.NET,6 23-456 789
#....
#***********************************************
#
#Get rid off latin characters, lowercase email and format phone numbers (for France +33 only)
#
#************** Sample Output CSV **************
#nom,prenom,email,gsm 
#nom0,prenom0,email0@dom.net,+33612345678 
#nom1,prenom1,email1@dom.net,+33623456789		
#....
#***********************************************

INPUT=$1
OUTPUT=${INPUT}.fixed
TEMP=${INPUT}.temp

echo "nom,prenom,email,gsm" > ${OUTPUT}

cat $1 | awk -F',' \
	'NR > 1 { gsub("[éèêë]", "e", $0) ; gsub("[âä]", "a", $0);gsub("[ûüù]", "u", $0); \
	gsub("[ôö]", "o", $0); gsub("[îï]", "i", $0); gsub("[ŷÿ]", "y", $0); \
	gsub("[ç]", "c", $0); gsub("[ ()+-]", "", $4); \
	t = gensub(".*([0-9]{9})", "\\1", "", $4); \
	print $1 "," $2 "," tolower($3) ",+33" t}' >> ${TEMP}
	
cat ${TEMP}| awk -F',' '$4 ~ /+33[0-9]{9}/' >> ${OUTPUT}

rm -f ${TEMP}

echo "" ${INPUT} "fixed in" ${OUTPUT} "."

exit 0
