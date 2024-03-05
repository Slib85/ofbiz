/**
 * Created by Manu on 11/10/2015.
 */
function setCaret() {
    var el = document.getElementById("editable");
    var range = document.createRange();
    var sel = window.getSelection();
    console.log(el.childNodes);
    range.setStart(el.childNodes[0], 5);
    range.collapse(true);
    sel.removeAllRanges();
    sel.addRange(range);
    el.focus();
}