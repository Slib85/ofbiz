// rotating text banner
(function() {
    var bncRotator = $(".bncRotator");
    var textIndex = -1;
    function showNextText() {
        ++textIndex;
        bncRotator.eq(textIndex % bncRotator.length)
            .fadeIn(2000)
            .delay(3000)
            .fadeOut(2000, showNextText);
    }
    showNextText();
})();