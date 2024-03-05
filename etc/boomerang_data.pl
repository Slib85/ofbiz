#!/usr/bin/env perl
use lib "/usr/local/ofbiz/etc/lib/";
use strict;
use warnings;
use DBI;
use Ofbiz;
use Data::Dumper;
use JSON;
use LWP::UserAgent;
use Text::CSV_XS;
use Net::Google::Analytics;
use Net::Google::Analytics::OAuth2;
use DateTime;
use Net::SFTP::Foreign;

my $ofbiz = Ofbiz->new(conf => "/usr/local/ofbiz/framework/entity/config/entityengine.xml", datasource => "localmysql");
my $dbh = DBI->connect($ofbiz->dsn("mysql"), $ofbiz->{dbuser}, $ofbiz->{dbpass}, {AutoCommit => 0}) or die "Couldn't connect to database: " . DBI->errstr;

use constant MAPPING_FILE => "/usr/local/ofbiz/etc/proxy_rewrite.txt";
use constant BOOMERANG_CHANNEL => "/usr/local/ofbiz/etc/boomerang_channel.csv";
use constant BOOMERANG_DATA => "/usr/local/ofbiz/etc/boomerang_data.csv";
use constant BOOMERANG_COST => "/usr/local/ofbiz/etc/boomerang_cost.csv";

my $dueDateReport = 0;
if(defined $ARGV[0] && defined $ARGV[1] && $ARGV[0] ne "" && $ARGV[1] ne "") {
	$dueDateReport = 1;
}

