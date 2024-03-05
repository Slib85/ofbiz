<style>
    .table {
        font-size: 12px;
    }
    .table td {
        white-space: nowrap;
    }
    .table a {
        text-decoration: none;
    }
</style>

<div class="panel panel-primary panel-line">
    <div class="panel-body">
        <form id="createSEO" method="POST" action="<@ofbizUrl>/setSEORule</@ofbizUrl>" class="form-wizard" enctype="application/x-www-form-urlencoded">
            <input type="hidden" name="ruleId" value="${(rule.ruleId)?if_exists}" />
            <input type="hidden" name="manual" value="Y" />
            <div class="row">
                <div class="col-md-2">
                    <div class="form-group">
                        <label class="control-label">Page</label>
                        <input class="form-control" name="currentView" placeholder="Page" value="${(rule.currentView)?if_exists}" />
                    </div>
                </div>
                <div class="col-md-1">
                    <div class="form-group">
                        <label class="control-label">ID</label>
                        <input class="form-control" name="id" placeholder="ID" value="${(rule.id)?if_exists}" />
                    </div>
                </div>
                <div class="col-md-1">
                    <div class="form-group">
                        <label class="control-label">SKU</label>
                        <input class="form-control" name="productId" placeholder="SKU" value="${(rule.productId)?if_exists}" />
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label class="control-label">Keyword</label>
                        <input class="form-control" name="keyword" placeholder="Keyword" value="${(rule.keyword)?if_exists}" />
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label class="control-label">Category</label>
                        <input class="form-control" name="categoryId" placeholder="Category" value="${(rule.categoryId)?if_exists}" />
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label class="control-label">Style</label>
                        <input class="form-control" name="styleId" placeholder="Style" value="${(rule.styleId)?if_exists}" />
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label class="control-label">Color Group</label>
                        <input class="form-control" name="colorGroupId" placeholder="Color Group" value="${(rule.colorGroupId)?if_exists}" />
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-2">
                    <div class="form-group">
                        <label class="control-label">Color</label>
                        <input class="form-control" name="colorId" placeholder="Color" value="${(rule.colorId)?if_exists}" />
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label class="control-label">Collection</label>
                        <input class="form-control" name="collectionId" placeholder="Collection" value="${(rule.collectionId)?if_exists}" />
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label class="control-label">Sealing Method</label>
                        <input class="form-control" name="sealingId" placeholder="Sealing Method" value="${(rule.sealingId)?if_exists}" />
                    </div>
                </div>
                <div class="col-md-1">
                    <div class="form-group">
                        <label class="control-label">Image Name</label>
                        <input class="form-control" name="imageName" placeholder="Image Name" value="${(rule.imageName)?if_exists}" />
                    </div>
                </div>
                <div class="col-md-1">
                    <div class="form-group">
                        <label class="control-label">Size</label>
                        <input class="form-control" name="sizeId" placeholder="Size" value="${(rule.sizeId)?if_exists}" />
                    </div>
                </div>
                <div class="col-md-1">
                    <div class="form-group">
                        <label class="control-label">Paper Weight</label>
                        <input class="form-control" name="paperWeightId" placeholder="Paper Weight" value="${(rule.paperWeightId)?if_exists}" />
                    </div>
                </div>
                <div class="col-md-1">
                    <div class="form-group">
                        <label class="control-label">Finish</label>
                        <input class="form-control" name="finishId" placeholder="Finish" value="${(rule.finishId)?if_exists}" />
                    </div>
                </div>
                <div class="col-md-1">
                    <div class="form-group">
                        <label class="control-label">Rating</label>
                        <input class="form-control" name="ratingId" placeholder="Rating" value="${(rule.ratingId)?if_exists}" />
                    </div>
                </div>
                <div class="col-md-1">
                    <div class="form-group">
                        <label class="control-label">Product Type</label>
                        <input class="form-control" name="productTypeId" placeholder="Product Type" value="${(rule.productTypeId)?if_exists}" />
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-1">
                    <div class="form-group">
                        <label class="control-label">Customizable</label>
                        <input class="form-control" name="customizableId" placeholder="Customizable" value="${(rule.customizableId)?if_exists}" />
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label class="control-label">Title</label>
                        <input class="form-control" name="metaTitle" placeholder="Title" value="${(rule.metaTitle)?if_exists}" />
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label class="control-label">H1</label>
                        <input class="form-control" name="h1" placeholder="H1" value="${(rule.h1)?if_exists}" />
                    </div>
                </div>
                <div class="col-md-1">
                    <div id="seoH1Color" class="form-group">
                        <label class="control-label">H1 Color</label>
                        <div>
                            <input type="radio" name="h1Color" value="000000" <#if !(rule.h1Color)?exists || rule.h1Color == '000000'>checked</#if>/>
                            <label>Black</label>
                        </div>
                        <div>
                            <input type="radio" name="h1Color" value="ffffff" <#if (rule.h1Color)?exists && rule.h1Color = 'ffffff'>checked</#if>/>
                            <label>White</label>
                        </div>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label class="control-label">H2</label>
                        <input class="form-control" name="h2" placeholder="H2" value="${(rule.h2)?if_exists}" />
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label class="control-label">H3</label>
                        <input class="form-control" name="h3" placeholder="H3" value="${(rule.h3)?if_exists}" />
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label class="control-label">Banner Background Color</label>
                        <input class="form-control" name="backgroundBannerColor" placeholder="Color" value="${(rule.backgroundBannerColor)?if_exists}" />
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-2">
                    <div class="form-group">
                        <label class="control-label">Meta Description</label>
                        <textarea class="form-control" name="metaDescription" placeholder="Description">${(rule.metaDescription)?if_exists}</textarea>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label class="control-label">Meta Keywords</label>
                        <textarea class="form-control" name="metaKeywords" placeholder="Keywords">${(rule.metaKeywords)?if_exists}</textarea>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label class="control-label">Page Description</label>
                        <textarea class="form-control" name="pageDescription" placeholder="Page Description">${(rule.pageDescription)?if_exists}</textarea>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label class="control-label">Alt Page Description</label>
                        <textarea class="form-control" name="altPageDescription" placeholder="Alt Page Description">${(rule.altPageDescription)?if_exists}</textarea>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label class="control-label">Footer Description</label>
                        <textarea class="form-control" name="footerDescription" placeholder="Footer Description">${(rule.footerDescription)?if_exists}</textarea>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label class="control-label">WebSite ID</label>
                        <select name="webSiteId" class="form-control" data-allow-clear="true" data-placeholder="Website ID">
                            <option value=""></option>
                            <option value="ae" <#if (rule.webSiteId)?exists && rule.webSiteId == "ae">selected</#if>>ActionEnvelope.com</option>
                            <option value="envelopes" <#if (rule.webSiteId)?exists && rule.webSiteId == "envelopes">selected</#if>>Envelopes.com</option>
                            <option value="folders" <#if (rule.webSiteId)?exists && rule.webSiteId == "folders">selected</#if>>Folders.com</option>
                            <option value="bags" <#if (rule.webSiteId)?exists && rule.webSiteId == "bags">selected</#if>>Bags.com</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="row text-right margin-right-15">
                <button id="submitSeoRule" type="submit" class="btn btn-success">
                    <i class="icon wb-check" aria-hidden="true"></i>SAVE
                </button>
            </div>
        </form>
	</div>
