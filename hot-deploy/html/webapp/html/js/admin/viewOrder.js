var texelProject = [];
var texelLoading = false;

function designNowEvent(lineItemElement, showTexel, loadAddressing) {
	var loadTexel = window.setInterval(function() {
		if (!texelLoading) {
			try {
				var index = lineItemElement.find('.jqs-proofParent').index('.jqs-proofParent');
				
				let projectData = {}
				
				projectData.lineItemElement = lineItemElement;
				projectData.designId = lineItemElement.find('.jqs-proofParent').attr('data-designparentid');

				if (typeof texelProject[index] == 'undefined') {
					texelLoading = true;
					texelProject[index] = new TexelProject(projectData, showTexel);
				} else if (typeof showTexel == 'undefined' || showTexel) {
					texelProject[index].loadTexel();
					lineItemElement.find('.jqs-saveDesignChanges').removeClass('hidden');
				}

				if (typeof loadAddressing != 'undefined' && loadAddressing) {
					texelProject[index].showGrid();
				}
				
				// Begin Temp Code
				// Temporary code until we fix pdf generation files.
				lineItemElement.closest('.jqs-orderItem').find('[bns-temporary_downloads]').empty();
				
				for (let location in texelProject[index].getActiveDesign().getAttribute('jsonData')) {
					lineItemElement.closest('.jqs-orderItem').find('[bns-temporary_downloads]').css('display', 'block').append(
						$('<div />').append(
							$('<i />').addClass('fa fa-file pdf').html('Download ' + location + ' Print File w/o Keyline').on('click', function(){
								let orderId = $('.jqs-orderId').html();
								let orderItemSeqId = $(texelProject[index].lineItemElement).data('seqid');
								texelProject[index].getActiveDesign().generateArtworkPDF({
									orderId: orderId, 
									orderItemSeqId: orderItemSeqId,
									quantity: lineItemElement.find('[bns-item_quantity]').attr('bns-item_quantity')
								}, {
									downloadFlag: true, 
									opt: (location == 'back' ? 2 : 1), 
									showKeyline: false
								});
							})
						)
					);
				}
				// End Temp Code
			} catch(e) {
				texelLoading = false;
			}
			
			clearInterval(loadTexel);
		}
	}, 100);
}

function reloadPage() {
    var reloadOrderPage = $('<form/>').attr('id', 'reloadOrderPage').attr('action', window.location.href).attr('method', 'POST').append('<input type="hidden" name="orderIds" value="' + orderIds + '" />');
    $('body').append(reloadOrderPage);
    $('#reloadOrderPage').submit();
}

$(function() {
	$('.jqs-designnow').on('click', function() {
		designNowEvent($(this).closest('.jqs-orderItem'));
	});

	$('.jqs-variabledatagrid').on('click', function() {
		designNowEvent($(this).closest('.jqs-orderItem'), false, true);
	});

	$('.jqs-saveDesignChanges').on('click', function() {
		var element = $(this);
		var index = $(element).closest('.jqs-proofParent').index('.jqs-proofParent');

		texelProject[index].saveProject();
		element.addClass('hidden');
	});
});

class TexelProject {
	constructor(projectData, showTexel) {
		let texelProject = this;

		texelProject.lineItemElement = projectData.lineItemElement;
		texelProject.designs = [];
		texelProject.productData = undefined;
		texelProject.addressing = {
			addressBookId: -1,
			data: []
		};

		if (typeof projectData.designId != 'undefined') {
			texelProject.loadProject(projectData.designId, showTexel, true);
		}

		texelProject.createPDFLinks();
	}

	createPDFLinks() {
		let texelProject = this;
		let orderId = $('.jqs-orderId').html();
		let orderItemSeqId = $(texelProject.lineItemElement).data('seqid');
		let orderItemHex = $(texelProject.lineItemElement).data('product-hex');
		texelProject.lineItemElement.find('[bns-texel_pdf]').css('display', 'block').append(
			$('<div />').append(
				$('<i />').addClass('fa fa-file pdf').html(orderId + '_' + orderItemSeqId + '_PROOF.pdf').on('click', function() {
					$('[bns-scene7_proofs]').spinner(true, false, 150, null, null, '', null, 3600000);
					console.log(orderId + ', ' + orderItemSeqId + ', ' + orderItemHex + ', ' + 'OIACPRP_PDF');
					texelProject.getOrderItemContent(orderId, orderItemSeqId, orderItemHex, 'OIACPRP_PDF', 0);
				})
			)
		);
		for (let location in texelProject.getActiveDesign().getAttribute('jsonData')) {
			texelProject.lineItemElement.find('[bns-texel_pdf]').css('display', 'block').append(
				$('<div />').append(
					$('<i />').addClass('fa fa-file pdf').html('Download ' + location + ' Print File').on('click', function(){
						$('[bns-scene7_proofs]').spinner(true, false, 150, null, null, '', null, 3600000);
						console.log(orderId + ', ' + orderItemSeqId + ', ' + orderItemHex + ', ' + (location == 'front' ? 'OIACPRP_SC7_FRNT_PDF' : 'OIACPRP_SC7_BACK_PDF'));
						texelProject.getOrderItemContent(orderId, orderItemSeqId, orderItemHex, location == 'front' ? 'OIACPRP_SC7_FRNT_PDF' : 'OIACPRP_SC7_BACK_PDF', location == 'front' ? 1 : 2);
					})
				)
			)	
		}
	}

	getOrderItemContent(orderId, orderItemSeqId, orderItemHex, contentPurpose, opt) {
		let texelProject = this;
		$.ajax({
			type: "GET",
			url: '/admin/control/getOrderItemContent',
			data: {
				orderId: orderId,
				orderItemSeqId: orderItemSeqId,
				contentPurpose: contentPurpose
			},
			dataType: 'json',
			cache: false
		}).done(function(data) {
			if (data.success) {
				if(data.content.length > 0) {
					window.location.href = '/admin/control/serveFileForStream?filePath=' + data.content[1] + '&fileName=' + data.content[0] + '&downLoad=Y';
				} else {
					texelProject.getActiveDesign().generateArtworkPDF({
						orderId : orderId, 
						orderItemSeqId : orderItemSeqId, 
						backgroundColor: orderItemHex,
						quantity: texelProject.lineItemElement.find('[bns-item_quantity]').attr('bns-item_quantity')
					}, {
						opt: opt, 
						downloadFlag: false
					}, function(res){
						if(res.success) {
							window.location.href = '/admin/control/serveFileForStream?filePath=' + res.path + '&fileName=' + res.name + '&downLoad=Y';
						} else {
							alert('An error occurred while generating the artwork file');
						}
					});
				}
					
				$('[bns-scene7_proofs]').spinner(false);
			} else {
				alert('An error occurred while getting the artwork file');
			}
		});
	}

	regenerateArtworkFiles(orderId, orderItemSeqId, orderItemHex, callback) {
		var texelProject = this;
		texelProject.getActiveDesign().regenerateArtworkFiles({
			orderId: orderId, 
			orderItemSeqId: orderItemSeqId, 
			regenerateImage: true, 
			quantity: texelProject.lineItemElement.find('[bns-item_quantity]').attr('bns-item_quantity')
		}, function(data) {
			if(callback) {
				callback(data);
			}
		});

	}

	loadProject(designId, showTexel, isProject) {
		let texelProject = this;

		$.ajax({
			type: 'POST',
			url: '/' + websiteId + '/control/loadProject',
			data: { 'id' : designId },
			async: false,
			dataType: 'json',
			success: function(response) {
				if(isProject && response.success && typeof response.settings !== 'undefined') {
					texelProject.productData = response;
					texelProject.productData.settings.projectId = designId;
					texelProject.loadProject(texelProject.productData.settings.product[0].scene7DesignId, showTexel, false);
				} else if(!isProject && response.success) {
					texelProject.productData.settings.product[0].s7Data = { 'scene7Data' : response };
					texelProject.loadNewTexel(Object.assign(response.designData, { 
						designId: designId 
					}), false, showTexel);
					texelProject.addressing = texelProject.productData.settings.product[0].addressingData;
				}
			}
		});
	}

	saveProject() {
		let texelProject = this;

		let projectId = texelProject.lineItemElement.find('.jqs-proofParent').attr('data-designparentid');
		let projectObj = texelProject.productData;
		let orderId = $('.jqs-orderId').html();
		let orderItemSeqId = texelProject.lineItemElement.attr('data-seqid');
		let orderItemHex = texelProject.lineItemElement.attr('data-product-hex');
		let designId = texelProject.lineItemElement.find('.jqs-proofParent').attr('data-designId');
		let numberOfAddresses = (typeof texelProject.addressing != 'undefined' ? texelProject.addressing.data.length : 0);

		$.ajax({
			type: 'POST',
			timeout: 5000,
			url: '/' + websiteId + '/control/saveProject',
			data: {
				'id': (projectId != null ? projectId : ''),
				'productData': JSON.stringify(projectObj),
				'orderId': orderId,
				'orderItemSeqId': orderItemSeqId,
				'designId': designId,
				'numberOfAddresses': numberOfAddresses
			},
			async: false,
			dataType: 'json',
			cache: false
		}).done(function(data) {
			if(!data.success) {
				alert('An Error has occured while trying to save design.  Please contact Magik Mike.');
			}
			else {
				texelProject.regenerateArtworkFiles(orderId, orderItemSeqId, orderItemHex, function(data) {
					console.log(data);
					alert('Design Saved!');
					window.location.reload(true);
				});

			}
		});
	}

