<link href="<@ofbizContentUrl>/html/css/account/account.css</@ofbizContentUrl>" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/account/designs.css</@ofbizContentUrl>" rel="stylesheet" />

<div class="content account">
    <div class="content-breadcrumbs">
        <a href="<@ofbizUrl>/account</@ofbizUrl>">Your Account</a> > Direct Mailing Documents
    </div>
    <div class="container designs padding-xs">
        <div id="design-message" data-alert class="alert-box success radius margin-top-xxs hidden">Document has been successfully deleted.</div>
        <div id="design-error" data-alert class="alert-box alert radius margin-top-xxs hidden">An error occurred while deleting the Document.</div>

        <div class="row design-list">
        <#list documents as document>

            <div id="document-${document.directMailingContentId}" class="padding-xxs margin-right-xxs margin-top-xxs left" style="min-width: 200px">
                <div style="min-height: 100px">
                    <span style="display: block">
                            <#assign fileType = "photo" />
                        <#if document.contentPath?has_content && document.contentPath?ends_with("pdf")>
                            <#assign fileType = "pdf" />
                        <#elseif document.contentPath?has_content && document.contentPath?ends_with("psd")>
                            <#assign fileType = "powerpoint" />
                        <#elseif document.contentPath?has_content && document.contentPath?ends_with("docx")>
                            <#assign fileType = "word" />
                        </#if>
                        <i class="col-xs-6 fa fa-file-${fileType}-o pdf"> <a target="_blank" href="/envelopes/control/serveFileForStream?filePath=${document.contentPath}&amp;fileName=${document.contentName}&amp;downLoad=Y">${document.contentName?default("_NA_")}</a></i>
                    </span>
                </div>
                <div>
					<span class="jqs-name">
                    ${document.lastUpdatedStamp}
					</span>
                    <div>
                        <span>
                            <a href="javascript:void(0)" class="jqs-delete-design" data-document-id="${document.directMailingContentId}">delete</a>
                        </span>
                    </div>

                </div>
            </div>
        </#list>
        </div>
    </div>
</div>
<script>
    var deleteDocumentEndPoint = '<@ofbizUrl>/deleteDirectMailingDocument</@ofbizUrl>';
</script>
<script src="<@ofbizContentUrl>/html/js/account/direct-mailing-documents.js</@ofbizContentUrl>"></script>