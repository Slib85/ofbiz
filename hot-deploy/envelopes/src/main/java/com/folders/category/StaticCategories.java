package com.folders.category;

import java.util.*;

public class StaticCategories {
    public static final Map<String, Map<String, Object>> blankCategories = new LinkedHashMap<>();

    static {
        Map<String, Object> category = new LinkedHashMap<>();
        category.put("friendlyUrl", "blank-9-x-12-folders");
        category.put("seoH1", "Blank 9x12 Folders (Cover Stock)");
        category.put("seoH2", "Keep organized and look professional with our selection of blank 9x12 folders. Choose from a variety of styles and paper stocks for any business or home office need.");

        Map<String, Object> subCategories = new LinkedHashMap<>();

        Map<String, Object> subCategory = new HashMap<>();

        subCategory.put("bullet1", "9\" x 12\" Standard Pocket");
        subCategory.put("min", 25);
        subCategory.put("categoryId", "BLANK_SM_FOLDERS");
        subCategory.put("products", Arrays.asList("PF-PUZZLE", "PF-WLI", "PF-130W", "SF-101-SG12", "SF-101-AW100", "PF-NLI", "LUX-PF-17", "LUX-PF-07", "SF-101-DMAH100", "LUX-PF-22", "SF-101-CS80", "SF-101-CSG100", "PF-BLI", "LUX-PF-56", "SF-101-DB12", "SF-101-CMBLK12", "PF-M06", "PF-25PACKASST", "PF-GB", "PF-M07", "LUX-PF-18", "LUX-PF-26", "SF-101-DB100", "PF-25PACKBURG", "SF-101-RGLOSS", "SF-101-CMBUR12", "PF-114", "LUX-PF-10", "LUX-PF-14", "LUX-PF-104", "SF-101-DE100", "PF-DBLI", "LUX-PF-13", "LUX-PF-23", "LUX-PF-25", "LUX-PF-102", "LUX-PF-103", "LUX-PF-113", "SF-101-DN12", "SF-101-CMBLU12", "LUX-PF-101", "LUX-PF-L17", "LUX-PF-L22", "SF-101-BT80", "SF-101-DDP100", "LUX-PF-12", "LUX-PF-15", "LUX-PF-L20", "LUX-PF-11", "LUX-PF-112"));
        subCategories.put("Standard Blank 9x12 Folders", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "9 1/2\" x 12\" Capacity Pocket");
        subCategory.put("min", 25);
        subCategory.put("categoryId", "BLANK_EX_CAP_FOLDERS");
        subCategory.put("products", Arrays.asList("CON-912F-DBLI", "CON-912F-BLI", "CON-912F-NLI"));
        subCategories.put("Blank Capacity Folders", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "9\" x 12\" Standard Pocket");
        subCategory.put("min", 25);
        subCategory.put("categoryId", "FOLDERS_W_FASTENERS");
        subCategory.put("products", Arrays.asList("PF912WHITE-BRAD", "PF912BLACK-BRAD", "PF912ASST50PK-BRAD", "PF912ASST50PDQ-BRAD", "PF912ASST100PDQ-BRAD", "PF912RED-BRAD", "PF912BURG-BRAD", "PF912PINK-BRAD", "PF912PURP-BRAD", "PF912BLUE-BRAD", "PF912TEAL-BRAD", "PF912DBLUE-BRAD", "SF-101-546-TANG", "PF912GREEN-BRAD", "PF912BRGREEN-BRAD", "PF912YELLOW-BRAD", "PF912ORANGE-BRAD"));
        subCategories.put("Blank Folders with Fasteners", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "9\" x 12\" Standard Pocket");
        subCategory.put("min", 25);
        subCategory.put("categoryId", "PP_TAX_FOLDERS");
        subCategory.put("products", Arrays.asList("TAX-912-FL", "TAX-912-NF80", "TAX-912-CEI80", "SF-101-546-TAX"));
        subCategories.put("Pre-Printed Tax Folders", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "9\" x 12\" Standard Pocket");
        subCategory.put("min", 25);
        subCategory.put("categoryId", "BLANK_ST_PR_FOLDERS");
        subCategory.put("products", Arrays.asList("SF-102-DDBLU100", "PF-202"));
        subCategories.put("Blank Folders with Window", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "9\" x 12\" Standard Pocket");
        subCategory.put("min", 25);
        subCategory.put("categoryId", "BLANK_ST_PR_FOLDERS");
        subCategory.put("products", Arrays.asList("OR-144-DDBLU100", "OR-144-DDBLK100", "OR-144-SG12"));
        subCategories.put("Blank Folders with Front Card Slits", subCategory);

        category.put("subCategories", subCategories);
        blankCategories.put("9x12_FOLDERS", category);

        category = new LinkedHashMap<>();
        category.put("friendlyUrl", "poly-folders");
        category.put("seoH1", "Blank Poly Folders");
        category.put("seoH2", "Durable folders that protect your documents and get the job done. Choose from a variety of pocket styles, assorted colors and features for any organizational need.");

        subCategories = new LinkedHashMap<>();

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "9 1/2\" x 11 3/4\" Standard Pocket");
        subCategory.put("min", 25);
        subCategory.put("categoryId", "FOLDERS");
        subCategory.put("products", Arrays.asList("PF-0913-BK", "PF-0913-WH", "PF-0913-GN", "PF-0913-BL", "PF-0913-RD", "PF-0913-MA", "PF-0916-BK", "PF-0916-WH", "PF-0916-GN", "PF-0916-BL", "PF-0916-RD", "PF-0916-MA"));
        subCategories.put("Blank Poly Folders ", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "9 1/2\" x 11 3/4\" Standard Pocket");
        subCategory.put("min", 25);
        subCategory.put("categoryId", "FOLDERS");
        subCategory.put("products", Arrays.asList("PF-0917-BK", "PF-0917-WH", "PF-0917-BL", "PF-0917-GN", "PF-0917-RD", "PF-0917-MA"));
        subCategories.put("Blank Front Pocket Poly Folders ", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "9\" x 12\" Standard Pocket");
        subCategory.put("min", 24);
        subCategory.put("categoryId", "FOLDERS_W_FASTENERS");
        subCategory.put("products", Arrays.asList("PLYF912CLEAR-BRAD", "PLYF912RED-BRAD", "PLYF912BLUE-BRAD", "PLYF912YELLOW-BRAD"));
        subCategories.put("Blank Poly Folders with Fasteners ", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "9 1/2\" x 11 1/2\" Continuous Pocket");
        subCategory.put("min", 48);
        subCategory.put("categoryId", "POLY_FOLDERS");
        subCategory.put("products", Arrays.asList("PLYF3HPCPCLEAR", "PLYF3HPCPPINK", "PLYF3HPCPPURPLE", "PLYF3HPCPDBLUE", "PLYF3HPCPBRGREEN", "PLYF3HPCPORANGE"));
        subCategories.put("Blank Continuous Pocket Poly Folders", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "11.25 x .125 x 9.25 Wave Pocket");
        subCategory.put("min", 120);
        subCategory.put("categoryId", "POLY_FOLDERS");
        subCategory.put("products", Arrays.asList("PLYFWAVE-ASST", "PLYFTWST-ASST"));
        subCategories.put("Blank Wave Pocket Poly Folders", subCategory);

