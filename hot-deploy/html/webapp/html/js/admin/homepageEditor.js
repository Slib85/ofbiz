function checkImageType() {
	if ($('select[name="imageType"] option:selected').val() == 'timed') {
		$('.jqs-submit span').html('ADD');
		$('input[name="fromDate"]').removeClass('jqs-validateExists').addClass('jqs-validateExists');
		$('input[name="thruDate"]').removeClass('jqs-validateExists').addClass('jqs-validateExists');
		$('.jqs-timedInfo').show();
	} else {
		$('.jqs-submit span').html('UPDATE');
		$('input[name="fromDate"]').removeClass('jqs-validateExists').val('');
		$('input[name="thruDate"]').removeClass('jqs-validateExists').val('');
		$('.jqs-timedInfo').hide();
	}
}
$(document).ready(function() {
	checkImageType();
});
$('select[name="imageType"]').on('change', function() {
	checkImageType();
});
$('.jqs-submit').on('click', function() {
	var valid = true;
	$('.jqs-validateExists').each(function() {
		if ($(this).val() == '' && $(this).css('display') != 'none') {
			valid = false;
		}
	});
	if (valid) {
		$.ajax({
			type: "POST",
			url: '/admin/control/doHomepageImageInsertsOrUpdates',
			data: $('#homepageImageEditor').serialize(),
			dataType: 'json',
			cache: false,
			async: false
		}).done(function(data) {
			if (data.success) {
				// do success response
				alert("Successfully Updated!");
			} else {
				//TODO throw error
				alert('There was an issue processing your form, please try again or contact your best buddies (The Dev Team).');
			}
		});
	} else {
		alert('Please make sure all required fields have data.');
	}
	return false;
});
var currentFgDesktop = '';
var currentBgDesktop = '';
var currentFgMobile = '';

// Set current images displaying on homepage as variables once iframe is loaded
function findOriginals() {
	if (currentFgDesktop === '' && currentBgDesktop === '' && currentFgMobile === '') {
		var preview = $(".preview-frame").contents().find(".banner");
		currentFgDesktop = preview.find(".desktop-only img").attr("src");
		currentBgDesktop = preview.find(".desktop-only").css('background');
		currentFgMobile = preview.find(".mobile-tablet-only img").attr("src");
		if (currentFgDesktop !== '' && currentBgDesktop !== '' && currentFgMobile !== '') {
			$(".panel .form-control").attr("disabled", false);
		}
	}
}
$("iframe").on("load", function() {
	findOriginals();
});

// Handles resizing of review windows
function editorPreview() {
	$(".preview-frame").each(function() {
		var sibWrapper = $(this).siblings(".wrapper");
		var desktopWidth = sibWrapper.css("max-width").replace("px", "");
		var aspectRatio = sibWrapper.height() / sibWrapper.width();
		var availWidth = $(".preview-frame-col").width();
		var scale = availWidth / desktopWidth;
		var multiply = 100 / scale;
		if (scale < 1) {
			var cssCorrection = "scale(" + scale + "," + scale + ")";
		} else {
			var cssCorrection = "scale(1, 1)";
		}
		$(".wrapper").hide();
		$(this).show();
		$(this).css("width", multiply + "%").css("-moz-transform", cssCorrection).css("-webkit-transform", cssCorrection).css("-o-transform", cssCorrection).css("-ms-transform", cssCorrection).css("transform", cssCorrection);
		var heighter = $(this).width() * aspectRatio;
		$(this).css("height", heighter + "px");
		$(this).parents(".preview-frame-col").css("max-height", (heighter * scale) + "px");
	});
	// Remove Olark and Modals on iFrame
	$(".preview-frame").contents().find("div[data-olark], .reveal-modal, .reveal-modal-bg").remove();
}

// Center iFrame if fullscreen is in effect
function iFrameCenter() {
	$(".preview-frame-col").each(function() {
		if ($(this).hasClass("preview-frame-col-fullscreen")) {
			var frameHeight = $(this).find("iframe").height();
			var containHeight = $(this).height();
			var margin = (containHeight - frameHeight) / 2;
			$(this).find("iframe").css("margin-top", margin + "px");
		} else {
			$(this).find("iframe").css("margin-top", "");
		}
	});
}

