function Texel(designerContainer, designData, isTemporary) {
    this.init = function(designerContainer, designData, isTemporary) {
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

        var texelHTML = '' +
            '<div id="' + designerContainer + '" class="designerHidden">' +
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

        texelObject.canvas.off('mouse:down').on('mouse:down', function (e) {
            texelObject._loadObjectTools(e.target);
        }).off('object:modified').on('object:modified', function (e) {
            texelObject.saveState();
            texelObject._setObjectToolsStyling(e.target);
        }).off('object:moving').on('object:moving', function (e) {
            texelObject._positionCanvasTools(e.target);
            texelObject._setObjectToolsStyling(e.target);
        }).off('object:cleared').on('selection:cleared', function (e) {
            texelObject.removeObjectTools();
        });

        texelObject.defaultImage = '';

        texelObject.inkColors = [
            {'name': 'Midnight Black', 'hex': '#000000', 'knockout': '#FFFFFF'},
            {'name': 'Deep Purple', 'hex': '#2E1B46', 'knockout': '#FFFFFF'},
            {'name': 'Navy', 'hex': '#002856', 'knockout': '#FFFFFF'},
            {'name': 'Pool', 'hex': '#0082CA', 'knockout': '#FFFFFF'},
            {'name': 'Baby Blue', 'hex': '#BAD2DD', 'knockout': '#000000'},
            {'name': 'Seafoam', 'hex': '#98D2DD', 'knockout': '#000000'},
            {'name': 'Teal', 'hex': '#008996', 'knockout': '#FFFFFF'},
            {'name': 'Racing Green', 'hex': '#005740', 'knockout': '#FFFFFF'},
            {'name': 'Bright Green', 'hex': '#009946', 'knockout': '#FFFFFF'},
            {'name': 'Limelight', 'hex': '#A1D784', 'knockout': '#000000'},
            {'name': 'Wasabi', 'hex': '#DBE444', 'knockout': '#000000'},
            {'name': 'Citrus', 'hex': '#F1ED74', 'knockout': '#000000'},
            {'name': 'Lemonade', 'hex': '#EDE59B', 'knockout': '#000000'},
            {'name': 'Nude', 'hex': '#F3DBB3', 'knockout': '#000000'},
            {'name': 'Silver', 'hex': '#C0C0C0', 'knockout': '#000000'},
            {'name': 'White', 'hex': '#FFFFFF', 'knockout': '#000000'},
            {'name': 'Sunflower', 'hex': '#FDDD3F', 'knockout': '#000000'},
            {'name': 'Gold', 'hex': '#937851', 'knockout': '#000000'},
            {'name': 'Pencil', 'hex': '#F8BF00', 'knockout': '#000000'},
            {'name': 'Mandarin', 'hex': '#FF9016', 'knockout': '#FFFFFF'},
            {'name': 'Grocery Bag', 'hex': '#CAAA7B', 'knockout': '#000000'},
            {'name': 'Ochre', 'hex': '#D57F00', 'knockout': '#000000'},
            {'name': 'Rust', 'hex': '#BC4800', 'knockout': '#FFFFFF'},
            {'name': 'Terracotta', 'hex': '#993921', 'knockout': '#FFFFFF'},
            {'name': 'Tangerine', 'hex': '#E1261C', 'knockout': '#FFFFFF'},
            {'name': 'Ruby Red', 'hex': '#C20430', 'knockout': '#FFFFFF'},
            {'name': 'Garnet', 'hex': '#A32136', 'knockout': '#FFFFFF'},
            {'name': 'Wine', 'hex': '#8C3D45', 'knockout': '#FFFFFF'},
            {'name': 'Vintage Plum', 'hex': '#84286B', 'knockout': '#FFFFFF'},
            {'name': 'Magenta', 'hex': '#CB007C', 'knockout': '#FFFFFF'},
            {'name': 'Candy Pink', 'hex': '#F7DBE0', 'knockout': '#000000'},
            {'name': 'Wisteria', 'hex': '#7474C1', 'knockout': '#FFFFFF'},
            {'name': 'Lilac', 'hex': '#B5B4E0', 'knockout': '#000000'},
            {'name': 'Pastel Gray', 'hex': '#CACAC8', 'knockout': '#000000'},
            {'name': 'Slate', 'hex': '#8D9B9B', 'knockout': '#000000'},
            {'name': 'Smoke', 'hex': '#6C6864', 'knockout': '#FFFFFF'},
            {'name': 'Tobacco', 'hex': '#88674D', 'knockout': '#FFFFFF'},
            {'name': 'Chocolate', 'hex': '#6F5D51', 'knockout': '#FFFFFF'},
            {'name': 'Moss', 'hex': '#6B5A24', 'knockout': '#FFFFFF'},
            {'name': 'Avocado', 'hex': '#90993F', 'knockout': '#000000'}
        ];

        texelObject.fonts = {
            "AddCityboy Normal": {
                "style":		"font-family:'AddCityboy Normal';",
                "jsPDFFile":    "addcityboy-normal.js",
                "jsPDFName":    "addcityboy",
                "jsPDFStyle":   "normal"
            },
			"Adobe Fangsong Std": {
                "style":		"font-family: 'Adobe Fangsong Std';",
				"jsPDFFile":	"adobeFangsongStdR-normal.js",
				"jsPDFName":	"adobeFangsongStdR",
				"jsPDFStyle":	"normal"
			},
			"Allura": {
                "style":		"font-family: 'Allura';",
				"jsPDFFile":	"allura.regular-normal.js",
				"jsPDFName":	"allura.regular",
				"jsPDFStyle":	"normal"
            },
			"Alte Haas Grotesk": {
                "style":		"font-family: 'Alte Haas Grotesk';",
				"jsPDFFile":	"AlteHaasGroteskRegular-normal.js",
				"jsPDFName":	"AlteHaasGroteskRegular",
				"jsPDFStyle":	"normal"
			},
			"Ambient Medium": {
                "style":		"font-family: 'Ambient Medium';",
				"jsPDFFile":	"AMBIENT-normal.js",
				"jsPDFName":	"AMBIENT",
				"jsPDFStyle":	"normal"
            },
			"Angelina": {
                "style":		"font-family: 'Angelina';",
				"jsPDFFile":	"angelina-normal.js",
				"jsPDFName":	"angelina",
				"jsPDFStyle":	"normal"
            },
			"Arnprior-Regular": {
                "style":		"font-family: 'Arnprior-Regular';",
				"jsPDFFile":	"ARNPRIOR-normal.js",
				"jsPDFName":	"ARNPRIOR",
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
				"jsPDFName":	"bakerSignet",
				"jsPDFStyle":	"normal"
			},
			"Baloney": {
                "style":		"font-family: 'Baloney';",
				"jsPDFFile":	"BALONEY_-normal.js",
				"jsPDFName":	"BALONEY_",
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
				"jsPDFName":	"batik_regular",
				"jsPDFStyle":	"normal"
			},
			"Bauderie Script SSi": {
                "style":		"font-family: 'Bauderie Script SSi';",
				"jsPDFFile":	"bauderieScript-normal.js",
				"jsPDFName":	"bauderieScript",
				"jsPDFStyle":	"normal"
			},
			"Bauhaus 93": {
                "style":		"font-family: 'Bauhaus 93';",
				"jsPDFFile":	"bauhaus93-normal.js",
				"jsPDFName":	"bauhaus93",
				"jsPDFStyle":	"normal"
            },
			"Bauhaus Light": {
                "style":		"font-family: 'Bauhaus Light';",
				"jsPDFFile":	"Bauhaus Light Regular-normal.js",
				"jsPDFName":	"Bauhaus Light Regular",
				"jsPDFStyle":	"normal"
            },
			"BlackChancery": {
                "style":		"font-family: 'BlackChancery';",
				"jsPDFFile":	"blackChancery-normal.js",
				"jsPDFName":	"blackChancery",
				"jsPDFStyle":	"normal"
			},
			"Brandon Grotesque Black": {
                "style":		"font-family: 'Brandon Grotesque Black';",
				"jsPDFFile":	"brandongrotesque-black-normal.js",
				"jsPDFName":	"brandongrotesque-black",
				"jsPDFStyle":	"normal"
            },
			"Brush Script Std": {
                "style":		"font-family: 'Brush Script Std';",
				"jsPDFFile":	"brushScript-normal.js",
				"jsPDFName":	"brushScript",
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
				"jsPDFName":	"CALIBRI",
				"jsPDFStyle":	"normal"
			},
			"Calibri Bold": {
                "style":		"font-family: 'Calibri Bold';",
				"jsPDFFile":	"calibriBold-bold.js",
				"jsPDFName":	"calibriBold",
				"jsPDFStyle":	"bold"
            },
			"Calibri Italic": {
                "style":		"font-family: 'Calibri Italic';",
				"jsPDFFile":	"calibriItalic-italic.js",
				"jsPDFName":	"calibriItalic",
				"jsPDFStyle":	"italic"
			},
			"Calligrapher": {
                "style":		"font-family: 'Calligrapher';",
				"jsPDFFile":	"calligrapher-normal.js",
				"jsPDFName":	"calligrapher",
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
				"jsPDFName":	"cambriaBold",
				"jsPDFStyle":	"bold"
			},
			"Cambria Italic": {
                "style":		"font-family: 'Cambria Italic';",
				"jsPDFFile":	"cambriaItalic-italic.js",
				"jsPDFName":	"cambriaItalic",
				"jsPDFStyle":	"italic"
			},
			"Candara": {
                "style":		"font-family: 'Candara';",
				"jsPDFFile":	"CANDARA-normal.js",
				"jsPDFName":	"CANDARA",
				"jsPDFStyle":	"normal"
            },
			"Cezanne": {
                "style":		"font-family: 'Cezanne';",
				"jsPDFFile":	"cezanne-normal.js",
				"jsPDFName":	"cezanne",
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
				"jsPDFName":	"ChalkboardBold",
				"jsPDFStyle":	"bold"
			},
			"Chaucer": {
                "style":		"font-family: 'Chaucer';",
				"jsPDFFile":	"CHAUCER-normal.js",
				"jsPDFName":	"CHAUCER",
				"jsPDFStyle":	"normal"
			},
			"Chisel Mark Regular": {
                "style":		"font-family: 'Chisel Mark Regular';",
				"jsPDFFile":	"chisel-normal.js",
				"jsPDFName":	"chisel",
				"jsPDFStyle":	"normal"
			},
			"Chopin Script": {
                "style":		"font-family: 'Chopin Script';",
				"jsPDFFile":	"chopinScript-normal.js",
				"jsPDFName":	"chopinScript",
				"jsPDFStyle":	"normal"
            },
			"Comic Sans MS": {
                "style":		"font-family: 'Comic Sans MS';",
				"jsPDFFile":	"comicSansMS-normal.js",
				"jsPDFName":	"comicSansMS",
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
				"jsPDFName":	"copperplate",
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
				"jsPDFName":	"cornerstone",
				"jsPDFStyle":	"normal"
            },
			"Courier New": {
                "style":		"font-family: 'Courier New';",
				"jsPDFFile":	"courierNew-normal.js",
				"jsPDFName":	"courierNew",
				"jsPDFStyle":	"normal"
            },
			"Courier New Italic": {
                "style":		"font-family: 'Courier New Italic';",
				"jsPDFFile":	"courierNewItalic-italic.js",
				"jsPDFName":	"courierNewItalic",
				"jsPDFStyle":	"italic"
			},
			"Crillee": {
                "style":		"font-family: 'Crillee';",
				"jsPDFFile":	"CRILLEE-normal.js",
				"jsPDFName":	"CRILLEE",
				"jsPDFStyle":	"normal"
			},
			"D3 Archism": {
                "style":		"font-family: 'D3 Archism';",
				"jsPDFFile":	"D3-normal.js",
				"jsPDFName":	"D3",
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
				"jsPDFName":	"dreamOrphans",
				"jsPDFStyle":	"normal"
			},
			"Eccentric Std": {
                "style":		"font-family: 'Eccentric Std';",
				"jsPDFFile":	"Eccentric-normal.js",
				"jsPDFName":	"Eccentric",
				"jsPDFStyle":	"normal"
            },
			"Echelon": {
                "style":		"font-family: 'Echelon';",
				"jsPDFFile":	"echelon-normal.js",
				"jsPDFName":	"echelon",
				"jsPDFStyle":	"normal"
            },
			"Echelon Italic": {
                "style":		"font-family: 'Echelon Italic';",
				"jsPDFFile":	"echelonItalic-italic.js",
				"jsPDFName":	"echelonItalic",
				"jsPDFStyle":	"italic"
			},
			"Eleganza": {
                "style":		"font-family: 'Eleganza';",
				"jsPDFFile":	"Eleganza-normal.js",
				"jsPDFName":	"Eleganza",
				"jsPDFStyle":	"normal"
			},
			"Elisia": {
                "style":		"font-family: 'Elisia';",
				"jsPDFFile":	"elisia-normal.js",
				"jsPDFName":	"elisia",
				"jsPDFStyle":	"normal"
			},
			"Emblem": {
                "style":		"font-family: 'Emblem';",
				"jsPDFFile":	"EMBLEM-normal.js",
				"jsPDFName":	"EMBLEM",
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
				"jsPDFName":	"franklinGothicMedium",
				"jsPDFStyle":	"normal"
			},
			"Freehand 471 BT": {
                "style":		"font-family: 'Freehand 471 BT';",
				"jsPDFFile":	"freehand_471_bt-normal.js",
				"jsPDFName":	"freehand_471_bt",
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
				"jsPDFName":	"georgiaItalic",
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
				"jsPDFName":	"goodfish",
				"jsPDFStyle":	"normal"
			},
			"Gotham Black": {
                "style":		"font-family: 'Gotham Black';",
				"jsPDFFile":	"gothamBlackRegular-normal.js",
				"jsPDFName":	"gothamBlackRegular",
				"jsPDFStyle":	"normal"
			},
			"HandelGotDBol": {
                "style":		"font-family: 'HandelGotDBol';",
				"jsPDFFile":	"HandelGotDBol-normal.js",
				"jsPDFName":	"HandelGotDBol",
				"jsPDFStyle":	"normal"
			},
			"HandelGotDLig": {
                "style":		"font-family: 'HandelGotDLig';",
				"jsPDFFile":	"HandelGotDLig-normal.js",
				"jsPDFName":	"HandelGotDLig",
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
				"jsPDFName":	"HORATIOL",
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
				"jsPDFName":	"JaneAust",
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
				"jsPDFName":	"LARIAT",
				"jsPDFStyle":	"normal"
			},
			"LatinoSamba": {
                "style":		"font-family: 'LatinoSamba';",
				"jsPDFFile":	"latino samba-normal.js",
				"jsPDFName":	"latino samba",
				"jsPDFStyle":	"normal"
			},
			"Lucida Sans Regular": {
                "style":		"font-family: 'Lucida Sans Regular';",
				"jsPDFFile":	"lucidaSansRegular-normal.js",
				"jsPDFName":	"lucidaSansRegular",
				"jsPDFStyle":	"normal"
			},
			"Magical Wands": {
                "style":		"font-family: 'Magical Wands';",
				"jsPDFFile":	"Magical-normal.js",
				"jsPDFName":	"Magical",
				"jsPDFStyle":	"normal"
			},
			"MicrogrammaDBolExt": {
                "style":		"font-family: 'MicrogrammaDBolExt';",
				"jsPDFFile":	"MICROGBE-normal.js",
				"jsPDFName":	"MICROGBE",
				"jsPDFStyle":	"normal"
            },
			"Microsoft Sans Serif": {
                "style":		"font-family: 'Microsoft Sans Serif';",
				"jsPDFFile":	"microsoftSansSerif-normal.js",
				"jsPDFName":	"microsoftSansSerif",
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
				"jsPDFName":	"poor_richard_2",
				"jsPDFStyle":	"normal"
			},
			"Poplar Std": {
                "style":		"font-family: 'Poplar Std';",
				"jsPDFFile":	"Poplar-normal.js",
				"jsPDFName":	"Poplar",
				"jsPDFStyle":	"normal"
			},
			"Pushkin": {
                "style":		"font-family: 'Pushkin';",
				"jsPDFFile":	"pushkin-normal.js",
				"jsPDFName":	"pushkin",
				"jsPDFStyle":	"normal"
            },
			"Radiated Pancake": {
                "style":		"font-family: 'Radiated Pancake';",
				"jsPDFFile":	"Radiated-normal.js",
				"jsPDFName":	"Radiated",
				"jsPDFStyle":	"normal"
			},
			"Renaissance": {
                "style":		"font-family: 'Renaissance';",
				"jsPDFFile":	"renaissance-normal.js",
				"jsPDFName":	"renaissance",
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
				"jsPDFName":	"Sabertooth",
				"jsPDFStyle":	"normal"
			},
			"Sable Lion": {
                "style":		"font-family: 'Sable Lion';",
				"jsPDFFile":	"Sable-normal.js",
				"jsPDFName":	"Sable",
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
				"jsPDFName":	"Sansation_Regular",
				"jsPDFStyle":	"normal"
			},
			"Sansation Bold": {
                "style":		"font-family: 'Sansation Bold';",
				"jsPDFFile":	"Sansation_Bold-bold.js",
				"jsPDFName":	"Sansation_Bold",
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
				"jsPDFName":	"Steinerlight",
				"jsPDFStyle":	"normal"
			},
			"Stencil Std": {
                "style":		"font-family: 'Stencil Std';",
				"jsPDFFile":	"stencil-normal.js",
				"jsPDFName":	"stencil",
				"jsPDFStyle":	"normal"
			},
			"T4C Beaulieux": {
                "style":		"font-family: 'T4C Beaulieux';",
				"jsPDFFile":	"T4C-normal.js",
				"jsPDFName":	"T4C",
				"jsPDFStyle":	"normal"
			},
			"Tag LET": {
                "style":		"font-family: 'Tag Let';",
				"jsPDFFile":	"Tag-normal.js",
				"jsPDFName":	"Tag",
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
				"jsPDFName":	"tahomaBold",
				"jsPDFStyle":	"bold"
			},
			"Times New Roman": {
                "style":		"font-family: 'Times New Roman';",
				"jsPDFFile":	"timesNewRomanNormal.js",
				"jsPDFName":	"timesNewRomanNormal",
				"jsPDFStyle":	"normal"
			},
			"Times New Roman Bold": {
                "style":		"font-family: 'Times New Roman Bold';",
				"jsPDFFile":	"timesNewRomanBold-bold.js",
				"jsPDFName":	"timesNewRomanBold",
				"jsPDFStyle":	"bold"
            },
			"Times New Roman Italic": {
                "style":		"font-family: 'Times New Roman Italic';",
				"jsPDFFile":	"timesNewRomanItalic-italic.js",
				"jsPDFName":	"timesNewRomanItalic",
				"jsPDFStyle":	"italic"
			},
			"Tycho": {
                "style":		"font-family: 'Tycho';",
				"jsPDFFile":	"TYCHO___-normal.js",
				"jsPDFName":	"TYCHO___",
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
				"jsPDFName":	"verdanaBold",
				"jsPDFStyle":	"bold"
            },
			"Verdana Italic": {
                "style":		"font-family: 'Verdana Italic';",
				"jsPDFFile":	"verdanaItalic-italic.js",
				"jsPDFName":	"verdanaItalic",
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
				"jsPDFName":	"walkwayBold",
				"jsPDFStyle":	"bold"
			},
			"Walkway Oblique": {
                "style":		"font-family: 'Walkway Oblique';",
				"jsPDFFile":	"walkwayOblique-normal.js",
				"jsPDFName":	"walkwayOblique",
				"jsPDFStyle":	"normal"
			},
			"Walkway RevOblique": {
                "style":		"font-family: 'Walkway RevOblique';",
				"jsPDFFile":	"walkwayRevOblique-normal.js",
				"jsPDFName":	"walkwayRevOblique",
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
            'backgroundColor': (typeof designData.backgroundColor == 'undefined' ? 'FFFFFF' : designData.backgroundColor.toUpperCase()),
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

        // Firefox Hack to fix loading template
        if (navigator.sayswho().match(/firefox/i) != null) {
            $('body').spinner(true, false, 150, null, null, '', null, null);
            window.setTimeout(function() {
                texelObject.loadState(0);
                $('body').spinner(false);
            }, 2000);
        }
    };

    this._setAllowedColors = function() {
        var texelObject = this;

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
    };

    this.areAssetsLoaded = function() {
        var texelObject = this;

        for (var key in texelObject.loaded) {
            if (!texelObject.loaded[key]) {
                return false;
            }
        }

        return true;
    };

    this.setAttribute = function(attributeName, attributeValue) {
        this._attributes[attributeName] = attributeValue;

        if (attributeName == 'isVariable') {
            this._setAllowedColors();
        }
    };

    this.setAttributes = function(attributes) {
        for (var key in attributes) {
            this.setAttribute(key, attributes[key]);
        }
    };

    this.getAttribute = function(attributeName) {
        return this._attributes[attributeName];
    };

    this.getAttributes = function() {
        return this._attributes;
    };

    this.addUpdateEvent = function(name, updateMethod) {
        var texelObject = this;

        var updateEvents = texelObject.updateEvents;

        updateEvents[name] = updateMethod;
    };

    this.destroyTexel = function() {
        var texelObject = this;

        texelObject.designerContainer.remove();
    };

    this.getCleanUserImages = function() {
        var texelObject = this;
        var userImageList = [];

        for (var i = 0; i < texelObject.getAttribute('userImages').length; i++) {
            var newUserImage = {}
            for (var key in texelObject.getAttribute('userImages')[i]) {
                //if (key != 'base64') {
                    newUserImage[key] = texelObject.getAttribute('userImages')[i][key];
                //}
            }
            userImageList.push(newUserImage);
        }

        return userImageList;
    };

    this.texelThread = function(method, timeout, timeToKill) {
        var texelObject = this;
        var intervalCounter = 0;
        timeout = (typeof timeout != 'undefined' ? timeout : 100);
        timeToKill = (typeof timeToKill != 'undefined' ? timeToKill : 10000);

        texelObject.designerContainer.spinner(true, false, 150, null, null, '', null, null);
        var thread = window.setInterval(function() {
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
    };

    this.updateAddressing = function(data, font) {
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
    };

    this._organizeAddressingText = function(textList) {
        return (typeof textList[0] != 'undefined' && textList[0] != '' ? textList[0] + '\n' : '') +
            (typeof textList[1] != 'undefined' && textList[1] != '' ? textList[1] + '\n' : '') +
            (typeof textList[2] != 'undefined' && textList[2] != '' ? textList[2] + '\n' : '') +
            (typeof textList[3] != 'undefined' && textList[3] != '' ? textList[3] + '\n' : '') +
            (typeof textList[4] != 'undefined' && textList[4] != '' ? textList[4] + ', ' : '') +
            (typeof textList[5] != 'undefined' && textList[5] != '' ? textList[5] + ' ' : '') +
            (typeof textList[6] != 'undefined' && textList[6] != '' ? textList[6] : '') +
            (typeof (textList[4] != 'undefined' && textList[4] != '') || (typeof textList[5] != 'undefined' && textList[5] != '') || (typeof textList[6] != 'undefined' && textList[6] != '') ? '\n' : '') +
            (typeof textList[7] != 'undefined' && textList[7] != '' ? textList[7] : '');
    };

    this._loadLocation = function(location) {
        var texelObject = this;

        if (location != texelObject.getAttribute('currentLocation') && typeof texelObject.getAttribute('jsonData')[location] != 'undefined') {
            $('[bns-texel_tool]').remove();
            texelObject.setAttribute('currentLocation', location);
            texelObject._drawCanvas(texelObject.canvas, texelObject.getAttribute('designerState')[texelObject.getAttribute('designerStateCurrentIndex')].jsonData[location], true);
        }
    };

    this.loadUserImages = function(indexToLoad) {
        var texelObject = this;

        for(var i = 0; i < texelObject.getAttribute('userImages').length; i++) {
            if (typeof texelObject.getAttribute('userImages')[i].base64 == 'undefined' && typeof texelObject.getAttribute('userImages')[i].src != 'undefined') {
                var src = texelObject.getAttribute('userImages')[i].src;

                $.ajax({
                    type: "GET",
                    url: '/' + websiteId + '/control/getBase64ImageData',
                    data: {
                        url: (src.match('^//') != null ? 'https:' : '') + src,
                        index: i
                    },
                    dataType: 'json',
                    cache: true
                }).done(function(data) {
                    if (data.success) {
                        texelObject.getAttribute('userImages')[data.index].base64 = 'data:image/png-alpha;base64,' + data.base64;
                        texelObject._paintUserImage(data.index, (data.index == indexToLoad ? true : false));
                    } else {
                        texelObject.getAttribute('userImages')[data.index].base64 = null;
                    }
                });
            } else if (typeof texelObject.getAttribute('userImages')[i].base64 != 'undefined') {
                texelObject._paintUserImage(i, (i == indexToLoad ? true : false));
            }
        }

        var checkImageLoad = window.setInterval(function() {
            var imagesLoaded = true;

            for(var i = 0; i < texelObject.getAttribute('userImages').length; i++) {
                if (typeof texelObject.getAttribute('userImages')[i].base64 == 'undefined') {
                    imagesLoaded = false;
                }
            }

            if (imagesLoaded) {
                texelObject.loaded.images = true;
                window.clearInterval(checkImageLoad);
            }
        }, 100);
    };

    this._getObjectByName = function(name) {
        var texelObject = this;

        if (typeof name != 'undefined') {
            texelObject.canvas.getObjects().each(function() {
                if (this.name == name) {
                    return this
                }
            });
        }
    };

    this._loadImageLibrary = function() {
        var texelObject = this;

        $('#texelToolBox .uploadGrid').empty();
        texelObject.designerContainer.find('.texelImageLibrary').clone(true).appendTo($('#texelToolBox .uploadGrid'));
        $('#texelToolBox .uploadGrid .texelImageLibrary').removeClass('texelImageLibrary');
    };

    this._paintUserImage = function(index, load) {
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
                texelObject._removeObjectByName($('#texelToolBox').attr('data-object_name'));
                texelObject.createDesignerObject(texelObject.canvas, 'image', {
                    'src': $(this).attr('src'),
                    'index': $(this).attr('index')
                });
                texelObject.removeObjectTools();
                texelObject.getAttribute('userImages')[index].used = true;
            });

            $('.texelImageLibrary').append(imageElement);

            if (typeof load != 'undefined' && load) {
                texelObject.texelThread(function() {
                    imageElement.trigger('click');
                }, 50);
            }
        }

        texelObject._loadImageLibrary();
    };

    this.getCanvasDataAsImage = function(jsonData) {
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
    };

    this._doUpdateEvents = function() {
        var texelObject = this;

        texelObject._updateThumbnails();

        for (var name in texelObject.updateEvents) {
            texelObject.updateEvents[name]();
        }
    };

    this._updateThumbnails = function() {
        var texelObject = this;
        var imageData = texelObject.getAttribute('designerState')[texelObject.getAttribute('designerStateCurrentIndex')].imageData;

        for (var location in imageData) {
            $('[bns-thumbnail_location="' + location + '"]').attr('src', imageData[location]);
        }
    };

    this.resetDesign = function() {
        var texelObject = this;

        texelObject.loadState(0);
        texelObject.getAttribute('designerState').splice(texelObject.getAttribute('designerStateCurrentIndex') + 1);
    };

    this._renderDesignerStyling = function(canvas, productWidth, productHeight) {
        var texelObject = this;

        var widthAllowed = getFullWidth(texelObject.designerContainer) - (texelObject.getAttribute('isTemporary') ? 0 : 500);
        var heightAllowed = getFullHeight(texelObject.designerContainer) - (texelObject.getAttribute('isTemporary') ? 0 : 130 + getFullHeight($('.jqs-texelTemplateHelp')));

        if (widthAllowed / heightAllowed <= productWidth / productHeight) {
            texelObject.setAttribute('canvasWidth', widthAllowed);
            texelObject.setAttribute('canvasHeight', texelObject.getAttribute('canvasWidth') * (productHeight / productWidth));
        } else {
            texelObject.setAttribute('canvasHeight', heightAllowed);
            texelObject.setAttribute('canvasWidth', texelObject.getAttribute('canvasHeight') * (productWidth / productHeight));
        }

        $('#designerCSSModified').remove();
        $('#designerCSS').after(
            $('<style />').attr('id', 'designerCSSModified').append(
                '.canvas-container {' +
                'left:' + 'calc(50% - ' + (texelObject.getAttribute('canvasWidth') / 2) + 'px) !important;',
                'opacity:' + '1 !important;' +
                '}'
            )
        );

        canvas.setWidth(texelObject.getAttribute('canvasWidth'));
        canvas.setHeight(texelObject.getAttribute('canvasHeight'));

        $('.envDesignerOutsideColumn.leftSide').css({
            'position': 'absolute',
            'left': 'calc(50% - ' + (((texelObject.getAttribute('canvasWidth') / 2) + 150) + 10) + 'px)',
            'height': 'calc(100%)'
        });

        $('.envDesignerOutsideColumn.rightSide').css({
            'position': 'absolute',
            'left': 'calc(50% + ' + ((texelObject.getAttribute('canvasWidth') / 2) + 10) + 'px)',
            'height': 'calc(100%)'
        });

        texelObject.setAttribute('canvasScaleX', texelObject.getAttribute('canvasWidth') / texelObject.getAttribute('productWidthPixels'));
        texelObject.setAttribute('canvasScaleY', texelObject.getAttribute('canvasHeight') / texelObject.getAttribute('productHeightPixels'));
    };

    this._drawCanvas = function(canvas, objList, save, ignoredEvents) {
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
                    texelObject.createDesignerObject(canvas, attributes.type, attributes, assign({
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
    };

    this._syncCanvasObjectZIndex = function() {
        var texelObject = this;

        texelObject.canvas.getObjects().forEach(function (obj) {
            texelObject.canvas.moveTo(obj, obj.zIndex);
        });
    };

    this._isColorAllowed = function(hex1, hex2) {
        var texelObject = this;

        var difference = Math.abs(
            texelObject._getColorContrast(hex1) -
            texelObject._getColorContrast(hex2)
        );

        return difference > 50;
    };

    this._getColorContrast = function(hex) {
        hex = hex.replace('#', '');
        if (hex.length == 2) {
            hex += hex + hex;
        }

        return (((parseInt(hex.substr(0,2),16) * 299) + (parseInt(hex.substr(2,2),16) * 587) + (parseInt(hex.substr(4,2),16) * 114)) / 1000);
    };

    this._addDesignerObject = function(canvas, obj, type, objName, ignoredEvents) {
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

            texelObject._positionCanvasTools(obj);

            if (typeof ignoredEvents.showToolBox == 'undefined' || !ignoredEvents.showToolBox) {
                texelObject._loadObjectTools(obj);
                canvas.setActiveObject(obj)
            }

            if (typeof ignoredEvents.saveState == 'undefined' || !ignoredEvents.saveState) {
                texelObject.saveState();
            }
        }
    };

    this._positionCanvasTools = function(obj) {
        var texelObject = this;

        if (typeof obj != 'undefined') {
            if (obj.type == 'textBoxAddressing') {
                $('[bns-texel_tool="addressRotator"]').css({
                    'top': obj.top - 25,
                    'left': obj.left + (obj.width * obj.scaleX) - 115
                });
            }
        } else {
            var addressRotator = $('[bns-texel_tool="addressRotator"]')
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
    };

    this.saveState = function(data) {
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
    };

    this.loadState = function(index) {
        var texelObject = this;

        if (typeof texelObject.getAttribute('designerState')[index] != 'undefined') {
            // reload image data for the thumbnails... this is for efficiency when switching background colors.
            texelObject.getAttribute('designerState')[index].imageData = texelObject.getCanvasDataAsImage(texelObject.getAttribute('designerState')[index].jsonData);
            $('[bns-texel_tool]').remove();
            texelObject.setAttribute('designerStateCurrentIndex', index);
            texelObject.setAttribute('objectCounter', 0);
            texelObject._drawCanvas(texelObject.canvas, texelObject.getAttribute('designerState')[index].jsonData[texelObject.getAttribute('designerState')[index].currentLocation], false);
            texelObject.setAttribute('currentLocation', texelObject.getAttribute('designerState')[index].currentLocation);
            texelObject._doUpdateEvents();
        }
    };

    this._getObjectAttributes = function(obj) {
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
    };

    this.createDesignerObject = function(canvas, type, attributes, ignoredEvents) {
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
                var textAlign = null;
                var currentAddressingIndex = (typeof attributes.currentAddressingIndex != 'undefined' ? attributes.currentAddressingIndex : 0);
                var fontFamily = (typeof attributes.fontFamily != 'undefined' && typeof texelObject.fonts[attributes.fontFamily] != 'undefined' ? attributes.fontFamily : 'Times New Roman');
                var fontSize = (typeof attributes.fontSize != 'undefined' ? attributes.fontSize : (texelObject.getAttribute('canvasScaleX') < 1 ? Math.ceil(12 / texelObject.getAttribute('canvasScaleX')) : 12));
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
                    textAlign = (typeof attributes.textAlign != 'undefined' ? attributes.textAlign : 'center');

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

                textAlign = (typeof attributes.textAlign != 'undefined' ? attributes.textAlign : 'left');

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
                    $('<div />').attr('bns-texel_temp_image_container', '').css({
                        'position': 'absolute',
                        'left': '-99999px',
                        'top': '0px',
                        'width': '10000px',
                        'height': '10000px'
                    }).append(
                        $('<img />').attr({
                            'bns-texel_temp_image': '',
                            'src': src
                        }).css({
                            'width': 'auto',
                            'height': 'auto'
                        })
                    )
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

                $('.canvas-container').find('[bns-texel_temp_image_container]').remove();

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
    };

    this._loadObjectTools = function(obj) {
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
                    if ((typeof texelObject.allowedInkColors['#' + productPage.returnActiveProduct(true).hex.toUpperCase()] != 'undefined' && $.inArray(texelObject.inkColors[i].hex, texelObject.allowedInkColors['#' + productPage.returnActiveProduct(true).hex.toUpperCase()]) > -1) || typeof texelObject.allowedInkColors['#' + productPage.returnActiveProduct(true).hex.toUpperCase()] == 'undefined') {
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
                    '<div id="texelToolBox" class="texelPopupContainer padding-xxs" data-object_name="' + obj.name + '">' +
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
                                '<strong>Supported Files:</strong> JPEG, PNG, GIF, BMP' +
                                '<br />20MB File Size Maximum.' +
                            '</p>' +
                            '<br />' +
                            '<p style="color:#dc143c;">' +
                                '<strong>NOTE:</strong>'+
                                '<br />' +
                                'You CANNOT use AI / EPS / SVG / PDF artwork using this function, Please use ' +
                                'our <strong>Use a Complete Design</strong> option.' +
                            '</p>' +
                        '</div>' + 
                        '<div class="texelPopupRow noTopBorder">' +
                            '<div class="round-btn texelButtonOrange texelImageUpload">Upload Image</div>' +
                        '</div>' +
                        '<div class="texelPopupRow uploadBlock">' +
                            '<h4 style="margin-top: 5px;color:#dc143c;">WHITE INK:</h4>' +
                            '<p style="color:#dc143c;">' +
                                'If you are looking to print with WHITE ink, please email VECTOR files (AI / EPS / SVG / PDF) to <strong>PREPRESS@ENVELOPES.COM</strong> with your order number in the subject line.' + 
                            '</p>' +
                            '<p class="margin-top-xxs">' +
                                'Order may be subject to an upcharge.' +
                            '</p>' +
                            '<br /><h4>Uploading Issues:</h4>' +
                            '<p>' + 
                                'If you have an issue uploading your image, please email the files to <strong>PREPRESS@ENVELOPES.COM</strong> with your order number in the subject line. You will receive a proof to approve before your order is printed.' +
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
    };

    this._bindObjectToolboxClickEvents = function(obj) {
        var texelObject = this;

        // Text
        $('#texelToolBox[data-object_name="' + obj.name + '"] [bns-texel_text]').on('input.texelText', function(e) {
            var selectedOption = $(this);

            var attributes = {};
            attributes[selectedOption.attr('data-name')] = selectedOption.val();

            texelObject._updateCanvasObject(obj, attributes);
        });

        // Select
        $('#texelToolBox[data-object_name="' + obj.name + '"] [bns-texel_select]').on('click.texelSelectPopup', function(e) {
            e.stopPropagation();
            var selectedOption = $(this);

            $('[bns-texel_select_popup]').css('display', 'none');
            $('#' + selectedOption.attr('bns-texel_select')).css('display', 'block');

            if (selectedOption.length > 0 && $('#texel_fontFamily').find('.texelSelectListSelected').length > 0) {
                $('#texel_fontFamily').scrollTop(($('#texel_fontFamily').find('.texelSelectListSelected').offset().top - $('#texel_fontFamily').offset().top) + $('#texel_fontFamily').scrollTop() - 1);
            }

            $('#texelToolBox').off('click.closeSelectPopup').on('click.closeSelectPopup', function(e) {
                if (!$(e.target).parents('[bns-texel_select_popup]').length > 0) {
                    $('[bns-texel_select_popup]').css('display', 'none');
                    $(this).off('click.closeSelectPopup');
                }
            });
        });

        // Option
        $('#texelToolBox[data-object_name="' + obj.name + '"] [bns-texel_option]').on('click.texelOptionSelection', function() {
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
        $('#texelToolBox[data-object_name="' + obj.name + '"] [bns-texel_radio]').on('click.texelRadio', function() {
            var selectedOption = $(this);

            $('[bns-texel_radio="' + selectedOption.attr('bns-texel_radio') + '"]').attr('data-selected', 'false');
            selectedOption.attr('data-selected', 'true');

            var attributes = {};
            attributes[selectedOption.attr('data-name')] = selectedOption.attr('data-value');

            texelObject._updateCanvasObject(obj, attributes);
        });

        // Action
        $('#texelToolBox[data-object_name="' + obj.name +'"] [bns-texel_action]').on('click.texelAction', function() {
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
        $('#texelToolBox [bns-edit_address]').off('click.editAddress').on('click.editAddress', function() {
            productPage.showGrid('manual', (typeof obj.currentAddressingIndex != 'undefined' ? obj.currentAddressingIndex : 0));
        });

        // Close!
        $('#texelToolBox').find('[bns-close_button]').on('click', function() {
            texelObject.removeObjectTools();
        });

        // Delete Object
        $('[bns-texel_remove_object]').on('click', function() {
            var objectName = $(this).attr('bns-texel_remove_object');

            texelObject.canvas.remove(texelObject._getObjectByName(objectName));
            texelObject.saveState();
            texelObject.removeObjectTools();
        });
    };

    this._updateCanvasObject = function(obj, attributes) {
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
    };

    this._setObjectToolsStyling = function(obj) {
        var texelObject = this;

        var leftPadding = 30;
        var topPadding = - 15;

        var top = obj.top + topPadding;
        var left = obj.left + (obj.width * obj.scaleX) + leftPadding;

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
    };

    this.removeObjectTools = function() {
        $('#texelToolBox').remove();
        $('[bns-texel_tool="addressRotator"]').removeClass('hidden').addClass('hidden');
    };

    this._getObjectByName = function(objectName) {
        var texelObject = this;
        var objectList = texelObject.canvas.getObjects();

        for (var x = 0; x < objectList.length; x++) {
            if (objectList[x].name == objectName) {
                return objectList[x];
            }
        }

        return undefined;
    };

    this._removeObjectByName = function(objectName) {
        var texelObject = this;

        texelObject.canvas.getObjects().forEach(function(obj) {
            if (obj.name == objectName) {
                texelObject.canvas.remove(obj)
            }
        });
    };
    if(typeof designerContainer !== 'undefined') {
        this.init(designerContainer, designData, isTemporary);
    }

}

function TexelCanvas(designerContainer, designData) {
    Texel.call(this, designerContainer, designData);
    var texelObject = this;

    texelObject.texelThread(function() {
        $('[bns-help_button]').off('click.showHelp').on('click.showHelp', function() {
            $('.jqs-texelHelpContainer').removeClass('hidden');

            if ($(this).attr('bns-help_button') == 'show') {
                $(this).attr('bns-help_button', 'hide').find('[bns-help_text]').html('Hide Help <i class="fa fa-caret-up"></i>');
                $('.jqs-texelTemplateHelp').css('max-width', 'calc(100% - 20px)');
            } else {
                $(this).attr('bns-help_button', 'show').find('[bns-help_text]').html('Show Help <i class="fa fa-caret-down"></i>');
                $('.jqs-texelTemplateHelp').css('max-width', '270px');
                $('.jqs-texelHelpContainer').addClass('hidden');
            }

            $(window).trigger('resize.texel');
        });

        var leftColumnDesigner;
        var rightColumnDesigner;

        if($('.envDesignerOutsideColumn.leftSide').length == 0) {
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
                    $('<div />').addClass('envDesignerButton hidden').attr('id','designerAddAddressing').on('click.addAddressing', function() {
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
            ).append(
                $('<div />').addClass('envDesignerBottomButton bottomRight envDesignerNewTemplateButton').html('Choose a New Template').on('click', function() {
                    $('#designerContainer').addClass('designerHidden');
                    $('#startDesign').foundation('reveal', 'open');
                })
            );

            $(leftColumnDesigner).insertBefore(texelObject.designerContainer.find('#' + texelObject.designerContainer.attr('id') + '_texel'));
        }

        if($('.envDesignerOutsideColumn.rightSide').length == 0) {
            var rightColumnDesigner = $('<div />').addClass('envDesignerOutsideColumn rightSide').append(
                $('<div />').addClass('envDesignerThumbnailContainer')
            ).append(
                $('<div />').addClass('envDesignerBottomButton bottomLeft envDesignerSaveAndCloseButton').html('Save &amp;<br />Close').on('click', function() {
                    $('#designerContainer').addClass('designerHidden');
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
        $('.envDesignerThumbnailContainer').append(frontThumbnail);
        $('.envDesignerThumbnailContainer').append(backThumbnail);

        $('.designUploadImageButton').off('click.uploadImage').on('click.uploadImage', function() {
            $('[bns-designuploadimage]').trigger('click');
        });

        texelObject._renderDesignerStyling(texelObject.canvas, texelObject.getAttribute('productWidthInches'), texelObject.getAttribute('productHeightInches'));
        texelObject._drawCanvas(texelObject.canvas, texelObject.getAttribute('jsonData')[texelObject.getAttribute('initialLocation')], false);
        texelObject.saveState(texelObject.getAttribute('jsonData'));

        $(window).off('resize.texel').on('resize.texel', function() {
            texelObject._renderDesignerStyling(texelObject.canvas, texelObject.getAttribute('productWidthInches'), texelObject.getAttribute('productHeightInches'));

            waitForFinalEvent(function() {
                texelObject.loadState(texelObject.getAttribute('designerStateCurrentIndex'));
            }, '150', 'adjustDesignerObjectData');
        });
    }, 50);
}

TexelCanvas.prototype = new Texel();

function assign (obj1, obj2) {
    return $.extend({},obj1, obj2);
}

//MOVE THESE TO PRODUCT PAGE OBJECT
//###############################
//###############################
function adjustPageContent() {
    if (window.outerWidth <= 767 && $('.productContentLeft').find('.jqs-longdescParent').length == 0) {
        $('.jqs-longdescParent').addClass('paddingLeftRight20').insertAfter('.productContent');
        $('.jqs-colordescParent').addClass('paddingLeftRight20').insertAfter('.jqs-longdescParent');
        $('.jqs-productSpecs').appendTo('.productContentRight');
    }
    else if (window.outerWidth > 767 && $('.productContentRight').find('.jqs-longdescParent').length == 0) {
        $('.jqs-longdescParent').appendTo('.productContentRight');
        $('.jqs-colordescParent').appendTo('.productContentRight');
        $('.jqs-productSpecs').appendTo('.productContentLeft');
    }



    // if (window.outerWidth > 425 ) {
    //     $('#sidebar-sizeList .colorTextureBodyInner [bns-selection]').each(function() {
    //         if($(this).find('.sizeName').children('.actualSizeName').length > 0) {
    //             $('<br />').insertBefore($(this).find('span.actualSizeName'));
    //         }
    //     });
    // }

    if($('.productContentLeft .jqs-addToCartRow').hasClass('hidden') && $('.productContentRight').css('display') == 'none') {
        $('.productContentLeft .jqs-addToCartRow').removeClass('hidden');
        // Quantity/Price Selection
        var leftSideQuantityPriceSelection = $('<div />').addClass('jqs-quantityPriceSelection margin-top-xs');
        $('.productContentRight .jqs-quantityPriceSelection > *').each(function() {
            leftSideQuantityPriceSelection.append($(this));
        });

        leftSideQuantityPriceSelection.insertAfter('.productImageContainer');

        // Color Selection
        var leftSideColorList = $('<div />').addClass('jqs-colorList margin-top-xs');
        $('.productContentRight .jqs-colorList > *').each(function() {
            leftSideColorList.append($(this));
        });

        leftSideColorList.insertAfter('.productImageContainer');

        $('.productContentLeft .jqs-colorList .selectList.colorSelection').on('click.scrollToColor', function() {
            $(window).scrollTo($(this).offset().top - 50, 500);
        });
        $('.colorList.selectListDropDown .selectList').on('click.scrollToTopAfterColorSelection', function() {
            $(window).scrollTo(100, 500);
        });
    } else if (!$('.productContentLeft .jqs-addToCartRow').hasClass('hidden') && $('.productContentRight').css('display') != 'none') {
        $('.productContentLeft .jqs-colorList .selectList').off('click.scrollToColor');
        $('.colorList.selectListDropDown .selectList').off('click.scrollToTopAfterColorSelection');

        $('.productContentLeft .jqs-addToCartRow').addClass('hidden');
        // Quantity/Price Selection
        $('.productContentLeft .jqs-quantityPriceSelection > *').each(function() {
            $('.productContentRight .jqs-quantityPriceSelection').append($(this));
        });

        $('.productContentLeft .jqs-quantityPriceSelection').remove();

        // Color Selection
        $('.productContentLeft .jqs-colorList > *').each(function() {
            $('.productContentRight .jqs-colorList').append($(this));
        });

        $('.productContentLeft .jqs-colorList').remove();
    }

    if (typeof localStorage.countryGeoId !== 'undefined' && localStorage.countryGeoId != 'US') {
        $('.jqs-pricelist').addClass('otherCountry');
    }
}
function createOrderSamplesEvent() {
    $(document).on('opened.fndtn.reveal', '#orderSamples', function() {
        var sku = $('.jqs-productfeatures > div:first-child > .specsCol2').html();
        if ($('#sampleRow-' + sku).length > 0) {
            $('.jqs-samplesPopupBody').scrollTo(($('#sampleRow-' + sku).offset().top - $('.jqs-samplesPopupBody').offset().top) + $('.jqs-samplesPopupBody').scrollTop());
        }
    });
}
function scrollToSelected(element) {
    var selectListContainer = $('#' + element.attr('data-sidebar-name')).find('.selectListContainer');
    if (selectListContainer.length > 0 && $(selectListContainer).find('.slSelected').length > 0) {
        $(selectListContainer).scrollTop(($(selectListContainer).find('.slSelected').offset().top - $(selectListContainer).offset().top) + $(selectListContainer).scrollTop() - 1);
    }
}

$(function() {
    //call back for order samples images
    $('[data-reveal-id=orderSamples]').on('click', function() {
        hoverImg('orderSamples');
    });

    var defaultQuantityType = $('.jqs-quantityType').html();

    $.each($('.matchingProducts .mpd-itemName'), function() {
        resizeText($(this), $(this).find('p'));
    });

    $('.selectListParent').on('click', function() {
        $('.selectListParent').removeClass('selectListSelected');
        $(this).addClass('selectListSelected');
    });

    $('.jqs-designTemplateHelpHeader').on('click', function() {
        if ($('.jqs-designTemplateHelpContainer').hasClass('hidden')) {
            $(this).children('div:eq(1)').html('Hide Help <i class="fa fa-caret-up"></i>');
            $('.jqs-designTemplateHelpContainer').removeClass('hidden');
        }
        else {
            $(this).children('div:eq(1)').html('Show Help <i class="fa fa-caret-down"></i>');
            $('.jqs-designTemplateHelpContainer').addClass('hidden');
        }
        $(window).trigger('resize');
    });

    $('.jqs-addressingTemplateHelpHeader').on('click', function() {
        if ($('.jqs-addressingTemplateHelpContainer').hasClass('hidden')) {
            $(this).children('div:eq(1)').html('Hide Help <i class="fa fa-caret-up"></i>');
            $('.jqs-addressingTemplateHelpContainer').removeClass('hidden');
        }
        else {
            $(this).children('div:eq(1)').html('Show Help <i class="fa fa-caret-down"></i>');
            $('.jqs-addressingTemplateHelpContainer').addClass('hidden');
        }
        $(window).trigger('resize');
    });

    $(window).on('resize', function() {
        if(!ignoreIE()) {
            waitForFinalEvent(function() {
                productPage.adjustProductNameSize();
                adjustPageContent();
            }, 250, 'adjustPageContent');
        }
    });

    //analytics for cross sell
    $('.jqs-cross-sells').find('a').on('click', function (e) {
        e.preventDefault();
        GoogleAnalytics.trackEvent('Product Page v2', 'Cross-Sell', $(this).attr('data-sku'));
        window.location.href = $(this).attr('href');
    });

    if (window.location.href.match(/(?:\?|\&)ss=true/) != null) {
        $('.jqs-orderSamplesQuickLink').trigger('click');
    }

    $('.jqs-optTestDesignsLeftSide .jqs-optTestDesignsLeftSideTemplate').css({
        'color': '#' + (isColorAllowed(productPage.returnActiveProduct(true).hex, '000000') ? '000000' : 'ffffff')
    });

    $('.jqs-variabledatagrid').off('click.variableDataGrid').on('click.variableDataGrid', function() {
        productPage.createAddressingGrid(-1, JSON.parse($('[bns-address_font_list]').find('.selected').attr('data-variable-style')));
        productPage.showGrid($('.jqs-addressOptions .selected').attr('data-addressing-option'));
    });

    /*
        adjustColorDropdown();

        function adjustColorDropdown() {
            $('[data-color-groups*="Yellow"]').each(function() {
                $(this).attr('data-color-groups', $(this).attr('data-color-groups').replace('Yellow', 'Gold'));
            });

            $('[data-color-group="Yellow"]').parent().remove();

            $('[data-color-groups*="Gray"]').each(function() {
                $(this).attr('data-color-groups', $(this).attr('data-color-groups').replace('Gray', 'Silver'));
            });

            $('[data-color-group="Gray"]').parent().remove();

            var parentElement = $('.colorFilterSlideIt > div');
            parentElement.prepend($('[data-color-group="Green"]').parent());
            parentElement.prepend($('[data-color-group="Red"]').parent());
            parentElement.prepend($('[data-color-group="Gold"]').parent());
            parentElement.prepend($('[data-color-group="Silver"]').parent());
            parentElement.prepend($('[data-color-group="White"]').parent());
        }
        */
    createOrderSamplesEvent();
    adjustPageContent();
});

/* ############################################################
 * Dependencies
 * jQuery
 * waitForFinalEvent function (global.js)
 * formatCurrency function (global.js)
 */

/**
 * Envelopes.com Product Page
 * @constructor
 */
function EnvProductPage() {
    /**
     * Array of all products on the page
     * Sequence of products are the sequence in which they should be displayed
     * @type {Array}
     */
    var products = [];
    this.getProducts = function() {
        return products;
    };
    this.setProducts = function(data) {
        products = data;
    };

    this.projectId = null;
    this.activeProductIndex = 0;
    this.firstToggle = true;
    this.productName = 'jqs-productname';
    this.colorName = 'jqs-colorname';
    this.inventoryDom = 'jqs-inventoryDom';
    this.getItQuick = 'jqs-getItQuick';
    this.templateGrid = 'jqs-templates';
    this.addressGrid = 'jqs-address';
    this.addressOptions = 'jqs-addressOptions';
    this.heroimage = 'jqs-imagehero';
    this.assetGrid = 'jqs-prodassets';
    this.featuresGrid = 'jqs-productfeatures';
    this.priceGrid = 'jqs-pricelist';
    this.selQty = 'jqs-selQty';
    this.selPrice = 'jqs-selPrice';
    this.originalPriceDisplay = 'jqs-originalPriceDisplay';
    this.savingsDisplay = 'jqs-savingsDisplay';
    this.shipNotice = 'jqs-freeshipnote';
    this.fileGrid = 'jqs-filecontainer';
    this.reuseId = 'startReuse';
    this.newreuseId = 'startUpload';
    this.reuseContent = 'jqs-reusehistory';
    this.reusedFiles = 'jqs-reusedfiles';
    this.uploadedFiles = 'jqs-uploadedfiles';
    this.variableDataGrid = 'jqs-variabledatagrid';
    this.addAddress = 'jqs-addaddresses';
    this.customQty = 'jqs-number';
    this.printDesc = 'jqs-printDesc';
    this.starRating = 'jqs-starRating';
    this.noReview = 'jqs-noreview';
    this.hasReview = 'jqs-hasreview';
    this.longDesc = 'jqs-longdesc';
    this.colorDesc = 'jqs-colordesc';
    this.brandDesc = 'jqs-brand';
    this.newRibbon = 'jqs-new';
    this.standardDelDate = 'jqs-standarddel';
    this.rushDelDate = 'jqs-rushdel';
    this.designNow = 'jqs-designnow';
    this.closeModal = 'jqs-closemodal';
    this.comments = 'jqs-itemcomments';
    this.productFilter = 'jqs-productFilter';
    this.qtyWarning = 'jqs-qlError';
    this.cart = 'jqs-addToCart';
    this.filterOption = 'jqs-filterOption';
    this.imageSelector = 'imageSelection';
    this.orderSamplesQuickLink = 'jqs-orderSamplesQuickLink';
    this.plainShipTime = 'jqs-plainShipTime';
    this.printedShipTime = 'jqs-printedShipTime';
    this.partyId = null;
    this.designs = [];
}

EnvProductPage.prototype = {
    constructor: EnvProductPage,
    /**
     * Create a new Product object and insert into array
     * @param id
     */
    addProduct: function(data) {
        var product = new EnvProduct();
        $.extend(true, product.getProduct(), data);
        product.bindAddressingTemplates();
        this.getProducts().push(product);
    },
    returnActiveProduct: function(dataOnly) {
        if(dataOnly) {
            return this.getProducts()[this.activeProductIndex].getProduct();
        } else {
            return this.getProducts()[this.activeProductIndex];
        }
    },
    /**
     * Remove a Product object from the array
     * @param id
     */
    removeProduct: function(id) {
        for(var i = 0; i < this.getProducts().length; i++) {
            if(this.getProducts()[i].returnProductId() == id) {
                //reset the active product the previous product if a product is removed unless it was the first product
                if(this.activeProductIndex != 0 && this.activeProductIndex >= i) {
                    this.activeProductIndex--;
                }

                this.getProducts().splice(i);
                break;
            }
        }
    },
    /**
     * Update the product page image based on the currently selected product
     */
    updateImage: function(source) {
        var self = this;
        waitForFinalEvent(function () {
            //show the image based on the activeProductIndex

            $('.' + self.heroimage).attr('src', source);
            if(source.indexOf('base64') == -1) {
                if(source.indexOf('hei=') != -1) {
                    source = source.match(/(.*?hei=)\d+(.*)/);
                    $('.jqs-enhancedImage').attr('src', source[1] + '600' + source[2]);
                } else {
                    $('.jqs-enhancedImage').attr('src', source + '&hei=600');
                }
            } else {
                $('.jqs-enhancedImage').attr('src', source);
            }
        }, 'updateImage', 50);
    },
    /**
     * Start the next product in the sequence
     */
    goToNextProduct: function() {
        if(this.activeProductIndex < (this.getProducts().length - 1)) {
            this.activeProductIndex++;
        }
    },
    /**
     * Go back to previous product in the sequence
     */
    goToPreviousProduct: function() {
        if(this.activeProductIndex > 0) {
            this.activeProductIndex--;
        }
    },
    createAddressingGrid: function (dataGroupId, font) {
        var self = this;
        var gridData = [[]];
        var method = 'manual';
        var partyId = productPage.getProducts()[0].getProduct().partyId;

        var gridOptions = $.extend({
            gridData: typeof gridData != 'undefined' ? gridData : [[]],
            partyId: typeof partyId === 'undefined' || partyId == null ? '' : partyId,
			sessionId: envSession,
            dataGroupId: dataGroupId,
            onGridApply: function (event, data) {
                GoogleAnalytics.trackEvent('Product Page v2', 'Addressing', 'Launched');
                
                if (productPage.getActiveDesign() == null) {
                    productPage.loadNewTexel(designData[$('[bns-design][data-design_type="Start From Scratch"]').attr('data-design')], true);
                } else {
                    productPage.loadExistingTexel(true);
                }

                productPage.getActiveDesign().updateAddressing(data.data, font);

                productPage.getProducts()[0].getProduct().dataGroupId = data.dataGroupId;
                productPage.getProducts()[0].getProduct().addressingData = data;
                self.updateAddressingButton(data.data.length);
                productPage.returnActiveProduct(false).calculatePrice(null);
            },
            onGridClose: function (event, data) {
                // Not sure if we will use this one day.
            },
            dataGroupMode: method
        }, {
            responsive: true,
            debug: false
        });

        $('body').append(
            $('<div />').addClass('gridOverlayBg grid-hide')
        ).append(
            $('<div/>').addClass('gridOverlay reset-css grid-hide').append(
                $('<div/>').attr('id', 'env-variabledata-grid')
            )
        );

        return $('#env-variabledata-grid').VariableDataGrid(gridOptions);
    },
    showGrid: function (method, font, index) {
        // $(window).scrollTop(0);
        if ($('#env-variabledata-grid').length > 0) {
            $('#env-variabledata-grid').VariableDataGrid('showGrid', method);
            $('#startAddressing').foundation('reveal', 'close');
        } else {
            $('#startAddressing').foundation('reveal', 'open');
        }
    },
    updateDesignButton: function(close) {
        if(close) {
            $('#startDesign').foundation('reveal', 'close');
        }
    },
    updateAddressingButton: function(totalAddresses) {
        if(totalAddresses > 0) {
            $('.' + this.addAddress).find('[bns-recipientaddresscount]').attr('bns-recipientaddresscount', totalAddresses).removeClass('hidden').html(totalAddresses + ' Address' + (totalAddresses > 1 ? 'es' : '') + ' Added').append(
                $('<span />').html('Edit')
            ).parent().removeAttr('data-reveal-id').parents('[bns-customization]').removeClass('hidden');
            $('[data-key=addresses][data-holder]').attr('data-total', totalAddresses);
        } else {
            $('[bns-recipientaddresscount]').html('');
            $('[data-key=addresses][data-holder]').attr('data-total', 0);
        }
    },
    loadDesignOrUploadAssets: function(type) {
        var self = this;

        switch(type) {
            case 'design':
                $('[bns-editdesign].jqs-designnow').removeClass('hidden');
                self.returnActiveProduct(true).designMethod = 'online';
                break;
            case 'upload':
                $('[bns-editdesign].jqs-designnow').removeClass('hidden').addClass('hidden');
                self.returnActiveProduct(true).designMethod = 'upload';
                $('[data-options=upload]').removeClass('hidden');
                break;
        }
    },
    /**
     * This will toggle the main selectors on and off, if noSelection is true, this option allows no selections to be made
     * Will return true if a change in selection is made, else false
     * @param dataKey
     * @param dataValue
     * @param obj
     * @param noSelection
     * @returns {boolean}
     */
    toggleSelector: function(dataKey, dataValue, obj, noSelection) {
        var currentlySelected = $('[data-key=' + dataKey + '].optionSelected').attr('data-value');

        function scrollToButton(obj) {
            $.scrollTo($(obj), 500, {offset:-250});
        }

        if(currentlySelected != dataValue) {
            $('[data-key=' + dataKey + ']').removeClass('.optionSelected').attr('data-selected', 'false');
            $(obj).addClass('optionSelected').attr('data-selected', 'true');

            scrollToButton(obj);
            return true;
        } else if(currentlySelected == dataValue && noSelection) {
            $('[data-key=' + dataKey + ']').removeClass('optionSelected');
            $(obj).removeClass('optionSelected').attr('data-selected', 'false');

            scrollToButton(obj);
            return true;
        }

        return false;
    },
    //bind file uploading to product
    //###########################################
    //##############BEGIN FILE UPLOAD############
    //###########################################
    createFileList: function(fileList) {
        var self = this;
        $('.' + self.uploadedFiles).removeClass('hidden').empty();

        var completedFileArray = [];
        var myList = $('<ul />').addClass('text-left no-margin margin-left-xs');

        $.each(fileList, function() {
            completedFileArray.push( { 'name' : $(this).attr('data-filename'), 'path' : $(this).attr('data-filepath') } );

            myList.append(
                $('<li />').append(
                    'Added ' + $(this).attr('data-filename')
                ).append(
                    $('<i/>').addClass('fa fa-trash-o').on('click', function() {
                        self.returnActiveProduct(true).files.splice($(this).parent().index(), 1);
                        $(this).parent().remove();
                        if(self.returnActiveProduct(true).files.length == 0) {
                            $('.' + self.uploadedFiles).removeClass('hidden').addClass('hidden');
                        }
                    })
                )
            );
        });

        if (completedFileArray.length > 0) {
            $('.' + self.uploadedFiles).append(myList);

            $('.' + self.uploadedFiles).append(
                $('<div />').html('After checkout, you\'ll receive an emailed proof within 24 hours.  Once you approve your proof, your order will enter print production.')
            );
        }

        self.returnActiveProduct(true).files = completedFileArray;
    },
    loadExistingTexel: function(addressingOnly, show) {
        var self = this;

        if (typeof self.designs == 'object' && self.designs.length > 0) {
            $(window).trigger('resize.texel');
            if (typeof show == 'undefined' || show) {
                GoogleAnalytics.trackEvent('Product Page v2', 'Designer', 'Launched');
                $('#designerContainer').removeClass('designerHidden');
            }
            if (typeof addressingOnly == 'undefined' || !addressingOnly) {
                $('[bns-load_design]').removeClass('hidden').html('Edit Your Design <i class="fa fa-caret-right');
            }
        } else {
            $('#startDesign').foundation('reveal', 'open');
        }
    },
    loadNewTexel: function(data, addressingOnly, show) {
        var self = this;

        if (typeof data == 'object') {
            for (var i = 0; i < self.designs.length; i++) {
                self.designs[i].active = false;
            }

            self.designs.push({
                'active': true,
                'productDesign': new TexelCanvas('designerContainer', assign(data, {
                    'backgroundColor': self.returnActiveProduct(true).hex,
                    'location': (typeof data.location != 'undefined' ? data.location : 'front')
                }))
            });

            if (typeof show == 'undefined' || show) {
                GoogleAnalytics.trackEvent('Product Page v2', 'Designer', 'Launched');
                $('#designerContainer').removeClass('designerHidden');
            }

            self.designs[self.designs.length - 1].productDesign.addUpdateEvent('productColors', function() {
                var productDesign = self.getActiveDesign();
                var jsonData = productDesign.getAttribute('designerState')[productDesign.getAttribute('designerStateCurrentIndex')].jsonData;
                var totalColorsMap = {};
                var sides = [
                    {
                        'productKey': 'colorsFront',
                        'productWhiteInkKey': 'whiteInkFront',
                        'designerKey': 'front'
                    }, {
                        'productKey': 'colorsBack',
                        'productWhiteInkKey': 'whiteInkBack',
                        'designerKey': 'back'
                    }
                ];

                for (var i = 0; i < sides.length; i++) {
                    var colorArray = [];
                    var hasImage = false;

                    for (var j = 0; j < jsonData[sides[i].designerKey].length; j++) {
                        if ((jsonData[sides[i].designerKey][j].type == 'textBox' || jsonData[sides[i].designerKey][j].type == 'textBoxAddressing') && $.inArray(jsonData[sides[i].designerKey][j].fill, colorArray) == -1) {
                            colorArray.push(jsonData[sides[i].designerKey][j].fill);
                        } else if (jsonData[sides[i].designerKey][j].type == 'image') {
                            hasImage = true;
                        }
                    }

                    var totalColors = (colorArray.length >= 3 ? 4 : colorArray.length);

                    if (hasImage && totalColors < 2) {
                        totalColors = 2;
                    }

                    $('[bns-selection][data-key="' + sides[i].productKey + '"][data-value="' + totalColors + '"]').trigger('click');

                    if ($.inArray('#FFFFFF', colorArray) >= 0) {
                        $('[bns-selection][data-key="' + sides[i].productKey + '"][data-value="' + sides[i].productWhiteInkKey + '"]').trigger('click');
                    }

                    self.getActiveDesign().designerContainer.find('#designerAddAddressing').removeClass('hidden');

                    if (!self.returnActiveProduct(true).hasAddressingAbility) {
                        self.getActiveDesign().designerContainer.find('#designerAddAddressing').addClass('hidden');
                    }

                    totalColorsMap[sides[i].designerKey] = totalColors;
                }

                self.returnActiveProduct(true).s7Data = {};

                for (var key in productDesign.getAttribute('designerState')[productDesign.getAttribute('designerStateCurrentIndex')].jsonData) {
                    self.returnActiveProduct(true).s7Data[key] = productDesign.getAttribute('designerState')[productDesign.getAttribute('designerStateCurrentIndex')].jsonData[key];
                }

                self.returnActiveProduct(true).s7Data.scene7Data = {
                    designData: {
                        userImages: productDesign.getCleanUserImages(),
                        width: productDesign.getAttribute('productWidthInches'),
                        height: productDesign.getAttribute('productHeightInches'),
                        jsonData: productDesign.getAttribute('designerState')[productDesign.getAttribute('designerStateCurrentIndex')].jsonData
                    }
                }

                var locationToShow = totalColorsMap.back > totalColorsMap.front ? 'back' : 'front';

                $('.jqs-imagehero').attr('src', productPage.getActiveDesign().getAttribute('designerState')[productPage.getActiveDesign().getAttribute('designerStateCurrentIndex')].imageData[locationToShow]);
            });

            $('#startDesign').foundation('reveal', 'close');

            if (typeof addressingOnly == 'undefined' || !addressingOnly) {
                $('[bns-load_design]').removeClass('hidden').html('Edit Your Design <i class="fa fa-caret-right');
            }
        }
    },
    /**
     * Bind all the click events on the product page for options
     */
    getActiveDesign: function(getDesignOnly) {
        var self = this;

        if (typeof self.designs == 'object') {
            for (var i = 0; i < self.designs.length; i++) {
                if (self.designs[i].active) {
                    if (typeof getDesignOnly == 'undefined' || getDesignOnly) {
                        return self.designs[i].productDesign;
                    } else {
                        return self.designs[i];
                    }
                }
            }
        }

        return null;
    },
    bindClickEvents: function() {
        var self = this;

        $('.jqs-customizeTemplateButton').off('click.customizeTemplateButton').on('click.customizeTemplateButton', function() {
            $('.jqs-editDesign').removeClass('hidden');
            $('.jqs-editDesign .jqs-designnow').removeAttr('data-reveal-id');
            $('[bns-editdesign].jqs-designnow').removeClass('hidden').removeAttr('data-reveal-id').html('Edit Your Design ').append(
                $('<i />').addClass('fa fa-caret-right')
            );
            $('.jqs-editUploadedFiles').removeClass('hidden').addClass('hidden');
            $('[data-key="plainOrPrinted"][data-additionalkey="designOrUpload"][data-value="printed"][data-additionalvalue="design"]').removeAttr('data-reveal-id');
            $('#texel_' + self.activeProductIndex).texel('destroyTexel');//remove();
            delete self.designs;

            if(self.toggleSelector('designOrUpload', 'design', this, false)) {
                GoogleAnalytics.trackEvent('Product Page v2', 'Design or Upload', $().capitalize('design'));
                self.loadDesignOrUploadAssets('design');
            }
        });

        $('[bns-addreturnaddress]').off('click.addReturnAddress').on('click.addReturnAddress', function() {
            if ($(this).attr('data-selected') != 'true') {
                if ($('.jqs-templates > *').length == 0) {
                    self.returnActiveProduct(false).getProductTemplates(true);
                }

                $('.jqs-templates [data-templatetype="RETURN"]').trigger('click');
                $('.jqs-customizeTemplateButton').trigger('click');
            } else {
                $('.jqs-designnow').trigger('click.designNow');
            }
        });

        $('[bns-design]').off('click.loadNewDesign').on('click.loadNewDesign', function() {
            self.loadNewTexel(designData[$(this).attr('data-design')]);
        });

        $('[bns-load_design]').off('click.loadDesign').on('click.loadDesign', function() {
            self.loadExistingTexel();
        });
        /*
                $('[bns-designnow]').off('click.designNow').on('click.designNow', function() {
                    var texelDiv = $('#texel_' + self.activeProductIndex);

                    if ($(this).attr('data-selected') != 'true' || (texelDiv.length == 0 && self.returnActiveProduct(true).design == null )) {
                        $('#startDesign').foundation('reveal', 'open');
                    } else {
                        $('.jqs-designnow').trigger('click.designNow');
                    }
                });
        */
        $('.jqs-uploadPopupButton').off('click.uploadPopupButton').on('click.uploadPopupButton', function() {
            $(window).scrollTop(0);
            $('.jqs-editDesign').removeClass('hidden').addClass('hidden');
            $('.jqs-editUploadedFiles').removeClass('hidden');
            $('#texel_' + self.activeProductIndex).remove();

            self.loadDesignOrUploadAssets('upload');
        });

        //###########################################
        //################BEGIN RE USE###############
        //###########################################
        function createReuseFileList(fileList) {
            $('.' + self.reusedFiles).removeClass('hidden').empty();
            var completedFileArray = [];
            $.each(fileList, function() {
                completedFileArray.push( { 'name' : $(this).attr('data-filename'), 'path' : $(this).attr('data-filepath'), 'order' : $(this).attr('data-order'), 'orderItem' : $(this).attr('data-order-item') } );
                $('.' + self.reusedFiles).append('Reuse from ' + $(this).attr('data-order')).append($('<i/>').addClass('fa fa-trash-o').on('click', function() {
                    self.returnActiveProduct(true).oldFiles.splice(0, 1);
                    $(this).parent().empty();
                    if(self.returnActiveProduct(true).oldFiles.length == 0) {
                        $('.' + self.reusedFiles).removeClass('hidden').addClass('hidden');
                    }
                }));
            });

            self.returnActiveProduct(true).oldFiles = completedFileArray;
        }

        function getReuseInks(orderId, orderItemSeqId) {
            $.ajax({
                type: "GET",
                url: '/' + websiteId + '/control/getOrderPrintOptions',
                timeout: 5000,
                data: {
                    'orderId': orderId,
                    'orderItemSeqId': orderItemSeqId
                },
                dataType: 'json',
                cache: true,
                async: false
            }).done(function(data) {
                if(data.success && typeof data.printOptions !== 'undefined') {
                    $('[data-key=colorsFront][data-value=0]').trigger('click');
                    $('[data-key=colorsBack][data-value=0]').trigger('click');
                    $.each(data.printOptions, function(k, v) {
                        if(k == 'whiteInkFront') {
                            if(v == 'false') {
                                return true;
                            }
                            k = 'colorsFront';
                            v = 'whiteInkFront';
                        } else if(k == 'whiteInkBack') {
                            if(v == 'false') {
                                return true;
                            }
                            k = 'colorsBack';
                            v = 'whiteInkBack';
                        }

                        $.each($('[data-key=' + k + ']'), function() {
                            if($(this).attr('data-value') == v) {
                                $(this).trigger('click');
                            }
                        });
                    });
                }
            });
        }

        function loadReuseData() {
            $('#' + self.reuseId).foundation('reveal', 'open');
            if(typeof $.cookie('__ES_ll') !== 'undefined' && $.cookie('__ES_ll') != 'false') {
                $.ajax({
                    type: "GET",
                    url: '/' + websiteId + '/control/getOrderAndContent',
                    data: {
                        contentEnumTypeId: 'OIACPRP_PDF'
                    },
                    dataType: 'json',
                    cache: true
                }).done(function(data) {
                    if(data.success && typeof data.orderAndContent !== 'undefined') {
                        $('#' + self.reuseId).find('.not-logged-in').addClass('hidden');
                        $('.' + self.reuseContent).append($('<div/>').addClass('historyHeader').html('Order History'));

                        var myList = $('<ol />').addClass('historyBody');

                        $.each(data.orderAndContent, function(i, v) {
                            $.each(v.orderItemContents, function(i2, v2) {
                                myList.append(
                                    $('<li/>').addClass('historyRow').append(
                                        $('<div/>').addClass('historyCount').append(
                                            $('<input/>').attr({ 'data-filename': v2.contentName, 'data-filepath': v2.contentPath, 'data-order': v.orderId, 'data-order-item': v2.orderItemSeqId }).addClass('no-margin height-auto').attr({ 'type': 'radio', 'name': 'reuseFiles', 'id': v.orderId + '_' + v2.orderItemSeqId }).on('click', function() {
                                                getReuseInks(v.orderId, v2.orderItemSeqId);
                                                createReuseFileList($(this));
                                            })
                                        )
                                    ).append(
                                        $('<div/>').addClass('historyDate').html(getFormatedDate(v2.createdStamp, true))
                                    ).append(
                                        $('<div/>').addClass('historyOrderNumber').html(v.orderId)
                                    ).append(
                                        $('<div/>').addClass('historyPreview').html('<a target="_blank" href="/' + websiteId + '/control/downloadFile?filePath=' + v2.contentPath + '&downLoad=Y">Preview</a>')
                                    ).append(
                                        $('<div/>').addClass('historyDescription').html(((v2.itemJobName != null) ? v2.itemJobName : v2.itemDescription) + ' - ' + v2.contentName)
                                    )
                                )
                            });
                        });

                        $('.' + self.reuseContent).append(myList);
                    } else {
                        //TODO throw error
                    }
                });
            } else {

            }
        }

        $('#' + self.reuseId).find('.not-logged-in').off('click.reuseArtwork').on('click.reuseArtwork', function() {
            $('.jqs-hidden-login-button').trigger('click', loadReuseData);
        });

        $('[data-reveal-id=' + self.reuseId + ']').off('click.reuseArtwork').on('click.reuseArtwork', function() {
            if(!$('#' + self.reuseId).find('.not-logged-in').hasClass('hidden')) {
                loadReuseData();
            }
        });
        //###########################################
        //################END RE USE#################
        //###########################################

        $('.dropzone').off('click.dropZone').on('click.dropZone', function() {
            $('.jqs-fileupload').trigger('click');
        });
        if($.fn.fileupload) {
            $('#uploadScene7Files').fileupload({
                url: '/' + websiteId + '/control/uploadScene7Files',
                dataType: 'json',
                dropZone: $('.' + self.fileGrid).find('.dropzone'),
                sequentialUploads: true,
                add: function (e, data) {
                    //remove the placeholder div
                    $('.dropzone').removeClass('placeholder');

                    //create the file progress
                    var fileDiv = $('<div/>').addClass('text-size-md relative design-file inprogress').append(
                        $('<div/>').addClass('absolute progress')
                    ).append(
                        $('<span/>').addClass('padding-left-xxs relative').append(
                            $('<i/>').addClass('fa fa-file-photo-o')
                        )
                    ).append(
                        $('<span/>').addClass('relative filename').html(data.files[0].name)
                    ).append(
                        $('<span/>').addClass('absolute remove action').append(
                            $('<i/>').addClass('fa fa-trash-o')
                        )
                    ).attr('data-filename', data.files[0].name);

                    //create the delete action
                    fileDiv.find('span.action').on('click', function (e) {
                        if (fileDiv.hasClass('inprogress')) {
                            jqXHR.abort();
                        }
                        fileDiv.fadeOut().remove();

                        self.createFileList($('.dropzone > div[data-filename]'));
                    });

                    data.context = fileDiv.appendTo($('.' + self.fileGrid).find('.dropzone'));
                    $('.' + self.fileGrid).find('[bns-removeonupload]').remove();
                    var jqXHR = data.submit();
                },
                progress: function (e, data) {
                    // Calculate the completion percentage of the upload
                    var progress = parseInt(data.loaded / data.total * 100, 10);
                    if (progress == 100) {
                        $('.' + self.fileGrid).find('.dropzone').find('[data-filename="' + data.files[0].name + '"]').first().removeClass('inprogress').children('div.progress').fadeOut();
                    } else {
                        $('.' + self.fileGrid).find('.dropzone').find('[data-filename="' + data.files[0].name + '"]').first().children('div.progress').width(progress + '%');
                    }
                },
                done: function (e, data) {
                    if (data.result.success) {
                        //add the path to the dom data for that line
                        $.each(data.result.files, function (idx) {
                            $('.' + self.fileGrid).find('.dropzone').find('[data-filename="' + data.result.files[idx].name + '"]').attr('data-filepath', data.result.files[idx].path);
                        });
                        self.createFileList($('.dropzone > div[data-filename]'));
                    }
                },
                fail: function (e, data) {
                    $.each(data.files, function (idx, el) {
                        $('.' + self.fileGrid).find('.dropzone').find('[data-filename="' + data.files[idx].name + '"]').first().children('div.progress').first().find('i.fa').removeClass('fa-trash-o').addClass('fa-warning');
                    });
                }
            });
        }
        //###########################################
        //################END FILE UPLOAD############
        //###########################################

        /**
         * Custom qty lookup
         */
        $('.' + this.customQty).on('keydown paste', function() {
            var cqty = this;
            waitForFinalEvent(function () {
                var quantity = $(cqty).val().replace(/\D/g,'');
                if(quantity != '') {
                    var warningBox = $('.' + self.qtyWarning);

                    quantity = parseInt(quantity);
                    if(quantity%self.returnActiveProduct(false).smallestQuantity == 0 && quantity <= 500000) {
                        warningBox.fadeOut('slow');
                        self.returnActiveProduct(false).calculatePrice(parseInt(quantity));
                    } else if(quantity > 5 && quantity < self.returnActiveProduct(false).smallestQuantity) {
                        warningBox.removeClass('hidden').fadeIn('slow', function () {
                            $(this).html('You must enter a quantity higher than ' + self.returnActiveProduct(false).smallestQuantity + '.');
                        });
                    } else if(quantity > 500000) {
                        warningBox.removeClass('hidden').fadeIn('slow', function() {
                            $(this).html('Please call customer service to get a quote.');
                        });
                    } else if(quantity%self.returnActiveProduct(false).smallestQuantity != 0) {
                        warningBox.removeClass('hidden').fadeIn('slow', function() {
                            $(this).html('Your quantity must be in increments of ' + self.returnActiveProduct(false).smallestQuantity + '.')
                        });
                    }
                }
            }, 250, 'customQty');
        });

        if (window.location.href.match(/(?:\?|\&)ss=true/) != null) {
            $('.' + self.orderSamplesQuickLink).trigger('click');
        }

        /**
         * addToCart
         */
        $('.' + self.cart).off('click.addToCart').on('click.addToCart', function(e) {
            self.addToCart(this);
        });

        //close modals
        $('.' + self.closeModal).on('click', function() {
            $(this).closest('.reveal-modal').foundation('reveal', 'close');
        });

        //bind comments for item
        $('.' + self.comments).on('keyup blur', function() {
            self.returnActiveProduct(true).comments = ($(this).val() == '') ? null : $(this).val();
        });

        // Bind filter type selection.
        $('.jqs-filterOption').on('click', function() {
            if (!$(this).hasClass('filterSelected')) {
                $('.jqs-clearProductFilter').trigger('click');
                $(this).addClass('filterSelected').siblings().removeClass('filterSelected');

                if ($(this).attr('data-filter-to-show') == 'saleFilter') {
                    $('[data-percent-savings="0"]').removeClass('hidden').addClass('hidden');
                    $('.jqs-collectionFilter, .jqs-colorFilter').removeClass('hidden').addClass('hidden');
                    $('.jqs-clearProductFilter').removeClass('hidden');
                }
                else {
                    $('.jqs-' + $(this).attr('data-filter-to-show')).removeClass('hidden').siblings().addClass('hidden');
                    slideIt_init($('.jqs-' + $(this).attr('data-filter-to-show') + ' .slideIt'));
                }

                slideIt_init();
            }
        });

        self.bindUpdateSelection();
    },
    bindUpdateSelection: function() {
        var self = this;
        //close select boxes
        $('.jqs-selectList').off('click.updateSelection').on('click.updateSelection', function(e, ignoreAction) {
            if (!$(this).hasClass('slSelected')) {
                self.updateSelection($(this), ignoreAction);
                hideAllDropDown(0, true);
            }
        });
    },
    /* Update Color Filter when changing size or style */
    updateColorFilter: function() {
        var availableColors = [];
        var availableCollections = [];

        $('[bns-colorlistoptioncontainer] [bns-selection]').each(function() {
            if (typeof $(this).attr('data-color-groups') != 'undefined') {
                if($.inArray($(this).attr('data-color-groups'), availableColors) == -1) {
                    availableColors.push($(this).attr('data-color-groups'));
                }
            }
            if (typeof $(this).attr('data-collection-groups') != 'undefined') {
                if($.inArray($(this).attr('data-collection-groups'), availableCollections) == -1) {
                    availableCollections.push($(this).attr('data-collection-groups'));
                }
            }
        });

        $('[bns-colorfilterlist] > *').remove();

        for (var x = 0; x < availableColors.length; x++) {
            $('[bns-colorfilterlist]').append(
                $('<div />').append(
                    $('<div />').attr('data-color-group', availableColors[x])
                )
            );
        }

        $('[bns-collectionfilterlist] > *').remove();

        for (var x = 0; x < availableCollections.length; x++) {
            $('[bns-collectionfilterlist]').append(
                $('<div />').addClass('text-center').attr('data-collection-group', availableCollections[x]).html(availableCollections[x]).prepend(
                    $('<img />').attr({
                        src: '/html/img/product/collections/' + availableCollections[x].replace(/[^a-zA-Z0-9]/g, '') + '.png',
                        alt: availableCollections[x]
                    })
                )
            );
        }

        //bind color filter
        $('[bns-colorfilterlist] [data-color-group], ' + '[bns-collectionfilterlist] [data-collection-group], .jqs-clearProductFilter').on('click', function(){
            var groupName = $(this)[0].hasAttribute('data-color-group') ? 'data-color-group' : 'data-collection-group';
            var clickedColor = typeof $(this).attr(groupName) == 'undefined' ? '' : $(this).attr(groupName);

            $('.cfSelected').removeClass('cfSelected');
            $('[' + groupName + 's]').removeClass('hidden');
            $('.jqs-clearProductFilter').removeClass('hidden').addClass('hidden');

            if ($('.jqs-filterOption.filterSelected').attr('data-filter-to-show') == 'saleFilter') {
                $('.jqs-filterOption').removeClass('filterSelected');
            }

            if(clickedColor != '') {
                $('.jqs-clearProductFilter').removeClass('hidden');
                $(this).addClass('cfSelected');
                $.each($('[' + groupName + 's]'), function() {
                    if($(this).attr(groupName + 's').indexOf(clickedColor) == -1) {
                        $(this).addClass('hidden');
                    }
                });
            }
        });
    },
    popState: function() {
        var self = this;

        history.replaceState({'style' : self.returnActiveProduct(true).style, 'category' : self.returnActiveProduct(true).category, 'size' : self.returnActiveProduct(true).size, 'actualSize' : self.returnActiveProduct(true).actualSize, 'productid' : self.returnActiveProduct(true).productId, 'productTypeId' : self.returnActiveProduct(true).productTypeId}, document.title, window.location.href);
        window.addEventListener('popstate', function(e) {
            var data = e.state;
            if (data != null) {
                if (self.returnActiveProduct(true).style != data.style) {
                    buildStyleList(data.size, data.style, true);
                    $('[bns-selection][data-target="styleSelection"][data-value="' + data.style + '"]').trigger('click.updateSelection', {updateURL: true});
                }

                if (self.returnActiveProduct(true).size != data.size) {
                    buildSizeList(data.size, data.style, true);
                    $('[bns-selection][data-target="sizeSelection"][data-value="' + data.size + '"]').trigger('click.updateSelection', {updateURL: true});
                }

                if (self.returnActiveProduct(true).size != data.size || self.returnActiveProduct(true).style != data.style) {
                    buildColorList(data.productid, true);
                }

                $('[bns-selection][data-target="colorSelection"][data-value="' + data.productid + '"]').trigger('click.updateSelection', {colorList: true, updateURL: true});
            }
        });
    },
    /**
     * Update selection for select list
     */
    updateSelection: function(element, ignoreAction) {
        var self = this;
        var ignoreAction = typeof ignoreAction == 'undefined' ? {}: ignoreAction;

        var continueUpdateSelection = function() {
            var dataKey = element.attr('data-key');
            var dataValue = element.attr('data-value');

            $(element).siblings().removeClass('slSelected').attr('data-selected', 'false');
            $(element).addClass('slSelected').attr('data-selected', 'true');

            var asset = $('.' + $(element).attr('data-target')).find('.slDownArrow');
            $('[bns-sidebarid="' + $(element).attr('data-target') + '"], #' + $(element).attr('data-target')).each(function() {
                $(this).html($(element).html()).append(asset);
                $(this).find('[selection-removeonselect]').remove();
            });

            if ($(element).attr('data-target') == 'printingOption') {
                if (typeof dataKey != 'undefined') {
                    if (dataKey == 'plainOrPrinted') {
                        $("[bns-return_address_content]").removeClass("hidden").addClass("hidden");
                        
                        if(dataValue == 'plain') {
                            $('[bns-load_design]').removeClass('hidden').addClass('hidden');
                            $('.jqs-longdescParent').appendTo('.productContentRight');
                            $('.jqs-colordescParent').appendTo('.productContentRight');
                            $('[bns-editdesign].jqs-designnow').removeClass('hidden').addClass('hidden');
                            if (self.returnActiveProduct(true).hasSample) {
                                $('.' + self.orderSamplesQuickLink).removeClass('hidden');
                                $('.samplesCheckboxContainer').removeClass('hidden');
                            }
                            self.updateImage('//actionenvelope.scene7.com/is/image/ActionEnvelope/' + self.returnActiveProduct(true).productId + '?hei=413&fmt=png-alpha&align=0,-1');
                        } else {
                            if (self.returnActiveProduct(true).hasSample) {
                                $('.' + self.orderSamplesQuickLink).removeClass('hidden').addClass('hidden');
                                $('.samplesCheckboxContainer').removeClass('hidden').addClass('hidden');
                            }

                            if ($('[data-key="designOrUpload"][data-value="design"]').attr('data-selected') == 'true') {
                                $('[bns-editdesign].jqs-designnow').removeClass('hidden');
                            }

                            if(self.firstToggle) {
                                if(self.returnActiveProduct(true).design == null) {
                                    self.returnActiveProduct(false).getProductTemplates(true);
                                }
                                self.returnActiveProduct(true).designMethod = $('[data-key="designOrUpload"][data-selected="true"]').attr('data-value');
                                self.firstToggle = false;
                            }

                            $('[data-options=printed]').removeClass('hidden');
                            $('.jqs-longdescParent').appendTo('.productContentLeft');
                            $('.jqs-colordescParent').appendTo('.productContentLeft');

                            var additionalKey = $(element).attr('data-additionalkey');
                            var additionalValue = $(element).attr('data-additionalvalue');
                            if (typeof additionalKey != 'undefined' && additionalKey == 'designOrUpload') {
                                GoogleAnalytics.trackEvent('Product Page v2', 'Design or Upload', $().capitalize(dataValue));

                                if(additionalValue == 'design') {
                                    $('[data-options=design]').removeClass('hidden').addClass('hidden');
                                    $('[data-options=upload]').removeClass('hidden').addClass('hidden');
                                    $('[bns-editdesign].jqs-designnow').removeClass('hidden').addClass('hidden');
                                    $('[bns-selection][data-key="plainOrPrinted"][data-additionalkey="designOrUpload"][data-additionalvalue="upload"][data-value="printed"]').removeAttr('data-reveal-id');
                                    if($(element).attr('data-selected') == 'true') {
                                        $('[bns-selection][data-key="plainOrPrinted"][data-additionalkey="designOrUpload"][data-additionalvalue="design"][data-value="printed"]').off('click.loadTexel').on('click.loadTexel', function() {
                                            self.loadExistingTexel();
                                        });
                                        $('[bns-editdesign].jqs-designnow').removeClass('hidden');
                                        self.returnActiveProduct(true).designMethod = 'online';
                                        $('[data-options=design]').removeClass('hidden');
                                        self.loadExistingTexel(false, (typeof ignoreAction['loadDesigner'] == 'undefined' || !ignoreAction['loadDesigner'] ? true : false));
                                    }

                                    $('[bns-customization]').removeClass('hidden').addClass('hidden');
                                    if (self.returnActiveProduct(true).hasAddressingAbility == true) {
                                        $('[bns-customization]').removeClass('hidden');
                                    }
                                    $('[bns-rush_production]').removeClass('hidden');
                                } else if (additionalValue == 'upload') {
                                    self.updateImage('//actionenvelope.scene7.com/is/image/ActionEnvelope/' + self.returnActiveProduct(true).productId + '?hei=413&fmt=png-alpha&align=0,-1');
                                    $('[bns-load_design]').removeClass('hidden').addClass('hidden');
                                    $('[data-options=design]').removeClass('hidden').addClass('hidden');
                                    $('[data-options=upload]').removeClass('hidden').addClass('hidden');
                                    $('[bns-editdesign].jqs-designnow').removeClass('hidden').addClass('hidden');
                                    if($(element).attr('data-selected') == 'true') {
                                        self.returnActiveProduct(true).designMethod = 'upload';
                                        $('[data-options=upload]').removeClass('hidden');
                                    }
                                    self.createFileList($('.dropzone > div[data-filename]'));
                                    $('[bns-selection][data-key="plainOrPrinted"][data-additionalkey="designOrUpload"][data-additionalvalue="upload"][data-value="printed"]').attr('data-reveal-id', 'startUpload');
                                    $('#startUpload').foundation('reveal', 'open');
                                    $(window).scrollTop(0);

                                    $('[bns-customization]').removeClass('hidden').addClass('hidden');
                                    if (self.returnActiveProduct(true).hasAddressingAbility == true) {
                                        $('[bns-customization]').removeClass('hidden');
                                    }
                                } else if (additionalValue == 'returnAddress') {
                                    $('[data-options=design]').removeClass('hidden').addClass('hidden');
                                    $('[data-options=upload]').removeClass('hidden').addClass('hidden');
                                    $('[bns-editdesign].jqs-designnow').removeClass('hidden').addClass('hidden');

                                    if($(element).attr('data-selected') == 'true') {
                                        self.returnActiveProduct(true).designMethod = "returnAddress";
                                        $("[bns-return_address_content]").removeClass("hidden").attr("bns-return_address_content", "true");
                                        $("[data-target=\"inkFrontSelection\"][data-key=\"colorsFront\"][data-value=\"1\"]").trigger("click");
                                    }

                                    $(window).scrollTop(parseInt($("[bns-return_address_content]").offset().top) - 50);
                                    $('[bns-customization]').removeClass('hidden').addClass('hidden');
                                }

                                if($('[data-key=' + dataKey + '][data-selected=true]').length == 0) {
                                    self.returnActiveProduct(true).designMethod = null;
                                }
                            } else if (typeof additionalKey != 'undefined' && additionalKey == 'addressing' && additionalValue == 'true') {
                                $('[data-key="addresses"][data-value="true"][data-target="addressing"]').trigger('click');
                                $('[bns-customization]').removeClass('hidden');
                            } else if (typeof additionalKey != 'undefined' && additionalKey == 'reuseDesign') {
                                self.returnActiveProduct(true).designMethod = 'upload';
                            }
                        }
                    } else if (dataKey == 'isRush') {
                        GoogleAnalytics.trackEvent('Product Page v2', 'Add Rush', (dataValue == 'true') ? 'Yes' : 'No');
                    }
                }

                $('[bns-uploadedfilesheader]').removeClass('hidden');

                if (element.attr('data-additionalvalue') != 'upload') {
                    $('[bns-uploadedfilesheader]').addClass('hidden');
                    $('.' + self.uploadedFiles).addClass('hidden');
                    $('.' + self.reusedFiles).addClass('hidden');
                }
            } else if ($(element).attr('data-target') == 'addressing') {
                GoogleAnalytics.trackEvent('Product Page v2', 'Add Addressing', (dataValue == 'true') ? 'Yes' : 'No');
                var texelDiv = $('#texel_' + self.activeProductIndex);
                if(dataValue == 'false') {
                    self.returnActiveProduct(true).addAddressing = false;
                    $('[data-options=addressing]').removeClass('hidden').addClass('hidden');
                    texelDiv.texel('deactivateOrActivateAddressing', false);
                } else {
                    self.returnActiveProduct(true).addAddressing = true;
                    $('[data-options=addressing]').removeClass('hidden');
                    texelDiv.texel('deactivateOrActivateAddressing', true);
                }

                texelDiv.texel('preview', (self.returnActiveProduct(true).templateType == 'FLAP') ? '1' : '0', 520, null, function (data, fmt) {
                    self.updateImage('data:image/' + fmt + ';base64,' + data.data);
                });
            } else if ($(element).attr('data-target') == 'colorSelection') {
                self.returnActiveProduct(true).productId   		= $(element).attr('data-product-id');
                self.returnActiveProduct(true).name        		= $(element).attr('data-product-name');
                self.returnActiveProduct(true).color       		= $(element).attr('data-product-color');
                self.returnActiveProduct(true).hex         		= $(element).attr('data-hex');
                self.returnActiveProduct(true).size				= $(element).attr('data-size');
                self.returnActiveProduct(true).actualSize		= $(element).attr('data-actualsize');
                self.returnActiveProduct(true).category			= $(element).attr('data-category');
                self.returnActiveProduct(true).style			= $(element).attr('data-style');
                self.returnActiveProduct(true).printable   		= ($(element).attr('data-printable') === 'true');
                self.returnActiveProduct(true).minColor    		= parseInt($(element).attr('data-min-color'));
                self.returnActiveProduct(true).maxColor    		= parseInt($(element).attr('data-max-color'));
                self.returnActiveProduct(true).hasWhiteInk 		= ($(element).attr('data-has-white-ink') === 'true');
                self.returnActiveProduct(true).hasSample   		= ($(element).attr('data-has-sample') === 'true');
                self.returnActiveProduct(true).hasRush   		= ($(element).attr('data-has-rush') === 'true');
                self.returnActiveProduct(true).paperWeight 		= $(element).attr('data-product-weight');
                self.returnActiveProduct(true).brand       		= $(element).attr('data-product-brand');
                self.returnActiveProduct(true).rating      		= $(element).attr('data-rating');
                self.returnActiveProduct(true).percentSavings	= parseInt($(element).attr('data-percent-savings'));
                self.returnActiveProduct(true).hasPeelAndPress  = $(element).attr('data-haspeelandpress');
                self.returnActiveProduct(true).isSpecialQuantityOffer  = $(element).attr('data-isSpecialQuantityOffer');
                document.title = $(element).attr('data-meta-title') + " | " + metaTitleSuffix;

                if ((typeof ignoreAction['googleAnalytic'] == 'undefined' || !ignoreAction['googleAnalytic'])) {
                    GoogleAnalytics.trackEvent('Product Page v2', 'Color Change', $().capitalize(self.returnActiveProduct(true).color));
                }

                if (self.returnActiveProduct(true).parentProductId != $(element).attr('data-parentproductid')) {
                    //$('#texel_' + self.activeProductIndex).texel('destroyTexel');
                    productPage.designs.splice(0, productPage.designs.length);
                    $('[bns-load_design]').addClass('hidden');
                    $('[bns-selection][data-key="plainOrPrinted"][data-value="plain"]').trigger('click');
                    //$('.jqs-designnow').off('click.designNow');
                    $('[bns-selection][data-key="plainOrPrinted"][data-additionalkey="designOrUpload"][data-additionalvalue="design"][data-value="printed"]').attr('data-reveal-id', 'startDesign');
                    self.updateImage('//actionenvelope.scene7.com/is/image/ActionEnvelope/' + $(element).attr('data-product-id') + '?hei=413&wid=510&fmt=jpeg&qlt=75&bgc=ffffff');
                    self.returnActiveProduct(true).parentProductId = $(element).attr('data-parentproductid');
                }

                if (self.returnActiveProduct(true).printable) {
                    $.ajax({
                        type: 'GET',
                        url: '/' + websiteId + '/control/getProductDesigns',
                        dataType: 'json',
                        data: {
                            productId: self.returnActiveProduct(true).productId,
                            parentProductId: self.returnActiveProduct(true).parentProductId
                        },
                        cache: true
                    }).done(function(response) {
                        if(response.success && typeof response.data == 'object') {
                            var designTemplateList = $('[bns-design_template_list]');

                            designTemplateList.empty();
                            designData = {};

                            for (var i = 0; i < response.data.length; i++) {
                                designData[response.data[i].designTemplateId] = response.data[i];
                                designData[response.data[i].designTemplateId].jsonData = JSON.parse(designData[response.data[i].designTemplateId].jsonData);
                                designData[response.data[i].designTemplateId].backgroundColor = self.returnActiveProduct(true).hex;

                                designTemplateList.append(
                                    '<div>' +
                                    '<div bns-design data-design="' + response.data[i].designTemplateId + '" data-design_type="' + designData[response.data[i].designTemplateId].name + '">' +
                                    '<img bns-design_image src="/html/img/designs/thumbnails/' + response.data[i].designTemplateId + '_' + designData[response.data[i].designTemplateId].location + '.png" alt="' + designData[response.data[i].designTemplateId].name + '" style="background-color: #' + designData[response.data[i].designTemplateId].backgroundColor + ';" />' +
                                    '</div>' +
                                    '<p style="text-transform: uppercase;">' + designData[response.data[i].designTemplateId].name + '</p>' +
                                    '</div>'
                                );
/*
                                var texelInstance = new Texel('texelTemporaryCanvasContainer_thumbnail_' + i, designData[response.data[i].designTemplateId], true);

                                $('[bns-design][data-design="' + response.data[i].designTemplateId + '"]').find('img[bns-design_image]').attr('src', texelInstance.getCanvasDataAsImage(designData[response.data[i].designTemplateId].jsonData)[designData[response.data[i].designTemplateId].location]);

                                texelInstance.destroyTexel();
                                */
                            }
                        }
                    });

                    if (productPage.getActiveDesign() != null) {
                        productPage.getActiveDesign().setAttribute('backgroundColor', self.returnActiveProduct(true).hex);
                        productPage.getActiveDesign().loadState(productPage.getActiveDesign().getAttribute('designerStateCurrentIndex'));
                        $('.jqs-imagehero').attr('src', productPage.getActiveDesign().getAttribute('designerState')[productPage.getActiveDesign().getAttribute('designerStateCurrentIndex')].imageData.front);
                    }
                }

                if (!self.returnActiveProduct(true).printable) {
                    $('.jqs-optTestDesignsLeftSide > div').removeClass('hidden').addClass('hidden');
                }
                else {
                    $('.jqs-optTestDesignsLeftSide > div').removeClass('hidden');
                    $('.jqs-optTestDesignsLeftSide .jqs-optTestDesignsLeftSideTemplate').css({
                        'background-color': '#' + self.returnActiveProduct(true).hex,
                        'color': '#' + (isColorAllowed(self.returnActiveProduct(true).hex, '000000') ? '000000' : 'ffffff')
                    });
                }

                if (self.returnActiveProduct(true).percentSavings > 0) {
                    $('.percentSavingsDisplay').removeClass('hidden').addClass('hidden');
                    $('.pricePerUnitDisplay').removeClass('hidden').addClass('hidden');
                    $('.originalPriceDisplay').removeClass('hidden');
                    $('.savingsDisplay').removeClass('hidden');
                }
                else {
                    $('.percentSavingsDisplay').removeClass('hidden');
                    $('.pricePerUnitDisplay').removeClass('hidden');
                    $('.originalPriceDisplay').removeClass('hidden').addClass('hidden');
                    $('.savingsDisplay').removeClass('hidden').addClass('hidden');
                }

                $('.jqs-peelAndPressLogo').removeClass('hidden');
                if (self.returnActiveProduct(true).hasPeelAndPress == "false") {
                    $('.jqs-peelAndPressLogo').addClass('hidden');
                }

                $('[data-target="productionTime"][data-key="isRush"][data-value="true"]').removeClass('hidden');
                if (!self.returnActiveProduct(true).hasRush) {
                    $('[data-target="productionTime"][data-key="isRush"][data-value="true"]').addClass('hidden');
                    $('[data-target="productionTime"][data-key="isRush"][data-value="false"]').trigger('click');
                }

                //change image (need to check if printed or not later
                var plainOrPrinted = $('[data-key=plainOrPrinted][data-selected=true]').attr('data-value');
                var texelDiv = $('#texel_' + self.activeProductIndex);
                var changeURL = true;

                if(plainOrPrinted == 'printed' && self.returnActiveProduct(true).designMethod == 'online' != null && self.returnActiveProduct(true).designMethod == 'online' && self.getActiveDesign() != null) {
                    self.getActiveDesign().designerContainer.find('#designerAddAddressing').removeClass('hidden');

                    if (!self.returnActiveProduct(true).hasAddressingAbility) {
                        self.getActiveDesign().designerContainer.find('#designerAddAddressing').addClass('hidden');
                    }
                } else {
                    self.updateImage('//actionenvelope.scene7.com/is/image/ActionEnvelope/' + self.returnActiveProduct(true).productId + '?hei=413&wid=510&fmt=png-alpha');
                }

                //update page areas
                $('.' + self.productName).html(self.returnActiveProduct(true).name);
                $('.' + self.colorName).html(self.returnActiveProduct(true).color);
                $('.' + self.printDesc).html($(element).attr('data-print-desc'));
                $('.' + self.brandDesc).removeClass('hidden').addClass('hidden');
                if($(element).attr('data-product-brand').match(/((?:LUXPaper|Neenah|Fredrigoni))/) != null) {
                    $('.' + self.brandDesc + '.jqs-' + $(element).attr('data-product-brand').match(/((?:LUXPaper|Neenah|Fredrigoni))/)[1]).removeClass('hidden');
                }
                $('.' + self.newRibbon).removeClass('hidden').addClass('hidden');
                if($(element).attr('data-new') == 'true') {
                    $('.' + self.newRibbon).removeClass('hidden');
                }
                $('.' + self.starRating).removeClass().addClass(self.starRating + ' rating-' + self.returnActiveProduct(true).rating);
                $('.' + self.noReview).removeClass('hidden').addClass('hidden');
                $('.' + self.hasReview).removeClass('hidden').find('a').html('Read Reviews');

                //hide all print selections and show based on availability
                var frontInkOptions = $('[data-target=inkFrontSelection]');
                var backInkOptions = $('[data-target=inkBackSelection]');

                frontInkOptions.removeClass('hidden').addClass('hidden');
                backInkOptions.removeClass('hidden').addClass('hidden');

                //re-enable white ink if available
                if(self.returnActiveProduct(true).hasWhiteInk) {
                    $('[data-target=inkFrontSelection][data-value=whiteInkFront]').removeClass('hidden');
                    $('[data-target=inkBackSelection][data-value=whiteInkBack]').removeClass('hidden');
                }
                //re-enable colors if available
                if(self.returnActiveProduct(true).maxColor > 0) {
                    for(var i = 0; i <= self.returnActiveProduct(true).maxColor; i++) {
                        $('[data-target=inkFrontSelection][data-value=' + i + ']').removeClass('hidden');
                        $('[data-target=inkBackSelection][data-value=' + i + ']').removeClass('hidden');
                    }
                }

                //if an option was selected that is no longer available, go one option down for printed only
                //if element has data-selected = true, but has class = hidden, then keep going down until it does not have class hidden
                if(plainOrPrinted == 'printed') {
                    var selectNextIter = false;

                    $.each(frontInkOptions.get().reverse(), function(i, inkEl) {
                        if(selectNextIter) {
                            $(inkEl).attr('data-selected', 'true');
                        }
                        if(!$(inkEl).hasClass('hidden') && $(inkEl).attr('data-selected') == 'true') {
                            return false;
                        } else if($(inkEl).hasClass('hidden') && $(inkEl).attr('data-selected') == 'true') {
                            $(inkEl).attr('data-selected', 'false');
                            selectNextIter = true;
                            return true;
                        }
                    });

                    if(selectNextIter) {
                        $('[data-key=colorsFront][data-selected=true]').trigger('click');
                    }

                    selectNextIter = false;
                    $.each(backInkOptions.get().reverse(), function(i, inkEl) {
                        if(selectNextIter) {
                            $(inkEl).attr('data-selected', 'true');
                        }
                        if(!$(inkEl).hasClass('hidden') && $(inkEl).attr('data-selected') == 'true') {
                            return false;
                        } else if($(inkEl).hasClass('hidden') && $(inkEl).attr('data-selected') == 'true') {
                            $(inkEl).attr('data-selected', 'false');
                            selectNextIter = true;
                            return true;
                        }
                    });

                    if(selectNextIter) {
                        $('[data-key=colorsBack][data-selected=true]').trigger('click');
                    }
                }

                if(self.returnActiveProduct(true).hasSample && self.returnActiveProduct(true).designMethod == null) {
                    $('.' + self.orderSamplesQuickLink).removeClass('hidden');
                    $('.samplesCheckboxContainer').removeClass('hidden');
                } else {
                    $('.' + self.orderSamplesQuickLink).removeClass('hidden').addClass('hidden');
                    $('.samplesCheckboxContainer').removeClass('hidden').addClass('hidden');
                }

                //self.getDueDates();
                self.returnActiveProduct(false).getProductInventory();
                self.returnActiveProduct(false).getProductReviews();
                self.returnActiveProduct(false).getProductFeatures();
                self.returnActiveProduct(false).getProductDesc();

                self.returnActiveProduct(false).getProductAssets(function(id) {
                    if (plainOrPrinted != 'printed' && texelDiv.length == 0 && typeof texelDiv.data('envTexel') == 'undefined' && self.returnActiveProduct(true).designMethod != 'online') {
                        self.updateImage('//actionenvelope.scene7.com/is/image/ActionEnvelope/' + id + '?hei=413&wid=510&fmt=png-alpha');
                    }
                });

                if(self.returnActiveProduct(true).printable) {
                    self.returnActiveProduct(false).getProductTemplates(false);
                }

                //change url
                if(changeURL && (typeof ignoreAction['updateURL'] == 'undefined' || !ignoreAction['updateURL'])) {
                    history.pushState({'style' : self.returnActiveProduct(true).style, 'category' : self.returnActiveProduct(true).category, 'size' : self.returnActiveProduct(true).size, 'actualSize' : self.returnActiveProduct(true).actualSize, 'productid' : self.returnActiveProduct(true).productId, 'productTypeId' : self.returnActiveProduct(true).productTypeId}, $(element).attr('data-product-name') + ' | ' + $(element).attr('data-product-color'), $(element).attr('data-url'));
                }

                //if the product doesnt support printing, hide printing
                if(!self.returnActiveProduct(true).printable) {
                    $('[data-key=plainOrPrinted][data-value=plain]').trigger('click');
                    $('.plainOrPrinted').removeClass('hidden').addClass('hidden');
                } else if(startedAsDesign) {
                    //
                } else {
                    $('.plainOrPrinted').removeClass('hidden');
                }

                self.adjustProductNameSize;
                self.returnActiveProduct(false).storeRecentlyViewed();
            } else if ($(element).attr('data-target') == 'sizeSelection' || $(element).attr('data-target') == 'styleSelection') {
                if ($(element).attr('data-target') != 'sizeSelection') {
                    getSizeAndStyleData(null);
                }

                if ($(element).attr('data-target') == 'sizeSelection') {
                    GoogleAnalytics.trackEvent('Product Page v2', 'Size Change', $().capitalize(dataValue));
                    buildStyleList();
                    $('#sizeSelection').height() <= 88 && window.outerWidth > 425 ? $('<br />').insertBefore($('#sizeSelection').find('span.actualSizeName')) : '';
                } else if ($(element).attr('data-target') == 'styleSelection') {
                    GoogleAnalytics.trackEvent('Product Page v2', 'Style Change', $().capitalize(dataValue));
                    buildSizeList();
                }

                adjustFontSize($('#' + $(element).attr('data-target') + ' [bns-adjustfontsize]'), ($(element).attr('data-target') == 'styleSelection' ? 15 : 30), null, getSelectionWidth());

                if ((typeof ignoreAction['colorList'] == 'undefined' || !ignoreAction['colorList'])) {
                    buildColorList();
                }
                productPage.returnActiveProduct(false).getProductTemplates(true);
                productPage.loadSamples();
            } else if ($(element).attr('data-target') == 'estimatePrintCostQuantity') {
                $('[bns-estimate_print_cost_total').html(formatCurrency($(element).attr('data-price')));
            } else if ($(element).attr('data-target').match(/ink(?:Front|Back)Selection2/) != null) {
                $.ajax({
                    type: "GET",
                    url: '/' + websiteId + '/control/getProductPrice',
                    data: {
                        id: productPage.returnActiveProduct(true).productId,
                        quantity: parseInt($('[bns-sidebarid="estimatePrintCostQuantity"] > div:first-child').html().replace(/[^0-9]/g, '')),
                        colorsFront: parseInt($('#dropdown-inkFrontSelection2 .slSelected').attr('data-value')),
                        colorsBack: parseInt($('#dropdown-inkBackSelection2 .slSelected').attr('data-value')),
                        whiteInkFront: false,
                        whiteInkBack: false,
                        isRush: false,
                        cuts: 0,
                        isFolded: false,
                        isFullBleed: false,
                        addresses: 0,
                        templateId: null
                    },
                    dataType: 'json',
                    cache: true
                }).done(function(productPriceData) {
                    // console.log(productPriceData);
                    // console.log(productPriceData.priceList[$('[bns-sidebarid="estimatePrintCostQuantity"] > div:first-child').html().replace(/[^0-9]/g, '')].price);
                    $("[bns-estimate_print_cost]").html("");
                    for (var quantity in productPriceData.priceList) {
                        // console.log("My Quantity Is: " + quantity + ", and my price is: " + productPriceData.priceList[quantity].price);
                        //console.log(quantity + " / " + productPriceData.priceList[quantity].price);
                        var colorCostValue = productPriceData.priceList[quantity].price;
                        var colorQuanValue = quantity;
                        $("[bns-estimate_print_cost]").append(
                                $("<div />").attr({
                                "class": "jqs-selectList qpsListItems selectList",
                                "data-qty": colorQuanValue,
                                "data-price": colorCostValue,
                                "bns-selection": "",
                                "data-target": "estimatePrintCostQuantity"
                            }).append(
                                $("<div />").attr({
                                    "selection-removeonselect": ""
                                }).addClass("width30").append(
                                    $("<span />").addClass("selectCheckbox")
                                )
                            ).append(
                                $("<div />").addClass("width95 quantityDisplay").html(colorQuanValue + " Qty")
                            ).append(
                                $("<div />").addClass("priceDisplay").attr("selection-removeonselect", "").html(formatCurrency(colorCostValue))
                            ).append(
                                $("<div />").addClass("jqs-pricePerUnit pricePerUnitDisplay").html(formatCurrency(colorCostValue / colorQuanValue) + "/each")
                            ).append(
                                $("<div />").addClass("jqs-subShipNotice freeshipnote hidden").attr("selection-removeonselect", "").html(
                                    " + <i class=\"fa fa-truck\"></i> Ships FREE w/ code"
                                )
                            ).append(
                                $("<i />").addClass("fa fa-care-right productCaretSidebar").attr("selection-showonselect")
                            )
                        );
                        /*0
                        $('#sidebar-estimatePrintCostQuantity [data-target="estimatePrintCostQuantity"]').each(function() {
                            // do stuff here to manipulate it
                            $(this).attr({
                                "data-qty" : colorQuanValue,
                                "data-price" : colorCostValue
                            });

                            $(this).find(".jqs-priceDisplay").html(formatCurrency(colorCostValue));
                            $(this).find(".jqs-pricePerUnit").html(formatCurrency(colorCostValue / colorQuanValue) + "/each")
                        })
                        */
                        $('#dropdown-inkFrontSelection2').click(function(){
                            $('[estimatePrintCostQuantity]').attr("data-qty").val(quantity);
                        });
                    };
                    $('[bns-estimate_print_cost_total]').html(formatCurrency(productPriceData.priceList[$('[bns-sidebarid="estimatePrintCostQuantity"] > div:first-child').html().replace(/[^0-9]/g, '')].price));
                });
            }

            if (typeof $(element).attr('data-additionalkey') != 'undefined' && typeof $(element).attr('data-additionalvalue') != 'undefined') {
                $('[data-key="' + $(element).attr('data-additionalkey') + '"][data-value="' + $(element).attr('data-additionalvalue') + '"]').trigger('click');
            }

            if (element.attr('data-target') != 'quantityPriceSelection') {
                //get new product price
                self.returnActiveProduct(false).calculatePrice(null);
            }
        }

        if (!element.hasClass('slSelected')) {
            if (($(element).attr('data-target') == 'sizeSelection' || $(element).attr('data-target') == 'styleSelection') && self.designs.length > 0 && typeof envConfirm == 'function') {
                envConfirm('Continue changing Size or Style?', 'Are you sure you want to switch Size or Style?  By doing so, you will remove all design changes.', 'Yes, change Size or Style', 'No, keep current Size and Style', function(success) {
                    if (success) {
                        continueUpdateSelection();
                    }
                });
            } else if ($(element).attr('data-target') == 'printingOption') {
                var dataKey = element.attr('data-additionalkey');
                var dataValue = element.attr('data-additionalvalue');


                if (typeof dataKey != 'undefined' && dataKey == 'designOrUpload') {
                    var texelDiv = $('#designerContainer');

                    if (dataValue == 'design' && ($('.jqs-uploadedfiles ul li').length > 0 || ($('.jqs-reusedfiles').html() != '' && typeof $('.jqs-reusedfiles').html() != 'undefined'))) {
                        envConfirm('Continue With Choosing a Template?', 'Are you sure you want to switch printing methods? You\'ve already uploaded a print-ready file.', 'Yes, choose template', 'No, keep uploads', function(success) {
                            if (success) {
                                continueUpdateSelection();
                            }
                        });
                    } else if (dataValue != 'design' && (texelDiv.length > 0 && self.returnActiveProduct(true).design != null)) {
                        envConfirm('Continue With Upload?', 'Are you sure you want to switch printing methods?  You\'ve already customized your design.', 'Yes, upload a file', 'No, keep design', function(success) {
                            if (success) {
                                continueUpdateSelection();
                            }
                        });
                    } else {
                        continueUpdateSelection();
                    }
                } else {
                    continueUpdateSelection();
                }
            } else {
                continueUpdateSelection();
            }
        }
    },
    /**
     * Get due dates for printed orders
     */
    getDueDates: function() {
        var self = this;
        $.ajax({
            type: 'GET',
            url: '/' + websiteId + '/control/getDueDate',
            async: true,
            data: {
                'date': getFutureDate(0, false),
                'productId': this.returnActiveProduct(true).productId
            },
            cache: true,
            dataType: 'json'
        }).done(function(data) {
            $('.' + self.plainShipTime).html(data.leadTime.leadTimePlain == 0 ? 'Today' : 'in ' + data.leadTime.leadTimePlain + ' Day' + (data.leadTime.leadTimePlain > 1 ? 's' : ''));
            $('.' + self.printedShipTime).html(data.leadTime.leadTimeStandardPrinted == 0 ? 'Today' : 'in ' + data.leadTime.leadTimeRushPrinted + '-' + data.leadTime.leadTimeStandardPrinted + ' Days');
            $('.' + self.standardDelDate).html('Ready to Ship on ' + data.standardDate);
            $('.' + self.rushDelDate).html('Ready to Ship on ' + data.rushDate);
        });
    },
    /**
     * Adjust Product Name Size based on size of browser.
     */
    adjustProductNameSize: function(ignoreFontReset) {
        if (!ignoreFontReset) {
            $('.productContentLeft h1').css({
                'font-size': '32px'
            });
        }

        if (getFullWidth($('.productContentLeft h1')) + 20 >= ($('.productContentLeft').innerWidth() - parseInt($('.productContentLeft').css('padding-right')))) {
            $('.productContentLeft h1').css({
                'font-size': (parseInt($('.productContentLeft h1').css('font-size')) - 1) + 'px'
            });
            this.adjustProductNameSize(true);
        }
    },
    /**
     * Validate the cart click before proceeding
     * @param el
     * @param product
     * @param totalAddresses
     * @returns {boolean} - If cart is not valid return false
     */
    isCartValid: function(el, product, totalAddresses) {
        var self = this;

        /* No Longer using this since there is no addressing toggle
        if(product.getProduct().addAddressing && totalAddresses == 0) {
            //addressing error
            self.processButtonError($('#printingOption'), null, 'You did not add any addresses yet!');
            return false;
        }
        */
        /*
                if(product.getProduct().designMethod == 'online' && product.getProduct().scene7DesignId == null) {
                    //design error
                    if(typeof startedAsDesign !== 'undefined' && startedAsDesign) {
                        self.processButtonError($('.productImageContainer .jqs-designnow'), null, 'You did not edit your design yet!');
                        $(window).scrollTop($('.productImageContainer').offset().top - 60);
                    } else {
                        self.processButtonError($('#printingOption'), null, 'You did not edit your design yet!');
                        $(window).scrollTop($('#printingOption').offset().top - 60);
                    }

                    return false;
                }
        */
        if(product.getProduct().designMethod == null && product.getProduct().addAddressing == false) {// && optTest == false) {
            //convert to plain
            self.processButtonError($('#printingOption'), null, 'You chose to print, but did not select any print options!');
            return false;
        }

        return true;
    },
    /**
     * Highlight button for multiselection Error Handling
     * @param element
     * @param elementToScrollTo
     * @param error
     */
    processButtonError: function(element, elementToScrollTo, error) {
        var self = this;
        var duration = 1000;
        var iteration = 2;
        elementToScrollTo = elementToScrollTo != null ? elementToScrollTo : element;
        $(window).stop(true);
        element.stop(true);
        element.attr('style', '');

        $(window).scrollTo(elementToScrollTo.offset().top - 250, 500);

        for (var i = 0; i < iteration; i++) {
            element.animate({
                'background-color': '#ffbbbb',
                'color': '#ffffff',
                'border-color': '#ff0000'
            }, duration);

            element.animate({
                'background-color': '#eeeeee',
                'color': '#858d93',
                'border-color': '#fc7e22'
            }, duration);
        }

        waitForFinalEvent(function() {
            element.stop();
            element.attr('style', '');
        }, duration * iteration * 2, 'processButtonError');

        self.removeFoundationTooltips(true);

        //add tooltip
        element.addClass('has-tip tip-top').attr({'data-tooltip':'', 'aria-haspopup': 'true', 'title': error});
        $(document).foundation('tooltip', 'reflow');
        Foundation.libs.tooltip.showTip(element);

        waitForFinalEvent(function() {
            self.removeFoundationTooltips();
        }, 5000, 'removeProductTooltip');
    },
    /**
     * Remove All Tooltips (Foundations)
     */
    removeFoundationTooltips: function(force) {
        $('[class*=tooltip][class*=tip-top]').fadeOut(typeof force === 'undefined' || force == false ? 1000 : 0);
        $('[data-key]').removeClass('has-tip tip-top').removeAttr('data-tooltip').removeAttr('aria-haspopup').removeAttr('title');
    },
    /**
     * Add all products to the cart
     * @param el - Button being clicked
     */
    addToCart: function(el) {
        var self = this;

        // $(el).removeAttr('data-dropdown-target').removeData('dropdown-target');

        if ($("[bns-return_address_content=\"true\"]").length > 0) {
            self.returnActiveProduct(true).comments += "\n\nReturn Address Information\n" + $("[name=\"return_address_fname\"]").val() + " " + $("[name=\"return_address_lname\"]").val() + "\n" + $("[name=\"return_address_address1\"]").val() + "\n" + $("[name=\"return_address_city\"]").val() + ", " + $("[name=\"return_address_state\"]").val() + " " + $("[name=\"return_address_zip\"]").val() + "\n\nFont: " + $("[name=\"return_address_font\"]").val() + "\nSize: " + $("[name=\"return_address_size\"]").val() + "\nColor: " + $("[name=\"return_address_color\"]").val() + "\nPrinting Location: " + $("[name=\"return_address_printing_location\"]").val();
        }

        if($(el).hasClass('processing')) {
            return;
        }

        $(el).addClass('processing');
        $(el).spinner(true, false, 50, null, null, '', null, null);

        // CODE FOR OPTIMIZELY TEST.
        if ((typeof optEnvAction !== 'undefined' && optEnvAction == 'ra') && (typeof $(el).attr('data-ignore') === 'undefined' || (typeof $(el).attr('data-ignore') !== 'undefined' && $(el).attr('data-ignore') != 'true'))) {
            try {
                if ($('.jqs-uploadedfiles ul li').length == 0 && !$('[name="sendFilesLater"]').is(':checked')) {
                    $('#optEnvAction').foundation('reveal', 'open');
                }
            }
            catch (e) {}
            $(el).removeClass('processing');
            $(el).spinner(false);
            return;
        }

        self.saveProject(); //save scene7 project if necessary
        var token = null;
        if(self.projectId != null) {
            token = randomToken();
        }

        if($(el).hasClass('processing')) {
            $(el).removeClass('processing')
        }

        for(var i = 0; i < this.getProducts().length; i++) {
            if(this.getProducts()[i].isProductEnabled()) {
                var attr = $.extend(true, {}, this.getProducts()[i].priceData);
                attr.add_product_id = this.getProducts()[i].returnProductId();

                //check order type, plain, printed
                var plainOrPrinted = $('[data-key=plainOrPrinted][data-selected=true]').attr('data-value');
                var totalAddresses = parseInt($('[data-key=addresses][data-selected=true]').attr('data-total'));
                var artworkSource = null;

                if(plainOrPrinted  == 'printed' && token != null) {
                    attr.token = token;
                }

                attr.isProduct = (self.getProducts().length > 1) ? false : true;
                if(plainOrPrinted  == 'printed' && self.projectId != null) {
                    attr.scene7ParentId = self.projectId;
                }

                if(self.getProducts()[i].getProduct().comments != null) {
                    attr.itemComment = self.getProducts()[i].getProduct().comments;
                }

                if(plainOrPrinted  == 'printed' && self.getProducts()[i].getProduct().scene7DesignId != null) {
                    attr.scene7DesignId = self.getProducts()[i].getProduct().scene7DesignId;
                }

                if(plainOrPrinted  == 'printed' && self.getProducts()[i].getProduct().designMethod != "returnAddress") {
                    artworkSource = 'ART_UPLOADED_LATER'; //default as long as its a printed job
                    //if its still null, lets make sure addressing is selected
                    if(!self.isCartValid(el, self.getProducts()[i], totalAddresses)) {
                        $(el).removeClass('processing');
                        $(el).spinner(false);
                        return;
                    } else {
                        if(self.getProducts()[i].getProduct().designMethod == 'upload') {
                            artworkSource = 'ART_UPLOADED';
                            var fileCount = 0;
                            var fileName = [];
                            var filePath = [];
                            var fileOrder = [];
                            var fileOrderItem = [];

                            if(self.getProducts()[i].getProduct().files != null && self.getProducts()[i].getProduct().files.length > 0) {
                                $.each(self.returnActiveProduct(true).files, function(i, file) {
                                    fileName[i]      = file.name;
                                    filePath[i]      = file.path;
                                    fileOrder[i]     = "";
                                    fileOrderItem[i] = "";
                                    fileCount++;
                                });
                            } else {
                                artworkSource = 'ART_UPLOADED_LATER';
                            }

                            //if its reuse
                            if(self.getProducts()[i].getProduct().oldFiles != null && self.getProducts()[i].getProduct().oldFiles.length > 0) {
                                $.each(self.returnActiveProduct(true).oldFiles, function(i, file) {
                                    fileName[fileCount+i]      = file.name;
                                    filePath[fileCount+i]      = file.path;
                                    fileOrder[fileCount+i]     = file.order;
                                    fileOrderItem[fileCount+i] = file.orderItem;
                                    fileCount++;
                                });
                                artworkSource = 'ART_REUSED';
                            }

                            //no files found
                            if(fileCount == 0 && self.getProducts()[i].getProduct().designMethod == 'upload') {
                                artworkSource = 'ART_UPLOADED_LATER';
                            }

                            //if files were uploaded and its a reuse
                            if(self.getProducts()[i].getProduct().oldFiles != null && self.getProducts()[i].getProduct().oldFiles.length > 0 && self.getProducts()[i].getProduct().files != null && self.getProducts()[i].getProduct().files.length > 0) {
                                artworkSource = 'ART_UPLOADED';
                                attr.itemComment = (typeof attr.itemComment == 'undefined') ? 'Added new files.' : attr.itemComment += '. Added new files.';
                            }

                            attr.fileName = fileName;
                            attr.filePath = filePath;
                            attr.fileOrder = fileOrder;
                            attr.fileOrderItem = fileOrderItem;
                        }

                        if(self.getProducts()[i].getProduct().addAddressing || self.getProducts()[i].getProduct().designMethod == 'online') {
                            artworkSource = 'SCENE7_ART_ONLINE';
                        }

                        if(artworkSource != null) { attr.artworkSource = artworkSource; }
                    }
                } else if (self.getProducts()[i].getProduct().designMethod == "returnAddress") {
                    attr.artworkSource = "ART_UPLOADED_LATER";
                }

                self.cartEndPoint(attr, self.getProducts()[i].getProduct().name, self.getProducts()[i].getProduct().color, false, null, null);
                if(self.getProducts()[i].getProduct().addOnProduct !== null) {
                    self.cartEndPoint( {'add_product_id': self.getProducts()[i].getProduct().addOnProduct, 'quantity': this.getProducts()[i].priceData.quantity }, '', '', false, null, null);
                }

            }
        }

        //remove warning
        self.pageExitWarning(true);

        $(el).spinner(false);

        //relocate to cart once all products have been added
        if( $(el).hasClass('testA') && !mobilecheck()) {
            $('[data-dropdown-target="dropdown-addtocartTestA"]').trigger('click.dropdown');
            self.resetSettings();
            return;
        } else {
            var crossSell = $('<form/>').attr('id', 'crossSell').attr('action', gCartUrl).attr('method', 'POST').append('<input type="hidden" name="lastVisitedURL" value="' + document.referrer + '" />').append('<input type="hidden" name="fromAddToCart" value="true" />');
            $('body').append(crossSell);
            $('#crossSell').submit();
        }
    },
    cartEndPoint: function(attr, name, color, async, successCallback, errorCallback) {
        var self = this;

        $.ajax({
            type: 'POST',
            url: gAddToCartUrl,
            timeout: 5000,
            async: async,
            data: attr,
            cache: false
        }).done(function(data) {
            $().updateMiniCart(data);
            ga('ec:addProduct', {
                'id': attr.add_product_id,
                'name': name,
                'category': '',
                'brand': '',
                'variant': color,
                'price': '',
                'quantity': attr.quantity
            });
            ga('ec:setAction', 'add');
            ga('send', 'event', 'UX', 'click', 'add to cart');

            dataLayer.push({
                'addedSku': attr.add_product_id,
                'addedQty': attr.quantity,
                'event': 'addtocart'
            });

            if(successCallback != null && typeof successCallback == 'function') {
                successCallback();
            }

            if(localStorageEnabled) {
                self.dropdownAddToCart();
            }

            if (typeof GTMCartUpdate == "function") {
                GTMCartUpdate();
            }
        }).fail(function(jqXHR) {
            if(errorCallback != null && typeof errorCallback == 'function') {
                errorCallback();
            }
        });

    },
    dropdownAddToCart: function() {
        var addToCartData = JSON.parse(localStorage.addToCartData);

        element = 'testA'

        $('.addToCartProductInfoTestA img, .addToCartProductTestA, .addToCartPricingTestA').remove();

        $('.addToCartProductInfoTestA').append(
            $('<img>').attr('src', 'https://actionenvelope2.scene7.com/is/image/ActionEnvelope/' + productPage.returnActiveProduct(true).productId + '?fmt=png-alpha&amp;wid=100&amp;ts=1')
        ).append(
            $('<div >').addClass('addToCartProductTestA').append(
                $('<h5>').html((productPage.returnActiveProduct(true).name).replace(/(^.*?)\s((?:\([0-9\/xX\s]+\)|\&\#x28\;[0-9\/xX\s\&\#f\;]+))/, '$1<br />$2'))
            ).append(
                $('<p>').html('Item #: ' + productPage.returnActiveProduct(true).productId)
            ).append(
                $('<p>').html('Color: ' + productPage.returnActiveProduct(true).color)
            ).append(
                $('<p>').html('Paper Weight: ' + productPage.returnActiveProduct(true).paperWeight)
            )
        ).append(
            $('<div >').addClass('addToCartPricingTestA').append(
                $('<h5>').html(formatCurrency(addToCartData.cartItems[0].totalPrice))
            ).append(
                $('<p>').html('Qty: ' + addToCartData.cartItems[0].quantity)
            )
        );

        if(productPage.returnActiveProduct(true).designMethod == 'upload' && typeof productPage.returnActiveProduct(true).files != 'undefined' && productPage.returnActiveProduct(true).files != null && productPage.returnActiveProduct(true).files.length > 0){
            $('.addToCartProductTestA').append(
                $('<p>').html('Attached Files: ' + productPage.returnActiveProduct(true).files[0].name)
            ).append(
                $('<p>').html('You will receive a proof within 1 business day')
            )
        }

        if(typeof productPage.returnActiveProduct(true).oldFiles != 'undefined' && productPage.returnActiveProduct(true).oldFiles != null && productPage.returnActiveProduct(true).oldFiles.length > 0) {
            $('.addToCartProductTestA').append(
                $('<p>').html('Attached Files: ' + productPage.returnActiveProduct(true).oldFiles[0].name)
            )
        }

        if(productPage.returnActiveProduct(true).addresses){
            $('.addToCartProductTestA').append(
                $('<p>').html('Adressing: ' + productPage.returnActiveProduct(true).addresses + ' Address')
            )
        }

        if(productPage.returnActiveProduct(true).colorsFront || productPage.returnActiveProduct(true).colorsBack) {
            $('.addToCartProductTestA').append(
                $('<p>').html('Printing: ' + productPage.returnActiveProduct(true).colorsFront + ' Color Front , ' + productPage.returnActiveProduct(true).colorsBack + ' Color Back')
            )
        }

        $('.addToCartPriceInfoTestA a, .addToCartPriceInfoTestA h5').remove();

        $('.addToCartPriceInfoTestA').append(
            $('<a>').attr('href', '/cart').html(addToCartData.cartItems.length+' Item(s) In Your Cart')
        ).append(
            $('<h5>').html('Subtotal: ').append(
                $('<span>').html(formatCurrency(addToCartData.subTotal))
            )
        );

        $().addToCartCheckSubtotal(element);
        $(window).scrollTop(0);

    },
    //save the entire project
    saveProject: function() {
        var self = this;

        var scene7Order = false;

        $.each(self.getProducts(), function(idx) {
            if((self.getProducts()[idx].getProduct().colorsFront > 0 || self.getProducts()[idx].getProduct().colorsBack > 0) && typeof self.getActiveDesign() != null && (self.getProducts()[idx].getProduct().designMethod == 'online' || self.getProducts()[idx].getProduct().addAddressing)) {
                scene7Order = true;
            }
        });

        if(scene7Order) {
            var projectObj = {
                'settings': {
                    'projectId': self.projectId,
                    'productType': 'product',
                    'product': []
                }
            };

            $.each(self.getProducts(), function(idx) {
                var tempProduct = $.extend(true, {}, self.getProducts()[idx].getProduct());
                delete tempProduct.templates;
                projectObj.settings.product.push(tempProduct);

                if (typeof projectObj.settings.product[idx] != 'undefined' && typeof projectObj.settings.product[idx].s7Data != 'undefined' && typeof projectObj.settings.product[idx].s7Data == 'object') {
                    projectObj.settings.product[idx].s7Data.scene7Data.imagePathList = [];

                    for (var key in projectObj.settings.product[idx].s7Data) {
                        if (key != 'scene7Data' && typeof projectObj.settings.product[idx].s7Data[key] == 'object') {
                            for (var i = 0; i < projectObj.settings.product[idx].s7Data[key].length; i++) {
                                if (typeof projectObj.settings.product[idx].s7Data[key][i] == 'object' && projectObj.settings.product[idx].s7Data[key][i].type == 'image' && typeof projectObj.settings.product[idx].s7Data[key][i].filePath != 'undefined') {
                                    if ($.inArray(projectObj.settings.product[idx].s7Data[key][i].filePath, projectObj.settings.product[idx].s7Data.scene7Data.imagePathList) == -1) {
                                       projectObj.settings.product[idx].s7Data.scene7Data.imagePathList.push(projectObj.settings.product[idx].s7Data[key][i].filePath);
                                    }
                                }
                            }
                        }
                    }
                }
            });

            $.ajax({
                type: 'POST',
                timeout: 10000,
                url: '/' + websiteId + '/control/saveProject',
                data: { 'id' : (self.projectId != null ? self.projectId : ''), 'productData' : JSON.stringify(projectObj) },
                async: false,
                dataType: 'json',
                cache: false
            }).done(function(data) {
                if(data.success) {
                    self.projectId = data.projectId;
                    if(typeof data.savedDesignIds !== 'undefined') {
                        $.each(data.savedDesignIds, function(idx) {
                            self.getProducts()[idx].getProduct().scene7DesignId = data.savedDesignIds[idx];
                            //self.getProducts()[idx].getProduct().itemInkColor = product[index].s7Data.inkColors;
                        });
                    }
                    self.saveDesignPreview();
                }
            });
        }

        return self.projectId;
    },

    saveDesignPreview: function() {
        var self = this;
        var imageData = productPage.getActiveDesign().getAttribute('designerState')[productPage.getActiveDesign().getAttribute('designerStateCurrentIndex')].imageData;
        for(var side in imageData) {
            if(imageData.hasOwnProperty(side)) {
                var base64ImageContent = imageData[side].replace(/^data:image\/(png|jpg);base64,/, "");
                var formData = new FormData();
                formData.append('designId', self.getProducts()[0].getProduct().scene7DesignId);
                formData.append('base64', base64ImageContent);
                formData.append('side', side);
                $.ajax({
                    url: '/envelopes/control/saveDesignPreview',
                    type: "POST",
                    cache: false,
                    contentType: false,
                    processData: false,
                    data: formData})
                    .done(function(data){
                        // console.log(data);
                    });
            }
        }
    },

    /**
     * Load a project
     * @param id - load this if value is passed
     * @param isProject - true, then its project and should not load item data, else load item project
     * @param index - index of product to update if available
     */
    loadProject: function(id, isProject, index) {
        var self = this;
        $.ajax({
            type: 'POST',
            url: '/' + websiteId + '/control/loadProject',
            data: { 'id' : id },
            async: false,
            dataType: 'json',
            success: function(response) {
                if(isProject && response.success && typeof response.settings !== 'undefined') {
                    $.each(response.settings.product, function(index) {
                        $.extend(true, self.getProducts()[index].getProduct(), response.settings.product[index]);
                        self.loadProject(response.settings.product[index].scene7DesignId, false, index);
                    });
                } else if(!isProject && response.success) {
                    self.loadNewTexel(response.designData, false, false);
                }
            }
        });
    },
    resetSettings: function() {
        var self = this;

        //remove hash from url if available
        if('pushState' in history) {
            history.pushState('', document.title, window.location.pathname + window.location.search);
        }

        //reset print data
        self.projectId = null;
        for(var i = 0; i < self.getProducts().length; i++) {
            self.getProducts()[i].getProduct().scene7DesignId = null;
        }
    },
    pageExitWarning: function(unbind) {
        if(unbind) {
            $(window).unbind('beforeunload');
            return false;
        }
        $(window).bind('beforeunload', function() {
            return 'You have edited a design, leaving this page will cause you to lose your design changes.';
        });
    },
    getUserLogin: function() {
        var self = this;

        if(self.partyId == null) {
            $.ajax({
                url: '/' + websiteId + '/control/getUserLogin',
                dataType: 'json',
                type: 'POST',
                data: {},
                async: true
            }).done(function(data) {
                if(typeof data.partyId != 'undefined' && data.partyId != null) {
                    self.partyId = data.partyId;
                }
            });
        }
    },
    /**
     *	Load Sample List
     */
    loadSamples: function() {
        var self = this;

        function updateSamplePrice(priceElement, numberOfSamples) {
            $(priceElement).html('$' + numberOfSamples + '.00')
        }

        $('.jqs-samplesPopupBody').empty();

        $('.jqs-sampleReadableList > div').each(function() {
            if ($(this).attr('data-has-sample') == 'true') {
                $('.jqs-samplesPopupBody').append(
                    $('<div />').addClass('samplesRow').attr('id', 'sampleRow-' + $(this).attr('data-product-id')).append(
                        $('<div />').addClass('samplesCol1').append(
                            $('<img />').addClass('jqs-hover-img').attr({'src' : 'data:image/png;base64,R0lGODlhAQABAAD/ACwAAAAAAQABAAACADs=', 'data-src': '//actionenvelope.scene7.com/is/image/ActionEnvelope/' + $(this).attr('data-product-id') + '?hei=50&wid=50&fmt=png-alpha' })
                        ).append(
                            $('<div />').html($(this).attr('data-product-color'))
                        )
                    ).append(
                        $('<div />').addClass('samplesCol2').html($(this).attr('data-product-weight'))
                    ).append(
                        $('<div />').addClass('samplesCol3 quantityColumn').append(
                            $('<div />').addClass('jqs-subtractSampleQuantity').html('-')
                        ).append(
                            $('<input />').attr('value', '1')
                        ).append(
                            $('<div />').addClass('jqs-addSampleQuantity').html('+')
                        )
                    ).append(
                        $('<div />').addClass('samplesCol4 jqs-samplePrice').html($(this).data('product-type') == 'FOLDER' ? '$3.00' : '$1.00')
                    ).append(
                        $('<div />').addClass('samplesCol5').append(
                            $('<div />').attr({
                                'data-product-id': $(this).attr('data-product-id'),
                                'data-product-name': $(this).attr('data-product-name'),
                                'data-product-color': $(this).attr('data-product-color'),
                                'data-adding-item': 'false'
                            }).addClass('button-regular jqs-addSampleToCart addSampleToCart').html('Add to Cart')
                        )
                    )
                );
            }
        });

        $('.quantityColumn > input').on('keydown', function(e) {
            if (e.keyCode < 49 || e.keyCode > 53) {
                e.preventDefault();
            }
            else {
                $(this).val('');
            }
        }).on('input paste', function(e) {
            try {
                $(this).val($(this).val().match(/(.).*/)[1]);
                updateSamplePrice($(this).closest('.samplesRow').find('.jqs-samplePrice'), $(this).val());
            }
            catch(e) {
                // do nothing
            }
        });

        $('.jqs-addSampleQuantity, .jqs-subtractSampleQuantity').on('click', function(e) {
            var targetElement = $(this).siblings('input');

            if ($(this).hasClass('jqs-addSampleQuantity')) {
                $(targetElement).val((parseInt($(targetElement).val()) >= 5 ? 5 : parseInt($(targetElement).val()) + 1));
            }
            else {
                $(targetElement).val((parseInt($(targetElement).val()) <= 1 ? 1 : parseInt($(targetElement).val()) - 1));
            }

            updateSamplePrice($(targetElement).closest('.samplesRow').find('.jqs-samplePrice'), $(targetElement).val());
        });

        $('.jqs-addSampleToCart').on('click', function() {
            var buttonElement = $(this);
            if ($(buttonElement).attr('data-adding-item') == 'false') {
                $(buttonElement).attr('data-adding-item', 'true');
                var originalButtonInfo = {
                    backgroundColor: $(this).css('background-color'),
                    cursor: $(this).css('cursor'),
                    html: $(this).html()
                }

                $(buttonElement).html('Adding Item...').css({
                    'background-color': '#00a4e4',
                    'cursor': 'default'
                });

                self.cartEndPoint({
                    add_product_id: $(buttonElement).attr('data-product-id'),
                    quantity: $(buttonElement).closest('.samplesRow').find('input').val()
                }, $(buttonElement).attr('data-product-name'), $(buttonElement).attr('data-product-color'), true, function() {
                    $(buttonElement).html('Added Item!');
                }, function() {
                    $(buttonElement).html(originalButtonInfo.html).css({
                        'background-color': originalButtonInfo.backgroundColor,
                        'cursor': originalButtonInfo.cursor
                    });
                    $(buttonElement).attr('data-adding-item', 'false');
                });
            }
        });
    }
};

/**
 * Envelopes.com Product Object
 * @constructor
 */
function EnvProduct() {
    EnvProductPage.call(this);


    /**
     * Product attributes
     * @type {{}}
     */
    var product = {
        'productId': null,
        'color': null,
        'name': null,
        'hex': null,
        'printable': false,
        'minColor': null,
        'maxColor': null,
        'hasWhiteInk': false,
        'hasSample': false,
        'paperWeight': null,
        'brand': null,
        'hasInventory': false,
        'hasVariableDataAbility': false,
        'hasAddressingAbility': false,
        'variableSettings': null,
        'templates': null,
        'design': null,
        'backDesign': null,
        'templateType': null,
        'rating': null,
        'parentProductId': null,
        'files': null,
        'oldFiles': null,
        'comments': null,
        'scene7DesignId': null,
        'designMethod': null,
        'addAddressing': false,
        'enabled': false,
        'percentSavings': 0,
        'addOnProduct': null,
        's7Data': []
    };
    this.getProduct = function() {
        return product;
    };
    this.setProduct = function(data) {
        product = data;
    };

    this.priceData = {};
    this.smallestQuantity = 1;
}

EnvProduct.prototype = Object.create(EnvProductPage.prototype);

EnvProduct.prototype = {
    constructor: EnvProduct,
    /**
     * Return the product id of Product object
     * @returns {String}
     */
    returnProductId: function() {
        return this.getProduct().productId;
    },
    /**
     * Activate, deactivate a product for purchase
     * @param enable
     */
    toggleProduct: function(enable) {
        this.getProduct().enabled = enable;
    },
    /**
     * Determine if the product is purchasable
     * @returns {*}
     */
    isProductEnabled: function() {
        return this.getProduct().enabled;
    },
    storeRecentlyViewed: function() {
        var recentlyVisitedProductsLimit = 14;
        if(typeof(Storage) !== 'undefined') {
            var recentlyVisitedProducts = {
                productList: []
            };

            if(typeof localStorage.recentlyVisitedProducts !== 'undefined') {
                recentlyVisitedProducts = JSON.parse(localStorage.recentlyVisitedProducts);
            }

            recentlyVisitedProducts.productList[(typeof (recentlyVisitedProducts.productList) != 'undefined' ? (recentlyVisitedProducts.productList).length : 0)] = { 'productId': this.getProduct().productId, 'color' : this.getProduct().color, 'name' : this.getProduct().name, 'href' : window.location.href, 'paperWeight' : this.getProduct().paperWeight, 'rating' : this.getProduct().rating, 'price' : this.getProduct().productPriceData[this.priceData.quantity].price};

            if(recentlyVisitedProducts.productList.length > recentlyVisitedProductsLimit) {
                var count = 0;
                for(var key in recentlyVisitedProducts.productList) {
                    if(recentlyVisitedProducts.hasOwnProperty('productList')) {
                        count++;
                        if(count > 14) {
                            recentlyVisitedProducts.productList.splice(0, 1);
                        }
                    }
                }
            }

            localStorageEnabled ? localStorage.recentlyVisitedProducts = JSON.stringify(recentlyVisitedProducts): '';
        }
    },
    /**
     * Prep all the price options to calculate base on the selected values
     */
    setPriceAttributes: function() {
        var self = this;

        //reset data first
        $.extend(true, self.priceData, {
            'colorsFront': 0,
            'colorsBack': 0,
            'whiteInkFront': false,
            'whiteInkBack': false,
            'isRush': false,
            'cuts': 0,
            'isFolded': false,
            'isFullBleed': false,
            'addresses': 0,
            'templateId': null
        });

        //check whether its plain or printed
        var plainOrPrinted = $('[data-key=plainOrPrinted].slSelected');
        switch(plainOrPrinted.attr('data-value')) {
            case 'plain':
                //do nothing
                break;
            case 'printed':
                $.each($('[data-price]'), function() {
                    if($(this).attr('data-key') in self.priceData && $(this).attr('data-selected') == 'true') {
                        if(($(this).attr('data-value') == 'whiteInkFront' || $(this).attr('data-value') == 'whiteInkBack') && ($(this).attr('data-key') == 'colorsFront' || $(this).attr('data-key') == 'colorsBack')) {
                            self.priceData[$(this).attr('data-key')] = 1;
                            self.priceData[$(this).attr('data-value')] = true;
                        } else if($(this).attr('data-key') == 'addresses') {
                            self.priceData[$(this).attr('data-key')] = parseInt($(this).attr('data-total'));
                        } else {
                            var value = (/^[a-zA-Z]+$/.test($(this).attr('data-value'))) ? $.parseJSON($(this).attr('data-value')) : parseInt($(this).attr('data-value'));
                            self.priceData[$(this).attr('data-key')] = value;
                        }
                    }
                });

                //set the templateId if its online designer
                if(self.getProduct().designMethod == 'online' && self.getProduct().design != '') {
                    self.priceData['templateId'] = self.getProduct().design;
                } else if(self.getProduct().designMethod == 'online' && self.getProduct().backDesign != '') {
                    self.priceData['templateId'] = self.getProduct().backDesign;
                }
                break;
        }

        $.extend(true, self.getProduct(), self.priceData);
    },
    /**
     * Calculate product price
     */
    calculatePrice: function(quantity) {
        var self = this;
        waitForFinalEvent(function() {
            self.setPriceAttributes();

            if(quantity !== null) {
                self.priceData.quantity = quantity;
            } else if(self.priceData.quantity == 0) {
                self.priceData.quantity = parseInt($('.' + self.priceGrid).find('.slSelected').attr('data-qty'));
            }

            var attr = $.extend(true, {}, self.priceData);
            attr.id = self.returnProductId();

            $.ajax({
                type: "GET",
                url: '/' + websiteId + '/control/getProductPrice',
                data: attr,
                dataType: 'json',
                cache: true
            }).done(function(productPriceData) {
                if(productPriceData.success && typeof productPriceData.priceList !== 'undefined') {
                    self.getProduct().productPriceData = productPriceData.priceList;
                    var originalPriceData = null;
                    $('.' + self.priceGrid).empty();

                    if (self.getProduct().percentSavings > 0) {
                        $.ajax({
                            type: "GET",
                            url: '/' + websiteId + '/control/getOriginalPrice',
                            data: attr,
                            dataType: 'json',
                            cache: true,
                            async: false
                        }).done(function(originalPriceDataReturn) {
                            if (originalPriceDataReturn.success && typeof originalPriceDataReturn.priceList !== 'undefined') {
                                originalPriceData = originalPriceDataReturn;
                            }
                        });
                    }

                    Object.keys(productPriceData.priceList).forEach(function(key, index) {
                        if (index == 0) {
                            self.smallestQuantity = key;
                            self.lowestPrice = productPriceData.priceList[key].price;
                        }

                        if(key >= attr.addresses) {
                            if (self.getProduct().percentSavings > 0 && typeof originalPriceData.priceList !== 'undefined') {
                                var productPercentSavings = (originalPriceData.priceList.hasOwnProperty(key)) ? Math.round((1 - (productPriceData.priceList[key].price / originalPriceData.priceList[key].price)) * 100) : 0;

                                $('.' + self.priceGrid).append(
                                    $('<div />').addClass('jqs-selectList qpsListItems selectList').attr({
                                        'data-qty': key,
                                        'data-price': productPriceData.priceList[key].price,
                                        'data-originalPrice': (originalPriceData.priceList.hasOwnProperty(key)) ? originalPriceData.priceList[key].price : 0,
                                        'bns-selection': '',
                                        'data-target': 'quantityPriceSelection'
                                    }).append(
                                        $('<div />').attr('selection-removeonselect', '').addClass('width30').append(
                                            $('<span />').addClass('selectCheckbox')
                                        )
                                    ).append(
                                        $('<div />').addClass('width95 quantityDisplay').html(numberWithCommas(key) + ' Qty')
                                    ).append(
                                        (originalPriceData.priceList.hasOwnProperty(key) && (originalPriceData.priceList[key].price) != null) ?
                                            $('<div />').attr((self.getProduct().isSpecialQuantityOffer ? "selection-removeonselect" : "null"), '').addClass('originalPriceDisplay').append(
                                                $('<strike />').html(formatCurrency(originalPriceData.priceList[key].price))
                                            )
                                            : ''
                                    ).append(
                                        $('<div />').addClass('priceDisplay margin-left-xxs').html(formatCurrency(productPriceData.priceList[key].price))
                                    ).append(
                                        $('<div />').attr((self.getProduct().isSpecialQuantityOffer ? "selection-removeonselect" : "null"), '').addClass('savingsDisplay').html(productPercentSavings > 0 ? 'Save ' + productPercentSavings + '%' : '')
                                    ).append(
                                        $('<div />').addClass('jqs-subShipNotice freeshipnote' + ((productPriceData.priceList[key].price < freeShippingAmount ) ? ' hidden' : '')).html(' + ').append(
                                            $('<i />').addClass('fa fa-truck')
                                        ).append(' Ships FREE w/code')
                                    ).append(
                                        $('<i />').addClass('fa fa-caret-right productCaretSidebar').attr('selection-showonselect', '')
                                    )
                                );
                            }
                            else {
                                if (index == 0) {
                                    $('.' + self.priceGrid).append(
                                        $('<div />').addClass('jqs-selectList qpsListItems selectList').attr({
                                            'data-qty': key,
                                            'data-price': productPriceData.priceList[key].price,
                                            'bns-selection': '',
                                            'data-target': 'quantityPriceSelection'
                                        }).append(
                                            $('<div />').attr('selection-removeonselect', '').addClass('width30').append(
                                                $('<span />').addClass('selectCheckbox')
                                            )
                                        ).append(
                                            $('<div />').addClass('width95 quantityDisplay').html(numberWithCommas(key) + ' Qty')
                                        ).append(
                                            $('<div />').addClass('priceDisplay').html(formatCurrency(productPriceData.priceList[key].price))
                                        ).append(
                                            $('<div />').addClass('jqs-pricePerUnit pricePerUnitDisplay').html(formatCurrency(productPriceData.priceList[key].price / key) + '/each')
                                        ).append(
                                            $('<div />').addClass('jqs-subShipNotice freeshipnote' + ((productPriceData.priceList[key].price < freeShippingAmount ) ? ' hidden' : '')).html(' + ').append(
                                                $('<i />').addClass('fa fa-truck')
                                            ).append(' Ships FREE w/code')
                                        ).append(
                                            $('<i />').addClass('fa fa-caret-right productCaretSidebar').attr('selection-showonselect', '')
                                        )
                                    );
                                }
                                else {
                                    $('.' + self.priceGrid).append(
                                        $('<div />').addClass('jqs-selectList qpsListItems selectList').attr({
                                            'data-qty': key,
                                            'data-price': productPriceData.priceList[key].price,
                                            'bns-selection': '',
                                            'data-target': 'quantityPriceSelection'
                                        }).append(
                                            $('<div />').attr('selection-removeonselect', '').addClass('width30').append(
                                                $('<span />').addClass('selectCheckbox')
                                            )
                                        ).append(
                                            $('<div />').addClass('width95 quantityDisplay').html(numberWithCommas(key) + ' Qty')
                                        ).append(
                                            $('<div />').addClass('priceDisplay').html(formatCurrency(productPriceData.priceList[key].price))
                                        ).append(
                                            $('<div />').addClass('jqs-pricePerUnit pricePerUnitDisplay').html(formatCurrency(productPriceData.priceList[key].price / key) + '/each')
                                        ).append(
                                            $('<div />').attr('selection-removeonselect', '').addClass('jqs-percentSavings percentSavingsDisplay').html('Save ' + Math.round((1 - (productPriceData.priceList[key].price / ((key / self.smallestQuantity) * self.lowestPrice))) * 100) + '%')
                                        ).append(
                                            $('<div />').addClass('jqs-subShipNotice freeshipnote' + ((productPriceData.priceList[key].price < freeShippingAmount ) ? ' hidden' : '')).html(' + ').append(
                                                $('<i />').addClass('fa fa-truck')
                                            ).append(' Ships FREE w/code')
                                        ).append(
                                            $('<i />').addClass('fa fa-caret-right productCaretSidebar').attr('selection-showonselect', '')
                                        )
                                    );
                                }
                            }
                            /*
                            $('[bns-estimate_print_cost]').html($('.' + self.priceGrid).html()).find('[data-target="quantityPriceSelection"]').attr('data-target', 'estimatePrintCostQuantity');
                            $('[bns-estimate_print_cost]').find('.jqs-subShipNotice').attr('selection-removeonselect', '');
                            $('[bns-estimate_print_cost]').find('.priceDisplay').attr('selection-removeonselect', '');
                            */
                        }

                        if(key == attr.quantity) {
                            self.showSelPrice(numberWithCommas(key) + ' Qty', {
                                salePrice: formatCurrency(productPriceData.priceList[key].price),
                                originalPrice: self.getProduct().percentSavings > 0 && originalPriceData.priceList.hasOwnProperty(key) ? formatCurrency(originalPriceData.priceList[key].price) : formatCurrency(productPriceData.priceList[key].price)
                            }, productPriceData.priceList[key].price >= freeShippingAmount);
                        }
                    });

                    $('[bns-sidebarid="quantityPriceSelection"] .freeshipnote').removeClass('hidden').addClass('hidden');

                    self.bindPrices();
                    $('.jqs-pricelist [data-target="quantityPriceSelection"][data-qty="' + attr.quantity + '"]').trigger('click.updateSelection');
                    $('.uploadQuantityDisplay').text(attr.quantity + ' Qty')
                    self.getFirstQuantity(); //if no price is selected for whatever reason, select the first
                    if (typeof productPriceData.priceList[attr.quantity] != 'undefined') {
                        $('.uploadPriceDisplay').text('$' + productPriceData.priceList[attr.quantity].price);
                    }
                }
            });
        }, 250, 'calcPrice');
    },
    getFirstQuantity: function() {
        var self = this;

        if($('.' + self.priceGrid).find('.slSelected').length == 0) {
            $('#sidebar-quantityList .jqs-selectList:first-child').trigger('click');
        }
    },
    /**
     *
     * @param qty
     * @param price
     * @param showShip
     */
    showSelPrice: function(qty, price, showShip) {
        $('.' + this.selQty).html(qty);

        if (this.getProduct().percentSavings > 0) {
            var productPercentSavings = Math.round((1 - (price.salePrice.replace(/[^0-9\.]/g, '') / price.originalPrice.replace(/[^0-9\.]/g, ''))) * 100);
            $('.' + this.originalPriceDisplay).html('<strike>' + price.originalPrice + '</strike>');
            $('.' + this.selPrice).html(price.salePrice);
            $('.' + this.savingsDisplay).html(productPercentSavings > 0 ? 'Save ' + productPercentSavings + '%' : '')
        }
        else {
            $('.' + this.selPrice).html(price.salePrice);
            $('#quantityPriceSelection .jqs-pricePerUnit').html(formatCurrency(price.salePrice.replace(/[^0-9\.]/g, '') / qty.replace(/[^0-9\.]/g, '')) + '/each');

            if (parseInt(qty) != this.smallestQuantity) {
                $('#quantityPriceSelection .jqs-percentSavings').remove();
                $('#quantityPriceSelection .jqs-pricePerUnit').after(
                    $('<span/>').addClass('jqs-percentSavings percentSavingsDisplay margin-left-xxs').html('Save ' + Math.round((1 - (price.salePrice.replace(/[^0-9\.]/g, '') / ((qty.replace(/[^0-9\.]/g, '') / this.smallestQuantity) * this.lowestPrice))) * 100) + '%')
                )
            }

            if(showShip) {
                $('.' + this.shipNotice).removeClass('hidden')
            } else {
                $('.' + this.shipNotice).removeClass('hidden').addClass('hidden');
            }
        }
    },
    /**
     * Bind price selections
     */
    bindPrices: function() {
        var self = this;
        productPage.bindClickEvents();
        initSideBar();

        $('.' + self.priceGrid + ' [data-qty]').off('click.priceGrid').on('click.priceGrid', function() {
            self.priceData.quantity = $(this).attr('data-qty').replace(/[^\d\.]+/g, '')*1;
        });

    },
    /**
     * Get the product description
     */
    getProductDesc: function() {
        var self = this;
        waitForFinalEvent(function() {
            $.ajax({
                type: "GET",
                url: '/' + websiteId + '/control/getProductDesc',
                data: { id: self.returnProductId() },
                dataType: 'json',
                cache: true
            }).done(function(data) {
                if(data.success) {
                    if(data.desc != null && data.desc != '') {
                        $('.' + self.longDesc).html(data.desc);
                        $('.' + self.longDesc+ 'Parent').removeClass('hidden')
                    } else {
                        $('.' + self.longDesc).empty();
                        $('.' + self.longDesc+ 'Parent').removeClass('hidden').addClass('hidden');
                    }
                    if(data.colorDesc != null && data.colorDesc != '') {
                        $('.' + self.colorDesc).html(data.colorDesc);
                        $('.' + self.colorDesc + 'Parent').removeClass('hidden')
                    } else {
                        $('.' + self.colorDesc).empty();
                        $('.' + self.colorDesc + 'Parent').removeClass('hidden').addClass('hidden');
                    }
                } else {
                    //TODO throw error
                }
            });
        }, 250, 'productDesc');
    },
    /**
     * Get the product inventory, this is not a cached page, direct hits to get the most up to date inventory
     */
    getProductInventory: function() {
        var self = this;
        waitForFinalEvent(function() {
            $.ajax({
                type: "POST",
                url: '/' + websiteId + '/control/getInventory',
                data: { id: self.returnProductId() },
                dataType: 'json',
                cache: false
            }).done(function(data) {
                self.hasInventory = data.hasInventory;
                if(data.success && data.hasInventory) {
                    $('.' + self.inventoryDom).removeClass('hidden');
                } else {
                    $('.' + self.inventoryDom).addClass('hidden');
                }
                self.getLocation();
            });
        }, 250, 'productInventory');
    },
    /**
     * Get the product reviews
     */
    getProductReviews: function() {
        var self = this;
        waitForFinalEvent(function() {
            $.ajax({
                type: "GET",
                url: '/' + websiteId + '/control/getProductReviews',
                data: {
                    product_id: self.returnProductId(),
                    product_rating: self.getProduct().rating
                },
                dataType: 'html',
                cache: true
            }).done(function(data) {
                if(data != '') {
                    $('#reviews').html(data);
                    createOrderSamplesEvent();
                }
            });
        }, 250, 'productReviews');
    },
    /**
     * Get the product features
     */
    getProductFeatures: function() {
        var self = this;
        waitForFinalEvent(function() {
            $.ajax({
                type: "GET",
                url: '/' + websiteId + '/control/getProductFeatures',
                data: { id: self.returnProductId() },
                dataType: 'json',
                cache: true
            }).done(function(data) {
                if(data.success && typeof data.features !== 'undefined') {
                    $('.' + self.featuresGrid).empty();
                    $('.' + self.featuresGrid).append(
                        $('<div/>').addClass('productSpecsRow').append(
                            $('<div/>').addClass('specsCol1').html('SKU')
                        ).append(
                            $('<div/>').addClass('specsCol2').html(data.productId)
                        )
                    );

                    $.each(data.features, function(k, v) {
                        $('.' + self.featuresGrid).append(
                            $('<div/>').addClass('productSpecsRow').append(
                                $('<div/>').addClass('specsCol1').html(k)
                            ).append(
                                $('<div/>').addClass('specsCol2').html(v)
                            )
                        );
                    });
                    createOrderSamplesEvent();
                }
            });
        }, 250, 'productFeatures');
    },
    /**
     * Get product assets such as additional images and videos
     */
    getProductAssets: function(callback) {
        var self = this;
        waitForFinalEvent(function() {
            $.ajax({
                type: "GET",
                url: '/' + websiteId + '/control/getProductAssets',
                data: { productId: self.returnProductId() },
                dataType:'json',
                cache: true
            }).done(function(data) {
                //show
                if(data.success && typeof data.productAssets !== 'undefined') {
                    var hasDefault = 'n';

                    for(var i = 0; i < data.productAssets.length; i++) {
                        if (typeof data.productAssets[i].assetDefault !== 'undefined' && data.productAssets[i].assetDefault == 'Y') {
                            hasDefault = 'y';
                            callback(data.productAssets[i].assetName);
                        }
                    }

                    $('.' + self.assetGrid).empty();
                    $('.' + self.assetGrid).append(
                        $('<div/>').attr({
                            'data-selected': (hasDefault == 'y' ? 'n' : 'y'),
                            'data-type': 'image',
                            'data-src': '//actionenvelope.scene7.com/is/image/ActionEnvelope/' + self.returnProductId() + '?hei=413&wid=510&fmt=jpeg&qlt=70&align=0,-1&bgc=ffffff'
                        }).append($('<img/>').attr('src', '//actionenvelope.scene7.com/is/image/ActionEnvelope/' + self.returnProductId() + '?hei=48&fmt=jpeg&qlt=70&bgc=ffffff'))
                    );

                    for(var i = 0; i < data.productAssets.length; i++) {
                        if (data.productAssets[i].assetType != 'printed') {
                            $('.' + self.assetGrid).append(
                                $('<div/>').attr({
                                    'data-selected': (hasDefault == 'y' && typeof data.productAssets[i].assetDefault !== 'undefined' && data.productAssets[i].assetDefault == 'Y' ? 'y' : 'n'),
                                    'data-type': data.productAssets[i].assetType,
                                    'data-src': (data.productAssets[i].assetType == 'video') ? '//fast.wistia.net/embed/iframe/' + data.productAssets[i].assetName : '//actionenvelope.scene7.com/is/image/ActionEnvelope/' + data.productAssets[i].assetName + '?hei=413&wid=510&fmt=png-alpha&align=0,-1'
                                }).append($('<img/>').attr('src', (data.productAssets[i].assetType == 'video') ? '//embed-ssl.wistia.com/deliveries/' + data.productAssets[i].assetThumbnail + '.jpg?image_crop_resized=48x36' : '//actionenvelope.scene7.com/is/image/ActionEnvelope/' + data.productAssets[i].assetName + '?hei=48&fmt=png-alpha')).append((data.productAssets[i].assetType == 'video') ? $('<i/>').addClass('fa fa-youtube-play') : '')
                            );
                        }
                    }

                    self.bindAssetImageSelection();
                }
            });
        }, 250, 'productAssets');
    },
    /**
     * Process template sources
     */
    getProductTemplates: function(fetch) {
        var self = this;

        function paintTemplates() {
            //paint templates
            var templateGrid = $('.' + self.templateGrid);
            templateGrid.empty();

            if(self.getProduct().templates != null && typeof self.getProduct().templates.primitives !== 'undefined' && self.getProduct().templates.primitives.length > 0) {
                var templateType = {};
                for(var k = 1; k <= 4; k++) {
                    switch (k) {
                        case 1:
                            templateType = {
                                value: 'return',
                                displayName: 'return address'
                            }
                            break;
                        case 2:
                            templateType = {
                                value: 'reply',
                                displayName: 'self addressed (reply)'
                            }
                            break;
                        case 3:
                            templateType = {
                                value: 'flap',
                                displayName: 'back flap address'
                            }
                            break;
                        case 4:
                            templateType = {
                                value: 'blank',
                                displayName: 'Start from Scratch'
                            }
                            break;
                    }

                    if(!self.getProduct().hasAddressingAbility && (templateType.value != 'blank')) {
                        continue;
                    }

                    if(templateType.value == 'blank') {
                        self.getProduct().design = self.getProduct().templates.primitives[0].scene7TemplateId;
                        self.getProduct().backDesign = self.getProduct().templates.primitives[0].backDesignId;
                        self.getProduct().templateType = (templateType.value).toUpperCase();
                    }

                    templateGrid.append(
                        $('<div/>').append(
                            $('<div/>').addClass((templateType.value == 'blank') ? 'selected' : '').attr({
                                'data-design': self.getProduct().templates.primitives[0].scene7TemplateId,
                                'data-backdesign': self.getProduct().templates.primitives[0].backDesignId,
                                'data-templatetype': (templateType.value).toUpperCase(),
                                'data-variant-ids': ''
                            }).append(
                                $('<img/>').attr({
                                    'src': self.getProduct().templates.primitives[0][templateType.value].replace(/ENV_HEX_PLACE_HOLDER/g, self.getProduct().hex) + '&hex=' + self.getProduct().hex + '&wid=260'
                                })
                            ).on('click', function () {
                                //if (optTest) {
                                //	$(this).parentsUntil('.designTemplateContainer').find('.jqs-customizeTemplateButton').removeClass('hidden');
                                //	$(this).parentsUntil('.designTemplateContainer').find('.jqs-uploadPopupButton').removeClass('hidden').addClass('hidden');
                                //}
                                self.getProduct().design = $(this).attr('data-design');
                                self.getProduct().backDesign = $(this).attr('data-backdesign');
                                self.getProduct().templateType = $(this).attr('data-templatetype');
                                self.setSelectable($(this), $('.designTemplateList > div > div'));
                            })
                        ).append(
                            $('<p/>').html((templateType.displayName).toUpperCase())
                        )
                    );
                }
            }

            //paint templates
            if(self.getProduct().templates != null && typeof self.getProduct().templates.templates !== 'undefined' && self.getProduct().templates.templates.length > 0) {
                for(var k = 0; k < self.getProduct().templates.templates.length; k++) {
                    var variants = '';
                    if(typeof self.getProduct().templates.templates[k].productVariantIds !== 'undefined') {
                        for(var l = 0; l < self.getProduct().templates.templates[k].productVariantIds.length; l++) {
                            variants += ((variants != '') ? ',' : '') + self.getProduct().templates.templates[k].productVariantIds[l];
                        }
                    }

                    templateGrid.append(
                        $('<div/>').append(
                            $('<div/>').attr({
                                'data-design': self.getProduct().templates.templates[k].scene7TemplateId,
                                'data-backdesign': self.getProduct().templates.templates[k].backDesignId,
                                'data-templatetype': '',
                                'data-variant-ids': variants
                            }).append(
                                $('<img/>').attr({
                                    'src': self.getProduct().templates.templates[k].thumbnailPath.replace(/ENV_HEX_PLACE_HOLDER/g, self.getProduct().hex) + '&hex=' + self.getProduct().hex + '&wid=260'
                                })
                            ).on('click', function () {
                                self.getProduct().design = $(this).attr('data-design');
                                self.getProduct().backDesign = $(this).attr('data-backdesign');
                                self.getProduct().templateType = null;
                                self.setSelectable($(this), $('.designTemplateList > div > div'));
                            })
                        ).append(
                            $('<p/>').html(self.getProduct().templates.templates[k].scene7TemplateId)
                        )
                    );
                }
            }
        }

        if(fetch) {
            $.ajax({
                type: "GET",
                url: '/' + websiteId + '/control/getDesignsForProduct',
                data: {id: self.returnProductId(), vId: self.getProduct().parentProductId},
                dataType: 'json',
                cache: true,
                async: false
            }).done(function (data) {
                if(data.success) {
                    delete data.success;

                    var foundPrimitive = false;
                    for (var i = 0; i < data.primitives.length; i++) {
                        if (typeof data.primitives[i].productVariantIds !== 'undefined' && $.inArray(self.returnProductId(), data.primitives[i].productVariantIds) >= 0) {
                            foundPrimitive = true;
                            data.primitives = [data.primitives[i]];
                        }
                    }

                    if(!foundPrimitive && data.primitives.length > 1) {
                        for (var i = 0; i < data.primitives.length; i++) {
                            if (typeof data.primitives[i].productVariantIds == 'undefined') {
                                data.primitives = [data.primitives[i]];
                            }
                        }
                    }

                    self.getProduct().templates = data;

                    paintTemplates();
                }
            });
        } else {
            paintTemplates();
        }
    },
    /**
     *
     */
    bindAssetImageSelection: function() {
        var self = this;
        $('.' + self.imageSelector + ' div[data-selected]').each(function() {
            $(this).on('click', function () {
                if ($(this).attr('data-selected') != 'y') {
                    $('.' + self.imageSelector + ' div[data-selected]').each(function () {
                        $(this).attr('data-selected', 'n');
                    });

                    $(this).attr('data-selected', 'y');

                    if($(this).attr('data-type') == 'image') {
                        $('.productImageContainer .productImage').show().attr('src', $(this).attr('data-src'));
                        $('.productImageContainer .jqs-productVideoContainer').addClass('hidden');
                        var splitSource = $(this).attr('data-src').match(/(.*?hei=)\d+(.*)/);
                        $('.jqs-enhancedImage').attr('src', (splitSource[1] + '600' + splitSource[2]).replace(/\&wid\=\d+/, ''));
                    } else {
                        $('.productImageContainer .jqs-productVideoContainer').removeClass('hidden');
                        $('.productImageContainer .productImage').hide().attr('src', '');
                        $('#productVideo').attr('src', $(this).attr('data-src'));
                    }
                }
            });
        });
    },
    /**
     * Bind addressing templates
     */
    bindAddressingTemplates: function() {
        var self = this;
        this.getProduct().variableSettings = $.parseJSON($('.' + this.addressGrid).find('.selected').attr('data-variable-style').replace(/\'/g, '"'));

        $('.' + this.addressGrid + ' [data-variable-style]').on('click', function() {
            self.getProduct().variableSettings = $.parseJSON($(this).attr('data-variable-style').replace(/\'/g, '"'));
            self.setSelectable($(this), $('.' + self.addressGrid + ' > div > div'));
        });

        $('.' + self.addressOptions + ' [data-addressing-option]').on('click', function() {
            self.setSelectable($(this), $('.' + self.addressOptions + ' > div'));
        });
    },
    /**
     * set elements selectable
     */
    setSelectable: function(selectedElement, siblingElements, selectedClass) {
        if(typeof selectedClass === 'undefined') {
            selectedClass = 'selected';
        }

        $(siblingElements).removeClass(selectedClass);
        $(selectedElement).addClass(selectedClass);
    },

    /**
     * Get the geolocation of the user if it hasn't already been retrieved
     */
    getLocation: function() {
        var self = this;

        var setQuickShipCookie = function(zip) {
            if(!zip) {
                document.cookie = '__ae_qs=false;path=/';
            } else {
                $.ajax({
                    type: 'GET',
                    url: '/' + websiteId + '/control/getTransitTime?postalCode=' + zip,
                    dataType: 'json',
                    cache: true
                }).done(function(data) {
                    if(typeof data !== 'undefined' && data.success && data.transitTime == 1) {
                        document.cookie = '__ae_qs=true;path=/';
                        self.showQuickShipMessage();
                    } else {
                        document.cookie = '__ae_qs=false;path=/';
                    }
                }).fail(function(data) {
                    document.cookie = '__ae_qs=false;path=/';
                });
            }
        };

        waitForFinalEvent(function() {
            if(!botcheck() && typeof $.cookie('__ae_qs') === 'undefined') {
                $.ajax({
                    type: 'GET',
                    url: '//texel.envelopes.com/getLocation',
                    data: {},
                    dataType: 'json',
                    cache: false
                }).done(function(data) {
                    if(typeof data.zip_code !== 'undefined') {
                        setQuickShipCookie(data.zip_code);
                    } else {
                        setQuickShipCookie(false);
                    }
                }).fail(function(data) {
                    setQuickShipCookie(false);
                });
            } else {
                self.showQuickShipMessage();
            }
        }, 250, 'geoLocation');
    },

    /**
     * Show the quick ship message
     */
    showQuickShipMessage: function() {
        if(this.hasInventory) {
            if(typeof $.cookie('__ae_qs') !== 'undefined' && $.cookie('__ae_qs') == 'true' && !startedAsDesign) {
                $('.' + this.getItQuick).removeClass('hidden');
                $('.' + this.inventoryDom).removeClass('hidden').addClass('hidden');
            } else {
                $('.' + this.getItQuick).removeClass('hidden').addClass('hidden');
                $('.' + this.inventoryDom).removeClass('hidden');
            }
        }
    },
    /**
     * When a product is clicked from search, we need to notify sli of this click
     */
    sendSLIClick: function() {
        // SLI Reporting on search products clicked.
        if((document.referrer).match(/.*?\/search(?:\?|$)/) || (document.referrer).match(/.*?\/category(?:\?|$)/)) {
            $.ajax({
                type: 'POST',
                url: '//envelopes.resultspage.com/search',
                data: {
                    'p': 'LG',
                    'lot': 'json',
                    'lbc': 'envelopes',
                    'ua': navigator.userAgent,
                    'ref': document.referrer,
                    'w': getUrlParameters('w', document.referrer, false),
                    'url': window.location.href,
                    'uid': ((typeof $.cookie('__SS_Data') !== 'undefined') ? $.cookie('__SS_Data') : '')
                },
                dataType: 'jsonp',
                async: true
            });
        }
    }
};

$('[bns-submit_custom_quote="estimatePrintCost"]').on('click', function() {
    var userEmail = $('[name="estimatePrintCostEmail"]').val();
    var buttonElement = $(this);

    if (validateEmailAddress(userEmail)) {
        buttonElement.addClass('invisible');

        $.ajax({
            type: 'POST',
            url: '/envelopes/control/emailQuote',
            data: {
                productId: productPage.returnActiveProduct(true).productId,
                productName: productPage.returnActiveProduct(true).name,
                productColorsFront: $('#inkFrontSelection2 > div').html(),
                productColorsBack: $('#inkBackSelection2 > div').html(),
                productQuantity: $('[bns-sidebarid="estimatePrintCostQuantity"] > div:first-child').html(),
                productPrice: $('[bns-estimate_print_cost_total]').html(),
                webSiteId: 'envelopes', 
                userEmail: userEmail,
                messageType: 'estimatePrintCost'
            },
            cache: false
        }).done(function(response) {
            response = JSON.parse(response);
            if (response.success) {
                // do success
                $('[jqs-useremail]').html(userEmail);
                $('[bns-showonsuccess="thankYou"]').removeClass('hidden');
                $('[bns-removeonsuccess="thankYou"').addClass('hidden');
            } else {
                // error message here
            }
            buttonElement.removeClass('invisible');
        }).fail(function() {
            buttonElement.removeClass('invisible');
        })
    } else {
        // Warn user that email is not valid
        alert('Please enter a valid e-mail.');
    }
});