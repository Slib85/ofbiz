use strict;
use warnings;
use File::Find qw(finddepth);

my $path = '../hot-deploy/html/webapp/html/css/';
my $ignorePath = $path.'addons/';
my @siteList = ("envelopes", "folders");

finddepth(sub {
	my $file = $_;
	return if($file eq '.' || $file eq '..');
	if ($File::Find::name =~ /\.less$/ && !($File::Find::name =~ /lib\.less$/) && !($File::Find::name =~ /^\Q$ignorePath/)) {
		if ($file =~ /^bigName.*?\.less$/) {
			foreach my $site (@siteList) {
				my $newPath = $File::Find::dir;
				if ($site ne "envelopes") { $newPath =~ s/^\Q$path/$path$site\//; }
				my $newFile = $file;
				$newFile =~ s/bigName(.*?)\.less$/$1.css/i;
				my $tempLessFile = $File::Find::name;
				$tempLessFile =~ s/\.less$/temp.less/;

				open(my $fh, "<", $File::Find::name) or die "Could not open file " . $File::Find::name . " $!";
				open(my $fh2, ">", $tempLessFile) or die "Could not open file " . $tempLessFile . " $!";

				my $output = qq|\@import "$path/| . ($site eq "envelopes" ? "" : $site . "/") . qq|lib.less";|;

				while (my $row = <$fh>) {
					chomp $row;
					$output .= $row;
				}

				print $fh2 $output;

				close $fh2;
				close $fh;

				system("lessc " . $tempLessFile . " > " . $newPath . "/" . lcfirst($newFile));

				unlink $tempLessFile;
			}
		}
		else {
			my $newFile = $file;
			$newFile =~ s/\.less$/.css/i;
			system("lessc " . $File::Find::name . " > " . $File::Find::dir . "/$newFile");
		}

	}

}, $path);