	getActiveDesign() {
		let texelProject = this;

		for (let i = 0; i < texelProject.designs.length; i++) {
			if (texelProject.designs[i].active) {
				return texelProject.designs[i].productDesign;
			}
		}
	}

	loadTexel() {
		let texelProject = this;

		if (typeof texelProject.designs == 'object' && texelProject.designs.length > 0) {
			$(window).trigger('resize.texel');

            if (typeof show == 'undefined' || show) {
                $('#designerContainer_' + texelProject.lineItemElement.attr('data-seqid')).removeClass('designerHidden');
			}
		}
	}

	loadNewTexel(data, addressingOnly, show) {
		let texelProject = this;

        if (typeof data == 'object') {
			texelProject.designs.push({
                'active': true,
                'productDesign': new TexelCanvas('designerContainer_' + texelProject.lineItemElement.attr('data-seqid'), Object.assign(data, {
					location: 'front',
					backgroundColor: (typeof texelProject.lineItemElement.attr('data-product-hex') != 'undefined' && texelProject.lineItemElement.attr('data-product-hex') != '' ? texelProject.lineItemElement.attr('data-product-hex') : undefined)
				}))
            });

            if (typeof show == 'undefined' || show) {
                $('#designerContainer_' + texelProject.lineItemElement.attr('data-seqid')).removeClass('designerHidden');
            }

            texelProject.designs[texelProject.designs.length - 1].productDesign.addUpdateEvent('productColors', function() {
                var productDesign = texelProject.getActiveDesign();
                var jsonData = productDesign.getAttribute('designerState')[productDesign.getAttribute('designerStateCurrentIndex')].jsonData;
                var sides = [
                    {
                        'productKey': 'colorsFront',
                        'productWhiteInkKey': 'whiteInkFront',
                        'designerKey': 'front'
                    }, { 
                        'productKey': 'colorsBack',
                        'productWhiteInkKey': 'whiteInkBack',
                        'designerKey': 'back'
                    }
                ];

                for (var i = 0; i < sides.length; i++) {
                    var colorArray = [];
                    var hasImage = false;

                    for (var j = 0; j < jsonData[sides[i].designerKey].length; j++) {
                        if ((jsonData[sides[i].designerKey][j].type == 'textBox' || jsonData[sides[i].designerKey][j].type == 'textBoxAddressing') && $.inArray(jsonData[sides[i].designerKey][j].fill, colorArray) == -1) {
                            colorArray.push(jsonData[sides[i].designerKey][j].fill);
                        } else if (jsonData[sides[i].designerKey][j].type == 'image') {
                            hasImage = true;
                        }
                    }

                    var totalColors = (colorArray.length >= 3 ? 4 : colorArray.length);

                    if (hasImage && totalColors < 2) {
                        totalColors = 2;
                    }

                    if ($.inArray('#ffffff', colorArray) >= 0) {
                        $('[bns-selection][data-key="' + sides[i].productKey + '"][data-value="' + sides[i].productWhiteInkKey + '"]').trigger('click');
                    }
				}
				
                texelProject.productData.settings.product[0].s7Data = {};

                for (let key in productDesign.getAttribute('designerState')[productDesign.getAttribute('designerStateCurrentIndex')].jsonData) {
                    texelProject.productData.settings.product[0].s7Data[key] = productDesign.getAttribute('designerState')[productDesign.getAttribute('designerStateCurrentIndex')].jsonData[key];
                }

				texelProject.productData.settings.product[0].s7Data.scene7Data = {
                    designData: {
                        userImages: productDesign.getCleanUserImages(),
                        width: productDesign.getAttribute('productWidthInches'),
                        height: productDesign.getAttribute('productHeightInches'),
                        jsonData: productDesign.getAttribute('designerState')[productDesign.getAttribute('designerStateCurrentIndex')].jsonData
                    }
				}

				/*texelProject.lineItemElement.find('[data-purpose-id="OIACPRP_FRONT"]').empty().append(
					$('<img />').attr('src', texelProject.getActiveDesign().getAttribute('designerState')[texelProject.getActiveDesign().getAttribute('designerStateCurrentIndex')].imageData.front)
				);

				texelProject.lineItemElement.find('[data-purpose-id="OIACPRP_BACK"]').empty().append(
					$('<img />').attr('src', texelProject.getActiveDesign().getAttribute('designerState')[texelProject.getActiveDesign().getAttribute('designerStateCurrentIndex')].imageData.back)
				);*/

				texelLoading = false;
            });

            if (typeof addressingOnly == 'undefined' || !addressingOnly) {
                $('[bns-load_design]').removeClass('hidden').html('Edit Your Design <i class="fa fa-caret-right');
            }
        }
	}

	createAddressingGrid(dataGroupId) {
		let texelProject = this;
        var gridData = texelProject.addressing.data;
        var method = 'manual';
		let font = JSON.parse('{"fontSize": 16, "lineHeight": 1.1875, "textAlign": "center", "fontFamily": "Myriad Pro"}');
		
        var gridOptions = $.extend({
            gridData: typeof gridData != 'undefined' ? gridData : [[]],
            partyId: '',
            dataGroupId: dataGroupId,
            onGridApply: function (event, data) {
                texelProject.loadTexel();
                texelProject.getActiveDesign().updateAddressing(data.data, font);
                texelProject.addressing = data;
				texelProject.lineItemElement.find('[name="addresses"]').val(data.data.length);
				$('.jqs-saveDesignChanges').removeClass('hidden');
            },
            onGridClose: function (event, data) {
                // Not sure if we will use this one day.
            },
            dataGroupMode: method
        }, {
            responsive: true,
            debug: false
        });

        $('body').append(
            $('<div />').addClass('gridOverlayBg grid-hide')
        ).append(
            $('<div/>').addClass('gridOverlay reset-css grid-hide').append(
                $('<div/>').attr('id', 'env-variabledata-grid')
            )
        );

        return $('#env-variabledata-grid').VariableDataGrid(gridOptions);
	}
	
    showGrid() {
		let texelProject = this;

		if ($('#env-variabledata-grid').length == 0) {
			texelProject.createAddressingGrid(texelProject.addressing.dataGroupId);
		}

		$('#env-variabledata-grid').VariableDataGrid('showGrid', 'manual');
    }
}

function getTotals() {
	//ajax call
	$.ajax({
		type: 'POST',
		url: '/admin/control/getOrderTotals',
		dataType: 'json',
		data: {
			"orderId": $('.order-id').html()
		}
	}).done(function( response ) {
		var total = 0;
		var obj = $('.sub-totals');
		if (response.success){
			obj.empty();
			for(var i = 0; i < response.data.length; i++){
				for(var key in response.data[i]) {
					if(key != 'Rate') { //Rate is only for Tax to show the rate, should not accumulate against other data
                        total += response.data[i][key];
                        obj.append(
                            $('<div/>', {"class": "col-xs-6 adj-name"}).html(key + ((key == 'Shipping') ? ' (' + genShipMethod + ')' : '')).prepend(
                                function (key) {
                                    if (key == 'Shipping') {
                                        return $('<button/>', {
                                            "class": "btn btn-default btn-xs change-shipping",
                                            "data-container": "body",
                                            "data-toggle": "popover",
                                            "data-html": "true",
                                            "data-placement": "top"
                                        }).html('CHANGE')
                                    }
                                }(key)
                            )
                        ).append(
                            $('<div/>', {"class": "col-xs-6 adj-total"}).html(formatCurrency(response.data[i][key]))
                        );
                        if (key == 'Subtotal') {
                            obj.append($('<div/>', {"class": "col-xs-6 adj-name"}).append('<span>Adjustment</span><span><input type="text" class="adjustmentDescription" style="line-height:5px;font-weight:normal;margin-left: 10px;" name="adjustmentDescription" value=""></span>'))
                                .append($('<div/>', {"class": "col-xs-6 adj-total"}).html('<span><input class="adjustment" type="text" style="line-height:5px;font-weight: normal;margin-right:10px" name="adjustment" value=""></span>')
                                    .append($('<button/>', {"class": "btn btn-default btn-xs apply-adjustment"}).html('APPLY').on('click', function () {
                                        applyAdjustment();
                                    })));
                        }
                    }
				}
			}

			obj.append(
				$('<div/>', {"class":"col-xs-6 adj-name totalBg"}).append($('<i/>', {"class":"icon wb-refresh"}).css({'font-size':'10px', 'padding-right':'10px'}).on('click', function() { updateOrderTotals(); })).append('Total')
			).append(
				$('<div/>', {"class":"col-xs-6 adj-total totalBg"}).html(formatCurrency(total))
			);

			$('.change-shipping').on('click', function(){
				if (typeof $(this).data('bs.popover') != 'undefined' && $($(this).data('bs.popover').tip()[0]).hasClass('in')){
					// popover is visible
					$(this).popover('hide');
					return;
				}
				var result = $('<ol>', {"class":"list-unstyled"});
				$.ajax({
					type: 'POST',
					url: '/admin/control/getShippingRates',
					dataType: 'json',
					data: { 'orderId' : $('.order-id').html(), 'shipping_postalCode' : $('.shipPostal>span').html(), 'destroyCart' : 'Y', 'useOrderPrice' : true, 'createAsNewOrder': true, 'showThirdParty' : 'Y' },
					async: false
				}).done(function( response ) {
					if(response.success && typeof response.estimates != 'undefined' && response.estimates != null){
						$.each(response.estimates, function(k,v){
							var item = $('<li/>', {"class":"ship-method"}).data("carrierPartyId", v.carrierPartyId).data("method", v.method).data("cost", v.cost);
							item.append($('<div/>', {"class":"desc"}).html(v.genericDesc))
								.append($('<div/>', {"class":"cost pull-right"}).html(formatCurrency(v.cost)));
							item.on('click', function(){
								$(this).addClass('selected').siblings('li').removeClass('selected').end().siblings('div').find('div').collapse('show')
							});
							result.append(item);
						});
						result.append($('<div/>', {"class":"save-container"}).append($('<div/>', {"class":"panel-collapse collapse"}).append($('<button/>', {"class":"btn btn-primary col-xs-12"}).html('Save').on('click', function(){
							var data = {};
							var obj = $(this).closest('.list-unstyled').find('li.selected');
							data['orderId'] = $('.order-id').html();
							data['amount'] = obj.data("cost");
							data['carrierPartyId'] = obj.data("carrierPartyId");
							data['shipmentMethodTypeId'] = obj.data("method");
							data['shipping_postalCode'] = $('.shipPostal').html();
							data['useOrderPrice'] = true;
                            data['createAsNewOrder'] = true;

							$.ajax({
								type: 'POST',
								url: '/admin/control/setShippingRate',
								dataType: 'json',
								data: data
							}).done(function( response ) {
								$('.change-shipping').popover('hide');
								getTotals();
							});
						}))));
					} else {
						result.append($('<li/>').html("Error calculating shipping! :("));
					}
				}).error(function( response ) {
					result.append($('<li/>').html("Error talking to server! :("));
				});

				$(this).popover('destroy').popover({
					trigger:"manual",
					content:result,
					template: '<div class="popover shipping"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>',
					title: 'Shipping Options:',
					placement:'top'
				}).popover('show');
			});
		} else {
			//todo error
		}
	}).error(function( response ) {
		//todo error
	});
}

