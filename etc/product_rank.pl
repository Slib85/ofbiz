#!/usr/bin/env perl
use lib "/usr/local/ofbiz/etc/lib/";
use strict;
use warnings;
use DBI;
use Ofbiz;

my $ofbiz = Ofbiz->new(conf => "/usr/local/ofbiz/framework/entity/config/entityengine.xml", datasource => "localmysql");
my $dbh = DBI->connect($ofbiz->dsn("mysql"), $ofbiz->{dbuser}, $ofbiz->{dbpass}, {AutoCommit => 0}) or die "Couldn't connect to database: " . DBI->errstr;

# delete old data
my $delete_handle = $dbh->prepare(qq{
	DELETE FROM product_recommendation
});

# retreive products and total orders order by desc
my $sales_rank = $dbh->prepare(qq{
	SELECT
		cw.variant_product_id, COUNT(DISTINCT oi.order_id) AS orders
	FROM
		color_warehouse cw
			INNER JOIN
		order_item oi ON cw.variant_product_id = oi.product_id
	GROUP BY cw.variant_product_id
	ORDER BY orders DESC
});

# retreive other products sold with this product
my $sku_order_list = $dbh->prepare(qq{
	SELECT
		oi.order_id, oi.order_item_seq_id, oi.product_id
	FROM
		order_item oi
			INNER JOIN
		color_warehouse cw ON oi.product_id = cw.variant_product_id
	WHERE
		order_id IN (SELECT
				order_id
			FROM
				order_item
			WHERE
				product_id = ?)
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

my $update_rank = $dbh->prepare(qq{
	INSERT INTO product_recommendation (rank, normalized_rank, product_id) VALUES (?, ?, ?)
});

die "Couldn't prepare queries; aborting" unless defined $delete_handle && $sales_rank;

my $success = 1;
$success &&= $delete_handle->execute();
$success &&= $sales_rank->execute();

my $maxValue = 1;
my $minValue = 1;

my $loopIter = 0;
while (my($productId, $rankId) = $sales_rank->fetchrow_array()) {
    if($loopIter == 0) {
        $maxValue = $rankId;
    }

    my $normalizedRank = ($rankId - $minValue)/($maxValue - $minValue);
    if($normalizedRank == 0) {
        $normalizedRank = 0.000001;
    }
	$success &&= $update_rank->execute($rankId, $normalizedRank, $productId);

	my %siblingHash = ();
	$success &&= $sku_order_list->execute($productId);
	while (my($orderId, $orderItemSeqId, $siblingProdId) = $sku_order_list->fetchrow_array()) {
		if($siblingProdId ne $productId) {
			if(exists $siblingHash{$siblingProdId}) {
				$siblingHash{$siblingProdId} = ($siblingHash{$siblingProdId} + 1)
			} else {
				$siblingHash{$siblingProdId} = 1
			}
		}
	}

	my $count = 1;
	foreach my $sibling (sort { $siblingHash{$b} <=> $siblingHash{$a} } keys %siblingHash) {
		if($count <= 10) {
		    my %features = ();
	    	$success &&= $features_handle->execute($productId);
            while(my $feature = $features_handle->fetchrow_hashref()) {
                $features{ lc $$feature{"product_feature_type_id"} } = $feature;
            }

            if(defined $features{"size"} && $features{"size"}->{"description"} eq "9 x 12" && $count == 4) {
                #printf $productId . " - " . $sibling . " - " . $siblingHash{$sibling} . "\n";
                my $sql    = "UPDATE product_recommendation SET recommendation" . $count . " = '" . "LUXMLR-M07" . "' WHERE product_id = '" . $productId . "'";
                #print $sql . "\n";
                my $result = $dbh->do($sql, undef);
                $count++;
            } elsif(defined $features{"size"} && $features{"size"}->{"description"} eq "9 x 12" && $count == 5) {
                #printf $productId . " - " . $sibling . " - " . $siblingHash{$sibling} . "\n";
                my $sql    = "UPDATE product_recommendation SET recommendation" . $count . " = '" . "LUXMLR-GB" . "' WHERE product_id = '" . $productId . "'";
                #print $sql . "\n";
                my $result = $dbh->do($sql, undef);
                $count++;
            } else {
                #printf $productId . " - " . $sibling . " - " . $siblingHash{$sibling} . "\n";
                my $sql    = "UPDATE product_recommendation SET recommendation" . $count . " = '" . $sibling . "' WHERE product_id = '" . $productId . "'";
                #print $sql . "\n";
                my $result = $dbh->do($sql, undef);
                $count++;
            }
		}
	}

    $loopIter++;
}

my @taxSKUs = ("7487-W2", "7486-W2", "7485-W2", "7489-W2", "WS-7496", "WS-7494", "WS-7484", "7489-W2-TAX", "WS-7494-TAX", "WS-7484-TAX");
foreach my $skuToUpdate (@taxSKUs) {
    my $taxCounter = 1;
    foreach my $taxSKU (@taxSKUs) {
        if($taxSKU ne $skuToUpdate) {
            my $sql = "UPDATE product_recommendation SET recommendation" . $taxCounter . " = '" . $taxSKU . "' where product_id = '" . $skuToUpdate . "'";
            my $result = $dbh->do($sql, undef);
            $taxCounter++;
        }
    }
}

$delete_handle->finish();
$sales_rank->finish();
$update_rank->finish();
$features_handle->finish();

# end transaction
my $result = ($success ? $dbh->commit : $dbh->rollback);

unless($result) {
  die "Couldn't finish transaction: " . $dbh->errstr;
}

$dbh->disconnect();