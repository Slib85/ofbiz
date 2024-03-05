import com.envelopes.util.EnvUtil;

String module = "new-arrivals.groovy";

Map<String, Object> params = EnvUtil.getParameterMap(request);
params.put("af", "new:Y");

context.put("params", params);
context.put("searchString", params.get("w"));

List holidayProductsList = new ArrayList<>();
holidayProductsList.add(new com.envelopes.product.Product(delegator, dispatcher, "8193-TAX", request));
holidayProductsList.add(new com.envelopes.product.Product(delegator, dispatcher, "WS-7484-TAX", request));
holidayProductsList.add(new com.envelopes.product.Product(delegator, dispatcher, "11874-TAX", request));
holidayProductsList.add(new com.envelopes.product.Product(delegator, dispatcher, "8193-IRS", request));
holidayProductsList.add(new com.envelopes.product.Product(delegator, dispatcher, "11874-IRS", request));
holidayProductsList.add(new com.envelopes.product.Product(delegator, dispatcher, "WS-7494-TAX", request));
holidayProductsList.add(new com.envelopes.product.Product(delegator, dispatcher, "7489-W2-TAX", request));
holidayProductsList.add(new com.envelopes.product.Product(delegator, dispatcher, "75746-TAX", request));
holidayProductsList.add(new com.envelopes.product.Product(delegator, dispatcher, "OFFICEBUNDLE-200PACK", request));
holidayProductsList.add(new com.envelopes.product.Product(delegator, dispatcher, "LUX-4880-COLORPACK", request));
holidayProductsList.add(new com.envelopes.product.Product(delegator, dispatcher, "Assorted", request));
context.put("holidayProducts", holidayProductsList);
