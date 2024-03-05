<link href="<@ofbizContentUrl>/html/css/folders/account/quoteOrder.css</@ofbizContentUrl>" rel="stylesheet" />

<#assign quoteOrderCounter = 0 />
<#list quotes.keySet() as key>
<div class="jqs-wrapper foldersContainer artworkChecklist quoteListContainer">
    <div class="foldersContainerContent">
        <div class="foldersContainerHeader">
            <h1 class="ftc-blue">Quote ${quotes[key].quoteRequestId}-${quotes[key].quoteId}</h1>
        </div>
        <div class="foldersContainerBody">
            <div class="foldersRow">
                <div class="foldersColumn large6 small12">
                    <h3>Product Description</h3>
                    <p><#if (quotes[key].product)?has_content>${quotes[key].product.productName}<#else>${quotes[key].productName}</#if></p>
                </div>
                <div class="foldersColumn large6 small12">
                    <div class="foldersRow">
                        <div class="foldersColumn large6 small4 productAttr">
                            <p>Material/Stock:</p>
                        </div>
                        <div class="foldersColumn large6 small8">
                            <p>${quotes[key].material}</p>
                        </div>
                    </div>
                    <div class="foldersRow">
                        <div class="foldersColumn large6 small4 productAttr">
                            <p>Print Methods:</p>
                        </div>
                        <div class="foldersColumn large6 small8">
                            <p>${quotes[key].printSummary}</p>
                        </div>
                    </div>
                    <div class="foldersRow">
                        <div class="foldersColumn large6 small4 productAttr">
                            <p>Shipping:</p>
                        </div>
                        <div class="foldersColumn large6 small8">
                            <p>Ground Service</p>
                        </div>
                    </div>
                    <div class="foldersRow">
                        <div class="foldersColumn large6 small4 productAttr">
                            <p>Terms:</p>
                        </div>
                        <div class="foldersColumn large6 small8">
                            <p>See Below</p>
                        </div>
                    </div>
                </div>
            </div>
            <table class="quantitiesTable">
                <tbody>
                    <#-- <img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/${quotes[key].product.productId}" alt="${quotes[key].product.productId}"> -->
                    <tr class="fbc-blue">
                        <th>Quantity</th>
                        <#list quotes[key].prices.keySet() as quantity>
                            <th>${quantity}</th>
                        </#list>
                    </tr>
                    <tr>
                        <td>Total</td>
                        <#list quotes[key].prices.keySet() as quantity>
                            <td><strike class="marginRight10"><@ofbizCurrency amount=(quotes[key].prices.get(quantity).total / 0.85) /></strike> <span class="ftc-orange textBold"><@ofbizCurrency amount=(quotes[key].prices.get(quantity).total) /></span></td>
                        </#list>
                    </tr>
                    <tr>
                        <td>Each</td>
                        <#list quotes[key].prices.keySet() as quantity>
                            <td><@ofbizCurrency amount=(quotes[key].prices.get(quantity).each) /> ea</td>
                        </#list>
                    </tr>
                    <tr>
                        <td></td>
                        <#list quotes[key].prices.keySet() as quantity>
                        <td>
                            <a class="jqs-addItem foldersButton buttonGreen" data-quantity="${quantity}" data-customsku="${quotes[key].customSKU?if_exists}" data-quoteid="${quoteRequest.quoteId}" data-quoteitemseqid="${quotes[key].quoteId}" data-quantity="${quantity}" data-colorsfront="${quotes[key].colorsFront}" data-colorsback="${quotes[key].colorsBack}">Add to Cart</a>
                        </td>
                        </#list>
                    </tr>
                </tbody>
            </table>

            <div class="quoteBottomDetails">
                <h3>Upload Artwork:</h3>
                <form method="POST" action="<@ofbizUrl>/uploadScene7Files</@ofbizUrl>" enctype="multipart/form-data" class="jqs-upload">
                    <#-- <input type="hidden" name="orderId" value="${orderItem.orderId}" />
                    <input type="hidden" name="orderItemSeqId" value="${orderItem.orderItemSeqId}" /> -->
                    <input type="file" name="upl" multiple style="position: absolute; top: -100000px; display: block"/>
                    <div class="foldersTabularRow artworkRow marginTop20">
                        <div class="noPadding">
                            <div class="foldersButton buttonGold noMargin upload-file-button">Upload File</div>
                        </div>
                        <div id="drop${quoteOrderCounter}" class="dropzone noPadding">
                            <div id="files${quoteOrderCounter}" class="design-files padding10 jqs-designFiles">
                                <div class="placeHolder" style="text-align: center;">DRAG FILES HERE</div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

            <div class="quoteBottomDetails">
                <#if (quotes[key].production)?has_content>
                <h3>Production Time:</h3>
                <p>${quotes[key].production}</p>
                </#if>
                <h3>Comments:</h3>
                <p>${(quotes[key].comment)?default("No Comments")}</p>
                <textarea class="jqs-comments" name="itemComment" placeholder="Please provide additional instructions or comments for your order (foil color, ink color, coatings, deadline, etc).  You may reuse previous artwork by providing us with an order number that has the artwork in it."></textarea>
            </div>
            <#if key_has_next>
                <hr/>
            </#if>

            <div class="quoteBottomDetails">
                <h3>Notes:</h3>
                <p>Free ground shipping included in quote. Applicable taxes will be collected on orders that ship to NY, PA &amp; CA.</p>
                <h3>Artwork and Art Services:</h3>
                <p>The quoted price is based on the customer supplying digital art, with all elements in position, on a supplied template ready for printing. It also includes ½ hour of time to review art, layout and provide typesetting assistance for the 1st proof. If additional proofs are needed and/or more time is needed to review multiple customer supplied art files, this may require additional charges to be billed at hourly rates.</p>
                <h3>Terms of Sale:</h3>
                <p>The entire order is payable in full at time of order. In addition, there is a maximum of 10% over run and 10% under run which will be billed or credited at the unit purchased price for the actual quantity of units shipped.</p>
            </div>
        </div>
    </div>
</div>
<#assign quoteOrderCounter = quoteOrderCounter + 1 />
</#list>

<script type="text/javascript" src="<@ofbizContentUrl>/html/js/folders/account/quoteOrder.js</@ofbizContentUrl>?ts=${pageTimestamp?default("65535")}"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/addons/fileupload/jquery.fileupload.js</@ofbizContentUrl>"></script>