function updateOrderTotals() {
	$.ajax({
		type: 'POST',
		url: '/admin/control/updateOrderTotals',
		dataType: 'json',
		data: { 'orderId' : $('.order-id').html(), 'useOrderPrice' : true, 'createAsNewOrder' : true },
		async: false
	}).done(function( data ) {
		if(data.success) {
			getTotals();
		} else {s
			alert("An error occurred updating order total.");
		}
	});
}

function applyAdjustment() {
    var description = $('.adjustmentDescription').val();
    var adjustment = $('.adjustment').val();
    if(adjustment == '' || isNaN(adjustment)) {
        alert('Please enter a valid adjustment amount');
        $('.adjustment').focus();
    } else if(description == '') {
        alert('Please enter a comment for the adjustment');
        $('.adjustmentDescription').focus();
    } else {
        $.ajax({
            type: 'POST',
            url: '/admin/control/applyManualAdjustments',
            dataType: 'json',
            data: { 'orderId' : $('.order-id').html(), 'description' : description, 'adjustment' : adjustment, 'useOrderPrice' : true, 'createAsNewOrder' : true },
            async: false
        }).done(function( data ) {
            if(data.success) {
                getTotals();
            } else {
                alert("An error occurred while applying a manual adjustment to the order");
            }
        });
    }
}

