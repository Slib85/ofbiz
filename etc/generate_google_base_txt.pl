#!/usr/bin/env perl
use lib "/usr/local/ofbiz/etc/lib/";
use strict;
use warnings;
use Data::Dumper;
use DBI;
use Ofbiz;
use Text::CSV_XS;

use constant MAPPING_FILE => "/usr/local/ofbiz/etc/proxy_rewrite.txt";
use constant DATA_FILE_ENV => "/usr/local/ofbiz/etc/google_base_data.csv";

use constant MAPPING_FILE_FOLD => "/usr/local/ofbiz/etc/folders-proxy_rewrite.txt";
use constant DATA_FILE_FOLD => "/usr/local/ofbiz/etc/google_base_data_folders.csv";

my $ofbiz = Ofbiz->new(conf => "/usr/local/ofbiz/framework/entity/config/entityengine.xml", datasource => "localmysql");
my $dbh = DBI->connect($ofbiz->dsn("mysql"), $ofbiz->{dbuser}, $ofbiz->{dbpass}, {AutoCommit => 0}) or die "Couldn't connect to database: " . DBI->errstr;

$dbh->do('set names utf8');

# retrieve details for fifty envelope bundles
my $variants_handle = $dbh->prepare(qq{
	SELECT
		p.product_id AS product_id,
		p.parent_product_id as parent_product_id,
		p.tag_line AS tag_line,
		p.internal_name AS name,
		p.product_type_id AS product_type_id,
		p.small_image_url AS image_url,
		p.long_description AS description,
		p.color_description AS alt_description,
		p.product_height AS height,
		p.product_width AS width,
		p.product_weight AS weight,
		p.primary_product_category_id AS category_id,
		pc.category_name AS category_name,
		p.has_color_opt AS has_color_opt,
		p.on_sale AS on_sale,
		ROUND(pp.price, 2) AS price,
		pp.upc AS upc,
		pc.primary_parent_category_id AS parent_category_id,
		pp.quantity AS quantity,
		pp.colors AS colors,
		p.created_stamp as created_stamp,
		pa.asset_name as asset_name,
		pa.asset_default as asset_default,
        pws.ae as ae,
        pws.envelopes as envelopes,
        pws.folders as folders,
        pws.bags as bags
	FROM
		product p
			INNER JOIN
		product_price pp ON p.product_id = pp.product_id
			INNER JOIN
		product_category pc ON p.primary_product_category_id = pc.product_category_id
			LEFT JOIN
		product_assets pa on pa.product_id = p.product_id
		    LEFT JOIN
		product_web_site pws ON p.product_id = pws.product_id
	WHERE
		pp.product_price_type_id = 'DEFAULT_PRICE'
			AND (p.sales_discontinuation_date > NOW()
			OR p.sales_discontinuation_date IS NULL)
			AND (pp.thru_date > NOW()
            OR pp.thru_date IS NULL)
			AND pp.colors = 0
			AND pp.quantity <= 1000
	GROUP BY p.product_id, pp.quantity
	ORDER BY product_id ASC, quantity ASC, colors ASC, asset_default DESC
});

# get the smallest print qty
my $getSmallestPrintQty = $dbh->prepare(qq{
	select quantity from product_price where product_id = ? and colors = ? order by quantity asc limit 1
});

# retrieve product features
my $features_handle = $dbh->prepare(qq{
	SELECT
	    pfa.product_feature_id,
	    pf.product_feature_type_id,
	    pf.description,
	    pf.default_sequence_num
	FROM
	    product_feature_appl pfa
	        INNER JOIN
	    product_feature pf ON pfa.product_feature_id = pf.product_feature_id
	WHERE
	    pfa.product_id = ?
});

#get parent category
my $product_category = $dbh->prepare(qq{
	select category_name, product_category_id, primary_parent_category_id from product_category where product_category_id = ?
});

#getting scene7 data
my $getScene7Data = $dbh->prepare(qq{
	select st.scene7_template_id, st.product_type_id, st.template_description, st.quick_desc, spa.product_id, st.base_quantity, st.base_price, st.product_desc, st.height, st.width, st.colors, st.upc from scene7_template st inner join scene7_prod_assoc spa on st.scene7_template_id = spa.scene7_template_id where spa.template_type_id = 'ADVANCE' and spa.template_assoc_type_id = 'TEMPLATE_FRONT' and st.print_price_description is not null and st.upc is not null
});

my $getScene7Category = $dbh->prepare(qq{
	select scene7_template_id, category_id, description from scene7_template_category where thru_date is null and scene7_template_id = ?
});

my $getScene7Product = $dbh->prepare(qq{
	select prod.product_id, prod.primary_product_category_id from scene7_prod_assoc spa inner join color_warehouse ai on ai.virtual_product_id = spa.product_id inner join product prod on ai.variant_product_id = prod.product_id where spa.scene7_template_id = ? order by ai.sequence_num asc limit 1
});

die "Couldn't prepare queries; aborting" unless defined $variants_handle && defined $features_handle;

# retrieve envelope redirects
die "URL mappings file does not exist; aborting" unless -f MAPPING_FILE;

my %url_mapping;
open(my $fh, "<", MAPPING_FILE) or die "Couldn't open mappings file at " . MAPPING_FILE;

while (<$fh>) {
	chomp;
	chop;
	my ($key, $val)    = split(/\t/);
	if (defined $key && defined $val) {
		$url_mapping{$val} = $key;
	}
}

close($fh) or die "Couldn't close mappings file at " . MAPPING_FILE;

my %url_mapping_fold;
open(my $fhf, "<", MAPPING_FILE_FOLD) or die "Couldn't open mappings file at " . MAPPING_FILE_FOLD;

while (<$fhf>) {
	chomp;
	chop;
	my ($key, $val)    = split(/\t/);
	if (defined $key && defined $val) {
		$url_mapping_fold{$val} = $key;
	}
}

close($fhf) or die "Couldn't close mappings file at " . MAPPING_FILE;

my %data_mapping_env = ();
my $dataFile = Text::CSV_XS->new ({ binary => 1, sep_char => ',', eol => $/, quote_char => '"' });
open (INPUTFILE, "<", DATA_FILE_ENV) or die "Couldn't open filehandle: $!";
while(<INPUTFILE>) {
	next if ($. == 1);
	if($dataFile->parse($_)) {
		my @columns = $dataFile->fields();
		if($columns[1] ne "") {
			$data_mapping_env{$columns[0]}->{"title"} = $columns[1];
		}
		if($columns[2] ne "") {
			$data_mapping_env{$columns[0]}->{"description"} = $columns[2];
		}
		if($columns[3] ne "") {
			$data_mapping_env{$columns[0]}->{"product_type"} = $columns[3];
		}
		if($columns[4] ne "") {
			$data_mapping_env{$columns[0]}->{"image_link"} = $columns[4];
		}
	}
}
close INPUTFILE or die "Couldn't close channel mappings file at " . DATA_FILE_ENV;

my %data_mapping_fold = ();
$dataFile = Text::CSV_XS->new ({ binary => 1, sep_char => ',', eol => $/, quote_char => '"' });
open (INPUTFILE, "<", DATA_FILE_FOLD) or die "Couldn't open filehandle: $!";
while(<INPUTFILE>) {
	next if ($. == 1);
	if($dataFile->parse($_)) {
		my @columns = $dataFile->fields();
		if($columns[1] ne "") {
			$data_mapping_fold{$columns[0]}->{"title"} = $columns[1];
		}
		if($columns[2] ne "") {
			$data_mapping_fold{$columns[0]}->{"description"} = $columns[2];
		}
		if($columns[3] ne "") {
			$data_mapping_fold{$columns[0]}->{"product_type"} = $columns[3];
		}
	}
}
close INPUTFILE or die "Couldn't close channel mappings file at " . DATA_FILE_FOLD;

# returns category hash
sub get_scene7_cat {
	my $template_id = shift;
	my %cats   = ();

	$getScene7Category->execute($template_id);
	while(my $cat = $getScene7Category->fetchrow_hashref()) {
	$cats{ lc $$cat{"category_id"} } = $$cat{"description"}; }
	return %cats;
}

############################ THIS FILE DOES BOTH GOOGLE AND MSN FEEDS #########################
# spreadsheet header columns
my $header = "id\ttitle\tdescription\tlink\tcolor\tprice\theight\twidth\tweight\timage link\tbrand\tgtin\tmpn\tproduct type\tgoogle product category\tquantity\tcondition\tcurrency\tadwords_redirect\tc:drlp\tadwords_queryparam\tadwords_labels\tavailability\tcreated_date\tcustom_label_0\tcustom_label_1\tcustom_label_2\tcustom_label_3\tcustom_label_4\tgoogle_funded_promotion_eligibility\n";
my $headerBronto = "id\ttitle\tdescription\tlink\tcolor\tprice\theight\twidth\tweight\timage link\tbrand\tmpn\tproduct type\tgoogle product category\tquantity\tcondition\tcurrency\tavailability\tcreated_date\tStyle\tCategory\tSize\n";
my $headerBing = "id\ttitle\tdescription\tlink\tcolor\tprice\theight\twidth\tweight\timage link\tbrand\tgtin\tmpn\tproduct type\tgoogle product category\tquantity\tcondition\tcurrency\tc:drlp\tadwords_queryparam\tadwords_labels\tavailability\tcreated_date\tcustom_label_0\tcustom_label_1\tcustom_label_2\tcustom_label_3\tcustom_label_4\tbingads_labels\n";

open(SPREADSHEET, ">/tmp/google-base-feed.txt") or die "Couldn't open filehandle: $!";
open(SPREADSHEET_FOLDERS, ">/tmp/google-base-feed_folders.txt") or die "Couldn't open filehandle: $!";
open(SPREADSHEETBRONTO, ">/tmp/bronto-feed.txt") or die "Couldn't open filehandle: $!";
open(SPREADSHEETBING, ">/tmp/bingshopping.txt") or die "Couldn't open filehandle: $!";

# create csv object

print SPREADSHEET $header;
print SPREADSHEET_FOLDERS $header;
print SPREADSHEETBRONTO $headerBronto;
print SPREADSHEETBING $headerBing;

my @fileHandles = ();
my @countryList = ('uk','ireland','newzealand','australia','canada');
for (@countryList) {
    open(my $fhI, ">/tmp/google-base-feed_" . $_ . ".txt") or die "Couldn't open filehandle: $!";
    print $fhI $header;
    push @fileHandles, $fhI;
}

my $uniqueSKU = "";
my %foldersList = (
    "FOLDER_STYLE" => [""],
    "9_BY_12_FOLDER" => ["SF-101","OR-145","OR-144","TAX-912"],
    "9_BY_12_FOLDER_WINDOW" => ["SF-102"],
    "9_X_14_1_2_FOLDER" => ["LF-118"],
    "CERTIF_HOLDER" => ["CHEL-185"],
    "5_3_4_X_8_3_4_FOLDER" => ["MF-144"],
	"5_3_4_X_8_3_4_FOLDER_FOIL" => ["WEL"],
	"5_3_4_X_8_3_4_FOLDER_FOIL_BV" => ["WEL-BV"],
    "WEL_10PACK" => ["WEL-10PACK"],
    "4_9_FOLDER" => ["MF-4801"],
    "8_5_X_11_CERTIF" => ["CERTIF"],
    "8_5_X_11_DIPLOMA" => ["PDCL-8.5x11"],
    "8_5_X_14_DIPLOMA" => ["PDCL-8.5x14"],
    "FOLDER_ACCESSORIES" => ["ACCO-SAPF","DTF-3PRONG","TPPF-S"],
    "8_5_X_11_BINDER" => ["PB-TUFFY"],
	"9_BY_12_FOLDER_TAX" => ["TAX-912-NF80"],
	"9_BY_12_FOLDER_TAX2" => ["TAX-912-CEI80"],
	"9_BY_12_FOLDER_2" => ["OR-145"],
	"9_BY_12_FOLDER_3" => ["OR-144"],
	"9_BY_12_FOLDER_TANG" => ["SF-101-546-TANG-25", "PF912BLACK-BRAD-25", "PF912DBLUE-BRAD-25", "PF912GREEN-BRAD-25", "PF912BURG-BRAD-25", "PF912TEAL-BRAD-25", "PF912PINK-BRAD-25"]
);

