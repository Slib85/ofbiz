<div class="row row-spacer">
	<div class="col-md-4"></div>
	<div class="col-md-4">
		<form class="" name="productEditor" action="<@ofbizUrl>/templateEditor</@ofbizUrl>" method="GET">
			<div class="input-group">
				<input type="text" class="form-control" name="id" placeholder="Search for a Template" value="${requestParameters.id?if_exists}">
				<span class="input-group-btn">
					<button class="btn btn-success" type="submit">GO</button>
				</span>
			</div>
		</form>
	</div>
	<div class="col-md-4"></div>
</div>

<div class="row">
	<table class="table table-bordered responsive table-striped datatable" id="productTable">
		<thead>
			<tr>
				<th>&nbsp;</th>
				<th>Template ID</th>
				<th>Name</th>
				<th>Front/Back</th>
				<th>Product Type</th>
				<th>Template Type</th>
				<th>Update</th>
			</tr>
		</thead>

		<tbody>
			<#if templates?has_content>
				<#list templates as template>
					<tr>
						<td><img src="//texel.envelopes.com/getBasicImage?id=${template.scene7TemplateId}&fmt=png&hei=40" /></td>
						<td>${template.scene7TemplateId?if_exists}</td>
						<td>${template.templateDescription?if_exists}</td>
						<td><#if template.templateAssocTypeId?has_content && template.templateAssocTypeId == "TEMPLATE_BACK">BACK<#else>FRONT</#if></td>
						<td>${template.productTypeId?if_exists}</td>
						<td>${template.productDesc?if_exists}</td>
						<td>
							<button type="button" class="btn btn-default btn-icon">EDIT<i class="entypo-check"></i></button>
						</td>
					</tr>
				</#list>
			</#if>
		</tbody>
	</table>
</div>

<script src="<@ofbizContentUrl>/html/js/admin/productEditor.js</@ofbizContentUrl>"></script>