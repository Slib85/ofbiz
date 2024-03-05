<link rel="stylesheet" href="/html/themes/global/vendor/bootstrap-datepicker/bootstrap-datepicker.css">
<style>
    .table a {
        text-decoration: none;
    }
    .table {
        font-size: 12px;
    }
    .table td {
        white-space: nowrap;
    }
    .dataTables_filter {
        display: none;
    }
</style>

<#if calcStats?c == "true">
<div class="panel">
    <div class="panel-body container-fluid">
        <div class="row">
            <div class="col-lg-12 col-xs-12">
                <div class="example-wrap">
                    <div class="example table-responsive">
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th></th>
                                <th>New Quotes Requests</th>
                                <th colspan="3">Incoming Quotes</th>
                                <th colspan="3">Sent Quotes</th>
                                <th></th>
                                <th></th>
                            </tr>
                            </thead>
                            <thead>
                            <tr>
                                <th></th>
                                <th></th>
                                <th>Today</th>
                                <th>Yesterday</th>
                                <th>MTD</th>
                                <th>Today</th>
                                <th>Yesterday</th>
                                <th>MTD</th>
                                <th>Open Quotes</th>
                                <th>Rejected Quotes (MTD)</th>
                            </tr>
                            </thead>
                            <tbody>
								<#if quoteQueue?has_content && quoteQueue.incomingQuotesMTD?has_content>
									<#list quoteQueue.incomingQuotesMTD.keySet() as salesrep>
                                    <tr>
                                        <td>${salesrep}</td>
                                        <td>
                                            <form id="${salesrep_index}_C" name="${salesrep_index}_C" method="POST" action="<@ofbizUrl>/foldersQuoteList</@ofbizUrl>">
                                                <input type="hidden" name="webSiteId" value="${requestParameters.webSiteId?if_exists}" />
                                                <input type="hidden" name="quoteIds" value="<#if (createdQuotes.get(salesrep))?has_content><#list createdQuotes.get(salesrep) as quote>${quote}<#if quote_has_next>,</#if></#list></#if>" />
                                            </form>
                                            <a href="#" onclick="$('#${salesrep_index}_C').submit()">${(createdQuotes.get(salesrep))?if_exists?size}</a>
                                        </td>
                                        <td>
                                            <form id="${salesrep_index}_0" name="${salesrep_index}_0" method="POST" action="<@ofbizUrl>/foldersQuoteList</@ofbizUrl>">
                                                <input type="hidden" name="webSiteId" value="${requestParameters.webSiteId?if_exists}" />
                                                <input type="hidden" name="quoteIds" value="<#if (quoteQueue.incomingQuotesT.get(salesrep))?has_content><#list quoteQueue.incomingQuotesT.get(salesrep) as quote>${quote}<#if quote_has_next>,</#if></#list></#if>" />
                                            </form>
                                            <a href="#" onclick="$('#${salesrep_index}_0').submit()">${quoteQueue.incomingQuotesT.get(salesrep)?if_exists?size}</a>
                                        </td>
                                        <td>
                                            <form id="${salesrep_index}_1" name="${salesrep_index}_1" method="POST" action="<@ofbizUrl>/foldersQuoteList</@ofbizUrl>">
                                                <input type="hidden" name="webSiteId" value="${requestParameters.webSiteId?if_exists}" />
                                                <input type="hidden" name="quoteIds" value="<#if (quoteQueue.incomingQuotesY.get(salesrep))?has_content><#list quoteQueue.incomingQuotesY.get(salesrep) as quote>${quote}<#if quote_has_next>,</#if></#list></#if>" />
                                            </form>
                                            <a href="#" onclick="$('#${salesrep_index}_1').submit()">${quoteQueue.incomingQuotesY.get(salesrep)?if_exists?size}</a>
                                        </td>
                                        <td>
                                            <form id="${salesrep_index}_2" name="${salesrep_index}_2" method="POST" action="<@ofbizUrl>/foldersQuoteList</@ofbizUrl>">
                                                <input type="hidden" name="webSiteId" value="${requestParameters.webSiteId?if_exists}" />
                                                <input type="hidden" name="quoteIds" value="<#if (quoteQueue.incomingQuotesMTD.get(salesrep))?has_content><#list quoteQueue.incomingQuotesMTD.get(salesrep) as quote>${quote}<#if quote_has_next>,</#if></#list></#if>" />
                                            </form>
                                            <a href="#" onclick="$('#${salesrep_index}_2').submit()">${quoteQueue.incomingQuotesMTD.get(salesrep)?if_exists?size}</a>
                                        </td>
                                        <td>
                                            <form id="${salesrep_index}_3" name="${salesrep_index}_3" method="POST" action="<@ofbizUrl>/foldersQuoteList</@ofbizUrl>">
                                                <input type="hidden" name="webSiteId" value="${requestParameters.webSiteId?if_exists}" />
                                                <input type="hidden" name="quoteIds" value="<#if (quoteQueue.sentQuotesT.get(salesrep))?has_content><#list quoteQueue.sentQuotesT.get(salesrep) as quote>${quote}<#if quote_has_next>,</#if></#list></#if>" />
                                            </form>
                                            <a href="#" onclick="$('#${salesrep_index}_3').submit()">${quoteQueue.sentQuotesT.get(salesrep)?if_exists?size}</a>
                                        </td>
                                        <td>
                                            <form id="${salesrep_index}_4" name="${salesrep_index}_4" method="POST" action="<@ofbizUrl>/foldersQuoteList</@ofbizUrl>">
                                                <input type="hidden" name="webSiteId" value="${requestParameters.webSiteId?if_exists}" />
                                                <input type="hidden" name="quoteIds" value="<#if (quoteQueue.sentQuotesY.get(salesrep))?has_content><#list quoteQueue.sentQuotesY.get(salesrep) as quote>${quote}<#if quote_has_next>,</#if></#list></#if>" />
                                            </form>
                                            <a href="#" onclick="$('#${salesrep_index}_4').submit()">${quoteQueue.sentQuotesY.get(salesrep)?if_exists?size}</a>
                                        </td>
                                        <td>
                                            <form id="${salesrep_index}_5" name="${salesrep_index}_5" method="POST" action="<@ofbizUrl>/foldersQuoteList</@ofbizUrl>">
                                                <input type="hidden" name="webSiteId" value="${requestParameters.webSiteId?if_exists}" />
                                                <input type="hidden" name="quoteIds" value="<#if (quoteQueue.sentQuotesMTD.get(salesrep))?has_content><#list quoteQueue.sentQuotesMTD.get(salesrep) as quote>${quote}<#if quote_has_next>,</#if></#list></#if>" />
                                            </form>
                                            <a href="#" onclick="$('#${salesrep_index}_5').submit()">${quoteQueue.sentQuotesMTD.get(salesrep)?if_exists?size}</a>
                                        </td>
                                        <td>
                                            <form id="${salesrep_index}_6" name="${salesrep_index}_6" method="POST" action="<@ofbizUrl>/foldersQuoteList</@ofbizUrl>">
                                                <input type="hidden" name="webSiteId" value="${requestParameters.webSiteId?if_exists}" />
                                                <input type="hidden" name="quoteIds" value="<#if (quoteQueue.openQuotes.get(salesrep))?has_content><#list quoteQueue.openQuotes.get(salesrep) as quote>${quote}<#if quote_has_next>,</#if></#list></#if>" />
                                            </form>
                                            <a href="#" onclick="$('#${salesrep_index}_6').submit()">${quoteQueue.openQuotes.get(salesrep)?if_exists?size}</a>
                                        </td>
                                        <td>
                                            <form id="${salesrep_index}_7" name="${salesrep_index}_7" method="POST" action="<@ofbizUrl>/foldersQuoteList</@ofbizUrl>">
                                                <input type="hidden" name="webSiteId" value="${requestParameters.webSiteId?if_exists}" />
                                                <input type="hidden" name="quoteIds" value="<#if (quoteQueue.rejectedQuotes.get(salesrep))?has_content><#list quoteQueue.rejectedQuotes.get(salesrep) as quote>${quote}<#if quote_has_next>,</#if></#list></#if>" />
                                            </form>
                                            <a href="#" onclick="$('#${salesrep_index}_7').submit()">${quoteQueue.rejectedQuotes.get(salesrep)?if_exists?size}</a>
                                        </td>
                                    </tr>
									</#list>
								</#if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</#if>

