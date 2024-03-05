String module = "design-templates.groovy";
import com.envelopes.refinements.*;
context.templatesCategories = RefinementsUtil.getDesignTemplates(request);

List breadcrumbs = new ArrayList();
Map breadcrumb = new HashMap();
breadcrumb.put("name" , (context.alternatePage == "size-guide" ? "Envelope Sizes" : "Free Downloadable Envelope Templates"));
breadcrumb.put("link", "");
breadcrumbs.add(breadcrumb);
context.breadcrumbs = breadcrumbs;

Map<String, Object> categories = new HashMap();
Map<String, String> categoryInfo = new HashMap();
categoryInfo.put("description", "Square Flap envelopes are styled with an elegant and distinctive square flap on the back of the envelope. Square Flap Envelopes come in all the standard invitation sizes and a broad array of colors. These envelopes are ideal for sending invitations, greeting cards, announcements, photos, and other personalized communications. Available in many colors including LuxColors, LuxTextures, LuxGrocery Bag, LuxBlack, LuxFoil Lined and Metallics.");
categoryInfo.put("link", "category/~category_id=SQUARE_FLAP");
categories.put("Square Flap Envelopes", categoryInfo);

categoryInfo = new HashMap();
categoryInfo.put("description", "Contour Flap Envelopes feature a deep triangular flap which nearly touches the bottom of the envelope, offset by a smooth curve at the tip of the flap. Contour Flaps give a luxurious feel and standout design to go with any invitation, announcement, or greeting card. The envelopes come in all the standard sizes for invitation envelopes so they will perfectly match the size of all of our invitations. Choose from a wide range of colors, including LuxColors, LuxTextures, LuxGrocery Bag, LuxBlack, LuxFoil Lined and Metallics.");
categoryInfo.put("link", "category/~category_id=CONTOUR_FLAP");
categories.put("Contour Flap Envelopes", categoryInfo);

categoryInfo = new HashMap();
categoryInfo.put("description", "Regular Envelopes are the standard of business envelopes. This product range includes the popular #10 envelope which measures 4 1/8\" by 9 1/2\". All regular envelopes feature a solid front with no window, and a basic flap on the back. Some Regular Envelopes have special features, including security tint to conceal the contents and pre-moistened adhesive flaps for easy closure. Businesses use these envelopes to send invoices, checks, letters, and other types of mailings. Choose from a wide range of colors, including LuxColors, LuxGrocery Bag, LuxBlack, and Metallics.");
categoryInfo.put("link", "category/~category_id=REGULAR");
categories.put("Regular Envelopes", categoryInfo);

categoryInfo = new HashMap();
categoryInfo.put("description", "Window Envelopes feature at least one opening on the front of the envelope that allows the contents inside the envelope to show through. Single Window Envelopes have just one opening for the mailing address, whereas Double Window Envelopes have an opening for the return address as well. Full Face Envelopes have an opening on the entire front of the envelope, giving a view of the contents inside. Choose from a wide array of sizes and colors including LuxColors, LuxGrocery Bag, LuxBlack, and Metallics to coordinate with your mailing needs, including Window Envelopes designed for standard business checks.");
categoryInfo.put("link", "category/~category_id=WINDOW");
categories.put("Window Envelopes", categoryInfo);

categoryInfo = new HashMap();
categoryInfo.put("description", "Square Envelopes are square and symmetrical, appealing to the eye and very stylish. Most square styles have square flaps on the back, but a few have deep triangular contour flaps that nicely offset the Square Envelope. Sizes range from miniature 3 1/4\" Square Envelopes to calendar size 12 1/2\" Square Envelopes. Choose from a wide range of colors, including LuxColors, LuxTextures, LuxGrocery Bag, LuxBlack, and Metallics. Keep in mind that when mailing square envelopes through the USPS, you will need additional postage because of the shape.");
categoryInfo.put("link", "category/~category_id=SQUARE");
categories.put("Square Envelopes", categoryInfo);

