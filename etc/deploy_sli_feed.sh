#!/bin/sh

cd /usr/local/ofbiz/etc/

# CREATE SLI FEEDS FOR ACTIONENVELOPE AND ENVELOPES
/usr/local/ofbiz/etc/sli_feed.pl > sli_envelopes.xml

# login to remote server
lftp -u envelopes,r5vid6xq sftp://secure1.sli-systems.com <<EOF
cd /incoming
put /usr/local/ofbiz/etc/sli_envelopes.xml sli_envelopes.xml
quit
EOF

# END OF SLI FEED CREATION AND FTP PROCESS
