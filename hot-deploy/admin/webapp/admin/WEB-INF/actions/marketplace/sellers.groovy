import com.bigname.marketplace.*;

context.sellers = MarketplaceHelper.getAllSellers(delegator);
context.sellersCount = context.sellers.size > 1 ? context.sellers.size + " Sellers" : context.sellers.size + " Seller";