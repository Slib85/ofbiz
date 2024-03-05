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
		<form id="createRewrite" method="POST" action="<@ofbizUrl>/setRewriteRule</@ofbizUrl>" class="form-wizard" enctype="application/x-www-form-urlencoded">
			<input type="hidden" name="ruleId" value="${(rule.ruleId)?if_exists}" />
			<input type="hidden" name="manual" value="Y" />
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label">From Url</label>
						<input class="form-control" name="fromUrl" placeholder="From Url" value="${(rule.fromUrl)?if_exists}" />
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label">To Url</label>
						<input class="form-control" name="toUrl" placeholder="To Url" value="${(rule.toUrl)?if_exists}" />
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label">Product ID</label>
						<input class="form-control" name="productId" placeholder="Product ID" value="${(rule.productId)?if_exists}" />
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label">Rewrite Type</label>
						<select name="rewriteTypeId" class="form-control" data-allow-clear="false" data-placeholder="Rewrite Type">
							<option value="REWRITE" <#if (rule.rewriteTypeId)?exists && rule.rewriteTypeId == "REWRITE">selected</#if>>REWRITE</option>
							<option value="REDIRECT" <#if (rule.rewriteTypeId)?exists && rule.rewriteTypeId == "REDIRECT">selected</#if>>REDIRECT</option>
						</select>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label">WebSite ID</label>
						<select name="webSiteId" class="form-control" data-allow-clear="false" data-placeholder="Website ID">
							<option value="envelopes" <#if (rule.webSiteId)?exists && rule.webSiteId == "ae">selected</#if>>Envelopes.com</option>
							<option value="ae" <#if (rule.webSiteId)?exists && rule.webSiteId == "ae">selected</#if>>ActionEnvelope.com</option>
                            <option value="folders" <#if (rule.webSiteId)?exists && rule.webSiteId == "folders">selected</#if>>Folders.com</option>
                            <option value="bags" <#if (rule.webSiteId)?exists && rule.webSiteId == "bags">selected</#if>>Bags.com</option>
						</select>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label">Response Code</label>
						<select name="responseCode" class="form-control" data-allow-clear="true" data-placeholder="Response Code">
							<option value=""></option>
							<option value="301" <#if (rule.responseCode)?exists && rule.responseCode == "301">selected</#if>>Permanent</option>
							<!--<option value="302" <#if (rule.responseCode)?exists && rule.responseCode == "302">selected</#if>>Temporary</option>-->
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
						<th>Rewrite Type ID</th>
						<th>From URL</th>
						<th>To URL</th>
						<th>Product ID</th>
						<th>Response Code</th>
						<th></th>
					</tr>
					</thead>

					<tbody>
						<#list rules as rule>
							<tr>
								<td><a href="<@ofbizUrl>/rewriteRules?ruleId=${rule.ruleId?if_exists}&webSiteId=${rule.webSiteId?if_exists}</@ofbizUrl>">${rule.ruleId?if_exists}</a></td>
								<td>${rule.webSiteId?if_exists}</td>
								<td>${rule.rewriteTypeId?if_exists}</td>
								<td>${rule.fromUrl?if_exists}</td>
								<td><div style="max-width: 500px; text-overflow: ellipsis; overflow: hidden;">${rule.toUrl?if_exists}</div></td>
								<td>${rule.productId?if_exists}</td>
								<td>${rule.responseCode?if_exists}</td>
								<td><a data-rule-id="${rule.ruleId?if_exists}" data-website-id="${rule.webSiteId?if_exists}" href="<@ofbizUrl>/removeRewriteRule</@ofbizUrl>?ruleId=${rule.ruleId?if_exists}&webSiteId=${rule.webSiteId?if_exists}" class="removeRewriteRule  btn btn-danger btn-xs">
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

<script src="<@ofbizContentUrl>/html/js/admin/rewriterules.js</@ofbizContentUrl>"></script>