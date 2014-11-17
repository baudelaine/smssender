# How to Deploy and Run SMSSender (a Java Twilio Web App) #

## Overview of the app ##

Visit (Chrome, Safari, ~~Firefox~~, ~~Internet Explorer~~) [**SMSSender**](http://smssender.mybluemix.net) demo (with a dummy SMSFactory) version.

This is a Java Web App that uses the following cloud services:

- Twilio (Build apps that communicate. Integrate voice, messaging and VoIP into your web and mobile apps.)

It demonstrates in ** 3 steps ** how a Java Web App bound to Twilio service on IBM Bluemix is able to send sms.

1. Enter phone numbers comma separated list in *** `Gsm(s)` *** text area:

       +33612345678,+33623456789...
      
 or simply *** `Browse...` *** and *** `Upload` *** a 4 columns comma separated values (CSV) file.

- Write your own message in *** `Message`*** text area or load the *** `Sample Message` ***:

- Then *** `Send` *** sms.

## Prerequisites ##

Unless you provide valid Twilio account, token and number(s), SMSSender will run in Dummy mode, running a Dummy SMSFactory which just show how the app is running but sending nothing. So, in order to send real sms follow the first two steps below

- You first need to signup for a Twilio account [**Try Twilio**](https://www.twilio.com/try-twilio) to get both an ** ACCOUNT SID ** (
the 34 character Account identifier - starting with 'AC'. This can be found on your Twilio dashboard page) and a ** AUTH TOKEN ** (the 32 character AuthToken. This can be found on your Twilio dashboard page.)

- In order to send sms you'll first need to credit your Twilio account [**Add Funds **](https://www.twilio.com/user/billing/add-funds)
then to buy a sms-enable phone number [**Buy A Number**](https://www.twilio.com/user/account/phone-numbers/search)
#### Be aware there's no sms-enable phone number available for France. Best practice is to buy a phone number with a country and an area code near the datacenter where your Bluemix app will run. The best choice for now - august 2014 - is to buy a US number located in Dallas: +1 (469) NNN-NNNN. ####

- Install the [**cf**](https://github.com/cloudfoundry/cli/releases) command line tool that will be used to upload and manage your application. If you've previously installed an older version of the cf tool, make sure you are now using v6 of cf by passing it the -v flag:

      $ cf -v
- Install the [**git**](http://git-scm.com/downloads) command line tools that will be used to import (*clone*) the project to your environment.
      
- (***Optionnal***) If you need to change java code (in [***app/src/com/baudelaine/bluemix/sms/***]()) then, before pushing app to Bluemix, for the change to take effect, you'll need to re-compile with the [**javac**](http://www.oracle.com/technetwork/java/javase/downloads/index.html) command (available in [**JDK**](http://www.oracle.com/technetwork/java/javase/downloads/index.html) and not in **~~JRE~~**). Once javac is in your PATH, change to the *** root project directory *** (it should be something like [***<where you hit the git clone command>/SMSSender/***]()) and execute the following command:
        .../SMSSender$ javac -cp wlp/*:app/WebContent/WEB-INF/lib/* -d app/WebContent/WEB-INF/classes/ app/src/com/baudelaine/bluemix/sms/*.java
     
## Download ##

- Clone the project in your environment:
      $ git clone https://hub.jazz.net/git/baudelaine/SMSSender
      
## Setup Properties ##

- Change from [***<where you hit the git clone command>***]() to your *** root project directory ***:

	  $ cd SMSSender
		
- Edit the *** [manifest.yml]() *** to tell Bluemix about your <App Name> and your <Twilio account>

        applications:
		- host: <App Name>
		  disk: 1024M
		  name: <App Name>
		  path: ./app/WebContent/
		  domain: mybluemix.net
		  mem: 512M
		  instances: 1
		  env:
		    TWILIO_ACCOUNT_SID: "<the 34 character Account identifier (starting with 'AC'). This can be found on your Twilio dashboard page.>"
		    TWILIO_AUTH_TOKEN: "<the 32 character AuthToken. This can be found on your Twilio dashboard page.>"
		    TWILIO_PHONES: "<sms-enable number commas separated list. This can be found on your Twilio numbers page.>"
		  services:
		  - <App Name>-svc
		  
	and save it to something like:
	
        applications:
		- host: myApp0
		  disk: 1024M
		  name: myApp0
		  path: ./app/WebContent/
		  domain: mybluemix.net
		  mem: 512M
		  instances: 1
		  env:
		    TWILIO_ACCOUNT_SID: "AC????????????????????????????????"
		    TWILIO_AUTH_TOKEN: "????????????????????????????????"
		    TWILIO_PHONES: "+1 234-567-8901,+1 345-678-9012,+1 456-789-0123"
		  services:
		  - myApp0-svc
		  
	####Warning !!! In case you choose to deploy your app from DevOps Services, * env: * values from the manifest.yml will be ignored. For your Twilio account to be known by your web app, you should edit * [app/WebContent/conf/twilio.properties]() * and save it to something like:####
	
		TWILIO_ACCOUNT_SID="AC????????????????????????????????"
		TWILIO_AUTH_TOKEN="????????????????????????????????"
		TWILIO_PHONES="+1 234-567-8901,+1 345-678-9012,+1 456-789-0123"
		
- (***Optionnal***) If you need to load *** `Contacts` *** in advance for futur use then edit *** [app/WebContent/files/event.contacts.csv]() *** and save it to something like:
      nom,prenom,email,gsm 
	  nom0,prenom0,email0@dom.net,+33612345678 
	  nom1,prenom1,email1@dom.net,+33623456789
	  ....

- (***Optionnal***) If you need to load *** `Best Regards` *** in advance for futur use then edit *** [app/WebContent/files/event.message]() *** and save it to something like:
	  Sent from IBM Bluemix: Hi ${prenom} ${nom}, IBM would like to thank you for coming to this Bluemix event. Best Regards from IBM!

## Deploy to IBM Bluemix ##

You should now be ready to deploy the java web app:

- Login to Bluemix:

	  $ cf l -a https://api.ng.bluemix.net -u <Bluemix userid> -p <Bluemix passwd>
	  
- Create Twilio service with the name you choose above in the [***manifest.yml***]():

      $ cf cs Twilio user-provided myApp0-svc

- If you're OK with your [***manifest.yml***]() content then change to your *** root project directory *** (it should be something like [***<where you hit the git clone command>/SMSSender/***]()) and simply push your java web app to Bluemix:
	      
	  .../SMSSender$ cf p

## Best Practises and Troubleshooting ##

- #### Following internet browers have been tested:	Chrome (Windows, Linux, Android), Safari (iOS), **~~Firefox (Windows, Linux, Android)~~**, **~~Internet Explorer~~**. ####

- #### Message lenght must not exceed the 160 characters limit. Don't forget to take ${nom} and ${prenom} into account. If you don't Twilio won't deliver the message. ####

- #### Avoid [âäéèêëîïôöûüùŷÿç] characters in both message and CSV file content when not sure to be UTF-8. If you don't, Twilio will truncate message each time it parse a weird character.  A shell script to help formating CSV files: ####

		#/bin/sh
		#
		#Take a 4 columns commas separated values CSV file with first row as column name for first parameter.
		#
		#************** Sample Input CSV ***************
		#Nom,Prénom,Adresse mail,Mobile 
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

- #### In case you choose to deploy your app from DevOps Services, * env: * values from the manifest.yml will be ignored. For your Twilio account to be known by your web app, you should edit * [app/WebContent/conf/twilio.properties]() * and save it to something like: ####
	
		TWILIO_ACCOUNT_SID="AC????????????????????????????????"
		TWILIO_AUTH_TOKEN="????????????????????????????????"
		TWILIO_PHONES="+1 234-567-8901,+1 345-678-9012,+1 456-789-0123"

- #### In case you choose to deploy your app from DevOps Services, * services: * values from the manifest.yml will be ignored and no binding will occured. For your app to be bound correctly, you should first stop your app: ####

	  $ cf sp myApp0
	  
	#### then bind your service and start your app again: ####
	
	  $ cf bs myApp0 myApp0-svc; cf st myApp0

- #### In case you need to change Twilio parameters and don't want to deploy again: ####

	  $ cf set-env myApp0 TWILIO_ACCOUNT_SID "AC????????????????????????????????" 
	  $ cf set-env myApp0 TWILIO_AUTH_TOKEN "????????????????????????????????"  
	  $ cf set-env myApp0 TWILIO_PHONES "+1 234-567-8901,+1 345-678-9012,+1 456-789-0123"
  
  #### then restart your app: ####
  	$ cf sp myApp0; cf st myApp0

	

