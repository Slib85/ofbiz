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
		<form id="createRule" method="POST" action="#" class="form-wizard" enctype="application/x-www-form-urlencoded">
            <#if rule?has_content && rule.ruleId?has_content>
			    <input type="hidden" name="ruleId" value="${rule.ruleId}" />
            </#if>
			<input type="hidden" name="manual" value="Y" />
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label">WebSite ID</label>
						<select name="webSiteId" class="form-control" data-allow-clear="false" data-placeholder="Website ID">
							<option value="envelopes" <#if (rule.webSiteId)?exists && (rule.webSiteId == "ae" || rule.webSiteId == "envelopes")>selected</#if>>Envelopes.com</option>
                            <option value="folders" <#if (rule.webSiteId)?exists && rule.webSiteId == "folders">selected</#if>>Folders.com</option>
                            <option value="bags" <#if (rule.webSiteId)?exists && rule.webSiteId == "bags">selected</#if>>Bags.com</option>
						</select>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label">Unfriendly Url</label>
						<input class="form-control" name="url" placeholder="Url" value="${(rule.url)?if_exists}" />
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label">Canonical Url (Friendly Url)</label>
						<input class="form-control" name="canonicalUrl" placeholder="Canonical Url" value="${(rule.canonicalUrl)?if_exists}" />
					</div>
				</div>
			</div>
			<div class="row text-right margin-right-15">
				<button type="submit" class="btn btn-success">
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
                <table id="ruleTable" class="table responsive table-striped">
					<thead>
					<tr>
						<th>Rule ID</th>
                        <th>Website Id</th>
						<th>Url</th>
						<th>Canonical URL</th>
                        <th></th>
					</tr>
					</thead>

					<tbody>
						<#list rules as rule>
							<tr>
								<td><a href="<@ofbizUrl>/canonicalRules?ruleId=${rule.ruleId?if_exists}</@ofbizUrl>">${rule.ruleId?if_exists}</a></td>
                                <td>${rule.webSiteId?if_exists}</td>
								<td>${rule.url?if_exists}</td>
								<td>${rule.canonicalUrl?if_exists}</td>
								<td><div data-rule-id="${rule.ruleId?if_exists}" data-website-id="${rule.webSiteId?if_exists}" href="#" class="removeRule btn btn-danger btn-xs">
                                    <i class="icon wb-link" aria-hidden="true"></i>Delete
								</div></td>
							</tr>
						</#list>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</#if>

<script src="<@ofbizContentUrl>/html/js/admin/canonicalRules.js</@ofbizContentUrl>"></script>