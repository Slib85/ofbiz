#!/usr/bin/env perl
use lib "/usr/local/ofbiz/etc/lib/";
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
use Net::OAuth;

my $ofbiz = Ofbiz->new(conf => "/usr/local/ofbiz/framework/entity/config/entityengine.xml", datasource => "localmysql");
my $dbh = DBI->connect($ofbiz->dsn("mysql"), $ofbiz->{dbuser}, $ofbiz->{dbpass}, {AutoCommit => 0}) or die "Couldn't connect to database: " . DBI->errstr;

# Runtime for script
my $runDate = strftime "%m/%d/%Y", localtime;

##
## Get the tax group from the database
##
my $getTaxInfo = $dbh->prepare(qq{
    SELECT
        group_id
    FROM
        zip_sales_tax_lookup
    WHERE zip_code = ?
});

##
## Get the last Amazon order ID used for Netsuite import
##
my $amazonID = "";
open FILE, "/usr/local/ofbiz/etc/automashun/amazonID.txt" or die "Couldn't open amazon order id file: $!";
$amazonID = join("", <FILE>);
close FILE;

##
## Get the history of all amazon order files
##
my @daysProcessedOrders = ();
open FILE, "/usr/local/ofbiz/etc/automashun/processedAmazonOrders.txt" or die "Couldn't open file: $!";
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
#empty the order log on every run after reading it
open FILE,">", "/usr/local/ofbiz/etc/automashun/processedAmazonOrders.txt" or die "Couldn't open file: $!";
print FILE $runDate . "\n";
close FILE;
# Array into hash for quick lookup
my %orderesProcessed = map { $_ => 1 } @daysProcessedOrders;

my @messageLog = ();
my @emailLog = ();
my $orderCount = 0;
my $orderErrorCount = 0;
my $tsv = Text::CSV->new({ binary => 1, sep_char => "\t" });

amazonImport();

