<div id="productFileDropZone" class="row">
    <div class="form-group">
        <form id="uploadPricingDataFile" method="POST" action="<@ofbizUrl>/uploadPricingDataFile</@ofbizUrl>" enctype="multipart/form-data">
            <div class="col-sm-6">
                <input type="file" name="file" class="form-control" id="field-file" placeholder="Upload File">
                <div class="input-group">
                    <span class="input-group-addon">
                      <span class="checkbox-custom checkbox-default">
                        <input type="checkbox" checked="checked" id="priceOnly" name="priceOnly">
                        <label for="inputCheckbox">Cancel import on error</label>
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
    $('#uploadPricingDataFile').on('submit', function() {
        if($('#priceOnly').is(':checked')) {
            $(this).attr('action', '<@ofbizUrl>/uploadProductPriceFile</@ofbizUrl>');
        }
    });
</script>