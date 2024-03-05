<link href="<@ofbizContentUrl>/html/css/account/account.css</@ofbizContentUrl>" rel="stylesheet"/>
<link href="<@ofbizContentUrl>/html/css/account/designs.css</@ofbizContentUrl>" rel="stylesheet"/>

<div class="content account">
    <div class="content-breadcrumbs">
        <a href="<@ofbizUrl>/account</@ofbizUrl>">Your Account</a> > Uploaded Images / Files
    </div>
    <div class="container uploaded-files padding-xs">
        <div class="row uploaded-files-list">
            <#if orders?has_content>
                <#list orders as order>
                <div class="padding-xxs margin-right-xxs margin-top-xxs left" style="min-width:200px; padding:5px; border: 1px solid #f1f1f1">
                    <div>
                        <span class="order-id" style="color: #FC7E22; font-weight: bold">${order.orderId?if_exists}</span>
                    </div>
                    <div style="padding: 0 10px 10px 20px">
                        <#list order.orderItemContents as content>
                        <span style="display: block">
                            <#assign fileType = "photo" />
                            <#if content.contentPath?has_content && content.contentPath?ends_with("pdf")>
                                <#assign fileType = "pdf" />
                            <#elseif content.contentPath?has_content && content.contentPath?ends_with("psd")>
                                <#assign fileType = "powerpoint" />
                            <#elseif content.contentPath?has_content && content.contentPath?ends_with("docx")>
                                <#assign fileType = "word" />
                            </#if>
                            <i class="col-xs-6 fa fa-file-${fileType}-o pdf"> <a href="/envelopes/control/serveFileForStream?filePath=${content.contentPath}&amp;fileName=${content.contentName}&amp;downLoad=Y">${content.contentName?default("_NA_")}</a></i>
                        </span>
                        </#list>
                    </div>
                </div>
                </#list>
            </#if>
        </div>
    </div>
</div>
<script>
    $(function(){
        function resize() {
            var element = $(".uploaded-files-list");
            var total_width = element.innerWidth();
            var min_width = parseInt(element.children(":first-child").css("min-width")) + 13;
            var total_children_per_row = parseInt(total_width / min_width);
            var width_percent = 0;

            // Adjust the width
            element.children().each(function() {
                var new_width;
                width_percent = parseInt(100 / (total_children_per_row <= element.children().length ? total_children_per_row : element.children().length));

                new_width = total_children_per_row == 1 ? "100%" : (Math.floor(total_width * (width_percent / 100)) - 11) + "px";

                $(this).css({
                    "width":new_width
                });
            });

            var max_height = 0;


            // Adjust the height;
            element.children().each(function() {
                $(this).css({
                    "height":"none"
                });

                if ($(this).outerHeight() > max_height) {
                    max_height = $(this).outerHeight();
                }
            });

            element.children().each(function() {
                $(this).css({
                    "height":max_height + "px"
                });
            });
        }

        resize();
    });

</script>
