import com.bigname.quote.calculator.*;
import java.util.*;

context.materialTypes = CalculatorHelper.getAllMaterialTypes(delegator);

context.items = CalculatorHelper.getAllStockTypes(delegator);
context.itemsCount = context.items.size;