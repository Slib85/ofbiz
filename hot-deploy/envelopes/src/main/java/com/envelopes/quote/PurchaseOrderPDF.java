package com.envelopes.quote;

import java.util.HashMap;
import java.util.List;

import com.bigname.quote.calculator.CalculatorHelper;
import com.envelopes.http.FileHelper;
import com.envelopes.order.OrderHelper;
import com.envelopes.product.ProductHelper;
import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.entity.util.EntityUtil;
import org.apache.ofbiz.order.order.OrderReadHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shoab on 7/14/17.
 */
public class PurchaseOrderPDF {
    public static final String module = PurchaseOrderPDF.class.getName();

    private String folder = FileHelper.cleanFolderPath(EnvConstantsUtil.OFBIZ_HOME + EnvConstantsUtil.UPLOAD_DIR + "/purchaseorders/");
    private Document pdf = new Document(PageSize.LETTER);
    private File file = null;
    private PdfWriter writer = null;

    private Delegator delegator = null;
    private Map<String, Object> data = null;
    private Map<String, Object> quoteSummary = null;
    private Map<String, Object> orderItemAttributes = null;
    private String orderId = null;
    private String orderItemSeqId = null;
    private String purchaseOrderId = null;
    private String shipping = null;
    private String proofApprovedDate = null;
    private Boolean isBlindShipment = null;
    private OrderReadHelper orh = null;
    private GenericValue userLogin = null;
    private GenericValue party = null;
    private GenericValue person = null;
    private GenericValue orderItem = null;
    private GenericValue vendorOrder = null;
    private List<GenericValue> attributes = null;

    private boolean success = false;