categoryInfo = new HashMap();
categoryInfo.put("description", "The main feature of Open End Envelopes, also known as catalog envelopes, is the placement of the envelope flap on the short end of the envelope. This design makes it easy and secure to insert and remove contents from the envelope. Choose from a wide array of sizes, from the smaller #1 Coin envelopes to standard #10 envelopes, 9 x 12 envelopes, and jumbo envelopes. Select the appropriate weight and color for your mailing needs. Open End Envelopes are available with adhesive Peel & Seel™; closures to make sealing your envelopes easy. Choose from a wide range of colors, including LuxColors, LuxGrocery Bag, LuxBlack, Brown Kraft, Tyvek and Metallics.");
categoryInfo.put("link", "category/~category_id=OPEN_END");
categories.put("Open End Envelopes", categoryInfo);

categoryInfo = new HashMap();
categoryInfo.put("description", "Clasp Envelopes feature a durable, double-prong metal clasp and reinforced eyelet and a heavily gummed flap closure and a reusable. These are perfect for office, school and home use. Choose from a wide range of colors, including LuxColors, LuxGrocery Bag, LuxBlack, and Brown Kraft.");
categoryInfo.put("link", "category/~category_id=CLASP");
categories.put("Clasp Envelopes", categoryInfo);

categoryInfo = new HashMap();
categoryInfo.put("description", "A flap on the long side of Booklet Envelopes makes it easy to enclose a brochure, catalog, a thick stack of paper or large business communications inside. Booklet Envelopes are available in a wide range of sizes, including 6 x 9, 9 x 12, and 10 x 13. The most popular sizes offer many colors to choose from to help coordinate envelopes with the visual branding of a business including LuxColors, LuxGrocery Bag, LuxBlack, and Brown Kraft.");
categoryInfo.put("link", "category/~category_id=BOOKLET");
categories.put("Booklet Envelopes", categoryInfo);

categoryInfo = new HashMap();
categoryInfo.put("description", "Document Envelopes are sized to accommodate papers that are flat but bulky, like magazines, catalogs, booklets, brochures, pamphlets, or other large stacks of paper. Envelopes open on the long side and the extra wide 3-inch flap securely holds the envelope closed. Mail with confidence that your documents will make it safely to their destination, thanks to thick and durable kraft paper and the Zip Stick™ adhesive closure on the flap. Choose from several common sizes of document envelopes, like 9 x 12 and 10 x 13.");
categoryInfo.put("link", "category/~category_id=DOCUMENT");
categories.put("Document Envelopes", categoryInfo);

categoryInfo = new HashMap();
categoryInfo.put("description", "Remittance envelopes (also known as donation envelopes) have very large flaps so the envelope itself can be used as a form to collect important information. The form can be printed on the inside of the flap and the back of the envelope. When the envelope is sealed, the form is hidden. Alternately, 2-way remittance envelopes have a tear-off form that can be enclosed in the envelope before mailing. This type of envelope is commonly used by charities, schools, churches, fundraising groups, or as order forms for businesses. Most Remittance envelopes are available in 24lb Bright White and pastel colors. Choose to have your Remittance Envelopes printed with your customized form and avoid having to create an additional enclosure.");
categoryInfo.put("link", "category/~category_id=REMITTANCE");
categories.put("Remittance Envelopes", categoryInfo);

categoryInfo = new HashMap();
categoryInfo.put("description", "Pointed Flap Envelopes and Baronials feature crisp edges and pointed triangular flap, giving a classic, traditional look appropriate for any elegant invitation. Choose from a variety of Pointed Flap and Baronial sizes. Colors range from bright white and natural colors to LuxColors, LuxTextures, LuxGrocery Bag, LuxBlack, and Metallics. When sending formal invitations, you can also order Pointed Flap inner envelopes without sealing glue, to smoothly fit inside your outer envelopes.");
categoryInfo.put("link", "category/~category_id=POINTED_FLAP");
categories.put("Pointed Flap Envelopes", categoryInfo);

categoryInfo = new HashMap();
categoryInfo.put("description", "Mini Envelopes are perfect for holding small folded and flat cards. Coordinate with your seating cards or place cards at your next holiday meal, or to hold tiny greeting cards, gift cards, business cards, and more! Although they are small in size, they pack a big punch in style, with bright colors and metallic options. Colors range from from bright white and natural colors to LuxColors, LuxTextures, LuxGrocery Bag, LuxBlack, and Metallics. Remember that Mini Envelopes are not mailable through the USPS!");
categoryInfo.put("link", "category/~category_id=MINI_ENVELOPES");
categories.put("Mini Envelopes", categoryInfo);

