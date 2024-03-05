#!/usr/bin/env perl
use lib "/usr/local/ofbiz/etc/lib/";
use strict;
use warnings;
use DBI;
use Ofbiz;
use Data::Dumper;
use LWP::UserAgent;
use HTTP::Headers;
use HTTP::Request;
use HTTP::Request::Common;
use HTTP::Request::StreamingUpload;
use HTTP::Cookies;
use HTTP::Response;
use HTML::TagParser;
use DateTime;
use JSON;
use Net::OAuth::Client;
use URL::Encode 'url_encode';
use Math::Random::MT 'rand';

my $ofbiz = Ofbiz->new(conf => "/usr/local/ofbiz/framework/entity/config/entityengine.xml", datasource => "localmysql");
my $dbh = DBI->connect($ofbiz->dsn("mysql"), $ofbiz->{dbuser}, $ofbiz->{dbpass}, {AutoCommit => 0}) or die "Couldn't connect to database: " . DBI->errstr;

# retrieve virtual/variants products
my $products_handle = $dbh->prepare(qq{
	SELECT      p.product_id as product_id,
				p.product_type_id as product_type_id,
	            p.product_name as product_name,
	            p.long_description as long_description,
	            p.primary_product_category_id as primary_product_category_id,
	            pp.quantity as quantity,
	            pp.price as price,
	            p.product_weight as weight,
	            p.product_width as width,
	            p.product_height as height,
	            p.product_depth as depth
	FROM        product p inner join product_price pp on p.product_id = pp.product_id
	WHERE
	    p.is_virtual = 'N'
	        AND p.is_variant = 'Y'
	        AND (p.sales_discontinuation_date > NOW() OR p.sales_discontinuation_date IS NULL)
	        AND pp.colors = 0
	        AND pp.quantity <= 10000
});

my $discontinued_products = $dbh->prepare(qq{
    SELECT      p.product_id as product_id,
                pp.quantity as quantity
    FROM        product p inner join product_price pp on p.product_id = pp.product_id
    WHERE
        p.is_virtual = 'N'
            AND p.is_variant = 'Y'
            AND (p.sales_discontinuation_date IS NOT NULL AND p.sales_discontinuation_date > date_sub(NOW(), interval 1 month))
            AND pp.colors = 0
            AND pp.quantity <= 10000
});

my $category_handle = $dbh->prepare(qq{
	SELECT      category_name
	FROM        product_category
	WHERE       product_category_id = ?
});

# retrieve product features
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

die "Couldn't prepare queries; aborting" unless defined $products_handle;

my $tdt = DateTime->now;
my $tdtUTC = DateTime->now( time_zone => 'UTC' );
my %quantities = ();
my %trackingInfo = ();
my %skuUPC = ();
my $token = getToken();
my $jetID;
my @daysProcessedOrders = ();
my %orderesProcessed = map { $_ => 1 } @daysProcessedOrders; #array into hash for quick lookup
my $runDate = $tdt->mdy('/');
my $success = 1;

####################
#TODO - can remove this once SHIPPING AND RETURNS IS COMPLETE
my @skuList = [];
push @skuList, "12328-50";
#putShipment($token, '04c48c70c9c44aeca15dc0bfd1758b34', \@skuList);
####################

my $fileName = "/usr/local/ofbiz/etc/jet.json";
my $upcFile = "/usr/local/ofbiz/etc/upclist.csv";
open(my $fh, '<:encoding(UTF-8)', $upcFile) or die "Could not open file '$upcFile' $!";
while (my $row = <$fh>) {
	chomp $row;
	my @values = split(',', $row);
	if(defined $values[0] && defined $values[1]) {
		$skuUPC{$values[0]} = $values[1];
	}
}

if(defined $ARGV[0] && $ARGV[0] eq "P") {
	sendProducts();
} elsif(defined $ARGV[0] && $ARGV[0] eq "O") {
	getOrders($token);
} elsif(defined $ARGV[0] && $ARGV[0] eq "T") {
	getTaxonomy($token);
} elsif(defined $ARGV[0] && $ARGV[0] eq "R") {
	getReturns($token);
} elsif(defined $ARGV[0] && $ARGV[0] eq "S") {
 	putShipment($token);
} elsif(defined $ARGV[0] && $ARGV[0] eq "A") {
  	archiveSKUs($token);
} elsif(defined $ARGV[0] && $ARGV[0] eq "C" && defined $ARGV[1]) {
   	putCancel($token, $ARGV[1]);
}

