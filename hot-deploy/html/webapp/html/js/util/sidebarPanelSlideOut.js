// Build this page just for envelopes and copy code from folders
$('.jqs-sidebarToggle').off('click.sidebarToggle').on('click.sidebarToggle', function() {
    if(!$(this).hasClass('noSlideOut')) {
        var sidebar = $(this).attr('data-sidebar-name');

        $('#' + sidebar).animate({
            'opacity': '1',
            'right': '0%'
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
    }
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
$('.sidebarPanel h4 .fa, #sidebar-returnAddress .colorTextureBodyInner .returnAddressButton').off('click.closeSidebar').on('click.closeSidebar', function(){
    $(this).parents('.sidebarPanel').animate({
        'opacity': '0',
        'right': '-100%'
    }, 150, 'linear');
});

$(document).mousedown(function(e){
    var container = $('.sidebarPanel');
    if (!container.is(e.target) && container.has(e.target).length === 0){
        container.animate({
            'opacity': '0',
            'right': '-100%'
        }, 150, 'linear');
    }
});

$('.env-grid_container').on('scroll', function() {
    $('.env-grid_top').css('top', $(this).scrollTop() + 'px');
    $('.env-grid_left').css('left', $(this).scrollLeft() + 'px');
});







