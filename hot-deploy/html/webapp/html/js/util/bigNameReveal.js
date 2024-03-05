function bnRevealClose(callback) {
	$('.bnRevealContainer.bnRevealActive').fadeOut(function() {
		$(this).removeClass('bnRevealActive');
		$('.bnRevealShadowedBackground').remove();
		$('body').removeClass('bnRevealNoOverflow');
        if (typeof callback == 'function') {
            callback();
        }
	});
}

/* This needs to go somewhere else lol */
$('.printedPriceList tr').on('click', function() {
	$(this).find('input').prop('checked', true);
});

function bnRevealLoad(targetId) {
	if(targetId == 'todaysDealEmailSignUp' || targetId == 'marchSale2019') {
		clearSiteWideTabContentShadowBox();
	}
	if ($('#' + targetId).length > 0) {
		//$('body').addClass('bnRevealNoOverflow').append(
		$('body').append(
			$('<div />').addClass('bnRevealShadowedBackground').on('click', function() {
				bnRevealClose();
			})
		);

		$(window).scrollTop(0);

		$('#' + targetId).addClass('bnRevealActive');

		var heightDifference = 0;

		$('#' + targetId + ' > *').each(function() {
			heightDifference += ($(this).hasClass('bnRevealBody') ? 0 : $(this).outerHeight());
		});
		
		$('#' + targetId + ' .bnRevealBody').css('height', 'calc(100% - ' + heightDifference + 'px)');

		var totalHeight = 0;
		
		// For when the position isn't fixed...
		setTimeout(function() {
			$(window).scrollTop(0);
		}, 500);
	}	
}

function bnRevealInit(element) {
    $('.jqs-bnRevealClose').off('click.bnRevealClose').on('click.bnRevealClose', function() {
        bnRevealClose();
    });

	$(element).off('click.bnReveal').on('click.bnReveal', function() {
		bnRevealLoad($(this).attr('data-bnReveal'));
	});
}

bnRevealInit($('[data-bnReveal]'));
