// Copy tranid into custbody_amazon_order_id

var count = 0;
var orders = nlapiSearchRecord('salesorder', null, [new nlobjSearchFilter('entity',null,'is',1038240), new nlobjSearchFilter('datecreated',null,'onorafter','12/26/2013')]);

var unique_orders = {};
for (var i = 0; i < orders.length; i++){
	unique_orders[orders[i].getId()] = 1;
}
console.log('FOUND: ' + Object.keys(unique_orders).length + ' orders.');
for (var id in unique_orders) {
	console.log(++count);
	if (count < 127 ) continue;

	var order = nlapiLoadRecord('salesorder', id);
	console.log('ORDER ID: ' + id);

	if (order.fields.custbody_amazon_order_id == undefined){
		order.setFieldValue('custbody_amazon_order_id', order.fields.tranid);
		nlapiSubmitRecord(order);
	}
}