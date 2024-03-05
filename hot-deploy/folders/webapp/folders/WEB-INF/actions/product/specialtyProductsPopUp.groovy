import com.envelopes.product.Product

Product product = new Product(delegator, dispatcher, requestParameters.productId, request);

context.product = product;