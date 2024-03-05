<link rel="stylesheet" href="//cdn.datatables.net/1.10.2/css/jquery.dataTables.min.css">
<link href="<@ofbizContentUrl>/html/css/account/orders.css</@ofbizContentUrl>" rel="stylesheet" />

<div class="foldersContainer account foldersNewLimiter paddingTop20 paddingBottom20">
    <div class="foldersBreadcrumbs">
        <a href="<@ofbizUrl>/account</@ofbizUrl>">Your Account</a> > Order History - <#if orderType == 'open'>Open Orders<#elseif orderType == 'all'>All Orders<#else></#if>
    </div>
    <div class="foldersContainerContent orders">
    <#if (orders?size == 0)>
        <div data-alert class="alertBox">
            No <#if orderType == 'open'>open <#elseif orderType == 'all'><#else></#if>orders available.
        </div>
    </#if>
        <div id="jqs-error" data-alert class="alert-box alert radius" style="display: none">
            <span></span>
            <a href="#" class="close">&times;</a>
        </div>
		<div class="foldersRow">
			<div class="pullRight foldersButton buttonGreen elementInactive reorder-button jqs-reorder">Reorder <i class="fa fa-caret-right"></i></div>
		</div>
        <table class="table" id="orderList">
            <thead>
            <tr>
                <th class="order-info order-full-info">
                    Order Info
                </th>
                <th class="order-separate-info">
                    Order #
                </th>
                <th class="order-separate-info">
                    Order Date
                </th>
                <th class="item-info-header">
                    <div>
                        <div class="order-item-header">
                            Item
                        </div>
                        <div class="order-status-header order-separate-info">
                            Status
                        </div>
                        <div class="actions-header">
                            Actions
                        </div>
                        <div class="reorder-header order-separate-info">
                            Reorder
                        </div>
                    </div>
                </th>
            </tr>
            </thead>
            <tbody>
            <#list orders as order>
            <#if webSiteId == 'folders' && order.salesChannel == 'FOLDERS'>
            <tr>
                <td class="order-info order-full-info">
						<span>
							<a href="<@ofbizUrl>/orderView</@ofbizUrl>?orderId=${order.orderId}">${order.orderId}</a>
						</span>
                    <span>${order.orderDate}</span>
                    <span><#if order.orderStatus == 'Created' || order.orderStatus == 'Backordered'>Processing<#else>${order.orderStatus}</#if></span>
                </td>
                <td class="order-separate-info">
                    <a href="<@ofbizUrl>/orderView</@ofbizUrl>?orderId=${order.orderId}">${order.orderId}</a>
                </td>
                <td class="order-separate-info">
                ${order.orderDate}
                </td>
                <td class="item-info">
                    <#list order.items as item>
                        <#assign filePath = "" />
                        <#if item.contents?has_content>
                            <#list item.contents as content>
                                <#if content.contentPurpose?has_content && content.contentPurpose == "OIACPRP_FRONT">
                                    <#assign filePath = content.contentPath?if_exists />
                                </#if>
                            </#list>
                        </#if>
                        <#assign product = delegator.findOne("Product", {"productId": item.productId}, true)/>
                        <div>
                            <div class="order-item">
                                <#if filePath != "">
                                    <img style="width: 55px;" src="<@ofbizUrl>/serveFileForStream?filePath=${filePath}</@ofbizUrl>" alt="Front" title="Front" />
                                <#else>
                                    <img src="<#if item.scene7DesignId?has_content><@ofbizUrl>/scene7Image.png</@ofbizUrl>?id=${item.scene7DesignId}&amp;setWidth=55<#else>//actionenvelope.scene7.com/is/image/ActionEnvelope/${item.productId}?wid=55&amp;hei=35&amp;fmt=png-alpha</#if>" alt="${item.itemDescription}" />
                                </#if>
                                <a href="<@ofbizUrl>/product/~category_id=${product.primaryProductCategoryId?if_exists}/~product_id=${item.productId}</@ofbizUrl>" class="item-name">${item.itemDescription}</a>
                                <#if item.contents?has_content>
                                    <div class="padding-left-xs">
                                        <div class="hidden">
                                            <#list item.contents as content>
                                                <#if content.contentName?has_content && content.contentPurpose == 'OIACPRP_PDF'>
                                                    <a href="<@ofbizUrl>/serveFileForStream</@ofbizUrl>?filePath=${content.contentPath}&amp;fileName=${order.orderId}_${item.orderItemSeqId}.pdf&amp;downLoad=Y" class="design-name">${content.contentName}</a>
                                                </#if>
                                            </#list>
                                        </div>
                                        <a class="view_artwork">View Artwork</a>
                                    </div>
                                </#if>
                                <div class="order-full-info">
                                    Reorder <input type="checkbox" name="reorder" data-order-id="${order.orderId}" data-item-sequence-id="${item.orderItemSeqId}"/>
                                </div>
                            </div>
                            <div class="order-status order-separate-info">
                                <#if order.orderStatus == 'Cancelled' || order.orderStatus == 'Shipped'>
                                ${order.orderStatus}
                                <#else>
                                    <#if item.itemStatus == 'Created' || item.itemStatus == 'Backordered' || item.itemStatus == 'Outsourced' || item.itemStatus == 'Sent Purchase Order'>Processing<#elseif item.itemStatus == "Proof Ready for Review"><a href="<@ofbizUrl>/proofApproval</@ofbizUrl>">${item.itemStatus}</a><#else>${item.itemStatus}</#if>
                                </#if>
                            </div>
                            <div class="actions">
                                <a href="<@ofbizUrl>/orderView</@ofbizUrl>?orderId=${order.orderId}">View Order Detail</a>
                                <#if item.orderItemStatusId == "ART_NOT_RECEIVED">
                                    <a href="<@ofbizUrl>/file-transfer-center</@ofbizUrl>?orderId=${order.orderId}">Upload Files</a>
                                </#if>
                            </div>
                            <div class="reorder order-separate-info">
                                <input type="checkbox" name="reorder" data-order-id="${order.orderId}" data-item-sequence-id="${item.orderItemSeqId}" />
                            </div>
                        </div>
                        <#if item.contents?has_content>
                            <div id="${order.orderId}-${item.orderItemSeqId}-reorder-options" class="reorder_options hidden">
                                <div>
                                    <#list item.contents as content>
                                        <#if content.contentPath?ends_with('.png')>
                                            <img style="width:100px;border:1px solid #DADADA" src="<@ofbizUrl>/downloadFile</@ofbizUrl>?filePath=${content.contentPath}&amp;downLoad=Y" alt="Product Image" />
                                        </#if>
                                    </#list>
                                </div>
                                <div>
                                    <div>
                                        <input type="radio" name="options-${order.orderId}-${item.orderItemSeqId}" value="approved" class="jqs-proof-approved" id="with-no-change-${order.orderId}-${item.orderItemSeqId}"><label for="with-no-change-${order.orderId}-${item.orderItemSeqId}">Reorder with no Changes</label>
                                    </div>
                                    <div>
                                        <input type="radio" name="options-${order.orderId}-${item.orderItemSeqId}" value="with-change" id="with-change-${order.orderId}-${item.orderItemSeqId}"><label for="with-change-${order.orderId}-${item.orderItemSeqId}">Reorder with Changes</label>
                                    </div>
                                </div>
                                <div><span class="jqs-artwork-comment-error error hidden margin-bottom-xs">Please enter the reorder comments</span><textarea class="jqs-artwork-comment hidden no-margin" name="artwork-comment-${order.orderId}-${item.orderItemSeqId}" placeholder="Reorder Comments"></textarea></div>
                            </div>
                        </#if>
                    </#list>
                </td>
            </tr>
            </#if>
            </#list>
            </tbody>
        </table>
		<div class="foldersRow">
			<div class="pullRight foldersButton buttonGreen elementInactive reorder-button jqs-reorder">Reorder <i class="fa fa-caret-right"></i></div>
		</div>
    </div>
</div>

<script type="text/javascript" src="//cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/account/reorder.js</@ofbizContentUrl>"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/account/orders.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>