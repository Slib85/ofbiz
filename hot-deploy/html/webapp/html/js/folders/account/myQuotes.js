$('#quoteList').dataTable( {
    "ordering": false
});

$.fn.dataTable.ext.search.push(
    function( settings, data, dataIndex ) {
        var statusId = $('[name="statusId"]').val();
        var rowData = data[0];

        if (
            (statusId == 'all' && rowData.match(/QUO_(?:ORDERED|CREATED|APPROVED|SENT_EMAIL|SENT_NETSUITE|ASSIGNED|WAITFORVEND)/) != null) ||
            (statusId == 'open' && rowData.match(/QUO_(?:CREATED|APPROVED|SENT_EMAIL|SENT_NETSUITE|ASSIGNED|WAITFORVEND)/) != null) ||
            (statusId == 'ordered' && rowData.match(/QUO_(?:ORDERED)/) != null)
        ) {
            return true;
        }
        return false;
    }
);
 
$(document).ready(function() {
    var table = $('#quoteList').DataTable();
     
    // Event listener to the two range filtering inputs to redraw on input
    $('[name="statusId"]').on('change', function() {
        table.draw();
    });

    $('[bns-newquote]').on('click', function() {
        var self = $(this);
        self.off('click');

        $.ajax({
			type: "POST",
			url: "/folders/control/quoteRequestSubmission",
			data: {
				productStyle: self.attr('data-productid'),
                webSiteId: 'folders',
				firstName: self.attr('data-firstname'),
				lastName: self.attr('data-lastname'),
				email: self.attr('data-useremail'),
                countryGeoId: self.attr('data-countrygeoid'),
				phone: self.attr('data-phone'),
                companyName: self.attr('data-companyname'),
				address1: self.attr('data-address1'),
				address2: self.attr('data-address2'),
				city: self.attr('data-city'),
                stateProvinceGeoId: self.attr('data-stateprovincegeoid'),
                postalCode: self.attr('data-postalcode'),
                comment: self.attr('data-internalcomment')
			},
			dataType:'json',
			cache: false
		}).done(function(data) {
			if (data.success) {
                self.html('Quote Created!')
			}
			else {
                alert('There was an error placing a new quote request.  Please contact customer service.');
			}
		});
    });
});