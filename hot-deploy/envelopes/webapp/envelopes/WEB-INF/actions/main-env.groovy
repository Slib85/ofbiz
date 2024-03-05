import com.envelopes.product.ProductEvents;

//context.topRatedProducts =  ProductEvents.getDynamicTopRatedProducts(request, response);
context.topRatedProducts =  ProductEvents.getStaticTopRatedProducts(request, response);

List staticCategoryDesigns = new ArrayList();

Map staticCategoryDesignsMap = new HashMap();
staticCategoryDesignsMap.put("designId", "14937");
staticCategoryDesignsMap.put("productId", "43687");
staticCategoryDesignsMap.put("name", "#10 Regular");
staticCategoryDesignsMap.put("imgName", "10RegularReturnAddress");
staticCategoryDesignsMap.put("type", "Return Address");
staticCategoryDesignsMap.put("colorCount", "75");
staticCategoryDesigns.add(staticCategoryDesignsMap);

staticCategoryDesignsMap = new HashMap();
staticCategoryDesignsMap.put("designId", "14945");
staticCategoryDesignsMap.put("productId", "4860-80W");
staticCategoryDesignsMap.put("name", "#10 Square Flap");
staticCategoryDesignsMap.put("imgName", "10SquareFlapReturnAddress");
staticCategoryDesignsMap.put("type", "Return Address");
staticCategoryDesignsMap.put("colorCount", "79");
staticCategoryDesigns.add(staticCategoryDesignsMap);

staticCategoryDesignsMap = new HashMap();
staticCategoryDesignsMap.put("designId", "14948");
staticCategoryDesignsMap.put("productId", "43703");
staticCategoryDesignsMap.put("name", "#10 Window");
staticCategoryDesignsMap.put("imgName", "10WindowReturnAddress");
staticCategoryDesignsMap.put("type", "Return Address");
staticCategoryDesignsMap.put("colorCount", "55");
staticCategoryDesigns.add(staticCategoryDesignsMap);

staticCategoryDesignsMap = new HashMap();
staticCategoryDesignsMap.put("designId", "14938");
staticCategoryDesignsMap.put("productId", "43687");
staticCategoryDesignsMap.put("name", "#10 Regular");
staticCategoryDesignsMap.put("imgName", "10RegularReplyAddress");
staticCategoryDesignsMap.put("type", "Reply Address");
staticCategoryDesignsMap.put("colorCount", "75");
staticCategoryDesigns.add(staticCategoryDesignsMap);

staticCategoryDesignsMap = new HashMap();
staticCategoryDesignsMap.put("designId", "14947");
staticCategoryDesignsMap.put("productId", "4860-80W");
staticCategoryDesignsMap.put("productId", "4860-80W");
staticCategoryDesignsMap.put("name", "#10 Square Flap");
staticCategoryDesignsMap.put("imgName", "10SquareFlapReplyAddress");
staticCategoryDesignsMap.put("type", "Reply Address");
staticCategoryDesignsMap.put("colorCount", "79");
staticCategoryDesigns.add(staticCategoryDesignsMap);

staticCategoryDesignsMap = new HashMap();
staticCategoryDesignsMap.put("designId", "15460");
staticCategoryDesignsMap.put("productId", "94623");
staticCategoryDesignsMap.put("name", "#1 Coin Envelope");
staticCategoryDesignsMap.put("imgName", "1CoinEnvelopes");
staticCategoryDesignsMap.put("type", "Custom Printing");
staticCategoryDesignsMap.put("colorCount", "57");
staticCategoryDesigns.add(staticCategoryDesignsMap);

staticCategoryDesignsMap = new HashMap();
staticCategoryDesignsMap.put("designId", "15384");
staticCategoryDesignsMap.put("productId", "72940");
staticCategoryDesignsMap.put("name", "A7 Square Flap");
staticCategoryDesignsMap.put("imgName", "A7SquareFlapReturnAddress");
staticCategoryDesignsMap.put("type", "Return Address");
staticCategoryDesignsMap.put("colorCount", "160");
staticCategoryDesigns.add(staticCategoryDesignsMap);

staticCategoryDesignsMap = new HashMap();
staticCategoryDesignsMap.put("designId", "15383");
staticCategoryDesignsMap.put("productId", "72940");
staticCategoryDesignsMap.put("name", "A7 Square Flap");
staticCategoryDesignsMap.put("imgName", "A7SquareFlapReplyAddress");
staticCategoryDesignsMap.put("type", "Reply Address");
staticCategoryDesignsMap.put("colorCount", "160");
staticCategoryDesigns.add(staticCategoryDesignsMap);

staticCategoryDesignsMap = new HashMap();
staticCategoryDesignsMap.put("designId", "15386");
staticCategoryDesignsMap.put("productId", "72940");
staticCategoryDesignsMap.put("name", "A7 Square Flap");
staticCategoryDesignsMap.put("imgName", "A7SquareFlapBackFlapAddress");
staticCategoryDesignsMap.put("type", "Back Flap Address");
staticCategoryDesignsMap.put("colorCount", "160");
staticCategoryDesigns.add(staticCategoryDesignsMap);

staticCategoryDesignsMap = new HashMap();
staticCategoryDesignsMap.put("designId", "14947");
staticCategoryDesignsMap.put("productId", "4860-80W");
staticCategoryDesignsMap.put("productId", "4860-80W");
staticCategoryDesignsMap.put("name", "#10 Square Flap");
staticCategoryDesignsMap.put("imgName", "10SquareFlapBackFlapAddress");
staticCategoryDesignsMap.put("type", "Back Flap Address");
staticCategoryDesignsMap.put("colorCount", "79");
staticCategoryDesigns.add(staticCategoryDesignsMap);

context.staticCategoryDesigns = staticCategoryDesigns;