    public PurchaseOrderPDF(Delegator delegator, Map<String, Object> data) throws Exception {
        this.delegator = delegator;
        this.data = data;
        this.orderId = (String) data.get("orderId");
        this.orderItemSeqId = (String) data.get("orderItemSeqId");
        this.purchaseOrderId = (String) data.get("purchaseOrderId");

        this.orh = new OrderReadHelper(this.delegator, this.orderId);
        this.userLogin = EntityQuery.use(delegator).from("UserLogin").where("userLoginId", this.orh.getOrderHeader().getString("createdBy")).queryOne();
        this.shipping = this.orh.getShippingMethod("00001", false);
        this.party = EntityQuery.use(delegator).from("Party").where("partyId", this.userLogin.getString("partyId")).queryOne();
        this.person = this.party.getRelatedOne("Person", false);
        this.orderItem = EntityQuery.use(delegator).from("OrderItem").where("orderId", this.orderId, "orderItemSeqId", this.orderItemSeqId).queryOne();
        this.vendorOrder = EntityQuery.use(delegator).from("VendorOrder").where("orderId", this.orderId, "orderItemSeqId", this.orderItemSeqId).queryOne();
        this.attributes = EntityQuery.use(delegator).from("OrderItemAttribute").where("orderId", this.orderId, "orderItemSeqId", this.orderItemSeqId).queryList();
        this.orderItemAttributes = OrderHelper.getOrderItemAttributeMap(delegator, this.orderId, this.orderItemSeqId, true);

        //this.quoteSummary = CalculatorHelper.deserializedQuoteSummary(delegator, null, this.orderItem.getString("referenceQuoteItemSeqId"));
        this.proofApprovedDate = (EntityQuery.use(delegator).from("OrderStatus").where("orderId", this.orderId, "orderItemSeqId", this.orderItemSeqId).orderBy("statusDatetime DESC").queryFirst()).getString("statusDatetime");
        this.isBlindShipment = OrderHelper.isBlindShipment(delegator, (EntityQuery.use(delegator).from("OrderContactMech").where("orderId", this.orderId, "contactMechPurposeTypeId", "SHIPPING_LOCATION").queryFirst()).getString("contactMechId"));

        this.file = new File(folder + this.orderId + "-" + this.purchaseOrderId + ".pdf");
        try {
            this.writer = PdfWriter.getInstance(this.pdf, new FileOutputStream(this.file));
            this.success = true;
        } catch(Exception e) {
            Debug.logError(e, "Error creating pdf.", module);
        }
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void savePDF() throws DocumentException, IOException, GenericEntityException {
        this.pdf.open();

        addMetaData();
        createHeader();
        addSpacer(3);
        createCustomerInfo();
        addSpacer(1);
        createInstructions();
        createStyle();
        //createFoilSpecs();
        //createEmbossingSpecs();
        //createDebossedSpecs();
        //createCoatingSpecs();
        //createSlitSpecs();
        //createPocketSpecs();
        //createMiscSpecs();
        createFooter();

        this.pdf.close();
    }

    /**
     * Add meta data to the document
     */
    private void addMetaData() {
        this.pdf.addTitle("Folders.com Purchase Order");
        this.pdf.addSubject("Purchase Order");
        this.pdf.addKeywords("Purchase Order");
        this.pdf.addAuthor("Folders.com");
        this.pdf.addCreator("Folders.com");
    }

    //612,792 - SIZE OF LETTER

    /**
     * Create the header element with default values
     * @throws DocumentException
     * @throws IOException
     */
    private void createHeader() throws DocumentException, IOException {
        PdfContentByte canvas = this.writer.getDirectContentUnder();
        Rectangle rect = new Rectangle(10f, 702f, 602f, 782f);
        rect.setBackgroundColor(BaseColor.BLACK);
        canvas.rectangle(rect);

        Image img = Image.getInstance(EnvConstantsUtil.OFBIZ_HOME + "/hot-deploy/html/webapp/html/img/logo/foldersWhiteLarge.png");
        img.setAbsolutePosition(20f, 710f);
        img.scalePercent(20f);
        this.pdf.add(img);

        PdfPTable table = new PdfPTable(1);
        table.setTotalWidth(150f);

        PdfPCell cell = new PdfPCell();
        Paragraph contact = new Paragraph();
        contact.setFont(QuotePDF.getFont(QuotePDF.LATO_BOLD, 8, 0, BaseColor.WHITE));
        contact.add("5300 NEW HORIZONS BLVD.");
        contact.add(Chunk.NEWLINE);
        contact.add("AMITYVILLE, NY 11701");
        contact.add(Chunk.NEWLINE);
        contact.add(" ");
        contact.setLeading(0, 1.2f);
        cell.addElement(contact);
        cell.setUseBorderPadding(true);
        cell.setBorderWidth(0f);
        table.addCell(cell);

        PdfPCell cell2 = new PdfPCell();
        Paragraph contact2 = new Paragraph();
        contact2.setFont(QuotePDF.getFont(QuotePDF.LATO_REGULAR, 8, 0, BaseColor.GRAY));
        contact2.add("1.800.296.4321");
        contact2.add(Chunk.NEWLINE);
        contact2.add("SERVICE@FOLDERS.COM");
        contact2.setLeading(0, 1.2f);
        cell2.addElement(contact2);
        cell2.setUseBorderPadding(true);
        cell2.setBorderWidth(0f);
        table.addCell(cell2);

        table.writeSelectedRows(0, -1, this.pdf.right() - 110f, this.pdf.top() + 15f, canvas);
    }

    /**
     * Create the customer data
     * @throws DocumentException
     */
    private void createCustomerInfo() throws DocumentException, GenericEntityException {
        Paragraph header = new Paragraph("Folders.com Purchase Order #" + this.orderId + "-" + this.purchaseOrderId, QuotePDF.getFont(QuotePDF.LATO_BLACK, 18, 0, BaseColor.BLACK));
        header.setAlignment(Element.ALIGN_CENTER);
        header.setSpacingAfter(10f);
        this.pdf.add(header);

        addHR(1f);

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        PdfPCell cell1 = new PdfPCell();
        cell1.setBorder(0);
        GenericValue customer = OrderHelper.getShippingAddress(this.orh, this.delegator, this.orderId);
        Paragraph address = new Paragraph();
        address.setFont(QuotePDF.getFont(QuotePDF.LATO_REGULAR, 10, 0, BaseColor.BLACK));
        address.setLeading(0, 1.2f);
        //title
        Chunk shippingHeader = new Chunk("Shipping Address:");
        shippingHeader.setFont(QuotePDF.getFont(QuotePDF.LATO_BOLD, 10, 0, BaseColor.BLACK));
        address.add(shippingHeader);
        address.add(Chunk.NEWLINE);
        //name
        if(UtilValidate.isNotEmpty(customer.getString("toName"))) {
            address.add(customer.getString("toName"));
            address.add(Chunk.NEWLINE);
        }
        //companyName
        if(UtilValidate.isNotEmpty(customer.getString("companyName"))) {
            address.add(customer.getString("companyName"));
            address.add(Chunk.NEWLINE);
        }
        //address
        if(UtilValidate.isNotEmpty(customer.getString("address1"))) {
            address.add(customer.getString("address1"));
            address.add(Chunk.NEWLINE);
        }
        if(UtilValidate.isNotEmpty(customer.getString("address2"))) {
            address.add(customer.getString("address2"));
            address.add(Chunk.NEWLINE);
        }
        if(UtilValidate.isNotEmpty(customer.getString("city"))) {
            address.add(customer.getString("city") + ", ");
        }
        if(UtilValidate.isNotEmpty(customer.getString("stateProvinceGeoId"))) {
            address.add(customer.getString("stateProvinceGeoId") + " ");
        }
        if(UtilValidate.isNotEmpty(customer.getString("postalCode"))) {
            address.add(customer.getString("postalCode"));
        }
        cell1.addElement(address);
        table.addCell(cell1);

        PdfPCell cell2 = new PdfPCell();
        cell2.setBorder(0);
        Paragraph quoteKey = new Paragraph();
        quoteKey.setAlignment(Paragraph.ALIGN_RIGHT);
        quoteKey.setFont(QuotePDF.getFont(QuotePDF.LATO_BOLD, 10, 0, BaseColor.BLACK));
        quoteKey.setLeading(0, 1.2f);
        quoteKey.add("Order #:");
        quoteKey.add(Chunk.NEWLINE);
        quoteKey.add("Date:");
        quoteKey.add(Chunk.NEWLINE);
        quoteKey.add("Sales Consultant:");
        quoteKey.add(Chunk.NEWLINE);
        quoteKey.add("Phone:");
        quoteKey.add(Chunk.NEWLINE);
        quoteKey.add("Email:");

        cell2.addElement(quoteKey);
        table.addCell(cell2);

        String repEmail = "service@folders.com";
        String repName = "Customer Service";
        String repPhone = "800-296-4321";
        if(UtilValidate.isNotEmpty(this.party.getString("salesRep"))) {
            repEmail = this.party.getString("salesRep");
            repPhone = (String) ((Map<String, Object>) ((Map<String, Object>) QuoteHelper.foldersAssignedToUsers).get((QuoteHelper.foldersAssignedToUsers.containsKey(repEmail) ? repEmail : "default"))).get("phoneNumber");

            GenericValue repEntity = EntityQuery.use(delegator).from("UserLogin").where("userLoginId", repEmail).queryFirst();
            if(repEntity != null) {
                GenericValue repPerson = EntityQuery.use(delegator).from("Person").where("partyId", repEntity.getString("partyId")).queryOne();
                repName = repPerson.getString("firstName") + " " + repPerson.getString("lastName");
            }
        }


        PdfPCell cell3 = new PdfPCell();
        cell3.setBorder(0);
        Paragraph quoteValue = new Paragraph();
        quoteValue.setAlignment(Paragraph.ALIGN_RIGHT);
        quoteValue.setFont(QuotePDF.getFont(QuotePDF.LATO_REGULAR, 10, 0, BaseColor.BLACK));
        quoteValue.setLeading(0, 1.2f);
        quoteValue.add(this.orderId + "-" + this.purchaseOrderId);
        quoteValue.add(Chunk.NEWLINE);

        try {
            quoteValue.add(EnvConstantsUtil.MDY.format(EnvUtil.convertStringToTimestamp(this.proofApprovedDate)));
        } catch (Exception e) {
            quoteValue.add("---");
            Debug.logError(e, "Error Parsing String to Timestamp.", module);
        }

        quoteValue.add(Chunk.NEWLINE);
        quoteValue.add(repName);
        quoteValue.add(Chunk.NEWLINE);
        quoteValue.add(repPhone);
        quoteValue.add(Chunk.NEWLINE);
        quoteValue.add(repEmail);

        cell3.addElement(quoteValue);
        table.addCell(cell3);

        this.pdf.add(table);
    }

    /**
     * Create the footer element
     * @throws DocumentException
     */
    private void createFooter() throws DocumentException {
        PdfContentByte canvas = this.writer.getDirectContent();
        Rectangle rect = new Rectangle(10f, 10f, 602f, 30f);
        rect.setBackgroundColor(BaseColor.BLACK);
        canvas.rectangle(rect);

        ColumnText ct = new ColumnText(canvas);
        ct.setSimpleColumn(rect);

        Paragraph footer = new Paragraph("FOLDERS.COM PURCHASE ORDER", QuotePDF.getFont(QuotePDF.LATO_BOLD, 9, 0, BaseColor.WHITE));
        footer.setAlignment(Paragraph.ALIGN_CENTER);
        ct.addElement(footer);
        ct.go();
    }

    /**
     * Create the instructions from the comments created sring outsourcing for this document
     * @throws DocumentException
     */
    private void createInstructions() throws DocumentException {
        if (UtilValidate.isNotEmpty(this.shipping)) {
            if (this.isBlindShipment) {
                Paragraph blindShipPara = new Paragraph("BLINDSHIP", QuotePDF.getFont(QuotePDF.LATO_BOLD, 12, 0, BaseColor.BLACK));
                this.pdf.add(blindShipPara);
            }
            
            Paragraph para = new Paragraph("SHIP VIA " + this.shipping, QuotePDF.getFont(QuotePDF.LATO_BOLD, 9, 0, BaseColor.BLACK));
            para.setSpacingAfter(10f);
            this.pdf.add(para);
        }

        Paragraph para = new Paragraph("Instructions:", QuotePDF.getFont(QuotePDF.LATO_BOLD, 9, 0, BaseColor.BLACK));
        para.setSpacingAfter(10f);
        this.pdf.add(para);

        addHR(3f);

        Paragraph instructions = new Paragraph();
        instructions.setFont(QuotePDF.getFont(QuotePDF.LATO_REGULAR, 9, 0, BaseColor.BLACK));

        Map<String, String> instructionDataList = new HashMap<>();

        String additionalText, side1Text, side2Text, foilText, embossText, rightPocketText, centerPocketText, leftPocketText, reinforcedEdgeText, backboneText, closureText, gussetText, fileTabText, spineText;
        additionalText = side1Text = side2Text = foilText = embossText = rightPocketText = centerPocketText = leftPocketText = reinforcedEdgeText = backboneText = closureText = gussetText = fileTabText = spineText = "";

        Iterator it = this.orderItemAttributes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            String description = (String) ((Map<String, String>) (this.orderItemAttributes.get(pair.getKey()))).get("description");
            String value = (String) ((Map<String, String>) (this.orderItemAttributes.get(pair.getKey()))).get("value");

            Pattern p = Pattern.compile("^((?:side1|side2|foil|emboss|rightPocket|centerPocket|leftPocket|reinforcedEdge|backbone|closure|gusset|fileTab|spine)).*?((?:\\d+.*?|))$");
            Matcher m = p.matcher((String) pair.getKey());
            while (m.find()) {
                switch (m.group(1)) {
                    case "side1":
                        instructionDataList.put("Side 1:", (UtilValidate.isNotEmpty(instructionDataList.get("Side 1:")) ? instructionDataList.get("Side 1:") : "") + description + ": " + value + Chunk.NEWLINE);
                        break;
                    case "side2":
                        instructionDataList.put("Side 2:", (UtilValidate.isNotEmpty(instructionDataList.get("Side 2:")) ? instructionDataList.get("Side 2:") : "") + description + ": " + value + Chunk.NEWLINE);
                        break;
                    case "foil":
                        instructionDataList.put("Foil" + (UtilValidate.isNotEmpty(m.group(2)) ? " " + m.group(2) : "") + ":", (UtilValidate.isNotEmpty(instructionDataList.get("Foil" + (UtilValidate.isNotEmpty(m.group(2)) ? " " + m.group(2) : "") + ":")) ? instructionDataList.get("Foil" + (UtilValidate.isNotEmpty(m.group(2)) ? " " + m.group(2) : "") + ":") : "") + description + ": " + value + Chunk.NEWLINE);
                        break;
                    case "emboss":
                        instructionDataList.put("Embossing" + (UtilValidate.isNotEmpty(m.group(2)) ? " " + m.group(2) : "") + ":", (UtilValidate.isNotEmpty(instructionDataList.get("Embossing" + (UtilValidate.isNotEmpty(m.group(2)) ? " " + m.group(2) : "") + ":")) ? instructionDataList.get("Embossing" + (UtilValidate.isNotEmpty(m.group(2)) ? " " + m.group(2) : "") + ":") : "") + description + ": " + value + Chunk.NEWLINE);
                        break;
                    case "rightPocket":
                        instructionDataList.put("Right Pocket:", (UtilValidate.isNotEmpty(instructionDataList.get("Right Pocket:")) ? instructionDataList.get("Right Pocket:") : "") + description + ": " + value + Chunk.NEWLINE);
                        break;
                    case "centerPocket":
                        instructionDataList.put("Center Pocket:", (UtilValidate.isNotEmpty(instructionDataList.get("Center Pocket:")) ? instructionDataList.get("Center Pocket:") : "") + description + ": " + value + Chunk.NEWLINE);
                        break;
                    case "leftPocket":
                        instructionDataList.put("Left Pocket:", (UtilValidate.isNotEmpty(instructionDataList.get("Left Pocket:")) ? instructionDataList.get("Left Pocket:") : "") + description + ": " + value + Chunk.NEWLINE);
                        break;
                    case "reinforcedEdge":
                        instructionDataList.put("Reinforced Edge:", (UtilValidate.isNotEmpty(instructionDataList.get("Reinforced Edge:")) ? instructionDataList.get("Reinforced Edge:") : "") + description + ": " + value + Chunk.NEWLINE);
                        break;
                    case "backbone":
                        instructionDataList.put("Backbone:", (UtilValidate.isNotEmpty(instructionDataList.get("Backbone:")) ? instructionDataList.get("Backbone:") : "") + description + ": " + value + Chunk.NEWLINE);
                        break;
                    case "closure":
                        instructionDataList.put("Closure:", (UtilValidate.isNotEmpty(instructionDataList.get("Closure:")) ? instructionDataList.get("Closure:") : "") + description + ": " + value + Chunk.NEWLINE);
                        break;
                    case "gusset":
                        instructionDataList.put("Gusset:", (UtilValidate.isNotEmpty(instructionDataList.get("Gusset:")) ? instructionDataList.get("Gusset:") : "") + description + ": " + value + Chunk.NEWLINE);
                        break;
                    case "fileTab":
                        instructionDataList.put("File Tab:", (UtilValidate.isNotEmpty(instructionDataList.get("File Tab:")) ? instructionDataList.get("File Tab:") : "") + description + ": " + value + Chunk.NEWLINE);
                        break;
                    case "spine":
                        instructionDataList.put("Spine:", (UtilValidate.isNotEmpty(instructionDataList.get("Spine:")) ? instructionDataList.get("Spine:") : "") + description + ": " + value + Chunk.NEWLINE);
                        break;
                    default:
                        instructionDataList.put("Additional Instructions:", (UtilValidate.isNotEmpty(instructionDataList.get("Additional Instructions:")) ? instructionDataList.get("Additional Instructions:") : "") + description + ": " + value + Chunk.NEWLINE);
                        break;
                }
            }
        }

        for (Map.Entry<String, String> instructionData : instructionDataList.entrySet()) {
            this.pdf.add(new Paragraph(instructionData.getKey(), QuotePDF.getFont(QuotePDF.LATO_BOLD, 9, 4, BaseColor.BLACK)));
            Paragraph instructionDataPara = new Paragraph(instructionDataList.get(instructionData.getKey()), QuotePDF.getFont(QuotePDF.LATO_REGULAR, 9, 0, BaseColor.BLACK));
            instructionDataPara.setSpacingAfter(10f);
            this.pdf.add(instructionDataPara);
        }

        this.pdf.add(new Paragraph(UtilValidate.isNotEmpty(this.vendorOrder.getString("comments")) ? this.vendorOrder.getString("comments") : "No special instructions provided.", QuotePDF.getFont(QuotePDF.LATO_REGULAR, 9, 0, BaseColor.BLACK)));
    }