##
## Start Amazon Import
##
sub amazonImport {
    use constant myAWSId        => 'AKIAIWD7AYNMMN4JINLQ';
	use constant myAWSSecret    => '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';
	my $awsSign = new Net::Amazon::AWSSign(myAWSId, myAWSSecret);

    my $reportDone = 0;
    my $reportID = "";

	# Request Report to be generated
    # TIMEZONE CHANGE (FALL BACK = 4)
    # TIMEZONE CHANGE (SPRING FORWARD = 3)
    my $request = {
        Action      => 'RequestReport',
        Marketplace => 'ATVPDKIKX0DER',
        SellerId    => 'A1RY28S982B9C3',
        ReportType  => '_GET_FLAT_FILE_ORDERS_DATA_',
        StartDate   => strftime("%Y-%m-%dT%H:%M:%S", localtime((time + 4 * 60 * 60) - 4 * 24 * 60 * 60)),
        EndDate     => strftime("%Y-%m-%dT%H:%M:%S", localtime((time + 4 * 60 * 60)))
    };
    my $awsRESTURI="https://mws.amazonservices.com/?".URI::Query->new($request)->stringify; #
    my $awsSignedRESTURI=$awsSign->addRESTSecret($awsRESTURI);
    my $ua = new LWP::UserAgent();

	my $res = $ua->get($awsSignedRESTURI);
	my $content = $res->content();

	my $xmlParser = new XML::Simple();
	my $xml = $xmlParser->XMLin($content);

	$reportID = $xml->{RequestReportResult}->{ReportRequestInfo}->{ReportRequestId};
	#$reportID = 354084017504;
	#$reportDone = 1;

	print "AMAZON: Waiting 2 minute for report: " . $reportID . "\n";
    sleep 120;

    # Check if report has been generated.
    $request = {
        Action                     => 'GetReportRequestList',
        "ReportRequestIdList.Id.1" => $reportID,
        Marketplace                => 'ATVPDKIKX0DER',
        SellerId                   => 'A1RY28S982B9C3'
    };

    $awsRESTURI="https://mws.amazonservices.com/?".URI::Query->new($request)->stringify; #
    $awsSignedRESTURI=$awsSign->addRESTSecret($awsRESTURI);

    my $counter = 0;
    while ($reportDone == 0 && $counter < 10) {
        $res = $ua->get($awsSignedRESTURI);
        $content = $res->content();
        $xml = $xmlParser->XMLin($content);
        if(defined $xml->{GetReportRequestListResult}->{ReportRequestInfo}->{ReportProcessingStatus} && $xml->{GetReportRequestListResult}->{ReportRequestInfo}->{ReportProcessingStatus} eq "_DONE_" ){
            $reportDone = 1;
            $reportID = $xml->{GetReportRequestListResult}->{ReportRequestInfo}->{GeneratedReportId};
        } elsif(defined $xml->{GetReportRequestListResult}->{ReportRequestInfo}->{ReportProcessingStatus} && $xml->{GetReportRequestListResult}->{ReportRequestInfo}->{ReportProcessingStatus} eq  "_DONE_NO_DATA_"){
            print "AMAZON: No new orders.\n";
        } else {
            print "AMAZON: Waiting 1 minute for report: " . $reportID . "\n";
        }
        $counter++;
        sleep 60;
    }

    if($reportDone == 0) {
        Log("AMAZON: Report is taking too long.\n");
        sendEmail("Automashun: Amazon Error, could not download order report.", join("", @emailLog));
        exit;
    }

    # Download Report once its done
    $request = {
        Action      => 'GetReport',
        ReportId    => $reportID,
        Marketplace => 'ATVPDKIKX0DER',
        SellerId    => 'A1RY28S982B9C3'
    };

    $awsRESTURI="https://mws.amazonservices.com/?".URI::Query->new($request)->stringify; #
    $awsSignedRESTURI=$awsSign->addRESTSecret($awsRESTURI);
    $res = $ua->get($awsSignedRESTURI);

    #print $res->{_content};
    my @lines = split /\n/, $res->{_content};
    my $x = 0;
    my %amazonOrder = ();
    my $lastCustomerID = "0";


    foreach my $line (@lines) {
        if ($x > 0){
            $line =~ s/\"//g;
            if ($tsv->parse($line."\n")) {
                my @fields = $tsv->fields();
                if ($fields[24] eq "CA") {
                    # Seller Central shows multiple line items if an item has a discount (Coupon).  We will skip them if that is true.
                    if ($fields[29] =~ /Coupon(?:\s+|)$/i) {
                        next;
                    }

                    my $itemPrice = $fields[11];

                    if (defined $fields[26] && $fields[26] ne "" && $fields[26] < 0) {
                        $itemPrice += $fields[26];
                    }

                    $fields[7] =~ s/^0+//g;

                    my $amazonShipMethod = "0";
                    if($fields[10] =~ /CDN/gi || $fields[10] eq "CDN") {
                        if($fields[15] eq "NextDay") {
                            $amazonShipMethod = 2130;
                        } elsif($fields[15] eq "Expedited") {
                            $amazonShipMethod = 2129;
                        } else {
                            $amazonShipMethod = 2128;
                        }
                    } elsif($fields[15] eq "Standard" || $fields[15] eq "FreeEconomy") {
                        $amazonShipMethod = 4;
                    } elsif($fields[15] eq "Expedited") {
                        $amazonShipMethod = 5;
                    } elsif($fields[15] eq "SecondDay") {
                        $amazonShipMethod = 6;
                    } elsif($fields[15] eq "NextDay") {
                        $amazonShipMethod = 7;
                    }

                    my $istaxable = ($fields[12] == 0) ? JSON::false : JSON::true;

                    my $fifteenPerc = ($itemPrice*.15)*-1;
                    my $fifteenPercShip = ($fields[13]*.15)*-1;
                    my $totalFifteenPerc = sprintf("%.4f", $fifteenPerc+$fifteenPercShip);

                    my @date = $fields[2] =~ /^(\d{4})\-(\d{2})\-(\d{2})/;
                    my $date = $date[1] . '/' . $date[2]. '/' . $date[0];

                    if($lastCustomerID eq $fields[0]){
                        #add to last order
                        Log("      Adding item: " . $fields[7] . "\n");
                        $amazonOrder{"shippingcost"} = $amazonOrder{"shippingcost"} + $fields[13];
                        push( @{$amazonOrder{"items"}}, {skuToItem($fields[7], $fields[9]), ("amount", $itemPrice)} );
                        push( @{$amazonOrder{"items"}}, {("amount", $totalFifteenPerc, "name", "Amazon Fees", "sku", "Amazon Fees", "quantity", 1)} );
                    } else {
                        # insert last order
                        if($lastCustomerID ne "0"){
                            Log(netSuiteAddOrder(to_json(\%amazonOrder)));
                        }

                        $getTaxInfo->execute(substr($fields[23],0,5));
                        my $taxInfo = $getTaxInfo->fetchrow_hashref();
                        my $taxRate = ($fields[12] + $fields[14]) / ($itemPrice + $fields[13]) * 100;

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
                        $amazonOrder{"customer"}->{"ignoreupdate"} = JSON::true;
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

                        ### HACK FOR WA ORDERS
                        my $stateName = $fields[22];
                        $stateName =~ s/^\s+|\s+$//g;
                        if(lc($stateName) eq "al" || lc($stateName) eq "alabama"
                            || lc($stateName) eq "pa" || lc($stateName) eq "pennsylvania"
                            || lc($stateName) eq "ct" || lc($stateName) eq "connecticut"
                            || lc($stateName) eq "ia" || lc($stateName) eq "iowa"
                            || lc($stateName) eq "mn" || lc($stateName) eq "minnesota"
                            || lc($stateName) eq "nj" || lc($stateName) eq "new jersey"
                            || lc($stateName) eq "ok" || lc($stateName) eq "oklahoma"
                            || lc($stateName) eq "wa" || lc($stateName) eq "washington") {
                            $amazonOrder{"taxitem"} = 19894;
                            $amazonOrder{"taxtotal"} = 0;
                            $amazonOrder{"taxrate"} = 0;
                        }

                        Log("   Processing Order #: " . $fields[0] . "\n");

                        # Add items
                        $amazonOrder{"items"} = [{skuToItem($fields[7], $fields[9]), ("amount", $itemPrice)}, {("amount", $totalFifteenPerc, "name", "Amazon Fees", "sku", "Amazon Fees", "quantity", 1)}];
                        Log("      Country Code: " . $fields[24] . "\n");
                        Log("      Adding item: " . $fields[7] . "\n");
                    }
                }
            } else {
               Log("Line could not be parsed: $line\n");
            }
        }
        $x++;
    }

    #Insert last order
    Log(netSuiteAddOrder(to_json(\%amazonOrder)));

    sendEmail("Automashun: Amazon - " . $orderCount . " orders processed, " . $orderErrorCount . " orders errored.", join("", @emailLog));
}

