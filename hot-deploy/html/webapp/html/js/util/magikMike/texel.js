class Texel {
    constructor(designerContainer, designData, isTemporary) {
        var texelObject = this;
        texelObject._attributes = {};
        texelObject.updateEvents = {};
        texelObject.designerContainer = '';
        texelObject.loaded = {
            general: false,
            images: false
        };

        if ($('#' + designerContainer).length > 0) {
            $('#' + designerContainer).remove();
        }

        let texelHTML = '' +
            '<div id="' + designerContainer + '" class="designerHidden designerContainer">' +
                '<div class="texelTemplateHelp jqs-texelTemplateHelp" style="background-color: rgb(248, 249, 251); margin: auto; margin-top: 10px; margin-bottom: 10px; text-align: left; max-width: 270px; width: 100%;">' +
                    '<div bns-help_button="show" class="row texelHelpHeader jqs-texelHelpHeader" style="color: rgb(0, 164, 228); cursor: pointer; padding: 5px 15px;">' +
                        '<div style="font-size: 14px; float: left;">Online Designer Help:</div>' +
                            '<div bns-help_text style="font-size: 14px; float: right;">Show Help <i class="fa fa-caret-down"></i>' +
                        '</div>' +
                    '</div>' +
                    '<div class="jqs-texelHelpContainer row hidden" style="padding: 0px 15px;">' +
                        '<div style="float: left; width: 50%; padding: 5px;">' +
                            '<span style="color: rgb(0, 0, 0); font-size: 16px; font-weight: bold;">Add Your Customization</span>' +
                            '<ol style="margin-bottom: 0px;">' +
                                '<li>To begin editing an existing text box or image, just click the box and the necessary tools will appear.</li>' +
                                '<li>To delete an existing text box or image, just click the item you\'d like to delete. Once the toolbox appears, click "remove" in the lower right-hand corner of the toolbar.</li>' +
                                '<li>Using the toolbar on the left-hand side you can: Add Text, Add An Image &amp; Under/Redo changes that you\'ve made or reset back to the original template.</li>' +
                            '</ol>' +
                        '</div>' +
                        '<div style="float: right; width: 50%; padding: 5px;">' +
                            '<div style="color: rgb(0, 0, 0); font-size: 16px; font-weight: bold;">Need Recipient Addressing?</div>' +
                            '<ol style="margin-bottom: 0px;">' +
                                '<li>Save the customization you add to this template and close the designer.</li>' +
                                '<li>On the product page, select "recipient addressing". This can be done before or after you customize a template, or as a stand-alone service.</li>' +
                            '</ol>' +
                        '</div>' +
                    '</div>' +
                '</div>' +
                '<canvas id="' + designerContainer + '_texel"></canvas>' +
            '</div>';
            
        $('body').append(texelHTML);

        texelObject.designerContainer = $('#' + designerContainer);
        texelObject.canvas = new fabric.Canvas(designerContainer + '_texel');
        texelObject.canvas.preserveObjectStacking = true;
        
        texelObject.canvas.off('mouse:down').on('mouse:down', function(e) {
            texelObject._loadObjectTools(e.target);
        }).off('object:modified').on('object:modified', function(e) {
            texelObject.saveState();
            texelObject._setObjectToolsStyling(e.target);
        }).off('object:moving').on('object:moving', function(e) {
            texelObject._positionCanvasTools(e.target);
            texelObject._setObjectToolsStyling(e.target);
        }).off('object:cleared').on('selection:cleared', function(e) {
            texelObject.removeObjectTools();
        });

        texelObject.defaultImage = '';

        texelObject.inkColors = [
            {'name':'Midnight Black',  'hex':'#000000', 'knockout':'#FFFFFF'},
            {'name':'Deep Purple',     'hex':'#2E1B46', 'knockout':'#FFFFFF'},
            {'name':'Navy',            'hex':'#002856', 'knockout':'#FFFFFF'},
            {'name':'Pool',            'hex':'#0082ca', 'knockout':'#FFFFFF'},
            {'name':'Baby Blue',       'hex':'#bad2dd', 'knockout':'#000000'},
            {'name':'Seafoam',         'hex':'#98d2dd', 'knockout':'#000000'},
            {'name':'Teal',            'hex':'#008996', 'knockout':'#FFFFFF'},
            {'name':'Racing Green',    'hex':'#005740', 'knockout':'#FFFFFF'},
            {'name':'Bright Green',    'hex':'#009946', 'knockout':'#FFFFFF'},
            {'name':'Limelight',       'hex':'#a1d784', 'knockout':'#000000'},
            {'name':'Wasabi',          'hex':'#dbe444', 'knockout':'#000000'},
            {'name':'Citrus',          'hex':'#f1ed74', 'knockout':'#000000'},
            {'name':'Lemonade',        'hex':'#ede59b', 'knockout':'#000000'},
            {'name':'Nude',            'hex':'#f3dbb3', 'knockout':'#000000'},
            {'name':'Silver',          'hex':'#c0c0c0', 'knockout':'#000000'},
            {'name':'White',           'hex':'#ffffff', 'knockout':'#000000'},
            {'name':'Sunflower',       'hex':'#fddd3f', 'knockout':'#000000'},
            {'name':'Gold',            'hex':'#937851', 'knockout':'#000000'},
            {'name':'Pencil',          'hex':'#f8bf00', 'knockout':'#000000'},
            {'name':'Mandarin',        'hex':'#ff9016', 'knockout':'#FFFFFF'},
            {'name':'Grocery Bag',     'hex':'#caaa7b', 'knockout':'#000000'},
            {'name':'Ochre',           'hex':'#d57f00', 'knockout':'#000000'},
            {'name':'Rust',            'hex':'#bc4800', 'knockout':'#FFFFFF'},
            {'name':'Terracotta',      'hex':'#993921', 'knockout':'#FFFFFF'},
            {'name':'Tangerine',       'hex':'#e1261c', 'knockout':'#FFFFFF'},
            {'name':'Ruby Red',        'hex':'#c20430', 'knockout':'#FFFFFF'},
            {'name':'Garnet',          'hex':'#a32136', 'knockout':'#FFFFFF'},
            {'name':'Wine',            'hex':'#8c3d45', 'knockout':'#FFFFFF'},
            {'name':'Vintage Plum',    'hex':'#84286b', 'knockout':'#FFFFFF'},
            {'name':'Magenta',         'hex':'#cb007c', 'knockout':'#FFFFFF'},
            {'name':'Candy Pink',      'hex':'#f7dbe0', 'knockout':'#000000'},
            {'name':'Wisteria',        'hex':'#7474c1', 'knockout':'#FFFFFF'},
            {'name':'Lilac',           'hex':'#b5b4e0', 'knockout':'#000000'},
            {'name':'Pastel Gray',     'hex':'#cacac8', 'knockout':'#000000'},
            {'name':'Slate',           'hex':'#8d9b9b', 'knockout':'#000000'},
            {'name':'Smoke',           'hex':'#6c6864', 'knockout':'#FFFFFF'},
            {'name':'Tobacco',         'hex':'#88674d', 'knockout':'#FFFFFF'},
            {'name':'Chocolate',       'hex':'#6f5d51', 'knockout':'#FFFFFF'},
            {'name':'Moss',            'hex':'#6b5a24', 'knockout':'#FFFFFF'},
            {'name':'Avocado',         'hex':'#90993F', 'knockout':'#000000'}
        ];

        texelObject.fonts = {
            "AddCityboy Normal": {
                "style":		"font-family:'AddCityboy Normal';",
                "jsPDFFile":    "addcityboy-normal.js",
                "jsPDFName":    "AddCityboyNormal",
                "jsPDFStyle":   "normal"
            },
			"Adobe Fangsong Std": {
                "style":		"font-family: 'Adobe Fangsong Std';",
				"jsPDFFile":	"adobeFangsongStdR-normal.js",
				"jsPDFName":	"AdobeFangsongStd-Regular-Alphabetic",
				"jsPDFStyle":	"normal"
			},
			"Allura": {
                "style":		"font-family: 'Allura';",
				"jsPDFFile":	"allura.regular-normal.js",
				"jsPDFName":	"Allura-Regular",
				"jsPDFStyle":	"normal"
            },
			"Alte Haas Grotesk": {
                "style":		"font-family: 'Alte Haas Grotesk';",
				"jsPDFFile":	"AlteHaasGroteskRegular-normal.js",
				"jsPDFName":	"AlteHaasGrotesk",
				"jsPDFStyle":	"normal"
			},
			"Ambient Medium": {
                "style":		"font-family: 'Ambient Medium';",
				"jsPDFFile":	"AMBIENT-normal.js",
				"jsPDFName":	"Ambient",
				"jsPDFStyle":	"normal"
            },
			"Angelina": {
                "style":		"font-family: 'Angelina';",
				"jsPDFFile":	"angelina-normal.js",
				"jsPDFName":	"Angelina",
				"jsPDFStyle":	"normal"
            },
			"Arnprior-Regular": {
                "style":		"font-family: 'Arnprior-Regular';",
				"jsPDFFile":	"ARNPRIOR-normal.js",
				"jsPDFName":	"Arnprior-Regular",
				"jsPDFStyle":	"normal"
			},
			"BabelSans Regular": {
                "style":		"font-family: 'BabelSans Regular';",
				"jsPDFFile":	"BabelSans-normal.js",
				"jsPDFName":	"BabelSans",
				"jsPDFStyle":	"normal"
			},
			"BabelSans Medium Italic": {
                "style":		"font-family: 'BabelSans Medium Italic';",
				"jsPDFFile":	"BabelSans-Oblique-italic.js",
				"jsPDFName":	"BabelSans-Oblique",
				"jsPDFStyle":	"italic"
			},
			"BakerSignet BT": {
                "style":		"font-family: 'BakerSignet BT';",
				"jsPDFFile":	"bakerSignet-normal.js",
				"jsPDFName":	"BakerSignetBT-Roman",
				"jsPDFStyle":	"normal"
			},
			"Baloney": {
                "style":		"font-family: 'Baloney';",
				"jsPDFFile":	"BALONEY_-normal.js",
				"jsPDFName":	"Baloney",
				"jsPDFStyle":	"normal"
			},
			"Batang": {
                "style":		"font-family: 'Batang';",
				"jsPDFFile":	"Batang-normal.js",
				"jsPDFName":	"Batang",
				"jsPDFStyle":	"normal"
			},
			"Batik Regular": {
                "style":		"font-family: 'Batik Regular';",
				"jsPDFFile":	"batik_regular-normal.js",
				"jsPDFName":	"BatikRegular",
				"jsPDFStyle":	"normal"
			},
			"Bauderie Script SSi": {
                "style":		"font-family: 'Bauderie Script SSi';",
				"jsPDFFile":	"bauderieScript-normal.js",
				"jsPDFName":	"BauderieScriptSSi",
				"jsPDFStyle":	"normal"
			},
			"Bauhaus 93": {
                "style":		"font-family: 'Bauhaus 93';",
				"jsPDFFile":	"bauhaus93-normal.js",
				"jsPDFName":	"Bauhaus93",
				"jsPDFStyle":	"normal"
            },
			"Bauhaus Light": {
                "style":		"font-family: 'Bauhaus Light';",
				"jsPDFFile":	"bauhausLightRegular-normal.js",
				"jsPDFName":	"BauhausLight",
				"jsPDFStyle":	"normal"
            },
			"BlackChancery": {
                "style":		"font-family: 'BlackChancery';",
				"jsPDFFile":	"blackChancery-normal.js",
				"jsPDFName":	"BlackChancery",
				"jsPDFStyle":	"normal"
			},
			"Brandon Grotesque Black": {
                "style":		"font-family: 'Brandon Grotesque Black';",
				"jsPDFFile":	"brandongrotesque-black-normal.js",
				"jsPDFName":	"BrandonGrotesque-Black",
				"jsPDFStyle":	"normal"
            },
			"Brush Script Std": {
                "style":		"font-family: 'Brush Script Std';",
				"jsPDFFile":	"brushScript-normal.js",
				"jsPDFName":	"BrushScriptStd",
				"jsPDFStyle":	"normal"
			},
			"Cabletv": {
                "style":		"font-family: 'Cabletv';",
				"jsPDFFile":	"Cabletv-normal.js",
				"jsPDFName":	"Cabletv",
				"jsPDFStyle":	"normal"
			},
			"Calibri": {
                "style":		"font-family: 'Calibri';",
				"jsPDFFile":	"CALIBRI-normal.js",
				"jsPDFName":	"Calibri",
				"jsPDFStyle":	"normal"
			},
			"Calibri Bold": {
                "style":		"font-family: 'Calibri Bold';",
				"jsPDFFile":	"calibriBold-bold.js",
				"jsPDFName":	"Calibri-Bold",
				"jsPDFStyle":	"bold"
            },
			"Calibri Italic": {
                "style":		"font-family: 'Calibri Italic';",
				"jsPDFFile":	"calibriItalic-italic.js",
				"jsPDFName":	"Calibri-Italic",
				"jsPDFStyle":	"italic"
			},
			"Calligrapher": {
                "style":		"font-family: 'Calligrapher';",
				"jsPDFFile":	"calligrapher-normal.js",
				"jsPDFName":	"CalligrapherRegular",
				"jsPDFStyle":	"normal"
			},
			"Cambria": {
                "style":		"font-family: 'Cambria';",
				"jsPDFFile":	"Cambria-normal.js",
				"jsPDFName":	"Cambria",
				"jsPDFStyle":	"normal"
			},
			"Cambria Bold": {
                "style":		"font-family: 'Cambria Bold';",
				"jsPDFFile":	"cambriaBold-bold.js",
				"jsPDFName":	"Cambria-Bold",
				"jsPDFStyle":	"bold"
			},
			"Cambria Italic": {
                "style":		"font-family: 'Cambria Italic';",
				"jsPDFFile":	"cambriaItalic-italic.js",
				"jsPDFName":	"Cambria-Italic",
				"jsPDFStyle":	"italic"
			},
			"Candara": {
                "style":		"font-family: 'Candara';",
				"jsPDFFile":	"CANDARA-normal.js",
				"jsPDFName":	"Candara",
				"jsPDFStyle":	"normal"
            },
			"Cezanne": {
                "style":		"font-family: 'Cezanne';",
				"jsPDFFile":	"cezanne-normal.js",
				"jsPDFName":	"Cezanne",
				"jsPDFStyle":	"normal"
            },
			"Chalkboard": {
                "style":		"font-family: 'Chalkboard';",
				"jsPDFFile":	"Chalkboard-normal.js",
				"jsPDFName":	"Chalkboard",
				"jsPDFStyle":	"normal"
			},
			"Chalkboard Bold": {
                "style":		"font-family: 'Chalkboard Bold';",
				"jsPDFFile":	"ChalkboardBold-normal.js",
				"jsPDFName":	"Chalkboard-Bold",
				"jsPDFStyle":	"bold"
			},
			"Chaucer": {
                "style":		"font-family: 'Chaucer';",
				"jsPDFFile":	"CHAUCER-normal.js",
				"jsPDFName":	"ChaucerRegular",
				"jsPDFStyle":	"normal"
			},
			"Chisel Mark Regular": {
                "style":		"font-family: 'Chisel Mark Regular';",
				"jsPDFFile":	"chisel-normal.js",
				"jsPDFName":	"ChiselMark",
				"jsPDFStyle":	"normal"
			},
			"Chopin Script": {
                "style":		"font-family: 'Chopin Script';",
				"jsPDFFile":	"chopinScript-normal.js",
				"jsPDFName":	"ChopinScript",
				"jsPDFStyle":	"normal"
            },
			"Comic Sans MS": {
                "style":		"font-family: 'Comic Sans MS';",
				"jsPDFFile":	"comicSansMS-normal.js",
				"jsPDFName":	"ComicSansMS",
				"jsPDFStyle":	"normal"
			},
			"Consolas": {
                "style":		"font-family: 'Consolas';",
				"jsPDFFile":	"Consolas-normal.js",
				"jsPDFName":	"Consolas",
				"jsPDFStyle":	"normal"
			},
			"Constantia": {
                "style":		"font-family: 'Constantia';",
				"jsPDFFile":	"Constantia-normal.js",
				"jsPDFName":	"Constantia",
				"jsPDFStyle":	"normal"
			},
			"Copperplate": {
                "style":		"font-family: 'Copperplate';",
				"jsPDFFile":	"copperplate-normal.js",
				"jsPDFName":	"Copperplate",
				"jsPDFStyle":	"normal"
			},
			"Corbel": {
                "style":		"font-family: 'Corbel';",
				"jsPDFFile":	"Corbel-normal.js",
				"jsPDFName":	"Corbel",
				"jsPDFStyle":	"normal"
			},
			"Cornerstone": {
                "style":		"font-family: 'Cornerstone';",
				"jsPDFFile":	"cornerstone-normal.js",
				"jsPDFName":	"Cornerstone",
				"jsPDFStyle":	"normal"
            },
			"Courier New": {
                "style":		"font-family: 'Courier New';",
				"jsPDFFile":	"courierNew-normal.js",
				"jsPDFName":	"CourierNewPSMT",
				"jsPDFStyle":	"normal"
            },
			"Courier New Italic": {
                "style":		"font-family: 'Courier New Italic';",
				"jsPDFFile":	"courierNewItalic-italic.js",
				"jsPDFName":	"CourierNewPS-ItalicMT",
				"jsPDFStyle":	"italic"
			},
			"Crillee": {
                "style":		"font-family: 'Crillee';",
				"jsPDFFile":	"CRILLEE-normal.js",
				"jsPDFName":	"Crillee",
				"jsPDFStyle":	"normal"
			},
			"D3 Archism": {
                "style":		"font-family: 'D3 Archism';",
				"jsPDFFile":	"D3-normal.js",
				"jsPDFName":	"D3Archism",
				"jsPDFStyle":	"normal"
			},
			"Diploma": {
                "style":		"font-family: 'Diploma';",
				"jsPDFFile":	"Diploma-normal.js",
				"jsPDFName":	"Diploma",
				"jsPDFStyle":	"normal"
            },
			"Dream Orphans": {
                "style":		"font-family: 'Dream Orphans';",
				"jsPDFFile":	"dreamOrphans-normal.js",
				"jsPDFName":	"DreamOrphans",
				"jsPDFStyle":	"normal"
			},
			"Eccentric Std": {
                "style":		"font-family: 'Eccentric Std';",
				"jsPDFFile":	"Eccentric-normal.js",
				"jsPDFName":	"EccentricStd",
				"jsPDFStyle":	"normal"
            },
			"Echelon": {
                "style":		"font-family: 'Echelon';",
				"jsPDFFile":	"echelon-normal.js",
				"jsPDFName":	"Echelon",
				"jsPDFStyle":	"normal"
            },
			"Echelon Italic": {
                "style":		"font-family: 'Echelon Italic';",
				"jsPDFFile":	"echelonItalic-italic.js",
				"jsPDFName":	"EchelonItalic",
				"jsPDFStyle":	"italic"
			},
			"Eleganza": {
                "style":		"font-family: 'Eleganza';",
				"jsPDFFile":	"Eleganza-normal.js",
				"jsPDFName":	"Eleganza-Plain",
				"jsPDFStyle":	"normal"
			},
			"Elisia": {
                "style":		"font-family: 'Elisia';",
				"jsPDFFile":	"elisia-normal.js",
				"jsPDFName":	"Elisia",
				"jsPDFStyle":	"normal"
			},
			"Emblem": {
                "style":		"font-family: 'Emblem';",
				"jsPDFFile":	"EMBLEM-normal.js",
				"jsPDFName":	"Emblem",
				"jsPDFStyle":	"normal"
			},
			"Florence": {
                "style":		"font-family: 'Florence';",
				"jsPDFFile":	"Florence-Regular-normal.js",
				"jsPDFName":	"Florence-Regular",
				"jsPDFStyle":	"normal"
            },
			"Franklin Gothic Medium": {
                "style":		"font-family: 'Franklin Gothic Medium';",
				"jsPDFFile":	"franklinGothicMedium-normal.js",
				"jsPDFName":	"FranklinGothic-Medium",
				"jsPDFStyle":	"normal"
			},
			"Freehand 471 BT": {
                "style":		"font-family: 'Freehand 471 BT';",
				"jsPDFFile":	"freehand_471_bt-normal.js",
				"jsPDFName":	"Freehand471BT-Regular",
				"jsPDFStyle":	"normal"
			},
			"Fritz-Quad": {
                "style":		"font-family: 'Fritz-Quad';",
				"jsPDFFile":	"Fritz-Quad-normal.js",
				"jsPDFName":	"Fritz-Quad",
				"jsPDFStyle":	"normal"
			},
			"Georgia": {
                "style":		"font-family: 'Georgia';",
				"jsPDFFile":	"Georgia-normal.js",
				"jsPDFName":	"Georgia",
				"jsPDFStyle":	"normal"
            },
			"Georgia Italic": {
                "style":		"font-family: 'Georgia Italic';",
				"jsPDFFile":	"georgiaItalic-italic.js",
				"jsPDFName":	"Georgia-Italic",
				"jsPDFStyle":	"italic"
			},
			"GeosansLight": {
                "style":		"font-family: 'GeosansLight';",
				"jsPDFFile":	"GeosansLight-normal.js",
				"jsPDFName":	"GeosansLight",
				"jsPDFStyle":	"normal"
            },
			"Goodfish": {
                "style":		"font-family: 'Goodfish';",
				"jsPDFFile":	"goodfish-normal.js",
				"jsPDFName":	"Goodfish",
				"jsPDFStyle":	"normal"
			},
			"Gotham Black": {
                "style":		"font-family: 'Gotham Black';",
				"jsPDFFile":	"gothamBlackRegular-normal.js",
				"jsPDFName":	"Gotham-Black",
				"jsPDFStyle":	"normal"
			},
			"HandelGotDBol": {
                "style":		"font-family: 'HandelGotDBol';",
				"jsPDFFile":	"HandelGotDBol-normal.js",
				"jsPDFName":	"HandelGotD-Bold",
				"jsPDFStyle":	"normal"
			},
			"HandelGotDLig": {
                "style":		"font-family: 'HandelGotDLig';",
				"jsPDFFile":	"HandelGotDLig-normal.js",
				"jsPDFName":	"HandelGotD-Ligh",
				"jsPDFStyle":	"normal"
			},
			"HandScript": {
                "style":		"font-family: 'HandScript';",
				"jsPDFFile":	"HandScript-normal.js",
				"jsPDFName":	"HandScript",
				"jsPDFStyle":	"normal"
            },
			"HoratioDLig": {
                "style":		"font-family: 'HoratioDLig';",
				"jsPDFFile":	"HORATIOL-normal.js",
				"jsPDFName":	"HoratioD-Ligh",
				"jsPDFStyle":	"normal"
			},
			"Impact": {
                "style":		"font-family: 'Impact';",
				"jsPDFFile":	"Impact-normal.js",
				"jsPDFName":	"Impact",
				"jsPDFStyle":	"normal"
			},
			"JaneAusten": {
                "style":		"font-family: 'JaneAusten';",
				"jsPDFFile":	"JaneAust-normal.js",
				"jsPDFName":	"JaneAusten",
				"jsPDFStyle":	"normal"
			},
			"Labtop": {
                "style":		"font-family: 'Labtop';",
				"jsPDFFile":	"Labtop-normal.js",
				"jsPDFName":	"Labtop",
				"jsPDFStyle":	"normal"
			},
			"Lariat": {
                "style":		"font-family: 'Lariat';",
				"jsPDFFile":	"LARIAT-normal.js",
				"jsPDFName":	"Lariat",
				"jsPDFStyle":	"normal"
			},
			"LatinoSamba": {
                "style":		"font-family: 'LatinoSamba';",
				"jsPDFFile":	"latino samba-normal.js",
				"jsPDFName":	"LatinoSamba",
				"jsPDFStyle":	"normal"
			},
			"Lucida Sans Regular": {
                "style":		"font-family: 'Lucida Sans Regular';",
				"jsPDFFile":	"lucidaSansRegular-normal.js",
				"jsPDFName":	"LucidaSans",
				"jsPDFStyle":	"normal"
			},
			"Magical Wands": {
                "style":		"font-family: 'Magical Wands';",
				"jsPDFFile":	"Magical-normal.js",
				"jsPDFName":	"MagicalWands",
				"jsPDFStyle":	"normal"
			},
			"MicrogrammaDBolExt": {
                "style":		"font-family: 'MicrogrammaDBolExt';",
				"jsPDFFile":	"MICROGBE-normal.js",
				"jsPDFName":	"MicrogrammaD-BoldExte",
				"jsPDFStyle":	"normal"
            },
			"Microsoft Sans Serif": {
                "style":		"font-family: 'Microsoft Sans Serif';",
				"jsPDFFile":	"microsoftSansSerif-normal.js",
				"jsPDFName":	"MicrosoftSansSerif",
				"jsPDFStyle":	"normal"
			},
			"Plantagenet Cherokee": {
                "style":		"font-family: 'Plantagenet Cherokee';",
				"jsPDFFile":	"PlantagenetCherokee-normal.js",
				"jsPDFName":	"PlantagenetCherokee",
				"jsPDFStyle":	"normal"
			},
			"Poor Richard": {
                "style":		"font-family: 'Poor Richard';",
				"jsPDFFile":	"poor_richard_2-normal.js",
				"jsPDFName":	"PoorRichard-Regular",
				"jsPDFStyle":	"normal"
			},
			"Poplar Std": {
                "style":		"font-family: 'Poplar Std';",
				"jsPDFFile":	"Poplar-normal.js",
				"jsPDFName":	"PoplarStd",
				"jsPDFStyle":	"normal"
			},
			"Pushkin": {
                "style":		"font-family: 'Pushkin';",
				"jsPDFFile":	"pushkin-normal.js",
				"jsPDFName":	"Pushkin",
				"jsPDFStyle":	"normal"
            },
			"Radiated Pancake": {
                "style":		"font-family: 'Radiated Pancake';",
				"jsPDFFile":	"Radiated-normal.js",
				"jsPDFName":	"RadiatedPancake",
				"jsPDFStyle":	"normal"
			},
			"Renaissance": {
                "style":		"font-family: 'Renaissance';",
				"jsPDFFile":	"renaissance-normal.js",
				"jsPDFName":	"Renaissance-Regular",
				"jsPDFStyle":	"normal"
            },
			"Sabadoo": {
                "style":		"font-family: 'Sabadoo';",
				"jsPDFFile":	"Sabadoo-normal.js",
				"jsPDFName":	"Sabadoo",
				"jsPDFStyle":	"normal"
			},
			"Sabertooth": {
                "style":		"font-family: 'Sabertooth';",
				"jsPDFFile":	"Sabertooth-normal.js",
				"jsPDFName":	"SabertoothRegularSWFTE",
				"jsPDFStyle":	"normal"
			},
			"Sable Lion": {
                "style":		"font-family: 'Sable Lion';",
				"jsPDFFile":	"Sable-normal.js",
				"jsPDFName":	"SableLion",
				"jsPDFStyle":	"normal"
			},
			"Saccule": {
                "style":		"font-family: 'Saccule';",
				"jsPDFFile":	"Saccule-normal.js",
				"jsPDFName":	"Saccule",
				"jsPDFStyle":	"normal"
            },
			"Sansation": {
                "style":		"font-family: 'Sansation';",
				"jsPDFFile":	"Sansation_Regular-normal.js",
				"jsPDFName":	"SansationRegular",
				"jsPDFStyle":	"normal"
			},
			"Sansation Bold": {
                "style":		"font-family: 'Sansation Bold';",
				"jsPDFFile":	"Sansation_Bold-bold.js",
				"jsPDFName":	"SansationBold",
				"jsPDFStyle":	"bold"
			},
			"Silom": {
                "style":		"font-family: 'Silom';",
				"jsPDFFile":	"Silom-normal.js",
				"jsPDFName":	"Silom",
				"jsPDFStyle":	"normal"
			},
			"Sofia Regular": {
                "style":		"font-family: 'Sofia Regular';",
				"jsPDFFile":	"Sofia-Regular-normal.js",
				"jsPDFName":	"Sofia-Regular",
				"jsPDFStyle":	"normal"
			},
			"Steiner": {
                "style":		"font-family: 'Steiner';",
				"jsPDFFile":	"Steinerlight-normal.js",
				"jsPDFName":	"Steiner",
				"jsPDFStyle":	"normal"
			},
			"Stencil Std": {
                "style":		"font-family: 'Stencil Std';",
				"jsPDFFile":	"stencil-normal.js",
				"jsPDFName":	"StencilStd",
				"jsPDFStyle":	"normal"
			},
			"T4C Beaulieux": {
                "style":		"font-family: 'T4C Beaulieux';",
				"jsPDFFile":	"T4C-normal.js",
				"jsPDFName":	"T4CBeaulieux",
				"jsPDFStyle":	"normal"
			},
			"Tag LET": {
                "style":		"font-family: 'Tag Let';",
				"jsPDFFile":	"Tag-normal.js",
				"jsPDFName":	"TagLetPlain",
				"jsPDFStyle":	"normal"
			},
			"Tahoma": {
                "style":		"font-family: 'Tahoma';",
				"jsPDFFile":	"Tahoma-normal.js",
				"jsPDFName":	"Tahoma",
				"jsPDFStyle":	"normal"
			},
			"Tahoma Bold": {
                "style":		"font-family: 'Tahoma Bold';",
				"jsPDFFile":	"tahomaBold-bold.js",
				"jsPDFName":	"Tahoma-Bold",
				"jsPDFStyle":	"bold"
			},
			"Times New Roman": {
                "style":		"font-family: 'Times New Roman';",
				"jsPDFFile":	"timesNewRoman-normal.js",
				"jsPDFName":	"TimesNewRomanPSMT",
				"jsPDFStyle":	"normal"
			},
			"Times New Roman Bold": {
                "style":		"font-family: 'Times New Roman Bold';",
				"jsPDFFile":	"timesNewRomanBold-bold.js",
				"jsPDFName":	"TimesNewRomanPS-BoldMT",
				"jsPDFStyle":	"bold"
            },
			"Times New Roman Italic": {
                "style":		"font-family: 'Times New Roman Italic';",
				"jsPDFFile":	"timesNewRomanItalic-italic.js",
				"jsPDFName":	"TimesNewRomanPS-ItalicMT",
				"jsPDFStyle":	"italic"
			},
			"Tycho": {
                "style":		"font-family: 'Tycho';",
				"jsPDFFile":	"TYCHO-normal.js",
				"jsPDFName":	"Tycho",
				"jsPDFStyle":	"normal"
			},
			"Verdana": {
                "style":		"font-family: 'Verdana';",
				"jsPDFFile":	"Verdana-normal.js",
				"jsPDFName":	"Verdana",
				"jsPDFStyle":	"normal"
            },
			"Verdana Bold": {
                "style":		"font-family: 'Verdana Bold';",
				"jsPDFFile":	"verdanaBold-bold.js",
				"jsPDFName":	"Verdana-Bold",
				"jsPDFStyle":	"bold"
            },
			"Verdana Italic": {
                "style":		"font-family: 'Verdana Italic';",
				"jsPDFFile":	"verdanaItalic-italic.js",
				"jsPDFName":	"Verdana-Italic",
				"jsPDFStyle":	"italic"
			},
			"Virtue": {
                "style":		"font-family: 'Virtue';",
				"jsPDFFile":	"Virtue-normal.js",
				"jsPDFName":	"Virtue",
				"jsPDFStyle":	"normal"
			},
			"Vivacious": {
                "style":		"font-family: 'Vivacious';",
				"jsPDFFile":	"Vivacious-normal.js",
				"jsPDFName":	"Vivacious",
				"jsPDFStyle":	"normal"
            },
			"Walkway Bold": {
                "style":		"font-family: 'Walkway Bold';",
				"jsPDFFile":	"walkwayBold-bold.js",
				"jsPDFName":	"WalkwayBold",
				"jsPDFStyle":	"bold"
			},
			"Walkway Oblique": {
                "style":		"font-family: 'Walkway Oblique';",
				"jsPDFFile":	"walkwayOblique-normal.js",
				"jsPDFName":	"WalkwayOblique",
				"jsPDFStyle":	"normal"
			},
			"Walkway RevOblique": {
                "style":		"font-family: 'Walkway RevOblique';",
				"jsPDFFile":	"walkwayRevOblique-normal.js",
				"jsPDFName":	"WalkwayRevOblique",
				"jsPDFStyle":	"normal"
			},
			"Windsong": {
                "style":		"font-family: 'Windsong';",
				"jsPDFFile":	"Windsong-normal.js",
				"jsPDFName":	"Windsong",
				"jsPDFStyle":	"normal"
            }
        };
        
        texelObject._setAllowedColors();

        texelObject.setAttributes({
            'isTemporary': (typeof isTemporary != 'undefined' ? isTemporary : false),
            'actualDPI': 72,
            'modifiedDPI': 72,
            'productWidthInches': designData.width,
            'productHeightInches': designData.height,
            'jsonData': designData.jsonData,
            'initialLocation': designData.location,
            'currentLocation': designData.location,
            'canvasWidth': 0,
            'canvasHeight': 0,
            'canvasScaleX': 1,
            'canvasScaleY': 1,
            'designerState': [],
            'designerStateCurrentIndex': -1,
            'objectCounter': 0,
            'isVariable': false,
            'backgroundColor': (typeof designData.backgroundColor == 'undefined' ? 'ffffff' : designData.backgroundColor),
            'userImages': (typeof designData.userImages != 'undefined' ? designData.userImages : [{
                'src': 'https://actionenvelope.scene7.com/is/image/ActionEnvelope/image_library_icon',
                'hide': true
            }])
        });

        if (typeof designData !== "undefined" && typeof designData.jsonData !== "undefined") {
            if (typeof designData.jsonData.front !== "undefined" && typeof designData.jsonData.front.length !== "undefined") {
                for (var i = 0; i < designData.jsonData.front.length; i++) {
                    var data = designData.jsonData.front[i];

                    if (data.drawAs == "image") {
                        var userImages = texelObject.getAttribute("userImages");

                        userImages.push({
                            "base64": data.base64.replace("&#x3b;", ";").replace(/&#x2b;/g, "+"),
                            "hide": true
                        });

                        texelObject.setAttribute("userImages", userImages);
                    }
                }
            }
        }

        texelObject.setAttribute('productWidthPixels', texelObject.getAttribute('productWidthInches') * texelObject.getAttribute('actualDPI'));
        texelObject.setAttribute('productHeightPixels', texelObject.getAttribute('productHeightInches') * texelObject.getAttribute('actualDPI'));
        texelObject.setAttribute('dpiMultiplier', texelObject.getAttribute('modifiedDPI') / texelObject.getAttribute('actualDPI'));
        texelObject.loadUserImages();
        texelObject.loaded.general = true;
    }

    areAssetsLoaded() {
        let texelObject = this;

        for (let key in texelObject.loaded) {
            if (!texelObject.loaded[key]) {
                return false;
            }
        }

        return true;
    }
    
    _setAllowedColors() {
        let texelObject = this;

        texelObject.allowedInkColors = {
            '#FFFFFF': ['#000000', '#002856', '#2E1B46', '#A32136', '#CB007C', '#005740', '#6F5D51', '#6C6864', '#008996', '#8C3D45', '#C20430', '#F7DBE0', '#009946', '#88674D', '#8D9B9B', '#0082CA', '#84286B', '#993921', '#F8BF00', '#6B5A24', '#CAAA7B', '#CACAC8', '#98D2DD', '#7474C1', '#BC4800', '#FDDD3F', '#90993F', '#F3DBB3', '#C0C0C0', '#BAD2DD', '#B5B4E0', '#D57F00', '#937851', '#A1D784', '#FF9016', '#EDE59B', '#DBE444', '#E1261C', '#F1ED74'],
            '#F4F0E4': ['#000000', '#002856', '#2E1B46', '#A32136', '#CB007C', '#005740', '#6F5D51', '#6C6864', '#008996', '#8C3D45', '#C20430', '#F7DBE0', '#009946', '#88674D', '#8D9B9B', '#0082CA', '#84286B', '#993921', '#F8BF00', '#6B5A24', '#CAAA7B', '#CACAC8', '#98D2DD', '#7474C1', '#BC4800', '#FDDD3F', '#90993F', '#F3DBB3', '#C0C0C0', '#BAD2DD', '#B5B4E0', '#D57F00', '#937851', '#A1D784', '#FF9016', '#EDE59B', '#DBE444', '#E1261C', '#F1ED74'],
            '#F0EAD5': ['#000000', '#002856', '#2E1B46', '#A32136', '#CB007C', '#005740', '#6F5D51', '#6C6864', '#008996', '#8C3D45', '#C20430', '#F7DBE0', '#009946', '#88674D', '#8D9B9B', '#0082CA', '#84286B', '#993921', '#F8BF00', '#6B5A24', '#CAAA7B', '#CACAC8', '#98D2DD', '#7474C1', '#BC4800', '#FDDD3F', '#90993F', '#C0C0C0', '#BAD2DD', '#B5B4E0', '#D57F00', '#937851', '#A1D784', '#FF9016', '#EDE59B', '#DBE444', '#E1261C', '#F1ED74'],
            '#E7DAB8': ['#000000', '#002856', '#2E1B46', '#A32136', '#CB007C', '#005740', '#6F5D51', '#6C6864', '#008996', '#8C3D45', '#C20430', '#009946', '#88674D', '#8D9B9B', '#0082CA', '#84286B', '#993921', '#6B5A24', '#CAAA7B', '#98D2DD', '#7474C1', '#BC4800', '#FDDD3F', '#90993F', '#C0C0C0', '#BAD2DD', '#B5B4E0', '#D57F00', '#937851', '#A1D784', '#FF9016', '#EDE59B', '#DBE444', '#E1261C', '#F1ED74'],
            '#D6C6A5': ['#000000', '#002856', '#2E1B46', '#A32136', '#CB007C', '#005740', '#6F5D51', '#6C6864', '#008996', '#8C3D45', '#C20430', '#009946', '#88674D', '#8D9B9B', '#0082CA', '#84286B', '#993921', '#6B5A24', '#CAAA7B', '#CACAC8', '#98D2DD', '#7474C1', '#BC4800', '#FDDD3F', '#90993F', '#C0C0C0', '#B5B4E0', '#937851', '#A1D784', '#FF9016', '#DBE444', '#E1261C'],
            '#BC9F73': ['#000000', '#002856', '#2E1B46', '#A32136', '#CB007C', '#005740', '#6F5D51', '#6C6864', '#008996', '#8C3D45', '#C20430', '#009946', '#8D9B9B', '#0082CA', '#84286B', '#BC4800', '#C0C0C0', '#937851', '#A1D784', '#FFFFFF', '#FF9016', '#DBE444', '#E1261C'],
            '#D3D4D6': ['#000000', '#002856', '#2E1B46', '#A32136', '#CB007C', '#005740', '#6F5D51', '#6C6864', '#008996', '#8C3D45', '#C20430', '#009946', '#88674D', '#0082CA', '#84286B', '#993921', '#6B5A24', '#CAAA7B', '#98D2DD', '#7474C1', '#BC4800', '#90993F', '#D57F00', '#A1D784', '#FF9016', '#DBE444', '#E1261C', '#F1ED74'],
            '#F5D791': ['#000000', '#002856', '#2E1B46', '#A32136', '#CB007C', '#005740', '#6F5D51', '#6C6864', '#008996', '#8C3D45', '#C20430', '#009946', '#88674D', '#8D9B9B', '#0082CA', '#84286B', '#CAAA7B', '#BC4800', '#D57F00', '#A1D784', '#FFFFFF', '#FF9016', '#E1261C'],
            '#FBBE15': ['#000000', '#002856', '#2E1B46', '#A32136', '#CB007C', '#005740', '#6F5D51', '#6C6864', '#008996', '#8C3D45', '#C20430', '#009946', '#88674D', '#8D9B9B', '#0082CA', '#84286B', '#CAAA7B', '#A1D784', '#FFFFFF', '#FF9016'],
            '#F1CCD4': ['#000000', '#002856', '#2E1B46', '#A32136', '#CB007C', '#005740', '#6F5D51', '#6C6864', '#008996', '#8C3D45', '#C20430', '#009946', '#88674D', '#8D9B9B', '#0082CA', '#84286B', '#6B5A24', '#CAAA7B', '#7474C1', '#BC4800', '#90993F', '#B5B4E0', '#A1D784'],
            '#B94873': ['#000000', '#002856', '#2E1B46', '#A32136', '#005740', '#6F5D51', '#6C6864', '#8C3D45', '#88674D', '#84286B', '#CAAA7B', '#C0C0C0', '#FFFFFF'],
            '#F3CACE': ['#000000', '#002856', '#2E1B46', '#A32136', '#CB007C', '#005740', '#6F5D51', '#6C6864', '#008996', '#8C3D45', '#C20430', '#009946', '#88674D', '#8D9B9B', '#0082CA', '#84286B', '#CAAA7B', '#BC4800', '#90993F', '#B5B4E0', '#D57F00', '#A1D784', '#FF9016'],
            '#CBBBEC': ['#000000', '#002856', '#2E1B46', '#A32136', '#CB007C', '#005740', '#6F5D51', '#6C6864', '#8C3D45', '#C20430', '#009946', '#8D9B9B', '#84286B', '#FFFFFF'],
            '#D7D7D7': ['#000000', '#002856', '#2E1B46', '#A32136', '#CB007C', '#005740', '#6F5D51', '#6C6864', '#8C3D45', '#C20430', '#88674D', '#8D9B9B', '#0082CA', '#84286B', '#993921', '#F8BF00', '#6B5A24', '#CAAA7B', '#CACAC8', '#98D2DD', '#7474C1', '#BC4800', '#FDDD3F', '#90993F', '#F3DBB3', '#BAD2DD', '#B5B4E0', '#A1D784', '#FF9016', '#EDE59B', '#DBE444', '#E1261C', '#F1ED74'],
            '#D0D2E9': ['#000000', '#002856', '#2E1B46', '#A32136', '#CB007C', '#005740', '#6F5D51', '#6C6864', '#8C3D45', '#C20430', '#009946', '#88674D', '#8D9B9B', '#84286B', '#A1D784', '#FFFFFF'],
            '#6A6092': ['#000000', '#002856', '#2E1B46', '#A32136', '#CB007C', '#005740', '#6F5D51', '#6C6864', '#8C3D45', '#C20430', '#FFFFFF'],
            '#B2DFDA': ['#000000', '#002856', '#2E1B46', '#A32136', '#CB007C', '#005740', '#6F5D51', '#6C6864', '#008996', '#8C3D45', '#C20430', '#88674D'],
            '#DAE8EB': ['#000000', '#002856', '#2E1B46', '#A32136', '#CB007C', '#005740', '#6F5D51', '#6C6864', '#008996', '#8C3D45', '#C20430', '#009946', '#88674D', '#8D9B9B', '#84286B', '#CAAA7B', '#FF9016'],
            '#C1D9F3': ['#000000', '#002856', '#2E1B46', '#A32136', '#CB007C', '#005740', '#6F5D51', '#6C6864', '#008996', '#8C3D45', '#C20430', '#009946', '#88674D', '#8D9B9B', '#0082CA'],
            '#9DBF69': ['#000000', '#002856', '#2E1B46', '#A32136', '#005740', '#6F5D51', '#6C6864', '#8C3D45', '#C20430', '#88674D', '#8D9B9B', '#CAAA7B', '#937851', '#FFFFFF'],
            '#AAE4CE': ['#000000', '#002856', '#2E1B46', '#A32136', '#CB007C', '#005740', '#6F5D51', '#6C6864', '#8C3D45', '#C20430', '#88674D', '#8D9B9B', '#0082CA', '#84286B'],
            '#91A15A': ['#000000', '#002856', '#2E1B46', '#A32136', '#005740', '#6F5D51', '#6C6864', '#C20430', '#88674D', '#8D9B9B', '#FFFFFF'],
            '#8DC65D': ['#000000', '#002856', '#2E1B46', '#A32136', '#CB007C', '#005740', '#6F5D51', '#6C6864', '#C20430', '#88674D', '#8D9B9B', '#84286B', '#C0C0C0', '#937851', '#FFFFFF'],
            '#FFFBA9': ['#000000', '#002856', '#2E1B46', '#A32136', '#CB007C', '#005740', '#6F5D51', '#6C6864', '#008996', '#8C3D45', '#C20430', '#009946', '#88674D', '#8D9B9B', '#0082CA', '#84286B', '#993921', '#6B5A24', '#CAAA7B', '#98D2DD', '#7474C1', '#BC4800', '#90993F', '#B5B4E0', '#D57F00', '#937851', '#A1D784', '#DBE444'],
            '#E17A38': ['#000000', '#002856', '#2E1B46', '#A32136', '#CB007C', '#005740', '#6F5D51', '#6C6864', '#8D9B9B', '#C0C0C0', '#937851', '#FFFFFF'],
            '#ED5D43': ['#000000', '#002856', '#2E1B46', '#A32136', '#CB007C', '#005740', '#6F5D51', '#6C6864', '#88674D', '#8D9B9B', '#C0C0C0', '#937851', '#FFFFFF'],
            '#E1A67E': ['#000000', '#002856', '#2E1B46', '#A32136', '#6F5D51', '#6C6864', '#C0C0C0', '#937851', '#FFFFFF'],
            '#72716D': ['#000000', '#002856', '#2E1B46', '#A32136', '#CB007C', '#005740', '#C0C0C0', '#937851', '#FFFFFF'],
            '#CB4252': ['#000000', '#002856', '#2E1B46', '#6C6864', '#C0C0C0', '#937851', '#FFFFFF'],
            '#A72E35': ['#000000', '#002856', '#2E1B46', '#6C6864', '#C0C0C0', '#937851', '#FFFFFF'],
            '#A02F41': ['#000000', '#002856', '#2E1B46', '#C0C0C0', '#937851', '#FFFFFF'],
            '#A877A4': ['#000000', '#002856', '#2E1B46', '#2E1B46', '#6F5D51', '#6C6864', '#C20430', '#937851', '#FFFFFF'],
            '#5D67B0': ['#000000', '#6F5D51', '#6C6864', '#C0C0C0', '#937851', '#FFFFFF'],
            '#016C74': ['#000000', '#002856', '#2E1B46', '#2E1B46', '#CB007C', '#6C6864', '#FFFFFF'],
            '#1095C0': ['#000000', '#002856', '#2E1B46', '#2E1B46', '#6C6864', '#FFFFFF'],
            '#D14A84': ['#000000', '#002856', '#2E1B46', '#2E1B46', '#6F5D51', '#6C6864', '#8C3D45', '#FFFFFF'],
            '#9BAAD5': ['#000000', '#002856', '#2E1B46', '#2E1B46', '#005740', '#6F5D51', '#6C6864', '#008996', '#FFFFFF'],
            '#B3BFB5': ['#000000', '#002856', '#2E1B46', '#2E1B46', '#CB007C', '#005740', '#6F5D51', '#6C6864', '#008996', '#FFFFFF'],
            '#DA7436': ['#000000', '#002856', '#2E1B46', '#2E1B46', '#CB007C', '#005740', '#6F5D51', '#6C6864', '#C0C0C0', '#FFFFFF'],
            '#7C4250': ['#000000', '#002856', '#2E1B46', '#2E1B46', '#6C6864', '#C0C0C0', '#937851', '#FFFFFF'],
            '#8A7059': ['#C0C0C0', '#937851', '#FFFFFF'],
            '#655444': ['#C0C0C0', '#937851', '#FFFFFF'],
            '#737D87': ['#C0C0C0', '#937851', '#FFFFFF'],
            '#747EAF': ['#C0C0C0', '#937851', '#FFFFFF'],
            '#29426B': ['#C0C0C0', '#937851', '#FFFFFF', '#000000'],
            '#2D2D2D': ['#C0C0C0', '#937851', '#FFFFFF'],
            '#392C23': ['#C0C0C0', '#937851', '#FFFFFF'],
            '#386151': ['#C0C0C0', '#937851', '#FFFFFF'],
            '#17273e': ['#C0C0C0', '#937851', '#FFFFFF'],
            '#6A313A': ['#C0C0C0', '#937851', '#FFFFFF']
        };

        if (texelObject.getAttribute('isVariable')) {
            var colorsRestricted = ['#8A7059', '#655444', '#737D87', '#747EAF', '#29426B', '#2D2D2D', '#392C23']
            var colorsNotAllowed = ['#C0C0C0', '#937851'];

            for (var i = 0; i < colorsRestricted.length; i++) {
                var newArray = [];
                for (var j = 0; j < texelObject.allowedInkColors[colorsRestricted[i]].length; j++) {
                    newArray.push(texelObject.allowedInkColors[colorsRestricted[i]][j]);
                }

                for (var j = 0; j < colorsNotAllowed.length; j++) {
                    var index = $.inArray(colorsNotAllowed[j], newArray);

                    if (index > -1) {
                        newArray.splice(index, 1);
                    }
                }

                texelObject.allowedInkColors[colorsRestricted[i]] = newArray;
            }
        }
    }

    setAttribute(attributeName, attributeValue) {
        this._attributes[attributeName] = attributeValue;
        
        if (attributeName == 'isVariable') {
            this._setAllowedColors();
        }
    }

    setAttributes(attributes) {
        for (let key in attributes) {
            this.setAttribute(key, attributes[key]);    
        }
    }

    getAttribute(attributeName) {
        return this._attributes[attributeName];
    }

    getAttributes() {
        return this._attributes;
    }

    addUpdateEvent(name, updateMethod) {
        var texelObject = this;
        
        var updateEvents = texelObject.updateEvents;

        updateEvents[name] = updateMethod;
    }

    destroyTexel() {
        var texelObject = this;

        texelObject.designerContainer.remove();
    }

    getCleanUserImages() {
        let texelObject = this;
        let userImageList = [];

        for (let i = 0; i < texelObject.getAttribute('userImages').length; i++) {
            let newUserImage = {}
            for (let key in texelObject.getAttribute('userImages')[i]) {
                //if (key != 'base64') {
                    newUserImage[key] = texelObject.getAttribute('userImages')[i][key];
                //}
            }
            userImageList.push(newUserImage);
        }

        return userImageList;
    }

    texelThread(method, timeout, timeToKill) {
        var texelObject = this;
        let intervalCounter = 0;
        timeout = (typeof timeout != 'undefined' ? timeout : 100);
        timeToKill = (typeof timeToKill != 'undefined' ? timeToKill : 10000);

        texelObject.designerContainer.spinner(true, false, 150, null, null, '', null, null);
        let thread = window.setInterval(function() {
            if (texelObject.areAssetsLoaded()) {
                if (typeof method == 'function') {
                    method();
                };
                intervalCounter = timeToKill;
            }

            if (intervalCounter >= timeToKill) {
                texelObject.designerContainer.spinner(false);
                window.clearInterval(thread);
            }

            intervalCounter += timeout;
        }, timeout);
    }

    updateAddressing(data, font) {
        var texelObject = this;

        texelObject.texelThread(function() {
            if (typeof data == 'object') {
                var updatedAddressing = false;

                texelObject.canvas.getObjects().forEach(function(obj) {
                    if (typeof obj.addressingData == 'object' && obj.addressingData.length > 0) {
                        obj.addressingData = data;

                        if (obj.addressingData.length <= obj.currentAddressingIndex) {
                            obj.currentAddressingIndex = 0;
                        }

                        obj.set('text', texelObject._organizeAddressingText(obj.addressingData[obj.currentAddressingIndex]));
                        texelObject.canvas.renderAll();
                        texelObject.saveState();
                        updatedAddressing = true;
                        return;
                    }
                });

                if (!updatedAddressing) {
                    texelObject.createDesignerObject(texelObject.canvas, 'textBoxAddressing', {
                        'addressingData': data,
                        'widthPercent': 90,
                        'xPercent': 5,
                        'fontSize': font.fontSize,
                        'lineHeight': font.lineHeight,
                        'textAlign': font.textAlign,
                        'fontFamily': font.fontFamily
                    });
                }
            }
        }, 50);
    }

    _organizeAddressingText(textList) {
        return (typeof textList[0] != 'undefined' && textList[0] != '' ? textList[0] + '\n' : '') +
            (typeof textList[1] != 'undefined' && textList[1] != '' ? textList[1] + '\n' : '') +
            (typeof textList[2] != 'undefined' && textList[2] != '' ? textList[2] + '\n' : '') +
            (typeof textList[3] != 'undefined' && textList[3] != '' ? textList[3] + '\n' : '') +
            (typeof textList[4] != 'undefined' && textList[4] != '' ? textList[4] + ', ' : '') +
            (typeof textList[5] != 'undefined' && textList[5] != '' ? textList[5] + ' ' : '') +
            (typeof textList[6] != 'undefined' && textList[6] != '' ? textList[6] : '') +
            (typeof (textList[4] != 'undefined' && textList[4] != '') || (typeof textList[5] != 'undefined' && textList[5] != '') || (typeof textList[6] != 'undefined' && textList[6] != '') ? '\n' : '') +
            (typeof textList[7] != 'undefined' && textList[7] != '' ? textList[7] : '');
    }

    _loadLocation(location) {
        var texelObject = this;

        if (location != texelObject.getAttribute('currentLocation') && typeof texelObject.getAttribute('jsonData')[location] != 'undefined') {
            texelObject.designerContainer.find('[bns-texel_tool]').remove();
            texelObject.setAttribute('currentLocation', location);
            texelObject._drawCanvas(texelObject.canvas, texelObject.getAttribute('designerState')[texelObject.getAttribute('designerStateCurrentIndex')].jsonData[location], true);
        }
    }

    loadUserImages(indexToLoad) {
        let texelObject = this;

        for(let i = 0; i < texelObject.getAttribute('userImages').length; i++) {
            if (typeof texelObject.getAttribute('userImages')[i].base64 == 'undefined' && typeof texelObject.getAttribute('userImages')[i].src != 'undefined') {
                let src = texelObject.getAttribute('userImages')[i].src;

                $.ajax({
                    type: "GET",
                    url: '/envelopes/control/getBase64ImageData',
                    data: {
                        url: (src.match('^//') != null ? 'https:' : '') + src
                    },
                    dataType: 'json',
                    cache: true
                }).done(function(data) {
                    if (data.success) {
                        texelObject.getAttribute('userImages')[i].base64 = 'data:image/png-alpha;base64,' + data.base64;
                        texelObject._paintUserImage(i)
                    } else {
                        texelObject.getAttribute('userImages')[i].base64 = null;
                    }
                });
            } else if (typeof texelObject.getAttribute('userImages')[i].base64 != 'undefined') {
                texelObject._paintUserImage(i, (i == indexToLoad ? true : false));
            }
        }

        let checkImageLoad = window.setInterval(function() {
            let imagesLoaded = true;

            for(let i = 0; i < texelObject.getAttribute('userImages').length; i++) {
                if (typeof texelObject.getAttribute('userImages')[i].base64 == 'undefined') {
                    imagesLoaded = false;
                }
            }

            if (imagesLoaded) {
                texelObject.loaded.images = true;
                window.clearInterval(checkImageLoad);
            }
        }, 100);
    }

    _getObjectByName(name) {
        var texelObject = this;

        if (typeof name != 'undefined') {
            texelObject.canvas.getObjects().each(function() {
                if (this.name == name) {
                    return this
                }
            });
        }
    }

    _loadImageLibrary() {
        var texelObject = this;

        texelObject.designerContainer.find('#texelToolBox .uploadGrid').empty();
        texelObject.designerContainer.find('.texelImageLibrary').clone(true).appendTo(texelObject.designerContainer.find('#texelToolBox .uploadGrid'));
        texelObject.designerContainer.find('#texelToolBox .uploadGrid .texelImageLibrary').removeClass('texelImageLibrary');
    }

    _paintUserImage(index, load) {
        var texelObject = this;

        if (texelObject.designerContainer.find('.texelImageLibrary').length == 0) {
            texelObject.designerContainer.append(
                $('<div />').addClass('texelImageLibrary')
            );
        }

        if (typeof index != 'undefined' && typeof texelObject.getAttribute('userImages')[index] != 'undefined') {
            var imageElement = $('<img/>').attr({
                'bns-texel_user_image': index, 
                'src': texelObject.getAttribute('userImages')[index].base64,
                'index': index
            }).css({
                'display': (texelObject.getAttribute('userImages')[index].hide ? 'none' : 'inline-block')
            }).on('click', function() {
                texelObject._removeObjectByName(texelObject.designerContainer.find('#texelToolBox').attr('data-object_name'));
                texelObject.createDesignerObject(texelObject.canvas, 'image', {
                    'src': $(this).attr('src'),
                    'index': $(this).attr('index')
                });
                texelObject.removeObjectTools();
            });

            texelObject.designerContainer.find('.texelImageLibrary').append(imageElement);

            if (typeof load != 'undefined' && load) {
                imageElement.trigger('click');
            }
        }

        texelObject._loadImageLibrary();
    }

    getCanvasDataAsImage(jsonData) {
        var texelObject = this;
        var response = {};

        if (typeof jsonData != 'undefined') {
             $('body').append(
                $('<div />').attr('id', 'texelTemporaryCanvasContainer').css({
                    'position': 'absolute',
                    'left': '-9999px',
                    'top': '-9999px',
                    'width': texelObject.getAttribute('productWidthPixels'),
                    'height': texelObject.getAttribute('productHeightPixels')
                }).append(
                    $('<canvas />').attr('id', 'texelTemporaryCanvas')
                )
            );

            if (typeof texelObject.designerContainer == 'undefined' || texelObject.designerContainer == null) { 
                texelObject.designerContainer = $('#texelTemporaryCanvasContainer');
            }

            var temporaryCanvas = new fabric.Canvas('texelTemporaryCanvas');
            temporaryCanvas.preserveObjectStacking = true;

            for (var location in jsonData) {
                texelObject._renderDesignerStyling(temporaryCanvas, texelObject.getAttribute('productWidthInches'), texelObject.getAttribute('productHeightInches'), true);
                texelObject._drawCanvas(temporaryCanvas, jsonData[location], false, {'loadObjectTools': 'true'});
                response[location] = temporaryCanvas.toDataURL();
            }

            temporaryCanvas.dispose();
            temporaryCanvas = undefined;
            $('#texelTemporaryCanvasContainer').remove();
        }

        return response;
    }

    _doUpdateEvents() {
        var texelObject = this;
        
        texelObject._updateThumbnails();

        for (var name in texelObject.updateEvents) {
            texelObject.updateEvents[name]();
        }
    }

    _updateThumbnails() {
        let texelObject = this;
        let imageData = texelObject.getAttribute('designerState')[texelObject.getAttribute('designerStateCurrentIndex')].imageData;

        for (let location in imageData) {
            texelObject.designerContainer.find('[bns-thumbnail_location="' + location + '"]').attr('src', imageData[location]);
        }
    }

    resetDesign() {
        var texelObject = this;

        texelObject.loadState(0);
        texelObject.getAttribute('designerState').splice(texelObject.getAttribute('designerStateCurrentIndex') + 1);
    }

    _renderDesignerStyling(canvas, productWidth, productHeight) {
        var texelObject = this;
    
        var widthAllowed = getFullWidth(texelObject.designerContainer) - (texelObject.getAttribute('isTemporary') ? 0 : 500);
        var heightAllowed = getFullHeight(texelObject.designerContainer) - (texelObject.getAttribute('isTemporary') ? 0 : 130 + getFullHeight(texelObject.designerContainer.find('.jqs-texelTemplateHelp')));
    
        if (widthAllowed / heightAllowed <= productWidth / productHeight) {
            texelObject.setAttribute('canvasWidth', widthAllowed);
            texelObject.setAttribute('canvasHeight', texelObject.getAttribute('canvasWidth') * (productHeight / productWidth));
        } else {
            texelObject.setAttribute('canvasHeight', heightAllowed);
            texelObject.setAttribute('canvasWidth', texelObject.getAttribute('canvasHeight') * (productWidth / productHeight));
        }
    
        $('#' + texelObject.designerContainer.attr('id') + '_CSS').remove();
        
        $('#designerCSSModified').remove();
        $('#designerCSS').after(
            $('<style />').attr('id', texelObject.designerContainer.attr('id') + '_CSS').append(
                '#' + texelObject.designerContainer.attr('id') + ' .canvas-container {' + 
                    'left:' + 'calc(50% - ' + (texelObject.getAttribute('canvasWidth') / 2) + 'px) !important;',
                    'opacity:' + '1 !important;' +
                '}'
            )
        );
    
        canvas.setWidth(texelObject.getAttribute('canvasWidth'));
        canvas.setHeight(texelObject.getAttribute('canvasHeight'));
    
        texelObject.designerContainer.find('.envDesignerOutsideColumn.leftSide').css({
            'position': 'absolute',
            'left': 'calc(50% - ' + (((texelObject.getAttribute('canvasWidth') / 2) + 150) + 10) + 'px)',
            'height': 'calc(100%)'
        });
    
        texelObject.designerContainer.find('.envDesignerOutsideColumn.rightSide').css({
            'position': 'absolute',
            'left': 'calc(50% + ' + ((texelObject.getAttribute('canvasWidth') / 2) + 10) + 'px)',
            'height': 'calc(100%)'
        });
    
        texelObject.setAttribute('canvasScaleX', texelObject.getAttribute('canvasWidth') / texelObject.getAttribute('productWidthPixels'));
        texelObject.setAttribute('canvasScaleY', texelObject.getAttribute('canvasHeight') / texelObject.getAttribute('productHeightPixels'));
    }
    
    _drawCanvas(canvas, objList, save, ignoredEvents) {
        var texelObject = this;
        var ignoredEvents = (typeof ignoredEvents == 'object' ? ignoredEvents : {});

        if (typeof objList != 'undefined') {
            canvas.clear();
            
            canvas.backgroundColor = 'transparent';
            //canvas.backgroundColor = '#' + texelObject.getAttribute('backgroundColor');
    
            for (var i = 0; i < objList.length; i++) {
                var attributes = objList[i];
                attributes.zIndex = i;
                
                if (typeof attributes.type != 'undefined') {
                    texelObject.createDesignerObject(canvas, attributes.type, attributes, Object.assign({
                        'showToolBox': true,
                        'saveState': true
                    }, ignoredEvents))
                };
            }
            
            if (typeof save == 'undefined' || save) {
                /*
                var saveStateInterval = window.setInterval(function() {
                    texelObject._syncCanvasObjectZIndex();
                    if (objList.length == canvas.getObjects().length) {
                        texelObject.saveState();
                        clearInterval(saveStateInterval);
                    }
                }, 100);
                */
                texelObject.saveState();
            }
        }
    }

    _syncCanvasObjectZIndex() {
        var texelObject = this;
        
        texelObject.canvas.getObjects().forEach(function (obj) {
            texelObject.canvas.moveTo(obj, obj.zIndex);
        });
    }

    _isColorAllowed(hex1, hex2) {
        let texelObject = this;

        let difference = Math.abs(
            texelObject._getColorContrast(hex1) -
            texelObject._getColorContrast(hex2)
        );

        return difference > 50;
    }

    _getColorContrast(hex) {
        hex = hex.replace('#', '');
        if (hex.length == 2) {
            hex += hex + hex;
        }

        return (((parseInt(hex.substr(0,2),16) * 299) + (parseInt(hex.substr(2,2),16) * 587) + (parseInt(hex.substr(4,2),16) * 114)) / 1000);
    }

    _addDesignerObject(canvas, obj, type, objName, ignoredEvents) {
        var texelObject = this;

        if (typeof obj != 'undefined') {
            if (typeof objName != 'undefined') {
                // Set additional information in the object created by FabricJS.
                obj.toObject = (function(toObject) {
                    return function() {
                        return fabric.util.object.extend(toObject.call(this), {
                            type: this.type,
                            name: this.name
                        });
                    };
                })(obj.toObject);
                
                obj.type = type;
                obj.name = objName;
            }
            
            canvas.add(obj);
            canvas.renderAll();
    
            texelObject._positionCanvasTools(obj);

            if (typeof ignoredEvents.showToolBox == 'undefined' || !ignoredEvents.showToolBox) {
                texelObject._loadObjectTools(obj);
                canvas.setActiveObject(obj)
            }
    
            if (typeof ignoredEvents.saveState == 'undefined' || !ignoredEvents.saveState) {
                texelObject.saveState();
            }
        }
    }

    _positionCanvasTools(obj) {
        var texelObject = this;

        if (typeof obj != 'undefined') {
            if (obj.type == 'textBoxAddressing') {
                texelObject.designerContainer.find('[bns-texel_tool="addressRotator"]').css({
                    'top': obj.top - 25,
                    'left': obj.left + (obj.width * obj.scaleX) - 115
                });
            }
        } else {
            var addressRotator = texelObject.designerContainer.find('[bns-texel_tool="addressRotator"]')
            if (addressRotator.length > 0) {
                var location = addressRotator.attr('bns-location');
                var index = addressRotator.attr('bns-index');
                var myObjectToFollow = texelObject.getAttribute('designerState')[texelObject.getAttribute('designerStateCurrentIndex')].jsonData[location][index];

                addressRotator.css({
                    'top': myObjectToFollow.top - 25,
                    'left': myObjectToFollow.left + (myObjectToFollow.width * myObjectToFollow.scaleX) - 115
                });
            }
        }
    }

    saveState(data) {
        var texelObject = this;
        
        texelObject.getAttribute('designerState').splice(texelObject.getAttribute('designerStateCurrentIndex') + 1);

        var jsonData = (typeof data != 'undefined' ? data : texelObject._getObjectAttributes(texelObject.canvas.getObjects()));
        var imageData = texelObject.getCanvasDataAsImage(jsonData);

        texelObject.getAttribute('designerState').push({
            'currentLocation': texelObject.getAttribute('currentLocation'),
            'jsonData': jsonData,
            'imageData': imageData
        });

        texelObject.setAttribute('designerStateCurrentIndex', texelObject.getAttribute('designerState').length - 1);
        
        texelObject._doUpdateEvents();
    }

    loadState(index) {
        var texelObject = this;

        if (typeof texelObject.getAttribute('designerState')[index] != 'undefined') {
            // reload image data for the thumbnails... this is for efficiency when switching background colors.
            texelObject.getAttribute('designerState')[index].imageData = texelObject.getCanvasDataAsImage(texelObject.getAttribute('designerState')[index].jsonData);
            texelObject.designerContainer.find('[bns-texel_tool]').remove();
            texelObject.setAttribute('designerStateCurrentIndex', index);
            texelObject.setAttribute('objectCounter', 0);
            texelObject._drawCanvas(texelObject.canvas, texelObject.getAttribute('designerState')[index].jsonData[texelObject.getAttribute('designerState')[index].currentLocation], false);
            texelObject.setAttribute('currentLocation', texelObject.getAttribute('designerState')[index].currentLocation);
            texelObject._doUpdateEvents();
        }
    }

    _getObjectAttributes(obj) {
        var texelObject = this;

        var attributesToClone = ['type', 'text', 'fontFamily', 'fontSize', 'textAlign', 'lineHeight', 'charSpacing', 'scaleX', 'scaleY', 'xPercent', 'yPercent', 'widthPercent', 'heightPercent', 'angle', 'src', 'left', 'top', 'zIndex', 'coordinates', 'originalFill', 'fill', 'addressingData', 'currentAddressingIndex', 'width', 'height', 'index', 'stroke', 'strokeWidth', 'filePath', 'strokeDashArray', 'drawAs', 'base64'];

        var copiedObject = {};
        
        for (var location in texelObject.getAttribute('jsonData')) {
            copiedObject[location] = [];

            if (texelObject.getAttribute('currentLocation') == location) {
                for (var i = 0; i < obj.length; i++) {
                    var attributes = {};
                    
                    for (var key in texelObject.canvas.getObjects()[i]) {
                        if (obj[i].hasOwnProperty(key) && $.inArray(key, attributesToClone) >= 0) {
                            attributes[key] = obj[i][key];
                        }
                    }
                    
                    copiedObject[location].push(attributes);
                }
            
                for (var i = 0; i < copiedObject[location].length; i++) {
                    if (typeof copiedObject[location][i].scaleX != 'undefined') {
                        copiedObject[location][i].scaleX = copiedObject[location][i].scaleX / texelObject.getAttribute('canvasScaleX');
                    }
            
                    if (typeof copiedObject[location][i].scaleY != 'undefined') {
                        copiedObject[location][i].scaleY = copiedObject[location][i].scaleY / texelObject.getAttribute('canvasScaleY');
                    }
            
                    if (typeof copiedObject[location][i].xPercent != 'undefined') {
                        copiedObject[location][i].xPercent = (copiedObject[location][i].left / texelObject.getAttribute('canvasWidth')) * 100;
                    }
            
                    if (typeof copiedObject[location][i].yPercent != 'undefined') {
                        copiedObject[location][i].yPercent = (copiedObject[location][i].top / texelObject.getAttribute('canvasHeight')) * 100;
                    }

                    if (typeof copiedObject[location][i].left != 'undefined') {
                        copiedObject[location][i].left = (copiedObject[location][i].left / texelObject.getAttribute('canvasScaleX'));
                    }
                    
                    if (typeof copiedObject[location][i].top != 'undefined') {
                        copiedObject[location][i].top = (copiedObject[location][i].top / texelObject.getAttribute('canvasScaleY'));
                    }

                    if (typeof copiedObject[location][i].type != 'undefined' && copiedObject[location][i].type == 'image' && typeof copiedObject[location][i].src != 'undefined' && typeof copiedObject[location][i].index != 'undefined') {
                        copiedObject[location][i].src = texelObject.getAttribute('userImages')[copiedObject[location][i].index].src;
                    }

                    if (typeof copiedObject[location][i].widthPercent != 'undefined') {
                        copiedObject[location][i].widthPercent = (copiedObject[location][i].width / (texelObject.getAttribute('canvasWidth') / texelObject.getAttribute('canvasScaleX'))) * 100;
                    }

                    if (typeof copiedObject[location][i].originalFill != 'undefined') {
                        copiedObject[location][i].fill = copiedObject[location][i].originalFill;
                    }
                }
            } else {
                for (var i = 0; i < texelObject.getAttribute('designerState')[texelObject.getAttribute('designerState').length - 1].jsonData[location].length; i++) {
                    var attributes = {};
                    var data = texelObject.getAttribute('designerState')[texelObject.getAttribute('designerState').length - 1].jsonData[location][i]

                    for (var key in data) {
                        if (data.hasOwnProperty(key) && $.inArray(key, attributesToClone) >= 0) {
                            attributes[key] = data[key];
                        }
                    }
                    
                    copiedObject[location].push(attributes);
                }
            }
        }

        return copiedObject;
    }

    createDesignerObject(canvas, type, attributes, ignoredEvents) {
        var texelObject = this;

        if (typeof type != 'undefined') {
            if (typeof attributes == 'undefined') {
                attributes = {};
            }

            ignoredEvents = (typeof ignoredEvents != 'undefined' && ignoredEvents != null ? ignoredEvents : {});
            var zIndex = (typeof attributes.zIndex != 'undefined' ? attributes.zIndex : texelObject.canvas.getObjects().length);

            if (type == 'keyline') {
                var stroke = (typeof attributes.stroke != 'undefined' ? attributes.stroke : '#000000');
                var strokeWidth = (typeof attributes.strokeWidth != 'undefined' ? parseFloat(attributes.strokeWidth) : 1);
                var fill = (typeof attributes.fill != 'undefined' ? attributes.fill : 'transparent');
                var left = (typeof attributes.left != 'undefined' ? parseFloat(attributes.left) : 0);
                var top = (typeof attributes.top != 'undefined' ? parseFloat(attributes.top) : 0);
                var scaleX = 1;
                var scaleY = 1;
                
                function getCoordinatesBySplit(coordinates) {
                    var newCoordinates = [];
                    var coordinateSplit = coordinates.split(' ');

                    for (var i = 0; i < coordinateSplit.length; i += 2) {
                        if (typeof coordinateSplit[i] != 'undefined' && typeof coordinateSplit[i + 1] != 'undefined') {
                            newCoordinates.push({
                                'x': parseFloat(coordinateSplit[i]),
                                'y': parseFloat(coordinateSplit[i + 1])
                            });
                        }
                    }
                    return newCoordinates;
                }

                if (typeof attributes.drawAs == 'undefined' || attributes.drawAs == 'path') {
                    obj = new fabric.Path(attributes.coordinates, {});
                    obj.set('coordinates', attributes.coordinates);
                } else if (typeof attributes.drawAs != 'undefined' && attributes.drawAs == 'line') {
                    obj = new fabric.Line(attributes.coordinates, {});
                    obj.set('coordinates', attributes.coordinates);
                } else if (typeof attributes.drawAs != 'undefined' && attributes.drawAs == 'polygon') {
                    obj = new fabric.Polygon(getCoordinatesBySplit(attributes.coordinates), {});
                    obj.set('coordinates', attributes.coordinates);
                } else if (typeof attributes.drawAs != 'undefined' && attributes.drawAs == 'polyline') {
                    obj = new fabric.Polyline(getCoordinatesBySplit(attributes.coordinates), {});
                    obj.set('coordinates', attributes.coordinates);
                } else if (typeof attributes.drawAs != 'undefined' && attributes.drawAs == 'rect') {
                    obj = new fabric.Rect({});
                    
                    var widthPercent = (typeof attributes.widthPercent == 'undefined' ? (attributes.width / texelObject.getAttribute('canvasWidth')) * texelObject.getAttribute('canvasScaleX') * 100 : attributes.widthPercent);
                    var heightPercent = (typeof attributes.heightPercent == 'undefined' ? (attributes.height / texelObject.getAttribute('canvasHeight')) * texelObject.getAttribute('canvasScaleY') * 100 : attributes.heightPercent);

                    obj.set('widthPercent', widthPercent);
                    obj.set('heightPercent', heightPercent);
                    obj.set('width', parseFloat(attributes.width));
                    obj.set('height', parseFloat(attributes.height));
                    obj.set('coordinates', attributes.coordinates);
                } else if (typeof attributes.drawAs != 'undefined' && attributes.drawAs == 'image') {
                    $('.canvas-container').append(
                        $('<div />').attr('bns-texel_temp_image_container', '').css({
                            'position': 'absolute',
                            'left': '-99999px',
                            'top': '0px',
                            'width': '10000px',
                            'height': '10000px'
                        }).append(
                            $('<img />').attr({
                                'bns-texel_temp_image': '',
                                'src': attributes.base64.replace("&#x3b;", ";").replace(/&#x2b;/g, "+")
                            }).css({
                                'width': 'auto',
                                'height': 'auto'
                            })
                        )
                    );

                    obj = new fabric.Image($('[bns-texel_temp_image]')[0], {
                        'width': parseInt($('[bns-texel_temp_image]').css('width')),
                        'height': parseInt($('[bns-texel_temp_image]').css('height'))
                    });

                    $('.canvas-container').find('[bns-texel_temp_image_container]').remove();

                    scaleX = (typeof attributes.scaleX !== "undefined" ? attributes.scaleX : scaleX);
                    scaleY = (typeof attributes.scaleY !== "undefined" ? attributes.scaleY : scaleY);

                    obj.set('base64', attributes.base64.replace("&#x3b;", ";"));
                    obj.set('width', parseFloat(attributes.width));
                    obj.set('height', parseFloat(attributes.height));
                    strokeWidth = 0;
                }

                if (typeof obj != 'undefined') {
                    if (typeof attributes.strokeDashArray != 'undefined') {
                        obj.set('strokeDashArray', attributes.strokeDashArray);
                    }
                    
                    obj.set('left', left * texelObject.getAttribute('canvasScaleX'));
                    obj.set('top', top * texelObject.getAttribute('canvasScaleY'));
                    obj.set('scaleX', texelObject.getAttribute('canvasScaleX') * scaleX);
                    obj.set('scaleY', texelObject.getAttribute('canvasScaleY') * scaleY);
                    obj.set('stroke', stroke);
                    obj.set('strokeWidth', strokeWidth);
                    obj.set('hoverCursor', 'default');
                    obj.set('selectable', false);
                    obj.set('drawAs', attributes.drawAs);
                    obj.set('fill', (fill == 'dynamic' ? '#' + texelObject.getAttribute('backgroundColor') : fill));
                    obj.set('originalFill', fill);

                    texelObject.setAttribute('objectCounter', texelObject.getAttribute('objectCounter') + 1);
                    texelObject._addDesignerObject(canvas, obj, type, 'keyline_' + texelObject.getAttribute('objectCounter'), ignoredEvents);
                }
            } else if (type == 'textBox' || type == 'textBoxAddressing') {
                if (typeof attributes.fill != 'undefined') {
                    attributes.fill = attributes.fill.replace('#', '');
                    if (attributes.fill.length == 2) {
                        attributes.fill += attributes.fill + attributes.fill;
                    }
                    attributes.fill = '#' + attributes.fill.toUpperCase();
                }

                var backgroundColor = '#' + texelObject.getAttribute('backgroundColor');
                var fill = (typeof attributes.fill != 'undefined' ? attributes.fill : (texelObject._isColorAllowed('#000000', backgroundColor) ? '#000000' : '#FFFFFF'));

                if (texelObject.allowedInkColors[backgroundColor] != 'undefined' && $.inArray(fill, texelObject.allowedInkColors[backgroundColor]) > -1) {

                } else if (typeof texelObject.allowedInkColors[backgroundColor] != 'undefined' && $.inArray(fill, texelObject.allowedInkColors[backgroundColor]) == -1) {
                    fill = texelObject.allowedInkColors[backgroundColor][0];
                }


                var objectName = 'textBox';
                texelObject.setAttribute('objectCounter', texelObject.getAttribute('objectCounter') + 1);
                var text = (typeof attributes.text != 'undefined' ? attributes.text : 'Enter your text here');
                var addressingData = [];
                var currentAddressingIndex = (typeof attributes.currentAddressingIndex != 'undefined' ? attributes.currentAddressingIndex : 0);
                var fontFamily = (typeof attributes.fontFamily != 'undefined' ? attributes.fontFamily : 'Times New Roman');
                var fontSize = (typeof attributes.fontSize != 'undefined' ? attributes.fontSize : (texelObject.getAttribute('canvasScaleX') < 1 ? Math.ceil(12 / texelObject.getAttribute('canvasScaleX')) : 12));
                var textAlign = (typeof attributes.textAlign != 'undefined' ? attributes.textAlign : 'left');
                var lineHeight = (typeof attributes.lineHeight != 'undefined' ? attributes.lineHeight : 1);
                var charSpacing = (typeof attributes.charSpacing != 'undefined' ? attributes.charSpacing : 0);
                var scaleX = (typeof attributes.scaleX != 'undefined' ? attributes.scaleX : 1) * texelObject.getAttribute('canvasScaleX');
                var scaleY = (typeof attributes.scaleY != 'undefined' ? attributes.scaleY : 1) * texelObject.getAttribute('canvasScaleY');
                var width = (typeof attributes.widthPercent != 'undefined' ? (attributes.widthPercent / 100) : .50) * (texelObject.getAttribute('canvasWidth') / texelObject.getAttribute('canvasScaleX'));
                var left = (typeof attributes.xPercent != 'undefined' ? (attributes.xPercent / 100) * texelObject.getAttribute('canvasWidth') : (texelObject.getAttribute('canvasWidth') / 2 - ((width * ((texelObject.getAttribute('canvasWidth') / 2) / width)) / 2)));
                var top = (typeof attributes.yPercent != 'undefined' ? (attributes.yPercent / 100) * texelObject.getAttribute('canvasHeight') : (texelObject.getAttribute('canvasHeight') / 2 - (((fontSize * lineHeight) / 2) * scaleY)));
                var angle = (typeof attributes.angle != 'undefined' ? attributes.angle : 0);
                var hasRotatingPoint = (typeof attributes.hasRotatingPoint != 'undefined' ? attributes.hasRotatingPoint : false);
                var lockScalingY = (typeof attributes.lockScalingY != 'undefined' ? attributes.lockScalingY : true);
                var editable = (typeof attributes.editable != 'undefined' ? attributes.editable : false);
                var xPercent = (left / texelObject.getAttribute('canvasWidth')) * 100;
                var yPercent = (top / texelObject.getAttribute('canvasHeight')) * 100;
                var widthPercent = (width / texelObject.getAttribute('canvasWidth')) * texelObject.getAttribute('canvasScaleX') * 100;

                if (type == 'textBoxAddressing') {
                    texelObject.setAttribute('isVariable', true);

                    objectName = 'textBoxAddressing';

                    if (typeof attributes.addressingData == 'object' && attributes.addressingData.length > 0) {
                        addressingData = attributes.addressingData;
                    } else {
                        addressingData.push(['Name Line 1', 'Name Line 2', 'Addressing Line 1', 'Addressing Line 2', 'City', 'State', 'Zip', 'Country']);
                    }

                    text = texelObject._organizeAddressingText(addressingData[currentAddressingIndex]);
                    textAlign = 'center';

                    if (texelObject.designerContainer.length > 0 && texelObject.designerContainer.find('.canvas-container [bns-texel_tool="addressRotator"]').length == 0 && (typeof ignoredEvents.loadObjectTools == 'undefined' || !ignoredEvents.loadObjectTools)) {
                        texelObject.designerContainer.find('.canvas-container').append(
                            $('<div />').attr({
                                'bns-texel_tool': 'addressRotator',
                                'bns-location': texelObject.getAttribute('currentLocation'),
                                'bns-index': zIndex
                            }).css('opactiy', 0).addClass('addressRotator').append(
                                $('<div />').css({
                                    'border': '1px solid ' + fill,
                                    'color': fill
                                }).addClass('addPrev').html('< Previous').on('click', function() {
                                    for (var i = 0; i < texelObject.canvas.getObjects().length; i++) {
                                        var obj = texelObject.canvas.getObjects()[i];
                                        if (obj.type == 'textBoxAddressing') {
                                            if (typeof obj.addressingData[obj.currentAddressingIndex - 1] != 'undefined') {
                                                obj.set('currentAddressingIndex', obj.currentAddressingIndex - 1);
                                                obj.text = texelObject._organizeAddressingText(obj.addressingData[obj.currentAddressingIndex]);
                                            }

                                            texelObject.saveState();
                                            texelObject.canvas.renderAll();
                                        }
                                    }
                                })
                            ).append(
                                $('<div />').css({
                                    'border': '1px solid ' + fill,
                                    'color': fill
                                }).addClass('addNext').html('Next >').on('click', function() {
                                    for (var i = 0; i < texelObject.canvas.getObjects().length; i++) {
                                        var obj = texelObject.canvas.getObjects()[i];

                                        if (obj.type == 'textBoxAddressing') {
                                            if (typeof obj.addressingData[obj.currentAddressingIndex + 1] != 'undefined') {
                                                obj.set('currentAddressingIndex', obj.currentAddressingIndex + 1);
                                                obj.text = texelObject._organizeAddressingText(obj.addressingData[obj.currentAddressingIndex]);
                                            }

                                            texelObject.saveState();
                                            texelObject.canvas.renderAll();
                                        }
                                    }
                                })
                            )
                        );
                    } else {
                        texelObject.designerContainer.find('.canvas-container [bns-texel_tool="addressRotator"] .addPrev').css({
                            'border': '1px solid ' + fill,
                            'color': fill
                        });

                        texelObject.designerContainer.find('.canvas-container [bns-texel_tool="addressRotator"] .addNext').css({
                            'border': '1px solid ' + fill,
                            'color': fill
                        });
                    }
                }

                var obj = new fabric.Textbox(text, {
                    'fontFamily': fontFamily,
                    'fontSize': fontSize,
                    'fill': fill,
                    'textAlign': textAlign,
                    'lineHeight': lineHeight,
                    'charSpacing': charSpacing,
                    'scaleX': scaleX,
                    'scaleY': scaleY,
                    'width': width,
                    'left': left,
                    'top': top,
                    'angle': angle,
                    'hasRotatingPoint': hasRotatingPoint,
                    'lockScalingY': lockScalingY,
                    'editable': editable,
                    'xPercent': xPercent,
                    'yPercent': yPercent,
                    'widthPercent': widthPercent,
                    'zIndex': zIndex,
                    'addressingData': addressingData,
                    'currentAddressingIndex': currentAddressingIndex
                });

                // Controls for resizing the TextBox
                obj.setControlsVisibility({
                    'tl': false,
                    'mt': false,
                    'tr': false,
                    'bl': false,
                    'mb': false,
                    'br': false,
                    //'mr': false,
                    //'ml': false
                });

                texelObject._addDesignerObject(canvas, obj, type, objectName + '_' + texelObject.getAttribute('objectCounter'), ignoredEvents);
            } else if (type == 'image') {
                var index = (typeof attributes.index != 'undefined' ? attributes.index : 0);

                var src = texelObject.getAttribute('userImages')[index].base64;
                var filePath = texelObject.getAttribute('userImages')[index].filePath;

                $('.canvas-container').append(
                    $('<img />').attr({
                        'bns-texel_temp_image': '',
                        'src': src
                    }).css({
                        'position': 'absolute',
                        'left': '-99999px',
                        'top': '0px',
                        'width': 'auto',
                        'height': 'auto'
                    })
                );

                var scaleX = (typeof attributes.scaleX != 'undefined' ? attributes.scaleX : 1);
                var scaleY = (typeof attributes.scaleY != 'undefined' ? attributes.scaleY : 1);
                var angle = (typeof attributes.angle != 'undefined' ? attributes.angle : 0);
                var left = 0;
                var top = 0;
                var xPercent = (typeof attributes.xPercent != 'undefined' ? attributes.xPercent : 0);
                var yPercent = (typeof attributes.yPercent != 'undefined' ? attributes.yPercent : 0);

                var obj = new fabric.Image($('[bns-texel_temp_image]')[0], {
                    'width': parseInt($('[bns-texel_temp_image]').css('width')),
                    'height': parseInt($('[bns-texel_temp_image]').css('height'))
                });

                $('.canvas-container').find('[bns-texel_temp_image]').remove();

                if (obj.width * scaleX * texelObject.getAttribute('canvasScaleX') > texelObject.getAttribute('canvasWidth') && obj.width / texelObject.getAttribute('canvasWidth') > obj.height / texelObject.getAttribute('canvasHeight')) {
                    scaleX *= (((texelObject.getAttribute('canvasWidth') / 2) / obj.width));
                    scaleY *= (((texelObject.getAttribute('canvasWidth') / 2) / obj.width));
                    left = texelObject.getAttribute('canvasWidth') / 2 - ((obj.width * ((texelObject.getAttribute('canvasWidth') / 2) / obj.width)) / 2);
                    top = texelObject.getAttribute('canvasHeight') / 2 - ((obj.height * ((texelObject.getAttribute('canvasWidth') / 2) / obj.width)) / 2);
                    xPercent = (left / texelObject.getAttribute('canvasWidth')) * 100;
                    yPercent = (top / texelObject.getAttribute('canvasHeight')) * 100;
                } else if (obj.height * scaleY * texelObject.getAttribute('canvasScaleY') > texelObject.getAttribute('canvasHeight') && obj.height / texelObject.getAttribute('canvasHeight') > obj.width / texelObject.getAttribute('canvasWidth')) {
                    scaleX *= ((texelObject.getAttribute('canvasHeight') / 2) / obj.height);
                    scaleY *= ((texelObject.getAttribute('canvasHeight') / 2) / obj.height);
                    left = texelObject.getAttribute('canvasWidth') / 2 - ((obj.width * ((texelObject.getAttribute('canvasHeight') / 2) / obj.height)) / 2);
                    top = texelObject.getAttribute('canvasHeight') / 2 - ((obj.height * ((texelObject.getAttribute('canvasHeight') / 2) / obj.height)) / 2);
                    xPercent = (left / texelObject.getAttribute('canvasWidth')) * 100;
                    yPercent = (top / texelObject.getAttribute('canvasHeight')) * 100;
                } else {
                    scaleX *= texelObject.getAttribute('canvasScaleX');
                    scaleY *= texelObject.getAttribute('canvasScaleY');
                    left = (typeof xPercent != 'undefined' ? (xPercent / 100) : .10) * texelObject.getAttribute('canvasWidth');
                    top = (typeof yPercent != 'undefined' ? (yPercent / 100) : .10) * texelObject.getAttribute('canvasHeight');
                    xPercent = (left / texelObject.getAttribute('canvasWidth')) * 100;
                    yPercent = (top / texelObject.getAttribute('canvasHeight')) * 100;
                }

                obj.set('scaleX', scaleX);
                obj.set('scaleY', scaleY);
                obj.set('angle', angle);
                obj.set('left', left);
                obj.set('top', top);
                obj.set('xPercent', xPercent);
                obj.set('yPercent', yPercent);
                obj.set('src', src);
                obj.set('zIndex', zIndex);
                obj.set('index', index);
                obj.set('hasRotatingPoint', false);
                obj.set('filePath', filePath);

                texelObject.setAttribute('objectCounter', texelObject.getAttribute('objectCounter') + 1);
                texelObject._addDesignerObject(canvas, obj, type, 'image_' + texelObject.getAttribute('objectCounter'), ignoredEvents);
            }
        }
    }

    _loadObjectTools(obj) {
        var texelObject = this;

        if (obj != null && typeof obj != 'unfined' && typeof obj.type != 'undefined') {
            texelObject.removeObjectTools();

            var objectToolBox = '';

            if (obj.type == 'textBox' || obj.type == 'textBoxAddressing') {
                objectToolBox = '<div id="texelToolBox" class="texelPopupContainer" data-object_name="' + obj.name + '">' +
                    '<div bns-texel_remove_object="' + obj.name + '" class="texelDeleteButton">Remove <img src="/html/texel/img/delete.png" /></div><div bns-close_button class="texelCloseButton">X</div>' +
                    '<div class="texelLeftPointer textAreaBackground"></div>';

                // Text
                if (obj.type == 'textBox') {
                    objectToolBox += '<textarea class="texelTextArea" bns-texel_text="texel_text" data-name="text" value="" placeholder="Begin by typing here...">' + obj.text + '</textarea>'
                }
                objectToolBox += '<div class="texelPopupRow">' +
                    '<div>' +
                    '<div class="texelSelect" bns-texel_select="texel_fontFamily" data-value="' + obj.fontFamily + '">' + obj.fontFamily + '</div>' +
                    '<ul id="texel_fontFamily" bns-texel_select_popup class="texelSelectListPopup jqs-scrollable">';

                // Font Family
                for (var key in texelObject.fonts) {
                    objectToolBox += '<li bns-texel_option="texel_fontFamily" class="texelSelectListOption ' + (obj.fontFamily == key ? 'texelSelectListSelected' : '') + '" data-name="fontFamily" data-value="' + key + '" data-selected="false" style="color: #000000;' + texelObject.fonts[key].style + '">' + key + '</li>';
                }

                objectToolBox += '</ul>' +
                    '<div class="toolText">Font</div>' +
                    '</div>' +
                    '<div>' +
                    '<div class="texelSelect" bns-texel_select="texel_inkColor" data-value="' + obj.fill +'">' +
                    '<div class="selectedColor" style="background-color: ' + obj.fill +';"></div>' +
                    '</div>' +
                    '<ul id="texel_inkColor" bns-texel_select_popup class="texelSelectListPopup jqs-scrollable">';

                // Ink Colour
                for (var i = 0; i < texelObject.inkColors.length; i++) {
                    
                    if ((typeof texelObject.allowedInkColors[obj.fill] != 'undefined' && $.inArray(texelObject.inkColors[i].hex, texelObject.allowedInkColors[obj.fill]) > -1) || typeof texelObject.allowedInkColors[obj.fill] == 'undefined') {
                        objectToolBox += '<li bns-texel_option="texel_inkColor" class="texelSelectListOption ' + (obj.fill == texelObject.inkColors[i].hex ? 'texelSelectListSelected' : '') + '" style="background-color: ' + texelObject.inkColors[i].hex + '; color: ' + texelObject.inkColors[i].knockout + ';" data-name="fill" data-value="' + texelObject.inkColors[i].hex + '">' + texelObject.inkColors[i].name + '</li>';
                    }
                }

                objectToolBox += '</ul>' +
                    '<div class="toolText">Ink Color</div>' +
                    '</div>' +
                    '<div>' +
                    '<img class="textSize" src="/html/texel/img/textSize.png" />' +
                    '<div class="texelSelect" bns-texel_select="texel_fontSize" data-value="' + obj.fontSize + '">' + obj.fontSize + '</div>' +
                    '<ul id="texel_fontSize" bns-texel_select_popup class="texelSelectListPopup jqs-scrollable">';

                // Font Size
                for (var i = 8; i <= 31; i++) {
                    objectToolBox += '<li bns-texel_option="texel_fontSize" class="texelSelectListOption ' + (obj.fontSize == i ? 'texelSelectListSelected' : '') + '" data-name="fontSize" data-value="' + i + '">' + i + '</li>';
                }

                objectToolBox += '</ul>' +
                    '<div class="toolText">Font Size</div>' +
                    '</div>' +
                    '</div>' +
                    '<p bns-metallic_ink_note style="font-size: 12px; line-height: 14px; padding: 0px 10px;' + (obj.fill == '#C0C0C0' || obj.fill == '#937851' ? ' display: block;' : ' display: none;') + '"><strong style="color: #ff0000;">*Note:</strong> Metallic ink can only be printed on quantities over 500</p>' +
                    '<div class="texelPopupRow">' +
                    '<div>' +
                    '<img class="textSpacing" src="/html/texel/img/textSpacing.png" />' +
                    '<div class="texelSelect" bns-texel_select="texel_charSpacing" data-value="' + obj.charSpacing + '">' + obj.charSpacing + '</div>' +
                    '<ul id="texel_charSpacing" bns-texel_select_popup class="texelSelectListPopup jqs-scrollable">';

                // Char Spacing
                for (var i = 0; i <= 219; i++) {
                    objectToolBox += '<li bns-texel_option="texel_charSpacing" class="texelSelectListOption ' + (obj.charSpacing == i ? 'texelSelectListSelected' : '') + '" data-name="charSpacing" data-value="' + i + '">' + i + '</li>';
                }

                objectToolBox += '</ul>' +
                    '<div class="toolText">Text Spacing</div>' +
                    '</div>' +
                    '<div>' +
                    '<img class="lineSpacing" src="/html/texel/img/lineSpacing.png" />' +
                    '<div class="texelSelect" bns-texel_select="texel_lineHeight" data-value="' + Math.floor(obj.lineHeight * obj.fontSize) + '">' + Math.floor(obj.lineHeight * obj.fontSize) + '</div>' +
                    '<ul id="texel_lineHeight" bns-texel_select_popup class="texelSelectListPopup jqs-scrollable">';

                // Line Height
                for (var i = 1; i <= 33; i++) {
                    objectToolBox += '<li bns-texel_option="texel_lineHeight" class="texelSelectListOption ' + (Math.floor(obj.lineHeight * obj.fontSize) == i ? 'texelSelectListSelected' : '') + '" data-name="lineHeight" data-value="' + i + '">' + i + '</li>';
                }

                objectToolBox += '</ul>' +
                    '<div class="toolText">Line Height</div>' +
                    '</div>' +
                    '<div>' +
                    '<div class="texelRadio" bns-texel_radio="texel_textAlign" data-name="textAlign"  data-value="left" data-selected="' + (obj.textAlign == 'left' ? 'true' : 'false') + '">' +
                    '<div class="tlb1-fontAlignment"></div>' +
                    '<div class="tlb2-fontAlignment"></div>' +
                    '<div class="tlb3-fontAlignment"></div>' +
                    '<div class="tlb4-fontAlignment"></div>' +
                    '</div>' +
                    '<div class="texelRadio" bns-texel_radio="texel_textAlign" data-name="textAlign" data-value="center" data-selected="' + (obj.textAlign == 'center' ? 'true' : 'false') + '">' +
                    '<div class="tmb1-fontAlignment"></div>' +
                    '<div class="tmb2-fontAlignment"></div>' +
                    '<div class="tmb3-fontAlignment"></div>' +
                    '<div class="tmb4-fontAlignment"></div>' +
                    '</div>' +
                    '<div class="texelRadio" bns-texel_radio="texel_textAlign" data-name="textAlign" data-value="right" data-selected="' + (obj.textAlign == 'right' ? 'true' : 'false') + '">' +
                    '<div class="trb1-fontAlignment"></div>' +
                    '<div class="trb2-fontAlignment"></div>' +
                    '<div class="trb3-fontAlignment"></div>' +
                    '<div class="trb4-fontAlignment"></div>' +
                    '</div>' +
                    '<div class="toolText">Font Alignment</div>' +
                    '</div>' +
                    '</div>' +
                    '<div class="texelPopupRow">' +
                    /*
                        '<div>' +
                            '<div class="texelRadio" bns-texel_action="texel_rotate" data-name="angle" data-value="left">' +
                                '<img src="/html/texel/img/rotateLeft.png" />' +
                            '</div>' +
                            '<div class="texelRadio "bns-texel_action="texel_rotate" data-name="angle" data-value="right">' +
                                '<img src="/html/texel/img/rotateRight.png" />' +
                            '</div>' +
                            '<div class="toolText">Rotate</div>' +
                        '</div>' +*/
                    '<div>' +
                    '<div class="texelRadio" bns-texel_action="texel_zIndex" data-name="zIndex" data-value="down">' +
                    '<img src="/html/texel/img/layerDown.png" />' +
                    '</div>' +
                    '<div class="texelRadio" bns-texel_action="texel_zIndex" data-name="zIndex" data-value="up">' +
                    '<img src="/html/texel/img/layerUp.png" />' +
                    '</div>' +
                    '<div class="toolText">Layer</div>' +
                    '</div>' +
                    '<div>' +
                    '<div class="texelRadio nudge" bns-texel_action="texel_nudge" data-name="left" data-value="left">' +
                    '<img src="/html/texel/img/nudgeLeft.png" />' +
                    '</div>' +
                    '<div class="texelRadio nudge" bns-texel_action="texel_nudge" data-name="top" data-value="up">' +
                    '<img src="/html/texel/img/nudgeUp.png" />' +
                    '</div>' +
                    '<div class="texelRadio nudge" bns-texel_action="texel_nudge" data-name="left" data-value="right">' +
                    '<img src="/html/texel/img/nudgeRight.png" />' +
                    '</div>' +
                    '<div class="texelRadio nudge" bns-texel_action="texel_nudge" data-name="top" data-value="down" >' +
                    '<img src="/html/texel/img/nudgeDown.png" />' +
                    '</div>' +
                    '<div class="toolText">Nudge</div>' +
                    '</div>' +
                    '</div>';

                if (obj.type == 'textBoxAddressing') {
                    objectToolBox += '<div class="texelPopupRow">' +
                        '<div bns-edit_address class="editAddress texelbtn">Edit Address</div>' +
                        '</div>';

                    $('[bns-texel_tool="addressRotator"]').removeClass('hidden');
                }

                objectToolBox += '</div>';
            } else if (obj.type == 'image') {
                objectToolBox = '' +
                    '<div id="texelToolBox" class="texelPopupContainer" data-object_name="' + obj.name + '">' +
                        '<div bns-texel_remove_object="' + obj.name + '" class="texelDeleteButton">Remove <img src="/html/texel/img/delete.png" /></div><div bns-close_button class="texelCloseButton">X</div>' +
                        '<div class="texelLeftPointer textAreaBackground"></div>' +
                        '<div class="texelPopupRow noTopBorder">' +
                            /*
                            + '<div>'
                                + '<div class="texelRadio" data-type="env-action" data-target="imageRotate" data-value="left">'
                                    + '<img src="/html/texel/img/rotateLeft.png" />'
                                + '</div>'
                                + '<div class="texelRadio" data-type="env-action" data-target="imageRotate" data-value="right">'
                                    + '<img src="/html/texel/img/rotateRight.png" />'
                                + '</div>'
                                + '<div class="toolText">Rotate</div>'
                            + '</div>'*/
                            '<div>' +
                                '<div class="texelRadio" bns-texel_action="texel_zIndex" data-name="zIndex" data-value="down">' +
                                    '<img src="/html/texel/img/layerDown.png" />' +
                                '</div>' +
                                '<div class="texelRadio" bns-texel_action="texel_zIndex" data-name="zIndex" data-value="up">' +
                                    '<img src="/html/texel/img/layerUp.png" />' +
                                '</div>' +
                                '<div class="toolText">Layer</div>' +
                            '</div>' +
                            '<div>' +
                                '<div class="texelRadio nudge" bns-texel_action="texel_nudge" data-name="left" data-value="left">' +
                                    '<img src="/html/texel/img/nudgeLeft.png" />' +
                                '</div>' +
                                '<div class="texelRadio nudge" bns-texel_action="texel_nudge" data-name="top" data-value="up">' +
                                    '<img src="/html/texel/img/nudgeUp.png" />' +
                                '</div>' +
                                '<div class="texelRadio nudge" bns-texel_action="texel_nudge" data-name="left" data-value="right">' +
                                    '<img src="/html/texel/img/nudgeRight.png" />' +
                                '</div>' +
                                '<div class="texelRadio nudge" bns-texel_action="texel_nudge" data-name="top" data-value="down" >' +
                                    '<img src="/html/texel/img/nudgeDown.png" />' +
                                '</div>' +
                                '<div class="toolText">Nudge</div>' +
                            '</div>' +/*
                            '<div>' +
                                '<div class="texelRadio" data-type="env-action" data-target="imageZoom" data-value="in">' +
                                    '<img src="/html/texel/img/zoomIn.png" />' +
                                '</div>' +
                                '<div class="texelRadio" data-type="env-action" data-target="imageZoom" data-value="out">' +
                                    '<img src="/html/texel/img/zoomOut.png" />' +
                                '</div>' +
                                '<div class="toolText">Zoom</div>' +
                            '</div>' +
                            '<div>' +
                                '<div class="texelRadio" data-type="env-action" data-target="imageCrop" data-active="false">' +
                                    '<img src="/html/texel/img/crop.png" />' +
                                '</div>' +
                                '<div class="toolText">Crop</div>' +
                            '</div>' +*/
                        '</div>' +
                        '<div class="texelPopupRow uploadBlock">' +
                            '<h4>Upload Your Own Images:</h4>' +
                            '<p>' +
                                'Supported Files: AI, BMP, EPS, GIF, JPEG, PDF, PNG' +
                                '<br />20MB File Size Maximum.' +
                            '</p>' +
                        '</div>' + 
                        '<div class="texelPopupRow noTopBorder">' +
                            '<div class="texelButtonOrange texelImageUpload">Upload Image</div>' +
                        '</div>' +
                        '<div class="texelPopupRow uploadBlock">' +
                            '<h4>Uploading Issues:</h4>' +
                            '<p>' + 
                                'If you have an issue uploading your image, please email the image to PREPRESS@ENVELOPES.COM.  You will receive a proof to approve before your order is printed.' +
                            '</p>' +
                            '<h4 style="margin-top: 5px;">White Ink Pricing:</h4>' +
                            '<p>' +
                                'If your image contains White Ink, your order will be subject to an upcharge.  Our prepress department will reach out to you regarding the price change.' + 
                            '</p>' +
                            (typeof navigator.sayswho == 'function' && (navigator.sayswho()).match(/(?:msie|ie \d+)/i) != null ? '<p>If you are experiencing difficulty uploading your logo on Internet Explorer, please switch to either FireFox or Chrome.</p>' : '') +
                        '</div>' +
                        '<div class="texelPopupRow texelNoPadding">' +
                            '<div class="texelUploadErrorBox jqs-texelUploadErrorBox texelHidden"></div>' +
                        '</div>' +
                        '<div class="texelPopupRow">' +
                            '<div class="uploadContainer">' +
                                '<div class="uploadGrid"></div>' +
                            '</div>' +
                        '</div>' +
                        '<form class="texelImgUpl" id="texelImgUpl" action="/' + websiteId + '/control/uploadUGCFile" method="post" enctype="multipart/form-data">' +
                            '<input type="file" name="ugcFile" id="ugcFileBut">' +
                        '</form>' +
                    '</div>';
            }

            if (objectToolBox != '') {
                $('.canvas-container').append(objectToolBox);

                texelObject._loadImageLibrary();

                $('.texelPopupContainer').find('.texelImageUpload').on('click', function() {
                    $('#texelToolBox').find('#ugcFileBut').trigger('click');
                });

                $('#texelToolBox').find('#ugcFileBut').on('change', function() {
                    $('#texelImgUpl').submit();
                });

                $('#texelToolBox #texelImgUpl').on('submit', function(e) {
                    e.preventDefault();
                    var form = e.target;
                    var formData = new FormData(form);

                    $('.jqs-texelUploadErrorBox').html('');
                    $('.jqs-texelUploadErrorBox').removeClass('texelHidden').addClass('texelHidden');

                    //add the waiting upload image
                    $('.texelPopupContainer .uploadContainer').texelLoader(true);

                    //send request
                    $.ajax({
                        url: form.action,
                        method: form.method,
                        processData: false,
                        contentType: false,
                        data: formData,
                        dataType: 'json'
                    }).done(function(data) {
                        $('.texelPopupContainer').find('#ugcFileBut').val('');
                        if(data.success && typeof data.paths != 'undefined') {
                            for(var i = 0; i < data.paths.length; i++) {
                                if(data.paths[i].path.indexOf('fxg') != -1) {
                                    texelObject.getAttribute('userImages').push({'src': '//actionenvelopew2p.scene7.com/is/agm/' + data.paths[i].path + '?fmt=png-alpha', 'filePath': data.filePath});
                                } else {
                                    texelObject.getAttribute('userImages').push({'src': '//actionenvelopew2p.scene7.com/is/image/' + data.paths[i].path + '?fmt=png-alpha', 'filePath': data.filePath});
                                }

                                texelObject.loadUserImages(texelObject.getAttribute('userImages').length - 1);
                                //texelObject._paintUserImage(texelObject.getAttribute('userImages')[texelObject.getAttribute('userImages').length - 1]);
                            }
                        }
                        else {
                            $('.jqs-texelUploadErrorBox').html('There was an issue uploading the image.');
                            $('.jqs-texelUploadErrorBox').removeClass('texelHidden');
                        }
                        $('.texelPopupContainer .uploadContainer').texelLoader(false);
                    }).fail(function() {
                        $('.jqs-texelUploadErrorBox').html('There was an issue uploading the image.');
                        $('.jqs-texelUploadErrorBox').removeClass('texelHidden');
                        $('.texelPopupContainer .uploadContainer').texelLoader(false);
                    });
                });

                texelObject._setObjectToolsStyling(obj);

                texelObject._bindObjectToolboxClickEvents(obj);
            }
        }
    }

    _bindObjectToolboxClickEvents(obj) {
        var texelObject = this;

        // Text
        texelObject.designerContainer.find('#texelToolBox[data-object_name="' + obj.name + '"] [bns-texel_text]').on('input.texelText', function(e) {
            var selectedOption = $(this);
    
            var attributes = {};
            attributes[selectedOption.attr('data-name')] = selectedOption.val();
    
            texelObject._updateCanvasObject(obj, attributes);
        });
    
        // Select
        texelObject.designerContainer.find('#texelToolBox[data-object_name="' + obj.name + '"] [bns-texel_select]').on('click.texelSelectPopup', function(e) {
            e.stopPropagation();
            var selectedOption = $(this);
    
            texelObject.designerContainer.find('[bns-texel_select_popup]').css('display', 'none');
            texelObject.designerContainer.find('#' + selectedOption.attr('bns-texel_select')).css('display', 'block');
    
            texelObject.designerContainer.find('#texelToolBox').off('click.closeSelectPopup').on('click.closeSelectPopup', function(e) {
                if (!$(e.target).parents('[bns-texel_select_popup]').length > 0) {
                    texelObject.designerContainer.find('[bns-texel_select_popup]').css('display', 'none');
                    $(this).off('click.closeSelectPopup');
                }
            });
        });
    
        // Option
        texelObject.designerContainer.find('#texelToolBox[data-object_name="' + obj.name + '"] [bns-texel_option]').on('click.texelOptionSelection', function() {
            var selectedOption = $(this);

            selectedOption.siblings().removeClass('texelSelectListSelected');
            selectedOption.addClass('texelSelectListSelected');

            if (selectedOption.attr('bns-texel_option') == 'texel_inkColor') {
                $('[bns-texel_select="' + selectedOption.attr('bns-texel_option') + '"]').css('data-value', selectedOption.attr('data-value')).empty().append(
                    $('<div />').addClass('selectedColor').css('background-color', selectedOption.attr('data-value'))
                );

                $('[bns-metallic_ink_note]').css('display', 'none');

                if (selectedOption.attr('data-value') == '#C0C0C0' || selectedOption.attr('data-value') == '#937851') {
                    $('[bns-metallic_ink_note]').css('display', 'block');
                }
            } else {
                if (selectedOption.attr('bns-texel_option') == 'texel_fontSize') {
                    $('#texel_lineHeight [bns-texel_option]').removeClass('texelSelectListSelected');
                    $('#texel_lineHeight [bns-texel_option][data-value="' + selectedOption.attr('data-value') + '"]').addClass('texelSelectListSelected');
                    $('[bns-texel_select="texel_lineHeight"]').attr({
                        'data-value': selectedOption.attr('data-value')
                    }).html(selectedOption.attr('data-value'));
                }

                $('[bns-texel_select="' + selectedOption.attr('bns-texel_option') + '"]').attr({
                    'data-value': selectedOption.attr('data-value'),
                    'style': selectedOption.attr('style')
                }).html(selectedOption.html());
            }

            selectedOption.parents('[bns-texel_select_popup]').css('display', 'none');

            var attributes = {};
            attributes[selectedOption.attr('data-name')] = selectedOption.attr('data-value');

            texelObject._updateCanvasObject(obj, attributes);
        });
    
        // Radio
        texelObject.designerContainer.find('#texelToolBox[data-object_name="' + obj.name + '"] [bns-texel_radio]').on('click.texelRadio', function() {
            var selectedOption = $(this);
    
            texelObject.designerContainer.find('[bns-texel_radio="' + selectedOption.attr('bns-texel_radio') + '"]').attr('data-selected', 'false');
            selectedOption.attr('data-selected', 'true');
    
            var attributes = {};
            attributes[selectedOption.attr('data-name')] = selectedOption.attr('data-value');
    
            texelObject._updateCanvasObject(obj, attributes);
        });
    
        // Action
        texelObject.designerContainer.find('#texelToolBox[data-object_name="' + obj.name +'"] [bns-texel_action]').on('click.texelAction', function() {
            var selectedOption = $(this);
            var attributes = {};
    
            if (selectedOption.attr('bns-texel_action') == 'texel_rotate') {
                attributes[selectedOption.attr('data-name')] = (obj.angle + (selectedOption.attr('data-value') == 'right' ? 90 : -90)) % 360;
            } else if (selectedOption.attr('bns-texel_action') == 'texel_zIndex') {
                var currentObjectIndex = $.inArray(obj, texelObject.canvas.getObjects());
    
                if (selectedOption.attr('data-value') == 'up' && currentObjectIndex != texelObject.canvas.getObjects().length - 1) {
                    texelObject.canvas.moveTo(obj, currentObjectIndex + 1);
                } else if (selectedOption.attr('data-value') == 'down' && currentObjectIndex != 0 && texelObject.canvas.getObjects()[currentObjectIndex - 1].type != 'keyline') {
                    texelObject.canvas.moveTo(obj, currentObjectIndex - 1);
                }
            } else if (selectedOption.attr('bns-texel_action') == 'texel_nudge') {
                attributes[selectedOption.attr('data-name')] = obj[selectedOption.attr('data-name')] + (selectedOption.attr('data-value') == 'left' || selectedOption.attr('data-value') == 'up' ? -1 : 1);
            }
    
            texelObject._updateCanvasObject(obj, attributes);
        });

        // Edit Addressing
        texelObject.designerContainer.find('#texelToolBox [bns-edit_address]').off('click.editAddress').on('click.editAddress', function() {
            //productPage.showGrid('manual', (typeof obj.currentAddressingIndex != 'undefined' ? obj.currentAddressingIndex : 0));
        });
    
        // Close!
        texelObject.designerContainer.find('#texelToolBox').find('[bns-close_button]').on('click', function() {
            texelObject.removeObjectTools();
        });
    
        // Delete Object
        texelObject.designerContainer.find('[bns-texel_remove_object]').on('click', function() {
            var objectName = $(this).attr('bns-texel_remove_object');
    
            texelObject.canvas.remove(texelObject._getObjectByName(objectName));
            texelObject.saveState();
            texelObject.removeObjectTools();
        });
    }

    _updateCanvasObject(obj, attributes) {
        var texelObject = this;
        // Update canvas Object
        for (var attribute in attributes) {
            if (attribute == 'lineHeight') {
                obj.set(attribute, attributes[attribute] / obj.fontSize);
            } else if (attribute == 'fontSize') {
                obj.set('lineHeight', 1);
                obj.set(attribute, attributes[attribute]);
            } else {
                obj.set(attribute, attributes[attribute]);
            }
        }
        
        var timeout = 0;

        if (typeof attributes.text != 'undefined') {
            timeout = 500;
        }

        waitForFinalEvent(function() {
            texelObject.saveState();
            if (typeof attributes.src != 'undefined') {
                texelObject.loadState(texelObject.getAttribute('designerStateCurrentIndex'));
            } else {
                texelObject.canvas.renderAll();   
            }
        }, timeout, 'saveStateAfterObjectUpdate');
    }

    _setObjectToolsStyling(obj) {
        let texelObject = this;
        
        let leftPadding = 30;
        let topPadding = - 15;

        let top = obj.top + topPadding;
        let left = obj.left + (obj.width * obj.scaleX) + leftPadding;
        
        if (left + texelObject.designerContainer.find('#texelToolBox').outerWidth() > texelObject.designerContainer.find('#texelToolBox').parent().outerWidth() + texelObject.designerContainer.find('#texelToolBox').outerWidth() + leftPadding) {
            left = texelObject.designerContainer.find('#texelToolBox').parent().outerWidth() + leftPadding;
        }

        if (top < 0) {
            top = 0;
        } else if (top + texelObject.designerContainer.find('#texelToolBox').outerHeight() > texelObject.designerContainer.find('#texelToolBox').parent().outerHeight() - 54) {
            top = texelObject.designerContainer.find('#texelToolBox').parent().outerHeight() - texelObject.designerContainer.find('#texelToolBox').outerHeight() - 54;
        }

        texelObject.designerContainer.find('#texelToolBox').css({
            'top': top + 'px',
            'left': left + 'px'
        }); 
    }

    removeObjectTools() {
        let texelObject = this;
        texelObject.designerContainer.find('#texelToolBox').remove();
        texelObject.designerContainer.find('[bns-texel_tool="addressRotator"]').removeClass('hidden').addClass('hidden');
    }

    _getObjectByName(objectName) {
        var texelObject = this;
        var objectList = texelObject.canvas.getObjects();
    
        for (var x = 0; x < objectList.length; x++) {
            if (objectList[x].name == objectName) {
                return objectList[x];
            }
        }
    
        return undefined;
    }

    _removeObjectByName(objectName) {
        var texelObject = this;

        texelObject.canvas.getObjects().forEach(function(obj) {
            if (obj.name == objectName) {
                texelObject.canvas.remove(obj)
            }
        });
    }

    // Options currently uses "opt", "downloadFlag", and "showKeyline"
    generateArtworkPDF(orderItemInfo, options, callback) {
        // opt == 1 ? front side
        // opt == 2 ? back only
        // opt == 'undefined' both

        var contentPurposeEnumId = options.opt && options.opt == 1 ? 'OIACPRP_SC7_FRNT_PDF' : options.opt && options.opt == 2 ? 'OIACPRP_SC7_BACK_PDF' : 'OIACPRP_PDF';
        var texelObject = this;

        var activeDesign = texelObject.getAttribute('designerState')[texelObject.getAttribute('designerState').length - 1];

        // load JSPDF fonts
        for (var location in activeDesign.jsonData) {
            for (var i = 0; i < activeDesign.jsonData[location].length; i++) {
                var obj = activeDesign.jsonData[location][i];
                if ((obj.type == 'textBox' || obj.type == 'textBoxAddressing') && typeof texelObject.fonts[obj.fontFamily] == 'object' && typeof texelObject.fonts[obj.fontFamily].jsPDFFile != 'undefined') {
                    try {
                        $.ajax({
                            url: '/html/js/util/jsPDF/fonts/' + texelObject.fonts[obj.fontFamily].jsPDFFile,
                            dataType: 'script',
                            async: false
                        });
                    } catch (e) {
                        console.log('ERROR: ' + e);
                    }
                }
            }
        }
        
        var pdf = new jsPDF({
            orientation: (texelObject.getAttribute('productWidthPixels') >= texelObject.getAttribute('productHeightPixels') ? 'landscape' : 'portrait'), 
            unit: 'in',
            format: [texelObject.getAttribute('productWidthPixels'), texelObject.getAttribute('productHeightPixels')]
        });

        var imageData = activeDesign.imageData;

        texelObject.canvas.clear();

        for (var location in activeDesign.jsonData) {
            if((options.opt == 1 && location == 'back') || (options.opt == 2 && location == 'front')) {
                continue;
            }
            if(options.opt != 1 && options.opt != 2 && location == 'back') {
                pdf.addPage();
            }
            var addressingLength = 0;
            var currentAddressingIndex = 0;
            var totalPageOutputIfAddressing = 0;

            for (var i = 0; i < activeDesign.jsonData[location].length; i++) {
                texelObject.canvas.clear();
                var obj = activeDesign.jsonData[location][i];
                if (typeof obj.addressingData == 'object' && obj.addressingData.length > 0) {
                    addressingLength = obj.addressingData.length - 1;
                }
            }

            if (addressingLength > 0) {
                totalPageOutputIfAddressing = (typeof orderItemInfo.quantity != 'undefined' ? parseInt(orderItemInfo.quantity) - 1 : addressingLength);
            }

            while (currentAddressingIndex <= totalPageOutputIfAddressing) {
                if (currentAddressingIndex > 0) {
                    pdf.addPage();
                }

                if (typeof options.showKeyline == 'undefined' || options.showKeyline) {
                    for (var i = 0; i < activeDesign.jsonData[location].length; i++) {
                        var obj = activeDesign.jsonData[location][i];
                        if (obj.type == 'keyline') {
                            texelObject.createDesignerObject(texelObject.canvas, obj.type, obj, { 'showToolBox': true, 'saveState': true })
                        }
                    }

                    var imgData = texelObject.canvas.toDataURL();

                    pdf.addImage(imgData, 'PNG', 0, 0, texelObject.getAttribute('productWidthInches'), texelObject.getAttribute('productHeightInches'));
                }

                for (var i = 0; i < activeDesign.jsonData[location].length; i++) {
                    texelObject.canvas.clear();

                    var obj = activeDesign.jsonData[location][i];

                    if (obj.type == 'textBox' || (obj.type == 'textBoxAddressing' && currentAddressingIndex <= addressingLength)) {
                        var text = obj.text;

                        if (obj.type == 'textBoxAddressing') {
                            var textList = obj.addressingData[currentAddressingIndex];
                            text =
                                (typeof textList[0] != 'undefined' && textList[0] != '' ? textList[0] + '\n' : '') +
                                (typeof textList[1] != 'undefined' && textList[1] != '' ? textList[1] + '\n' : '') +
                                (typeof textList[2] != 'undefined' && textList[2] != '' ? textList[2] + '\n' : '') +
                                (typeof textList[3] != 'undefined' && textList[3] != '' ? textList[3] + '\n' : '') +
                                (typeof textList[4] != 'undefined' && textList[4] != '' ? textList[4] + ', ' : '') +
                                (typeof textList[5] != 'undefined' && textList[5] != '' ? textList[5] + ' ' : '') +
                                (typeof textList[6] != 'undefined' && textList[6] != '' ? textList[6]: '') +
                                (typeof (textList[4] != 'undefined' && textList[4] != '') || (typeof textList[5] != 'undefined' && textList[5] != '') || (typeof textList[6] != 'undefined' && textList[6] != '') ? '\n' : '') +
                                (typeof textList[7] != 'undefined' && textList[7] != '' ? textList[7] : '');
                        }
                        //pdf.setFont('times', 'normal');
                        if (typeof texelObject.fonts[obj.fontFamily] != 'undefined' && typeof texelObject.fonts[obj.fontFamily].jsPDFName != 'undefined' && typeof texelObject.fonts[obj.fontFamily].jsPDFStyle != 'undefined') {
                            pdf.setFont(texelObject.fonts[obj.fontFamily].jsPDFName, texelObject.fonts[obj.fontFamily].jsPDFStyle);
                        } else {
                            pdf.setFont('Times New Roman');
                        }
                        
                        pdf.setFontSize(obj.fontSize);
                        pdf.setTextColor(obj.fill);
                        pdf.setLineHeightFactor(obj.lineHeight);
                        var objectAlignmentAdjustment = 0;

                        if (obj.textAlign == 'right') {
                            objectAlignmentAdjustment = (obj.widthPercent / 100) * (texelObject.getAttribute('canvasWidth') / texelObject.getAttribute('canvasScaleX'))
                        } else if (obj.textAlign == 'center') {
                            objectAlignmentAdjustment = ((obj.widthPercent / 100) * (texelObject.getAttribute('canvasWidth') / texelObject.getAttribute('canvasScaleX'))) / 2
                        }

                        var x = (((((texelObject.getAttribute('productWidthPixels') / texelObject.getAttribute('canvasWidth')) * (texelObject.getAttribute('canvasWidth') * (obj.xPercent / 100)))) + objectAlignmentAdjustment) / texelObject.getAttribute('actualDPI'));

                        var y = ((((texelObject.getAttribute('productHeightPixels') / texelObject.getAttribute('canvasHeight')) * (texelObject.getAttribute('canvasHeight') * (obj.yPercent / 100)))) / texelObject.getAttribute('actualDPI'));

                        // need to review the placement based on left center right.  Seems to be centering text by x axis being middle if center.
                        text = pdf.splitTextToSize(text.replaceAll("\t", " "), obj.width / texelObject.getAttribute('actualDPI'));
                        pdf.text(x, y, text, {
                            'baseline': 'top',
                            'align': obj.textAlign
                        });
                    } else if (obj.type == 'image') {
                        texelObject.canvas.clear();
                        texelObject.createDesignerObject(texelObject.canvas, obj.type, obj, { 'showToolBox': true, 'saveState': true });
                        var imgData = texelObject.canvas.toDataURL();
                        pdf.addImage(imgData, 'PNG', 0, 0, texelObject.getAttribute('productWidthInches'), texelObject.getAttribute('productHeightInches'));
                    }

                }

                currentAddressingIndex++;
            }

        }

        let fileName = orderItemInfo.orderId + '_' + orderItemInfo.orderItemSeqId + (options.opt && options.opt == 1 ? '_FRONT' : options.opt && options.opt == 2 ? '_BACK' : '_PROOF') + ".pdf";
        if(options.downloadFlag) {
            pdf.save(fileName);
        } else {
            if(orderItemInfo.regenerateImage && options.opt == 0) {
                for(var side in imageData) {
                    if(imageData.hasOwnProperty(side)) {
                        texelObject.uploadFileToServer(imageData[side], orderItemInfo.orderId, orderItemInfo.orderItemSeqId, 'OIACPRP_FRONT', function(){
                            console.log(imageData[side]);
                        });
                    }
                }
            }
            texelObject.uploadFileToServer(btoa(pdf.output()), orderItemInfo.orderId, orderItemInfo.orderItemSeqId, contentPurposeEnumId, callback);
        }

        texelObject.loadState(texelObject.getAttribute('designerStateCurrentIndex'));
    }

    uploadFileToServer(base64ArtworkContent, orderId, orderItemSeqId, contentPurposeEnumId, callback) {
        let formData = new FormData();
        formData.append('orderId', orderId);
        formData.append('orderItemSeqId', orderItemSeqId);
        formData.append('contentPurposeEnumId', contentPurposeEnumId);
        formData.append('base64', base64ArtworkContent);
        $.ajax({
            url: '/admin/control/generateArtwork',
            type: "POST",
            cache: false,
            contentType: false,
            processData: false,
            data: formData})
            .done(function(data){
                if(callback) {
                    callback(JSON.parse(data));
                }
            });
    }

    regenerateArtworkFiles(orderItemInfo, callback) {
        var texelObject = this;
        var res = {};
        texelObject.generateArtworkPDF(orderItemInfo, {opt: 0, downloadFlag: false}, function(data) {
            if(data.success) {
                res['proof'] = data;
                texelObject.generateArtworkPDF(orderItemInfo, {opt: 1, downloadFlag: false, showKeyline: false}, function(data) {
                    if(data.success) {
                        res['front'] = data;
                        texelObject.generateArtworkPDF(orderItemInfo, {opt: 2, downloadFlag: false, showKeyline: false}, function(data) {
                            if (data.success) {
                                res['back'] = data;
                                if(callback) {
                                    callback(res);
                                }
                            } else {
                                alert('An error occurred while regenerating the updated back pdf');
                            }
                        });
                    } else {
                        alert('An error occurred while regenerating the updated front pdf');
                    }
                });
            } else {
                alert('An error occurred while regenerating the updated proof pdf');
            }
        });
    }
}

class TexelCanvas extends Texel {
    constructor(designerContainer, designData) {
        super(designerContainer, designData);
        let texelObject = this;

        texelObject.texelThread(function() {
            texelObject.designerContainer.find('[bns-help_button]').off('click.showHelp').on('click.showHelp', function() {
                texelObject.designerContainer.find('.jqs-texelHelpContainer').removeClass('hidden');

                if ($(this).attr('bns-help_button') == 'show') {
                    $(this).attr('bns-help_button', 'hide').find('[bns-help_text]').html('Hide Help <i class="fa fa-caret-up"></i>');
                    texelObject.designerContainer.find('.jqs-texelTemplateHelp').css('max-width', 'calc(100% - 20px)');
                } else {
                    $(this).attr('bns-help_button', 'show').find('[bns-help_text]').html('Show Help <i class="fa fa-caret-down"></i>');
                    texelObject.designerContainer.find('.jqs-texelTemplateHelp').css('max-width', '270px');
                    texelObject.designerContainer.find('.jqs-texelHelpContainer').addClass('hidden');
                }

                $(window).trigger('resize.texel');
            });
            
            var leftColumnDesigner;
            var rightColumnDesigner;

            if(texelObject.designerContainer.find('.envDesignerOutsideColumn.leftSide').length == 0) {
                leftColumnDesigner = $('<div />').addClass('envDesignerOutsideColumn leftSide').append(
                    $('<div />').addClass('envDesignerButtonContainer').append(
                        $('<div />').addClass('envDesignerButton').attr('id','designerAddText').on('click', function() {
                            texelObject.createDesignerObject(texelObject.canvas, 'textBox');
                        }).append(
                            $('<img />').attr('src','https://www.envelopes.com/html/texel/img/toolbox/addText.png')
                        ).append(
                            $('<p />').html('Text')
                        )
                    ).append(
                        $('<div />').addClass('envDesignerButton').attr('id','designerAddImage').on('click', function() {
                            texelObject.createDesignerObject(texelObject.canvas, 'image');
                        }).append(
                            $('<img />').attr('src','https://www.envelopes.com/html/texel/img/toolbox/addImage.png')
                        ).append(
                            $('<p />').html('Image')
                        )
                    ).append(
                        $('<div />').addClass('envDesignerButton').attr('id','designerAddAddressing').on('click.addAddressing', function() {
                            $('[data-target="addressing"][data-key="addresses"]').trigger('click');
                    }).append(
                            $('<img />').attr('src','https://www.envelopes.com/html/texel/img/toolbox/addAddressing.png')
                        ).append(
                            $('<p />').html('Addressing')
                        )
                    ).append(
                        $('<div />').addClass('envDesignerButton').attr('id','designerUndo').on('click', function() {
                            texelObject.loadState(texelObject.getAttribute('designerStateCurrentIndex') - 1);
                    }).append(
                            $('<img />').attr('src','https://www.envelopes.com/html/texel/img/toolbox/undoLast.png')
                        ).append(
                            $('<p>').html('Undo')
                        )
                    ).append(
                        $('<div />').addClass('envDesignerButton').attr('id','designerRedo').on('click', function() {
                            texelObject.loadState(texelObject.getAttribute('designerStateCurrentIndex') + 1);
                    }).append(
                            $('<img />').attr('src','https://www.envelopes.com/html/texel/img/toolbox/redoLast.png')
                        ).append(
                            $('<p />').html('Redo')
                        )
                    ).append(
                        $('<div />').addClass('envDesignerButton').attr('id','designerReset').on('click', function() {
                            texelObject.resetDesign()
                    }).append(
                            $('<img />').attr('src','https://www.envelopes.com/html/texel/img/toolbox/refreshDesign.png')
                        ).append(
                            $('<p />').html('Reset')
                        )
                    )
                );/*.append(
                    $('<div />').addClass('envDesignerBottomButton bottomRight').html('Choose a New Template').on('click', function() {
                        texelObject.designerContainer.addClass('designerHidden');
                        $('#startDesign').foundation('reveal', 'open');
                    })
                );*/

                $(leftColumnDesigner).insertBefore(texelObject.designerContainer.find('#' + texelObject.designerContainer.attr('id') + '_texel'));
            }

            if(texelObject.designerContainer.find('.envDesignerOutsideColumn.rightSide').length == 0) {
                var rightColumnDesigner = $('<div />').addClass('envDesignerOutsideColumn rightSide').append(
                    $('<div />').addClass('envDesignerThumbnailContainer')
                ).append(
                    $('<div />').addClass('envDesignerBottomButton bottomLeft envDesignerSaveAndCloseButton').html('Save &amp;<br />Close').on('click', function() {
                        texelObject.designerContainer.addClass('designerHidden');
                    })
                );

                $(rightColumnDesigner).insertAfter(texelObject.designerContainer.find('#' + texelObject.designerContainer.attr('id') + '_texel'));
            }

            var frontThumbnail = $('<div />').addClass('frontThumbnailText').html('Front').on('click', function() {
                    texelObject._loadLocation('front');
                }).append(
                    $('<img />').attr({
                    'bns-thumbnail_location': 'front',
                    'src': '',
                    'alt': 'Front Thumbnail Image'
                })
            );

            var backThumbnail = $('<div />').addClass('backThumbnailText').html('Back').on('click', function() {
                    texelObject._loadLocation('back');
                }).append(
                    $('<img />').attr({
                    'bns-thumbnail_location': 'back',
                    'src': '',
                    'alt': 'Back Thumbnail Image'
                })
            );
            texelObject.designerContainer.find('.envDesignerThumbnailContainer').append(frontThumbnail);
            texelObject.designerContainer.find('.envDesignerThumbnailContainer').append(backThumbnail);

            texelObject.designerContainer.find('.designUploadImageButton').off('click.uploadImage').on('click.uploadImage', function() {
                $('[bns-designuploadimage]').trigger('click');
            });

            texelObject._renderDesignerStyling(texelObject.canvas, texelObject.getAttribute('productWidthInches'), texelObject.getAttribute('productHeightInches'));
            texelObject._drawCanvas(texelObject.canvas, texelObject.getAttribute('jsonData')[texelObject.getAttribute('initialLocation')], false);
            texelObject.saveState(texelObject.getAttribute('jsonData'));

            $(window).off('resize.' + texelObject.designerContainer.attr('id')).on('resize.' + texelObject.designerContainer.attr('id'), function() {
                texelObject._renderDesignerStyling(texelObject.canvas, texelObject.getAttribute('productWidthInches'), texelObject.getAttribute('productHeightInches'));
        
                waitForFinalEvent(function() {
                    texelObject.loadState(texelObject.getAttribute('designerStateCurrentIndex'));
                }, '150', 'adjustDesignerObjectData');
            });
        }, 50);
    }
}

$.fn.texelLoader = function(create) {
	var element = $(this);
	if(create) {
		element.find('div.texelLoader').remove();
		element.append($('<div/>').addClass('texelLoader').append($('<div/>').addClass('spin')));
	} else {
		element.find('div.texelLoader').remove();
	}
};
