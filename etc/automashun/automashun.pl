#!/usr/bin/env perl
use lib "../lib/";
use strict;
use warnings;
use IPC::System::Simple qw(system capture);
use LWP::UserAgent;
use HTTP::Headers;
use HTTP::Request;
use HTTP::Cookies;
use HTTP::Response;
use HTML::TagParser;
use Data::Dumper;
use Mail::IMAPClient;
use JSON;
use Text::CSV;
use Email::Send;
use Email::Simple::Creator;
use Try::Tiny;
use Net::Amazon::AWSSign;
use URI::Query;
use XML::Simple;
use POSIX qw(strftime);
use POSIX qw(ceil);
use XML::DOM;
use Spreadsheet::ParseExcel;
use MIME::Parser;
use IO::Socket::SSL;
use MIME::Lite;
use Email::Send::SMTP::Gmail;
use URI::Escape;
use DBI;
use Ofbiz;
use Data::Dumper;

my $ofbiz = Ofbiz->new(conf => "../../framework/entity/config/entityengine.xml", datasource => "localmysql");
my $dbh = DBI->connect($ofbiz->dsn("mysql"), $ofbiz->{dbuser}, $ofbiz->{dbpass}, {AutoCommit => 0}) or die "Couldn't connect to database: " . DBI->errstr;
my $getTaxInfo = $dbh->prepare(qq{
    SELECT
        group_id
    FROM
        zip_sales_tax_lookup
    WHERE zip_code = ?
});

my @messageLog = ();
my $error = 0;
print "Starting Netsuite Importer\n";
my $csv = Text::CSV->new({ binary => 1 });
my $tsv = Text::CSV->new({ binary => 1, sep_char => "\t" });
print "Loading New ID #s\n";
my $amazonID;
my $ebayID;
my @daysProcessedOrders = ();
my $runDate = strftime "%m/%d/%Y", localtime;

open FILE, "./amazonID.txt" or die "Couldn't open file: $!";
$amazonID = join("", <FILE>);
close FILE;
open FILE, "./ebayID.txt" or die "Couldn't open file: $!";
$ebayID = join("", <FILE>);
close FILE;
open FILE, "./processedOrders.txt" or die "Couldn't open file: $!";
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
#empty the order log on every run after reading it
open FILE,">", "./processedOrders.txt" or die "Couldn't open file: $!";
print FILE $runDate . "\n";
close FILE;

my %orderesProcessed = map { $_ => 1 } @daysProcessedOrders; #array into hash for quick lookup

#process inventory data
my %inventory = ();
#getInventory();

my %nysTax = ();

#print Dumper(\%inventory);

print "Parsing NY State Tax File.\n";
my $parser = Spreadsheet::ParseExcel->new();
my $workbook = $parser->Parse('./nys-tax.xls');
for my $worksheet ( $workbook->worksheets() ) {
	my ( $row_min, $row_max ) = $worksheet->row_range();
	for my $row ( $row_min .. $row_max ) {
		if ($row == 0){ next; }
		my $zip =$worksheet->get_cell( $row, 0 )->value();
		my $code =$worksheet->get_cell( $row, 1 )->value();
		my $rate =$worksheet->get_cell( $row, 2 )->value();
		$nysTax{$zip} = {"rate" => $rate, "code" => $code };
	}
}

#amazonImport();
eBayImport();

print "End of main program\n";

