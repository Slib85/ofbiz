#!/bin/sh

# info for rkg ftp
USERNAME="envelopes"
PASSWORD="run9maple"
SERVER="data.rimmkaufman.com"

# info for bing ftp
USERNAMEBING="envelopes"
PASSWORDBING="5300envelopes!"
SERVERBING="feeds.adcenter.microsoft.com"

cd /usr/local/ofbiz/etc/

# CREATE GOOGLE FEED FOR ENVELOPES
/usr/local/ofbiz/etc/generate_google_base_txt.pl

cp /tmp/google-base-feed*.txt /usr/local/ofbiz/hot-deploy/html/webapp/html/feeds/
cp /tmp/bronto-feed.txt /usr/local/ofbiz/hot-deploy/html/webapp/html/feeds/bronto-feed.txt
cp /tmp/bingshopping.txt /usr/local/ofbiz/hot-deploy/html/webapp/html/feeds/bingshopping.txt

# local directory to pickup google feed file
FILE="/usr/local/ofbiz/hot-deploy/html/webapp/html/feeds/google-base-feed.txt"
REMOTEFILE="/envelopes-google-base-feed.txt"

# remote server directory to upload backup
REMOTEDIR="/"

# login to remote server
#lftp -u $USERNAME,$PASSWORD,ftp://$SERVER <<EOF
#cd $REMOTEDIR
#put $FILE $REMOTEFILE
#quit
#EOF

# END OF GOOGLE FEED CREATION AND FTP PROCESS
