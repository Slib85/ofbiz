import com.envelopes.util.EnvUtil;

String module = "search.groovy";

Map<String, Object> params = EnvUtil.getParameterMap(request);
params.put("af", "sale:Sale sale:Clearance");

context.put("params", params);
context.put("searchString", params.get("w"));