import com.bigname.quote.calculator.*;

context.styleGroups = CalculatorHelper.getAllStyleGroups(delegator);

context.items = CalculatorHelper.getAllStyles(delegator);
context.itemsCount = context.items.size;