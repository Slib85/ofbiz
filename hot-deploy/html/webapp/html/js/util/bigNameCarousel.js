function BigNameCarousel(parentElement) {
    var bigNameCarouselInterval;
    var hide;
    var show;

    function beginInterval() {
        bigNameCarouselInterval = setInterval(function() {
            loadCarouselItem((parentElement.find("[bns-carousel-dot] [bns-carousel-dot-active]").index() + 1) % parseInt(parentElement.find("[bns-carousel-dot] > *").length));
        }, 6000);
    }

    function loadCarouselItem(carouselIndex) {
        var activeCarousel = (parentElement.find("[bns-image_active]").length > 0 ? parentElement.find("[bns-image_active]") : parentElement.find("[bns-image_index]:nth-child(1)"));
        var nextCarousel = parentElement.find("[bns-image_index='" + carouselIndex + "']");

        if (typeof hide == "undefined" && typeof show == "undefined") {
            window.clearInterval(hide);
            hide = window.setInterval(function() {
                var newOpacity = parseFloat(activeCarousel.css("opacity")) - .04;
                
                if (newOpacity < 0) {
                    parentElement.find("[bns-carousel-dot] > *:nth-child(" + (parseInt(activeCarousel.attr("bns-image_index")) + 1) + ")").removeAttr("bns-carousel-dot-active"); 
                    activeCarousel.css("opacity", 0);
                    activeCarousel.css("z-index", "1");
                    activeCarousel.removeAttr("bns-image_active");
                    window.clearInterval(hide);
                    hide = undefined;
                } else {
                    activeCarousel.css("opacity", newOpacity);
                    activeCarousel.css("z-index", "2");
                }
            }, 16);

            window.clearInterval(show);
            show = window.setInterval(function() {
                var newOpacity = parseFloat(nextCarousel.css("opacity")) + .04;
                
                if (newOpacity > 1) {
                    parentElement.find("[bns-carousel-dot] > *:nth-child(" + (parseInt(nextCarousel.attr("bns-image_index")) + 1) + ")").attr("bns-carousel-dot-active", ""); 
                    nextCarousel.css("opacity", 1);
                    nextCarousel.css("z-index", "2");
                    nextCarousel.attr("bns-image_active", "");
                    window.clearInterval(show);
                    show = undefined;
                } else {
                    nextCarousel.css("opacity", newOpacity);
                    nextCarousel.css("z-index", "2");
                }
            }, 16);
        }
    }

    function init() {
        var dotElement = $("<div />").attr("bns-carousel-dot", "");

        for (var i = 0; i < $(parentElement).find("[bns-carouse-image-container] > *").length; i++) {
            if (i == 0) {
                dotElement.append(
                    $("<div />").attr("bns-carousel-dot-active", "")
                );
            } else {
                dotElement.append(
                    $("<div />")
                );
            }
        }

        $(parentElement).append(dotElement);

        $(parentElement).append(
            $("<div />").attr("bns-carousel-box-left", "")
        );
        $(parentElement).append(
            $("<i />").attr({
                "bns-carousel-arrow-left": "",
                "class": "fa fa-chevron-left"
            })
        );
        $(parentElement).append(
            $("<div />").attr("bns-carousel-box-right", "")
        );
        $(parentElement).append(
            $("<i />").attr({
                "bns-carousel-arrow-right": "",
                "class": "fa fa-chevron-right"
            })
        );

        $(parentElement).find("[bns-carousel-dot] > *").each(function() {
            $(this).on("click", function() {
                if (typeof hide == "undefined" && typeof show == "undefined") {
                    clearInterval(bigNameCarouselInterval);
                    loadCarouselItem($(this).index());
                }
            });
        });
    
        $(parentElement).find("[bns-carousel-box-left], [bns-carousel-arrow-left]").on("click", function() {
            if (typeof hide == "undefined" && typeof show == "undefined") {
                var previousIndex = parentElement.find("[bns-carousel-dot] [bns-carousel-dot-active]").index() - 1;
                
                clearInterval(bigNameCarouselInterval);
                loadCarouselItem(previousIndex < 0 ? parentElement.find("[bns-carousel-dot] > *").length - 1 : previousIndex);
            }
        });
        
        $(parentElement).find("[bns-carousel-box-right], [bns-carousel-arrow-right]").on("click", function() {
            if (typeof hide == "undefined" && typeof show == "undefined") {
                var nextIndex = parentElement.find("[bns-carousel-dot] [bns-carousel-dot-active]").index() + 1;
                
                clearInterval(bigNameCarouselInterval);
                loadCarouselItem(nextIndex > parentElement.find("[bns-carousel-dot] > *").length - 1 ? 0 : nextIndex);
            }
        });
    
        $(parentElement).hover(function() {
            clearInterval(bigNameCarouselInterval);
        },function() {
            beginInterval();
        });

        beginInterval();
    }

    init();
}

$("[bns-carousel]").each(function() {
    BigNameCarousel($(this));
});