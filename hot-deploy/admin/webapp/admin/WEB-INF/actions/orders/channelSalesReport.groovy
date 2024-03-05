import java.sql.Timestamp;
import java.util.*;
import com.envelopes.util.EnvConstantsUtil;

String module = "channelSalesReport.groovy";

int i = 5;
List<String> days = new ArrayList<>();
Calendar cal = Calendar.getInstance();
days.add(EnvConstantsUtil.NON_LEADING_MDY.format(new Timestamp(cal.getTime().getTime())));
while(i > 0) {
    cal.add(Calendar.DATE, -1);
    days.add(EnvConstantsUtil.NON_LEADING_MDY.format(new Timestamp(cal.getTime().getTime())));
    i--;
}

context.days = days;