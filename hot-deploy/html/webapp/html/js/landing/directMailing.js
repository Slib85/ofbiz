$('.slideIt-right').on('click', function(){
	var activeSlideParent = $(this).parents('.directMailingColumn').find('.activeThumbnail');
	activeSlideParent.next().addClass('activeThumbnail');
	$('.activeThumbnail').prev().removeClass('activeThumbnail');	
});

$('.slideIt-left').on('click', function(){
	var activeSlideParent = $(this).parents('.directMailingColumn').find('.activeThumbnail');
	activeSlideParent.prev().addClass('activeThumbnail');
	$('.activeThumbnail').next().removeClass('activeThumbnail');	
});

$('.directMailingThumbnails img').on('click', function(){
	var imageCounter = $(this).attr('data-key');
    var parent_element = $(this).parents('.directMailingColumn').find('.slideIt');
    parent_element.children("div").stop();

    parent_element.children("div").children().each(function() {
        $(this).css("visibility", "visible");
    });

    if (imageCounter == 1){
    	parent_element.children("div").animate({"left": "115px"},  200, function() {
            condenseContent(0, parent_element);
        });
    } else if (imageCounter == 2) {
    	parent_element.children("div").animate({"left": "-199px"},  200, function() {
            condenseContent(0, parent_element);
        });
    } else if (imageCounter == 3) {
    	parent_element.children("div").animate({"left": "-513px"},  200, function() {
            condenseContent(0, parent_element);
        });
    } else if(imageCounter == 4) {
    	parent_element.children("div").animate({"left": "-827px"},  200, function() {
            condenseContent(0, parent_element);
        });
    } else {
    	parent_element.children("div").animate({"left": "-1140px"},  200, function() {
            condenseContent(0, parent_element);
        });
    }

    $(this).parent().find('.activeThumbnail').removeClass('activeThumbnail');
    $(this).addClass('activeThumbnail');
})

$('.directMailingButton').on('click', function() {
    window.location = '/'+websiteId+'/control/directMailingProduct?productId=' + $(this).data('name') + '&get_quote=true'
})

$(document).ready(function(){
    slideIt_init();
})