# product information
$variants_handle->execute();
while (my $product = $variants_handle->fetchrow_hashref()) {
	###########################################
	#IF its the same sku, continue on, only want 1 record per sku
	###########################################
	if($uniqueSKU eq $$product{"product_id"}) {
		next;
	}

	my %features = ();

	# retrieve product features
	$features_handle->execute($$product{"product_id"});
	while(my $feature = $features_handle->fetchrow_hashref()) {
		$features{ lc $$feature{"product_feature_type_id"} } = $feature;
	}

	#here we are testing if the product is the smallest quantity, if it is, we only want this product to show in Google Product Listing ads
	my $isSmallestQty = "";
	if($uniqueSKU ne $$product{"product_id"}) {
		$isSmallestQty = "Y";
	} else {
		$isSmallestQty = "N";
	}

	my $prodSKU = $$product{"product_id"};
	$prodSKU = $$product{"product_id"} . "-" . $$product{"quantity"};

	# scrub invalid characters
	$$product{"name"}        =~ s/[^!-~\s]//g if defined $$product{"name"};
	$$product{"description"} =~ s/\r?\n|\r//g if defined $$product{"description"};
	$$product{"description"} =~ s/[^!-~\s]//g if defined $$product{"description"};

	if(defined $$product{"has_color_opt"} && $$product{"has_color_opt"} eq "N" && defined $features{"mill"}->{"description"}) {
		$$product{"name"} = $features{"mill"}->{"description"} . " " . $$product{"name"};
	}

	if(defined $features{"color"}->{"description"}) {
		$$product{"name"} .= " - " . $features{"color"}->{"description"};
	}

	if(defined $features{"color_group"}->{"description"} && index($$product{"name"}, $features{"color_group"}->{"description"}) == -1) {
		$$product{"name"} .= " - " . $features{"color_group"}->{"description"};
	}

	# if needed, modify the product name
	if($$product{"product_type_id"} eq "ENVELOPE") {
		if ($$product{"name"} !~ m/envelope/i) {
		  # add envelopes to product name
		  $$product{"name"} .= " Envelopes";
		}
	}

	#if its a brandshop item or not the smallest quantity, we don't want to show this in Product Ads
	my $isExcluded = "";
	if($isSmallestQty eq "N") {
		$isExcluded = "Y";
	}

	# make sure description is not blank
	if ((!defined $$product{"description"}) || ($$product{"description"} eq "")) {
		$$product{"description"} = $$product{"alt_description"};
	}

	# generate the url parameters
	my $url_domain = "https://www.envelopes.com";
	my $url_path = sprintf("/envelopes/control/product/~category_id=%s/~product_id=%s", $$product{"category_id"}, $$product{"product_id"});

	my $url_query = sprintf("?utm_source=gbase&utm_medium=cpc&utm_campaign=gbase&CS_003=2521211&CS_010=%s", $$product{"product_id"});
	my $url_query_bing = sprintf("?utm_source=bingfeed&utm_medium=cpc&utm_campaign=bingfeed&CS_003=2521211&CS_010=%s", $$product{"product_id"});
	my $url_seo_path = $url_mapping{$url_path} || $url_path;
	my $height = "";
	my $width = "";
	my $weight = (defined $$product{"weight"}) ? $$product{"weight"}*$$product{"quantity"} : 0;
	my $desc = "";
	my $addWordsLabel = "";
	my $bingLabel = "";
	my $custom0 = "";
	my $custom1 = "";
	my $custom2 = "";
	my $custom3 = "";
	my $custom4 = "";

	#custom bid rules
	my %bidHash = (
		"WS-3342-50" => "Tax 1",
		"45146-50" => "Tax 1",
		"WS-2652-50" => "Tax 1",
		"43687-50" => "Tax 1",
		"75746-50" => "Tax 1",
		"4860-80W-50" => "Tax 1",
		"99977-50" => "Tax 1",
		"28728-50" => "Tax 1",
		"WS-3322-50" => "Tax 1",
		"75761-50" => "Tax 1",
		"45179-ST-50" => "Tax 1",
		"43675-ST-50" => "Tax 1",
		"WS-0056-50" => "Tax 1",
		"61532-50" => "Tax 1",
		"WS-1128-50" => "Tax 1",
		"48643-50" => "Tax 1",
		"61537-50" => "Tax 1",
		"24529-50" => "Tax 1",
		"61538-50" => "Tax 1",
		"43554-50" => "Tax 1",
		"61549-50" => "Tax 1",
		"QUAR2012-50" => "Tax 1",
		"WS-5280-50" => "Tax 1",
		"E4820-00-50" => "Tax 1",
		"EX4820-18-50" => "Tax 1",
		"FFW-69-50" => "Tax 1",
		"FFW-69-103-50" => "Tax 1",
		"FFW-69-102-50" => "Tax 1",
		"12310-50" => "Tax 1",
		"3694-50" => "Tax 1",
		"12377-50" => "Tax 1",
		"E4895-00-50" => "Tax 1",
		"4895-GL-50" => "Tax 1",
		"15891-50" => "Tax 1",
		"FE-6070-15-50" => "Tax 1",
		"6075-01-50" => "Tax 1",
		"4899-NLI-50" => "Tax 1",
		"14554-50" => "Tax 1",
		"49783-50" => "Tax 1",
		"CC9x12-50" => "Tax 1",
		"WS-4918-50" => "Tax 1",
		"912CWIN-50" => "Tax 1",
		"PC1102PL-500" => "Tax 1",
		"QUAR2011-100" => "Tax 1",
		"PC1250PL-500" => "Tax 1",
		"4894-AIR-50" => "Tax 1",
		"8193-50" => "Tax 1",
		"8383-50" => "Tax 1",
		"8250-50" => "Tax 1",
		"4132-50" => "Tax 1",
		"4894-MK-50" => "Tax 1",
		"WS-4894-ST-50" => "Tax 1",
		"1590-32IJ-50" => "Tax 1",
		"75456-50" => "Tax 1",
		"10157-50" => "Tax 1",
		"75514-50" => "Tax 1",
		"4894-GB-50" => "Tax 1",
		"80428-50" => "Tax 1",
		"7285-01-50" => "Tax 1",
		"EX4894-18-50" => "Tax 1",
		"EX4894-22-50" => "Tax 1",
		"75407-50" => "Tax 1",
		"1590BK-50" => "Tax 1",
		"1590-WLI-50" => "Tax 1",
		"1590PS-50" => "Tax 1",
		"57633-50" => "Tax 1",
		"INVDW-50" => "Tax 1",
		"INVDW-GB-50" => "Tax 1",
		"INVDW-B-50" => "Tax 1",
		"INVDW-28BK-50" => "Tax 1",
		"WS-3876-50" => "Tax 1",
		"WS-3878-50" => "Tax 1",
		"7487-W2-50" => "Tax 1",
		"WS-7484-50" => "Tax 1",
		"WS-7494-50" => "Tax 1",
		"WS-7496-50" => "Tax 1",
		"43703-50" => "Tax 1",
		"12328-50" => "Tax 1",
		"7485-W2-50" => "Tax 1",
		"7486-W2-50" => "Tax 1",
		"28726-50" => "Tax 1",
		"92021-50" => "Tax 1",
		"4899-WLI-50" => "Tax 1",
		"PC1207FC-500" => "Tax 1",
		"E4894-00-50" => "Tax 1",
		"4894-NLI-50" => "Tax 1",
		"PF-130W-10" => "Tax 1",
		"INVDW-18-50" => "Tax 1",
		"WS-2956-50" => "Tax 1",
		"45161-50" => "Tax 1",
		"1590-50" => "Tax 1",
		"WS-2371-50" => "Tax 1",
		"7489-W2-50" => "Tax 1",
		"1590-GB-50" => "Tax 2",
		"26949-50" => "Tax 2",
		"4820-06-50" => "Tax 2",
		"4820-07-50" => "Tax 2",
		"4820-24IJ-50" => "Tax 2",
		"4820-32IJ-50" => "Tax 2",
		"4820-32IJ-50" => "Tax 2",
		"4820-BLI-50" => "Tax 2",
		"4820-NLI-50" => "Tax 2",
		"4894-06-50" => "Tax 2",
		"4894-06-50" => "Tax 2",
		"4894-07-50" => "Tax 2",
		"4894-07-50" => "Tax 2",
		"4894-WLI-50" => "Tax 2",
		"4894-WLI-50" => "Tax 2",
		"4899-32IJ-50" => "Tax 2",
		"4899-32IJ-50" => "Tax 2",
		"4899-BLI-50" => "Tax 2",
		"4899-BLI-50" => "Tax 2",
		"5350-06-50" => "Tax 2",
		"5350-07-50" => "Tax 2",
		"5350-20-50" => "Tax 2",
		"5350-20-50" => "Tax 2",
		"57909-50" => "Tax 2",
		"6025-01-50" => "Tax 2",
		"75852-50" => "Tax 2",
		"EX4820-10-50" => "Tax 2",
		"EX4820-11-50" => "Tax 2",
		"EX4820-12-50" => "Tax 2",
		"EX4820-13-50" => "Tax 2",
		"EX4820-14-50" => "Tax 2",
		"EX4820-15-50" => "Tax 2",
		"EX4820-17-50" => "Tax 2",
		"EX4820-23-50" => "Tax 2",
		"EX4820-25-50" => "Tax 2",
		"EX4820-25-50" => "Tax 2",
		"EX4820-26-50" => "Tax 2",
		"EX4820-26-50" => "Tax 2",
		"EX4820-27-50" => "Tax 2",
		"EX4894-10-50" => "Tax 2",
		"EX4894-10-50" => "Tax 2",
		"EX4894-11-50" => "Tax 2",
		"EX4894-11-50" => "Tax 2",
		"EX4894-11-50" => "Tax 2",
		"EX4894-12-50" => "Tax 2",
		"EX4894-12-50" => "Tax 2",
		"EX4894-12-50" => "Tax 2",
		"EX4894-12-50" => "Tax 2",
		"EX4894-13-50" => "Tax 2",
		"EX4894-14-50" => "Tax 2",
		"EX4894-14-50" => "Tax 2",
		"EX4894-15-50" => "Tax 2",
		"EX4894-15-50" => "Tax 2",
		"EX4894-15-50" => "Tax 2",
		"EX4894-17-50" => "Tax 2",
		"EX4894-17-50" => "Tax 2",
		"EX4894-17-50" => "Tax 2",
		"EX4894-23-50" => "Tax 2",
		"EX4894-23-50" => "Tax 2",
		"EX4894-25-50" => "Tax 2",
		"EX4894-25-50" => "Tax 2",
		"EX4894-26-50" => "Tax 2",
		"EX4894-26-50" => "Tax 2",
		"EX4894-26-50" => "Tax 2",
		"EX4894-27-50" => "Tax 2",
		"EX4894-27-50" => "Tax 2",
		"EX4894-27-50" => "Tax 2",
		"EX4899-10-50" => "Tax 2",
		"EX4899-11-50" => "Tax 2",
		"EX4899-12-50" => "Tax 2",
		"EX4899-12-50" => "Tax 2",
		"EX4899-13-50" => "Tax 2",
		"EX4899-14-50" => "Tax 2",
		"EX4899-15-50" => "Tax 2",
		"EX4899-15-50" => "Tax 2",
		"EX4899-17-50" => "Tax 2",
		"EX4899-18-50" => "Tax 2",
		"EX4899-22-50" => "Tax 2",
		"EX4899-22-50" => "Tax 2",
		"EX4899-23-50" => "Tax 2",
		"EX4899-25-50" => "Tax 2",
		"EX4899-26-50" => "Tax 2",
		"EX4899-27-50" => "Tax 2",
		"EX4899-27-50" => "Tax 2",
		"FE-4220-12-50" => "Tax 2",
		"FE-4220-12-50" => "Tax 2",
		"FE-4220-15-50" => "Tax 2",
		"FE-4220-15-50" => "Tax 2",
		"FE-7280-12-50" => "Tax 2",
		"FE-7280-12-50" => "Tax 2",
		"FE-7280-15-50" => "Tax 2",
		"FFW-69-18-50" => "Tax 2",
		"FFW-69-18-50" => "Tax 2",
		"FFW-69-B-50" => "Tax 2",
		"FFW-69-B-50" => "Tax 2",
		"FFW-69-L20-50" => "Tax 2",
		"FFW-69-L20-50" => "Tax 2",
		"FFW-69-L22-50" => "Tax 2",
		"FFW-69-L22-50" => "Tax 2",
		"HD-E055-50" => "Tax 2",
		"INVDW-102-50" => "Tax 2",
		"INVDW-11-50" => "Tax 2",
		"INVDW-11-50" => "Tax 2",
		"INVDW-11-50" => "Tax 2",
		"INVDW-L20-50" => "Tax 2",
		"INVDW-L20-50" => "Tax 2",
		"INVDW-L20-50" => "Tax 2",
		"INVDW-L22-50" => "Tax 2",
		"INVDW-L22-50" => "Tax 2",
		"LUX-1590-07-50" => "Tax 2",
		"LUX-1590-07-50" => "Tax 2",
		"LUX-1590-101-50" => "Tax 2",
		"LUX-1590-101-50" => "Tax 2",
		"LUX-1590-102-50" => "Tax 2",
		"LUX-1590-103-50" => "Tax 2",
		"LUX-1590-103-50" => "Tax 2",
		"LUX-1590-11-50" => "Tax 2",
		"LUX-1590-11-50" => "Tax 2",
		"LUX-1590-12-50" => "Tax 2",
		"LUX-1590-12-50" => "Tax 2",
		"LUX-1590-13-50" => "Tax 2",
		"LUX-1590-13-50" => "Tax 2",
		"LUX-1590-17-50" => "Tax 2",
		"LUX-1590-17-50" => "Tax 2",
		"LUX-1590-18-50" => "Tax 2",
		"LUX-1590-18-50" => "Tax 2",
		"LUX-1590-22-50" => "Tax 2",
		"LUX-1590-22-50" => "Tax 2",
		"LUX-1590-B-50" => "Tax 2",
		"LUX-1590-L20-50" => "Tax 2",
		"LUX-1590-L20-50" => "Tax 2",
		"LUX-1590-L22-50" => "Tax 2",
		"LUX-1590-L22-50" => "Tax 2",
		"LUX-4820-112-50" => "Tax 2",
		"LUX-4820-113-50" => "Tax 2",
		"LUX-4894-05-50" => "Tax 2",
		"LUX-4894-07-50" => "Tax 2",
		"LUX-4894-07-50" => "Tax 2",
		"LUX-4894-101-50" => "Tax 2",
		"LUX-4894-101-50" => "Tax 2",
		"LUX-4894-101-50" => "Tax 2",
		"LUX-4894-103-50" => "Tax 2",
		"LUX-4894-103-50" => "Tax 2",
		"LUX-4894-104-50" => "Tax 2",
		"LUX-4894-104-50" => "Tax 2",
		"LUX-4894-106-50" => "Tax 2",
		"LUX-4894-113-50" => "Tax 2",
		"LUX-4894-113-50" => "Tax 2",
		"LUX-4894-L20-50" => "Tax 2",
		"LUX-4894-L22-50" => "Tax 2",
		"LUX-4899-05-50" => "Tax 2",
		"LUX-4899-07-50" => "Tax 2",
		"LUX-4899-07-50" => "Tax 2",
		"LUX-4899-101-50" => "Tax 2",
		"LUX-4899-102-50" => "Tax 2",
		"LUX-4899-103-50" => "Tax 2",
		"LUX-4899-103-50" => "Tax 2",
		"LUX-4899-104-50" => "Tax 2",
		"LUX-4899-106-50" => "Tax 2",
		"LUX-4899-112-50" => "Tax 2",
		"LUX-4899-113-50" => "Tax 2",
		"LUX-4899-17-50" => "Tax 2",
		"LUX-4899-L20-50" => "Tax 2",
		"LUX-4899-L20-50" => "Tax 2",
		"LUX-4899-L22-50" => "Tax 2",
		"LUX-PF-07-10" => "Tax 2",
		"LUX-PF-07-10" => "Tax 2",
		"LUX-PF-10-10" => "Tax 2",
		"LUX-PF-101-10" => "Tax 2",
		"LUX-PF-102-10" => "Tax 2",
		"LUX-PF-102-10" => "Tax 2",
		"LUX-PF-103-10" => "Tax 2",
		"LUX-PF-113-10" => "Tax 2",
		"LUX-PF-12-10" => "Tax 2",
		"LUX-PF-13-10" => "Tax 2",
		"LUX-PF-14-10" => "Tax 2",
		"LUX-PF-17-10" => "Tax 2",
		"LUX-PF-18-10" => "Tax 2",
		"LUX-PF-23-10" => "Tax 2",
		"LUX-PF-26-10" => "Tax 2",
		"LUX-PF-L20-10" => "Tax 2",
		"LUX-PF-L22-10" => "Tax 2",
		"PF-GB-10" => "Tax 2",
		"PF-M06-10" => "Tax 2",
		"PF-M07-10" => "Tax 2",
		"R0260-500" => "Tax 2",
		"R0263-500" => "Tax 2",
		"R0264-500" => "Tax 2",
		"R0265-500" => "Tax 2",
		"R0266-500" => "Tax 2",
		"WS-4604-50" => "Tax 2",
		"4894-BLI-50" => "Tax 2",
		"F-6075-B-50" => "Tax 2",
		"LUX-PF-56-10" => "Tax 2",
		"11874-50" => "Tax 2",
		"11924-50" => "Tax 2",
		"11999-50" => "Tax 2",
		"17889-50" => "Tax 2",
		"17889-50" => "Tax 2",
		"22646-50" => "Tax 2",
		"22646-50" => "Tax 2",
		"4820-GB-50" => "Tax 2",
		"4820-GK-50" => "Tax 2",
		"4820-WLI-50" => "Tax 2",
		"4995-GB-50" => "Tax 2",
		"EX4820-22-50" => "Tax 2",
		"F-4220-B-50" => "Tax 2",
		"FFW-69-GB-50" => "Tax 2",
		"FFW-69-GB-50" => "Tax 2",
		"LUX-4894-102-50" => "Tax 2",
		"LUX-4894-102-50" => "Tax 2",
		"LUX-4894-102-50" => "Tax 2",
		"LUX-4894-112-50" => "Tax 2",
		"LUX-4894-112-50" => "Tax 2",
		"LUX-4894-112-50" => "Tax 2",
		"LUX-PF-11-10" => "Tax 2",
		"LUX-PF-22-10" => "Tax 2",
		"LUX-PF-25-10" => "Tax 2",
		"LUX-PF-L17-10" => "Tax 2",
		"PF-BLI-10" => "Tax 2",
		"PF-DBLI-10" => "Tax 2",
		"PF-NLI-10" => "Tax 2",
		"PF-WLI-10" => "Tax 2",
		"R0267-500" => "Tax 2",
		"R0268-500" => "Tax 2",
		"FE4280-15-50" => "Wedding 1",
		"LEVC-99-50" => "Wedding 1",
		"EXLEVC-18-50" => "Wedding 1",
		"4880-GB-50" => "Wedding 1",
		"4872-W-50" => "Wedding 1",
		"10902-50" => "Wedding 1",
		"A2FW-50" => "Wedding 1",
		"7716-GB-50" => "Wedding 1",
		"LUX-4880-103-50" => "Wedding 1",
		"8505-GB-50" => "Wedding 1",
		"1COGB-50" => "Wedding 1",
		"10936-50" => "Wedding 1",
		"EX4870-18-50" => "Wedding 1",
		"4865-W-50" => "Wedding 1",
		"5380-07-50" => "Wedding 1",
		"10894-50" => "Wedding 1",
		"LUX-A7PKT103-10" => "Wedding 1",
		"72940-50" => "Wedding 1",
		"FE4580-05-50" => "Wedding 1",
		"8635-W-50" => "Wedding 1",
		"72771-50" => "Wedding 1",
		"90417-50" => "Wedding 1",
		"EX4880-22-50" => "Wedding 1",
		"EX4880-18-50" => "Wedding 1",
		"EX-1880-18-50" => "Wedding 1",
		"5370-07-50" => "Wedding 1",
		"A7PKTGB-10" => "Wedding 1",
		"EX4875-18-50" => "Wedding 1",
		"1872-GB-50" => "Wedding 1",
		"14257-50" => "Wedding 1",
		"LEVC-GB-50" => "Wedding 1",
		"4880-00-50" => "Wedding 1",
		"LEVC902-50" => "Wedding 1",
		"72932-50" => "Wedding 1",
		"5365-07-50" => "Wedding 1",
		"8503-AO-50" => "Wedding 1",
		"5370-M07-50" => "Wedding 1",
		"FE4275-15-50" => "Wedding 1",
		"10951-50" => "Wedding 1",
		"8515-50-50" => "Wedding 1",
		"LUXLEVC-101-50" => "Wedding 1",
		"5380-11-50" => "Wedding 1",
		"8520-70W-50" => "Wedding 1",
		"89129-50" => "Wedding 1",
		"F-4570-B-50" => "Wedding 1",
		"MINSDS-50" => "Wedding 1",
		"4870-GB-50" => "Wedding 1",
		"5380-20-50" => "Wedding 1",
		"FE4280-12-50" => "Wedding 1",
		"EX4870-12-50" => "Wedding 1",
		"6680-14-50" => "Wedding 1",
		"LUX-4875-113-50" => "Wedding 1",
		"8505-06-50" => "Wedding 1",
		"WS-5222-50" => "Wedding 1",
		"LUXLEVC-103-50" => "Wedding 1",
		"LUX-4880-113-50" => "Wedding 1",
		"F-4580-B-50" => "Wedding 1",
		"LUXLEVC-113-50" => "Wedding 1",
		"LUX-4895-106-50" => "Wedding 1",
		"LUX-8525-103-50" => "Wedding 1",
		"1880-GB-50" => "Wedding 1",
		"MINSDG-50" => "Wedding 1",
		"20578-50" => "Wedding 1",
		"5395-07-50" => "Wedding 1",
		"LUX-4875-103-50" => "Wedding 1",
		"5370-MS03-50" => "Wedding 1",
		"EX10-LEBATE24-10" => "Wedding 1",
		"LUX-4865-103-50" => "Wedding 1",
		"LUXLEVC-106-50" => "Wedding 1",
		"5370-MS03-50" => "Wedding 1",
		"4875-00-50" => "Wedding 1",
		"5375-07-50" => "Wedding 1",
		"5370-06-50" => "Wedding 1",
		"EX4880-25-50" => "Wedding 1",
		"1875-B-50" => "Wedding 1",
		"1880-07-50" => "Wedding 1",
		"4040-MS02-50" => "Wedding 1",
		"5380-17-50" => "Wedding 1",
		"EX4875-14-50" => "Wedding 1",
		"EX4880-12-50" => "Wedding 1",
		"SH4275-03-50" => "Wedding 1",
		"8505-15-50" => "Wedding 1",
		"8565-50-50" => "Wedding 1",
		"LUX-8575-18-50" => "Wedding 1",
		"4870-WPC-50" => "Wedding 1",
		"CCA7-50" => "Wedding 1",
		"EX10-LEBA700PF-10" => "Wedding 1",
		"EX10-LEBAGMPF-10" => "Wedding 1",
		"FA4880-01-50" => "Wedding 1",
		"FE4570-05-50" => "Wedding 1",
		"LUXA7GF-103-10" => "Wedding 1",
		"SH4275-06-50" => "Wedding 1",
		"EX10-LEBA543PC-10" => "Wedding 1",
		"FLWH8535-04-50" => "Wedding 1",
		"LUX-8525-101-50" => "Wedding 1",
		"1870-NPC-50" => "Wedding 1",
		"1895-06-50" => "Wedding 1",
		"4040-WPC-50" => "Wedding 1",
		"5040-MET-M07-50" => "Wedding 1",
		"5365-14-50" => "Wedding 1",
		"5370-11-50" => "Wedding 1",
		"5380-S01-50" => "Wedding 1",
		"5380-S02-50" => "Wedding 1",
		"CCA9-50" => "Wedding 1",
		"EX4020-26-50" => "Wedding 1",
		"EX4880-17-50" => "Wedding 1",
		"EX5030-13-50" => "Wedding 1",
		"EX5040-27-50" => "Wedding 1",
		"FA4875-01-50" => "Wedding 1",
		"FE4270-12-50" => "Wedding 1",
		"LUX-4895-104-50" => "Wedding 1",
		"SH4270-03-50" => "Wedding 1",
		"SH4280-02-50" => "Wedding 1",
		"8520-BLI-50" => "Wedding 1",
		"8520-GB-50" => "Wedding 1",
		"EX8515-14-50" => "Wedding 1",
		"LUX-8515-101-50" => "Wedding 1",
		"LEVC903-50" => "Wedding 2",
		"1880-B-50" => "Wedding 2",
		"EX4880-11-50" => "Wedding 2",
		"F-4575-B-50" => "Wedding 2",
		"20677-50" => "Wedding 2",
		"EX10-LEBASMPF-10" => "Wedding 2",
		"5380-06-50" => "Wedding 2",
		"10BS-GB-50" => "Wedding 2",
		"72924-50" => "Wedding 2",
		"BC4040-B-50" => "Wedding 2",
		"LUX-4880-104-50" => "Wedding 2",
		"10969-50" => "Wedding 2",
		"8510-70W-50" => "Wedding 2",
		"5380-04-50" => "Wedding 2",
		"5380-M07-50" => "Wedding 2",
		"6680-11-50" => "Wedding 2",
		"MR4895-01-50" => "Wedding 2",
		"LUX-8505-101-50" => "Wedding 2",
		"5365-06-50" => "Wedding 2",
		"5370-MS02-50" => "Wedding 2",
		"5895-01-50" => "Wedding 2",
		"EX10-LEBATE28-10" => "Wedding 2",
		"EX4880-13-50" => "Wedding 2",
		"EX4880-14-50" => "Wedding 2",
		"LUX-4870-103-50" => "Wedding 2",
		"SH4280-08-50" => "Wedding 2",
		"F-8505-B-50" => "Wedding 2",
		"LEVC-98-50" => "Wedding 2",
		"5370-MS02-50" => "Wedding 2",
		"1895-07-50" => "Wedding 2",
		"4880-WPP-50" => "Wedding 2",
		"4895-00-50" => "Wedding 2",
		"5380-M08-50" => "Wedding 2",
		"5395-06-50" => "Wedding 2",
		"EX4865-19-50" => "Wedding 2",
		"EX4870-14-50" => "Wedding 2",
		"EX5020-14-50" => "Wedding 2",
		"F-4595-B-50" => "Wedding 2",
		"LUX-4880-101-50" => "Wedding 2",
		"SH4280-03-50" => "Wedding 2",
		"1840-07-50" => "Wedding 2",
		"8575-GB-50" => "Wedding 2",
		"EXLEVC-22-50" => "Wedding 2",
		"LUX-5080-14-50" => "Wedding 2",
		"1870-07-50" => "Wedding 2",
		"4030-L20-50" => "Wedding 2",
		"4040-MS03-50" => "Wedding 2",
		"4870-00-50" => "Wedding 2",
		"4870-WPP-50" => "Wedding 2",
		"4875-GB-50" => "Wedding 2",
		"4880-WLI-50" => "Wedding 2",
		"4895-70W-50" => "Wedding 2",
		"5380-25-50" => "Wedding 2",
		"6670-11-50" => "Wedding 2",
		"EX10-LEBA716PF-10" => "Wedding 2",
		"EX10-LEBAPI28-10" => "Wedding 2",
		"EX4870-22-50" => "Wedding 2",
		"EX4875-27-50" => "Wedding 2",
		"LUX-4875-101-50" => "Wedding 2",
		"LUX-4895-103-50" => "Wedding 2",
		"LUX-A7PKT104-10" => "Wedding 2",
		"SH4280-04-50" => "Wedding 2",
		"SH4280-05-50" => "Wedding 2",
		"1840-06-50" => "Wedding 2",
		"1840-WPC-50" => "Wedding 2",
		"8505-12-50" => "Wedding 2",
		"EX-1840-18-50" => "Wedding 2",
		"EX10-LEBAGMPC-10" => "Wedding 2",
		"EX10-LEBATE23-10" => "Wedding 2",
		"LEVC-96-50" => "Wedding 2",
		"LEVC-97-50" => "Wedding 2",
		"LUX-614PTL103-10" => "Wedding 2",
		"LUX-8545-103-50" => "Wedding 2",
		"1865-07-50" => "Wedding 2",
		"1875-07-50" => "Wedding 2",
		"1875-WPC-50" => "Wedding 2",
		"1880-WPC-50" => "Wedding 2",
		"1895-WPC-50" => "Wedding 2",
		"4020-MET-07-50" => "Wedding 2",
		"4865-GB-50" => "Wedding 2",
		"4870-NLI-50" => "Wedding 2",
		"4875-WPP-50" => "Wedding 2",
		"4880-NLI-50" => "Wedding 2",
		"4880-WPC-50" => "Wedding 2",
		"4895-NLI-50" => "Wedding 2",
		"5030-SW-50" => "Wedding 2",
		"51867-MI-50" => "Wedding 2",
		"5370-MS01-50" => "Wedding 2",
		"5375-11-50" => "Wedding 2",
		"5380-08-50" => "Wedding 2",
		"5380-15-50" => "Wedding 2",
		"5380-S03-50" => "Wedding 2",
		"6675-11-50" => "Wedding 2",
		"A7CW-50" => "Wedding 2",
		"BC4040-07-50" => "Wedding 2",
		"EX10-LEBA701PF-10" => "Wedding 2",
		"EX10-LEBA706PF-10" => "Wedding 2",
		"EX10-LEBA712PF-10" => "Wedding 2",
		"EX10-LEBANA28-10" => "Wedding 2",
		"EX10-LEBASM28-10" => "Wedding 2",
		"EX4020-19-50" => "Wedding 2",
		"EX4030-22-50" => "Wedding 2",
		"EX4865-11-50" => "Wedding 2",
		"EX4870-23-50" => "Wedding 2",
		"EX4880-10-50" => "Wedding 2",
		"EX4895-11-50" => "Wedding 2",
		"EX5030-18-50" => "Wedding 2",
		"EX5060-13-50" => "Wedding 2",
		"LUX-4880-106-50" => "Wedding 2",
		"LUX-4895-101-50" => "Wedding 2",
		"LUX-4895-102-50" => "Wedding 2",
		"LUX-4895-L05-50" => "Wedding 2",
		"LUX-A7PKT106-10" => "Wedding 2",
		"LUXA7GF-B-10" => "Wedding 2",
		"LUXA7ZF-07-10" => "Wedding 2",
		"PGCST970-50" => "Wedding 2",
		"SH4270-06-50" => "Wedding 2",
		"10977-50" => "Wedding 2",
		"1840-30-50" => "Wedding 2",
		"614ZF-80W-10" => "Wedding 2",
		"8505-50-50" => "Wedding 2",
		"8505-BLI-50" => "Wedding 2",
		"8515-15-50" => "Wedding 2",
		"8515-M09-50" => "Wedding 2",
		"8530-WPC-50" => "Wedding 2",
		"8545-GB-50" => "Wedding 2",
		"8555-GB-50" => "Wedding 2",
		"8565-06-50" => "Wedding 2",
		"8575-50-50" => "Wedding 2",
		"8595-50-50" => "Wedding 2",
		"EX10-LEBA537PC-10" => "Wedding 2",
		"EX10-LEBA538PC-10" => "Wedding 2",
		"EX4080-26-50" => "Wedding 2",
		"EXLEVC-26-50" => "Wedding 2",
		"F-8515-B-50" => "Wedding 2",
		"LUX-8525-106-50" => "Wedding 2",
		"71414-50" => "Wedding 2",
		"93961-50" => "Wedding 2",
		"98156-50" => "Wedding 2",
		"4860-GB-50" => "Wedding 2",
		"5370-MS01-50" => "Wedding 2",
		"A7BB-MS02-10" => "Wedding 2",
		"LINER-MS02-50" => "Wedding 2",
		"1865-06-50" => "Wedding 2",
		"1870-GB-50" => "Wedding 2",
		"1875-GB-50" => "Wedding 2",
		"1880-08-50" => "Wedding 2",
		"1880-30-50" => "Wedding 2",
		"1880-NPC-50" => "Wedding 2",
		"1895-B-50" => "Wedding 2",
		"1895-GB-50" => "Wedding 2",
		"4010-103-50" => "Wedding 2",
		"4020-103-50" => "Wedding 2",
		"4020-MET-06-50" => "Wedding 2",
		"4030-18GB-50" => "Wedding 2",
		"4030-MET-06-50" => "Wedding 2",
		"4865-70W-50" => "Wedding 2",
		"4875-M07-50" => "Wedding 2",
		"4875-NPC-50" => "Wedding 2",
		"4875-WGV-50" => "Wedding 2",
		"4875-WPC-50" => "Wedding 2",
		"4880-SBW-50" => "Wedding 2",
		"4880-SG-50" => "Wedding 2",
		"4880-SW-50" => "Wedding 2",
		"4895-WPC-50" => "Wedding 2",
		"5040-SG-50" => "Wedding 2",
		"5040-SN-50" => "Wedding 2",
		"5060-SN-50" => "Wedding 2",
		"5365-02-50" => "Wedding 2",
		"5365-04-50" => "Wedding 2",
		"5365-20-50" => "Wedding 2",
		"5365-M07-50" => "Wedding 2",
		"5370-04-50" => "Wedding 2",
		"5370-15-50" => "Wedding 2",
		"5370-17-50" => "Wedding 2",
		"5370-20-50" => "Wedding 2",
		"5370-27-50" => "Wedding 2",
		"5370-29-50" => "Wedding 2",
		"5370-30-50" => "Wedding 2",
		"5370-M08-50" => "Wedding 2",
		"5370-S01-50" => "Wedding 2",
		"5370-S03-50" => "Wedding 2",
		"5375-12-50" => "Wedding 2",
		"5380-12-50" => "Wedding 2",
		"5380-27-50" => "Wedding 2",
		"5380-30-50" => "Wedding 2",
		"5395-20-50" => "Wedding 2",
		"5895-GL-50" => "Wedding 2",
		"6675-14-50" => "Wedding 2",
		"6675-16-50" => "Wedding 2",
		"6675-17-50" => "Wedding 2",
		"6680-13-50" => "Wedding 2",
		"A1CW-50" => "Wedding 2",
		"A7FW-50" => "Wedding 2",
		"A7GF-07-10" => "Wedding 2",
		"A7GF-80N-10" => "Wedding 2",
		"A7GF-80W-10" => "Wedding 2",
		"A7GF-NPC-10" => "Wedding 2",
		"A7GF-SN-10" => "Wedding 2",
		"ET4875-14-50" => "Wedding 2",
		"ET4880-14-50" => "Wedding 2",
		"EX-1875-18-50" => "Wedding 2",
		"EX-1895-18-50" => "Wedding 2",
		"EX10-LEBA705PF-10" => "Wedding 2",
		"EX10-LEBA711PF-10" => "Wedding 2",
		"EX10-LEBA713PF-10" => "Wedding 2",
		"EX10-LEBABR28-10" => "Wedding 2",
		"EX10-LEBADR24-10" => "Wedding 2",
		"EX10-LEBAGM28-10" => "Wedding 2",
		"EX10-LEBARE28-10" => "Wedding 2",
		"EX4010-27-50" => "Wedding 2",
		"EX4020-11-50" => "Wedding 2",
		"EX4020-12-50" => "Wedding 2",
		"EX4020-13-50" => "Wedding 2",
		"EX4030-14-50" => "Wedding 2",
		"EX4030-25-50" => "Wedding 2",
		"EX4040-14-50" => "Wedding 2",
		"EX4040-16-50" => "Wedding 2",
		"EX4060-23-50" => "Wedding 2",
		"EX4865-14-50" => "Wedding 2",
		"EX4870-26-50" => "Wedding 2",
		"EX4875-22-50" => "Wedding 2",
		"EX4875-25-50" => "Wedding 2",
		"EX4875-26-50" => "Wedding 2",
		"EX4880-23-50" => "Wedding 2",
		"EX4880-27-50" => "Wedding 2",
		"EX4895-10-50" => "Wedding 2",
		"EX4895-12-50" => "Wedding 2",
		"EX4895-13-50" => "Wedding 2",
		"EX4895-18-50" => "Wedding 2",
		"EX4895-25-50" => "Wedding 2",
		"EX5010-23-50" => "Wedding 2",
		"EX5030-14-50" => "Wedding 2",
		"EX5030-25-50" => "Wedding 2",
		"EX5030-28-50" => "Wedding 2",
		"EX5040-17-50" => "Wedding 2",
		"EX5040-26-50" => "Wedding 2",
		"FA4030-02-50" => "Wedding 2",
		"FA4040-05-50" => "Wedding 2",
		"FA4865-01-50" => "Wedding 2",
		"FA4875-07-50" => "Wedding 2",
		"FA4880-07-50" => "Wedding 2",
		"FA4895-02-50" => "Wedding 2",
		"FA5010-04-50" => "Wedding 2",
		"FE4270-22-50" => "Wedding 2",
		"FE4275-12-50" => "Wedding 2",
		"FE4595-05-50" => "Wedding 2",
		"LUX-4865-104-50" => "Wedding 2",
		"LUX-4870-101-50" => "Wedding 2",
		"LUX-4870-102-50" => "Wedding 2",
		"LUX-4870-113-50" => "Wedding 2",
		"LUX-4875-104-50" => "Wedding 2",
		"LUX-4880-112-50" => "Wedding 2",
		"LUX-4895-112-50" => "Wedding 2",
		"LUX-A7PTL103-10" => "Wedding 2",
		"LUXA7GF-101-10" => "Wedding 2",
		"LUXA7GF-11-10" => "Wedding 2",
		"LUXA7ZF-112-10" => "Wedding 2",
		"LUXA7ZF-113-10" => "Wedding 2",
		"MR4870-01-50" => "Wedding 2",
		"PGCST929-50" => "Wedding 2",
		"SH4280-01-50" => "Wedding 2",
		"SH4280-06-50" => "Wedding 2",
		"SH4280-07-50" => "Wedding 2",
		"5080-30-50" => "Wedding 2",
		"614GF-80N-10" => "Wedding 2",
		"614GF-WPC-10" => "Wedding 2",
		"8505-07-50" => "Wedding 2",
		"8505-18-50" => "Wedding 2",
		"8510-GB-50" => "Wedding 2",
		"8520-01-50" => "Wedding 2",
		"8530-70W-50" => "Wedding 2",
		"8565-03-50" => "Wedding 2",
		"8565-07-50" => "Wedding 2",
		"8585-07-50" => "Wedding 2",
		"EX10-LEBA525PC-10" => "Wedding 2",
		"EX10-LEBA536PC-10" => "Wedding 2",
		"EX10-LEBA539PC-10" => "Wedding 2",
		"EX4080-27-50" => "Wedding 2",
		"EX8515-11-50" => "Wedding 2",
		"EX8515-12-50" => "Wedding 2",
		"EX8555-14-50" => "Wedding 2",
		"EX8555-25-50" => "Wedding 2",
		"EX8555-27-50" => "Wedding 2",
		"EXLEVC-14-50" => "Wedding 2",
		"EXLEVC-25-50" => "Wedding 2",
		"EXLEVC-27-50" => "Wedding 2",
		"FLWH8535-02-50" => "Wedding 2",
		"LUX-8505-106-50" => "Wedding 2",
		"LUX-8510-103-50" => "Wedding 2",
		"LUX-8515-102-50" => "Wedding 2",
		"LUX-8515-112-50" => "Wedding 2",
		"LUX-8525-102-50" => "Wedding 2",
		"LUX-8525-104-50" => "Wedding 2",
		"LUX-8545-101-50" => "Wedding 2",
		"LUX614GF-17-10" => "Wedding 2",
		"WS-5221-50" => "Wedding 2",
		"55845-50" => "Wedding 2",
		"82064-50" => "Wedding 2",
		"93979-50" => "Wedding 2",
		"FFW-10-GB-50" => "Wedding 2",
		"LINER-MS03-50" => "Wedding 2",
		"LINER-MS08-50" => "Wedding 2",
		"1865-08-50" => "Wedding 3",
		"1865-30-50" => "Wedding 3",
		"1865-GB-50" => "Wedding 3",
		"1865-NPC-50" => "Wedding 3",
		"1865-WPC-50" => "Wedding 3",
		"1870-30-50" => "Wedding 3",
		"1870-WPC-50" => "Wedding 3",
		"1875-08-50" => "Wedding 3",
		"1875-NPC-50" => "Wedding 3",
		"1895-30-50" => "Wedding 3",
		"4010-101-50" => "Wedding 3",
		"4010-102-50" => "Wedding 3",
		"4010-104-50" => "Wedding 3",
		"4010-106-50" => "Wedding 3",
		"4010-112-50" => "Wedding 3",
		"4010-L05-50" => "Wedding 3",
		"4010-L17-50" => "Wedding 3",
		"4010-L20-50" => "Wedding 3",
		"4010-L22-50" => "Wedding 3",
		"4010-MET-07-50" => "Wedding 3",
		"4010-SN-50" => "Wedding 3",
		"4010-SW-50" => "Wedding 3",
		"4020-101-50" => "Wedding 3",
		"4020-102-50" => "Wedding 3",
		"4020-104-50" => "Wedding 3",
		"4020-112-50" => "Wedding 3",
		"4020-113-50" => "Wedding 3",
		"4020-L05-50" => "Wedding 3",
		"4020-L17-50" => "Wedding 3",
		"4020-L20-50" => "Wedding 3",
		"4020-L22-50" => "Wedding 3",
		"4020-M07-50" => "Wedding 3",
		"4020-M08-50" => "Wedding 3",
		"4020-SN-50" => "Wedding 3",
		"4030-101-50" => "Wedding 3",
		"4030-102-50" => "Wedding 3",
		"4030-103-50" => "Wedding 3",
		"4030-104-50" => "Wedding 3",
		"4030-106-50" => "Wedding 3",
		"4030-112-50" => "Wedding 3",
		"4030-113-50" => "Wedding 3",
		"4030-L05-50" => "Wedding 3",
		"4030-MET-07-50" => "Wedding 3",
		"4030-SW-50" => "Wedding 3",
		"4040-101-50" => "Wedding 3",
		"4040-102-50" => "Wedding 3",
		"4040-112-50" => "Wedding 3",
		"4040-L05-50" => "Wedding 3",
		"4040-L17-50" => "Wedding 3",
		"4040-L20-50" => "Wedding 3",
		"4040-SW-50" => "Wedding 3",
		"4865-NLI-50" => "Wedding 3",
		"4865-NPC-50" => "Wedding 3",
		"4865-SG-50" => "Wedding 3",
		"4865-WGV-50" => "Wedding 3",
		"4865-WLI-50" => "Wedding 3",
		"4865-WPC-50" => "Wedding 3",
		"4865-WPP-50" => "Wedding 3",
		"4870-NPC-50" => "Wedding 3",
		"4870-SN-50" => "Wedding 3",
		"4870-SW-50" => "Wedding 3",
		"4870-WGV-50" => "Wedding 3",
		"4870-WPQ-50" => "Wedding 3",
		"4875-NLI-50" => "Wedding 3",
		"4875-SBW-50" => "Wedding 3",
		"4875-SG-50" => "Wedding 3",
		"4875-SN-50" => "Wedding 3",
		"4880-32IJ-50" => "Wedding 3",
		"4895-SN-50" => "Wedding 3",
		"4895-WGV-50" => "Wedding 3",
		"5010-SN-50" => "Wedding 3",
		"5010-SW-50" => "Wedding 3",
		"5020-SN-50" => "Wedding 3",
		"5020-SW-50" => "Wedding 3",
		"5030-SN-50" => "Wedding 3",
		"5040-96-50" => "Wedding 3",
		"5040-97-50" => "Wedding 3",
		"5040-98-50" => "Wedding 3",
		"5040-99-50" => "Wedding 3",
		"5040-BLI-50" => "Wedding 3",
		"5040-GB-50" => "Wedding 3",
		"5040-MET-08-50" => "Wedding 3",
		"5040-MET-30-50" => "Wedding 3",
		"5040-MET-M08-50" => "Wedding 3",
		"5040-SBW-50" => "Wedding 3",
		"5040-SW-50" => "Wedding 3",
		"5060-SW-50" => "Wedding 3",
		"5365-12-50" => "Wedding 3",
		"5365-17-50" => "Wedding 3",
		"5365-18-50" => "Wedding 3",
		"5365-25-50" => "Wedding 3",
		"5365-26-50" => "Wedding 3",
		"5365-28-50" => "Wedding 3",
		"5365-29-50" => "Wedding 3",
		"5365-30-50" => "Wedding 3",
		"5370-08-50" => "Wedding 3",
		"5370-12-50" => "Wedding 3",
		"5370-18-50" => "Wedding 3",
		"5370-25-50" => "Wedding 3",
		"5370-26-50" => "Wedding 3",
		"5375-02-50" => "Wedding 3",
		"5375-04-50" => "Wedding 3",
		"5375-08-50" => "Wedding 3",
		"5375-15-50" => "Wedding 3",
		"5375-17-50" => "Wedding 3",
		"5375-18-50" => "Wedding 3",
		"5375-20-50" => "Wedding 3",
		"5375-24-50" => "Wedding 3",
		"5375-26-50" => "Wedding 3",
		"5375-29-50" => "Wedding 3",
		"5380-24-50" => "Wedding 3",
		"5395-02-50" => "Wedding 3",
		"5395-12-50" => "Wedding 3",
		"5870-GL-50" => "Wedding 3",
		"5875-GL-50" => "Wedding 3",
		"6675-12-50" => "Wedding 3",
		"6675-13-50" => "Wedding 3",
		"6680-12-50" => "Wedding 3",
		"A1CN-50" => "Wedding 3",
		"A1FN-50" => "Wedding 3",
		"A2FN-50" => "Wedding 3",
		"A6CW-50" => "Wedding 3",
		"A7CN-50" => "Wedding 3",
		"A7GF-06-50" => "Wedding 3",
		"A7GF-07-50" => "Wedding 3",
		"A7GF-08-10" => "Wedding 3",
		"A7GF-08-50" => "Wedding 3",
		"A7GF-30-10" => "Wedding 3",
		"A7GF-30-50" => "Wedding 3",
		"A7GF-80N-50" => "Wedding 3",
		"A7GF-80W-50" => "Wedding 3",
		"A7GF-BLI-50" => "Wedding 3",
		"A7GF-GB-10" => "Wedding 3",
		"A7GF-GB-50" => "Wedding 3",
		"A7GF-NLI-10" => "Wedding 3",
		"A7GF-NLI-50" => "Wedding 3",
		"A7GF-NPC-50" => "Wedding 3",
		"A7GF-SN-50" => "Wedding 3",
		"A7GF-SW-10" => "Wedding 3",
		"A7GF-SW-50" => "Wedding 3",
		"A7GF-WLI-10" => "Wedding 3",
		"A7GF-WLI-50" => "Wedding 3",
		"A7GF-WPC-10" => "Wedding 3",
		"A7GF-WPC-50" => "Wedding 3",
		"A7PKTGB-50" => "Wedding 3",
		"A7ZF-06-10" => "Wedding 3",
		"A7ZF-06-50" => "Wedding 3",
		"A7ZF-07-10" => "Wedding 3",
		"A7ZF-07-50" => "Wedding 3",
		"A7ZF-08-50" => "Wedding 3",
		"A7ZF-30-10" => "Wedding 3",
		"A7ZF-30-50" => "Wedding 3",
		"A7ZF-80N-10" => "Wedding 3",
		"A7ZF-80N-50" => "Wedding 3",
		"A7ZF-80W-10" => "Wedding 3",
		"A7ZF-80W-50" => "Wedding 3",
		"A7ZF-BLI-10" => "Wedding 3",
		"A7ZF-BLI-50" => "Wedding 3",
		"A7ZF-GB-10" => "Wedding 3",
		"A7ZF-GB-50" => "Wedding 3",
		"A7ZF-NLI-10" => "Wedding 3",
		"A7ZF-NLI-50" => "Wedding 3",
		"A7ZF-NPC-50" => "Wedding 3",
		"A7ZF-SN-10" => "Wedding 3",
		"A7ZF-SN-50" => "Wedding 3",
		"A7ZF-SW-10" => "Wedding 3",
		"A7ZF-SW-50" => "Wedding 3",
		"A7ZF-WLI-10" => "Wedding 3",
		"A7ZF-WLI-50" => "Wedding 3",
		"A7ZF-WPC-50" => "Wedding 3",
		"BC4010-06-50" => "Wedding 3",
		"BC4010-07-50" => "Wedding 3",
		"BC4010-B-50" => "Wedding 3",
		"BC4040-11-50" => "Wedding 3",
		"BC4040-22-50" => "Wedding 3",
		"ET4865-14-50" => "Wedding 3",
		"ET4870-16-50" => "Wedding 3",
		"ET4875-16-50" => "Wedding 3",
		"EX-1865-18-50" => "Wedding 3",
		"EX-1870-18-50" => "Wedding 3",
		"EX10-LEBA700PF-50" => "Wedding 3",
		"EX10-LEBA701PF-50" => "Wedding 3",
		"EX10-LEBA702PF-50" => "Wedding 3",
		"EX10-LEBA703PF-10" => "Wedding 3",
		"EX10-LEBA703PF-50" => "Wedding 3",
		"EX10-LEBA704PF-50" => "Wedding 3",
		"EX10-LEBA705PF-50" => "Wedding 3",
		"EX10-LEBA706PF-50" => "Wedding 3",
		"EX10-LEBA707PF-10" => "Wedding 3",
		"EX10-LEBA707PF-50" => "Wedding 3",
		"EX10-LEBA711PF-50" => "Wedding 3",
		"EX10-LEBA712PF-50" => "Wedding 3",
		"EX10-LEBA713PF-50" => "Wedding 3",
		"EX10-LEBA716PF-50" => "Wedding 3",
		"EX10-LEBA718PF-50" => "Wedding 3",
		"EX10-LEBABK28-50" => "Wedding 3",
		"EX10-LEBABL28-50" => "Wedding 3",
		"EX10-LEBABR28-50" => "Wedding 3",
		"EX10-LEBACM28-10" => "Wedding 3",
		"EX10-LEBACM28-50" => "Wedding 3",
		"EX10-LEBACMPF-50" => "Wedding 3",
		"EX10-LEBADR24-50" => "Wedding 3",
		"EX10-LEBADR28-50" => "Wedding 3",
		"EX10-LEBAGG28-10" => "Wedding 3",
		"EX10-LEBAGG28-50" => "Wedding 3",
		"EX10-LEBAGM28-50" => "Wedding 3",
		"EX10-LEBAGMPF-50" => "Wedding 3",
		"EX10-LEBAGO28-50" => "Wedding 3",
		"EX10-LEBAGR28-10" => "Wedding 3",
		"EX10-LEBAGR28-50" => "Wedding 3",
		"EX10-LEBALB28-50" => "Wedding 3",
		"EX10-LEBALY28-10" => "Wedding 3",
		"EX10-LEBALY28-50" => "Wedding 3",
		"EX10-LEBAMG28-10" => "Wedding 3",
		"EX10-LEBAMG28-50" => "Wedding 3",
		"EX10-LEBANA28-50" => "Wedding 3",
		"EX10-LEBAOL24-50" => "Wedding 3",
		"EX10-LEBAOL28-10" => "Wedding 3",
		"EX10-LEBAOL28-50" => "Wedding 3",
		"EX10-LEBAOR28-50" => "Wedding 3",
		"EX10-LEBAPI28-50" => "Wedding 3",
		"EX10-LEBAQM28-50" => "Wedding 3",
		"EX10-LEBAQMPF-50" => "Wedding 3",
		"EX10-LEBARE28-50" => "Wedding 3",
		"EX10-LEBASM28-50" => "Wedding 3",
		"EX10-LEBASMPF-50" => "Wedding 3",
		"EX10-LEBATE24-50" => "Wedding 3",
		"EX10-LEBATE28-50" => "Wedding 3",
		"EX10-LEBAWH28-50" => "Wedding 3",
		"EX4010-10-50" => "Wedding 3",
		"EX4010-11-50" => "Wedding 3",
		"EX4010-12-50" => "Wedding 3",
		"EX4010-13-50" => "Wedding 3",
		"EX4010-14-50" => "Wedding 3",
		"EX4010-15-50" => "Wedding 3",
		"EX4010-16-50" => "Wedding 3",
		"EX4010-17-50" => "Wedding 3",
		"EX4010-18-50" => "Wedding 3",
		"EX4010-22-50" => "Wedding 3",
		"EX4010-23-50" => "Wedding 3",
		"EX4010-25-50" => "Wedding 3",
		"EX4010-26-50" => "Wedding 3",
		"EX4010-56-50" => "Wedding 3",
		"EX4020-15-50" => "Wedding 3",
		"EX4020-17-50" => "Wedding 3",
		"EX4020-18-50" => "Wedding 3",
		"EX4020-22-50" => "Wedding 3",
		"EX4020-23-50" => "Wedding 3",
		"EX4020-25-50" => "Wedding 3",
		"EX4030-10-50" => "Wedding 3",
		"EX4030-11-50" => "Wedding 3",
		"EX4030-12-50" => "Wedding 3",
		"EX4030-13-50" => "Wedding 3",
		"EX4030-15-50" => "Wedding 3",
		"EX4030-26-50" => "Wedding 3",
		"EX4030-56-50" => "Wedding 3",
		"EX4040-11-50" => "Wedding 3",
		"EX4040-13-50" => "Wedding 3",
		"EX4040-15-50" => "Wedding 3",
		"EX4040-25-50" => "Wedding 3",
		"EX4060-12-50" => "Wedding 3",
		"EX4060-13-50" => "Wedding 3",
		"EX4060-14-50" => "Wedding 3",
		"EX4060-16-50" => "Wedding 3",
		"EX4060-17-50" => "Wedding 3",
		"EX4060-22-50" => "Wedding 3",
		"EX4060-26-50" => "Wedding 3",
		"EX4060-27-50" => "Wedding 3",
		"EX4865-10-50" => "Wedding 3",
		"EX4865-12-50" => "Wedding 3",
		"EX4865-13-50" => "Wedding 3",
		"EX4865-15-50" => "Wedding 3",
		"EX4865-16-50" => "Wedding 3",
		"EX4865-17-50" => "Wedding 3",
		"EX4865-18-50" => "Wedding 3",
		"EX4865-22-50" => "Wedding 3",
		"EX4865-23-50" => "Wedding 3",
		"EX4865-26-50" => "Wedding 3",
		"EX4870-11-50" => "Wedding 3",
		"EX4870-13-50" => "Wedding 3",
		"EX4870-15-50" => "Wedding 3",
		"EX4870-17-50" => "Wedding 3",
		"EX4875-12-50" => "Wedding 3",
		"EX4875-15-50" => "Wedding 3",
		"EX4875-17-50" => "Wedding 3",
		"EX4875-19-50" => "Wedding 3",
		"EX5010-10-50" => "Wedding 3",
		"EX5010-12-50" => "Wedding 3",
		"EX5010-13-50" => "Wedding 3",
		"EX5010-14-50" => "Wedding 3",
		"EX5010-15-50" => "Wedding 3",
		"EX5010-16-50" => "Wedding 3",
		"EX5010-17-50" => "Wedding 3",
		"EX5010-21-50" => "Wedding 3",
		"EX5010-22-50" => "Wedding 3",
		"EX5010-25-50" => "Wedding 3",
		"EX5010-26-50" => "Wedding 3",
		"EX5010-27-50" => "Wedding 3",
		"EX5010-28-50" => "Wedding 3",
		"EX5020-10-50" => "Wedding 3",
		"EX5020-11-50" => "Wedding 3",
		"EX5020-12-50" => "Wedding 3",
		"EX5020-13-50" => "Wedding 3",
		"EX5020-15-50" => "Wedding 3",
		"EX5020-16-50" => "Wedding 3",
		"EX5020-17-50" => "Wedding 3",
		"EX5020-18-50" => "Wedding 3",
		"EX5020-21-50" => "Wedding 3",
		"EX5020-22-50" => "Wedding 3",
		"EX5020-23-50" => "Wedding 3",
		"EX5020-25-50" => "Wedding 3",
		"EX5020-26-50" => "Wedding 3",
		"EX5020-27-50" => "Wedding 3",
		"EX5030-10-50" => "Wedding 3",
		"EX5030-11-50" => "Wedding 3",
		"EX5030-12-50" => "Wedding 3",
		"EX5030-15-50" => "Wedding 3",
		"EX5030-16-50" => "Wedding 3",
		"EX5030-17-50" => "Wedding 3",
		"EX5030-22-50" => "Wedding 3",
		"EX5030-23-50" => "Wedding 3",
		"EX5030-26-50" => "Wedding 3",
		"EX5030-27-50" => "Wedding 3",
		"EX5040-10-50" => "Wedding 3",
		"EX5040-11-50" => "Wedding 3",
		"EX5040-13-50" => "Wedding 3",
		"EX5040-14-50" => "Wedding 3",
		"EX5040-18-50" => "Wedding 3",
		"EX5040-23-50" => "Wedding 3",
		"EX5040-25-50" => "Wedding 3",
		"EX5040-28-50" => "Wedding 3",
		"EX5040-56-50" => "Wedding 3",
		"EX5060-10-50" => "Wedding 3",
		"EX5060-12-50" => "Wedding 3",
		"EX5060-14-50" => "Wedding 3",
		"EX5060-15-50" => "Wedding 3",
		"EX5060-17-50" => "Wedding 3",
		"EX5060-21-50" => "Wedding 3",
		"EX5060-22-50" => "Wedding 3",
		"EX5060-23-50" => "Wedding 3",
		"EX5060-26-50" => "Wedding 3",
		"EX5060-27-50" => "Wedding 3",
		"FA4010-01-50" => "Wedding 3",
		"FA4010-02-50" => "Wedding 3",
		"FA4010-03-50" => "Wedding 3",
		"FA4010-04-50" => "Wedding 3",
		"FA4010-05-50" => "Wedding 3",
		"FA4010-06-50" => "Wedding 3",
		"FA4010-07-50" => "Wedding 3",
		"FA4020-02-50" => "Wedding 3",
		"FA4020-03-50" => "Wedding 3",
		"FA4020-04-50" => "Wedding 3",
		"FA4020-05-50" => "Wedding 3",
		"FA4020-06-50" => "Wedding 3",
		"FA4020-07-50" => "Wedding 3",
		"FA4030-01-50" => "Wedding 3",
		"FA4030-03-50" => "Wedding 3",
		"FA4030-04-50" => "Wedding 3",
		"FA4030-05-50" => "Wedding 3",
		"FA4030-06-50" => "Wedding 3",
		"FA4030-07-50" => "Wedding 3",
		"FA4040-01-50" => "Wedding 3",
		"FA4040-02-50" => "Wedding 3",
		"FA4040-03-50" => "Wedding 3",
		"FA4040-06-50" => "Wedding 3",
		"FA4040-07-50" => "Wedding 3",
		"FA4060-01-50" => "Wedding 3",
		"FA4060-02-50" => "Wedding 3",
		"FA4060-03-50" => "Wedding 3",
		"FA4060-04-50" => "Wedding 3",
		"FA4060-05-50" => "Wedding 3",
		"FA4060-06-50" => "Wedding 3",
		"FA4060-07-50" => "Wedding 3",
		"FA4865-03-50" => "Wedding 3",
		"FA4865-04-50" => "Wedding 3",
		"FA4865-06-50" => "Wedding 3",
		"FA4865-07-50" => "Wedding 3",
		"FA4870-01-50" => "Wedding 3",
		"FA4870-02-50" => "Wedding 3",
		"FA4870-04-50" => "Wedding 3",
		"FA4870-06-50" => "Wedding 3",
		"FA4875-03-50" => "Wedding 3",
		"FA4875-05-50" => "Wedding 3",
		"FA4895-05-50" => "Wedding 3",
		"FA5010-01-50" => "Wedding 3",
		"FA5010-02-50" => "Wedding 3",
		"FA5010-05-50" => "Wedding 3",
		"FA5010-06-50" => "Wedding 3",
		"FA5020-01-50" => "Wedding 3",
		"FA5020-02-50" => "Wedding 3",
		"FA5020-03-50" => "Wedding 3",
		"FA5020-04-50" => "Wedding 3",
		"FA5020-05-50" => "Wedding 3",
		"FA5020-06-50" => "Wedding 3",
		"FA5020-07-50" => "Wedding 3",
		"FA5030-02-50" => "Wedding 3",
		"FA5030-03-50" => "Wedding 3",
		"FA5030-04-50" => "Wedding 3",
		"FA5030-05-50" => "Wedding 3",
		"FA5030-06-50" => "Wedding 3",
		"FA5040-01-50" => "Wedding 3",
		"FA5040-02-50" => "Wedding 3",
		"FA5040-03-50" => "Wedding 3",
		"FA5040-04-50" => "Wedding 3",
		"FA5040-05-50" => "Wedding 3",
		"FA5040-07-50" => "Wedding 3",
		"FA5060-01-50" => "Wedding 3",
		"FA5060-02-50" => "Wedding 3",
		"FA5060-04-50" => "Wedding 3",
		"FA5060-05-50" => "Wedding 3",
		"FA5060-07-50" => "Wedding 3",
		"FE4270-20-50" => "Wedding 3",
		"FE4275-20-50" => "Wedding 3",
		"FE4565-05-50" => "Wedding 3",
		"FE4575-05-50" => "Wedding 3",
		"FEA7GF-22-50" => "Wedding 3",
		"FEXA7ZF-22-10" => "Wedding 3",
		"FEXA7ZF-22-50" => "Wedding 3",
		"LUX-4865-101-50" => "Wedding 3",
		"LUX-4865-102-50" => "Wedding 3",
		"LUX-4865-106-50" => "Wedding 3",
		"LUX-4865-112-50" => "Wedding 3",
		"LUX-4865-113-50" => "Wedding 3",
		"LUX-4870-104-50" => "Wedding 3",
		"LUX-4870-106-50" => "Wedding 3",
		"LUX-4870-112-50" => "Wedding 3",
		"LUX-4875-102-50" => "Wedding 3",
		"LUX-4875-106-50" => "Wedding 3",
		"LUX-5040-07-50" => "Wedding 3",
		"LUX-5040-102-50" => "Wedding 3",
		"LUX-5040-L05-50" => "Wedding 3",
		"LUX-5040-L17-50" => "Wedding 3",
		"LUX-5040-L20-50" => "Wedding 3",
		"LUX-5040-L22-50" => "Wedding 3",
		"LUX-A7PKT103-50" => "Wedding 3",
		"LUX-A7PKT104-50" => "Wedding 3",
		"LUX-A7PKT106-50" => "Wedding 3",
		"LUX-A7PTL103-50" => "Wedding 3",
		"LUX-A7PTL104-50" => "Wedding 3",
		"LUX-A7PTL106-50" => "Wedding 3",
		"LUX-MP-04-50" => "Wedding 3",
		"LUXA7GF-05-10" => "Wedding 3",
		"LUXA7GF-05-50" => "Wedding 3",
		"LUXA7GF-07-50" => "Wedding 3",
		"LUXA7GF-10-10" => "Wedding 3",
		"LUXA7GF-10-50" => "Wedding 3",
		"LUXA7GF-101-50" => "Wedding 3",
		"LUXA7GF-102-10" => "Wedding 3",
		"LUXA7GF-102-50" => "Wedding 3",
		"LUXA7GF-103-50" => "Wedding 3",
		"LUXA7GF-104-10" => "Wedding 3",
		"LUXA7GF-104-50" => "Wedding 3",
		"LUXA7GF-106-10" => "Wedding 3",
		"LUXA7GF-106-50" => "Wedding 3",
		"LUXA7GF-11-50" => "Wedding 3",
		"LUXA7GF-112-10" => "Wedding 3",
		"LUXA7GF-112-50" => "Wedding 3",
		"LUXA7GF-113-10" => "Wedding 3",
		"LUXA7GF-113-50" => "Wedding 3",
		"LUXA7GF-12-10" => "Wedding 3",
		"LUXA7GF-12-50" => "Wedding 3",
		"LUXA7GF-13-50" => "Wedding 3",
		"LUXA7GF-14-10" => "Wedding 3",
		"LUXA7GF-14-50" => "Wedding 3",
		"LUXA7GF-15-10" => "Wedding 3",
		"LUXA7GF-15-50" => "Wedding 3",
		"LUXA7GF-17-50" => "Wedding 3",
		"LUXA7GF-18-50" => "Wedding 3",
		"LUXA7GF-22-10" => "Wedding 3",
		"LUXA7GF-22-50" => "Wedding 3",
		"LUXA7GF-23-10" => "Wedding 3",
		"LUXA7GF-23-50" => "Wedding 3",
		"LUXA7GF-25-50" => "Wedding 3",
		"LUXA7GF-26-50" => "Wedding 3",
		"LUXA7GF-B-50" => "Wedding 3",
		"LUXA7ZF-05-10" => "Wedding 3",
		"LUXA7ZF-05-50" => "Wedding 3",
		"LUXA7ZF-07-50" => "Wedding 3",
		"LUXA7ZF-10-10" => "Wedding 3",
		"LUXA7ZF-10-50" => "Wedding 3",
		"LUXA7ZF-101-10" => "Wedding 3",
		"LUXA7ZF-101-50" => "Wedding 3",
		"LUXA7ZF-102-10" => "Wedding 3",
		"LUXA7ZF-102-50" => "Wedding 3",
		"LUXA7ZF-103-10" => "Wedding 3",
		"LUXA7ZF-103-50" => "Wedding 3",
		"LUXA7ZF-104-10" => "Wedding 3",
		"LUXA7ZF-104-50" => "Wedding 3",
		"LUXA7ZF-106-10" => "Wedding 3",
		"LUXA7ZF-106-50" => "Wedding 3",
		"LUXA7ZF-11-50" => "Wedding 3",
		"LUXA7ZF-112-50" => "Wedding 3",
		"LUXA7ZF-113-50" => "Wedding 3",
		"LUXA7ZF-12-10" => "Wedding 3",
		"LUXA7ZF-12-50" => "Wedding 3",
		"LUXA7ZF-13-10" => "Wedding 3",
		"LUXA7ZF-13-50" => "Wedding 3",
		"LUXA7ZF-14-10" => "Wedding 3",
		"LUXA7ZF-14-50" => "Wedding 3",
		"LUXA7ZF-15-10" => "Wedding 3",
		"LUXA7ZF-15-50" => "Wedding 3",
		"LUXA7ZF-17-10" => "Wedding 3",
		"LUXA7ZF-17-50" => "Wedding 3",
		"LUXA7ZF-18-10" => "Wedding 3",
		"LUXA7ZF-18-50" => "Wedding 3",
		"LUXA7ZF-22-10" => "Wedding 3",
		"LUXA7ZF-22-50" => "Wedding 3",
		"LUXA7ZF-23-10" => "Wedding 3",
		"LUXA7ZF-23-50" => "Wedding 3",
		"LUXA7ZF-25-10" => "Wedding 3",
		"LUXA7ZF-25-50" => "Wedding 3",
		"LUXA7ZF-26-10" => "Wedding 3",
		"LUXA7ZF-26-50" => "Wedding 3",
		"LUXA7ZF-B-50" => "Wedding 3",
		"MR4865-01-50" => "Wedding 3",
		"MR4875-01-50" => "Wedding 3",
		"MR4880-02-50" => "Wedding 3",
		"PGCST919-50" => "Wedding 3",
		"PGCST940-50" => "Wedding 3",
		"SH4270-01-50" => "Wedding 3",
		"SH4270-02-50" => "Wedding 3",
		"SH4270-07-50" => "Wedding 3",
		"SH4275-02-50" => "Wedding 3",
		"SH4275-04-50" => "Wedding 3",
		"17MFN-50" => "Wedding 3",
		"1840-08-50" => "Wedding 3",
		"1840-B-50" => "Wedding 3",
		"1840-NPC-50" => "Wedding 3",
		"5080-06-50" => "Wedding 3",
		"5080-07-50" => "Wedding 3",
		"5080-08-50" => "Wedding 3",
		"5080-BLI-50" => "Wedding 3",
		"5080-NLI-50" => "Wedding 3",
		"5080-NPC-50" => "Wedding 3",
		"5080-SN-50" => "Wedding 3",
		"5080-SW-50" => "Wedding 3",
		"5080-WLI-50" => "Wedding 3",
		"5080-WPC-50" => "Wedding 3",
		"614GF-06-50" => "Wedding 3",
		"614GF-07-10" => "Wedding 3",
		"614GF-07-50" => "Wedding 3",
		"614GF-08-50" => "Wedding 3",
		"614GF-30-10" => "Wedding 3",
		"614GF-30-50" => "Wedding 3",
		"614GF-80N-50" => "Wedding 3",
		"614GF-80W-10" => "Wedding 3",
		"614GF-80W-50" => "Wedding 3",
		"614GF-BLI-50" => "Wedding 3",
		"614GF-GB-10" => "Wedding 3",
		"614GF-GB-50" => "Wedding 3",
		"614GF-NLI-10" => "Wedding 3",
		"614GF-NLI-50" => "Wedding 3",
		"614GF-NPC-10" => "Wedding 3",
		"614GF-NPC-50" => "Wedding 3",
		"614GF-SN-10" => "Wedding 3",
		"614GF-SN-50" => "Wedding 3",
		"614GF-SW-10" => "Wedding 3",
		"614GF-SW-50" => "Wedding 3",
		"614GF-WLI-10" => "Wedding 3",
		"614GF-WLI-50" => "Wedding 3",
		"614GF-WPC-50" => "Wedding 3",
		"614PTLGB-50" => "Wedding 3",
		"614SQFLD-SN-50" => "Wedding 3",
		"614ZF-06-50" => "Wedding 3",
		"614ZF-07-10" => "Wedding 3",
		"614ZF-07-50" => "Wedding 3",
		"614ZF-08-10" => "Wedding 3",
		"614ZF-08-50" => "Wedding 3",
		"614ZF-30-10" => "Wedding 3",
		"614ZF-30-50" => "Wedding 3",
		"614ZF-80N-10" => "Wedding 3",
		"614ZF-80N-50" => "Wedding 3",
		"614ZF-80W-50" => "Wedding 3",
		"614ZF-BLI-10" => "Wedding 3",
		"614ZF-BLI-50" => "Wedding 3",
		"614ZF-NLI-10" => "Wedding 3",
		"614ZF-NLI-50" => "Wedding 3",
		"614ZF-NPC-10" => "Wedding 3",
		"614ZF-NPC-50" => "Wedding 3",
		"614ZF-SN-10" => "Wedding 3",
		"614ZF-SN-50" => "Wedding 3",
		"614ZF-SW-50" => "Wedding 3",
		"614ZF-WLI-10" => "Wedding 3",
		"614ZF-WLI-50" => "Wedding 3",
		"614ZF-WPC-10" => "Wedding 3",
		"614ZF-WPC-50" => "Wedding 3",
		"8503-01-50" => "Wedding 3",
		"8503-07-50" => "Wedding 3",
		"8503-18-50" => "Wedding 3",
		"8503-B-50" => "Wedding 3",
		"8503-M07-50" => "Wedding 3",
		"8504-01-50" => "Wedding 3",
		"8504-06-50" => "Wedding 3",
		"8504-07-50" => "Wedding 3",
		"8504-18-50" => "Wedding 3",
		"8505-03-50" => "Wedding 3",
		"8505-80W-50" => "Wedding 3",
		"8505-NLI-50" => "Wedding 3",
		"8505-NPC-50" => "Wedding 3",
		"8505-SW-50" => "Wedding 3",
		"8505-WLI-50" => "Wedding 3",
		"8510-01-50" => "Wedding 3",
		"8510-06-50" => "Wedding 3",
		"8510-07-50" => "Wedding 3",
		"8510-SW-50" => "Wedding 3",
		"8510-WPC-50" => "Wedding 3",
		"8515-06-50" => "Wedding 3",
		"8515-12-50" => "Wedding 3",
		"8515-20-50" => "Wedding 3",
		"8515-BLI-50" => "Wedding 3",
		"8515-GB-50" => "Wedding 3",
		"8515-M07-50" => "Wedding 3",
		"8515-NLI-50" => "Wedding 3",
		"8515-NPC-50" => "Wedding 3",
		"8515-SN-50" => "Wedding 3",
		"8515-WLI-50" => "Wedding 3",
		"8515-WPC-50" => "Wedding 3",
		"8520-06-50" => "Wedding 3",
		"8520-SW-50" => "Wedding 3",
		"8530-01-50" => "Wedding 3",
		"8530-07-50" => "Wedding 3",
		"8530-BLI-50" => "Wedding 3",
		"8530-NLI-50" => "Wedding 3",
		"8530-WLI-50" => "Wedding 3",
		"8545-06-50" => "Wedding 3",
		"8545-07-50" => "Wedding 3",
		"8555-03-50" => "Wedding 3",
		"8575-03-50" => "Wedding 3",
		"8575-06-50" => "Wedding 3",
		"8575-07-50" => "Wedding 3",
		"8585-03-50" => "Wedding 3",
		"8585-06-50" => "Wedding 3",
		"8585-50-50" => "Wedding 3",
		"8585-GB-50" => "Wedding 3",
		"EX10-LEBA527PC-50" => "Wedding 3",
		"EX10-LEBA528PC-10" => "Wedding 3",
		"EX10-LEBA528PC-50" => "Wedding 3",
		"EX10-LEBA529PC-10" => "Wedding 3",
		"EX10-LEBA529PC-50" => "Wedding 3",
		"EX10-LEBA530PC-10" => "Wedding 3",
		"EX10-LEBA530PC-50" => "Wedding 3",
		"EX10-LEBA531PC-50" => "Wedding 3",
		"EX10-LEBA532PC-10" => "Wedding 3",
		"EX10-LEBA532PC-50" => "Wedding 3",
		"EX10-LEBA534PC-50" => "Wedding 3",
		"EX10-LEBA536PC-50" => "Wedding 3",
		"EX10-LEBA537PC-50" => "Wedding 3",
		"EX10-LEBA538PC-50" => "Wedding 3",
		"EX10-LEBA539PC-50" => "Wedding 3",
		"EX10-LEBA541PC-50" => "Wedding 3",
		"EX10-LEBA543PC-50" => "Wedding 3",
		"EX10-LEBACMPC-10" => "Wedding 3",
		"EX10-LEBADR23-50" => "Wedding 3",
		"EX10-LEBARU23-10" => "Wedding 3",
		"EX10-LEBARU23-50" => "Wedding 3",
		"EX10-LEBATE23-50" => "Wedding 3",
		"EX4080-10-50" => "Wedding 3",
		"EX4080-11-50" => "Wedding 3",
		"EX4080-12-50" => "Wedding 3",
		"EX4080-13-50" => "Wedding 3",
		"EX4080-14-50" => "Wedding 3",
		"EX4080-15-50" => "Wedding 3",
		"EX4080-18-50" => "Wedding 3",
		"EX4080-19-50" => "Wedding 3",
		"EX4080-22-50" => "Wedding 3",
		"EX4080-25-50" => "Wedding 3",
		"EX8515-10-50" => "Wedding 3",
		"EX8515-15-50" => "Wedding 3",
		"EX8515-17-50" => "Wedding 3",
		"EX8515-25-50" => "Wedding 3",
		"EX8515-26-50" => "Wedding 3",
		"EX8555-11-50" => "Wedding 3",
		"EX8555-12-50" => "Wedding 3",
		"EX8555-17-50" => "Wedding 3",
		"EX8555-18-50" => "Wedding 3",
		"EX8555-19-50" => "Wedding 3",
		"EX8555-22-50" => "Wedding 3",
		"EXLEVC-12-50" => "Wedding 3",
		"EXLEVC-13-50" => "Wedding 3",
		"EXLEVC-15-50" => "Wedding 3",
		"EXLEVC-17-50" => "Wedding 3",
		"F-8545-B-50" => "Wedding 3",
		"F-8555-B-50" => "Wedding 3",
		"F-8585-B-50" => "Wedding 3",
		"F-8595-B-50" => "Wedding 3",
		"FE614GF-22-10" => "Wedding 3",
		"FE614GF-22-50" => "Wedding 3",
		"FE614ZF-22-10" => "Wedding 3",
		"FE614ZF-22-50" => "Wedding 3",
		"FLWH8535-03-50" => "Wedding 3",
		"LEVC933-50" => "Wedding 3",
		"LEVCBLK-50" => "Wedding 3",
		"LEVCGLD-50" => "Wedding 3",
		"LEVCSIL-50" => "Wedding 3",
		"LUX-5080-10-50" => "Wedding 3",
		"LUX-5080-102-50" => "Wedding 3",
		"LUX-5080-103-50" => "Wedding 3",
		"LUX-5080-106-50" => "Wedding 3",
		"LUX-5080-11-50" => "Wedding 3",
		"LUX-5080-112-50" => "Wedding 3",
		"LUX-5080-113-50" => "Wedding 3",
		"LUX-5080-12-50" => "Wedding 3",
		"LUX-5080-13-50" => "Wedding 3",
		"LUX-5080-17-50" => "Wedding 3",
		"LUX-5080-18-50" => "Wedding 3",
		"LUX-5080-22-50" => "Wedding 3",
		"LUX-5080-25-50" => "Wedding 3",
		"LUX-5080-26-50" => "Wedding 3",
		"LUX-5080-B-50" => "Wedding 3",
		"LUX-614PTL103-50" => "Wedding 3",
		"LUX-614PTL104-50" => "Wedding 3",
		"LUX-614PTL106-50" => "Wedding 3",
		"LUX-614PTL113-10" => "Wedding 3",
		"LUX-614PTL113-50" => "Wedding 3",
		"LUX-8505-102-50" => "Wedding 3",
		"LUX-8505-103-50" => "Wedding 3",
		"LUX-8505-104-50" => "Wedding 3",
		"LUX-8510-18-50" => "Wedding 3",
		"LUX-8510-B-50" => "Wedding 3",
		"LUX-8520-B-50" => "Wedding 3",
		"LUX-8530-18-50" => "Wedding 3",
		"LUX-8545-106-50" => "Wedding 3",
		"LUX-8545-18-50" => "Wedding 3",
		"LUX-8555-113-50" => "Wedding 3",
		"LUX-8565-18-50" => "Wedding 3",
		"LUX-8585-18-50" => "Wedding 3",
		"LUX614GF-05-10" => "Wedding 3",
		"LUX614GF-05-50" => "Wedding 3",
		"LUX614GF-07-10" => "Wedding 3",
		"LUX614GF-07-50" => "Wedding 3",
		"LUX614GF-10-10" => "Wedding 3",
		"LUX614GF-10-50" => "Wedding 3",
		"LUX614GF-101-10" => "Wedding 3",
		"LUX614GF-101-50" => "Wedding 3",
		"LUX614GF-102-10" => "Wedding 3",
		"LUX614GF-102-50" => "Wedding 3",
		"LUX614GF-103-50" => "Wedding 3",
		"LUX614GF-104-10" => "Wedding 3",
		"LUX614GF-104-50" => "Wedding 3",
		"LUX614GF-106-10" => "Wedding 3",
		"LUX614GF-106-50" => "Wedding 3",
		"LUX614GF-11-10" => "Wedding 3",
		"LUX614GF-11-50" => "Wedding 3",
		"LUX614GF-112-10" => "Wedding 3",
		"LUX614GF-112-50" => "Wedding 3",
		"LUX614GF-113-10" => "Wedding 3",
		"LUX614GF-113-50" => "Wedding 3",
		"LUX614GF-12-10" => "Wedding 3",
		"LUX614GF-12-50" => "Wedding 3",
		"LUX614GF-13-10" => "Wedding 3",
		"LUX614GF-13-50" => "Wedding 3",
		"LUX614GF-14-10" => "Wedding 3",
		"LUX614GF-14-50" => "Wedding 3",
		"LUX614GF-15-10" => "Wedding 3",
		"LUX614GF-15-50" => "Wedding 3",
		"LUX614GF-17-50" => "Wedding 3",
		"LUX614GF-18-10" => "Wedding 3",
		"LUX614GF-18-50" => "Wedding 3",
		"LUX614GF-22-10" => "Wedding 3",
		"LUX614GF-22-50" => "Wedding 3",
		"LUX614GF-23-10" => "Wedding 3",
		"LUX614GF-23-50" => "Wedding 3",
		"LUX614GF-25-10" => "Wedding 3",
		"LUX614GF-25-50" => "Wedding 3",
		"LUX614GF-26-10" => "Wedding 3",
		"LUX614GF-26-50" => "Wedding 3",
		"LUX614GF-B-10" => "Wedding 3",
		"LUX614GF-B-50" => "Wedding 3",
		"LUX614ZF-05-10" => "Wedding 3",
		"LUX614ZF-05-50" => "Wedding 3",
		"LUX614ZF-07-10" => "Wedding 3",
		"LUX614ZF-07-50" => "Wedding 3",
		"LUX614ZF-10-10" => "Wedding 3",
		"LUX614ZF-10-50" => "Wedding 3",
		"LUX614ZF-101-10" => "Wedding 3",
		"LUX614ZF-101-50" => "Wedding 3",
		"LUX614ZF-102-10" => "Wedding 3",
		"LUX614ZF-102-50" => "Wedding 3",
		"LUX614ZF-103-10" => "Wedding 3",
		"LUX614ZF-103-50" => "Wedding 3",
		"LUX614ZF-104-10" => "Wedding 3",
		"LUX614ZF-104-50" => "Wedding 3",
		"LUX614ZF-106-50" => "Wedding 3",
		"LUX614ZF-11-10" => "Wedding 3",
		"LUX614ZF-11-50" => "Wedding 3",
		"LUX614ZF-112-10" => "Wedding 3",
		"LUX614ZF-112-50" => "Wedding 3",
		"LUX614ZF-113-10" => "Wedding 3",
		"LUX614ZF-113-50" => "Wedding 3",
		"LUX614ZF-12-10" => "Wedding 3",
		"LUX614ZF-12-50" => "Wedding 3",
		"LUX614ZF-13-10" => "Wedding 3",
		"LUX614ZF-13-50" => "Wedding 3",
		"LUX614ZF-14-10" => "Wedding 3",
		"LUX614ZF-14-50" => "Wedding 3",
		"LUX614ZF-15-10" => "Wedding 3",
		"LUX614ZF-15-50" => "Wedding 3",
		"LUX614ZF-17-10" => "Wedding 3",
		"LUX614ZF-17-50" => "Wedding 3",
		"LUX614ZF-18-10" => "Wedding 3",
		"LUX614ZF-18-50" => "Wedding 3",
		"LUX614ZF-22-10" => "Wedding 3",
		"LUX614ZF-22-50" => "Wedding 3",
		"LUX614ZF-23-10" => "Wedding 3",
		"LUX614ZF-23-50" => "Wedding 3",
		"LUX614ZF-25-10" => "Wedding 3",
		"LUX614ZF-25-50" => "Wedding 3",
		"LUX614ZF-26-10" => "Wedding 3",
		"LUX614ZF-26-50" => "Wedding 3",
		"LUX614ZF-B-10" => "Wedding 3",
		"LUX614ZF-B-50" => "Wedding 3",
		"LUXLEVC-102-50" => "Wedding 3",
		"MINSDQ-50" => "Wedding 3",
		"614BB-MS08-50" => "Wedding 3",
		"A7BB-MS01-10" => "Wedding 3",
		"A7BB-MS01-50" => "Wedding 3",
		"A7BB-MS02-50" => "Wedding 3",
		"A7BB-MS03-50" => "Wedding 3",
		"A7BB-MS08-50" => "Wedding 3",
		"LINER-MS01-50" => "Wedding 3",
		"8504-AO-50" => "Wedding 3",
		"8504-GB-50" => "Wedding 3",
		"4895-GB-50" => "Wedding 3",
		"1865-B-50" => "Wedding 3",
		"4895-WLI-50" => "Wedding 3",
		"5370-M09-50" => "Wedding 3",
		"EX4030-27-50" => "Wedding 3",
		"LUXA7GF-17-10" => "Wedding 3",
		"8503-GB-50" => "Wedding 3",
		"F-8565-B-50" => "Wedding 3",
		"1880-06-50" => "Wedding 3",
		"4040-113-50" => "Wedding 3",
		"4040-MS01-50" => "Wedding 3",
		"4040-SN-50" => "Wedding 3",
		"5365-11-50" => "Wedding 3",
		"5365-M08-50" => "Wedding 3",
		"5380-26-50" => "Wedding 3",
		"A1FW-50" => "Wedding 3",
		"A2CN-50" => "Wedding 3",
		"A2CW-50" => "Wedding 3",
		"BC4040-18-50" => "Wedding 3",
		"EX10-LEBAWH28-10" => "Wedding 3",
		"EX4020-10-50" => "Wedding 3",
		"EX4020-56-50" => "Wedding 3",
		"EX4060-18-50" => "Wedding 3",
		"EX4865-25-50" => "Wedding 3",
		"EX4870-27-50" => "Wedding 3",
		"EX4875-23-50" => "Wedding 3",
		"EX4880-26-50" => "Wedding 3",
		"EX4895-14-50" => "Wedding 3",
		"EX4895-22-50" => "Wedding 3",
		"EX5010-18-50" => "Wedding 3",
		"EX5030-21-50" => "Wedding 3",
		"EX5040-19-50" => "Wedding 3",
		"F-4565-B-50" => "Wedding 3",
		"FA4880-05-50" => "Wedding 3",
		"FE4275-17-50" => "Wedding 3",
		"LUX-4895-L20-50" => "Wedding 3",
		"LUX-A7PTL104-10" => "Wedding 3",
		"LUX-A7PTL106-10" => "Wedding 3",
		"LUXA7GF-18-10" => "Wedding 3",
		"LUXA7GF-26-10" => "Wedding 3",
		"SH4275-08-50" => "Wedding 3",
		"614GF-BLI-10" => "Wedding 3",
		"8515-07-50" => "Wedding 3",
		"8530-SW-50" => "Wedding 3",
		"8595-03-50" => "Wedding 3",
		"EX10-LEBADR23-10" => "Wedding 3",
		"EX4080-17-50" => "Wedding 3",
		"EX8515-22-50" => "Wedding 3",
		"EX8555-13-50" => "Wedding 3",
		"EXLEVC-11-50" => "Wedding 3",
		"FLWH8535-01-50" => "Wedding 3",
		"10350-50" => "Wedding 3",
		"1870-06-50" => "Wedding 3",
		"1870-08-50" => "Wedding 3",
		"1870-B-50" => "Wedding 3",
		"1875-06-50" => "Wedding 3",
		"1875-30-50" => "Wedding 3",
		"1895-08-50" => "Wedding 3",
		"1895-NPC-50" => "Wedding 3",
		"20446-50" => "Wedding 3",
		"4010-113-50" => "Wedding 3",
		"4010-MET-06-50" => "Wedding 3",
		"4010-WPC-50" => "Wedding 3",
		"4020-106-50" => "Wedding 3",
		"4020-SW-50" => "Wedding 3",
		"4030-L17-50" => "Wedding 3",
		"4030-L22-50" => "Wedding 3",
		"4030-SN-50" => "Wedding 3",
		"4040-103-50" => "Wedding 3",
		"4040-104-50" => "Wedding 3",
		"4040-106-50" => "Wedding 3",
		"4040-18GB-50" => "Wedding 3",
		"4040-L22-50" => "Wedding 3",
		"4040-MS08-50" => "Wedding 3",
		"4060-SN-50" => "Wedding 3",
		"4060-SW-50" => "Wedding 3",
		"4865-SBW-50" => "Wedding 3",
		"4865-SN-50" => "Wedding 3",
		"4865-SW-50" => "Wedding 3",
		"4870-SBW-50" => "Wedding 3",
		"4870-SG-50" => "Wedding 3",
		"4870-WCN-50" => "Wedding 3",
		"4870-WLI-50" => "Wedding 3",
		"4875-M08-50" => "Wedding 3",
		"4875-SW-50" => "Wedding 3",
		"4875-WLI-50" => "Wedding 3",
		"4880-24IJ-50" => "Wedding 3",
		"4880-NPC-50" => "Wedding 3",
		"4880-SN-50" => "Wedding 3",
		"4880-WGV-50" => "Wedding 3",
		"4895-NPC-50" => "Wedding 3",
		"4895-SW-50" => "Wedding 3",
		"4895-WPP-50" => "Wedding 3",
		"5040-NLI-50" => "Wedding 3",
		"5040-NPC-50" => "Wedding 3",
		"5040-WLI-50" => "Wedding 3",
		"5040-WPC-50" => "Wedding 3",
		"5365-08-50" => "Wedding 3",
		"5365-15-50" => "Wedding 3",
		"5365-24-50" => "Wedding 3",
		"5365-27-50" => "Wedding 3",
		"5370-02-50" => "Wedding 3",
		"5370-24-50" => "Wedding 3",
		"5370-S02-50" => "Wedding 3",
		"5375-06-50" => "Wedding 3",
		"5375-25-50" => "Wedding 3",
		"5375-27-50" => "Wedding 3",
		"5380-02-50" => "Wedding 3",
		"5380-18-50" => "Wedding 3",
		"5380-29-50" => "Wedding 3",
		"5395-04-50" => "Wedding 3",
		"5395-08-50" => "Wedding 3",
		"5395-11-50" => "Wedding 3",
		"5865-01-50" => "Wedding 3",
		"5870-01-50" => "Wedding 3",
		"5875-01-50" => "Wedding 3",
		"5880-01-50" => "Wedding 3",
		"5880-GL-50" => "Wedding 3",
		"6670-13-50" => "Wedding 3",
		"6670-14-50" => "Wedding 3",
		"A6CN-50" => "Wedding 3",
		"A6FN-50" => "Wedding 3",
		"A6FW-50" => "Wedding 3",
		"A7FN-50" => "Wedding 3",
		"A7GF-06-10" => "Wedding 3",
		"A7GF-BLI-10" => "Wedding 3",
		"A7ZF-08-10" => "Wedding 3",
		"A7ZF-NPC-10" => "Wedding 3",
		"A7ZF-WPC-10" => "Wedding 3",
		"BC4010-102-50" => "Wedding 3",
		"BC4010-11-50" => "Wedding 3",
		"BC4010-18-50" => "Wedding 3",
		"BC4010-22-50" => "Wedding 3",
		"BC4040-06-50" => "Wedding 3",
		"BC4040-102-50" => "Wedding 3",
		"BC4040-14-50" => "Wedding 3",
		"CCA2-50" => "Wedding 3",
		"CCA6-50" => "Wedding 3",
		"ET4870-14-50" => "Wedding 3",
		"ET4880-16-50" => "Wedding 3",
		"EX10-LEBA702PF-10" => "Wedding 3",
		"EX10-LEBA704PF-10" => "Wedding 3",
		"EX10-LEBA718PF-10" => "Wedding 3",
		"EX10-LEBABK28-10" => "Wedding 3",
		"EX10-LEBABL28-10" => "Wedding 3",
		"EX10-LEBACMPF-10" => "Wedding 3",
		"EX10-LEBADR28-10" => "Wedding 3",
		"EX10-LEBAGO28-10" => "Wedding 3",
		"EX10-LEBALB28-10" => "Wedding 3",
		"EX10-LEBAOL24-10" => "Wedding 3",
		"EX10-LEBAOR28-10" => "Wedding 3",
		"EX10-LEBAQM28-10" => "Wedding 3",
		"EX10-LEBAQMPF-10" => "Wedding 3",
		"EX4020-14-50" => "Wedding 3",
		"EX4020-27-50" => "Wedding 3",
		"EX4030-17-50" => "Wedding 3",
		"EX4030-18-50" => "Wedding 3",
		"EX4030-19-50" => "Wedding 3",
		"EX4030-23-50" => "Wedding 3",
		"EX4040-10-50" => "Wedding 3",
		"EX4040-12-50" => "Wedding 3",
		"EX4040-17-50" => "Wedding 3",
		"EX4040-18-50" => "Wedding 3",
		"EX4040-22-50" => "Wedding 3",
		"EX4040-23-50" => "Wedding 3",
		"EX4040-26-50" => "Wedding 3",
		"EX4040-27-50" => "Wedding 3",
		"EX4060-10-50" => "Wedding 3",
		"EX4060-11-50" => "Wedding 3",
		"EX4060-15-50" => "Wedding 3",
		"EX4060-19-50" => "Wedding 3",
		"EX4060-25-50" => "Wedding 3",
		"EX4865-27-50" => "Wedding 3",
		"EX4870-10-50" => "Wedding 3",
		"EX4870-25-50" => "Wedding 3",
		"EX4875-10-50" => "Wedding 3",
		"EX4875-11-50" => "Wedding 3",
		"EX4875-13-50" => "Wedding 3",
		"EX4880-15-50" => "Wedding 3",
		"EX4895-15-50" => "Wedding 3",
		"EX4895-17-50" => "Wedding 3",
		"EX4895-23-50" => "Wedding 3",
		"EX4895-26-50" => "Wedding 3",
		"EX4895-27-50" => "Wedding 3",
		"EX5010-11-50" => "Wedding 3",
		"EX5020-28-50" => "Wedding 3",
		"EX5040-12-50" => "Wedding 3",
		"EX5040-15-50" => "Wedding 3",
		"EX5040-22-50" => "Wedding 3",
		"EX5060-11-50" => "Wedding 3",
		"EX5060-18-50" => "Wedding 3",
		"EX5060-25-50" => "Wedding 3",
		"EX5060-28-50" => "Wedding 3",
		"FA4020-01-50" => "Wedding 3",
		"FA4040-04-50" => "Wedding 3",
		"FA4865-02-50" => "Wedding 3",
		"FA4865-05-50" => "Wedding 3",
		"FA4870-05-50" => "Wedding 3",
		"FA4870-07-50" => "Wedding 3",
		"FA4875-02-50" => "Wedding 3",
		"FA4875-04-50" => "Wedding 3",
		"FA4875-06-50" => "Wedding 3",
		"FA4880-02-50" => "Wedding 3",
		"FA4880-04-50" => "Wedding 3",
		"FA4880-06-50" => "Wedding 3",
		"FA4895-01-50" => "Wedding 3",
		"FA4895-04-50" => "Wedding 3",
		"FA4895-06-50" => "Wedding 3",
		"FA4895-07-50" => "Wedding 3",
		"FA5010-07-50" => "Wedding 3",
		"FA5030-01-50" => "Wedding 3",
		"FA5030-07-50" => "Wedding 3",
		"FA5040-06-50" => "Wedding 3",
		"FA5060-03-50" => "Wedding 3",
		"FA5060-06-50" => "Wedding 3",
		"FE4270-15-50" => "Wedding 3",
		"FE4275-22-50" => "Wedding 3",
		"FEA7GF-22-10" => "Wedding 3",
		"LUX-4875-112-50" => "Wedding 3",
		"LUX-4880-102-50" => "Wedding 3",
		"LUX-4895-113-50" => "Wedding 3",
		"LUX-5040-101-50" => "Wedding 3",
		"LUX-5040-103-50" => "Wedding 3",
		"LUX-5040-104-50" => "Wedding 3",
		"LUX-5040-112-50" => "Wedding 3",
		"LUXA7GF-07-10" => "Wedding 3",
		"LUXA7GF-13-10" => "Wedding 3",
		"LUXA7GF-25-10" => "Wedding 3",
		"LUXA7ZF-11-10" => "Wedding 3",
		"LUXA7ZF-B-10" => "Wedding 3",
		"MR4880-01-50" => "Wedding 3",
		"MR4895-02-50" => "Wedding 3",
		"PGCST909-50" => "Wedding 3",
		"PGCST950-50" => "Wedding 3",
		"SH4270-04-50" => "Wedding 3",
		"SH4270-05-50" => "Wedding 3",
		"SH4270-08-50" => "Wedding 3",
		"SH4275-01-50" => "Wedding 3",
		"SH4275-05-50" => "Wedding 3",
		"SH4275-07-50" => "Wedding 3",
		"11017-50" => "Wedding 3",
		"17MFW-50" => "Wedding 3",
		"614GF-06-10" => "Wedding 3",
		"614GF-08-10" => "Wedding 3",
		"614PTLGB-10" => "Wedding 3",
		"614SQFLD-SW-50" => "Wedding 3",
		"614SQFLT-SN-50" => "Wedding 3",
		"614SQFLT-SW-50" => "Wedding 3",
		"614ZF-06-10" => "Wedding 3",
		"614ZF-SW-10" => "Wedding 3",
		"8503-06-50" => "Wedding 3",
		"8503-103-50" => "Wedding 3",
		"8503-M08-50" => "Wedding 3",
		"8504-103-50" => "Wedding 3",
		"8504-B-50" => "Wedding 3",
		"8505-20-50" => "Wedding 3",
		"8505-SN-50" => "Wedding 3",
		"8505-WPC-50" => "Wedding 3",
		"8510-BLI-50" => "Wedding 3",
		"8510-NLI-50" => "Wedding 3",
		"8510-WLI-50" => "Wedding 3",
		"8515-03-50" => "Wedding 3",
		"8515-80W-50" => "Wedding 3",
		"8515-SW-50" => "Wedding 3",
		"8520-07-50" => "Wedding 3",
		"8520-NLI-50" => "Wedding 3",
		"8520-WLI-50" => "Wedding 3",
		"8520-WPC-50" => "Wedding 3",
		"8530-06-50" => "Wedding 3",
		"8530-GB-50" => "Wedding 3",
		"8545-03-50" => "Wedding 3",
		"8545-50-50" => "Wedding 3",
		"8555-50-50" => "Wedding 3",
		"8565-GB-50" => "Wedding 3",
		"EX10-LEBA526PC-10" => "Wedding 3",
		"EX10-LEBA527PC-10" => "Wedding 3",
		"EX10-LEBA531PC-10" => "Wedding 3",
		"EX10-LEBA534PC-10" => "Wedding 3",
		"EX10-LEBA541PC-10" => "Wedding 3",
		"EX10-LEBAQMPC-10" => "Wedding 3",
		"EX10-LEBASMPC-10" => "Wedding 3",
		"EX4080-23-50" => "Wedding 3",
		"EX8515-13-50" => "Wedding 3",
		"EX8515-18-50" => "Wedding 3",
		"EX8515-23-50" => "Wedding 3",
		"EX8515-27-50" => "Wedding 3",
		"EX8555-10-50" => "Wedding 3",
		"EX8555-15-50" => "Wedding 3",
		"EX8555-23-50" => "Wedding 3",
		"EX8555-26-50" => "Wedding 3",
		"EXLEVC-10-50" => "Wedding 3",
		"EXLEVC-23-50" => "Wedding 3",
		"F-8575-B-50" => "Wedding 3",
		"LEVC-S01-50" => "Wedding 3",
		"LEVC930-50" => "Wedding 3",
		"LUX-5080-101-50" => "Wedding 3",
		"LUX-5080-104-50" => "Wedding 3",
		"LUX-5080-15-50" => "Wedding 3",
		"LUX-5080-23-50" => "Wedding 3",
		"LUX-614PTL104-10" => "Wedding 3",
		"LUX-614PTL106-10" => "Wedding 3",
		"LUX-8515-104-50" => "Wedding 3",
		"LUX-8515-113-50" => "Wedding 3",
		"LUX-8520-103-50" => "Wedding 3",
		"LUX-8530-103-50" => "Wedding 3",
		"LUX-8530-B-50" => "Wedding 3",
		"LUX-8545-102-50" => "Wedding 3",
		"LUX-8545-104-50" => "Wedding 3",
		"LUX-8555-112-50" => "Wedding 3",
		"LUX614GF-103-10" => "Wedding 3",
		"LUX614ZF-106-10" => "Wedding 3",
		"LUXLEVC-104-50" => "Wedding 3",
		"LUXLEVC-112-50" => "Wedding 3",
		"MINBLK-50" => "Wedding 3",
		"22500-50" => "Wedding 3",
		"93839-50" => "Wedding 3",
		"96740-50" => "Wedding 3",
		"4860-WGB-50" => "Wedding 3",
		"4040-MS08-50" => "Wedding 3",
		"A7BB-MS03-10" => "Wedding 3"
    );

	if(defined $$product{"height"}) {
		$height = sprintf("%0.1f inches", $$product{"height"});
	}
	if(defined $$product{"width"})  {
		$width = sprintf("%0.1f inches", $$product{"width"});
	}
	if(defined $weight) {
		$weight = sprintf("%0.1f pounds", $weight);
	}
	if(defined $$product{"description"}) {
		$desc = $$product{"description"};
	}

	if(defined $$product{"category_id"}) {
		$product_category->execute($$product{"category_id"});
		my($categoryName, $categoryId, $parentCat) = $product_category->fetchrow_array();
		if(defined $categoryName && $categoryId && $parentCat) {
			$addWordsLabel = $addWordsLabel . $categoryName;
			$bingLabel = $bingLabel . $categoryName;
			$custom0 = $categoryName;
			#get parent category
			$product_category->execute($parentCat);
			my($parentCategoryName, $parentCategoryId, $parentCategoryId2) = $product_category->fetchrow_array();
			if(defined $parentCategoryName && $parentCategoryId) {
				$addWordsLabel = $addWordsLabel . "," . $parentCategoryName;
				$bingLabel = $bingLabel . "," . $parentCategoryName;
				$custom1 = $parentCategoryName;
			} else {
				$addWordsLabel = $addWordsLabel . ",N/A";
				$bingLabel = $bingLabel . ",N/A";
				$custom1 = "N/A";
			}
		} else {
			$addWordsLabel = $addWordsLabel . "N/A,N/A";
			$bingLabel = $bingLabel . "N/A,N/A";
			$custom0 = "N/A";
			$custom1 = "N/A";
		}
	}

	if(defined $features{"size"}->{"description"}) {
		$addWordsLabel = $addWordsLabel . "," . $features{"size"}->{"description"};
		$custom2 = $features{"size"}->{"description"};
	} else {
		$addWordsLabel = $addWordsLabel . ",N/A";
		$custom2 = "N/A";
	}
	if(defined $features{"color"}->{"description"}) {
		$addWordsLabel = $addWordsLabel . "," . $features{"color"}->{"description"};
		$custom3 = $features{"color"}->{"description"};
	} else {
		$addWordsLabel = $addWordsLabel . ",N/A";
		$custom3 = "N/A";
	}

	if($isSmallestQty eq "N" && $addWordsLabel ne "") {
		$addWordsLabel = $addWordsLabel . ",BID LOW";
		$bingLabel = $bingLabel . ",BID LOW";
		$custom4 = "BID LOW";
	} elsif($isSmallestQty eq "N" && $addWordsLabel eq "") {
		$addWordsLabel = "BID LOW";
		$bingLabel = "BID LOW";
		$custom4 = "BID LOW";
	} elsif($isSmallestQty ne "N" && $addWordsLabel ne "") {
		$addWordsLabel = $addWordsLabel . ",BID";
		$bingLabel = $bingLabel . ",BID";
		$custom4 = "BID";
	} elsif($isSmallestQty ne "N" && $addWordsLabel eq "") {
		$addWordsLabel = "BID";
		$bingLabel = "BID";
		$custom4 = "BID";
	}

	if($isExcluded ne "Y" && $addWordsLabel eq "") {
		$addWordsLabel = "remarketing";
	} elsif($isExcluded ne "Y" && $addWordsLabel ne "") {
		$addWordsLabel = $addWordsLabel . ",remarketing";
	}

	my $isYEC = "";
	#if(defined $features{"print"}->{"description"}) {
	#	$isYEC = "";
	#} else {
	#	$isYEC = "YEC13";
	#}

	my $prodName = (exists $data_mapping_env{$prodSKU} && exists $data_mapping_env{$prodSKU}->{"title"}) ? $data_mapping_env{$prodSKU}->{"title"} : sprintf("%s%s%s", $$product{"name"}, " - Pack of ", $$product{"quantity"});
	my $productType = (exists $data_mapping_env{$prodSKU} && exists $data_mapping_env{$prodSKU}->{"product_type"}) ? $data_mapping_env{$prodSKU}->{"product_type"} : ucfirst lc $$product{"product_type_id"};
	my $imageLink = (exists $data_mapping_env{$prodSKU} && exists $data_mapping_env{$prodSKU}->{"image_link"}) ? $data_mapping_env{$prodSKU}->{"image_link"} : sprintf("https://www.envelopes.com/images/products/detail/%s%s", (defined $$product{"asset_default"} && $$product{"asset_default"} eq "Y" ? $$product{"asset_name"} : $$product{"product_id"}), ".jpg?ts=10239123");
	$desc = (exists $data_mapping_env{$prodSKU} && exists $data_mapping_env{$prodSKU}->{"description"} && $data_mapping_env{$prodSKU}->{"description"} ne "") ? $data_mapping_env{$prodSKU}->{"description"} : $desc;
	#print for google file
	for (@fileHandles) {
		print $_ $prodSKU . "\t";
		print $_ $prodName . "\t";
		print $_ $desc . "\t";
		print $_ sprintf("%s%s%s", $url_domain, $url_seo_path, $url_query) . "\t";
		print $_ ($features{"color_group"}->{"description"} || "") . "\t";
		print $_ sprintf("%0.2f", $$product{"price"}) . "\t";
		print $_ $height . "\t";
		print $_ $width . "\t";
		print $_ $weight . "\t";
		print $_ $imageLink . "\t";
		print $_ "Envelopes.com" . "\t";
		print $_ ($$product{"upc"} || "") . "\t";
		print $_ $prodSKU . "\t";
		print $_ $productType . "\t";
		print $_ "Office Supplies > General Supplies > Paper Products > Envelopes" . "\t";
		print $_ $$product{"quantity"} . "\t";
		print $_ "new" . "\t";
		print $_ "USD" . "\t";
		print $_ sprintf("%s%s", $url_domain, $url_seo_path) . "\t";
		print $_ sprintf("%s%s", urlencode($url_domain), urlencode($url_seo_path)) . "\t";
		print $_ "adtype={adtype}" . "\t";
		print $_ $addWordsLabel . "\t";
		print $_ "in stock" . "\t";
		print $_ $$product{"created_stamp"} . "\t";
		print $_ $custom0 . "\t";
		print $_ $custom1 . "\t";
		print $_ $custom2 . "\t";
		print $_ $custom3 . "\t";
		print $_ ((exists $bidHash{$prodSKU}) ? $bidHash{$prodSKU} : $custom4) . "\t";
		print $_ "" . "\n";
	}

    if(defined $$product{"envelopes"} && $$product{"envelopes"} eq "Y") {
        print SPREADSHEET $prodSKU . "\t";
        print SPREADSHEET $prodName . "\t";
        print SPREADSHEET $desc . "\t";
        print SPREADSHEET sprintf("%s%s%s", $url_domain, $url_seo_path, $url_query) . "\t";
        print SPREADSHEET ($features{"color_group"}->{"description"} || "") . "\t";
        print SPREADSHEET sprintf("%0.2f", $$product{"price"}) . "\t";
        print SPREADSHEET $height . "\t";
        print SPREADSHEET $width . "\t";
        print SPREADSHEET $weight . "\t";
        print SPREADSHEET $imageLink . "\t";
        print SPREADSHEET "Envelopes.com" . "\t";
        print SPREADSHEET ($$product{"upc"} || "") . "\t";
        print SPREADSHEET $prodSKU . "\t";
        print SPREADSHEET $productType . "\t";
        print SPREADSHEET "Office Supplies > General Supplies > Paper Products > Envelopes" . "\t";
        print SPREADSHEET $$product{"quantity"} . "\t";
        print SPREADSHEET "new" . "\t";
        print SPREADSHEET "USD" . "\t";
        print SPREADSHEET sprintf("%s%s", $url_domain, $url_seo_path) . "\t";
        print SPREADSHEET sprintf("%s%s", urlencode($url_domain), urlencode($url_seo_path)) . "\t";
        print SPREADSHEET "adtype={adtype}" . "\t";
        print SPREADSHEET $addWordsLabel . "\t";
        print SPREADSHEET "in stock" . "\t";
        print SPREADSHEET $$product{"created_stamp"} . "\t";
        print SPREADSHEET $custom0 . "\t";
        print SPREADSHEET $custom1 . "\t";
        print SPREADSHEET $custom2 . "\t";
        print SPREADSHEET $custom3 . "\t";
        print SPREADSHEET ((exists $bidHash{$prodSKU}) ? $bidHash{$prodSKU} : $custom4) . "\t";
        print SPREADSHEET "ALL" . "\n";

        #print for msn bing file
        print SPREADSHEETBING $prodSKU . "\t";
        print SPREADSHEETBING $prodName . "\t";
        print SPREADSHEETBING $desc . "\t";
        print SPREADSHEETBING sprintf("%s%s%s", $url_domain, $url_seo_path, $url_query_bing) . "\t";
        print SPREADSHEETBING ($features{"color_group"}->{"description"} || "") . "\t";
        print SPREADSHEETBING sprintf("%0.2f", $$product{"price"}) . "\t";
        print SPREADSHEETBING $height . "\t";
        print SPREADSHEETBING $width . "\t";
        print SPREADSHEETBING $weight . "\t";
        print SPREADSHEETBING sprintf("https://actionenvelope.scene7.com/is/image/ActionEnvelope/%s%s", (defined $$product{"asset_default"} && $$product{"asset_default"} eq "Y" ? $$product{"asset_name"} : $$product{"product_id"}), "?wid=700&hei=700&fmt=jpeg") . "\t";
        print SPREADSHEETBING "Envelopes.com" . "\t";
        print SPREADSHEETBING ($$product{"upc"} || "") . "\t";
        print SPREADSHEETBING $prodSKU . "\t";
        print SPREADSHEETBING $productType . "\t";
        print SPREADSHEETBING "Office Supplies > General Supplies > Paper Products > Envelopes" . "\t";
        print SPREADSHEETBING $$product{"quantity"} . "\t";
        print SPREADSHEETBING "new" . "\t";
        print SPREADSHEETBING "USD" . "\t";
        print SPREADSHEETBING sprintf("%s%s", urlencode($url_domain), urlencode($url_seo_path)) . "\t";
        print SPREADSHEETBING "adtype={adtype}" . "\t";
        print SPREADSHEETBING $addWordsLabel . "\t";
        print SPREADSHEETBING "in stock" . "\t";
        print SPREADSHEETBING $$product{"created_stamp"} . "\t";
        print SPREADSHEETBING $custom0 . "\t";
        print SPREADSHEETBING $custom1 . "\t";
        print SPREADSHEETBING $custom2 . "\t";
        print SPREADSHEETBING $custom3 . "\t";
        print SPREADSHEETBING $custom4 . "\t";
        print SPREADSHEETBING $bingLabel . "\n";

        #print for bronto file
        print SPREADSHEETBRONTO $$product{"product_id"} . "\t";
        print SPREADSHEETBRONTO $$product{"name"} . "\t";
        print SPREADSHEETBRONTO $desc . "\t";
        print SPREADSHEETBRONTO sprintf("%s%s", $url_domain, $url_seo_path) . "\t";
        print SPREADSHEETBRONTO ($features{"color"}->{"description"} || "") . "\t";
        print SPREADSHEETBRONTO sprintf("%0.2f", $$product{"price"}) . "\t";
        print SPREADSHEETBRONTO $height . "\t";
        print SPREADSHEETBRONTO $width . "\t";
        print SPREADSHEETBRONTO $weight . "\t";
        print SPREADSHEETBRONTO $imageLink . "\t";
        print SPREADSHEETBRONTO "Envelopes.com" . "\t";
        print SPREADSHEETBRONTO $$product{"product_id"} . "\t";
        print SPREADSHEETBRONTO $productType . "\t";
        print SPREADSHEETBRONTO "Office Supplies > General Supplies > Paper Products > Envelopes" . "\t";
        print SPREADSHEETBRONTO $$product{"quantity"} . "\t";
        print SPREADSHEETBRONTO "new" . "\t";
        print SPREADSHEETBRONTO "USD" . "\t";
        print SPREADSHEETBRONTO "in stock" . "\t";
        print SPREADSHEETBRONTO $$product{"created_stamp"} . "\t";
        print SPREADSHEETBRONTO $custom0 . "\t";
        print SPREADSHEETBRONTO $custom1 . "\t";
        print SPREADSHEETBRONTO $custom2 . "\n";
    }

	##folders list
	if(defined $$product{"folders"} && $$product{"folders"} eq "Y") {
		# generate the url parameters
		my $fold_url_domain = "https://www.folders.com";
		my $fold_url_path = sprintf("/folders/control/product/~category_id=%s/~product_id=%s", $$product{"category_id"}, $$product{"product_id"});
		my $fold_url_seo_path = $url_mapping_fold{$fold_url_path} || $fold_url_path;
		my $fold_url_query = sprintf("?utm_source=gbase&utm_medium=cpc&utm_campaign=gbase&CS_003=2521211&CS_010=%s", $$product{"product_id"});

		$prodName = (exists $data_mapping_fold{$prodSKU} && exists $data_mapping_fold{$prodSKU}->{"title"}) ? $data_mapping_fold{$prodSKU}->{"title"} : sprintf("%s%s%s", $$product{"name"}, " - Pack of ", $$product{"quantity"});
		$productType = (exists $data_mapping_fold{$prodSKU} && exists $data_mapping_fold{$prodSKU}->{"product_type"}) ? $data_mapping_fold{$prodSKU}->{"product_type"} : ucfirst lc $$product{"product_type_id"};
		$desc = (exists $data_mapping_fold{$prodSKU} && exists $data_mapping_fold{$prodSKU}->{"description"} && $data_mapping_fold{$prodSKU}->{"description"} ne "") ? $data_mapping_fold{$prodSKU}->{"description"} : $desc;

        print SPREADSHEET_FOLDERS $prodSKU . "\t";
        print SPREADSHEET_FOLDERS $prodName . "\t";
        print SPREADSHEET_FOLDERS $desc . "\t";
        print SPREADSHEET_FOLDERS sprintf("%s%s%s", $fold_url_domain, $fold_url_seo_path, $fold_url_query) . "\t";
        print SPREADSHEET_FOLDERS ($features{"color_group"}->{"description"} || "") . "\t";
        print SPREADSHEET_FOLDERS sprintf("%0.2f", $$product{"price"}) . "\t";
        print SPREADSHEET_FOLDERS $height . "\t";
        print SPREADSHEET_FOLDERS $width . "\t";
        print SPREADSHEET_FOLDERS $weight . "\t";
        print SPREADSHEET_FOLDERS sprintf("https://www.folders.com/images/products/detail/%s%s", (defined $$product{"asset_default"} && $$product{"asset_default"} eq "Y" ? $$product{"asset_name"} : $$product{"product_id"}), ".jpg?ts=10239123") . "\t";
        print SPREADSHEET_FOLDERS "Folders.com" . "\t";
        print SPREADSHEET_FOLDERS ($$product{"upc"} || "") . "\t";
        print SPREADSHEET_FOLDERS $prodSKU . "\t";
        print SPREADSHEET_FOLDERS $productType . "\t";
        print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
        print SPREADSHEET_FOLDERS $$product{"quantity"} . "\t";
        print SPREADSHEET_FOLDERS "new" . "\t";
        print SPREADSHEET_FOLDERS "USD" . "\t";
        print SPREADSHEET_FOLDERS sprintf("%s%s", $fold_url_domain, $fold_url_seo_path) . "\t";
        print SPREADSHEET_FOLDERS sprintf("%s%s", urlencode($fold_url_domain), urlencode($fold_url_seo_path)) . "\t";
        print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
        print SPREADSHEET_FOLDERS $addWordsLabel . "\t";
        print SPREADSHEET_FOLDERS "in stock" . "\t";
        print SPREADSHEET_FOLDERS $$product{"created_stamp"} . "\t";
        print SPREADSHEET_FOLDERS "" . "\t";
        print SPREADSHEET_FOLDERS "" . "\t";
        print SPREADSHEET_FOLDERS "" . "\t";
        print SPREADSHEET_FOLDERS "" . "\t";
        print SPREADSHEET_FOLDERS "" . "\t";
        print SPREADSHEET_FOLDERS "" . "\n";
	}

	$uniqueSKU = $$product{"product_id"};
}

