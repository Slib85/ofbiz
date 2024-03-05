<link rel="stylesheet" href="/html/css/admin/orders/orderList.css" />
<link rel="stylesheet" href="/html/themes/global/vendor/bootstrap-datepicker/bootstrap-datepicker.css">
<link rel="stylesheet" href="/html/themes/global/vendor/toastr/toastr.css" />
<link rel="stylesheet" href="/html/themes/base/assets/examples/css/advanced/toastr.css" />

<style>
    .vendorOrderList td.expandRow {
        position: relative;
        cursor: pointer;
    }
    .vendorOrderList td.expandRow .fa-plus {
        font-size: 16px;
        color: #00a4e4;
        position: absolute;
        top: 50%;
        transform: translate(0px, -50%);
    }
</style>

<div class="vendorOrderList">
    <#--
    <h4 class="example-title">Date Range</h4>
    <div class="example">
        <form name="orderSearch" method="GET" action="<@ofbizUrl>/vendorOrderList</@ofbizUrl>">
            <div class="input-daterange" data-plugin="datepicker">
                <div class="input-group">
                    <span class="input-group-addon"><i class="icon wb-calendar" aria-hidden="true"></i></span>
                    <input type="text" class="form-control" name="start" value="<#if requestParameters.start?has_content>${requestParameters.start}<#else>${nowTimestamp?string("yyyy-MM-dd")}</#if>"/>
                </div>
                <div class="input-group">
                    <span class="input-group-addon">to</span>
                    <input type="text" class="form-control" name="end" value="<#if requestParameters.end?has_content>${requestParameters.end}<#else>${nowTimestamp?string("yyyy-MM-dd")}</#if>" />
                    <span class="input-group-btn">
                    <button type="submit" class="btn btn-primary"><i class="icon wb-search" aria-hidden="true"></i></button>
                </span>
                </div>
            </div>
        </form>
    </div>
    -->
    <div class="panel">
        <div class="panel-heading">
            <h3 class="panel-title">Artwork Needed</h3>
        </div>
        <div class="panel-body">
            <div class="table-responsive" style="overflow-x: auto;">
                <table class="table table-hover dataTable table-striped" id="vendorArtworkList">
                    <thead>
                    <tr>
                        <th>ORDER ID</th>
                        <th>ADDRESS</th>
                        <th>FILES</th>
                        <th>STATUS</th>
                        <th>QUANTITY</th>
                        <th>DATE</th>
                        <th>DUE DATE</th>
                        <th>PROOF</th>
                    </tr>
                    </thead>
                    <tfoot>
                    <tr class="replace-inputs">
                        <th>ORDER ID</th>
                        <th>ADDRESS</th>
                        <th>FILES</th>
                        <th>STATUS</th>
                        <th>QUANTITY</th>
                        <th>DATE</th>
                        <th>DUE DATE</th>
                        <th>PROOF</th>
                    </tr>
                    </tfoot>
                    <tbody>
                    <#if orderList?has_content>
                        <#list orderList as order>
                            <#list order.items as item>
                            <#if !item.vendorOrder.purchaseOrderId?has_content>
                            <tr data-payload="${item.vendorOrder.payloadId}" data-orderid="${order.orderHeader.orderId}" data-orderitemseqid="${item.item.orderItemSeqId}" class="${item.vendorOrder.payloadId}">
                            <#--<td class="jqs-expandRow expandRow" data-payload="${item.vendorOrder.payloadId}" data-orderId="${order.orderHeader.orderId}" data-orderItemSeqId="${item.item.orderItemSeqId}"><i class="fa fa-plus"></i></td>-->
                                <td>${order.orderHeader.orderId}</td>
                                <td>
                                    <#if order.shippingAddress?has_content>
                                        <span>${order.shippingAddress.toName?if_exists}</span><br />
                                        <#if order.shippingAddress.companyName?has_content><span>${order.shippingAddress.companyName?if_exists}</span><br /></#if>
                                        <span>${order.shippingAddress.address1?if_exists}</span><br />
                                        <#if order.shippingAddress.address2?has_content><span>${order.shippingAddress.address2?if_exists}</span></#if>
                                        <span>${order.shippingAddress.city?if_exists}</span>, <span>${order.shippingAddress.stateProvinceGeoId?if_exists}</span> <span>${order.shippingAddress.postalCode?if_exists}</span><br />
                                        <span>${order.shippingAddress.countryGeoId?if_exists}</span><br />
                                        <span>${order.shippingTelecom.contactNumber?if_exists}</span>
                                    <#else>
                                        NO SHIPPING ADDRESS FOUND
                                    </#if>
                                    <br /><span>Email: ${order.orderHeader.createdBy}</span>
                                </td>
                                <td>
                                    <#if item.content?has_content>
                                        <#assign fileType = "photo" />
                                        <#assign userFiles = true />
                                        <#list item.content as content>
                                            <#if content.contentPurposeEnumId == "OIACPRP_FILE" && content.contentPath?has_content>
                                                <#if content.contentPath?has_content && content.contentPath?ends_with("pdf")>
                                                    <#assign fileType = "pdf" />
                                                <#elseif content.contentPath?has_content && content.contentPath?ends_with("psd")>
                                                    <#assign fileType = "powerpoint" />
                                                <#elseif content.contentPath?has_content && content.contentPath?ends_with("docx")>
                                                    <#assign fileType = "word" />
                                                </#if>
                                                <div class="row">
                                                    <i class="col-xs-6 fa fa-file-${fileType}-o pdf"> <a href="<@ofbizUrl>/serveFileForStream</@ofbizUrl>?filePath=${content.contentPath}&amp;fileName=${content.contentName?default("_NA_")?replace("[^A-Za-z0-9\\.\\-\\_]","","r")}&amp;downLoad=Y">${content.contentName?default("_NA_")}</a> (${content.createdStamp?date})</i>
                                                </div>
                                            </#if>
                                        </#list>
                                        <#if item.item.artworkSource?has_content && item.item.artworkSource == "SCENE7_ART_ONLINE" && item.artwork.scene7DesignId?has_content>
                                            <#assign doogmaFiles = Static["com.envelopes.scene7.Scene7Helper"].getDoogmaFiles(delegator, item.artwork.scene7DesignId)?if_exists />
                                            <#if doogmaFiles?has_content>
                                                <#list doogmaFiles as url>
                                                    <#if url?has_content>
                                                        <div class="row">
                                                            <i class="col-xs-6 fa fa-file-${fileType}-o pdf"> <a href="${url?default("_NA_")?replace("&#x2f;","/")}">Image (Doogma File)</a></i>
                                                        </div>
                                                    </#if>
                                                </#list>
                                            </#if>
                                        </#if>
                                    </#if>
                                    <#if !userFiles?exists>
                                        <div>Customer did not upload any files.</div>
                                    </#if>
                                </td>
                                <td>
                                    ${Static["com.envelopes.order.OrderHelper"].getStatusDesc(delegator, item.item.statusId?default('ITEM_CREATED'))}
                                </td>
                                <td>${item.item.quantity}</td>
                                <td>${order.orderHeader.orderDate?string["yyyy-MM-dd"]}</td>
                                <td><#if item.item.dueDate?exists>${item.item.dueDate?string["yyyy-MM-dd"]}</#if></td>
                                <td>
                                    <div class="form-group">
                                        <#if item.item.statusId != "ART_RDY_FOR_INTRNL_REVIEW">
                                        <form name="uploadProofFile" method="POST" action="<@ofbizUrl>/processSwitchProof</@ofbizUrl>" enctype="multipart/form-data">
                                            <input type="hidden" name="statusId" value="ART_RDY_FOR_INTRNL_REVIEW" />
                                            <input type="hidden" name="orderId" value="${order.orderHeader.orderId}" />
                                            <input type="hidden" name="orderItemSeqId" value="${item.item.orderItemSeqId}" />
                                            <input type="file" name="proofFile" class="form-control form-control-sm" placeholder="Upload File" />
                                            <button type="submit" class="btn btn-sm btn-success uploadProofFile">Upload</button>
                                        </form>
                                        <#elseif item.item.statusId == "ART_RDY_FOR_INTRNL_REVIEW">
                                            Proof file submitted
                                        </#if>
                                    </div>
                                </td>
                            </tr>
                            </#if>
                            </#list>
                        </#list>
                    </#if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div class="panel">
        <div class="panel-heading">
            <h3 class="panel-title">Orders</h3>
        </div>
        <div class="panel-body">
            <div class="table-responsive" style="overflow-x: auto;">
                <table class="table table-hover dataTable table-striped" id="vendorOrderList">
                    <thead>
                    <tr>
                        <th>ORDER ID</th>
                        <th>PO ID</th>
                        <th>ADDRESS</th>
                        <th>DOWNLOAD INFO</th>
                        <th>FILES</th>
                        <th>STATUS</th>
                        <th>QUANTITY</th>
                        <th>TRACKING</th>
                        <th>DATE</th>
                    </tr>
                    </thead>
                    <tfoot>
                    <tr class="replace-inputs">
                        <th>ORDER ID</th>
                        <th>PO ID</th>
                        <th>ADDRESS</th>
                        <th>DOWNLOAD INFO</th>
                        <th>FILES</th>
                        <th>STATUS</th>
                        <th>QUANTITY</th>
                        <th>TRACKING</th>
                        <th>DATE</th>
                    </tr>
                    </tfoot>
                    <tbody>
                    <#if orderList?has_content>
                        <#list orderList as order>
                            <#list order.items as item>
                            <#if item.vendorOrder.purchaseOrderId?has_content>
                            <tr data-payload="${item.vendorOrder.payloadId}" data-orderid="${order.orderHeader.orderId}" data-orderitemseqid="${item.item.orderItemSeqId}" class="${item.vendorOrder.payloadId}">
                                <#--<td class="jqs-expandRow expandRow" data-payload="${item.vendorOrder.payloadId}" data-orderId="${order.orderHeader.orderId}" data-orderItemSeqId="${item.item.orderItemSeqId}"><i class="fa fa-plus"></i></td>-->
                                <td>${order.orderHeader.orderId}</td>
                                <td>${item.vendorOrder.purchaseOrderId}</td>
                                <td>
                                    <#if order.shippingAddress?has_content>
                                        <span>${order.shippingAddress.toName?if_exists}</span><br />
                                        <#if order.shippingAddress.companyName?has_content><span>${order.shippingAddress.companyName?if_exists}</span><br /></#if>
                                        <span>${order.shippingAddress.address1?if_exists}</span><br />
                                        <#if order.shippingAddress.address2?has_content><span>${order.shippingAddress.address2?if_exists}</span></#if>
                                        <span>${order.shippingAddress.city?if_exists}</span>, <span>${order.shippingAddress.stateProvinceGeoId?if_exists}</span> <span>${order.shippingAddress.postalCode?if_exists}</span><br />
                                        <span>${order.shippingAddress.countryGeoId?if_exists}</span><br />
                                        <span>${order.shippingTelecom.contactNumber?if_exists}</span>
                                    <#else>
                                        NO SHIPPING ADDRESS FOUND
                                    </#if>
                                    <br /><span>Email: ${order.orderHeader.createdBy}</span>
                                </td>
                                <td>
                                    <a href="<@ofbizUrl>/serveFileForStream</@ofbizUrl>?filePath=uploads/purchaseorders/${order.orderHeader.orderId}-${item.vendorOrder.purchaseOrderId}.pdf&fileName=${order.orderHeader.orderId}-${item.vendorOrder.purchaseOrderId}.pdf&downLoad=Y")">Download PO</a>
                                    <button type="button" data-orderid="${order.orderHeader.orderId}" data-orderitemseqid="${item.item.orderItemSeqId}" data-purchaseorderid="${item.vendorOrder.purchaseOrderId}" class="generatePDF btn btn-warning btn-xs" style="font-size:10px;">
                                        <i class="site-menu-icon fa-refresh"></i>
                                    </button>
                                </td>
                                <td>
                                    <#if item.content?has_content>
                                        <#list item.content as content>
                                            <#if ((content.contentPurposeEnumId == "OIACPRP_PDF" || content.contentPurposeEnumId == "OIACPRP_WORKER_FRONT") && (item.item.statusId?default('ITEM_CREATED') == "ART_PROOF_APPROVED" || item.item.statusId == 'SENT_PURCHASE_ORDER')) && content.contentPath?has_content>
                                                <#assign userFiles = true />
                                                <#assign fileType = "photo" />
                                                <#if content.contentPath?has_content && content.contentPath?ends_with("pdf")>
                                                    <#assign fileType = "pdf" />
                                                <#elseif content.contentPath?has_content && content.contentPath?ends_with("psd")>
                                                    <#assign fileType = "powerpoint" />
                                                <#elseif content.contentPath?has_content && content.contentPath?ends_with("docx")>
                                                    <#assign fileType = "word" />
                                                <#elseif content.contentPath?has_content && content.contentPath?ends_with("zip")>
                                                    <#assign fileType = "zip" />
                                                </#if>
                                                <div class="row">
                                                    <i class="col-xs-6 fa fa-file-${fileType}-o pdf"> <a href="<@ofbizUrl>/serveFileForStream</@ofbizUrl>?filePath=${content.contentPath}&amp;fileName=${content.contentName?default("_NA_")?replace("[^A-Za-z0-9\\.\\-\\_]","","r")}&amp;downLoad=Y">${content.contentName?default("_NA_")}</a> (${content.createdStamp?date})</i>
                                                </div>
                                            </#if>
                                        </#list>
                                    </#if>

                                    <#if !userFiles?exists>
                                        <div>Customer did not upload any files.</div>
                                    </#if>
                                </td>
                                <td>
                                <#--
                                    <select name="item-status" class="form-control form-control-sm pull-xs-left">
                                        <option value="ITEM_CANCELLED" <#if item.item.statusId == "ITEM_CANCELLED">selected</#if>>Cancelled</option>
                                        <option value="ITEM_BACKORDERED" <#if item.item.statusId == "ITEM_BACKORDERED">selected</#if>>Backordered</option>
                                        <option value="ITEM_READY_FOR_SHIP" <#if item.item.statusId == "ITEM_READY_FOR_SHIP">selected</#if>>Ready to Ship</option>
                                        <option value="SENT_PURCHASE_ORDER" <#if item.item.statusId == "SENT_PURCHASE_ORDER">selected</#if>>Sent Purchase Order</option>
                                        <option value="ITEM_SHIPPED" <#if item.item.statusId == "ITEM_SHIPPED">selected</#if>>Shipped</option>
                                        <#if item.item.artworkSource?has_content && item.artwork?has_content>
                                            <#assign printStatuses = delegator.findByAnd("StatusItem", Static["org.apache.ofbiz.base.util.UtilMisc"].toMap("statusTypeId", "AE_ARTWORK_STTS"), Static["org.apache.ofbiz.base.util.UtilMisc"].toList("sequenceId ASC"), true) />
                                            <#list printStatuses?sort as printStatus>
                                                <option value="${printStatus.statusId}" <#if item.item.statusId == printStatus.statusId>selected</#if>>${printStatus.description?if_exists}</option>
                                            </#list>
                                        </#if>
                                    </select>
                                    &nbsp;<button id="changeItemStatus_${item.vendorOrder.payloadId}" data-current-item-status="${item.item.statusId}" class="btn btn-primary btn-sm" style="display: inline-block;">Save</button>
                                -->
                                    ${Static["com.envelopes.order.OrderHelper"].getStatusDesc(delegator, item.item.statusId?default('ITEM_CREATED'))}
                                </td>
                                <td>${item.item.quantity}</td>
                                <td>
                                    <#if !item.vendorOrder.trackingNumber?has_content>
                                    <input class="form-control" type="text" name="trackingNumber" value="" placeholder="Tracking Number" size="12" /><input class="form-control" type="text" name="packageWeight" value="" placeholder="Weight" size="3" />
                                    &nbsp;<button id="addTracking_${item.vendorOrder.payloadId}" data-current-item-status="${item.item.statusId}" class="btn btn-primary btn-sm addTracking" style="display: inline-block;">Save</button>
                                    <#else>
                                        ${item.vendorOrder.trackingNumber}
                                    </#if>
                                </td>
                                <td>${order.orderHeader.orderDate?string["yyyy-MM-dd"]}</td>
                            </tr>
                            </#if>
                            </#list>
                        </#list>
                    </#if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script src="/html/themes/global/vendor/toastr/toastr.js"></script>
<script src="/html/themes/global/vendor/bootstrap-datepicker/bootstrap-datepicker.js"></script>
<script src="/html/themes/global/js/Plugin/bootstrap-datepicker.js"></script>
<script src="<@ofbizContentUrl>/html/js/admin/vendorOrderList.js</@ofbizContentUrl>?ts=3"></script>