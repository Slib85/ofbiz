var texelProject = [];

$(function() {
	$('.jqs-designnow').on('click', function() {
		var index = $(this).closest('.jqs-proofParent').index();

		if (typeof texelProject[index] == 'undefined') {
			texelProject[index] = new TexelProject(index);
		}

		texelProject[index].loadTexel();
	});

	$('.jqs-variabledatagrid').on('click', function() {
		var index = $(this).closest('.jqs-proofParent').index();

		if (typeof texelProject[index] == 'undefined') {
			texelProject[index] = new TexelProject(index);
		}

		texelProject[index].loadGrid();
	});

	$('.jqs-submit').on('click', function() {
		var element = $(this);
		var index = $(element).closest('.jqs-proofParent').index();

		if (element.attr('data-value') == "true") {
			if ($(element).closest('.jqs-proofParent').find('.jqs-upload-new-artwork-content').length > 0) {
				$(element).closest('.jqs-proofParent').find('.jqs-upload-new-artwork-content').find('textarea').val()
			}
		}
		else {
			if (typeof texelProject[index] != 'undefined' && (element.closest('.jqs-proofParent').find('[name="change-address"]').is(':checked') || element.closest('.jqs-proofParent').find('[name="change-design"]').is(':checked'))) {
				texelProject[index].saveProject(element);
			}
		}

		var validation_info = validSubmission(element);
		if(!validation_info.err) {
			$.ajax({
				type: 'POST',
				url: '/' + websiteId + '/control/approveOrRejectProof',
				async: false,
				data: {
					'orderId': element.attr('data-order-id'),
					'orderItemSeqId': element.attr('data-order-item-seq-id'),
					'approved': element.attr('data-value'),
					'message': element.closest('.approve-proof').find('textarea[name="message' + (element.data('action') == 'reject' ? 'Reject' : 'Approve') + '"]').val(),
					'orderItemArtworkId': element.attr('data-order-item-artwork-id'),
					'addressing': element.closest('.jqs-proofParent').find('[name="change-address"]').is(':checked') ? 'true' : 'false',
					'scene7': element.closest('.jqs-proofParent').find('[name="change-address"]').is(':checked') || element.closest('.jqs-proofParent').find('[name="change-design"]').is(':checked') ? 'true' : 'false',
					'upload': element.closest('.jqs-proofParent').find('[name="upload-new-artwork"]').is(':checked') ? 'true' : 'false'
				},
				cache: false,
				context: element,
				dataType: 'json'
			}).done(function(data) {
				if ($(element).attr('data-action') == 'approve') {
					alert('Your proof has been approved.');
				}
				else {
					alert('Your proof has been rejected.');
				}

				element.closest('.proofParent').remove();
				if($('.proofParent').length == 0) {
					$('.approve-proof-message').removeClass('hidden');
				}
			});
		} else {
			alert(validation_info.err_msg);
		}
	});

	$('.jqs-calltexel').on('click', function() {
		$(this).closest('.jqs-proofParent').find('.jqs-saveProject').parent().removeClass('hidden');
	});

	$('.jqs-cancel').on('click', function() {
		var parent = $(this).closest('.proofParent');
        parent.find('.jqs-approve-content').removeClass('hidden');
        parent.find('.jqs-reject-content').addClass('hidden');
        parent.find('.jqs-proofAction').html('Approve Your Proof');
	});
	$('.jqs-reject').on('click', function() {
		var parent = $(this).closest('.proofParent');
        parent.find('.jqs-approve-content').addClass('hidden');
        parent.find('.jqs-reject-content').removeClass('hidden');
        parent.find('.jqs-proofAction').html('Reject Your Proof');
	});

	$('.jqs-modify-address').on('click', function() {
		var parent = $(this).closest('.proofParent');
		if(parent.find('.jqs-modify-address')[0].checked == true) {
			parent.find('.jqs-modify-address-content').removeClass('hidden');
		} else {
			parent.find('.jqs-modify-address-content').addClass('hidden');
		}
	});

	$('.jqs-modify-design').on('click', function() {
		var parent = $(this).closest('.proofParent');
		if(parent.find('.jqs-modify-design')[0].checked == true) {
			parent.find('.jqs-modify-design-content').removeClass('hidden');
		} else {
			parent.find('.jqs-modify-design-content').addClass('hidden');
		}
	});

	$('.jqs-upload-new-artwork').on('click', function() {
		var parent = $(this).closest('.proofParent');
		if(parent.find('.jqs-upload-new-artwork')[0].checked == true) {
			parent.find('.jqs-upload-new-artwork-content').removeClass('hidden');
		} else {
			parent.find('.jqs-upload-new-artwork-content').addClass('hidden');
		}
	});

	$('.jqs-other').on('click', function() {
		var parent = $(this).closest('.proofParent');
		if(parent.find('.jqs-other')[0].checked == true) {
			parent.find('.jqs-other-content').removeClass('hidden');
		} else {
			parent.find('.jqs-other-content').addClass('hidden');
		}
	});
});

$('.jqs-approveOrRejectButton').on('click', function() {
	if ($(this).attr('data-do') == 'approve') {
		$('.jqs-approveProof').removeClass('hidden');
	}
	else {
		$('.jqs-rejectProof').removeClass('hidden');
	}

	$(this).parent().addClass('hidden');
});

$('.jqs-goBack').on('click', function() {
	$('.jqs-approveOrRejectButton').parent().removeClass('hidden');
	$('.jqs-approveProof').removeClass('hidden').addClass('hidden');
	$('.jqs-rejectProof').removeClass('hidden').addClass('hidden');
});

