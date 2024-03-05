import com.envelopes.product.*;

String module = "taxEnvelopesShop.groovy";

List breadcrumbs = new ArrayList();
Map breadcrumb = new HashMap();
breadcrumb.put("name" , "The Tax Envelope Shop");
breadcrumb.put("link", "");
breadcrumbs.add(breadcrumb);

context.breadcrumbs = breadcrumbs;

List<Product> w2And1099Envelopes = new ArrayList<>();
w2And1099Envelopes.add(new Product(delegator, dispatcher, "7489-T4-TAX", request));
w2And1099Envelopes.add(new Product(delegator, dispatcher, "7487-W2", request));
w2And1099Envelopes.add(new Product(delegator, dispatcher, "7486-W2", request));
w2And1099Envelopes.add(new Product(delegator, dispatcher, "7485-W2", request));
w2And1099Envelopes.add(new Product(delegator, dispatcher, "7489-W2", request));
w2And1099Envelopes.add(new Product(delegator, dispatcher, "WS-7496", request));
w2And1099Envelopes.add(new Product(delegator, dispatcher, "WS-7494", request));
w2And1099Envelopes.add(new Product(delegator, dispatcher, "WS-7484", request));
w2And1099Envelopes.add(new Product(delegator, dispatcher, "7489-W2-TAX", request));
w2And1099Envelopes.add(new Product(delegator, dispatcher, "WS-7494-TAX", request));
w2And1099Envelopes.add(new Product(delegator, dispatcher, "WS-7484-TAX", request));
w2And1099Envelopes.add(new Product(delegator, dispatcher, "75746-TAX", request));
w2And1099Envelopes.add(new Product(delegator, dispatcher, "11874-IRS", request));
w2And1099Envelopes.add(new Product(delegator, dispatcher, "11874-TAX", request));
w2And1099Envelopes.add(new Product(delegator, dispatcher, "8193-IRS", request));
w2And1099Envelopes.add(new Product(delegator, dispatcher, "8193-TAX", request));
context.w2And1099Envelopes = w2And1099Envelopes;

Map<String, Object> regularAndWindowEnvelopesMap = new HashMap<>();
List<Product> regularAndWindowEnvelopes = new ArrayList<>();
regularAndWindowEnvelopes.add(new Product(delegator, dispatcher, "45146", request));
regularAndWindowEnvelopes.add(new Product(delegator, dispatcher, "43703", request));
regularAndWindowEnvelopes.add(new Product(delegator, dispatcher, "4860-70W", request));
regularAndWindowEnvelopes.add(new Product(delegator, dispatcher, "4860-80W", request));
regularAndWindowEnvelopes.add(new Product(delegator, dispatcher, "97767", request));
regularAndWindowEnvelopes.add(new Product(delegator, dispatcher, "WS-2956", request));
regularAndWindowEnvelopes.add(new Product(delegator, dispatcher, "75761", request));
regularAndWindowEnvelopes.add(new Product(delegator, dispatcher, "61597", request));
regularAndWindowEnvelopes.add(new Product(delegator, dispatcher, "92021", request));
regularAndWindowEnvelopes.add(new Product(delegator, dispatcher, "WS-2652", request));
regularAndWindowEnvelopes.add(new Product(delegator, dispatcher, "WS-3322", request));
regularAndWindowEnvelopes.add(new Product(delegator, dispatcher, "45161", request));
regularAndWindowEnvelopes.add(new Product(delegator, dispatcher, "99977", request));
regularAndWindowEnvelopes.add(new Product(delegator, dispatcher, "92908", request));
regularAndWindowEnvelopes.add(new Product(delegator, dispatcher, "75747", request));
regularAndWindowEnvelopes.add(new Product(delegator, dispatcher, "61538", request));
regularAndWindowEnvelopes.add(new Product(delegator, dispatcher, "61549", request));
regularAndWindowEnvelopes.add(new Product(delegator, dispatcher, "61537", request));
regularAndWindowEnvelopes.add(new Product(delegator, dispatcher, "48643", request));
regularAndWindowEnvelopes.add(new Product(delegator, dispatcher, "734R-ST", request));
regularAndWindowEnvelopes.add(new Product(delegator, dispatcher, "WS-1128", request));
regularAndWindowEnvelopes.add(new Product(delegator, dispatcher, "61532", request));
regularAndWindowEnvelopes.add(new Product(delegator, dispatcher, "WS-0056", request));
regularAndWindowEnvelopes.add(new Product(delegator, dispatcher, "17905", request));
regularAndWindowEnvelopes.add(new Product(delegator, dispatcher, "72634", request));
regularAndWindowEnvelopes.add(new Product(delegator, dispatcher, "45179-ST", request));
regularAndWindowEnvelopes.add(new Product(delegator, dispatcher, "43675-ST", request));
regularAndWindowEnvelopes.add(new Product(delegator, dispatcher, "14R-WST", request));
regularAndWindowEnvelopes.add(new Product(delegator, dispatcher, "15R-W", request));
regularAndWindowEnvelopes.add(new Product(delegator, dispatcher, "69BW-200", request));

context.regularAndWindowEnvelopes = regularAndWindowEnvelopes;

