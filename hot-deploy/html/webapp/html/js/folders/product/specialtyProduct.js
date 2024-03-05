$(document).ready(function(){
	$('[data-bnreveal]').on('click', function(){
		var specialtyProductId = $(this).attr('data-prodId');

		//console.log(specialtyProductId);
		waitForFinalEvent(function() {
            $.ajax({
                url: '/folders/control/specialtyProductsPopUp',
                data: {
                    productId: specialtyProductId
                }
            }).done(function(data) {
                $('.specialtyProdModal').html(data);
            });
        }, 0);
	});
    
});

if (typeof BigNameValidate != 'undefined') {
    window['quoteRequestSpecialtyProduct'] = new BigNameValidate($('[data-bigNameValidateForm="quoteRequestSpecialtyProduct"]'), 'quoteRequestSpecialtyProduct');
}

function quoteRequestSubmit() {
    var quantities = [];

    for (var i = 1; i <= 4; i++) {
        // var quantity = productPage.getProducts()[productPage.activeProductIndex].getProductAttributes().selectionData['quoteQuantity' + i];
        var quantity = $('select[name="quoteQuantity' + i + 'Selection"]').val();

        if (typeof quantity != 'undefined') {
            quantities.push(quantity);
        }
    }

    var formData = {
            additonalInfo: $('textarea[name="quoteDescribeProject"]').val() + "<br />" + "Reference Number: " + $('input[name="quoteReferenceNumber"]').val(),
            referenceNumber: $('input[name="quoteReferenceNumber"]').val(),
            productQuantity: quantities.join(', '),
            firstName: $('input[name="quoteFirstName"]').val(),
            lastName: $('input[name="quoteLastName"]').val(),
            email: $('input[name="quoteEmailAddress"]').val(),
            phone: $('input[name="quotePhoneNumber"]').val(),
            companyName: $('input[name="quoteCompanyName"]').val(),
            address1: $('input[name="quoteAddress"]').val(),
            stateProvinceGeoId: $('input[name="quoteState"]').val(),
            postalCode: $('input[name="quoteZip"]').val(),
            webSiteId: 'folders'
        };

    $.ajax({
        type: "POST",
        url: "/folders/control/quoteRequestSubmission",
        data: formData,
        dataType:'json',
        cache: false
    }).done(function(data) {
        if (data.success) {
            GoogleAnalytics.trackEvent('Specialty Quote Request', 'Finish', data.quoteId);
            $('#quoteId').html(data.quoteId);

            $('[data-bigNameValidateForm="quoteRequestSpecialtyProduct"]').removeClass('hidden').addClass('hidden');
            $('[bns-quotecompleted]').removeClass('hidden');
        }
        else {
            GoogleAnalytics.trackEvent('Specialty Quote Request', 'Finish', 'Error');
        }
    }).fail(function(XMLHttpRequest, error, errorThrown){
        //console.log(errorThrown)
    });

    //clicks on this will trigger tracking of quote start
    $('[name="quoteRequestForm"] select,[name="quoteRequestForm"] textarea').on('change', function(e) {
        GoogleAnalytics.trackEvent('Specialty Quote Request', 'CUSTOM', 'Start');
    });
}

$('[name="quoteQuantity1Selection"], [name="quoteQuantity2Selection"], [name="quoteQuantity3Selection"], [name="quoteQuantity4Selection"]').on('change', function() {
    if ($(this).val() == 'custom') {
        var parent = $(this).parent().parent();
        var quoteQuantitySelectionNumber = $(this).attr('name').replace(/\D/g, '');
        $(this).parent().remove();

        parent.append(
            $('<input />').attr({
                'placeholder': 'Enter Custom Quantity',
                'name': 'quoteQuantity' + quoteQuantitySelectionNumber + 'Selection'
            })
        );

        parent.find('input').focus();
    }
});