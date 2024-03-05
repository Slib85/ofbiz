$.fn.spinner = function(create, fullScreen, size, borderWidth, bgColor, text, textColor, timeToKill) {
	var element = this;
	
	if(getIEVersion() != 8) {
		function removeSpinner(element) {
			if (typeof element.attr('bns-spinner_id') != 'undefined') {
				var spinnerId = element.attr('bns-spinner_id');
				element.removeAttr('bns-spinner_id').find('[bns-spinner_id="' + spinnerId + '"]').remove();
			}
		}
        var rand = 'spinner_' + Math.floor((Math.random() * 99999) + 1);
        //element.addClass(rand);

		/*
        element.find('div[bns-spinner_id="' + spinnerId + '"].spinner-shade').remove();
        element.find('div[bns-spinner_id="' + spinnerId + '"].spinner').remove();
        element.find('div[bns-spinner_id="' + spinnerId + '"].spinner-text').remove();
		*/

		removeSpinner(element);

        if(create) {
            var fullScreen = (typeof fullScreen !== 'undefined' && fullScreen != null ? fullScreen : false);
            var size = (typeof size !== 'undefined' && size != null ? size : 100) + 'px';
            var borderWidth = (typeof borderWidth !== 'undefined' && borderWidth != null ? borderWidth : 3) + 'px';
            var bgColor = (typeof bgColor !== 'undefined' && bgColor != null ? bgColor : '#000000');
            var text = (typeof text !== 'undefined' && text != null ? text : 'loading...');
            var textColor = (typeof textColor !== 'undefined' && textColor != null ? textColor : 'ffffff');
			var timeToKill = (typeof timeToKill !== 'undefined' && timeToKill != null ? timeToKill : 10000);
			element.attr('bns-spinner_id', rand);
            element.append(
                $('<div/>').attr('bns-spinner_id', rand).addClass('spinner-shade').css({
                    'background-color': '#' + bgColor
                })
            ).append(
                $('<div/>').attr('bns-spinner_id', rand).addClass('spinner')
            ).append(
                $('<span/>').attr('bns-spinner_id', rand).addClass('spinner-text').html(text)
            );

            try {
                document.styleSheets[1].insertRule('.spinner:after { width:' + size + ' !important; height:' + size + ' !important; border-width:' + borderWidth + ' !important;', 0);
            } catch (e) {
                // Do Nothing
            }
            $('.spinner-text').css({
                'color': '#' + textColor,
                'top': 'calc(50% - ' + parseInt($('.spinner-text').height() / 2) + 'px)',
                'left': 'calc(50% - ' + parseInt($('.spinner-text').width() / 2) + 'px)'
            });

            if (fullScreen) {
                $('.spinner-shade').css({ 'position':'fixed' });
                $('.spinner-text').css({ 'position':'fixed' });
                $('.spinner').css({ 'position':'fixed' });
            }

            waitForFinalEvent(function(){
                element.spinner(false);
                //element.removeClass(rand);
            }, timeToKill, 'spinner_id_' + rand);
        }
    }
};