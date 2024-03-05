/*
 FILE: dropdown.js
 AUTHOR: Michael Harokopakis

 OPTIONS:
 1) data-dropdown-target: Points to the element that has the content you wish to display. The
 target requires an ID with the same value.

 2) data-dropdown-options: click (DEFAULT), hover, lock-width (Used to allow the buttons width to not be the same size as the children in the dropdown)

 3) data-dropdown-icons: Used to display two separate icons when the target element is displayed
 or not.  The first icon listed will display when content is not visible.
 */

var mobile_limit = 1024;
var mouse_Y_axis = 0;
var mouse_X_axis = 0;
var mouse_Y_direction = "";
var mouse_X_direction = "";
var current_hovered_element;
var display_drop_down_timeout = "";
var allowDefaultFunctionality = true;

// Used for when we leave the popup to prevent it from disappearing when going back in.
var drop_down_target_listener;

// Used for delaying the popup from disappearing immediately after leaving.
var hide_drop_down_timeout;

// Prevents the .each from queing up too many at once and eventually slowing down the site.
var resize_timeout;

// Used to keep track of what element was recently used.
var previous_drop_down = [];

// List of styles to ignore for when the window is resized.
var style_ignore_list = [];

var createDropDownEvent = function(element) {
    if (element.data("dropdown-options")) {
        $("#" + element.data("dropdown-target")).appendTo("body");
        styleDropDownContent(element, style_ignore_list, true, getReverseDropDown(element));

        // If we are using a dropdown as a select list, we need to set the highest width of both the parent and the dropdown.
        if (element.data("dropdown-options").match(/^(?:.*\s+)?select(?:\s+.*)?$/gi)) {
            // Select
            if (element.width() > $("#" + element.data("dropdown-target")).width()) {
                $("#" + element.data("dropdown-target")).css("width", (element.outerWidth() + 1) + "px");
                element.css("width", element.outerWidth() + 1);
            }
            else if (element.data("dropdown-options").match(/^(?:.*\s+)?lock\-width(?:\s+.*)?$/gi)) {
                element.css("width", (element.outerWidth() + 1) + "px");
                element.attr("data-owl", element.children("span").html().length);
            }
            else {
                element.css("width", $("#" + element.data("dropdown-target")).outerWidth());
            }
        }

        // List of optional events.
        if (element.data("dropdown-options").match(/^(?:.*\s+)?hover(?:\s+.*)?$/gi) && !window.mobilecheck()) {
            element.data("dropdown-options").match(/^(?:.*\s+)?delayed\-(.*?)(?:\s+.*)?$/gi)
            var delay = RegExp.$1 > 0 ? RegExp.$1 : 0;
            var hover_timeout;
            // Hover
            element.on("mousemove.dropdown", function(e) {
                element = $(this);
                if ($(this).attr('data-dropdown-active') == undefined) {
                    hover_timeout = window.setTimeout(function() {
                        setCurrentHoveredElement(e);

                        if (($("[data-dropdown-target=" + previous_drop_down[previous_drop_down.length - 1] + "]").data("dropdown-parent") == element.data("dropdown-parent")) && element.data("dropdown-parent") && mouse_X_direction == "right") {
                            window.clearTimeout(display_drop_down_timeout);
                            window.clearTimeout(hide_drop_down_timeout);
                            display_drop_down_timeout = window.setTimeout(function() {
                                if (current_hovered_element == $(element).data("dropdown-parent")) {
                                    doDropDownHover(element);
                                    display_drop_down_timeout = "";
                                }
                            }, 200);
                        }
                        else {
                            doDropDownHover(element);
                        }
                    }, delay);
                    hoverImg(element.attr('data-dropdown-target'));
                }
            }).on("mouseleave.dropdown", function(e) {
                window.clearTimeout(hover_timeout);

                setCurrentHoveredElement(e)

                element = $(this);

                drop_down_target_listener = $("#" + element.data("dropdown-target")).on("mouseleave", function() {
                    hideDropDownContent(element, 350);
                });

                hideDropDownContent(element, 350);
            });
        }

        if (element.data("dropdown-options").match(/^(?:.*\s+)?click(?:\s+.*)?$/gi) || element.data("dropdown-options").match(/^(?:.*\s+)?hover(?:\s+.*)?$/gi) && window.mobilecheck()) {
            // Click

            element.on("click.dropdown", function(e) {
                if ($(window).width() < mobile_limit && $(this).data("dropdown-options").match(/^(?:.*\s+)?mobile(?:\s+.*)?$/gi) || !$(this).data("dropdown-options").match(/^(?:.*\s+)?mobile(?:\s+.*)?$/gi)) {
                    allowDefaultFunctionality = false;

                    setTimeout(function() {
                        allowDefaultFunctionality = true;
                    }, 100);

                    e.stopPropagation();

                    if (!($(this).data("dropdown-target") == previous_drop_down[previous_drop_down.length - 1])) {
                        hideAllDropDownChildren($(this).data("dropdown-parent"), 0);
                    }

                    styleDropDownContent($(this), style_ignore_list, false, getReverseDropDown($(this)));

                    if ($("#" + $(this).data("dropdown-target")).css("display") != "none") {
                        doDropDown($("#" + $(this).data("dropdown-target")), false, 0);
                    }
                    else {
                        vals = prepareDropDown($(this), previous_drop_down, drop_down_target_listener, "click");

                        drop_down_target_listener = vals[1];
                        doDropDown($("#" + $(this).data("dropdown-target")), true, 0);
                    }
                    hoverImg(element.attr('data-dropdown-target'));
                }
            });
        }

        if (element.data("dropdown-options").match(/^(?:.*\s+)?focus(?:\s+.*)?$/gi)) {
            $("#" + element.data("dropdown-target")).on("mousedown.dropdown", function(e) {
                e.preventDefault();
            });

            // Focus
            element.on("focus.dropdown", function(e) {
                e.stopPropagation();
                if (!$(this).data("dropdown-parent")) {
                    hideAllDropDown(0);
                }

                if (!($(this).data("dropdown-target") == previous_drop_down[previous_drop_down.length - 1])) {
                    hideAllDropDownChildren($(this).data("dropdown-parent"), 0);
                }

                styleDropDownContent($(this), style_ignore_list, false, getReverseDropDown($(this)));

                doDropDown($("#" + $(this).data("dropdown-target")), true, 0);

                $(this).bind("blur", function(e) {
                    hideAllDropDown(0, true);
                });
            });
        }

        $("#" + element.data("dropdown-target")).css('opacity', '1');
    }
}

