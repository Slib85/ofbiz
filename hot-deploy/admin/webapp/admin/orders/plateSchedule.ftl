<link rel="stylesheet" href="/html/themes/global/vendor/bootstrap-datepicker/bootstrap-datepicker.css">

<style>
    h1, h1 a {
        color: #000;
        font: bold 14px/5px Verdana,Arial,Helvetica,sans-serif;
    }

    h1 a:hover {
    	text-decoration: underline;
    }
    #orderInfo .row:after, #bodyHead:after, #plateInfo:after, #bodyContainer:after {
        clear: both;
        content: ".";
        display: block;
        height: 0;
        visibility: hidden;
    }
	.page-container .main-content {
		padding: 5px;
	}
    .order-item {
        color: #000000;
    }

    table.ticket {
        border-collapse: collapse;
        border-spacing: 0;
        margin-bottom: 10px;
        width: 100%;
    }

    table.ticket th, table.ticket td {
        font-size: 14pt;
        padding: 9px;
    }
    table.ticket h1 {
        font-size: 12pt;
    }
    table.ticket h2 {
        color: #000;
        text-decoration: underline;
    }

	table.overview h1.left {
        float: left;
    }
	table.overview h1.right {
		float: right;
	}

	table.overview thead th, table.overview tbody th, table.overview tbody td {
        border: 1px solid #777;
        font-size: 11px;;
    }
    table.overview tbody .dark {
		background-color: #F5F5F6;
    }
    table.overview .preview {
		max-width: 100px;
        max-height: 100px;
		display: inline-block;
    }
	table.overview .workerLink {
		display: inline-block;
	}

	#startJob .imageData {
        text-align: center
    }
	#startJob .imageData div {
		display: inline-block;
		vertical-align: top
	}
	#startJob .info {
        text-align: left;
	}
	#startJob .info table {
		margin: auto
	}
	#startJob .info table td {
		border: 1px solid #ccc;
        padding: 5px;
	}

	#startJob .workers {
        text-align: center;
    }
    #startJob .image img {
		max-width: 400px;
		max-height: 400px;
        display: inline-block;
    }

	#startJob div.timer {
        text-align: center;
        margin-top: 20px;
    }
    #startJob .jobTimer, #startJob .approvalTimer {
		text-align: center;
		display: block;
		font-weight: bold;
		margin: 0 20px 0 20px;
		font-size: 20px;
    }

	.modal-dialog {
        width: 90% !important;
    }

	input[type="button"] {
        margin: 5px;
    }

    .stockOrderAndItem {
		display: inline-block;
        border: 1px solid #ccc;
        margin: 10px;
        padding: 5px;
    }

    .completedPick {
        background-color: #FFDDA1;
    }

	.orderSchedule {
		margin: 0 0 20px 0;
		width: 100%;
		padding: 5px;
		border: 1px solid #ccc;
	}
</style>

<div class="row">
	<div class="col-md-12">
        <form id="plateOrderLookup" action="" method="GET">
            <div>
                <input type="text" class="orderSchedule" name="orderSchedule" placeholder="Scan Barcode" />
            </div>
        </form>
    </div>
</div>