sub getInventory {
	my $script = 19;
	my $oauth = Net::OAuth->request('protected resource')->new(
		consumer_key => '52f7448500c29b1c5fee67d032ef9fbe90fcc3fe1f824e8a22b43f78b8d46186',
		consumer_secret => 'd31ce77fb9ff1f079afa20e1732c86ef28b046c4c1c7c71ed9ee75a377d12f46',
		token            => '265bc141d90e3537137335374cff257ed5a83af795aad4e73419b7404c7656bc',
		token_secret     => '14fe0dadb2339781778b2fb7521dc69b8543f7fb17a750c6cddbc4c7aa97dd31',
		protocol_version => Net::OAuth::PROTOCOL_VERSION_1_0A,
		signature_method => 'HMAC-SHA256',
		timestamp        => time,
		nonce            => int( rand( 2**32 ) ),
		request_url => "https://073514.restlets.api.netsuite.com/app/site/hosting/restlet.nl?script=$script&deploy=1",
		request_method => 'POST',
		signature_method => 'HMAC-SHA256',
		oauth_version => '1.0',
	);
	$oauth->sign;

	my $nsContent = '{}';
	my $header = $oauth->to_authorization_header . ',realm="073514"';
	my $httpRequest = HTTP::Request->new(
		$oauth->request_method,
		$oauth->request_url,
		[
			'Authorization' => $header,
			'Content-Type' => 'application/json'
		],
		$nsContent,
	);

	my $httpResponse = LWP::UserAgent->new->request($httpRequest);

    my @manualZeroInventory = ("PC0702PL","MIR-TP40R-BP","MIR-TP40L-BP","MIR-TP40FV-BP","MIR-TP40FG-BP","MIR-TP40BK-BP","TC2W","TC2R","TR234X138W","TR234X138R","TR234X138GN","RC81211-CT","RC81211-SGBK","RC81211-BASST","PRC81211-ASST","RC81211-PCY","RC81211-PCW","RC81211-PCDR","RC81211-PCGN","RC81211-PCBL","RC81211-PCDBL","RC81211-PCBK","RC81211-R","RC81211-LB","RC81211-GN","RC81211-DBL","RC81211-BL","RC81211-BK","RC81211-CNLI","RC81211-CBLI","RC81211-CGN","RC81211-CLB","RC81211-CDG","RC81211-CR","RC81211-CDBL","RC81211-CBK","RC81211-ECBK","RC81211-RECYR","RC81211-RECYBL","RC81211-DASST","RC81211-CDBK","PLN812X634-B","PLN812X634-GN","PLN9X7-GN","STAMP-04","STAMP-06","STAMP-08","STAMP-02","STAMP-07","STAMP-03","STAMP-05","STAMP-01","NP6X9NR-ASST","LP8X11CR-W","NP5X8-W","NP6X9GR-ASST","NP5X8NRR-W","NP8X11WRR-W","NP5X8NR-ASST","NP8X112HP-Y","NP3X5NR-W","NP3X5NR-ASST","NP8X11WR-Y","NP3X5-DASST","NP3X5-BASST","MB3X5NR-ASST","NP8X11WR-W","NP8X11LR-BL","NP5X8P-ASST","NP5X8MR-IV","NP8X11QR-W","NP8X113HP-Y","NP8X113HP-W","NP5X8DS-ASST","NP5X8AP-W","NP8X11LR-W","NB9X6CR-BL","NB7X5CR-BL","NB11X9CR-BL","NB8X1012CR-ASST","NB8X1012WR-ASST","CB9X7WR-B","CB9X7WR-ASST","M912X1234TS","M912X1234S","M912X1234R","M912X1234GD","M614X1014GN","M614X1014GD","M614X1014TS","M614X1014S","M614X1014R","M1034X13S","M1034X13R","M1034X13GN","M1034X13GD","M912X1412W","M912X1412BK","M812X1412W","M812X1412BK","M812X12W","M812X12BK","M714X12W","M714X12BK","M6X10W","M6X10BK","M1414X20W","M1414X20BK","M1212X19W","M1212X19BK","M1012X16W","M1012X16BK","MIR-JB8278W-BP","MIR-JB8278K-BP","MIR-JB51433478W-BP","MIR-JB51433478K-BP","MIR-JB3123121K-BP","MIR-JB3123121W-BP","INKPAD-21","INKPAD-20","INKPAD-19","INKPAD-18","INKPAD-17","INKPAD-16","INKPAD-14","INKPAD-13","INKPAD-12","INKPAD-11","INKPAD-10","INKPAD-09","INKPAD-08","INKPAD-07","INKPAD-06","INKPAD-05","INKPAD-04","INKPAD-03","INKPAD-02","INKPAD-01","3HP20SH-S","3HP12SH-PU","3HP12SH-BL","1HP10SH-GY","2HP40SH-S","2HP20SH-S","3HP12SH-PK","3HPELEC-GY","3HP12SH-S","PLYFWAVE-ASST","PLYFTWST-ASST","PLYFVFBLUE","PLYFDSNAVY","PLYFDSBLACK","PLYF9124PNAVY","PLYF9124PBLACK","PLYF9124PASST","PLYF9124PMBLUE","FP812X11COR-W","FP43H-20W","FP8113HMP-20W","FP3HL-24W","FP812X11CR-W","FP3HMP-24W","FP3HMP-20W","FP512X8127HP-W","FP3HRECY-20W","FP812X512CR-W","FP1012X812CR-W","FP1012X812WR-W","FP3HCR-W","EXP-0321PL","EXP-0215PL","EXP-9122-26H","EXP-0275PL","EXP-0214PL","EXP-1608PL","EXP-0288PL","EXP-0251PL","EXP-1607PL","EXP-9121B-40WK","EXP-1651PL","PC0501PL","PC1806PL","PC1805PL","BM9X1112PL","BM9X1112PK","BM9X1112B","BM9X1112BK","BM912X1412TSBK","BM812X1412TSBK","BM812X12TSBK","BM714X12TSBK","LUX-MMBM-SIL1","LUX-MMBM-BLU1","LUX-MMBM-BLK1","BM6X10TSBK","LUX-MMBM-SIL3","LUX-MMBM-BLK3","BM5X10TSBK","BM4X8TSBK","BM1414X20TSBK","LUX-MMBM-SIL7","LUX-MMBM-RED7","LUX-MMBM-BLK7","LUX-MMBM-SIL5","LUX-MMBM-BLK5","BM1212X19TSBK","BM1012X16TSBK","LUX-KWBM-7","LUX-KBBM-7","LUX-KWBM-6","LUX-KBBM-6","LUX-KBBM-5","LUX-KWBM-4","LUX-KBBM-4","LUX-KWBM-3","LUX-KBBM-3","LUX-KWBM-00","LUX-KBBM-00","BP81211SL-ASST","MIR-PB9X12C-BP","MIR-PB4X6T-BP","MIR-PB4X6S-BP","MIR-AB1181W-BP","MIR-AB1181K-BP","86339","E4880-42","5875-FK","5870-FK","EXP-991J-40BK","MIR-GB9412412W-BP","BP-GB944BK","BP-GB944K","MIR-GB9412412HR-BP","KF-13536","QUAR7525","1024PL","1014PL","PF912ASST50PK-BRAD","PF912BURG-BRAD","PF912TEAL-BRAD","PF912PINK-BRAD","PF912GREEN-BRAD","PF912DBLUE-BRAD","PF912YELLOW-BRAD","PF912PURP-BRAD","PF912ORANGE-BRAD","PF912BLUE-BRAD","PF912BLACK-BRAD","PF912WHITE-BRAD","PF912ASST100PDQ-BRAD","PF912RED-BRAD","PPF-133","PF912ASST50PDQ-BRAD","PLYF912BLUE-BRAD","PF-25PACKBURG","PF-25PACKASST","16482","PLYF912YELLOW-BRAD","PLYF912RED-BRAD","PLYF912PURPLE-BRAD","PLYF912ORANGE-BRAD","PLYF912LBLUE-BRAD","PLYF912PINK-BRAD","PLYF912BRGREEN-BRAD","PLYF912DGREEN-BRAD","PLYF912DPBLUE-BRAD","PLYF912CLEAR-BRAD","PLYF912BURG-BRAD","PLYF912BLACK-BRAD","PLYF3HPCPDBLUE","PLYF3HPCPORANGE","PLYF3HPCPBRGREEN","PLYF3HPCPPURPLE","PLYF3HPCPPINK","PLYF3HPCPCLEAR","PC7107VP","PC1250PL","QUAR2011","BP-TYC912S","BP-TYC912B","BP-TYC912R","BP-TYC912G","BP-TYC912Y","80352","PR105-912","PR-108-912","PR112-912","NDP500-912","PR108-912","13234","PC7207VP","PC0503PL","912B-14T","912BFB","FE-6070-16","AVE47991","AVE47985","PF912BRGREEN-BRAD","BP-RM2K","PBM9341214-HSF","PBM9341214-HBOW","BP-RM5K","GEO45332","GEO45333","GEO47401","GEO45331","SMD89547","SMD89544","SMD89543","SMD89540","SMD89542","SMD89527","SMD89523","SMD89521","SMD89522","CON-912F-100W","91212B-14T","PFX90016","SMD11647","AVE47811","BP-888W","BP-888","BP-GB886","MIR-GB88312W-BP","BP-GB883K","MIR-GB88312HR-BP","MIR-GB88312BK-BP","ORO11102","BP-M843","BP-M843K","EXP-0213PL","PFX638143","81211-18T","81211-14T","81211-P-01","QUA35327","PB-1SRWHITE","PB-1SRBLUE","PB-1SRBLACK","PB-12SRWHITE","PB-3RREWHITE","PB-3RREBLACK","PB-3SRWHITE","PB-2RREWHITE","PB-2RREBLUE","PB-2SRWHITE","PB-2SRBLACK","PB-112RREWHITE","BP-RM10K","BP-PL415","BP-PL23","BP-M742","BP-M742K","PC7106VP","71012B-14T","DME500-6X9W","PR501-6X9W","NDP500-6X9W","PR105-6X9W","PR107-6X9W","PC7103VP","ZA-705","ZA-704","ZA-703","ZA-701","87972","DME500-6X9","NDP500-6X9","PR105-6X9","PR107-6X9","69B-14T","D-0201-502-C","QUA90063","BP-RM1K","MIR-GB666W-BP","BP-GB666K","MIR-GB666HR-BP","MIR-GB666BK-BP","BP-666","BP-666W","BP-GB664","BP-GB664BK","BP-GB664K","BP-GB664R","ORO11101","8525-17","BP-RM9K","BP-M643","BP-M643K","BP-GB644","61138B-14T","1011PL","QUA37863","612912B-14T","BP-INM610","BP-GB553","BP-GB553K","8505-14","EXP-0206PL","EXP-0265PL","EXP-0205PL","PC1129PL","8515-14","BP-P4048W","BP-P4048K","BP-GB444","MIR-GB444HR-BP","MIR-GB444BK-BP","BP-444W","BP-444","MIR-GB442W-BP","BP-GB442BK","BP-GB442K","BP-GB442R","BP-M442","BP-M442K","BP-P4036W","BP-P4036K","EXP-0201PL","PC7100VP","BP-PL420","BP-PL463","BP-PL17","BP-PL13","BP-P3036W","BP-P3036Y","BP-P3036BL","BP-P3036K","BP-P3036R","BP-P3036G","BP-P3036GO","BP-P3036B","BP-GB333","BP-GB333K","BP-GB332","BP-GB332K","BP-P3026W","BP-P3026K","BP-P3024W","BP-P3024Y","BP-P3024BL","BP-P3024K","BP-P3024R","BP-P3024G","BP-P3024GO","BP-P3024B","BP-242424W","BP-242424","BP-202020","BP-202020W","BP-P2024W","BP-P2024Y","BP-P2024BL","BP-P2024K","BP-P2024R","BP-P2024G","BP-P2024GO","BP-P2024B","MIR-GB222W-BP","BP-GB222K","BP-P2018W","BP-P2018Y","BP-P2018BL","BP-P2018K","BP-P2018R","BP-P2018G","BP-P2018GO","BP-P2018B","BP-P2012W","BP-P2012Y","BP-P2012BL","BP-P2012K","BP-P2012R","BP-P2012G","BP-P2012GO","BP-P2012B","41823","BP-181818W","BP-181818","BP-161616W","BP-161616","41520","KF-15369","KF-53676","KF-15269","KF-53656","BP-GB1466K","BP-GB1466","BP-141414W","BP-141414","BP-CPM1419BK","BP-CPM1419R","41319","BP-M13104","BP-M12932BFK","BP-GB126","BP-GB126K","MIR-GB1266HR-BP","MIR-GB1266BK-BP","BP-GB1233","PC0511PL","EXP-12153-40BK","BP-CPM1215BK","BP-CPM1215R","BP-CPM1215Y","BP-CPM1215BL","PC7112VP","MIR-GB12126W-BP","BP-121212","BP-RM4K","BP-M2BK","BP-M1291K","1117-18T","1117-14T","BP-RM3K","KF-53573","KF-53653","BP-M1184","BP-M1184K","BP-M1182","BP-M1182K","1114B-14T","PC1211FC","ORO11103","BP-GB105","BP-GB105K","EXP-0258PL","EXP-0222PL","EXP-1615PL","EXP-10152B-40WK","EXP-10152B-40BK","PC1180FC","QUA54416","1015B-14T","EXP-10132-26H","EXP-0257PL","EXP-0221PL","EXP-10132B-40WK","EXP-10132B-40BK","EXP-10132B-26H","EXP-10131-26H","EXP-0322PL","EXP-10131B-40WK","EXP-0282PL","EXP-0220PL","EXP-1677PL","EXP-1013112-26H","EXP-1013112-32WK","1007PL","1003PL","1004PL","PC7109VP","PC1251PL","QUA77197","QUA63778","QUAR2012","PC1123PL","87766","BP-TYC1013S","BP-TYC1013B","BP-TYC1013R","BP-TYC1013G","BP-TYC1013Y","1017PL","ZA-705-13","ZA-704-13","ZA-703-13","ZA-701-13","BP-EN1096","BP-EN1097","BP-EN1095","BP-EN1091","PC0506PL","1013B-14T","EXP-10124-40BK","EXP-10122B-40BK","EXP-0218PL","BP-GB101","BP-GB101K","MIR-GB10106HR-BP","MIR-GB10106BK-BP","BP-101010W","BP-101010","BP-M1081","BP-M1018K","90977","PC1906PL","QUA63262","PC1904PL","PC1127PL","QUA90077","GEO47371S","GEO47377S","PC1913PL","PC1903PL","ZA-513","ZA-512","ZA-511","2538-18T","2538-14T","2335-18T","2335-14T","1722-18T","1722-14T","PDCL85X11DB10","133FG","133FO","133FP","133FY","133PB","133PG","133PO","133PP","133PY","135W","160FG","160FO","160FP","160FY","160PB","160PG","160PO","160PP","160PY","160W","16CJ","16FG","16FO","16FP","16FY","16PB","16PG","16PO","16PP","16PY","16W","285CJ","285FG","285FO","285FP","285FY","285GF","285PB","285PG","285PO","285PP","285PY","285SF","285W","390CJ","390FG","390FO","390FP","390FY","390PB","390PG","390PO","390PP","390PY","390W","46CJ","46FG","46FO","46FP","46FY","46PB","46PG","46PO","46PP","46PY","46W","60CJ","60FG","60FO","60FP","60FY","60GF","60PB","60PG","60PO","60PP","60PY","60SF","60W","780CJ","780FG","780FO","780FP","780FY","780GF","780PB","780PG","780PO","780PP","780PY","780SF","780W","91W","96CJ","96FG","96FO","96FP","96FY","96PB","96PG","96PO","96PP","96PY","96W");
	if(!$httpResponse->is_error) {
		my @perl_array = @{decode_json ($httpResponse->content())};
		foreach my $key (@perl_array) {
			next if (!$key->{'itemid'} =~ m/.*\s+\:+\s+(.*)$/);
			$key->{'itemid'} =~ s/.*\s+\:+\s+(.*)$/$1/;

			my $retrievedQty = $key->{'qty'};
            if($key->{'itemid'} ~~ @manualZeroInventory) {
                $retrievedQty = 0;
            }

			$quantities{$key->{'itemid'}} = $retrievedQty;
		}
	} else {
		print "Failed to get Inventory Data from Netsuite.";
		exit;
	}
}