function getScrollTop(){
    if(typeof pageYOffset != 'undefined'){
        //most browsers except IE before #9
        return pageYOffset;
    }
    else{
        var B= document.body; //IE 'quirks'
        var D= document.documentElement; //IE with doctype
        D= (D.clientHeight)? D: B;
        return D.scrollTop;
    }
}


function dropDownCheck(speed) {
    if (typeof current_hovered_element != 'object' || ($(current_hovered_element).parents('[bns-ignoredropdownactions]').length == 0 && typeof $(current_hovered_element).attr('bns-ignoredropdownactions') == 'undefined')) {
        hide_all_dropdown = true;

        if (jQuery.inArray(current_hovered_element, previous_drop_down) !== -1) {
            for (var i = previous_drop_down.length - 1; i >= 0; i--) {
                hide_all_dropdown = false;

                if (i > 0 && previous_drop_down[previous_drop_down.length - 1] != current_hovered_element) {
                    hideAllDropDownChildren(previous_drop_down[i - 1], speed);
                }

                break;
            }
        }

        if (hide_all_dropdown && allowDefaultFunctionality) {
            hideAllDropDown(speed);
        }
    }
}

//************************************************************************
//	FUNCTION: getReverseDropDown
//	PURPOSE: Checks to see if the dropdown needs to be reversed or not.
//	PARAMETERS:
//  	1) ELEMENT: The element that you wish to check.
//************************************************************************
function getReverseDropDown(element) {
    var offset = ($(window).height() + $(window).scrollTop()) - parseInt((element.data('dropdown-alternate-parent') ? $("#" + element.data("dropdown-alternate-parent")).offset().top + parseInt(getFullHeight($("#" + element.data("dropdown-alternate-parent")))) : element.offset().top + parseInt(getFullHeight($(element)))) + parseInt(getFullHeight($("#" + element.data("dropdown-target")))))

    return (offset < 0 && !$(element).data("dropdown-options").match(/^(?:.*\s+)?ignore\-reverse\-dropdown(?:\s+.*)?$/gi) ? true : false);
}