Map<String, Object> openEndAndBookletEnvelopesMap = new HashMap<>();
List<Product> openEndAndBookletEnvelopes = new ArrayList<>();
openEndAndBookletEnvelopes.add(new Product(delegator, dispatcher, "1590-32IJ", request));
openEndAndBookletEnvelopes.add(new Product(delegator, dispatcher, "10157", request));
openEndAndBookletEnvelopes.add(new Product(delegator, dispatcher, "WS-4918", request));
openEndAndBookletEnvelopes.add(new Product(delegator, dispatcher, "1590-WLI", request));
openEndAndBookletEnvelopes.add(new Product(delegator, dispatcher, "912-SAT", request));
openEndAndBookletEnvelopes.add(new Product(delegator, dispatcher, "912CWIN", request));
openEndAndBookletEnvelopes.add(new Product(delegator, dispatcher, "4899-WLI", request));
openEndAndBookletEnvelopes.add(new Product(delegator, dispatcher, "4894-AIR", request));
openEndAndBookletEnvelopes.add(new Product(delegator, dispatcher, "75407", request));
openEndAndBookletEnvelopes.add(new Product(delegator, dispatcher, "1590", request));
openEndAndBookletEnvelopes.add(new Product(delegator, dispatcher, "1590BK", request));
openEndAndBookletEnvelopes.add(new Product(delegator, dispatcher, "WS-4894-ST", request));
openEndAndBookletEnvelopes.add(new Product(delegator, dispatcher, "49783", request));
openEndAndBookletEnvelopes.add(new Product(delegator, dispatcher, "14554", request));
openEndAndBookletEnvelopes.add(new Product(delegator, dispatcher, "49783", request));
openEndAndBookletEnvelopes.add(new Product(delegator, dispatcher, "16139", request));
openEndAndBookletEnvelopes.add(new Product(delegator, dispatcher, "69BW-200", request));
context.openEndAndBookletEnvelopes = openEndAndBookletEnvelopes;

Map<String, Object> tyvekEnvelopesMap = new HashMap<>();
List<Product> tyvekEnvelopes = new ArrayList<>();
tyvekEnvelopes.add(new Product(delegator, dispatcher, "PC1207FC", request));
tyvekEnvelopes.add(new Product(delegator, dispatcher, "75894", request));
tyvekEnvelopes.add(new Product(delegator, dispatcher, "75852", request));
tyvekEnvelopes.add(new Product(delegator, dispatcher, "PC1102PL", request));
tyvekEnvelopes.add(new Product(delegator, dispatcher, "PC1250PL", request));
tyvekEnvelopes.add(new Product(delegator, dispatcher, "QUAR2011", request));
tyvekEnvelopes.add(new Product(delegator, dispatcher, "QUAR2012", request));
tyvekEnvelopes.add(new Product(delegator, dispatcher, "41319", request));
tyvekEnvelopes.add(new Product(delegator, dispatcher, "41520", request));
tyvekEnvelopes.add(new Product(delegator, dispatcher, "41823", request));
tyvekEnvelopes.add(new Product(delegator, dispatcher, "EXP-1607PL", request));
tyvekEnvelopes.add(new Product(delegator, dispatcher, "EXP-1608PL", request));
tyvekEnvelopes.add(new Product(delegator, dispatcher, "EXP-1615PL", request));
tyvekEnvelopes.add(new Product(delegator, dispatcher, "EXP-1639PL", request));
tyvekEnvelopes.add(new Product(delegator, dispatcher, "PC1176FC", request));
tyvekEnvelopes.add(new Product(delegator, dispatcher, "PC1180FC", request));
tyvekEnvelopes.add(new Product(delegator, dispatcher, "PC1251PL", request));
tyvekEnvelopes.add(new Product(delegator, dispatcher, "EXP-1677PL", request));
context.tyvekEnvelopes = tyvekEnvelopes;

Map<String, Object> foldersMap = new HashMap<>();
List<Product> folders = new ArrayList<>();
folders.add(new Product(delegator, dispatcher, "TAX-912-NRTL", request));
folders.add(new Product(delegator, dispatcher, "TAX-912-FL", request));
folders.add(new Product(delegator, dispatcher, "TAX-912-NF80", request));
folders.add(new Product(delegator, dispatcher, "TAX-912-CEI80", request));
folders.add(new Product(delegator, dispatcher, "SF-101-546-TAX", request));
folders.add(new Product(delegator, dispatcher, "SF-101-DB100", request));
folders.add(new Product(delegator, dispatcher, "SF-101-CSG100", request));
folders.add(new Product(delegator, dispatcher, "PF-DBLI", request));
folders.add(new Product(delegator, dispatcher, "SF-101-DDP100", request));
folders.add(new Product(delegator, dispatcher, "PF-WLI", request));
folders.add(new Product(delegator, dispatcher, "PF-BLI", request));
folders.add(new Product(delegator, dispatcher, "SF-101-546-TANG", request));
folders.add(new Product(delegator, dispatcher, "OR-144-DDBLU100", request));
folders.add(new Product(delegator, dispatcher, "OR-144-DDBLK100", request));
folders.add(new Product(delegator, dispatcher, "OR-145-DDBLU100", request));
folders.add(new Product(delegator, dispatcher, "OR-145-CSG100", request));
folders.add(new Product(delegator, dispatcher, "OR-145-DDBLK100", request));
folders.add(new Product(delegator, dispatcher, "SF-102-DDBLU100", request));




context.folders = folders;