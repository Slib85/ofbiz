#!/usr/bin/env perl
use lib "/usr/local/ofbiz/etc/lib/";
use strict;
use warnings;
use XML::Writer;

use constant ENV_MAPPING_FILE => "/usr/local/ofbiz/etc/proxy_rewrite.txt";
use constant FOLD_MAPPING_FILE => "/usr/local/ofbiz/etc/folders-proxy_rewrite.txt";
use constant AE_MAPPING_FILE => "/usr/local/ofbiz/etc/ae-proxy_rewrite.txt";

##############
##############
#ENVELOPES
##############
open(my $fh, "<", ENV_MAPPING_FILE) or die "Couldn't open mappings file at " . ENV_MAPPING_FILE;

# build google base xml
my $output = IO::File->new(">/usr/local/ofbiz/hot-deploy/html/webapp/html/sitemap.xml");
my $doc = new XML::Writer(OUTPUT => $output, DATA_MODE => 1, DATA_INDENT => 4);

$doc->xmlDecl("UTF-8");
$doc->startTag("urlset", "xmlns" => "http://www.sitemaps.org/schemas/sitemap/0.9");

while (<$fh>) {
	chomp;
	my ($key, $val)    = split(/\t/);
	$doc->startTag("url");
	$doc->dataElement("loc", "https://www.envelopes.com" . $key);
	$doc->endTag();
}

$doc->endTag();
$doc->end();
close($fh) or die "Couldn't close mappings file at " . ENV_MAPPING_FILE;


##############
##############
#FOLDERS
##############
open($fh, "<", FOLD_MAPPING_FILE) or die "Couldn't open mappings file at " . FOLD_MAPPING_FILE;
# build google base xml
my $outputFold = IO::File->new(">/usr/local/ofbiz/hot-deploy/html/webapp/html/sitemap_folders.xml");
my $docFold = new XML::Writer(OUTPUT => $outputFold, DATA_MODE => 1, DATA_INDENT => 4);

$docFold->xmlDecl("UTF-8");
$docFold->startTag("urlset", "xmlns" => "http://www.sitemaps.org/schemas/sitemap/0.9");

while (<$fh>) {
	chomp;
	my ($key, $val)    = split(/\t/);
	if($key ne "/cart" && $key ne "/checkout" && $key ne "/account") {
	    $docFold->startTag("url");
	    $docFold->dataElement("loc", "https://www.folders.com" . $key);
	    $docFold->endTag();
	}
}

$docFold->endTag();
$docFold->end();
close($fh) or die "Couldn't close mappings file at " . FOLD_MAPPING_FILE;

##############
##############
#AE
##############
open($fh, "<", AE_MAPPING_FILE) or die "Couldn't open mappings file at " . AE_MAPPING_FILE;

# build google base xml
my $outputAe = IO::File->new(">/usr/local/ofbiz/hot-deploy/html/webapp/html/sitemap_ae.xml");
my $docAe = new XML::Writer(OUTPUT => $outputAe, DATA_MODE => 1, DATA_INDENT => 4);

$docAe->xmlDecl("UTF-8");
$docAe->startTag("urlset", "xmlns" => "http://www.sitemaps.org/schemas/sitemap/0.9");

while (<$fh>) {
	chomp;
	my ($key, $val)    = split(/\t/);
	$val =~ s/.$//;
	$docAe->startTag("url");
	$docAe->dataElement("loc", "https://www.actionenvelope.com" . $val);
	$docAe->endTag();
}

$docAe->endTag();
$docAe->end();
close($fh) or die "Couldn't close mappings file at " . AE_MAPPING_FILE;