$(function() {
	$(document).ready(function() {
		$('.jqs-canned_response').on('click', function() {
			var id = $(this).data('response-id');
			$('#jqs-canned_response_input-' + id).val(
				($('#jqs-canned_response_input-' + id).val() ? $('#jqs-canned_response_input-' + id).val() + '\n\n' : '') + $(this).html()
			);
		});
	});
	//on exit
	$(window).bind('beforeunload', function(e){
		if ($('.j-save-cancel:not(.hidden)').length > 0){
			return "There are still unsaved changes."
		} else {
			e = null;
		}
	});
	//set barcode
	$('#barcodeTarget').barcode($('#barcodeTarget').html(), 'code39');

	//get totals
	getTotals();

	$('#exportOrder').on('click', function(e) {
		e.preventDefault();
		$('.exportGroup').spinner(true, false, 75);
		$.ajax({
			type: 'POST',
			url: '/admin/control/exportOrder',
			dataType: 'json',
			data: { 'orderId' : $('.order-id').html(), 'ignoreValidity': true },
			cache: false
		}).done(function( response ) {
			if(response.success) {
				var errorMessage = response.message.match(/\"error\".*?message.*?:.*?\"(.*?)\"/);
				alert(response.message.replace(/{(.*)}/,'') + (errorMessage != null ? '\nIssue needing resolution: ' + errorMessage[1] : ''));
			}
			$('.exportGroup').spinner(false);
		});
	});

    $('#resendConfirmation').on('click', function(e) {
        e.preventDefault();
        $.ajax({
            type: 'POST',
            url: '/admin/control/sendOrderConfirmation',
            dataType: 'json',
            data: { 'orderId' : $('.order-id').html() },
            cache: false
        }).done(function( response ) {
            if(response.success) {
                alert('Confirmation sent.');
            } else {
                alert('Error sending confirmation.');
			}
        });
    });

    $('#sendBusinessReviewEmail').on('click', function(e) {
        e.preventDefault();
        $.ajax({
            type: 'POST',
            url: '/admin/control/sendBusinessReviewEmail',
            dataType: 'json',
            data: { 'orderId' : $('.order-id').html() },
            cache: false
        }).done(function( response ) {
            if(response.success) {
                alert('Email sent.');
            } else {
                alert('Error sending email.');
            }
        });
    });

	$('.fileupload').each(function(){
		$(this).fileupload({
			dropZone: $(this).closest('.widget').find('.dropzone'),
			acceptFileTypes: /(\.|\/)(jpe?g)$/i,
        	maxFileSize: 400000000,
        	autoUpload:true,
    		formData: {
    			"contentPurposeEnumId": $(this).closest('.widget').find('.dropzone').data('purpose-id'),
				"orderId": $('.order-id').html(),
				"orderItemSeqId": $(this).closest('.order-item').data('seqid')
    		}
		}).bind('fileuploaddone', function (e, data) {
			var result = JSON.parse(data.result);

			if (result.success) {
				for (var i = 0; i < result.files.length; i++){
					var dropzone = $(this).closest('.widget').find('.dropzone');
					var placeholder = dropzone.find('.img-placeholder');
					if ( placeholder.length > 0 ) {
						dropzone.append($('<img/>'));
						placeholder.remove();
					}
					dropzone.find('img').attr('src', '/admin/control/serveFileForStream?filePath='+result.files[i].path)
				}
			} else {
				console.log("============================");
				console.log("e: ");
				console.log(e);
				console.log("============================");
				console.log("============================");
				console.log("data: ");
				console.log(data);
				console.log("============================");
				alert('There was an error with the file upload.  Please contact IT!');
			}
			//TODO CALLBACK
		});
	});

	$('.fileupload2').each(function(){
		$(this).fileupload({
			dropZone: $(this).closest('.dropzone'),
			acceptFileTypes: /(\.|\/)(pdf)$/i,
        	maxFileSize: 400000000,
        	autoUpload: true,
    		formData: {
    			"contentPurposeEnumId": $(this).closest('.dropzone').data('purpose-id'),
				"orderId": $('.order-id').html(),
				"orderItemSeqId": $(this).closest('.order-item').data('seqid'),
				"ignoreStatusChange": (typeof $(this).attr('data-ignorestatuschange') != 'undefined' ? $(this).attr('data-ignorestatuschange') : '0')
			}
		}).bind('fileuploadstart', function() {
			$(this).closest('.dropzone').spinner(true, false, 75);
		}).bind('fileuploaddone', function (e, data) {
			var result = JSON.parse(data.result);
			if (result.success) {
				$(this).closest('.dropzone').spinner(false);
				var result = JSON.parse(data.result);
				for (var i = 0; i < result.files.length; i++){
					$(this).closest('.dropzone').find('.proof-files > div[bns-uploaded_files]').empty().append(
						$('<div/>').append(
							$('<i/>', {"class":"fa fa-file pdf"})
						).append(" ../" + result.files[i].path)
					)
				}
			} else {
				console.log("============================");
				console.log("e: ");
				console.log(e);
				console.log("============================");
				console.log("============================");
				console.log("data: ");
				console.log(data);
				console.log("============================");
				alert('There was an error with the file upload.  Please contact IT!');
			}
			//TODO CALLBACK
		});
	});

	$('.fileupload3').each(function(){
		$(this).fileupload({
			dropZone: $(this).closest('.dropzone'),
			acceptFileTypes: /^.*?$/i,
			maxFileSize: 400000000,
			autoUpload: true,
			formData: {
				"contentPurposeEnumId": $(this).closest('.dropzone').data('purpose-id'),
				"orderId": $('.order-id').html(),
				"orderItemSeqId": $(this).closest('.order-item').data('seqid')
			}
		}).bind('fileuploadstart', function() {
			$(this).closest('.dropzone').spinner(true, false, 75);
		}).bind('fileuploaddone', function (e, data) {
			var result = JSON.parse(data.result);
			if (result.success) {
				$(this).closest('.dropzone').spinner(false);
				var result = JSON.parse(data.result);
				for (var i = 0; i < result.files.length; i++){
					$(this).closest('.dropzone').find('.proof-files > div').empty().append(
						$('<div/>').append(
							$('<i/>', {"class":"fa fa-file pdf"})
						).append(" ../" + result.files[i].path)
					)
				}
			} else {
				console.log("============================");
				console.log("e: ");
				console.log(e);
				console.log("============================");
				console.log("============================");
				console.log("data: ");
				console.log(data);
				console.log("============================");
				alert('There was an error with the file upload.  Please contact IT!');
			}
			//TODO CALLBACK
		});
	});

	$(document).bind('drop', function (e) {
	    e.preventDefault();
	});

	$('.dropzone').each(function(){
		var timeoutID = null;
		$(this).bind('dragover', function (e) {
			clearTimeout(timeoutID);
			e.preventDefault();
			$(this).addClass('hover');
		}).bind('dragleave', function (e) {
			e.preventDefault();
			var self = $(this);
			timeoutID = setTimeout(function(){
				self.removeClass('hover');
			}, 100);
		}).bind('drop', function (e) {
			e.preventDefault();
			var self = $(this);
			timeoutID = setTimeout(function(){
				self.removeClass('hover');
			}, 100);
		});
	})

	$('.status-change').on('click', function() {
		if(typeof $(this).data('bs.popover') != 'undefined' && $($(this).data('bs.popover').tip()[0]).hasClass('in')){
			// popover is visible
			$(this).popover('hide');
			return;
		}

		var formData = $('<form/>').attr({'method': 'POST', 'action': '/admin/control/changeOrderStatus'})
							.append($('<input/>').attr({ 'type': 'hidden', 'name': 'orderId', 'value': $('.order-id').html() }))
							.append($('<input/>').attr({ 'type': 'hidden', 'name': 'oldStatusId', 'value': $('.status-change').attr('data-currentStatus') }))
							.append($('<select/>').attr('name', 'statusId')
									.append($('<option/>').attr('value', 'ORDER_CREATED').text('Order Created').prop('selected', ($(this).attr('data-currentStatus') == 'ORDER_CREATED') ? true : false))
									.append($('<option/>').attr('value', 'ORDER_PENDING').text('Order Pending').prop('selected', ($(this).attr('data-currentStatus') == 'ORDER_PENDING') ? true : false))
									.append($('<option/>').attr('value', 'ORDER_APPROVED').text('Order Approved').prop('selected', ($(this).attr('data-currentStatus') == 'ORDER_APPROVED') ? true : false))
                                	.append($('<option/>').attr('value', 'ORDER_PARTIALLY_FULFILLED').text('Order Partially Fulfilled').prop('selected', ($(this).attr('data-currentStatus') == 'ORDER_PARTIALLY_FULFILLED') ? true : false))
									.append($('<option/>').attr('value', 'ORDER_SHIPPED').text('Order Shipped').prop('selected', ($(this).attr('data-currentStatus') == 'ORDER_SHIPPED') ? true : false))
									.append($('<option/>').attr('value', 'ORDER_CANCELLED').text('Order Cancelled').prop('selected', ($(this).attr('data-currentStatus') == 'ORDER_CANCELLED') ? true : false))
									.on('change', function() {
										$(this).siblings('div').removeClass('hidden').collapse('show')
									}))
							.append($('<div/>').addClass('save-container hidden').append($('<div/>')
																						.append($('<button/>').addClass('btn btn-primary col-xs-12').css('margin-top', '10px').html('Save')
							)));

		$(this).popover('destroy').popover({
			trigger: 'manual',
			container: 'body',
			html: true,
			content: formData,
			title: 'Change Status',
			placement: 'right'
		}).popover('show');
	});

	$('.status').tooltip();

	$('[id^=changeItemStatus_]').on('click', function(e) {
		e.preventDefault();

		$.ajax({
			type: 'POST',
			url: '/admin/control/changeOrderItemStatus',
			dataType: 'text',
			data: { 'orderId': $('.order-id').html(), 'orderItemSeqId': $(this).attr('id').replace('changeItemStatus_',''), 'fromStatusId': $(this).attr('data-current-item-status'), 'statusId': $(this).siblings('select').val() }
		}).done(function( response ) {
            reloadPage();
		});
	});

	$('[data-outsource]').on('click.outsource', function(e) {
		var self = this;
		$(self).removeAttr('data-outsource');
		$(self).removeData('outsource');

		$.ajax({
			type: 'GET',
			url: '/admin/control/outsourceList',
			dataType: 'json',
			data: { 'orderId': $('.order-id').html(), 'orderItemSeqId': $(self).attr('data-orderitemseqid') }
		}).done(function(response) {
			if(response.success){
				var htmlBox = $(self).siblings('.modal').find('.fillData');
				htmlBox.empty();
				htmlBox.append(
					'Vendor'
				).append(
					$('<input/>').attr( {'type': 'hidden', 'name': 'orderItemSeqId', 'value': $(self).attr('data-orderitemseqid') } )
				);

				var select = $('<select/>').attr( {'name': 'vendorList'}).addClass('form-control vendorList');
				select.append($('<option/>').attr( {'value': 'V_DUPLI', 'data-cost': '1'} ).text('Dupli: Not Available'));
				$.each(response.grossProfit, function(k, v) {
					select.append($('<option/>').attr( {'value': k, 'data-cost': v.cost } ).text(k + ': ' + formatCurrency(response.netItemRevenue - v.shipping - v.cost) ));
				});

				delete response.success;
				select.attr('data-alldata', JSON.stringify(response));
				htmlBox.append(select);

				$(self).off('click.outsource');
			} else {
				//
			}
		}).error(function(response) {
			//
		});
	});

	$('[bns-buttonedit]').off('click.editOutsourceComments').on('click.editOutsourceComments', function() {
		var editElement = $(this);
		editElement.addClass('hidden');

		function clearEditing(editElement) {
			$('[bns-editable="' + editElement.attr('bns-buttonedit') + '"]').removeClass('hidden');
			editElement.removeClass('hidden');
			$('[bns-edit="' + editElement.attr('bns-buttonedit') + '"]').remove();
		}

		$('[bns-editable="' + editElement.attr('bns-buttonedit') + '"]').addClass('hidden').after(
			$('<span />').attr('bns-edit', editElement.attr('bns-buttonedit')).addClass('btn btn-default btn-xs').html('Cancel').on('click.cancel', function() {
				clearEditing(editElement);
			})
		).after(
			$('<span />').attr('bns-edit', editElement.attr('bns-buttonedit')).addClass('btn btn-default btn-xs').html('Save').on('click.save', function() {
				var data = {};
				data['orderId'] = $('.order-id').html();
				data['orderItemSeqId'] = editElement.closest('div.order-item').attr('data-seqid');
				data[$('[bns-editable="' + editElement.attr('bns-buttonedit') + '"]').attr('data-fieldname')] = editElement.siblings('input[bns-edit="' + editElement.attr('bns-buttonedit') + '"]').val();

				$.ajax({
					type: 'POST',
					url: '/admin/control/' + editElement.attr('bns-buttonedit'),
					dataType: 'json',
					data: data
				}).done(function(response) {
					if(response.success) {
						$('[bns-editable="' + editElement.attr('bns-buttonedit') + '"]').html(editElement.siblings('input[bns-edit="' + editElement.attr('bns-buttonedit') + '"]').val());
					} else {
						if(typeof response.error != 'undefined') {
							alert(response.error);
						} else {
							alert('Error!');
						}
					}
					clearEditing(editElement);
				});
			})
		).after(
			$('<input />').attr('bns-edit', editElement.attr('bns-buttonedit')).attr('type', 'text').val($('[bns-editable="' + editElement.attr('bns-buttonedit') + '"]').html())
		);
	});

	$('input[name^="vendorPriceWhole"], input[name^="vendorPriceDecimal"]').off('click.selectAll').on('click.selectAll', function() {
		if ($(this).val() == '0' || $(this).val() == '00') {
			document.querySelectorAll('input[name="' + $(this).attr('name') + '"]')[0].selectionStart = 0;
			document.querySelectorAll('input[name="' + $(this).attr('name') + '"]')[0].selectionEnd = $(this).val().length;
		}
	});

	$('input[name^="vendorPriceWhole"], input[name^="vendorPriceDecimal"]').off('input.removeNonDigits').on('input.removeNonDigits', function() {
		var selectionStart = document.querySelectorAll('input[name="' + $(this).attr('name') + '"]')[0].selectionStart;
		if ($(this).val().match(/[^0-9]/) != null) {
			$(this).val($(this).val().replace(/[^0-9]/, ''));
			
			document.querySelectorAll('input[name="' + $(this).attr('name') + '"]')[0].selectionStart = selectionStart - 1;
			document.querySelectorAll('input[name="' + $(this).attr('name') + '"]')[0].selectionEnd = selectionStart - 1;
		}
	});

	$('[id*=outsource-item_]').find('.j-btn-add').on('click', function(e){
		e.preventDefault();
		var self = this;
		$(this).prop('disabled', true);
		$(this).spinner(true, false, 50, null, null, '', null, 160000);

		var cost = $(this).closest('.modal').find('[name=vendorList] option:selected').attr('data-cost');

		if (typeof $(this).closest('.modal').find('input[name^="vendorPriceWhole"]').val() != 'undefined' && typeof $(this).closest('.modal').find('input[name^="vendorPriceDecimal"]').val() != 'undefined' && parseFloat($(this).closest('.modal').find('input[name^="vendorPriceWhole"]').val().replace(/[^0-9]/, '') + '.' + $(this).closest('.modal').find('input[name^="vendorPriceDecimal"]').val().replace(/[^0-9]/, '')) > 0) {
			cost = parseFloat($(this).closest('.modal').find('input[name^="vendorPriceWhole"]').val().replace(/[^0-9]/, '') + '.' + $(this).closest('.modal').find('input[name^="vendorPriceDecimal"]').val().replace(/[^0-9]/, ''));
		}

		$.ajax({
			type: 'POST',
			url: '/admin/control/outsourceOrderRequest',
			dataType: 'json',
			data: {
				'orderId': $('.order-id').html(), 
				'orderItemSeqId': $(this).closest('.modal').attr('id').replace('outsource-item_',''), 
				'vendorPartyId': $(this).closest('.modal').find('[name=vendorList]').val(), 
				'cost': cost, 
				'comments': $(this).closest('.modal').find('[name=vendorComments]').val(), 
				'data': $(this).closest('.modal').find('[name=vendorList]').attr('data-alldata')
			},
			timeout: 180000
		}).done(function(response) {
			$(self).spinner(false);
			if(response.success) {
				alert('Order was outsourced successfully');
                reloadPage();
			} else {
				if(typeof response.error != 'undefined') {
					alert(response.error);
				} else {
					alert('Error!');
				}
			}
		}).error(function(response) {
			$(self).spinner(false);
			alert('Error!');
		});
	});

	$('.prePressCommentSave').on('click', function(e) {
		e.preventDefault();
		var thisParent = $(this).parent();
		thisParent.spinner(true, false, 75);
		var textarea = $(this).siblings('textarea');
		$.ajax({
			type: 'POST',
			url: '/admin/control/setArtworkInternalComment',
			dataType: 'json',
			data: { 'comment': textarea.val(), 'type': 'internal', 'orderItemArtworkId': $(this).closest('.order-item').data('artwork-id')}
		}).done(function( response ) {
			if (!response.success){
				alert('There was an error processing your request. Please try again');
			}
			thisParent.spinner(false);
		}).error(function( response ) {
			alert('There was an error processing your request. Please try again');
			thisParent.spinner(false);
		});
	});

	$('.pressManCommentSave').on('click', function(e) {
		e.preventDefault();
		var thisParent = $(this).parent();
		thisParent.spinner(true, false, 75);
		var textarea = $(this).siblings('textarea');
		$.ajax({
			type: 'POST',
			url: '/admin/control/setArtworkInternalComment',
			dataType: 'json',
			data: { 'comment': textarea.val(), 'type': 'pressman', 'orderItemArtworkId': $(this).closest('.order-item').data('artwork-id')}
		}).done(function( response ) {
			if (!response.success){
				alert('There was an error processing your request. Please try again');
			}
			thisParent.spinner(false);
		}).error(function( response ) {
			alert('There was an error processing your request. Please try again');
			thisParent.spinner(false);
		});
	});

	$('.customerCommentSave').on('click', function(e) {
		e.preventDefault();
		var thisParent = $(this).parent();
		thisParent.spinner(true, false, 75);
		var textarea = $('#jqs-canned_response_input-' + $(this).data('response-id'));
		$.ajax({
			type: 'POST',
			url: '/admin/control/setArtworkInternalComment',
			dataType: 'json',
			data: { 'comment': textarea.val(), 'type': 'external', 'orderItemArtworkId': $(this).closest('.order-item').data('artwork-id')}
		}).done(function( response ) {
			if (!response.success){
				alert('There was an error processing your request. Please try again');
			}
			thisParent.spinner(false);
		}).error(function( response ) {
			alert('There was an error processing your request. Please try again');
			thisParent.spinner(false);
		});
	});

	/* widget */
	$('.widget .tools .icon-remove').click(function () {
		$(this).parents(".widget").parent().remove();
	});

	$('.widget .tools .icon-chevron-down, .widget .tools .icon-chevron-up').click(function () {
		var el = $(this).parents(".widget").children(".widget-body");
		if ($(this).hasClass("icon-chevron-down")) {
		$(this).removeClass("icon-chevron-down fa-chevron-down").addClass("icon-chevron-up fa-chevron-up");
			el.slideUp(200);
		} else {
		$(this).removeClass("icon-chevron-up fa-chevron-up").addClass("icon-chevron-down fa-chevron-down");
			el.slideDown(200);
		}
	});

	$('.widget').each(function(){
		var self = $(this);

		var checkChanged = function(){
			var changes = {};
			$.each(inputs, function(id, input){
				if (input.type == "text"){
					if($(this).prop("defaultValue") != $(this).val()){
						changes[$(this).attr('name')] = $(this).val();
					}
				} else if (input.type == "checkbox"){
					if($(this).prop("defaultChecked") != this.checked){
						if($(this).attr('name') == 'savePrintSample' || $(this).attr('name') == "isRushProduction" || $(this).attr('name') == 'lockScene7Design' || $(this).attr('name') == 'inkMatched') {
							changes[$(this).attr('name')] = ((this.checked)?"Y":"N");
						} else {
							changes[$(this).attr('name')] = ((this.checked)?"true":"false");
						}
					}
				} else if (input.type == "select-one"){
					if($(this).data("defaultvalue") != $(this).val()){
						changes[$(this).attr('name')] = $(this).val();
					}
				}
			});
			if (Object.keys(changes).length > 0){
				$(self).find('.j-save-cancel').removeClass('hidden')
			} else{
				$(self).find('.j-save-cancel').addClass('hidden')
			}
			return changes;
		};

		var inputs = $(this).find('input[type=text],input[type=checkbox],select');
		if ( inputs.length > 0){
			$(this).find('.widget-title').append(
				$('<span/>', {"class":"tools hidden j-save-cancel"}).append(
					$('<a/>', {"class":"fa fa-money updatepricetoggle on"}).on('click', function(){
						if($(this).hasClass("on")) {
							$(this).removeClass("on");
							$(this).addClass("off");
						} else {
							$(this).removeClass("off");
							$(this).addClass("on");
						}
					})
				).append(
					$('<a/>', {"class":"fa fa-floppy-o"}).on('click', function(){
						var data = checkChanged();
						data['orderId'] = $('.order-id').html();
						data['orderItemSeqId'] = $(this).closest('.order-item').data('seqid');
						data['useOrderPrice'] = true;
                        data['createAsNewOrder'] = true;
						//data['updatePrice'] = (partyId == '2162652' || ($(this).closest('.order-item').data('channel') == 'folders' && ($(this).closest('.order-item').data('isprinted') == 'true' || $(this).closest('.order-item').data('isprinted')))) ? false : ($(this).siblings('.updatepricetoggle').hasClass('on')); //###HARDCODED RULE FOR CIMPRESS TO NOT UPDATE ITEM PRICING
						data['updatePrice'] = (partyId == '2162652') ? false : ($(this).siblings('.updatepricetoggle').hasClass('on')); //###HARDCODED RULE FOR CIMPRESS TO NOT UPDATE ITEM PRICING
                        data['productId'] = $(this).closest('.order-item').attr('data-productid');
						$.ajax({
							type: 'POST',
							url: '/admin/control/setOrderItem' + ($(this).closest('.jqs-savePrintSample').length > 0 ? '' : 'Attribute'),
							dataType: 'json',
							data: data
						}).done(function(response) {
							if (response.success){
								$.each(inputs, function (id, input){
									if (input.type == "text"){
										$(this).prop("defaultValue", $(this).val());
										if ($(input).attr('name') == 'addresses' && parseInt($(input).attr('value')) > 0) {
											designNowEvent($(input).closest('.jqs-orderItem'), false, true);
										}
									} else if (input.type == "checkbox"){
										$(this).prop("defaultChecked", this.checked);
									}else if (input.type == "select-one"){
										$(this).data("defaultvalue", $(this).val());
									}
								});
								$(self).find('.j-save-cancel').addClass('hidden')
							} else {
								//todo error
							}
						}).error(function( response ) {
							//todo error
						});
					})
				).append(
					$('<a/>', {"class":"fa fa-times"}).on('click', function(e){
						e.preventDefault();
						$.each(inputs, function (id, input){
							if (input.type == "text"){
								$(this).val($(this).prop("defaultValue"))
							} else if (input.type == "checkbox"){
								this.checked = $(this).prop("defaultChecked");
							}else if (input.type == "select-one"){
								$(this).val($(this).data("defaultvalue"))
							}
						});
						$(self).find('.j-save-cancel').addClass('hidden')
					})
				)
			)
		}

		$.each(inputs, function(id, input){
			if (input.type == "text"){
				$(input).on('keyup', function(e){
					checkChanged();
				})
			} else if (input.type == "checkbox" || input.type == "select-one"){
				$(input).on('change', function(e){
					checkChanged();
				})
			}
		});
	});

	$('#add-item').on('shown.bs.modal', function (e) {
		$(this).find('#productID').focus();
	})

	$('#add-item #productID').on('keyup', function(e){
		var self = this;
		waitForFinalEvent(function(){
			var modal = $(self).closest('.modal');
			var productID = modal.find('#productID').val().toUpperCase();

			//ajax call
			$.ajax({
				type: 'GET',
				url: '/admin/control/getProductName',
				dataType: 'json',
				data: {
					"id": productID
				}
			}).done(function( response ) {
				if (response.success){
					modal.find('#productID').closest('.form-group').removeClass('has-error').addClass('has-success').find('.glyphicon').removeClass('hidden');
					modal.find('.product-image').attr('src', 'https://www.envelopes.com/images/products/medium/'+productID.toLowerCase()+'.jpg');
					modal.find('.product-name').html(response.productName);
				} else {
					modal.find('#productID').closest('.form-group').addClass('has-error').find('.glyphicon').addClass('hidden');
					modal.find('.product-image').attr('src', 'https://placehold.it/200x100&text=Select%20Product%20ID');
					modal.find('.product-name').html('');
				}
			}).error(function( response ) {
				modal.find('#productID').closest('.form-group').addClass('has-error').find('.glyphicon').addClass('hidden');
				modal.find('.product-image').attr('src', 'https://placehold.it/200x100&text=Select%20Product%20ID');
				modal.find('.product-name').html('');
			});
		}, 500, "lookup-product-id");
	});

	$('#add-item .j-btn-add').on('click', function(e){
		var self = this;
		var modal = $(this).closest('.modal');
		var productID = modal.find('#productID').val();
		var productQty = modal.find('#productQty').val();
		var productImage = modal.find('.product-image').attr('src').replace('/medium/', '/small/');
		var productName = modal.find('.product-name').html();

		//ajax call
		$.ajax({
			type: 'POST',
			url: '/admin/control/addItemToOrder',
			dataType: 'json',
			data: {
				'productId': productID,
				'quantity': productQty,
				'orderId': $('.order-id').html(),
				'shipping_postalCode': $('.shipPostal').html(),
				'useOrderPrice': true,
                'createAsNewOrder': true
			}
		}).done(function( response ) {
			if (response.success){
				//hide
				var row = $('.order-item').first().clone();
				row.find('.quantity span').html(productQty);
				row.find('.product-image').attr('src', productImage);
				row.find('.product-id').html(productID);
				row.find('.product-name').html(productName);
				row.find('.item-total').html(response.unitPrice);
				row.insertAfter($('.order-item').last());
				modal.modal('hide');
				getTotals();
			} else {
				//todo error
			}
		}).error(function( response ) {
			//todo error
		});
	});

	$('[id*=update-item_]').find('.j-btn-add').on('click', function(e){
		var self = this;
		var modal = $(this).closest('.modal');
		var productId = modal.find('.productId').val();
		var productQty = modal.find('.productQty').val();
		var isPrinted = modal.find('input[name=isPrinted_' + $(this).closest('div.order-item').attr('data-seqid') + ']:checked').val();

		//ajax call
		$.ajax({
			type: 'POST',
			url: '/admin/control/updateItemInOrder',
			dataType: 'json',
			data: {
				'productId': productId,
				'quantity': productQty,
				'orderId': $('.order-id').html(),
				'orderItemSeqId': $(this).closest('div.order-item').attr('data-seqid'),
				'isPrinted': isPrinted,
				'updatePrice': (partyId == '2162652') ? false : true,
				'useOrderPrice': true,
				'createAsNewOrder': true
			}
		}).done(function( response ) {
			if(response.success) {
                reloadPage();
			} else {
				alert("Error, please try again.")
			}
		}).error(function( response ) {
			//todo error
		});
	});

	$('.reorder').on('click', function(e) {
		$.ajax({
			type: 'POST',
			url: 'https://www.envelopes.com/envelopes/control/reorderItems',
			dataType: 'json',
			data: {
				'itemUUIDs': $(this).data('reorder')
			}
		}).done(function( response ) {
			var cart = $('<form/>').attr( {'id': 'cart', 'action': 'http://www.envelopes.com/cart', 'method': 'POST', 'target': 'cartassist'} ).append('<input type="hidden" name="lastVisitedURL" value="' + document.referrer + '" />').append('<input type="hidden" name="fromAddToCart" value="true" />');
			$('body').append(cart);
			$('#cart').submit();
		}).error(function( response ) {
			//todo error
		});
	});

	$('#refundPop .j-btn-add').on('click', function(e){
		var self = this;
		var modal = $(this).closest('.modal');
		var orderPaymentPreferenceId = modal.find('#orderPaymentPreferenceId').val();
		var refundAmount = modal.find('#refundAmount').val();
		var maxAmount = modal.find('#maxAmount').val();
		var paymentMethodTypeId = modal.find('#paymentMethodTypeId').val();

		if(parseInt(refundAmount) > parseInt(maxAmount)) {
			alert("Cannot refund more then what was captured!");
			return false;
		}

		//ajax call
		$.ajax({
			type: 'POST',
			url: '/admin/control/' + (paymentMethodTypeId == 'EXT_AMAZON' ? 'refundAmazon' : 'refundPayPal'),
			dataType: 'json',
			data: {
				"orderPaymentPreferenceId": orderPaymentPreferenceId,
				"refundAmount": refundAmount
			}
		}).done(function( response ) {
			if(response.success) {
                reloadPage();
			} else {
				if(typeof response.error != 'undefined') {
					alert(response.error);
				} else {
					alert("There was an error processing the refund.");
				}
			}
		}).error(function( response ) {
			//todo error
		});
	});

    $('#capturePop .j-btn-add').on('click', function(e){
        var self = this;
        var modal = $(this).closest('.modal');
        var captureAmount = modal.find('#captureAmount').val();
        var maxAmount = modal.find('#maxAmount').val();

        if(parseInt(captureAmount) > parseInt(maxAmount)) {
            alert("Cannot capture more then what was authorized!");
            return false;
        }

        //ajax call
        $.ajax({
            type: 'POST',
            url: '/admin/control/captureAuth',
            dataType: 'json',
            data: {
                "orderId": $('.order-id').html(),
                "captureAmount": captureAmount
            }
        }).done(function( response ) {
            if(response.success) {
                reloadPage();
            } else {
                if(typeof response.error != 'undefined') {
                    alert(response.error);
                } else {
                    alert("There was an error processing the capture.");
                }
            }
        }).error(function( response ) {
            //todo error
        });
    });
});

// Payment Info.
$(document).ready(function() {
	var previously_saved_HTML;
	var element_currently_editing

	var endEditMode = function(save_data) {
		if (save_data) {
			element_currently_editing.find('.jqs-security-code').addClass('hidden');
			element_currently_editing.find('.edit-mode').hide();
			element_currently_editing.find('.non-edit-mode').show();
			element_currently_editing.parentsUntil('.jqs-editable-info').parent().find("[class*='locked-']").each(function() {
				var span = $(this);

				if (span.hasClass('locked-payment_paymentMethodTypeIdDesc')) {
					span.attr(
						'data-selected-value', span.children('select').val()
					).html(
						span.children('select').children('option[value="' + span.children('select').val() + '"]').html()
					);
				}
				else {
					span.html(span.children('input').val());
				}
			});
		} else {
			element_currently_editing.html(previously_saved_HTML);
			element_currently_editing.find('.jqs-button-edit').bind('click', editContent);
			element_currently_editing.find('.jqs-button-cancel').bind('click', cancelContent);
            if(element_currently_editing.hasClass('jqs-payment')) {
                element_currently_editing.find('.jqs-button-save').bind('click', savePayment);
            } else if(element_currently_editing.hasClass('jqs-billing-address') || element_currently_editing.hasClass('jqs-billing-phone')) {
                element_currently_editing.find('.jqs-button-save').bind('click', saveBilling);
            } else if(element_currently_editing.hasClass('jqs-shipping-address') || element_currently_editing.hasClass('jqs-shipping-phone')) {
                element_currently_editing.find('.jqs-button-save').bind('click', saveShipping);
            } else if(element_currently_editing.hasClass('jqs-email')) {
                element_currently_editing.find('.jqs-button-save').bind('click', saveEmail);
            }
		}

		previously_saved_HTML = "";
		element_currently_editing = "";
	};

	editContent = function() {
		if(element_currently_editing) {
			endEditMode(false);
		}
		element_currently_editing = $(this).parentsUntil('.jqs-editable-info').parent();
		previously_saved_HTML = element_currently_editing.html();
		element_currently_editing.find('.jqs-security-code').removeClass('hidden');
		element_currently_editing.find('.non-edit-mode').hide();
		element_currently_editing.find('.edit-mode').show();
		element_currently_editing.find('[class*="locked-"]').each(function() {
			var span = $(this);
			var class_name = $(this).attr("class");

			if (span.hasClass('locked-payment_paymentMethodTypeIdDesc')) {
				// We hard coded types for now since running a distinct on the database would give types we don't want.  In the future, if we have the types
				// that we only want in the database or maybe set them to active or inactive then we can pull the types from there and dynamicly generate this.
				span.html('').append(
					$('<select />').attr('name', 'paymentMethodType').append(
						$('<option />').attr('value', 'CERTIFIED_CHECK').html('Certified Check').prop('selected', (span.attr('data-selected-value') == 'CERTIFIED_CHECK' ? true : false))
					).append(
						$('<option />').attr('value', 'EXT_NET30').html('Net 30').prop('selected', (span.attr('data-selected-value') == 'EXT_NET30' ? true : false))
					).append(
						$('<option />').attr('value', 'CREDIT_CARD').html('Credit Card').prop('selected', (span.attr('data-selected-value') == 'CREDIT_CARD' ? true : false))
					).bind('change', function() {
						if ($(this).val() == "CERTIFIED_CHECK") {
							$(this).parent().siblings('.jqs-pay-by-card').hide();
							$(this).parent().siblings('.jqs-pay-by-net30').hide();
							$(this).parent().siblings('.jqs-pay-by-check').show();
						} else if ($(this).val() == "EXT_NET30") {
							$(this).parent().siblings('.jqs-pay-by-check').hide()
							$(this).parent().siblings('.jqs-pay-by-card').hide();
							$(this).parent().siblings('.jqs-pay-by-net30').show();
						} else if ($(this).val() == "CREDIT_CARD") {
							$(this).parent().siblings('.jqs-pay-by-check').hide();
							$(this).parent().siblings('.jqs-pay-by-net30').hide();
							$(this).parent().siblings('.jqs-pay-by-card').show();
						}
					})
				);
			} else {
				span.html('<input name="' + span.attr("class") + '" type="text" value="' + span.html() + '" />');
			}
		});
	};

	cancelContent = function() {
		endEditMode(false);
	};

    savePayment = function() {
        var data = $('.jqs-editable-info.jqs-payment').find('form').serialize().replace(/locked-payment_/g, '');
        $.ajax({
            type: "POST",
            url: "/admin/control/changeOrderPayment",
            data: data ,
            dataType:'json',
            cache: false
        }).done(function(data) {
            if (data.success) {
                endEditMode(true);
            } else {
                alert('An error occurred while updating the payment on this order');
                reloadPage();
            }
        });
    };

    saveBilling = function() {
        var data = $('.jqs-editable-info.jqs-billing-address').find('form').serialize().replace(/locked-billingAddress_/g, '');
        $.ajax({
            type: "POST",
            url: "/admin/control/changeOrderBillingAddress",
            data: data ,
            dataType:'json',
            cache: false
        }).done(function(data) {
            if (data.success) {
                $('.jqs-billing-name').html($('.jqs-editable-info.jqs-billing-address').find('input[name=locked-billingAddress_toName]').val());
                endEditMode(true);
            } else {
                alert('An error occurred while updating the billing address on this order');
                reloadPage();
            }
        });
    };

    saveShipping = function() {
        var data = $('.jqs-editable-info.jqs-shipping-address').find('form').serialize().replace(/locked-shippingAddress_/g, '');
        $.ajax({
            type: "POST",
            url: "/admin/control/changeOrderShippingAddress",
            data: data ,
            dataType:'json',
            cache: false
        }).done(function(data) {
            if (data.success) {
                endEditMode(true);
            } else {
                alert('An error occurred while updating the shipping address on this order');
                reloadPage();
            }
        });
    };

	saveBillingPhone = function() {
		var data = $('.jqs-editable-info.jqs-billing-phone').find('form').serialize().replace(/locked-billingTelecom_/g, '');
		$.ajax({
			type: "POST",
			url: "/admin/control/changeOrderBillingPhone",
			data: data ,
			dataType:'json',
			cache: false
		}).done(function(data) {
			if (data.success) {
				endEditMode(true);
			} else {
				alert('An error occurred while updating the billing phone on this order');
                reloadPage();
			}
		});
	};

	saveShippingPhone = function() {
		var data = $('.jqs-editable-info.jqs-shipping-phone').find('form').serialize().replace(/locked-shippingTelecom_/g, '');
		$.ajax({
			type: "POST",
			url: "/admin/control/changeOrderShippingPhone",
			data: data ,
			dataType:'json',
			cache: false
		}).done(function(data) {
			if (data.success) {
				endEditMode(true);
			} else {
				alert('An error occurred while updating the shipping phone on this order');
                reloadPage();
			}
		});
	};

    saveEmail = function() {
        var data = $('.jqs-editable-info.jqs-email').find('form').serialize().replace(/locked-/g, '');
        $.ajax({
            type: "POST",
            url: "/admin/control/changeOrderEmailAddress",
            data: data ,
            dataType:'json',
            cache: false
        }).done(function(data) {
            if (data.success) {
                endEditMode(true);
            } else {
                alert('An error occurred while updating the email address on this order');
                reloadPage();
            }
        });
        endEditMode(true);
    };

	/*saveContent = function() {
        console.log('hoi');

		// Do save stuff...
		*//*
		$.ajax({
			type: "POST",
			url: "<@ofbizUrl></@ofbizUrl>",
			data: ,
			dataType:'json',
			cache: false
		}).done(function(data) {
			if (data.success) {
				// yay made it
			}
			else {
				// show error
			}
		});
		*//*
		endEditMode(true);
	}*/

	$(".payment-info .non-edit-mode .jqs-button-edit").on('click', editContent);
	$(".payment-info .edit-mode .jqs-button-cancel").on('click', cancelContent);
	$(".payment-info .jqs-payment .edit-mode .jqs-button-save").on('click', savePayment);
	$(".payment-info .jqs-billing-address .edit-mode .jqs-button-save").on('click', saveBilling);
	$(".payment-info .jqs-billing-phone .edit-mode .jqs-button-save").on('click', saveBillingPhone);
	$(".payment-info .jqs-shipping-address .edit-mode .jqs-button-save").on('click', saveShipping);
	$(".payment-info .jqs-shipping-phone .edit-mode .jqs-button-save").on('click', saveShippingPhone);
	$(".payment-info .jqs-email .edit-mode .jqs-button-save").on('click', saveEmail);

	var endAddCommentMode = function() {
		$(".payment-info .comment-add").hide();
		$(".payment-info .non-add-comment").show();
		$(".payment-info .comment-list").show();
	};

	var beginAddCommentMode = function() {
		$("textarea[name='orderNote']").val("");
		$(".payment-info .comment-list").hide();
		$(".payment-info .non-add-comment").hide();
		$(".payment-info .comment-add").show();
	};
	$(".payment-info .non-add-comment input[name='button-add']").click(function() {
		beginAddCommentMode();
	});

	$(".payment-info .add-comment .jqs-button-cancel").click(function() {
		endAddCommentMode();
	});

	$(".payment-info .add-comment .jqs-button-save").click(function() {
		$.ajax({
			type: 'POST',
			url: '/admin/control/addOrderNote',
			data: { orderNote: $('textarea[name=orderNote]').val(), internalNote:'Y', orderId:$('.order-id').html() },
			dataType: 'json',
			cache: false
		}).done(function(data) {
			if(data.success && typeof data.notes != 'undefined' && data.notes.length > 0) {
				$('.comment-list').empty();
				$.each(data.notes, function(i, v) {
					$('.comment-list').append(
						'<div class="comment">' +
							'<div class="row">' +
								'<div class="col-xs-12 commenter-name">' + v.name + ':</div>' +
								'<div class="col-xs-12">' + v.comment + '</div>' +
							'</div>' +
							'<div class="row">' +
								'<div class="col-xs-12 date text-right">' + getCommentDateFormat(v.createdStamp) + '</div>' +
							'</div>' +
						'</div>'
					)
				});
			} else {
				//show error
			}
		});

		// If all good then...
		endAddCommentMode();
	});
	
/*
	//load deskcom data
	//3 loops for 3 different data sets
	var caseData = {};
	var completedCount = 0;
	var searchEndpoints = ['case_custom_order_id','subject','description'];
	
	$.each(searchEndpoints, function(i, v) {
		var busy = true;
		var dataObj = {};
		dataObj[v] = $('.order-id').html();
		
		$.ajax({
			type: 'GET',
			url: '/admin/control/getCaseData',
			dataType: 'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			data: dataObj,
			async: true
		}).done(function(response) {
			var processor = setInterval(function() {
				if (completedCount == i) {
					if(typeof response.result._embedded != 'undefined' && typeof response.result._embedded.entries != 'undefined' && response.result._embedded.entries.length > 0) {
						$.each(response.result._embedded.entries, function(i2, v2) {
							caseData[v2.id] = v2;
						});
					}
					
					completedCount++;
					clearInterval(processor);
				
					if (searchEndpoints.length == completedCount && Object.keys(caseData).length > 0) {
						if (window.console) {
							console.log('Made it in...');
						}
						$.each(caseData, function(k, v) {
							var caseDiv = $('.caseTickets');
							caseDiv.removeClass('hidden');
							caseDiv.find('.bodyData').append(
								$('<tr/>').append(
									$('<td/>').append($('<a/>').attr({target:'_blank',href:'https://envelopes.desk.com/agent/case/' + k}).html(k))
								).append(
									$('<td/>').html(v.status)
								).append(
									$('<td/>').html(v.subject)
								).append(
									$('<td/>').html(v.blurb)
								)
							);
						});
					}
				}
			}, 1000);
		});
	});
*/

	$('.jqs-status_button').on('click', function() {
		if ($(this).val() == 'See More') {
			$(this).closest('.jqs-orderItem').find('.jqs-extra_status').slideToggle(200);
			$(this).val('See Less');
		}
		else {
			$(this).closest('.jqs-orderItem').find('.jqs-extra_status').slideToggle(200);
			$(this).val('See More');
		}
	});

	$('[data-duedate]').on('click', function() {
		$.ajax({
			type: 'POST',
			url: '/admin/control/setDueDate',
			dataType: 'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			data: { orderId : $('.order-id').html(), 'orderItemSeqId' : $(this).closest('.order-item').attr('data-seqid'), dueDate : $(this).parent().siblings('.form-control').val() },
			async: false
		}).done(function(response) {
			if(!response.success) {
				alert('Error updating due date');
			}
		});
	});

	$('[data-removedate]').on('click', function() {
		var self = this;
		$.ajax({
			type: 'POST',
			url: '/admin/control/setDueDate',
			dataType: 'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			data: { orderId : $('.order-id').html(), 'orderItemSeqId' : $(this).closest('.order-item').attr('data-seqid'), dueDate : null },
			async: false
		}).done(function(response) {
			if(!response.success) {
				alert('Error updating due date');
			} else {
				$(self).parent().siblings('.form-control').val('Not Yet Assigned');
			}
		});
	});

	$('[data-assignedto]').on('click', function() {
		var action = $(this).attr('data-assignedto');
		var self = this;

		var data = {
			orderId: $('.order-id').html(), 
			orderItemSeqId: $(this).closest('.order-item').attr('data-seqid')
		}

		if (action == "add") { 
			data.assignToUser = $('[bns-assignedto]').val();
			data.removeUserAssignment = false;
		} else {
			data.removeUserAssignment = true;
		}
		$.ajax({
			type: 'POST',
			url: '/admin/control/setOrderItemArtwork',
			dataType: 'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			data: data,
			async: false
		}).done(function(response) {
			if(response.success) {
				if (action == "delete") {
					$(self).parent().siblings('.form-control').val('Not Yet Assigned');
				}
			} else {
				alert('Error updating user assignment.');
			}
		});
	});

	$('[data-plateid]').on('click', function() {
		var self = this;
		var printPlateId = $(this).parent().siblings('.form-control').val();
		$.ajax({
			type: 'POST',
			url: '/admin/control/setOrderItemArtwork',
			dataType: 'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			data: { orderId : $('.order-id').html(), 'orderItemSeqId' : $(this).closest('.order-item').attr('data-seqid'), printPlateId : printPlateId },
			async: false
		}).done(function(response) {
			if(response.success) {
				$(self).parent().siblings('.form-control').val(printPlateId);
			} else {
				alert('Error updating print plate id.');
			}
		});
	});

	$('.outsourcechange').on('change', function(e) {
		var outsource = $('select[name=outsource]').val();
		var dataObj = { orderId : $('.order-id').html(), outsourceable : $('input[name=outsourceable]').prop('checked') ? 'Y' : 'N' };
		if(outsource != '') {
			dataObj.outsource = outsource;
		}
		$.ajax({
			type: 'POST',
			url: '/admin/control/updateOrderItemOutSourceData',
			dataType: 'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			data: dataObj,
			async: false
		}).done(function(response) {
			if(response.success) {
				//
			} else {
				alert('Error updating outsource data.');
			}
		});
	});

	$('.pendingNetsuiteChange').on('change', function(e) {
		var dataObj = { orderId : $('.order-id').html(), pendingChange : $('input[name=pendingChange]').prop('checked') ? 'Y' : 'N' };
		$.ajax({
			type: 'POST',
			url: '/admin/control/updateOrderItemPendingChangeData',
			dataType: 'json',
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			data: dataObj,
			async: false
		}).done(function(response) {
			if(response.success) {
				//
			} else {
				alert('Error updating outsource data.');
			}
		});
	});

    $('.syracuseableToggle').on('change', function(e) {
        var dataObj = { orderId : $('.order-id').html(), printableSyracuse : $('input[name=printableSyracuse]').prop('checked') ? 'Y' : 'N' };
        $.ajax({
            type: 'POST',
            url: '/admin/control/updateOrderItemSyracuseable',
            dataType: 'json',
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: dataObj,
            async: false
        }).done(function(response) {
            if(response.success) {
                //
            } else {
                alert('Error updating outsource data.');
            }
        });
    });

    $('#isBlindShipUpdate').on('change', function(e) {
        var dataObj = { orderId : $('.order-id').html(), isBlindShipment : $('input[name=isBlindShip]').prop('checked') ? 'Y' : 'N' };
        $.ajax({
            type: 'POST',
            url: '/admin/control/updateOrderBlindShipData',
            dataType: 'json',
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: dataObj,
            async: false
        }).done(function(response) {
            if(response.success) {
                //
            } else {
                alert('Error updating blind shipment data.');
            }
        });
    });
});

function removeAutoFill() {
	$('.jqs-phraseListPopup').remove();
	$('*').unbind('click.autoFill89484739');
}

$('[data-auto-fill]').on('focus', function() {
	var thisElement = $(this);

	$('.jqs-phraseListPopup').remove();

	var leftAdjustment = thisElement.offset().left - thisElement.parent().offset().left;
	var topAdjustment = (thisElement.offset().top - thisElement.parent().offset().top) + thisElement.innerHeight() + parseInt(thisElement.css('border-width'));

	var phraseListPopup = $('<div />').addClass('jqs-phraseListPopup phraseListPopup').attr('style', 'top:' + topAdjustment + 'px; left:' + leftAdjustment + 'px;');

	for(var i = 0; i < phraseList.length; i++) {
		phraseListPopup.append(
			$('<div />').html(phraseList[i]).on('click', function() {
				thisElement.val($(this).html());
				removeAutoFill();
			})
		);
	}

	thisElement.parent().append(phraseListPopup);

	$(document).on('click.autoFill89484739', function(e) {
		if (!$(e.target).is(thisElement) && $(e.target).closest('.jqs-phraseListPopup').length == 0) {
			removeAutoFill();
		}
	});
});

$('.has-dropdown-options').focus(function(){
	$('.dropdown-options').hide();
	$(this).next('.dropdown-options').show();
});
$(document).mousedown(function(e){
    var container = $('.has-dropdown-options, .dropdown-options');
    if (!container.is(e.target) && container.has(e.target).length === 0){
    	$('.dropdown-options').hide();
    }
});
$('.dropdown-options li').click(function(){
	var dropdown = $(this).parents('.dropdown-options');
	var choice = $(this).html();
	dropdown.prev('.has-dropdown-options').val($(this).html());
	dropdown.hide();
});
$('.has-dropdown-options').on('input', function(e){
	var dropdown = $(this).next('.dropdown-options');
	dropdown.show();
	var term = $(this).val().toLowerCase();
	var showing = 0;
	dropdown.find('li').each(function(){
		var match = $(this).html().toLowerCase();
		if(match.indexOf(term) !== -1){
			$(this).show();
			showing++;
		} else {
			$(this).hide();
		}
	});
	if(showing == 0) {
		dropdown.hide();
	}
});

$('[bns-edittrigger]').on('click', function() {
	var targetedElement = $('[bns-editname="' + $(this).attr('bns-edittrigger') + '"]');
	targetedElement.addClass('hidden');
	targetedElement.after(
		$('<input />').attr({
			'type': 'text',
			'bns-editing': $(this).attr('bns-edittrigger')
		}).val(targetedElement.html())
	);

	$(this).addClass('hidden').after(
		$('<div />').attr('bns-editcancel', $(this).attr('bns-edittrigger')).addClass('btn btn-default btn-xs').on('click', function() {
			var targetedElement = $('[bns-editname="' + $(this).attr('bns-editcancel') + '"]');
			targetedElement.removeClass('hidden');
			$('[bns-editing="' + $(this).attr('bns-editcancel') + '"]').remove();
			$('[bns-editsave="' + $(this).attr('bns-editcancel') + '"]').remove();
			$(this).remove();
		}).html('Cancel')
	).after(
		$('<div />').attr('bns-editsave', $(this).attr('bns-edittrigger')).addClass('btn btn-default btn-xs').on('click', function() {
			var targetedElement = $('[bns-editname="' + $(this).attr('bns-editsave') + '"]');

			$.ajax({
				type: 'POST',
				url: '/admin/control/updateLineItemDescription',
				dataType: 'json',
				data: { 
					'orderId' : $('.order-id').html(), 
					'orderItemSequenceId' : $(this).closest('.order-item').data('seqid'), 
					'itemDescription' : $('[bns-editing="' + $(this).attr('bns-editsave') + '"]').val()
				},
				async: false
			}).done(function( data ) {
				
			});

			targetedElement.html($('[bns-editing="' + $(this).attr('bns-editsave') + '"]').val()).removeClass('hidden');
			$('[bns-editing="' + $(this).attr('bns-editsave') + '"]').remove();
			$('[bns-editcancel="' + $(this).attr('bns-editsave') + '"]').remove();
			$(this).remove();
		}).html('Save')
	)
});

var dataRows = ["foil", "emboss"];

$('.jqs-orderItem').each(function() {
	var orderItem = $(this);
	for (var x = 0; x < dataRows.length; x++) {
		var lastDataIndex = 0;
		var totalRows = $(orderItem).find('.jqs-' + dataRows[x] + 'DataRows').length;
		$(orderItem).find('.jqs-' + dataRows[x] + 'DataRows').each(function(i) {
			$(this).find('select').each(function() {
				if ($(this).val() != '') {
					lastDataIndex = i;
				}
			});
		});
		if (typeof lastDataIndex != 'undefined') {
			$(orderItem).find('.jqs-' + dataRows[x] + 'DataRows').each(function(i) {
				if (i <= lastDataIndex) {
					$(this).removeClass('hidden');
					if (i == totalRows - 2) {
						$('.jqs-add' + dataRows[x].charAt(0).toUpperCase() + dataRows[x].slice(1) + 'Row').parent().parent().remove();
					}
				}
			});
		}

		$('.jqs-add' + dataRows[x].charAt(0).toUpperCase() + dataRows[x].slice(1) + 'Row').on('click', function() {
			var controlParent = $(this).parent().parent();
			controlParent.siblings('.jqs-dataRows.hidden').first().removeClass('hidden');

			if (parseInt(controlParent.siblings('.jqs-dataRows:not(.hidden)').length) >= 20) {
				controlParent.remove();
			}
		});
	}
});

$('#priceJSON').each(function() {
	if (typeof $(this).html() != 'undefined') {
		$(this).html(JSON.stringify(JSON.parse($(this).html()), null, 2));
	}
});


$('.jqs-proofParent').each(function() {
	designNowEvent($(this).closest('.jqs-orderItem'), false);
});