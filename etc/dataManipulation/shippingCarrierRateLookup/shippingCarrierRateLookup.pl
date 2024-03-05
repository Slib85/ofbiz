#!/user/bin/perl -w
use strict;
use warnings;
use POSIX;
use Data::Dumper qw(Dumper);
use Text::ParseWords;

my @files = <files/*>;
my $outputFile = 'inserts.sql';

open (my $fhout, '>', $outputFile) or die $!;

foreach my $file (@files) {
    open (my $fhin, '<', $file) or die $!;

    my @zoneList = ();
    my $counter = 0;
    my $fileName = $file;
    $fileName =~ s/^(?:.*?\/|)(.*?)\.[a-zA-Z]+$/$1/;

    while(my $row = <$fhin>) {
        chomp $row;

        my ($quantity, @zones) = split(/\t/, $row);
        $quantity =~ s/(?:\n|\r|\s)//g;

        if ($counter == 0) {
            foreach my $zone (@zones) {
                $zone =~ s/^.*?(?:0|)(\d+)/$1/;
                $zone =~ s/(?:\n|\r|\s)//g;
                push (@zoneList, $zone);
            }
        } elsif ($quantity =~ m/^\d+$/) {
            for(my $i = 0; $i < @zones; $i++) {
                my $price = $zones[$i];
                $price =~ s/(?:\$|\s|\n|\r)//g;
                print $fhout qq|INSERT INTO shipping_carrier_rate_lookup(carrier_id, zone, shipping_method_id, weight_break_id, rate_per_unit) VALUES("FEDEX", "$zoneList[$i]", "$fileName", (SELECT quantity_break_id FROM quantity_break WHERE $quantity BETWEEN from_quantity AND thru_quantity ORDER BY thru_quantity ASC LIMIT 1), | . sprintf("%.6f", $price) . qq|) ON DUPLICATE KEY UPDATE rate_per_unit = | . sprintf("%.6f", $price) . qq|;\n|
            }
        }

        $counter++;
    }

    close ($fhin);
}

close ($fhout);
#INSERT INTO shipping_carrier_rate_lookup(carrier_id, zone, shipping_method_id, weight_break_id, rate_per_unit) 
#VALUES("FEDEX", "2", "TWO_DAY", (SELECT quantity_break_id FROM quantity_break WHERE 1 BETWEEN from_quantity AND thru_quantity), 7.410000) 
#ON DUPLICATE KEY UPDATE rate_per_unit = 7.410000;