### AMAZON IMPORT ###
sub amazonImport {
	$error = 0;
	my $num = shift;
	use constant myAWSId        => 'AKIAIWD7AYNMMN4JINLQ';
	use constant myAWSSecret    => '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';
	my $awsSign = new Net::Amazon::AWSSign(myAWSId, myAWSSecret);  # New object

	# Request Report to be generated
	my $request = {
		Action => 'RequestReport',
		Marketplace => 'ATVPDKIKX0DER',
		SellerId => 'A1RY28S982B9C3',
		ReportType => '_GET_FLAT_FILE_ORDERS_DATA_',
		StartDate => strftime("%Y-%m-%dT%H:%M:%S", localtime((time + 5 * 60 * 60) - 5 * 24 * 60 * 60)),
		EndDate => strftime("%Y-%m-%dT%H:%M:%S", localtime((time + 5 * 60 * 60)))
	};
	my $awsRESTURI="https://mws.amazonservices.com/?".URI::Query->new($request)->stringify; #
	my $awsSignedRESTURI=$awsSign->addRESTSecret($awsRESTURI);
	my $ua = new LWP::UserAgent();
	print "   AMAZON: Creating new report. \n";
	my $res = $ua->get($awsSignedRESTURI);
	my $content = $res->content();

	my $xmlParser = new XML::Simple();
	my $xml = $xmlParser->XMLin($content);
	my $reportID = $xml->{RequestReportResult}->{ReportRequestInfo}->{ReportRequestId};

	print "   AMAZON: Waiting 1 minute for report: " . $reportID . "\n";
	sleep 60;

	# Check if report has been generated.
	$request = {
		Action => 'GetReportRequestList',
		"ReportRequestIdList.Id.1" => $reportID,
		Marketplace => 'ATVPDKIKX0DER',
		SellerId => 'A1RY28S982B9C3'
	};

	$awsRESTURI="https://mws.amazonservices.com/?".URI::Query->new($request)->stringify; #
	$awsSignedRESTURI=$awsSign->addRESTSecret($awsRESTURI);

	my $reportDone = 0;
	while ($reportDone == 0){
		$res = $ua->get($awsSignedRESTURI);
		$content = $res->content();
		$xml = $xmlParser->XMLin($content);
		if(defined $xml->{GetReportRequestListResult}->{ReportRequestInfo}->{ReportProcessingStatus} && $xml->{GetReportRequestListResult}->{ReportRequestInfo}->{ReportProcessingStatus} eq "_DONE_" ){
			$reportDone = 1;
			$reportID = $xml->{GetReportRequestListResult}->{ReportRequestInfo}->{GeneratedReportId};
		} elsif(defined $xml->{GetReportRequestListResult}->{ReportRequestInfo}->{ReportProcessingStatus} && $xml->{GetReportRequestListResult}->{ReportRequestInfo}->{ReportProcessingStatus} eq  "_DONE_NO_DATA_"){
			sendEmail("Automashun: Amazon - 0 orders processed.", "");
			print "   AMAZON: No new orders.\n";
			print "Sleeping Amazon Thread for 60 minutes.\n";
			sleep(3600);
			amazonImport();
			return $num;
		}else {
			print "   AMAZON: Waiting 1 minute for report: " . $reportID . "\n";
			sleep 60;
		}
	}

	# Download Report
	$request = {
		Action => 'GetReport',
		ReportId => $reportID,
		Marketplace => 'ATVPDKIKX0DER',
		SellerId => 'A1RY28S982B9C3'
	};

	$awsRESTURI="https://mws.amazonservices.com/?".URI::Query->new($request)->stringify; #
	$awsSignedRESTURI=$awsSign->addRESTSecret($awsRESTURI);
	$res = $ua->get($awsSignedRESTURI);

	my @lines = split /\n/, $res->{_content};
	my $x = 0;
	my %amazonOrder = ();
	my $lastCustomerID = "0";
	my $orderCount = 0;

	foreach my $line (@lines) {
		if ($x > 0){
			$line =~ s/\"//g;
			if ($tsv->parse($line."\n")) {
				my @fields = $tsv->fields();
				$fields[7] =~ s/^0+//g;

				my $amazonShipMethod = "0";
				if($fields[10] =~ /CDN/gi || $fields[10] eq "CDN") { $amazonShipMethod = 2128; }
				elsif($fields[15] eq "Standard" || $fields[15] eq "FreeEconomy") { $amazonShipMethod = 4; }
				elsif($fields[15] eq "Expedited") { $amazonShipMethod = 5; }
				elsif($fields[15] eq "SecondDay") { $amazonShipMethod = 6; }
				elsif($fields[15] eq "NextDay") { $amazonShipMethod = 7; }

				my $istaxable = ($fields[12] == 0) ? JSON::false : JSON::true;

				my $fifteenPerc = ($fields[11]*.15)*-1;
				my $fifteenPercShip = ($fields[13]*.15)*-1;
				my $totalFifteenPerc = sprintf("%.4f", $fifteenPerc+$fifteenPercShip);

				my @date = $fields[2] =~ /^(\d{4})\-(\d{2})\-(\d{2})/;
				my $date = $date[1] . '/' . $date[2]. '/' . $date[0];

				if($lastCustomerID eq $fields[0]){
					#add to last order
					Log ("      Adding item: " . $fields[7] . "\n");
					$amazonOrder{"shippingcost"} = $amazonOrder{"shippingcost"} + $fields[13];
					push( @{$amazonOrder{"items"}}, {skuToItem($fields[7], $fields[9]), ("amount", $fields[11])} );
					push( @{$amazonOrder{"items"}}, {("amount", $totalFifteenPerc, "name", "Amazon Fees", "sku", "Amazon Fees", "quantity", 1)} );
				} else {
					# insert last order
					if($lastCustomerID ne "0"){
						Log( netSuiteAddOrder(to_json(\%amazonOrder), 'AMAZON'));
						$orderCount++;
					}

                    $getTaxInfo->execute(substr($fields[23],0,5));
                    my $taxInfo = $getTaxInfo->fetchrow_hashref();
                    my $taxRate = ($fields[12] + $fields[14]) / ($fields[11] + $fields[13]) * 100;

					# and create new one
					$lastCustomerID = $fields[0];
					%amazonOrder = ();
					$amazonOrder{"custbody_brand"} = 2;
					$amazonOrder{"tranid"} = 'AMZ' . $amazonID;
					$amazonOrder{"customer"}->{"partyId"} = ($fields[10] =~ /CDN/gi || $fields[10] eq "CDN"? "9995" : "9997");
					$amazonOrder{"custbody_amazon_order_id"} = $fields[0];
					$amazonOrder{"externalid"} = $fields[0];
					$amazonOrder{"tranDate"} = $date;
					$amazonOrder{"shipdate"} = $date;
					$amazonOrder{"totalcostestimate"} = 0;
					$amazonOrder{"custbody_rush_production"} = JSON::false;
					$amazonOrder{"discountcode"} = JSON::null;
					$amazonOrder{"shippingcost"} = $fields[13];
					$amazonOrder{"custbody_comments"} = JSON::null;
					$amazonOrder{"custbody_printed_or_plain"} = "Plain";
					$amazonOrder{"shipmethod"} = $amazonShipMethod;
					$amazonOrder{"discounttotal"} = 0;
					$amazonOrder{"payment"} = JSON::null;
					$amazonOrder{"taxitem"} = $$taxInfo{"group_id"};
					$amazonOrder{"taxtotal"} = 0;
					$amazonOrder{"taxrate"} = $taxRate;
					$amazonOrder{"discountitem"} = JSON::null;
					$amazonOrder{"istaxable"} = $istaxable;
					$amazonOrder{"custbody_actual_ship_cost"} = JSON::null;
					$amazonOrder{"customform"} = 68;
					$amazonOrder{"custbody_blind_shipment"} = JSON::false;
					$amazonOrder{"custbody_loyalty_points"} = 0;
					if($fields[10] =~ /CDN/gi || $fields[10] eq "CDN") {
						$amazonOrder{"terms"} = 1;
					}
					$amazonOrder{"getauth"} = JSON::false;
					$amazonOrder{"creditcardprocessor"} = JSON::null;
					$amazonOrder{"order_source"} = 3;
					my $channelCustomer = $fields[5];
					$channelCustomer =~ s/[^ -~]*//g;
					$amazonOrder{"channel_customer"} = $channelCustomer;
					$amazonOrder{"custbody_address_type"} ="";
					$amazonOrder{"custbody_customer_ship_via"} ="";
					$amazonOrder{"shipnote"} = ($fields[15] eq "FreeEconomy" || lc($fields[45]) eq "true" ? "PRIME" : ($amazonShipMethod != 4 ? "EXPEDITED" : ""));

					# add shipping
					$amazonOrder{"customer"}->{"phone"} = $fields[6];
					$amazonOrder{"customer"}->{"shipping"} = {
						nameToFirstLast($fields[17]),
						"address1", $fields[18],
						"address2", $fields[19],
						"address3", $fields[20],
						"city", $fields[21],
						"state", $fields[22],
						"zip", $fields[23],
						"country", $fields[24]
					};

					Log("   Processing Order #: " . $fields[0] . "\n");

					# add items
					$amazonOrder{"items"} = [{skuToItem($fields[7], $fields[9]), ("amount", $fields[11])}, {("amount", $totalFifteenPerc, "name", "Amazon Fees", "sku", "Amazon Fees", "quantity", 1)}];
					Log ("      Adding item: " . $fields[7] . "\n");
				}

			} else {
			   Log("Line could not be parsed: $line\n");
			}
		}
		$x++;
	}
	#Insert last order
	Log(netSuiteAddOrder(to_json(\%amazonOrder), 'AMAZON'));
	$orderCount++;

	sendEmail("Automashun: Amazon - " . $orderCount . " orders processed.", join("", @messageLog));
	@messageLog = ();
}