<table class="ticket overview">
    <thead>
        <tr>
            <th>Preview</th>
            <th>Order#</th>
            <th>Qty</th>
            <th>Ink</th>
            <th style="min-width: 165px;">Item</th>
            <th>Name</th>
            <th><a href="#" onclick="togglePlates()">Due Date</a></th>
            <th>Shipping</th>
            <th>Production Time</th>
            <th>Save Sample</th>
            <th>Remove From Schedule</th>
        </tr>
    </thead>
    <tbody>
    <#assign date = "" />
    <#list plates as plate>
        <#list plate as orderItem>
            <#if date != "" && date != orderItem.dueDate>
                <tr class="${date} <#if (date)?has_content && date?replace("&#x2f;","/")?date("MM/dd/yyyy") gt .now?date>autoHide hidden</#if>">
                    <td colspan="3" style="font-weight: bold;">STOCK REPORT FOR ${date}</td>
                    <td colspan="8">
                        <#assign cumulativeStock = stockMap.get(date) />
                        <#assign orderItemList = stockOrders.get(date) />
                        <#assign pickedOrderItemList = stockPickedOrders.get(date)?if_exists />
                        <#list cumulativeStock.keySet() as sku>
                            <#assign productGV = delegator.findOne("Product", Static["org.apache.ofbiz.base.util.UtilMisc"].toMap("productId", sku), true) />
							<div class="stockOrderAndItem <#if pickedOrderItemList?has_content && pickedOrderItemList.get(sku)?has_content && pickedOrderItemList.get(sku) == orderItemList.get(sku)>completedPick</#if>" data-orders="${orderItemList.get(sku)}"><div><strong>NAME:</strong> ${stockName.get(sku)}</div><div><strong>SKU:</strong> ${sku}</div><div><strong>QTY:</strong> ${cumulativeStock.get(sku)}</div><strong>BIN:</strong> ${productGV.binLocation?default("")}</div></div>
                        </#list>
                    </td>
                </tr>
            </#if>
            <#if orderItem_index == 0>
                <tr id="${orderItem.plateNumber}" class="<#if (orderItem.dueDate)?has_content && orderItem.dueDate?replace("&#x2f;","/")?date("MM/dd/yyyy") gt .now?date>autoHide hidden</#if>">
                    <td align="left" colspan="11">
                        <h1 class="left"><a href="<@ofbizUrl>/plateEdit</@ofbizUrl>?plateId=${orderItem.plateNumber?upper_case}">Plate # ${orderItem.plateNumber?upper_case}</a></h1>
                        <h1 class="right">Due: ${orderItem.dueDate}</h1>
                    </td>
                </tr>
            </#if>
            <tr id="${orderItem.name}" class="${orderItem.plateNumber} order-item <#if (orderItem_index % 2) == 0>dark</#if> <#if (orderItem.dueDate)?has_content && orderItem.dueDate?replace("&#x2f;","/")?date("MM/dd/yyyy") gt .now?date>autoHide hidden</#if>">
                <td class="image">
                    <img class="preview" src="<@ofbizScene7Url>/is/image/ActionEnvelope/${orderItem.sku}?wid=100&amp;hei=100&amp;fmt=jpeg&amp;bgc=f5f5f6</@ofbizScene7Url>" />
                    <#list orderItem.content as content>
                        <#if content.contentPurposeEnumId == "OIACPRP_FRONT" || content.contentPurposeEnumId == "OIACPRP_BACK">
                            <img class="preview" src="<@ofbizUrl>serveFileForStream?filePath=</@ofbizUrl>${content.contentPath}" />
                        <#elseif content.contentPurposeEnumId == "OIACPRP_WORKER_FRONT">
                            <a target="_blank" class="workerLink hidden" href="<@ofbizUrl>serveFileForStream?filePath=</@ofbizUrl>${content.contentPath}">FRONT PDF</a>
                        <#elseif content.contentPurposeEnumId == "OIACPRP_WORKER_BACK">
                            <a target="_blank" class="workerLink hidden" href="<@ofbizUrl>serveFileForStream?filePath=</@ofbizUrl>${content.contentPath}">BACK PDF</a>
                        </#if>
                    </#list>
                </td>
                <td><a class="ordernumber" href="<@ofbizUrl>/viewOrder?orderId=${orderItem.orderNumber}</@ofbizUrl>">${orderItem.orderNumber}</a></td>
                <td class="quantity">${orderItem.quantity?string["0"]}</td>
                <td class="inks <#if orderItem.inkPicked?has_content && orderItem.inkPicked == "Y">completedPick</#if>" data-order="${orderItem.orderNumber}_${orderItem.orderItemSeqId}">${orderItem.ink}</td>
                <td class="itemname">${orderItem.item}<span class="hidden comments">${orderItem.prepressComments?if_exists}</span><span class="hidden pressmancomments">${orderItem.pressmanComments?if_exists}</span></td>
                <td>${orderItem.name}</td>
                <td class="duedate">${orderItem.itemDueDate}</td>
                <td class="shipping" style="background-color: <#if (orderItem.shipping)?has_content><#if orderItem.shipping?contains("Standard")>#95EFB4<#elseif orderItem.shipping?contains("Premium")>#F6FF88<#elseif orderItem.shipping?contains("Priority")>#97FFFA<#elseif orderItem.shipping?contains("Next")>#FF3636<#elseif orderItem.shipping?contains("Fedex")>#A39DFD<#else>#fff</#if></#if>">${orderItem.shipping?if_exists}</td>
                <td><#if orderItem.productionTime?has_content && orderItem.productionTime == "Y">Rush<#else>Standard</#if> <#if orderItem.timeWarning == "Y">(2 DAY)</#if></td>
                <td class="saveprint" style="background-color: <#if (orderItem.savePrintSample)?has_content && orderItem.savePrintSample == "Y">#ffce9b</#if>"><#if (orderItem.savePrintSample)?has_content && orderItem.savePrintSample == "Y">Yes<#else>No</#if></td>
                <td>
                    <input class="start" type="button" data-backdrop="static" data-keyboard="false" data-toggle="modal" data-plateid="${orderItem.plateNumber}" data-orderid="${orderItem.orderNumber}" data-orderitemseqid="${orderItem.orderItemSeqId}" data-target="#startJob" value="Start Job">
                    <div style="white-space: nowrap;">
                        <input class="printdate" <#if (session.getAttribute("userLogin").userLoginId)?exists && (session.getAttribute("userLogin").userLoginId?starts_with("shawn"))><#else>style="display: none;"</#if> type="text" placeholder="Date Printed" data-plugin="datepicker" data-format="yyyy-mm-dd" value="">
                        <input class="remove" type="button" onclick="removeFromSchedule('${orderItem.plateNumber}', '${orderItem.orderNumber}', '${orderItem.orderItemSeqId}')" value="Printed">
                    </div>
                    <input class="add hidden" type="button" onclick="addToSchedule('${orderItem.plateNumber}', '${orderItem.orderNumber}', '${orderItem.orderItemSeqId}')" value="Re-Plate">
                </td>
            </tr>

            <#assign date = orderItem.dueDate />

            <#if !plate_has_next>
			<tr class="${date} <#if (date)?has_content && date?replace("&#x2f;","/")?date("MM/dd/yyyy") gt .now?date>autoHide hidden</#if>">
				<td colspan="3" style="font-weight: bold;">STOCK REPORT FOR ${date}</td>
				<td colspan="8">
                    <#assign cumulativeStock = stockMap.get(date) />
                    <#assign orderItemList = stockOrders.get(date) />
                    <#assign pickedOrderItemList = stockPickedOrders.get(date)?if_exists />
                    <#list cumulativeStock.keySet() as sku>
                        <#assign productGV = delegator.findOne("Product", Static["org.apache.ofbiz.base.util.UtilMisc"].toMap("productId", sku), true) />
						<div class="stockOrderAndItem <#if pickedOrderItemList?has_content && pickedOrderItemList.get(sku)?has_content && pickedOrderItemList.get(sku) == orderItemList.get(sku)>completedPick</#if>" data-orders="${orderItemList.get(sku)}"><div><strong>NAME:</strong> ${stockName.get(sku)}</div><div><strong>SKU:</strong> ${sku}</div><div><strong>QTY:</strong> ${cumulativeStock.get(sku)}</div><div><strong>BIN:</strong> ${productGV.binLocation?default("")}</div></div>
                    </#list>
				</td>
			</tr>
            </#if>
        </#list>
    </#list>
	</tbody>
