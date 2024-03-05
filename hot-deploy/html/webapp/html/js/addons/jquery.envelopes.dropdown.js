// Product Page
// Developed by Ricardo Cunha <ricardo@envelopes.com> & Shoab Khan <shoab@envelopes.com>
// Copyright 2013 - All rights reserved

var $ = jQuery.noConflict();
(function($){
	var Dropdown = function(element, options) {
		var elem = $(element);
		var self = this;
		var lastScroll = 0;
		var closeTimeout = null;

		/* PRODUCT SETTINGS */
		var settings = $.extend({
			debug: false,
			choices: {},
			altChoices: {},
			action: function(){},
			clearChoice: null,
			clearAction: function(){},
			extraClass: '',
			appendTo: $('body')
		}, options || {});

		var choices = settings.choices;
		var altChoices = settings.altChoices;

		currentChoice = elem.html();

		// Public method - can be called from client code
		this.init = function(){
			init();
		};

		this.addChoice = function(option, val, offset){
			choices[option] = val;
		}

		// Private methods - can only be called from within this object
		var init = function(){
			elem.unbind('click').on('click', function(){
				var scrollY = 0;
				var menu = $('<div/>').addClass('dropdown').addClass(settings.extraClass);
				menu.css({
					position:'absolute',
					top:$(this).position().top + parseFloat($(this).css('margin-top')),
					left:$(this).position().left + parseFloat($(this).css('margin-left'))
				});
				menu.append('<span/>').html(settings.title);
				var opts = $('<ul/>');

				if(settings.clearChoice !== null){
					for (var key in settings.clearChoice) {
						opts.append(
							$('<li/>').html(((settings.clearChoice[key]!=null)?"<img src='//www.envelopes.com/html/img/icon/swatch/"+settings.clearChoice[key]+"'/>":"")+key).on('click', function(){
								settings.clearAction();
								menu.remove();
							})
						);
					}
				}

				if($.isEmptyObject(altChoices)) {
					var isArray = (typeof choices === 'object' && choices.length > 1)
					$.each(choices, function(k,v){
						var label = '';
						if (isArray){
							label = v;
						} else if (v!=null && typeof v === 'number' && isFinite(v)){
							label = k + ' ('+v+')';
						} else if (v!=null && typeof v != 'function'){
							label = "<img src='//www.envelopes.com/html/img/icon/swatch/"+v+"'/>"+k;
						} else {
							label = k;
						}

						opts.append(
							$('<li/>').html(label).on('click', function(){
								if (isArray){
									settings.action(v);
								} else {
									settings.action(k);
								}
								menu.remove();
							}).addClass(function(){
								return ($('<div/>').html(label).html().toLowerCase() == elem.html().toLowerCase()) ? "selected" : "";
							}())
						);

						if (typeof v === 'function'){
							var el = opts.children().last();
							v(el);
							el.unbind();
							el.addClass('func')
						}
					});
				} else {
					$.each(altChoices, function(k,v){
						var label = k;

						opts.append(
							$('<li/>').html(label).on('click', function(){
								settings.action(v);
								menu.remove();
							})
						);
					});
				}

				var fnItems = opts.find('.func');
				fnItems.parent().append(fnItems);
				menu.append(opts);
				menu.on('mouseleave', function(){
					closeTimeout = window.setTimeout(function(){menu.remove()}, 500);
				}).on('mouseenter', function(){
					window.clearTimeout(closeTimeout);
				});

				elem.parent().append(menu);
				var goTo = menu.find('.selected');

				if (goTo.length != 0){
					menu.scrollTo(goTo, {offset:-4} );
				}
			})
		};
		init();
	};

	$.fn.Dropdown = function(options) {
		return this.each(function(){
			var element = $(this);
			// Return early if this element already has a plugin instance
			if (element.data('dropdown')) {
				return;
			}
			// pass options to plugin constructor
			var dropdownInstance = new Dropdown(this, options);
			// Store plugin object in this element's data
			element.data(dropdownInstance);
		});
	};
})(jQuery);