### EBAY IMPORT ###
sub eBayImport {
	$error = 0;
    # Setup database connections
	Log("EBay: Getting orders from API." . "\n");
	my $objUserAgent = LWP::UserAgent->new;
	#get the HTTP::Headers object needed for calls to the eBay API
	my $httpHeader = HTTP::Headers->new;
	#add all the headers with their values
	$httpHeader->push_header('X-EBAY-API-COMPATIBILITY-LEVEL' => 851);
	$httpHeader->push_header('X-EBAY-API-DEV-NAME' => "9f5ff0a8-56d8-4697-ab4f-36e525ba373a");
	$httpHeader->push_header('X-EBAY-API-APP-NAME' => "Envelope-043d-4aed-bf4d-1d7e0cd102a1");
	$httpHeader->push_header('X-EBAY-API-CERT-NAME' => "f6abba88-b25a-4fec-91fa-5650db84cbc7");
	$httpHeader->push_header('X-EBAY-API-CALL-NAME' => "GetOrders");
	$httpHeader->push_header('X-EBAY-API-SITEID' => "0");
	# NOTE: TOKEN WILL EXPIRE ON 2015-12-25
	my $xmlBody = '<?xml version="1.0" encoding="utf-8" ?>';
	$xmlBody .= '<GetOrdersRequest xmlns="urn:ebay:apis:eBLBaseComponents">';
	$xmlBody .= '<RequesterCredentials>';
	$xmlBody .= '<eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**lUdlWQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wJkYGnAJmDqQudj6x9nY+seQ**kFICAA**AAMAAA**hbCI+qhLry/MNATrwiiwh3kN6cmTAXirAe7WynefizKqee2fA1NKe8VxnWpQEM0rDXmGABE6EZ8Nykxwva/FiQW+SVvoc3vDXe+UOd7SGHqMt942nmT0QmiwCP4F8YlZvxLfm048EyjwG3U8oIUgSt7BO0oXy2YxHUGVMDNY5yKKg654cwbgzUdW9+GM2f63xhN8g5qEHbIMpjqog6MG//uIf23m7LrBcCqXlkaK4StL4AuzprdJ9sda/9Oktr5ec3FWw+ctZeCIBXmF3Q2JUX8SfhlWV3FbdqC47pwWeZiA92a0e9SgHV0oXY/vtUpSVLHpY/BHfBPfxW4cyxJPQCp+hTGZoUCARurs2qZyJFHZVqueAAG++lreryX24LJFvxa/8oHL6qJGfhzKs8VIWScBkV9gpdDxUO2Jn93F/HmRTWe+YQbrpx1p6XcpnvEbM3vxTiSMOyBh3z99yj4/FgCnGcX4MTGS9GjhiB6d0/F5T5Yv4O3WewjeRztJmyzpaQc0oBw9psjr+aKre2chqHhxJvyQbZokSH24ykkwXOTTZLHNUdXieDVayDy8n5tK4CzpP/u0+BEPdyKw8d7PRyWXsBui5uTsUumaPnB+zkPMubHhtf7y5ZHB/vx657pKrttjKeqnvbMJnLaEUHkXwvN46G71cv5d7XMv8wNaAspDQlr4F2NLtXsqiuh+EBChZwRSLqYKGUomJLV/eXC7j5XdOjgVzsdOQDU7wCjEhEuZTa2idTq516ut/8icfVWL</eBayAuthToken>';
	$xmlBody .= '</RequesterCredentials>';
	$xmlBody .= '<NumberOfDays>4</NumberOfDays>';
	$xmlBody .= '<DetailLevel>ReturnAll</DetailLevel>';
	$xmlBody .= '<OrderRole>Seller</OrderRole>';
	$xmlBody .= '<Version>873</Version>';
	$xmlBody .= '</GetOrdersRequest>';

	my $httpRequest = HTTP::Request->new("POST", 'https://api.ebay.com/ws/api.dll', $httpHeader, $xmlBody);
	my $httpResponse = $objUserAgent->request($httpRequest);

	my %eBayOrder = ();
	my $orderCount = 0;
	#See if there were any errors getting the response
	if (!$httpResponse->is_error) {
		my $xmlParser = new XML::Simple();
		my $content = $httpResponse->content();
		my $xml = XMLin($content);
		my @orders;
		if (ref($xml->{OrderArray}->{Order}) eq "ARRAY") {
			@orders = @{ $xml->{OrderArray}->{Order}};
		} else {
			@orders = ($xml->{OrderArray}->{Order});
		}
		for (@orders) {
			#skip if not complete
			my $orderID = $_->{OrderID};
			if ($_->{CheckoutStatus}->{Status} ne "Complete"){
				Log("   Skipping order " . $orderID . " because its not complete.\n");
				next;
			} else {
				my @date = $_->{CreatedTime} =~ /^(\d{4})\-(\d{2})\-(\d{2})/;
				my $date = $date[1] . '/' . $date[2]. '/' . $date[0];
				$getTaxInfo->execute(substr($_->{ShippingAddress}->{PostalCode},0,5));
                my $taxInfo = $getTaxInfo->fetchrow_hashref();
				my $taxRate = $nysTax{substr($_->{ShippingAddress}->{PostalCode},0,5)}->{rate};
				my $istaxable = ($_->{ShippingDetails}->{SalesTax}->{SalesTaxAmount}->{content} eq '0.0') ? JSON::false : JSON::true;

				%eBayOrder = ();
				$eBayOrder{"custbody_brand"} = 2;
				$eBayOrder{"tranid"} = 'EBY' . $ebayID;
				$eBayOrder{"customer"}->{"partyId"} = "9996";
				if(ref( $_->{ExternalTransaction}) eq "ARRAY") {
					$eBayOrder{"custbody_amazon_order_id"} = $_->{ExternalTransaction}[0]->{ExternalTransactionID};
					$eBayOrder{"externalid"} = $_->{ExternalTransaction}[0]->{ExternalTransactionID};
				} else {
					$eBayOrder{"custbody_amazon_order_id"} = $_->{ExternalTransaction}->{ExternalTransactionID};
					$eBayOrder{"externalid"} = $_->{ExternalTransaction}->{ExternalTransactionID};
				}
				$eBayOrder{"tranDate"} = $date;
				$eBayOrder{"shipdate"} = $date;
				$eBayOrder{"totalcostestimate"} = 0;
				$eBayOrder{"custbody_rush_production"} = JSON::false;
				$eBayOrder{"discountcode"} = JSON::null;
				$eBayOrder{"shippingcost"} = $_->{ShippingServiceSelected}->{ShippingServiceCost}->{content};
				$eBayOrder{"custbody_comments"} = JSON::null;
				$eBayOrder{"custbody_printed_or_plain"} = "Plain";
				$eBayOrder{"shipmethod"} = eBayShippingMethodTranslator($_->{ShippingServiceSelected}->{ShippingService});
				$eBayOrder{"discounttotal"} = 0;
				$eBayOrder{"payment"} = JSON::null;
				$eBayOrder{"taxitem"} = $$taxInfo{"group_id"};
				$eBayOrder{"taxtotal"} = 0;
				$eBayOrder{"taxrate"} = $_->{ShippingDetails}->{SalesTax}->{SalesTaxPercent};
				$eBayOrder{"discountitem"} = JSON::null;
				$eBayOrder{"istaxable"} = $istaxable;
				$eBayOrder{"custbody_actual_ship_cost"} = JSON::null;
				$eBayOrder{"customform"} = 68;
				$eBayOrder{"custbody_blind_shipment"} = JSON::false;
				$eBayOrder{"custbody_loyalty_points"} = 0;
				$eBayOrder{"getauth"} = JSON::false;
				$eBayOrder{"creditcardprocessor"} = JSON::null;
				$eBayOrder{"terms"} = 5;
				$eBayOrder{"order_source"} = 5;
				$eBayOrder{"channel_customer"} = $_->{BuyerUserID};
				$eBayOrder{"custbody_address_type"} ="";
				$eBayOrder{"custbody_customer_ship_via"} ="";
				$eBayOrder{"shipnote"} = (eBayShippingMethodTranslator($_->{ShippingServiceSelected}->{ShippingService}) != 4 ? "EXPEDITED" : "");

				# add shipping
				$eBayOrder{"customer"}->{"phone"} = $_->{ShippingAddress}->{Phone};
				$eBayOrder{"customer"}->{"shipping"} = {
					nameToFirstLast($_->{ShippingAddress}->{Name}),
					"address1", $_->{ShippingAddress}->{Street1},
					"address2", (ref $_->{ShippingAddress}->{Street2} ne "HASH")?$_->{ShippingAddress}->{Street2}:"",
					"address3", '',
					"city", $_->{ShippingAddress}->{CityName},
					"state", $_->{ShippingAddress}->{StateOrProvince},
					"zip", $_->{ShippingAddress}->{PostalCode},
					"country", $_->{ShippingAddress}->{Country}
				};
				Log("   Processing Order #: " . $orderID . "\n");

				# add items
				$eBayOrder{"items"} = [];
				my @items;
				if (ref($_->{TransactionArray}->{Transaction}) eq "ARRAY") {
					@items = @{ $_->{TransactionArray}->{Transaction}};
				} else {
					@items = ($_->{TransactionArray}->{Transaction});
				}

				my $foundValidItems = 1;
				for (@items) {
					#print Dumper($_);
					if(defined $_->{Item}->{SKU}) {
						my $ebaySKU = $_->{Item}->{SKU};
						$ebaySKU =~ s/^0+//;
						push(@{$eBayOrder{"items"}},  {skuToItem($ebaySKU, $_->{QuantityPurchased}), ("amount", $_->{TransactionPrice}->{content}*$_->{QuantityPurchased})});
						Log("   Adding Item #: " . $_->{Item}->{SKU} . "\n");
					} else {
						Log("   Could not find SKU for: " . $_->{Item}->{ItemID} . "\n");
						$foundValidItems = 0;
						last;
					}
				}
				if($foundValidItems) {
					Log(netSuiteAddOrder(to_json(\%eBayOrder), 'EBAY'));
					$orderCount++;
				}
			}
		}
	}
	sendEmail("Automashun: EBay - " . $orderCount++ . " orders processed.", join("", @messageLog));
	@messageLog = ();
}