<div class="panel panel-primary panel-line">
    <div class="panel-body">
		<form name="quoteSearch" method="GET" action="<@ofbizUrl>/${websiteId}QuoteList</@ofbizUrl>">
			<input type="hidden" name="webSiteId" value="${websiteId}" />
			<div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label">Status ID</label>
							<select name="statusId" class="form-control" data-allow-clear="true" data-placeholder="Status ID">
								<option value="">Status ID</option>
								<option value="QUO_CREATED" <#if requestParameters.statusId?if_exists == "QUO_CREATED">selected</#if>>Created</option>
								<option value="QUO_ASSIGNED" <#if requestParameters.statusId?if_exists == "QUO_ASSIGNED">selected</#if>>Assigned</option>
								<option value="QUO_APPROVED" <#if requestParameters.statusId?if_exists == "QUO_APPROVED">selected</#if>>Quoted</option>
								<option value="QUO_SENT" <#if requestParameters.statusId?if_exists == "QUO_SENT">selected</#if>>Sent</option>
								<option value="QUO_ORDERED" <#if requestParameters.statusId?if_exists == "QUO_ORDERED">selected</#if>>Ordered</option>
                                <option value="QUO_ALTERNATE_QUO" <#if requestParameters.statusId?if_exists == "QUO_ALTERNATE_QUO">selected</#if>>Alternate Quote</option>
                                <option value="QUO_ALTERNATE_ORDR" <#if requestParameters.statusId?if_exists == "QUO_ALTERNATE_ORDR">selected</#if>>Alternate Order</option>
								<option value="QUO_WAITFORVEND" <#if requestParameters.statusId?if_exists == "QUO_WAITFORVEND">selected</#if>>Waiting For Vendor</option>
								<option value="QUO_REJECTED" <#if requestParameters.statusId?if_exists == "QUO_REJECTED">selected</#if>>Rejected</option>
							</select>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<label class="control-label">Assigned To</label>
							<select name="assignedTo" class="form-control" data-allow-clear="true" data-placeholder="Assigned To">
								<option value="">Assigned To</option>
                            <#list foldersAssignedToUsers.keySet() as key>
                                <option value="${key}" <#if requestParameters.assignedTo?exists && requestParameters.assignedTo == key>selected="selected"</#if>>${key}</option>
                            </#list>
							</select>
						</div>
					</div>
				</div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="form-group">
                            <label class="control-label">Search Term</label>
                            <input class="form-control" type="text" name="w" value="" placeholder="Search Term"/>
                        </div>
                    </div>
				</div>
				<div class="input-daterange" data-plugin="datepicker">
					<div class="input-group">
						<span class="input-group-addon"><i class="icon wb-calendar" aria-hidden="true"></i></span>
						<input type="text" class="form-control" name="minDate" data-format="yyyy-mm-dd" autocomplete="off" value="<#if requestParameters.minDate?has_content>${requestParameters.minDate}</#if>"/>
					</div>
					<div class="input-group">
						<span class="input-group-addon">to</span>
						<input type="text" class="form-control" name="maxDate" data-format="yyyy-mm-dd" autocomplete="off" value="<#if requestParameters.maxDate?has_content>${requestParameters.maxDate}</#if>" />
						<span class="input-group-btn">
						<button type="submit" class="btn btn-primary"><i class="icon wb-search" aria-hidden="true"></i></button>
					</span>
					</div>
                </div>
			</div>
		</form>
    </div>