//************************************************************************
//	FUNCTION: adjustDropDownIcons
//	PURPOSE: Swaps icons depending on the display of the content.
//	PARAMETERS:
//		1) ELEMENT: The element you wish have icons swapped.
//  	2) DISPLAY: Whether or not the dropdown content is being displayed.
//************************************************************************
function adjustDropDownIcons(element, display) {
    if (element.data("dropdown-icons")) {
        classes = element.data("dropdown-icons").split(" ");
        element.find("." + classes[(display ? 0 : 1)]).addClass(classes[(display ? 1 : 0)]).removeClass(classes[(display ? 0 : 1)]);
    }
}

//************************************************************************
//	FUNCTION: hideAllDropDown
//	PURPOSE: Hides the display for all dropdown elements.
//	PARAMETERS:
//		1) SPEED: The speed in which the dropdown content is displayed at.
//		2) FORCE: Force the dropdown to go away no matter what other conditions exist.
//************************************************************************
function hideAllDropDown(speed, force) {
    for (var i = 0; i < previous_drop_down.length; i++) {
        if ($("[data-dropdown-target=" + previous_drop_down[i] + "]").data("dropdown-options").match(/^(?:.*\s+)?focus(?:\s+.*)?$/gi)) {
            $("[data-dropdown-target=" + previous_drop_down[i] + "]").unbind("blur");
            $("[data-dropdown-target=" + previous_drop_down[i] + "]").trigger("blur");
        }
        if (previous_drop_down[i] != current_hovered_element || force) {
            doDropDown($("#" + previous_drop_down[i]), false, speed);
        }
    }

    previous_drop_down = [];
}

//************************************************************************
//	FUNCTION: hideAllDropDownChildren
//	PURPOSE: Hides the display for all of the children elements.
//	PARAMETERS:
//		1) PARENT_ID: The ID of the children you want to hide.
//		2) SPEED: The speed in which the dropdown content is displayed at.
//************************************************************************
function hideAllDropDownChildren(parent_id, speed) {
    $("[data-dropdown-parent=" + parent_id + "]").each(function() {
        if ($("#" + $(this).data("dropdown-target")).css("display") != "none") {
            doDropDown($("#" + $(this).data("dropdown-target")), false, speed);
        }
    });
}

function toggleHoverClass(element, append) {
    class_list = element.attr("class") ? element.attr("class") : "";
    if (class_list.match(/(?:.*|^)?dropdown\-hover\-(.*?)(?:\s|$)/gi)) {
        append ? element.addClass("hover-" + RegExp.$1) : element.removeClass("hover-" + RegExp.$1);
    }
}


function displayShadowBox(top_parent) {
    if (top_parent.data("dropdown-options").match(/^(?:.*\s+)?shadowed\((.*)\)(?:\s+.*)?$/gi) && getIEVersion() != 8) {
        $("#" + RegExp.$1).addClass("drop-down-shadow-box-content");
        $(".drop-down-shadow-box").css('display', 'block');
    }
}

function hideShadowBox(top_parent) {
    lock_shadow = false;

    if (typeof current_hovered_element == 'string' && $("[data-dropdown-target=" + current_hovered_element + "]").data("dropdown-options").match(/^(?:.*\s+)?hover(?:\s+.*)?$/gi)) {
        if (($("[data-dropdown-target=" + current_hovered_element + "]").data("dropdown-options")) && ($("[data-dropdown-target=" + current_hovered_element + "]").data("dropdown-options").match(/^(?:.*\s+)?shadowed\((.*)\)(?:\s+.*)?$/gi) && $(".drop-down-shadow-box").css("display") == "block")) {
            lock_shadow = true;
        }
    }

    if (top_parent.data("dropdown-options").match(/^(?:.*\s+)?shadowed\((.*)\)(?:\s+.*)?$/gi) && !lock_shadow) {
        $("#" + RegExp.$1).removeClass("drop-down-shadow-box-content");
        $(".drop-down-shadow-box").css("display", "none");
    }
}


