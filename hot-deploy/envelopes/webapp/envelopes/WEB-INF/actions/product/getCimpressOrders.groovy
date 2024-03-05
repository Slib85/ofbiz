import com.bigname.integration.cimpress.CimpressHelper;

String module = "getCimpressOrders.groovy";

//CimpressHelper.setProductionStarted(delegator, dispatcher);
//CimpressHelper.createShipment(delegator, dispatcher);
CimpressHelper.getOrders(delegator, dispatcher);
//CimpressHelper.getAddressChangesFromCimpressTest(delegator, dispatcher);
//CimpressHelper.getShippingCarrierChangesFromCimpressTest(delegator, dispatcher);
//CimpressHelper.getCancelledOrdersFromCimpressTest(delegator, dispatcher);