### HELPER FUNCTIONS ####
sub skuToItem {
	my $sku = $_[0];
	my $qty = $_[1];
	my @response = $sku =~ m/(.*)-(\d|\w*)$/gis;

	my $specialNeedsSkus = {
		"1693" => {
			"sku" => "1693",
			"quantity" => 500
		},
		"512CO-28BK" => {
			"sku" => "512CO-28BK",
			"quantity" => 500
		},
		"45315-500A" => {
			"sku" => "45315",
			"quantity" => 500
		},
		"4880-GB-25A" => {
	        "sku" => "4880-GB",
	        "quantity" => 25
		},
		"28728-500A" => {
			"sku" => "28728",
			"quantity" => 500
		},
		"28726-500A" => {
			"sku" => "28726",
			"quantity" => 500
		},
		"45315-500A" => {
			"sku" => "45315",
			"quantity" => 500
		},
		"10215" => {
			"sku" => "10215",
			"quantity" => 100
		},
		"4872-W" => {
			"sku" => "4872-W",
			"quantity" => 50
		},
		"8515-GB" => {
			"sku" => "8515-GB",
			"quantity" => 50
		},
		"4260-14-500A" => {
			"sku" => "4260-14",
			"quantity" => 500
		},
		"6675-13-50-A" => {
			"sku" => "6675-13",
			"quantity" => 50
		},
		"1693" => {
			"sku" => "1693",
			"quantity" => 500
		},
		"1693-500A" => {
			"sku" => "1693",
			"quantity" => 500
		},
		"81211-P-78-250A" => {
			"sku" => "81211-P-78",
			"quantity" => 250
		},
		"81214-P-M78-50A" => {
			"sku" => "81214-P-M78",
			"quantity" => 50
		},
		"81211-C-78-50A" => {
			"sku" => "81211-C-78",
			"quantity" => 50
		}
	};

	if (defined $specialNeedsSkus->{$sku}) {
		$response[0] = $specialNeedsSkus->{$sku}->{"sku"};
		$response[1] = $specialNeedsSkus->{$sku}->{"quantity"};
	}

	my $cleanSku = $response[0];
    if($cleanSku eq "69W-W") {
        $cleanSku = "69BW-24W";
    } elsif($cleanSku eq "WS-6946") {
        $cleanSku = "CURINNER-28W";
    }

	return ("sku", $cleanSku, "quantity", qtyTranslator($response[1]) * $qty);
}

