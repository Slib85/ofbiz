$(document).ready(function() {
    $('.env-grid_container').on('scroll', function() {
        $('.env-grid_top').css('top', $(this).scrollTop() + 'px');
        $('.env-grid_left').css('left', $(this).scrollLeft() + 'px');
    });

    $(function() {
        $( "#resizable" ).resizable({
            helper: "ui-resizable-helper"
        });
    });


    $('.env-grid_container').trigger('scroll');


    $('.env-grid_row > *').on('keydown', function(e) {
        // Return/Enter Key
        if (e.keyCode == '13') {
            e.preventDefault();
            $(this).blur();
        }
        else if (e.keyCode == '37') {
            var currentElementFocus = $(':focus');
            var newElement = $(currentElementFocus).prev();

            e.preventDefault();

            doArrowAction(e.keyCode, newElement, $('.env-grid_container'), $('.env-grid_container .env-grid_left'));
        }
        else if (e.keyCode == '38') {
            var currentElementFocus = $(':focus');
            var newElement = $(currentElementFocus).parent().prev('.env-grid_row').children(':eq(' + $(currentElementFocus).index() + ')');

            e.preventDefault();

            doArrowAction(e.keyCode, newElement, $('.env-grid_container'), $('.env-grid_container .env-grid_top'));
        }
        else if (e.keyCode == '39') {
            var currentElementFocus = $(':focus');
            var newElement = $(currentElementFocus).next();

            e.preventDefault();

            doArrowAction(e.keyCode, newElement, $('.env-grid_container'), $('.env-grid_container .env-grid_left'));
        }
        else if (e.keyCode == '40') {
            var currentElementFocus = $(':focus');
            var newElement = $(currentElementFocus).parent().next('.env-grid_row').children(':eq(' + $(currentElementFocus).index() + ')');

            e.preventDefault();

            doArrowAction(e.keyCode, newElement, $('.env-grid_container'), $('.env-grid_container .env-grid_top'));
        }
    });


    function doArrowAction(keyCode, newElement, containerElement, containerElementChild) {
        if (newElement.length > 0) {
            newElement.focus();
            var sizeOfFloatingElement = (keyCode == '37' || keyCode == '39' ? containerElementChild.outerWidth() : containerElementChild.outerHeight());
            var distanceToIgnore = (keyCode == '37' || keyCode == '39' ? containerElement.offset().left : containerElement.offset().top) + sizeOfFloatingElement;
            var distanceOfNewElement = (keyCode == '37' || keyCode == '39' ? newElement.offset().left + containerElement.scrollLeft(): newElement.offset().top + containerElement.scrollTop()) - distanceToIgnore;

            if (keyCode == '37') {
                if (distanceOfNewElement <= containerElement.scrollLeft()) {
                    containerElement.scrollLeft(distanceOfNewElement);
                }
            }
            else if (keyCode == '38') {
                if (distanceOfNewElement <= containerElement.scrollTop()) {
                    containerElement.scrollTop(distanceOfNewElement);
                }
            }
            else if (keyCode == '39') {
                if (distanceOfNewElement + $(newElement).outerWidth() >= containerElement.scrollLeft() + ($(containerElement).outerWidth() - getScrollBarDistance(containerElement)) - containerElementChild.outerWidth()) {
                    containerElement.scrollLeft(containerElement.scrollLeft() + ((distanceOfNewElement + $(newElement).outerWidth()) - (containerElement.scrollLeft() + ($(containerElement).outerWidth() - getScrollBarDistance(containerElement)) - containerElementChild.outerWidth())));
                }
            }
            else if (keyCode == '40') {
                if (distanceOfNewElement + $(newElement).outerHeight() >= containerElement.scrollTop() + ($(containerElement).outerHeight() - getScrollBarDistance(containerElement)) - containerElementChild.outerHeight()) {
                    containerElement.scrollTop(containerElement.scrollTop() + ((distanceOfNewElement + $(newElement).outerHeight()) - (containerElement.scrollTop() + ($(containerElement).outerHeight() - getScrollBarDistance(containerElement)) - containerElementChild.outerHeight())));
                }
            }
        }
    }


    function getScrollBarDistance(element) {
        var helperDiv = $('<div />');
        $(element).append(helperDiv);

        var scrollBarDistance = $(element).outerWidth() - helperDiv.width();

        helperDiv.remove();

        return scrollBarDistance;
    }

    /*$('.env-grid_container').on('mousedown', function(e) {
        // Right Mouse Click
        if (e.which == '3') {
            var topAdjustment = 10;
            $('.env-grid_contextmenu').css({
                top: parseInt(e.pageY) - parseInt($('.env-grid_container').offset().top - topAdjustment),
                left: parseInt(e.pageX) - parseInt($('.env-grid_container').offset().left)
            });

            $('.env-grid_contextmenu').show();
        }
    });*/

    $('.env-grid_row > *').on('focus', function(e) {

//            console.log($(this).parent().index('.env-grid_row'));
//            console.log($(this).index());
//            console.log(($(this).next('div')).length);


    });

    $('.env-grid_row > *').on('blur', function(e) {

            console.log($(this).parent().index('.env-grid_row'));
            console.log($(this).index());
            console.log($(this).text());
        $('#my-grid').AddressingGrid('setCellValue', $(this).parent().index('.env-grid_row'), $(this).index(), $(this).text());


    });

    /*$('.env-grid_container').contextmenu(function() {
        return false;
    });*/
});