</div>

<#if rules?has_content>
<div class="row margin-top-15">
	<div class="col-md-12">
        <div class="panel panel-primary panel-line">
			<!-- panel head -->
            <div class="panel-heading">
                <h3 class="panel-title">All Rules</h3>
            </div>

			<!-- panel body -->
			<div class="panel-body">
				<table class="table responsive table-striped" id="ruleTable">
					<thead>
					<tr>
						<th>Rule ID</th>
						<th>WebSite ID</th>
						<th>Page</th>
						<th>Product ID</th>
						<th>Keyword</th>
						<th>Category ID</th>
						<th>Style ID</th>
						<th>Color Group ID</th>
						<th>Color ID</th>
						<th>Collection</th>
						<th>Sealing Method</th>
						<th>Size</th>
						<th>Paper Weight</th>
						<th>Rating</th>
						<th>Customizable</th>
						<th></th>
					</tr>
					</thead>

					<tbody>
						<#list rules as rule>
							<tr>
								<td><a href="<@ofbizUrl>/seoRules?ruleId=${rule.ruleId?if_exists}</@ofbizUrl>">${rule.ruleId?if_exists}</a></td>
								<td>${rule.webSiteId?if_exists}</td>
								<td>${rule.currentView?if_exists}</td>
								<td>${rule.productId?if_exists}</td>
								<td>${rule.keyword?if_exists}</td>
								<td>${rule.categoryId?if_exists}</td>
								<td>${rule.styleId?if_exists}</td>
								<td>${rule.colorGroupId?if_exists}</td>
								<td>${rule.colorId?if_exists}</td>
								<td>${rule.collectionId?if_exists}</td>
								<td>${rule.sealingId?if_exists}</td>
								<td>${rule.sizeId?if_exists}</td>
								<td>${rule.paperWeightId?if_exists}</td>
								<td>${rule.ratingId?if_exists}</td>
								<td>${rule.customizableId?if_exists}</td>
								<td><a data-rule-id="${rule.ruleId?if_exists}" href="<@ofbizUrl>/removeSEORule</@ofbizUrl>?ruleId=${rule.ruleId?if_exists}" class="removeSEORule btn btn-danger btn-xs">
                                    <i class="icon wb-link" aria-hidden="true"></i>Delete
								</a></td>
							</tr>
						</#list>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</#if>

<script src="<@ofbizContentUrl>/html/js/admin/seorules.js</@ofbizContentUrl>"></script>