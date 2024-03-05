/** global.js **/
if(typeof(websiteId) != 'undefined') {
    $.removeCookie('JSESSIONID', {"domain" : '.envelopes.com'});
    $.removeCookie('ShoppingCart', {"domain" : '.envelopes.com'});
}

minimum_desktop_width = 1024;
minimum_tablet_width = 768;
var window_height = 0;
var window_width = 0;

var monthNames = [ 'January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December' ];
var weekDays = [ 'Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat' ];

navigator.sayswho = (function(){
    var ua= navigator.userAgent, tem, 
    M= ua.match(/(opera|chrome|safari|firefox|msie|trident(?=\/))\/?\s*(\d+)/i) || [];
    if(/trident/i.test(M[1])){
        tem=  /\brv[ :]+(\d+)/g.exec(ua) || [];
        return 'IE '+(tem[1] || '');
    }
    if(M[1]=== 'Chrome'){
        tem= ua.match(/\b(OPR|Edge)\/(\d+)/);
        if(tem!= null) return tem.slice(1).join(' ').replace('OPR', 'Opera');
    }
    M= M[2]? [M[1], M[2]]: [navigator.appName, navigator.appVersion, '-?'];
    if((tem= ua.match(/version\/(\d+)/i))!= null) M.splice(1, 1, tem[1]);
    return M.join(' ');
});

window.mobilecheck = function() {
	var check = false;
	(function(a){if(/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows ce|xda|xiino/i.test(a)||/1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(a.substr(0,4)))check = true})(navigator.userAgent||navigator.vendor||window.opera);
	return check;
};

window.botcheck = function() {
	var check = false;
	(function(a){if(/googlebot\/|Googlebot\-Mobile|Googlebot\-Image|Google favicon|Mediapartners\-Google|bingbot|slurp|java|wget|curl|Commons\-HttpClient|Python\-urllib|libwww|httpunit|nutch|phpcrawl|msnbot|jyxobot|FAST\-WebCrawler|FAST Enterprise Crawler|biglotron|teoma|convera|seekbot|gigablast|exabot|ngbot|ia_archiver|GingerCrawler|webmon |httrack|webcrawler|grub.org|UsineNouvelleCrawler|antibot|netresearchserver|speedy|fluffy|bibnum.bnf|findlink|msrbot|panscient|yacybot|AISearchBot|IOI|ips\-agent|tagoobot|MJ12bot|dotbot|woriobot|yanga|buzzbot|mlbot|yandexbot|purebot|Linguee Bot|Voyager|CyberPatrol|voilabot|baiduspider|citeseerxbot|spbot|twengabot|postrank|turnitinbot|scribdbot|page2rss|sitebot|linkdex|Adidxbot|blekkobot|ezooms|dotbot|Mail.RU_Bot|discobot|heritrix|findthatfile|europarchive.org|NerdByNature.Bot|sistrix crawler|ahrefsbot|Aboundex|domaincrawler|wbsearchbot|summify|ccbot|edisterbot|seznambot|ec2linkfinder|gslfbot|aihitbot|intelium_bot|facebookexternalhit|yeti|RetrevoPageAnalyzer|lb\-spider|sogou|lssbot|careerbot|wotbox|wocbot|ichiro|DuckDuckBot|lssrocketcrawler|drupact|webcompanycrawler|acoonbot|openindexspider|gnam gnam spider|web\-archive\-net.com.bot|backlinkcrawler|coccoc|integromedb|content crawler spider|toplistbot|seokicks\-robot|it2media\-domain\-crawler|ip\-web\-crawler.com|siteexplorer.info|elisabot|proximic|changedetection|blexbot|arabot|WeSEE:Search|niki\-bot|CrystalSemanticsBot|rogerbot|360Spider|psbot|InterfaxScanBot|Lipperhey SEO Service|CC Metadata Scaper|g00g1e.net|GrapeshotCrawler|urlappendbot|brainobot|fr\-crawler|binlar|SimpleCrawler|Livelapbot|Twitterbot|cXensebot|smtbot|bnf.fr_bot|A6\-Indexer|ADmantX|Facebot|Twitterbot|OrangeBot|memorybot|AdvBot|MegaIndex|SemanticScholarBot|ltx71|nerdybot|xovibot|BUbiNG|Qwantify|archive.org_bot|Applebot|TweetmemeBot|crawler4j|findxbot|SemrushBot|yoozBot|lipperhey|y!j\-asr|Domain Re\-Animator Bot|AddThis/i.test(a))check = true})(navigator.userAgent||navigator.vendor||window.opera);
	return check;
};

currentWindowScrollTop = null;

function toTitleCase(str) {
    return str.replace(/\w\S*/g, function(txt){
        return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();
    });
}

var localStorageEnabled = (function (){
    try {
        var testKey = 'test', storage = window.localStorage;
        storage.setItem(testKey, '1');
        storage.removeItem(testKey);
        return true;
    } catch (error) {
        return false;
    }
})();

function bindEnvMessageBoxEvents() {
	$('.jqs-closeEnvMessageBox').off('click.closeEnvMessageBox').on('click.closeEnvMessageBox', function() {
		$(this).closest('.envMessageBox').hide();
		$('.envMessageBoxShadowedBackground').hide();
	});
}

function envConfirm(headerText, bodyText, confirmText, denyText, callback) {
	if (typeof bodyText != 'undefined' && bodyText != null && typeof callback == 'function') {
		var options = $('<div />').addClass('row text-center margin-top-xxs').append(
			$('<div />').addClass('columns small-12 medium-6 large-6').append(
				$('<div />').addClass('jqs-closeEnvMessageBox jqs-envMessageBoxYes button button-cta no-margin padding-xxs').html(typeof confirmText != 'undefined' && confirmText != null ? confirmText : 'Yes')
			)
		).append(
			$('<div />').addClass('columns small-12 medium-6 large-6').append(
				$('<div />').addClass('jqs-closeEnvMessageBox jqs-envMessageBoxNo button button-cta no-margin padding-xxs').html(typeof denyText != 'undefined' && denyText != null ? denyText : 'No')
			)
		);

		var body = $('<div />').addClass('envMessageBoxBody').append(
			$('<div />').html(bodyText)
		).append(options);

		var header = $('<div />').addClass('envMessageBoxHeader').html(typeof headerText != 'undefined' && headerText != null ? headerText : 'Confirm?');

		$('body').append(
			$('<div />').addClass('envMessageBoxShadowedBackground')
		);
		$('body').append(
			$('<div />').addClass('envMessageBox').append(header).append(body)
		);

		bindEnvMessageBoxEvents();

		$('.jqs-envMessageBoxYes').on('click', function() { callback(true); });
		$('.jqs-envMessageBoxNo').on('click', function() { callback(false);	});
	}
}

function createScrollableLock(element) {
	if (typeof window.mobilecheck != 'undefined' && !window.mobilecheck()) {
		$(typeof element !== 'undefined' ? element : '.jqs-scrollable').off('mouseenter.scrollableLock').on('mouseenter.scrollableLock', function() {
			currentWindowScrollTop = {
				'element': this,
				'scrollTop': $(window).scrollTop()
			};
		}).off('mouseleave.scrollableLock').on('mouseleave.scrollableLock', function() {
			currentWindowScrollTop = null;
		});
	}
}

$(window).on('scroll', function(e) {
    if (currentWindowScrollTop !== null && $(currentWindowScrollTop.element).is(":hover")) {
        $(this).scrollTop(currentWindowScrollTop.scrollTop);
    }
});

function formatCurrency(n) {
	return ('$' + numberWithCommas((Math.round(n * 100) / 100).toFixed(2))).replace(/(^.*?\.{2}).*/g, '$1');
}

