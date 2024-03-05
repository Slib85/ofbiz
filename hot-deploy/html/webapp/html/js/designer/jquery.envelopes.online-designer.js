// Copyright 2014 Envelopes.com - All rights reserved
//Raygun.init('dmSA6yRdNZwhYLhrBFXChw==', { allowInsecureSubmissions: true });

(function($) {
	var OnlineDesigner = function(element, options) {
		var elem = $(element);
		var self = this;

		/* DESIGN SETTINGS */
		var settings = $.extend({
			debug: false,
			index: 0,
			activeDesign: 0,
			animate: false,
			backgroundColor: '#FFFFFF',
			productId: '',
			spotColors: '',
			templateHeight: 0,
			templateWidth: 0,
			colorMap: {},
			designs: {},
			product: null,
			skipFirstColorWarning: false,
			decorators:{
				verticalFolded:false,
				folded:false
			},
			loadFromProduct: true
		}, options || {});

		/* GLOBAL VARIABLES */
		var launchedFrom = window.location.href;
		var templateUrl = 'http://actionenvelopew2p.scene7.com/is/agm/ActionEnvelope/';
		var staticWidth = 100;
		var staticHeight = 100;
		var borderReduction = 1;
		var showItems = false;
		var showGrid = false;
		var defaultDesign = settings.activeDesign;
		var popupLocations = {};
		var designApproved = false;
		var originalProductImage = null;
		var lastNumColors = { 'BACK' : 0, 'FRONT' : 0, 'WHITE_FRONT' : false, 'WHITE_BACK' : false};
		var lastImage = null;
		var allSpotColors = {};
		var minAddresses = 25;
		var isUploadedDesign = getUrlParameters('ugcId', '', false);
		var imageQuality = { 'fmt' : 'jpeg', 'qlt' : '90' };
		var fonts = ($('body').data('product').getProduct($('body').data('slider').getActiveSlideSetIndex()).type == 'STAMP' ? [
			'Abby',
			'Acknowledgement',
			'Adobe Caslon Pro',
			'Aphrodite',
			'Archive Penman',
			'Barrio',
			'Bickley Script',
			'Bombshell',
			'Bonveno',
			'Brandon Grotesque',
			'Broadway',
			'Cabrito',
			'Campbell',
			'Candy Round',
			'Cantoni',
			'Carol Etched',
			'Caslon',
			'Century Schoolbook',
			'Charlesworth',
			'Cinzel',
			'Cipher',
			'Circle Mono',
			'Courier Prime',
			'Dancing Script',
			'Diamond Mono',
			'Edwardian',
			'Emily Bamboo',
			'Emily Greek Key',
			'Emily Signature Mono',
			'Euphorigenic',
			'Fantasy Heather',
			'Fantasy Kirsten',
			'Fantasy Nora 2',
			'Fantasy Nora',
			'Foglihten',
			'Futura',
			'Garamond',
			'Gatlinggun',
			'Geo Mono',
			'Geo',
			'Grand Hotel',
			'Great Vibes',
			'Hana',
			'Harrington',
			'Kartago',
			'Kathy',
			'Kenosha',
			'Lobster',
			'Luella',
			'Maximo',
			'Novak Spring',
			'Novak',
			'Ornamental',
			'Parisian',
			'Pendulum',
			'Peoni',
			'Pompiere',
			'Pupcat',
			'Renaissance',
			'Rochester',
			'Roman',
			'Spinwerad',
			'Sweetheart',
			'Thirsty',
			'Times',
			'Vine Mono',
			'Vingy',
			'Vintage Block',
			'Windsong',
			'Wishes'
		] : [
			'AddCityboy',
			'Adobe Caslon Pro',
			'Adobe Caslon Pro Bold',
			'Adobe Caslon Pro Italic',
			'Adobe Fangsong Std R',
			'Adobe Garamond Pro',
			'Adobe Garamond Pro Bold',
			'Adobe Garamond Pro Italic',
			'Adobe Heiti Std R',
			'Adobe Jenson Pro',
			'Adobe Kaiti Std R',
			'Adobe Ming Std L',
			'Adobe Myungjo Std M',
			'Adobe Song Std L',
			'Adstien Normal',
			'Allura',
			'Alte Haas Grotesk',
			'ALS Script',
			'Ambient',
			'American Typewriter',
			'Angelina',
			'AppleMyungjo',
			'Arno Pro Display',
			'Arnprior',
			'BabelSans',
			'BabelSans Italic',
			'BakerSignet BT',
			'Baloney',
			'Batang',
			'Batik Regular',
			'Bauderie Script SSi',
			'Bauhaus 93',
			'Bauhaus Light',
			'Bickham Script Pro Bold',
			'Bickham Script Pro Regular',
			'Birch Std',
			'BlackChancery',
			'Brandon Grotesque Regular',
			'Brush Script Std',
			'Cabletv',
			'Calibri',
			'Calibri Bold',
			'Calibri Italic',
			'Calligrapher',
			'Cambria',
			'Cambria Bold',
			'Cambria Italic',
			'Candara',
			'Celtic Bold',
			'Cezanne',
			'Chalkboard',
			'Chalkboard Bold',
			'Chaparral Pro',
			'Chaucer',
			'Chisel',
			'ChopinScript',
			'Comic Sans MS',
			'Consolas',
			'Constantia',
			'Copperplate',
			'Corbel',
			'Cornerstone',
			'Courier New',
			'Courier New Bold',
			'Courier New Italic',
			'Crillee',
			'D3 Archism',
			'Diploma',
			'Dream Orphans',
			'Eccentric Std',
			'Echelon',
			'Echelon Italic',
			'Eleganza',
			'Elisia',
			'Emblem',
			'English',
			'Florence Regular',
			'Franklin Gothic Medium',
			'Freehand591',
			'Fritz-Quad',
			'Garamond Premr Pro',
			'Garamond Premr Pro Italic',
			'Garamond Premr Pro Smbd',
			'Georgia',
			'Georgia Italic',
			'GeosansLight',
			'Giddyup Std',
			'Goodfish',
			'Gotham Black',
			'HandelGotDBol',
			'HandelGotDLig',
			'HandScript',
			'Helsinki Light',
			'Hobo Std',
			'HoratioDLig',
			'Impact',
			'JaneAusten',
			'Labtop',
			'Lariat',
			'LatinoSamba',
			'Letter Gothic Std',
			'Letter Gothic Std Bold',
			'Lithos Pro Regular',
			'Lucida Sans',
			'Magical Wands',
			'Mesquite Std',
			'MicrogrammaDBolExt',
			'Microsoft Sans Serif',
			'Minion Pro',
			'Mrs Eaves OT',
			'Myriad Pro',
			'Myriad Pro Bold',
			'Myriad Pro CondItalic',
			'Nueva Std Cond',
			'OCR A Std',
			'Orator Std',
			'Plantagenet Cherokee',
			'Poor Richard',
			'Poplar Std',
			'Pushkin',
			'Radial',
			'Radiated Pancake',
			'Radii',
			'Renaissance',
			'Riesling',
			'Rosewood Std Regular',
			'Sabadoo',
			'Sabertooth',
			'Sable Lion',
			'Saccule',
			'Sachem',
			'Sachem Bold',
			'Sansation',
			'Sansation Bold',
			'Silom Regular',
			'Sofia',
			'Steiner',
			'Stencil Std',
			'T4C Beaulieux',
			'Tag LET',
			'Tahoma',
			'Tahoma Bold',
			'Times New Roman',
			'Times New Roman Bold',
			'Times New Roman Italic',
			'Trajan Pro',
			'Trajan Pro Bold',
			'Tycho',
			'Verdana',
			'Verdana Bold',
			'Verdana Italic',
			'Virtue',
			'Vivacious',
			'Walkway Bold',
			'Walkway Oblique',
			'Walkway RevOblique',
			'Windsong'
		]);

		var colors;

		if ($('body').data('product').getProduct($('body').data('slider').getActiveSlideSetIndex()).sku == 'STAMP-01' || $('body').data('product').getProduct($('body').data('slider').getActiveSlideSetIndex()).sku == 'STAMP-03' || $('body').data('product').getProduct($('body').data('slider').getActiveSlideSetIndex()).sku == 'STAMP-05' || $('body').data('product').getProduct($('body').data('slider').getActiveSlideSetIndex()).sku == 'STAMP-07') {
			colors = [
				{"name":"Black", "hex":"#000000", "knockout":"#FFFFFF"},
				{"name":"Turquoise", "hex":"#0E727C", "knockout":"#FFFFFF"},
				{"name":"Royal Blue", "hex":"#0B51B8", "knockout":"#FFFFFF"},
				{"name":"Poppy Orange", "hex":"#FD7923", "knockout":"#FFFFFF"},
				{"name":"Plum Purple", "hex":"#625EC0", "knockout":"#FFFFFF"},
				{"name":"Lime Green", "hex":"#61BF57", "knockout":"#FFFFFF"},
				{"name":"Holiday Red", "hex":"#EF2D3F", "knockout":"#FFFFFF"},
				{"name":"Holiday Green", "hex":"#169874", "knockout":"#FFFFFF"},
				{"name":"Grapefruit Pink", "hex":"#DF4A7E", "knockout":"#FFFFFF"},
				{"name":"Espresso Brown", "hex":"#562E1A", "knockout":"#FFFFFF"},
				{"name":"Hunter Green", "hex":"#0E7839", "knockout":"#FFFFFF"},
				{"name":"Cabernet", "hex":"#9D1E34", "knockout":"#FFFFFF"}
			];
		} else if ($('body').data('product').getProduct($('body').data('slider').getActiveSlideSetIndex()).sku == 'STAMP-02' || $('body').data('product').getProduct($('body').data('slider').getActiveSlideSetIndex()).sku == 'STAMP-04' || $('body').data('product').getProduct($('body').data('slider').getActiveSlideSetIndex()).sku == 'STAMP-06' || $('body').data('product').getProduct($('body').data('slider').getActiveSlideSetIndex()).sku == 'STAMP-08') {
			colors = [
				{"name":"Black", "hex":"#000000", "knockout":"#FFFFFF"},
				{"name":"Cherry Red", "hex":"#F7202C", "knockout":"#FFFFFF"},
				{"name":"Espresso Brown", "hex":"#6D3816", "knockout":"#FFFFFF"},
				{"name":"Grapefruit", "hex":"#E6447F", "knockout":"#FFFFFF"},
				{"name":"Marsala", "hex":"#B85D5C", "knockout":"#FFFFFF"},
				{"name":"Navy Blue", "hex":"#0E1D44", "knockout":"#FFFFFF"},
				{"name":"Royal Blue", "hex":"#1447B8", "knockout":"#FFFFFF"},
				{"name":"Titanium Gray", "hex":"#9EA2A3", "knockout":"#FFFFFF"},
				{"name":"Turquoise", "hex":"#076E7C", "knockout":"#FFFFFF"}
			];
		} else {
			colors = [
				{"name":"Midnight Black", "hex":"#000000", "knockout":"#FFFFFF"},
				{"name":"Deep Purple", "hex":"#2e1b46", "knockout":"#FFFFFF"},
				{"name":"Navy", "hex":"#002856", "knockout":"#FFFFFF"},
				{"name":"Pool", "hex":"#0082ca", "knockout":"#FFFFFF"},
				{"name":"Baby Blue", "hex":"#bad2dd", "knockout":"#000000"},
				{"name":"Seafoam", "hex":"#98d2dd", "knockout":"#000000"},
				{"name":"Teal", "hex":"#008996", "knockout":"#FFFFFF"},
				{"name":"Racing Green", "hex":"#005740", "knockout":"#FFFFFF"},
				{"name":"Bright Green", "hex":"#009946", "knockout":"#FFFFFF"},
				{"name":"Limelight", "hex":"#a1d784", "knockout":"#000000"},
				{"name":"Wasabi", "hex":"#dbe444", "knockout":"#000000"},
				{"name":"Citrus", "hex":"#f1ed74", "knockout":"#000000"},
				{"name":"Lemonade", "hex":"#ede59b", "knockout":"#000000"},
				{"name":"Nude", "hex":"#f3dbb3", "knockout":"#000000"},
				{"name":"Silver", "hex":"#c0c0c0", "knockout":"#000000"},
				{"name":"White", "hex":"#ffffff", "knockout":"#000000"},
				{"name":"Sunflower", "hex":"#fddd3f", "knockout":"#000000"},
				{"name":"Gold", "hex":"#ffd700", "knockout":"#000000"},
				{"name":"Pencil", "hex":"#f8bf00", "knockout":"#000000"},
				{"name":"Mandarin", "hex":"#ff9016", "knockout":"#FFFFFF"},
				{"name":"Grocery Bag", "hex":"#caaa7b", "knockout":"#000000"},
				{"name":"Ochre", "hex":"#d57f00", "knockout":"#000000"},
				{"name":"Rust", "hex":"#bc4800", "knockout":"#FFFFFF"},
				{"name":"Terracotta", "hex":"#993921", "knockout":"#FFFFFF"},
				{"name":"Tangerine", "hex":"#e1261c", "knockout":"#FFFFFF"},
				{"name":"Ruby Red", "hex":"#c20430", "knockout":"#FFFFFF"},
				{"name":"Garnet", "hex":"#a32136", "knockout":"#FFFFFF"},
				{"name":"Wine", "hex":"#8c3d45", "knockout":"#FFFFFF"},
				{"name":"Vintage Plum", "hex":"#84286b", "knockout":"#FFFFFF"},
				{"name":"Magenta", "hex":"#cb007c", "knockout":"#FFFFFF"},
				{"name":"Candy Pink", "hex":"#f7dbe0", "knockout":"#000000"},
				{"name":"Wisteria", "hex":"#7474c1", "knockout":"#FFFFFF"},
				{"name":"Lilac", "hex":"#b5b4e0", "knockout":"#000000"},
				{"name":"Pastel Gray", "hex":"#cacac8", "knockout":"#000000"},
				{"name":"Slate", "hex":"#8d9b9b", "knockout":"#000000"},
				{"name":"Smoke", "hex":"#6c6864", "knockout":"#FFFFFF"},
				{"name":"Tobacco", "hex":"#88674d", "knockout":"#FFFFFF"},
				{"name":"Chocolate", "hex":"#6f5d51", "knockout":"#FFFFFF"},
				{"name":"Moss", "hex":"#6b5a24", "knockout":"#FFFFFF"},
				{"name":"Avocado", "hex":"#90993f", "knockout":"#000000"}
			];
		}

		// Designs in Studio
		var designs = {};
		$.each(settings.designs, function(k,v){
			designs[Object.keys(designs).length] = {
				templateId : v.templateId,
				templateType : v.templateType,
				templateSide : v.templateSide,
				elements : [],
				hiddenElements: [],
				designHistory : [],
				unEditableColors: [],
				approvedHistoryIndex: 0,
				customCount : 0,
				lastElementId : null,
				scene7url : null,
				cleanAddressUrl: null,
				loadedTemplate : false,
				addresses : [],
				activeAddress : 0,
				addressBookId : null
			}
		});

		// Design Limitations
		var limitationsByProduct = {
			STAMP: ['addText', 'addImage', 'fontFamily', 'fontSize', 'textAlign', 'textSpacing', 'lineSpacing', 'rotation']
		}

		//public functions
		this.show = function(designId, discreet) {
			show(designId, discreet);
		};

		this.idealZoom = function() {
			idealZoom();
		};

		this.saveDesign = function() {
			return saveDesign();
		};

		this.loadDesign = function() {
			return loadDesign();
		};

		this.getSettings = function() {
			return settings;
		};

		this.processPricing = function(showMessage) {
			processPricing(showMessage);
		};

		this.paintEditor = function(designId) {
			paintEditor(designId);
		};

		this.getFonts = function() {
			return fonts;
		};

		this.exitValidation = function(forward) {
			exitValidation(forward);
		};

		//for now this will only be for addressing
		this.showTextEditor = function(elementId, designerId) {
			$('#designer_' + designerId).find('#' + elementId).trigger('click');
		};

		this.removeAddressing = function() {
			removeAddressing();
		};

		this.showAddressing = function(designId) {
			showAddressing((typeof designId !== 'undefined' && designId !== null) ? designs[designId] : settings.activeDesign);
		};

		this.updateActiveAddress = function(designId, int, update) {
			if(designId !== null) {
				designs[designId].activeAddress+=int;
			} else {
				designs[settings.activeDesign].activeAddress+=int;
			}
			if(update) {
				paintEditor((designId !== null) ? designId : settings.activeDesign);
			}
		};

		this.getAddressingData = function(designId, width) {
			var result = {};
			approve(false);
			generateScene7Image(
				generateParamsForDesign((designId !== null) ? designs[designId] : designs[settings.activeDesign]),
				width,
				function(data) {
					result = { 'src' : ((data.type === 'url') ? '' : 'data:image/' + imageQuality.fmt + ';base64,') + data.data };
				},
				function(XMLHttpRequest, textStatus, errorThrown) {
					//Raygun.send(new Error('There was an error trying to process: ' + designs[settings.activeDesign].templateId), [params]);
				},
				false
			);

			$.each(designs[(designId !== null) ? designId : settings.activeDesign].elements, function(k, v) {
				if(v.id == 'RichText_addressing') {
					result['offsetTop'] = getNewSize(v.y, true, width);
				}
			});

			result['total'] = countAddresses();
			result['activeAddress'] = (designId !== null) ? designs[designId].activeAddress : designs[settings.activeDesign].activeAddress;
			result['addressBookId'] = (designId !== null) ? designs[designId].addressBookId : designs[settings.activeDesign].addressBookId;

			return result;
		};

		//private functions
		var updateStampElements = function(designId, hex) {
			for (var j = 0; j < designs[designId].elements.length; j++) {
				if(designs[designId].elements[j].id.indexOf('TEXT_') >= 0) {
					designs[designId].elements[j].color = hex;
				}
				else if(designs[designId].elements[j].id.indexOf('COLOR_') >= 0) {
					designs[designId].elements[j].pathData[0].color = hex;
				}
				designs[designId].elements[j].changed = true;
			}
		};

		var saveDesign = function() {
			var cleanedData = cleanDesignForStorage();
			var data = {
				'scene7Data': { 'designs': cleanedData.designs, 'settings': cleanedData.settings },
				'itemComments' : '',
				'inkColors' : settings.spotColors
			};
			settings.product.s7Data = data;
		};

		var loadDesign = function() {
			if(typeof settings.product.s7Data !== 'undefined' && typeof settings.product.s7Data.scene7Data !== 'undefined') {
				if(typeof settings.product.s7Data.scene7Data.settings !== 'undefined') {
					delete settings.product.s7Data.scene7Data.settings.obj;
					delete settings.product.s7Data.scene7Data.settings.product;
					delete settings.product.s7Data.scene7Data.settings.backgroundColor;
					$.extend(settings, settings.product.s7Data.scene7Data.settings, {});
				}

				if(typeof settings.product.s7Data.scene7Data.designs != 'undefined') {
					$.extend(designs, settings.product.s7Data.scene7Data.designs, {});;
					$.each(designs, function(k, v) {
						$.each(v.elements, function(k2, v2) {
							//this is to support legacy saved designs
							if(typeof v2.pathData === 'undefined') {
								v2.pathData = [{
									pathType: v2.pathType,
									color: v2.color,
									weight: v2.weight,
									miterLimit: v2.miterLimit,
									joints: v2.joints,
									caps: v2.caps
								}];
								delete v2.pathType;
								delete v2.weight;
								delete v2.miterLimit;
								delete v2.joints;
								delete v2.caps;
							}

							//since we delete all addressing from the data storage object, we need to re-apply it if the person reloads the design
							if(v2.id == 'RichText_addressing' && (typeof v2.deleted != 'undefined' && v2.deleted == false)) {
								rebuildAddressing(v.addressBookId, false);
							}
						});
						if(typeof v.unEditableColors === 'undefined') {
							v.unEditableColors = [];
						}
					});
				}

				if(typeof settings.product.s7Data.itemComments !== 'undefined') {
					//TODO set comments if available
				}
				log('Loaded from server', settings.debug);
			} else {
				log('Must provide ID.', settings.debug);
			}
		};

		//method cleans the design object and removes unnecessary data thats not needed for storage
		var cleanDesignForStorage = function() {
			var tempSettings = $.extend(true, {}, settings);
			tempSettings.spotColors = '';
			tempSettings.product = null;

			var tempDesign = $.extend(true, {}, designs);
			Object.keys(tempDesign).forEach(function(key) {
				//loop active editable elements
				tempDesign[key].elements.forEach(function(element) {
					element.tip = null; //delete tooltips TODO reload tip on load of saved design

					//lets create the addressing blank url if it needs to be there
					if(element.id == 'RichText_addressing' && (typeof element.deleted != 'undefined' && element.deleted == false)) {
						tempDesign[key].cleanAddressUrl = (generateUrlFromParams(generateParamsForDesign(tempDesign[key], true)));
					}
				});

				//loop hidden uneditable elements
				tempDesign[key].hiddenElements.forEach(function(element) {
					element.tip = null; //delete tooltips TODO reload tip on load of saved design
				});

				tempDesign[key].addresses.length = 0;
				tempDesign[key].designHistory.length = 0;
			});
			return { 'settings': tempSettings, 'designs': tempDesign };
		};

		//if there is due to template change lets rebuild it
		var rebuildAddressing = function(addressBookId, repaint) {
			loadAddressBook(addressBookId, false);
			applyAddresses(null, repaint);
		}

		//remove addressing
		var removeAddressing = function() {
			$.each(designs, function(k, v) {
				$.each(v.elements, function(k2, v2) {
					if(v2.id == 'RichText_addressing') {
						v2.deleted = true;
						designs[k].addresses = [];
						designs[k].activeAddress = 0;
						designs[k].addressBookId = null;
					}
				});
			});
			paintEditor(settings.activeDesign);
		};

		var showLoginRegister = function(className) {
			$('body').find(className).bPopup({
				modal: false,
				escClose: false,
				transition: "fadeIn",
				speed: 100,
				positionStyle: 'fixed',
				appending: true
			}).draggable({
				handle: '.head'
			}).find('.close').on('click', function(){
				$(this).closest(className).draggable('destroy').bPopup().close();
			});
		};

		var submitLoginRegister = function(element, uri) {
			$.ajax({
				url: '/' + websiteId + '/control/' + uri,
				dataType: 'json',
				type: 'POST',
				data: {
					USERNAME: $(element).closest('.login').find('input[name="USERNAME"]').val(),
					PASSWORD: $(element).closest('.login').find('input[name="PASSWORD"]').val()
				},
				context: element
			}).done(function(data) {
				if(typeof data.success != 'undefined' && data.success == true) {
					$(element).closest('.loginRegister').draggable('destroy').bPopup().close();
					$(element).parent().find('.loginError').html('').css({display: 'none'});
				} else if(typeof data.errors != 'undefined') {
					$(element).parent().find('.loginError').html(data.errors.error).css({display: 'block'});
				}
				getUserLogin();
				loadAddressBooks();
			});
		};

        var showSaveDesignConfirmation = function(className) {
            $('body').find(className).bPopup({
                modal: false,
                escClose: false,
                transition: "fadeIn",
                speed: 100,
                positionStyle: 'fixed',
                appending: false
            }).draggable({
                handle: '.head'
            }).find('.close').on('click', function(){
                $(this).closest(className).draggable('destroy').bPopup().close();
            });
        };

        var submitLoginRegisterAndSaveDesign = function(element, uri) {
            $.ajax({
                url: '/' + websiteId + '/control/' + uri,
                dataType: 'json',
                type: 'POST',
                data: {
                    USERNAME: $(element).closest('.login').find('input[name="USERNAME"]').val(),
                    PASSWORD: $(element).closest('.login').find('input[name="PASSWORD"]').val()
                },
                context: element
            }).done(function(data) {
                if(typeof data.success != 'undefined' && data.success == true) {
                    $(element).closest('.loginRegisterAndSaveDesign').draggable('destroy').bPopup().close();
                    $(element).parent().find('.loginError').html('').css({display: 'none'});
                } else if(typeof data.errors != 'undefined') {
                    $(element).parent().find('.loginError').html(data.errors.error).css({display: 'block'});
                }
                getUserLogin();
                if($('body').data('product').saveProject() != '') {
                    showSaveDesignConfirmation('.saveDesignConfirmation');
                }
            });
        };

        var saveCurrentDesign = function(className) {
            if(getUserLogin()) {
                if($('body').data('product').saveProject() != '') {
                    showSaveDesignConfirmation('.saveDesignConfirmation');
                }
            } else {
                showLoginRegister(className);
            }
        };

		var getUserLogin = function() {
			var isLoggedIn = false;
			$.ajax({
				url: '/' + websiteId + '/control/getUserLogin',
				dataType: 'json',
				type: 'POST',
				data: {},
				async: false,
				context: document.body
			}).done(function(data) {
				if(typeof data.userLogin != 'undefined' && data.userLogin != null) {
					$('.askLogin').hide();
					$('.showLogin').html('You are logged in as <strong>' + data.userLogin + '</strong>, all data will be saved to this account.').show();
					isLoggedIn = true;
				} else {
					$('.askLogin').show();
					$('.showLogin').hide();
				}
			});

			return isLoggedIn;
		};

		var Pulldown =function(trigger, pulldown) {
			this.trigger  = $(trigger);
			this.pulldown = $(pulldown);

			this.setup = function() {
				this.elements = this.pulldown.find('a');
				this.title = this.trigger.find('span').first();
				this.defaultTitle = this.title.html();
				var self = this;
				this.trigger.on('click', function(){self.show();});
			};

			this.show = function() {
				this.pulldown.show();
				this.pulldown.css({'z-index': 150});
				var self = this;
				this.pulldown.on('click', function(){});
				this.pulldown.on('mouseleave', function(){self.hide();});
			};

			this.hide = function() {
				this.pulldown.hide();
				this.pulldown.off('click');
				this.pulldown.off('mouseleave');
			};

			this.setup();
		};

		var FontPulldown = function(container, callback){
			this.container = $(container);
			this.trigger = this.container.find('li.fontSelect');
			this.pulldown = this.container.find('div.pulldown');

			Pulldown.call(this, this.trigger, this.pulldown);

			this.parent_setup = this.setup;
			this.setup = function() {
				this.parent_setup(this);
				this.fontInput = this.pulldown.find('input.textFont');
				var self = this;
				this.trigger.on('click', function(event){self.show(event);});
				this.elements.each(function() {
					$(this).unbind().on('click', function(event) {
						self.select(this,event);
						self.hide();
						event.preventDefault();
					});
				});
			};

			this.select = function(option, event) {
				GoogleAnalytics.trackEvent('Product Page', 'Scene7v2', 'Font Family');
				var title = $(option).find('img').attr('alt');
				this.selectedTitle = title;
				this.title.html(title);
				this.fontInput.val(title);
				event.preventDefault();
				if (typeof callback === 'function'){
					callback()
				}
			};

			this.setup();
		};

		var FontColorPulldown = function(container, callback){
			this.container = $(container);
			this.trigger = this.container.find('div.colorSelected');
			this.pulldown = this.container.find('div.colorSelector');

			Pulldown.call(this, this.trigger, this.pulldown);

			this.parent_setup = this.setup;
			this.setup = function() {
				this.parent_setup(this);
				this.fontInput = (this.pulldown.find('input.fontColor').length > 0) ? this.pulldown.find('input.fontColor') : this.pulldown.find('input.pathColor');

				var self = this;
				this.trigger.on('click', function(event){self.show(event)});
				this.elements.each(function() {
					$(this).unbind().on('click', function(event) {
						self.select(this,event);
						event.preventDefault();
					});
				});
			};

			this.select = function(option, event) {
				GoogleAnalytics.trackEvent('Product Page', 'Scene7v2', 'Font Color');
				var hexValue   = '#000000';
				hexValue = "#"+$(option).attr('class').replace("colorSelect_","");
				this.trigger.css({backgroundColor: hexValue});
				this.fontInput.val(hexValue);
				this.hide();
				event.preventDefault();
				if (typeof callback === 'function'){
					callback()
				}
			};
			this.setup();
		};

		var getScaleMultiplier = function(newWidth) {
			return (newWidth != null) ? newWidth/settings.templateWidth : staticWidth/settings.templateWidth;
		};

		var getNewSize = function(size, observeBorder, newWidth) {
			var newSize = size*getScaleMultiplier((newWidth !== 'undefined') ? newWidth : null);
			if(observeBorder) {
				newSize = newSize-borderReduction;
			}
			return newSize;
		};

		var getOriginalSize = function(size, observeBorder) {
			var newSize = size/getScaleMultiplier(null);
			if(observeBorder) {
				newSize = newSize+borderReduction;
			}
			return newSize;
		};

		var historyBack = function() {
			var history = designs[settings.activeDesign].designHistory.pop();
			if(typeof history === 'undefined') {
				showWarningMessage('You cannot revert your design any further.')
				return;
			}
			$.extend(designs[settings.activeDesign], history);
			initEditor();
			addEditorElements(settings.activeDesign);
			setBleedMargins();
			paintEditor(settings.activeDesign);
			$('.odPop').each(function() {
				$('#'+$(this).data('element_')).trigger('click');
			});
		};

		var createHistory = function(){
			clearApproval();
			var history = $.extend(true, {}, designs[settings.activeDesign]);
			delete history.designHistory;
			designs[settings.activeDesign].designHistory.push(history);
		};

		var clearApproval = function(){
			elem.find('#s7Initials').val('');
			elem.find('#agreeCopy').prop('checked', false);
			elem.find('#agreeTC').prop('checked', false);
			designApproved = false;
		};

		var approve = function(destroy) {
			saveDesign();
			designApproved = true;

			if(destroy) {
				$('#imageEditPop, #textEditPop, #colorEditPop').each(function(k,v) {
					if($(v).css('display') != 'none'){
						$(v).bPopup().close();
					}
				});
				$.powerTip.hide();
			}

			$.each(designs, function(k,v) {
				v.approvedHistoryIndex = v.designHistory.length;
			});

			//destroy the online designer
			if(destroy) {
				elem.hide();
			}

			//update all images
			GoogleAnalytics.trackEvent('Product Page', 'Scene7v2', 'Save & Continue');
		};

		var show = function(designId, discreet) {
			if(typeof discreet === 'undefined' || !discreet) {
				elem.show();
			}
			if(typeof designId != 'undefined') {
				initEditor();
				addEditorElements(designId);
				paintEditor(designId);
			}
		};

		var setBleedMargins = function(){
			elem.find('.bleedMargins').css({
				'width':getNewSize(settings.templateWidth+18, false),
				'height':getNewSize(settings.templateHeight+18, false),
				'top':getNewSize(-9, false),
				'left':getNewSize(-9, false)
			});
		};

		var initEditor = function(){
			elem.find('#templateControlContainer').empty()
				.append($('<div />').attr('id', 'gridLines').append($('<div>').addClass('verticalBar')).hide())
				.append($('<div />').attr('id', 'cropMask').hide())
				.append($('<div />').attr('class', 'bleedMargins'))
				.append($('<div />').addClass('curtain').attr('id', 'curtain'))
		};

		var addElement = function(el, designId) {
			if(elem.find('#'+el.id).length > 0) {
				return;
			}

			var originalZ;
			var dimensions = {
				'width': getNewSize(el.width, true)+1,
				'height': getNewSize(el.height, true)+1,
				'left': getNewSize(el.x, true),
				'top': getNewSize(el.y, true)
			};

			if(el.originalRotation == 90) {
				dimensions['left'] = dimensions['left'] - dimensions['height'];
				var tmp = dimensions['height'];
				dimensions['height'] = dimensions['width'];
				dimensions['width'] = tmp;
			} else if(el.originalRotation == 180) {
				dimensions['left'] = dimensions['left'] - dimensions['width'];
				dimensions['top'] = dimensions['top'] - dimensions['height'];
			} else if(el.originalRotation == 270) {
				dimensions['top'] = dimensions['top'] - dimensions['width'];
				var tmp = dimensions['height'];
				dimensions['height'] = dimensions['width'];
				dimensions['width'] = tmp;
			}
			var domElem = $('<div />').attr('id', el.id)
				.attr('data-designer-id', elem.attr('id'))
				.attr('data-powertip', el.tip).powerTip()
				.addClass('element' + el.type)
				.addClass(((el.custom && el.justAdded) ? 'customElementAnimate' : ''))
				.css(dimensions);

			if ($('body').data('product').getProduct($('body').data('slider').getActiveSlideSetIndex()).type != 'STAMP') {
				domElem.draggable({
					cursor: 'move',
					delay: 100,
					start: function(el) {
						return function(){
							originalZ = $(this).css('z-index');
							$(this).css({'z-index': 1000});
							if (el.type=='BitmapImage'){
								domElem.find('.innerImage').show().fadeTo(0, 0.5);
								domElem.addClass('no-click');
							}
						}
					}(el),
					drag: function(event, ui) {
						$.powerTip.reposition($(this));
					},
					stop: function(el){
						return function(){
							$(this).css('z-index', originalZ);
							createHistory();
							if (el.type=='BitmapImage') {
								domElem.find('.innerImage').hide().fadeTo(0, 1);
								GoogleAnalytics.trackEvent('Product Page', 'Scene7v2', 'Repositioned Image Box');
							} else if(el.type=='Path') {
								GoogleAnalytics.trackEvent('Product Page', 'Scene7v2', 'Repositioned Path Box');
							} else if(el.type=='Rect') {
								GoogleAnalytics.trackEvent('Product Page', 'Scene7v2', 'Repositioned Rectangle Box');
							} else {
								GoogleAnalytics.trackEvent('Product Page', 'Scene7v2', 'Repositioned Text Box');
							}
							var x = getOriginalSize(parseFloat($(this).css('left')));
							var y = getOriginalSize(parseFloat($(this).css('top')));

							if(el.originalRotation == 90) {
								x += getOriginalSize(parseFloat($(this).css('width')));
							}
							if(el.originalRotation == 270) {
								y += getOriginalSize(parseFloat($(this).css('height')));
							}

							el.x = x;
							el.y = y;
							el.changed = true;

							paintEditor(designId);
						}
					}(el),
					cancel: '.no-drag',
					containment: elem.find('#templateControlContainer > .bleedMargins')
				}).css('position','absolute');
			}

			if(el.type == 'RichText') {
				if ($('body').data('product').getProduct($('body').data('slider').getActiveSlideSetIndex()).type != 'STAMP') {
					addResizeGrips(domElem);
					domElem.resizable({
						handles: {
							'nw': '#nwgrip',
							'ne': '#negrip',
							'sw': '#swgrip',
							'se': '#segrip',
							'n': '#ngrip',
							'e': '#egrip',
							's': '#sgrip',
							'w': '#wgrip'
						},
						containment: 'parent',
						delay: 150,
						start: function(event, ui) {
							originalZ = $(this).css('z-index');
							$(this).css('z-index', 1000).addClass('ui-resizable-helper').addClass('ui-resizeable-active').addClass('no-click').children().show();
						},
						stop: function(el) {
							return function() {
							$(this).css('z-index', originalZ);
								$(this).removeClass('ui-resizable-helper').removeClass('ui-resizeable-active').children().hide();
								createHistory();
								GoogleAnalytics.trackEvent('Product Page', 'Scene7v2', 'Resized Text Box');

								var x = getOriginalSize(parseFloat($(this).css('left')), true);
								var y = getOriginalSize(parseFloat($(this).css('top')), true);
								var w = getOriginalSize(parseFloat($(this).css('width')), true);
								var h = getOriginalSize(parseFloat($(this).css('height')), true);

								if(el.originalRotation == 90) {
									x += w;
								} else if(el.originalRotation == 270) {
									y += h;
									var temp = w;
									w = h;
									h = temp;
								}

								el.x = x;
								el.y = y;
								el.width = w;
								el.height =h;

								el.changed = true;
								paintEditor(designId);
							}
						}(el)
					}).children().each( function() {
						$(this).hide()
					});
				}

				//add addressing pager
				if(el.id == 'RichText_addressing') {
					el.text = designs[settings.activeDesign].addresses[designs[settings.activeDesign].activeAddress];

					domElem.append(
						getAddressTag(el)
					).addClass('addressBox').data("powertip",
						 '<strong>This is a Variable Addressing Text Box.</strong><br/><br/>'+
						 'You can:<br/>'+
						 '&nbsp;&nbsp;1. Click on it to change the font or manage addresses.<br/>'+
						 '&nbsp;&nbsp;2. Drag it to change its location on the page.<br/>'+
						 '&nbsp;&nbsp;3. Grab the edge points to resize it.<br/>'+
						 '&nbsp;&nbsp;4. Use inline pager to preview other addresses.'
					).on('mouseleave', function(event){
						e = event.toElement || event.relatedTarget;
						if ($(e).closest(this).length != 0 || e == this) {
							return;
						}
						$(this).find('#pageSelector').hide();
					});
				}

				//click event
				domElem.on('click', function(el, designId){
					return function(){
						if(domElem.hasClass('no-click')) {
							domElem.removeClass('no-click')
							return;
						}
						log('Editing Text: ' + el.id, settings.debug);
						//rebind
						var selectColor = function(choice){
							var hex = $(choice).data('color');
							popup.find('.textColor').html($(choice).clone());
							createHistory();

							if ($('body').data('product').getProduct($('body').data('slider').getActiveSlideSetIndex()).type == 'STAMP') {
								updateStampElements(designId, hex);
							}
							else {
								el.color = hex;
								el.changed = true;
							}

							paintEditor(designId);
						};
						$('.odPop').remove();
						var popup = $('<div/>', {id:'textEditPop', class:'odPop confirmDialog element_'+el.id}).data('element_', el.id).append(
							$('<div/>', {class:'content'}).append(
								$('<div/>', {class:'header'}).append(
									$('<img/>', {src:'/html/img/designer/text_edit_logo.png'})
								).append(
									$('<span/>', {class:'title'}).html((el.id === 'RichText_addressing') ? 'EDIT ADDRESSING' : 'TEXT EDIT')
								).append(
									$('<span/>', {class:'close'}).html('X')
								)
							).append(
								$('<div/>', {class:'message'}).append(
									$('<div>', {class:'filter fontFamily'}).html(getFontImage(el.fontFamily)).Dropdown({
										title: 'FONTS',
										choices: function(){
											var choices = {};
											$.each(fonts, function(k,v){
												choices[getFontImage(v)] = null;
											});
											return choices;
										}(),
										action:function(choice){
											popup.find('.fontFamily').html( $(choice).clone() );
											createHistory();
											el.changed = true;
											el.fontFamily = $(choice).attr('alt');;
											paintEditor(designId);
										},
										extraClass: 'fontFilter'
									})
								).append(
									$('<div>', {class:'filter fontSize'}).html(el.fontSize).Dropdown({
										title: 'SIZES',
										choices: function(){
											var choices = {};
											for (var i = 1; i <= 72; i++){
												choices[i] = null;
											}
											return choices;
										}(),
										action:function(choice){
											popup.find('.fontSize').html(choice);
											createHistory();
											el.changed = true;
											el.fontSize = choice;
											paintEditor(designId);
										}
									})
								).append(
									$('<div>', {class:'filter textAlign'}).html(el.textAlign).Dropdown({
										title: 'ALIGN',
										choices: {
											'Left':null,
											'Center':null,
											'Right':null
										},
										action:function(choice){
											popup.find('.textAlign').html(choice);
											popup.find('.textEdit').css('text-align', choice.toLowerCase());
											createHistory();
											el.changed = true;
											el.textAlign = choice.toLowerCase();
											paintEditor(designId);
										}
									})
								).append(
									$('<div>', {class:'filter textSpacing'}).html(el.tracking).Dropdown({
										title: 'SPACING',
										choices: function(){
											var choices = [];
											for(var i = -10; i <= 10; i+=0.5) {
												choices.push(i)
											}
											return choices;
										}(),
										action:function(choice){
											popup.find('.textSpacing').html(choice);
											createHistory();
											el.changed = true;
											el.tracking = choice;
											paintEditor(designId);
										}
									})
								).append(
									$('<div>', {class:'filter lineSpacing'}).html(el.lineHeight).Dropdown({
										title: 'SPACING',
										choices: function(){
											var choices = [];
											for(var i = 1; i <= 100; i++) {
												choices.push(i)
											}
											return choices;
										}(),
										action:function(choice){
											popup.find('.lineSpacing').html(choice);
											createHistory();
											el.lineHeight = choice;
											paintEditor(designId);
										}
									})
								).append(
									$('<div>', {class:'filter rotation'}).html(el.rotation+'&deg;').Dropdown({
										title: 'ROTATION',
										choices: {
											"0&deg;":null,
											"90&deg;":null,
											"180&deg;":null,
											"270&deg;":null
										},
										action:function(choice){
											popup.find('.rotation').html(choice);
											createHistory();
											el.changed = true;
											el.rotation = choice.replace('&deg;','');
											paintEditor(designId);
										},
										extraClass: 'rotationFilter'
									})
								).append(
									$('<div>', {class:'filter textColor'}).html(getColorSwatch(el.color)).Dropdown({
										title: 'COLOR',
										choices:function(){
											var self = this;
											var choices = {};
											$.each(colors, function(k,v){
												choices[getColorSwatch(v.hex, v.name, v.knockout)] = null;
											});

											if ($('body').data('product').getProduct($('body').data('slider').getActiveSlideSetIndex()).type != 'STAMP') {
												choices['<input class="swatchColor" type="text" value="#2828db"/><button class="add">ADD</button>']= function(el){
													$(el).find('input').minicolors({
														position:"top",
														inline: false
													});
													$(el).find('button').on('click', function(){
														var val = $(this).siblings('.minicolors').find('input').val()
														$(this).closest('ul').find('li').last().before(
															$('<li/>').html(getColorSwatch(val)).on('click', function(){
																$(this).data('color', val);
																selectColor($(this));
																$('.colorFilter').remove();
															})
														);
														$.each($('.odPop .textColor'), function(){
															$(this).data().addChoice(getColorSwatch(val), null);
														});
														colors.push({"name":"Custom "+val, "hex":val, "knockout":"#000000"});
														$(this).closest('.colorFilter').scrollTop(100000)
													})
												}
											}

											return choices;
										}(),
										action: selectColor,
										extraClass: 'colorFilter'
									})
								).append(function() {
									if(el.id.indexOf('_addressing') == -1) {
										return $('<textarea>').addClass('textEdit margin-bottom-xxs').html(el.text).on('keyup', function(){
												var self = $(this);
												waitForFinalEvent(function() {
													createHistory();
													el.changed = true;
													el.text = self.val();
													paintEditor(designId);
												}, 750, 'updatingTextValue');
											})
									} else {
										return $('<div/>', {class:'grayButton'}).html('Modify Address Book').on('click', function(){
											showAddressing(settings.activeDesign);
											popup.bPopup().close();
										})
									}
								}).append(
									$('<div/>').addClass('text-center margin-bottom-xxs').append(
										$('<span/>', {class:'delete'}).html('Delete this text box').on('click', function(){
											prettyConfirm(
												'Are you sure?',
												'Do you want to remove this text box?',
												{
													'Yes:active': function(){
														createHistory();
														el.changed = true;
														el.deleted = true;
														popup.bPopup().close();
														domElem.remove();
														if(el.id.indexOf('_addressing') == -1) {
															designs[settings.activeDesign].addresses = [];
															designs[settings.activeDesign].activeAddress = 0;
															designs[settings.activeDesign].addressBookId = null;
														}
														paintEditor(designId);
													},
													'No': null
												}
											);
										})
									)
								)
							)
						).appendTo(elem);
						limitProductActions();
						popupElementEditor(popup, el);
					}
				}(el, designId));
			} else if(el.type === 'BitmapImage') {
				addResizeGrips(domElem);
				domElem.append($('<div/>').addClass('innerImage').append($('<img/>').attr('src', (el.innerImage.url))));
				domElem.find('innerImage').show();
				makeImgResizable(domElem , el, designId);
				paintInnerImage(el, false);

				//click event
				domElem.on('click', function(el, designId){
					return function(){
						if (domElem.hasClass('no-click')){
							domElem.removeClass('no-click')
							return;
						}
						log('Editing image: ' + el.id, settings.debug);
						//check if edited yet
						if(el.innerImage.original){
							prettyConfirm(
								"Change Or Remove?",
								"What would you like to do with this image?",
								{
									"Change:active": function () {
										createHistory();
										showImageLib(el, designId);
									},
									"Remove": function(){
										createHistory();
										GoogleAnalytics.trackEvent('Product Page', 'Scene7v2', 'Deleted Original Image');
										el.deleted = true;
										el.changed = true;
										domElem.remove();
										paintEditor(designId);
									}
								}
							);
							return;
						}

						var msg = '<strong>This is an Image in Editing Mode.</strong><br/><br/>'+
							 'You can:<br/>'+
							 '&nbsp;&nbsp;1. Change the properties of the Image using the Image Edit Popup.<br/>'+
							 '&nbsp;&nbsp;2. Drag it to change which portion of the image that is displayed.<br/>'+
							 '&nbsp;&nbsp;3. Grab the edge handles to crop it.'

						$('#powerTip').html(msg);
						var originalMsg = $(this).data("powertip");
						var originalZ = $(this).css('z-index');
						$(this).css('z-index', 1000).data("powertip", msg);
						$.powerTip.reposition($(this));
						elem.find('#cropMask').show().css('z-index', 999);

						$('.odPop').remove();
						var popup = $('<div/>', {id:'imageEditPop', class:'odPop confirmDialog element_'+el.id}).data('element_', el.id).append(
							$('<div/>', {class:'content'}).append(
								$('<div/>', {class:'header'}).append(
									$('<img/>', {src:'/html/img/designer/image_edit_logo.png'})
								).append(
									$('<span/>', {class:'title'}).html('IMAGE EDIT')
								).append(
									$('<span/>', {class:'close'}).html('X').on('click', function(){
										paintInnerImage(el);
										domElem.draggable('enable').find('.innerImage').hide();
										domElem.resizable('enable').find('.ui-resizable-handle-hidden').removeClass('ui-resizable-handle-hidden').addClass('ui-resizable-handle')
										paintEditor(designId);
										domElem.resizable( "destroy" );
										domElem.css('z-index', originalZ);
										domElem.data("powertip", originalMsg);
										addResizeGrips( domElem );
										makeImgResizable(domElem.draggable('enable'), el, designId);
										elem.find('#cropMask').hide();
										popup.bPopup().close();
									})
								)
							).append(
								$('<div/>', {class:'imgButton'}).append(
									$('<img/>', {'src':'/html/img/designer/img_edit_resize.png'}).on('click', function(){
										log('Zoom In Image', settings.debug);
										zoomInnerImage(el, 20);
									}).data('powertip', 'Make your image larger<br/>within the containing box.').powerTip({placement:'e', popupId:'helpTip'})
								).append(
									$('<span/>').html('Zoom +')
								)
							).append(
								$('<div/>', {class:'imgButton'}).append(
									$('<img/>', {'src':'/html/img/designer/img_edit_resize_minus.png'}).on('click', function(){
										log('Zoom Out Image', settings.debug);
										zoomInnerImage(el, -20);
									}).data('powertip', 'Make your image smaller<br/>within the containing box.').powerTip({placement:'e', popupId:'helpTip'})
								).append(
									$('<span/>').html('Zoom -')
								)
							).append(
								$('<div/>', {class:'imgButton'}).append(
									$('<img/>', {'src':'/html/img/designer/img_edit_rotate.png'}).on('click', function(){
										log('Rotate Image Right', settings.debug);
										rotateInnerImage(el, 90);
									}).data('powertip', 'Rotate your image<br/>to the right.').powerTip({placement:'e', popupId:'helpTip'})
								).append(
									$('<span/>').html('Rotate +')
								)
							).append(
								$('<div/>', {class:'imgButton'}).append(
									$('<img/>', {'src':'/html/img/designer/img_edit_rotate_minus.png'}).on('click', function(){
										log('Rotate Image Left', settings.debug);
										rotateInnerImage(el, -90);
									}).data('powertip', 'Rotate your image<br/>to the left.').powerTip({placement:'e', popupId:'helpTip'})
								).append(
									$('<span/>').html('Rotate -')
								)
							).append(
								$('<div/>', {class:'imgButton'}).append(
									$('<img/>', {'src':'/html/img/designer/img_edit_brightness.png'}).on('click', function(){
										log('Brightness +', settings.debug);
										brightnessInnerImage(el, 10);
									}).data('powertip', 'Increase<br/>the brightness.').powerTip({placement:'e', popupId:'helpTip'})
								).append(
									$('<span/>').html('Brightness +')
								)
							).append(
								$('<div/>', {class:'imgButton'}).append(
									$('<img/>', {'src':'/html/img/designer/img_edit_brightness_minus.png'}).on('click', function(){
										log('Brightness -', settings.debug);
										brightnessInnerImage(el, -10);
									}).data('powertip', 'Reduce<br/>the brightness.').powerTip({placement:'e', popupId:'helpTip'})
								).append(
									$('<span/>').html('Brightness -')
								)
							).append(
								$('<div/>', {class:'imgButton'}).append(
									$('<img/>', {'src':'/html/img/designer/img_edit_contrast.png'}).on('click', function(){
										log('Contrast +', settings.debug);
										contrastInnerImage(el, 10);
									}).data('powertip', 'Increase<br/>the contrast.').powerTip({placement:'e', popupId:'helpTip'})
								).append(
									$('<span/>').html('Contrast +')
								)
							).append(
								$('<div/>', {class:'imgButton'}).append(
									$('<img/>', {'src':'/html/img/designer/img_edit_contrast_minus.png'}).on('click', function(){
										log('Contrast -', settings.debug);
										contrastInnerImage(el, -10);
									}).data('powertip', 'Reduce<br/>the contrast.').powerTip({placement:'e', popupId:'helpTip'})
								).append(
									$('<span/>').html('Contrast -')
								)
							).append(
								$('<div/>', {class:''}).append(
									$('<div/>', {class:'grayButton'}).html('Reset Image').on('click', function(){
										log('Reseting image', settings.debug);
										el.width = el.originalWidth;
										el.height = el.originalHeight;
										elem.find('#'+el.id).animate({
											width: getNewSize(el.width),
											height: getNewSize(el.height)
										}, 500);
										el.innerImage.colorize = 0;
										el.innerImage.saturation = 0;
										el.innerImage.contrast = 0;
										el.innerImage.brightness = 0;
										el.innerImage.grow = 0;
										el.innerImage.rotation = 0;
										el.innerImage.top = 0;
										el.innerImage.left = 0;

										if(el.custom){
											el.innerImage.width = getNewSize(el.width);
											el.innerImage.height = getNewSize(el.height);
										}
										fitToBox(el);
										paintInnerImage(el);
									})
								).append(
									$('<div/>', {class:'grayButton'}).html('Change Image').on('click', function(){
										showImageLib(el, designId);
										createHistory();
										popup.find('.close').trigger('click');
										domElem.remove();
										elem.find('#cropMask').hide();
									})
								)
							).append(
								$('<div/>',{class:'orangeButton save'}).html('SAVE').on('click', function(){
									createHistory();
									el.changed = true;
									domElem.resizable( "destroy" );
									addResizeGrips(domElem);
									makeImgResizable(domElem.draggable('enable'), el, designId);
									elem.find('#cropMask').hide();
									domElem.css('z-index', originalZ);
									domElem.data("powertip", originalMsg);
									paintEditor(designId);
									popup.bPopup().close();
								})
							).append(
								$('<div/>', {class:'delete'}).html('Delete Image').on('click', function(){
									prettyConfirm(
										'Are you sure?',
										'Do you want to remove this image?',
										{
											'Yes:active': function(){
												createHistory();
												el.changed = true;
												el.deleted = true;
												popup.find('.close').trigger('click');
												domElem.remove();
												paintEditor(designId);
											},
											'No': null
										}
									);
								})
							)
						).appendTo(elem);

						var image = domElem.draggable('disable').find('.innerImage');
						image.fadeIn({
								duration:500
						});

						image.find('img').draggable({
							cursor: "move",
							drag: function (event, ui) {
								var bounds = boundsInnerImage(	ui.position.top,
																ui.position.left,
																ui.helper.width(),
																ui.helper.height(),
																ui.helper.parent().width(),
																ui.helper.parent().height() );

								ui.position.top = bounds.top;
								ui.position.left = bounds.left;
							},
							stop: function(event, ui){
								el.innerImage.top = getOriginalSize(ui.position.top);
								el.innerImage.left = getOriginalSize(ui.position.left);

								el.innerImage.topPerc = Math.abs(ui.position.top / ui.helper.height());
								el.innerImage.leftPerc = Math.abs(ui.position.left / ui.helper.width());

								el.innerImage.maxTop = Math.abs((ui.helper.parent().height() -  ui.helper.height()) /  ui.helper.height());
								el.innerImage.maxLeft = Math.abs((ui.helper.parent().width() -  ui.helper.width()) /  ui.helper.width());
							}
						});
						var handle;
						var originalW = el.width;
						var originalH = el.height;
						var originalImgTop = el.innerImage.top;
						var originalImgLeft = Math.abs( el.innerImage.left );
						var originalZ;

						domElem.resizable( "destroy" );
						addResizeGrips(domElem, ['n','s','e','w'], 'c');
						domElem.resizable({
							handles: {
								'n': '#ngripc',
								'e': '#egripc',
								's': '#sgripc',
								'w': '#wgripc'
							},
							maxWidth: $(this).find('.innerImage>img').width(),
							maxHeight: $(this).find('.innerImage>img').height(),
							aspectRatio: false,
							containment: "parent",
							delay: 150,
							start: function (event, ui) {
								originalZ = $(this).css('z-index');
								$(this).css('z-index', 1000);
								$(this).addClass('ui-resizable-helper');
								$(this).addClass('ui-resizeable-active');
								$(this).addClass('no-click');
								handle = $(event.originalEvent.target).attr('id');
							},
							resize: function (el) {
								return function() {
									if(handle === 'ngripc'){
										var newTop = 0 - (getNewSize(originalImgTop) + getNewSize(originalH) - $(this).height());
										if (newTop < 1 ){
											$(this).find('.innerImage > img').css('top', newTop );
											el.innerImage.top = getOriginalSize(newTop);
										} else {
											$(this).resizable('widget').trigger('mouseup');
										}
									}
									else if(handle === 'wgripc'){
										var newLeft = 0 - (getNewSize(originalImgLeft) + getNewSize(originalW) - $(this).width());
										if (newLeft < 1 ){
											$(this).find('.innerImage > img').css('left', newLeft );
											el.innerImage.left = getOriginalSize(newLeft);
										} else {
											$(this).resizable('widget').trigger('mouseup');
										}
									}
									else if(handle === 'egripc'){
										//prevent from resizing past limit
										if(Math.abs(getNewSize(el.innerImage.left)) + $(this).width() > getNewSize(el.innerImage.width)){
											$(this).resizable('widget').trigger('mouseup');
											$(this).css({width: getNewSize(el.innerImage.width) - getNewSize(Math.abs(el.innerImage.left)) - 1});
										}
									}
									else if(handle === 'sgripc'){
										//prevent from resizing past limit
										if(Math.abs(getNewSize(el.innerImage.top)) + $(this).height() > getNewSize(el.innerImage.height)){
											$(this).resizable('widget').trigger('mouseup');
											$(this).css({height: getNewSize(el.innerImage.height) - getNewSize(Math.abs(el.innerImage.top)) - 1});
										}
									}
								}
							}(el),
							stop: function (el) {
								return function() {
									$(this).css('z-index', originalZ);
									$(this).removeClass('ui-resizable-helper');
									$(this).removeClass('ui-resizeable-active');
									el.width = getOriginalSize($(this).width());
									el.height = getOriginalSize($(this).height());
									el.x = getOriginalSize($(this).position().left);
									el.y = getOriginalSize($(this).position().top);
									originalImgTop = Math.abs(el.innerImage.top );
									originalImgLeft = Math.abs( el.innerImage.left );
									originalW = el.width;
									originalH = el.height;
									paintInnerImage(el, false);
								}
							}(el)
						});

						popupElementEditor(popup);
					}
				}(el, designId))
			} else if(el.type === 'Path') {
				//click event
				domElem.on('click', function(el, designId){
					return function(){
						log('Editing path: ' + el.id, settings.debug);

						var selectColor = function(choice){
							var hex = $(choice).data('color');
							popup.find('.textColor').html($(choice).clone());
							createHistory();

							if ($('body').data('product').getProduct($('body').data('slider').getActiveSlideSetIndex()).type == 'STAMP') {
								updateStampElements(designId, hex);
							}
							else {
								el.changed = true;
								el.pathData[0].color  = hex;
							}

							paintEditor(designId);
						};

						$('.odPop').remove();
						var popup = $('<div/>', {id:'colorEditPop', class:'odPop confirmDialog element_'+el.id}).data('element_', el.id).append(
							$('<div/>', {class:'content'}).append(
								$('<div/>', {class:'header'}).append(
									$('<img/>', {src:'/html/img/designer/image_edit_logo.png'})
								).append(
									$('<span/>', {class:'title'}).html('COLOR EDIT')
								).append(
									$('<span/>', {class:'close'}).html('X')
								)
							).append(
								$('<div/>', {class:'message'}).append(
									function(){
										var cls = [];
										if (typeof el.pathData[0] == 'undefined'){
											el.pathData.push({'color':'#FFFFFF'})
										}
										return $('<div>', {class:'filter textColor'}).html(getColorSwatch(el.pathData[0].color)).Dropdown({
											title: 'COLOR',
											choices:function(){
												var self = this;
												var choices = {};
												$.each(colors, function(k,v){
													choices[getColorSwatch(v.hex, v.name, v.knockout)] = null;
												});
												choices['<input class="swatchColor" type="text" value="#2828db"/><button class="add">ADD</button>']= function(el){
													$(el).find('input').minicolors({
														position:"top",
														inline: false
													});
													$(el).find('button').on('click', function(){
														var val = $(this).siblings('.minicolors').find('input').val()
														$(this).closest('ul').find('li').last().before(
															$('<li/>').html(getColorSwatch(val)).on('click', function(){
																$(this).data('color', val);
																selectColor($(this));
																$('.colorFilter').remove();
															})
														);
														$.each($('.odPop .textColor'), function(){
															$(this).data().addChoice(getColorSwatch(val), null);
														});
														colors.push({"name":"Custom "+val, "hex":val, "knockout":"#000000"});
														$(this).closest('.colorFilter').scrollTop(100000)
													})
												}
												return choices;
											}(),
											action: selectColor,
											extraClass: 'colorFilter'
										})
									}
								)
							).append(
								$('<div/>', {class:'delete'}).html('Delete').on('click', function(){
									prettyConfirm(
										'Are you sure?',
										'Do you want to remove this element?',
										{
											'Yes:active': function(){
												createHistory();
												el.deleted = true;
												el.changed = true;
												popup.find('.close').trigger('click');
												domElem.remove();
												paintEditor(designId);
											},
											'No': null
										}
									);
								})
							)
						).appendTo(elem);
						popupElementEditor(popup);
					}
				}(el, designId));
			} else if(el.type === 'Rect') {
				return function(){
					log('Editing rectangle: ' + el.id, settings.debug);
					var popup = $('#colorEditPop');
					popup.find(".colorSelector").find("input.pathColor").val(el.color);
					popup.find(".colorSelected").css({"background-color":el.color});

					popup.find('.submit').unbind('click').unbind('click').on('click', function(){
						el.color = popup.find(".colorSelector").find("input.pathColor").val();
						paintEditor(designId);
						popup.bPopup().close();
					});

					popup.find('.close').unbind('click').unbind('click').on('click', function(){
						popup.bPopup().close();
						return false;
					});

					popup.find('.delete').unbind('click').on('click', function(){
						createHistory();
						GoogleAnalytics.trackEvent('Product Page', 'Scene7v2', 'Deleted Rectangle');
						el.deleted = true;
						el.changed = true;
						paintEditor(designId);
						popup.bPopup().close();
					});

					popupElementEditor(popup);
				}
			}(el, designId);

			elem.find('#templateControlContainer').append(domElem);

			if (el.justAdded) {
				domElem.hide().show( "clip", {} , 500);
				el.justAdded = false;
				noLoadScreen = true;
			}
		}

		var popupElementEditor = function(popup, el){
			var popupLoc = {x:0, y:0};
			if(typeof popupLocations[popup.attr('id')] !== 'undefined') {
				popupLoc.x = popupLocations[popup.attr('id')].x;
				popupLoc.y = popupLocations[popup.attr('id')].y;
			} else {
				popupLoc.x = window.innerWidth - parseInt(popup.css('width')) - 30 - elem.offset().left;
				popupLoc.y = (window.innerHeight/2) - (parseInt(popup.css('height')) /2);
			}
			popup.bPopup({
				modal:false,
				escClose: false,
				transition: "fadeIn",
				speed: 100,
				positionStyle: 'absolute',
				follow: [false, false],
				appending: true,
				position : [popupLoc.x, popupLoc.y],
				closeClass: 'close',
				onOpen: function() {
					popup.css('z-index', 90000);
				},
				onClose: function() {
					popup.remove();
				},
				appendTo: $('body')
			}).draggable({
				handle: '.head, .header',
				stop:function(){
					popupLocations[$(this).attr('id')] = {x:$(this).css('left'),y:$(this).css('top')};
				}
			});

			if(typeof el !== 'undefined' && !el.changed) {
				popup.find('.textEdit').focus().select();
			} else {
				popup.find('.textEdit').focus();
			}
		}

		var addEditorElements = function(designId) {
			for(var i = 0; i < designs[designId].elements.length; i++) {
				var el = designs[designId].elements[i];
				if(el.deleted) { continue; }

				if(el.id.toLowerCase() != 'COLOR_bgcolor'.toLowerCase()) {
					addElement(el, designId);
				} else {
					el.pathData[0].color = settings.backgroundColor;
					el.pathData[0].pathType = 'fill';
				}
			}
		}

		var makeImgResizable = function(domObj, el, designId) {
			var originalZ;
			domObj.resizable({
				handles: {
					'nw': '#nwgrip',
					'ne': '#negrip',
					'sw': '#swgrip',
					'se': '#segrip',
					'n': '#ngrip',
					'e': '#egrip',
					's': '#sgrip',
					'w': '#wgrip'
				},
				aspectRatio: true,
				containment: "parent",
				delay: 150,
				start: function (event, ui) {
					originalZ = $(this).css('z-index');
					$(this).css('z-index', 1000);
					$(this).addClass('ui-resizable-helper');
					$(this).addClass('ui-resizeable-active');
					$(this).addClass('no-click');
				},
				stop: function (el) {
					return function() {
						$(this).css('z-index', originalZ);
						$(this).removeClass('ui-resizable-helper');
						$(this).removeClass('ui-resizeable-active');
						createHistory();
						GoogleAnalytics.trackEvent('Product Page', 'Scene7v2', 'Resized Image Box');
						$(this).find('.innerImage').hide();
						var x = getOriginalSize(parseFloat($(this).css('left')), true);
						var y = getOriginalSize(parseFloat($(this).css('top')), true);
						var w = getOriginalSize(parseFloat($(this).css('width')), true);
						var h = getOriginalSize(parseFloat($(this).css('height')), true);

						var scale = w/el.width;

						el.x = x;
						el.y = y;
						el.width = w;
						el.height *= scale;
						el.innerImage.width *= scale;
						el.innerImage.height *= scale;
						el.innerImage.left *= scale;
						el.innerImage.top *= scale;
						el.changed = true;

						paintInnerImage(el, false);
						paintEditor(designId);
					}
				}(el)
			});

			domObj.children().each( function(k,v){
				if(!$(v).hasClass('innerImage')){
					$(v).hide()
				}
			})
		}

		var getFontImage = function(v) {
			return '<img src="/html/img/fonts/' + v.toLowerCase().replace(/\s/g,'') + '.png" alt="'+v+'">';
		}

		var getColorSwatch = function(v, v2, v3) {
			if (typeof v2 == 'undefined'){
				$.each(colors, function (k,color){
					if(color.hex.toLowerCase() == v.toLowerCase()){
						v2 = color.name;
						v3 = color.knockout;
					}
				});
				if (typeof v2 == 'undefined'){
					v2 = "Custom " + v;
					v3 = "#000000";
				}

			}
			return '<div class="swatchColor" style="background-color:'+v+'; color:'+v3+'" data-color="'+v+'">'+v2+'</div>';
		}

		var addResizeGrips = function(elem, directions, idSufix) {
			if(typeof directions ==='undefined') {
				directions = ['nw', 'ne', 'sw', 'se', 'n', 's', 'e', 'w']
			}
			if(typeof idSufix === 'undefined') {
				idSufix = '';
			}
			elem.children().not('.innerImage').remove();
			$.each(directions, function(k,v){
				elem.append( $('<div/>', {class:'ui-resizable-handle ui-resizable-'+v,id:v+'grip'+idSufix}) )
			});
		}

		/* ######################### MAIN INIT ####################### */
		var init = function() {
			//lets set an event to show we launched designer from designProdut view /hack/
			if(launchedFrom.indexOf('designProduct') != -1) {
				GoogleAnalytics.trackEvent('Product Page', 'Scene7v2', 'Template Design');
			}

			log('Initializing OnlineDesigner!', settings.debug);

			getSpotColors(); //load all predefined colors

			elem.append(
				$('<div/>').hide().addClass('imgLibPop').append(
					$('<div/>').addClass('content').append(
						$('<div/>').addClass('head').append(
							$('<span/>').addClass('name').html('IMAGE LIBRARY')
						).append(
							$('<span/>').addClass('close').html('x')
						)
					).append(
						$('<ul/>').addClass('tabs').append(
							$('<li/>').addClass('active').html('Your Library').on('click', function(){
								viewImageLibrary($(this), elem.find('#imgLibUser'));
							})
						).append(
							$('<li/>').html('Backgrounds').on('click', function(){
								loadImageLib('#imgLibBackgrounds', 'http://actionenvelope.scene7.com/is/image/ActionEnvelope/', 'Backgrounds');
								viewImageLibrary($(this), elem.find('#imgLibBackgrounds'));
							})
						).append(
							$('<li/>').html('Banners').on('click', function(){
								loadImageLib('#imgLibBanners', 'http://actionenvelope.scene7.com/is/image/ActionEnvelope/', 'Banners');
								viewImageLibrary($(this), elem.find('#imgLibBanners'));
							})
						).append(
							$('<li/>').html('Clip Art').on('click', function(){
								loadImageLib('#imgLibClipArt', 'http://actionenvelope.scene7.com/is/image/ActionEnvelope/', 'Clip Art');
								viewImageLibrary($(this), elem.find('#imgLibClipArt'));
							})
						).append(
							$('<li/>').html('Floral').on('click', function(){
								loadImageLib('#imgLibFloral', 'http://actionenvelope.scene7.com/is/image/ActionEnvelope/', 'Floral');
								viewImageLibrary($(this), elem.find('#imgLibFloral'));
							})
						).append(
							$('<li/>').html('Holiday').on('click', function(){
								loadImageLib('#imgLibHoliday', 'http://actionenvelope.scene7.com/is/image/ActionEnvelope/', 'Holiday');
								viewImageLibrary($(this), elem.find('#imgLibHoliday'));
							})
						).append(
							$('<li/>').html('Logos').on('click', function(){
								loadImageLib('#imgLibLogos', 'http://actionenvelope.scene7.com/is/image/ActionEnvelope/', 'Logos');
								viewImageLibrary($(this), elem.find('#imgLibLogos'));
							})
						).append(
							$('<li/>').html('Bursts & Teasers').on('click', function(){
								loadImageLib('#imgLibBursts', 'http://actionenvelope.scene7.com/is/image/ActionEnvelope/', 'Bursts & Teasers');
								viewImageLibrary($(this), elem.find('#imgLibBursts'));
							})
						).append(
							$('<li/>').html('Social Media').on('click', function(){
								loadImageLib('#imgLibSocialMedia', 'http://actionenvelope.scene7.com/is/image/ActionEnvelope/', 'Social Media Logos');
								viewImageLibrary($(this), elem.find('#imgLibSocialMedia'));
							})
						).append(
							$('<li/>').html('Misc.').on('click', function(){
								loadImageLib('#imgLibMisc', 'http://actionenvelope.scene7.com/is/image/ActionEnvelope/', 'Images');
								viewImageLibrary($(this), elem.find('#imgLibMisc'));
							})
						)
					).append(
						$('<div>').addClass('contentTabs').append(
							$('<div/>').attr('id', 'imgLibBackgrounds').addClass('imgLib').hide()
						).append(
							$('<div/>').attr('id', 'imgLibBanners').addClass('imgLib').hide()
						).append(
							$('<div/>').attr('id', 'imgLibClipArt').addClass('imgLib').hide()
						).append(
							$('<div/>').attr('id', 'imgLibFloral').addClass('imgLib').hide()
						).append(
							$('<div/>').attr('id', 'imgLibHoliday').addClass('imgLib').hide()
						).append(
							$('<div/>').attr('id', 'imgLibLogos').addClass('imgLib').hide()
						).append(
							$('<div/>').attr('id', 'imgLibBursts').addClass('imgLib').hide()
						).append(
							$('<div/>').attr('id', 'imgLibSocialMedia').addClass('imgLib').hide()
						).append(
							$('<div/>').attr('id', 'imgLibMisc').addClass('imgLib').hide()
						).append(
							$('<div/>').attr('id', 'imgLibUser').addClass('imgLib').append(
								$('<div/>').addClass('uploadArea').append(
									$('<form/>')
										.addClass('scene7LogoUGC')
										.attr('name', 'scene7LogoUGC' + '_' + elem.attr('data-id'))
										.attr('id', 'scene7LogoUGC' + '_' + elem.attr('data-id'))
										.attr('action', '/' + websiteId + '/control/uploadUGCFile')
										.attr('method', 'post')
										.attr('target', 'ugcFrame' + '_' + elem.attr('data-id'))
										.attr('enctype', 'multipart/form-data')
										.append(
											$('<div/>').html('Select and upload your file(s)')
										)
										.append(
											$('<input/>')
												.attr('type','file')
												.attr('id','ugcUploadFile' + '_' + elem.attr('data-id'))
												.attr('name','ugcUploadFile[]')
												.attr('multiple', '')
												.on('change', function() {
													uploadFiles(this.files);
											})
										)
										.append(
											$('<br/>')
										)
										.append(
											$('<input/>')
												.attr('type','submit')
												.attr('value','Upload')
												.addClass('orangeButton').on('click', function(){
												if(elem.find('#ugcUploadFile' + '_' + elem.attr('data-id')).val() === ''){
													prettyConfirm(
														'No File Found',
														'Please choose a file first!',
														{
															'OK:active': null
														}
													);
													event.preventDefault();
												} else {
													var count = ((typeof elem.find('#ugcUploadFile' + '_' + elem.attr('data-id'))[0].files !== 'undefined')?elem.find('#ugcUploadFile' + '_' + elem.attr('data-id'))[0].files.length : 1);
													for (var i = 0; i < count; ++i) {
														addPendingUpload();
													}
												}
											})
										)
									.append(
										$('<iframe/>')
											.addClass('ugcFrame')
											.attr('name', 'ugcFrame' + '_' + elem.attr('data-id'))
											.attr('id', 'ugcFrame' + '_' + elem.attr('data-id'))
											.attr('src', 'about:blank')
									)
								).append(
									$('<div/>').attr('id', 'uploadMessage').html('UPLOAD IMAGES HERE FROM YOUR OWN PERSONAL COMPUTER')
								).append(
									$('<div/>').attr('id', 'fileInfo').html('You can upload AI, EPS, BMP, JPEG, PNG, GIF, TIFF up to 20MBs in size.')
								).append(
									$('<div/>').addClass('orangeButton').html('Click Here to Upload').on('click', function(){
										elem.find('#ugcUploadFile' + '_' + elem.attr('data-id')).trigger('click');
									})
								)
							)
						)
					).append(
						$('<div/>').addClass('useThisImage').addClass('orangeButton').html('Use This Image').hide()
					)
				)
			).append(
				$('<div/>').hide().attr('id', 'emailForLater').addClass('emailForLater').addClass('emailLoginForm').append(
					$('<div/>').addClass('content').append(
						$('<div/>').addClass('head').append(
							$('<span/>').addClass('name').html('EMAIL ADDRESS')
						).append(
							$('<span/>').addClass('close').html('x')
						)
					).append(
						$('<div/>').addClass('body').append(
							$('<div/>').addClass('login').append(
								$('<span/>').addClass('title').html('Enter Email')
							).append(
								$('<span/>').addClass('message').html('We will send you an email with instructions on how to resume your design.')
							).append(
								$('<input/>').attr('type', 'text').attr('name', 'USERNAME').attr('value', '').attr('placeholder', 'Enter your email address').addClass('text')
							).append(
								$('<a/>').addClass('create').addClass('marginTop5').attr('href', '').html('SAVE').on('click', function(e){
									saveToServer($(this).closest('.login').find('input[name="USERNAME"]').val());
									e.preventDefault();
								})
							).append(
								$('<span/>').hide().addClass('loginError').html('There was an error. Please try again.')
							)
						)
					)
				)
			/*).append(
				function(){
					if(!getUserLogin()) {
						return $('<a/>').addClass('saveForLater').html('Save for Later').on('click', function(e) {
							showLoginRegister('.emailForLater');
							e.preventDefault();
						});
					}
				}()*///TODO: FIX ME
			).append(
				$('<img/>').attr('id', 'scene7ImageURL').attr('src', '').on('load', function () {
					elem.find('#curtain').fadeOut('fast');
					elem.find('#templateControlContainer').css({'background-image':'url("' + $(this).attr("src") + '")'});
					elem.find('.customElementAnimate').fadeOut({
							duration:500,
							complete:function() {
								$(this).removeClass('customElementAnimate');
								$(this).show().css('opacity', '')
						}
					});
					elem.find('.innerImage').fadeOut({
							duration:100
					});
					processPricing(true);

					//TODO think of another way to do this
					//we will check here if addressing is available if so, lets update the addressing image
					var addressingImageUpdateFunction = 'updateAddress_' + elem.attr('data-id');
					if($('[data-name=step-addressing_' + elem.attr('data-id') + ']').closest('li').attr('data-enabled') == 'true' && typeof eval(addressingImageUpdateFunction) == 'function') {
						eval(addressingImageUpdateFunction)(true);
					};
				})
			).append(
				$('<div/>').attr('id', 'onlineDesigner').append(
					$('<div/>').attr('id', 'onlineDesignerContainer').append(
						$('<div/>').attr('id', 'templateSides')
					).append(
						$('<div/>').attr('id', 'zoomController')/*.append(
							$('<img/>').attr('id', 'zoomIn').addClass('zoomBut').attr('src', '/html/img/designer/zoomin.png')
								.on('click', function(){
									var step = 50;
									if(canZoomIn(step, false)){
										staticWidth = staticWidth + step;
										staticHeight = staticWidth * (settings.templateHeight/settings.templateWidth);
										processODResize(true, step);
									} else {
										showWarningMessage('Can\'t Zoom IN any futher!')
									}
								})
								.data('powertip', 'Zoom In').powerTip({placement:'e', popupId:'helpTip'})
						).append(
							$('<img/>').attr('id', 'zoomOut').addClass('zoomBut').attr('src', '/html/img/designer/zoomout.png')
							.on('click', function(){
									var step = -50;
									if(canZoomOut(step)){
										staticWidth = staticWidth + step;
										staticHeight = staticWidth * (settings.templateHeight/settings.templateWidth);
										processODResize(true, step);
									}else {
										showWarningMessage('Can\'t Zoom OUT any futher!')
									}
								})
								.data('powertip', 'Zoom Out').powerTip({placement:'e', popupId:'helpTip'})
						)*/.append(
							$('<img/>').attr('id', 'addText').addClass('zoomBut').attr('src', '/html/img/designer/addtext.png')
							.on('click', function(){
								log('Add Text', settings.debug, settings.debug);
								createHistory();
								GoogleAnalytics.trackEvent('Product Page', 'Scene7v2', 'Add Custom Text');

								var params = {};
								params['type'] = 'RichText';
								params['index'] = designs[settings.activeDesign].customCount++;
								params['templateWidth'] = settings.templateWidth;
								params['templateHeight'] = settings.templateHeight;

								$.ajax({
									type: 'GET',
									url: '/' + websiteId + '/control/generateCustomFXGContent',
									data: params,
									dataType: 'xml',
									success: function(xml) {
										var el = createRichText($(xml).find('RichText'), true, false, false);
										designs[settings.activeDesign].elements.push(el);
										addElement(el, settings.activeDesign)
										paintEditor(settings.activeDesign);
									}
								});
							})
							.data('powertip', 'Add Text').powerTip({placement:'e', popupId:'helpTip'})
						).append(
							$('<img/>').attr('id', 'addImage').addClass('zoomBut').attr('src', '/html/img/designer/addimage.png')
							.on('click', function(){
								log('Add Image', settings.debug);
								if (elem.find('.imgLibPop').css('display') != 'none'){
									return;
								}
								GoogleAnalytics.trackEvent('Product Page', 'Scene7v2', 'Add Custom Image');

								var params = {};
								params['type'] = 'BitmapImage';
								params['index'] = designs[settings.activeDesign].customCount++;
								params['templateWidth'] = settings.templateWidth;
								params['templateHeight'] = settings.templateHeight;

								$.ajax({
									type: 'GET',
									url: '/' + websiteId + '/control/generateCustomFXGContent',
									data: params,
									dataType: 'xml',
									success: function(xml) {
										var el = createImage($(xml).find('BitmapImage'), true);
										createHistory();
										el.changed = true;
										designs[settings.activeDesign].elements.push(el);
										showImageLib(el, settings.activeDesign);
									}
								});
							})
							.data('powertip', 'Add Image').powerTip({placement:'e', popupId:'helpTip'})
						).append(
							$('<img/>').hide().attr('id', 'addressing').addClass('zoomBut').attr('src', '/html/img/designer/addaddress.png')
							.on('click', function(){
								GoogleAnalytics.trackEvent('Product Page', 'Scene7v2', 'Addressing');
								showAddressing(settings.activeDesign);
							})
							.data('powertip', 'Add Variable Addressing').powerTip({placement:'e', popupId:'helpTip'})
						).append(
							$('<img/>').attr('id', 'editableTogglers').addClass('zoomBut').attr('src', '/html/img/designer/showitems.png')
							.on('click', function(){
								GoogleAnalytics.trackEvent('Product Page', 'Scene7v2', 'Toggle Editable Items');
								var elems = $('.elementRichText, .elementPath, .elementRect, .elementBitmapImage');
								if(showItems) {
									showItems = false;
									elems.removeClass('elementActive');
									$(this).attr('src', '/html/img/designer/showitems.png');
								} else {
									showItems = true;
									elems.addClass('elementActive');
									$(this).attr('src', '/html/img/designer/showitems_act.png');
								}
							})
							.data('powertip', 'Show Editable Elements').powerTip({placement:'e', popupId:'helpTip'})
						).append(
							$('<img/>').attr('id', 'gridTogglers').addClass('zoomBut').attr('src', '/html/img/designer/showgrid.png')
							.on('click', function(){
								GoogleAnalytics.trackEvent('Product Page', 'Scene7v2', 'Toggle Grid');
								var elems = elem.find('#gridLines');
								if (showGrid){
									showGrid = false;
									elems.show();
									$(this).attr('src', '/html/img/designer/showgrid.png');
								} else {
									showGrid = true;
									elems.hide();
									$(this).attr('src', '/html/img/designer/showgrid_act.png');
								}
								$(elems).toggle();
							})
							.data('powertip', 'Show Grid').powerTip({placement:'e', popupId:'helpTip'})
						).append(
							$('<img/>').attr('id', 'switchTemplate').css({'display':'none'}).addClass('zoomBut').attr('src', '/html/img/designer/switch_layouts.png')
						).append(
							$('<img/>').attr('id', 'stepBack').addClass('zoomBut').attr('src', '/html/img/designer/undostep.png')
							.on('click', function(){
								historyBack();
								GoogleAnalytics.trackEvent('Product Page', 'Scene7v2', 'Undo');
							})
							.data('powertip', 'Undo').powerTip({placement:'e', popupId:'helpTip'})
						).append(
							$('<img/>').attr('id', 'saveDesign').addClass('saveDesign').attr('src', '/html/img/designer/save.png')
							.on('click', function(){
                                 saveCurrentDesign('.loginRegisterAndSaveDesign');
//								GoogleAnalytics.trackEvent('Product Page', 'Scene7v2', 'Save');
							})
							.data('powertip', 'Save Design').powerTip({placement:'e', popupId:'helpTip'})
						)
					).append(
						$('<div/>').attr('id', 'templateController').append(
							$('<div/>').attr('id', 'templateControlContainer')
						)
					)
				/*).append(
					$('<div/>').addClass('printWarning').html(
						'<strong>PLEASE NOTE:</strong> <br> ' +
						'This is a computer representation only. Ink color may be affected by paper color. For example, blue ink on a yellow <br>' +
						'envelope may look green. Light ink colors will not show on dark paper colors, even though they look great on the screen.'
					)*/
				)
			).append(
				$('<div/>').attr('id', 'addressing_' + elem.attr('data-id')).hide().addClass('addressing confirmDialog').append(
					$('<div/>').addClass('content').append(
						$('<div/>').addClass('head').append(
							$('<span/>').addClass('name').html('ADDRESSING')
						).append(
							$('<span/>').addClass('close').html('x')
						)
					).append(
						$('<div/>').addClass('stepContainer').append(
							$('<div/>').addClass('step1').append(
								$('<div/>').addClass('welcome').html('Envelope Addressing')
							).append(
								$('<div/>').addClass('welcomeSub').html('We can print the recipient addresses on your envelopes so they come ready to fill! No labels, no fuss.')
							).append(
								$('<div/>').addClass('options').addClass('createAdd').append(
									$('<h2/>').html('Create New Address Book')
								).append(
									$('<div/>').addClass('grid-content').append(
										$('<div/>').append(
											$('<span/>').addClass('msg').html('Download our template, enter your addresses and then upload your file!')
										)
									).append(
										$('<div/>').append(
											$('<a/>').addClass('create').addClass('active').attr({ 'target': '_blank', 'href': '/html/files/addressing.csv' }).html('DOWNLOAD TEMPLATE')
										)
									)
								).append(
									$('<div/>').addClass('grid-content').append(
										$('<div/>').append(
											$('<span/>').addClass('msg').html('Have your address list in a file ready to go? Upload it.')
										)
									).append(
										$('<div/>').append(
											$('<a/>').addClass('create').attr('href', '').html('UPLOAD CSV FILE').on('click', function(e){
												$('#ugcUploadFile2' + '_' + elem.attr('data-id')).val('');
												$(this).closest('.step1').hide();
												$(this).closest('.content').find('.step2').show();
												e.preventDefault();
											})
										)
									)
								).append(
									$('<div/>').addClass('grid-content').append(
										$('<div/>').append(
											$('<span/>').addClass('msg').html('Or you could enter your addresses manually.')
										)
									).append(
										$('<div/>').append(
											$('<a/>').addClass('create').attr('href', '').html('ENTER MANUALLY').on('click', function(e){
												$(this).closest('.step1').hide();
												createNewAddressBook();
												e.preventDefault();
											})
										)
									)
								)
							).append(
								$('<div/>').addClass('options').append(
									$('<h2/>').html('Select/Manage Address Books')
								).append(
									$('<ul/>').addClass('existingAddressBooks').append(
										$('<li/>').html('You do not have any addresses.')
									)
								).append(
									$('<span/>').addClass('msg').html('Select an address book from above. You can then edit your address list, apply the address list to your design and preview an address on your design.')
								)
							).append(
								$('<div/>').addClass('options').addClass('saveAdd').append(
									$('<h2/>').addClass('save').html('Save Your Addresses For Later')
								).append(
									$('<span/>').addClass('msg2').addClass('askLogin').append(
										'In order for you to use these addresses in the future please '
									).append(
										$('<a/>').attr('href', '').html('login or register').on('click', function(e) {
											showLoginRegister('.loginRegister');
											e.preventDefault();
										})
									).append(
										' and it will save to your account.'
									).append(
										$('<a/>').addClass('create').addClass('saveAddB').attr('href', '').html('LOGIN / REGISTER').on('click', function(e){
											showLoginRegister('.loginRegister');
											e.preventDefault();
										})
									)
								).append(
									$('<span/>').addClass('msg2').addClass('showLogin').html('You are logged in!')
								)
							)
						).append(
							$('<div/>').addClass('step2').hide().append(
								$('<div/>').addClass('welcome').html('Download Our Template')
							).append(
								$('<div/>').addClass('welcomeSub2').html('Download our template and add your addresses to the file using software such as Microsoft Excel. Once completed, upload it below.')
							).append(
								$('<br/>')
							).append(
								$('<div/>').addClass('warning-message')
							).append(
								$('<br/>')
							).append(
								$('<div/>').append(
									$('<a/>').addClass('create').attr({ 'target': '_blank', 'href': '/html/files/addressing.csv' }).html('DOWNLOAD TEMPLATE FILE')
								)
							).append(
								$('<div/>').addClass('uploadArea').append(
									$('<form/>')
										.addClass('scene7LogoUGC2')
										.attr('name', 'scene7LogoUGC' + '_' + elem.attr('data-id'))
										.attr('id', 'scene7LogoUGC2' + '_' + elem.attr('data-id'))
										.attr('action', '/' + websiteId + '/control/uploadFilePost')
										.attr('method', 'post')
										.attr('target', 'ugcFrame2' + '_' + elem.attr('data-id'))
										.attr('enctype', 'multipart/form-data')
										.append(
											$('<div/>').html('Select and upload your file(s)')
										)
										.append(
											$('<input/>')
												.attr('type','file')
												.attr('id','ugcUploadFile2' + '_' + elem.attr('data-id'))
												.attr('name','ugcUploadFile[]')
												.attr('multiple', '')
												.on('change', function() {
													uploadFilesAddress(this.files);
											})
										)
										.append(
											$('<br/>')
										)
										.append(
											$('<input/>')
												.attr('type','submit')
												.attr('value','Upload')
												.addClass('orangeButton').on('click', function(){
												if($('#ugcUploadFile2' + '_' + elem.attr('data-id')).val() === ''){
													prettyConfirm(
														'No File Found',
														'Please choose a file first!',
														{
															'OK:active': null
														}
													);
													event.preventDefault();
												} else {
													elem.find('.step2').append(
														$('<div/>').addClass('dContentLoadingAddress').append(
															$('<img/>').attr('src', '/html/img/designer/image_loading.gif')
														)
													);
												}
											})
										)
									.append(
										$('<iframe/>')
											.addClass('ugcFrame2')
											.attr('name', 'ugcFrame2' + '_' + elem.attr('data-id'))
											.attr('id', 'ugcFrame2' + '_' + elem.attr('data-id'))
											.attr('src', 'about:blank')
									)
								).append(
									$('<div/>').attr('id', 'uploadMessage').html('UPLOAD OUR TEMPLATE HERE FROM YOUR OWN PERSONAL COMPUTER')
								).append(
									$('<div/>').attr('id', 'fileInfo').html('You can only upload our formated template (csv) up to 20MBs in size.')
								).append(
									$('<div/>').attr('id', 'uploadButton').html('<br/> &nbsp; <br/><br/>').append(
										$('<div/>').addClass('orangeButton').html('CLICK HERE TO UPLOAD').on('click', function(){
											$('#ugcUploadFile2' + '_' + elem.attr('data-id')).trigger('click');
										})
									)
								)
							).append(
								$('<div/>').attr('class', 'goBack').html('GO BACK').on('click', function(){
									$(this).closest('.step2').hide();
									$(this).closest('.content').find('.step1').show();
								})
							)
						).append(
							$('<div/>').addClass('step3').hide().append(
								$('<label/>').attr('for', 'addressBookName').html('Address Book Name: ')
							).append(
								$('<input/>').attr('type', 'text').attr('id', 'addressBookName').on('keyup', function(){
									var self = this;
									waitForFinalEvent(function() {
										$(self).parent().find('.status').remove();
										var status = $('<img/>').addClass('status').attr('src', '/html/img/designer/spinner.gif').insertAfter(self);
										$.ajax({
											url: '/envelopes/control/updateAddressBook',
											dataType: 'json',
											type: 'POST',
											data:{id:$('#addressBookId').val(), value:$('#addressBookName').val()},
											context: document.body
										}).done(function(data) {
											status.attr('src', '/html/img/designer/green_check.gif');
											window.setTimeout(function(){status.remove();}, 3000);
											loadAddressBooks();
										});
									},1000 , 'updateAddressBook');
								})
							).append(
								$('<img/>').addClass('deleteAddBook').attr('src', '/html/img/designer/od_delete.png').on('click', function() {
									var self = this;
									prettyConfirm(
										'Are you sure?',
										'Do you want to remove this entire address book?',
										{
											'Yes:active': function(){
												removeAddressBook($('#addressBookId').val());
												$(self).closest('.step3').hide();
												$(self).closest('.content').find('.step1').show();
											},
											'No': null
										}
									);
								})
							).append(
								$('<input/>').attr('hidden', 'text').attr('id', 'addressBookId')
							).append(
								$('<div/>').addClass('addressBookTableWrap').append(
									$('<table/>').addClass('addressBookTable').append(
										$('<thead/>').append(
											$('<tr/>').append(
												$('<th/>').attr('id', 'corner').html('')
											).append(
												$('<th/>').attr('id', 'addressName1').html('Name Line 1')
											).append(
												$('<th/>').attr('id', 'addressName2').html('Name Line 2')
											).append(
												$('<th/>').attr('id', 'addressAddress1').html('Address 1')
											).append(
												$('<th/>').attr('id', 'addressAddress2').html('Address 2')
											).append(
												$('<th/>').attr('id', 'addressCity').html('City')
											).append(
												$('<th/>').attr('id', 'addressState').html('State')
											).append(
												$('<th/>').attr('id', 'addressZip').html('Zip')
											).append(
												$('<th/>').attr('id', 'addressCountry').html('Country')
											).append(
												$('<th/>').html('').addClass('addressControls')
											)
										)
									).append(
										$('<tbody/>')
									)
								).on('scroll', function(e){
									$(this).find('.fixedTableHeader').css('top', $('#addressing_' + elem.attr('data-id')).find('.addressBookTableWrap').scrollTop());
								})
							).append(
								$('<div/>').attr('id', 'addAddress').html('+ ADD LINE').on('click', function(){
									addDataRow({id:-1});
									GoogleAnalytics.trackEvent('Product Page', 'Scene7v2', 'Added New Address Row');
									$('#addressing_' + elem.attr('data-id')).find('.addressBookTable').first().find('.save').trigger('click');
									$('#addressing_' + elem.attr('data-id')).find('.addressBookTable').first().find('tr').last().find('td').first().next().trigger('click');
								}).append(
									$('<span/>').attr('id', 'addressBookCount').html('(0 addresses)')
								)
							).append(
								$('<div/>').attr('id', 'saveAddresses').html('APPLY TO DESIGN').on('click', function() {
									applyAddresses(null, true);
								})
							).append(
								$('<div/>').attr('id', 'startOver').html('PICK ANOTHER LIST').on('click', function(){
									$(this).closest('.step3').hide();
									$(this).closest('.content').find('.step1').show();
								})
							)
						)
					)
				)
			).append(
				$('<div/>').hide().attr('id', 'loginRegister').addClass('loginRegister').addClass('emailLoginForm').append(
					$('<div/>').addClass('content').append(
						$('<div/>').addClass('head').append(
							$('<span/>').addClass('name').html('LOGIN OR REGISTER')
						).append(
							$('<span/>').addClass('close').html('x')
						)
					).append(
						$('<div/>').addClass('body').append(
							$('<div/>').addClass('login').append(
								$('<span/>').addClass('title').html('Login/Register')
							).append(
								$('<input/>').attr('type', 'text').attr('name', 'USERNAME').attr('value', '').attr('placeholder', 'Enter your email address').addClass('text')
							).append(
								$('<input/>').attr('type', 'password').attr('name', 'PASSWORD').attr('value', '').attr('placeholder', 'Enter your password').addClass('text')
							).append(
								$('<a/>').addClass('create margin-top-xs').attr('href', '').html('LOGIN').on('click', function(e){
									submitLoginRegister(this, 'loginUser');
									e.preventDefault();
								})
							).append(
								$('<a/>').addClass('create margin-top-xs').attr('href', '').html('REGISTER').on('click', function(e){
									submitLoginRegister(this, 'registerUser');
									e.preventDefault();
								})
							).append(
								$('<span/>').hide().addClass('loginError').html('There was an error. Please try again.')
							)
						)
					)
				)
			).append(
                $('<div/>').hide().attr('id', 'loginRegisterAndSaveDesign').addClass('loginRegisterAndSaveDesign').addClass('emailLoginForm').append(
                    $('<div/>').addClass('content').append(
                        $('<div/>').addClass('head').append(
                            $('<span/>').addClass('name').html('LOGIN OR REGISTER')
                        ).append(
                            $('<span/>').addClass('close').html('x')
                        )
                    ).append(
                        $('<div/>').addClass('body').append(
                            $('<div/>').addClass('login').append(
                                $('<span/>').addClass('title').html('Login/Register')
                            ).append(
                                $('<input/>').attr('type', 'text').attr('name', 'USERNAME').attr('value', '').attr('placeholder', 'Enter your email address').addClass('text')
                            ).append(
                                $('<input/>').attr('type', 'password').attr('name', 'PASSWORD').attr('value', '').attr('placeholder', 'Enter your password').addClass('text')
                            ).append(
                                $('<a/>').addClass('create margin-top-xs').attr('href', '').html('LOGIN').on('click', function(e){
                                    submitLoginRegisterAndSaveDesign(this, 'loginUser');
                                    e.preventDefault();
                                })
                            ).append(
                                $('<a/>').addClass('create margin-top-xs').attr('href', '').html('REGISTER').on('click', function(e){
                                    submitLoginRegisterAndSaveDesign(this, 'registerUser');
                                    e.preventDefault();
                                })
                            ).append(
                                $('<span/>').hide().addClass('loginError').html('There was an error. Please try again.')
                            )
                        )
                    )
                )
            ).append(
                $('<div/>').hide().attr('id', 'saveDesignConfirmation').addClass('saveDesignConfirmation').addClass('emailLoginForm').addClass('saveDesignDialog').append(
                    $('<div/>').addClass('content').append(
                        $('<div/>').addClass('head').append(
                            $('<span/>').addClass('name').html('SAVE DESIGN')
                        ).append(
                            $('<span/>').addClass('close').html('x')
                        )
                    ).append(
                        $('<div/>').addClass('body').append(
                            $('<div/>').addClass('dialogButton').append(
                                $('<div/>').addClass('confirmationMessage').html('The current design has been saved successfully.')
                            ).append(
                                $('<div/>').hide().addClass('saveDesignError').html('An error occurred while saving the design. Please try again.')
                            ).append(
                                $('<div/>').append(
                                $('<a/>').addClass('create margin-top-xs').attr('href', '').html('OK').on('click', function(e){
                                    $(this).closest('.saveDesignConfirmation').draggable('destroy').bPopup().close();
                                    e.preventDefault();
                                }))
                            )
                        )
                    )
                )
            );

			loadImageLib('#imgLibUser', 'http://actionenvelopew2p.scene7.com/is/image/', '');
			loadAddressBooks();
			getUserLogin();

			if(settings.loadFromProduct == true) {
				loadDesign();
			} else {
				loadEditableElements(settings.activeDesign, false, true);
				if(typeof isUploadedDesign != 'undefined' && isUploadedDesign != false) {
					loadEditableElements(settings.activeDesign, true, true);
				}
			}

			//load product specific border decorators
			if(settings.decorators.verticalFolded){
				elem.find('#templateController').prepend(
					$('<div/>').attr('id', 'verticalFoldedShadow')
				).prepend(
					$('<div/>').attr('id', 'verticalFoldedShadowBorder')
				);
			}
			if(settings.decorators.folded){
				elem.find('#templateController').prepend(
					$('<div/>').attr('id', 'foldedShadow')
				).prepend(
					$('<div/>').attr('id', 'foldedShadowBorder').append(
						$('<div/>').addClass('footer').css({
							width: '70px',
							position: 'absolute',
							right: '3px',
							'z-index': -1
						})
					)
				);
			}

			idealZoom();
			settings.animate = true;
		};

		var exitValidation = function(forward) {
			/*var unchanged = [];
			$.each(designs, function(k, v){
				$.each(v.elements, function(id, el){
					if(el.deleted == false && el.changed == false && el.type != 'Path' && el.id.indexOf('stamp_mark') == -1) {
						unchanged.push(el);
					}
				})
			});

			if(unchanged.length > 0) {
				prettyConfirm(
					'Are you sure?',
					'Your online design contains<br/>unedited images or text.<br/><br/>'+
					'<b>Continue:</b> Approve your design as is.<br/>'+
					'<b>Remove:</b> Remove these items and re-approve.<br/>'+
					'<b>Go Back:</b> edit your design.<br/>',
					{
						'Remove': function () {
							createHistory();
							$.each(unchanged, function(k,v){
								v.deleted = true;
								elem.find('#'+v.id).remove()
							});
							paintEditor(settings.activeDesign);
							elem.find('#saveInfo').hide().show();
						},
						'Go Back': null,
						'Continue:active': function(){
							approve(true);
							$('body').data('slider').navigatorTrigger(forward);
						}
					}
				);
			} else {
				approve(true);
				$('body').data('slider').navigatorTrigger(forward);
			}*/
			approve(true);
		};

		var paintEditor = function(designId){
			var noLoadScreen = false;
			if(!noLoadScreen) {
				elem.find('#templateControlContainer > .curtain').fadeIn('fast');
			}

			if(!designs[designId].loadedTemplate){
				loadEditableElements(designId, false, true);
				return;
			}

			if(settings.activeDesign != designId) {
				initEditor();
				addEditorElements(designId);
				setBleedMargins();
				settings.activeDesign = designId;
			}

			//we should reset the background color here if they happen to choose a different product and the sku
			if(typeof settings.product !== 'undefined' && settings.product != null) {
				if(typeof settings.product.color !== 'undefined' && settings.product.color != null) {
					settings.backgroundColor = settings.product.color;
				}
				if(typeof settings.product.sku !== 'undefined' && settings.product.sku != null) {
					settings.productId = settings.product.sku;
				}
			}

			log('Painting', settings.debug);
			if(showItems) {
				elem.find('.elementRichText, .elementPath, .elementRect, .elementBitmapImage').addClass("elementActive");
			}

			if(showGrid) {
				elem.find('#gridLines').show();
			}

			var params = generateParamsForDesign(designs[settings.activeDesign]);
			waitForFinalEvent(function() {
				generateScene7Image(
					params,
					null,
					function(data){
						return function(sideId) {
							if(sideId != settings.activeDesign){return;}
							designs[settings.activeDesign].scene7url = data.scene7url;
							lastImage = ((data.type === 'url') ? '' : 'data:image/' + imageQuality.fmt + ';base64,') + data.data;

							elem.find("#scene7ImageURL").attr("src", "").attr("src", ((data.type === 'url') ? '' : 'data:image/' + imageQuality.fmt + ';base64,') + data.data);
							if(settings.activeDesign == 0) {
								if($('body').data('product') !== 'undefined') {
									$('body').data('product').updateProductImage(settings.index, ((data.type === 'url') ? '' : 'data:image/' + imageQuality.fmt + ';base64,') + data.data, true);
								}
							}
							elem.find("#templateControlContainer").find(".elementRichText").powerTip({manual:false}).removeClass("overflow_warning").find(".warning_icon").remove();

							$.each(data.overflow, function(){
								elem.find("#"+this).powerTip({manual:true}).powerTip("hide").addClass("overflow_warning").append(
									$("<img />").attr("src", "/html/img/designer/warning.png")
												 .attr("class", "warning_icon")
												 .data("powertip", "<div style=\"text-align:center;font-size:12px;\">Text Is Too Big!<br/>"+
													   "Please use fewer words, a smaller font size,<br/> choose a different font or resize "+
													   "this text box.</div>")
												 .powerTip({
													popupId:'errorTip'
												 })
								).css('z-index', 99999);
							});
							paintSides(settings.activeDesign);
						}(designId)
					},
					function(XMLHttpRequest, textStatus, errorThrown) {
						historyBack();
						//Raygun.send(new Error('There was an error trying to process: ' + designs[settings.activeDesign].templateId), [params]);
					}
				);
			} ,500 ,'loadScene7');
		};

		var paintSides = function(designId) {
			elem.find('#templateSides').empty();
			$.each(designs, function(k) {
				var innerBox = $('<div/>').addClass('innerBox').html(designs[k].templateSide);
				if(k == designId) {
					innerBox.addClass('active');
				}

				if(k != defaultDesign) {
					if(designs[k].scene7url !== null) {
						innerBox.append($('<div />').addClass('delete').html("<a href=\"javascript:void(0);\">x</a>").on('click', function() {
							prettyConfirm(
								'Are you sure?',
								'Do you want to clear the '+designs[k].templateSide+' of this design?',
								{
									'Yes:active' : function () {
										clearApproval();
										designs[k].loadedTemplate = false;
										designs[k].elements = [];
										designs[k].unEditableColors = [];
										designs[k].scene7url = null;
										if(k != settings.activeDesign){
											paintEditor(settings.activeDesign);
										} else {
											paintEditor(defaultDesign);
										}
									},
									'No:active' : null
								}
							);
						}));
					} else {
						innerBox.append($('<div />').addClass('add').html("<a href=\"javascript:void(0);\">+</a>"));
					}
				}

				elem.find('#templateSides').append(
					$('<div/>').addClass('box').append(innerBox).on('click', function() {
						//$('#templateSides > .box > .innerBox').removeClass('active');
						//$(this).find('.innerBox').addClass('active');
						$('#imageEditPop, #textEditPop, #colorEditPop').each(function(k,v) {
							if($(v).css('display') != 'none'){
								$(v).bPopup().close();
							}
						});

						if(settings.activeDesign == k ) return;

						if($(this).find('.add').length > 0 ){
							clearApproval();
						}

						elem.find('#foldedShadowBorder, #foldedShadow').animate({
							'border-left-width':0
						}, 10)

						elem.find('#verticalFoldedShadowBorder, #verticalFoldedShadow').animate({
							'border-top-width':0
						}, 10)

						elem.find('#templateControlContainer').flippy({
							duration: 500,
							color_target: settings.backgroundColor,
							light:0,
							onFinish: function(){
								paintEditor(parseInt(k));
								elem.find('#foldedShadowBorder, #foldedShadow').animate({
									'border-left-width':50
								}, 50);
								elem.find('#verticalFoldedShadowBorder, #verticalFoldedShadow').animate({
									'border-top-width':18
								}, 50);
							}
						});
					})
				);
			});
		};

		var generateParamsForDesign = function(design, maskAddress){
			var params = {};
			params["url"] = templateUrl + design.templateId;
			params["fmt"] = imageQuality.fmt;
			params["qlt"] = imageQuality.qlt;
			params["wid"] = staticWidth;

			for(var i = 0; i < design.elements.length; i++) {
				var el = design.elements[i];

				//handle deleted elements right away
				if(el.deleted) {
					if (!el.custom){
						params["setAttr." + el.id] = '{visible=false}';
					}
					continue;
				}

				//weird offset for groups in groups
				var offsetX = 0;
				var offsetY = 0;
				if(el.custom && design.lastElementId.indexOf('IMAGE') != -1) {
					var offsets = getObjParentOffsetByName(design.lastElementId, design);
					offsetX = offsets.x;
					offsetY = offsets.y;
				}

				if(el.type === "RichText") {
					if(el.id === "RichText_addressing") {
						el.text = designs[settings.activeDesign].addresses[designs[settings.activeDesign].activeAddress];
					}
					var longestLine = 0;
					var numberOfLines = 0;
					var scene7ValidTextElement = "";
					if(!maskAddress || (maskAddress && el.id !== "RichText_addressing")) {
						scene7ValidTextElement += "<content>";
						$.each((replaceBreakLines(el.text)).split("\n"), function(index, eachLine) {
							numberOfLines++;
							if(eachLine.length > longestLine) {
								longestLine = eachLine.length;
							}
							scene7ValidTextElement += buildEachLine(
														charEncodingForScene7(eachLine), el.fontSize, el.textAlign,
														el.textDecoration, el.fontWeight, el.fontFamily, el.lineHeight, el.tracking, el.color
							);
						});
						scene7ValidTextElement += "</content>";
					}

					var x = el.x;
					var y = el.y;

					if(typeof isUploadedDesign != 'undefined' && isUploadedDesign != false) {
						if(el.fitToSize) {
							resizeTextEditorToFitLength(el, longestLine, numberOfLines);
						}
						el.fitToSize = false;
					}

					var height = el.height;
					var width = el.width;
					var rotation = normalizeRotation(el.rotation - el.originalRotation);

					if(rotation == 90) {
						if (el.originalRotation == 0){
							x += el.width;
						} else {
							y += el.width;
						}
						height = el.width;
						width = el.height;
					} else if(rotation == 180) {
						if (el.originalRotation == 0){
							x += el.width;
							y += el.height
						} else {
							x -= el.height;
							y += el.width;
						}
					} else if(rotation == 270) {
						if (el.originalRotation == 0){
							y += el.height
						} else{
							x -= el.height;
						}
						height = el.width;
						width = el.height;
					}

					if(el.fontWeight != '') {
						params['deleteAttr.'+el.id] = '(fontWeight)';
					}

					if(el.custom) {
						var xml = '<RichText';
						xml += ' s7:elementID="' + el.id + '" ';
						xml += ' rotation="' + rotation + '" ';
						xml += ' x="' + (x - offsetX) + '" ';
						xml += ' y="' + (y - offsetY) + '" ';
						xml += ' width="' + width + '" ';
						xml += ' height="' + height + '" ';
						xml += '>' + scene7ValidTextElement + '</RichText>';

						if (typeof params["insertAfter." +  design.lastElementId] == 'undefined'){
							params["insertAfter." +  design.lastElementId] = [encodeURIComponent(xml)];
						} else {
							params["insertAfter." +  design.lastElementId].unshift( encodeURIComponent(xml) );
						}
					} else {
						params["setElement." + el.id] = encodeURIComponent( scene7ValidTextElement );
						params["setAttr." + el.id] = "{x=" + x + "%26y=" + y+ "%26width=" + width + "%26height=" + height + "%26rotation=" + el.rotation +  '%26typographicCase=lowercase}';
					}

				} else if(el.type === "BitmapImage") {
					var imageXml = '';
					var top = 0;
					var left = 0;
					var w = 0;
					var h = 0;
					var imgRatioW = (el.innerImage.maxWidth / el.innerImage.width);
					var imgRatioH = (el.innerImage.maxHeight / el.innerImage.height);

					var imageParams = {};

					if (el.innerImage.rotation === 0) {
						top = (Math.abs(el.innerImage.top) / (el.innerImage.height)) * el.innerImage.maxHeight ;
						left = (Math.abs(el.innerImage.left) / (el.innerImage.width)) * el.innerImage.maxWidth ;
						h =  el.height / el.innerImage.height * el.innerImage.maxHeight;
						w = el.width / el.innerImage.width * el.innerImage.maxWidth;
						imageParams.wid = Math.ceil( w );
						imageParams.hei = Math.ceil( h );
					} else if (el.innerImage.rotation == 90) {
						top = (el.innerImage.maxLeft - el.innerImage.leftPerc) * el.innerImage.maxHeight;
						left = ((Math.abs(el.innerImage.top) / (el.innerImage.height)) * el.innerImage.maxWidth);
						w = el.height / el.innerImage.height * el.innerImage.maxWidth;
						h = el.width / el.innerImage.width * el.innerImage.maxHeight;
						imageParams.wid = Math.ceil( h );
						imageParams.hei = Math.ceil( w );
					} else if (el.innerImage.rotation == 180) {
						top = (el.innerImage.maxTop - el.innerImage.topPerc) * el.innerImage.maxHeight;
						left = (el.innerImage.maxLeft - el.innerImage.leftPerc) * el.innerImage.maxWidth;
						h = el.height / el.innerImage.height * el.innerImage.maxHeight;
						w = el.width / el.innerImage.width * el.innerImage.maxWidth;
						imageParams.wid = Math.ceil( w );
						imageParams.hei = Math.ceil( h );
					} else if (el.innerImage.rotation == 270){
						top = ((Math.abs(el.innerImage.left) / (el.innerImage.width)) * el.innerImage.maxHeight);
						left = (el.innerImage.maxTop - el.innerImage.topPerc) * el.innerImage.maxWidth;
						w = el.height / el.innerImage.height * el.innerImage.maxWidth;
						h = el.width / el.innerImage.width * el.innerImage.maxHeight;
						imageParams.wid = Math.ceil( h );
						imageParams.hei = Math.ceil( w );
					}
					imageParams.crop = left + "," + top + "," + w + "," + h;
					imageParams.op_brightness = el.innerImage.brightness;
					imageParams.op_contrast = el.innerImage.contrast;
					imageParams.op_grow = el.innerImage.grow;
					if (el.innerImage.colorize != 0) {
						imageParams.op_colorize = el.innerImage.colorize;
					}
					imageParams.op_saturation = el.innerImage.saturation;
					imageParams.rotate = el.innerImage.rotation;
					imageParams = encodeURIComponent(makeStringFromParam(imageParams));

					var imageUrl = el.url.substring(el.url.indexOf('ActionEnvelope/'));

					if (imageUrl.indexOf('fxgHolder') !== -1){
						imageXml = "source=@Embed('is(/ActionEnvelope?src=fxg{/" + (imageUrl.replace(/%26s7:fit=fit}/g, '')) + '}%26amp;' + imageParams + ")')";
					} else {
						imageXml = "source=@Embed('is(" + imageUrl + '?' + imageParams + ")')" + ((el.custom) ? "" : "%26s7:fit=fit" );
					}

					if(el.custom){
						var xml = '<Group';
						xml += ' x="' + (el.x-offsetX) + '"';
						xml += ' y="' + (el.y-offsetY) + '"';
						xml += ' s7:referencePoint="inherit"><BitmapImage';
						xml += ' s7:elementID="' + el.id + '"';
						xml += ' scaleX="1"';
						xml += ' scaleY="1"';
						xml += ' s7:fillOverprint="false"';
						xml += ' s7:fillOverprintMode="true"';
						xml += ' s7:fit="stretch"';
						xml += ' s7:strokeOverprint="false"';
						xml += ' s7:strokeOverprintMode="true"';
						xml += ' height="'+el.height+'"';
						xml += ' width="'+el.width+'"';
						xml += ' source="' + imageXml.replace(/%26/g,"%26amp;").replace("source=","") + '"';
						xml += '/></Group>';

						if(typeof params["insertAfter." +  design.lastElementId] == 'undefined'){
							params["insertAfter." +  design.lastElementId] = [encodeURIComponent(xml)];
						} else {
							params["insertAfter." +  design.lastElementId].unshift( encodeURIComponent(xml) );
						}
					} else {
						params["setAttr." + el.id] = "x=" + (el.x - el.parentX) + "%26y=" + (el.y - el.parentY)+"%26"+"%26width=" + (el.width / el.scaleX) + "%26height=" + (el.height / el.scaleY) + imageXml;
					}
				} else if(el.type === 'Path') {
					var xml = "";

					//background color override
					if(el.id == 'COLOR_bgcolor'){
						el.pathData[0].pathType = 'fill'
						el.pathData[0].color = settings.backgroundColor;
					}

					$.each(el.pathData, function(index) {
						if(el.pathData[index].pathType === "fill") {
							xml += "<fill><SolidColor color=\"" + el.pathData[index].color + "\"/></fill>";
						} else if(el.pathData[index].pathType === "stroke") {
							xml += "<stroke><SolidColorStroke color=\"" + el.pathData[index].color + "\" joints=\"" + el.pathData[index].joints + "\" caps=\"" + el.pathData[index].caps + "\" miterLimit=\"" + el.pathData[index].miterLimit + "\" weight=\"" + el.pathData[index].weight + "\"/></stroke>";
						}
					});

					if(el.custom) {
						var xmlContainer = '<Path';
						xmlContainer += ' x="' + ((typeof el.x !== 'undefined') ? (el.x - offsetX) : 0) + '"';
						xmlContainer += ' y="' + ((typeof el.y !== 'undefined') ? (el.y - offsetY) : 0) + '"';
						xmlContainer += ' data="' + el.data + '">';
						xmlContainer += xml;
						xmlContainer += '</Path>';

						if(typeof params["insertAfter." +  design.lastElementId] == 'undefined'){
							params["insertAfter." +  design.lastElementId] = [encodeURIComponent(xmlContainer)];
						} else {
							params["insertAfter." +  design.lastElementId].unshift(encodeURIComponent(xmlContainer) );
						}
					} else {
						params["setElement." + el.id] = encodeURIComponent(xml);
						params["setAttr." + el.id] = "{x=" + ((typeof el.x !== 'undefined') ? (el.x - offsetX) : 0) + "%26y=" + ((typeof el.y !== 'undefined') ? (el.y - offsetY) : 0) + "}";
					}
				} else if(el.type === 'Rect') {
					var xml = "";

					if(el.pathType === "fill") {
						xml = "<fill><SolidColor color=\"" + el.color + "\"/></fill>";
					} else if( el.pathType === "stroke") {
						xml = "<stroke><SolidColorStroke color=\"" + el.color + "\" joints=\"" + el.joints + "\" caps=\"" + el.caps + "\" miterLimit=\"" + el.miterLimit + "\" weight=\"" + el.weight + "\"/></stroke>";
					}

					if(el.custom) {
						var xmlContainer = '<Rect';
						xmlContainer += ' x="' + ((typeof el.x !== 'undefined') ? (el.x - offsetX) : 0) + '"';
						xmlContainer += ' y="' + ((typeof el.y !== 'undefined') ? (el.y - offsetY) : 0) + '"';
						xmlContainer += ' height="' + el.height + '"';
						xmlContainer += ' width="' + el.width + '">';
						xmlContainer += xml;
						xmlContainer += '</Rect>';

						if(typeof params["insertAfter." +  design.lastElementId] == 'undefined'){
							params["insertAfter." +  design.lastElementId] = [encodeURIComponent(xmlContainer)];
						} else {
							params["insertAfter." +  design.lastElementId].unshift(encodeURIComponent(xmlContainer) );
						}
					} else {
						params["setElement." + el.id] = encodeURIComponent(xml);
						params["setAttr." + el.id] = "{x=" + ((typeof el.x !== 'undefined') ? (el.x - offsetX) : 0) + "%26y=" + ((typeof el.y !== 'undefined') ? (el.y - offsetY) : 0) + "}";
					}
				}
			}

			for (var i = 0; i < design.hiddenElements.length; i++){
				var el = design.hiddenElements[i].id;
				params["setAttr." + el] = '{visible=false}';
			}

			return params;
		};

		var generateScene7Image = function(params, width, success, error, async) {
			if (typeof width != 'undefined' && width != null){ params['wid'] = width; }
			if (typeof async === 'undefined'){ async = true; }
			$.ajax({
				url : 'http://scene7.envelopes.com',
				type: "POST",
				timeout: 15000,
				data: params,
				success: success,
				error: error,
				async: async
			});
		};

		var loadEditableElements = function(designId, loadUGC, paint) {
			if(typeof paint == 'undefined' || paint == null){
				paint = false;
			}

			$.ajax({
				type: "GET",
				async: false,
				url: '/' + websiteId + '/control/fxg.xml?id=' + designs[designId].templateId + '&templateType=' + designs[designId].templateType + ((loadUGC && typeof isUploadedDesign != 'undefined' && isUploadedDesign != false) ? '&ugcId=' + isUploadedDesign : ''),
				dataType: "xml",
				success: function(xml) {
					settings.templateWidth = parseFloat($(xml).find('Graphic').attr('viewWidth'));
					settings.templateHeight = parseFloat($(xml).find('Graphic').attr('viewHeight'));
					staticHeight = settings.templateHeight / settings.templateWidth * staticWidth;

					$(xml).find('s7\\:Color, Color').each(function(index) {
						if($(this).attr("colorName") === "Black") {
							settings.colorMap["Black"] = "#000000";
						} else if($(this).attr("colorName") === "Paper") {
							settings.colorMap["Paper"] = "#FFFFFF";
						} else {
							var parsedColor = $(this).attr("colorName").match(/C=([0-9]+)\sM=([0-9]+)\sY=([0-9]+)\sK=([0-9]+)/);
							if(parsedColor != null) {
								settings.colorMap[$(this).attr("colorName")] = cmykToHex(parseInt(parsedColor[1]), parseInt(parsedColor[2]), parseInt(parsedColor[3]), parseInt(parsedColor[4]));
								return;
							}

							parsedColor = $(this).attr("colorName").match(/([0-9]+),([0-9]+),([0-9]+),([0-9]+)/);
							if(parsedColor != null) {
								settings.colorMap[$(this).attr("colorName")] = cmykToHex(parseInt(parsedColor[1]), parseInt(parsedColor[2]), parseInt(parsedColor[3]), parseInt(parsedColor[4]));
								return;
							}

							parsedColor = $(this).attr("colorName").match(/R=([0-9]+)\sG=([0-9]+)\sB=([0-9]+)/);
							if(parsedColor != null) {
								settings.colorMap[$(this).attr("colorName")] = rgbToHex(parseInt(parsedColor[1]), parseInt(parsedColor[2]), parseInt(parsedColor[3]));
								return;
							}
						}
					});

					$(xml).find('RichText, Path, BitmapImage, Rect').each(function(index) {
						if(loadUGC && typeof isUploadedDesign != 'undefined' && isUploadedDesign != false && $(this).prop("nodeName") != 'BitmapImage') {
							$(this).attr('s7:elementID', 'UGC_' + index + '');
						}
						if(typeof $(this).attr('s7:elementID') !== 'undefined') {
							var elementId = $(this).attr('s7:elementID');
							var nodeType = $(this).prop("nodeName");
							var node = {};
							if (!isElementVisible(elementId, designs[designId], loadUGC)){
								if (nodeType === "RichText"){
									designs[designId].hiddenElements.push( createRichText($(this), loadUGC, false, loadUGC) );
								} else if(nodeType === "BitmapImage"){
									designs[designId].hiddenElements.push( createImage($(this)) );
								} else if(nodeType === "Path"){
									designs[designId].hiddenElements.push( createPath($(this), loadUGC, loadUGC) );
								} else if(nodeType === "Rect"){
									designs[designId].hiddenElements.push( createRect($(this), loadUGC, loadUGC) );
								}
							} else {
								if (nodeType === "RichText"){
									designs[designId].elements.push( createRichText($(this), loadUGC, false, loadUGC) );
								} else if(nodeType === "BitmapImage"){
									designs[designId].elements.push( createImage($(this)) );
								} else if(nodeType === "Path"){
									designs[designId].elements.push( createPath($(this), loadUGC, loadUGC) );
								} else if(nodeType === "Rect"){
									designs[designId].elements.push( createRect($(this), loadUGC, loadUGC) );
								}
							}
						} else {
							if($(this).prop("nodeName") !== "BitmapImage"){
								var color = getColor($(this));
								if ($.inArray(color, designs[designId].unEditableColors) == -1){
									designs[designId].unEditableColors.push(color);
								}
							}
						}
					});
				},
				complete: function(xml) {
					designs[designId].loadedTemplate = true;
					if(paint){
						paintEditor(designId);
					}
				}
			});
		};

		var viewImageLibrary = function(obj, target){
			obj.addClass('active');
			obj.siblings().removeClass('active');
			target.siblings().hide();
			target.show();
		};

		var deleteImage = function(id, elem){
			elem = $(elem);
			$.ajax({
				url: '/envelopes/control/removeUGCFile',
				cache: false,
				data: {id:id},
				beforeSend: function( ) {
					elem.slideUp();
				}
			}).done(function( data ) {
				elem.remove();
			}).error(function(  ) {
				elem.slideDown();
			});
		};

		var loadImageLib = function(target, path, category, force){
			force = (typeof force == 'undefined')?false:force;

			var holder = elem.find(target);

			if (force || holder.find('ul').length == 0){
				$.ajax({
					url: '/' + websiteId + '/control/getImageLibrary',
					cache: false,
					data: {catId:category.toUpperCase()}
				}).done(function( data ) {
					holder.find('ul').remove();
					holder.append($('<ul>'));
					holder = holder.find('ul')
					$.each(data.content, function(k,v){
						var assetUrl = '';
						var attribUrl = '';
						var fullUrl = '';

						if (v.assetUrl.indexOf('fxg') !== -1){
							assetUrl = "http://actionenvelope.scene7.com/is/agm/ActionEnvelope/fxgHolder?setAttr.IMAGEfxgimage={source=@Embed('" + v.assetUrl + "')%26s7:fit=fit}&fmt=png-alpha&wid=100&hei=100";
							attribUrl = "http://actionenvelope.scene7.com/is/agm/ActionEnvelope/fxgHolder?setAttr.IMAGEfxgimage={source=@Embed('" + v.assetUrl + "')%26s7:fit=fit}";
							fullUrl = "http://actionenvelope.scene7.com/is/image/ActionEnvelope?src=fxg{/ActionEnvelope/fxgHolder?setAttr.IMAGEfxgimage={source=@Embed('" + v.assetUrl + "')%26s7:fit=fit}}";
						} else {
							assetUrl = path + v.assetUrl + '?fmt=png-alpha&wid=100&hei=100';
							attribUrl = path + v.assetUrl;
							fullUrl = attribUrl;
						}

						holder.append(
							$('<li/>').addClass('dContent').append(
								$('<img/>').attr('src', assetUrl)
									.data('attriburl', attribUrl)
									.data('fullurl',fullUrl)
							).data('data-id', (category == '')?v.id:undefined)
						);
					});

					makeSelectable(elem.find('.dContent>img'));
				});
			}
		};

		var createNewAddressBook = function(){
			$.ajax({
				url: '/' + websiteId + '/control/createAddressBook',
				cache: false,
				async: false
			}).done(function( data ) {
				GoogleAnalytics.trackEvent('Product Page', 'Scene7v2', 'Created New Address Book');
				loadAddressBooks();
				loadAddressBook(data.id, true);
				for (var i = 0; i < settings.product.quantity && i < 250; i++){
					addDataRow({id:-1});
				}
				//wait for render before trigger
				window.setTimeout(function(){
					$('#addressing_' + elem.attr('data-id')).find('.addressBookTable').first().find('tr').find('td[class!=count]').first().trigger('click').find('input[type=text]').focus();
				}, 500);
			});
		};

		var loadAddressBooks = function() {
			$.ajax({
				url: '/' + websiteId + '/control/getAddressBooks',
				cache: false,
			}).done(function( data ) {
				if(typeof data.data !== 'undefined') {
					$('body').find('.existingAddressBooks').empty();
					$.each(data.data, function(k,v){
						$('body').find('.existingAddressBooks').append(
							$('<li/>').html(v.name).on('click', function(){
								$(this).closest('.step1').hide();
								loadAddressBook(v.id, true);
							})
						)
					})
				}
			});
		};

		//TODO is this function used?
		var getTotalAddresses = function(id) {
			var total = 0;
			$.ajax({
				url: '/' + websiteId + '/control/getTotalAddresses',
				dataType: 'json',
				async : true,
				type: 'POST',
				context: document.body,
				data: { id: id }
			}).done(function( data ) {
				total = data.total;
			});

			return total;
		};

		function applyAddresses(active, repaint) {
			if(active == null) {
				active = 0;
			}

			//remove empty rows
			$.each($('#addressing_' + elem.attr('data-id')).find('.addressBookTable > tbody> tr'), function(k,v){
				//check for empty data
				var hasData = false;
				$.each($(v).find('td'), function(k2,v2){
					$(v2).find('.save').trigger('click');
					if($(v2).text().trim() != "" && !$(v2).hasClass('noedit') && !$(v2).hasClass('count')){
						hasData = true;
					}
				});
				if (!hasData){
					$(v).remove();
				}
			});

			if($('#addressing_' + elem.attr('data-id')).find('.addressBookTable > tbody> tr').length ==0){
				prettyConfirm(
					'Warning',
					'You must have at least one address.<br/>',
					{
						'OK:active': null
					}
				);
				return;
			}

			if(countAddresses() < minAddresses) {
				showWarningMessage('You have only added ' + countAddresses() + ' addresses, there is a minimum of ' + minAddresses + ' addresses required for printing variable addresses.');
			}

			createHistory();
			var self = this;
			designs[settings.activeDesign].addresses = [];
			var address = '';
			$.each($('#addressing_' + elem.attr('data-id')).find('.addressBookTable > tbody>  tr'), function(){
				address = $($(this).find('td')[1]).html()+'\n';
				address += $($(this).find('td')[2]).html()+'\n';
				address += $($(this).find('td')[3]).html()+'\n';
				address += $($(this).find('td')[4]).html()+'\n';
				address += $($(this).find('td')[5]).html()+', ';
				address += $($(this).find('td')[6]).html()+' ';
				address += $($(this).find('td')[7]).html()+'\n';
				address += $($(this).find('td')[8]).html();
				address = address.replace(/\n[\s,]*\n/g, '\n');
				address = $('<div/>').html(address).text();
				designs[settings.activeDesign].addresses.push(address);
			});

			designs[settings.activeDesign].addressBookId = $('#addressBookId').val();

			if(elem.find('#RichText_addressing').length == 0) {
				var params = {};
				params['type'] = 'RichText';
				params['index'] = 'addressing';
				params['templateWidth'] = settings.templateWidth;
				params['templateHeight'] = settings.templateHeight;

				$.ajax({
					type: 'GET',
					url: '/' + websiteId + '/control/generateCustomFXGContent',
					data: params,
					dataType: 'xml',
					success: function(xml) {
						var addressBox = createRichText($(xml).find('RichText'), true, true, false);
						addressBox.text = designs[settings.activeDesign].addresses[designs[settings.activeDesign].activeAddress];
						designs[settings.activeDesign].activeAddress = active;
						addressBox.width = getOriginalSize(staticWidth * 0.90);
						addressBox.x = (getOriginalSize(staticWidth) - addressBox.width) / 2;
						addressBox.height = getOriginalSize(staticHeight * 0.40);
						addressBox.y = (getOriginalSize(staticHeight) - addressBox.height) / 1.4;
						addressBox.textAlign = 'center';
						addressBox.changed = true;
						if(elem.find('#RichText_addressing').length == 0) {
							designs[settings.activeDesign].elements.push(addressBox);
						}
						if(repaint) {
							addElement(addressBox, settings.activeDesign);
							paintEditor(settings.activeDesign);
						}
					}
				});
			} else {
				designs[settings.activeDesign].activeAddress = active;
				if(repaint) {
					paintEditor(settings.activeDesign);
					elem.find('#RichText_addressing').find('.addressTag').remove();
					elem.find('#RichText_addressing').append(getAddressTag(1))
				}
			}

			//save address book id
			if(repaint) {
				$('#addressing_' + elem.attr('data-id')).draggable('destroy').bPopup().close();
				GoogleAnalytics.trackEvent('Product Page', 'Scene7v2', 'Applied Address Book');
			}
		}

		var loadAddressBook = function(id, async) {
			$('#addressing_' + elem.attr('data-id')).find('.addressBookTable > tbody').empty();
			$('#addressing_' + elem.attr('data-id')).find('#addressBookId').val(id);

			$.ajax({
				url: '/envelopes/control/getAddresses',
				context: document.body,
				data: { id: id },
				async: async
			}).done(function(data) {
				$.each(data.data, function (index, row) {
					addDataRow(row);
				});
				$('#addressing_' + elem.attr('data-id')).find('#addressBookName').val(data.name);

				designs[settings.activeDesign].addressBookId = id;

				$('#addressing_' + elem.attr('data-id')).find('.dContentLoadingAddress').remove();
				$('#addressing_' + elem.attr('data-id')).find('.step1').hide();
				$('#addressing_' + elem.attr('data-id')).find('.step2').hide();
				$('#addressing_' + elem.attr('data-id')).find('.step3').show();

			});
			countAddresses();
		};

		var addDataRow = function(row){
			$('#addressing_' + elem.attr('data-id')).find('.addressBookTable > tbody').append($('<tr />').data('rowId', row['id'])
				.append($('<td />').attr('class', 'count').html($('#addressing_' + elem.attr('data-id')).find('.addressBookTable > tbody > tr').length+1))
				.append($('<td />').data('fieldid', 'name').data('value', row['name']).html(row['name']))
				.append($('<td />').data('fieldid', 'name2').data('value', row['name2']).html(row['name2']))
				.append($('<td />').data('fieldid', 'address1').data('value', row['address1']).html(row['address1']))
				.append($('<td />').data('fieldid', 'address2').data('value', row['address2']).html(row['address2']))
				.append($('<td />').data('fieldid', 'city').data('value', row['city']).html(row['city']))
				.append($('<td />').data('fieldid', 'state').data('value', row['state']).html(row['state']))
				.append($('<td />').data('fieldid', 'zip').data('value', row['zip']).html(row['zip']))
				.append($('<td />').data('fieldid', 'country').data('value', row['country']).html(row['country']))
				.append($('<td />').attr('class', 'noedit').append(
					$('<img src="/html/img/designer/icon_eye.png" title="Preview this address"/>').on("click", function(){
						applyAddresses($(this).closest('tr').index(), true);
					})
				).append(
					$('<img class="deleteRow" src="/html/img/icon/icon_trash.png" title="Remove this address"/>').on("click", function(){
						var self = this;
						prettyConfirm(
							'Are you sure?',
							'Do you want to remove this address?',
							{
								'Yes:active': function(){
									deleteRow($(self).closest('tr'));
								},
								'No': null
							}
						);
					})
				)).find('td').each(function(){
					if (!$(this).hasClass('noedit') && !$(this).hasClass('count')){
						makeCellEditable(this);
					}
				}).end()
			);
			countAddresses();
		};

		var reAdjustAddressRowNumbers = function() {
			$.each($('#addressing_' + elem.attr('data-id')).find('.addressBookTable > tbody > tr > td.count'), function(i) {
				$(this).html(i+1);
			});
		};

		function deleteRow(row){
			$.ajax({
				url: '/envelopes/control/removeAddress',
				dataType: 'json',
				type: 'POST',
				context: document.body,
				data: {
					id:row.data('rowId')
				}
			}).done(function() {
				row.remove();
				reAdjustAddressRowNumbers();
				countAddresses()
			});
		}

		function removeAddressBook(groupId){
			$.ajax({
				url: '/envelopes/control/removeAddressBook',
				dataType: 'json',
				type: 'POST',
				context: document.body,
				data: {
					id: groupId
				}
			}).done(function() {
				loadAddressBooks();
			});
		}

		function countAddresses(){
			var count = $('#addressing_' + elem.attr('data-id')).find('.addressBookTable tbody tr').length;

			if (count === 0){
				$('#addressBookCount').html(' (0 ADDRESSES)');
			} else {
				$('#addressBookCount').html(' (' + count + ' ADDRESSES)');
			}

			return count;
		}

		var makeCellEditable = function(cell){
			var cell = $(cell);
			cell.unbind().on('click', function() {
				if (!$(this).hasClass('noedit')){
					editCell(this);
				}
			});
		};

		var saveCell = function(event, field, cell) {
			var keyCode = event.keyCode || event.which;

			waitForFinalEvent(function() {
				event.preventDefault();
				var nextField = $(field).parent().next();
				$(field).parent().find('input.save').trigger('click', keyCode);
				if(keyCode == 9 || keyCode == 13) { //move on when tab or enter is pressed
					if(nextField.hasClass('noedit') && nextField.parent().next().length == 0){
						addDataRow({id:-1});
					}
					if(nextField.hasClass('noedit')){
						nextField.parent().next().find('td').first().next().trigger('click');
					} else {
						nextField.trigger('click');
					}
				}
			}, (keyCode == 9 || keyCode == 13) ? 0 : 500, 'saveCell_' + cell.parent().data('rowId'));
		};

		var editCell = function(cell) {
			// cancel all other cells
			var cell = $(cell);
			cell.closest('tbody').find('td > input.save').click();
			var value = cell.text();

			var inputField =
				$('<input type="text" class="no-margin" value="'+value+'">').on("click", function(e){
					return false;
				}).on('keydown', function(e) {
					saveCell(e, this, cell);
				}).css({width: cell.width() - 20});

			cell.unbind().html('').append(inputField,
				$('<input type="image" src="/html/img/designer/add_check.png" class="save">').on("click", function(e, keyCode) {
					var newValue = $(this).parent().find('input[type=text]').val();

					if(cell.data('value') == newValue) {
						cell.html(newValue);
						makeCellEditable(cell);
						return false;
					}

					if(typeof keyCode == 'undefined' || keyCode == 9 || keyCode == 13) {
						cell.html(newValue);
					}

					//if row-id doesnt exist make new
					if(cell.parent().data('rowId') == -1) {
						$.ajax({
							url: '/envelopes/control/addAddress',
							dataType: 'json',
							type: 'POST',
							data: {id: cell.closest('.step3').find('#addressBookId').val()},
							context: document.body,
							async: false,
							timeout: 5000
						}).done(function(data) {
							cell.parent().data('rowId', data.id);
						});
					}

					$.ajax({
					  url: '/envelopes/control/updateAddress',
					  dataType: 'json',
					  type: 'POST',
					  context: document.body,
					  data: {
						  id: cell.parent().data('rowId'),
						  field: cell.data('fieldid'),
						  value: newValue
						}
					}).done(function() {
						cell.data('value', newValue);
					});

					makeCellEditable(cell);
					return false;
				})
			);

			inputField.focus().val(inputField.val()).select();
		};

		var getAddressTag = function(el) {
			var dropDown = $('<div/>').attr('id', 'pageSelector').append($('<ol/>').addClass('content').addClass('no-drag'));

			for(var j=0; j< designs[settings.activeDesign].addresses.length; j++){
				dropDown.find('.content').append(
					$('<li/>').html(designs[settings.activeDesign].addresses[j].split('\n')[0]+'&nbsp;').on('click', function(){
						designs[settings.activeDesign].activeAddress = $(this).index();
						$(this).closest('#pageSelector').siblings('.selectedAddress').html(designs[settings.activeDesign].activeAddress+1);
						paintEditor(settings.activeDesign);
						return false;
					})
				);
			}

			dropDown.hide();
			return $('<div/>').addClass('addressTag').addClass('no-drag').on('click', function(){
				return false;
			}).append(
				dropDown
			).append(
				$('<span/>').html('&laquo;&nbsp;').addClass('cursorPointer').on('click', function(){
					if(designs[settings.activeDesign].activeAddress ==  0){
						return false;
					}
					designs[settings.activeDesign].activeAddress--;
					$(this).siblings('.selectedAddress').html(designs[settings.activeDesign].activeAddress+1);
					paintEditor(settings.activeDesign);
					return false;
				})
			).append(
				$('<span/>', {class:'selectedAddress'}).html(designs[settings.activeDesign].activeAddress+1).on('mouseenter', function(){
					dropDown.css({left:((0-dropDown.width())/2)+$(this).position().left+($(this).width()/2)}).show();
					$('#pageSelector > .content').animate({
						scrollTop: dropDown.find('li:nth-child('+(designs[settings.activeDesign].activeAddress+1)+')').position().top
					}, 0);
					return false;
				})
			).append(
				' of ' + designs[settings.activeDesign].addresses.length
			).append(
				$('<span/>').html('&nbsp;&raquo;').addClass('cursorPointer').on('click', function(){
					if(designs[settings.activeDesign].activeAddress ==  designs[settings.activeDesign].addresses.length - 1){
						return false;
					}
					designs[settings.activeDesign].activeAddress++;
					$(this).siblings('.selectedAddress').html(designs[settings.activeDesign].activeAddress+1);
					paintEditor(settings.activeDesign);
					return false;
				})
			)
		};

		var addPendingUpload = function(){
			elem.find('.noDContent').remove();
			elem.find('#imgLibUser>ul').prepend(
				$('<li>').addClass('dContent').addClass('dContentLoading').append(
					$('<img>').attr('src', '/html/img/designer/image_loading.gif')
				)
			);
		};

		var finishPendingUpload = function(url, id){
			var assetUrl = '';
			var attribUrl = '';
			var fullUrl = '';

			if (url.indexOf('fxg') !== -1){
				assetUrl = "http://actionenvelopew2p.scene7.com/is/agm/ActionEnvelope/fxgHolder?setAttr.IMAGEfxgimage={source=@Embed('" + url + "')%26s7:fit=fit}&fmt=png-alpha&wid=100&hei=100";
				attribUrl = "http://actionenvelopew2p.scene7.com/is/agm/ActionEnvelope/fxgHolder?setAttr.IMAGEfxgimage={source=@Embed('" + url + "')%26s7:fit=fit}";
				fullUrl = "http://actionenvelopew2p.scene7.com/is/image/ActionEnvelope?src=fxg{/ActionEnvelope/fxgHolder?setAttr.IMAGEfxgimage={source=@Embed('" + url + "')%26s7:fit=fit}}";
			} else {
				assetUrl = 'http://actionenvelopew2p.scene7.com/is/image/' + url + '?fmt=png-alpha&wid=100&hei=100';
				attribUrl = "http://actionenvelopew2p.scene7.com/is/image/" + url;
				fullUrl = attribUrl;
			}

			makeSelectable(elem.find('.dContentLoading').last().removeClass('dContentLoading')
				.attr('data-data-id', id).find('img')
				.attr('src', assetUrl)
				.attr('data-attriburl', attribUrl)
				.attr('data-fullurl', fullUrl));
		};

		var uploadFiles = function(files){
			if (elem.find('.dContentLoading').length !== 0){
				showWarningMessage('Please wait for existing upload to finish!');
				$(this).val('');
				return;
			}
			var formData = new FormData();

			for (var i = 0, file; file = files[i]; ++i) {
				if(!(/\.(ai|eps|bmp|gif|jpg|jpeg|tiff|tif|png)$/i).test(files[0].name)){
					showWarningMessage('Invalid File Type:  We only accept AI, EPS, BMP, JPEG, PNG, GIF &amp; TIFF');
					return;
				}
				formData.append('ugcUploadFile['+i+']', file);
				//add temp
				addPendingUpload();
			}

			var xhr = new XMLHttpRequest();
			xhr.open('POST', '/' + websiteId + '/control/uploadUGCFile', true);
			xhr.onload = function(e) {
				var jsonResponse = JSON.parse(xhr.responseText);
				if(jsonResponse.success) {
					$.each(jsonResponse.paths, function(k, v){
						finishPendingUpload(v.path, v.id);
					});
					if (jsonResponse.paths.length == 1){
						elem.find('#imgLibUser .dContent > img').first().trigger('dblclick');
					} else {
						elem.find('#imgLibUser .dContent > img').first().trigger('click');
					}
				} else {
					showWarningMessage('File failed to upload.  Please try again!')
					elem.find('.dContentLoading').remove();
				}
			};

			xhr.send(formData);
			GoogleAnalytics.trackEvent('Product Page', 'Scene7v2', 'Upload Image');
		};

		var uploadFilesAddress = function(files){
			if (elem.find('.dContentLoadingAddress').length !== 0){
				showWarningMessage('Please wait for existing upload to finish!');
				$(this).val('');
				return;
			}

			var formData = new FormData();

			elem.find('.step2').append(
				$('<div/>').addClass('dContentLoadingAddress').append(
					$('<img/>').attr('src', '/html/img/designer/image_loading.gif')
				)
			);
			for (var i = 0, file; file = files[i]; ++i) {
				if(!(/\.(csv)$/i).test(file.name)){
					$('.warning-message').html('Invalid File Type:  We only accept CSV');
					showWarningMessage('Invalid File Type:  We only accept CSV');
					elem.find('.step2 .dContentLoadingAddress').remove();
					$('#ugcUploadFile2' + '_' + elem.attr('data-id')).val('');
					return;
				}
				formData.append('ugcUploadFile['+i+']', file);
			}

			var xhr = new XMLHttpRequest();
			xhr.open('POST', '/' + websiteId + '/control/processAddressFile', true);
			xhr.onload = function(e) {
				var jsonResponse = JSON.parse(xhr.responseText);
				if (jsonResponse.status === 'OK'){
					loadAddressBooks();
					loadAddressBook(jsonResponse.id, true);
					$('#ugcUploadFile2' + '_' + elem.attr('data-id')).val('');
				} else {
					$('.warning-message').html(jsonResponse.status.replace(/\n/g, '<br/>'));
					showWarningMessage(jsonResponse.status.replace(/\n/g, '<br/>'));
					elem.find('.step2 .dContentLoadingAddress').remove();
					$('#ugcUploadFile2' + '_' + elem.attr('data-id')).val('');
				}
			};
			xhr.send(formData);
			GoogleAnalytics.trackEvent('Product Page', 'Scene7v2', 'Upload Address CSV');
		};

		var makeSelectable = function(el){
			el.unbind().on('click', function(){
				elem.find('.dContent.active').removeClass('active').find('.checkbox').remove();
				$(this).parent().addClass('active').append($('<div/>').addClass('checkbox'));
				elem.find('.useThisImage').show();
			}).on('dblclick', function(){
				$(this).trigger('click');
				elem.find('.useThisImage').trigger('click');
			}).parent().on('mouseenter', function(){
				var id = $(this).data('data-id');
				var self = this;
				if (id === undefined){
					return false;
				}
				$(this).append(
					$('<div/>').addClass('trash').on('click', function(){
						prettyConfirm(
							'Warning',
							'Are you sure you want to delete this image?',
							{
								'YES:active': function(){
									deleteImage(id, $(self));
								},
								'NO': null
							}
						);
						return false;
					})
				)
			}).on('mouseleave', function(){
				$(this).find('.trash').remove();
			});
		};

		var isElementVisible = function(name, design, loadUGC) {
			if(loadUGC) {
				return true;
			}
			design.lastElementId = name;
			if(design.templateType == 'BLANK' && name.indexOf('COLOR_bgcolor') == -1) {
				return false;
			} else if(name.indexOf('_logo_left') != -1) {
				return false;
			} else if(name.indexOf('COLOR_reply_border') != -1) {
				return false;
			} else if(name.indexOf('TEXT_reply_stamp_mark') != -1) {
				return false;
			} else if (name.indexOf('_logo_top') != -1) {
				return false;
			} else if(design.templateSide === 'FRONT' && design.templateType === 'REPLY' && name.indexOf('_return_') != -1) {
				return false;
			} else if(design.templateSide === 'FRONT' && design.templateType === 'RETURN' && name.indexOf('_reply_') != -1) {
				return false;
			} else {
				return true;
			}
		};

		var getColor = function(element) {
			if(typeof element.find('SolidColorStroke').first().attr('s7:colorName') !== 'undefined' && element.find('SolidColorStroke').first().attr('s7:colorName') == 'KEYLINE') {
				return 'KEYLINE';
			} else if(typeof element.find('SolidColor').first().attr('s7:colorName') !== 'undefined' && element.find('SolidColor').first().attr('s7:colorName') == 'KEYLINE') {
				return 'KEYLINE';
			} else if(typeof element.attr("color") !== 'undefined') {
				return makeFullHex(element.attr("color"));
			} else if(typeof element.attr("s7:colorName") !== 'undefined' && element.attr("s7:colorName") != '') {
				return settings.colorMap[element.attr("s7:colorName")];
			} else if(typeof element.attr("s7:colorValue") !== 'undefined' && element.attr("s7:colorValue") != '') {
				if(element.attr("s7:colorValue").length > 7) {
					return settings.colorMap[element.attr("s7:colorValue").substring(0,7)];
				} else {
					return settings.colorMap[element.attr("s7:colorValue")];
				}
			} else if(typeof element.find('SolidColorStroke').first().attr('color') !== 'undefined') {
				return makeFullHex(element.find('SolidColorStroke').first().attr('color'));
			} else if(typeof element.find('SolidColorStroke').first().attr('s7:colorName') !== 'undefined' && element.find('SolidColorStroke').first().attr('s7:colorName') != '') {
				return settings.colorMap[element.find('SolidColorStroke').first().attr('s7:colorName')];
			} else if(typeof element.find('SolidColorStroke').first().attr('s7:colorValue') !== 'undefined' && element.find('SolidColorStroke').first().attr('s7:colorValue') != '') {
				if(element.find('SolidColorStroke').first().attr('s7:colorValue').length > 7) {
					return makeFullHex(element.find('SolidColorStroke').first().attr('s7:colorValue').substring(0,7));
				} else {
					return makeFullHex(element.find('SolidColorStroke').first().attr('s7:colorValue'));
				}
			} else if(typeof element.find('SolidColor').first().attr('color') !== 'undefined') {
				return makeFullHex(element.find('SolidColor').first().attr('color'));
			} else if(typeof element.find('SolidColor').first().attr('s7:colorName') !== 'undefined' && element.find('SolidColor').first().attr('s7:colorName') != '') {
				return settings.colorMap[element.find('SolidColor').first().attr('s7:colorName')];
			} else if(typeof element.find('SolidColor').first().attr('s7:colorValue') !== 'undefined' && element.find('SolidColor').first().attr('s7:colorValue') != '') {
				if(element.find('SolidColor').first().attr('s7:colorValue').length > 7) {
					return makeFullHex(element.find('SolidColor').first().attr('s7:colorValue').substring(0,7));
				} else {
					return makeFullHex(element.find('SolidColor').first().attr('s7:colorValue'));
				}
			}
		};

		/* CREATE THE EDITABLE ELEMENT OBJECTS */
		var createImage = function(xmlObj, custom) {
			var scaleX = (typeof xmlObj.attr('scaleX') !== 'undefined') ? xmlObj.attr('scaleX') : 1;
			var scaleY = (typeof xmlObj.attr('scaleY') !== 'undefined') ? xmlObj.attr('scaleY') : 1;
			var width = (typeof xmlObj.attr('width') !== 'undefined') ? xmlObj.attr('width') : 1;
			var height = (typeof xmlObj.attr('height') !== 'undefined') ? xmlObj.attr('height') : 1;
			var url = xmlObj.attr('source').match(/.*@Embed\((?:.*\/.*\/(.*)\.[a-z]{3,4})|.*@Embed\('is\(ActionEnvelope\/(.*)\)'/);
			url = 'http://actionenvelope.scene7.com/is/image/ActionEnvelope/' + url[1]||url[2];

			var id = xmlObj.attr('s7:elementID');
			var imgDims = getImageAttribs(url);
			var maxWidth = imgDims.w;
			var maxHeight = imgDims.h;

			return {
				id: id,
				type: 'BitmapImage',
				scaleX: scaleX,
				scaleY: scaleY,
				parentX: ((typeof xmlObj.parent().attr('x') !== 'undefined') ? parseFloat(xmlObj.parent().attr('x')) : 0),
				parentY: ((typeof xmlObj.parent().attr('y') !== 'undefined') ? parseFloat(xmlObj.parent().attr('y')) : 0),
				x: ((typeof xmlObj.parent().attr('x') !== 'undefined') ? parseFloat(xmlObj.parent().attr('x')) : 0),
				y: ((typeof xmlObj.parent().attr('y') !== 'undefined') ? parseFloat(xmlObj.parent().attr('y')) : 0),
				width: width*scaleX,
				height: height*scaleY,
				originalWidth: width*scaleX,
				originalHeight: height*scaleY,
				rotation: ((typeof xmlObj.attr('rotation') !== 'undefined') ? parseFloat(xmlObj.attr('rotation')) : 0 ),
				url: url,
				innerImage: {
					width : width*scaleX,
					height : height*scaleY,
					aspectRatio :  maxHeight / maxWidth,
					maxWidth : maxWidth,
					maxHeight : maxHeight,
					brightness: 0,
					contrast: 0,
					saturation: 0,
					grow: 0,
					colorize: 0,
					rotation: 0,
					top: 0,
					left: 0,
					left: 0,
					leftPerc: 0,
					maxTop: 0,
					maxLeft: 0,
					url: '',
					original: true
				},
				tip: '<strong>This is an Image.</strong><br/><br/>'+
					 'You can:<br/>'+
					 '&nbsp;&nbsp;1. Click on it to change the image or crop.<br/>'+
					 '&nbsp;&nbsp;2. Drag it to change its location on the page.<br/>'+
					 '&nbsp;&nbsp;3. Grab the edge points to resize it.',
				custom: ((typeof custom !== 'undefined') ? custom : false ),
				justAdded: ((typeof custom !== 'undefined') ? custom : false ),
				changed: false,
				deleted: false
			}
		};

		var getImageAttribs = function(url) {
			var w = 0;
			var h = 0;
			$.ajax({
				url : '/' + websiteId + '/control/getImgInfo',
				data: {
					url: url,
					req: 'imageprops,xml'
				},
				dataType: 'json',
				async : false,
				success: function(data){
					w =  parseFloat(data.width);
					h = parseFloat(data.height);
				}
			});

			return {w:w, h:h};
		};

		var boundsInnerImage = function(top, left, width, height, parentWidth, parentHeight){
			if(top > 0) { top = 0; }
			if(left > 0) { left = 0; }
			if(top < (parentHeight - height)) { top = (parentHeight - height); }
			if(left < (parentWidth - width)) { left = (parentWidth - width); }
			return { top: top, left:left };
		};

		var paintInnerImage = function(el, animate){
			if (el.deleted){
				return;
			}
			animate = (typeof animate === 'undefined') ? true : false;

			var params = {};
			params["fmt"] = imageQuality.fmt;
			params["qlt"] = imageQuality.qlt;
			params['wid'] = Math.ceil(getNewSize(el.innerImage.width, true));
			params['rotate'] = el.innerImage.rotation;
			params['op_brightness'] = el.innerImage.brightness;
			params['op_contrast'] = el.innerImage.contrast;
			if (el.innerImage.colorize != 0) {
				params['op_colorize'] = el.innerImage.colorize;
			}
			params['op_grow'] = el.innerImage.grow;
			params['op_saturation'] = el.innerImage.saturation;
			params['fit'] = 'stretch,1';

			el.innerImage.url =  el.url + ((el.url.indexOf('fxgHolder') != -1) ? '&' : '?') + makeStringFromParam(params);

			elem.find('#'+el.id).find('.innerImage > img').attr('src', el.innerImage.url).css({'position': 'absolute'})
			.stop().animate({
				'width': getNewSize(el.innerImage.width, true),
				'height': getNewSize(el.innerImage.height, true),
				'top': getNewSize(el.innerImage.top),
				'left': getNewSize(el.innerImage.left),
			}, {
				duration: (animate) ? 500 : 0,
				complete: function() {
					var parent = elem.find('#' + el.id);
					var inner = elem.find('#' + el.id +'>.innerImage>img');

					el.innerImage.topPerc = Math.abs(inner.position().top / inner.height());
					el.innerImage.leftPerc = Math.abs(inner.position().left / inner.width());

					el.innerImage.maxTop = Math.abs((parent.height() -  inner.height()) / inner.height());
					el.innerImage.maxLeft = Math.abs((parent.width() -  inner.width()) / inner.width());
				}
			});
		};

		var zoomInnerImage = function(el, offset) {
			var newW = 0;
			var newH = 0;

			if(el.innerImage.rotation == 0 || el.innerImage.rotation == 180 ) {
				newW = el.innerImage.width + offset;
				newH = el.innerImage.height + (offset * el.innerImage.aspectRatio);
			} else {
				newW = el.innerImage.width + (offset * el.innerImage.aspectRatio);
				newH = el.innerImage.height + offset;
			}

			if(newW >= el.width && newH >= el.height) {
				el.innerImage.width = newW;
				el.innerImage.height = newH;
				var bounds = boundsInnerImage(	el.innerImage.top,
												el.innerImage.left,
												el.innerImage.width,
												el.innerImage.height,
												el.width,
												el.height );

				el.innerImage.top = bounds.top;
				el.innerImage.left = bounds.left;
				paintInnerImage(el);

			} else {
				showWarningMessage('Can\'t zoom '+(offset > 0 ? 'in' : 'out')+' image any further!');
				paintInnerImage(el);
			}
		};

		var rotateInnerImage = function(el, offset) {
			createHistory();
			el.changed = true;
			var temp = el.innerImage.width;
			el.innerImage.width = el.innerImage.height;
			el.innerImage.height = temp;
			el.innerImage.rotation += offset;
			el.innerImage.rotation = normalizeRotation( el.innerImage.rotation );

			fitToBox(el);

			paintInnerImage(el, false);
		};

		var fitToBox = function(el) {
			var resetTopLeft = function(el) {
				el.innerImage.top = 0;
				el.innerImage.left = 0;
			}
			var ratio = Math.max(el.width / el.innerImage.width, el.height / el.innerImage.height);
			el.innerImage.width = ratio * el.innerImage.width;
			el.innerImage.height = ratio * el.innerImage.height;
		};

		var brightnessInnerImage = function(el, offset) {
			if (el.innerImage.brightness + offset >= -100  && el.innerImage.brightness + offset <= 100 ){
				el.innerImage.brightness += offset;
				paintInnerImage(el);
			} else {
				showWarningMessage('Can\'t '+(offset > 0 ? 'increse' : 'decrease')+' image brightness any further!');
			}
		};

		var contrastInnerImage = function(el, offset) {
			if (el.innerImage.contrast + offset >= -100  && el.innerImage.contrast + offset <= 100 ){
				el.innerImage.contrast += offset;
				paintInnerImage(el);
			} else {
				showWarningMessage('Can\'t '+(offset > 0 ? 'increse' : 'decrease')+' image contrast any further!');
			}
		};

		var createPath = function(xmlObj, custom, loadUGC) {
			var id = xmlObj.attr('s7:elementID');
			var x = xmlObj.attr('x');
			var y = xmlObj.attr('y');
			var data = xmlObj.attr('data');
			var weight = 1;

			var pathData = [];

			$.each(xmlObj.children(), function(index) {
				var color = '#FFFFFF';
				var pathType = this.tagName;
				var miterLimit = 1;
				var joints = 'miter';
				var caps = 'none';

				if (pathType == 'stroke'){
					color = getColor($(this));
					if(typeof $($(this).children()[0]).attr('weight') !== 'undefined'){
						weight = parseFloat($($(this).children()[0]).attr('weight'));
					}
					if(typeof $($(this).children()[0]).attr('miterLimit') !== 'undefined'){
						miterLimit = $($(this).children()[0]).attr('miterLimit');
					}
					if(typeof $($(this).children()[0]).attr('caps') !== 'undefined'){
						caps = $($(this).children()[0]).attr('caps');
					}
					if(typeof $($(this).children()[0]).attr('joints') !== 'undefined'){
						joints = $($(this).children()[0]).attr('joints');
					}
				} else if(pathType == 'fill'){
					color = getColor($(this));
				}
				pathData.push({
					pathType: pathType,
					color: color,
					weight: weight,
					miterLimit: miterLimit,
					joints: joints,
					caps: caps
				});
			});

			var dimensions = getPathDimensions(data, weight);

			return {
				id: id,
				type: 'Path',
				x: x,
				y: y,
				width: dimensions.w,
				height: dimensions.h,
				data: data,
				pathData: pathData,
				custom: ((typeof custom !== 'undefined') ? custom : false),
				tip: 'Click to change color.' + ($('body').data('product').getProduct($('body').data('slider').getActiveSlideSetIndex()).type == 'STAMP' ? '' : '<br/>Drag to move.'),
				changed: false,
				deleted: false
			}
		};

		var createRect = function(xmlObj, custom, loadUGC){
			var id = xmlObj.attr('s7:elementID');
			var color = '#FFFFFF';
			var pathType = xmlObj.children()[0].tagName;
			var weight = 1;
			var miterLimit = 1;
			var joints = 'miter';
			var caps = 'none';
			var x = xmlObj.attr('x');
			var y = xmlObj.attr('y');
			var width = xmlObj.attr('width');
			var height = xmlObj.attr('height');

			if (pathType == 'stroke'){
				color = getColor(xmlObj);
				if (typeof $($(xmlObj.children()[0]).children()[0]).attr('weight') !== 'undefined'){
					weight = parseFloat( $($(xmlObj.children()[0]).children()[0]).attr('weight') );
				}
				if (typeof $($(xmlObj.children()[0]).children()[0]).attr('miterLimit') !== 'undefined'){
					miterLimit = $($(xmlObj.children()[0]).children()[0]).attr('miterLimit');
				}
				if (typeof $($(xmlObj.children()[0]).children()[0]).attr('caps') !== 'undefined'){
					caps = $($(xmlObj.children()[0]).children()[0]).attr('caps');
				}
				if (typeof $($(xmlObj.children()[0]).children()[0]).attr('joints') !== 'undefined'){
					joints = $($(xmlObj.children()[0]).children()[0]).attr('joints');
				}
			} else if (pathType == 'fill'){
				color = getColor(xmlObj);
			}

			return {
				id: id,
				type: 'Rect',
				x: x,
				y: y,
				width: width,
				height: height,
				color: color,
				pathType: pathType,
				weight: weight,
				miterLimit: miterLimit,
				caps: caps,
				joints: joints,
				custom: ((typeof custom !== 'undefined') ? custom : false),
				tip: 'Click to change color.' + ($('body').data('product').getProduct($('body').data('slider').getActiveSlideSetIndex()).type == 'STAMP' ? '' : '<br/>Drag to move.'),
				changed: false,
				deleted: false
			}
		};

		var buildEachLine = function(line, fontSize, textAlign, textDecoration, fontWeight, fontFamily, lineHeight, trackingRight, color) {
			var scene7ValidTextElement = "<p ";
			scene7ValidTextElement += " fontSize='" + fontSize + "'";
			scene7ValidTextElement += " s7:maxFontSize='" + fontSize + "'";
			scene7ValidTextElement += " textAlign='" + textAlign + "'";
			scene7ValidTextElement += " textDecoration='" + textDecoration + "'";
			scene7ValidTextElement += " fontFamily='" + fontFamily + "'";
			scene7ValidTextElement += " lineHeight='" + lineHeight + "'";
			trackingRight = "" + trackingRight + "%25";
			scene7ValidTextElement += " trackingRight='" + trackingRight + "'";
			scene7ValidTextElement += " color='" + color + "'";
			scene7ValidTextElement += ">";
			scene7ValidTextElement += "<span>" + line + "</span></p>";
			return scene7ValidTextElement;
		};

		var getPathDimensions = function(data, stroke) {
			var minX = Number.MAX_VALUE;
			var minY = Number.MAX_VALUE;
			var maxX = 0 - Number.MAX_VALUE;
			var maxY = 0 - Number.MAX_VALUE;
			var knowXY = [];
			var arr = data.match(/([A-Z]{1}(?:-*[0-9]+\.*[0-9]*e*-*[0-9]*(?:\s){0,1}){2,})Z*/g);

			$.each(arr, function () {
				var data = this + '';
				var type = data.charAt(0);
				data = data.substring(1, data.length - 1);
				data = data.split(' ');

				if (type === 'M' || type === 'L') {
					for (var i = 0; i < data.length; i = i + 2) {
						knowXY.push({
							x: data[i],
							y: data[i + 1]
						});
					}
				} else if (type === 'C') {
					for (var i = 0; i < data.length; i = i + 6) {
						knowXY.push({
							x: data[i + 4],
							y: data[i + 5]
						});
					}
				}
			});

			$.each(knowXY, function () {
				this.x = parseFloat(this.x);
				this.y = parseFloat(this.y);

				if (this.x > maxX) { maxX = this.x; }
				if (this.x < minX) { minX = this.x; }
				if (this.y > maxY) { maxY = this.y; }
				if (this.y < minY) { minY = this.y; }
			});

			return ({
				w: (maxX - minX + stroke),
				h: (maxY - minY + stroke),
				x: (minX),
				y: (minY)
			});
		};

		var createRichText = function(xmlObj, custom, changed, fitToSize){
			var id = xmlObj.attr('s7:elementID');
			var textValue;
			var fontWeight = ((typeof xmlObj.attr('fontWeight') !== 'undefined') ? xmlObj.attr('fontWeight') : '');
			var fontFamily = ((typeof xmlObj.attr('fontFamily') !== 'undefined') ? xmlObj.attr('fontFamily') : ($('body').data('product').getProduct($('body').data('slider').getActiveSlideSetIndex()).type == 'STAMP' ? 'Abby' : 'Minion Pro'))

			xmlObj.find('content>p>span').each(function() {
				textValue = (typeof textValue === 'undefined') ? $(this).text() : textValue + '<br />' + $(this).text();
			});

			//bold workarround
			if (fontWeight == 'bold'){
				if (boldFontExists(fontFamily)){
					fontFamily = fontFamily+ ' Bold';
				}
			}

			return {
				id: id,
				type: 'RichText',
				x: ((typeof xmlObj.attr('x') !== 'undefined') ? parseFloat(xmlObj.attr('x')) : 0),
				y: ((typeof xmlObj.attr('y') !== 'undefined') ? parseFloat(xmlObj.attr('y')) : 0),
				width: ((typeof xmlObj.attr('width') !== 'undefined') ? parseFloat(xmlObj.attr('width')) : 1),
				height: ((typeof xmlObj.attr('height') !== 'undefined') ? parseFloat(xmlObj.attr('height')) : 1),
				tracking: ((typeof xmlObj.attr('trackingRight') !== 'undefined') ? xmlObj.attr('trackingRight').replace('%', '') : 0),
				rotation: ((typeof xmlObj.attr('rotation') !== 'undefined') ? parseFloat(xmlObj.attr('rotation')) : 0 ),
				originalRotation: ((typeof xmlObj.attr('rotation') !== 'undefined') ? parseFloat(xmlObj.attr('rotation')) : 0 ),
				textAlign: ((typeof xmlObj.attr('textAlign') !== 'undefined') ? xmlObj.attr('textAlign') : 'left' ),
				lineHeight: ((typeof xmlObj.attr('lineHeight') !== 'undefined') ? parseInt(xmlObj.attr('lineHeight')) : 12 ),
				color: ((typeof getColor(xmlObj) !=='undefined') ? getColor(xmlObj) : '#000000'),
				fontFamily: fontFamily,
				fontSize: ((typeof xmlObj.attr('fontSize') !== 'undefined') ? parseInt(xmlObj.attr('fontSize')) : 12),
				fontWeight: fontWeight,
				textDecoration: ((typeof xmlObj.attr('textDecoration') !== 'undefined') ? xmlObj.attr('textDecoration') : ''),
				text: charEncodingForUser(replaceBreakLines(textValue)),
				tip:	'<strong>This is a Text Box.</strong><br/><br/>'+
						'You can:<br/>'+
						(
							$('body').data('product').getProduct($('body').data('slider').getActiveSlideSetIndex()).type == 'STAMP' ?
								'&nbsp;&nbsp;1. Click on it to change the color.'
							:
								'&nbsp;&nbsp;1. Click on it to change the font.<br/>'+
								'&nbsp;&nbsp;2. Drag it to change its location on the page.<br/>'+
								'&nbsp;&nbsp;3. Grab the edge points to resize it.'
						),
				custom: ((typeof custom !== 'undefined') ? custom : false ),
				justAdded: ((typeof custom !== 'undefined') ? custom : false ),
				changed: ((typeof changed !== 'undefined') ? changed : false ),
				fitToSize: ((typeof fitToSize !== 'undefined') ? fitToSize : false ),
				deleted: false
			}
		};

		var boldFontExists = function(name){
			var fonts = $('#fontPulldown img');
			for(var i = 0; i < fonts.length; i++) {
				if($(fonts[i]).attr('alt') == name + ' Bold'){
					return true;
				}
			}
			return false;
		};

		var replaceBreakLines = function (text) {
			if(typeof text !== 'undefined') {
				return text.replace(/(?:\\r\\n)|(?:<br\s*\/*>)/g, "\n");
			} else {
				return "";
			}
		};

		var charEncodingForScene7 = function(value) {
			return value.replace(/’/g, "'")
						.replace(/é/g, '&eacute;')
						.replace(/&/g, '&amp;')
						.replace(/</g, '&lt;')
						.replace(/>/g, '&gt;')
						.replace(/"/g, '&quot;')
						.replace(/'/g, '&apos;')
						.replace(/%/g, '&#37;')
						.replace(/\+/g, '&#43;');
		};

		var charEncodingForUser = function (value) {
			return value.replace("’", "'").replace("é", "e");
		};

		var showImageLib = function(el, designId){
			elem.find('.imgLibPop').bPopup({
				modal: false,
				escClose: false,
				transition: "fadeIn",
				speed: 100,
				positionStyle: 'fixed',
				appending: false
			}).draggable({
				handle: '.head',
			}).find('.useThisImage').hide().unbind('click').on('click', function(){
				el.justAdded = false;
				el.url = $(this).parent().find('.dContent.active>img').data('fullurl');
				var attribUrl = $(this).parent().find('.dContent.active>img').data('attriburl');
				el.changed = true;
				var imgDims = getImageAttribs(attribUrl);
				el.innerImage.maxWidth = imgDims.w;
				el.innerImage.maxHeight = imgDims.h;
				el.innerImage.aspectRatio =  el.innerImage.maxHeight / el.innerImage.maxWidth;
				el.innerImage.width = el.width;
				el.innerImage.height = el.innerImage.width * el.innerImage.aspectRatio

				if ( el.custom ) {
					el.height = el.innerImage.height;
				} else {
					fitToBox(el);
				}

				el.innerImage.original = false;
				el.originalWidth = el.width;
				el.originalHeight = el.height;

				paintInnerImage(el);
				addElement(el, settings.activeDesign);
				paintEditor(designId);
				$(this).closest('.imgLibPop').bPopup().close();
			}).end().find('.dContent.active').removeClass('active').find('.checkbox').remove();

			elem.find('.imgLibPop').find('.close').unbind('click').on('click', function(){
				if (el.justAdded){
					designs[settings.activeDesign].elements.splice(designs[settings.activeDesign].elements.length-1);
				}
				$(this).closest('.imgLibPop').draggable('destroy').bPopup().close();
			});
		};

		var showAddressing = function(designId){
			var alreadyHas = false;
			$.each(designs, function(k,v){
				if (k != settings.activeDesign && v.addressBookId != null){
					alreadyHas = true;
				}
			});
			if(alreadyHas){
				prettyConfirm(
					"Sorry",
					"Addressing can only be applied to one side.",
					{
						'OK:active' : null
					}
				);
				return;
			}

			$('#addressing_' + elem.attr('data-id')).bPopup({
				modal: false,
				escClose: false,
				transition: "fadeIn",
				speed: 100,
				positionStyle: 'fixed',
				appending: true
			}).draggable({
				handle: '.head',
			}).find('.close').unbind('click').on('click', function(){
				$(this).closest('.addressing').draggable('destroy').bPopup().close();
				var alreadyHasHere = false;
				$.each(designs, function(k,v){
					if (k == settings.activeDesign && v.addressBookId != null){
						alreadyHasHere = true;
					}
				});
				if (!alreadyHasHere){
					$(this).closest('.content').find('.step2, .step3').hide();
					$(this).closest('.content').find('.step1').show();
				}
			});

			if (designs[settings.activeDesign].addressBookId == null){
				$('#addressing_' + elem.attr('data-id')).find('.step2, .step3').hide();
				$('#addressing_' + elem.attr('data-id')).find('.step1').show();
			}
		};

		var generateUrlFromParams = function(params) {
			var url = params['url'];
			delete params['url'];
			return url + "?" + makeStringFromParam(params);
		};

		var resizeTextEditorToFitLength = function(el, longestLine, numberOfLines) {
			var textBoxWidth = longestLine * (el.fontSize*0.9);
			var textBoxHeight = 0;
			if(el.fontSize > (el.lineHeight*2)) {
				textBoxHeight = el.fontSize * numberOfLines;
			} else {
				textBoxHeight = (el.lineHeight*2) * numberOfLines;
			}

			el.width = textBoxWidth;
			el.height = textBoxHeight;
		};

		var makeStringFromParam = function(params) {
			var paramsAsString = "";
			var baseUrl = "";
			$.each(params, function(key, value) {
				if(paramsAsString.length > 0) {
					paramsAsString += "&";
				}
				if(key === "url") {
					baseUrl = value + "?";
				} else if(value instanceof Array) {
					$.each(value, function(i, val) {
						if(i !== 0) {
							paramsAsString += "&";
						}
						paramsAsString += key + "=" + val;
					});
				} else {
					paramsAsString += key + "=" + value;
				}
			});
			return baseUrl + paramsAsString;
		};

		var getObjParentOffsetByName = function(id, design) {
			for(var i = 0; i < design.elements.length; i++) {
				if(design.elements[i].id == id) {
					return { x:design.elements[i].parentX, y:design.elements[i].parentY };
				}
			}

			for(var i = 0; i < design.hiddenElements.length; i++) {
				if(design.hiddenElements[i].id == id && typeof design.hiddenElements[i].parentX != 'undefined') {
					return { x:design.hiddenElements[i].parentX, y:design.hiddenElements[i].parentY };
				}
			}
			return { x:0, y:0 };
		};

		var showWarningMessage = function(messagebody) {
			var popClose = function(el) {
				el.slideUp("slow", function(){$(this).remove();});
			};

			var warning = $("<div />")
				.append($("<img/>").attr("src","/html/img/designer/close-warning.png")
									.on("click", function(){
										popClose($(this).parent());
									}))
				.append($("<span/>").html(messagebody)).addClass("warningMessage");

			elem.find("#onlineDesigner").append(warning);

			warning.slideDown("slow");
			window.setTimeout(function(){
				popClose(warning);
			}, 10000);
		};

		var processPricing = function(showMessage) {
			var tempLastNumColors = $.extend({}, lastNumColors);
			settings.product.addresses = 0;
			$.each(designs, function(k,v){
				chooseCorrectInkColor(calculateNumberOfColors((k == settings.activeDesign), v), v.templateSide);
				if(designs[k].addresses.length > 0) {
					settings.product.addresses = designs[k].addresses.length;
				}
			});

			var same = true;
			$.each(lastNumColors, function(key, value) {
				if(tempLastNumColors[key] !== value) {
					same = false;
				}
			});

			if(typeof $('body').data('product') !== 'undefined') {
				$('body').data('product').updatePrice(parseInt(elem.attr('data-id')));
			}
		};

		var chooseCorrectInkColor = function(colors, side) {
			if(settings.product != null) {
				if(side == 'FRONT') {
					settings.product.colorsFront = colors['colorCount'];
					settings.product.whiteInkFront = colors['hasWhiteInk'];
				} else if(side == 'BACK') {
					settings.product.colorsBack = colors['colorCount'];
					settings.product.whiteInkBack = colors['hasWhiteInk'];
				}
			}
		};

		var calculateNumberOfColors = function(showMessage, side) {
			var colors = {};
			var color = undefined;
			var colorCount = 0;
			var hasWhiteInk = false;
			var spots = "";

			$.each(side.elements, function(i) {
				if(!((typeof this.deleted !== 'undefined') ? this.deleted : false) && this.id != "COLOR_bgcolor") {
					color = (typeof this.color != 'undefined') ? this.color : '#000000';
					if (color == 'KEYLINE') {
						return true;
					}
					if(typeof this.type !== 'undefined' && this.type === 'BitmapImage') {
						if(typeof this.innerImage !== 'undefined' && typeof this.innerImage.saturation !== 'undefined' && this.innerImage.saturation === -100) {
							colors['#000000'] = (typeof colors["#000000"] === 'undefined')? 1 : colors['#000000'] + 1;
						} else {
							colorCount = 4;
						}
					} else {
						if(color.replace('#', '').toLowerCase() === 'ffffff' && settings.backgroundColor.toLowerCase().replace('#','') !== 'ffffff') {
							hasWhiteInk = true;
						}
						if(color.replace('#', '').toLowerCase() != settings.backgroundColor.toLowerCase().replace('#','')) {
							colors[color] = (typeof colors[color] === 'undefined') ? 1 : colors[color] + 1;
						}
					}
				}
			});

			if(side.templateType !== "BLANK") {
				$.each(side.unEditableColors, function(index, value) {
					color = (typeof value != 'undefined') ? value : '#000000';
					if (color == 'KEYLINE') {
						return true;
					}
					if(color.replace("#","").toLowerCase() === 'ffffff' && settings.backgroundColor.toLowerCase().replace('#','') !== 'ffffff') {
						hasWhiteInk = true;
					}
					if(color.replace('#', '').toLowerCase() != settings.backgroundColor.toLowerCase().replace('#', '')) {
						colors[color] = (typeof colors[color] === 'undefined') ? 1 : colors[color] + 1;
					}
				});
			}

			colorCount += Object.keys(colors).length;

			var i = 0;
			$.each(colors, function(colorVal, count) {
				if(i > 0) { spots += ","; }
				if(typeof allSpotColors !== 'undefined' && colorVal.toLowerCase().replace("#","") in allSpotColors) {
					spots += allSpotColors[colorVal.toLowerCase().replace('#', '')];
				}
				i++;
			});

			if(spots.length > 0) {
				settings.spotColors = spots;
			}

			//set the predefined colors
			if(colorCount > 2) {
				colorCount = 4;
			} else if(colorCount > 1) {
				colorCount = 2;
			}

			//now lets update the colors to the maxColor
			if(settings.product != null && typeof settings.product.maxColors !== 'undefined' && settings.product.maxColors != null) {
				if(colorCount > settings.product.maxColors) {
					colorCount = settings.product.maxColors;
				} else if(colorCount != 0 && colorCount < settings.product.minColors) {
					colorCount = settings.product.minColors;
				}
				if(lastNumColors["FRONT"] > settings.product.maxColors) { lastNumColors['FRONT'] = settings.product.maxColors; }
				if(lastNumColors["BACK"] > settings.product.maxColors) { lastNumColors['BACK'] = settings.product.maxColors; }
			}

			lastNumColors[side.templateSide] = colorCount;
			lastNumColors['WHITE_' + side.templateSide] = hasWhiteInk;

			return ({colorCount: colorCount, hasWhiteInk: hasWhiteInk});
		};

		var getSpotColors = function() {
			$.ajax({
				type: 'GET',
				url: '/' + websiteId + '/control/getSpotColors',
				dataType: 'json',
				success: function(data) {
					if(typeof data.spotColors !== 'undefined' && data.spotColors.length > 0) {
						$.each(data.spotColors, function(index, value) {
							if(typeof value !== 'undefined' && typeof value.hex !== 'undefined' && typeof value.pms !== 'undefined') {
								allSpotColors[value.hex] = value.pms;
							}
						});
					}
				}
			});
		};

		//we allow the user to zoom as long as the width supports it,
		var canZoomIn = function(step, restricted) {
			if(restricted) {
				return (staticWidth + step) < (elem.find('#onlineDesigner').width()-300)
					&& staticHeight + (step * (settings.templateWidth/settings.templateHeight)) < (elem.find('#onlineDesigner').height()-50);
			}

			return (staticWidth + step) < ($(window).width()-300);
		};

		var canZoomOut = function(step){
			return (staticWidth + step > 100) && (staticHeight + step * (settings.templateWidth/settings.templateHeight) > 100);
		};

		var idealZoom = function() {
			var width = elem.width();
			var height = elem.height();
			var minimum = 530;

			if(height < minimum) { height = minimum; }
			if(width < minimum) { width = minimum; }

			elem.css({
				'width': width + 'px',
				'height':  height + 'px',
			});

			var step = 5;

			//reset the editor size and scale up
			staticWidth = (elem.css('display') == 'none') ? minimum : 100;
			staticHeight = staticWidth * (settings.templateHeight/settings.templateWidth);
			while(canZoomIn(step, true)) {
				staticWidth = staticWidth + step;
				staticHeight = staticWidth * (settings.templateHeight/settings.templateWidth);
			}

			processODResize(false);
		};

		var processODResize = function(resizeContainer) {
			if(resizeContainer) {
				elem.css({
					'height':  (staticHeight+200) + 'px',
				});
				elem.parent().css({
					'height':  (staticHeight+200) + 'px',
				});
			};

			initEditor();
			addEditorElements(settings.activeDesign);
			setBleedMargins();
			limitProductActions();
			paintEditor(settings.activeDesign);

			elem.find('#templateControlContainer, #templateController').css({
				'width':getNewSize(settings.templateWidth, false),
				'height':getNewSize(settings.templateHeight, false)
			});

			//resize decorators
			elem.find('#foldedShadow').css({
				'left':getNewSize(settings.templateWidth, false)-1,
				'border-top-width':(getNewSize(settings.templateHeight, false)*0.95)-3,
				'border-left-color':shadeColor(settings.backgroundColor, -20)
			})
			elem.find('#foldedShadowBorder').css({
				'left':getNewSize(settings.templateWidth, false)+2,
				'border-top-width':(getNewSize(settings.templateHeight, false)*0.95)
			});

			elem.find('#verticalFoldedShadow').css({
				'left':0,
				'border-right-width':(getNewSize(settings.templateWidth, false)*0.95)-3,
				'border-right-color':shadeColor(settings.backgroundColor, -20)
			})
			elem.find('#verticalFoldedShadowBorder').css({
				'left':0,
				'border-right-width':(getNewSize(settings.templateWidth, false)*0.95),
				'border-right-color':'#cccccc'
			});
		};

		var normalizeRotation = function(deg) {
			if(deg < 0) { deg += 360; }
			return deg % 360;
		};

		var limitProductActions = function() {
			var productType = $('body').data('product').getProduct($('body').data('slider').getActiveSlideSetIndex()).type;

			if (limitationsByProduct[productType] != undefined) {
				for (var i = 0; i <= limitationsByProduct[productType].length; i++) {
					$(($('#' + limitationsByProduct[productType][i]).length > 0 ? '#' : '.') + limitationsByProduct[productType][i]).hide();
				}
			}
		};

		window.setTimeout(function() {
			init();
		}, 1);

		$(window).resize(function (e) {
			if(e.target === this) {
				waitForFinalEvent(function() {
					elem.css('width', 'auto');
					idealZoom();
				}, 1000, 'resizeDesigner');
			}
		});
	};

	$.fn.OnlineDesigner = function(options) {
		return this.each(function() {
			var element = $(this);
			// Return early if this element already has a plugin instance
			if(element.data('online-designer')) {
				element.data('online-designer').show(options.activeDesign, options.discreet);
				element.data('online-designer').idealZoom();
				return;
			}

			// pass options to plugin constructor
			var myplugin = new OnlineDesigner(this, options);
			// Store plugin object in this element's data
			element.data('online-designer', myplugin);
		});
	};
})(jQuery);