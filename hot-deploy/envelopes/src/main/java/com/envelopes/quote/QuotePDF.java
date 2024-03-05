package com.envelopes.quote;

import com.bigname.quote.calculator.CalculatorHelper;
import com.bigname.pricingengine.calculator.impl.AdmorePricingCalculator;
import com.envelopes.http.FileHelper;
import com.envelopes.util.EnvConstantsUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.GenericValue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shoab on 7/14/17.
 */
public class QuotePDF {
    public static final String module = QuotePDF.class.getName();

    protected static BaseFont LATO_REGULAR;
    protected static BaseFont LATO_ITALIC;
    protected static BaseFont LATO_BOLD;
    protected static BaseFont LATO_BOLD_ITALIC;
    protected static BaseFont LATO_BLACK;
    protected static BaseFont LATO_BLACK_ITALIC;

    static {
        try {
            LATO_REGULAR = BaseFont.createFont(EnvConstantsUtil.OFBIZ_HOME + "/hot-deploy/html/webapp/html/fonts/Lato-Regular.ttf", BaseFont.WINANSI, true);
            LATO_ITALIC = BaseFont.createFont(EnvConstantsUtil.OFBIZ_HOME + "/hot-deploy/html/webapp/html/fonts/Lato-Italic.ttf", BaseFont.WINANSI, true);
            LATO_BOLD = BaseFont.createFont(EnvConstantsUtil.OFBIZ_HOME + "/hot-deploy/html/webapp/html/fonts/Lato-Bold.ttf", BaseFont.WINANSI, true);
            LATO_BOLD_ITALIC = BaseFont.createFont(EnvConstantsUtil.OFBIZ_HOME + "/hot-deploy/html/webapp/html/fonts/Lato-BoldItalic.ttf", BaseFont.WINANSI, true);
            LATO_BLACK = BaseFont.createFont(EnvConstantsUtil.OFBIZ_HOME + "/hot-deploy/html/webapp/html/fonts/Lato-Black.ttf", BaseFont.WINANSI, true);
            LATO_BLACK_ITALIC = BaseFont.createFont(EnvConstantsUtil.OFBIZ_HOME + "/hot-deploy/html/webapp/html/fonts/Lato-BlackItalic.ttf", BaseFont.WINANSI, true);
        } catch(Exception e) {
            //
        }
    }

    private Map<String, Object> pdfData = null;
    private String folder = FileHelper.cleanFolderPath(EnvConstantsUtil.OFBIZ_HOME + EnvConstantsUtil.UPLOAD_DIR + "/quotes/");
    private Document pdf = new Document(PageSize.LETTER); //PageSize.LETTER.rotate() for LANDSCAPE
    private File file = null;
    private PdfWriter writer = null;
    private String quoteRequestId = null;
    private String quoteId = null;
    private Map<String, Object> data = null;
    private boolean success = false;

