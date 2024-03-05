String module = "users.groovy"
import com.envelopes.party.PartyEvents;

if(request.getSession().getAttribute("userLogin") != null) {
    request.setAttribute("saveResponse", true);
    PartyEvents.getUserList(request, response, true);
    context.users = request.getAttribute("savedResponse").get("data");
}