        category.put("subCategories", subCategories);
        blankCategories.put("POLY_FOLDERS", category);

        category = new LinkedHashMap<>();
        category.put("friendlyUrl", "blank-legal-folders");
        category.put("seoH1", "Blank Legal Folders");
        category.put("seoH2", "Keep organized and look professional with our selection of blank legal size folders. Available in different styles and paper stocks for any business or home office need. ");

        subCategories = new LinkedHashMap<>();

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "9\" x 14 1/2\" Standard Pocket");
        subCategory.put("min", 25);
        subCategory.put("categoryId", "BLANK_LEGAL_FOLDERS");
        subCategory.put("products", Arrays.asList("LF-118-DDBLU100", "LF-WLI", "LF-118-SG12", "LF-NLI", "LF-118-DDBLK100", "LF-118-DDP100", "LF-GRLI", "LF-22"));
        subCategories.put("Blank Legal Folders", subCategory);

        category.put("subCategories", subCategories);
        blankCategories.put("LEGAL_FOLDERS", category);

        category = new LinkedHashMap<>();
        category.put("friendlyUrl", "welcome-folders");
        category.put("seoH1", "Welcome Folders");
        category.put("seoH2", "Considered a small presentation folder, Welcome Folders are ideal for churches, organizations, educational institutions, and charities.  They feature a foil stamped \"Welcome\" design on the front cover, and are the perfect fit for pamphlets, stepped inserts and more.");

        subCategories = new LinkedHashMap<>();

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "5 3/4\" x 8 3/4\" Standard Pocket");
        subCategory.put("min", 25);
        subCategory.put("categoryId", "WELCOME_FOLDERS");
        subCategory.put("products", Arrays.asList("WEL-DB100-GF", "WEL-SG12-GF", "WEL-BN100-GF", "WEL-DB100-FGF", "WEL-DB100-FSF", "WEL-DDBLU100-SF", "WEL-DDBLU100-FGF", "WEL-DDBLU100-FSF", "WEL-DDP100-GF", "WEL-BIEN-DB100-GF"));
        subCategories.put("Welcome Folders", subCategory);

        category.put("subCategories", subCategories);
        blankCategories.put("WELCOME_FOLDERS", category);

        category = new LinkedHashMap<>();
        category.put("friendlyUrl", "blank-small-folders");
        category.put("seoH1", "Blank Small Folders");
        category.put("seoH2", "Small presentation folders with a big use. Available in a variety of paper stocks and small folder sizes, and are ideal for presenting pamphlets, inserts and more.");

        subCategories = new LinkedHashMap<>();

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "4\" x 9\" Standard Pocket");
        subCategory.put("min", 25);
        subCategory.put("categoryId", "BLANK_SM_FOLDERS");
        subCategory.put("products", Arrays.asList("49F-WLI", "49F-W", "MF-4801-SG12", "49F-NLI", "MF-22", "49F-BLI", "49F-GB", "49F-BGLI", "MF-114", "MF-113", "MF-4801-DDBLU100", "49F-GNLI"));
        subCategories.put("Blank 4x9 Folders", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "6\" x 9\" Standard Pocket");
        subCategory.put("min", 25);
        subCategory.put("categoryId", "BLANK_SM_FOLDERS");
        subCategory.put("products", Arrays.asList("SPF-WLI", "SPF-WG120", "SPF-NLI", "SPF-22", "SPF-GRLI", "SPF-BLI", "SPF-GB", "SPF-BGLI", "SPF-BULI", "SPF-GNLI", "MF-144-SG12"));
        subCategories.put("Blank 6x9 Folders", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "3 3/8\" x 6\" Standard Pocket");
        subCategory.put("min", 25);
        subCategory.put("categoryId", "BLANK_CARD_HOLDERS");
        subCategory.put("products", Arrays.asList("KHF-W", "KCH-WG120", "KHF-WLI", "KHF-NLI", "KCH-22", "KHF-BLI", "KCH-114", "KCH-113", "KCH-BULI"));
        subCategories.put("Blank Card Holders", subCategory);

        category.put("subCategories", subCategories);
        blankCategories.put("SMALL_FOLDERS", category);

        category = new LinkedHashMap<>();
        category.put("friendlyUrl", "blank-binders");
        category.put("seoH1", "Blank Binders");
        category.put("seoH2", "Stay organized with quality blank binders. Perfect for holding presentations, documents, lecture notes and more. Choose from various sizes, colors and ring styles for any need.");

        subCategories = new LinkedHashMap<>();

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "8 1/2\" x 11\"");
        subCategory.put("min", 12);
        subCategory.put("categoryId", "BLANK_POLY_BINDERS");
        subCategory.put("products", Arrays.asList("PB-209", "PB-221", "PB-213", "PB-219", "PB-211", "PB-212", "PB-215", "PB-210", "PB-214", "PB-216", "PB-217", "PB-218", "PB-220", "PB-222"));
        subCategories.put("1\" Earth Friendly View Binders", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "8 1/2\" x 11\"");
        subCategory.put("min", 12);
        subCategory.put("categoryId", "BLANK_POLY_BINDERS");
        subCategory.put("products", Arrays.asList("PB-235", "PB-231", "PB-223", "PB-228", "PB-226", "PB-229", "PB-232", "PB-234", "PB-224", "PB-225", "PB-227", "PB-230", "PB-233", "PB-226"));
        subCategories.put("1 1/2\" Earth Friendly View Binders", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "8 1/2\" x 11\"");
        subCategory.put("min", 12);
        subCategory.put("categoryId", "BLANK_POLY_BINDERS");
        subCategory.put("products", Arrays.asList("PB-240", "PB-241", "PB-242", "PB-246", "PB-245", "PB-247", "PB-248", "PB-238", "PB-239", "PB-243", "PB-244", "PB-237", "PB-249"));
        subCategories.put("2\" Earth Friendly View Binders", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "8 1/2\" x 11\"");
        subCategory.put("min", 12);
        subCategory.put("categoryId", "BLANK_POLY_BINDERS");
        subCategory.put("products", Arrays.asList("PB-263", "PB-251", "PB-258"));
        subCategories.put("3\" Earth Friendly View Binders", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "8 1/2\" x 11\"");
        subCategory.put("min", 1);
        subCategory.put("categoryId", "BLANK_POLY_BINDERS");
        subCategory.put("products", Arrays.asList("PB-1SRWHITE", "PB-1SRBLACK", "PB-1SRBLUE"));
        subCategories.put("1\" Poly Binders with Slant Rings", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "8 1/2\" x 11\"");
        subCategory.put("min", 1);
        subCategory.put("categoryId", "BLANK_POLY_BINDERS");
        subCategory.put("products", Arrays.asList("PB-204", "PB-207"));
        subCategories.put("1\" Poly Binder with Round Rings", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "8 1/2\" x 11\"");
        subCategory.put("min", 1);
        subCategory.put("categoryId", "BLANK_POLY_BINDERS");
        subCategory.put("products", Arrays.asList("PB-201", "PB-205", "PB-200"));
        subCategories.put("1\" Non-View Poly Binders with Round Rings", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "8 1/2\" x 11\"");
        subCategory.put("min", 1);
        subCategory.put("categoryId", "BLANK_POLY_BINDERS");
        subCategory.put("products", Arrays.asList("PB-2SRBLACK", "PB-2SRWHITE"));
        subCategories.put("2\" Poly Binders with Slant Rings", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "8 1/2\" x 11\"");
        subCategory.put("min", 1);
        subCategory.put("categoryId", "BLANK_POLY_BINDERS");
        subCategory.put("products", Arrays.asList("PB-3RREWHITE", "PB-3RREBLACK"));
        subCategories.put("3\" Poly Binders with Round Rings", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "8 1/2\" x 11\"");
        subCategory.put("min", 1);
        subCategory.put("categoryId", "BLANK_POLY_BINDERS");
        subCategory.put("products", Arrays.asList("PB-3SRWHITE"));
        subCategories.put("3\" Poly Binders with Slant Rings", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "8 1/2\" x 11\"");
        subCategory.put("min", 1);
        subCategory.put("categoryId", "BLANK_POLY_BINDERS");
        subCategory.put("products", Arrays.asList("PB-TUFFY", "PB-TUFFY-W", "PB-TUFFY-BK"));
        subCategories.put("1\" Plastic Tuffy Binders with Plastic Rings", subCategory);

        category.put("subCategories", subCategories);
        blankCategories.put("BINDERS", category);

        category = new LinkedHashMap<>();
        category.put("friendlyUrl", "pre-printed-certificate-holders");
        category.put("seoH1", "Pre-Printed Certificate Holders");
        category.put("seoH2", "Pre-Printed Certificate Holders are a unique and professional way to present certificates, awards and more. Choose from two popular sizes and different themed prints for any occasion");

        subCategories = new LinkedHashMap<>();

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "9 1/2\" x 12\"");
        subCategory.put("min", 25);
        subCategory.put("categoryId", "BLANK_CERT_HOLDERS");
        subCategory.put("products", Arrays.asList("CH-369","CH-370","CH-365","CH-366","CH-367","CH-368","CH-376","CH-377","CH-378","CH-379","CH-380","CH-387","CH-388","CH-390","CH-364","CH-350","CH-354","CH-355","CH-356"));
        subCategories.put("9 1/2 x 12 Certificate Holders", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "6 1/2\" x 9 1/2\"");
        subCategory.put("min", 25);
        subCategory.put("categoryId", "BLANK_CERT_HOLDERS");
        subCategory.put("products", Arrays.asList("CH-362","CH-363","CH-385","CH-373","CH-371","CH-374","CH-381","CH-382","CH-386","CH-389","CH-358","CH-359","CH-360","CH-361","CH-372","CH-357","CH-349","CH-353","CH-351","CH-352"));
        subCategories.put("6 1/2 x 9 1/2 Certificate Holders", subCategory);

        category.put("subCategories", subCategories);
        blankCategories.put("PRE_PRINTED_CERTIFICATE_HOLDERS", category);

        category = new LinkedHashMap<>();
        category.put("friendlyUrl", "blank-certificate-holders");
        category.put("seoH1", "Blank Certificate Holders");
        category.put("seoH2", "Blank Certificate Holders are an elegant and professional way to present certificates, awards and more. Choose from different styles, sizes and paper stocks for any need.");

        subCategories = new LinkedHashMap<>();

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "9 1/2\" x 12\"");
        subCategory.put("min", 25);
        subCategory.put("categoryId", "BLANK_CERT_HOLDERS");
        subCategory.put("products", Arrays.asList("CH91212-WLI", "CH91212-WG120", "CHEL-185-SG12", "CHEL-185-BN100", "CH-303", "CH91212-22", "CH-SF-300", "CHEL-185-DDBLK100", "CHEL-185-DDBLK100-F", "CHEL-185-DDBLK100-FLORALGF", "CHEL-185-DDBLK100-FLORALSF", "CH91212-GB", "CH91212-M06", "CH91212-M07", "CH-304", "CHEL-185-DB100", "CH-SF-301", "CHEL-185-DDBLU100", "CHEL-185-DDBLU100-F", "CHEL-185-DDBLU100-FLORALGF", "CHEL-185-DDBLU100-FLORALSF", "CHEL-185-DDP100"));
        subCategories.put("Blank 9 1/2 x 12 Certificate Holders", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "9 1/2\" x 12\"");
        subCategory.put("min", 25);
        subCategory.put("categoryId", "BLANK_CERT_HOLDERS");
        subCategory.put("products", Arrays.asList("SCH-WLI", "SCH-WG120", "SCH-NLI", "SCH-22", "SCH-GRLI", "SCH-BLI", "SCH-GB", "SCH-M06", "SCH-M07", "SCH-26", "SCH-BGLI", "SCH-10", "SCH-BULI", "SCH-GNLI"));
        subCategories.put("Blank Single Certificate Holders", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "8 1/2\" x 12\"");
        subCategory.put("min", 25);
        subCategory.put("categoryId", "BLANK_CERT_HOLDERS");
        subCategory.put("products", Arrays.asList("CH-332", "CH-306", "CH-329", "CH-330", "CH-305", "CH-331", "CH-328"));
        subCategories.put("Blank 8 1/2 x 11 Leatherette Certificate Holders", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "5\" x 7\"");
        subCategory.put("min", 25);
        subCategory.put("categoryId", "BLANK_CERT_HOLDERS");
        subCategory.put("products", Arrays.asList("CH-342", "CH-309", "CH-339", "CH-340", "CH-310", "CH-341", "CH-338"));
        subCategories.put("Blank 5x7 Leatherette Certificate Holders", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "6\" x 8\"");
        subCategory.put("min", 25);
        subCategory.put("categoryId", "BLANK_CERT_HOLDERS");
        subCategory.put("products", Arrays.asList("CH-337", "CH-307", "CH-334", "CH-335", "CH-308", "CH-336", "CH-333"));
        subCategories.put("Blank 6x8 Leatherette Certificate Holders", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "10 3/4\" x 13\"");
        subCategory.put("min", 1);
        subCategory.put("categoryId", "BLANK_CERT_HOLDERS");
        subCategory.put("products", Arrays.asList("CH-311", "CH-312", "CH-315", "CH-313", "CH-317"));
        subCategories.put("Blank Certificate Frames with Easel", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "8 1/2\" x 11\"");
        subCategory.put("min", 1);
        subCategory.put("categoryId", "BLANK_CERT_HOLDERS");
        subCategory.put("products", Arrays.asList("CH-319", "CH-320", "CH-318"));
        subCategories.put("Blank Certificate Boards", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "");
        subCategory.put("min", 1);
        subCategory.put("categoryId", "BLANK_CERT_HOLDERS");
        subCategory.put("products", Arrays.asList("FS-2020", "FS-300", "FS-306", "FS-301", "FS-303", "FS-307", "FS-305", "FS-302", "FS-308", "FS-309"));
        subCategories.put("Embossed Foil Seals", subCategory);

        category.put("subCategories", subCategories);
        blankCategories.put("CERTIFICATE_HOLDERS", category);

        category = new LinkedHashMap<>();
        category.put("friendlyUrl", "blank-padded-diploma-covers");
        category.put("seoH1", "Blank Diploma Covers");
        category.put("seoH2", "Blank Diploma Covers are an elegant and professional way to present awards, certificates, diplomas and more. Choose from different styles, sizes and cover materials for any need.");

        subCategories = new LinkedHashMap<>();

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "8 1/2\" x 11\"");
        subCategory.put("min", 1);
        subCategory.put("categoryId", "BLANK_PD_DIPLOMA_COV");
        subCategory.put("products", Arrays.asList("PDCL-85X11-DB", "DC-207", "DC-208", "PDCL-85X11-NB", "DC-206"));
        subCategories.put("Blank 8 1/2 x 11 Padded Diploma Cover", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "5\" x 7\"");
        subCategory.put("min", 25);
        subCategory.put("categoryId", "BLANK_PD_DIPLOMA_COV");
        subCategory.put("products", Arrays.asList("DC-214", "DC-202", "DC-211", "DC-212", "DC-203", "DC-210", "DC-213"));
        subCategories.put("Blank 5x7 Padded Diploma Cover", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "6\" x 8\"");
        subCategory.put("min", 25);
        subCategory.put("categoryId", "BLANK_PD_DIPLOMA_COV");
        subCategory.put("products", Arrays.asList("DC-219", "DC-200", "DC-216", "DC-217", "DC-201", "DC-218", "DC-215"));
        subCategories.put("Blank 6x8 Padded Diploma Cover", subCategory);

        category.put("subCategories", subCategories);
        blankCategories.put("DIPLOMA_COVERS", category);

        category = new LinkedHashMap<>();
        category.put("friendlyUrl", "blank-report-covers");
        category.put("seoH1", "Blank Report Covers");
        category.put("seoH2", "Keep important documents and presentations together with blank report covers. Choose from various sizes, styles, colors and features for any need.");

        subCategories = new LinkedHashMap<>();

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "8 1/2\" x 11\"");
        subCategory.put("min", 25);
        subCategory.put("categoryId", "REPORT_COVERS");
        subCategory.put("products", Arrays.asList("RC-400", "RC81211-BK", "RC81211-CBK", "RC81211-ECBK", "RC81211-SGBK", "RC81211-CT", "RC81211-DASST", "RC81211-BASST", "RC81211-R", "RC81211-CR", "RC81211-RECYR", "RC81211-BL", "RC81211-LB", "RC81211-CLB", "RC81211-DBL", "RC81211-CDBL", "RC81211-RECYBL", "RC81211-GN", "RC81211-CGN"));
        subCategories.put("Oxford Panel & Border Report Covers", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "8 1/2\" x 11\"");
        subCategory.put("min", 1);
        subCategory.put("categoryId", "REPORT_COVERS");
        subCategory.put("products", Arrays.asList("RC-402", "RC-401", "RC-405", "RC-404", "RC-403"));
        subCategories.put("Pressboard Report Cover with Prong Clip", subCategory);

        category.put("subCategories", subCategories);
        blankCategories.put("REPORT_COVERS", category);

        category = new LinkedHashMap<>();
        category.put("friendlyUrl", "paper-certificates");
        category.put("seoH1", "Certificates");
        category.put("seoH2", "Reward a job well-done with with our pre-printed or blank paper certificates. Available in a variety of styles, designs and colors for any presentation need.");

        subCategories = new LinkedHashMap<>();

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "8 1/2\" x 11\"");
        subCategory.put("min", 25);
        subCategory.put("categoryId", "PP_PAPER_CERT");
        subCategory.put("products", Arrays.asList("CERT-PREK ", "CERT-K", "CERT-STARBLU","CERT-STARGLD","CERT-STARRED","CERT-PUZZLE","CERT-PUZBDR", "CERT-70WBLK", "CERT-70WBLU", "CERT-70WGLD", "CERT-70WRED", "CERT-29APP", "CERT-29AWA", "CERT-29BLA", "CERT-29PAR", "CERTIF-BLA", "CERTIF-PAR"));
        subCategories.put("Blank 8 1/2 x 11 Paper Certificates", subCategory);

        category.put("subCategories", subCategories);
        blankCategories.put("CERTIFICATES", category);

        category = new LinkedHashMap<>();
        category.put("friendlyUrl", "blank-padfolios");
        category.put("seoH1", "Notebooks & Padfolios");
        category.put("seoH2", "Look professional and stay organized with our selection of blank notebooks, journals and padfolios. Perfect for all personal or business needs, and available in a variety of colors and styles to fit any use.");

        subCategories = new LinkedHashMap<>();

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "6\" x 8 1/4\"");
        subCategory.put("min", 1);
        subCategory.put("categoryId", "PADFOLIOS");
        subCategory.put("products", Arrays.asList("NB-1002", "NB-1003", "NB-1004", "NB-1001", "NB-1005", "NB-1006", "NB-1007", "NB-1008"));
        subCategories.put("Notebooks", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "5 3/4\" x 8\"");
        subCategory.put("min", 1);
        subCategory.put("categoryId", "PADFOLIOS");
        subCategory.put("products", Arrays.asList("VJ-1003", "VJ-1004", "VJ-1001", "VJ-1002"));
        subCategories.put("Journals", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "8 1/2\" x 11\"");
        subCategory.put("min", 1);
        subCategory.put("categoryId", "PADFOLIOS");
        subCategory.put("products", Arrays.asList("PD-267", "PD-266", "PD-265", "PD-274", "PD-275", "PD-276", "PD-277"));
        subCategories.put("Graduate Padfolios", subCategory);

        category.put("subCategories", subCategories);
        blankCategories.put("PADFOLIOS", category);


        category = new LinkedHashMap<>();
        category.put("friendlyUrl", "mailers");
        category.put("seoH1", "Mailers");
        category.put("seoH2", "Keep your important documents, folders and more safe and secure in the mail with our blank and custom Mailers. Available in a variety of colors, sizes and styles for any need.");

        subCategories = new LinkedHashMap<>();

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "Variety of sizes available");
        subCategory.put("min", 0);
        subCategory.put("categoryId", "MAILERS");
        subCategory.put("products", Arrays.asList("LUXMLR-GB", "LUXMLR-NLI", "LUXMLR-17"));
        subCategories.put("LUX Mailers", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "Variety of sizes available");
        subCategory.put("min", 0);
        subCategory.put("categoryId", "MAILERS");
        subCategory.put("products", Arrays.asList("PM-241", "PM-274", "PM-275", "PM-276", "PM-277", "PM-278", "PM-279", "PM-280", "PM-312", "PM-244", "PM-245", "PM-239", "BP-CPM1215BK", "BP-CPM1215BL", "BP-CPM1215R", "BP-CPM1215Y", "BP-CPM1419BK", "BP-CPM1419BL", "BP-CPM1419R", "PM-202", "PM-203", "PM-200", "79249", "BP-CPM1215BK", "PM-243", "BP-CPM1215R", "PM-310", "BP-CPM1215BL", "BP-CPM1215Y", "PM-201", "BP-CPM1419BK", "PM-311", "BP-CPM1419BL", "PM-246", "PM-242", "PM-309", "PM-240", "14190", "92663", "82520"));
        subCategories.put("Poly Mailers", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "Variety of sizes available");
        subCategory.put("min", 0);
        subCategory.put("categoryId", "MAILERS");
        subCategory.put("products", Arrays.asList("69PBM-BF", "69PBM-G", "69PBM-HR", "BM1012X16TSBK", "BP-RM2K", "BP-RM5K", "84477WIN", "84477W", "10968", "BP-RM1K", "BP-RM10K", "BP-RM4K", "BP-RM3K", "1113PBM-HR", "1113PBM-BF", "PM-206", "PM-228"));
        subCategories.put("Paperboard Mailers", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "Variety of sizes available");
        subCategory.put("min", 0);
        subCategory.put("categoryId", "MAILERS");
        subCategory.put("products", Arrays.asList("QUAR7525", "BM-200", "BM-201", "BM-203", "BM-204", "BM-206", "BM-207", "BM-208", "BM-209", "BM-211", "BM-212", "BM-214", "BM-215", "BM-216", "BM-217", "BM-219", "BM-220", "BM-222", "BM-223", "BM-225", "BM-226", "BM-227", "BM-228", "BM-229", "BM-230", "BM-231", "BM-232", "BM-233", "BM-234", "BM-235", "BM-236", "BM-237", "BM-238", "BM-239", "BM-240", "BM-241", "BM-242", "BM-243", "BM-244", "BM-245", "XPJ851125", "LUX-KWBM-1", "SBM851125", "LUX-KBBM-4", "LUX-KWBM-CD"));
        subCategories.put("Bubble Mailers", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "Variety of sizes available");
        subCategory.put("min", 0);
        subCategory.put("categoryId", "MAILERS");
        subCategory.put("products", Arrays.asList("SBM851125", "73228", "1410", "37090", "45622", "27297", "45641", "86339", "45643"));
        subCategories.put("AirJacket Mailers", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "Variety of sizes available");
        subCategory.put("min", 0);
        subCategory.put("categoryId", "MAILERS");
        subCategory.put("products", Arrays.asList("CM-247", "CM-248"));
        subCategories.put("Gusseted Flat Mailers", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "Variety of sizes available");
        subCategory.put("min", 0);
        subCategory.put("categoryId", "MAILERS");
        subCategory.put("products", Arrays.asList("JM-251", "JM-252", "JM-254", "JM-256", "JM-255", "JM-253"));
        subCategories.put("Jiffy Rigi Bag Mailers", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "Variety of sizes available");
        subCategory.put("min", 0);
        subCategory.put("categoryId", "MAILERS");
        subCategory.put("products", Arrays.asList("M1034X13GD", "M1034X13GN", "M1034X13R", "M1034X13S", "M614X1014GD", "M614X1014GN", "M614X1014R", "M614X1014S", "M614X1014TS", "M912X1234GD", "M912X1234R", "M912X1234S", "M912X1234TS", "PM-229", "PM-230", "PM-231", "PM-232", "PM-233", "PM-234", "PM-235", "PM-236", "PM-237", "PM-238"));
        subCategories.put("Metallic Glamour Mailers", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "Variety of sizes available");
        subCategory.put("min", 0);
        subCategory.put("categoryId", "MAILERS");
        subCategory.put("products", Arrays.asList("BP-M442", "BP-M442K", "BP-M643", "BP-M643K", "BP-M742", "BP-M742K", "BP-M843", "BP-M843K", "BP-101010", "BP-101010W", "BP-121212", "BP-141414", "BP-141414W", "BP-161616", "BP-161616W", "BP-181818", "BP-181818W", "BP-202020", "BP-202020W", "BP-242424", "BP-242424W", "BP-444", "BP-444W", "BP-666", "BP-666W", "BP-888", "BP-888W", "LB-LB122", "MB-MB124", "MB-MB249", "MB-MB256", "MB-MB426", "MB-SB124", "SB-SB122", "CM-210", "CM-211", "CM-212", "CM-213", "CM-214", "CM-215", "CM-217", "CM-218", "CM-219", "CM-220", "CM-221", "CM-222", "CM-223", "CM-224", "CM-225", "CM-226", "CM-227"));
        subCategories.put("Mailing Boxes", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "Variety of sizes available");
        subCategory.put("min", 0);
        subCategory.put("categoryId", "MAILERS");
        subCategory.put("products", Arrays.asList("BP-M1018K", "BP-M1081", "BP-M1182", "BP-M1182K", "BP-M1184", "BP-M1184K", "BP-M1291K", "BP-M12932BFK", "BP-M2BK", "BP-M13104", "CM-249", "CM-250", "PM-204", "PM-205", "PM-206", "PM-207", "PM-208", "PM-209"));
        subCategories.put("Fold Over Mailers", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "Variety of sizes available");
        subCategory.put("min", 0);
        subCategory.put("categoryId", "MAILERS");
        subCategory.put("products", Arrays.asList("CM-291", "CM-292", "CM-293", "CM-294", "CM-304", "CM-305", "CM-306", "CM-307", "CM-308"));
        subCategories.put("Tuck Flap Mailers", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "Variety of sizes available");
        subCategory.put("min", 0);
        subCategory.put("categoryId", "MAILERS");
        subCategory.put("products", Arrays.asList("BP-P2012B", "BP-P2012BL", "BP-P2012G", "BP-P2012GO", "BP-P2012K", "BP-P2012R", "BP-P2012W", "BP-P2012Y", "BP-P2018B", "BP-P2018BL", "BP-P2018G", "BP-P2018GO", "BP-P2018K", "BP-P2018R", "BP-P2018W", "BP-P2018Y", "BP-P2024B", "BP-P2024BL", "BP-P2024G", "BP-P2024GO", "BP-P2024K", "BP-P2024R", "BP-P2024W", "BP-P2024Y", "BP-P3024B", "BP-P3024BL", "BP-P3024G", "BP-P3024GO", "BP-P3024K", "BP-P3024R", "BP-P3024W", "BP-P3024Y", "BP-P3026K", "BP-P3026W", "BP-P3036B", "BP-P3036BL", "BP-P3036G", "BP-P3036GO", "BP-P3036K", "BP-P3036R", "BP-P3036W", "BP-P3036Y", "BP-P4036K", "BP-P4036W", "BP-P4048K", "BP-P4048W"));
        subCategories.put("Mailing Tubes", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "Variety of sizes available");
        subCategory.put("min", 0);
        subCategory.put("categoryId", "MAILERS");
        subCategory.put("products", Arrays.asList("MT-257", "MT-258", "MT-259", "MT-260", "MT-261", "MT-262", "MT-263", "MT-264", "MT-265", "MT-266", "MT-267", "MT-268", "MT-269", "MT-270", "MT-271", "MT-272", "MT-313", "MT-314", "MT-315", "MT-317", "MT-318", "MT-319", "MT-320", "MT-321", "MT-322", "MT-323", "MT-324", "MT-325", "MT-326", "MT-327", "MT-328", "MT-329", "MT-330", "MT-331", "MT-332", "MT-333", "MT-334", "MT-335"));
        subCategories.put("Crimped End Tubes", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "Variety of sizes available");
        subCategory.put("min", 0);
        subCategory.put("categoryId", "MAILERS");
        subCategory.put("products", Arrays.asList("MIR-GB442W-BP", "MIR-GB444BK-BP", "MIR-GB444HR-BP", "MIR-GB666BK-BP", "MIR-GB666HR-BP", "MIR-GB666W-BP"));
        subCategories.put("Gift Boxes", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "Variety of sizes available");
        subCategory.put("min", 0);
        subCategory.put("categoryId", "MAILERS");
        subCategory.put("products", Arrays.asList("CM-1611", "CM-1612", "CM-2616", "CM-1613", "CM-1614"));
        subCategories.put("Custom Mailers", subCategory);

        category.put("subCategories", subCategories);
        blankCategories.put("MAILERS", category);

        category = new LinkedHashMap<>();
        category.put("friendlyUrl", "envelopes");
        category.put("seoH1", "Envelopes");
        category.put("seoH2", "Use our Jumbo and Expansion Envelopes to send flat or bulky materials, like certificates, catologs, folders, important business documents and more. Available in a variety of sizes for any need.");

        subCategories = new LinkedHashMap<>();

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "Various Sizes");
        subCategory.put("min", 0);
        subCategory.put("categoryId", "ENVELOPES");
        subCategory.put("products", Arrays.asList("41319", "78642", "81608", "82964", "88982", "91867", "22663", "78650", "76104", "25552", "41520", "41823"));
        subCategories.put("Jumbo Envelopes", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "Various Sizes");
        subCategory.put("min", 0);
        subCategory.put("categoryId", "ENVELOPES");
        subCategory.put("products", Arrays.asList("EXP-10122B-40BK", "EXP-10131-26H", "EXP-10131B-40WK", "EXP-10132B-26H", "EXP-10132B-40BK", "EXP-10132B-40WK", "EXP-0257PL", "EXP-0265PL", "EXP-0275PL", "EXP-0214PL", "EXP-1608PL", "EXP-9122-26H", "EXP-0205PL", "EXP-0222PL", "EXP-0288PL", "EXP-0215PL", "EXP-1615PL", "EXP-0282PL", "EXP-1607PL", "EXP-1639PL", "EXP-9121B-40WK", "EXP-12153-40BK", "EXP-0251PL", "EXP-0220PL", "EX-10152B-40WK", "EXP-1013112-32WK", "EXP-10132-26H", "EXP-1013112-26H"));
        subCategories.put("Expansion Envelopes", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "Various Sizes");
        subCategory.put("min", 0);
        subCategory.put("categoryId", "ENVELOPES");
        subCategory.put("products", Arrays.asList("75795", "75936"));
        subCategories.put("Tyvek Envelopes", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "Various Sizes");
        subCategory.put("min", 0);
        subCategory.put("categoryId", "ENVELOPES");
        subCategory.put("products", Arrays.asList("LE-LY122", "LE-LY124", "ME-LY141", "ME-LY256", "ME-LY401", "ME-MY122", "ME-MY124", "ME-MY256", "ME-MY401", "ME-SY256", "SE-SY139"));
        subCategories.put("Padded Mailing Envelopes", subCategory);

        category.put("subCategories", subCategories);
        blankCategories.put("ENVELOPES", category);


        category = new LinkedHashMap<>();
        category.put("friendlyUrl", "papers");
        category.put("seoH1", "Paper & Cardstock");
        category.put("seoH2", "Browse our assorted variety packs of premium 8 1/2” x 11” paper and cardstock, available in different themed assortments for any business, social or crafting need.");

        subCategories = new LinkedHashMap<>();

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "8 1/2\" x 11\"");
        subCategory.put("min", 0);
        subCategory.put("categoryId", "PAPERS");
        subCategory.put("products", Arrays.asList("81211-P-VARIETY", "81211-P-RESUME", "81211-P-StPatricks", "81211-P-VALENTINES"));
        subCategories.put("Paper", subCategory);

        subCategory = new HashMap<>();
        subCategory.put("bullet1", "8 1/2\" x 11\"");
        subCategory.put("min", 0);
        subCategory.put("categoryId", "PAPERS");
        subCategory.put("products", Arrays.asList("81211-C-VARIETY", "81211-C-METALLICS", "81211-C-BRIGHTS", "81211-C-UNICORN", "81211-C-HOLIDAY", "81211-C-STPATRICKS", "81211-C-VALENTINES"));
        subCategories.put("Cardstock", subCategory);

        category.put("subCategories", subCategories);
        blankCategories.put("PAPERS", category);


    }
}
