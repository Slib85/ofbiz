<div class="row">
    <div class="col-md-6">
        <div class="panel">
            <div class="panel-body">
                <ul class="list-group list-group-bordered">
                    <li class="list-group-item active">
                        <div class="row">
                            <div class="col-md-9">BigName Product Attributes</div>
                            <div class="col-md-3">
                                <div class="pull-xs-right">
                                    <a class="jqs-export panel-action icon fa-exchange" data-toggle="tooltip"
                                       data-original-title="Upload Bigname Hierarchy to Marketplace" data-container="body" title=""></a>
                                </div>
                            </div>
                        </div>
                    </li>
                    <#list bignameProductAttributes as attribute>
                        <li class="list-group-item">
                            <div class="row">
                                <div class="col-md-10"><div class="row"><div class="col-md-4">${attribute.attributeLabel}</div><div class="col-md-8"><span class="tag tag-round tag-dark m-l-5 m-r-5">${attribute.attributeId}</span><span class="tag tag-round tag-info m-l-5 m-r-5">${attribute.type}</span><span class="tag tag-round tag-${(attribute.required == 'Y')?string('danger', 'default')} m-l-5 m-r-5">${(attribute.required == 'Y')?string('REQUIRED', 'OPTIONAL')}</span></div></div></div>
                                <div class="col-md-2">
                                    <#--<div class="pull-xs-right">
                                        <a class="panel-action icon fa-remove" data-toggle="tooltip"
                                           data-original-title="send-to-marketplace" data-container="body" title=""></a>
                                    </div>-->
                                </div>
                            </div>
                        </li>
                    </#list>
                </ul>
            </div>
        </div>
    </div>

    <div class="col-md-6">
        <div class="panel">
            <div class="panel-body">
                <ul class="jqs-attributes list-group list-group-bordered">
                    <li class="list-group-item active">
                        <div class="row">
                            <div class="col-md-9">Seller Product Attributes</div>
                            <div class="col-md-3">
                                <div class="pull-xs-right">
                                    <a class="jqs-sync panel-action icon fa-refresh" data-toggle="tooltip"
                                       data-original-title="Upload Bigname Hierarchy to Marketplace" data-container="body" title=""></a>
                                </div>
                            </div>
                        </div>
                    </li>
                    <#list miraklProductAttributes as attribute>
                        <li class="jqs-attribute list-group-item">
                            <div class="row">
                                <div class="col-md-12"><div class="row"><div class="col-md-4">${attribute.attributeLabel}</div><div class="col-md-8"><span class="tag tag-round tag-dark m-l-5 m-r-5">${attribute.attributeId}</span><span class="tag tag-round tag-info m-l-5 m-r-5">${attribute.type}</span><span class="tag tag-round tag-${(attribute.required == 'Y')?string('danger', 'default')} m-l-5 m-r-5">${(attribute.required == 'Y')?string('REQUIRED', 'OPTIONAL')}</span></div></div></div>
                            </div>
                        </li>
                    </#list>
                    <#--<li class="list-group-item">
                        <div class="row">
                            <div class="col-md-10">Category <span class="tag tag-round tag-dark m-l-5 m-r-5">CATEGORY</span><span class="tag tag-round tag-info m-l-5 m-r-5">TEXT</span><span class="tag tag-round tag-warning m-l-5 m-r-5">REQUIRED</span></div>
                            <div class="col-md-2">
                                &lt;#&ndash;<div class="pull-xs-right">
                                    <a class="panel-action icon fa-remove" data-toggle="tooltip"
                                       data-original-title="send-to-marketplace" data-container="body" title=""></a>
                                </div>&ndash;&gt;
                            </div>
                        </div>
                    </li>
                    <li class="list-group-item">
                        <div class="row">
                            <div class="col-md-10">Code <span class="tag tag-round tag-dark m-l-5 m-r-5">IDENTIFIER</span><span class="tag tag-round tag-info m-l-5 m-r-5">TEXT</span><span class="tag tag-round tag-warning m-l-5 m-r-5">REQUIRED</span></div>
                            <div class="col-md-2">
                                &lt;#&ndash;<div class="pull-xs-right">
                                    <a class="panel-action icon fa-remove" data-toggle="tooltip"
                                       data-original-title="send-to-marketplace" data-container="body" title=""></a>
                                </div>&ndash;&gt;
                            </div>
                        </div>
                    </li>
                    <li class="list-group-item">
                        <div class="row">
                            <div class="col-md-10">Product Name <span class="tag tag-round tag-dark m-l-5 m-r-5">TITLE</span><span class="tag tag-round tag-info m-l-5 m-r-5">TEXT</span><span class="tag tag-round tag-warning m-l-5 m-r-5">REQUIRED</span></div>
                            <div class="col-md-2">
                                &lt;#&ndash;<div class="pull-xs-right">
                                    <a class="panel-action icon fa-remove" data-toggle="tooltip"
                                       data-original-title="send-to-marketplace" data-container="body" title=""></a>
                                </div>&ndash;&gt;
                            </div>
                        </div>
                    </li>-->
                </ul>
            </div>
        </div>
    </div>
</div>
<script src="/html/js/admin/marketplace/productAttributes.js"></script>
