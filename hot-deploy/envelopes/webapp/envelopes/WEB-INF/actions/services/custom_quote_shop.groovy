String module = "custom_quote_shop.groovy";

List breadcrumbs = new ArrayList();
Map breadcrumb = new HashMap();

breadcrumb.put("name" , (context.shopName == "lqo" ? "Large Quantity Orders" : "Custom Quote Shop"));
breadcrumb.put("link", "");
breadcrumbs.add(breadcrumb);

context.breadcrumbs = breadcrumbs;
