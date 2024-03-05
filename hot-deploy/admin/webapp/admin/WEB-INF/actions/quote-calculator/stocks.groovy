import com.bigname.quote.calculator.*;

context.stockTypes = CalculatorHelper.getAllStockTypes(delegator);

context.items = CalculatorHelper.getAllStocks(delegator);
context.itemsCount = context.items.size;