#####HARDCODED PRINT VALUES
print SPREADSHEET_FOLDERS "912-501-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "Foil Stamped 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-501-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "Bright White" . "\t";
print SPREADSHEET_FOLDERS "365.00" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-501-C-SF250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-501-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-501-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-501-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Bright White,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-501-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "Spot Color 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-501-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "Bright White" . "\t";
print SPREADSHEET_FOLDERS "530.00" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-501-C.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-501-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-501-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-501-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DSPOT" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Bright White,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-501-C-S4250" . "\t";
print SPREADSHEET_FOLDERS "Four Color 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-501-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR" . "\t";
print SPREADSHEET_FOLDERS "Bright White" . "\t";
print SPREADSHEET_FOLDERS "617.50" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-501-C-S4250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-501-C-S4250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-501-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-501-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOUR_COLOR" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Bright White,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-501-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "Embossed 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-501-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "Bright White" . "\t";
print SPREADSHEET_FOLDERS "452.50" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-501-C-SE250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-501-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-501-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-501-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DEMBOSS" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Bright White,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-505-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "Foil Stamped 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-505-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "Bright White 14PT Semi-Gloss" . "\t";
print SPREADSHEET_FOLDERS "405.00" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-505-C-SF250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-505-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-505-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-505-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Bright White 14PT Semi-Gloss,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-505-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "Spot Color 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-505-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "Bright White 14PT Semi-Gloss" . "\t";
print SPREADSHEET_FOLDERS "572.50" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-505-C.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-505-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-505-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-505-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Bright White 14PT Semi-Gloss,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-505-C-S4250" . "\t";
print SPREADSHEET_FOLDERS "Four Color 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-505-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR" . "\t";
print SPREADSHEET_FOLDERS "Bright White 14PT Semi-Gloss" . "\t";
print SPREADSHEET_FOLDERS "657.50" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-505-C-S4250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-505-C-S4250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-505-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-505-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOUR_COLOR" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Bright White 14PT Semi-Gloss,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-505-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "Embossed 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-505-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "Bright White 14PT Semi-Gloss" . "\t";
print SPREADSHEET_FOLDERS "495" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-505-C-SE250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-505-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-505-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-505-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DEMBOSS" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Bright White 14PT Semi-Gloss,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-510-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "Foil Stamped 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-510-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "Bright White 120lb. Gloss" . "\t";
print SPREADSHEET_FOLDERS "427.5" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-510-C-SF250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-510-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-510-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-510-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Bright White 120lb. Gloss,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-510-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "Spot Color 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-510-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "Bright White 120lb. Gloss" . "\t";
print SPREADSHEET_FOLDERS "595.00" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-510-C.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-510-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-510-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-510-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Bright White 120lb. Gloss,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-510-C-S4250" . "\t";
print SPREADSHEET_FOLDERS "Four Color 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-510-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR" . "\t";
print SPREADSHEET_FOLDERS "Bright White 120lb. Gloss" . "\t";
print SPREADSHEET_FOLDERS "680.00" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-510-C-S4250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-510-C-S4250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-510-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-510-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOUR_COLOR" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Bright White 120lb. Gloss,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-510-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "Embossed 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-510-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "Bright White 120lb. Gloss" . "\t";
print SPREADSHEET_FOLDERS "517.50" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-510-C-SE250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-510-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-510-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-510-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DEMBOSS" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Bright White 120lb. Gloss,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-511-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "Foil Stamped 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-511-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "Bright White 130lb. Dull" . "\t";
print SPREADSHEET_FOLDERS "427.5" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-511-C-SF250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-511-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-511-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-511-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Bright White 130lb. Dull,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-511-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "Spot Color 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-511-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "Bright White 130lb. Dull" . "\t";
print SPREADSHEET_FOLDERS "595.00" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-511-C.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-511-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-511-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-511-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Bright White 130lb. Dull,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-511-C-S4250" . "\t";
print SPREADSHEET_FOLDERS "Four Color 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-511-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR" . "\t";
print SPREADSHEET_FOLDERS "Bright White 130lb. Dull" . "\t";
print SPREADSHEET_FOLDERS "680.00" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-511-C-S4250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-511-C-S4250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-511-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-511-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOUR_COLOR" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Bright White 130lb. Dull,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-511-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "Embossed 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-511-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "Bright White 130lb. Dull" . "\t";
print SPREADSHEET_FOLDERS "517.50" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-511-C-SE250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-511-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-511-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-511-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DEMBOSS" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Bright White 130lb. Dull,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-508-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "Foil Stamped 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-508-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "Bright White 100lb." . "\t";
print SPREADSHEET_FOLDERS "405" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-508-C-SF250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-508-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-508-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-508-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Bright White 100lb.,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-508-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "Spot Color 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-508-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "Bright White 100lb." . "\t";
print SPREADSHEET_FOLDERS "572.5" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-508-C.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-508-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-508-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-508-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Bright White 100lb.,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-508-C-S4250" . "\t";
print SPREADSHEET_FOLDERS "Four Color 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-508-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR" . "\t";
print SPREADSHEET_FOLDERS "Bright White 100lb." . "\t";
print SPREADSHEET_FOLDERS "657.5" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-508-C-S4250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-508-C-S4250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-508-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-508-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOUR_COLOR" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Bright White 100lb.,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-508-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "Embossed 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-508-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "Bright White 100lb." . "\t";
print SPREADSHEET_FOLDERS "495" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-508-C-SE250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-508-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-508-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-508-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DEMBOSS" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Bright White 100lb.,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-546-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "Foil Stamped 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-546-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "Nautical Blue 100lb. Linen" . "\t";
print SPREADSHEET_FOLDERS "510" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-546-C-SF250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-546-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-546-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-546-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Nautical Blue 100lb. Linen,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-546-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "Spot Color 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-546-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "Nautical Blue 100lb. Linen" . "\t";
print SPREADSHEET_FOLDERS "675" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-546-C.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-546-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-546-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-546-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Nautical Blue 100lb. Linen,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-546-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "Embossed 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-546-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "Nautical Blue 100lb. Linen" . "\t";
print SPREADSHEET_FOLDERS "597.5" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-546-C-SE250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-546-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-546-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-546-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DEMBOSS" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Nautical Blue 100lb. Linen,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-560-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "Foil Stamped 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-560-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "Rosewood 100lb. Linen" . "\t";
print SPREADSHEET_FOLDERS "510" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-560-C-SF250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-560-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-560-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-560-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Rosewood 100lb. Linen,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-560-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "Spot Color 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-560-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "Rosewood 100lb. Linen" . "\t";
print SPREADSHEET_FOLDERS "675" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-560-C.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-560-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-560-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-560-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Rosewood 100lb. Linen,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-560-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "Embossed 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-560-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "Rosewood 100lb. Linen" . "\t";
print SPREADSHEET_FOLDERS "597.5" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-560-C-SE250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-560-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-560-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-560-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DEMBOSS" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Rosewood 100lb. Linen,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-540-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "Foil Stamped 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-540-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "Midnight Black 100lb. Linen" . "\t";
print SPREADSHEET_FOLDERS "510" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-540-C-SF250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-540-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-540-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-540-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Midnight Black 100lb. Linen,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-540-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "Spot Color 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-540-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "Midnight Black 100lb. Linen" . "\t";
print SPREADSHEET_FOLDERS "675" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-540-C.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-540-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-540-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-540-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Midnight Black 100lb. Linen,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-540-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "Embossed 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-540-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "Midnight Black 100lb. Linen" . "\t";
print SPREADSHEET_FOLDERS "597.5" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-540-C-SE250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-540-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-540-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-540-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DEMBOSS" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Midnight Black 100lb. Linen,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-533-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "Foil Stamped 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-533-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "Chelsea Gray 100lb. Linen" . "\t";
print SPREADSHEET_FOLDERS "462.5" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-533-C-SF250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-533-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-533-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-533-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Chelsea Gray 100lb. Linen,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-533-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "Spot Color 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-533-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "Chelsea Gray 100lb. Linen" . "\t";
print SPREADSHEET_FOLDERS "627.5" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-533-C.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-533-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-533-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-533-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Chelsea Gray 100lb. Linen,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-533-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "Embossed 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-533-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "Chelsea Gray 100lb. Linen" . "\t";
print SPREADSHEET_FOLDERS "550" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-533-C-SE250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-533-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-533-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-533-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DEMBOSS" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Chelsea Gray 100lb. Linen,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-554-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "Foil Stamped 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-554-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "Dark Pine Green 100lb. Linen" . "\t";
print SPREADSHEET_FOLDERS "462.5" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-554-C-SF250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-554-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-554-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-554-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Dark Pine Green 100lb. Linen,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-554-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "Spot Color 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-554-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "Dark Pine Green 100lb. Linen" . "\t";
print SPREADSHEET_FOLDERS "627.5" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-554-C.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-554-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-554-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-554-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Dark Pine Green 100lb. Linen,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "912-554-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "Embossed 9 x 12 Presentation Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "9 x 12 Presentation Folders offer a professional level of quality and durability. Designed with a portrait orientation to hold standard letter size 8 ½\" x 11\" print media, brochures, stepped inserts and sales literature." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-554-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "Dark Pine Green 100lb. Linen" . "\t";
print SPREADSHEET_FOLDERS "550" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "12" . "\t";
print SPREADSHEET_FOLDERS "25" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/912-554-C-SE250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "912-554-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=STANDARD_FOLDER/~product_id=912-554-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DSTANDARD_FOLDER%2F~product_id%3D912-554-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DEMBOSS" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Presentation Folders,9 x 12,Dark Pine Green 100lb. Linen,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-501-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "Foil Stamped Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-501-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "Bright White" . "\t";
print SPREADSHEET_FOLDERS "530" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-501-C-SF250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-501-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-501-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-501-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Bright White,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-501-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "Spot Color Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-501-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "Bright White" . "\t";
print SPREADSHEET_FOLDERS "725" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-501-C.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-501-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-501-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-501-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Bright White,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-501-C-S4250" . "\t";
print SPREADSHEET_FOLDERS "Four Color Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-501-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR" . "\t";
print SPREADSHEET_FOLDERS "Bright White" . "\t";
print SPREADSHEET_FOLDERS "845" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-501-C-S4250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-501-C-S4250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-501-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-501-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOUR_COLOR" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Bright White,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-501-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "Embossed Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-501-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "Bright White" . "\t";
print SPREADSHEET_FOLDERS "617.5" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-501-C-SE250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-501-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-501-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-501-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DEMBOSS" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Bright White,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-505-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "Foil Stamped Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-505-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "Bright White 14PT Semi-Gloss" . "\t";
print SPREADSHEET_FOLDERS "570" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-505-C-SF250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-505-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-505-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-505-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Bright White 14PT Semi-Gloss,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-505-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "Spot Color Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-505-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "Bright White 14PT Semi-Gloss" . "\t";
print SPREADSHEET_FOLDERS "767.5" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-505-C.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-505-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-505-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-505-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Bright White 14PT Semi-Gloss,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-505-C-S4250" . "\t";
print SPREADSHEET_FOLDERS "Four Color Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-505-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR" . "\t";
print SPREADSHEET_FOLDERS "Bright White 14PT Semi-Gloss" . "\t";
print SPREADSHEET_FOLDERS "887.5" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-505-C-S4250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-505-C-S4250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-505-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-505-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOUR_COLOR" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Bright White 14PT Semi-Gloss,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-505-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "Embossed Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-505-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "Bright White 14PT Semi-Gloss" . "\t";
print SPREADSHEET_FOLDERS "660" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-505-C-SE250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-505-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-505-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-505-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DEMBOSS" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Bright White 14PT Semi-Gloss,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-510-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "Foil Stamped Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-510-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "Bright White 120lb. Gloss" . "\t";
print SPREADSHEET_FOLDERS "592.5" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-510-C-SF250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-510-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-510-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-510-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Bright White 120lb. Gloss,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-510-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "Spot Color Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-510-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "Bright White 120lb. Gloss" . "\t";
print SPREADSHEET_FOLDERS "787.5" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-510-C.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-510-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-510-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-510-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Bright White 120lb. Gloss,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-510-C-S4250" . "\t";
print SPREADSHEET_FOLDERS "Four Color Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-510-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR" . "\t";
print SPREADSHEET_FOLDERS "Bright White 120lb. Gloss" . "\t";
print SPREADSHEET_FOLDERS "907.5" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-510-C-S4250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-510-C-S4250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-510-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-510-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOUR_COLOR" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Bright White 120lb. Gloss,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-510-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "Embossed Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-510-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "Bright White 120lb. Gloss" . "\t";
print SPREADSHEET_FOLDERS "680" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-510-C-SE250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-510-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-510-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-510-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DEMBOSS" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Bright White 120lb. Gloss,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-511-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "Foil Stamped Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-511-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "Bright White 130lb. Dull" . "\t";
print SPREADSHEET_FOLDERS "592.5" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-511-C-SF250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-511-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-511-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-511-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Bright White 130lb. Dull,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-511-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "Spot Color Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-511-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "Bright White 130lb. Dull" . "\t";
print SPREADSHEET_FOLDERS "787.5" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-511-C.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-511-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-511-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-511-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Bright White 130lb. Dull,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-511-C-S4250" . "\t";
print SPREADSHEET_FOLDERS "Four Color Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-511-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR" . "\t";
print SPREADSHEET_FOLDERS "Bright White 130lb. Dull" . "\t";
print SPREADSHEET_FOLDERS "907.5" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-511-C-S4250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-511-C-S4250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-511-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-511-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOUR_COLOR" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Bright White 130lb. Dull,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-511-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "Embossed Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-511-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "Bright White 130lb. Dull" . "\t";
print SPREADSHEET_FOLDERS "680" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-511-C-SE250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-511-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-511-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-511-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DEMBOSS" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Bright White 130lb. Dull,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-508-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "Foil Stamped Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-508-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "Bright White 100lb." . "\t";
print SPREADSHEET_FOLDERS "570" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-508-C-SF250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-508-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-508-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-508-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Bright White 100lb.,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-508-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "Spot Color Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-508-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "Bright White 100lb." . "\t";
print SPREADSHEET_FOLDERS "767.5" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-508-C.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-508-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-508-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-508-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Bright White 100lb.,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-508-C-S4250" . "\t";
print SPREADSHEET_FOLDERS "Four Color Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-508-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR" . "\t";
print SPREADSHEET_FOLDERS "Bright White 100lb." . "\t";
print SPREADSHEET_FOLDERS "887.5" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-508-C-S4250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-508-C-S4250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-508-C/~pocket_type=STANDARD/~print_method=FOUR_COLOR" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-508-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOUR_COLOR" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Bright White 100lb.,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-508-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "Embossed Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-508-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "Bright White 100lb." . "\t";
print SPREADSHEET_FOLDERS "660" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-508-C-SE250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-508-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-508-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-508-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DEMBOSS" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Bright White 100lb.,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-546-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "Foil Stamped Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-546-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "Nautical Blue 100lb. Linen" . "\t";
print SPREADSHEET_FOLDERS "672.5" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-546-C-SF250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-546-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-546-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-546-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Nautical Blue 100lb. Linen,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-546-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "Spot Color Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-546-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "Nautical Blue 100lb. Linen" . "\t";
print SPREADSHEET_FOLDERS "870" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-546-C.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-546-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-546-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-546-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Nautical Blue 100lb. Linen,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-546-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "Embossed Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-546-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "Nautical Blue 100lb. Linen" . "\t";
print SPREADSHEET_FOLDERS "762.5" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-546-C-SE250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-546-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-546-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-546-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DEMBOSS" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Nautical Blue 100lb. Linen,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-560-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "Foil Stamped Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-560-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "Rosewood 100lb. Linen" . "\t";
print SPREADSHEET_FOLDERS "672.5" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-560-C-SF250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-560-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-560-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-560-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Rosewood 100lb. Linen,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-560-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "Spot Color Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-560-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "Rosewood 100lb. Linen" . "\t";
print SPREADSHEET_FOLDERS "870" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-560-C.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-560-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-560-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-560-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Rosewood 100lb. Linen,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-560-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "Embossed Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-560-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "Rosewood 100lb. Linen" . "\t";
print SPREADSHEET_FOLDERS "762.5" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-560-C-SE250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-560-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-560-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-560-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DEMBOSS" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Rosewood 100lb. Linen,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-540-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "Foil Stamped Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-540-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "Midnight Black 100lb. Linen" . "\t";
print SPREADSHEET_FOLDERS "672.5" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-540-C-SF250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-540-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-540-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-540-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Midnight Black 100lb. Linen,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-540-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "Spot Color Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-540-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "Midnight Black 100lb. Linen" . "\t";
print SPREADSHEET_FOLDERS "870" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-540-C.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-540-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-540-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-540-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Midnight Black 100lb. Linen,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-540-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "Embossed Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-540-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "Midnight Black 100lb. Linen" . "\t";
print SPREADSHEET_FOLDERS "762.5" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-540-C-SE250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-540-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-540-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-540-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DEMBOSS" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Midnight Black 100lb. Linen,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-533-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "Foil Stamped Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-533-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "Chelsea Gray 100lb. Linen" . "\t";
print SPREADSHEET_FOLDERS "625" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-533-C-SF250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-533-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-533-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-533-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Chelsea Gray 100lb. Linen,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-533-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "Spot Color Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-533-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "Chelsea Gray 100lb. Linen" . "\t";
print SPREADSHEET_FOLDERS "822.5" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-533-C.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-533-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-533-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-533-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Chelsea Gray 100lb. Linen,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-533-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "Embossed Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-533-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "Chelsea Gray 100lb. Linen" . "\t";
print SPREADSHEET_FOLDERS "715" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-533-C-SE250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-533-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-533-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-533-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DEMBOSS" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Chelsea Gray 100lb. Linen,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-554-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "Foil Stamped Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-554-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "Dark Pine Green 100lb. Linen" . "\t";
print SPREADSHEET_FOLDERS "672.5" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-554-C-SF250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-554-C-SF250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-554-C/~pocket_type=STANDARD/~print_method=FOIL" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-554-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Dark Pine Green 100lb. Linen,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-554-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "Spot Color Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-554-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "Dark Pine Green 100lb. Linen" . "\t";
print SPREADSHEET_FOLDERS "870" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-554-C.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-554-C-SS250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-554-C/~pocket_type=STANDARD/~print_method=SPOT" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-554-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DFOIL" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Dark Pine Green 100lb. Linen,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