// Run function on load, resize and periodically
$(window).on("load resize", function() {
	editorPreview();
	iFrameCenter();
});
window.setInterval(function() {
	editorPreview();
	iFrameCenter();
	findOriginals();
	// Trigger a change to the inputs just in case
	$(".preview-form-fg-desktop").trigger('change');
}, 2000);

// Creates a test image from URL to ensure that image is valid
function checkImgAndApply(url, el, targetClass, fallback) {
	$("#img-validator").remove();
	var prevStart = $(el).parents(".panel").find(".preview-frame").contents();
	if (el.is('.preview-form-bg-desktop')) {
		var previewImg = prevStart.find(".banner-image");
	} else {
		var previewImg = prevStart.find(".banner-image." + targetClass + " img");
	}
	if (url !== '') {
		$("body").append("<img id='img-validator' src='" + url + "'>");
		var image = $('#img-validator');
		$(image).on('load', function() {
			console.log($(this).prop('naturalHeight'));
			if ($(this).prop('naturalHeight') > 0) {
				if (el.is('.preview-form-bg-desktop')) {
					previewImg.css("background-image", "url(" + url + ")");
					previewImg.css("color", "green");
				} else {
					previewImg.attr("src", url);
				}
				el.siblings(".validity-alert").remove();
			}
		});
		$(image).on('error', function() {
			if (el.is('.preview-form-bg-desktop')) {
				previewImg.css("background", fallback);
			} else {
				previewImg.attr("src", fallback);
			}
			el.siblings(".validity-alert").remove();
			el.after("<span class='validity-alert'>This does not appear to be a valid URL</span>");
		});
	} else {
		if (el.is('.preview-form-bg-desktop')) {
			previewImg.css("background", fallback);
		} else {
			previewImg.attr("src", fallback);
		}
		el.siblings(".validity-alert").remove();
	}
}

// Handles updating the iFrames and fallback behavior for foreground images
$(".preview-form-fg-desktop, .preview-form-fg-mobile").on('input change', function() {
	var el = $(this);
	if (el.is('.preview-form-fg-desktop')) {
		var format = $('select[name=foregroundImageFormat]').val();
	} else {
		var format = $('select[name=mobileImageFormat]').val();
	}
	var imgSrc = '';
	if (el.val() !== '') {
		imgSrc = '//actionenvelope.scene7.com/is/image/ActionEnvelope/' + el.val() + '?wid=1024&fmt=' + format + '&ts=6';
	}
	if ($(this).is(".preview-form-fg-desktop")) {
		targetClass = "desktop-only";
		fallback = currentFgDesktop;
	} else {
		targetClass = "mobile-tablet-only";
		fallback = currentFgMobile;
	}
	checkImgAndApply(imgSrc, el, targetClass, fallback);
});

// Handles updating the iFrames and fallback behavior for background image
$(".preview-form-bg-desktop").on('input change', function() {
	var el = $(this);
	var imgSrc = '';
	if (el.val() !== '') {
		imgSrc = '//actionenvelope.scene7.com/is/image/ActionEnvelope/' + el.val() + '?wid=1024&fmt=jpg&qlt=70&ts=6';
	}
	checkImgAndApply(imgSrc, el, '', currentBgDesktop);
});

// Trigger change event to replace image src
$('select[name=foregroundImageFormat], select[name=mobileImageFormat]').bind('propertychange change click keyup input paste', function() {
	$(".preview-form-fg-desktop, .preview-form-fg-mobile").trigger('change');
});

// Fullscreen Mode
$(".wb-fullscreen").click(function() {
	$("html, body").toggleClass("scrolljack");
	$(this).siblings(".preview-frame-col").toggleClass("preview-frame-col-fullscreen");
	editorPreview();
	iFrameCenter();
});