sub getTracking {
	my $script = 20;
	my $oauth = Net::OAuth->request('protected resource')->new(
		consumer_key => '52f7448500c29b1c5fee67d032ef9fbe90fcc3fe1f824e8a22b43f78b8d46186',
		consumer_secret => 'd31ce77fb9ff1f079afa20e1732c86ef28b046c4c1c7c71ed9ee75a377d12f46',
		token            => '265bc141d90e3537137335374cff257ed5a83af795aad4e73419b7404c7656bc',
		token_secret     => '14fe0dadb2339781778b2fb7521dc69b8543f7fb17a750c6cddbc4c7aa97dd31',
		protocol_version => Net::OAuth::PROTOCOL_VERSION_1_0A,
		signature_method => 'HMAC-SHA256',
		timestamp        => time,
		nonce            => int( rand( 2**32 ) ),
		request_url => "https://073514.restlets.api.netsuite.com/app/site/hosting/restlet.nl?script=$script&deploy=1",
		request_method => 'POST',
		signature_method => 'HMAC-SHA256',
		oauth_version => '1.0',
	);
	$oauth->sign;

	my $nsContent = '{"company":"jet"}';
	my $header = $oauth->to_authorization_header . ',realm="073514"';
	my $httpRequest = HTTP::Request->new(
		$oauth->request_method,
		$oauth->request_url,
		[
			'Authorization' => $header,
			'Content-Type' => 'application/json'
		],
		$nsContent,
	);

	my $httpResponse = LWP::UserAgent->new->request($httpRequest);

	if (!$httpResponse->is_error){
    	my @perl_array = @{decode_json ($httpResponse->content())};
    	foreach my $key ( @perl_array ){
        $key->{'tracking'}  =~ s/(\d+\w+)(?:<BR>(?:\d+\w+))*/$1/g;
        $key->{'tracking'}  =~ s/(?:USPSPriorityMail)(?:<BR>(\d+\w+))*/$1/g;
    		$trackingInfo{$key->{'orderid'}} = {'tracking' => $key->{'tracking'}, 'carrier' => $key->{'carrier'}, 'method' => $key->{'method'}, 'date' => $key->{'date'}};
    	}
    } else {
    	Log ("Failed to get Tracking Data from Netsuite.");
    	exit;
    }
}

