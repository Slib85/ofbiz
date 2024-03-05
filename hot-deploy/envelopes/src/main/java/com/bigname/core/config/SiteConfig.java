/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.bigname.core.config;

import java.util.*;

import com.envelopes.util.EnvUtil;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilProperties;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;

public class SiteConfig {
    public static final String module = SiteConfig.class.getName();

    private String webSiteId = null;
    private String salesChannelEnumId = null;
    private String properties = null;
    private GenericValue webSite = null;

    //BRONTO
    public String BRONTO_API_KEY = null;
    public String BRONTO_LIST_ID = null;
    public String BRONTO_FROM_EMAIL = null;
    public String BRONTO_FROM_NAME = null;
    public String BRONTO_REPLY_EMAIL = null;
    public String BRONTO_PREPRESS_REPLY_EMAIL;
    public String BRONTO_BCC_EMAIL = null;
    public String BRONTO_SMS_KEYWORD = null;
    public Map<String, String> BRONTO_MESSAGE_TYPES = new HashMap<>();
    public Map<String, String> BRONTO_FIELDS = new HashMap<>();
    public Map<String, String> BRONTO_CART_FIELDS  = new HashMap<>();
    public Map<String, String> BRONTO_CHECKOUT_FIELDS  = new HashMap<>();
    public Map<String, String> BRONTO_SMS_MESSAGE_TYPES = new HashMap<>();
    public Map<String, List<String>> BRONTO_CC_EMAIL_ADDRESSES = new HashMap<>();
    public Map<String, String> BRONTO_PRINT_TYPES = new HashMap<>();
    public List<String> BRONTO_PREPRESS_MESSAGE_TYPES = new ArrayList<>();

    //PAYMENTS & NETSUITE
    public Map<String, String> PAYMENT_SETTINGS = new HashMap<>();
    public Map<String, String> NETSUITE_SETTINGS = new HashMap<>();

    public SiteConfig(String webSiteId) {
        this.webSiteId = webSiteId;
        this.salesChannelEnumId = EnvUtil.getSalesChannelEnumId(webSiteId);
        this.properties = webSiteId;
        setBrontoSettings();
        setPaymentSettings();
        setNetsuiteSettings();
    }

    public String getWebSiteId() {
        return this.webSiteId;
    }

    public String getSalesChannelEnumId() {
        return this.salesChannelEnumId;
    }

    public String getPropertiesFile() {
        return this.properties;
    }

