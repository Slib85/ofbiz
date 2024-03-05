#!/bin/bash

MODPROBE=/sbin/modprobe
$MODPROBE ip_conntrack_ftp

# RESET RULES
iptables -F
iptables -X
iptables -t nat -F
iptables -t nat -X
iptables -t mangle -F
iptables -t mangle -X
iptables -P INPUT ACCEPT
iptables -P OUTPUT ACCEPT
iptables -P FORWARD ACCEPT

#keep established connections
iptables -A INPUT -m conntrack --ctstate ESTABLISHED,RELATED -j ACCEPT

#allow loopback
iptables -I INPUT 1 -i lo -j ACCEPT

## open port ftp tcp port 21 from internal office##
#iptables -A INPUT -p tcp -s 173.251.121.74 --dport 21 -j ACCEPT

## open port ssh tcp port 22 from internal office##
iptables -A INPUT -p tcp -s 173.251.121.74,108.27.213.61,100.2.251.81,67.85.242.1 --dport 22 -j ACCEPT

## open port ssh tcp port 22 from internal network##
iptables -A INPUT -p tcp -s 10.210.161.69,10.211.65.214,10.211.64.42,10.211.66.40 --dport 22 -j ACCEPT

## open http/https (Nginx) server port to all ##
iptables -A INPUT -p tcp --dport 80 -j ACCEPT
iptables -A INPUT -p tcp --dport 443 -j ACCEPT

## open http/https (Nginx) server port to internal routing
iptables -A INPUT -p tcp -s 10.211.65.214 -m multiport --dport 8080,8443,3001 -j ACCEPT
iptables -A INPUT -p tcp -s 10.211.64.42 -m multiport --dport 8080,8443,3001 -j ACCEPT
iptables -A INPUT -p tcp -s 10.211.66.40 -m multiport --dport 8080,8443,3001 -j ACCEPT

## open cups (printing service) udp/tcp port 631 for LAN users ##
iptables -A INPUT -p udp --dport 631 -j ACCEPT
iptables -A INPUT -p tcp --dport 631 -j ACCEPT

## allow time sync via NTP for lan users (open udp port 123) ##
iptables -A INPUT -p udp --dport 123 -j ACCEPT

## open tcp port 25 (smtp) for all ##
iptables -A INPUT -p tcp --dport 25 -j ACCEPT

# open dns server ports for all ##
iptables -A INPUT -p udp --dport 53 -j ACCEPT
iptables -A INPUT -p tcp --dport 53 -j ACCEPT

## open tcp port 110 (pop3) for all ##
iptables -A INPUT -p tcp --dport 110 -j ACCEPT

## open tcp port 143 (imap) for all ##
iptables -A INPUT -p tcp --dport 143 -j ACCEPT

## open access to proxy server for lan users only ##
iptables -A INPUT -p tcp --dport 3128 -j ACCEPT

## open access to mysql server for lan users only ##
iptables -A INPUT -p tcp --dport 4406 -j ACCEPT

#now block all remaining incomming ports
iptables -A INPUT -j DROP

##############
#SPECIFIC IP TO BLOCK ALL CONNECTIONS
#iptables -A INPUT -s IP_ADDRESS_HERE -j DROP
iptables -A INPUT -s 212.92.127.188 -j DROP

#Save our firewall rules
iptables-save > /etc/iptables.rules