</table>
<div class="modal fade" id="startJob" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">Start Job</h4>
			</div>
			<div class="modal-body row">
				<div class="workers"></div>
				<div class="imageData">
					<div class="image"></div>
					<div class="info"></div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default hidden j-btn-approval">Get Approval</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
				<button type="button" class="btn btn-primary j-btn-start">Start</button>
			</div>
		</div>
	</div>
</div>

<div class="completedJobs <#if !completedPlates?has_content>hidden</#if>">
    <div style="background-color: #A5E7A5; margin-top: 70px; padding: 20px; font-size: 20px; font-weight: bold;">
        Completed Jobs
    </div>
    <table id="completedList" class="ticket overview">
        <tbody>
        <tr>
            <th>Preview</th>
            <th>Order#</th>
            <th>Qty</th>
            <th>Ink</th>
            <th style="min-width: 165px;">Item</th>
            <th>Name</th>
            <th>Due Date</th>
            <th>Shipping</th>
            <th>Production Time</th>
            <th>Save Sample</th>
            <th>Remove From Schedule</th>
        </tr>
        <#if completedPlates?has_content>
            <#list completedPlates as plate>
                <#list plate as orderItem>
                    <tr id="completed_${orderItem.name}" class="order-item">
						<td class="image">
                            <img class="preview" src="<@ofbizScene7Url>/is/image/ActionEnvelope/${orderItem.sku}?wid=100&amp;hei=100&amp;fmt=jpeg&amp;bgc=f5f5f6</@ofbizScene7Url>" />
                            <#list orderItem.content as content>
                                <#if content.contentPurposeEnumId == "OIACPRP_FRONT" || content.contentPurposeEnumId == "OIACPRP_BACK">
									<img class="preview" src="<@ofbizUrl>serveFileForStream?filePath=</@ofbizUrl>${content.contentPath}" style="max-width: 100px; max-height: 100px;" />
                                <#elseif content.contentPurposeEnumId == "OIACPRP_WORKER_FRONT">
									<a target="_blank" class="workerLink" href="<@ofbizUrl>serveFileForStream?filePath=</@ofbizUrl>${content.contentPath}">FRONT PDF</a>
                                <#elseif content.contentPurposeEnumId == "OIACPRP_WORKER_BACK">
									<a target="_blank" class="workerLink" href="<@ofbizUrl>serveFileForStream?filePath=</@ofbizUrl>${content.contentPath}">BACK PDF</a>
                                </#if>
                            </#list>
						</td>
                        <td class="ordernumber"><a href="<@ofbizUrl>/viewOrder?orderId=${orderItem.orderNumber}</@ofbizUrl>">${orderItem.orderNumber}</a></td>
                        <td class="quantity">${orderItem.quantity?string["0"]}</td>
                        <td class="inks">${orderItem.ink}</td>
                        <td class="itemname">${orderItem.item}<span class="hidden comments">${orderItem.prepressComments?if_exists}</span><span class="hidden pressmancomments">${orderItem.pressmanComments?if_exists}</span></td>
                        <td>${orderItem.name}</td>
                        <td class="duedate">${orderItem.itemDueDate}</td>
                        <td class="shipping" style="background-color: <#if (orderItem.shipping)?has_content><#if orderItem.shipping?contains("Standard")>#95EFB4<#elseif orderItem.shipping?contains("Premium")>#F6FF88<#elseif orderItem.shipping?contains("Priority")>#97FFFA<#elseif orderItem.shipping?contains("Next")>#FF3636<#elseif orderItem.shipping?contains("Fedex")>#A39DFD<#else>#fff</#if></#if>">${orderItem.shipping?if_exists}</td>
                        <td><#if orderItem.productionTime?has_content && orderItem.productionTime == "Y">Rush<#else>Standard</#if> <#if orderItem.timeWarning == "Y">(2 DAY)</#if></td>
                        <td class="saveprint" style="background-color: <#if (orderItem.savePrintSample)?has_content && orderItem.savePrintSample == "Y">#ffce9b</#if>"><#if (orderItem.savePrintSample)?has_content && orderItem.savePrintSample == "Y">Yes<#else>No</#if></td>
                        <td>
							<input class="start hidden" type="button" data-toggle="modal" data-plateid="${orderItem.plateNumber}" data-orderid="${orderItem.orderNumber}" data-orderitemseqid="${orderItem.orderItemSeqId}" data-target="#startJob" value="Start Job">
                            <div style="white-space: nowrap;">
                                <input class="printdate hidden" <#if (session.getAttribute("userLogin").userLoginId)?exists && (session.getAttribute("userLogin").userLoginId?starts_with("shawn") || session.getAttribute("userLogin").userLoginId?starts_with("shoab"))><#else>style="display: none;"</#if> type="text" placeholder="Date Printed" data-plugin="datepicker" data-format="yyyy-mm-dd" value="">
                                <input class="remove hidden" type="button" onclick="removeFromSchedule('${orderItem.plateNumber}', '${orderItem.orderNumber}', '${orderItem.orderItemSeqId}')" value="Printed">
                            </div>
                            <input class="add" type="button" onclick="addToSchedule('${orderItem.plateNumber}', '${orderItem.orderNumber}', '${orderItem.orderItemSeqId}')" value="Re-Plate">
                        </td>
                    </tr>
                </#list>
            </#list>
        </#if>
        </tbody>
    </table>
