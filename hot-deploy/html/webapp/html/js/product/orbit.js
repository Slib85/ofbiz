/**
 * Created by Manu on 8/13/2014.
 */


/*$(document).foundation({
    orbit: {
        navigation_arrows: true,
        slide_number: false,
        timer: false,
        next_on_click: false,
        bullets: false,
        swipe: false
    }
});*/

//navigation_arrows: true;slide_number: false;timer: false;next_on_click: false;bullets: false;swipe: false;

$(document).ready(function(){



    $(".button-previous").on("click", function() {
        target = $(this).data("target");

        $("[data-orbit-slide^=" + target + "]").each(function() {
            if (parseInt($(this).css("margin-left")) == 0) {
                current_position = parseInt($(this).data("orbit-slide").replace(target + "-", ""));
                if (!((current_position - 1) <= 0)) {
                    $("#bullet-" + target + current_position).removeClass("filled");
                    $("#bullet-" + target + (current_position - 1)).addClass("filled");


                    if ((current_position - 1) == 1) {
                        $(".button-previous").css("visibility", "hidden");
                    }

                    $(".button-next").css("visibility", "visible");

                    $(".orbit-prev").trigger("click");
                }

                return false;
            }
        });
    });

    $(".button-next").on("click", function() {
        target = $(this).data("target");
        total_elements = $("[data-orbit-slide^=" + target + "]").length;

        $("[data-orbit-slide^=" + target + "]").each(function() {
            if (parseInt($(this).css("margin-left")) == 0) {
                current_position = parseInt($(this).data("orbit-slide").replace(target + "-", ""));
                if (!((current_position + 1) > total_elements)) {
                    $("#bullet-" + target + current_position).removeClass("filled");
                    $("#bullet-" + target + (current_position + 1)).addClass("filled");

                    if ((current_position + 1) >= total_elements) {
                        $(".button-next").css("visibility", "hidden");
                    }

                    $(".button-previous").css("visibility", "visible");

                    $(".orbit-next").trigger("click");
                }

                return false;
            }
        });
    });
});
