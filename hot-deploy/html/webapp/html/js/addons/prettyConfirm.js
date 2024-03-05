var prettyConfirm = function(title, message, buttons) {
	var buttonsDiv = $('<div/>').addClass('buttons');

	$.each(buttons, function(name,action){
		var isActive = ((name.indexOf(':active') !== -1) ? true : false);
		buttonsDiv.append(
			$('<div/>').addClass('button').addClass(((isActive) ? 'active' : '')).html(((isActive) ? name.split(':')[0] : name)).on('click', function(){
				if(typeof action === 'function'){
					action();
				}
				$(this).closest('.confirmDialog').bPopup().close();
			})
		)
	})

	$('<div/>').addClass('confirmDialog').append(
		$('<div/>').addClass('content').append(
			$('<div/>').addClass('header').append(
				$('<img/>').attr('src', '/html/img/icon/question_ico.png')
			).append(
				$('<span/>').addClass('title').html(title)
			).append(
				$('<span/>').addClass('close').html('X')
			)
		).append(
			$('<div/>').addClass('message').append(
				message
			)
		).append(
			buttonsDiv
		)
	).appendTo(
		$('body')
	).bPopup({
		modal:true,
		modalClose: false,
		transition: "fadeIn",
		speed: 100,
		closeClass: 'close',
		positionStyle: 'fixed',
		modalColor: '#aaa',
		onClose: function(){
			$(this).remove();
		}
	}).draggable({
		handle: ".header",
		containment: $('body')
	});
}