</div>

<script>
    function togglePlates() {
        $('.autoHide').each(function(e) {
            if($(this).hasClass('hidden')) {
                $(this).removeClass('hidden');
            } else {
                $(this).addClass('hidden');
            }
        });
    }

	$('.stockOrderAndItem').on('click', function(e) {
		e.preventDefault();

        var self = $(this);
        var picked = !(self.hasClass('completedPick'));
		$.ajax({
			url: '<@ofbizUrl>/pickOrderItemStock</@ofbizUrl>',
			data: { 'orders': self.attr('data-orders'), 'picked': picked },
			dataType: 'json',
			method: 'POST'
		}).done(function (data) {
			if (data.success) {
                if(picked) {
					self.addClass('completedPick');
                } else {
					self.removeClass('completedPick');
                }
			} else {
				//
			}
		});
	});

	$('.inks').on('click', function(e) {
		e.preventDefault();

		var self = $(this);
		var picked = !(self.hasClass('completedPick'));
		$.ajax({
			url: '<@ofbizUrl>/pickInk</@ofbizUrl>',
			data: { 'order': self.attr('data-order'), 'picked': picked },
			dataType: 'json',
			method: 'POST'
		}).done(function (data) {
			if (data.success) {
				if(picked) {
					self.addClass('completedPick');
				} else {
					self.removeClass('completedPick');
				}
			} else {
				//
			}
		});
	});

    function removeFromSchedule(plateId, orderId, orderItemSeqId) {
        var rFS = false;
        $.ajax({
            url: '<@ofbizUrl>/removeFromSchedule</@ofbizUrl>',
            data: 'orderId=' + orderId + '&orderItemSeqId=' + orderItemSeqId + '&timestamp=' + $('#' + orderId + '_' + orderItemSeqId).find('.printdate').val(),
            dataType: 'json',
            method: 'POST',
            async: false
        }).done(function(data) {
            if(data.success == true) {
				$('.completedJobs').removeClass('hidden');
                var clone = $('#' + orderId + '_' + orderItemSeqId).clone();
                clone.find('input.remove').addClass('hidden');
				clone.find('input.add').removeClass('hidden');
				clone.find('input.printdate').removeClass('hidden');
				clone.find('input.start').addClass('hidden');
                clone.attr('id', 'completed_' + clone.attr('id'));

                if($('.' + plateId).length == 1) {
					$('#' + plateId).remove();
					$('#' + orderId + '_' + orderItemSeqId).remove();
                } else {
                    $('#' + orderId + '_' + orderItemSeqId).remove();
                }

				$('.completedJobs').find('table > tbody').append(clone);
				rFS = true;
            } else {
                alert('An error occurred while removing the plate from schedule.');
            }
        });

        return rFS;
    }

	function addToSchedule(plateId, orderId, orderItemSeqId) {
		$.ajax({
			url: '<@ofbizUrl>/addToSchedule</@ofbizUrl>',
			data: 'orderId=' + orderId + '&orderItemSeqId=' + orderItemSeqId,
			dataType: 'json',
			method: 'POST'
		}).done(function(data) {
			if(data.success == true) {
				$('#completed_' + orderId + '_' + orderItemSeqId).remove();
			} else {
				alert('An error occurred while adding the plate to the schedule.');
			}
		});
	}

	function startJobTime(orderId, orderItemSeqId, approval, finish) {
		$.ajax({
			url: '<@ofbizUrl>/startJobTime</@ofbizUrl>',
			data: 'orderId=' + orderId + '&orderItemSeqId=' + orderItemSeqId + '&approval=' + approval + '&finish=' + finish,
			dataType: 'json',
			method: 'POST'
		}).done(function(data) {
			if(data.success == true) {
				//
			} else {
				alert('An error occurred while starting the job.');
			}
		});
	}

    var jobClicked = null;
	$(function() {
        $('#startJob').on('show.bs.modal', function (e) {
			jobClicked = $(e.relatedTarget);
			$('#startJob').find('.workers').empty();
			$('#startJob').find('.image').empty();
			$('#startJob').find('.info').empty();
			$('#startJob').find('.info').html($('<table/>').append($('<tr/>').append($('<td/>').html('Name')).append($('<td/>').html(jobClicked.parent().siblings('.itemname').html())))
					                                        .append($('<tr/>').append($('<td/>').html('Quantity')).append($('<td/>').html(jobClicked.parent().siblings('.quantity').html())))
					                                        .append($('<tr/>').append($('<td/>').html('Ink')).append($('<td/>').html(jobClicked.parent().siblings('.inks').html())))
					                                        .append($('<tr/>').append($('<td/>').html('Due Date')).append($('<td/>').html(jobClicked.parent().siblings('.duedate').html())))
					                                        .append($('<tr/>').append($('<td/>').html('Shipping')).append($('<td/>').html(jobClicked.parent().siblings('.shipping').html())))
					                                        .append($('<tr/>').append($('<td/>').html('Comments')).append($('<td/>').html(jobClicked.parent().siblings().find('.comments').html())))
					                                        .append($('<tr/>').append($('<td/>').html('Pressman Comments')).append($('<td/>').html(jobClicked.parent().siblings().find('.pressmancomments').html())))
                                                            .append($('<tr/>').append($('<td/>').html('Order ID')).append($('<td/>').html(jobClicked.parent().siblings().find('.ordernumber').html())))
                                                            .append($('<tr/>').append($('<td/>').html('Save Samples')).append($('<td/>').html(jobClicked.parent().siblings('.saveprint').html()))));

			jobClicked.parent().siblings('.image').find('a').each(function(i) {
				var worker = $(this).clone();
                worker.removeClass('hidden');
				$('#startJob').find('.workers').append(worker);
			});
			jobClicked.parent().siblings('.image').find('img').each(function(i) {
                var img = $(this).clone();
				$('#startJob').find('.image').append(img);
            });
		});

		$('#startJob').on('hide.bs.modal', function (e) {
			$(this).find('.j-btn-start').html('Start');
			$(this).find('.j-btn-approval').html('Get Approval').addClass('hidden');
		});

        <#if requestParameters.orderId?has_content && requestParameters.orderItemSeqId?has_content>
            togglePlates();
			$('.start').not('.hidden').trigger('click');
        </#if>
    });

	$('#startJob .j-btn-start').on('click', function(e) {
        if($(this).html() == 'Start') {
			startJobTime(jobClicked.data('orderid'), jobClicked.data('orderitemseqid'), '', '');
			$(this).html('Finish');
            $(this).siblings('.j-btn-approval').removeClass('hidden');
        } else {
			var rFS = removeFromSchedule(jobClicked.data('plateid'), jobClicked.data('orderid'), jobClicked.data('orderitemseqid'));
            if(rFS) {
				$('#startJob').modal('hide');
				$(this).html('Start');
				$(this).siblings('.j-btn-approval').removeClass('hidden').addClass('hidden');
			}
        }
	});
	$('#startJob .j-btn-approval').on('click', function(e) {
		if($(this).html() == 'Get Approval') {
			startJobTime(jobClicked.data('orderid'), jobClicked.data('orderitemseqid'), 'Y', '');
			$(this).html('Approved');
		} else {
			startJobTime(jobClicked.data('orderid'), jobClicked.data('orderitemseqid'), 'Y', 'Y');
			$(this).removeClass('hidden').addClass('hidden');
		}
	});
</script>
<script src="/html/themes/global/vendor/bootstrap-datepicker/bootstrap-datepicker.js"></script>
<script src="/html/themes/global/js/Plugin/bootstrap-datepicker.js"></script>
<script src="<@ofbizContentUrl>/html/js/admin/plateLookup.js</@ofbizContentUrl>?ts=1"></script>