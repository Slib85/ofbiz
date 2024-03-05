#!/usr/bin/perl -w
use strict;
use warnings;
use Text::CSV;

my $file = "taxCodes.csv";
my $csv = Text::CSV->new({ binary => 1, eol => $/ }) or die "Cannot use CSV: ".Text::CSV->error_diag();
open(my $fh, '<', $file) or die "Can't read file '$file' [$!]\n";
my $output;

while (my $line = <$fh>) {
	if ($csv->parse($line."\n")) {
		my @fields = $csv->fields();
		my $taxRate = $fields[2];
		$taxRate =~ s/\%//g;
		my @zipList = split(",", $fields[6]);
		foreach my $zip (@zipList) {
			$output .= qq|<ZipSalesTaxLookup city="_NA_" zipCode="$zip" stateCode="$fields[3]" county="$fields[4]" fromDate="2017-7-1 0:00:00.0" comboSalesTax="| . ($taxRate / 100) . qq|" comboUseTax="| . ($taxRate / 100) . qq|" groupId="$fields[0]" />\n|;
		}
    }
}

close $fh;

open($fh, '>', "taxCodes.txt");
print $fh $output;
close $fh;