function TexelProject(index) {
	var projectId = null;
	var projectIndex = null;
	var productList = null;
	var projectObj = null;
	var texelDiv = null;

	function initProject(index) {
		projectIndex = index;
		projectId = $('.jqs-proofParent:eq(' + projectIndex + ')').attr('data-designParentId');
		loadProject(projectId, true);
		texelDiv = $('#texel_' + projectIndex);

		if (texelDiv.length == 0 && projectObj.settings.product[0].s7Data.scene7Data.designs[0].templateId != null) {
			texelDiv = $('#texel_' + projectIndex);
			if(typeof texelDiv.data('envTexel') === 'undefined') {
				texelDiv = startTexel();
			}
			GoogleAnalytics.trackEvent('Proof Approval', 'Designer', 'Launched');
		}
		else {
			this.loadTexel();
		}
	}

	function startTexel() {
		if(texelDiv.length == 0) {
			texelDiv = $('<div/>').attr('id', 'texel_' + projectIndex).addClass('texel');
			$('body').append(texelDiv);
		}

		var texelSettings = {
			'designs': {
				'0': {
					'templateId': projectObj.settings.product[0].s7Data.scene7Data.designs[0].templateId,
					'templateType': projectObj.settings.product[0].s7Data.scene7Data.designs[0].templateType,
					'templateSide': 'FRONT'
				},
				'1': {
					'templateId': projectObj.settings.product[0].s7Data.scene7Data.designs[1].templateId,
					'templateType': projectObj.settings.product[0].s7Data.scene7Data.designs[1].templateType,
					'templateSide': 'BACK'
				}
			},
			'activeDesign': (projectObj.settings.product[0].s7Data.scene7Data.designs[1].templateType == 'FLAP') ? '1' : '0',
			'backgroundColor': '#' + projectObj.settings.product[0].hex
		};

		if(typeof projectObj.settings.product[0].s7Data !== 'undefined') {
			texelSettings = projectObj.settings.product[0].s7Data.scene7Data.settings;
			$.extend(true, texelSettings.designs, projectObj.settings.product[0].s7Data.scene7Data.designs);
		}

		texelDiv.texel(texelSettings);
		texelDiv.texel('setProduct', projectObj.settings.product[0]);

		return texelDiv;
	}

	function loadProject(id, isProject) {
		$.ajax({
			type: 'POST',
			url: '/' + websiteId + '/control/loadProject',
			data: { 'id' : id },
			async: false,
			dataType: 'json',
			success: function(data) {
				if(isProject && data.success && typeof data.settings !== 'undefined') {
					projectObj = data;
					projectObj.settings.projectId = id;
					loadProject(projectObj.settings.product[0].scene7DesignId, false);
				} else if(!isProject && data.success && typeof data.settings != 'undefined') {
					projectObj.settings.product[0].s7Data = { 'scene7Data' : data };
				}
			}
		});
	}

	this.loadGrid = function() {
		if (typeof texelDiv != 'undefined' && $(texelDiv).hasClass('hidden')) {
			texelDiv.texel('onLoad');
		}

        texelDiv.texel('showGrid', 'manual');
    }

	this.loadTexel = function() {
		if (typeof texelDiv != 'undefined' && $(texelDiv).hasClass('hidden')) {
			texelDiv.texel('onLoad');
		}
	}

	this.saveProject = function(element) {
		$('.jqs-proofParent:eq(' + projectIndex + ')').find('.jqs-saveProject').parent().removeClass('hidden').addClass('hidden');
		
		$.ajax({
			type: 'POST',
			timeout: 5000,
			url: '/' + websiteId + '/control/saveProject',
			data: {
				'id': (projectId != null ? projectId : ''),
				'productData': JSON.stringify(projectObj),
				'orderId': $('.jqs-proofParent:eq(' + projectIndex + ')').attr('data-orderId'),
				'orderItemSeqId': $('.jqs-proofParent:eq(' + projectIndex + ')').attr('data-orderItemSeqId'),
				'designId': $('.jqs-proofParent:eq(' + projectIndex + ')').attr('data-designId'),
				'numberOfAddresses': (texelDiv.texel('getGlobalVars').designs[0].variableData.length != null ? texelDiv.texel('getGlobalVars').designs[0].variableData.length : '')
			},
			async: false,
			dataType: 'json',
			cache: false
		}).done(function(data) {
			if(!data.success) {
				alert('An Error has occured while trying to save design.  Please contact customer service');
			}
		});
	}

	initProject(index);
}

function validSubmission(element) {
	var parentContainer = $(element).closest('.jqs-proofParent');
	var validation_info = {
		err: true,
		err_msg: ''
	}

	if (element.data('action') == 'approve') {
		if($(element).attr('data-value') == "false") {
			validation_info.err = false;
		} else if(parentContainer.find('input[name=ink-color]').prop('checked')
		) {
			if (parentContainer.find('input[name=comments]').length == 1) {
				if (parentContainer.find('input[name=comments]').prop('checked')) {
					validation_info.err = false;
				}
			}
			else {
				validation_info.err = false;
			}
		}

		validation_info.err_msg = "Please check all required fields.";
	}
	else if (element.data('action') == 'reject') {
		if (!parentContainer.find('[name="upload-new-artwork"]').length == 0) {
			if (!parentContainer.find('[name="upload-new-artwork"]').is(':checked') || parentContainer.find('textarea[name="messageReject"]').val().replace(/\s+/, '')) {
				validation_info.err = false;
			}

			validation_info.err_msg = 'Please add a rejection comment.';
		}
		else {
			validation_info.err = false;
		}
	}
	else {
		validation_info.err_msg = 'An error has occured.  Please contact Customer Service.';
	}

	return validation_info;
}