    private void setBrontoSettings() {
        try {
            this.BRONTO_API_KEY = UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.api.key");
            this.BRONTO_LIST_ID = UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.list.id");
            this.BRONTO_FROM_EMAIL = UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.from.email");
            this.BRONTO_FROM_NAME = UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.from.name");
            this.BRONTO_REPLY_EMAIL = UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.reply.email");
            this.BRONTO_PREPRESS_REPLY_EMAIL = UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.prepress.reply.email");
            this.BRONTO_BCC_EMAIL = UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.bcc.email");
            this.BRONTO_SMS_KEYWORD = UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.sms.keyword.id");

            this.BRONTO_MESSAGE_TYPES.put("orderConfirmation", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.order.confirmation.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("orderConfirmation2", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.order.confirmation2.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("confirmationquoteassignedto", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.order.confirmationquoteassignedto.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("temporaryPassword", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.temp.password.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("itemShipped", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.item.shipped.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("orderShipped", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.order.shipped.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("orderPickup", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.order.pickup.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("needArtwork", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.need.artwork.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("needNewArtwork", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.need.new.artwork.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("needArtworkReminder", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.need.new.artwork.reminder.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("proofUploaded", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.proof.uploaded.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("proofUploadedReminder", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.proof.uploaded.reminder.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("sampleCoupon", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.sample.coupon.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("tradeProRequest", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.tradePro.application.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("postNetRequest", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.postNet.application.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("allianceRequest", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.alliance.application.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("tradeProApproval", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.tradePro.approval.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("postNetApproval", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.postNet.approval.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("allianceApproval", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.alliance.approval.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("nonProfitApproval", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.nonprofit.approval.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("nonProfitRequest", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.nonprofit.application.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("forgotPassword", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.password.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("orderPending", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.order.pending.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("orderAuth", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.order.auth.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("quoteConfirmation", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.quote.confirmation.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("genericEmail", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.generic.email.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("yourOrderReview", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.review.order.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("emailDesign", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.email.design.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("plainReorder", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.plain.order.reminder.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("printReorder", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.print.order.reminder.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("bulkContact", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.email.bulk.contact.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("outsourceArtwork", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.email.orderoutsourceartwork.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("outsourceOrder", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.email.outsource.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("production", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.email.production.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("quoteRequest", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.email.quoterequest.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("quoteAssignment", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.email.quoteassignment.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("emailQuote", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.email.emailquote.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("estimatePrintCost", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.email.estimatePrintCost.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("quoteCompleted", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.email.quotecompleted.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("sampleRequest", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.email.samplerequest.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("customSamplePack", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.email.customSamplePack.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("emailThisQuote", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.email.emailThisQuote.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("commerceConciergeSubmission", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.email.commerceConciergeSubmission.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("requestAddressBook", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.email.requestaddressbook.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("reviewBusiness", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.email.reviewbusiness.messageId"));
            this.BRONTO_MESSAGE_TYPES.put("todaysTradeAnniversary", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.email.todaysTradeAnniversary.messageId"));

            this.BRONTO_FIELDS.put("ADDRESSLINE1", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.ADDRESSLINE1"));
            this.BRONTO_FIELDS.put("ADDRESSLINE2", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.ADDRESSLINE2"));
            this.BRONTO_FIELDS.put("ANNIVERSARYOFSIGNUP", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.ANNIVERSARYOFSIGNUP"));
            this.BRONTO_FIELDS.put("BIRTHDAY", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.BIRTHDAY"));
            this.BRONTO_FIELDS.put("BOOKLET_ENVELOPES", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.BOOKLET_ENVELOPES"));
            this.BRONTO_FIELDS.put("BOTH_PERSONAL_AND_BUSINES", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.BOTH_PERSONAL_AND_BUSINES"));
            this.BRONTO_FIELDS.put("BRAND_SPECIFIC_PAPER_LS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.BRAND_SPECIFIC_PAPER_LS"));
            this.BRONTO_FIELDS.put("BRIDE", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.BRIDE"));
            this.BRONTO_FIELDS.put("BRIGHT_ENVELOPES", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.BRIGHT_ENVELOPES"));
            this.BRONTO_FIELDS.put("BUS_CARDS_AND_STATIONERY", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.BUS_CARDS_AND_STATIONERY"));
            this.BRONTO_FIELDS.put("CANADIAN", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CANADIAN"));
            this.BRONTO_FIELDS.put("CARDSTOCK_LS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CARDSTOCK_LS"));
            this.BRONTO_FIELDS.put("CARTPRODIMAGE1", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CARTPRODIMAGE1"));
            this.BRONTO_FIELDS.put("CARTPRODIMAGE2", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CARTPRODIMAGE2"));
            this.BRONTO_FIELDS.put("CARTPRODIMAGE3", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CARTPRODIMAGE3"));
            this.BRONTO_FIELDS.put("CARTPRODIMAGE4", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CARTPRODIMAGE4"));
            this.BRONTO_FIELDS.put("CARTPRODIMAGE5", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CARTPRODIMAGE5"));
            this.BRONTO_FIELDS.put("CARTPRODNAME1", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CARTPRODNAME1"));
            this.BRONTO_FIELDS.put("CARTPRODNAME2", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CARTPRODNAME2"));
            this.BRONTO_FIELDS.put("CARTPRODNAME3", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CARTPRODNAME3"));
            this.BRONTO_FIELDS.put("CARTPRODNAME4", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CARTPRODNAME4"));
            this.BRONTO_FIELDS.put("CARTPRODNAME5", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CARTPRODNAME5"));
            this.BRONTO_FIELDS.put("CARTPRODPRICE1", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CARTPRODPRICE1"));
            this.BRONTO_FIELDS.put("CARTPRODPRICE2", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CARTPRODPRICE2"));
            this.BRONTO_FIELDS.put("CARTPRODPRICE3", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CARTPRODPRICE3"));
            this.BRONTO_FIELDS.put("CARTPRODPRICE4", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CARTPRODPRICE4"));
            this.BRONTO_FIELDS.put("CARTPRODPRICE5", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CARTPRODPRICE5"));
            this.BRONTO_FIELDS.put("CARTPRODQTY1", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CARTPRODQTY1"));
            this.BRONTO_FIELDS.put("CARTPRODQTY2", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CARTPRODQTY2"));
            this.BRONTO_FIELDS.put("CARTPRODQTY3", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CARTPRODQTY3"));
            this.BRONTO_FIELDS.put("CARTPRODQTY4", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CARTPRODQTY4"));
            this.BRONTO_FIELDS.put("CARTPRODQTY5", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CARTPRODQTY5"));
            this.BRONTO_FIELDS.put("CARTPRODSKU1", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CARTPRODSKU1"));
            this.BRONTO_FIELDS.put("CARTPRODSKU2", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CARTPRODSKU2"));
            this.BRONTO_FIELDS.put("CARTPRODSKU3", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CARTPRODSKU3"));
            this.BRONTO_FIELDS.put("CARTPRODSKU4", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CARTPRODSKU4"));
            this.BRONTO_FIELDS.put("CARTPRODSKU5", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CARTPRODSKU5"));
            this.BRONTO_FIELDS.put("CARTID", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CARTID"));
            this.BRONTO_FIELDS.put("CITY", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CITY"));
            this.BRONTO_FIELDS.put("CLASP_ENVELOPES", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CLASP_ENVELOPES"));
            this.BRONTO_FIELDS.put("CLASP_ENVELOPES_LS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CLASP_ENVELOPES_LS"));
            this.BRONTO_FIELDS.put("CLEAR_ENVELOPES", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CLEAR_ENVELOPES"));
            this.BRONTO_FIELDS.put("COLOR_LINED_ENVELOPES_LS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.COLOR_LINED_ENVELOPES_LS"));
            this.BRONTO_FIELDS.put("COMPANYNAME", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.COMPANYNAME"));
            this.BRONTO_FIELDS.put("COMPLETEDPURCHASE", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.COMPLETEDPURCHASE"));
            this.BRONTO_FIELDS.put("CU0C1D9108", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CU0C1D9108"));
            this.BRONTO_FIELDS.put("CU1D217F98", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CU1D217F98"));
            this.BRONTO_FIELDS.put("CU4067C3AA", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CU4067C3AA"));
            this.BRONTO_FIELDS.put("CU66970439", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CU66970439"));
            this.BRONTO_FIELDS.put("CUA0AAF609", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CUA0AAF609"));
            this.BRONTO_FIELDS.put("CUAD81C1E2", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CUAD81C1E2"));
            this.BRONTO_FIELDS.put("CURRENTDISCOUNT", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CURRENTDISCOUNT"));
            this.BRONTO_FIELDS.put("CUSTOMERTYPE", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.CUSTOMERTYPE"));
            this.BRONTO_FIELDS.put("DESIGNCART", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.DESIGNCART"));
            this.BRONTO_FIELDS.put("DESIGNCHECKOUT", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.DESIGNCHECKOUT"));
            this.BRONTO_FIELDS.put("DESIGNPURCHASE", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.DESIGNPURCHASE"));
            this.BRONTO_FIELDS.put("DESIGNSTART", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.DESIGNSTART"));
            this.BRONTO_FIELDS.put("DIRECTMAIL", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.DIRECTMAIL"));
            this.BRONTO_FIELDS.put("DISCOUNT_PROGRAM", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.DISCOUNT_PROGRAM"));
            this.BRONTO_FIELDS.put("DISCOUNTLEVEL", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.DISCOUNTLEVEL"));
            this.BRONTO_FIELDS.put("DIY_INVITES", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.DIY_INVITES"));
            this.BRONTO_FIELDS.put("DONTSEND_ABANDONMENTEMAIL", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.DONTSEND_ABANDONMENTEMAIL"));
            this.BRONTO_FIELDS.put("EARTHTONES_ENVELOPES", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.EARTHTONES_ENVELOPES"));
            this.BRONTO_FIELDS.put("EMAIL_FREQUENCY", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.EMAIL_FREQUENCY"));
            this.BRONTO_FIELDS.put("EMAILSOURCE", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.EMAILSOURCE"));
            this.BRONTO_FIELDS.put("EVERSAMPLEODRED", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.EVERSAMPLEODRED"));
            this.BRONTO_FIELDS.put("EVERUSEDPROMO", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.EVERUSEDPROMO"));
            this.BRONTO_FIELDS.put("FACEBOOK", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.FACEBOOK"));
            this.BRONTO_FIELDS.put("FASHION_COLORS_LS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.FASHION_COLORS_LS"));
            this.BRONTO_FIELDS.put("FIRST_NAME", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.FIRST_NAME"));
            this.BRONTO_FIELDS.put("FIRSTNAME", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.FIRSTNAME"));
            this.BRONTO_FIELDS.put("FOILLINED", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.FOILLINED"));
            this.BRONTO_FIELDS.put("FULL_BLEED_PRINTING", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.FULL_BLEED_PRINTING"));
            this.BRONTO_FIELDS.put("FULL_BLEED_PRINTING_LS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.FULL_BLEED_PRINTING_LS"));
            this.BRONTO_FIELDS.put("FUNDRAISING_NONPROFIT", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.FUNDRAISING_NONPROFIT"));
            this.BRONTO_FIELDS.put("GENDER", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.GENDER"));
            this.BRONTO_FIELDS.put("GOOGLE", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.GOOGLE"));
            this.BRONTO_FIELDS.put("GRATUITY_GIFTCARDENVELOPE", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.GRATUITY_GIFTCARDENVELOPE"));
            this.BRONTO_FIELDS.put("GREETING_CARD_PRODUCTS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.GREETING_CARD_PRODUCTS"));
            this.BRONTO_FIELDS.put("GROCERYBAG", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.GROCERYBAG"));
            this.BRONTO_FIELDS.put("HARD_TO_FIND_ENVELOPES_LS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.HARD_TO_FIND_ENVELOPES_LS"));
            this.BRONTO_FIELDS.put("INCHECKOUT", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.INCHECKOUT"));
            this.BRONTO_FIELDS.put("INDUSTRY", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.INDUSTRY"));
            this.BRONTO_FIELDS.put("INSTAGRAM", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.INSTAGRAM"));
            this.BRONTO_FIELDS.put("INTEREST_LOYALTY_DIS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.INTEREST_LOYALTY_DIS"));
            this.BRONTO_FIELDS.put("INTEREST_NON_PROFIT_DIS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.INTEREST_NON_PROFIT_DIS"));
            this.BRONTO_FIELDS.put("INTEREST_TRADE_DIS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.INTEREST_TRADE_DIS"));
            this.BRONTO_FIELDS.put("INTERESTED_LOYALTY_DIS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.INTERESTED_LOYALTY_DIS"));
            this.BRONTO_FIELDS.put("INTERESTED_NON_PROFIT_DIS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.INTERESTED_NON_PROFIT_DIS"));
            this.BRONTO_FIELDS.put("INTERESTED_TRADE_DIS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.INTERESTED_TRADE_DIS"));
            this.BRONTO_FIELDS.put("INTERESTEDCATEGORY", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.INTERESTEDCATEGORY"));
            this.BRONTO_FIELDS.put("INTERESTEDCOLOR", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.INTERESTEDCOLOR"));
            this.BRONTO_FIELDS.put("INTERESTEDSIZE", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.INTERESTEDSIZE"));
            this.BRONTO_FIELDS.put("INTERESTEDPRODUCT", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.INTERESTEDPRODUCT"));
            this.BRONTO_FIELDS.put("INVITATIONS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.INVITATIONS"));
            this.BRONTO_FIELDS.put("INVITATIONS_LS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.INVITATIONS_LS"));
            this.BRONTO_FIELDS.put("ITEMINCART", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.ITEMINCART"));
            this.BRONTO_FIELDS.put("IVORY_ENVELOPES", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.IVORY_ENVELOPES"));
            this.BRONTO_FIELDS.put("KRAFT", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.KRAFT"));
            this.BRONTO_FIELDS.put("KRAFT_LS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.KRAFT_LS"));
            this.BRONTO_FIELDS.put("LARGE_DOCUMENTENVELOPES", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.LARGE_DOCUMENTENVELOPES"));
            this.BRONTO_FIELDS.put("LAST_NAME", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.LAST_NAME"));
            this.BRONTO_FIELDS.put("LASTITEMPURCHASEIMAGE	", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.LASTITEMPURCHASEIMAGE	"));
            this.BRONTO_FIELDS.put("LASTITEMPURCHASETITLE	", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.LASTITEMPURCHASETITLE	"));
            this.BRONTO_FIELDS.put("LASTITEMPURCHASESIZECOLOR", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.LASTITEMPURCHASESIZECOLOR"));
            this.BRONTO_FIELDS.put("LASTITEMPURCHASEPRICE	", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.LASTITEMPURCHASEPRICE	"));
            this.BRONTO_FIELDS.put("LASTITEMPURCHASESALEPRICE", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.LASTITEMPURCHASESALEPRICE"));
            this.BRONTO_FIELDS.put("LASTNAME", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.LASTNAME"));
            this.BRONTO_FIELDS.put("LASTPURCHASEDATE", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.LASTPURCHASEDATE"));
            this.BRONTO_FIELDS.put("LASTQTYITEMS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.LASTQTYITEMS"));
            this.BRONTO_FIELDS.put("LASTQTYUNIQUESKUS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.LASTQTYUNIQUESKUS"));
            this.BRONTO_FIELDS.put("LASTSHIPCOST", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.LASTSHIPCOST"));
            this.BRONTO_FIELDS.put("LINED_ENVELOPES", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.LINED_ENVELOPES"));
            this.BRONTO_FIELDS.put("LINEN", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.LINEN"));
            this.BRONTO_FIELDS.put("LINEN_LS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.LINEN_LS"));
            this.BRONTO_FIELDS.put("LINKEDIN", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.LINKEDIN"));
            this.BRONTO_FIELDS.put("LOYALTYDISCOUNT", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.LOYALTYDISCOUNT"));
            this.BRONTO_FIELDS.put("LOYALTYPOINTBALANCE", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.LOYALTYPOINTBALANCE"));
            this.BRONTO_FIELDS.put("MAIL_MERGE_1", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.MAIL_MERGE_1"));
            this.BRONTO_FIELDS.put("MAILING_LABELS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.MAILING_LABELS"));
            this.BRONTO_FIELDS.put("METALLICS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.METALLICS"));
            this.BRONTO_FIELDS.put("METALLICS_LS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.METALLICS_LS"));
            this.BRONTO_FIELDS.put("MICROSEGMENT", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.MICROSEGMENT"));
            this.BRONTO_FIELDS.put("MINI_ENVELOPES", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.MINI_ENVELOPES"));
            this.BRONTO_FIELDS.put("MINI_ENVELOPES_LS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.MINI_ENVELOPES_LS"));
            this.BRONTO_FIELDS.put("MIRROR_ENVELOPES", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.MIRROR_ENVELOPES"));
            this.BRONTO_FIELDS.put("NATURAL_ENVELOPES", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.NATURAL_ENVELOPES"));
            this.BRONTO_FIELDS.put("NEXTDISCOUNT", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.NEXTDISCOUNT"));
            this.BRONTO_FIELDS.put("NONPROFITDISSAVING", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.NONPROFITDISSAVING"));
            this.BRONTO_FIELDS.put("NONPROFITTRADEDISCOUNT", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.NONPROFITTRADEDISCOUNT"));
            this.BRONTO_FIELDS.put("NONPROFITTRADEDISCSAVINGS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.NONPROFITTRADEDISCSAVINGS"));
            this.BRONTO_FIELDS.put("NUMBEROFORDERS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.NUMBEROFORDERS"));
            this.BRONTO_FIELDS.put("OPEN_END_ENVELOPES", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.OPEN_END_ENVELOPES"));
            this.BRONTO_FIELDS.put("ORDERAMT", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.ORDERAMT"));
            this.BRONTO_FIELDS.put("ORDERNUMBER", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.ORDERNUMBER"));
            this.BRONTO_FIELDS.put("OTHER_SOCIAL", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.OTHER_SOCIAL"));
            this.BRONTO_FIELDS.put("PAPER", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.PAPER"));
            this.BRONTO_FIELDS.put("PAPER_CARDSTOCK", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.PAPER_CARDSTOCK"));
            this.BRONTO_FIELDS.put("PAPER_LS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.PAPER_LS"));
            this.BRONTO_FIELDS.put("PAPERSPECSLUXLINED", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.PAPERSPECSLUXLINED"));
            this.BRONTO_FIELDS.put("PARCHMENT_ENVELOPES", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.PARCHMENT_ENVELOPES"));
            this.BRONTO_FIELDS.put("PARCHMENT_ENVELOPES_LS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.PARCHMENT_ENVELOPES_LS"));
            this.BRONTO_FIELDS.put("PARTYID", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.PARTYID"));
            this.BRONTO_FIELDS.put("PASTEL_ENVELOPES", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.PASTEL_ENVELOPES"));
            this.BRONTO_FIELDS.put("PERSONAL_PROJECTS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.PERSONAL_PROJECTS"));
            this.BRONTO_FIELDS.put("PERSONAL_STATIONERY", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.PERSONAL_STATIONERY"));
            this.BRONTO_FIELDS.put("PHONE_HOME", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.PHONE_HOME"));
            this.BRONTO_FIELDS.put("PHOTOGRAPHY_ENVELOPES", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.PHOTOGRAPHY_ENVELOPES"));
            this.BRONTO_FIELDS.put("PHOTOGREETING", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.PHOTOGREETING"));
            this.BRONTO_FIELDS.put("PINTEREST", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.PINTEREST"));
            this.BRONTO_FIELDS.put("PLAIN", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.PLAIN"));
            this.BRONTO_FIELDS.put("PLAIN_AND_PRINTED", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.PLAIN_AND_PRINTED"));
            this.BRONTO_FIELDS.put("PLASTIC_MAILERS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.PLASTIC_MAILERS"));
            this.BRONTO_FIELDS.put("PLASTIC_POLY_MAILERS_LS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.PLASTIC_POLY_MAILERS_LS"));
            this.BRONTO_FIELDS.put("POINTBALANCE", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.POINTBALANCE"));
            this.BRONTO_FIELDS.put("POINTSTONEXTDISCOUNT", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.POINTSTONEXTDISCOUNT"));
            this.BRONTO_FIELDS.put("POSTAGESAVER_PAPERSPECS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.POSTAGESAVER_PAPERSPECS"));
            this.BRONTO_FIELDS.put("POSTAL_CODE", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.POSTAL_CODE"));
            this.BRONTO_FIELDS.put("PREFERRED_TIMING", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.PREFERRED_TIMING"));
            this.BRONTO_FIELDS.put("PREFERREDMESSAGEFORMAT", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.PREFERREDMESSAGEFORMAT"));
            this.BRONTO_FIELDS.put("PRINTCUST", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.PRINTCUST"));
            this.BRONTO_FIELDS.put("PRINTED", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.PRINTED"));
            this.BRONTO_FIELDS.put("RECYCLED", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.RECYCLED"));
            this.BRONTO_FIELDS.put("RECYCLED_PRODUCTS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.RECYCLED_PRODUCTS"));
            this.BRONTO_FIELDS.put("REGULAR_ENVELOPES", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.REGULAR_ENVELOPES"));
            this.BRONTO_FIELDS.put("REMITTANCE_ENVELOPES_LS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.REMITTANCE_ENVELOPES_LS"));
            this.BRONTO_FIELDS.put("REVIEWHTML", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.REVIEWHTML"));
            this.BRONTO_FIELDS.put("ROLETYPE", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.ROLETYPE"));
            this.BRONTO_FIELDS.put("SALUTATION", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.SALUTATION"));
            this.BRONTO_FIELDS.put("SALUTATION_1", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.SALUTATION_1"));
            this.BRONTO_FIELDS.put("SAMPLECOUPON", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.SAMPLECOUPON"));
            this.BRONTO_FIELDS.put("SAMPLECOUPONAMOUNT", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.SAMPLECOUPONAMOUNT"));
            this.BRONTO_FIELDS.put("SAMPLECOUPONDATE", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.SAMPLECOUPONDATE"));
            this.BRONTO_FIELDS.put("SQUAREFLAP", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.SQUAREFLAP"));
            this.BRONTO_FIELDS.put("STATE_ABBREV", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.STATE_ABBREV"));
            this.BRONTO_FIELDS.put("STRICTLY_BUSINESS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.STRICTLY_BUSINESS"));
            this.BRONTO_FIELDS.put("SUPRESSWELCOME", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.SUPRESSWELCOME"));
            this.BRONTO_FIELDS.put("TIME_OF_DAY", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.TIME_OF_DAY"));
            this.BRONTO_FIELDS.put("TOTALCARTVALUE", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.TOTALCARTVALUE"));
            this.BRONTO_FIELDS.put("TOTALLOYALTYPOINTSUNUSED", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.TOTALLOYALTYPOINTSUNUSED"));
            this.BRONTO_FIELDS.put("TOTALLOYALTYPOINTSUSED", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.TOTALLOYALTYPOINTSUSED"));
            this.BRONTO_FIELDS.put("TOTALQTYITEMS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.TOTALQTYITEMS"));
            this.BRONTO_FIELDS.put("TOTALQTYUNIQUESKUS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.TOTALQTYUNIQUESKUS"));
            this.BRONTO_FIELDS.put("TOTALREVENUE", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.TOTALREVENUE"));
            this.BRONTO_FIELDS.put("TOTALSHIPCOST", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.TOTALSHIPCOST"));
            this.BRONTO_FIELDS.put("TRADEANDLOYALTY", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.TRADEANDLOYALTY"));
            this.BRONTO_FIELDS.put("TRADECOUPONCODE", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.TRADECOUPONCODE"));
            this.BRONTO_FIELDS.put("TRADEDISCOUNT", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.TRADEDISCOUNT"));
            this.BRONTO_FIELDS.put("TRADEDISCSAVINGS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.TRADEDISCSAVINGS"));
            this.BRONTO_FIELDS.put("TRADELOGIN", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.TRADELOGIN"));
            this.BRONTO_FIELDS.put("TRADELOYALTYANDCOUPON", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.TRADELOYALTYANDCOUPON"));
            this.BRONTO_FIELDS.put("TRADEPASSWORD", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.TRADEPASSWORD"));
            this.BRONTO_FIELDS.put("TRANSLUCENTS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.TRANSLUCENTS"));
            this.BRONTO_FIELDS.put("TRANSLUCENTS_LS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.TRANSLUCENTS_LS"));
            this.BRONTO_FIELDS.put("TWITTER", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.TWITTER"));
            this.BRONTO_FIELDS.put("USE", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.USE"));
            this.BRONTO_FIELDS.put("VARIABLE_ADDRESSING", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.VARIABLE_ADDRESSING"));
            this.BRONTO_FIELDS.put("WEDDING_DATE", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.WEDDING_DATE"));
            this.BRONTO_FIELDS.put("WEDDINGSHOP", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.WEDDINGSHOP"));
            this.BRONTO_FIELDS.put("WHITE_ENVELOPES", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.WHITE_ENVELOPES"));
            this.BRONTO_FIELDS.put("WHITE_INK_PRINTING", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.WHITE_INK_PRINTING"));
            this.BRONTO_FIELDS.put("WINDOW_ENVELOPES", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.WINDOW_ENVELOPES"));
            this.BRONTO_FIELDS.put("WINDOW_ENVELOPES_LS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.WINDOW_ENVELOPES_LS"));
            this.BRONTO_FIELDS.put("ZIP", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.ZIP"));
            this.BRONTO_FIELDS.put("PROOF_STATUS", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.PROOF_STATUS"));
            this.BRONTO_FIELDS.put("TRIGGER_EMAIL", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.field.TRIGGER_EMAIL"));

            this.BRONTO_CART_FIELDS.put("CARTPRODIMAGE1", this.BRONTO_FIELDS.get("CARTPRODIMAGE1"));
            this.BRONTO_CART_FIELDS.put("CARTPRODIMAGE2", this.BRONTO_FIELDS.get("CARTPRODIMAGE2"));
            this.BRONTO_CART_FIELDS.put("CARTPRODIMAGE3", this.BRONTO_FIELDS.get("CARTPRODIMAGE3"));
            this.BRONTO_CART_FIELDS.put("CARTPRODIMAGE4", this.BRONTO_FIELDS.get("CARTPRODIMAGE4"));
            this.BRONTO_CART_FIELDS.put("CARTPRODIMAGE5", this.BRONTO_FIELDS.get("CARTPRODIMAGE5"));
            this.BRONTO_CART_FIELDS.put("CARTPRODNAME1",  this.BRONTO_FIELDS.get("CARTPRODNAME1"));
            this.BRONTO_CART_FIELDS.put("CARTPRODNAME2",  this.BRONTO_FIELDS.get("CARTPRODNAME2"));
            this.BRONTO_CART_FIELDS.put("CARTPRODNAME3",  this.BRONTO_FIELDS.get("CARTPRODNAME3"));
            this.BRONTO_CART_FIELDS.put("CARTPRODNAME4",  this.BRONTO_FIELDS.get("CARTPRODNAME4"));
            this.BRONTO_CART_FIELDS.put("CARTPRODNAME5",  this.BRONTO_FIELDS.get("CARTPRODNAME5"));
            this.BRONTO_CART_FIELDS.put("CARTPRODPRICE1", this.BRONTO_FIELDS.get("CARTPRODPRICE1"));
            this.BRONTO_CART_FIELDS.put("CARTPRODPRICE2", this.BRONTO_FIELDS.get("CARTPRODPRICE2"));
            this.BRONTO_CART_FIELDS.put("CARTPRODPRICE3", this.BRONTO_FIELDS.get("CARTPRODPRICE3"));
            this.BRONTO_CART_FIELDS.put("CARTPRODPRICE4", this.BRONTO_FIELDS.get("CARTPRODPRICE4"));
            this.BRONTO_CART_FIELDS.put("CARTPRODPRICE5", this.BRONTO_FIELDS.get("CARTPRODPRICE5"));
            this.BRONTO_CART_FIELDS.put("CARTPRODQTY1",   this.BRONTO_FIELDS.get("CARTPRODQTY1"));
            this.BRONTO_CART_FIELDS.put("CARTPRODQTY2",   this.BRONTO_FIELDS.get("CARTPRODQTY2"));
            this.BRONTO_CART_FIELDS.put("CARTPRODQTY3",   this.BRONTO_FIELDS.get("CARTPRODQTY3"));
            this.BRONTO_CART_FIELDS.put("CARTPRODQTY4",   this.BRONTO_FIELDS.get("CARTPRODQTY4"));
            this.BRONTO_CART_FIELDS.put("CARTPRODQTY5",   this.BRONTO_FIELDS.get("CARTPRODQTY5"));
            this.BRONTO_CART_FIELDS.put("CARTPRODSKU1",   this.BRONTO_FIELDS.get("CARTPRODSKU1"));
            this.BRONTO_CART_FIELDS.put("CARTPRODSKU2",   this.BRONTO_FIELDS.get("CARTPRODSKU2"));
            this.BRONTO_CART_FIELDS.put("CARTPRODSKU3",   this.BRONTO_FIELDS.get("CARTPRODSKU3"));
            this.BRONTO_CART_FIELDS.put("CARTPRODSKU4",   this.BRONTO_FIELDS.get("CARTPRODSKU4"));
            this.BRONTO_CART_FIELDS.put("CARTPRODSKU5",   this.BRONTO_FIELDS.get("CARTPRODSKU5"));
            this.BRONTO_CART_FIELDS.put("CARTID",         this.BRONTO_FIELDS.get("CARTID"));

            this.BRONTO_CHECKOUT_FIELDS.put("LASTITEMPURCHASEIMAGE",		this.BRONTO_FIELDS.get("LASTITEMPURCHASEIMAGE"));
            this.BRONTO_CHECKOUT_FIELDS.put("LASTITEMPURCHASETITLE",		this.BRONTO_FIELDS.get("LASTITEMPURCHASETITLE"));
            this.BRONTO_CHECKOUT_FIELDS.put("LASTITEMPURCHASESIZECOLOR",	this.BRONTO_FIELDS.get("LASTITEMPURCHASESIZECOLOR"));
            this.BRONTO_CHECKOUT_FIELDS.put("LASTITEMPURCHASEPRICE",		this.BRONTO_FIELDS.get("LASTITEMPURCHASEPRICE"));
            this.BRONTO_CHECKOUT_FIELDS.put("LASTITEMPURCHASESALEPRICE",	this.BRONTO_FIELDS.get("LASTITEMPURCHASESALEPRICE"));

            this.BRONTO_PREPRESS_MESSAGE_TYPES.add("needArtwork");
            this.BRONTO_PREPRESS_MESSAGE_TYPES.add("needNewArtwork");
            this.BRONTO_PREPRESS_MESSAGE_TYPES.add("needArtworkReminder");
            this.BRONTO_PREPRESS_MESSAGE_TYPES.add("proofUploaded");
            this.BRONTO_PREPRESS_MESSAGE_TYPES.add("proofUploadedReminder");

            this.BRONTO_SMS_MESSAGE_TYPES.put("orderConfirmation", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.sms.order.confirmation.messageId"));
            this.BRONTO_SMS_MESSAGE_TYPES.put("orderShipped", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.sms.order.shipped.messageId"));
            this.BRONTO_SMS_MESSAGE_TYPES.put("proofUploaded", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".bronto.sms.proof.uploaded.messageId"));

            this.BRONTO_PRINT_TYPES.put("ART_NOT_RECEIVED", "Art not received.");
            this.BRONTO_PRINT_TYPES.put("ART_DESIGNED_ONLINE", "Designed online.");
            this.BRONTO_PRINT_TYPES.put("ART_UPLOADED", "Uploaded files.");
            this.BRONTO_PRINT_TYPES.put("ART_REUSED", "Reused from previous order.");
            this.BRONTO_PRINT_TYPES.put("ART_UPLOADED_LATER", "Will upload later.");
            this.BRONTO_PRINT_TYPES.put("SCENE7_ART_ONLINE", "Designed online.");

            this.BRONTO_CC_EMAIL_ADDRESSES.put("outsourceOrder", Arrays.asList("outsource@" + this.webSiteId + ".com"));
            this.BRONTO_CC_EMAIL_ADDRESSES.put("tradeProRequest", Arrays.asList("trade@" + this.webSiteId + ".com"));
            this.BRONTO_CC_EMAIL_ADDRESSES.put("postNetRequest", Arrays.asList("trade@" + this.webSiteId + ".com"));
            this.BRONTO_CC_EMAIL_ADDRESSES.put("allianceRequest", Arrays.asList("trade@" + this.webSiteId + ".com"));
            this.BRONTO_CC_EMAIL_ADDRESSES.put("nonProfitRequest", Arrays.asList("trade@" + this.webSiteId + ".com"));
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError("Error, could not open envelopes.properties.", module);
        }
    }

    private void setPaymentSettings() {
        this.PAYMENT_SETTINGS.put("mesId", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".netsuite.mes.id"));
    }

    private void setNetsuiteSettings() {
        this.NETSUITE_SETTINGS.put("brand", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".netsuite.brand"));
        this.NETSUITE_SETTINGS.put("formQuote", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".netsuite.quoteform"));
        this.NETSUITE_SETTINGS.put("formPrint", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".netsuite.printform"));
        this.NETSUITE_SETTINGS.put("formPlain", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".netsuite.plainform"));
        this.NETSUITE_SETTINGS.put("formSample", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".netsuite.sampleform"));
        this.NETSUITE_SETTINGS.put("department", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".netsuite.department"));
        this.NETSUITE_SETTINGS.put("estimate", UtilProperties.getPropertyValue(this.webSiteId, this.webSiteId + ".netsuite.estimate"));
    }

    public GenericValue getWebSite(Delegator delegator) {
        try {
            return (this.webSite == null) ? this.webSite = EntityQuery.use(delegator).from("WebSite").where("webSiteId", this.webSiteId).cache().queryOne() : this.webSite;
        } catch(Exception e) {
            EnvUtil.reportError(e);
        }

        return null;
    }

    public String webSiteName() {
        return this.webSite.getString("siteName");
    }

    public String webSiteUrl() {
        return this.webSite.getString("cookieDomain");
    }
}