##
### Add order to netsuite
##
sub netSuiteAddOrder {
    my $error = 0;
    my $errorMsg = "";
    my $json = $_[0];

    try {
	print $json;
    	my $decodedJSON = decode_json($json);
    	if(!exists($orderesProcessed{"AMAZON:" . $decodedJSON->{externalid}})) {
            my $script = 17;
            my $oauth = request('protected resource')->new(
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
                if(index($res->{_content}, "record already exists") != -1) {
                    ## sometimes netsuite will say order exists and it really doesn't
                    ## in this case, look up the order and see if it does indeed exist
                    my %amazonOrderCheck = ();
                    $amazonOrderCheck{"orderId"} = $decodedJSON->{tranid};
                    $amazonOrderCheck{"externalId"} = $decodedJSON->{externalid};
		    if(!netSuiteFindOrder(to_json(\%amazonOrderCheck))) {
                        updateProcessedOrders($decodedJSON->{externalid});
                        $errorMsg = "      Order already exist.\n";
                    } else {
                        $error = 1;
                        $errorMsg = "      Error inserting and finding order.\n";
                        $orderErrorCount++;
                    }
                } elsif(index($res->{_content}, "orderId") != -1 && index($res->{_content}, "externalId") != -1) {
                    $response_json = decode_json($res->{_content});
                    if(defined $response_json->{orderId} && defined $response_json->{externalId} && $response_json->{externalId} eq $decodedJSON->{externalid}) {
                	    updateProcessedOrders($decodedJSON->{externalid});
                	    $errorMsg = "      Order created in netsuite with ID: " . $decodedJSON->{tranid} . "\n";
                	    $orderCount++;
                	} else {
                        $errorMsg = "      Error inserting order.\n";
                	}
                } else {
                    $error = 1;
                    $errorMsg = "      " . $res->{_content} . "\n";
                    $orderErrorCount++;
                }
            } catch {
                $error = 1;
                $errorMsg = "      Error communicating with netsuite.\n";
                $orderErrorCount++;
            };
    	} else {
            updateProcessedOrders($decodedJSON->{externalid});
            $errorMsg = "      Order has already been processed.\n";
        }
    } catch {
        $error = 1;
        $errorMsg = "      Error Inserting Order: $@\n";
        $orderErrorCount++;
    };

    updateOrderSequence();
    if($error) {
        emailLog("Issue inserting order: " . $json . " : " . $errorMsg . "\n\n");
    }
    return $errorMsg;
}

