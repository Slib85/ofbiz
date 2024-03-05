<style>
	h1, h1 a {
		color: #000;
		font: bold 12pt/24pt Verdana,Arial,Helvetica,sans-serif;
	}

	#orderInfo .row:after, #bodyHead:after, #plateInfo:after, #bodyContainer:after {
		clear: both;
		content: ".";
		display: block;
		height: 0;
		visibility: hidden;
	}

    body {
        color: #000000;
        margin: 5px;
    }

	table.ticket {
		border-collapse: collapse;
		border-spacing: 0;
		margin-bottom: 10px;
		width: 100%;
	}

	table.ticket th, table.ticket td {
		font-size: 12pt;
		padding: 9px;
	}
	table.ticket h1 {
		font-size: 12pt;
	}
	table.ticket h2 {
		color: #000;
		text-decoration: underline;
	}

	table.overview tbody th, table.overview tbody td {
		border: 1px solid #777;
		font-size: 10pt;
	}
	table.overview tbody th {
		border-bottom: 2px solid #777;
	}

	table.job table.details {
		border-collapse: collapse;
	}
	table.job table.details td {
		border: 1px solid #777;
	}
	table.job table.rush {
		color: #cc0000;
		font-weight: bold;
	}
	table.stock tbody th, table.stock tbody td {
		border: 1px solid #777;
	}
	table.stock tbody th {
		border-bottom: 2px solid #777;
	}
	table.inks tbody th, table.inks tbody td {
		border: 1px solid #777;
	}
	table.inks tbody th {
		border-bottom: 2px solid #777;
	}
</style>

<#list plate as orderItem>
	<#if orderItem_index == 0>
		<table class="ticket overview" style="page-break-after: always;">
			<thead>
			<tr>
				<td align="left" colspan="3">
					<h1>Plate # <a style="color:blue;text-decoration: underline" href="<@ofbizUrl>/plateEdit</@ofbizUrl>?plateId=${orderItem.plateNumber?upper_case}">${orderItem.plateNumber?upper_case}</a></h1>
				</td>
				<td align="right" colspan="3">
					<h1>Due: ${orderItem.dueDate}</h1>
				</td>
			</tr>
			</thead>
			<tbody>
			<tr>
				<th>Order#</th>
				<th>Qty</th>
				<th>SKU#</th>
				<th>Ink</th>
				<th>Item</th>
				<th>Name</th>
			</tr>
		</#if>
			<tr>
				<td>
                    <a style="font-weight:bold;color:#0000ff;text-decoration:underline" href="#${orderItem.orderNumber}">${orderItem.orderNumber}</a>
				</td>
				<td>
				${orderItem.quantity?string["0"]}

				</td>
				<td>${orderItem.sku}</td>
				<td>${orderItem.ink}</td>
				<td>${orderItem.item}</td>
				<td>${orderItem.name}</td>
			</tr>
			<#if !orderItem_has_next>
			</tbody>
		</table>
	</#if>
</#list>

