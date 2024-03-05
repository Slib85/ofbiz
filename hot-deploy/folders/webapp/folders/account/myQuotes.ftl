<link rel="stylesheet" href="//cdn.datatables.net/1.10.2/css/jquery.dataTables.min.css">
<link href="<@ofbizContentUrl>/html/css/folders/account/myQuotes.css</@ofbizContentUrl>" rel="stylesheet" />

<div class="foldersContainer account">
    <div class="foldersBreadcrumbs">
        <a href="<@ofbizUrl>/account</@ofbizUrl>">Your Account</a> > My Quotes
    </div>
    <div class="foldersContainerContent myQuotes">
    <#if (quoteList?size == 0)>
        <div data-alert class="alertBox">
            No quotes available.
        </div>
    </#if>
        <div class="foldersRow">
            <div class="foldersColumn large6">
                <h3>Sales Rep. Information</h3>
                <span class="textBold">Email:</span> <a href="mailto:${salesRepInfo.get("email")}">${salesRepInfo.get("email")}</a><br />
                <span class="textBold">Phone:</span> <a href="tel:${salesRepInfo.get("userInfo").get("phoneNumber")}">${salesRepInfo.get("userInfo").get("phoneNumber")}</a>
            </div>
            <div class="foldersColumn large6">
                <label class="bigNameSelect">
                    <select name="statusId">
                        <option value="all">All Quotes</option>
                        <option value="open">Open Quotes</option>
                        <option value="ordered">Ordered Quotes</option>
                    </select>
                </label>
            </div>
        </div>
        <table class="table" id="quoteList">
            <thead>
                <tr>
                    <th>
                        <div class="foldersTabularRow">
                            <div class="quoteNumberColumn">
                                Quote #
                            </div>
                            <div class="productDescriptionColumn">
                                Product Description
                            </div>
                            <div class="quantitiesColumn">
                                Quantities
                            </div>
                            <div class="printingMethodColumn">
                                Printing Method
                            </div>
                            <div class="imagesColumn">
                                Images
                            </div>
                            <div class="productionTimeColumn">
                                Production Time
                            </div>
                            <div class="viewFullQuoteColumn"></div>
                        </div>
                    </th>
                </tr>
            </thead>
            <tbody>
                <#list quoteList as quote>
                    <#assign dateDifference = Static["com.envelopes.util.EnvUtil"].getDaysBetweenDates(quote.createdStamp, Static["org.apache.ofbiz.base.util.UtilDateTime"].nowTimestamp(), true) />
                    <tr bns-statusid="${quote.statusId?default("QUO_REJECTED")}">
                        <td colspan="7">
                            <div class="foldersTabularRow">
                                <div class="quoteNumberColumn">
                                    <span class="hideOnDesktop showOnMobile">Quote #</span>
                                    <div class="textCenter textBold">${quote.quoteRequestId?if_exists}-${quote.quoteId?if_exists}</div>
                                    <#if quote.pricingRequest.STYLE?exists>
                                    <img src="https://actionenvelope.scene7.com/is/image/ActionEnvelope/${quote.pricingRequest.STYLE}?wid=150" alt="${quote.quoteRequestId?if_exists}-${quote.quoteId?if_exists}" />
                                    </#if>
                                </div>
                                <div class="productDescriptionColumn">
                                    <span class="hideOnDesktop showOnMobile">Product Description</span>
                                    ${quote.pricingResponse.details?if_exists}
                                </div>
                                <div class="quantitiesColumn">
                                    <span class="hideOnDesktop showOnMobile">Quantities</span>
                                    <#list 1..quote.pricingResponse.pricingDetails.get(0)?size - 1 as index>
                                    <div>
                                        <span class="textBold">Quantity:</span> ${quote.pricingResponse.pricingDetails.get(0).get(index)}<#if dateDifference lte 60> - <span class="textBold">Price:</span> $${quote.pricingResponse.pricingDetails.get(quote.pricingResponse.pricingDetails?size - 2).get(index)} - <span class="textBold">Each:</span> $${quote.pricingResponse.pricingDetails.get(quote.pricingResponse.pricingDetails?size - 1).get(index)}</#if>
                                    </div>
                                    </#list>
                                </div>
                                <div class="printingMethodColumn">
                                    <span class="hideOnDesktop showOnMobile">Printing Method</span>
                                    <#assign printMethods = "" />
                                    <#assign totalImages = 0 />
                                    <#if quote.pricingRequest.PRINT_OPTIONS?exists>
                                        <#list quote.pricingRequest.PRINT_OPTIONS as printOption>
                                            <#assign printMethods = printMethods + printOption.PRINT_OPTION_NAME?if_exists + ", " />

                                            <@getTotalQuoteImages printOption=printOption />
                                        </#list>
                                    </#if>

                                    <#macro getTotalQuoteImages printOption>
                                        <#if printOption.SIDES?exists>
                                            <#list printOption.SIDES as side>
                                                <@getTotalQuoteImages printOption=side />
                                            </#list>
                                        <#elseif printOption.RUNS?exists>
                                            <#list printOption.RUNS as run>
                                                <#assign totalImages = totalImages + run.IMAGES?size />
                                            </#list>
                                        </#if>
                                    </#macro>
                                    <div>
                                        <#if printMethods != "">${printMethods?replace(", $", "", "r")?replace("_", " ", "r")?capitalize}<#else>None</#if>
                                    </div>
                                </div>
                                <div class="imagesColumn">
                                    <span class="hideOnDesktop showOnMobile">Images</span>
                                    ${totalImages}
                                </div>
                                <div class="productionTimeColumn">
                                    <span class="hideOnDesktop showOnMobile">Production Time</span>
                                    ${quote.production?default("Not Specified")}
                                </div>
                                <div class="viewFullQuoteColumn textCenter">
                                    <#if dateDifference gt 60>
                                    <div bns-newquote data-productid="${quote.productId?if_exists}" data-useremail="${quote.userEmail?if_exists}" data-firstname="${quote.firstName?if_exists}" data-lastname="${quote.lastName?if_exists}" data-companyname="${quote.companyName?if_exists}" data-address1="${quote.address1?if_exists}" data-address2="${quote.address2?if_exists}" data-city="${quote.city?if_exists}" data-stateprovincegeoid="${quote.stateProvinceGeoId?if_exists}" data-postalcode="${quote.postalCode?if_exists}" data-phone="${quote.phone?if_exists}" data-countrygeoid="${quote.countryGeoId?if_exists}" data-internalcomment="${quote.internalComment?if_exists}" class="foldersButton buttonGreen textBold">Create New Quote</div>
                                    <#else>
                                    <a href="<@ofbizUrl>/quoteOrder?quoteRequestId=${quote.quoteRequestId?if_exists}&quoteIds=${quote.quoteId?if_exists}</@ofbizUrl>" class="foldersButton buttonGreen textBold">View Quote/Reorder <i class="fa fa-caret-right"></i></a>
                                    </#if>
                                    <a href="<@ofbizUrl>serveFileForStream?filePath=uploads/quotes/${quote.quoteRequestId?if_exists}-${quote.quoteId?if_exists}.pdf&fileName=${quote.quoteRequestId?if_exists}-${quote.quoteId?if_exists}.pdf&downLoad=Y</@ofbizUrl>">Download Quote PDF</a>
                                </div>
                                <#--  <div class="hidden">
                                    ${quote.statusId?default("QUO_REJECTED")}
                                </div>  -->
                            </div>
                            <#if quote.comment?exists || (quote.proofPDFContentPath?exists && quote.proofPDFContentName?exists)>
                            <div class="foldersTabularRow">
                                <div>
                                    <#if quote.comment?exists>
                                    <div>
                                        <span class="textBold">Comments:</span> ${quote.comment} 
                                    </div> 
                                    </#if>
                                    <#if quote.proofPDFContentPath?exists && quote.proofPDFContentName?exists>
                                    <div>
                                        <span class="textBold">Artwork:</span> <a href="<@ofbizUrl>${quote.proofPDFContentPath}</@ofbizUrl>">${quote.proofPDFContentName}</a>
                                    </div>
                                    </#if>
                                </div>
                            </div>
                            </#if>
                        </td>
                    </tr>
                </#list>
            </tbody>
        </table>
    </div>
</div>

<script type="text/javascript" src="//cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/html/js/folders/account/myQuotes.js</@ofbizContentUrl>"></script>