function numberWithCommas(n) {
	return (typeof n != 'undefined' ? n.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") : 0);
}

function isColorAllowed(hex1, hex2) {
	if (typeof hex1 != 'undefined' && typeof hex2 != 'undefined') {
		var difference = Math.abs(
			getColorContrast(hex1) - getColorContrast(hex2)
		);

		return difference > 75;
	}

	return false;
}

function getColorContrast(hex) {
	hex = hex.replace('#', '');
	return (((parseInt(hex.substr(0,2),16) * 299) + (parseInt(hex.substr(2,2),16) * 587) + (parseInt(hex.substr(4,2),16) * 114)) / 1000);
}

function formatFileSize(bytes) {
	if(typeof bytes !== 'number') { return ''; }
	if(bytes >= 1000000000) { return (bytes / 1000000000).toFixed(2) + ' GB'; }
	if(bytes >= 1000000) { return (bytes / 1000000).toFixed(2) + ' MB'; }
	return (bytes / 1000).toFixed(2) + ' KB';
}

//this is not meant for security, just to generate a random number
function randomToken() {
	return Math.random().toString(36).substr(2) + Math.random().toString(36).substr(2);
}

function getFutureDate(numberOfDays, formal) {
	var newDate = new Date();

	for(i = 0; i < numberOfDays; i++) {
		newDate.setDate(newDate.getDate() + 1);
		switch(newDate.getDay()) {
			case 0:
				newDate.setDate(newDate.getDate() + 1);
				break;

			case 6:
				newDate.setDate(newDate.getDate() + 2);
				break;
		}
	}

	if(formal) {
		return weekDays[newDate.getDay()] + ', ' + monthNames[newDate.getMonth()] + ' ' + newDate.getDate();
	} else {
		return (1900 + newDate.getYear()) + '-' + (1 + newDate.getMonth()) + '-' + newDate.getDate();
	}
}

function getFormatedDate(dateStr, formal) {
	var newDate = new Date(dateStr);
	if(formal) {
		return monthNames[newDate.getMonth()] + ' ' + newDate.getDate() + ', ' + (1900 + newDate.getYear());
	} else {
		return (1900 + newDate.getYear()) + '-' + (1 + newDate.getMonth()) + '-' + newDate.getDate();
	}
}

function getCommentDateFormat(dateStr) {
	var date = new Date(dateStr);
	var year = date.getFullYear().toString().substr(-2);
	var month = date.getMonth() + 1;
	var day = date.getDate();
	var hours = date.getHours();
	var ampm = hours >= 12 ? 'PM' : 'AM';
	hours = hours % 12;
	hours = hours ? hours : 12;
	var minutes = "0" + date.getMinutes();
	var formattedTime = month + '/' + day + '/' + year + ' ' + hours + ':' + minutes.substr(-2) + ' ' + ampm;
	return formattedTime;
}


function ignoreIE() {
	// We are currently ignoring javascript for only IE8 and below when needed.
	if(getIEVersion() >= 8 || getIEVersion() == 0) {
		return false;
	} else {
		return true;
	}
}

function getFullHeight(element) {
	var margin_top;
	var margin_bottom;

	if (element.css("margin-top")) {
		margin_top = (element.css("margin-top").match("px") ? parseInt(element.css("margin-top")) : 0);
	}

	if (element.css("margin-bottom")) {
		margin_bottom = (element.css("margin-bottom").match("px") ? parseInt(element.css("margin-bottom")) : 0);
	}

	return element.outerHeight() + margin_top + margin_bottom;
}

function getFullWidth(element) {
	var margin_right;
	var margin_left;

	if (element.css("margin-right")) {
		margin_right = (element.css("margin-right").match("px") ? parseInt(element.css("margin-right")) : 0);
	}

	if (element.css("margin-left")) {
		margin_left = (element.css("margin-left").match("px") ? parseInt(element.css("margin-left")) : 0);
	}

	return element.outerWidth() + margin_left + margin_right;
}

function getIEVersion() {
	var ua = window.navigator.userAgent;
	var msie = ua.indexOf("MSIE ");

	if(msie > 0) {
		// Return Version of IE
		return parseInt(ua.substring(msie + 5, ua.indexOf(".", msie)));
	} else {
		return 0;
	}
}

function removeArrayValue(value, array) {
	var index = $.inArray(value, array);
	if(index > -1) {
		array.splice(index, 1);
	}
	return array;
}

var waitForFinalEvent = (function () {
	var timers = {};
	return function (callback, ms, uniqueId) {
		if(!uniqueId) {
			uniqueId = "Don't call this twice without a uniqueId.";
		}
		if(timers[uniqueId]) {
			clearTimeout (timers[uniqueId]);
		}
		timers[uniqueId] = setTimeout(callback, ms);
	};
})();

function getParameterNames(func) {
	var STRIP_COMMENTS = /((\/\/.*$)|(\/\*[\s\S]*?\*\/))/mg;
	var ARGUMENT_NAMES = /([^\s,]+)/g;
	var fnStr = func.toString().replace(STRIP_COMMENTS, '');
	var result = fnStr.slice(fnStr.indexOf('(')+1, fnStr.indexOf(')')).match(ARGUMENT_NAMES);
	if(result === null) {
		result = [];
	}
	return result;
}

function log(message, debug) {
	if(debug) { window.console && console.log(message); }
}

function getUrlParameters(parameter, staticURL, decode) {
	var currLocation = (staticURL.length)? staticURL : window.location.search,
		parArr = (typeof currLocation.split("?")[1] != 'undefined') ? currLocation.split("?")[1].split("&") : [],
		returnBool = true;

	for(var i = 0; i < parArr.length; i++){
		parr = parArr[i].split("=");
		if(parr[0] == parameter){
			return (decode) ? decodeURIComponent(parr[1]) : parr[1];
		}else{
			returnBool = false;
		}
	}

	if(!returnBool) return false;
}

function validateEmailAddress(email) {
	var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return re.test(email);
}

function setPersistentCartId() {
    if(typeof(websiteId) != 'undefined') {
        if (typeof $.cookie("__SC_Data") !== 'undefined') {
            var pCartId = $.cookie("__SC_Data").match(/.*?\"id\".*?\:\"(.*?)\"/)[1];
            $('.persistentCartId').html('Your Shopping Cart ID: ' + pCartId);
        }
    }
}

var InspectletTagging = {
	tagSession: function(action, label) {
		if(typeof __insp !== 'undefined') {
			var inspMap = {};
			inspMap[action] = label;
			__insp.push(['tagSession', inspMap]);
		}
	}
};

function deferImg() {
	$('.jqs-defer-img').each(function(i) {
		$(this).attr('src', $(this).attr('data-src'));
	});
}

function hoverImg(id) {
	$('#' + id).find('.jqs-hover-img').each(function(i) {
		$(this).attr('src', $(this).attr('data-src'));
		$(this).removeClass('jqs-hover-img');
	});
}

function addLoadEvent(func) {
	var oldonload = window.onload;
	if (typeof window.onload != 'function') {
		window.onload = func;
	} else {
		window.onload = function() {
			if (oldonload) {
				oldonload();
			}
			func();
		}
	}
}

// Resize text to two lines with ellipsis
function twoLineFontResize(element, lineHeight, fontSizeLimit) {
    $.each($(element), function() {
        var counter = 0;
        while (getFullHeight($(this)) > lineHeight && parseInt($(this).css('font-size')) > fontSizeLimit) {
            counter++;
            $(this).css('font-size', (parseInt($(this).css('font-size')) - 1) + 'px');
            if(counter > 100) {
                break;
            }
        }
        if (getFullHeight($(this)) > lineHeight && parseInt($(this).css('font-size')) == fontSizeLimit) {
            $(this).css({
                'overflow': 'hidden',
                'text-overflow': 'ellipsis',
                'display': '-webkit-box',
                '-webkit-box-orient': 'vertical',
                '-webkit-line-clamp': '2',
                'height': '35px'
            });
        }
    });
};

function certonaCallback(websiteId, data, currentView) {
	if(websiteId) {
		if(websiteId == 'envelopes') {
			certonaCallbackEnv(data, currentView)
		} else {
			certonaCallbackFolders(data, currentView)
		}
	}
}

// Envelopes Certona
function certonaCallbackEnv(data, currentView) {
	if(typeof data != 'undefined' && typeof data.resonance != 'undefined' && typeof data.resonance.schemes != 'undefined' && typeof data.resonance.schemes[0].items != 'undefined' && data.resonance.schemes[0].items.length > 0) { 
		if(currentview == 'main') { //homepage
		  if(typeof certonaListLoadVertical !== 'undefined') {
			certonaListLoadVertical(data);
			/*if(typeof certonaListLoadMobile !== 'undefined') {
			  certonaListLoadMobile(data);
			}*/
		  }
		} else if(currentview == 'category') { //category && category change
		  if(typeof certonaListLoadHorizontal !== 'undefined') {
			certonaListLoadHorizontal(data);
			if(typeof certonaListLoadMobile !== 'undefined') {
			  certonaListLoadMobile(data);
			}
		  }
		} else if(currentview == 'product') { //product
			if(typeof dataLayer != 'undefined' && 'event' in dataLayer[dataLayer.length - 1] && dataLayer[dataLayer.length - 1]['event'] == 'addtocart') {
				if(typeof certonaListLoadATC !== 'undefined') {
					certonaListLoadATC(data);
				}
			} else {
				if(typeof certonaListLoadVertical !== 'undefined') {
					certonaListLoadVertical(data);
					if(typeof certonaListLoadMobile !== 'undefined') {
						certonaListLoadMobile(data);
					}
				}
			}
		} else if(currentview == 'cart') { //cart && cart remove
			if(typeof certonaListLoadHorizontal !== 'undefined') {
				certonaListLoadHorizontal(data);
				if(typeof certonaListLoadMobile !== 'undefined') {
					certonaListLoadMobile(data);
				}
			}
		} else if(currentview == 'search' && $('.jqs-item').length == 0) { //search
			if(typeof certonaListLoadHorizontal !== 'undefined') {
				certonaListLoadHorizontal(data);
				if(typeof certonaListLoadMobile !== 'undefined') {
					certonaListLoadMobile(data);
				}
			}
		} else if(currentview == 'receipt') {
			if(typeof certonaListLoadHorizontal !== 'undefined') {
				certonaListLoadHorizontal(data);
				if(typeof certonaListLoadMobile !== 'undefined') {
					certonaListLoadMobile(data);
				}
			}  
		}
	  } else {
		return;
	}
}

function certonaListLoadATC(certonaData) {
    var certonaDataNew = certonaData.resonance.schemes[0].items;
    var certonaDataList = certonaDataNew.slice(0,3);
    $('.certonaListATC[bns-certonalist]').length > 0 ? $('[bns-certonalist]').remove() : '';

    var certonaList = $('<div />').addClass('certonaListATC').attr('bns-certonalist', '').append(
                            $('<h4 />').html('You May Also Like')
                        );

    certonaList.insertAfter($('[bns-certonaloadlist]'));

    $.each(certonaDataList, function(key) {
    	if (certonaDataList[key].Rating.length == 1) {
    		certonaDataList[key].Rating += '_0'
    	}
        $('[bns-certonaList]').append(
            $('<div />').addClass('atcProductDisplay').append(
                $('<a />').attr({
                    'data-sku' : certonaDataList[key].ChildID,
                    'href' : certonaDataList[key].Detail_URL
                }).on('click', function(e) {
	                	e.preventDefault()
	                	GoogleAnalytics.trackEvent('certona', 'addtocart', certonaDataList[key].ChildID)
	                	window.location.href = certonaDataList[key].Detail_URL
	            }).append(
                    $('<img />').attr({
                        'src' : '//actionenvelope.scene7.com/is/image/ActionEnvelope/' + certonaDataList[key].ChildID + '?wid=100&hei=100&bgc=f8f8f8',
                        'alt' : certonaDataList[key].ChildID
                    })
                ).append(
                    $('<div />').addClass('atcpd-itemName').append(
                        $('<p />').html(certonaDataList[key].Title.replace(/(^.*?)\s((?:\([0-9\/xX\s]+\)|\&\#x28\;[0-9\/xX\s\&\#f\;]+))/, '$1'))
                    ).append(
                        $('<p />').addClass('certonaSize').html(certonaDataList[key].Size[0])
                    )
                ).append(
                    $('<p />').addClass('atcpd-itemSku').html(certonaDataList[key].ChildID)
                ).append(
                    $('<p />').addClass('atcpd-itemColor').html(certonaDataList[key].ColorName[0])
                ).append(
                    $('<div />').addClass('atcpd-row').append(
                        $('<div />').addClass('stars rating-' + certonaDataList[key].Rating.replace('.', '_'))
                    ).append(
                        $('<p />').addClass('atcpd-itemPrice').html('From ' + formatCurrency(certonaDataList[key]['Price-Current']) + ' / ' + certonaDataList[key].Quantity)
                    )
                )
            )
        );
    });
	slideIt_init()
}

function certonaListLoadMobile(certonaData) {
	if(certonaData) {
	    var certonaDataList = certonaData.resonance.schemes[0].items;
	    $('.certonaDataMobile').length > 0 ? $('.certonaDataMobile').remove() : '';


	    var certonaList = $('<div />').addClass(currentview == 'product' ? 
	    											'certonaDataMobile certonaProductMobile matchingProducts jqs-product margin-bottom-xs margin-top-xs' : 
	    												'certonaDataMobile matchingProducts jqs-product margin-bottom-xs margin-top-xs'
	    					).append(
	                            $('<h4 />').addClass('padding-left-xxs').html(currentview == 'search' ? 'There are zero results that match your search term. Try these highly recommended products' : 'You May Also Like')
	                        ).append(
	                            $('<div />').attr('bns-certonaMobileList', '')
	                        );

	    if (currentview == 'category') {
	    	if ($('[bns-mostrecentreviews]').length > 0) {
	        	certonaList.insertBefore($('[bns-mostrecentreviews]'));
	        } else if ($('[bns-pagination]').length > 0) {
	        	certonaList.insertAfter($('[bns-pagination]'));
	        } else {
	        	certonaList.insertAfter($('[bns-elasticsearchcontainer]'));
	        }
	    } else if (currentview == 'search') {
	    	certonaList.insertBefore($('[bns-loadcertonalist]'))
	    } else if (currentview == 'main') {
	    	certonaList.insertAfter($('.content-full'))
	    } else if(currentview == 'product') {
	    	certonaList.insertAfter($('.productContent'))
	    } else {
	    	if($('.line-items').children().attr('data-item-comment') == undefined) {
				certonaList.insertAfter($('#jqs-order-summary'))
	    	} else {
	    		certonaList.insertAfter($('.coupons-shipping-price'))
	    	}
	    }

	    $.each(certonaDataList, function(key) {
	    	if (certonaDataList[key].Rating.length == 1) {
	    		certonaDataList[key].Rating += '_0'
	    	}
	        $('[bns-certonaMobileList]').append(
	            $('<div />').addClass('mobileProductDisplay').append(
	                $('<a />').attr({
	                    'data-sku' : certonaDataList[key].ChildID,
	                    'href' : certonaDataList[key].Detail_URL
	                }).on('click', function(e) {
	                	e.preventDefault()
	                	GoogleAnalytics.trackEvent('certona', currentview, certonaDataList[key].ChildID)
	                	window.location.href = certonaDataList[key].Detail_URL
		            }).append(
	                    $('<img />').attr({
	                        'src' : '//actionenvelope.scene7.com/is/image/ActionEnvelope/' + certonaDataList[key].ChildID + '?wid=100&hei=125',
	                        'alt' : certonaDataList[key].ChildID
	                    })
	                ).append(
	                	$('<div />').addClass('mpd-itemInfo').append(
		                    $('<div />').addClass('mpd-itemName').append(
		                        $('<p />').html(certonaDataList[key].Title.replace(/(^.*?)\s((?:\([0-9\/xX\s]+\)|\&\#x28\;[0-9\/xX\s\&\#f\;]+))/, '$1'))
		                    ).append(
		                        $('<p />').addClass('certonaSize').html(certonaDataList[key].Size[0])
		                    )
		                ).append(
		                    $('<p />').addClass('mpd-itemSku').html(certonaDataList[key].ChildID)
		                ).append(
		                    $('<p />').addClass('mpd-itemColor').html(certonaDataList[key].ColorName[0])
		                ).append(
		                    $('<div />').addClass('mpd-row').append(
		                        $('<div />').addClass('stars rating-' + certonaDataList[key].Rating.replace('.', '_'))
		                    ).append(
		                        $('<p />').addClass('mpd-itemPrice').html('From ' + formatCurrency(certonaDataList[key]['Price-Current']) + ' / ' + certonaDataList[key].Quantity)
		                    )
		                )
		            )    
	            )
	        );
	    });
		slideIt_init()
	}
}

function certonaListLoadHorizontal(certonaData) {
	if(certonaData) {
	    var certonaDataList = certonaData.resonance.schemes[0].items;
	    $('.certonaData').length > 0 ? $('.certonaData').remove() : '';

	    if (currentview == 'product') {
			var certonaList = $('<div />').addClass('certonaData content certonaSearch margin-top-xxs margin-bottom-xs').append(
								$('<h4 />').html('You May Also Like')
							).append(
								$('<div />').addClass('slideIt-container padding-xxs').append(
									$('<div />').addClass('slideIt-left').append(
										$('<i />').addClass('fa fa-chevron-left')
									)).append(
									$('<div />').addClass('slideIt text-left productImageSlideIt').append(
										$('<div />').attr('bns-certonaList', '')
									)
								).append(
									$('<div />').addClass('slideIt-right').append(
										$('<i />').addClass('fa fa-chevron-right')
									)
								) 
							);

				certonaList.insertBefore($('.productContentLeft'));

				$.each(certonaDataList, function(key) {
				if (certonaDataList[key].Rating.length == 1) {
					certonaDataList[key].Rating += '_0'
				}
				$('[bns-certonaList]').append(
					$('<div />').addClass('topProductDisplay').append(
						$('<a />').attr({
							'data-sku' : certonaDataList[key].ChildID,
							'href' : certonaDataList[key].Detail_URL
						}).on('click', function(e) {
								e.preventDefault()
								GoogleAnalytics.trackEvent('certona', 'pla', certonaDataList[key].ChildID)
								window.location.href = certonaDataList[key].Detail_URL
						}).append(
							$('<img />').attr({
								'src' : '//actionenvelope.scene7.com/is/image/ActionEnvelope/' + certonaDataList[key].ChildID + '?wid=100&hei=100',
								'alt' : certonaDataList[key].ChildID
							})
						).append(
							$('<div />').addClass('tpd-itemInfo').append(
								$('<div />').addClass('tpd-itemName').append(
									$('<p />').html(certonaDataList[key].Title.replace(/(^.*?)\s((?:\([0-9\/xX\s]+\)|\&\#x28\;[0-9\/xX\s\&\#f\;]+))/, '$1'))
								).append(
									$('<p />').addClass('certonaSize').html(certonaDataList[key].Size[0])
								)
							).append(
								$('<p />').addClass('tpd-itemSku').html(certonaDataList[key].ChildID)
							).append(
								$('<p />').addClass('tpd-itemColor').html(certonaDataList[key].ColorName[0])
							).append(
								$('<div />').addClass('tpd-row').append(
									$('<div />').addClass('rating-' + certonaDataList[key].Rating.replace('.', '_'))
								).append(
									$('<p />').addClass('tpd-itemPrice').html('From ' + formatCurrency(certonaDataList[key]['Price-Current']) + ' / ' + certonaDataList[key].Quantity)
								)
							)
						).append(
							$('<div />').addClass('certonaDetailsButton').html('View Details ').append(
								$('<i />').addClass('fa fa-caret-right')
							)
						)
					)
				);
			});
	    } else if(currentview == 'category') {
	    	var certonaList = $('<div />').addClass('certonaData certonaCategory matchingProducts jqs-product margin-bottom-xs').append(
	                            $('<h4 />').html('You May Also Like')
	                        ).append(
	                            $('<div />').addClass('slideIt-container padding-top-xxs padding-left-xxs padding-right-xxs').append(
	                                $('<div />').addClass('slideIt-left').append(
	                                    $('<i />').addClass('fa fa-chevron-left')
	                                )).append(
	                                $('<div />').addClass('slideIt text-left productImageSlideIt').append(
	                                    $('<div />').attr('bns-certonaList', '')
	                                )
	                            ).append(
	                                $('<div />').addClass('slideIt-right').append(
	                                    $('<i />').addClass('fa fa-chevron-right')
	                                )
	                            ) 
	                        );

	        if ($('[bns-mostrecentreviews]').length > 0) {
	        	certonaList.insertBefore($('[bns-mostrecentreviews]'));
	        } else if ($('[bns-pagination]').length > 0) {
	        	certonaList.insertAfter($('[bns-pagination]'));
	        } else {
	        	certonaList.insertAfter($('[bns-elasticsearchcontainer]'));
	        }
		    
		    $.each(certonaDataList, function(key) {
		    	if (certonaDataList[key].Rating.length == 1) {
		    		certonaDataList[key].Rating += '_0'
		    	}
		        $('[bns-certonaList]').append(
		            $('<div />').addClass('topProductDisplay').append(
		                $('<a />').attr({
		                    'data-sku' : certonaDataList[key].ChildID,
		                    'href' : certonaDataList[key].Detail_URL
		                }).on('click', function(e) {
			                	e.preventDefault()
			                	GoogleAnalytics.trackEvent('certona', currentview, certonaDataList[key].ChildID)
			                	window.location.href = certonaDataList[key].Detail_URL
			            }).append(
		                    $('<img />').attr({
		                        'src' : '//actionenvelope.scene7.com/is/image/ActionEnvelope/' + certonaDataList[key].ChildID + '?wid=100&hei=75',
		                        'alt' : certonaDataList[key].ChildID
		                    })
		                ).append(
		                	$('<div />').addClass('tpd-itemInfo').append(
			                    $('<div />').addClass('tpd-itemName').append(
			                        $('<p />').html(certonaDataList[key].Title.replace(/(^.*?)\s((?:\([0-9\/xX\s]+\)|\&\#x28\;[0-9\/xX\s\&\#f\;]+))/, '$1'))
			                    ).append(
			                        $('<p />').addClass('certonaSize').html(certonaDataList[key].Size[0])
			                    )
			                ).append(
			                    $('<p />').addClass('tpd-itemSku').html(certonaDataList[key].ChildID)
			                ).append(
			                    $('<p />').addClass('tpd-itemColor').html(certonaDataList[key].ColorName[0])
			                ).append(
			                    $('<div />').addClass('tpd-row').append(
			                        $('<div />').addClass('stars rating-' + certonaDataList[key].Rating.replace('.', '_'))
			                    ).append(
			                        $('<p />').addClass('tpd-itemPrice').html('From ' + formatCurrency(certonaDataList[key]['Price-Current']) + ' / ' + certonaDataList[key].Quantity)
			                    )
			                )
			            ).append(
		                	$('<div />').addClass('certonaDetailsButton').html('View Details ').append(
		            			$('<i />').addClass('fa fa-caret-right')
		            		)
		                )    
		            )
		        );
		    });
	    } else if (currentview == 'search') {
	    	var certonaList = $('<div />').addClass('certonaData content certonaSearch').append(
	                            $('<h4 />').html('There are zero results that match your search term. Try these highly recommended products')
	                        ).append(
	                            $('<div />').addClass('slideIt-container padding-xxs margin-top-xxs').append(
	                                $('<div />').addClass('slideIt-left').append(
	                                    $('<i />').addClass('fa fa-chevron-left')
	                                )).append(
	                                $('<div />').addClass('slideIt text-left productImageSlideIt').append(
	                                    $('<div />').attr('bns-certonaList', '')
	                                )
	                            ).append(
	                                $('<div />').addClass('slideIt-right').append(
	                                    $('<i />').addClass('fa fa-chevron-right')
	                                )
	                            ) 
	                        );

		    certonaList.insertBefore($('.category'));

		    $.each(certonaDataList, function(key) {
		    	if (certonaDataList[key].Rating.length == 1) {
		    		certonaDataList[key].Rating += '_0'
		    	}
		        $('[bns-certonaList]').append(
		            $('<div />').addClass('topProductDisplay').append(
		                $('<a />').attr({
		                    'data-sku' : certonaDataList[key].ChildID,
		                    'href' : certonaDataList[key].Detail_URL
		                }).on('click', function(e) {
			                	e.preventDefault()
			                	GoogleAnalytics.trackEvent('certona', currentview, certonaDataList[key].ChildID)
			                	window.location.href = certonaDataList[key].Detail_URL
			            }).append(
		                    $('<img />').attr({
		                        'src' : '//actionenvelope.scene7.com/is/image/ActionEnvelope/' + certonaDataList[key].ChildID + '?wid=100&hei=100',
		                        'alt' : certonaDataList[key].ChildID
		                    })
		                ).append(
		                	$('<div />').addClass('tpd-itemInfo').append(
			                    $('<div />').addClass('tpd-itemName').append(
			                        $('<p />').html(certonaDataList[key].Title.replace(/(^.*?)\s((?:\([0-9\/xX\s]+\)|\&\#x28\;[0-9\/xX\s\&\#f\;]+))/, '$1'))
			                    ).append(
			                        $('<p />').addClass('certonaSize').html(certonaDataList[key].Size[0])
			                    )
			                ).append(
			                    $('<p />').addClass('tpd-itemSku').html(certonaDataList[key].ChildID)
			                ).append(
			                    $('<p />').addClass('tpd-itemColor').html(certonaDataList[key].ColorName[0])
			                ).append(
			                    $('<div />').addClass('tpd-row').append(
			                        $('<div />').addClass('rating-' + certonaDataList[key].Rating.replace('.', '_'))
			                    ).append(
			                        $('<p />').addClass('tpd-itemPrice').html('From ' + formatCurrency(certonaDataList[key]['Price-Current']) + ' / ' + certonaDataList[key].Quantity)
			                    )
			                )
			            ).append(
		                	$('<div />').addClass('certonaDetailsButton').html('View Details ').append(
		            			$('<i />').addClass('fa fa-caret-right')
		            		)
		                )
		            )
		        );
			});
		} else {
	    	var certonaList = $('<div />').addClass('certonaData certonaCart matchingProducts').append(
	                          	$('<h4 />').html('You May Also Like')
	                        ).append(
	                            $('<div />').addClass('slideIt-container').append(
	                                $('<div />').addClass('slideIt-left').append(
	                                    $('<i />').addClass('fa fa-chevron-left')
	                                )).append(
	                                $('<div />').addClass('slideIt text-left productImageSlideIt').append(
	                                    $('<div />').attr('bns-certonaList', '')
	                                )
	                            ).append(
	                                $('<div />').addClass('slideIt-right').append(
	                                    $('<i />').addClass('fa fa-chevron-right')
	                                )
	                            ) 
	                        );

			currentview == 'receipt' ? 
				$('.content.container.receipt').prepend(certonaList) :
		    	certonaList.insertBefore($('[bns-loadcertonalist]'))

		    $.each(certonaDataList, function(key) {
		    	if (certonaDataList[key].Rating.length == 1) {
		    		certonaDataList[key].Rating += '_0'
		    	}
		        $('[bns-certonaList]').append(
		            $('<div />').addClass('topProductDisplay').append(
		                $('<a />').attr({
		                    'data-sku' : certonaDataList[key].ChildID,
		                    'href' : certonaDataList[key].Detail_URL
		                }).on('click', function(e) {
			                	e.preventDefault()
			                	GoogleAnalytics.trackEvent('certona', currentview, certonaDataList[key].ChildID)
			                	window.location.href = certonaDataList[key].Detail_URL
			            }).append(
		                    $('<img />').attr({
		                        'src' : '//actionenvelope.scene7.com/is/image/ActionEnvelope/' + certonaDataList[key].ChildID + '?wid=75&hei=75',
		                        'alt' : certonaDataList[key].ChildID
		                    })
		                ).append(
		                	$('<div />').addClass('tpd-itemInfo').append(
			                    $('<div />').addClass('tpd-itemName').append(
			                        $('<p />').html(certonaDataList[key].Title.replace(/(^.*?)\s((?:\([0-9\/xX\s]+\)|\&\#x28\;[0-9\/xX\s\&\#f\;]+))/, '$1'))
			                    ).append(
			                        $('<p />').addClass('certonaSize').html(certonaDataList[key].Size[0])
			                    )
			                ).append(
			                    $('<p />').addClass('tpd-itemSku').html(certonaDataList[key].ChildID)
			                ).append(
			                    $('<p />').addClass('tpd-itemColor').html(certonaDataList[key].ColorName[0])
			                ).append(
			                    $('<div />').addClass('tpd-row').append(
			                        $('<div />').addClass('stars rating-' + certonaDataList[key].Rating.replace('.', '_'))
			                    ).append(
			                        $('<p />').addClass('tpd-itemPrice').html('From ' + formatCurrency(certonaDataList[key]['Price-Current']) + ' / ' + certonaDataList[key].Quantity)
			                    )
			                )
			            ).append(
		                	$('<div />').addClass('certonaDetailsButton').html('View Details ').append(
		            			$('<i />').addClass('fa fa-caret-right')
		            		)
		                )    
		            )
		        );
		    });
	    }
		slideIt_init()
	} 
}

function certonaListLoadVertical(certonaData) {
	if(certonaData) {
		if(certonaData.resonance.schemes[0].scheme == 'pla1_rr') {
			certonaListLoadHorizontal(certonaData)
		} else {
			var certonaDataList = certonaData.resonance.schemes[0].items;
			$('.certonaData').length > 0 ? $('.certonaData').remove() : '';

			currentview == 'product' ? $('.product .productContent').css('max-width', '1360px') : '';

			var certonaList = $('<div />').addClass('certonaData').append(
									currentview == 'product' ? 
										$('<h4 />').html('You May Also Like') :
										$('<h4 />').html('Recommended for You')
								).append(
									$('<div />').css({
										'background-color' : 'white',
										'height' : currentview == 'product' ? '992px' : '1025px' 
										}).append(
											$('<div />').addClass('slideIt-container vertical padding-left-xxs padding-right-xxs').append(
												$('<div />').addClass('slideIt-up').append(
													$('<i />').addClass('fa fa-chevron-up')
												)).append(
												$('<div />').addClass('slideIt vertical productImageSlideIt').attr('bns-verticalslideit', 'true').append(
													$('<div />').attr('bns-certonaList', '')
												)
										).append(
											$('<div />').addClass('slideIt-down').append(
												$('<i />').addClass('fa fa-chevron-down')
											)
										) 
									)   
								);

			if(currentview == 'product') {
				certonaList.insertAfter($('.productContentRight'))
				$('.certonaData').addClass('certonaRight')
			} else {
				$('[bns-certonalisthp]').append(certonaList)
				$('.certonaData').addClass('certonaHpContainer')
			}
			
			$.each(certonaDataList, function(key) {
				if (certonaDataList[key].Rating.length == 1) {
					certonaDataList[key].Rating += '_0'
				}
				$('[bns-certonaList]').append(
					$('<div />').addClass(currentview == 'product' ? 'rightProductDisplay certonaProductPage' : 'rightProductDisplay').append(
						$('<a />').attr({
							'data-sku' : certonaDataList[key].ChildID,
							'href' : certonaDataList[key].Detail_URL
						}).on('click', function(e) {
								e.preventDefault()
								GoogleAnalytics.trackEvent('certona', currentview, certonaDataList[key].ChildID)
								window.location.href = certonaDataList[key].Detail_URL
						}).append(
							$('<img />').attr({
								'src' : currentview == 'product' ? 
											'//actionenvelope.scene7.com/is/image/ActionEnvelope/' + certonaDataList[key].ChildID + '?wid=75&hei=75' : 
												'//actionenvelope.scene7.com/is/image/ActionEnvelope/' + certonaDataList[key].ChildID + '?wid=100&hei=100',
								'alt' : certonaDataList[key].ChildID
							})
						).append(
							$('<div />').addClass('rpd-itemName').append(
								$('<p />').html(certonaDataList[key].Title.replace(/(^.*?)\s((?:\([0-9\/xX\s]+\)|\&\#x28\;[0-9\/xX\s\&\#f\;]+))/, '$1'))
							).append(
								$('<p />').addClass('certonaSize').html(certonaDataList[key].Size[0])
							)
						).append(
							$('<p />').addClass('rpd-itemSku').html(certonaDataList[key].ChildID)
						).append(
							currentview == 'product' ?
							$('<p />').addClass('rpd-itemColor').html(certonaDataList[key].ColorName[0]) :
								$('<p />').addClass('rpd-itemColor').html(' - ' + certonaDataList[key].ColorName[0])
						).append(
							$('<div />').addClass('rpd-row').append(
								$('<div />').addClass('rating-' + certonaDataList[key].Rating.replace('.', '_'))
							).append(
								$('<p />').addClass('rpd-itemPrice').html('From ' + formatCurrency(certonaDataList[key]['Price-Current']) + ' / ' + certonaDataList[key].Quantity)
							)
						)
					)
				);
			});
			slideIt_init()
		}
	}
}

// Folders Certona - work on combining with envelopes
function certonaCallbackFolders(data, currentView) {
	if (currentView) {
		if (currentView == 'cart') {
			foldersCertonaListLoadHorizontal(data, currentView);
			foldersCertonaListLoadMobile(data);
		} else if (currentView == 'product') {
			if(data.resonance.schemes[0].scheme == 'pla1_rr') {
				foldersCertonaListLoadHorizontal(data, currentView);
			} else {
				foldersCertonaListLoadVertical(data);
			}
			foldersCertonaListLoadMobile(data);
		} else if (currentView == 'receipt') {
			foldersCertonaListLoadHorizontal(data, currentView);
			foldersCertonaListLoadMobile(data);
		} else if (currentView == 'main') {
			foldersCertonaListLoadHorizontal(data, currentView);
			foldersCertonaListLoadMobile(data);
		} else {
			// search page
			if ($('[bns-foldersnosearchresults]').length > 0 ) {
				foldersCertonaListLoadHorizontal(data, currentView);
				foldersCertonaListLoadMobile(data);
			}
		}
	}
}
function foldersCertonaListLoadMobile(certonaData) {
	if(typeof certonaData != 'undefined' && typeof certonaData.resonance != 'undefined' && typeof certonaData.resonance.schemes != 'undefined' && typeof certonaData.resonance.schemes[0] != 'undefined' && typeof certonaData.resonance.schemes[0].items == 'object' && certonaData.resonance.schemes[0].items.length > 0) {
	    var certonaDataList = certonaData.resonance.schemes[0].items;
	    $('.certonaDataMobile').length > 0 ? $('.certonaDataMobile').remove() : '';

	    var certonaList = $('<div />').addClass(currentview == 'product' ? 
	    											'certonaDataMobile certonaProductMobile matchingProducts jqs-product' : 
	    												'certonaDataMobile matchingProducts jqs-product'
	    					).append(
	                            $('<h4 />').addClass('padding-left-xxs').html(currentview == 'search' ? 'There are zero results that match your search term. Try these highly recommended products' : 'You May Also Like')
	                        ).append(
	                            $('<div />').attr('bns-certonaMobileList', '')
	                        );

	    if (currentview == 'category') {
	    	if ($('[bns-mostrecentreviews]').length > 0) {
	        	certonaList.insertBefore($('[bns-mostrecentreviews]'));
	        } else if ($('[bns-pagination]').length > 0) {
	        	certonaList.insertAfter($('[bns-pagination]'));
	        } else {
	        	certonaList.insertAfter($('[bns-elasticsearchcontainer]'));
	        }
	    } else if (currentview == 'search') {
	    	certonaList.insertBefore($('[bns-loadcertonalist]'))
	    } else if (currentview == 'main') {
	    	//certonaList.insertAfter($('.content-full'))
	    } else if(currentview == 'product') {
	    	certonaList.insertAfter($('.productContent'))
	    } else if(currentview == 'receipt') {
	    	certonaList.insertAfter($('[bns-loadcertonalist]'))
	    } else {
	    	// if($('.line-items').children().attr('data-item-comment') == undefined) {
			// 	certonaList.insertAfter($('#jqs-order-summary'))
	    	// } else {
	    	// 	certonaList.insertAfter($('.coupons-shipping-price'))
			// }
			$('.foldersContainer.cart').append(certonaList);
	    }

	    $.each(certonaDataList, function(key) {
	    	if (certonaDataList[key].Rating.length == 1) {
	    		certonaDataList[key].Rating += '_0'
	    	}
	        $('[bns-certonaMobileList]').append(
	            $('<div />').addClass('mobileProductDisplay').append(
	                $('<a />').attr({
	                    'data-sku' : certonaDataList[key].ChildID,
	                    'href' : certonaDataList[key].Detail_URL
	                }).on('click', function(e) {
	                	e.preventDefault()
	                	GoogleAnalytics.trackEvent('certona', currentview, certonaDataList[key].ChildID)
	                	window.location.href = certonaDataList[key].Detail_URL
		            }).append(
	                    $('<img />').attr({
	                        'src' : '//actionenvelope.scene7.com/is/image/ActionEnvelope/' + certonaDataList[key].ChildID + '?wid=100&hei=125',
	                        'alt' : certonaDataList[key].ChildID
	                    })
	                ).append(
	                	$('<div />').addClass('mpd-itemInfo').append(
		                    $('<div />').addClass('mpd-itemName').append(
		                        $('<p />').html(certonaDataList[key].Title.replace(/(^.*?)\s((?:\([0-9\/xX\s]+\)|\&\#x28\;[0-9\/xX\s\&\#f\;]+))/, '$1'))
		                    ).append(
		                        $('<p />').addClass('certonaSize').html('SKU: ' + certonaDataList[key].ChildID)
		                    )
		                ).append(
		                    $('<div />').addClass('certonaProductDetails').append(
								$('<i />').addClass('fa fa-circle-o')
							).append(
								$('<p />').addClass('tpd-itemSku').html(certonaDataList[key].Size[0])
							)
		                ).append(
		                    $('<div />').addClass('certonaProductDetails').append(
								$('<i />').addClass('fa fa-circle-o')
							).append(
								$('<p />').addClass('tpd-itemColor').html(certonaDataList[key].ColorName[0])
							)
		                ).append(
		                    $('<div />').addClass('mpd-row').append(
		                        $('<div />').addClass('stars rating-' + certonaDataList[key].Rating.replace('.', '_'))
		                    ).append(
		                        $('<p />').addClass('mpd-itemPrice').html('From ' + formatCurrency(certonaDataList[key]['Price-Current']) + ' / ' + certonaDataList[key].Quantity)
		                    )
		                )
		            )    
	            )
	        );
	    });
		slideIt_init()
	}
}

function foldersCertonaListLoadHorizontal(certonaData, currentView) {
	if(typeof certonaData != 'undefined' && typeof certonaData.resonance != 'undefined' && typeof certonaData.resonance.schemes != 'undefined' && typeof certonaData.resonance.schemes[0] != 'undefined' && typeof certonaData.resonance.schemes[0].items == 'object' && certonaData.resonance.schemes[0].items.length > 0) {
	    var certonaDataList = certonaData.resonance.schemes[0].items;
	    $('.certonaData').length > 0 ? $('.certonaData').remove() : '';
		
		var certonaList = $('<div />').addClass('certonaData content marginLeft20 marginRight20 marginTop20').append(
								currentView == 'search' ? 
									$('<h4 />').html('There are zero results that match your search term. Try these highly recommended products') :
								currentView == 'main' ?
									$('<h4 />').html('Recommended for You') :
								currentView == 'receipt' ?
									$('<h4 />').html('') :
									$('<h4 />').html('You May Also Like')
							).append(
								$('<div />').addClass('slideIt-container padding10 marginTop10').append(
									$('<div />').addClass('slideIt-left').append(
										$('<i />').addClass('fa fa-chevron-left')
									)).append(
									$('<div />').addClass('slideIt textLeft productImageSlideIt').append(
										$('<div />').attr('bns-certonaList', '')
									)
								).append(
									$('<div />').addClass('slideIt-right').append(
										$('<i />').addClass('fa fa-chevron-right')
									)
								) 
							);

		if (currentView == 'search') {
			certonaList.insertBefore($('[bns-certonasearch]'));
			$('.certonaData').addClass('certonaSearch');
		} else if (currentView == 'cart') {
			certonaList.insertBefore($('.foldersContainer.cart'));
			$('.certonaData').addClass('certonaCart');
		} else if (currentView == 'receipt') {
			certonaList.insertBefore($('.foldersContainer.receipt'));
			$('.certonaData').addClass('certonaSearch');
		} else if (currentView == 'main') {
			$('[bns-certonalisthp]').append(certonaList);
			$('.certonaData').addClass('textCenter');
		}
		
		$.each(certonaDataList, function(key) {
			if (certonaDataList[key].Rating.length == 1) {
				certonaDataList[key].Rating += '_0'
			}
			$('[bns-certonaList]').append(
				$('<div />').addClass('topProductDisplay').append(
					$('<a />').attr({
						'data-sku' : certonaDataList[key].ChildID,
						'href' : certonaDataList[key].Detail_URL
					}).on('click', function(e) {
							e.preventDefault()
							GoogleAnalytics.trackEvent('certona', currentview, certonaDataList[key].ChildID)
							window.location.href = certonaDataList[key].Detail_URL
					}).append(
						$('<img />').attr({
							'src' : '//actionenvelope.scene7.com/is/image/ActionEnvelope/' + certonaDataList[key].ChildID + (currentView == 'main' ? '?wid=150&hei=150' : '?wid=100&hei=100'),
							'alt' : certonaDataList[key].ChildID
						})
					).append(
						$('<div />').addClass('tpd-itemInfo').append(
							$('<div />').addClass('tpd-itemName').append(
								$('<p />').html(certonaDataList[key].Title.replace(/(^.*?)\s((?:\([0-9\/xX\s]+\)|\&\#x28\;[0-9\/xX\s\&\#f\;]+))/, '$1'))
							).append(
								$('<p />').addClass('certonaSize').html('SKU: ' + certonaDataList[key].ChildID)
							)
						).append(
							$('<div />').addClass('certonaProductDetails').append(
								$('<i />').addClass('fa fa-circle-o')
							).append(
								$('<p />').addClass('tpd-itemSku').html(certonaDataList[key].Size)
							)
						).append(
							$('<div />').addClass('certonaProductDetails').append(
								$('<i />').addClass('fa fa-circle-o colorAlignment')
							).append(
								$('<p />').addClass('tpd-itemColor').html(certonaDataList[key].ColorName[0])
							)
						).append(
							$('<div />').addClass('tpd-row').append(
								$('<div />').addClass('rating-' + certonaDataList[key].Rating.replace('.', '_'))
							).append(
								$('<p />').addClass('tpd-itemPrice').html('From ' + formatCurrency(certonaDataList[key]['Price-Current']) + ' / ' + certonaDataList[key].Quantity)
							)
						)
					).append(
						currentView != 'main' ? 
							$('<div />').addClass('certonaDetailsButton').html('View Details ').append(
								$('<i />').addClass('fa fa-caret-right')
							) : ''
					)
				)
			);
		});
		slideIt_init()
	} 
}

function foldersCertonaListLoadVertical(certonaData) {
	if(certonaData) {
		if(certonaData.resonance.schemes[0].scheme == 'pla1_rr') {
			foldersCertonaListLoadHorizontal(certonaData)
		} else {
			var certonaDataList = certonaData.resonance.schemes[0].items;
			$('.certonaData').length > 0 ? $('.certonaData').remove() : '';

			currentview == 'product' ? $('.foldersContainer.foldersProduct').css('max-width', '1360px') : '';

			var certonaList = $('<div />').addClass('certonaData certonaRight').append(
									$('<div />').append(
										$('<h4 />').addClass('paddingTop10').html('You May Also Like')
									)
								).append(
									$('<div />').css({
										'margin-bottom' : currentview == 'product' ? '10px' : 'inherit',
										'background-color' : 'white',
										'height' : currentview == 'product' ? '925px' : '720px' 
										}).append(
											$('<div />').addClass('slideIt-container vertical paddingLeft10 paddingRight10').append(
												$('<div />').addClass('slideIt-up').append(
													$('<i />').addClass('fa fa-chevron-up')
												)).append(
												$('<div />').addClass('slideIt vertical productImageSlideIt').attr('bns-verticalslideit', 'true').append(
													$('<div />').attr('bns-certonaList', '')
												)
										).append(
											$('<div />').addClass('slideIt-down').append(
												$('<i />').addClass('fa fa-chevron-down')
											)
										) 
									)   
								);

			currentview == 'product' ? certonaList.insertAfter($('.productSidebar')) :	$('[bns-certonalisthp]').append(certonaList);

			$.each(certonaDataList, function(key) {
				if (certonaDataList[key].Rating.length == 1) {
					certonaDataList[key].Rating += '_0'
				}
				$('[bns-certonaList]').append(
					$('<div />').addClass(currentview == 'product' ? 'rightProductDisplay certonaProductPage' : 'rightProductDisplay').append(
						$('<a />').attr({
							'data-sku' : certonaDataList[key].ChildID,
							'href' : certonaDataList[key].Detail_URL
						}).on('click', function(e) {
								e.preventDefault()
								GoogleAnalytics.trackEvent('certona', currentview, certonaDataList[key].ChildID)
								window.location.href = certonaDataList[key].Detail_URL
						}).append(
							$('<img />').attr({
								'src' : currentview == 'product' ? 
											'//actionenvelope.scene7.com/is/image/ActionEnvelope/' + certonaDataList[key].ChildID + '?wid=75&hei=75' : 
												'//actionenvelope.scene7.com/is/image/ActionEnvelope/' + certonaDataList[key].ChildID + '?wid=100&hei=75',
								'alt' : certonaDataList[key].ChildID
							})
						).append(
							$('<div />').addClass('rpd-itemName').append(
								$('<p />').html(certonaDataList[key].Title.replace(/(^.*?)\s((?:\([0-9\/xX\s]+\)|\&\#x28\;[0-9\/xX\s\&\#f\;]+))/, '$1'))
							).append(
								$('<p />').addClass('certonaSize').html('SKU: ' + certonaDataList[key].ChildID)
							)
						).append(
							$('<p />').addClass('rpd-itemColor').html(certonaDataList[key].ColorName[0])
						).append(
							$('<div />').addClass('rpd-row').append(
								$('<div />').addClass('rating-' + certonaDataList[key].Rating.replace('.', '_'))
							).append(
								$('<p />').addClass('rpd-itemPrice').html('From ' + formatCurrency(certonaDataList[key]['Price-Current']) + ' / ' + certonaDataList[key].Quantity)
							)
						)
					)
				);
			});
		}	
		slideIt_init()
	}
}

var GoogleAnalytics = {
	trackEvent: function(category, action, label, value) {
		if(category == undefined || action == undefined) {
			return false;
		}

		gaParameters = '"send", "event", "' + category + '", "' + action + '"';

		if (typeof label != 'undefined') {
			gaParameters += ', "' + label + '"';
		}

		if (typeof value != 'undefined') {
			gaParameters += ', "' + value + '"';
		}

		eval('ga(' + gaParameters + ')');

		if (typeof label != 'undefined') {
			InspectletTagging.tagSession(action, label);
		}
	},
	trackPageview: function(url, title) {
		if(url == undefined || title == undefined) {
			return false;
		}
		ga('set', 'page', url);
		ga('send', 'pageview');
	},
	setCustomDimension: function(dimensionValue, index) {
		ga('set', 'dimension'+index.toString(), dimensionValue);
	},
	trackContent: function(id, name, creative, position) {
        if(id == undefined || name == undefined || creative == undefined || position == undefined) {
            return false;
        }
        ga('ec:addPromo', {
            'id': id,
            'name': name,
            'creative': creative,
            'position': position
        });

        ga('ec:setAction', 'promo_click');
        ga('send', 'event', 'Internal Promotions', 'click', name);
    },
	trackProductView: function(id, name, color) {
        ga('ec:addProduct', {
            'id': id,
            'name': name,
            'category': '',
            'brand': '',
            'variant': color
        });
        ga('ec:setAction', 'detail');
        ga('send', 'event', 'UX', 'click', 'product detail');
	}
};

//quick jquery plugins
$.fn.visible = function() {
	return this.css('visibility', 'visible');
};

$.fn.invisible = function() {
	return this.css('visibility', 'hidden');
};

$.fn.setSelectable = function(add, siblingDepth) {
	if(add) {
		if(typeof siblingDepth !== 'undefined' && siblingDepth > 0) {
			var parentContainer = this;
			for(i = 1; i <= siblingDepth; i++) {
				parentContainer = parentContainer.parent();
			}
			parentContainer.find('[data-type=selectable-ui]').each(function (idx, el) {
				$(el).attr('data-checked', 'false').removeClass('selected');
				$(el).find('i[class*=check]').removeClass('hidden').addClass('hidden');
			});
		} else {
			this.siblings().each(function(idx, el) {
				$(el).attr('data-checked', 'false').removeClass('selected');
				$(el).find('i[class*=check]').removeClass('hidden').addClass('hidden');
			});
		}

		if(this.attr('data-type') == 'selectable-ui') {
			this.attr('data-checked', 'true').removeClass('selected').addClass('selected');
			this.find('i[class*=check]').removeClass('hidden');
		}
	} else {
		if(this.attr('data-type') == 'selectable-ui') {
			this.attr('data-checked', 'false').removeClass('selected');
			this.find('i[class*=check]').removeClass('hidden').addClass('hidden');
		}
	}
};

$.fn.getCartTotal = function() {
	var cartCookie = $.cookie('__ES_Data');
	return cartCookie ? parseFloat(JSON.parse($.cookie('__ES_Data')).cartSubtotal) : parseFloat(0);
};

$.fn.getTransporterCookie = function(name, defaultValue) {
    var transporterCookie = $.cookie(name);
    return transporterCookie ? $.cookie(name) : defaultValue;
};

$.fn.updateMiniCart = function(data) {
	try {
		if(typeof data != 'undefined' && localStorageEnabled) {
			if (data == '') {
				localStorage.setItem('addToCartData' , '{}');
			} else {
				localStorage.setItem('addToCartData', data);
			}
		}
	} catch(e) {
		log(e, true);
	}
	
	var cartItems = 0;
	var cartSubtotal = 'empty';
	var cartStorage = localStorageEnabled && typeof localStorage.addToCartData != "[object Object]" && typeof localStorage.addToCartData != 'undefined' ? JSON.parse(localStorage.addToCartData) : ''; 
	var emptyCart = true;
	var itemsData = '';

	var renderEmptyMiniCart = function() {
		$('#dropdown-nav-cart').addClass('hidden');
		var cartCountText = $('#cartContainer #jqs-mini-cart-count').text();
		$('#cartContainer #jqs-mini-cart-count').text(cartCountText.replace(/\d+/, '0'));
		//$('#jqs-mini-cart-total').html('empty');
		$('[bns-mini-cart-total]').html('empty');
		/*$('#jqs-mini-cart-items').addClass('empty').html('').append($('<div/>').append($('<span/>').html('Your shopping cart is empty.')));
		$('#dropdown-nav-cart').find('.nav-cart-footer').addClass('hidden');*/
	};

	var getMiniCartItemsHTML = function(itemsData) {
		var itemsHTML = $('<div/>');
		for(var i = 0; i < itemsData.length; i ++) {
			var productId = itemsData[i].productId;
			var itemName = itemsData[i].product.productName;
			var total = itemsData[i].totalPrice;
			var quantity = itemsData[i].quantity;
			var s7Id = itemsData[i].envArtworkAttributes.scene7DesignId;
			itemsHTML.append(
				$('<div/>').append(
					$('<div/>').append(
						$('<img/>').attr('src',  ((s7Id != undefined) ? 
							'/' + websiteId + '/control/serveFileForStream?filePath=uploads/texel/designs/' + s7Id + '_front.png' : 
							'//actionenvelope.scene7.com/is/image/ActionEnvelope/' + productId +'?wid=100&fmt=png-alpha'))
					)
				).append(
					$('<div/>').append(
						$('<span/>').text(itemName)
					).append(
						$('<span/>').text('$' + total + ' / ' + parseInt(quantity).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","))
					)
				)
			);
		}
		return itemsHTML.html();
	};

	if (cartStorage) {
		if(typeof cartStorage != 'undefined' && typeof cartStorage.cartItems != 'undefined' && cartStorage.cartItems.length > 0) {
			emptyCart = false;
			cartItems = cartStorage.cartItems.length;
			cartSubtotal = cartStorage.subTotal;
			itemsData = cartStorage.cartItems;

		} else {
			cartItems = 0;
			cartSubtotal = 'EMPTY';
			itemsData = '';
		}
	}

	if(cartItems == 0) {
		renderEmptyMiniCart();
	} else {
		$('#dropdown-nav-cart').removeClass('hidden');
        var cartCountText = $('#cartContainer #jqs-mini-cart-count').text();
		$('#cartContainer #jqs-mini-cart-count').text(cartCountText.replace(/\d+/, cartItems));
		//$('#jqs-mini-cart-total').html(formatCurrency(parseFloat(cartSubtotal)));
		$('#cartContainer #jqs-mini-cart-items').removeClass('empty').html('').append(getMiniCartItemsHTML(itemsData));
		$('[bns-mini-cart-total]').html(formatCurrency(parseFloat(cartSubtotal)));
	}
};

$.fn.addToCartCheckSubtotal = function(element) {
	var addToCartData = JSON.parse(localStorage.addToCartData);

	if(addToCartData.subTotal > 0) {
		var subtotal = addToCartData.subTotal;
		$('.jqs-fship_warning').remove();

		var freeShipAmount = (typeof freeShippingAmount != 'undefined' ? freeShippingAmount : 250);
		var fshipApplied = false;

		if (subtotal >= (freeShipAmount / 2) && !fshipApplied) {
			if(element == 'testA') {
				$('<div class="jqs-fship_warning" style="width: 100%; font-size: 15px; cursor: pointer; color: #000000; text-align: center; margin: 5px 0 15px;" data-reveal-id="fship-promo">' + (subtotal >= freeShipAmount ? 'Your order now qualifies for Free Shipping. <strong>Use code FREE' + freeShipAmount + '</strong>' : 'Spend An Additional <strong>$' + (freeShipAmount - subtotal).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,') + '</strong> For <strong>FREE</strong> Shipping! <strong>Code: FREE' + freeShipAmount + '</strong>*') + '</div>').insertBefore($('.addToCartContinueShoppingA'));
			} else {
				$('<div class="jqs-fship_warning" style="width: 100%; font-size: 15px; cursor: pointer; color: #000000; text-align: center; margin: 5px 0 15px;" data-reveal-id="fship-promo">' + (subtotal >= freeShipAmount ? 'Your order now qualifies for Free Shipping. <strong>Use code FREE' + freeShipAmount + '</strong>' : 'Spend An Additional <strong>$' + (freeShipAmount - subtotal).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,') + '</strong> For <strong>FREE</strong> Shipping! <strong>Code: FREE' + freeShipAmount + '</strong>*') + '</div>').insertBefore($('.addToCartContinueShoppingB'));			
			}
		}
	}
}

$.fn.addOrUpdateBrontoEmail = function(emailAddress, callback, source) {
    if(validateEmailAddress(emailAddress)) {
        $.ajax({
            type: 'POST',
            url: '/' + websiteId + '/control/addOrUpdateContact',
            data: 'email=' + emailAddress + '&mode=subscribe' + (typeof source !== 'undefined' ? '&emailSource=' + source + '&triggerEmail=' + source : ''),
            dataType: 'json',
            cache: false
        }).done(function(data) {
           callback(data.success);
        }).fail(function(data) {
            callback(false);
        });
    } else {
        callback(false);
    }
};

$.fn.formatPagination = function(diffSize) {
	diffSize = diffSize === undefined ? 3 : diffSize;
	var pages = $('.pagination li').length - 2;
	var currentPage = parseInt($('.pagination li.current').text());
	var lowerLimit = currentPage - (diffSize + 1);
	var upperLimit = currentPage + diffSize;

	if(lowerLimit < 0) {
		lowerLimit = 0;
	}
	if(upperLimit > pages) {
		upperLimit = pages;
	}
	if(currentPage - lowerLimit <= diffSize) {
		upperLimit = upperLimit + (diffSize - currentPage + 1);
	} else if(upperLimit - currentPage < diffSize) {
		lowerLimit = lowerLimit - (diffSize - (upperLimit - currentPage));
	}
	if(lowerLimit < 0) {
		lowerLimit = 0;
	}
	if(upperLimit > pages) {
		upperLimit = pages;
	}
	$('.pagination li').not('.pagination li:first').not('.pagination li:last').hide();
	$('.pagination').find('li.current').show().siblings(':lt('+upperLimit+'):gt('+lowerLimit+')').show();
};

$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name] !== undefined) {
			if (!o[this.name].push) {
				o[this.name] = [o[this.name]];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};

$.fn.capitalize = function(text) {
	return text.charAt(0).toUpperCase() + text.slice(1).toLowerCase();
};

$.fn.updateHeaderLogin = function() {
    var userFirstName = $.cookie('__ES_ll');

    if(userFirstName !== undefined && userFirstName != 'false' && userFirstName != 'true' && userFirstName != 'close') {
    	$('#jqs-login-container .logged-in-text').html('').append($('<span/>').text('').text('Welcome ' + userFirstName + '! '));
    	$('.jqs-login-button').off('click.loginLayer');
        $('.jqs-login-button').removeClass('jqs-login-button').removeAttr('data-reveal-id data-dest data-bnreveal').addClass('jqs-myAccountDropDownButton').attr({
        	'data-dropdown-target': 'dropdown-myAccount',
        	'data-dropdown-options': 'hover shadowed(' + (typeof websiteId != 'undefined' && websiteId == 'folders' ? 'foldersHeader' : 'main-header')  +') reverse-horizontal ignore-reverse-dropdown',
			'href': '/' + websiteId + '/control/account'
        }).text('My Account ').append(
        	$('<i />').addClass('fa fa-caret-down')
        );

		$('.jqs-firstName').html(userFirstName);
		createDropDownEvent($('.jqs-myAccountDropDownButton'));
		/*
		$('#jqs-login-container .logged-in-text').html('').append($('<span/>').text('').text('Welcome ' + userFirstName + '! ')).append('<span class="tablet-desktop-only-inline-block">Not ' + userFirstName + '?</span>');
        $('.jqs-login-button').removeClass('jqs-login-button').addClass('jqs-logout-button').attr('data-dest', 'logout').removeAttr('data-reveal-id').text('Log Out');
        $.bindLogoutClickEvent();
		*/

    }
};

/* BEGIN TOOLTIPS */
var toolTips = function() {
	var first_tooltip;

	var closeToolTip = function(element, speed, disable) {
		if (first_tooltip) {
			if (element.hasClass('jqs-show-once') || disable == true) {
				first_tooltip.tooltip('disable');
			}
			else {
				first_tooltip.tooltip('close');
			}
		}

		//clearTimeout(close_timeout);
		setTimeout(function() {
			if (element.hasClass('jqs-show-once') || disable == true) {
				element.tooltip('disable');
			}
			else {
				element.tooltip('close');
			}
		}, (speed !== undefined ? speed : 5000));
	};

	this.disableToolTip = function(element) {
		element.tooltip('disable');
	};

	this.setFirstTooltip = function(element) {
		first_tooltip = element;
	};

	this.createCloseEvent = function(element, target, speed) {
		element.on("click", function() {
			closeToolTip(target, (speed !== undefined ? speed : 0));
		});
	};

	this.openFirstTooltip = function() {
		if (first_tooltip) {
			first_tooltip.tooltip('open');
		}
	};

	this.newToolTip = function(element) {
		element.attr("class").match(/(jqui\-tooltip\-.*?)(?:\s|$)/i);
		$("." + RegExp.$1).tooltip({
			show: null,
			position: {
				at: "left top"
			},
			open: function(event, ui) {
				var element = $(this);

				$('[class*="jqui-tooltip-"]').each(function() {
					if (!($(this).attr('class') == element.attr('class'))) {
						closeToolTip($(this), 0);
					}
				});

				$('.ui-tooltip').append(
					$('<i>').addClass('fa fa-times').bind('click', function() {
						closeToolTip(element, 0, true);
					})
				);
				if (element.hasClass('jqui-bottom-tooltip')) {
					ui.tooltip.addClass('tooltip-bottom');
					ui.tooltip.css({ top: element.offset().top + getFullHeight(element) + 10});
				}
				else { // default
					ui.tooltip.addClass('tooltip-top');
					ui.tooltip.css({ top: element.offset().top - getFullHeight(ui.tooltip) - 10 });
				}

				if (element.hasClass('jqui-right-aligned-tooltip')) {
					ui.tooltip.addClass('arrow-right');
					ui.tooltip.css({ left: element.offset().left - getFullWidth(ui.tooltip) + getFullWidth(element)});
				}
				else {
					ui.tooltip.addClass('arrow-left');
				}

				closeToolTip(element);
			}
		});

		this.createCloseEvent(element, element, 0)
	};
};
/* END TOOLTIPS */

/* Fixed Content */
var fixedContent = function() {
	var current_fixed_element = false;

	var clearFixedElement = function() {
		$('.jqs-fixed-placeholder').remove();
		$(current_fixed_element).removeClass('fixed-header');
		$(current_fixed_element).css({
			'padding-left':'initial',
			'padding-right':'initial',
			'background-color':'initial',
			'border':'initial',
			'width':'initial'
		});
		$(current_fixed_element).find('.jqs-fixed-content').children(':first-child').removeClass('no-margin');
		current_fixed_element = false;
	}

	var doFixedHeader = function(window_element) {
		fixed_element_offset = current_fixed_element ? parseInt(current_fixed_element.offset().top) : 0;
		var new_fixed_element;

		if (current_fixed_element && $(current_fixed_element).next().length > 0 && parseInt($(current_fixed_element).next().offset().top) > fixed_element_offset) {
			clearFixedElement();
		}

		$('.jqs-fixed').each(function() {
			if (parseInt(window_element.scrollTop()) > parseInt($(this).offset().top)) {
				clearFixedElement();
				new_fixed_element = $(this);
			}
		});

		if (new_fixed_element && new_fixed_element.html() != (current_fixed_element ? current_fixed_element.html() : '')) {
			current_fixed_element = new_fixed_element;
			if (current_fixed_element.data('fixed-options')) {
				current_fixed_element.data('fixed-options').match(/^(?:.*\s+)?border\-bottom\-(.*?)(?:\s+.*)?$/gi);
			}
			var bottom_border = RegExp.$1;

			current_fixed_element.css({
				'left':parseInt(current_fixed_element.offset().left + parseInt(current_fixed_element.find('.jqs-fixed-content').children(':first-child').css('margin-left'))) + 'px',
				'background-color':current_fixed_element.find('.jqs-fixed-content').children(':first-child').css('background-color'),
				'width':parseInt(current_fixed_element.find('.jqs-fixed-content').children(':first-child').outerWidth())  + 'px',
				'border-bottom':(bottom_border ? '1px solid #' + bottom_border : 'initial')
			});

			current_fixed_element.after(
				$('<div />').addClass('jqs-fixed-placeholder').html(' ').css({
					'height':parseInt(current_fixed_element.find('.jqs-fixed-content').innerHeight()) + 'px',
					'visibility':'hidden'
				})
			);

			current_fixed_element.addClass('fixed-header').children('.jqs-fixed-content').children(':first-child').addClass('no-margin');
		}
	};

	$(window).off('resize.fixedHeader').on('resize.fixedHeader', function() {
		if (!ignoreIE()) {
			clearFixedElement();
			doFixedHeader($(this));
		}
	}).off('load.fixedHeader').on('load.fixedHeader', function() {
		doFixedHeader($(this));
	}).off('scroll.fixedHeader').on('scroll.fixedHeader', function() {
		doFixedHeader($(this));
	});
};

var bigNameSpinner = function(element, create, fullScreen, size, borderWidth, bgColor, text, textColor, timeToKill) {
    if(getIEVersion() != 8) {
		function removeSpinner(element) {
			if (typeof element.attr('bns-spinner_id') != 'undefined') {
				var spinnerId = element.attr('bns-spinner_id');
				element.removeAttr('bns-spinner_id').find('[bns-spinner_id="' + spinnerId + '"]').remove();
			}
		}
        var rand = 'spinner_' + Math.floor((Math.random() * 99999) + 1);
        //element.addClass(rand);

		/*
        element.find('div[bns-spinner_id="' + spinnerId + '"].spinner-shade').remove();
        element.find('div[bns-spinner_id="' + spinnerId + '"].spinner').remove();
        element.find('div[bns-spinner_id="' + spinnerId + '"].spinner-text').remove();
		*/

		removeSpinner(element);

        if(create) {
            var fullScreen = (typeof fullScreen !== 'undefined' && fullScreen != null ? fullScreen : false);
            var size = (typeof size !== 'undefined' && size != null ? size : 100) + 'px';
            var borderWidth = (typeof borderWidth !== 'undefined' && borderWidth != null ? borderWidth : 3) + 'px';
            var bgColor = (typeof bgColor !== 'undefined' && bgColor != null ? bgColor : '#000000');
            var text = (typeof text !== 'undefined' && text != null ? text : 'loading...');
            var textColor = (typeof textColor !== 'undefined' && textColor != null ? textColor : 'ffffff');
			var timeToKill = (typeof timeToKill !== 'undefined' && timeToKill != null ? timeToKill : 10000);
			element.attr('bns-spinner_id', rand);
            element.append(
                $('<div/>').attr('bns-spinner_id', rand).addClass('spinner-shade').css({
                    'background-color': '#' + bgColor
                })
            ).append(
                $('<div/>').attr('bns-spinner_id', rand).addClass('spinner')
            ).append(
                $('<span/>').attr('bns-spinner_id', rand).addClass('spinner-text').html(text)
            );

            try {
                document.styleSheets[1].insertRule('.spinner:after { width:' + size + ' !important; height:' + size + ' !important; border-width:' + borderWidth + ' !important;', 0);
            } catch (e) {
                // Do Nothing
            }
            $('.spinner-text').css({
                'color': '#' + textColor,
                'top': 'calc(50% - ' + parseInt($('.spinner-text').height() / 2) + 'px)',
                'left': 'calc(50% - ' + parseInt($('.spinner-text').width() / 2) + 'px)'
            });

            if (fullScreen) {
                $('.spinner-shade').css({ 'position':'fixed' });
                $('.spinner-text').css({ 'position':'fixed' });
                $('.spinner').css({ 'position':'fixed' });
            }

            waitForFinalEvent(function(){
                element.spinner(false);
                //element.removeClass(rand);
            }, timeToKill, 'spinner_id_' + rand);
        }
    }
}
/* Fixed Content */
$.fn.spinner = function(create, fullScreen, size, borderWidth, bgColor, text, textColor, timeToKill) {
	bigNameSpinner($(this), create, fullScreen, size, borderWidth, bgColor, text, textColor, timeToKill);
}

$.fn.bigNameSpinner = function(create, fullScreen, size, borderWidth, bgColor, text, textColor, timeToKill) {
    bigNameSpinner($(this), create, fullScreen, size, borderWidth, bgColor, text, textColor, timeToKill);
};

/* Truncate Customer Reviews */
var truncateReview = function() {
    $('.cr-review').each(function () {
        var childElement = $(this).find('p');
        var textFit = false;
        var safetyCounter = 1000;

        while (textFit == false && safetyCounter > 0) {
            safetyCounter--;
            if ($(this).height() < $(childElement).height()) {
                var adjustedText = $(childElement).html().replace(/(?:\n|\r)/g, '');
                var newText = '';
                if (adjustedText.match(/(.*?)\s[^\s]+(?:\s|)(?:\.\.\.|)$/) != null) {
                    newText = adjustedText.match(/(.*?)\s[^\s]+(?:\s|)(?:\.\.\.|)$/)[1];
                }
                $(childElement).html(newText + '...');
            } else {
                textFit = true;
            }
        }
    });
};

// Envelopes Mobile Nav Dropdown Clicks
$(document).ready(function(){
	$('[bns-mobilenavdropdown]').on('click', function(){
		$(this).next('.mobileNavDropdown').toggleClass('hidden');
		if($(this).find('i').hasClass('fa-angle-right') == true) {
			$(this).find('i').removeClass('fa-angle-right').addClass('fa-angle-down');
		} else {
			$(this).find('i').removeClass('fa-angle-down').addClass('fa-angle-right');
		}
	});

	if (typeof navigator != 'undefined' && typeof navigator.cookieEnabled != 'undefined' && !navigator.cookieEnabled) {
		$('body').css('margin-top', '30px').prepend(
			$('<div />').css({
				'font-weight': 'bold',
				'color': '#ffffff',
				'background-color': '#ff0000',
				'padding': '5px',
                'text-align': 'center',
                'position': 'fixed',
                'top': '0px',
                'left': '0px',
                'width': '100%',
                'z-index': '9999999'
			}).html('You do not have cookies enabled.  Please enable them and refresh the page to continue with the site.')
		);
	}
});

function GTMCartUpdate() {
	if(localStorageEnabled) {
		var addToCartData = localStorage.addToCartData;
		if (typeof addToCartData !== "undefined") { 
			addToCartData = JSON.parse(addToCartData);
			if (typeof addToCartData.cartItems !== "undefined") {
				var cartItems = [];

				for (var i = 0; i < addToCartData.cartItems.length; i++) {
					cartItems.push({
						"name": addToCartData.cartItems[i].itemDescription,
						"id": addToCartData.cartItems[i].productId,
						"price": addToCartData.cartItems[i].unitPrice,
						"quantity": addToCartData.cartItems[i].quantity
					});
				}

				// Clear before hand to fix an issue with not properly updating...
				dataLayer.push({
					"products": undefined
				});

				dataLayer.push({
					"products": cartItems,
					"event": "cartUpdate"
				});
			}
		}
	}
}