    /**
     * Create the style element for the document
     * @throws DocumentException
     * @throws GenericEntityException
     */
    private void createStyle() throws DocumentException, GenericEntityException {
        Paragraph para = new Paragraph("Style & Description:", QuotePDF.getFont(QuotePDF.LATO_BOLD, 9, 0, BaseColor.BLACK));
        para.setSpacingBefore(20f);
        para.setSpacingAfter(10f);
        this.pdf.add(para);

        addHR(3f);

        if(ProductHelper.isCustomSku(this.orderItem.getString("productId"))) {
            this.pdf.add(new Paragraph(this.orderItem.getString("itemDescription"), QuotePDF.getFont(QuotePDF.LATO_REGULAR, 9, 0, BaseColor.BLACK)));
        } else {
            GenericValue product = this.orderItem.getRelatedOne("Product", true);

            GenericValue colorWarehouseProduct = EntityQuery.use(delegator).from("ColorWarehouse").where("variantProductId", product.getString("productId")).queryOne();
            String productColorWeightTexture = "";
            if (UtilValidate.isNotEmpty(colorWarehouseProduct)) {
                productColorWeightTexture += UtilValidate.isNotEmpty(colorWarehouseProduct.get("colorDescription")) ? colorWarehouseProduct.get("colorDescription") + " " : "";
                productColorWeightTexture += UtilValidate.isNotEmpty(colorWarehouseProduct.get("paperWeightDescription")) ? colorWarehouseProduct.get("paperWeightDescription") + " " : "";
                productColorWeightTexture += UtilValidate.isNotEmpty(colorWarehouseProduct.get("paperTextureDescription")) ? colorWarehouseProduct.get("paperTextureDescription") : "";
            }
            
            this.pdf.add(new Paragraph(product.getString("productId") + " - " + product.getString("productName") + (!productColorWeightTexture.equals("") ? Chunk.NEWLINE + "Stock: " + productColorWeightTexture : ""), QuotePDF.getFont(QuotePDF.LATO_REGULAR, 9, 0, BaseColor.BLACK)));
        }

        this.pdf.add(new Paragraph("Quantity: " + EnvUtil.convertBigDecimal(this.orderItem.getBigDecimal("quantity"), true) , QuotePDF.getFont(QuotePDF.LATO_REGULAR, 9, 0, BaseColor.BLACK)));

        Float cost = Float.parseFloat((UtilValidate.isNotEmpty(this.vendorOrder.getString("cost")) ? this.vendorOrder.getString("cost") : "0.00"));

        this.pdf.add(new Paragraph("Cost: $" + cost, QuotePDF.getFont(QuotePDF.LATO_REGULAR, 9, 0, BaseColor.BLACK)));
    }

