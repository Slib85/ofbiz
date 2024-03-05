//quill orders fix fee


function sleep(delay) {
	var start = new Date().getTime();
	while (new Date().getTime() < start + delay);
}

function roundTo2(amt){
	return Math.round(parseFloat(amt * 100)) / 100 ;
}

var count = 0;
var orders = nlapiSearchRecord('salesorder', null, [new nlobjSearchFilter('entity',null,'is',1038240), new nlobjSearchFilter('datecreated',null,'on','1/8/2014')]);

var unique_orders = {};
for (var i = 0; i < orders.length; i++){
	unique_orders[orders[i].getId()] = 1;
}

console.log('FOUND: ' + Object.keys(unique_orders).length + ' orders');

for (var id in unique_orders) {
	console.log(++count);
	//if (count > 1) continue;

	var order = nlapiLoadRecord('salesorder', id);
	var orderAmt = 0;
	var feeAmt = 0;
	var hasFee = false;

	for(var i in order.lineitems.item){
		if(order.lineitems.item[i].item_display == "Quill Fees" && order.lineitems.item[i].quantity != 1){
			hasFee = true;
		}
	}

	if (hasFee && (order.lineitems.item.length - 1) % 2 ==0){
		console.log("ORDER: " + id + " needs adjustment.");
		console.log("Number of Items: " + (order.lineitems.item.length-1));
		for(var i in order.lineitems.item){
			i = parseFloat(i);
			if (i%2==0) continue;

			console.log("Orginal Price: " + order.lineitems.item[i].amount + "\t\tOrginal Fee: " + order.lineitems.item[i+1].amount);

			var newPrice = roundTo2((parseFloat(order.lineitems.item[i].amount) + parseFloat(order.lineitems.item[i+1].amount)) / 0.65);
			var newFee = roundTo2(newPrice * 0.35 * -1);

			console.log("NEW Price: " + newPrice+ "\t\t\tNEW Fee: " + newFee);

			order.setLineItemValue('item', 'amount', i, newPrice);
			order.setLineItemValue('item', 'amount', i+1, newFee);
			order.setLineItemValue('item', 'quantity', i+1, 1);
			nlapiSubmitRecord(order);
		}
	}
}