<#list plate as orderItem>
	<table id="${orderItem.orderNumber}" class="ticket job" style="page-break-after: always;">
		<tbody>
			<tr>
				<td valign="top" align="left">
					<#if orderItem.rushProduction?c == 'true'>
					<table width="100%" cellspacing="6" cellpadding="0" border="0">
						<tbody>
						<tr>
							<td>
								<strong>RUSH</strong>
							</td>
							<td>
								<strong>RUSH</strong>
							</td>
							<td>
								<strong>RUSH</strong>
							</td>
							<td>
								<strong>RUSH</strong>
							</td>
							<td>
								<strong>RUSH</strong>
							</td>
						</tr>
						</tbody>
					</table>
					</#if>
					<table width="100%" cellspacing="6" cellpadding="5" border="0">
						<tbody>
						<tr>
							<td colspan="2">
								<h1 class="big">
									Order # <a style="font-weight:bold;color:#0000ff;text-decoration:underline" href="<@ofbizUrl>/viewOrder?orderId=${orderItem.orderNumber}</@ofbizUrl>">${orderItem.orderNumber}</a>
								</h1>
							</td>
							<td width="10%" valign="top" rowspan="6">&nbsp;</td>
							<td width="30%" valign="top" rowspan="6">
								<div class="barcode">*${orderItem.orderNumber}*</div>
							<#-- TODO -->
								<#--<table width="100%" cellspacing="0" cellpadding="0">
									<tbody>
									<tr>
										<td align="center">
											Blind Shipment
										</td>
									</tr>
									</tbody>
								</table>-->
							</td>

						</tr>
						<tr>
							<td width="20%">Due Date:</td>
							<td width="50%">
							${orderItem.itemDueDate}
							</td>
						</tr>
						<tr>
							<td>Order Date:</td>
							<td>
							${orderItem.orderDate}
							</td>
						</tr>
						<tr>
							<td>Proof Approved:</td>
							<td>
								<#-- TODO -->
							</td>
						</tr>
						<tr>
							<td>Plate#:</td>
							<td>${orderItem.plateNumber?upper_case}</td>
						</tr>
						<tr>
							<td>Press#:</td>
							<td>${orderItem.pressNum}</td>
						</tr>
						</tbody>
					</table>

					<table width="100%" cellspacing="0" cellpadding="0" border="0" class="details">
						<tbody>
						<tr>
							<td width="20%">Qty:</td>
							<td width="80%">
							${orderItem.quantity?string["0"]}
							</td>
						</tr>
						<tr>
							<td>SKU:</td>
							<td>${orderItem.sku}</td>
						</tr>
						<tr>
							<td>Item:</td>
							<td>
							${orderItem.item}
							</td>
						</tr>
						<tr>
							<td>Ink:</td>
							<td>${orderItem.ink}</td>
						</tr>
						<tr>
							<td>Position:</td>
							<td>${orderItem.position?if_exists}</td>
						</tr>
						<tr>
							<td>Name:</td>
							<td>${orderItem.name}</td>
						</tr>
						<tr>
							<td>Comments:</td>
							<td>${orderItem.prepressComments?default('')}</td>
						</tr>
						</tbody>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
</#list>

<#list plate as orderItem>
	<#if orderItem_index == 0>
		<table class="ticket stock" style="page-break-after: always;">
			<thead>
			<tr>
				<td align="center" colspan="7">
					<h1><u>STOCK LIST</u></h1>
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<h1>Plate# <a style="color:blue;text-decoration: underline" href="<@ofbizUrl>/plateEdit</@ofbizUrl>?plateId=${orderItem.plateNumber?upper_case}">${orderItem.plateNumber?upper_case}</a></h1>
				</td>
			</tr>
			</thead>
			<tbody>
			<tr>
				<th>Order</th>
				<th>Qty.</th>
				<th>SKU</th>
				<th>Barcode</th>
				<th>Item</th>
				<th>Bin</th>
				<th>Availability</th>
			</tr>
			</#if>
			<tr>
				<td>${orderItem.orderNumber}</td>
				<td>${orderItem.quantity?string["0"]}</td>
				<td>${orderItem.sku}</td>
				<td><div class="barcodeMini">*${orderItem.orderNumber}*</div></td>
				<td>${orderItem.item}</td>
				<td>${orderItem.bin?default('')}</td>
				<td>${orderItem.availability?default('')}</td>
			</tr>
			<#if !orderItem_has_next>
			</tbody>
		</table>
	</#if>
</#list>

<#list plate as orderItem>
	<#if orderItem_index == 0>
		<table class="ticket inks">
			<thead>
			<tr>
				<td align="center" colspan="10">
					<h1><u>INK LIST</u></h1>
				</td>
			</tr>
			<tr>
				<td colspan="5">
					<h1>Plate# <a style="color:blue;text-decoration: underline" href="<@ofbizUrl>/plateEdit</@ofbizUrl>?plateId=${orderItem.plateNumber?upper_case}">${orderItem.plateNumber?upper_case}</a></h1>
				</td>
			</tr>
			</thead>
			<tbody>
			<tr>
				<th>Order</th>
				<th>Qty.</th>
                <#list [1,2,3,4,5,6,7,8] as i>
                    <th>Ink${i}</th>
                </#list>
			</tr>
			</#if>

			<tr>
				<td>${orderItem.orderNumber}</td>
				<td>${orderItem.quantity?string["0"]}</td>${orderItem.inkList}
                <#list [0,1,2,3,4,5,6,7] as i>
				<td><#if i < orderItem.inkList?size>${orderItem.inkList.get(i)}</#if></td>
                </#list>
			</tr>
			<#if !orderItem_has_next>
			</tbody>
		</table>
	</#if>
</#list>