print SPREADSHEET_FOLDERS "LF-118-554-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "Embossed Legal Size Folders - Pack of 250" . "\t";
print SPREADSHEET_FOLDERS "Legal Size Folders with a standard two pocket design measure 9\" x 14 1/2\" and are perfect for holding legal size 8 1/2\" x 14\" paper and documents used by attorneys, mortgage lenders, title companies and other agencies that work with legal documents. The two interior pockets measure 4\" in height and are a die-cut v-split style to prevent buckling when opening and closing the covers. The square corners of this legal size presentation folder were expertly die-cut for a clean, professional look." . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-554-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "Dark Pine Green 100lb. Linen" . "\t";
print SPREADSHEET_FOLDERS "762.5" . "\t";
print SPREADSHEET_FOLDERS "9" . "\t";
print SPREADSHEET_FOLDERS "14.5" . "\t";
print SPREADSHEET_FOLDERS "40" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/images/products/detail/LF-118-554-C-SE250.jpg?ts=10239123" . "\t";
print SPREADSHEET_FOLDERS "Folders.com" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "LF-118-554-C-SE250" . "\t";
print SPREADSHEET_FOLDERS "FOLDER" . "\t";
print SPREADSHEET_FOLDERS "Office Supplies > Filing & Organization > File Folders" . "\t";
print SPREADSHEET_FOLDERS "250" . "\t";
print SPREADSHEET_FOLDERS "new" . "\t";
print SPREADSHEET_FOLDERS "USD" . "\t";
print SPREADSHEET_FOLDERS "https://www.folders.com/folders/control/product/~category_id=LEGAL_FOLDERS/~product_id=LF-118-554-C/~pocket_type=STANDARD/~print_method=EMBOSS" . "\t";
print SPREADSHEET_FOLDERS "https%3A%2F%2Fwww.folders.com%2Ffolders%2Fcontrol%2Fproduct%2F~category_id%3DLEGAL_FOLDERS%2F~product_id%3DLF-118-554-C%2F~pocket_type%3DSTANDARD%2F~print_method%3DEMBOSS" . "\t";
print SPREADSHEET_FOLDERS "adtype={adtype}" . "\t";
print SPREADSHEET_FOLDERS "Legal Folders,9 x 14 1/2,Dark Pine Green 100lb. Linen,BID,remarketing" . "\t";
print SPREADSHEET_FOLDERS "in stock" . "\t";
print SPREADSHEET_FOLDERS "2018-01-01 00:00:00.0" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\t";
print SPREADSHEET_FOLDERS "" . "\n";

