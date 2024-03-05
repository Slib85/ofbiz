import java.util.*;
import org.apache.ofbiz.entity.*;
import com.envelopes.plating.*;

String module = "pressMan.groovy";

List<Map<String, Object>> stats = new ArrayList<>();
try {
	stats = PlateHelper.pressmanStats(request);
} catch(Exception e) {
	//
}

context.stats = stats;