##
### Check if an order exists ###
##
sub netSuiteFindOrder {
    my $error = 0;
    my $errorMsg = "";
	my $json = $_[0];
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
            $response_json = decode_json($res->{_content});
            if(defined $response_json->{id}) {
                $error = 0;
            } else {
                $error = 1;
                $errorMsg = $res->{_content};
            }
        } catch {
            $error = 1;
            $errorMsg = "      Error requesting order lookup.\n";
        };
    } catch {
        $error = 1;
        $errorMsg = "      Error creating order lookup json.\n";
    };

    if($error) {
        emailLog("Issue finding order: " . $json . " : " . $errorMsg . "\n\n");
    }
    return $error;
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
	$cleanSku =~ s/^\s+|\s+$//g;
	if($cleanSku eq "69W-W") {
	    $cleanSku = "69BW-24W";
	} elsif($cleanSku eq "WS-6946") {
	    $cleanSku = "CURINNER-28W";
	}

	return ("sku", $cleanSku, "quantity", qtyTranslator($response[1]) * $qty);
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

sub sendEmail {
	my $subject = $_[0];
	my $body = $_[1];
	my $filename = $_[2];
	my $mail=Email::Send::SMTP::Gmail->new( -smtp=>'smtp.gmail.com', -login=>'service@envelopes.com', -pass=>'Envelopes@123');
	my @receipients = ( 'shoab@bigname.com', 'amy@bigname.com', 'mike@bigname.com', 'ian@bigname.com', 'caitlin@bigname.com', 'laura@bigname.com' );
	for (@receipients){
		$mail->send(-to=>$_,
					-subject=> $subject,
					-verbose=>'1',
					-body=>$body);
	}
	$mail->bye;
}

sub updateOrderSequence {
	my $fh;
    $amazonID++;
    open($fh, '>', 'amazonID.txt');
    print $fh $amazonID;
    close $fh;
}

sub updateProcessedOrders {
	my $fh;
	open($fh, '>>', '/usr/local/ofbiz/etc/automashun/processedAmazonOrders.txt');
	print $fh "AMAZON:" . $_[0] . "\n";
	close $fh;
}

###############################################
sub Log {
	push(@messageLog, $_[0]);
	print $_[0];
	return;
}
sub emailLog {
	push(@emailLog, $_[0]);
	return;
}
