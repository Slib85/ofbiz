#!/usr/bin/env perl
use strict;
use warnings;
use WWW::Selenium;

my $sel = WWW::Selenium->new( host => "localhost",
							  port => 4444,
							  browser => "*firefox",
							  browser_url => "http://splinter.envelopes.com",
							);


$sel->start;

open (MYFILE, '/usr/local/ofbiz/etc/proxy_rewrite.txt');
while(my $row = <MYFILE>) {
	my @url = split( "\t", $row );
	if(index($url[0], "login") != -1 || index($url[0], "#") != -1) {
		next;
	}
	print "Fetching: " . "http://splinter.envelopes.com" . $url[0] . "\n";
	$sel->open("http://splinter.envelopes.com" . $url[0]);
	$sel->wait_for_page_to_load(15000);
}
close(MYFILE);

$sel->stop;