import com.envelopes.party.PartyHelper;

String module = "saved-designs.groovy";
context.designs = PartyHelper.getSavedDesigns(request, response);