    public QuotePDF(Map<String, Object> data) throws DocumentException, IOException {
        this.pdfData = data;
        this.data = data;
        this.quoteRequestId = (String) data.get("quoteRequestId");
        this.quoteId = (String) data.get("quoteId");
        this.file = new File(folder + this.quoteRequestId + "-" + this.quoteId + ".pdf");
        try {
            this.writer = PdfWriter.getInstance(this.pdf, new FileOutputStream(this.file));
            success = true;
        } catch(Exception e) {
            Debug.logError(e, "Error creating pdf", module);
        }
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void savePDF() throws DocumentException, IOException {
        this.pdf.open();

        addMetaData();
        createHeader();
        addSpacer(3);
        createCustomerInfo();
        addHR(3f);
        createProductInfo();
        createPriceTable();
        createNotes();
        createOffsetNotes();
        createFoilNotes();
        createEmbossingNotes();
        createCoatingNotes();
        createArtMessage();
        createComments();
        createProduction();
        createTerms();
        createFooter();

        this.pdf.close();
    }

    //612,792 - SIZE OF LETTER
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
        contact.setFont(getFont(LATO_BOLD, 8, 0, BaseColor.WHITE));
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
        contact2.setFont(getFont(LATO_REGULAR, 8, 0, BaseColor.GRAY));
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

    private void createCustomerInfo() throws DocumentException {
        Paragraph header = new Paragraph("CUSTOM QUOTE", getFont(LATO_BLACK, 20, 0, BaseColor.BLACK));
        header.setAlignment(Element.ALIGN_CENTER);
        this.pdf.add(header);

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setSpacingBefore(20f);
        table.setSpacingAfter(20f);

        PdfPCell cell1 = new PdfPCell();
        cell1.setBorder(0);
        GenericValue customer = (GenericValue) this.data.get("customer");
        Paragraph address = new Paragraph();
        address.setFont(getFont(LATO_REGULAR, 10, 0, BaseColor.BLACK));
        address.setLeading(0, 1.2f);
        //name
        if(UtilValidate.isNotEmpty(customer.getString("firstName")) && UtilValidate.isNotEmpty(customer.getString("lastName"))) {
            address.add(customer.getString("firstName") + " " + customer.getString("lastName"));
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
            address.add(Chunk.NEWLINE);
        }
        //email
        if(UtilValidate.isNotEmpty(customer.getString("userEmail"))) {
            address.add("Email: " + customer.getString("userEmail"));
            address.add(Chunk.NEWLINE);
        }
        //phone
        if(UtilValidate.isNotEmpty(customer.getString("phone"))) {
            address.add("Phone: " + customer.getString("phone"));
        }
        cell1.addElement(address);
        table.addCell(cell1);

        PdfPCell cell2 = new PdfPCell();
        cell2.setBorder(0);
        Paragraph quoteKey = new Paragraph();
        quoteKey.setAlignment(Paragraph.ALIGN_RIGHT);
        quoteKey.setFont(getFont(LATO_BOLD, 10, 0, BaseColor.BLACK));
        quoteKey.setLeading(0, 1.2f);
        quoteKey.add("Quote #:");
        quoteKey.add(Chunk.NEWLINE);
        quoteKey.add("Date:");
        quoteKey.add(Chunk.NEWLINE);
        quoteKey.add(Chunk.NEWLINE);
        quoteKey.add("Sales Consultant:");
        quoteKey.add(Chunk.NEWLINE);
        quoteKey.add("Phone:");
        quoteKey.add(Chunk.NEWLINE);
        quoteKey.add("Email:");

        cell2.addElement(quoteKey);
        table.addCell(cell2);

        PdfPCell cell3 = new PdfPCell();
        cell3.setBorder(0);
        Paragraph quoteValue = new Paragraph();
        quoteValue.setAlignment(Paragraph.ALIGN_RIGHT);
        quoteValue.setFont(getFont(LATO_REGULAR, 10, 0, BaseColor.BLACK));
        quoteValue.setLeading(0, 1.2f);
        quoteValue.add(this.quoteRequestId + "-" + this.quoteId);
        quoteValue.add(Chunk.NEWLINE);
        quoteValue.add(EnvConstantsUtil.MDY.format(this.data.get("createdStamp")));
        quoteValue.add(Chunk.NEWLINE);
        quoteValue.add(Chunk.NEWLINE);
        quoteValue.add((String) this.data.get("salesPerson"));
        quoteValue.add(Chunk.NEWLINE);
        quoteValue.add((String) this.data.get("salesNumber"));
        quoteValue.add(Chunk.NEWLINE);
        quoteValue.add((String) this.data.get("createdBy"));

        cell3.addElement(quoteValue);
        table.addCell(cell3);

        this.pdf.add(table);
    }

    private void createProductInfo() throws DocumentException {
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);

        GenericValue product = (UtilValidate.isNotEmpty(this.data.get("product"))) ? (GenericValue) this.data.get("product") : null;
        PdfPCell cell1 = new PdfPCell();
        cell1.setBorder(0);
        cell1.setVerticalAlignment(Element.ALIGN_TOP);
        Paragraph productHeader = new Paragraph("Product Description:");
        productHeader.setFont(getFont(LATO_BOLD, 14, 0, BaseColor.BLACK));
        cell1.addElement(productHeader);
        Paragraph productDescription = new Paragraph((product != null) ? product.getString("productId") + " : " + product.getString("productName") : (String) this.data.get("productName") + (UtilValidate.isNotEmpty(this.data.get("COATING")) ? " - C" + ((java.util.List) this.data.get("COATING")).size() + "S" : ""));
        productDescription.setFont(getFont(LATO_REGULAR, 10, 0, BaseColor.BLACK));
        cell1.addElement(productDescription);
        table.addCell(cell1);

        PdfPCell cell2 = new PdfPCell();
        cell2.setBorder(0);
        cell1.setVerticalAlignment(Element.ALIGN_TOP);
        Paragraph quoteKey = new Paragraph();
        quoteKey.setAlignment(Paragraph.ALIGN_RIGHT);
        quoteKey.setFont(getFont(LATO_BOLD, 10, 0, BaseColor.BLACK));
        quoteKey.setLeading(0, 1.2f);
        quoteKey.add("Material/Stock:");
        quoteKey.add(Chunk.NEWLINE);
        quoteKey.add("Print Methods:");
        quoteKey.add(Chunk.NEWLINE);
        quoteKey.add("Shipping:");
        quoteKey.add(Chunk.NEWLINE);
        quoteKey.add("Terms:");

        cell2.addElement(quoteKey);
        table.addCell(cell2);

        PdfPCell cell3 = new PdfPCell();
        cell3.setBorder(0);
        cell1.setVerticalAlignment(Element.ALIGN_TOP);
        Paragraph quoteValue = new Paragraph();
        quoteValue.setAlignment(Paragraph.ALIGN_RIGHT);
        quoteValue.setFont(getFont(LATO_REGULAR, 10, 0, BaseColor.BLACK));
        quoteValue.setLeading(0, 1.2f);
        quoteValue.add((String) this.data.get("material"));
        quoteValue.add(Chunk.NEWLINE);
        quoteValue.add(CalculatorHelper.printOptions(this.data));
        quoteValue.add(Chunk.NEWLINE);
        quoteValue.add("Ground Service");
        quoteValue.add(Chunk.NEWLINE);
        quoteValue.add("See Below");

        cell3.addElement(quoteValue);
        table.addCell(cell3);

        this.pdf.add(table);
    }

