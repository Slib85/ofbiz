if (typeof elasticSearchResults == 'undefined') {
    $(document).on('ready', function () {
        elasticSearchResults.initSearch();
    });
} else {
    elasticSearchResults.initSearch();
}

deferImg();
fixedContent();

if (typeof initSlideout == 'function') {
    initSlideout();
}


var checkFilterList = function() {
    var displayed_width = 0;

    $(".filter-list").children("div").each(function() {
        displayed_width += $(this).css("display") != "none" ? getFullWidth($(this)) : 0;
    });

    if (getFullWidth($(".filter-list")) > displayed_width && $(window).width() > 1000) {
        $(".more-filters-container > div").css("display", "none");
    }
    else {
        $(".more-filters-container > div").css("display", "block");
    }
};

checkFilterList();

$(window).on("resize", function() {
    waitForFinalEvent(function(){
        checkFilterList();
    }, 200, "filterList_Id");
});

$(".more-filters-container > .button-regular").on("click", function() {
    if ($(".filter-list").css("overflow") == "hidden") {
        $(".filter-list").css({
            "overflow":"auto",
            "height":"auto"
        }).addClass("show");

        $(this).html("Less Filters");
    }
    else {
        $(".filter-list").css({
            "overflow":"hidden",
            "height":"35px"
        }).removeClass("show");

        $(this).html("More Filters");
    }
});

$('[bns-removefilter]').off('click.removeFilter').on('click.removeFilter', function() {
    elasticSearchResults.removeFilter($(this).attr('bns-removefilter'));
});

window.addEventListener('popstate', function(e) {
    var data = e.state.request.replace('?','');
    elasticSearchResults.getResults('','','', data)
});

$(document).ready(function() {
    slideIt_init($('.category .slideIt'));
});