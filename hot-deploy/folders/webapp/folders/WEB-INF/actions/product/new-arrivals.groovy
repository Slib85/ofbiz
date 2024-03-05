import com.envelopes.util.EnvUtil;

String module = "new-arrivals.groovy";

Map<String, Object> params = EnvUtil.getParameterMap(request);
params.put("af", "new:Y");

context.put("params", params);
context.put("searchString", params.get("w"));