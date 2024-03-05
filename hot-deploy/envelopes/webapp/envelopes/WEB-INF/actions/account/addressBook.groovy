import com.envelopes.party.PartyHelper;

String module = "addressBook.groovy";

context.addressBook = PartyHelper.getAddressBookWithAddresses(request, response);