/*-----------------------------------------
 * Collapsible widget JS
-----------------------------------------*/
$(document).ready(function() {
    function hideElement(parentElement, targetElement) {
        parentElement.attr("bns-collapsed", "true");

        targetElement.css({
            height: "0px",
            overflow: "hidden"
        });
    }

    function showElement(parentElement, targetElement) {
        parentElement.attr("bns-collapsed", "false");

        targetElement.css({
            height: "auto",
            overflow: "visible"
        });
    }

    $("[bns-collapse_content]").each(function() {
        var targetElement = $("#" + $(this).attr("bns-collapse_content"));

        if (targetElement.length > 0) {
            if ($(this).attr("bns-collapsed") == "true") {
                hideElement($(this), targetElement);
            } else {
                showElement($(this), targetElement);
            }
        }
    });

    $("[bns-collapse_content]").on("click", function() {
        var targetElement = $("#" + $(this).attr("bns-collapse_content"));

        if (targetElement.length > 0) {
            if ($(this).attr("bns-collapsed") == "false") {
                hideElement($(this), targetElement);
            } else {
                showElement($(this), targetElement);
            }
        }
    });
});