    private void createPriceTable() throws DocumentException {
        Paragraph para;

        if (CalculatorHelper.discountAmount.compareTo(new BigDecimal(0)) != 0) {
            para = new Paragraph("Save " + CalculatorHelper.discountAmount.multiply(new BigDecimal("100")).setScale(0, RoundingMode.FLOOR).toString() + "% This Month!", getFont(LATO_BOLD, 13, 0, BaseColor.RED));
            para.setSpacingAfter(10f);
            this.pdf.add(para);
        }

        Map<Integer, Map<String, BigDecimal>> priceQuotes = (Map<Integer, Map<String, BigDecimal>>) this.data.get("prices");
        int numberOfColumns = priceQuotes.size() + 1;
        PdfPTable table = new PdfPTable( numberOfColumns);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        //add row for quantity
        for(int i = 0; i < numberOfColumns; i++) {
            PdfPCell cell = new PdfPCell();
            cell.setPadding(5f);
            cell.setBackgroundColor(BaseColor.BLACK);
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            cell.setBorderColor(new BaseColor(199, 200, 202));
            if(i == 0) {
                cell.addElement(new Paragraph("Quantity", getFont(LATO_BOLD, 12, 0, BaseColor.WHITE)));
            } else {
                Integer key = (Integer) priceQuotes.keySet().toArray()[i-1];
                para = new Paragraph(EnvConstantsUtil.WHOLE_NUMBER.format(key), getFont(LATO_BOLD, 12, 0, BaseColor.WHITE));
                para.setAlignment(Paragraph.ALIGN_RIGHT);
                cell.addElement(para);
            }
            table.addCell(cell);
        }

        //add row for totals
        for(int i = 0; i < numberOfColumns; i++) {
            PdfPCell cell = new PdfPCell();
            cell.setPadding(5f);
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            cell.setBorderColor(new BaseColor(199, 200, 202));
            if(i == 0) {
                cell.setBackgroundColor(new BaseColor(243, 243, 244));
                para = new Paragraph("Total", getFont(LATO_BOLD, 12, 0, BaseColor.BLACK));
                cell.addElement(para);
            } else {
                Integer key = (Integer) priceQuotes.keySet().toArray()[i-1];

                if (CalculatorHelper.discountAmount.compareTo(new BigDecimal(0)) != 0) {
                    Chunk saveThisMonth = new Chunk("$" + ((priceQuotes.get(key)).get("total")).divide(new BigDecimal(1).subtract(CalculatorHelper.discountAmount), 2, RoundingMode.HALF_UP)).setUnderline(0.8f, 3.5f);
                    para = new Paragraph(saveThisMonth);
                    para.setAlignment(Paragraph.ALIGN_RIGHT);
                    cell.addElement(para);
                }

                para = new Paragraph(" $" + EnvConstantsUtil.STANDARD_DECIMAL.format((priceQuotes.get(key)).get("total")), getFont(LATO_BOLD, 12, 0, new BaseColor(252, 126, 34)));
                para.setAlignment(Paragraph.ALIGN_RIGHT);
                cell.addElement(para);
            }
            table.addCell(cell);
        }

        //add row for each price
        for(int i = 0; i < numberOfColumns; i++) {
            PdfPCell cell = new PdfPCell();
            cell.setPadding(5f);
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            cell.setBorderColor(new BaseColor(199, 200, 202));
            if(i == 0) {
                cell.setBackgroundColor(new BaseColor(243, 243, 244));
                para = new Paragraph("Each", getFont(LATO_BOLD, 12, 0, BaseColor.BLACK));
                cell.addElement(para);
            } else {
                Integer key = (Integer) priceQuotes.keySet().toArray()[i-1];
                para = new Paragraph("$" + (priceQuotes.get(key)).get("each") + " ea");
                para.setAlignment(Paragraph.ALIGN_RIGHT);
                cell.addElement(para);
            }
            table.addCell(cell);
        }

        this.pdf.add(table);
    }

