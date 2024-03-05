function toolTipContent(e, imageName) {
	if(e == 'image') {
		$('body').append($('<div />').addClass('toolTip textCenter').append(
            $('<img />').attr({
                'src': 'https://actionenvelope.scene7.com/is/image/ActionEnvelope/' + imageName + '?fmt=png-alpha&wid=100',
            }).addClass('padding10')
        )) 
	} else if (e == 'text') {
		$('body').append($('<div />').addClass('toolTip textCenter').append(
            $('<p />').addClass('margin10').html($('[bns-bignametooltip]').attr('name'))
        ))

		$('.toolTip').css({
			left: parseInt($('[bns-bignametooltip]').offset().left) - parseInt(getFullWidth($('.toolTip')) / 2) + 6, 
			top: parseInt($('[bns-bignametooltip]').offset().top) - parseInt(getFullHeight($('.toolTip')) + 20) 
		}); 
	}	
}

$('[bns-bignametooltip]').hover(function(){
	toolTipContent('text')
}, function(){
	$('.toolTip').remove();
});
