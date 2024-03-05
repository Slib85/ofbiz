#!/bin/sh

JVM=$1
DB=$2
DB1=$3
DB2=$4
DB3=$5
PORT=$6
USER=$7
PASS=$8

echo "Updating /usr/local/ofbiz/framework/catalina/ofbiz-component.xml"
#<property name="jvm-route" value="jvm1"/>
#<property name="jvm-route" value="$1"/>
sed -i 's/\(<property name="jvm-route" value="\)jvm1"/\1'"$JVM"'"/g' /usr/local/ofbiz/framework/catalina/ofbiz-component.xml

echo "Updating /usr/local/ofbiz/framework/common/config/general.properties"
#unique.instanceId=ofbiz1
#unique.instanceId=$1
sed -i 's/\(unique.instanceId=\)ofbiz1/\1'"$JVM"'/g' /usr/local/ofbiz/framework/common/config/general.properties

echo "Updating /usr/local/ofbiz/framework/entity/config/entityengine.xml"
#jdbc-uri="jdbc:mysql://127.0.0.1/ofbizlive?autoReconnect=true"
#jdbc-uri="jdbc:mysql://$2:$6/$3?autoReconnect=true"
sed -i 's/\(jdbc-uri="jdbc:mysql:\/\/\)127.0.0.1\/ofbizlive\(?autoReconnect=true\)/\1'"$DB"':'"$PORT"'\/'"$DB1"'\2/g' /usr/local/ofbiz/framework/entity/config/entityengine.xml

#jdbc-uri="jdbc:mysql://127.0.0.1/ofbiz2017olap?autoReconnect=true"
#jdbc-uri="jdbc:mysql://$2:$6/$4?autoReconnect=true"
sed -i 's/\(jdbc:mysql:\/\/\)127.0.0.1\/ofbiz2017olap\(?autoReconnect=true\)/\1'"$DB"':'"$PORT"'\/'"$DB2"'\2/g' /usr/local/ofbiz/framework/entity/config/entityengine.xml

#jdbc-uri="jdbc:mysql://127.0.0.1/ofbiz2017tenant?autoReconnect=true"
#jdbc-uri="jdbc:mysql://$2:$6/$5?autoReconnect=true"
sed -i 's/\(jdbc:mysql:\/\/\)127.0.0.1\/ofbiz2017tenant\(?autoReconnect=true\)/\1'"$DB"':'"$PORT"'\/'"$DB3"'\2/g' /usr/local/ofbiz/framework/entity/config/entityengine.xml

#jdbc-username="root"
#jdbc-username="$7"
sed -i 's/\(dbc-username="\)root"/\1'"$USER"'"/g' /usr/local/ofbiz/framework/entity/config/entityengine.xml

#jdbc-password="ofbiz"
#jdbc-password="$8"
sed -i 's/\(jdbc-password="\)ofbiz"/\1'"$PASS"'"/g' /usr/local/ofbiz/framework/entity/config/entityengine.xml

#ofbiz_server.sh jvm2 bigname-databases.cjccfmpisr54.us-east-2.rds.amazonaws.com ofbiz ofbizolap ofbiztenant 4406 root suyeqUz7
