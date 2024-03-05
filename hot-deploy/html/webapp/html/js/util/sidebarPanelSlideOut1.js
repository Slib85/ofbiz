// Build this page just for envelopes and copy code from folders

// function sidebarPanelSlideOut() {
// 	var self = this;

//     $('.jqs-sidebarToggle').off('click.sidebarToggle').on('click.sidebarToggle', function() {
//         var sidebar = $(this).attr('data-sidebar-name');

//         $('#' + sidebar).animate({
//             'opacity': '1',
//             'left': ($('.productSidebar').offset().left / $(window).width() * 100) + '%'
//         }, 150, 'linear');

//         if(sidebar == 'dropdown-editText' || sidebar == 'dropdown-editImage'){
//             $('.product-images').addClass('hidden');
//             $('#doogma').removeClass('hidden');
//         }

//         if ($('.colorTextureBodyInner').find('[selection-target="colorSelection"]').attr('selection-selected') != 'undefined') {
//             var pageColorTexture = $(this).offset().top;
//             $('.colorTextureBodyInner').scrollTo('[selection-selected]');
//         }

//         if ($('.colorTextureBodyInner').find('[selection-target="quoteColorSelection"]').attr('selection-selected') != 'undefined') {
//             var pageColorTexture = $(this).offset().top;
//             $('.colorTextureBodyInner').scrollTo('[selection-selected]');
//         }

//         slideIt_init();
//     });

//     $('.sidebarPanel h4 .fa').off('click.closeSidebar').on('click.closeSidebar', function(){
//         $(this).parents('.sidebarPanel').animate({
//             'opacity': '0',
//             'left': '100%'
//         }, 150, 'linear');

//         $('.product-images').removeClass('hidden');
//         $('#doogma').addClass('hidden');
//     });
// };

$('.jqs-sidebarToggle').off('click.sidebarToggle').on('click.sidebarToggle', function() {
    var sidebar = $(this).attr('data-sidebar-name');

    $('#' + sidebar).animate({
        'opacity': '1',
        'left': ($('.productSidebar').offset().left / $(window).width() * 100) + '%'
    }, 150, 'linear');

    if ($('.colorTextureBodyInner').find('[selection-target="layoutSelection"]').attr('selection-selected') != 'undefined') {
        var pageColorTexture = $(this).offset().top;
        $('.colorTextureBodyInner').scrollTo('[selection-selected]');
    }

    if ($('.colorTextureBodyInner').find('[selection-target="quoteColorSelection"]').attr('selection-selected') != 'undefined') {
        var pageColorTexture = $(this).offset().top;
        $('.colorTextureBodyInner').scrollTo('[selection-selected]');
    }

    slideIt_init();

});

// Update Selection from Side Bar Panel
$('[bns-selection]').off('click.bnsSelection').on('click.bnsSelection', function() {
    if (typeof $(this).attr('selection-selected') == 'undefined' || (typeof $(this).attr('selection-selected') != 'undefined' && $(this).attr('selection-selected') == 'false')) {
        $('[bns-selection]').each(function(){
            $(this).removeAttr('selection-selected');
        });
        $(this).attr('selection-selected', 'true');
        $('.sidebarPanel h4 .fa').trigger('click.closeSidebar');
    }
    //if ($(this).attr('selection-target') == $('.selectListParent').attr('selection-name')) {
    var selectionName = $(this).attr('selection-target');
    var selectedText = $(this).find('div div').text();
    if (selectionName == 'quantity') {
        $('.productSideBarSection').find('[selection-name="' + selectionName + '"]').find('.placeholder').text(selectedText + ' Qty.');
        productPage.updateProductQuantity();
        productPage.updateProductPrice();
        productPage.getCostEstimate();
    } else {
        $('.productSideBarSection').find('[selection-name="' + selectionName + '"]').text(selectedText);
    }
    //}

    // self.selectionFontResize($('[bns-colorselecttext]'), 56);
});


function updateInnerBodyHeight() {
    $('.sidebarPanel').each(function(){
        var headerHeight = $(this).find('.colorTextureHeading').outerHeight();
        var windowHeight = $(window).height();
        var scrollHeight = windowHeight - headerHeight - 15;

        var innerBody = $(this).find('.colorTextureBodyInner');
        innerBody.css('height', 'auto');
        var innerBodyContents = $(innerBody).height();
        innerBody.css('height', scrollHeight + 'px');
    });
}

updateInnerBodyHeight();

// Close Sidebar Panel by clicking Icon or clicking off screen
$('.sidebarPanel h4 .fa').off('click.closeSidebar').on('click.closeSidebar', function(){
    $(this).parents('.sidebarPanel').animate({
        'opacity': '0',
        'left': '100%'
    }, 150, 'linear');
});

$(document).mousedown(function(e){
    var container = $('.sidebarPanel');
    if (!container.is(e.target) && container.has(e.target).length === 0){
        container.animate({
            'opacity': '0',
            'left': '100%'
        }, 150, 'linear');
    }
});

$('.env-grid_container').on('scroll', function() {
    $('.env-grid_top').css('top', $(this).scrollTop() + 'px');
    $('.env-grid_left').css('left', $(this).scrollLeft() + 'px');
});







