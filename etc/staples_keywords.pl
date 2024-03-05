#!/usr/bin/env perl
use lib "/usr/local/ofbiz/etc/lib/";
use strict;
use warnings;
use DBI;
use Ofbiz;
use Data::Dumper;
use File::Basename;
use Text::CSV_XS;

my $ofbiz = Ofbiz->new(conf => "/usr/local/ofbiz/framework/entity/config/entityengine.xml", datasource => "localmysql");
my $dbh = DBI->connect($ofbiz->dsn("mysql"), $ofbiz->{dbuser}, $ofbiz->{dbpass}, {AutoCommit => 0}) or die "Couldn't connect to database: " . DBI->errstr;

my $variants_handle = $dbh->prepare(qq{
	select 		p.parent_product_id 			as virtual_product_id,
				p.product_name 					as product_name,
				p.primary_product_category_id	as primary_product_category_id,
				p.product_id 					as variant_product_id,
				p.on_sale 						as on_sale,
				p.on_clearance					as on_clearance,
				p.product_type_id 				as product_type_id,
				p.is_printable 					as is_printable,
				p.product_height 				as product_height,
				p.product_width 				as product_width,
				p.product_depth 				as product_depth,
				p.plain_price_description		as plain_price,
				p.print_price_description		as print_price,
				p.created_stamp                 as created_stamp,
				p.meta_title                    as meta_title,
				p.has_sample					as has_sample,
				p.has_white_ink					as has_white_ink,
				p.long_description              as long_description,
				p.color_description             as color_description
	from   		product p
	where  		p.product_id = ?
});

my $features_handle = $dbh->prepare(qq{
	select		pfa.product_feature_id,
				pf.product_feature_type_id,
				pf.description,
				pf.default_sequence_num
	from		product_feature_appl pfa
	inner join	product_feature pf
	on			pfa.product_feature_id = pf.product_feature_id
	where		pfa.product_id = ?
});

my $productCategory = $dbh->prepare(qq{
	select description from product_category where product_category_id = ?
});

my $success = 1;

my $HEADER = [
    'ItemNumber',
    'BuyerNumber',
    'InnerPack',
    'ItemID',
    'Keywords'
];

my $staplesFile = Text::CSV_XS->new ({ binary => 1, sep_char => ',', eol => $/, quote_char => '"' });
open(STAPLES, '>/tmp/staples_keywords.csv') or die "Couldn't open filehandle: $!";
$staplesFile->print(\*STAPLES, $HEADER);

my $staplesList = Text::CSV_XS->new ({ binary => 1 });
open (INPUTFILE, "<", "/tmp/StaplesSkus_NeedMetaKeywords.csv") or die "Couldn't open filehandle: $!";
while(<INPUTFILE>) {
    next if ($. == 1);
    if($staplesList->parse($_)) {
        my @columns = $staplesList->fields();

        my @keywords;
        my $sku = $columns[3];
        $sku =~ s/^[^:]*://;
        $sku =~ s/^\s+|\s+$//g; #remove trailing and leading spaces


        $success &&= $variants_handle->execute($sku);
        my $product = $variants_handle->fetchrow_hashref();
        if(defined $product) {
            $success &&= $productCategory->execute($$product{"primary_product_category_id"});
            my $catDesc = $productCategory->fetchrow_array();

            if(defined $catDesc) {
                @keywords = splitStr(\@keywords, $catDesc, 1);
            }

            $success &&= $features_handle->execute($sku);
            while(my $feature = $features_handle->fetchrow_hashref()) {
                if($$feature{"product_feature_type_id"} ne "AVAILABILITY" && $$feature{"product_feature_type_id"} ne "INKJET" && $$feature{"product_feature_type_id"} ne "LASER" && $$feature{"product_feature_type_id"} ne "PRINTABLE") {
                    if($$feature{"product_feature_type_id"} eq "SIZE") {
                        @keywords = splitStr(\@keywords, $$feature{"description"}, 0);
                    }
                    @keywords = splitStr(\@keywords, $$feature{"description"}, 1);
                }
            }

            if(defined $$product{"long_description"}) {
                @keywords = splitStr(\@keywords, $$product{"long_description"}, 1);
            }

            if(defined $$product{"color_description"}) {
                @keywords = splitStr(\@keywords, $$product{"color_description"}, 1);
            }

            my $ROW = [
                $columns[0],
                $columns[1],
                $columns[2],
                $columns[3],
                join(",", @keywords)
            ];

            #write data back
            $staplesFile->print(\*STAPLES, $ROW);
        }
    }
}
close INPUTFILE or die "Couldn't close file.";
close STAPLES or die "Couldn't close filehandle: $!";

$variants_handle->finish();
$features_handle->finish();
$productCategory->finish();

# end transaction
my $result = ($success ? $dbh->commit : $dbh->rollback);

unless($result) {
  die "Couldn't finish transaction: " . $dbh->errstr;
}

$dbh->disconnect();

sub splitStr {
    my ($keywords, $str, $toSplit) = @_;
    my @newKeywords = @{$keywords};
    my %keywordsHash = map { $_ => 1 } @newKeywords;

    if($toSplit == 0) {
        if(!exists($keywordsHash{$str})) {
            push @newKeywords, $str;
        }
    } else {
        my @words = split / /, $str;
        foreach(@words) {
            $_ =~ s/^\s+|\s+$//g; #remove trailing and leading spaces
            if($_ ne "" && !exists($keywordsHash{$_})) {
                push @newKeywords, $_;
            }
        }
    }

    return @newKeywords;
}