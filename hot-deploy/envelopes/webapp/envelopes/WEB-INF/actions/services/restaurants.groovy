String module = "restaurants.groovy";

List breadcrumbs = new ArrayList();
Map breadcrumb = new HashMap();
breadcrumb.put("name" , "The Restaurant Stationery Shop");
breadcrumb.put("link", "");
breadcrumbs.add(breadcrumb);

context.breadcrumbs = breadcrumbs;
