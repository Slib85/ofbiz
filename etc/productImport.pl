#/usr/lib/perl
use lib "/usr/local/ofbiz/etc/lib/";
use strict;
use warnings;
use DBI;
use Ofbiz;
use Data::Dumper;
use JSON;

my $filename = 'PIM.json';
my $data;

if (open (my $json_str, $filename)) {
    local $/ = undef;
    my $json = JSON->new;
    $data = $json->decode(<$json_str>);
    close($json_stream);
}

#my $newData = {};
#
#foreach my $key (keys %$data) {
#    print "$key\n";
#    #WEBSITES IS KEY
#    foreach my $key2 (@{$data->{$key}}) {
#        print "$key2\n";
#        foreach my $key3 (keys %$key2) {
#            print "$key3: " . $key2->{$key3} . "\n";
#        }
#        exit;
#        # FAMILIES HAS 2 ARRAY INDEX
#        #foreach my $key3 (@$key2[0])
#        #print @$key2[0];
#    }
#}

foreach my $parentProduct (@{$data->{"PRODUCTS"}}) {
    ##########################################
    # Step 1 
    # Import parent product.
    ##########################################

    my $productId = $parentProduct->{"PRODUCT_ID"};
    my $name = $parentProduct->{"NAME"};
    my $type = $parentProduct->{"FAMILY_ID"};
    #my $active = $parentProduct->{"ACTIVE"};
    my $discontinuationDate = ($parentProduct->{"DISCONTINUED"} == "N" ? undef : "1985-06-01 00:00:00");
    my $sizeCode = $parentProduct->{"SIZE_CODE"};
    my $printable = ($parentProduct->{"PRINTABLE"} == "" ? "N" : $parentProduct->{"PRINTABLE"});
    my $size = $parentProduct->{"SIZE"};
    my $metricSize = $parentProduct->{"METRIC_SIZE"};
    my $imageSize = $parentProduct->{"IMAGE_SIZE"};
    my $hasRushProduction = (defined $parentProduct->{"RUSH_LEAD_TIME"} && $parentProduct->{"RUSH_LEAD_TIME"} != "" ? "Y" : "N");
    my $rushLeadTime = $parentProduct->{"RUSH_LEAD_TIME"};
    my $standardLeadTime = $parentProduct->{"STANDARD_LEAD_TIME"};
    my $plainLeadTime = $parentProduct->{"PLAIN_LEAD_TIME"};

    #TODO: add PRIMARY_PRODUCT_CATEGORY_ID
    my $parentProduct_handle = $dbh->prepare(qq{
        INSERT INTO product(
            PRODUCT_ID, 
            PRODUCT_TYPE_ID, 
            SALES_DISCONTINUATION_DATE, 
            INTERNAL_NAME, 
            PRODUCT_NAME, 
            IS_VIRTUAL, 
            IS_VARIANT, 
            HAS_RUSH_PRODUCTION
        )
        VALUES (
            ?, ?, ?, ?, ?, ?, ?, ?, ?
        )
        ON DUPLICATE KEY UPDATE
            PRODUCT_TYPE_ID = ?,
            SALES_DISCONTINUATION_DATE = ?,
            INTERNAL_NAME = ?,
            PRODUCT_NAME = ?,
            IS_VIRTUAL = ?, 
            IS_VARIANT = ?, 
            HAS_RUSH_PRODUCTION = ?
    });

    $parentProduct_handle->execute(
        $productId, $type, $discontinuationDate, $name, $name, "Y", "N", $hasRushProduction,
                    $type, $discontinuationDate, $name, $name, "Y", "N", $hasRushProduction
    );

    ##########################################
    # Step 2 
    # Import parent product assets.
    ##########################################
    foreach my $parentProductAsset (@{$parentProduct->{"DIGITAL_ASSETS"}->{"ASSETS"}) {
        my $assetName = $parentProductAsset->{"NAME"};
        my $fileName = $parentProductAsset->{"FILE_NAME"};
        my $isDefault = $parentProductAsset->{"IS_DEFAULT"};
        my $sequenceNum = $parentProductAsset->{"SEQUENCE_NUM"};
        my $type = $parentProductAsset->{"TYPE"};

        my $parentProduct_handle = $dbh->prepare(qq{
            INSERT INTO product(
                PRODUCT_ID, 
                PRODUCT_TYPE_ID, 
                SALES_DISCONTINUATION_DATE, 
                INTERNAL_NAME, 
                PRODUCT_NAME, 
                IS_VIRTUAL, 
                IS_VARIANT, 
                HAS_RUSH_PRODUCTION
            )
            VALUES (
                ?, ?, ?, ?, ?, ?, ?, ?, ?
            )
            ON DUPLICATE KEY UPDATE
                PRODUCT_TYPE_ID = ?,
                SALES_DISCONTINUATION_DATE = ?,
                INTERNAL_NAME = ?,
                PRODUCT_NAME = ?,
                IS_VIRTUAL = ?, 
                IS_VARIANT = ?, 
                HAS_RUSH_PRODUCTION = ?
        });

        $parentProduct_handle->execute(
            $productId, $type, $discontinuationDate, $name, $name, "Y", "N", $hasRushProduction,
                        $type, $discontinuationDate, $name, $name, "Y", "N", $hasRushProduction
        );
    }

    foreach my $productKey (keys %{$parentProduct}) {
        if ($productKey == "DIGITAL_ASSETS") {
            
        } elsif ($productKey == "PRODUCT_VARIANTS") {

        } else {
            
        }
    }

    exit;
}