//************************************************************************
//	FUNCTION: doDropDown
//	PURPOSE: Displays or Hides the dropdown content.
//	PARAMETERS:
//		1) ELEMENT: The tag you wish to show or hide.
//  	2) DISPLAY: Whether or not the dropdown content is being displayed.
//		3) SPEED: The speed in which the dropdown content is displayed at.
//************************************************************************
function doDropDown(element, display, speed) {
    if (display && element.css("display") == "none") {
        $("[data-dropdown-target='" + element.attr("id") + "']").attr('data-dropdown-active', '');
        displayShadowBox($("[data-dropdown-target='" + element.attr("id") + "']"));

        element.slideDown(speed);
        toggleHoverClass($("[data-dropdown-target=" + element.attr("id") + "]"), true);
        if (jQuery.inArray(element.attr("id"), previous_drop_down) == -1) {
            previous_drop_down.push(element.attr("id"));
        }
    }
    else if (!display && element.css("display") != "none") {
        $("[data-dropdown-target='" + element.attr("id") + "']").removeAttr('data-dropdown-active');
        hideShadowBox($("[data-dropdown-target='" + element.attr("id") + "']"));

        element.slideUp(speed);
        toggleHoverClass($("[data-dropdown-target=" + element.attr("id") + "]"), false);
        previous_drop_down.splice(jQuery.inArray(element.attr("id"), previous_drop_down), 1);

        // Clear all of the children dropdowns.
        hideAllDropDownChildren(element.attr("id"), speed);
    }

    adjustDropDownIcons($("[data-dropdown-target=" + element.attr("id") + "]"), display);
}


//************************************************************************
//	FUNCTION: prepareDropDown
//	PURPOSE: Prepares the drop down functionality.
//	PARAMETERS:
//  	1) ELEMENT: The tag that you are clicking.
//		2) PREVIOUS_ELEMENTS: The tags that were previously active.
//		3) DROP_DOWN_TARGET_LISTENER: Listener for old drop down.
//  	   If we start a new dropdown, we must kill the old one.
//		4) EVENT: Required for unbinding the event that existed in the
//		   drop_down_target_listener variable.
//************************************************************************
function prepareDropDown(element, previous_elements, drop_down_target_listener, event) {
    $("#" + previous_drop_down[previous_drop_down.length - 2]).stop(true, true).animate({});

    if (drop_down_target_listener) {
        drop_down_target_listener.unbind(event);
    }

    if (element.data("dropdown-target") != previous_elements.length[previous_elements.length - 2] && jQuery.inArray(element.data("dropdown-parent"), previous_elements) === -1) {
        if (jQuery.inArray(current_hovered_element, previous_drop_down) === -1) {
            hideAllDropDown(0);
        }
        else {
            hideAllDropDownChildren(current_hovered_element, 0);
        }
    }

    return [element.data("dropdown-target"), drop_down_target_listener];
}

//************************************************************************
//	FUNCTION: hideDropDownContent
//	PURPOSE: Hides the drop down content box.
//	PARAMETERS:
//  	1) ELEMENT: The tag that you wish to hide.
//		2) SPEED: The speed for the drop down timeout.
//************************************************************************
function hideDropDownContent(element, speed) {
    hide_drop_down_timeout = window.setTimeout(function() {
        drop_down_target_listener.unbind("mouseleave");
        dropDownCheck(0);
    }, speed);

}


//************************************************************************
//	FUNCTION: hasSpecialPositioning
//	PURPOSE: Checks to see which side the content should be displayed on
//			 from its parent.
//	PARAMETERS:
//  	1) ELEMENT: The element that you wish to check.
//************************************************************************
function hasSpecialPositioning(element) {
    if (element.data("dropdown-options").match(/^(?:.*\s+)?position\-((?:right|left|top|bottom))(?:\s+.*)?$/gi)) {
        return RegExp.$1;
    }
    else {
        return false;
    }
}