    /**
     * Create the foil specs for the document
     * @throws DocumentException
     * @throws GenericEntityException
     */
    private void createFoilSpecs() throws DocumentException, GenericEntityException {
        Map<String, Object> data = getFoilSpecs(this.attributes);
        if(UtilValidate.isNotEmpty(data)) {
            Paragraph para = new Paragraph("Foil Specs:", QuotePDF.getFont(QuotePDF.LATO_BOLD, 9, 0, BaseColor.BLACK));
            para.setSpacingBefore(20f);
            para.setSpacingAfter(10f);
            this.pdf.add(para);

            addHR(3f);

            Paragraph specList = new Paragraph();
            specList.setFont(QuotePDF.getFont(QuotePDF.LATO_REGULAR, 9, 0, BaseColor.BLACK));

            Map<String, Object> side = (Map<String, Object>) data.get("1");
            if(UtilValidate.isNotEmpty(side)) {
                specList.add("Side One:");
                specList.add(Chunk.NEWLINE);
                for (Map.Entry<String, Object> entry : side.entrySet()) {
                    List values = (List) entry.getValue();
                    specList.add("     Color: " + values.get(0) + ", Images: " + values.get(1) + ", Size: " + values.get(2));
                    specList.add(Chunk.NEWLINE);
                }
            }

            side = (Map<String, Object>) data.get("2");
            if(UtilValidate.isNotEmpty(side)) {
                specList.add("Side Two:");
                specList.add(Chunk.NEWLINE);
                for (Map.Entry<String, Object> entry : side.entrySet()) {
                    List values = (List) entry.getValue();
                    specList.add("     Color: " + values.get(0) + ", Images: " + values.get(1) + ", Size: " + values.get(2));
                    specList.add(Chunk.NEWLINE);
                }
            }

            this.pdf.add(specList);
        }
    }