sub eBayShippingMethodTranslator{
	my $val = $_[0];
	my %crossRef = (
		"ShippingMethodStandard", "4",
		"ShippingMethodExpress", "5",
		"ShippingMethodOvernight", "7",
		"ShippingMethodPriorityShipping", "1341",
		"InternationalPriorityShipping", "2128",
		"Other", "1341"
	);
	return $crossRef{$val};
}

sub qtyTranslator{
	my $val = $_[0];
	my %crossRef = (
		"1M", "1000",
		"1.5M", "1500",
		"2M", "2000",
		"2.5M", "2500",
		"3M", "3000",
		"5M", "5000",
		"10M", "10000",
	);
	return (defined $crossRef{$val}) ? $crossRef{$val} : $val;
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

sub parseStaplesCompanyFile{
	my @lines = split /\n/, $_[1];
	my $lineCount = 0;
	foreach my $line (@lines) {
		if ($lineCount != 0 && $csv->parse($line."\n")) {
			my @fields = $csv->fields();
			if(defined $fields[15] && defined $fields[20] && $fields[20] ne "") {
				$_[0]{$fields[15]} = $fields[20];
			}
		}
		$lineCount++;
	}
}

sub sendEmail {
	my $subject = $_[0];
	my $body = $_[1];
	my $filename = $_[2];
	my $mail=Email::Send::SMTP::Gmail->new( -smtp=>'smtp.gmail.com', -login=>'service@envelopes.com', -pass=>'BNCPassword@123');
	my @receipients = ( 'shoab@bigname.com', 'seth@bigname.com', 'amy@bigname.com', 'mike@bigname.com', 'ian@bigname.com', 'caitlin@bigname.com', 'laura@bigname.com' );
	for (@receipients){
		$mail->send(-to=>$_,
					-subject=> $subject,
					-verbose=>'1',
					-body=>$body);
	}
	$mail->bye;
}

### NETSUITE REST API ###
sub netSuiteAddOrder {
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
						updateOrderSequence($source);
						updateProcessedOrders($source, $decodedJSON->{externalid});
					} else {
						#if we receive any error other then the record existing
						#we will increment the sequence id because sometimes netsuite will fail even on a successful import
						updateOrderSequence($source);
					}

					if ($response_json->{error}->{message} ne "This record already exists") {
						$error = 1;
						return "      Error Inserting order into netsuite: " . $response_json->{error}->{message}.".\n";
					} else {
						return $response_json->{error}->{message}.".\n";
					}
				} else {
				    my %amazonOrderCheck = ();
                    $amazonOrderCheck{"orderId"} = $decodedJSON->{tranid};
                    $amazonOrderCheck{"externalid"} = $decodedJSON->{externalid};
                    netSuiteFindOrder(to_json(\%amazonOrderCheck), 'AMAZON');
				}
			} catch {
				updateOrderSequence($source);
				$error = 1;
				return "      Error communicating with netsuite.\n";
			}
		} else {
			updateOrderSequence($source);
			updateProcessedOrders($source, $decodedJSON->{externalid});
			return "      Order has already been processed.\n";
		}
	} catch {
		updateOrderSequence($source);
		$error = 1;
		return "      Error Inserting Order.\n";
	}
}