#do scene7 data
if(1) {
	$getScene7Data->execute();
	my %designHash = ();
	while (my $s7Data = $getScene7Data->fetchrow_hashref()) {
		if(!exists($designHash{$$s7Data{"scene7_template_id"}})) {
			#print "Working on " . $$s7Data{"scene7_template_id"} . "\n";
			my %s7cats = get_scene7_cat($$s7Data{"scene7_template_id"});

			my $url_query2 = sprintf("&utm_source=gbase&utm_medium=organic&utm_campaign=gbase&CS_003=2521211&CS_010=%s", $$s7Data{"scene7_template_id"});

			my $height = "";
			my $width = "";
			if(defined $$s7Data{"height"}) {
				$height = sprintf("%0.1f inches", $$s7Data{"height"});
			}
			if(defined $$s7Data{"width"})  {
				$width = sprintf("%0.1f inches", $$s7Data{"width"});
			}

			$getScene7Product->execute($$s7Data{"scene7_template_id"});
			my($designProductId, $designProductCategory) = $getScene7Product->fetchrow_array();

			my %features = ();
			if(defined $designProductId) {
				# retrieve product features
				$features_handle->execute($designProductId);
				while(my $feature = $features_handle->fetchrow_hashref()) {
					$features{ lc $$feature{"product_feature_type_id"} } = $feature;
				}
			}

			my $addWordsLabel = "";
			my $bingLabel = "";
			my $custom0 = "";
			my $custom1 = "";
			my $custom2 = "";
			my $custom3 = "";
			my $custom4 = "";
			if(defined $designProductCategory) {
				$product_category->execute($designProductCategory);
				my($categoryName, $categoryId, $parentCat) = $product_category->fetchrow_array();
				if(defined $categoryName && $categoryId && $parentCat) {
					$addWordsLabel = $addWordsLabel . $categoryName;
					$bingLabel = $bingLabel . $categoryName;
					$custom0 = $categoryName . " - Custom";
					#get parent category
					$product_category->execute($parentCat);
					my($parentCategoryName, $parentCategoryId, $parentCategoryId2) = $product_category->fetchrow_array();
					if(defined $parentCategoryName && $parentCategoryId) {
						$addWordsLabel = $addWordsLabel . "," . $parentCategoryName;
						$bingLabel = $bingLabel . "," . $parentCategoryName;
						$custom1 = $parentCategoryName;
					} else {
						$addWordsLabel = $addWordsLabel . ",N/A";
						$bingLabel = $bingLabel . ",N/A";
						$custom1 = "N/A";
					}
				} else {
					$addWordsLabel = $addWordsLabel . "N/A,N/A";
					$bingLabel = $bingLabel . "N/A,N/A";
					$custom0 = "N/A";
					$custom1 = "N/A";
				}
			}

			if(defined $features{"size"}->{"description"}) {
				$addWordsLabel = $addWordsLabel . "," . $features{"size"}->{"description"};
				$custom2 = $features{"size"}->{"description"};
			} else {
				$addWordsLabel = $addWordsLabel . ",N/A";
				$custom2 = "N/A";
			}
			if(defined $features{"color"}->{"description"}) {
				$addWordsLabel = $addWordsLabel . "," . $features{"color"}->{"description"};
				$custom3 = $features{"color"}->{"description"};
			} else {
				$addWordsLabel = $addWordsLabel . ",N/A";
				$custom3 = "N/A";
			}

			$bingLabel = $bingLabel . "N/A,N/A";
			$custom4 = "N/A";

			if(defined $designProductId && defined $$s7Data{"colors"}) {
				$getSmallestPrintQty->execute($designProductId, $$s7Data{"colors"});
				my($quantity) = $getSmallestPrintQty->fetchrow_array();
				if(defined $quantity) {
					if($quantity <= 250 && $addWordsLabel ne "") {
						#$addWordsLabel = $addWordsLabel . ",BID";
						$bingLabel = $bingLabel . ",BID";
						$custom4 = "BID";
					} elsif($quantity <= 250 && $addWordsLabel eq "") {
						#$addWordsLabel = "BID";
						$bingLabel = "BID";
						$custom4 = "BID";
					} elsif($quantity >= 500 && $addWordsLabel ne "") {
						#$addWordsLabel = $addWordsLabel . ",BID LOW";
						$bingLabel = $bingLabel . ",BID LOW";
						$custom4 = "BID LOW";
					} elsif($quantity >= 500 && $addWordsLabel eq "") {
						#$addWordsLabel = "BID LOW";
						$bingLabel = "BID LOW";
						$custom4 = "BID LOW";
					}
				}
			}

			my $prodName = (exists $data_mapping_env{$$s7Data{"scene7_template_id"}} && exists $data_mapping_env{$$s7Data{"scene7_template_id"}}->{"title"}) ? $data_mapping_env{$$s7Data{"scene7_template_id"}}->{"title"} : sprintf("%s%s%s", ($$s7Data{"template_description"} || $$s7Data{"scene7_template_id"}), " - Custom Printed " . ($$s7Data{"product_desc"} || ""), " - Pack of " . $$s7Data{"base_quantity"});
			#if(index("1001b,1002b,1003b,1005b,1006b,1007b,1009b,1010b,1011b,1013b,1014b,1015b,1016b,1017b,1018b,1019b,1020b,1021b,1022b,4012c,4013c,4014c,4015c,4016c,4017c,7004b,7008b,7009b,7014b,7015b,7016b,7018b,7022b,7023b,7029e,7029j,7031e,7032e,7032j,7033d,7033i,7038a_std_envelope,7038a_ty_envelope,7041a_std_envelope,7041a_ty_envelope,7063a_std_envelope",$$s7Data{"scene7_template_id"}) != -1) {
			#	$custom4 = "BID";
			#}
			my $desc = (exists $data_mapping_env{$$s7Data{"scene7_template_id"}} && exists $data_mapping_env{$$s7Data{"scene7_template_id"}}->{"description"} && $data_mapping_env{$$s7Data{"scene7_template_id"}}->{"description"} ne "") ? $data_mapping_env{$$s7Data{"scene7_template_id"}}->{"description"} : $$s7Data{"quick_desc"};
			my $imageLink = (exists $data_mapping_env{$$s7Data{"scene7_template_id"}} && exists $data_mapping_env{$$s7Data{"scene7_template_id"}}->{"image_link"}) ? $data_mapping_env{$$s7Data{"scene7_template_id"}}->{"image_link"} : "https://texel.envelopes.com/getBasicImage?id=" . $$s7Data{"scene7_template_id"} . "&fmt=jpg&wid=600";

			#print for google file
			print SPREADSHEET $$s7Data{"scene7_template_id"} . "\t";
			print SPREADSHEET $prodName . "\t";
			print SPREADSHEET ($desc || "") . "\t";
			print SPREADSHEET "https://www.envelopes.com/envelopes/control/designProduct?designId=" . $$s7Data{"scene7_template_id"} . $url_query2 . "\t";
			print SPREADSHEET "" . "\t";
			print SPREADSHEET sprintf("%0.2f", $$s7Data{"base_price"}) . "\t";
			print SPREADSHEET $height . "\t";
			print SPREADSHEET $width . "\t";
			print SPREADSHEET "2.0 pounds" . "\t";
			print SPREADSHEET $imageLink . "\t";
			print SPREADSHEET "Envelopes.com" . "\t";
			print SPREADSHEET $$s7Data{"upc"} . "\t";
			print SPREADSHEET ucfirst lc ($$s7Data{"scene7_template_id"} || "") . "\t";
			print SPREADSHEET ucfirst lc ($$s7Data{"product_type_id"} || "") . "\t";
			print SPREADSHEET "Office Supplies > General Supplies > Paper Products > Envelopes" . "\t";
			print SPREADSHEET $$s7Data{"base_quantity"} . "\t";
			print SPREADSHEET "new" . "\t";
			print SPREADSHEET "USD" . "\t";
			print SPREADSHEET "https://www.envelopes.com/envelopes/control/designProduct?designId=" . $$s7Data{"scene7_template_id"} . "\t";
			print SPREADSHEET urlencode("https://www.envelopes.com/envelopes/control/designProduct?designId=" . $$s7Data{"scene7_template_id"}) . "\t";
			print SPREADSHEET "adtype={adtype}" . "\t";
			print SPREADSHEET $addWordsLabel . "\t";
			print SPREADSHEET "in stock" . "\t";
			print SPREADSHEET "" . "\t";
			print SPREADSHEET $custom0 . "\t";
			print SPREADSHEET $custom1 . "\t";
			print SPREADSHEET $custom2 . "\t";
			print SPREADSHEET $custom3 . "\t";
			print SPREADSHEET $custom4 . "\n";

			#print for msn bing file
			print SPREADSHEETBING $$s7Data{"scene7_template_id"} . "\t";
			print SPREADSHEETBING $prodName . "\t";
			print SPREADSHEETBING ($desc || "") . "\t";
			print SPREADSHEETBING "https://www.envelopes.com/envelopes/control/designProduct?designId=" . $$s7Data{"scene7_template_id"} . $url_query2 . "\t";
			print SPREADSHEETBING "" . "\t";
			print SPREADSHEETBING sprintf("%0.2f", $$s7Data{"base_price"}) . "\t";
			print SPREADSHEETBING $height . "\t";
			print SPREADSHEETBING $width . "\t";
			print SPREADSHEETBING "2.0 pounds" . "\t";
			print SPREADSHEETBING $imageLink . "\t";
			print SPREADSHEETBING "Envelopes.com" . "\t";
			print SPREADSHEETBING $$s7Data{"upc"} . "\t";
			print SPREADSHEETBING ucfirst lc ($$s7Data{"scene7_template_id"} || "") . "\t";
			print SPREADSHEETBING ucfirst lc ($$s7Data{"product_type_id"} || "") . "\t";
			print SPREADSHEETBING "Office Supplies > General Supplies > Paper Products > Envelopes" . "\t";
			print SPREADSHEETBING $$s7Data{"base_quantity"} . "\t";
			print SPREADSHEETBING "new" . "\t";
			print SPREADSHEETBING "USD" . "\t";
			print SPREADSHEETBING urlencode("https://www.envelopes.com/envelopes/control/designProduct?designId=" . $$s7Data{"scene7_template_id"}) . "\t";
			print SPREADSHEETBING "adtype={adtype}" . "\t";
			print SPREADSHEETBING $addWordsLabel . "\t";
			print SPREADSHEETBING "in stock" . "\t";
			print SPREADSHEETBING "" . "\t";
			print SPREADSHEETBING $custom0 . "\t";
			print SPREADSHEETBING $custom1 . "\t";
			print SPREADSHEETBING $custom2 . "\t";
			print SPREADSHEETBING $custom3 . "\t";
			print SPREADSHEETBING $custom4 . "\t";
			print SPREADSHEETBING $bingLabel . "\n";

			$designHash{$$s7Data{"scene7_template_id"}} = 1;
		}
	}
}

close(SPREADSHEET) or die "Couldn't close filehandle: $!";
close(SPREADSHEET_FOLDERS) or die "Couldn't close filehandle: $!";
close(SPREADSHEETBRONTO) or die "Couldn't close filehandle: $!";
close(SPREADSHEETBING) or die "Couldn't close filehandle: $!";

for (@fileHandles) {
    close($_) or die "Couldn't open filehandle: $!";
}

$variants_handle->finish();
$getSmallestPrintQty->finish();
$features_handle->finish();
$product_category->finish();
$getScene7Data->finish();
$getScene7Category->finish();
$getScene7Product->finish();
$dbh->disconnect();

sub urlencode {
    my $s = shift;
    $s =~ s/ /+/g;
    $s =~ s/([^A-Za-z0-9\+-])/sprintf("%%%02X", ord($1))/seg;
    return $s;
}