sub sendProducts {
	getInventory();

	#=begin HEAD

	my %jsonData = ();
	my $counter = 0;
	#open (OD, '>', $fileName);

	$success &&= $products_handle->execute();
	while (my $product = $products_handle->fetchrow_hashref()) {
		my $sku = $$product{"product_id"};
		my $skuQty = skuWithQty($$product{"product_id"}, $$product{"quantity"});

		if(exists $skuUPC{$skuQty} && $$product{"quantity"} >= 10) {
			$counter++;
			my $weight = $$product{"quantity"} * $$product{"weight"};

			my $inventory = 0;
			if(index($sku, "81211-") != -1 || index($sku, "1218-") != -1 || index($sku, "1319-") != -1 || index($sku, "1212-") != -1 || index($sku, "1117-") != -1 || index($sku, "81214-") != -1) {
                #paper/cardstock
                $inventory = ($$product{"quantity"}*1) < 2000 ? 1000 : 0;
            } elsif(defined $$product{"product_name"} && (index(lc($$product{"product_name"}), "z-fold") != -1 || index(lc($$product{"product_name"}), "gatefold") != -1 || index(lc($$product{"product_name"}), "flat card") != -1 || index(lc($$product{"product_name"}), "folded card") != -1 || index(lc($$product{"product_name"}), "layer card") != -1 || index(lc($$product{"product_name"}), "liner") != -1 || index(lc($$product{"product_name"}), "belly band") != -1)) {
                #small paper products
                $inventory = ($$product{"quantity"}*1) < 2000 ? 1000 : 0;
            } elsif(defined $quantities{$sku} && $quantities{$sku} ne "") {
				#divide the quantity by break
				#print $sku . " " . $quantities{$sku} . "\n";
				my $inventoryCount = int($quantities{$sku} / $$product{"quantity"});
				if($inventoryCount > 0) {
					$inventory = $inventoryCount;
				}
			}

			#regular data
			my %features = ();
			$success &&= $features_handle->execute($sku);
			while(my $feature = $features_handle->fetchrow_hashref()) {
				$features{ lc $$feature{"product_feature_type_id"} } = $feature;
			}

			my $brand = "Envelopes.com";
			if(defined $features{"brand"}->{"description"} && index($features{"brand"}->{"description"}, "LUXPaper") != -1) {
				$brand = "LuxPaper";
			}

			my $catName = "";
			if(defined $$product{"primary_product_category_id"}) {
				$success &&= $category_handle->execute($$product{"primary_product_category_id"});
				$catName = $category_handle->fetchrow_array();
			}

			my $prodFullName = $$product{"product_name"} . " - " . ($features{"color"}->{"description"} || "") . " (" . $$product{"quantity"} . " Qty.)";

			my %productCode = (
							'standard_product_code_type' => 'UPC',
							'standard_product_code' => $skuUPC{$skuQty}
						);
			my @productCodes = ( \%productCode );

			my %fullFillment = (
							'fulfillment_node_id' => '60b5678e2cb745968ce15617c40f48b3',
							'fulfillment_node_price' => ($$product{"price"} || 0)*1,
							'quantity' => $inventory
						);
			my @fullFillments = ( \%fullFillment );

			my %mainData = (
							'product_title' => $prodFullName,
							'product_description' => ($$product{"long_description"} || ""),
							'multipack_quantity' => 1,
							'brand' => $brand,
							'standard_product_codes' => \@productCodes,
							'shipping_weight_pounds' => $weight,
							'jet_browse_node_id' => (($$product{"product_type_id"} eq "ENVELOPE") ? 12000120 : 5000058),
							'main_image_url' => sprintf("http://actionenvelope.scene7.com/is/image/ActionEnvelope/%s%s", $sku, "?wid=700&hei=700&fmt=jpeg"),
							'price' => ($$product{"price"} || 0)*1,
							'fulfillment_nodes' => \@fullFillments
						);

			$jsonData{$skuQty} = \%mainData;
			putProduct($token, $skuQty, $prodFullName, $skuUPC{$skuQty}, ($$product{"long_description"} || ""), ($$product{"price"} || ""), $weight, $inventory, $$product{"product_type_id"}, $brand);
		}
	}

	my $jsonFMT = encode_json \%jsonData;
    #print OD $jsonFMT;
    #close (OD);
    #=end HEAD
    #=cut

    #getUploadToken($token);
}

sub archiveSKUs {
    my $token = $_[0];

    $success &&= $discontinued_products->execute();
	while (my $product = $discontinued_products->fetchrow_hashref()) {
		my $sku = $$product{"product_id"};
		my $skuQty = skuWithQty($$product{"product_id"}, $$product{"quantity"});

        my $ua = LWP::UserAgent->new;

        my $httpHeader = HTTP::Headers->new;
        $httpHeader->push_header( "Authorization" => "bearer " . $token );
        $httpHeader->push_header( "Content-Type" => "application/json" );

        my %jsonData = (
            'is_archived' => JSON::true
        );

        my $jsonFMT = encode_json \%jsonData;
        #print Dumper $jsonFMT;

        my $httpRequest = HTTP::Request->new('PUT', 'https://merchant-api.jet.com/api/merchant-skus/' . $skuQty . '/status/archive', $httpHeader, $jsonFMT);
        my $httpResponse = $ua->request($httpRequest);

        if(!$httpResponse->is_error) {
            print "SKU: " . $skuQty . " Archived\n";
            #print $httpResponse->content() . "\n";
            #my $json = decode_json ($httpResponse->content());
            #print Dumper $json;
        } else {
            print $httpResponse->content() . "\n";
            print "Error archiving SKU: " . $skuQty . ".\n";
        }
	}
}

sub getToken {
	my $ua = LWP::UserAgent->new;

	my $httpHeader = HTTP::Headers->new;
	$httpHeader->push_header( "Content-Type" => "application/json" );

	my $httpRequest = HTTP::Request->new('POST', 'https://merchant-api.jet.com/api/token', $httpHeader, '{ "user" : "B5DD04BDE1C76D0A7B1CAD353CD5ECA93E1802AF", "pass" : "FN1ZzvRJl/JvZrZPEhlE7qWPIVSTRxv9d6iBB6zsNVUG" }');
	my $httpResponse = $ua->request($httpRequest);

	if(!$httpResponse->is_error) {
		my $json = decode_json ($httpResponse->content());
		#print "Token: " . $json->{'id_token'} . "\n";
		return $json->{'id_token'};
	} else {
		print $httpResponse->content() . "\n";
		print "Error getting token.";
	}
}

sub getUploadToken {
	my $token = $_[0];

	my $ua = LWP::UserAgent->new;

	my $httpHeader = HTTP::Headers->new;
	$httpHeader->push_header( "Authorization" => "bearer " . $token );
	$httpHeader->push_header( "Content-Type" => "application/json" );

	my $httpRequest = HTTP::Request->new('GET', 'https://merchant-api.jet.com/api/files/uploadToken', $httpHeader, '{ "user" : "225897D3D688EE742F987640AF49432307BCAEF4", "pass" : "NEtl4CGOZyeWicq7mA68uYMlvJUnjAH169D6ojdQNgxN" }');
	my $httpResponse = $ua->request($httpRequest);

	if(!$httpResponse->is_error) {
		my $json = decode_json ($httpResponse->content());
		print "Url: " . $json->{'url'} . "\n";
		print "FileID: " . $json->{'jet_file_id'} . "\n";
		putFile($json->{'url'}, $json->{'jet_file_id'}, $token);
	} else {
		print $httpResponse->content() . "\n";
		print "Error getting token.\n";
	}
}

sub putFile {
	my $url = $_[0];
	my $fileId = $_[1];
	my $token = $_[2];

	my $readData = sub {
	read(STDIN, my $buf, 65536);
		return $buf;
	};

	open my $fh, '<', $fileName or die $!;

	my $httpHeader = HTTP::Headers->new;
	#$httpHeader->push_header( "Authorization" => "bearer " . $token );
	#$httpHeader->push_header( "Content-Type" => "application/json" );
	$httpHeader->push_header( "x-ms-blob-type" => "BlockBlob" );
	$httpHeader->push_header( "Content-Length" => -s $fh );

	my $req = HTTP::Request::StreamingUpload->new(
		PUT     => $url,
		fh      => $fh,
		headers => $httpHeader,
	);

	my $httpResponse = LWP::UserAgent->new->request($req);

	if(!$httpResponse->is_error) {
		print $httpResponse->content() . "\n";
		#my $json = decode_json ($httpResponse->content());
		getFileStatus($token, $fileId);
	} else {
		print $httpResponse->content() . "\n";
		print "Error uploading file.\n";
	}
}

sub getFileStatus {
	my $token = $_[0];
	my $fileId = $_[1];

	my $ua = LWP::UserAgent->new;

	my $httpHeader = HTTP::Headers->new;
	$httpHeader->push_header( "Authorization" => "bearer " . $token );
	$httpHeader->push_header( "Content-Type" => "application/json" );

	my $httpRequest = HTTP::Request->new('GET', 'https://merchant-api.jet.com/api/files/' . $fileId, $httpHeader);
	my $httpResponse = $ua->request($httpRequest);

	if(!$httpResponse->is_error) {
		print $httpResponse->content() . "\n";
	} else {
		print $httpResponse->content() . "\n";
		print "Error getting file status.\n";
	}
}

