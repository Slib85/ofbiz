$(function() {
	$('[data-action]').on('click', function(e) {
		var self = this;
		if(validateUpload(self)) {
            e.preventDefault();
            var data = new FormData($(self).closest('form')[0]);
            $(this).prop('disabled', true);

            $.ajax({
                type: 'POST',
                enctype: 'multipart/form-data',
                url: $(self).closest('form').attr('action'),
                data: data,
                processData: false,
                dataType: 'json',
                contentType: false,
                cache: false
            }).done(function(data) {
                if (data.success) {
                	window.location = '/' + websiteId + '/control/order-detail?orderId=' + $(self).attr('data-order-id');
                } else {
                	alert('There was an error uploading the file. Please try again.');
                }
            });
		} else {
			alert('Please select at least 1 file to upload.');
		}
	});
});

function validateUpload(element) {
	var parentContainer = $(element).closest('.file-transfer');
	if(parentContainer.find('input[name=file1]').val() != '' || parentContainer.find('input[name=file2]').val() != '' || parentContainer.find('input[name=file3]').val() != '') {
		return true;
	} else {
		return false;
	}
}