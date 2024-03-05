function sleep(delay) {
	var start = new Date().getTime();
	while (new Date().getTime() < start + delay);
}

var count = 0;
var staples_inv = nlapiSearchRecord('invoice', null, [new nlobjSearchFilter('entity',null,'is',1038240), new nlobjSearchFilter('datecreated',null,'onorafter','12/26/2013')]);

var unique_staples = {};
for (var i = 0; i < staples_inv.length; i++){
	unique_staples[staples_inv[i].getId()] = 1;
}

console.log('FOUND: ' + Object.keys(unique_staples).length + ' invoices');

return 0;

for (var id in unique_staples) {
	console.log(count++);
	if (count < 115){
		//continue;
	}
	var invoice = nlapiLoadRecord('invoice', id);
	var numItems = 0;
	var itemsTotal = 0;
	var numFees = 0;
	var feesTotal = 0;

	for(var y in invoice.lineitems.item){
		if (typeof invoice.lineitems.item[y].item_display !=="undefined"){
			if(invoice.lineitems.item[y].item_display == "Quill Fees"){
				numFees++;
				feesTotal += parseFloat(invoice.lineitems.item[y].amount) ;
			} else {
				numItems++;
				itemsTotal += parseFloat(invoice.lineitems.item[y].amount) ;
			}
		}
	}

	if (numFees != numItems){
		var order = nlapiLoadRecord('salesorder', invoice.fields.createdfrom);
		var newFee = 0;
		for(var y in order.lineitems.item){
			if (order.lineitems.item[y].item_display == "Quill Fees"){
				newFee = parseFloat(order.lineitems.item[y].amount);
			}
		}
		if (newFee != 0){
			console.log( id + ' Needs fee of: ' + newFee + ' [' + (itemsTotal * 0.35 * -1) + ']');
			var newItemId = numItems + numFees + 1;
			invoice.insertLineItem('item', newItemId);
			invoice.setLineItemValue('item', 'item', newItemId, 18444);
		    invoice.setLineItemValue('item', 'quantity', newItemId, 1);
		    invoice.setLineItemValue('item', 'amount', newItemId , newFee);
		    nlapiSubmitRecord(invoice);
		} else {
			console.log('No Fee Bro!');
		}
	} else {
		console.log( id + ' is cool.  Amount: ' + itemsTotal + '  Fees: ' + feesTotal + '  %: ' + (Math.abs(feesTotal)/itemsTotal*100));
	}
	sleep(500);
}