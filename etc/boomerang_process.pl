#!/usr/bin/env perl
use lib "/usr/local/ofbiz/etc/lib/";
use strict;
use warnings;
use DBI;
use Ofbiz;
use Data::Dumper;
use Text::CSV_XS;
use DateTime;
use Net::SFTP::Foreign;
use Email::Send;
use Email::Simple::Creator;
use Email::Send::SMTP::Gmail;

my $ofbiz = Ofbiz->new(conf => "/usr/local/ofbiz/framework/entity/config/entityengine.xml", datasource => "localmysql");
my $dbh = DBI->connect($ofbiz->dsn("mysql"), $ofbiz->{dbuser}, $ofbiz->{dbpass}, {AutoCommit => 0}) or die "Couldn't connect to database: " . DBI->errstr;

# retrieve product features
my $price_check_handle = $dbh->prepare(qq{
    SELECT price as price,
            colors as colors
    FROM product_price
    WHERE
        product_id = ? AND quantity = ?
            AND colors IN (1,2,4);
});

# retrieve product features
my $price_handle = $dbh->prepare(qq{
    SELECT price as price
    FROM product_price
    WHERE
        product_id = ? AND quantity = ?
            AND colors = 0;
});

my $update_handle = $dbh->prepare(qq{
    UPDATE product_price
    SET
        price = ?
    WHERE
        product_id = ? AND quantity = ?
            AND colors = ?;
});

my $update_original_handle = $dbh->prepare(qq{
    UPDATE product_price
    SET
        original_price = ?
    WHERE
        product_id = ? AND quantity = ?
            AND colors = ?;
});

my $product_handle = $dbh->prepare(qq{
    SELECT on_sale as on_sale,
           on_clearance as on_clearance
    FROM product
    WHERE
        product_id = ?;
});

my $success = 1;
my $tdt = DateTime->now;
$tdt = $tdt->subtract(days => 1);

open (LOG, '>', '/tmp/boomerang_log_' . $tdt->ymd('') . '.txt');
print LOG ("SKU\tQuantity\tOld Price\tNew Price\tColors\n");

my $fileName = 'boomerangoutput_' . $tdt->ymd('') . '.csv';
my $localFilePath = '/tmp/' . $fileName;
getData();

my $counter = 0;
my $boomerangData = Text::CSV_XS->new ({ binary => 1 });
open (INPUTFILE, "<", $localFilePath) or die "Couldn't open filehandle: $!";
while(<INPUTFILE>) {
    next if ($. == 1);
    if($boomerangData->parse($_)) {
        my @columns = $boomerangData->fields();
        my $sku = $columns[0];
        my $price = $columns[2];
        my $quantity = $columns[4];

        $success &&= $price_handle->execute($sku, $quantity);
        my $oldPrice = $price_handle->fetchrow_array();
        if(defined $oldPrice) {
            #update the plain product to the new price
            print LOG ($sku . "\t" . $quantity . "\t" . $oldPrice . "\t" . $price . "\t0\n");
            $success &&= $update_handle->execute($price, $sku, $quantity, 0) or die "Couldn't save data: " . $dbh->errstr;

            #get clearance or sale data
            $success &&= $product_handle->execute($sku) or die "Couldn't get data: " . $dbh->errstr;
            my ($onSale, $onClearance) = $price_handle->fetchrow_array();
            if(defined $onSale && $onSale eq "Y") {
                my $newOriginalPrice = (($price*1)/0.75);
                $success &&= $update_original_handle->execute($newOriginalPrice, $sku, $quantity, 0) or die "Couldn't save data: " . $dbh->errstr;
            } elsif(defined $onClearance && $onClearance eq "Y") {
                my $newOriginalPrice = (($price*1)/0.50);
                $success &&= $update_original_handle->execute($newOriginalPrice, $sku, $quantity, 0) or die "Couldn't save data: " . $dbh->errstr;
            }

            #get the change in price
            my $plainPriceChange = ($price*1)-($oldPrice*1);

            #update the printed prices at the same rate of the plain
            $success &&= $price_check_handle->execute($sku, $quantity);
            while (my $printPricing = $price_check_handle->fetchrow_hashref()) {
                if(defined $$printPricing{"price"}) {
                    my $newPrintPrice = ($$printPricing{"price"}*1)+($plainPriceChange*1);
                    print LOG ($sku . "\t" . $quantity . "\t" . $$printPricing{"price"} . "\t" . $newPrintPrice . "\t" . $$printPricing{"colors"} . "\n");
                    $success &&= $update_handle->execute($newPrintPrice, $sku, $quantity, $$printPricing{"colors"}) or die "Couldn't save data: " . $dbh->errstr;

                    if(defined $onSale && $onSale eq "Y") {
                        my $newOriginalPrice = ($newPrintPrice/0.75);
                        $success &&= $update_original_handle->execute($newOriginalPrice, $sku, $quantity, $$printPricing{"colors"}) or die "Couldn't save data: " . $dbh->errstr;
                    } elsif(defined $onClearance && $onClearance eq "Y") {
                        my $newOriginalPrice = ($newPrintPrice/0.50);
                        $success &&= $update_original_handle->execute($newOriginalPrice, $sku, $quantity, $$printPricing{"colors"}) or die "Couldn't save data: " . $dbh->errstr;
                    }
                }
            }
        }
    }
}
close INPUTFILE or die "Couldn't close channel mappings file at " . $localFilePath;
close (LOG);

sendEmail();

sub getData {
    my $sftp = Net::SFTP::Foreign->new('sftp.envelopes.rboomerang.com', user => 'sftp-env-use', password => 'SgA49ArEyaTGy5CK', autodie => 1) or die 'Cannot connect to some.host.name: $@';
    $sftp->setcwd('/prod/OUT');
    $sftp->get($fileName, $localFilePath);
    $sftp->disconnect or die 'Error closing ftp connection: $!';
}

sub sendEmail {
    my $body = $_[0];
	my $mail=Email::Send::SMTP::Gmail->new( -smtp=>'smtp.gmail.com', -login=>'service@envelopes.com', -pass=>'10Window');
	my @receipients = ( 'shoab@envelopes.com', 'michelle.stern@bigname.com', 'whitney@bigname.com', 'laura@bigname.com' );
	for (@receipients){
		$mail->send(
            -to=>$_,
            -subject=> 'Boomerang price update: ' . $tdt->ymd('/'),
            -verbose=>'1',
            -body=> '',
            -attachments=>'/tmp/boomerang_log_' . $tdt->ymd('') . '.txt'
		);
	}
	$mail->bye;
}

# end transaction
my $result = ($success ? $dbh->commit : $dbh->rollback);

unless($result) {
    die "Couldn't finish transaction: " . $dbh->errstr;
}

$product_handle->finish();
$price_check_handle->finish();
$price_handle->finish();
$update_handle->finish();
$update_original_handle->finish();
$dbh->disconnect();