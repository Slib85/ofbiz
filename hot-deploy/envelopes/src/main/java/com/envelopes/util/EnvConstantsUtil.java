package com.envelopes.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.DecimalFormat;

import com.bigname.core.config.SiteConfig;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilProperties;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;

import com.bigname.navigation.NavigationHelper;

public class EnvConstantsUtil {
	public static final String module = EnvConstantsUtil.class.getName();

	public static List<String> HOLIDAYS;
	public static final boolean IS_PRODUCTION                   = EnvUtil.RuntimeEnvironment.getEnvironment() == EnvUtil.RuntimeEnvironment.PROD;
	public static final boolean IS_QA                   		= EnvUtil.RuntimeEnvironment.getEnvironment() == EnvUtil.RuntimeEnvironment.QA;
	public static final boolean IS_DEV                   		= EnvUtil.RuntimeEnvironment.getEnvironment() == EnvUtil.RuntimeEnvironment.DEV;
	public static final String ENV_PROD_STORE           		= "10000";
	public static final String ENV_RAYGUN_API_KEY       		= UtilProperties.getPropertyValue("envelopes", "raygun.api.key");
	public static final String SWITCH_KEY               		= UtilProperties.getPropertyValue("envelopes", "switch.api.key");
	public static final String REST_KEY                 		= UtilProperties.getPropertyValue("envelopes", "rest.api.key");
	public static final String FREE_SAMPLES             		= UtilProperties.getPropertyValue("envelopes", "freeSamples");
	public static final RoundingMode ENV_ROUNDING       		= RoundingMode.HALF_UP;
	public static final int ENV_SCALE                   		= 2;
	public static final int ENV_SCALE_P                 		= 3;
	public static final int ENV_SCALE_L                 		= 6;
	public static final int PROMO_CODE_LENGTH           		= 8;
	public static final int PASSWORD_LENGTH             		= 8;
	public static final DecimalFormat STANDARD_DECIMAL  		= new DecimalFormat("#,###.00");
	public static final DecimalFormat WHOLE_NUMBER      		= new DecimalFormat("#,###");
	public static final String RESPONSE_GIF             		= "image/gif";
	public static final String RESPONSE_PNG             		= "image/png";
	public static final String RESPONSE_PLAIN           		= "text/plain";
	public static final String RESPONSE_JSON            		= "application/json";
	public static final String RESPONSE_XML             		= "text/xml";
	public static final String RESPONSE_HTML            		= "text/html";
	public static final String RESPONSE_CSV             		= "text/csv";
	public static final String RESPONSE_BINARY          		= "application/octet-stream";
	public static final String ENV_CHAR_ENCODE          		= "UTF-8";
	public static final String OFBIZ_HOME               		= System.getProperty("ofbiz.home") + "/";
	public static final String FILE_PATH            			= UtilProperties.getPropertyValue("envelopes", "upload.path");
	public static final String UPLOAD_DIR               		= UtilProperties.getPropertyValue("envelopes", "envelopes.uploadDir");
	public static final String TEXEL_DESIGN_IMAGE_DIR           = UtilProperties.getPropertyValue("envelopes", "envelopes.texel.design.imageDir");
	public static final String SWITCH_UPLOAD_DIR        		= UtilProperties.getPropertyValue("envelopes", "envelopes.switchXML");
	public static final String PRODUCT_UPLOAD_DIR       		= UtilProperties.getPropertyValue("envelopes", "envelopes.productUpload");
	public static final String DIRECT_MAILING_DOC_UPLOAD_DIR	= UtilProperties.getPropertyValue("envelopes", "envelopes.directMailing.documentUpload");
	public static final String ADDRESSING_UPLOAD_DIR    		= UtilProperties.getPropertyValue("envelopes", "envelopes.addressingUpload");
	public static final String PRODUCT_LABEL_DIR    			= UtilProperties.getPropertyValue("envelopes", "envelopes.productLabels");
	public static final String ARTWORK_PREVIEW_FILE_DIR 		= UtilProperties.getPropertyValue("envelopes", "envelopes.artworkPreview");
	public static final String GOOD_TO_GO_BACKLOG_DIR 			= UtilProperties.getPropertyValue("envelopes", "envelopes.switch.backlog.goodToGo");
	public static final String CLIENT_SIDE_OBFUSCATOR_SECRET 	= UtilProperties.getPropertyValue("envelopes", "envelopes.client.secret");
	public static final int SAMPLE_EXPIRE_DAYS          		= 93;
	public static final double UPLOAD_MAX_FILES         		= Double.valueOf(UtilProperties.getPropertyValue("envelopes", "envelopes.uploadDir.maxFiles")).doubleValue();
	public static final boolean SHOW_INT_SHIP_METHODS   		= Boolean.valueOf(UtilProperties.getPropertyValue("envelopes", "shipment.show.international.methods")).booleanValue();
	public static final boolean SHOW_GENERIC_SHIP_NAMES 		= Boolean.valueOf(UtilProperties.getPropertyValue("envelopes", "shipment.show.generic.name")).booleanValue();
	public static final String UPS_REGEX                		= "\\b(1Z ?[0-9A-Z]{3} ?[0-9A-Z]{3} ?[0-9A-Z]{2} ?[0-9A-Z]{4} ?[0-9A-Z]{3} ?[0-9A-Z]|[\\dT]\\d\\d\\d ?\\d\\d\\d\\d ?\\d\\d\\d)\\b";
	public static final String USPS_REGEX               		= "\\b(91\\d\\d ?\\d\\d\\d\\d ?\\d\\d\\d\\d ?\\d\\d\\d\\d ?\\d\\d\\d\\d ?\\d\\d|91\\d\\d ?\\d\\d\\d\\d ?\\d\\d\\d\\d ?\\d\\d\\d\\d ?\\d\\d\\d\\d|94\\d\\d ?\\d\\d\\d\\d ?\\d\\d\\d\\d ?\\d\\d\\d\\d ?\\d\\d\\d\\d ?\\d\\d|94\\d\\d ?\\d\\d\\d\\d ?\\d\\d\\d\\d ?\\d\\d\\d\\d ?\\d\\d\\d\\d)\\b";
	public static final String FEDEX_REGEX              		= "\\b(?:[0-9]{12}|[0-9]{15})\\b";
	public static final String IPARCEL_REGEX            		= "^\\d+US$";
	public static final SimpleDateFormat NON_LEADING_MDY   		= new SimpleDateFormat("M/d/yyyy");
	public static final SimpleDateFormat MDY            		= new SimpleDateFormat("MM/dd/yyyy");
	public static final SimpleDateFormat MDYT           		= new SimpleDateFormat("MM-dd-yyyy h:mma");
	public static final SimpleDateFormat MDYTA          		= new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
	public static final SimpleDateFormat COMPACT_MDY	   		= new SimpleDateFormat("MMddyy");
	public static final SimpleDateFormat WMD            		= new SimpleDateFormat("EEE, MMMM d");
	public static final SimpleDateFormat WMDSHORT       		= new SimpleDateFormat("EEE, MMM d");
	public static final SimpleDateFormat UTC            		= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
	public static final SimpleDateFormat yyyyMMdd       		= new SimpleDateFormat("yyyyMMdd");
	public static final SimpleDateFormat yyyyMMddDash   		= new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat timeStamp      		= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	public static final SimpleDateFormat COMPACT_TIME      		= new SimpleDateFormat("HHmmss");
	public static final BigDecimal DIGITALQTY           		= new BigDecimal(UtilProperties.getPropertyValue("envelopes", "press.digitalOffset"));
	public static final String UPS_ACCOUNT              		= UtilProperties.getPropertyValue("envelopes", "ups.accountnumber");
	public static final String CUSTOM_PRODUCT           		= UtilProperties.getPropertyValue("envelopes", "envelopes.customProduct");
	public static final String SAMPLE_PRODUCT           		= UtilProperties.getPropertyValue("envelopes", "envelopes.sampleProduct");
	public static final String DIRECT_MAIL_PRODUCT           	= UtilProperties.getPropertyValue("envelopes", "envelopes.directMailProduct");
	public static final BigDecimal IPARCEL_FEE          		= new BigDecimal(UtilProperties.getPropertyValue("envelopes", "iparcel.fee"));
	public static final BigDecimal IPARCEL_ADMIN_FEE    		= new BigDecimal(UtilProperties.getPropertyValue("envelopes", "iparcel.admin.fee"));
	public static final Integer CART_LIMIT              		= new Integer(UtilProperties.getPropertyValue("envelopes", "cart.limit"));
	public static final String CART_ENDPOINT            		= UtilProperties.getPropertyValue("envelopes", "shared.cart");
	public static final Integer MIN_TO_STAGGER_SHIP_STATUS      = 7;
	public static Map<String, List<GenericValue>> navContent	= new HashMap<>();
	public static final String TAXABLE_STATES                   = "AL, AR, AZ, CA, CO, CT, FL, GA, HI, IA, ID, IL, IN, KS, KY, LA, MA, MD, ME, MI, MN, MO, NC, NE, NJ, NM, NV, NY, OH, OK, PA, RI, SC, SD, TN, TX, UT, VA, VT, WA, WI, WV, WY";
	public static final String FREE_SHIP_PROV_EXCL		 		= UtilProperties.getPropertyValue("envelopes", "promo.freeship.province.exclusions");
	public static final String FREE_SHIP_COUNTRY_EXCL 			= UtilProperties.getPropertyValue("envelopes", "promo.freeship.country.exclusions");
	public static final String US_TERRITORIES		 			= UtilProperties.getPropertyValue("envelopes", "shipment.us.territories");
	public static final String US_TERRITORIES_ZIP	 			= UtilProperties.getPropertyValue("envelopes", "shipment.us.territories.zip");
	public static final String BIGNAME_DUNS						= UtilProperties.getPropertyValue("envelopes", "duns.number");
	public static final boolean IS_UPS							= EnvUtil.ShippingCarrier.getShippingCarrier() == EnvUtil.ShippingCarrier.UPS;
	public static final boolean IS_FEDEX						= EnvUtil.ShippingCarrier.getShippingCarrier() == EnvUtil.ShippingCarrier.FEDEX;;
	public static Map<String, SiteConfig> SITE_CONFIGS  = new HashMap<>();