</div>

<div class="row">
    <div class="col-md-12">
        <div class="panel">
            <div class="panel-heading">
                <div class="panel-title">Quotes</div>
                <div class="panel-actions">
                    <form class="" name="viewCustomEnvelope" action="<@ofbizUrl>/${websiteId}QuoteView</@ofbizUrl>" method="GET">
                        <div class="input-group-sm">
                            <button type="submit" class="input-search-btn">
                                <i class="icon wb-search" aria-hidden="true"></i>
                            </button>
                            <input type="text" class="form-control" name="quoteId" placeholder="Search for a Quote" value="">
                        </div>
                    </form>
                </div>
            </div>
			<div class="panel-body">
				<div class="table-responsive">
					<table class="table table-hover dataTable table-striped" id="orderTableFixedHeader">
						<thead>
							<tr>
								<th>QUOTE ID</th>
								<th>STATUS</th>
								<th>QTY</th>
								<th>ORDER DATE</th>
                                <th>COMPANY NAME</th>
                                <th>TYPE</th>
								<th>FIRST NAME</th>
								<th>LAST NAME</th>
								<#if requestParameters.webSiteId != 'folders'>
								<th>SIZE</th>
								<th>WINDOWS Y/N</th>
								<th>SEALING</th>
								<th>PRINTING</th>
								</#if>
                                <th>PRODUCT ID</th>
								<#if websiteId == "folders">
                                <th>EMAIL</th>
                                <th>PHONE</th>
                                <th>ASSIGNED TO</th>
                                </#if>
							</tr>
						</thead>
						<tfoot>
							<tr class="replace-inputs">
								<th>QUOTE ID</th>
								<th>STATUS</th>
								<th>QTY</th>
								<th>ORDER DATE</th>
                                <th>COMPANY NAME</th>
                                <th>TYPE</th>
								<th>FIRST NAME</th>
								<th>LAST NAME</th>
								<#if requestParameters.webSiteId != 'folders'>
                                <th>SIZE</th>
                                <th>WINDOWS Y/N</th>
                                <th>SEALING</th>
                                <th>PRINTING</th>
								</#if>
                                <th>PRODUCT ID</th>
								<#if websiteId == "folders">
                                <th>EMAIL</th>
                                <th>PHONE</th>
                                <th>ASSIGNED TO</th>
                                </#if>
							</tr>
						</tfoot>
						<tbody>
							<#list customEnvelopeList as customEnvelope>
								<tr>
									<td><a href="<@ofbizUrl>/${websiteId}QuoteView?quoteId=${customEnvelope.quoteId}</@ofbizUrl>">${customEnvelope.quoteId}</a></td>
									<td>${Static["com.envelopes.order.OrderHelper"].getStatusDesc(delegator, customEnvelope.statusId?default('QUO_CREATED'))}</td>
									<td>${customEnvelope.quantity?if_exists}</td>
									<td>${customEnvelope.createdDate?string.medium}</td>
                                    <td>${customEnvelope.companyName?if_exists}</td>
                                    <td>${customEnvelope.quoteType?if_exists}</td>
									<td>${customEnvelope.firstName?if_exists}</td>
									<td>${customEnvelope.lastName?if_exists}</td>
									<#if requestParameters.webSiteId != 'folders'>
									<td>${customEnvelope.standardSize?if_exists}</td>
									<td>${customEnvelope.windowCount?if_exists}</td>
									<td>${customEnvelope.sealingMethod?if_exists}</td>
									<td>${customEnvelope.printingRequired?if_exists}</td>
									</#if>
                                    <td>${customEnvelope.productId?if_exists}</td>
									<#if websiteId == "folders">
                                        <td>${customEnvelope.userEmail?if_exists}</td>
                                        <td>${customEnvelope.phone?if_exists}</td>
                                        <td>
                                            <select name="assignedTo-${customEnvelope.quoteId}" class="jqs-assignedTo form-control form-control-sm">
                                                <option value="none">None</option>
                                                <#list foldersAssignedToUsers.keySet() as key>
                                                    <option value="${key}" <#if customEnvelope.assignedTo?exists && customEnvelope.assignedTo == key>selected="selected"</#if>>${key}</option>
                                                </#list>
                                            </select>
                                        </td>
									</#if>
								</tr>
							</#list>
						</tbody>
					</table>
				</div>
			</div>
        </div>
    </div>