sub putProduct {
	my $token = $_[0];
	my $sku = $_[1];
	my $title = $_[2];
	my $upc = $_[3];
	my $desc = $_[4];
	my $price = $_[5];
	my $weight = $_[6];
	my $inventory = $_[7];
	my $productType = $_[8];
	my $brand = $_[9];

	my $ua = LWP::UserAgent->new;

	my $httpHeader = HTTP::Headers->new;
	$httpHeader->push_header( "Authorization" => "bearer " . $token );
	$httpHeader->push_header( "Content-Type" => "application/json" );

	my %productCode = (
        'standard_product_code_type' => 'UPC',
        'standard_product_code' => $upc
    );
	my @productCodes = ( \%productCode );
	my %jsonData = (
        'product_title' => $title,
        'product_description' => $desc,
        'multipack_quantity' => 1,
        'brand' => $brand,
        'standard_product_codes' => \@productCodes,
        'shipping_weight_pounds' => $weight,
        'main_image_url' => sprintf("https://www.envelopes.com/images/products/detail/%s%s", skuWithoutQty($sku), ".jpg"),
        'jet_browse_node_id' => (($productType eq "ENVELOPE") ? 12000120 : 5000058)
    );

	my $jsonFMT = encode_json \%jsonData;
	#print Dumper $jsonFMT;

	my $httpRequest = HTTP::Request->new('PUT', 'https://merchant-api.jet.com/api/merchant-skus/' . $sku, $httpHeader, $jsonFMT);
	my $httpResponse = $ua->request($httpRequest);

	if(!$httpResponse->is_error) {
		print "Added $sku\t";
		#print $httpResponse->content() . "\n";
		#my $json = decode_json ($httpResponse->content());
		#print Dumper $json;
		#putProductImage($token, $sku);
		putProductPrice($token, $sku, $price);
		putProductInventory($token, $sku, $inventory);
	} else {
		print $httpResponse->content() . "\t";
		print "Error adding product.\n";
	}
}

sub putProductImage {
	my $token = $_[0];
	my $sku = $_[1];

	my $ua = LWP::UserAgent->new;

	my $httpHeader = HTTP::Headers->new;
	$httpHeader->push_header( "Authorization" => "bearer " . $token );
	$httpHeader->push_header( "Content-Type" => "application/json" );

	my %jsonData = (
        'main_image_url' => sprintf("http://actionenvelope.scene7.com/is/image/ActionEnvelope/%s%s", skuWithoutQty($sku), "?wid=700&hei=700&fmt=jpeg")
    );

	my $jsonFMT = encode_json \%jsonData;
	#print Dumper $jsonFMT;
	#print sprintf("http://actionenvelope.scene7.com/is/image/ActionEnvelope/%s%s", skuWithoutQty($sku), "?wid=700&hei=700&fmt=jpeg") . "\t";

	my $httpRequest = HTTP::Request->new('PATCH', 'https://merchant-api.jet.com/api/merchant-skus/' . $sku . '/image', $httpHeader, $jsonFMT);
	my $httpResponse = $ua->request($httpRequest);

	if(!$httpResponse->is_error) {
		print "Image\t";
		#print $httpResponse->content() . "\n";
		#my $json = decode_json ($httpResponse->content());
		#print Dumper $json;
	} else {
		print $httpResponse->content() . "\t";
		print "Error adding product image.\n";
	}
}

sub putProductPrice {
	my $token = $_[0];
	my $sku = $_[1];
	my $price = $_[2];
	my $ua = LWP::UserAgent->new;

	my $httpHeader = HTTP::Headers->new;
	$httpHeader->push_header( "Authorization" => "bearer " . $token );
	$httpHeader->push_header( "Content-Type" => "application/json" );

	my %fullFillment = (
        'fulfillment_node_id' => '60b5678e2cb745968ce15617c40f48b3',
        'fulfillment_node_price' => $price*1
	);
	my @fullFillments = ( \%fullFillment );
	my %jsonData = (
        'price' => $price*1,
        'fulfillment_nodes' => \@fullFillments
    );

	my $jsonFMT = encode_json \%jsonData;
	#print Dumper $jsonFMT;

	my $httpRequest = HTTP::Request->new('PUT', 'https://merchant-api.jet.com/api/merchant-skus/' . $sku . '/price', $httpHeader, $jsonFMT);
	my $httpResponse = $ua->request($httpRequest);

	if(!$httpResponse->is_error) {
		print "Price\t";
		#print $httpResponse->content() . "\t";
		#my $json = decode_json ($httpResponse->content());
		#print Dumper $json;
	} else {
		print $httpResponse->content() . "\t";
		print "Error adding product price.\n";
	}
}

sub putProductInventory {
	my $token = $_[0];
	my $sku = $_[1];
	my $inventory = $_[2];

	my $ua = LWP::UserAgent->new;

	my $httpHeader = HTTP::Headers->new;
	$httpHeader->push_header( "Authorization" => "bearer " . $token );
	$httpHeader->push_header( "Content-Type" => "application/json" );

	my %fullFillment = (
						'fulfillment_node_id' => '60b5678e2cb745968ce15617c40f48b3',
						'quantity' => $inventory
						);
	my @fullFillments = ( \%fullFillment );
	my %jsonData = (
						'fulfillment_nodes' => \@fullFillments
					);

	my $jsonFMT = encode_json \%jsonData;
	#print Dumper $jsonFMT;

	my $httpRequest = HTTP::Request->new('PATCH', 'https://merchant-api.jet.com/api/merchant-skus/' . $sku . '/inventory', $httpHeader, $jsonFMT);
	my $httpResponse = $ua->request($httpRequest);

	if(!$httpResponse->is_error) {
		print "Inventory\n";
		#print $httpResponse->content() . "\t";
		#my $json = decode_json ($httpResponse->content());
		#print Dumper $json;
	} else {
		print $httpResponse->content() . "\t";
		print "Error adding product inventory.\n";
	}
}

sub getTaxonomy {
	my $token = $_[0];
	my $ua = LWP::UserAgent->new;

	my $httpHeader = HTTP::Headers->new;
	$httpHeader->push_header( "Authorization" => "bearer " . $token );
	$httpHeader->push_header( "Content-Type" => "application/json" );

	my $httpRequest = HTTP::Request->new('GET', 'https://merchant-api.jet.com/api/taxonomy/links/v1?limit=1000&offset=3000', $httpHeader);
	my $httpResponse = $ua->request($httpRequest);

	if(!$httpResponse->is_error) {
		#print $httpResponse->content() . "\n";
		my $json = decode_json ($httpResponse->content());
		my $nodeList = $json->{'node_urls'};

		open(my $fh, '>>', '/usr/local/ofbiz/etc/taxonomy.txt') or die "Could not open file taxonomy.txt $!";
		foreach my $nodeUrl(@$nodeList) {
			$httpRequest = HTTP::Request->new('GET', 'https://merchant-api.jet.com/api' . $nodeUrl, $httpHeader);
			$httpResponse = $ua->request($httpRequest);
			print $httpResponse->content() . "\n\n";
			print $fh $httpResponse->content() . "\n\n";
		}
		close $fh;

		#my $json = decode_json ($httpResponse->content());
		#print Dumper $json;
	} else {
		print $httpResponse->content() . "\t";
		print "Error getting taxonomy.";
	}
}

