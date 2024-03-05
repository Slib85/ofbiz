String module = "team.groovy";

List breadcrumbs = new ArrayList();
Map breadcrumb = new HashMap();
breadcrumb.put("name" , "Meet the Envelopes.com Team");
breadcrumb.put("link", "");
breadcrumbs.add(breadcrumb);

context.breadcrumbs = breadcrumbs;
