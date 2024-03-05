String module = "wedding-shop.groovy";

List breadcrumbs = new ArrayList();
Map breadcrumb = new HashMap();
breadcrumb.put("name" , "The Wedding Shop");
breadcrumb.put("link", "");
breadcrumbs.add(breadcrumb);

context.breadcrumbs = breadcrumbs;
