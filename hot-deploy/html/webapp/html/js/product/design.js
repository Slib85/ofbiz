$(function() {
	$('.design .stock-chooser li').on('click', function(){
		$(this).addClass('active').siblings().removeClass('active');
	})

	$('.design .prices button').on('click', function(){
		$(this).addClass('active').find('i').removeClass('fa-circle-o').addClass('fa-dot-circle-o').end()
			.siblings('button').removeClass('active').find('i').removeClass('fa-dot-circle-o').addClass('fa-circle-o')
	})

	$('.design .production button, .printed-options button').on('click', function(){
		$(this).addClass('active').parent().siblings().find('button').removeClass('active')
	})

	$('.j-btn-printed').on('click', function(){
		$(this).closest('.panel-body').find('.printed-options').collapse('show');
	})

	$('.j-btn-plain').on('click', function(){
		$(this).closest('.panel-body').find('.printed-options').collapse('hide');
	})

	$('.quantity > input').on('keydown', function(e){
        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
            (e.keyCode == 65 && e.ctrlKey === true) ||
            (e.keyCode >= 35 && e.keyCode <= 39)) {
                 return;
        }
        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
            e.preventDefault();
        }
	}).on('keyup', function(e){
		$(this).parent().removeClass('has-error');
		var error = false;
		var errorText = "";

		var value = $(this).val();
		if(value === ""){
			error = true;
		} else if ( value <= 0){
			error = true;
			errorText = "Must be greater than 0.";
		} else if ( value < 500 && value %50 !== 0){
			error = true;
			errorText = "Must be increments of 50.";
		} else if ( value > 500 && value %100 !== 0){
			error = true;
			errorText = "Must be increments of 100.";
		} else if ( value > 50000){
			error = true;
			errorText = "Maximum of 50,000.";
		}

		var priceBlock = $(this).closest('.panel-body').find('.custom-qty');
		var errorBlock = $(this).closest('.panel-body').find('.text-danger');
		if(error){
			if(value !== ""){
				$(this).parent().addClass('has-error');
			}
			priceBlock.addClass('hidden')
			errorBlock.html(errorText);
			priceBlock.siblings().first().trigger('click');
		} else {
			var priceBlock = $(this).closest('.panel-body').find('.custom-qty');
			priceBlock.children('.qty').html(value);
			priceBlock.children('.val').html('$50.00');
			errorBlock.html("");
			priceBlock.removeClass('hidden').trigger('click');
		}
	})

	$('#yes-envelopes').on('click', function(){
		if($(this).hasClass('active')){
			$(this).removeClass('active').find('i').removeClass('fa-check-square-o').addClass('fa-square-o');
			$(this).closest('.panel').find('.panel-collapse').collapse('hide');
		} else {
			$(this).addClass('active').find('i').removeClass('fa-square-o').addClass('fa-check-square-o');
			$(this).closest('.panel').find('.panel-collapse').collapse('show');
		}
	})

    var domObjOrigOffset = $('.summary').parent().offset().top;
	//run on scroll
	$(window).scroll(function(){
		var el = $('.summary').parent();
		var windowPos = $(window).scrollTop();
		var windowHeight= $(window).height();
		var parentHeight = el.parent().height();
		var objHeight = el.height();
		var top = 0;
		if (windowHeight < objHeight){
			top = windowPos + windowHeight - objHeight - domObjOrigOffset;
		} else {
			top = windowPos - domObjOrigOffset + 30;
		}
		if (top < 0){
			top = 0;
		} else if (top > parentHeight - objHeight){
			top = parentHeight - objHeight;
		}
		el.stop().animate({'padding-top':top},500);
	});

	$('.customize').on('click', function(e){
		e.preventDefault();
		$(this).hide();
		var el = $(this).closest('.row');
		var carousel = el.find('#design-carousel');
		var top = carousel.position().top;
		var left = carousel.position().left;
		el.find('#design-carousel').css({'position':'absolute', 'top':top, 'left':left}).animate({'width':'100%','height':'100%', 'left':0});
		el.find('.item img').animate({'max-height':'90%', 'max-width':'90%', 'min-height':'90%'});
		el.find('.stock-chooser').animate({'opacity':0}, 50, function(){$(this).hide();});
		el.animate({'height':750}, 500, function(){
			el.find('.designer-toolbar').animate({'left':'3px'})
		});
	});
});
