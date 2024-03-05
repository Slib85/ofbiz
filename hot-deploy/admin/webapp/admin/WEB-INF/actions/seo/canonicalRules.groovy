import org.apache.ofbiz.base.util.*;
import com.envelopes.util.EnvUtil;
import com.envelopes.seo.CanonicalHelper;

String module = "canonicalRules.groovy";

Map<String, Object> params = EnvUtil.getParameterMap(request);

context.rules = CanonicalHelper.getCanonicalRules(delegator);

if (UtilValidate.isNotEmpty((String) params.get("ruleId"))) {
    context.rule = CanonicalHelper.getCanonicalRule(delegator, (String) params.get("ruleId"));
}