	/* This is a list of the SAMPLE coupon promo Ids $1-$5 */
	public static final Map<Integer, String> SAMPLE_COUPONS;

	static {
		SAMPLE_COUPONS = new HashMap<Integer, String>();
		SAMPLE_COUPONS.put(1, "9500");
		SAMPLE_COUPONS.put(2, "9501");
		SAMPLE_COUPONS.put(3, "9502");
		SAMPLE_COUPONS.put(4, "9503");
		SAMPLE_COUPONS.put(5, "9504");

		SITE_CONFIGS.put("envelopes", new SiteConfig("envelopes"));
		SITE_CONFIGS.put("ae", new SiteConfig("ae"));
		SITE_CONFIGS.put("bags", new SiteConfig("envelopes"));
		SITE_CONFIGS.put("bigname", new SiteConfig("envelopes"));
		SITE_CONFIGS.put("folders", new SiteConfig("folders"));

		try {
			String holidayDates = UtilProperties.getPropertyValue("envelopes", "shipment.holidays");
			if(UtilValidate.isNotEmpty(holidayDates)) {
				HOLIDAYS = Arrays.asList(holidayDates.split(","));
			}
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError("Error, could not open envelopes.properties.", module);
		}
	}

	public static List<GenericValue> getNavContent(Delegator delegator, String webSiteId) {
		if (UtilValidate.isEmpty(navContent.get(webSiteId))) {
			setNavContent(delegator, webSiteId);
		}

		return navContent.get(webSiteId);
	}

	private static void setNavContent(Delegator delegator, String webSiteId) {
		navContent.put(webSiteId, NavigationHelper.getMegaMenuData(delegator, webSiteId));
	}

	public static void clearNavContent(Delegator delegator, String webSiteId) {
		navContent.remove(webSiteId);
		setNavContent(delegator, webSiteId);
	}
}
