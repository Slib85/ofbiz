#!/usr/bin/env perl
use lib "/usr/local/ofbiz/etc/lib/";
use strict;
use warnings;
use DBI;
use Ofbiz;
use Data::Dumper;
use Digest::MD5 qw(md5 md5_hex md5_base64);
use POSIX;
use File::stat;

my $ofbiz = Ofbiz->new(conf => "/usr/local/ofbiz/framework/entity/config/entityengine.xml", datasource => "localmysql");
my $dbh = DBI->connect($ofbiz->dsn("mysql"), $ofbiz->{dbuser}, $ofbiz->{dbpass}, {AutoCommit => 0}) or die "Couldn't connect to database: " . DBI->errstr;

my $file_handle = $dbh->prepare(qq{
	select folder as fileName from scene7_user_content where folder is not null and (folder like "uploads%" or folder like "/uploads%")
	UNION ALL
	select content_path as fileName from order_item_content where content_path is not null and (content_path like "/uploads%" or content_path like "uploads%")
});
my $totalMB = 0;

$file_handle->execute();

my @dbFileArray;

while(my $dbFile = $file_handle->fetchrow_hashref()) {
	my $dbFileName = $$dbFile{"fileName"};
	$dbFileName =~ s/^.*\///;
	push(@dbFileArray, "~" . $dbFileName);
}

print "Starting File Search:\n\n";

my $path = "/usr/local/ofbiz/uploads/";

readDirectory($path);

sub readDirectory {
	my $path = shift;
	my @files = <"$path"*>;

	foreach my $file (@files) {
		my $output = "";
		$file =~ s/.*?\///g;
		my $fileModTime = -M $path . $file;

		if (!(($fileModTime / 365) < 5)) {
			if (-d $path . $file && $file =~ /^\d+$/) {
				print "Entering Directory: $file.  This directory is " . ($fileModTime / 365) . " years old.\n";

				readDirectory("$path$file/");
			}
			elsif (-f $path . $file) {
				if (!grep(/\~$file/, @dbFileArray)) {
					my $filesize = ceil(((-s $path . $file) / (1024 * 1024)) * 1000) / 1000;
					$totalMB += $filesize;
					print "Deleting File: $file\n";
					unlink $path . $file;
				}
			}
		}
	}

	print "Current Total: $totalMB MB\n";
}

print "Total: $totalMB MB\n";

$file_handle->finish();
$dbh->disconnect();