sub getOrders {
	my $token = $_[0];
	my $ua = LWP::UserAgent->new;

	my $httpHeader = HTTP::Headers->new;
	$httpHeader->push_header( "Authorization" => "bearer " . $token );
	$httpHeader->push_header( "Content-Type" => "application/json" );

	my $httpRequest = HTTP::Request->new('GET', 'https://merchant-api.jet.com/api/orders/ready', $httpHeader);
	my $httpResponse = $ua->request($httpRequest);

	if(!$httpResponse->is_error) {
		print $httpResponse->content() . "\n";

		open FILE, "/usr/local/ofbiz/etc/jetID.txt" or die "Couldn't open file: $!";
		$jetID = join("", <FILE>);
		close FILE;

        open FILE, "/tmp/jetProcessedOrders.txt" or die "Couldn't open file: $!";
        while(<FILE>) {
        	chomp;
        	if($. == 0 && $runDate eq $_) {
        		next;
        	} elsif($. == 0 && $runDate ne $_) {
        		last;
        	}
        	if($_ ne "") {
        		push(@daysProcessedOrders, $_);
        	}
        }
        close FILE;

        open FILE, ">", "/tmp/jetProcessedOrders.txt" or die "Couldn't open file: $!";
        print FILE $runDate . "\n";
        close FILE;

		my $json = decode_json($httpResponse->content());
		my $orderList = $json->{'order_urls'};

		my %ordersForNetsuite = ();

		foreach my $orderUrl(@$orderList) {
			$httpRequest = HTTP::Request->new('GET', 'https://merchant-api.jet.com/api' . $orderUrl, $httpHeader);
			$httpResponse = $ua->request($httpRequest);

			$json = decode_json ($httpResponse->content());
			#print $httpResponse->content() . "\n";
			my $orderId = $json->{'merchant_order_id'};

			my @date = $json->{'order_ready_date'} =~ /^(\d{4})\-(\d{2})\-(\d{2})/;
			my $date = $date[1] . '/' . $date[2]. '/' . $date[0];

			Log ("	Processing Order: " . $orderId . "\n");

			%ordersForNetsuite = ();
			$ordersForNetsuite{"items"} = [];
			my $totalShipCost = 0;

			my $orderItemList = $json->{'order_items'};
			my @orderItems = ();
			foreach my $orderItemData(@$orderItemList) {
				my %tempOrderItem = ();
				$tempOrderItem{'order_item_acknowledgement_status'} = "fulfillable";
				$tempOrderItem{'order_item_id'} = $orderItemData->{'order_item_id'};

				push @orderItems, \%tempOrderItem;

				my $orderItemPrice = $orderItemData->{'item_price'};
				$totalShipCost += ($orderItemPrice->{'item_shipping_cost'} * $orderItemData->{'request_order_quantity'});

				my $totalFees = 0;
				my $orderItemFeeList = $orderItemData->{'fee_adjustments'};
				foreach my $orderItemFee(@$orderItemFeeList) {
					 $totalFees += ($orderItemFee->{'value'} * $orderItemData->{'request_order_quantity'});
				}

				Log ("		Adding item: " . skuWithoutQty($orderItemData->{'merchant_sku'}) . "\n");
				push( $ordersForNetsuite{"items"}, {skuToItem($orderItemData->{'merchant_sku'}, $orderItemData->{'request_order_quantity'}), ("amount", ($orderItemPrice->{'base_price'} * $orderItemData->{'request_order_quantity'}))} );
				push( $ordersForNetsuite{"items"}, {("amount", ($totalFees*-1), "name", "Jet Fees", "sku", "Jet Fees", "quantity", 1)} );
			}

			my $shipMethod = 4;
			if($json->{'order_detail'}->{'request_service_level'} eq "Standard") { $shipMethod = 4; }
			elsif($json->{'order_detail'}->{'request_service_level'} eq "Expedited") { $shipMethod = 5; }
			elsif($json->{'order_detail'}->{'request_service_level'} eq "Second Day") { $shipMethod = 6; }
			elsif($json->{'order_detail'}->{'request_service_level'} eq "Next Day") { $shipMethod = 7; }

			$ordersForNetsuite{"custbody_brand"} = 2;
			$ordersForNetsuite{"tranid"} = 'JET' . $jetID;
			$ordersForNetsuite{"customer"}->{"partyId"} = "9993";
			$ordersForNetsuite{"custbody_amazon_order_id"} = $orderId;
			$ordersForNetsuite{"externalid"} = $orderId;
			$ordersForNetsuite{"tranDate"} = $date;
			$ordersForNetsuite{"shipdate"} = $date;
			$ordersForNetsuite{"totalcostestimate"} = 0;
			$ordersForNetsuite{"custbody_rush_production"} = JSON::false;
			$ordersForNetsuite{"discountcode"} = JSON::null;
			$ordersForNetsuite{"shippingcost"} = $totalShipCost;
			$ordersForNetsuite{"custbody_comments"} = JSON::null;
			$ordersForNetsuite{"custbody_printed_or_plain"} = "Plain";
			$ordersForNetsuite{"shipmethod"} = $shipMethod;
			$ordersForNetsuite{"discounttotal"} = 0;
			$ordersForNetsuite{"payment"} = JSON::null;
			$ordersForNetsuite{"taxitem"} = JSON::null;
			$ordersForNetsuite{"taxtotal"} = 0;
			$ordersForNetsuite{"taxrate"} = 0;
			$ordersForNetsuite{"discountitem"} = JSON::null;
			$ordersForNetsuite{"istaxable"} = JSON::false;
			$ordersForNetsuite{"custbody_actual_ship_cost"} = JSON::null;
			$ordersForNetsuite{"customform"} = 147;
			$ordersForNetsuite{"custbody_blind_shipment"} = JSON::false;
			$ordersForNetsuite{"custbody_loyalty_points"} = 0;
			$ordersForNetsuite{"getauth"} = JSON::false;
			$ordersForNetsuite{"creditcardprocessor"} = JSON::null;
			$ordersForNetsuite{"terms"} = 5;
			$ordersForNetsuite{"order_source"} = 10;
			$ordersForNetsuite{"channel_customer"} = JSON::null;
			$ordersForNetsuite{"custbody_address_type"} = "";
			$ordersForNetsuite{"custbody_customer_ship_via"} = "";

			my $soldToName = $json->{'buyer'}->{'name'};
			$soldToName =~ s/[^a-zA-Z0-9 _-]//g;

            $ordersForNetsuite{"customer"}->{"ignoreupdate"} = JSON::true;
			$ordersForNetsuite{"customer"}->{"phone"} = $json->{'buyer'}->{'phone_number'};
			$ordersForNetsuite{"customer"}->{"billing"} = {
				nameToFirstLast($soldToName),
				"address1", "221 River Street",
				"address2", JSON::null,
				"city", "Hoboken",
				"state", "NJ",
				"zip", "07030",
				"country", "US"
			};

			my $shipToName = $json->{'shipping_to'}->{'recipient'}->{'name'};
			$shipToName =~ s/[^a-zA-Z0-9 _-]//g;

			$ordersForNetsuite{"customer"}->{"shipping"} = {
				nameToFirstLast($shipToName),
				"address1", $json->{'shipping_to'}->{'address'}->{'address1'},
				"address2", $json->{'shipping_to'}->{'address'}->{'address2'},
				"city", $json->{'shipping_to'}->{'address'}->{'city'},
				"state", $json->{'shipping_to'}->{'address'}->{'state'},
				"zip", $json->{'shipping_to'}->{'address'}->{'zip_code'},
				"country", "US"
			};

			#print Dumper(encode_json \%ordersForNetsuite);
			#exit;

			my $netsuiteOrderId = 'JET' . $jetID;
			my $intNetsuiteId = netSuiteAddOrder(to_json(\%ordersForNetsuite), 'JET');
			print $intNetsuiteId;
			if(index($intNetsuiteId, "Order created in netsuite with ID")) {
				#
			} else {
				next;
			}

			####################
			####################
			# alt_order_id should be netsuite order id
			####################
			####################
			my %jsonData = (
				'alt_order_id' => $netsuiteOrderId,
				'acknowledgement_status' => 'accepted',
				'order_items' => \@orderItems
			);

			my $jsonFMT = encode_json \%jsonData;
			#print $jsonFMT . "\n";

			$httpRequest = HTTP::Request->new('PUT', 'https://merchant-api.jet.com/api/orders/' . $orderId . '/acknowledge', $httpHeader, $jsonFMT);
			$httpResponse = $ua->request($httpRequest);
			#print $httpResponse->content() . "\n";

			#print "Order ID: " . $orderId . "\n";
		}
		#print Dumper $json;
	} else {
		print $httpResponse->content() . "\n";
		print "Error getting orders.";
	}
}

