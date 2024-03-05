#!/bin/sh

cd /usr/local/ofbiz/etc/

# CREATE CERTONA FEEDS FOR ENVELOPES
/usr/local/ofbiz/etc/certona_feed.pl
/usr/local/ofbiz/etc/certona_feed_folders.pl

# login to remote server
lftp sftp://bigname:'wfBD7t945Pt7&d7X'@sftp.res-x.com <<EOF
put /tmp/certona_envelopes.xml
put /tmp/certona_folders.xml
quit
EOF

# END OF CERTONA FEED CREATION AND FTP PROCESS
