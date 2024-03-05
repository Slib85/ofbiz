var runHTML = '<blockquote class="jqs-run blockquote custom-blockquote blockquote-success"><div class="run-label">RUN1</div><div class="jqs-remove-addl-run text-xs-right"><i class="icon wb-close pointer jqs-remove-addl-run" style="display: none;" aria-hidden="true"></i></div><div class="row jqs-images"><div class="jqs-image"><div class="img-label input-group input-group-file"><span class="input-group-btn"><span class="btn btn-primary">IMAGE #1</span></span><select name="IMAGES" class="form-control"><option value="15">Up to 15 Sq. In.</option><option value="36">Up to 36 Sq. In.</option><option value="40">Up to 40 Sq. In.</option><option value="40+">Over 40 Sq. In.</option></select></div></div></div><div style="height:30px"><div class="form-group pull-right clearfix margin-right-15"><button type="button" style="position: relative; top:-2px" class="jqs-add-image btn btn-primary addaction">Add Image</button></div></div></blockquote>';

var imageHTML = '<div class="jqs-image"><div class="img-label input-group input-group-file"><span class="input-group-btn"><span class="jqs-btn-label btn btn-primary">IMAGE #1</span></span><select name="IMAGES" class="form-control"><option value="15">Up to 15 Sq. In.</option><option value="36">Up to 36 Sq. In.</option><option value="40">Up to 40 Sq. In.</option><option value="40+">Over 40 Sq. In.</option><option value="remove">Remove this Image</option></select></div></div>';
(function ($) {
    var parseColorJSON = colorJSON === '' ? undefined : $.parseJSON(colorJSON);

    $.extend({

        bindDefaultMessages: function() {
            $('.jqs-show-quote').off('click.show-quote-msg').on('click.show-quote-msg', function() {
                $.showMessage("Please select at least one printing option to view the Quote", true);
            });
        },

        formatNumber: function (x) {
            return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        },

        bindPanelToggleEvent: function(bindingEls) {
            if(typeof bindingEls === 'undefined') {
                bindingEls = $('.jqs-panel-toggle');
            }

            $(bindingEls).each(function(i, bindingEl) {
                if($(bindingEl).hasClass('wb-plus')) {
                    $(bindingEl).closest('.panel-heading').addClass('pointer').on('click.panel.expand', function() {
                        var el = $(this).closest('.panel');
                        var isCollapsed = el.hasClass('is-collapse');
                        if(isCollapsed) {
                            el.removeClass('is-collapse');
                            $(this).off('click.panel.expand').removeClass('pointer').find('.jqs-panel-toggle').removeClass('wb-plus').addClass('wb-close');
                            $.bindPanelToggleEvent(bindingEl);
                            $.buildState();
                        }

                    });
                } else {
                    $(bindingEl).closest('.panel-heading').removeClass('pointer');
                    $(bindingEl).on('click.panel.collapse', function(e){
                        var el = $(this).closest('.panel');
                        var panelName = $(el).find('.panel-title').data('panelname');
                        var isCollapsed = el.hasClass('is-collapse');
                        if(!isCollapsed) {
                            el.addClass('is-collapse');
                            $(this).removeClass('wb-close').addClass('wb-plus').off('click.panel.collapse');
                            $(this).closest('.panel-heading').addClass('pointer');
                            $.buildState();
                            e.stopPropagation();
                            $.bindPanelToggleEvent(bindingEl);
                        }
                    });
                }
            });
        },

        bindAddImageToRunEvent: function(elemToBind) {
            if(typeof elemToBind !== 'undefined') {
                $(elemToBind).on('click', function() {
                    $.addImageToRun($(this));
                });
            } else {
                $('.jqs-add-image').on('click', function() {
                    $.addImageToRun($(this));
                });
            }

        },

        bindAddRunToPrintOptionEvent: function(elemToBind) {
            if(typeof elemToBind !== 'undefined') {
                $(elemToBind).on('click', function() {
                    $.addRunToPrintOption($(this));
                });
            } else {
                $('.jqs-add-run').on('click', function() {
                    $.addRunToPrintOption($(this));
                });
            }

        },

        bindRemoveRunFromPrintOptionEvent: function(elemToBind) {
            if(typeof elemToBind !== 'undefined') {
                $(elemToBind).on('click', function() {
                    $.removeRunFromPrintOption($(this));
                });
            } else {
                $('.jqs-remove-addl-run').on('click', function() {
                    $.removeRunFromPrintOption($(this));
                });
            }

        },

        bindRemoveImageFromRunEvent: function(elemToBind) {
            if(typeof elemToBind !== 'undefined') {
                $(elemToBind).on('click', function() {
                    $.removeImageFromRun($(this));
                });
            } else {
                $('.jqs-remove-image').on('click', function() {
                    $.removeImageFromRun($(this));
                });
            }

        },

        addImageToRun: function(el) {
            var runEl = $(el).closest('.jqs-run');
            var numOfImages = $(runEl).find('.jqs-image').length;
            if (numOfImages < 10) {
                var imgEl = $(imageHTML);
                $(imgEl).find('.jqs-btn-label').html('IMAGE #' + (numOfImages + 1));
                $(runEl).find('.jqs-images').append(imgEl);
                $.stateChangeEvent();
                $.buildState();
            }
        },

        addRunToPrintOption: function(el) {
            var runsEl = $(el).parent().siblings('.jqs-runs');
            var numOfRuns = $(runsEl).find('.jqs-run').length;
            var printOption = $(el).closest('.jqs-FOIL-STAMP').length > 0 ? 'FOIL' : $(el).closest('.jqs-EMBOSS').length > 0 ? 'EMBOSS' : '';
            if((printOption == 'FOIL' && numOfRuns < 5) || (printOption == 'EMBOSS' && numOfRuns < 2)) {
                var runEl = $(runHTML);
                $(runEl).find('.run-label').html('RUN' + (numOfRuns + 1));
                $(runsEl).append(runEl);
                $(runsEl).find('.jqs-remove-addl-run').show();
                $.bindRemoveRunFromPrintOptionEvent($(runEl).find('.jqs-remove-addl-run'));
                $.bindAddImageToRunEvent($(runEl).find('.jqs-add-image'));
                $.stateChangeEvent();
                $.buildState();
            } else {
                if(printOption == 'FOIL' && numOfRuns >= 5) {
                    alert("Foil Stamping supports a maximum of 5 RUNS only");
                }  else if(printOption == 'EMBOSS' && numOfRuns >= 2) {
                    alert("Embossing supports a maximum of 2 RUNS only");
                }
            }
        },

        removeImageFromRun: function(el) {
            var runEl = $(el).closest('.jqs-run');
            var tbody = $(runEl).find('tbody');
            var numOfImages = $(tbody).find('tr').length;
            if(numOfImages > 1) {
                $(tbody).find('tr:last').remove();
            }
        },

        removeRunFromPrintOption: function(el) {
            var runsEl = $(el).closest('.jqs-runs');
            var numOfRuns = $(runsEl).find('.jqs-run').length;
            if(numOfRuns > 1) {
                var runEl = $(el).closest('.jqs-run');
                $(runEl).remove();
                if(numOfRuns === 2) {
                    $(runsEl).find('.jqs-remove-addl-run').hide();
                }
            }
            $.buildState();
        },

        buildState: function(el) {

            if (typeof el !== 'undefined' && $(el).attr('name') === 'IMAGES' && $(el).val() === 'remove') {
                var imagesEl = $(el).closest('.jqs-images');
                $(el).closest('.jqs-image').remove();
                $(imagesEl).find('.jqs-image').each(function (i) {
                    $(this).find('.jqs-btn-label').html('IMAGE #' + (i + 1));
                });
            }
            var state = {};
            if ($(el).attr('name') === 'PRINT_METHOD') {
                if ($(el).val() === 'PMS_OFFSET') {
                    $(el).parent().parent().parent().find('select[name="NUMBER_OF_INKS"]').find('option[value="0"]').remove().val('1');
                } else {
                    $(el).parent().parent().parent().find('select[name="NUMBER_OF_INKS"]').prepend('<option value="0">0</option>').val('0');
                }
            }

            state['VENDOR_SKU'] = $('select[name="SKU"] option:selected').val();
            state['VENDOR_ID'] = $('select[name="VENDOR"] option:selected').val();;
            state['COLOR_GROUP'] = $('select[name="COLOR_GROUP"] option:selected').val();
            state['COLOR_NAME'] = $('select[name="COLOR_NAME"] option:selected').val();
            state['PAPER_TEXTURE'] = $('select[name="PAPER_TEXTURE"] option:selected').val();
            state['PAPER_WEIGHT'] = $('select[name="PAPER_WEIGHT"] option:selected').val();
            state['RESPONSE_TYPE'] = 'DETAILED';
            state['CUSTOM_QUANTITY'] = $('input[name="CUSTOM_QUANTITY"]').val() !== '' ? parseInt($('input[name="CUSTOM_QUANTITY"]').val()) : '';

            var quantities = [];
            $('input[name="QUANTITIES"]').each(function () {
                if($(this).val() !== '') {
                    quantities.push(parseInt($(this).val()));
                }
            });
            state['QUANTITIES'] = quantities;
            var printOptions = [];
            var offsetPrintOption = $.getOffsetPrintOption();
            if (typeof offsetPrintOption !== 'undefined') {
                printOptions.push(offsetPrintOption);
            }

            var foilStampPrintOption = $.getFoilStampingPrintOption();
            if (typeof foilStampPrintOption !== 'undefined') {
                printOptions.push(foilStampPrintOption);
            }
            var embossingPrintOption = $.getEmbossingPrintOption();
            if (typeof embossingPrintOption !== 'undefined') {
                printOptions.push(embossingPrintOption);
            }

            state['CUSTOM_OPTIONS'] = printOptions;

            state['ADDONS_OPTIONS'] = $.getAddons();

            $.renderJSON($('.jqs-REQUEST .panel-body.container'), state);



            $.ajax({
             type: 'POST',
             data: {
             'pricingRequest': JSON.stringify(state)
             },
             async: false,
             dataType: 'json',
             url: '/admin/control/getPricing'
             }).done(function (data) {
                 if(data.success === true) {
                    var response = {};
                    response['VENDORS_SIMPLE_PRICING'] = data.output.VENDORS_SIMPLE_PRICING;
                    if(state.RESPONSE_TYPE === 'DETAILED') {
                        response['VENDORS_DETAILED_PRICING'] = data.output.VENDORS_DETAILED_PRICING;
                    }
                    $.renderJSON($('.jqs-RESPONSE .panel-body.container'), response);
                 } else {
                     $.renderJSON($('.jqs-RESPONSE .panel-body.container'), data);
                 }
             });
        },

        renderJSON : function(el, JSONObject) {
            $(el).text(JSON.stringify(JSONObject)).beautifyJSON();
        },

        stateChangeEvent : function() {
//                $('select[name="VENDOR"],select[name="STYLE_GROUP"],select[name="STYLE"],select[name="MATERIAL_TYPE"],select[name="STOCK_TYPE"],select[name="STOCK"],select[name="IMAGES"]').off().on('change', function() {
            $('.jqs-CALCULATOR').find('input,select').off().on('change', function() {
                    $.buildState($(this));
            });
        },

        getOffsetPrintOption : function() {
            var offsetPrintOption;
            if($('.jqs-offset.is-collapse').length === 0) {
                offsetPrintOption = {'CUSTOM_OPTION_NAME' : 'OFFSET'};
                var sides = [];
                var side1 = {};
                side1['PRINT_METHOD'] = $('select[name="PRINT_METHOD"] option:selected', '.jqs-OFFSET .jqs-SIDE1').val();
                side1['NUMBER_OF_INKS'] = parseInt($('select[name="NUMBER_OF_INKS"] option:selected', '.jqs-OFFSET .jqs-SIDE1').val());
                side1['HEAVY_COVERAGE'] = $('select[name="HEAVY_COVERAGE"] option:selected', '.jqs-OFFSET .jqs-SIDE1').val();
                side1['COLOR_WASHES'] = parseInt($('select[name="COLOR_WASHES"] option:selected', '.jqs-OFFSET .jqs-SIDE1').val());
                side1['PLATE_CHANGES'] = parseInt($('select[name="PLATE_CHANGES"] option:selected', '.jqs-OFFSET .jqs-SIDE1').val());
                sides.push(side1);
                if($('.jqs-OFFSET .jqs-side2.is-collapse').length === 0) {
                    var side2 = {};
                    side2['PRINT_METHOD'] = $('select[name="PRINT_METHOD"] option:selected', '.jqs-OFFSET .jqs-SIDE2').val();
                    side2['NUMBER_OF_INKS'] = parseInt($('select[name="NUMBER_OF_INKS"] option:selected', '.jqs-OFFSET .jqs-SIDE2').val());
                    side2['HEAVY_COVERAGE'] = $('select[name="HEAVY_COVERAGE"] option:selected', '.jqs-OFFSET .jqs-SIDE2').val();
                    side2['COLOR_WASHES'] = parseInt($('select[name="COLOR_WASHES"] option:selected', '.jqs-OFFSET .jqs-SIDE2').val());
                    side2['PLATE_CHANGES'] = parseInt($('select[name="PLATE_CHANGES"] option:selected', '.jqs-OFFSET .jqs-SIDE2').val());
                    sides.push(side2);
                }
                offsetPrintOption['SIDES'] = sides;
            }

            return offsetPrintOption;
        },


        getFoilStampingPrintOption : function() {
            var foilStampingPrintOption;
            if($('.jqs-foil-stamp.is-collapse').length === 0) {
                foilStampingPrintOption = {'CUSTOM_OPTION_NAME': 'FOIL_STAMPING'};
                var runs = [];
                $('.jqs-FOIL-STAMP .jqs-run').each(function(){
                    var run = {};
                    var images = [];
                    $(this).find('.jqs-image').each(function(){
                        images.push($(this).find('select').val());
                    });
                    run['IMAGES'] = images;
                    runs.push(run);
                });
                foilStampingPrintOption['RUNS'] = runs;
            }
            return foilStampingPrintOption;
        },

        getEmbossingPrintOption : function() {
            var embossingPrintOption;
            if($('.jqs-emboss.is-collapse').length === 0) {
                embossingPrintOption = {'CUSTOM_OPTION_NAME': 'EMBOSSING'};
                var sides = [];
                var side1 = {};
                var runs = [];
                $('.jqs-EMBOSS .jqs-SIDE1 .jqs-run').each(function(){
                    var run = {};
                    var images = [];
                    $(this).find('.jqs-image').each(function(){
                        images.push($(this).find('select').val());
                    });
                    run['IMAGES'] = images;
                    runs.push(run);
                });
                side1['RUNS'] = runs;
                sides.push(side1);

                if($('.jqs-EMBOSS .jqs-side2.is-collapse').length === 0) {
                    var side2 = {};
                    runs = [];
                    $('.jqs-EMBOSS .jqs-SIDE2 .jqs-run').each(function(){
                        var run = {};
                        var images = [];
                        $(this).find('.jqs-image').each(function(){
                            images.push($(this).find('select').val());
                        });
                        run['IMAGES'] = images;
                        runs.push(run);
                    });
                    side2['RUNS'] = runs;
                    sides.push(side2);
                }
                embossingPrintOption['SIDES'] = sides;

            }
            return embossingPrintOption;
        },

        getQuantityBreaks: function() {

        },
        getAddons: function() {
            var addons = [];
            var coatings1 = [];
            var coatings2 = [];
            var attachments1 = [];
            var misc1 = [];
            var coatingSides = [];
            var attachmentSides = [];
            var miscSides = [];
                if($('.jqs-ADDON div.jqs-regular .jqs-addon.is-collapse').length === 0) {
                    if($('.jqs-addon-coating-side1.is-collapse').length === 0) {
                        $('.jqs-addon-coating-side1').find('input[name="COATING_SIDE_1"]:checked').each(function(){
                            coatings1.push($(this).val());
                        });
                        coatingSides.push(coatings1);
                    }
                    if($('.jqs-addon-coating-side2.is-collapse').length === 0) {
                        $('.jqs-addon-coating-side2').find('input[name="COATING_SIDE_2"]:checked').each(function(){
                            coatings2.push($(this).val());
                        });
                        coatingSides.push(coatings2);
                    }
                    if($('.jqs-addon-attachment-side1.is-collapse').length === 0) {
                        $('.jqs-addon-attachment-side1').find('input[name="ATTACHMENT_SIDE_1"]:checked').each(function(){
                            attachments1.push($(this).val());
                        });
                        attachmentSides.push(attachments1);
                    }
                    if($('.jqs-addon-misc-side1.is-collapse').length === 0) {
                        $('.jqs-addon-misc-side1').find('input[name="ADDON_SIDE_1"]:checked').each(function(){
                            misc1.push($(this).val());
                        });
                        miscSides.push(misc1);
                    }
                    if(coatingSides.length > 0) {
                        var coating = {};
                        coating['ADDON_TYPE'] = 'COATINGS';
                        coating['SIDES'] = coatingSides;
                        addons.push(coating);
                    }

                    if(attachmentSides.length > 0) {
                        var attachment = {};
                        attachment['ADDON_TYPE'] = 'ATTACHMENTS';
                        attachment['SIDES'] = attachmentSides;
                        addons.push(attachment);
                    }

                    if(miscSides.length > 0) {
                        var misc = {};
                        misc['ADDON_TYPE'] = 'MISC_ADDONS';
                        misc['SIDES'] = miscSides;
                        addons.push(misc);
                    }
                }
            return addons;
        },


        closeQuoteDialogs: function(quoteId) {
            $('#jqs-price-breakdown').modal('hide');
            $('#jqs-quote').modal('hide');
        },

        showPriceBreakdown: function(buttonEl) {
            $(buttonEl).closest('.modal').modal('hide');
            $('#jqs-price-breakdown').modal('show');
        },
        closePriceBreakdown: function(buttonEl) {
            $(buttonEl).closest('.modal').modal('hide');
            $('#jqs-quote').modal('show');
        },
        closeModal: function(buttonEl) {
            $(buttonEl).closest('.modal').modal('hide');
        },
        closeAllModal: function() {
            $('.modal').each(function(){
                $(this).modal('hide');
            });
        },
        clearDropdownOptions: function (dropdownEl) {
            $(dropdownEl).find('option:gt(0)').remove();
        },
        updateColorDropdowns: function (colorDropdownEl) {
            var name = $(colorDropdownEl).attr('name');
            var colorGroupEl = $('select[name="COLOR_GROUP"]');
            var colorNameEl = $('select[name="COLOR_NAME"]');
            var paperTextureEl = $('select[name="PAPER_TEXTURE"]');
            var paperWeightEl = $('select[name="PAPER_WEIGHT"]');
            switch (name) {
                case 'COLOR_GROUP' :
                    var colorGroupValue = $(colorDropdownEl).val();
                    var colorGroup = $.getColorGroup(colorGroupValue);
                    console.log(colorGroup);
                    $.clearDropdownOptions(colorNameEl);
                    $.clearDropdownOptions(paperTextureEl);
                    $.clearDropdownOptions(paperWeightEl);
                    $.each(colorGroup.colorNames, function (i, v) {
                        $(colorNameEl).append('<option value="' + v.value + '">' + v.text + '</option>');
                    });
                    break;
                case 'COLOR_NAME' :
                    var colorNameValue = $(colorNameEl).val();
                    var colorName = $.getColorName(colorNameValue, $.getColorGroup($(colorGroupEl).val()));
                    $.clearDropdownOptions(paperTextureEl);
                    $.clearDropdownOptions(paperWeightEl);
                    $.each(colorName.paperTextures, function (i, v) {
                        $(paperTextureEl).append('<option value="' + v.value + '">' + v.text + '</option>');
                    });
                    break;
                case 'PAPER_TEXTURE' :
                    var colorTextureValue = $(paperTextureEl).val();
                    var colorNameValue = $(colorNameEl).val();
                    var colorGroup = $(colorGroupEl).val();
                    var paperTexture = $.getPaperTexture(colorTextureValue, $.getColorName(colorNameValue, $.getColorGroup(colorGroup)));
                    $.clearDropdownOptions(paperWeightEl);
                    console.log(paperTexture);
                    $.each(paperTexture.paperWeights, function (i, v) {
                        $(paperWeightEl).append('<option value="' + v.value + '">' + v.text + '</option>');
                    });
                    break;
            }
        },
        getColorGroup: function (colorGroupValue) {
            var colorGroups = parseColorJSON.colorGroups;
            var colorGroup = {}
            $.each(colorGroups, function (i, v) {
                if (v.value === colorGroupValue) {
                    colorGroup = v;
                    return false;
                }
            });
            return colorGroup;
        },
        getColorName: function (colorNameValue, colorGroup) {
            var colorNames = colorGroup.colorNames;
            var colorName = {}
            $.each(colorNames, function (i, v) {
                if (v.value === colorNameValue) {
                    colorName = v;
                    return false;
                }
            });
            return colorName;
        },
        getPaperTexture: function (paperTextureValue, colorName) {
            var paperTextures = colorName.paperTextures;
            var paperTexture = {}
            $.each(paperTextures, function (i, v) {
                if (v.value === paperTextureValue) {
                    paperTexture = v;
                    return false;
                }
            });
            return paperTexture;
        },
        getPaperWeight: function (paperWeightValue, paperTexture) {
            var paperWeights = paperTexture.paperWeights;
            var paperWeight = {}
            $.each(paperWeights, function (i, v) {
                if (v.value === paperWeightValue) {
                    paperWeight = v;
                    return false;
                }
            });
            return paperWeight;
        },
        bindColorDropdownOnChangeEvent: function () {
            $('.jqs-color-dropdown').on('change', function () {
                $.updateColorDropdowns($(this));
            });
        },
        bindVendorDropdownOnChangeEvent: function () {
            $('.jqs-vendor-dropdown').on('change', function () {
                var vendorId = $(this).val();
                if(vendorId !== '') {
                    $('#vendorChangeForm').find('input[name="vendorId"]').val(vendorId);
                    $('#vendorChangeForm').submit();
                }
            });
        }
    });
    $.fn.beautifyJSON = function(options) {
        var defaults = {
            type: "strict",
            hoverable: true,
            collapsible: true,
            color: true
        };
        var settings = jQuery.extend({}, defaults, options);
        this.each( function() {
            if(settings.type == "plain") {
                var INCREMENT = "&ensp;&ensp;&ensp;";
                var s = [];
                var indent = "";
                var input = this.innerHTML;
                var output = input.split('"').map(function(v,i){
                    return i%2 ? v : v.replace(/\s/g, "");
                }).join('"');
                var text = "";
                function peek(stack) {
                    var val = stack.pop();
                    stack.push(val);
                    return val;
                }
                for(i = 0; i < input.length; i++) {
                    if(input.charAt(i) == '{') {
                        s.push(input.charAt(i));
                        text += input.charAt(i)+'<br>';
                        indent += INCREMENT;
                        text += indent;
                    } else if(input.charAt(i) == '\"' && peek(s) != '\"') {
                        text += input.charAt(i);
                        s.push(input.charAt(i));
                    } else if(input.charAt(i) == '[' && input.charAt(i+1) == ']') {
                        s.push(input.charAt(i));
                        text += input.charAt(i);
                        indent += INCREMENT;
                    } else if(input.charAt(i) == '[') {
                        s.push(input.charAt(i));
                        text += input.charAt(i)+'<br>';
                        indent += INCREMENT;
                        text += indent;
                    } else if(input.charAt(i) == ']') {
                        indent = indent.substring(0,(indent.length-18));
                        text += '<br>'+indent;
                        text += input.charAt(i)
                        s.pop();
                    } else if(input.charAt(i) == '}') {
                        indent = indent.substring(0,(indent.length-18));
                        text += '<br>'+indent+input.charAt(i);
                        s.pop();
                        if(s.length != 0)
                            if(peek(s) != '[' && peek(s) != '{') {
                                text += indent;
                            }
                    } else if(input.charAt(i) == '\"' && peek(s) == '\"') {
                        text += input.charAt(i)
                        s.pop();
                    } else if(input.charAt(i) == ',' && peek(s) != '\"') {
                        text += input.charAt(i)+'<br>';
                        text += indent;
                    } else if(input.charAt(i) == '\n') {
                    } else if(input.charAt(i) == ' ' && peek(s) != '\"') {
                    } else {
                        text += input.charAt(i)
                    }
                }
                this.innerHTML = text;
            } else if(settings.type == "flexible") {
                var s = [];
                var s_html = [];
                var input = this.innerHTML;
                var text = "";
                if(settings.collapsible) {
                    var collapser = "<span class='ellipsis'></span><div class='collapser'></div><ul class='array collapsible'>";
                } else {
                    var collapser = "<div></div><ul class='array'>";
                }
                if(settings.hoverable) {
                    var hoverabler = "<div class='hoverable'>";
                } else {
                    var hoverabler = "<div>"
                }
                text += "<div id='json'>";
                s_html.push("</div>");
                function peek(stack) {
                    var val = stack.pop();
                    stack.push(val);
                    return val;
                }
                for(i = 0; i < input.length; i++) {
                    if(input.charAt(i) == '{') {
                        s.push(input.charAt(i));
                        text += input.charAt(i);
                        text += collapser;
                        s_html.push("</ul>");
                        text += "<li>"+hoverabler;
                        s_html.push("</div></li>");
                    } else if(input.charAt(i) == '\"' && peek(s) != '\"') {
                        text += input.charAt(i);
                        s.push(input.charAt(i));
                    } else if(input.charAt(i) == '[' && input.charAt(i+1) == ']') {
                        s.push(input.charAt(i));
                        text += input.charAt(i);
                        text += collapser;
                        s_html.push("</ul>");
                        text += "<li>"+hoverabler;
                        s_html.push("</div></li>");
                    } else if(input.charAt(i) == '[') {
                        s.push(input.charAt(i));
                        text += input.charAt(i);
                        text += collapser;
                        s_html.push("</ul>");
                        text += "<li>"+hoverabler;
                        s_html.push("</div></li>");
                    } else if(input.charAt(i) == ']') {
                        text += s_html.pop()+s_html.pop();
                        text += input.charAt(i);
                        // text += s_html.pop();
                        s.pop();
                    } else if(input.charAt(i) == '}') {
                        text += s_html.pop()+s_html.pop();
                        text += input.charAt(i);
                        s.pop();
                        if(s.length != 0)
                            if(peek(s) != '[' && peek(s) != '{') {
                                text += s_html.pop();
                            }
                    } else if(input.charAt(i) == '\"' && peek(s) == '\"') {
                        text += input.charAt(i)
                        s.pop();
                    } else if(input.charAt(i) == ',' && peek(s) != '\"') {
                        text += input.charAt(i);
                        text += s_html.pop();
                        text += "<li>"+hoverabler;
                        s_html.push("</div></li>");
                    } else if(input.charAt(i) == '\n') {
                    } else if(input.charAt(i) == ' ' && peek(s) != '\"') {
                    } else {
                        text += input.charAt(i)
                    }
                }
                this.innerHTML = text;
            } else {
                var text = "";
                var s_html = [];
                if(settings.collapsible) {
                    var collapser = "<span class='ellipsis'></span><div class='collapser'></div><ul class='array collapsible'>";
                    var collapser_obj = "<span class='ellipsis'></span><div class='collapser'></div><ul class='obj collapsible'>";
                } else {
                    var collapser = "<div></div><ul class='array'>";
                    var collapser_obj = "<div></div><ul class='obj'>";
                }
                if(settings.hoverable) {
                    var hoverabler = "<div class='hoverable'>";
                } else {
                    var hoverabler = "<div>"
                }
                function peek(stack) {
                    var val = stack.pop();
                    stack.push(val);
                    return val;
                }
                function iterateObject(object) {
                    $.each(object, function(index, element) {
                        if(element == null) {
                            text += "<li>"+hoverabler+"<span class='property'>"+index+"</span>: <span class='type-null'>"+element+"</span></div></li>";
                        } else if(element instanceof Array) {
                            text += "<li>"+hoverabler+"<span class='property'>"+index+"</span>: "+"["+collapser;
                            s_html.push("</li>");
                            s_html.push("</div>");
                            s_html.push("</ul>");
                            iterateArray(element);
                        } else if(typeof element == 'object') {
                            text += "<li>"+hoverabler+"<span class='property'>"+index+"</span>: "+"{"+collapser_obj;
                            s_html.push("</li>");
                            s_html.push("</div>");
                            s_html.push("</ul>");
                            iterateObject(element);
                        } else {
                            if(typeof element == "number") {
                                text += "<li>"+hoverabler+"<span class='property'>"+index+"</span>: <span class='type-number'>"+element+"</span></div></li>";
                            } else if(typeof element == "string") {
                                text += "<li>"+hoverabler+"<span class='property'>"+index+"</span>: <span class='type-string'>\""+element+"\"</span></div></li>";
                            } else if(typeof element == "boolean") {
                                text += "<li>"+hoverabler+"<span class='property'>"+index+"</span>: <span class='type-boolean'>"+element+"</span></div></li>";
                            } else {
                                text += "<li>"+hoverabler+"<span class='property'>"+index+"</span>: "+element+"</div></li>";
                            }
                        }
                    });
                    text += s_html.pop()+"}"+s_html.pop()+s_html.pop();
                }
                function iterateArray(array) {
                    $.each(array, function(index, element) {
                        if(element == null) {
                            text += "<li>"+hoverabler+"<span class='property'>"+index+"</span>: <span class='type-null'>"+element+"</span></div></li>";
                        } else if(element instanceof Array) {
                            text += "<li>"+hoverabler+"["+collapser;
                            s_html.push("</li>");
                            s_html.push("</div>");
                            s_html.push("</ul>");
                            iterateArray(element);
                        } else if(typeof element == 'object') {
                            text += "<li>"+hoverabler+"{"+collapser_obj;
                            s_html.push("</li>");
                            s_html.push("</div>");
                            s_html.push("</ul>");
                            iterateObject(element);
                        } else {
                            if(typeof element == "number") {
                                text += "<li>"+hoverabler+"<span class='property'>"+index+"</span>: <span class='type-number'>"+element+"</span></div></li>";
                            } else if(typeof element == "string") {
                                text += "<li>"+hoverabler+"<span class='property'>"+index+"</span>: <span class='type-string'>\""+element+"\"</span></div></li>";
                            } else if(typeof element == "boolean") {
                                text += "<li>"+hoverabler+"<span class='property'>"+index+"</span>: <span class='type-boolean'>"+element+"</span></div></li>";
                            } else {
                                text += "<li>"+hoverabler+"<span class='property'>"+index+"</span>: "+element+"</div></li>";
                            }
                        }
                    });
                    text += s_html.pop()+"]"+s_html.pop()+s_html.pop();
                }
                var input = this.innerHTML;
                var json = jQuery.parseJSON(input);
                text = "";
                text += "<div id='json'>";
                text += hoverabler+"{"+collapser_obj;
                s_html.push("");
                s_html.push("</div>");
                s_html.push("</ul>")
                iterateObject(json);
                text += "</ul></div></div>";
                this.innerHTML = text;
            }
            $('.hoverable').hover(function(event) {
                event.stopPropagation();
                $('.hoverable').removeClass('hovered');
                $(this).addClass('hovered');
            }, function(event) {
                event.stopPropagation();
                // $('.hoverable').removeClass('hovered');
                $(this).addClass('hovered');
            });
            $('.collapser').off().click(function(event) {
                $(this).parent('.hoverable').toggleClass('collapsed');
            });
        });
    }
    $.bindDefaultMessages();
    $.bindPanelToggleEvent();
    $.bindAddRunToPrintOptionEvent();
    $.bindRemoveRunFromPrintOptionEvent();
    $.bindAddImageToRunEvent();
    $.bindRemoveImageFromRunEvent();
    $.stateChangeEvent();

})(jQuery);

$(function () {
    $.bindColorDropdownOnChangeEvent();
    $.bindVendorDropdownOnChangeEvent();
});