//************************************************************************
//	FUNCTION: styleDropDownContent
//	PURPOSE: Dynamically styles the drop down content box and weeds
//			 out what doesn't need to be styled after load.
//	PARAMETERS:
//  	1) ELEMENT: The tag that you wish to style.
//		2) STYLE_IGNORE_LIST: Updated list of styles to ignore for each element.
//		3) DO_MATCH: Checks to see if there was a value already set.
//		4) REVERSE_DROPDOWN:
//************************************************************************
function styleDropDownContent(element, style_ignore_list, do_match, reverse_dropdown) {
    element_to_style_from = $(element).data("dropdown-alternate-parent") ? $("#" + $(element).data("dropdown-alternate-parent")) : element;

    default_left = element_to_style_from.offset().left;
    default_top = (reverse_dropdown ? $(window).height() - parseInt(element_to_style_from.offset().top) : parseInt(element_to_style_from.offset().top) + parseInt(element_to_style_from.outerHeight())) - ($(element).css('border-width') ? parseInt($(element).css('border-width')) : 0);

    // Position for center
    if (element_to_style_from.data("dropdown-style") && element_to_style_from.data("dropdown-style").match(/^(?:.*\s+)?center(?:\s+.*)?$/gi)) {
        default_left = default_left - parseInt(($("#" + element_to_style_from.data("dropdown-target")).width() - element_to_style_from.width()) / 2);
    }

    if (hasSpecialPositioning(element) == "right") {
        default_left = parseInt(element_to_style_from.offset().left) + parseInt(element_to_style_from.outerWidth());
        if (element_to_style_from.data("dropdown-parent") && element_to_style_from.data("dropdown-options").match(/^(?:.*\s+)?use\-parent\-top(?:\s+.*)?$/gi)) {
            default_top = parseInt($("#" + element_to_style_from.data("dropdown-parent")).offset().top);
        }
        else {
            default_top = parseInt(element_to_style_from.offset().top);
        }
    }

    if ($("#" + element.data("dropdown-target")).css("max-width")) {
        default_left += element_to_style_from.outerWidth() > parseInt($("#" + element.data("dropdown-target")).css("max-width")) ? parseInt((element_to_style_from.outerWidth() - parseInt($("#" + element.data("dropdown-target")).css("max-width"))) / 2) : 0;
    }

    reverse_horizontal = $(element).data("dropdown-options").match(/^(?:.*\s+)?reverse\-horizontal(?:\s+.*)?$/gi) ? true : false;
    
    var styles = [
        {
            style_name: 'left',
            match: "px",
            current_value: $("#" + element.data("dropdown-target")).css('left'),
            default_value: (reverse_horizontal ? default_left - getFullWidth($('#' + $(element).attr('data-dropdown-target'))) + getFullWidth($(element_to_style_from)) : default_left)
        },
        {
            style_name: "top",
            match: "px",
            current_value: $("#" + element.data("dropdown-target")).css("top"),
            default_value: default_top
        },
        {
            style_name: "width",
            match: "^(?:.*[0-9][0-9]|[1-9])px$",
            current_value: parseInt($("#" + element.data("dropdown-target")).css("width")) - (parseInt(Math.ceil($("#" + element.data("dropdown-target")).css("border-left-width").replace("px", ""))) * 2) + "px",
            default_value: element_to_style_from.outerWidth()
        }
    ];

    var skip_ignore = false;

    for (var i = 0; i < style_ignore_list.length; i++) {
        if (style_ignore_list[i]['target_name'] == element.data('dropdown-target')) {
            skip_ignore = true;
        }
    }

    for (var i = 0; i < styles.length; i++) {
        if (do_match) {
            re = new RegExp(styles[i]["match"], "i");
            if (styles[i]["current_value"].match(re)) {
                if (!skip_ignore) {
                    style_ignore_list.push(
                        {
                            target_name: element.data("dropdown-target"),
                            style_name: styles[i]["style_name"]
                        }
                    );
                }
            }
        }
    }

    for (var i = 0; i < styles.length; i++) {
        if (indexOfStyle(style_ignore_list, element.data("dropdown-target"), styles[i]["style_name"]) < 0) {
            if (reverse_dropdown && styles[i]["style_name"] == "top" && !hasSpecialPositioning(element)) { // Display on top
                $("#" + element.data("dropdown-target")).css({
                    "top":"auto",
                    "bottom":styles[i]["default_value"] + "px"
                });
            }
            else if (!reverse_dropdown && styles[i]["style_name"] == "top") { // Display on bottom
                $("#" + element.data("dropdown-target")).css({
                    "top":styles[i]["default_value"] + "px",
                    "bottom":"auto"
                });
            }
            else {
                $("#" + element.data("dropdown-target")).css(styles[i]["style_name"], styles[i]["default_value"])
            }
        }
    }
}