sub putShipment {
	getTracking();

	my $token = $_[0];
	my $ua = LWP::UserAgent->new;

	my $httpHeader = HTTP::Headers->new;
	$httpHeader->push_header( "Authorization" => "bearer " . $token );
	$httpHeader->push_header( "Content-Type" => "application/json" );

	my $httpRequest = HTTP::Request->new('GET', 'https://merchant-api.jet.com/api/orders/acknowledged', $httpHeader);
	my $httpResponse = $ua->request($httpRequest);

	if(!$httpResponse->is_error) {
		#print $httpResponse->content() . "\n";
		my $json = decode_json($httpResponse->content());
		my $orderList = $json->{'order_urls'};

		foreach my $orderUrl(@$orderList) {
			$httpRequest = HTTP::Request->new('GET', 'https://merchant-api.jet.com/api' . $orderUrl, $httpHeader);
			$httpResponse = $ua->request($httpRequest);

			$json = decode_json ($httpResponse->content());
			#print $httpResponse->content() . "\n";
			my $orderId = $json->{'merchant_order_id'};
			#print $orderId . "\n";

			if(exists $trackingInfo{$orderId}) {
				my $orderItemList = $json->{'order_items'};
				my @shipmentItems = ();
				foreach my $orderItemData(@$orderItemList) {
					my %tempOrderItem = ();
					$tempOrderItem{'merchant_sku'} = $orderItemData->{'merchant_sku'};
					$tempOrderItem{'response_shipment_sku_quantity'} = $orderItemData->{'request_order_quantity'};

					push @shipmentItems, \%tempOrderItem;
				}

				my $responseShipmentDate = DateTime->now( time_zone => "UTC" )->strftime("%Y-%m-%dT%H:%M:%S") . DateTime->now( time_zone => "local" )->strftime(".0000000%z");
				$responseShipmentDate =~ s/(\d{2})$/:$1/;

				my %shipment = (
					'response_shipment_date' => $responseShipmentDate,
					'shipment_tracking_number' => $trackingInfo{$orderId}->{'tracking'},
					'carrier' => 'UPS',
					'shipment_items' => \@shipmentItems
				);
				my @shipments = ( \%shipment );
				my %jsonData = (
					'shipments' => \@shipments
				);

				my $jsonFMT = encode_json \%jsonData;
				#print $jsonFMT . "\n";

				#exit;

				my $httpRequest = HTTP::Request->new('PUT', 'https://merchant-api.jet.com/api/orders/' . $orderId . '/shipped', $httpHeader, $jsonFMT);
				my $httpResponse = $ua->request($httpRequest);

				if(!$httpResponse->is_error) {
					print $httpResponse->content() . "\n";
					#my $json = decode_json ($httpResponse->content());
					#print Dumper $json;
				} else {
					print $httpResponse->content() . "\n";
					print "Error sending shipment.";
				}
			}
		}
	} else {
		print $httpResponse->content() . "\n";
		print "Error sending shipment.";
	}
}

sub putCancel {
	my $token = $_[0];
	my $orderToCancel = $_[1];
	my $ua = LWP::UserAgent->new;

	my $httpHeader = HTTP::Headers->new;
	$httpHeader->push_header( "Authorization" => "bearer " . $token );
	$httpHeader->push_header( "Content-Type" => "application/json" );

	my $httpRequest = HTTP::Request->new('GET', 'https://merchant-api.jet.com/api/orders/acknowledged', $httpHeader);
	my $httpResponse = $ua->request($httpRequest);

	if(!$httpResponse->is_error) {
		#print $httpResponse->content() . "\n";
		my $json = decode_json($httpResponse->content());
		my $orderList = $json->{'order_urls'};

		foreach my $orderUrl(@$orderList) {
			$httpRequest = HTTP::Request->new('GET', 'https://merchant-api.jet.com/api' . $orderUrl, $httpHeader);
			$httpResponse = $ua->request($httpRequest);

			$json = decode_json ($httpResponse->content());
			#print Dumper $json;
			#print $httpResponse->content() . "\n";
			my $orderId = $json->{'merchant_order_id'};
			#print $orderId . "\n";

			next if ($orderToCancel ne $orderId);

            my $orderItemList = $json->{'order_items'};
            my @shipmentItems = ();
            foreach my $orderItemData(@$orderItemList) {
                my %tempOrderItem = ();
                $tempOrderItem{'merchant_sku'} = $orderItemData->{'merchant_sku'};
                $tempOrderItem{'response_shipment_sku_quantity'} = 0;
                $tempOrderItem{'response_shipment_cancel_qty'} = $orderItemData->{'request_order_quantity'};

                push @shipmentItems, \%tempOrderItem;
            }

            my $responseShipmentDate = DateTime->now( time_zone => "UTC" )->strftime("%Y-%m-%dT%H:%M:%S") . DateTime->now( time_zone => "local" )->strftime(".0000000%z");
            $responseShipmentDate =~ s/(\d{2})$/:$1/;

            my %shipment = (
                'alt_shipment_id' => $orderId,
                'response_shipment_date' => $responseShipmentDate,
                'shipment_items' => \@shipmentItems
            );
            my @shipments = ( \%shipment );
            my %jsonData = (
                'shipments' => \@shipments
            );

            my $jsonFMT = encode_json \%jsonData;
            #print $jsonFMT . "\n";

            #exit;

            my $httpRequest = HTTP::Request->new('PUT', 'https://merchant-api.jet.com/api/orders/' . $orderId . '/shipped', $httpHeader, $jsonFMT);
            my $httpResponse = $ua->request($httpRequest);

            if(!$httpResponse->is_error) {
                print $httpResponse->content() . "\n";
                #my $json = decode_json ($httpResponse->content());
                #print Dumper $json;
            } else {
                print $httpResponse->content() . "\n";
                print "Error sending shipment.";
            }
		}
	} else {
		print $httpResponse->content() . "\n";
		print "Error sending shipment.";
	}
}

sub getCanceledOrders {
	my $token = $_[0];
	my $ua = LWP::UserAgent->new;

	my $httpHeader = HTTP::Headers->new;
	$httpHeader->push_header( "Authorization" => "bearer " . $token );
	$httpHeader->push_header( "Content-Type" => "application/json" );

	my $httpRequest = HTTP::Request->new('GET', 'https://merchant-api.jet.com/api/orders/directedCancel', $httpHeader);
	my $httpResponse = $ua->request($httpRequest);

	if(!$httpResponse->is_error) {
		my $json = decode_json ($httpResponse->content());
		my $orderList = $json->{'order_urls'};
		foreach my $orderUrl(@$orderList) {
			$httpRequest = HTTP::Request->new('GET', 'https://merchant-api.jet.com/api' . $orderUrl, $httpHeader);
			$httpResponse = $ua->request($httpRequest);

			$json = decode_json ($httpResponse->content());
			#print $httpResponse->content() . "\n";
			my $orderId = $json->{'merchant_order_id'};
			my $orderItemList = $json->{'order_items'};

			my @shipmentItems = ();
			foreach my $orderItemData(@$orderItemList) {
				my %tempOrderItem = ();
				$tempOrderItem{'merchant_sku'} = $orderItemData->{'merchant_sku'};
				$tempOrderItem{'response_shipment_cancel_qty'} = $orderItemData->{'request_order_cancel_qty'};
				push @shipmentItems, \%tempOrderItem;
			}

			my %shipment = (
						#'response_shipment_date' => '2015-07-13T21:35:43.5018545Z',
						'shipment_tracking_number' => '1ZAXASAS123213',
						#'carrier' => 'UPS',
						'alt_shipment_id' => '2015-07-14T21:35:43.5018545Z',
						'shipment_items' => \@shipmentItems
					);
			my @shipments = ( \%shipment );
			my %jsonData = (
								'shipments' => \@shipments
							);

			my $jsonFMT = encode_json \%jsonData;
			#print $jsonFMT . "\n";

			my $httpRequest = HTTP::Request->new('PUT', 'https://merchant-api.jet.com/api/orders/' . $orderId . '/shipped', $httpHeader, $jsonFMT);
			$httpResponse = $ua->request($httpRequest);
			print $httpResponse->content() . "\n";

			#print "Order ID: " . $orderId . "\n";
		}
	} else {
		print $httpResponse->content() . "\n";
		print "Error sending shipment.";
	}
}