    private void createNotes() throws DocumentException {
        Paragraph para = new Paragraph("Notes:", getFont(LATO_BOLD, 9, 0, BaseColor.BLACK));
        para.setSpacingAfter(10f);
        this.pdf.add(para);

        addHR(3f);

        this.pdf.add(new Paragraph("Free ground shipping included in quote. Applicable taxes will be collected on orders that ship to " + EnvConstantsUtil.TAXABLE_STATES + ".", getFont(LATO_BOLD, 9, 0, BaseColor.BLACK)));

        if(UtilValidate.isNotEmpty((String) this.data.get("offsetNote"))) {
            Pattern p = Pattern.compile("PMS Color(?:s|) with Heavy Coverage");
            Matcher m = p.matcher((String) this.data.get("offsetNote"));
            if (m.find()) {
                this.pdf.add(new Paragraph("Your quote includes an additional charge for Heavy Coverage.", getFont(LATO_BOLD, 9, 0, BaseColor.BLACK)));
            }
        }
        this.pdf.add(new Paragraph(" "));
    }

    private void createOffsetNotes() throws DocumentException {
        if(UtilValidate.isNotEmpty(this.data.get("offset")) && (Boolean) this.data.get("offset")) {
            Paragraph para = new Paragraph("For Offset Printing:", getFont(LATO_BOLD, 9, 0, BaseColor.BLACK));
            para.setSpacingAfter(10f);
            this.pdf.add(para);

            addHR(3f);

            this.pdf.add(new Paragraph((String) this.data.get("offsetNote"), getFont(LATO_REGULAR, 9, 0, BaseColor.BLACK)));
            this.pdf.add(new Paragraph(" "));
        }
    }

    private void createFoilNotes() throws DocumentException {
        if(UtilValidate.isNotEmpty(this.data.get("foil")) && (Boolean) this.data.get("foil")) {
            Paragraph para = new Paragraph("For Foil:", getFont(LATO_BOLD, 9, 0, BaseColor.BLACK));
            para.setSpacingAfter(10f);
            this.pdf.add(para);

            addHR(3f);

            this.pdf.add(new Paragraph((String) this.data.get("foilNote"), getFont(LATO_REGULAR, 9, 0, BaseColor.BLACK)));
            this.pdf.add(new Paragraph(" "));
        }
    }

    private void createEmbossingNotes() throws DocumentException {
        if(UtilValidate.isNotEmpty(this.data.get("embossing")) && (Boolean) this.data.get("embossing")) {
            Paragraph para = new Paragraph("For Embossing:", getFont(LATO_BOLD, 9, 0, BaseColor.BLACK));
            para.setSpacingAfter(10f);
            this.pdf.add(para);

            addHR(3f);

            this.pdf.add(new Paragraph((String) this.data.get("embossingNote"), getFont(LATO_REGULAR, 9, 0, BaseColor.BLACK)));
            this.pdf.add(new Paragraph(" "));
        }
    }

    private void createCoatingNotes() throws DocumentException {
        if(UtilValidate.isNotEmpty(this.data.get("addOnOptions"))) {
            Paragraph para = new Paragraph("Coatings:", getFont(LATO_BOLD, 9, 0, BaseColor.BLACK));
            para.setSpacingAfter(10f);
            this.pdf.add(para);

            addHR(3f);

            this.pdf.add(new Paragraph((String) this.data.get("addOnOptions"), getFont(LATO_REGULAR, 9, 0, BaseColor.BLACK)));
            this.pdf.add(new Paragraph(" "));
        }
    }

