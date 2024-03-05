#!/usr/bin/env perl
use lib "/usr/local/ofbiz/etc/lib/";
use strict;
use warnings;
use XML::Writer;
use IO::File;

#use constant MAPPING_FILE => "/Users/mikeh/dev/ofbiz/etc/proxy_rewrite.txt";
use constant MAPPING_FILE => "/usr/local/ofbiz/etc/proxy_rewrite.txt";

open(my $fh, "<", MAPPING_FILE) or die "Couldn't open mappings file at " . MAPPING_FILE;

#my $output = IO::File->new(">../hot-deploy/html/webapp/html/feeds/rss-feed.xml");
my $output = IO::File->new(">/usr/local/ofbiz/hot-deploy/html/webapp/html/feeds/rss-feed.xml");
my $writer = XML::Writer->new(OUTPUT => $output);

print $output qq|<?xml version="1.0" encoding="UTF-8" ?>\n|;

$writer->startTag(
	"rss",
	"version" => "2.0");

print $output qq|\n|;

while (<$fh>) {
	chomp;
	my $link = (split /\t/, $_)[0];
	print $output qq|\t|;
	$writer->startTag("channel");
	print $output qq|\n\t\t|;
	$writer->startTag("title");
	$writer->characters("");
	$writer->endTag("title");
	print $output qq|\n\t\t|;
	$writer->startTag("link");
	$writer->characters("http://www.envelopes.com" . $link);
	$writer->endTag("link");
	print $output qq|\n\t\t|;
	$writer->startTag("description");
	$writer->characters("");
	$writer->endTag("description");
	print $output qq|\n\t|;
	$writer->endTag("channel");
	print $output qq|\n|;
}
$writer->endTag("rss");
$writer->end();
$output->close();