categoryInfo = new HashMap();
categoryInfo.put("description", "The LUXLined silver, gold, black, or red extends down the flap and into the envelope, to within 1/4\" of the bottom of the envelope. LuxLined envelopes add a surprise splash of metallic color to most of the standard sizes of invitation envelopes. Choose the perfect color combination of envelope and elegant lining to coordinate with your invitations, photo greeting cards, holiday letters, or special notes. Add customized printing of your return address on the front or flap of the envelope for a special touch that will also save valuable time.");
categoryInfo.put("link", "category/~category_id=LINED?af=st:lined%20col:linedenvelopes");
categories.put("Lined Envelopes", categoryInfo);

categoryInfo = new HashMap();
categoryInfo.put("description", "Combining the sleek sophistication of high-quality cardstock and the simplicity of a blank card, folded cards are easily customizable. The front of card may be personalized with text, logos and designs, while the inside can be left blank for message or printed. Folded cards are ideal for a variety of usages, including response cards, informal note cards and more. Folded cards are available in a variety of colors and textures including LuxColors, LuxTextures, LuxGrocery Bag, LuxBlack, and Metallics.");
categoryInfo.put("link", "category/~category_id=FOLDED_CARDS");
categories.put("Folded Cards", categoryInfo);

categoryInfo = new HashMap();
categoryInfo.put("description", "Find flat cards and folded cards in the most common envelope sizes, including mini cards, square cards, A1 cards, and A7 cards. Notecards are available in a rainbow of colors, including LuxColors, LuxTextures, LuxGrocery Bag, LuxBlack, and Metallics. Border cards are also available, which have a bright border printed around the edge of the card to frame the space for the note in the center. All notecards are made of high-quality cardstock or heavy weight paper so they will be durable and have a luxurious feel in the hand. Stock up on notecards for DIY invitations, business communications, and personal use.");
categoryInfo.put("link", "category/~category_id=NOTECARDS");
categories.put("Notecards", categoryInfo);

categoryInfo = new HashMap();
categoryInfo.put("description", "These envelopes are the perfect choice when mailing items too thick to fit in a standard envelope, like books, financial reports, and gifts! With an accordion-folded side panel designed to expand anywhere from 1 to 4 inches to accommodate your materials, they still stack flat taking up very little storage space in your office. Choose from durable white or brown kraft paper, or lightweight and tear-resistant Tyvek® material. All expansion envelopes have a Zip Stick® self-adhesive flap on the short side to help you close the envelope securely.");
categoryInfo.put("link", "category/~category_id=EXPANSION");
categories.put("Expansion Envelopes", categoryInfo);

categoryInfo = new HashMap();
categoryInfo.put("description", "From flat or folded mini cards to business cards, invitations, scrapbook paper, restaurant menus or any other DIY project, we've got you covered. Over 100 colors, sizes and textures available for all your paper and cardstock needs.");
categoryInfo.put("link", "category/~category_id=CARDSTOCK");
categories.put("Cardstock", categoryInfo);

categoryInfo = new HashMap();
categoryInfo.put("description", "Durable, moisture resistant, co-extruded polyethylene, just the thing for mailing clothing and other soft goods, documents, metal or plastic parts, and pharmaceuticals.");
categoryInfo.put("link", "category/~category_id=PLASTIC_MAILERS");
categories.put("Poly Mailers", categoryInfo);

categoryInfo = new HashMap();
categoryInfo.put("description", "From 9 x 12 Presentation folders for presenting proposals, branding your business and delivering reports to smaller pocket folders for promotions, Envelopes.com offers a variety of folders for your business and personal needs. Custom printed folders are perfect for corporate events, branding your accounting firm, law practices, educational institutions and more. All folders are created from premium cardstock in an array of beautiful colors and textures. Available both plain and with custom printing in large and small quantities.");
categoryInfo.put("link", "category?af=st:folders");
categoryInfo.put("alternateName", "Folders");
categories.put("Folders Main Category", categoryInfo);

context.categories = categories;