    private void createArtMessage() throws DocumentException {
        Paragraph para = new Paragraph("Artwork and Art Services:", getFont(LATO_BOLD, 9, 0, BaseColor.BLACK));
        para.setSpacingAfter(10f);
        this.pdf.add(para);

        addHR(3f);

        this.pdf.add(new Paragraph("The quoted price is based on the customer supplying digital art, with all elements in position, on a supplied template ready for printing. It also includes ½ hour of time to review art, layout and provide typesetting assistance for the 1st proof. If additional proofs are needed and/or more time is needed to review multiple customer supplied art files, this may require additional charges to be billed at hourly rates.", getFont(LATO_REGULAR, 9, 0, BaseColor.BLACK)));
        this.pdf.add(new Paragraph(" "));
    }

    private void createComments() throws DocumentException {
        if(UtilValidate.isNotEmpty(this.data.get("comment"))) {
            Paragraph para = new Paragraph("Comments:", getFont(LATO_BOLD, 9, 0, BaseColor.BLACK));
            para.setSpacingAfter(10f);
            this.pdf.add(para);

            addHR(3f);

            this.pdf.add(new Paragraph((String) this.data.get("comment"), getFont(LATO_REGULAR, 9, 0, BaseColor.BLACK)));
            this.pdf.add(new Paragraph(" "));
        }
    }

    private void createProduction() throws DocumentException {
        if(UtilValidate.isNotEmpty(this.data.get("production"))) {
            Paragraph para = new Paragraph("Production Time:", getFont(LATO_BOLD, 9, 0, BaseColor.BLACK));
            para.setSpacingAfter(10f);
            this.pdf.add(para);

            addHR(3f);

            this.pdf.add(new Paragraph((String) this.data.get("production"), getFont(LATO_REGULAR, 9, 0, BaseColor.BLACK)));
            this.pdf.add(new Paragraph(" "));
        }
    }

    private void createTerms() throws DocumentException {
        Paragraph para = new Paragraph("Terms of Sale:", getFont(LATO_BOLD, 9, 0, BaseColor.BLACK));
        para.setSpacingAfter(10f);
        this.pdf.add(para);

        addHR(3f);

        this.pdf.add(new Paragraph("The entire order is payable in full at time of order. In addition, there is a maximum of 10% over run and 10% under run which will be billed or credited at the unit purchased price for the actual quantity of units shipped.", getFont(LATO_REGULAR, 9, 0, BaseColor.BLACK)));
    }
    private void createFooter() throws DocumentException {
        PdfContentByte canvas = this.writer.getDirectContent();
        Rectangle rect = new Rectangle(10f, 10f, 602f, 30f);
        rect.setBackgroundColor(BaseColor.BLACK);
        canvas.rectangle(rect);

        ColumnText ct = new ColumnText(canvas);
        ct.setSimpleColumn(rect);
        ct.addElement(new Paragraph("   THIS QUOTE WILL EXPIRE 30 DAYS FROM THE ISSUE DATE", getFont(LATO_BOLD, 9, 0, BaseColor.WHITE)));
        ct.go();
    }

    private void addMetaData() {
        this.pdf.addTitle("Folders.com Quote");
        this.pdf.addSubject("Quote");
        this.pdf.addKeywords("Quote");
        this.pdf.addAuthor("Folders.com");
        this.pdf.addCreator("Folders.com");
    }

    protected static Font getFont(BaseFont baseFont, int size, int style, BaseColor color) {
        Font font = null;
        switch(style) {
            case 0:
                font = new Font(baseFont, size, Font.NORMAL, color);
                break;
            case 1:
                font = new Font(baseFont, size, Font.BOLD, color);
                break;
            case 2:
                font = new Font(baseFont, size, Font.ITALIC, color);
                break;
            case 1 | 2:
                font = new Font(baseFont, size, Font.BOLDITALIC, color);
                break;
            case 4:
                font = new Font(baseFont, size, Font.UNDERLINE, color);
                break;
        }

        return font;
    }

    private void addHR(float thickness) throws DocumentException {
        LineSeparator ls = new LineSeparator();
        ls.setLineWidth(thickness);

        this.pdf.add(ls);
    }

    private void addSpacer(int numberOfLines) throws DocumentException {
        Paragraph spacer = new Paragraph();
        spacer.setAlignment(Element.ALIGN_CENTER);

        for(int i = 1; i <= numberOfLines; i++) {
            spacer.add(Chunk.NEWLINE);
        }
        this.pdf.add(spacer);
    }
}