<#if error?has_content>
	<div class="row">
		<div class="col-md-12">
			<div class="alert alert-danger"><strong>Oh snap! </strong>${error?replace("&lt;br&gt;","<br>")}</div>
		</div>
	</div>
<#elseif success?has_content && success?c == "true">
	<div class="row">
		<div class="col-md-12">
			<div class="alert alert-success"><strong>Great news! </strong>Data imported successfully</div>
		</div>
	</div>
</#if>

<div id="productFileDropZone" class="row">
    <div class="form-group">
		<form id="uploadProductFile" method="POST" action="<@ofbizUrl>/uploadProductFile</@ofbizUrl>" enctype="multipart/form-data">
			<div class="col-sm-6">
				<input type="file" name="file" class="form-control" id="field-file" placeholder="Upload File">
                <div class="input-group">
                    <span class="input-group-addon">
                      <span class="checkbox-custom checkbox-default">
                        <input type="checkbox" id="priceOnly" name="priceOnly">
                        <label for="inputCheckbox">Update Price Only</label>
                      </span>
                    </span>
                </div>
			</div>
			<div class="col-sm-6">
				<button type="submit" class="btn btn-success">Process</button>
			</div>
		</form>
	</div>
</div>

<script>
	$('#uploadProductFile').on('submit', function() {
		if($('#priceOnly').is(':checked')) {
		    $(this).attr('action', '<@ofbizUrl>/uploadProductPriceFile</@ofbizUrl>');
		}
	});
</script>