    /**
     * Create embossing specs for the document
     * @throws DocumentException
     * @throws GenericEntityException
     */
    private void createEmbossingSpecs() throws DocumentException, GenericEntityException {
        Map<String, Object> data = getEmbossSpecs(this.attributes);
        if(UtilValidate.isNotEmpty(data)) {
            Paragraph para = new Paragraph("Embossing Specs:", QuotePDF.getFont(QuotePDF.LATO_BOLD, 9, 0, BaseColor.BLACK));
            para.setSpacingBefore(20f);
            para.setSpacingAfter(10f);
            this.pdf.add(para);

            addHR(3f);

            Paragraph specList = new Paragraph();
            specList.setFont(QuotePDF.getFont(QuotePDF.LATO_REGULAR, 9, 0, BaseColor.BLACK));

            Map<String, Object> side = (Map<String, Object>) data.get("1");
            if(UtilValidate.isNotEmpty(side)) {
                specList.add("Side One:");
                specList.add(Chunk.NEWLINE);
                for (Map.Entry<String, Object> entry : side.entrySet()) {
                    List values = (List) entry.getValue();
                    specList.add("     Level: " + values.get(0) + ", Images: " + values.get(1) + ", Size: " + values.get(2));
                    specList.add(Chunk.NEWLINE);
                }
            }

            side = (Map<String, Object>) data.get("2");
            if(UtilValidate.isNotEmpty(side)) {
                specList.add("Side Two:");
                specList.add(Chunk.NEWLINE);
                for (Map.Entry<String, Object> entry : side.entrySet()) {
                    List values = (List) entry.getValue();
                    specList.add("     Level: " + values.get(0) + ", Images: " + values.get(1) + ", Size: " + values.get(2));
                    specList.add(Chunk.NEWLINE);
                }
            }

            this.pdf.add(specList);
        }
    }

    /**
     * Create deboss specs for the document
     * @throws DocumentException
     * @throws GenericEntityException
     */
    private void createDebossedSpecs() throws DocumentException, GenericEntityException {
        Map<String, Object> data = getDebossSpecs(this.attributes);
        if(UtilValidate.isNotEmpty(data)) {
            Paragraph para = new Paragraph("Deboss Specs:", QuotePDF.getFont(QuotePDF.LATO_BOLD, 9, 0, BaseColor.BLACK));
            para.setSpacingBefore(20f);
            para.setSpacingAfter(10f);
            this.pdf.add(para);

            addHR(3f);

            Paragraph specList = new Paragraph();
            specList.setFont(QuotePDF.getFont(QuotePDF.LATO_REGULAR, 9, 0, BaseColor.BLACK));

            GenericValue side = (GenericValue) data.get("1");
            if(UtilValidate.isNotEmpty(side)) {
                specList.add("Side One: Deboss");
                specList.add(Chunk.NEWLINE);
            }

            side = (GenericValue) data.get("2");
            if(UtilValidate.isNotEmpty(side)) {
                specList.add("Side Two: Deboss");
                specList.add(Chunk.NEWLINE);
            }

            this.pdf.add(specList);
        }
    }

    /**
     * Create coating specs for the document
     * @throws DocumentException
     * @throws GenericEntityException
     */
    private void createCoatingSpecs() throws DocumentException, GenericEntityException {
        Map<String, Object> data = getCoatingSpecs(this.attributes);
        if(UtilValidate.isNotEmpty(data)) {
            Paragraph para = new Paragraph("Coating Specs:", QuotePDF.getFont(QuotePDF.LATO_BOLD, 9, 0, BaseColor.BLACK));
            para.setSpacingBefore(20f);
            para.setSpacingAfter(10f);
            this.pdf.add(para);

            addHR(3f);

            Paragraph specList = new Paragraph();
            specList.setFont(QuotePDF.getFont(QuotePDF.LATO_REGULAR, 9, 0, BaseColor.BLACK));

            String side = (String) data.get("1");
            if(UtilValidate.isNotEmpty(side)) {
                specList.add("Side One: " + side);
                specList.add(Chunk.NEWLINE);
            }

            side = (String) data.get("2");
            if(UtilValidate.isNotEmpty(side)) {
                specList.add("Side Two: " + side);
                specList.add(Chunk.NEWLINE);
            }

            this.pdf.add(specList);
        }
    }