</div>

<#if calcStats?c == "true">
<#assign largestList = quoteScorecard.unQuotedPercentage.keySet() />
<#if quoteScorecard.requestToQuotePercentage.keySet()?size gt largestList?size>
	<#assign largestList = quoteScorecard.requestToQuotePercentage.keySet() />
</#if>
<#if quoteScorecard.quoteToOrderPercentage.keySet()?size gt largestList?size>
	<#assign largestList = quoteScorecard.quoteToOrderPercentage.keySet() />
</#if>

<div class="panel">
    <div class="panel-body container-fluid">
        <div class="row">
            <div class="col-lg-12 col-xs-12">
                <div class="example-wrap">
                    <h4 class="example-title">Scorecard (Last 30 Days)</h4>
                    <div class="example table-responsive">
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th></th>
                                <th>No Quote %</th>
                                <th>Quoted %</th>
                                <th>Avg Time to Quote</th>
                                <th>Ordered %</th>
                                <th>Rejected %</th>
                                <th>Avg Order Amount</th>
                            </tr>
                            </thead>
                            <thead>
                            <tbody>
								<#if largestList?has_content>
									<#list largestList as salesrep>
                                    <tr>
                                        <td>${salesrep!}</td>
                                        <td>${quoteScorecard.unQuotedPercentage.get(salesrep)?if_exists?string.percent}</td>
                                        <td>${quoteScorecard.requestToQuotePercentage.get(salesrep)?if_exists?string.percent}</td>
                                        <td>${quoteScorecard.timeToQuote.get(salesrep)?if_exists}</td>
                                        <td>${quoteScorecard.quoteToOrderPercentage.get(salesrep)?if_exists?string.percent}</td>
                                        <td>${quoteScorecard.rejectedPercentage.get(salesrep)?if_exists?string.percent}</td>
                                        <td><@ofbizCurrency amount=quoteScorecard.avgOrderAmount.get(salesrep)?default(0) /></td>
                                    </tr>
									</#list>
								</#if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</#if>

<script src="/html/themes/global/vendor/bootstrap-datepicker/bootstrap-datepicker.js"></script>
<script src="/html/themes/global/js/Plugin/bootstrap-datepicker.js"></script>
<script src="<@ofbizContentUrl>/html/js/admin/customQuoteList.js</@ofbizContentUrl>"></script>