//************************************************************************
//	FUNCTION: indexOfObject
//	PURPOSE: Find out where the object exists within the array
//	PARAMETERS:
//  	1) LIST: Array list that you want to find the index of.
//		2) TARGET_NAME: The name of the element that you wish to examine.
//		3) STYLE_NAME: The name of the style that you wish to check for.
//************************************************************************
function indexOfStyle(list, target_name, style_name) {
    for(var i = 0; i < list.length; i++) {
        if (list[i]["target_name"] === target_name && list[i]["style_name"] === style_name) {
            return i;
        }
    }

    return -1;
}

//************************************************************************
//	FUNCTION: adjustDropdownPosition
//	PURPOSE: Sets the new position of every dropdown element
//************************************************************************
function setDropdownPosition() {
    $("[data-dropdown-target^=dropdown-][data-dropdown-active]").each(function() {
        // Required to remember to scroll position if it had one.
        target_scroll_position = $("#" + $(this).data("dropdown-target")).scrollTop();

        styleDropDownContent($(this), style_ignore_list, false, getReverseDropDown($(this)));

        $("#" + $(this).data("dropdown-target")).scrollTop(target_scroll_position);
    });
}

function doDropDownHover(element) {
    hideAllDropDownChildren(element.data("dropdown-parent"), 0);

    styleDropDownContent(element, style_ignore_list, false, getReverseDropDown(element));
    window.clearTimeout(hide_drop_down_timeout);

    vals = prepareDropDown(element, previous_drop_down, drop_down_target_listener, "mouseleave");

    drop_down_target_listener = vals[1];
    doDropDown($("#" + element.data("dropdown-target")), true, 0);
}


function setCurrentHoveredElement(e) {
    if ($(e.target).data("dropdown-target")) {
        current_hovered_element = $(e.target).data("dropdown-target");
    }
    else if ($(e.target).closest("[data-dropdown-target]").data("dropdown-target")) {
        current_hovered_element = $(e.target).closest("[data-dropdown-target]").data("dropdown-target");
    }
    else if (typeof $(e.target).closest("[id^=dropdown]").attr("id") != 'undefined') {
        current_hovered_element = $(e.target).closest("[id^=dropdown]").attr("id");
    }
    else {
        current_hovered_element = $(e.target);
    }
}

$(document).ready(function() {
    $("body").append('<div class="drop-down-shadow-box"></div>');

    $(window).on("mousemove.dropdown", function(e) {

        setCurrentHoveredElement(e);

        if (e.pageY < mouse_Y_axis) {
            mouse_Y_direction = "up";
        }
        else if (e.pageY > mouse_Y_axis) {
            mouse_Y_direction = "down";
        }
        else {
            mouse_Y_direction = "";
        }

        mouse_Y_axis = e.pageY;

        if (e.pageX < mouse_X_axis) {
            mouse_X_direction = "left";
        }
        else if (e.pageX > mouse_X_axis) {
            mouse_X_direction = "right";
        }
        else {
            mouse_X_direction = "";
        }

        mouse_X_axis = e.pageX;
    });

    $(window).on("resize.dropdown scroll.dropdown", function(e) {
        if (!ignoreIE()) {
            waitForFinalEvent(function(){
                if (!(e.type == 'scroll' && typeof $('[data-dropdown-target="' + previous_drop_down[previous_drop_down.length - 1] + '"]').attr('data-dropdown-options') !== 'undefined' && $('[data-dropdown-target="' + previous_drop_down[previous_drop_down.length - 1] + '"]').attr('data-dropdown-options').match('ignore-scroll') != null)) {
                    setDropdownPosition();

                    if($(window).width() >= mobile_limit && previous_drop_down.length > 0) {
                        $("[data-dropdown-options*=mobile]").each(function() {
                            doDropDown($("#" + $(this).data("dropdown-target")), false, 0);
                        });
                    }
                }
            }, 200, "dropdown_Id");
        }
    })

    $("html, .drop-down-shadow-box").on("click.dropdown", function() {
        // When clicking on an area that has nothing to do with the dropdown, we will hide all dropdown content.
        dropDownCheck(0);
    });

    $("[id^=dropdown-], [id^=dropdown-] input").on("mouseenter.dropdown", function() {
        // Prevent the dropdown from closing itself when leaving the dropdown trigger
        window.clearTimeout(hide_drop_down_timeout);
    });

    $("[data-dropdown-target^=dropdown-]").each(function() {
        createDropDownEvent($(this));
    });
});