# retrieve virtual/variants products
my $products_handle = $dbh->prepare(qq{
    SELECT
        p.PRODUCT_ID AS product_id,
        p.PRODUCT_TYPE_ID AS product_type_id,
        p.PRIMARY_PRODUCT_CATEGORY_ID AS primary_product_category_id,
        p.MANUFACTURER_PARTY_ID AS manufacturer_party_id,
        p.FACILITY_ID AS facility_id,
        p.INTRODUCTION_DATE AS introduction_date,
        p.RELEASE_DATE AS release_date,
        p.SUPPORT_DISCONTINUATION_DATE AS support_discontinuation_date,
        p.SALES_DISCONTINUATION_DATE AS sales_discontinuation_date,
        p.SALES_DISC_WHEN_NOT_AVAIL AS sales_disc_when_not_avail,
        p.INTERNAL_NAME AS internal_name,
        p.BRAND_NAME AS brand_name,
        p.COMMENTS AS comments,
        p.PRODUCT_NAME AS product_name,
        p.DESCRIPTION AS description,
        p.LONG_DESCRIPTION AS long_description,
        p.PRICE_DETAIL_TEXT AS price_detail_text,
        p.SMALL_IMAGE_URL AS small_image_url,
        p.MEDIUM_IMAGE_URL AS medium_image_url,
        p.LARGE_IMAGE_URL AS large_image_url,
        p.DETAIL_IMAGE_URL AS detail_image_url,
        p.ORIGINAL_IMAGE_URL AS original_image_url,
        p.DETAIL_SCREEN AS detail_screen,
        p.INVENTORY_MESSAGE AS inventory_message,
        p.REQUIRE_INVENTORY AS require_inventory,
        p.QUANTITY_UOM_ID AS quantity_uom_id,
        p.QUANTITY_INCLUDED AS quantity_included,
        p.PIECES_INCLUDED AS pieces_included,
        p.REQUIRE_AMOUNT AS require_amount,
        p.FIXED_AMOUNT AS fixed_amount,
        p.AMOUNT_UOM_TYPE_ID AS amount_uom_type_id,
        p.WEIGHT_UOM_ID AS weight_uom_id,
        p.PRODUCT_WEIGHT AS weight,
        p.PRODUCT_WEIGHT AS product_weight,
        p.HEIGHT_UOM_ID AS height_uom_id,
        p.PRODUCT_HEIGHT AS product_height,
        p.SHIPPING_HEIGHT AS shipping_height,
        p.WIDTH_UOM_ID AS width_uom_id,
        p.PRODUCT_WIDTH AS product_width,
        p.SHIPPING_WIDTH AS shipping_width,
        p.DEPTH_UOM_ID AS depth_uom_id,
        p.PRODUCT_DEPTH AS product_depth,
        p.SHIPPING_DEPTH AS shipping_depth,
        p.DIAMETER_UOM_ID AS diameter_uom_id,
        p.PRODUCT_DIAMETER AS product_diameter,
        p.PRODUCT_RATING AS product_rating,
        p.RATING_TYPE_ENUM AS rating_type_enum,
        p.RETURNABLE AS returnable,
        p.TAXABLE AS taxable,
        p.CHARGE_SHIPPING AS charge_shipping,
        p.AUTO_CREATE_KEYWORDS AS auto_create_keywords,
        p.INCLUDE_IN_PROMOTIONS AS include_in_promotions,
        p.IS_VIRTUAL AS is_virtual,
        p.IS_VARIANT AS is_variant,
        p.VIRTUAL_VARIANT_METHOD_ENUM AS virtual_variant_method_enum,
        p.ORIGIN_GEO_ID AS origin_geo_id,
        p.REQUIREMENT_METHOD_ENUM_ID AS requirement_method_enum_id,
        p.BILL_OF_MATERIAL_LEVEL AS bill_of_material_level,
        p.RESERV_MAX_PERSONS AS reserv_max_persons,
        p.RESERV2ND_P_P_PERC AS reserv2nd_p_p_perc,
        p.RESERV_NTH_P_P_PERC AS reserv_nth_p_p_perc,
        p.CONFIG_ID AS config_id,
        p.CREATED_DATE AS created_date,
        p.CREATED_BY_USER_LOGIN AS created_by_user_login,
        p.LAST_MODIFIED_DATE AS last_modified_date,
        p.LAST_MODIFIED_BY_USER_LOGIN AS last_modified_by_user_login,
        p.IN_SHIPPING_BOX AS in_shipping_box,
        p.DEFAULT_SHIPMENT_BOX_TYPE_ID AS default_shipment_box_type_id,
        p.LOT_ID_FILLED_IN AS lot_id_filled_in,
        p.ORDER_DECIMAL_QUANTITY AS order_decimal_quantity,
        p.LAST_UPDATED_STAMP AS last_updated_stamp,
        p.LAST_UPDATED_TX_STAMP AS last_updated_tx_stamp,
        p.CREATED_STAMP AS created_stamp,
        p.CREATED_TX_STAMP AS created_tx_stamp,
        p.PARENT_PRODUCT_ID AS parent_product_id,
        p.AVERAGE_CUSTOMER_RATING AS average_customer_rating,
        p.META_TITLE AS meta_title,
        p.META_DESCRIPTION AS meta_description,
        p.META_KEYWORD AS meta_keyword,
        p.COLOR_DESCRIPTION AS color_description,
        p.COLOR_COUNT AS color_count,
        p.PLAIN_PRICE_DESCRIPTION AS plain_price_description,
        p.PRINT_PRICE_DESCRIPTION AS print_price_description,
        p.IS_PRINTABLE AS is_printable,
        p.HAS_RUSH_PRODUCTION AS has_rush_production,
        p.CARTON_QTY AS carton_qty,
        p.HAS_COLOR_OPT AS has_color_opt,
        p.BIN_LOCATION AS bin_location,
        p.IS_ASSEMBLY_ITEM AS is_assembly_item,
        p.TAG_LINE AS tag_line,
        p.ON_SALE AS on_sale,
        p.ON_CLEARANCE as on_clearance,
        p.HAS_SAMPLE AS has_sample,
        p.HAS_WHITE_INK as has_white_ink,
        ps.QUANTITY_ON_HAND as quantity_on_hand
    from product p left join product_stock ps on p.product_id = ps.product_id where p.sales_discontinuation_date is null and p.is_variant = "Y"
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

# retrieve product price
my $price_handle = $dbh->prepare(qq{
    select		quantity,
                price,
                original_price,
                colors
    from		product_price
    where		product_id = ?
    order by    quantity, colors asc
});

my $category_handle = $dbh->prepare(qq{
    select      category_name
    from        product_category
    where       product_category_id = ?
});

# get order data
my $getOrderData = $dbh->prepare(qq{
    SELECT
        date(oh.created_stamp) AS date,
        oi.product_id as product_id,
        oi.quantity AS quantity,
        oi.order_id AS order_id,
        (oi.unit_price * oi.quantity) AS price,
        oi.artwork_source as artwork_source,
        oa.amount AS shipping
    FROM
        order_item oi
            INNER JOIN
        order_header oh ON oi.order_id = oh.order_id
            LEFT JOIN
        order_adjustment oa ON oh.order_id = oa.order_id
    WHERE
        oi.product_id = ?
            AND oa.order_adjustment_type_id = 'SHIPPING_CHARGES'
            AND date(oh.created_stamp) = ?
    ORDER BY oh.created_stamp asc, oh.order_id
});

# get order data
my $getOrderDataRange = $dbh->prepare(qq{
    SELECT
        date(oh.created_stamp) AS date,
        oi.product_id as product_id,
        oi.quantity AS quantity,
        oi.order_id AS order_id,
        (oi.unit_price * oi.quantity) AS price,
        oa.amount AS shipping
    FROM
        order_item oi
            INNER JOIN
        order_header oh ON oi.order_id = oh.order_id
            LEFT JOIN
        order_adjustment oa ON oh.order_id = oa.order_id
    WHERE
        oi.product_id = ?
            AND oa.order_adjustment_type_id = 'SHIPPING_CHARGES'
            AND date(oh.entry_date) >= ? AND date(oh.entry_date) <= ?
    ORDER BY oh.entry_date asc, oh.order_id
});

# get adjustment data
my $getAvgAdjustment = $dbh->prepare(qq{
    SELECT
        (SUM(oa.amount) / (SELECT
                COUNT(*)
            FROM
                order_item
            WHERE
                order_id = ?))
    FROM
        order_adjustment oa
    WHERE
        oa.order_id = ?
            AND oa.amount < 0;
});

my $analytics = Net::Google::Analytics->new;
########ANALYTICS
my $profile_id    = '25325701';
my $client_id     = '607186549491-sp8gdjfughb1c27oun05sc1e6j45dcti.apps.googleusercontent.com';
my $client_secret = 'EaiZFQaTwZ5-8nlBy-MrNmzQ';
my $refresh_token = '1/TpLRzvm2ehcC6ETBHv6IW99jC2uorY_sTBnXO9SZvw1IgOrJDtdun6zK6XiATCKT';

my $tdt = DateTime->now;
$tdt = $tdt->subtract(days => 1);
my $pdt = DateTime->now;
$pdt = $pdt->subtract(days => 2);

if($dueDateReport == 0) {
    my %averageCost = ();
    my $getNetsuiteCost = 1;
    if($getNetsuiteCost) {
        print "Getting Average Cost Data.\n";
        my $script = 30;
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

        if (!$httpResponse->is_error){
            my @perl_array = @{decode_json ($httpResponse->content())};
            foreach my $key ( @perl_array ) {
                my $avgCost = ($key->{'averageCost'} ne "") ? $key->{'averageCost'} : 0;
                if($avgCost <= 0) {
                    next;
                }
                $averageCost{$key->{'sku'}} = $avgCost;
            }
        } else {
            print "   Failed to get Inventory Data from Netsuite.";
            exit;
        }
    }

    # Authenticate
    my $oauth = Net::Google::Analytics::OAuth2->new(
        client_id     => $client_id,
        client_secret => $client_secret,
    );

    my $token = $oauth->refresh_access_token($refresh_token);
    $analytics->token($token);

    # Send request
    my $startIndex = 1;
    my $maxResults = 1000;
    my $totalResults = 0;
    my %pageAndViews = ();
    my %uniqueVisits = ();
    my $processAnalytics = 1;

    if($processAnalytics) {
        print "Processing\n";
        do {
            my $res = $analytics->retrieve(getAnalyticsPageViews($startIndex, $maxResults, $tdt->ymd('-'), $pdt->ymd('-')));
            if(!$res->is_success) {
                print $res->error_message;
            } else {
                $totalResults = $res->total_results;
                for my $row (@{ $res->rows }) {
                    my $pagePath = ((index $row->get_page_path, '?') == -1) ? $row->get_page_path : substr $row->get_page_path, 0, (index $row->get_page_path, '?');
                    #print "Path: " . $pagePath . ", Page Views: " . $row->get_pageviews .  ", Unique: " . $row->get_unique_pageviews . "\n";
                    if(exists $pageAndViews{$pagePath}) {
                        $pageAndViews{$pagePath} = ($pageAndViews{$pagePath}*1) + ($row->get_pageviews*1);
                    } else {
                        $pageAndViews{$pagePath} = $row->get_pageviews;
                    }

                    if(exists $uniqueVisits{$pagePath}) {
                        $uniqueVisits{$pagePath} = ($uniqueVisits{$pagePath}*1) + ($row->get_unique_pageviews*1);
                    } else {
                        $uniqueVisits{$pagePath} = $row->get_unique_pageviews;
                    }
                }

                $startIndex += $maxResults;
                print "Completing $startIndex of $totalResults\n";
            }
        } while($startIndex + $maxResults < $totalResults);
    }

    die "Couldn't prepare queries; aborting" unless defined $products_handle;
    die "URL mappings file does not exist; aborting" unless -f MAPPING_FILE;
    die "Channel mappings file does not exist; aborting" unless -f BOOMERANG_CHANNEL;
    die "Data mappings file does not exist; aborting" unless -f BOOMERANG_DATA;
    die "Cost mappings file does not exist; aborting" unless -f BOOMERANG_COST;

    my %url_mapping;
    open(my $urlfh, "<", MAPPING_FILE) or die "Couldn't open mappings file at " . MAPPING_FILE;
    while (<$urlfh>) {
        chomp;
        chop;
        my ($key, $val)    = split(/\t/);
        $url_mapping{$val} = $key
        if (defined $key && defined $val);
    }
    close($urlfh) or die "Couldn't close mappings file at " . MAPPING_FILE;

    my %channel_mapping = ();
    my $boomerangChannel = Text::CSV_XS->new ({ binary => 1 });
    open (INPUTFILE, "<", BOOMERANG_CHANNEL) or die "Couldn't open filehandle: $!";
    while(<INPUTFILE>) {
        next if ($. == 1);
        if($boomerangChannel->parse($_)) {
            my @columns = $boomerangChannel->fields();
            $channel_mapping{$columns[0]}->{"staples"} = $columns[1];
            $channel_mapping{$columns[0]}->{"officedepot"} = $columns[2];
            $channel_mapping{$columns[0]}->{"amazon"} = $columns[3];
        }
    }
    close INPUTFILE or die "Couldn't close channel mappings file at " . BOOMERANG_CHANNEL;
    #print Dumper %channel_mapping;

    my %data_mapping = ();
    my $boomerangData = Text::CSV_XS->new ({ binary => 1 });
    open (INPUTFILE, "<", BOOMERANG_DATA) or die "Couldn't open filehandle: $!";
    while(<INPUTFILE>) {
        next if ($. == 1);
        if($boomerangData->parse($_)) {
            my @columns = $boomerangData->fields();
            $data_mapping{$columns[0]}->{"brand"} = $columns[1];
            $data_mapping{$columns[0]}->{"manufacturer"} = $columns[2];
            $data_mapping{$columns[0]}->{"business"} = $columns[3];
            $data_mapping{$columns[0]}->{"group"} = $columns[4];
            $data_mapping{$columns[0]}->{"launch"} = $columns[5];
        }
    }
    close INPUTFILE or die "Couldn't close channel mappings file at " . BOOMERANG_DATA;
    #print Dumper %data_mapping;

    my %cost_mapping = ();
    my $boomerangCost = Text::CSV_XS->new ({ binary => 1 });
    open (INPUTFILE, "<", BOOMERANG_COST) or die "Couldn't open filehandle: $!";
    while(<INPUTFILE>) {
        next if ($. == 1);
        if($boomerangCost->parse($_)) {
            my @columns = $boomerangCost->fields();
            $cost_mapping{$columns[0]} = $columns[1];
        }
    }
    close INPUTFILE or die "Couldn't close channel mappings file at " . BOOMERANG_COST;
    #print Dumper %cost_mapping;

    my $itemMaster = Text::CSV_XS->new ({ binary => 1, sep_char => '|', eol => $/, quote_char => '' });
    my $performanceData = Text::CSV_XS->new ({ binary => 1, sep_char => '|', eol => $/, quote_char => '' });
    my $performanceData2 = Text::CSV_XS->new ({ binary => 1, sep_char => '|', eol => $/, quote_char => '' });
    my $priceData = Text::CSV_XS->new ({ binary => 1, sep_char => '|', eol => $/, quote_char => '' });
    my $costData = Text::CSV_XS->new ({ binary => 1, sep_char => '|', eol => $/, quote_char => '' });
    my $inventory = Text::CSV_XS->new ({ binary => 1, sep_char => '|', eol => $/, quote_char => '' });

    my $IMHEADER = [
        'sku',
        'Title',
        'product_url',
        'image_url',
        'description',
        'Size',
        'brand',
        'Upc',
        'Ean',
        'Isbn',
        'Manufacturer',
        'Mpn',
        'Line',
        'is_marketplace',
        'is_privatelabel',
        'gl_l1_id',
        'gl_l1_name',
        'gl_l2_id',
        'gl_l2_name',
        'gl_l3_id',
        'gl_l3_name',
        'gl_l4_id',
        'gl_l4_name',
        'gl_l5_id',
        'gl_l5_name',
        'merch_l1_id',
        'merch_l1_name',
        'merch_l2_id',
        'merch_l2_name',
        'merch_l3_id',
        'merch_l3_name',
        'merch_l4_id',
        'merch_l4_name',
        'merch_l5_id',
        'merch_l5_name',
        'browse_taxonomy',
        'Color',
        'Color Group',
        'Dimension',
        'Material',
        'Paper weight',
        'Style',
        'Sealing method',
        'Texture',
        'Recycled content',
        'Brightness',
        'Printer compatibility',
        'Pack size',
        'Business Category',
        'Product Group',
        'Launch Date',
        'Staples_Flag',
        'OD_Flag',
        'Amazon_Flag',
        'On_Sale'
    ];

    my $PERFORMANCEHEADER = [
        'Sku',
        'transaction_date',
        'gross_units',
        'gross_orders',
        'gross_revenue',
        'adjusted_revenue',
        'gross_cost',
        'gross_subsidized_cost',
        'gross_shipping_cost',
        'gross_variable_cost',
        'gross_shipping_charges',
        'page_views',
        'unique_visits',
        'channel_id',
        'inventory'
    ];

    my $PERFORMANCEHEADER2 = [
        'Sku',
        'transaction_date',
        'gross_units',
        'gross_orders',
        'gross_revenue',
        'adjusted_revenue',
        'gross_cost',
        'gross_subsidized_cost',
        'gross_shipping_cost',
        'gross_variable_cost',
        'gross_shipping_charges',
        'page_views',
        'unique_visits',
        'channel_id',
        'inventory',
        'plain_revenue',
        'printed_revenue'
    ];

    my $PRICEHEADER = [
        'sku',
        'channel_id',
        'effective_date',
        'list_price',
        'reg_price',
        'offer_price',
        'Msrp',
        'map_price',
        'on_map',
        'break_point_price1',
        'break_point_qty1',
        'break_point_price2',
        'break_point_qty2',
        'break_point_price3',
        'break_point_qty3',
        'break_point_price4',
        'break_point_qty4',
        'break_point_price5',
        'break_point_qty5',
        'break_point_price6',
        'break_point_qty6',
        'break_point_price7',
        'break_point_qty7',
        'break_point_price8',
        'break_point_qty8',
        'break_point_price9',
        'break_point_qty9',
        'break_point_price10',
        'break_point_qty10',
        'break_point_price11',
        'break_point_qty11',
        'break_point_price12',
        'break_point_qty12',
        'break_point_price13',
        'break_point_qty13',
        'break_point_price14',
        'break_point_qty14',
        'break_point_price15',
        'break_point_qty15'
    ];

    my $COSTHEADER = [
        'Sku',
        'effective_date',
        'po_cost',
        'shipping_cost',
        'variable_cost',
        'vendor_subsidized_po_cost',
        'channel_id'
    ];

    my $INVENTORYHEADER = [
        'Sku',
        'Quantity'
    ];

    open(IM, '>/tmp/boomerang_im.csv') or die "Couldn't open filehandle: $!";
    $itemMaster->print(\*IM, $IMHEADER);

    open(PD, '>/tmp/boomerang_pd.csv') or die "Couldn't open filehandle: $!";
    $performanceData->print(\*PD, $PERFORMANCEHEADER);

    open(PD2, '>/tmp/boomerang_pd2.csv') or die "Couldn't open filehandle: $!";
    $performanceData2->print(\*PD2, $PERFORMANCEHEADER2);

    open(PRD, '>/tmp/boomerang_prd.csv') or die "Couldn't open filehandle: $!";
    $priceData->print(\*PRD, $PRICEHEADER);

    open(CD, '>/tmp/boomerang_cd.csv') or die "Couldn't open filehandle: $!";
    $costData->print(\*CD, $COSTHEADER);

    open(INV, '>/tmp/boomerang_inv.csv') or die "Couldn't open filehandle: $!";
    $inventory->print(\*INV, $INVENTORYHEADER);

    my $success = 1;

    $success &&= $products_handle->execute();
    while (my $product = $products_handle->fetchrow_hashref()) {
        #regular data
        my %features = ();
        $success &&= $features_handle->execute($$product{"product_id"});
        while(my $feature = $features_handle->fetchrow_hashref()) {
            $features{ lc $$feature{"product_feature_type_id"} } = $feature;
        }

        $success &&= $category_handle->execute($$product{"primary_product_category_id"});
        my $category = $category_handle->fetchrow_array();

        my $longDesc = $$product{"long_description"} || "";
        $longDesc =~ s/\R//g;

        my $colorDesc = $$product{"color_description"} || "";
        $colorDesc =~ s/\R//g;

        my $url_domain = 'http://www.envelopes.com';
        my $url_path   = sprintf('/envelopes/control/product/~category_id=%s/~product_id=%s', $$product{"primary_product_category_id"}, $$product{"product_id"});

        my $brand = (exists $data_mapping{$$product{"product_id"}} && exists $data_mapping{$$product{"product_id"}}->{"brand"}) ? $data_mapping{$$product{"product_id"}}->{"brand"} : '';
        my $manufacturer = (exists $data_mapping{$$product{"product_id"}} && exists $data_mapping{$$product{"product_id"}}->{"manufacturer"}) ? $data_mapping{$$product{"product_id"}}->{"manufacturer"} : '';
        my $businesscat = (exists $data_mapping{$$product{"product_id"}} && exists $data_mapping{$$product{"product_id"}}->{"business"}) ? $data_mapping{$$product{"product_id"}}->{"business"} : '';
        my $prodgroup = (exists $data_mapping{$$product{"product_id"}} && exists $data_mapping{$$product{"product_id"}}->{"group"}) ? $data_mapping{$$product{"product_id"}}->{"group"} : '';

        my $staplesflag = (exists $channel_mapping{$$product{"product_id"}} && exists $channel_mapping{$$product{"product_id"}}->{"staples"}) ? $channel_mapping{$$product{"product_id"}}->{"staples"} : 'N';
        my $odflag = (exists $channel_mapping{$$product{"product_id"}} && exists $channel_mapping{$$product{"product_id"}}->{"officedepot"}) ? $channel_mapping{$$product{"product_id"}}->{"officedepot"} : 'N';
        my $amazonflag = (exists $channel_mapping{$$product{"product_id"}} && exists $channel_mapping{$$product{"product_id"}}->{"amazon"}) ? $channel_mapping{$$product{"product_id"}}->{"amazon"} : 'N';

        my $cost = (exists $cost_mapping{$$product{"product_id"}}) ? $cost_mapping{$$product{"product_id"}} : ((exists $averageCost{$$product{"product_id"}}) ? $averageCost{$$product{"product_id"}} : '');

        my $row = [
            $$product{"product_id"},
            $$product{"internal_name"},
            sprintf("%s%s", $url_domain, ($url_mapping{$url_path} || $url_path)),
            sprintf("http://actionenvelope.scene7.com/is/image/ActionEnvelope/%s%s", $$product{"product_id"}, "?wid=700&hei=700&fmt=png-alpha"),
            $longDesc,
            ($features{"size"}->{"description"} || ''),
            $brand,
            '',
            '',
            '',
            $manufacturer,
            '',
            '',
            '',
            '',
            '',
            '',
            '',
            '',
            '',
            '',
            '',
            '',
            '',
            '',
            $$product{"primary_product_category_id"},
            $category,
            '',
            '',
            '',
            '',
            '',
            '',
            '',
            '',
            '',
            ($features{"color"}->{"description"} || ''),
            ($features{"color_group"}->{"description"} || ''),
            ($features{"size"}->{"description"} || ''),
            '',
            ($features{"paper_weight"}->{"description"} || ''),
            '',
            ($features{"sealing_method"}->{"description"} || ''),
            ($features{"paper_texture"}->{"description"} || ''),
            ($features{"recycled_content"}->{"description"} || ''),
            '',
            '',
            '',
            $businesscat,
            $prodgroup,
            ($$product{"created_stamp"} || ''),
            $staplesflag,
            $odflag,
            $amazonflag,
            ((defined $$product{"on_sale"} && $$product{"on_sale"} eq "Y") || (defined $$product{"on_clearance"} && $$product{"on_clearance"} eq "Y")) ? "Y" : "N"
        ];
        $itemMaster->print(\*IM, $row);

        my $totalOrders = 0;
        my $grossUnits = 0;
        my $grossRevenue = 0;
        my $adjustedRevenue = 0;
        my $grossShipping = 0;
        my $transactionDate = '';
        my $plainRevenue = 0;
        my $printedRevenue = 0;

        $success &&= $getOrderData->execute($$product{"product_id"}, $tdt->ymd('-'));
        while(my $orders = $getOrderData->fetchrow_hashref()) {
            $success &&= $getAvgAdjustment->execute($$orders{"order_id"}, $$orders{"order_id"});
            my $avgDiscount = $getAvgAdjustment->fetchrow_array();
            if(!defined $avgDiscount) {
                $avgDiscount = 0;
            }

            if($transactionDate eq '') {
                $transactionDate = $$orders{"date"};
            }

            if($transactionDate eq $$orders{"date"}) {
                $totalOrders++;
                $grossUnits += $$orders{"quantity"};
                $grossRevenue += ((defined $$orders{"price"}) ? $$orders{"price"} : 0);
                $adjustedRevenue += (((defined $$orders{"price"}) ? $$orders{"price"} : 0) + $avgDiscount);
                $grossShipping += ((defined $$orders{"shipping"}) ? $$orders{"shipping"} : 0);

                if(defined $$orders{"artwork_source"}) {
                    $printedRevenue += ((defined $$orders{"price"}) ? $$orders{"price"} : 0);
                } else {
                    $plainRevenue += ((defined $$orders{"price"}) ? $$orders{"price"} : 0);
                }
            } else {
                $row = [
                    $$product{"product_id"},
                    ($transactionDate eq '') ? $tdt->ymd('-') : $transactionDate,
                    $grossUnits,
                    $totalOrders,
                    $grossRevenue,
                    $adjustedRevenue,
                    '',
                    '',
                    '',
                    '',
                    $grossShipping,
                    (exists $pageAndViews{($url_mapping{$url_path} || $url_path)}) ? $pageAndViews{($url_mapping{$url_path} || $url_path)} : 0, #pageviews
                    (exists $uniqueVisits{($url_mapping{$url_path} || $url_path)}) ? $uniqueVisits{($url_mapping{$url_path} || $url_path)} : 0, #uniqueviews
                    'website',
                    ($$product{"quantity_on_hand"} || '')
                ];
                $performanceData->print(\*PD, $row);

                $row = [
                    $$product{"product_id"},
                    ($transactionDate eq '') ? $tdt->ymd('-') : $transactionDate,
                    $grossUnits,
                    $totalOrders,
                    $grossRevenue,
                    $adjustedRevenue,
                    '',
                    '',
                    '',
                    '',
                    $grossShipping,
                    (exists $pageAndViews{($url_mapping{$url_path} || $url_path)}) ? $pageAndViews{($url_mapping{$url_path} || $url_path)} : 0, #pageviews
                    (exists $uniqueVisits{($url_mapping{$url_path} || $url_path)}) ? $uniqueVisits{($url_mapping{$url_path} || $url_path)} : 0, #uniqueviews
                    'website',
                    ($$product{"quantity_on_hand"} || ''),
                    $plainRevenue,
                    $printedRevenue
                ];
                $performanceData2->print(\*PD2, $row);

                #reset
                $transactionDate = $$orders{"date"};
                $totalOrders = 1;
                $grossUnits = $$orders{"quantity"};
                $grossRevenue = ((defined $$orders{"price"}) ? $$orders{"price"} : 0);
                $adjustedRevenue = (((defined $$orders{"price"}) ? $$orders{"price"} : 0) + $avgDiscount);
                $grossShipping = ((defined $$orders{"shipping"}) ? $$orders{"shipping"} : 0);

                if(defined $$orders{"artwork_source"}) {
                    $printedRevenue = ((defined $$orders{"price"}) ? $$orders{"price"} : 0);
                } else {
                    $plainRevenue = ((defined $$orders{"price"}) ? $$orders{"price"} : 0);
                }
            }
        }

        #add the last processed row
        $row = [
            $$product{"product_id"},
            ($transactionDate eq '') ? $tdt->ymd('-') : $transactionDate,
            $grossUnits,
            $totalOrders,
            $grossRevenue,
            $adjustedRevenue,
            '',
            '',
            '',
            '',
            $grossShipping,
            (exists $pageAndViews{($url_mapping{$url_path} || $url_path)}) ? $pageAndViews{($url_mapping{$url_path} || $url_path)} : 0, #pageviews
            (exists $uniqueVisits{($url_mapping{$url_path} || $url_path)}) ? $uniqueVisits{($url_mapping{$url_path} || $url_path)} : 0, #uniqueviews
            'website',
            ($$product{"quantity_on_hand"} || '')
        ];
        $performanceData->print(\*PD, $row);

        $row = [
            $$product{"product_id"},
            ($transactionDate eq '') ? $tdt->ymd('-') : $transactionDate,
            $grossUnits,
            $totalOrders,
            $grossRevenue,
            $adjustedRevenue,
            '',
            '',
            '',
            '',
            $grossShipping,
            (exists $pageAndViews{($url_mapping{$url_path} || $url_path)}) ? $pageAndViews{($url_mapping{$url_path} || $url_path)} : 0, #pageviews
            (exists $uniqueVisits{($url_mapping{$url_path} || $url_path)}) ? $uniqueVisits{($url_mapping{$url_path} || $url_path)} : 0, #uniqueviews
            'website',
            ($$product{"quantity_on_hand"} || ''),
            $plainRevenue,
            $printedRevenue
        ];
        $performanceData2->print(\*PD2, $row);

        my %quantityPrice;
        $success &&= $price_handle->execute($$product{"product_id"});
        my $priceCounter = 1;
        my $colors = -1;
        while(my $price = $price_handle->fetchrow_hashref()) {
            if($colors < 0) {
                $colors = $$price{"colors"};
            }

            if($colors != $$price{"colors"}) {
                next;
            }

            $quantityPrice{$priceCounter}{"quantity"} = $$price{"quantity"};
            $quantityPrice{$priceCounter}{"price"} = $$price{"price"};
            $quantityPrice{$priceCounter}{"originalPrice"} = (defined $$price{"original_price"}) ? $$price{"original_price"} : $$price{"price"};
            $priceCounter++;
        }
        $row = [
            $$product{"product_id"},
            'website',
            '',
            (exists $quantityPrice{"1"}) ? $quantityPrice{"1"}{"price"} : '',
            (exists $quantityPrice{"1"}) ? $quantityPrice{"1"}{"price"} : '',
            (exists $quantityPrice{"1"}) ? $quantityPrice{"1"}{"price"} : '',
            '',
            '',
            '',
            (exists $quantityPrice{"1"}) ? $quantityPrice{"1"}{"price"} : '',
            (exists $quantityPrice{"1"}) ? $quantityPrice{"1"}{"quantity"} : '',
            (exists $quantityPrice{"2"}) ? $quantityPrice{"2"}{"price"} : '',
            (exists $quantityPrice{"2"}) ? $quantityPrice{"2"}{"quantity"} : '',
            (exists $quantityPrice{"3"}) ? $quantityPrice{"3"}{"price"} : '',
            (exists $quantityPrice{"3"}) ? $quantityPrice{"3"}{"quantity"} : '',
            (exists $quantityPrice{"4"}) ? $quantityPrice{"4"}{"price"} : '',
            (exists $quantityPrice{"4"}) ? $quantityPrice{"4"}{"quantity"} : '',
            (exists $quantityPrice{"5"}) ? $quantityPrice{"5"}{"price"} : '',
            (exists $quantityPrice{"5"}) ? $quantityPrice{"5"}{"quantity"} : '',
            (exists $quantityPrice{"6"}) ? $quantityPrice{"6"}{"price"} : '',
            (exists $quantityPrice{"6"}) ? $quantityPrice{"6"}{"quantity"} : '',
            (exists $quantityPrice{"7"}) ? $quantityPrice{"7"}{"price"} : '',
            (exists $quantityPrice{"7"}) ? $quantityPrice{"7"}{"quantity"} : '',
            (exists $quantityPrice{"8"}) ? $quantityPrice{"8"}{"price"} : '',
            (exists $quantityPrice{"8"}) ? $quantityPrice{"8"}{"quantity"} : '',
            (exists $quantityPrice{"9"}) ? $quantityPrice{"9"}{"price"} : '',
            (exists $quantityPrice{"9"}) ? $quantityPrice{"9"}{"quantity"} : '',
            (exists $quantityPrice{"10"}) ? $quantityPrice{"10"}{"price"} : '',
            (exists $quantityPrice{"10"}) ? $quantityPrice{"10"}{"quantity"} : '',
            (exists $quantityPrice{"11"}) ? $quantityPrice{"11"}{"price"} : '',
            (exists $quantityPrice{"11"}) ? $quantityPrice{"11"}{"quantity"} : '',
            (exists $quantityPrice{"12"}) ? $quantityPrice{"12"}{"price"} : '',
            (exists $quantityPrice{"12"}) ? $quantityPrice{"12"}{"quantity"} : '',
            (exists $quantityPrice{"13"}) ? $quantityPrice{"13"}{"price"} : '',
            (exists $quantityPrice{"13"}) ? $quantityPrice{"13"}{"quantity"} : '',
            (exists $quantityPrice{"14"}) ? $quantityPrice{"14"}{"price"} : '',
            (exists $quantityPrice{"14"}) ? $quantityPrice{"14"}{"quantity"} : '',
            (exists $quantityPrice{"15"}) ? $quantityPrice{"15"}{"price"} : '',
            (exists $quantityPrice{"15"}) ? $quantityPrice{"15"}{"quantity"} : ''
        ];
        $priceData->print(\*PRD, $row);

        $row = [
            $$product{"product_id"},
            '2001-01-01 00:00:00.0',
            $cost,
            '',
            '',
            '',
            'website'
        ];

        $costData->print(\*CD, $row);
    }

    #get inventory
    my %inventoryData = ();

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

    if (!$httpResponse->is_error){
        my @perl_array = @{decode_json ($httpResponse->content())};
        foreach my $key ( @perl_array ){
            next if (!$key->{'itemid'} =~ m/.*\s+\:+\s+(.*)$/);
            $key->{'itemid'} =~ s/.*\s+\:+\s+(.*)$/$1/;
            $inventory->print(\*INV, [ $key->{'itemid'}, $key->{'qty'} ]);
        }
    } else {
        print "   Failed to get Inventory Data from Netsuite.";
        exit;
    }

    close IM or die "Couldn't close filehandle: $!";
    close PD or die "Couldn't close filehandle: $!";
    close PRD or die "Couldn't close filehandle: $!";
    close CD or die "Couldn't close filehandle: $!";
    close INV or die "Couldn't close filehandle: $!";

    sendData();
} else {
    my $PERFORMANCEHEADER = [
        'Sku',
        'gross_units',
        'gross_orders',
        'gross_revenue',
        'adjusted_revenue',
        'gross_cost',
        'gross_subsidized_cost',
        'gross_shipping_cost',
        'gross_variable_cost',
        'gross_shipping_charges',
        'page_views',
        'unique_visits',
        'channel_id',
        'inventory'
    ];

    my $PERFORMANCEHEADER2 = [
        'Sku',
        'gross_units',
        'gross_orders',
        'gross_revenue',
        'adjusted_revenue',
        'gross_cost',
        'gross_subsidized_cost',
        'gross_shipping_cost',
        'gross_variable_cost',
        'gross_shipping_charges',
        'page_views',
        'unique_visits',
        'channel_id',
        'inventory',
        'plain_revenue',
        'printed_revenue'
    ];

    my $performanceData = Text::CSV_XS->new ({ binary => 1, sep_char => '|', eol => $/, quote_char => '' });
    open(PD, '>/tmp/boomerang_range.csv') or die "Couldn't open filehandle: $!";
    $performanceData->print(\*PD, $PERFORMANCEHEADER);

    my $performanceData2 = Text::CSV_XS->new ({ binary => 1, sep_char => '|', eol => $/, quote_char => '' });
    open(PD2, '>/tmp/boomerang_range2.csv') or die "Couldn't open filehandle: $!";
    $performanceData2->print(\*PD2, $PERFORMANCEHEADER2);

    my $totalOrders = 0;
    my $grossUnits = 0;
    my $grossRevenue = 0;
    my $adjustedRevenue = 0;
    my $grossShipping = 0;
    my $plainRevenue = 0;
    my $printedRevenue = 0;

    my $success = 1;
    my $row = [];

    $success &&= $products_handle->execute();
    while (my $product = $products_handle->fetchrow_hashref()) {
        $success &&= $getOrderDataRange->execute($$product{"product_id"}, $ARGV[0], $ARGV[1]);
        while(my $orders = $getOrderDataRange->fetchrow_hashref()) {
            $success &&= $getAvgAdjustment->execute($$orders{"order_id"}, $$orders{"order_id"});
            my $avgDiscount = $getAvgAdjustment->fetchrow_array();
            if(!defined $avgDiscount) {
                $avgDiscount = 0;
            }

            $totalOrders++;
            $grossUnits += $$orders{"quantity"};
            $grossRevenue += ((defined $$orders{"price"}) ? $$orders{"price"} : 0);
            $adjustedRevenue += (((defined $$orders{"price"}) ? $$orders{"price"} : 0) + $avgDiscount);
            $grossShipping += ((defined $$orders{"shipping"}) ? $$orders{"shipping"} : 0);

            if(defined $$orders{"artwork_source"}) {
                $printedRevenue += ((defined $$orders{"price"}) ? $$orders{"price"} : 0);
            } else {
                $plainRevenue += ((defined $$orders{"price"}) ? $$orders{"price"} : 0);
            }
        }

        if($totalOrders > 0) {
            $row = [
                $$product{"product_id"},
                $grossUnits,
                $totalOrders,
                $grossRevenue,
                $adjustedRevenue,
                '',
                '',
                '',
                '',
                $grossShipping,
                0, #pageviews
                0, #uniqueviews
                'website',
                ''
            ];
            $performanceData->print(\*PD, $row);

            $row = [
                $$product{"product_id"},
                $grossUnits,
                $totalOrders,
                $grossRevenue,
                $adjustedRevenue,
                '',
                '',
                '',
                '',
                $grossShipping,
                0, #pageviews
                0, #uniqueviews
                'website',
                '',
                $plainRevenue,
                $printedRevenue
            ];
            $performanceData2->print(\*PD2, $row);
        }

        #reset
        $totalOrders = 0;
        $grossUnits = 0;
        $grossRevenue = 0;
        $adjustedRevenue = 0;
        $grossShipping = 0;
        $plainRevenue = 0;
        $printedRevenue = 0;
    }

    close PD or die "Couldn't close filehandle: $!";
}

$getAvgAdjustment->finish();
$getOrderData->finish();
$features_handle->finish();
$price_handle->finish();
$products_handle->finish();
$category_handle->finish();
$getOrderDataRange->finish();
$dbh->disconnect();

sub getAnalyticsPageViews {
	my ($startIndex, $maxRows, $endDate, $startDate) = @_;

	my $req = $analytics->new_request(
		ids         => 'ga:' . $profile_id,
		dimensions  => 'ga:pagePath',
		metrics     => 'ga:pageviews,ga:uniquePageviews',
		sort        => '-ga:pageviews',
		start_date  => $startDate,
		end_date    => $endDate,
		start_index => $startIndex,
		max_results => $maxRows
	);

	return $req;
}

sub sendData {
    my $sftp = Net::SFTP::Foreign->new('sftp.envelopes.rboomerang.com', user => 'sftp-env-use', password => 'SgA49ArEyaTGy5CK', autodie => 1, more => '-v') or die 'Cannot connect to some.host.name: $@';
    $sftp->setcwd('/prod/IN');
    $sftp->put('/tmp/boomerang_im.csv', 'boomerang_im_' . $tdt->ymd('') . '.csv');
    $sftp->put('/tmp/boomerang_pd.csv', 'boomerang_pd_' . $tdt->ymd('') . '.csv');
    $sftp->put('/tmp/boomerang_pd2.csv', 'boomerang_pd2_' . $tdt->ymd('') . '.csv');
    $sftp->put('/tmp/boomerang_prd.csv', 'boomerang_prd_' . $tdt->ymd('') . '.csv');
    $sftp->put('/tmp/boomerang_cd.csv', 'boomerang_cd_' . $tdt->ymd('') . '.csv');
    $sftp->put('/tmp/boomerang_inv.csv', 'boomerang_inv_' . $tdt->ymd('') . '.csv');
    $sftp->disconnect or die 'Error closing ftp connection: $!';
}