sub getReturns {
	my $token = $_[0];
	my $ua = LWP::UserAgent->new;

	my $httpHeader = HTTP::Headers->new;
	$httpHeader->push_header( "Authorization" => "bearer " . $token );
	$httpHeader->push_header( "Content-Type" => "application/json" );

	my $httpRequest = HTTP::Request->new('GET', 'https://merchant-api.jet.com/api/returns/created', $httpHeader);
	my $httpResponse = $ua->request($httpRequest);

	if(!$httpResponse->is_error) {
		my $json = decode_json ($httpResponse->content());
		print $httpResponse->content() . "\n";
		my $orderList = $json->{'return_urls'};
		foreach my $orderUrl(@$orderList) {
			my $returnId = $orderUrl;
			$returnId =~ s/.*\///g;
			$httpRequest = HTTP::Request->new('GET', 'https://merchant-api.jet.com/api/' . $orderUrl, $httpHeader);
			$httpResponse = $ua->request($httpRequest);
			#print $httpResponse->content() . "\n";

			$json = decode_json ($httpResponse->content());
			my $orderId = $json->{'merchant_order_id'};

			my %jsonData = (
								'return_status' => 'acknowledged',
								'jet_pick_return_location' => JSON::true
							);
			my $jsonFMT = encode_json \%jsonData;
			#print $jsonFMT . "\n";

			my $httpRequest = HTTP::Request->new('PUT', 'https://merchant-api.jet.com/api/returns/' . $returnId . '/acknowledge', $httpHeader, $jsonFMT);
			$httpResponse = $ua->request($httpRequest);

			my $skuList = $json->{'return_merchant_SKUs'};
			my @refundItems = ();
			foreach my $skuObj(@$skuList) {
				my %tempOrderItem = ();
				$tempOrderItem{'order_item_id'} = $skuObj->{'order_item_id'};
				$tempOrderItem{'total_quantity_returned'} = $skuObj->{'return_quantity'};
				$tempOrderItem{'order_return_refund_qty'} = $skuObj->{'return_quantity'};

					my %refund_amount = ();
					$refund_amount{'principal'} = $skuObj->{'requested_refund_amount'}->{'principal'};
					$refund_amount{'tax'} = $skuObj->{'requested_refund_amount'}->{'tax'};
					$refund_amount{'shipping_cost'} = $skuObj->{'requested_refund_amount'}->{'shipping_cost'};
					$refund_amount{'shipping_tax'} = $skuObj->{'requested_refund_amount'}->{'shipping_tax'};

					$tempOrderItem{'refund_amount'} = \%refund_amount;
				push @refundItems, \%tempOrderItem;
			}

			#print $httpResponse->content() . "\n";
			%jsonData = (
								'merchant_order_id' => $orderId,
								'agree_to_return_charge' => JSON::true,
								'items' => \@refundItems
							);
			$jsonFMT = encode_json \%jsonData;
			print $jsonFMT . "\n";

			$httpRequest = HTTP::Request->new('PUT', 'https://merchant-api.jet.com/api/returns/' . $returnId . '/complete', $httpHeader, $jsonFMT);
			$httpResponse = $ua->request($httpRequest);
			print $httpResponse->content() . "\n";

			print "Return ID: " . $returnId . "\n";
		}
	} else {
		print $httpResponse->content() . "\n";
		print "Error sending shipment.";
	}
}

###############################################
sub Log {
	print $_[0];
	return;
}

### NETSUITE REST API ###
sub netSuiteAddOrder {
	my $error = 0;
	my $json = $_[0];
	my $source = $_[1];
	try {
		my $decodedJSON = decode_json($json);
		if(!exists($orderesProcessed{$source . ":" . $decodedJSON->{externalid}})) {
			my $script = 17;
			my $oauth = Net::OAuth->request('protected resource')->new(
				consumer_key => '52f7448500c29b1c5fee67d032ef9fbe90fcc3fe1f824e8a22b43f78b8d46186',
				consumer_secret => 'd31ce77fb9ff1f079afa20e1732c86ef28b046c4c1c7c71ed9ee75a377d12f46',
				token            => '265bc141d90e3537137335374cff257ed5a83af795aad4e73419b7404c7656bc',
				token_secret     => '14fe0dadb2339781778b2fb7521dc69b8543f7fb17a750c6cddbc4c7aa97dd31',
				protocol_version => Net::OAuth::PROTOCOL_VERSION_1_0A,
				signature_method => 'HMAC-SHA256',
				timestamp        => time,
				nonce            => int( rand( 2**32 ) ),
				request_url => "https://073514.restlets.api.netsuite.com/app/site/hosting/restlet.nl?script=$script&deploy=1",
				request_method => 'POST',
				signature_method => 'HMAC-SHA256',
				oauth_version => '1.0',
			);
			$oauth->sign;

			my $nsContent = $json;
			my $header = $oauth->to_authorization_header . ',realm="073514"';
			my $httpRequest = HTTP::Request->new(
				$oauth->request_method,
				$oauth->request_url,
				[
					'Authorization' => $header,
					'Content-Type' => 'application/json'
				],
				$nsContent,
			);

			my $res = LWP::UserAgent->new->request($httpRequest);
			my $response_json = "";

			try {
				$response_json = decode_json( $res->{_content} );
				if(defined $response_json->{error}){
					#handle error
					if($response_json->{error}->{message} eq "This record already exists") {
						updateOrderSequence();
						updateProcessedOrders($source, $decodedJSON->{externalid});
					} else {
						#if we receive any error other then the record existing
						#we will increment the sequence id because sometimes netsuite will fail even on a successful import
						updateOrderSequence();
					}

					if ($response_json->{error}->{message} ne "This record already exists") {
						$error = 1;
						return "		Error Inserting order into netsuite: " . $response_json->{error}->{message}.".\n";
					}
					else {
						return $response_json->{error}->{message}.".\n";
					}
				} else {
					#success
					updateOrderSequence();
					updateProcessedOrders($source, $decodedJSON->{externalid});
					return "		Order created in netsuite with ID: ".$response_json->{orderId}.".\n";
				}
			} catch {
				updateOrderSequence();
				$error = 1;
				return "		Error communicating with netsuite.\n";
			}
		} else {
			updateOrderSequence();
			updateProcessedOrders($source, $decodedJSON->{externalid});
			return "		Order has already been processed.\n";
		}
	} catch {
		updateOrderSequence();
		$error = 1;
		return "		Error Inserting Order.\n";
	}
}

sub updateOrderSequence {
	$jetID++;
	my $fh;
	open($fh, '>', '/usr/local/ofbiz/etc/jetID.txt');
	print $fh $jetID;
	close $fh;
}

sub updateProcessedOrders {
	my $fh;
	open($fh, '>>', '/tmp/jetProcessedOrders.txt') or die "Couldn't open file: $!";
	print $fh $_[0] . ":" . $_[1] . "\n";
	close $fh;
}

sub nameToFirstLast{
	my $name = $_[0];
	my @name = $name =~ m/(.*)\s(.*)$/gsi;
	if(scalar(@name) > 0){
		my $firstName = $name[0];
		$firstName =~ s/[^a-zA-Z0-9 _-]//g;
		my $lastName = $name[1];
		$lastName =~ s/[^a-zA-Z0-9 _-]//g;
		return ("firstname", $firstName, "lastname", $lastName)
	} else{
		my $fullName = $name;
		$fullName =~ s/[^a-zA-Z0-9 _-]//g;
		return ("firstname", $fullName, "lastname", "")
	}
}

sub skuWithoutQty {
	my $sku = shift;
	$sku = substr $sku, 0, rindex($sku, "-");

	return $sku;
}

sub skuWithQty {
	my $sku = $_[0];
	my $qty = $_[1];

	my $newSku = $sku . "-" . $qty;

	if($qty > 500) {
		my $mVal = $qty/1000;
		$newSku = $sku . "-" . $mVal . "M";
	}

	return $newSku;
}

sub skuToItem {
	my $sku = $_[0];
	my $qty = $_[1];

	my $skuNoQty = skuWithoutQty($sku);
	my $skuQty = substr $sku, (rindex($sku, "-")+1);

	if(index($skuQty, "M") != -1) {
		$skuQty = substr $skuQty, 0, (rindex($skuQty, "M"));
		$skuQty = $skuQty * 1000;
	}

	return ("sku", $skuNoQty, "quantity", $skuQty * $qty);
}

$discontinued_products->finish();
$features_handle->finish();
$category_handle->finish();
$products_handle->finish();
$dbh->disconnect();
