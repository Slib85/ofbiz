#!/usr/bin/env perl
use lib "/usr/local/ofbiz/etc/lib/";
use strict;
use warnings;
use DBI;
use Ofbiz;
use Data::Dumper;
use File::Basename;
use open ':std', ':encoding(UTF-8)';

my $ofbiz = Ofbiz->new(conf => "/usr/local/ofbiz/framework/entity/config/entityengine.xml", datasource => "localmysql");
my $dbh = DBI->connect($ofbiz->dsn("mysql"), $ofbiz->{dbuser}, $ofbiz->{dbpass}, {AutoCommit => 0, mysql_enable_utf8 => 1}) or die "Couldn't connect to database: " . DBI->errstr;
# brand, GTIN. There are far more fields (the more we can include, the better)
my $productInfo = $dbh->prepare(qq|
    select 
        p.product_id as `id`,
        p.long_description as `description`,
        p.product_name as `title`,
	concat(p.product_type_id, " > ", cw.size_description) as `product_type`,
        min(pp.price) as `price`,
        pws.envelopes,
        pws.folders,
	ps.quantity_on_hand,
	ps.always_instock
    from product p
    inner join product_price pp on pp.product_id = p.product_id
    inner join product_web_site pws on pws.product_id = p.product_id
    inner join color_warehouse cw on cw.variant_product_id = p.product_id
    left join product_stock ps on ps.product_id = p.product_id 
    where p.sales_discontinuation_date is null
    group by pp.product_id
|);

$productInfo->execute();

my $envelopesXMLOutput = qq{<?xml version="1.0"?>
<rss xmlns:g="http://base.google.com/ns/1.0" version="2.0">
    <channel>
        <title>Envelopes | Envelopes.com</title>
        <link>https://www.envelopes.com/</link>
        <description>Envelopes.com Product List RSS feed</description>
};

my $foldersXMLOutput = qq{<?xml version="1.0"?>
<rss xmlns:g="http://base.google.com/ns/1.0" version="2.0">
    <channel>
        <title>Folders | Folders.com</title>
        <link>https://www.folders.com/</link>
        <description>Folders.com Product List RSS feed</description>
};

while (my $product = $productInfo->fetchrow_hashref()) {
    my $productFeatureOutput = "";    

    my $productFeatureInfo = $dbh->prepare(qq|
        selecT 
            pf.product_feature_type_id as `id`, 
            pf.description as `description`
        from product_feature pf
        inner join product_feature_appl pfa on pfa.product_feature_id = pf.product_feature_id
        where pfa.product_id = "| . $$product{"id"} . qq|"
    |);

    $productFeatureInfo->execute();

    while (my $productFeature = $productFeatureInfo->fetchrow_hashref()) {
	if (lc($$productFeature{"id"}) ne "availability") {
        	my $productFeatureDescription = $$productFeature{"description"};
		#$productFeatureDescription =~ s/®™©//g;

		$productFeatureOutput .= qq|
           	 	<g:| . lc($$productFeature{"id"}) . qq|>$productFeatureDescription</g:| . lc($$productFeature{"id"}) . qq|>
        	|;
	}
    }

    $productFeatureInfo->finish();

    my $availability = (defined $$product{"quantity_on_hand"} && $$product{"quantity_on_hand"} > 0) || (defined $$product{"always_instock"} && uc($$product{"always_instock"}) eq "Y") ? "in_stock" : "out_of_stock";
    
    if (uc($$product{"envelopes"}) eq "Y") {
        $envelopesXMLOutput .= qq|
            <item>
                <g:id>| . $$product{"id"} . qq|</g:id>
                <g:title>| . $$product{"title"} . qq|</g:title>
                <g:link>https://www.envelopes.com/envelopes/control/product?product_id=| . $$product{"id"} . qq|</g:link>
                <g:description>| . $$product{"description"} . qq|</g:description>
		<g:product_type>| . $$product{"product_type"} . qq|</g:product_type>
                <g:image_link>https://actionenvelope.scene7.com/is/image/ActionEnvelope/| . $$product{"id"} . qq|?hei=413&wid=510&fmt=jpeg&qlt=75&bgc=ffffff</g:image_link>
                <g:price>| . $$product{"price"} . qq| USD</g:price>
                <g:condition>new</g:condition>
                <g:availability>$availability</g:availability>
                $productFeatureOutput
            </item>
        |;
    }

    if (uc($$product{"folders"}) eq "Y") {
        $foldersXMLOutput .= qq|
            <item>
                <g:id>| . $$product{"id"} . qq|</g:id>
                <g:title>| . $$product{"title"} . qq|</g:title>
                <g:link>https://www.folders.com/envelopes/control/product?product_id=| . $$product{"id"} . qq|</g:link>
                <g:description>| . $$product{"description"} . qq|</g:description>
		<g:product_type>| . $$product{"product_type"} . qq|</g:product_type>
                <g:image_link>https://actionenvelope.scene7.com/is/image/ActionEnvelope/| . $$product{"id"} . qq|?hei=413&wid=510&fmt=jpeg&qlt=75&bgc=ffffff</g:image_link>
                <g:price>| . $$product{"price"} . qq| USD</g:price>
                <g:condition>new</g:condition>
                <g:availability>$availability</g:availability>
                $productFeatureOutput
            </item>
        |;
    } 
}

$envelopesXMLOutput .= qq|
    </channel>
</rss>
|;

$foldersXMLOutput .= qq|
    </channel>
</rss>
|;

$envelopesXMLOutput =~ s/&/&amp;/g;
$foldersXMLOutput =~ s/&/&amp;/g;

open FH1, ">", "/usr/local/ofbiz/hot-deploy/html/webapp/html/envelopesProductFeed.txt";
print FH1 $envelopesXMLOutput;
close FH1;

open FH2, ">", "/usr/local/ofbiz/hot-deploy/html/webapp/html/foldersProductFeed.txt";
print FH2 "$foldersXMLOutput";
close FH2;

$productInfo->finish();
$dbh->disconnect();
exit;
