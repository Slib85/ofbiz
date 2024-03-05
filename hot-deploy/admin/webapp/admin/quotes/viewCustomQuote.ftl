<link rel="stylesheet" href="/html/themes/global/vendor/toastr/toastr.css" />
<link rel="stylesheet" href="/html/themes/base/assets/examples/css/advanced/toastr.css" />
<link rel="stylesheet" href="/html/css/admin/quotes/viewCustomQuote.css?ts=1" />

<#if customEnvelopeInfo?has_content>
<div class="panel">
    <div class="panel-heading">
    	<h3 class="panel-title"><strong>QUOTE: #${customEnvelopeInfo("quoteId")}</strong></h3>
    </div>
	<div class="panel-body container-fluid">
		<div class="row">
			<div class="col-xs-3">
                <#if websiteId == "folders">
                    <#if customEnvelopeInfo("productId")?has_content>
                        <img src="https://actionenvelope.scene7.com/is/image/ActionEnvelope/${customEnvelopeInfo("productId")?if_exists}?wid=300" />
                    </#if>
                    <div>
                        <strong>Description:</strong> <#if product?has_content>${(product.productName)?default("N/A")}<#else>${customEnvelopeInfo("productId")?default("N/A")}</#if>
                    </div>
                </#if>
                <div>
                    <strong>Type:</strong> <#if websiteId == "folders">Folders ${customEnvelopeInfo.quoteType?if_exists}<#else>Envelopes</#if>
                </div>
                <div>
                    <strong>Date Created:</strong> ${customEnvelopeInfo("createdDate")?string.medium}
                </div>
                <#if websiteId == "folders">
                    <div class="tglButtonContainer">
                        <div class="tglText"><strong>Drift Quote:</strong></div>
                        <div class="tglButton">
                            <input class="tgl tgl-ios" id="driftQuote" name="driftQuote" type="checkbox" value="Y" <#if customEnvelopeInfo.isDrift?exists && customEnvelopeInfo.isDrift == "Y">checked</#if> />
                            <label class="tgl-btn" for="driftQuote"></label>
                        </div>
                    </div>
                </#if>
                <script>
                    $('[name="driftQuote"]').on('change', function() {
                        var element = $(this);
                        $.ajax({
                            type: 'GET',
                            url: '/' + websiteId + '/control/setDriftQuote',
                            dataType: 'json',
                            data: {
                                'quoteId': '${customEnvelopeInfo("quoteId")}',
                                'isDrift': $(element).is(':checked')
                            },
                            async: false
                        }).done(function(response) { 
                            if (!response.success) {
                                alert('Issue when trying to update quote being a drift quote.  Please contact the dev team.');
                            }
                        });
                    });
                </script>
			</div>
			<div class="col-xs-3">
                <strong>Created By:</strong>
				<div>
					<p>
						<#if customEnvelopeInfo("firstName")?exists>${customEnvelopeInfo("firstName")} </#if><#if customEnvelopeInfo("lastName")?exists>${customEnvelopeInfo("lastName")} </#if>
						<#if customEnvelopeInfo("companyName")?exists><br>${customEnvelopeInfo("companyName")}</#if>
						<#if customEnvelopeInfo("address1")?exists><br>${customEnvelopeInfo("address1")}</#if>
						<#if customEnvelopeInfo("address2")?exists><br>${customEnvelopeInfo("address2")}</#if>
						<#if customEnvelopeInfo("city")?exists><br>${customEnvelopeInfo("city")}, </#if><#if customEnvelopeInfo("stateProvinceGeoId")?exists>${customEnvelopeInfo("stateProvinceGeoId")} </#if><#if customEnvelopeInfo("postalCode")?exists>${customEnvelopeInfo("postalCode")}</#if>
						<#if customEnvelopeInfo("phone")?exists><br>Phone: ${customEnvelopeInfo("phone")}</#if>
                        <#if customEnvelopeInfo("additionalPhone")?exists><br>Additional Phone: ${customEnvelopeInfo("additionalPhone")}</#if>
						<#if customEnvelopeInfo("userEmail")?exists><br><a href="mailto:${customEnvelopeInfo("userEmail")}">${customEnvelopeInfo("userEmail")}</a></#if>
                        <#if customEnvelopeInfo("partyId")?exists>Party ID: <a href="<@ofbizUrl>/customers</@ofbizUrl>?id=ID:${customEnvelopeInfo("partyId")}">${customEnvelopeInfo("partyId")}</a></#if>
					</p>
                    <div class="white-200">
                        <#if isWholesaler?c == "true"><span data-role="wholesaler" class="partyRole tag tag-<#if isWholesaler?c == "true">success<#else>default</#if>">Wholesaler</span></#if>
                        <#if isTradeAllegra?c == "true"><span data-role="wholesaler_algra" class="partyRole tag tag-<#if isTradeAllegra?c == "true">success<#else>default</#if>">Wholesaler Allegra</span></#if>
                        <#if isTradePostNet?c == "true"><span data-role="wholesaler_pn" class="partyRole tag tag-<#if isTradePostNet?c == "true">success<#else>default</#if>">Wholesaler PostNet</span></#if>
                        <#if isNonProfit?c == "true"><span data-role="non_profit" class="partyRole tag tag-<#if isNonProfit?c == "true">success<#else>default</#if>">Non Profit</span></#if>
                         <#if isNonTaxable?c == "true"><span data-role="non_taxable" class="partyRole tag tag-<#if isNonTaxable?c == "true">success<#else>default</#if>">Non Taxable</span></#if>
                        <#if isNet30?c == "true"><span data-role="net_30" class="partyRole tag tag-<#if isNet30?c == "true">success<#else>default</#if>">Net 30</span></#if>
                    </div>
				</div>
			</div>
			<div class="col-xs-3">
				<div>
					<strong>Change Custom Quote Status:</strong>
					<div class="form-group">
						<select class="form-control form-control-sm" name="customQuoteStatus">
							<option value="QUO_CREATED" <#if customEnvelopeInfo("statusId")?if_exists == "QUO_CREATED">selected</#if>>Created</option>
                            <option value="QUO_ASSIGNED" <#if customEnvelopeInfo("statusId")?if_exists == "QUO_ASSIGNED">selected</#if>>Assigned</option>
							<option value="QUO_APPROVED" <#if customEnvelopeInfo("statusId")?if_exists == "QUO_APPROVED">selected</#if>>Quoted</option>
                            <option value="QUO_SENT_NETSUITE" <#if customEnvelopeInfo("statusId")?if_exists == "QUO_SENT_NETSUITE">selected</#if>>Sent to Netsuite</option>
                            <option value="QUO_SENT_EMAIL" <#if customEnvelopeInfo("statusId")?if_exists == "QUO_SENT_EMAIL" || customEnvelopeInfo("statusId")?if_exists == "QUO_SENT">selected</#if>>Sent Email</option>
							<option value="QUO_ORDERED" <#if customEnvelopeInfo("statusId")?if_exists == "QUO_ORDERED">selected</#if>>Ordered</option>
                            <option value="QUO_ALTERNATE_QUO" <#if customEnvelopeInfo("statusId")?if_exists == "QUO_ALTERNATE_QUO">selected</#if>>Alternate Quote</option>
                            <option value="QUO_ALTERNATE_ORDR" <#if customEnvelopeInfo("statusId")?if_exists == "QUO_ALTERNATE_ORDR">selected</#if>>Alternate Order</option>
							<option value="QUO_WAITFORVEND" <#if customEnvelopeInfo("statusId")?if_exists == "QUO_WAITFORVEND">selected</#if>>Waiting For Vendor</option>
							<option value="QUO_REJECTED" <#if customEnvelopeInfo("statusId")?if_exists == "QUO_REJECTED">selected</#if>>Rejected</option>
						</select>
						<input type="hidden" name="quoteId" value="${customEnvelopeInfo("quoteId")}"/>
					</div>
                    <#if quoteStatusList?has_content>
                        <div style="text-decoration: underline; font-weight: bold; font-size: 14px;">Quote Status History</div>
                        <#list quoteStatusList as quoteStatus>
                            <div style="font-size: 12px;"><strong>${Static["com.envelopes.order.OrderHelper"].getStatusDesc(delegator, quoteStatus.statusId?default('QUO_CREATED'))}</strong> - ${quoteStatus.statusDatetime?string.medium} (${quoteStatus.statusUserLogin?if_exists}<#if quoteStatus.changeReason?exists> - ${quoteStatus.changeReason}</#if>)</div>
                        </#list>
                    </#if>
				</div>
			</div>
            <div class="col-xs-3">
                <div>
                    <#if websiteId == "folders">
                        <strong>Assigned To:</strong>
                        <div class="form-group">
                            <select name="assignedTo" class="jqs-assignedTo form-control form-control-sm">
                                <option value="none">None</option>
                                <#list foldersAssignedToUsers.keySet() as key>
                                    <option value="${key}" <#if customEnvelopeInfo.assignedTo?exists && customEnvelopeInfo.assignedTo == key>selected="selected"</#if>>${key}</option>
                                </#list>
                            </select>
                        </div>
                    </#if>
                    <div class="jqs-comment">
                        <strong>Internal Comments:</strong>
                        <div class="comment-list">
                            <#list quoteNoteDataList as quoteNoteData>
                                <div class="comment">
                                    <div class="row">
                                        <div class="col-xs-12 commenter-name">${quoteNoteData.firstName?if_exists} ${quoteNoteData.lastName?if_exists}:</div>
                                        <div class="col-xs-12">${quoteNoteData.noteInfo?if_exists}</div>
                                    </div>
                                    <div class="row">
                                        <div class="col-xs-12 date text-right">${quoteNoteData.noteDateTime?if_exists?string.short}</div>
                                    </div>
                                </div>
                            </#list>
                        </div>
                        <div class="non-add-comment">
                            <input name="button-add" class="form-control form-control-sm" type="button" value="Add Comment" />
                        </div>
                        <div id="comment-add" class="comment-add" style="display: none;">
                            <textarea id="jqs-canned_response_input-order_comment" type="text" name="orderNote" value="" placeholder="Your Comment"></textarea>
                            <div class="add-comment">
                                <div class="row">
                                    <div class="col-xs-12 col-sm-6">
                                        <div class="pull-xs-left" style="margin-top: 10px;">
                                            <div class="btn btn-primary jqs-button-cancel" href="#">Cancel</div>
                                            <div class="btn btn-primary jqs-button-save" href="#">Save</div>
                                        </div>
                                    </div>
                                    <div class="col-xs-12 col-sm-6">
                                        <#--
                                        <div class="pull-xs-right" style="margin-top: 10px;" data-dropdown-target="dropdown-canned_responses-order_comment" data-dropdown-options="click ignore-reverse-dropdown" data-dropdown-alternate-parent="comment-add">
                                            <div class="btn btn-primary btn-wrap">Add Canned Response</div>
                                        </div>
                                        -->
                                    </div>
                                </div>
                            </div>
                            <#--
                            <div style="width: 600px;" id="dropdown-canned_responses-order_comment" class="drop-down canned_responses">
                                <div>
                                    <div>
                                        <div>
                                            Premade Comments...
                                        </div>
                                        <div>
                                            <p class="jqs-canned_response response_text" data-response-id="order_comment">Left voicemail and emailed regarding declined Credit Card.</p>
                                            <p class="jqs-canned_response response_text" data-response-id="order_comment">Got new Credit Card, approved and exported.</p>
                                            <p class="jqs-canned_response response_text" data-response-id="order_comment">Spoke with customer.  Tried Credit Card and was declined.</p>
                                            <p class="jqs-canned_response response_text" data-response-id="order_comment">Got new Credit Card and was declined.</p>
                                            <p class="jqs-canned_response response_text" data-response-id="order_comment">Spoke with the customer and said will have to call back.</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            -->
                        </div>
                    </div>
                </div>
            </div>
		</div>
		<div class="col-xs-12"><hr></div>
	</div>
	<div class="panel-heading">
		<h3 class="panel-title"><strong>PRODUCT INFORMATION</strong></h3>
	</div>
	<div class="panel-body container-fluid">
		<div class="row">
			<div class="col-xs-3">
				<div class="info-header">Product Style:</div>
				<div>${customEnvelopeInfo("productId")?default("None")}</div>
			</div>
            <div class="col-xs-3">
                <div class="info-header">Quantity:</div>
                <div>${customEnvelopeInfo("quantity")?if_exists}</div>
            </div>
            <#if websiteId == "envelopes">
            <div class="col-xs-3">
                <div class="info-header">Size:</div>
                <div>${customEnvelopeInfo("standardSize")?if_exists}</div>
            </div>
            <div class="col-xs-3">
                <div class="info-header">Size Orientation:</div>
                <div><#if customEnvelopeInfo("sizeOrientation")?if_exists != "">Open on the ${customEnvelopeInfo("sizeOrientation")?if_exists} side.<#else>None.</#if></div>
            </div>
            <div class="col-xs-3">
                <div class="info-header">Has Window:</div>
                <div><#if customEnvelopeInfo("windowComment")?exists>Yes<#else>No</#if></div>
            </div>
            <div class="col-xs-3">
                <div class="info-header">Color &amp; Weight:</div>
                <div>${customEnvelopeInfo('paperType')?if_exists}</div>
            </div>
            <div class="col-xs-3">
                <div class="info-header">Printing:</div>
                <div><#if customEnvelopeInfo("printingRequired")?if_exists == "Y">Yes<#else>No</#if></div>
            </div>
            <div class="col-xs-3">
                <div class="info-header">Sealing Method:</div>
                <div>${customEnvelopeInfo("sealingMethod")?default("None")}</div>
            </div>
            <div class="col-xs-6">
                <div class="info-header">Window Comment:</div>
                <div>${customEnvelopeInfo("windowComment")?default("None")}</div>
            </div>
			</#if>
			<div class="col-xs-6">
				<div class="info-header">Project Details:</div>
				<div>${customEnvelopeInfo("comment")?default("None")}</div>
			</div>
			<#if customEnvelopeInfo("printingRequired")?if_exists == "Y">
			<div class="col-xs-3">
				<div class="info-header">Colors On Front:</div>
				<div>${customEnvelopeInfo("inkFront")?if_exists}</div>
			</div>
			<div class="col-xs-3">
				<div class="info-header">Colors On Back:</div>
				<div>${customEnvelopeInfo("inkBack")?if_exists}</div>
			</div>
			</#if>
		</div>
	</div>
</div>
<#else>
This quote does not exist.
</#if>

<div class="panel" id="quoteList">
    <div class="panel-heading">
        <h3 class="panel-title">Quote List</h3>
    </div>
    <div class="panel">
        <div class="panel-body">
			<#if quotes?has_content>
            <div class="table-responsive">
                <table class="table table-hover dataTable table-striped" id="quoteListFixedHeader">
                    <thead>
                    <tr>
                        <th></th>
                        <th>ID</th>
                        <th>PRODUCT</th>
                        <th>PRICE</th>
                        <th>INTERNAL COMMENTS</th>
                        <th>CREATED BY</th>
                        <th>CREATED DATE</th>
                        <th>PDF</th>
                        <th>ORDER</th>
                        <th>DELETE</th>
                    </tr>
                    </thead>
                    <tfoot>
                    <tr class="replace-inputs">
                        <th></th>
                        <th>ID</th>
                        <th>PRODUCT</th>
                        <th>PRICE</th>
                        <th>INTERNAL COMMENTS</th>
                        <th>CREATED BY</th>
                        <th>CREATED DATE</th>
                        <th>PDF</th>
                        <th>ORDER</th>
                        <th>DELETE</th>
                    </tr>
                    </tfoot>
                    <tbody>

					<#list quotes as quote>
                    <#assign quoteData = Static["com.bigname.quote.calculator.CalculatorHelper"].deserializedQuoteSummary(delegator, null, quote.quoteId) />
                    <#assign printMethod = Static["com.bigname.quote.calculator.CalculatorHelper"].printOptions(quoteData) />
                    <tr>
                        <td><input name="sendQuote" type="checkbox" value="${quote.quoteId}" /></td>
                        <td><a href="<@ofbizUrl>/quoteCalculator</@ofbizUrl>?quoteRequestId=${customEnvelopeInfo("quoteId")?if_exists}&quoteId=${quote.quoteId?if_exists}">${quote.quoteId}</a></td>
                        <td class="quoteDataProduct"><strong>Product Name:</strong> <#if (quoteData.product)?has_content>${quoteData.product.productId} : ${quoteData.product.productName}<#else>${quoteData.productName}</#if><br /><strong>Material:</strong> ${quoteData.material}<br /><strong>Print Method:</strong> ${printMethod?if_exists}</td>
						<td class="quoteDataPricing">
							<#list quoteData.prices.keySet() as key>
                                <div class="breakNextLine" aria-label="Extra-small button group" role="group" style="margin: 3px 0px; display: block;">
                                    <button type="button" class="btn btn-outline btn-primary">Quantity: ${key}</button><button type="button" class="btn btn-outline btn-success">Total: <@ofbizCurrency amount=(quoteData.prices(key).total)?default(0) /></button><button type="button" class="btn btn-outline btn-warning">Each: <@ofbizCurrency amount=(quoteData.prices(key).each)?default(0) /></button>
                                </div>
							</#list>
						</td>
                        <td class="quoteDataInternalComment">${quoteData.internalComment?if_exists}</td>
                        <td>${(quote.createdBy)?if_exists}</td>
                        <td>${(quote.createdStamp)?string.medium}</td>
                        <td>
                            <a href="<@ofbizUrl>/serveFileForStream</@ofbizUrl>?filePath=uploads/quotes/${customEnvelopeInfo("quoteId")?if_exists}-${quote.quoteId}.pdf&fileName=${customEnvelopeInfo("quoteId")?if_exists}-${quote.quoteId}.pdf&downLoad=Y"">Download PDF</a>
                            <button type="button" data-qid="${quote.quoteId}" class="generatePDF btn btn-warning btn-xs" style="font-size:10px;">
                                <i class="site-menu-icon fa-refresh"></i>
                            </button>
                        </td>
                        <td>
                            <a href="https://www.folders.com/folders/control/quoteOrder?quoteRequestId=${customEnvelopeInfo("quoteId")?if_exists}&quoteIds=${quote.quoteId}">Place An Order</a>
                            <#if quote.orderId?exists>
                                <br /><a href="<@ofbizUrl>/viewOrder?orderId=${quote.orderId}</@ofbizUrl>">${quote.orderId}</a>
                            </#if>
                        </td>
                        <td><a href="#" class="jqs-discontinueChildQuote" data-quoteId="${quote.quoteId}">Delete</a></td>
                    </tr>
					</#list>
                    </tbody>
                </table>
            </div>
			<#else>
				No quotes created.
			</#if>
			<div class="row">
				<#if quotes?has_content><button data-quoterequestid="${customEnvelopeInfo("quoteId")}" class="jqs-sendEmail btn btn-primary m-t-15">Send Email</button> <button data-quoterequestid="${customEnvelopeInfo("quoteId")}" class="jqs-sendQuoteToNetsuite btn btn-primary m-t-15">Send To Netsuite</button></#if> <a target="_blank" href="<@ofbizUrl>/quoteCalculator</@ofbizUrl>?quoteRequestId=${customEnvelopeInfo("quoteId")?if_exists}" class="addQuote btn btn-primary m-t-15">Create Quote</a>
			</div>
        </div>
    </div>
</div>

<script>
    var isSalesRepEqual = <#if customEnvelopeInfo.assignedTo?has_content && partyGV.salesRep?has_content && customEnvelopeInfo.assignedTo == partyGV.salesRep>true<#else>false</#if>;
    quoteWebsiteId = '${websiteId}';
</script>

<script src="/html/themes/global/vendor/toastr/toastr.js"></script>
<script src="/html/js/admin/viewCustomQuote.js?ts=2"></script>