### NETSUITE REST API ###
sub netSuiteFindOrder {
	my $json = $_[0];
	my $source = $_[1];
	try {
        my $decodedJSON = decode_json($json);
		my $script = 32;
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
            if(defined $response_json->{id}){
                #success
                updateOrderSequence($source);
                updateProcessedOrders($source, $decodedJSON->{externalid});
                return "      Order created in netsuite with ID: " . $response_json->{orderId}.".\n";
            }
        } catch {
            $error = 1;
            return "      Error communicating with netsuite.\n";
        }
    } catch {
        $error = 1;
        return "      Error Finding Order.\n";
    }
}

sub hasEnoughInventory() {
	if(defined $inventory{$_[0]}){
		if($inventory{$_[0]} ne "" && int($inventory{$_[0]} / $_[1]) > 0){
			return 1;
		}
	}
	return 0;
}

sub updateOrderSequence {
	my $fh;
	if($_[0] eq 'AMAZON') {
		$amazonID++;
		open($fh, '>', 'amazonID.txt');
		print $fh $amazonID;
		close $fh;
	} elsif($_[0] eq 'EBAY'){
		$ebayID++;
		open($fh, '>', 'ebayID.txt');
		print $fh $ebayID;
		close $fh;
	}
}

sub getInventory {
	print "Getting New Inventory Data.\n";
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

	if(!$httpResponse->is_error) {
		my @perl_array = @{decode_json ($httpResponse->content())};
		foreach my $key ( @perl_array ){
			next if (!$key->{'itemid'} =~ m/.*\s+\:+\s+(.*)$/);
			$key->{'itemid'} =~ s/.*\s+\:+\s+(.*)$/$1/;
			$inventory{$key->{'itemid'}} = $key->{'qty'};
		}
	} else {
		print "   Failed to get Inventory Data from Netsuite.";
	}
}

sub updateProcessedOrders {
	my $fh;
	open($fh, '>>', 'processedOrders.txt');
	print $fh $_[0] . ":" . $_[1] . "\n";
	close $fh;
}

###############################################
sub Log {
	push(@messageLog, $_[0]);
	print $_[0];
	return;
}
