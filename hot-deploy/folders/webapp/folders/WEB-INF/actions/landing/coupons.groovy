import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.service.GenericServiceException;

emailAddress = request.getParameter("emailAddress");

if(emailAddress != "" && emailAddress != " " && emailAddress != null) {
	try {
		dispatcher.runSync("subscribeAnonToBronto", UtilMisc.toMap("emailAddress", emailAddress, "storeName", "Folders"));
		context.put("success","Y");
	} catch (GenericEntityException e) {
		context.put("success","N");
	} catch (GenericServiceException e) {
		context.put("success","N");
	}
}