    /**
     * Create the card slit data for the document
     * @throws DocumentException
     * @throws GenericEntityException
     */
    private void createSlitSpecs() throws DocumentException, GenericEntityException {
        Map<String, Object> data = getCardSlit(this.attributes);
        if(UtilValidate.isNotEmpty(data)) {
            Paragraph para = new Paragraph("Card Slit Options:", QuotePDF.getFont(QuotePDF.LATO_BOLD, 9, 0, BaseColor.BLACK));
            para.setSpacingBefore(20f);
            para.setSpacingAfter(10f);
            this.pdf.add(para);

            addHR(3f);

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);

            for (Map.Entry<String, Object> entry : data.entrySet()) {
                List values = (List) entry.getValue();

                PdfPCell cell = new PdfPCell();
                cell.addElement(new Paragraph(entry.getKey() + ":", QuotePDF.getFont(QuotePDF.LATO_BOLD, 8, 0, BaseColor.BLACK)));
                cell.setUseBorderPadding(true);
                cell.setBorderWidth(0f);
                table.addCell(cell);

                PdfPCell cell2 = new PdfPCell();
                cell2.addElement(new Paragraph((String) values.get(0), QuotePDF.getFont(QuotePDF.LATO_REGULAR, 8, 0, BaseColor.BLACK)));
                cell2.setUseBorderPadding(true);
                cell2.setBorderWidth(0f);
                table.addCell(cell2);

                PdfPCell cell3 = new PdfPCell(); //empty spacer
                cell3.setUseBorderPadding(true);
                cell3.setBorderWidth(0f);
                table.addCell(cell3);

                PdfPCell cell4 = new PdfPCell();
                cell4.addElement(new Paragraph("Position:", QuotePDF.getFont(QuotePDF.LATO_BOLD, 8, 0, BaseColor.BLACK)));
                cell4.setUseBorderPadding(true);
                cell4.setBorderWidth(0f);
                table.addCell(cell4);

                PdfPCell cell5 = new PdfPCell();
                cell5.addElement(new Paragraph((String) values.get(1), QuotePDF.getFont(QuotePDF.LATO_REGULAR, 8, 0, BaseColor.BLACK)));
                cell5.setUseBorderPadding(true);
                cell5.setBorderWidth(0f);
                table.addCell(cell5);
            }

            this.pdf.add(table);
        }
    }

    /**
     * Create the card pocket data for the document
     * @throws DocumentException
     * @throws GenericEntityException
     */
    private void createPocketSpecs() throws DocumentException, GenericEntityException {
        Map<String, Object> data = getPocketStyles(this.attributes);
        if(UtilValidate.isNotEmpty(data)) {
            Paragraph para = new Paragraph("Pocket Options:", QuotePDF.getFont(QuotePDF.LATO_BOLD, 9, 0, BaseColor.BLACK));
            para.setSpacingBefore(20f);
            para.setSpacingAfter(10f);
            this.pdf.add(para);

            addHR(3f);

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);

            for (Map.Entry<String, Object> entry : data.entrySet()) {
                List values = (List) entry.getValue();

                PdfPCell cell = new PdfPCell();
                cell.addElement(new Paragraph(entry.getKey() + ":", QuotePDF.getFont(QuotePDF.LATO_BOLD, 8, 0, BaseColor.BLACK)));
                cell.setUseBorderPadding(true);
                cell.setBorderWidth(0f);
                table.addCell(cell);

                PdfPCell cell2 = new PdfPCell();
                cell2.addElement(new Paragraph((String) values.get(0), QuotePDF.getFont(QuotePDF.LATO_REGULAR, 8, 0, BaseColor.BLACK)));
                cell2.setUseBorderPadding(true);
                cell2.setBorderWidth(0f);
                table.addCell(cell2);

                PdfPCell cell3 = new PdfPCell(); //empty spacer
                cell3.setUseBorderPadding(true);
                cell3.setBorderWidth(0f);
                table.addCell(cell3);

                PdfPCell cell4 = new PdfPCell();
                cell4.addElement(new Paragraph("Capacity:", QuotePDF.getFont(QuotePDF.LATO_BOLD, 8, 0, BaseColor.BLACK)));
                cell4.setUseBorderPadding(true);
                cell4.setBorderWidth(0f);
                table.addCell(cell4);

                PdfPCell cell5 = new PdfPCell();
                cell5.addElement(new Paragraph((String) values.get(1), QuotePDF.getFont(QuotePDF.LATO_REGULAR, 8, 0, BaseColor.BLACK)));
                cell5.setUseBorderPadding(true);
                cell5.setBorderWidth(0f);
                table.addCell(cell5);
            }

            this.pdf.add(table);
        }
    }

    /**
     * Create the all misc data for the document
     * @throws DocumentException
     * @throws GenericEntityException
     */
    private void createMiscSpecs() throws DocumentException, GenericEntityException {
        Map<String, Object> data = getMiscStyles(this.attributes);
        if(UtilValidate.isNotEmpty(data)) {
            Paragraph para = new Paragraph("Other Options:", QuotePDF.getFont(QuotePDF.LATO_BOLD, 9, 0, BaseColor.BLACK));
            para.setSpacingBefore(20f);
            para.setSpacingAfter(10f);
            this.pdf.add(para);

            addHR(3f);

            Paragraph specList = new Paragraph();
            specList.setFont(QuotePDF.getFont(QuotePDF.LATO_REGULAR, 9, 0, BaseColor.BLACK));

            for (Map.Entry<String, Object> entry : data.entrySet()) {
                specList.add(entry.getKey() + ": " + entry.getValue());
                specList.add(Chunk.NEWLINE);
            }

            this.pdf.add(specList);
        }
    }

    /**
     * Add a line separator between elements in a document
     * @param thickness
     * @throws DocumentException
     */
    private void addHR(float thickness) throws DocumentException {
        LineSeparator ls = new LineSeparator();
        ls.setLineWidth(thickness);

        this.pdf.add(ls);
    }

    /**
     * Add a spacer between elements in a document
     * @param numberOfLines
     * @throws DocumentException
     */
    private void addSpacer(int numberOfLines) throws DocumentException {
        Paragraph spacer = new Paragraph();
        spacer.setAlignment(Element.ALIGN_CENTER);

        for(int i = 1; i <= numberOfLines; i++) {
            spacer.add(Chunk.NEWLINE);
        }
        this.pdf.add(spacer);
    }

    /**
     * Get the foil related information for a order item
     * @param orderItemAttributes
     * @return
     * @throws GenericEntityException
     */
    public static Map<String, Object> getFoilSpecs(List<GenericValue> orderItemAttributes) throws GenericEntityException  {
        Map<String, Object> foilData = new HashMap<>();

        GenericValue side1Foil = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "side1Foil", "attrValue", "true")));
        GenericValue side2Foil = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "side2Foil", "attrValue", "true")));

        if(side1Foil != null) {
            Map<String, Object> sideData = new HashMap<>();
            //query generic data to get the number of foils
            long numberOfPossibleFoils = EntityQuery.use(side1Foil.getDelegator()).from("ProductAttribute").where(EntityCondition.makeCondition("attrName", EntityOperator.LIKE, "foilImages%")).cache().queryCount();
            //number of foils
            for(int i = 1; i <= numberOfPossibleFoils; i++) {
                GenericValue image = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "foilImages" + i)));
                if(image != null) {
                    GenericValue color = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "foilColor" + i)));
                    if(color.getString("attrValue").equalsIgnoreCase("Other")) {
                        color = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "foilColorOther" + i)));
                    }
                    GenericValue size = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "foilSize" + i)));
                    if(size.getString("attrValue").equalsIgnoreCase("Other")) {
                        size = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "foilSizeOther" + i)));
                    }
                    sideData.put(String.valueOf(i), UtilMisc.toList(color.getString("attrValue"), image.getString("attrValue"), size.getString("attrValue")));
                }
            }

            foilData.put("1", sideData);
        }

        if(side2Foil != null) {
            Map<String, Object> sideData = new HashMap<>();
            //query generic data to get the number of foils
            long numberOfPossibleFoils = EntityQuery.use(side1Foil.getDelegator()).from("ProductAttribute").where(EntityCondition.makeCondition("attrName", EntityOperator.LIKE, "foilImages%")).cache().queryCount();
            //number of foils
            for(int i = 1; i <= numberOfPossibleFoils; i++) {
                GenericValue image = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "foilImages" + i)));
                if(image != null) {
                    GenericValue color = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "foilColor" + i)));
                    if(color.getString("attrValue").equalsIgnoreCase("Other")) {
                        color = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "foilColorOther" + i)));
                    }
                    GenericValue size = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "foilSize" + i)));
                    if(size.getString("attrValue").equalsIgnoreCase("Other")) {
                        size = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "foilSizeOther" + i)));
                    }
                    sideData.put(String.valueOf(i), UtilMisc.toList(color.getString("attrValue"), image.getString("attrValue"), size.getString("attrValue")));
                }
            }

            foilData.put("2", sideData);
        }

        return foilData;
    }

    /**
     * Get the embossing related information for a order item
     * @param orderItemAttributes
     * @return
     * @throws GenericEntityException
     */
    public static Map<String, Object> getEmbossSpecs(List<GenericValue> orderItemAttributes) throws GenericEntityException  {
        Map<String, Object> embossData = new HashMap<>();

        GenericValue side1Emboss = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "side1Emboss", "attrValue", "true")));
        GenericValue side2Emboss = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "side2Emboss", "attrValue", "true")));

        if(side1Emboss != null) {
            Map<String, Object> sideData = new HashMap<>();
            //query generic data to get the number of foils
            long numberOfPossibleEmbosses = EntityQuery.use(side1Emboss.getDelegator()).from("ProductAttribute").where(EntityCondition.makeCondition("attrName", EntityOperator.LIKE, "embossImages%")).cache().queryCount();
            //number of foils
            for(int i = 1; i <= numberOfPossibleEmbosses; i++) {
                GenericValue image = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "embossImages" + i)));
                if(image != null) {
                    GenericValue level = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "embossLevel" + i)));
                    if(level.getString("attrValue").equalsIgnoreCase("Other")) {
                        level = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "embossLevelOther" + i)));
                    }
                    GenericValue size = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "embossSize" + i)));
                    if(size.getString("attrValue").equalsIgnoreCase("Other")) {
                        size = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "embossSizeOther" + i)));
                    }
                    sideData.put(String.valueOf(i), UtilMisc.toList(level.getString("attrValue"), image.getString("attrValue"), size.getString("attrValue")));
                }
            }

            embossData.put("1", sideData);
        }

        if(side2Emboss != null) {
            Map<String, Object> sideData = new HashMap<>();
            //query generic data to get the number of foils
            long numberOfPossibleEmbosses = EntityQuery.use(side2Emboss.getDelegator()).from("ProductAttribute").where(EntityCondition.makeCondition("attrName", EntityOperator.LIKE, "embossImages%")).cache().queryCount();
            //number of foils
            for(int i = 1; i <= numberOfPossibleEmbosses; i++) {
                GenericValue image = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "embossImages" + i)));
                if(image != null) {
                    GenericValue level = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "embossLevel" + i)));
                    if(level.getString("attrValue").equalsIgnoreCase("Other")) {
                        level = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "embossLevelOther" + i)));
                    }
                    GenericValue size = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "embossSize" + i)));
                    if(size.getString("attrValue").equalsIgnoreCase("Other")) {
                        size = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "embossSizeOther" + i)));
                    }
                    sideData.put(String.valueOf(i), UtilMisc.toList(level.getString("attrValue"), image.getString("attrValue"), size.getString("attrValue")));
                }
            }

            embossData.put("2", sideData);
        }

        return embossData;
    }

    /**
     * Get the debossing related information for a order item
     * @param orderItemAttributes
     * @return
     * @throws GenericEntityException
     */
    public static Map<String, Object> getDebossSpecs(List<GenericValue> orderItemAttributes) throws GenericEntityException {
        Map<String, Object> debossData = new HashMap<>();

        GenericValue side1Deboss = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "side1Deboss", "attrValue", "true")));
        GenericValue side2Deboss = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "side2Deboss", "attrValue", "true")));

        if(side1Deboss != null) {
            debossData.put("1", side1Deboss);
        }

        if(side2Deboss != null) {
            debossData.put("2", side2Deboss);
        }

        return debossData;
    }

    /**
     * Get the coating related information for a order item
     * @param orderItemAttributes
     * @return
     * @throws GenericEntityException
     */
    public static Map<String, Object> getCoatingSpecs(List<GenericValue> orderItemAttributes) throws GenericEntityException {
        Map<String, Object> coatingData = new HashMap<>();

        GenericValue side1Coating = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "side1Coating")));
        GenericValue side2Coating = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "side2Coating")));

        if(side1Coating != null) {
            coatingData.put("1", side1Coating.getString("attrValue"));
        }

        if(side2Coating != null) {
            coatingData.put("2", side2Coating.getString("attrValue"));
        }

        return coatingData;
    }

    /**
     * Get pocket style and capacity
     * @param orderItemAttributes
     * @return
     * @throws GenericEntityException
     */
    public static Map<String, Object> getPocketStyles(List<GenericValue> orderItemAttributes) throws GenericEntityException {
        Map<String, Object> data = new HashMap<>();

        GenericValue leftPocketStyle = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "leftPocketStyle")));
        if(leftPocketStyle != null) {
            GenericValue leftPocketCapacity = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "leftPocketCapacity")));
            data.put("Left Pocket Style", UtilMisc.toList(leftPocketStyle.getString("attrValue"), leftPocketCapacity.getString("attrValue")));
        }

        GenericValue centerPocketStyle = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "centerPocketStyle")));
        if(centerPocketStyle != null) {
            GenericValue centerPocketCapacity = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "centerPocketCapacity")));
            data.put("Center Pocket Style", UtilMisc.toList(centerPocketStyle.getString("attrValue"), centerPocketCapacity.getString("attrValue")));
        }

        GenericValue rightPocketStyle = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "rightPocketStyle")));
        if(rightPocketStyle != null) {
            GenericValue rightPocketCapacity = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "rightPocketCapacity")));
            data.put("Left Pocket Style", UtilMisc.toList(rightPocketStyle.getString("attrValue"), rightPocketCapacity.getString("attrValue")));
        }

        return data;
    }

    /**
     * Get misc data
     * @param orderItemAttributes
     * @return
     * @throws GenericEntityException
     */
    public static Map<String, Object> getMiscStyles(List<GenericValue> orderItemAttributes) throws GenericEntityException {
        Map<String, Object> data = new HashMap<>();

        GenericValue reinforcedEdgeType = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "reinforcedEdgeType")));
        if(reinforcedEdgeType != null) {
            data.put("Reinforced Edges", reinforcedEdgeType.getString("attrValue"));
        }

        GenericValue backboneSize = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "backboneSize")));
        if(backboneSize != null) {
            data.put("Backbone", backboneSize.getString("attrValue"));
        }

        GenericValue closureColor = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "closureColor")));
        if(closureColor != null) {
            data.put("Closure", closureColor.getString("attrValue"));
        }

        GenericValue gussetType = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "gussetType")));
        if(gussetType != null) {
            data.put("Gusset", gussetType.getString("attrValue"));
        }

        GenericValue fileTabType = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "fileTabType")));
        if(fileTabType != null) {
            data.put("File Tab", fileTabType.getString("attrValue"));
        }

        GenericValue spineColor = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "spineColor")));
        if(spineColor != null) {
            data.put("Spine Attachment", spineColor.getString("attrValue"));
        }

        return data;
    }

    /**
     * Get card slit type and position
     * @param orderItemAttributes
     * @return
     * @throws GenericEntityException
     */
    public static Map<String, Object> getCardSlit(List<GenericValue> orderItemAttributes) throws GenericEntityException {
        Map<String, Object> data = new HashMap<>();

        GenericValue leftPocket = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "leftPocket")));
        if(leftPocket != null && leftPocket.getString("attrValue").equalsIgnoreCase("Other")) {
            leftPocket = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "leftPocketOther")));
        }
        if(leftPocket != null) {
            GenericValue leftPocketPosition = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "leftPocketPosition")));
            data.put("Left Pocket", UtilMisc.toList(leftPocket.getString("attrValue"), leftPocketPosition.getString("attrValue")));
        }

        GenericValue centerPocket = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "centerPocket")));
        if(centerPocket != null && centerPocket.getString("attrValue").equalsIgnoreCase("Other")) {
            centerPocket = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "centerPocketOther")));
        }
        if(centerPocket != null) {
            GenericValue centerPocketPosition = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "centerPocketPosition")));
            data.put("Center Pocket", UtilMisc.toList(centerPocket.getString("attrValue"), centerPocketPosition.getString("attrValue")));
        }

        GenericValue rightPocket = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "rightPocket")));
        if(rightPocket != null && rightPocket.getString("attrValue").equalsIgnoreCase("Other")) {
            rightPocket = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "rightPocketOther")));
        }
        if(rightPocket != null) {
            GenericValue rightPocketPosition = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", "rightPocketPosition")));
            data.put("Right Pocket", UtilMisc.toList(rightPocket.getString("attrValue"), rightPocketPosition.getString("attrValue")));
        }

        return data;
    }

    /**
     *
     * @param orderItemAttributes
     * @param attrName
     * @return
     * @throws GenericEntityException
     */
    public static String getSimpleAttribute(List<GenericValue> orderItemAttributes, String attrName) throws GenericEntityException {
        GenericValue reinforcedEdges = EntityUtil.getFirst(EntityUtil.filterByAnd(orderItemAttributes, UtilMisc.toMap("attrName", attrName)));
        if(reinforcedEdges != null) {
            return reinforcedEdges.getString("attrValue");
        }

        return null;
    }

    /**
     * Get the description of a product attribute
     * @param delegator
     * @param attrName
     * @return
     * @throws GenericEntityException
     */
    public static String getAttributeDescription(Delegator delegator, String attrName) throws GenericEntityException {
        GenericValue attr = EntityQuery.use(delegator).from("ProductAttribute").where("attrName", attrName).cache().queryFirst();

        if(attr != null) {
            return attr.getString("attrDescription");
        }

        return null;
    }
}