var runHTML = '<blockquote class="jqs-run blockquote custom-blockquote blockquote-success"><div class="run-label">RUN1</div><div class="jqs-remove-addl-run text-xs-right"><i class="icon wb-close pointer jqs-remove-addl-run" style="display: none;" aria-hidden="true"></i></div><div class="row jqs-images"><div class="jqs-image"><div class="img-label input-group input-group-file"><span class="input-group-btn"><span class="btn btn-primary">IMAGE #1</span></span><select name="IMAGES" class="form-control"><option value="15">Up to 15 Sq. In.</option><option value="36">Up to 36 Sq. In.</option><option value="40">Up to 40 Sq. In.</option><option value="40+">Over 40 Sq. In.</option></select></div></div></div><div style="height:30px"><div class="form-group pull-right clearfix margin-right-15"><button type="button" style="position: relative; top:-2px" class="jqs-add-image btn btn-primary addaction">Add Image</button></div></div></blockquote>';

var imageHTML = '<div class="jqs-image"><div class="img-label input-group input-group-file"><span class="input-group-btn"><span class="jqs-btn-label btn btn-primary">IMAGE #1</span></span><select name="IMAGES" class="form-control"><option value="15">Up to 15 Sq. In.</option><option value="36">Up to 36 Sq. In.</option><option value="40">Up to 40 Sq. In.</option><option value="40+">Over 40 Sq. In.</option><option value="remove">Remove this Image</option></select></div></div>';
(function($){
    $.extend({

        bindDefaultMessages: function() {
            $('.jqs-show-quote').off('click.show-quote-msg').on('click.show-quote-msg', function() {
                $.showMessage("Please select at least one printing option to view the Quote", true);
            });
        },

        hasCustomStyle: function() {
            return $('select[name="STYLE"]').hasClass('jqs-hidden');
        },

        hasCustomStyleGroup: function() {
            return $('select[name="STYLE_GROUP"]').hasClass('jqs-hidden');
        },

        hasCustomVendor: function() {
            return $('select[name="VENDOR"]').hasClass('jqs-hidden');
        },

        hasCustomStock: function() {
            return $('select[name="STOCK"]').hasClass('jqs-hidden');
        },

        hasCustomStockType: function() {
            return $('select[name="STOCK_TYPE"]').hasClass('jqs-hidden');
        },

        isStyleSelected: function() {
            if($.hasCustomStyle()) {
                return $('input[name="CUSTOM_STYLE"]').val() !== '';
            } else {
                return $('select[name="STYLE"] option:selected').val() !== '';
            }
        },

        isStyleGroupSelected: function() {

            if($.hasCustomStyleGroup()) {
                return $('input[name="CUSTOM_STYLE_GROUP"]').val() !== '';
            } else {
                return $('select[name="STYLE_GROUP"] option:selected').val() !== '';
            }
        },

        isVendorSelected: function() {
            if($.hasCustomVendor()) {
                return $('input[name="CUSTOM_VENDOR"]').val() !== '';
            } else {
                return $('select[name="VENDOR"] option:selected').val() !== '';
            }
        },

        isStockSelected: function() {
            if($.hasCustomStock()) {
                return $('input[name="CUSTOM_STOCK"]').val() !== '';
            } else {
                return $('select[name="STOCK"] option:selected').val() !== '';
            }
        },

        isStockTypeSelected: function() {
            if($.hasCustomStockType()) {
                return $('input[name="CUSTOM_STOCK_TYPE"]').val() !== '';
            } else {
                return $('select[name="STOCK_TYPE"] option:selected').val() !== '';
            }
        },
        isPrintOptionSelected: function() {
            return $('.jqs-OFFSET').find('.jqs-offset.is-collapse').length == 0 || $('.jqs-FOIL-STAMP').find('.jqs-foil-stamp.is-collapse').length == 0 || $('.jqs-EMBOSS').find('.jqs-emboss.is-collapse').length == 0;
        },

        isAddressSelected: function() {
            return $('.jqs-CUSTOMER-ADDRESS').find('.jqs-customer-address.is-collapse').length == 0;
        },

        isAddressValid: function(validateOnly) {

            /*if($('input[name="firstName"]', '.jqs-CUSTOMER-ADDRESS').val() == '' && $('input[name="companyName"]', '.jqs-CUSTOMER-ADDRESS').val()) {
                if(!validateOnly) {
                    $.showMessage('Please enter Customer name or Company Name for Customer Address', true);
                }
                return false;
            }

            if($('input[name="address1"]', '.jqs-CUSTOMER-ADDRESS').val() == '') {
                if(!validateOnly) {
                    $.showMessage('Please enter Address Line 1 for Customer Address', true);
                }
                return false;
            }

            if($('input[name="city"]', '.jqs-CUSTOMER-ADDRESS').val() == '') {
                if(!validateOnly) {
                    $.showMessage('Please enter City for Customer Address', true);
                }
                return false;
            }

            if($('select[name="stateProvinceGeoId"]', '.jqs-CUSTOMER-ADDRESS').val() == '') {
                if(!validateOnly) {
                    $.showMessage('Please select State for Customer Address', true);
                }
                return false;
            }*/

            if($('input[name="postalCode"]', '.jqs-CUSTOMER-ADDRESS').val() == '') {
                if(!validateOnly) {
                    $.showMessage('Please enter Zip Code for Customer Address', true);
                }
                return false;
            }

            if($('input[name="userEmail"]', '.jqs-CUSTOMER-ADDRESS').val() == '') {
                if(!validateOnly) {
                    $.showMessage('Please enter E-mail Address for Customer Address', true);
                }
                return false;
            }

            if($('input[name="phone"]', '.jqs-CUSTOMER-ADDRESS').val() == '') {
                if(!validateOnly) {
                    $.showMessage('Please enter Phone Number for Customer Address', true);
                }
                return false;
            }

            return true;
        },
        isVendorCostValid: function () {
            var valid = true;
            if($.isCustomQuote() === true) {
                var quantities = [];
                $('input[name="QUANTITIES"]').each(function () {
                    quantities.push(parseInt($(this).val()));
                });

                var customCost = $.getVendorCost();

                for(var i = 0; i < quantities.length; i ++) {
                    if(quantities[i] > 0 && (customCost.length < (i + 1) || customCost[i] <= 0)) {
                        valid = false;
                        return false;
                    }
                }
            }
            return valid;
        },

        isPrintingOptionSelectable: function(printingOption) {
            if(!$.isStyleSelected()) {
                $.showMessage("Please " + ($.hasCustomStyle() ? "enter" : "select") + " a Folder Style before adding " + printingOption, true);
                return false;
            } else if(!$.isStyleGroupSelected()) {
                $.showMessage("Please " + ($.hasCustomStyleGroup() ? "enter" : "select") + " a Style Category before adding " + printingOption, true);
                return false;
            } else if(!$.isVendorSelected()) {
                $.showMessage("Please " + ($.hasCustomVendor() ? "enter" : "select") + " a Vendor before adding " + printingOption, true);
                return false;
            } else if(!$.isStockSelected()) {
                $.showMessage("Please " + ($.hasCustomStock() ? "enter" : "select") + " a Stock before adding " + printingOption, true);
                return false;
            } else if(!$.isStockTypeSelected()) {
                $.showMessage("Please " + ($.hasCustomStockType() ? "enter" : "select") + " a Stock Type before adding " + printingOption, true);
                return false;
            } else {
                return true;
            }
        },

        isPanelSelectable: function(panelName) {
            if($.validateQuantities() === false) {
                return false;
            } else {
                if(panelName === 'offset') {
                    return $.isPrintingOptionSelectable('Offset Printing');
                } else if(panelName === 'foilstamp') {
                    return $.isPrintingOptionSelectable('Foil Stamping');
                } else if(panelName === 'emboss') {
                    return $.isPrintingOptionSelectable('Embossing');
                } else if(panelName === 'addon' && !$.isPrintOptionSelected()) {
                    if($.isCustomQuote() === false) {
                        $.showMessage("Please select a Printing Option before adding an Add-on", true);
                    } else {
                        return true;
                    }
                    return false;
                } else if(panelName === 'markups' && !$.isPrintOptionSelected()) {
                    if($.isCustomQuote() === false) {
                        $.showMessage("Please select a Printing Option before applying Markups or Discounts", true);
                    } else {
                        return true;
                    }
                    return false;
                } else {
                    return true;
                }
            }
        },

        panelUnselectEvent: function(panelName) {
            if(panelName === 'offset') {
                $.showMessage("Offset Printing removed", false);
            } else if(panelName === 'offsetside2') {
                $.showMessage("Offset Printing  2nd side removed", false);
            } else if(panelName === 'foilstamp') {
                $.showMessage("Foil Stamping removed", false);
            } else if(panelName === 'emboss') {
                $.showMessage("Embossing removed", false);
            } else if(panelName === 'embossside2') {
                $.showMessage("Embossing 2nd side removed", false);
            } else if(panelName === 'addon') {
                $.showMessage("Add-ons removed", false);
            } else if(panelName === 'addoncoatingside1') {
                $.showMessage("1st side Coating removed", false);
            } else if(panelName === 'addoncoatingside2') {
                $.showMessage("2nd side Coating removed", false);
            } else if(panelName === 'markups') {
                $.showMessage("Markups / Discounts removed", false);
            }
        },

        formatNumber: function (x) {
            return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        },



        /*bindPanelToggleEvent: function() {
         $('.jqs-panel-toggle').on('click', function(){
         var el = $(this).closest('.panel');
         var panelName = $(el).find('.panel-title').data('panelname');
         if($.isPanelSelectable(panelName)) {
         var isCollapsed = el.hasClass('is-collapse');
         if(isCollapsed) {
         el.removeClass('is-collapse');
         $(this).removeClass('wb-plus').addClass('wb-close');
         } else {
         el.addClass('is-collapse');
         $(this).removeClass('wb-minus').addClass('wb-plus');
         $.panelUnselectEvent(panelName);
         }
         $.buildState();
         }
         });
         },*/

        bindPanelToggleEvent: function(bindingEls) {
            if(typeof bindingEls === 'undefined') {
                bindingEls = $('.jqs-panel-toggle');
            }

            $(bindingEls).each(function(i, bindingEl) {
                if($(bindingEl).hasClass('wb-plus')) {
                    $(bindingEl).closest('.panel-heading').addClass('pointer').on('click.panel.expand', function() {
                        var el = $(this).closest('.panel');
                        var panelName = $(el).find('.panel-title').data('panelname');
                        if($.isPanelSelectable(panelName)) {
                            var isCollapsed = el.hasClass('is-collapse');
                            if(isCollapsed) {
                                el.removeClass('is-collapse');
                                $(this).off('click.panel.expand').removeClass('pointer').find('.jqs-panel-toggle').removeClass('wb-plus').addClass('wb-close');
                                $.bindPanelToggleEvent(bindingEl);
                                $.buildState();
                            }
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
                            $.panelUnselectEvent(panelName);
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

        bindAddAddlMarkupDiscount: function() {
            $('.jqs-add-addl-markup-discount').on('click', function() {
                var markupDiscounts = $('.jqs-MARKUP').find('.jqs-markup-discount');
                var numOfMarkupDiscounts = markupDiscounts.length;
                if(numOfMarkupDiscounts < 5) {
                    var addlMarkupDiscount = $(markupDiscounts[0]).clone();
                    $(addlMarkupDiscount).find('select[name="MARKUP_DISCOUNT_TYPE"]').val('');
                    $(addlMarkupDiscount).find('[name^="qtyBreak"]').val('0.00');
                    $(addlMarkupDiscount).find('input,select').off().on('change', function() {
                        $.buildState($(this));
                    });
                    $('.jqs-MARKUP .jqs-markup-discounts').append(addlMarkupDiscount);
                    numOfMarkupDiscounts ++;
                    if(numOfMarkupDiscounts >= 5) {
                        $('.jqs-add-addl-markup-discount').hide();
                    }
                    $('.jqs-remove-addl-markup-discount').show();
                }
                $.bindRemoveAddlMarkupDiscount();
            });
        },

        bindRemoveAddlMarkupDiscount: function(el) {
            $('.jqs-remove-addl-markup-discount').off().on('click', function() {
                var markupDiscounts = $('.jqs-MARKUP').find('.jqs-markup-discount');
                var numOfMarkupDiscounts = markupDiscounts.length;
                if(numOfMarkupDiscounts > 1) {
                    $(this).closest('.jqs-markup-discount').remove();
                    $.showMessage("Markup / Discount removed", false);
                    numOfMarkupDiscounts --;
                    $('.jqs-add-addl-markup-discount').show();
                    if(numOfMarkupDiscounts == 1) {
                        $('.jqs-remove-addl-markup-discount:eq(0)').hide();
                    }
                    $.buildState();
                }
            });
        },

        bindAddAddlCustomAddon: function() {
            $('.jqs-add-addl-custom-addon').on('click', function() {
                var customAddons = $('.jqs-ADDON div.jqs-custom').find('.jqs-addon');

                var addlCustomAddon = $(customAddons[0]).clone();
                $(addlCustomAddon).find('select[name="ADDON"]').val('');
                $.toggleCustomOption($(addlCustomAddon).find('select[name="ADDON"]'), true);
                $(addlCustomAddon).find('input[name="CUSTOM_ADDON"]').val('');

                /*$(addlCustomAddon).find('input,select').off().on('change', function() {
                    $.buildState($(this));
                });*/
                $('.jqs-ADDON div.jqs-custom .jqs-addons').append(addlCustomAddon);
                $('.jqs-remove-addl-custom-addon').show();
                $.stateChangeEvent();
                $.bindRegularOptionsLink();
                $.bindRemoveAddlCustomAddon();
            });
        },

        bindRemoveAddlCustomAddon: function() {
            $('.jqs-remove-addl-custom-addon').off().on('click', function() {
                var customAddons = $('.jqs-ADDON div.jqs-custom').find('.jqs-addon');
                var numOfCustomAddons = customAddons.length;
                if(numOfCustomAddons > 1) {
                    $(this).closest('.jqs-addon').remove();
                    $.showMessage("Addon removed", false);
                    numOfCustomAddons --;
                    if(numOfCustomAddons == 1) {
                        $('.jqs-remove-addl-custom-addon:eq(0)').hide();
                    }
                    $.buildState();
                }
            });
        },

        bindGenerateQuote: function() {
            $('.jqs-generate-quote').off().on('click', function() {
                if($.isQuoteLocked()) {
                    $.showMessage('The Quote has already been generated and is in locked status. If you want to create another Quote, please click \'Generate Similar\'', true);
                    $('.jqs-quote .jqs-generate-similar').removeClass('hidden');
                } else {
                    $.buildState($(this), true);
                }
            });
            $.bindGenerateSimilarQuote();
        },

        bindGenerateSimilarQuote: function() {
            $('.jqs-generate-similar').off().on('click', function() {
                $('.jqs-quote').removeClass('jqs-locked');
                $.bindGenerateQuote();
                $(this).addClass('hidden');
            });

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

        bindRegularOptionsLink: function() {
            $('a.jqs-regular').off().on('click', function() {
                var el = $(this).siblings('select')[0];
                $.toggleCustomOption(el, true);
                if(($(el).attr('name') === 'STYLE' || $(el).attr('name') === 'VENDOR') && $.hasCustomStyleGroup() === false) {
                    $.buildState($('select[name="STYLE_GROUP"]'));
                } else if($(el).attr('name') === 'STOCK' && $.hasCustomStockType() === false) {
                    $.buildState($('select[name="STOCK_TYPE"]'));
                } else {
                    $.buildState(el);
                }

            });
        },

        isCustomQuote: function() {
            return $('input.jqs-hidden.jqs-custom').length - $('.jqs-custom-addons input.jqs-hidden.jqs-custom').length !== 5;
        },

        toggleCustomOption: function(el, defaultFlag) {
            if(defaultFlag === true) {
                $(el).removeClass('jqs-hidden').show().siblings('.jqs-custom,.jqs-regular').hide().addClass('jqs-hidden');
                $(el).val("");
            } else {
                $(el).addClass('jqs-hidden').hide().siblings('.jqs-custom,.jqs-regular').show().removeClass('jqs-hidden');
            }
            if($.isCustomQuote() === true) {
                $('.jqs-VENDOR-COST').show().removeClass('jqs-hidden');
                $('.jqs-ADDON div.jqs-regular').hide().addClass('jqs-hidden');
                $('.jqs-ADDON div.jqs-custom').show().removeClass('jqs-hidden');

            } else {
                $('.jqs-VENDOR-COST').hide().addClass('jqs-hidden');
                $('.jqs-ADDON div.jqs-regular').show().removeClass('jqs-hidden');
                $('.jqs-ADDON div.jqs-custom').hide().addClass('jqs-hidden');
            }

            if(defaultFlag === true) {
                return el;
            } else {
                return $(el).siblings('input')[0];
            }
        },

        buildState: function(el, generate, pricingResponse) {
            if(typeof generate === 'undefined') {
                generate = false;
            }
            if(typeof(pricingResponse) !== 'undefined') {
                $.updateView(pricingResponse, false, true);
            } else {
                if (typeof el !== 'undefined' && ($(el).attr('name') === 'QUANTITIES')) {
                    if($.validateQuantities() === false) {
                        return false;
                    }
                }
                if($.isCustomQuote() === true) {
                    if(typeof el !== 'undefined') {
                        var elName = $(el).attr('name');
                        if(elName === 'STOCK' || elName === 'CUSTOM_STOCK' || elName === 'STOCK_TYPE' || elName === 'CUSTOM_STOCK_TYPE') {
                            var curEl = elName === 'STOCK' || 'CUSTOM_STOCK' ? "Stock" : "Stock Type";
                            var isCustom = $(el).is('input');
                            var returnVal = true;
                            if (!$.isStyleSelected()) {
                                $.showMessage("Please " + ($.hasCustomStyle() ? "enter" : "select") + " a Folder Style before " + (isCustom ? "entering" : "selecting") + " " + curEl, true);
                                returnVal = false;
                            } else if (!$.isStyleGroupSelected()) {
                                $.showMessage("Please " + ($.hasCustomStyleGroup() ? "enter" : "select") + " a Style Category before " + (isCustom ? "entering" : "selecting") + " " + curEl, true);
                                returnVal = false;
                            } else if (!$.isVendorSelected()) {
                                $.showMessage("Please " + ($.hasCustomVendor() ? "enter" : "select") + " a Vendor before " + (isCustom ? "entering" : "selecting") + " " + curEl, true);
                                returnVal = false;
                            }
                            if(returnVal === false) {
                                $(el).val('');
                                return false;
                            }
                        }
                    }
                } else {
                    if (typeof el !== 'undefined' && ($(el).attr('name') === 'STOCK' || $(el).attr('name') === 'STOCK_TYPE')) {
                        var curEl = $(el).attr('name') === 'STOCK' ? "Stock" : "Stock Type";
                        var returnVal = true;
                        if (!$.isStyleSelected()) {
                            $.showMessage("Please select a Folder Style before selecting " + curEl, true);
                            returnVal = false;
                        } else if (!$.isStyleGroupSelected()) {
                            $.showMessage("Please select a Style Category before selecting " + curEl, true);
                            returnVal = false;
                        } else if (!$.isVendorSelected()) {
                            $.showMessage("Please select a Vendor before selecting " + curEl, true);
                            returnVal = false;
                        }

                        if (returnVal == false) {
                            if (curEl === 'Stock') {
                                $('select[name="STOCK"]').val('');
                            } else if (curEl === 'Stock Type') {
                                $('select[name="STOCK_TYPE"]').val('');
                            }
                            return false;
                        }
                    }
                }


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
                state['CHANGED_PARAM'] = $(el).attr('name');
                if($.hasCustomStyle()) {
                    state['CUSTOM_STYLE'] = $('input[name="CUSTOM_STYLE"]').val();
                } else {
                    state['STYLE'] = $('select[name="STYLE"] option:selected').val();
                }

                if($.hasCustomStyleGroup()) {
                    state['CUSTOM_STYLE_GROUP'] = $('input[name="CUSTOM_STYLE_GROUP"]').val();
                } else {
                    state['STYLE_GROUP'] = $('select[name="STYLE_GROUP"] option:selected').val();
                }

                if($.hasCustomVendor()) {
                    state['CUSTOM_VENDOR'] = $('input[name="CUSTOM_VENDOR"]').val();
                } else {
                    state['VENDOR'] = $('select[name="VENDOR"] option:selected').val();
                }

                if($.hasCustomStock()) {
                    state['CUSTOM_STOCK'] = $('input[name="CUSTOM_STOCK"]').val();
                } else {
                    state['STOCK'] = $('select[name="STOCK"] option:selected').val();
                }

                if($.hasCustomStockType()) {
                    state['CUSTOM_STOCK_TYPE'] = $('input[name="CUSTOM_STOCK_TYPE"]').val();
                } else {
                    state['STOCK_TYPE'] = $('select[name="STOCK_TYPE"] option:selected').val();
                }

                state['MATERIAL_TYPE'] = $('select[name="MATERIAL_TYPE"] option:selected').val();

                var quantities = [];
                $('input[name="QUANTITIES"]').each(function () {
                    quantities.push(parseInt($(this).val()));
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

                state['PRINT_OPTIONS'] = printOptions;
                if($.isCustomQuote() === true) {
                    state['CUSTOM_ADDONS'] = $.getAddons();
                    state['CUSTOM_VENDOR_COST'] = $.getVendorCost();
                } else {
                    state['ADDONS'] = $.getAddons();
                }

                state['MARKUPS_AND_DISCOUNTS'] = $.getMarkupsAndDiscounts();
                state['CUSTOMER_ADDRESS'] = $.getCustomerAddress();

                //create or update quote request first
                var quoteRequestId = $('input[name="quoteRequestId"]').val();
                if (generate) {
                    var customerData = $.getCustomerAddress();
                    customerData['quoteRequestId'] = quoteRequestId;
                    customerData['pricingRequest'] = JSON.stringify(state);
                    $.ajax({
                        type: 'POST',
                        data: customerData,
                        async: false,
                        dataType: 'json',
                        url: '/admin/control/createOrUpdateQuoteCustomer'
                    }).done(function (data) {
                        $('input[name="quoteRequestId"]').val(data.quoteId);
                        quoteRequestId = data.quoteId;
                    });
                }

                if(generate && quoteRequestId === '') {
                    generate = false;
                    $.showMessage('There was an error trying to create new customer for this quote.', true);
                }
                $.ajax({
                    type: 'POST',
                    data: {
                        'generate': (generate ? 'true' : 'false'),
                        'state': JSON.stringify(state),
                        'quoteRequestId': quoteRequestId,
                        'comment' : $.getComments(),
                        'internalComment' : $.getInternalComments(),
                        'production' : $.getProduction()
                    },
                    async: false,
                    dataType: 'json',
                    url: '/admin/control/getDetailedPrice'
                }).done(function (data) {
                    $.updateView(data.response, generate);
                    if(generate) {
                        window.setTimeout(function(){
                            window.location.href = '/admin/control/foldersQuoteView?quoteId=' + quoteRequestId;
                        }, 2000);
                    }
                });

            }
        },
        validateQuantities: function() {
            var valid = false;
            var styleGroupId = $('select[name="STYLE_GROUP"] option:selected').val();
            if(styleGroupId === '') {
                return true;
            }

            $.ajax({
                type: 'GET',
                data: {
                    'styleGroupId' : styleGroupId
                },
                url: '/admin/control/getQuoteMinMaxQuantities',
                async: false,
                dataType: 'json'
            }).done(function(data) {

                $('input[name="QUANTITIES"]').each(function () {
                    if (parseInt($(this).val()) < parseInt(data.response.MIN_QTY)) {
                        $.showMessage('The minimum quantity required to generate a quote for the selected Style is ' + data.response.MIN_QTY + '. One or more of the requested quantities is lower than the minimum quantity requirement for the selected Style.', true);
                        return false;
                    } else if (parseInt($(this).val()) > parseInt(data.response.MAX_QTY)) {
                        $.showMessage('The maximum possible quantity to generate a quote for the selected Style is ' + data.response.MAX_QTY + '. One or more of the requested quantities is greater than the maximum quantity requirement for the selected Style.', true);
                        return false;
                    }
                    valid = true;
                });
            });
            return valid;
        },
        updateView: function(response, generate, loadedFlag) {
            // var response = data.response;
            var vendors = response.VENDORS;
            var styleGroups = response.STYLE_GROUPS;
            var styles = response.STYLES;
            var stockTypes = response.STOCK_TYPES;
            var stocks = response.STOCKS;
            var quoteId = response.quoteId;

            $.updateDropDown('STYLE', styles);
            $.updateDropDown('STYLE_GROUP', styleGroups, response.STYLE_GROUPS_VALUE);
            $.updateDropDown('VENDOR', vendors, response.VENDORS_VALUE);
            $.updateDropDown('STOCK', stocks);
            $.updateDropDown('STOCK_TYPE', stockTypes, response.STOCK_TYPES_VALUE);

            var pricingDetails = response.pricingDetails;
            $.updateSummaryTile(pricingDetails);
            $.updateQuantities(pricingDetails);
            $.updateQuote(response);
            if(typeof loadedFlag === 'undefined') {
                if(typeof quoteId !== 'undefined') {
                    $.closeQuoteDialogs();
                    $.showMessage('Quote with id \'' + quoteId + '\' created successfully', false);
                } else if(generate === true) {
                    $.showMessage('An error occurred while generating the Quote', true);
                }
            }
        },
        updateDropDown: function(name, options, value) {
            if(typeof options !== 'undefined') {
                var val = $('select[name="' + name + '"] option:selected').val();
                $('select[name="' + name + '"] option:gt(1)').remove();
                for (var prop in options) {
                    var selected = prop === val ? ' selected' : '';
                    if (!options.hasOwnProperty(prop)) continue;
                    $('select[name="' + name + '"]').append($('<option value="' + prop + '"' + selected + '>' + options[prop] + '</option>'));
                }
                if(val === '' && typeof value !== 'undefined' && value !== '') {
                    $('select[name="' + name + '"]').val(value);
                }
            }

        },

        updateSummaryTile: function(pricingDetails) {
            var qtyBreaks = pricingDetails[0];
            var totals = pricingDetails[pricingDetails.length - 2];
            var each = pricingDetails[pricingDetails.length - 1];
            var numOfQtyBreaks = pricingDetails[0].length - 1;
            var hiddenTiles = 4 - numOfQtyBreaks;

            for(var i = 0; i < hiddenTiles; i ++) {
                $('.jqs-summary-' + (i + 1)).css('visibility', 'hidden');
            }

            for(var j = hiddenTiles, k = 0; k < numOfQtyBreaks; j ++, k++) {
                var tileEl = $('.jqs-summary-' + (j + 1));
                $(tileEl).css('visibility', 'visible');
                $(tileEl).find('.jqs-qty-tile').html(qtyBreaks[k + 1]);
                $(tileEl).find('.jqs-total-tile').html('$ ' + $.formatNumber(totals[k + 1]));
                if(parseFloat(totals[k + 1]) > 0) {
                    $(tileEl).find('.jqs-each').html('$ ' + each[k + 1] + ' / ea');
                } else {
                    $(tileEl).find('.jqs-each').html('');
                }

            }
        },

        updateQuantities: function(pricingDetails) {
            var qtyBreaks = pricingDetails[0];
            var numOfQtyBreaks = pricingDetails[0].length - 1;



            $('.jqs-markup-discount,.jqs-vendor-cost').each(function(i1, el) {
                for(var i = numOfQtyBreaks; i < 4; i ++) {
                    $(el).find('.jqs-qty-break-' + (i + 1)).hide();
                }
                for(var j = 0; j < numOfQtyBreaks; j ++) {
                    $(el).find('.jqs-qty-break-' + (j + 1)).show();
                    $(el).find('.jqs-qty-break-' + (j + 1) + ' .btn').text($.formatNumber(qtyBreaks[j + 1]));
                }
            });
        },
        isQuoteLocked: function() {
            return $('.jqs-quote .jqs-locked').length > 0;
        },
        updateQuote: function(response) {
            var pricingDetails = response.pricingDetails;
            var quoteId = response.quoteId;
            var quoteCreatedDate = response.createdDate;
            var quoteCreatedBy = response.createdBy;
            var quoteDetails = response.details;

            $('.jqs-quote-created-date').text(quoteCreatedDate);
            $('.jqs-quote-created-by').text(quoteCreatedBy);
            $('.jqs-quote-summary').text(quoteDetails);
            if(typeof quoteId !== 'undefined') {
                $('.jqs-quote-num').text(quoteId);
                $('.jqs-quote').addClass('jqs-locked');
                $('.jqs-quote .jqs-generate-quote').removeClass('btn-primary').addClass('btn-default').off().on('click', function() {
                    $.showMessage('The Quote has already been generated and is in locked status. If you want to create another Quote, please click \'Generate Similar\'', true);
                });
                $('.jqs-quote .jqs-generate-similar').removeClass('hidden');

            }
            $('#jqs-quote .jqs-quote-details').html('');
            for(var i = 0; i < pricingDetails.length && pricingDetails.length > 2; i ++) {
                var pricingDetail = pricingDetails[i];
                var qtyBreaks = pricingDetails[i].length - 1;
                var colSpan = 4 - qtyBreaks;
                if(i == 0) {
                    var headerRow = '<tr><th class="text-xs-center"></th><th colspan="' + colSpan + '" class="text-xs-left">Quantity</th>';
                    for(var j = 1; j < pricingDetail.length; j ++) {
                        headerRow += '<th class="text-xs-center">' + pricingDetail[j] + '</th>';
                    }
                    headerRow += '</tr>';
                    $('#jqs-quote .jqs-qty-breaks').html('').append($(headerRow));
                } else {
                    if(i >= pricingDetails.length - 2) {
                        var detailRow = '<tr><td class="text-xs-center"></td><td colspan="' + colSpan + '" class="text-xs-left">' + pricingDetail[0] + '</td>';
                        for (var j = 1; j < pricingDetail.length; j++) {
                            detailRow += '<td class="text-xs-center">$' + pricingDetail[j] + '</td>';
                        }
                        detailRow += '</tr>';
                        $('#jqs-quote .jqs-quote-details').append($(detailRow));
                    }
                }
            }
            var customerAddress = $.getCustomerAddress();
            var addressHtml = '';
            if(customerAddress['firstName'] != '') {
                addressHtml += '<div>' + customerAddress['firstName'] + ' ' + customerAddress['lastName'] + '</div>';
            }
            if(customerAddress['companyName'] != '') {
                addressHtml += '<div>' + customerAddress['companyName'] + '</div>';
            }
            addressHtml += '<div>' + customerAddress['address1'] + '</div>';

            if(customerAddress['address2'] != '' && typeof customerAddress['address2'] != 'undefined') {
                addressHtml += '<div>' + customerAddress['address2'] + '</div>';
            }
            addressHtml += '<div>' + customerAddress['city'] + ', ' + customerAddress['stateProvinceGeoId'] + ' ' + customerAddress['postalCode'] + '</div>';
            addressHtml += '<div>Email: ' + customerAddress['userEmail'] + '</div>';
            addressHtml += '<div>Phone: ' + customerAddress['phone'] + '</div>';

            $('.jqs-prepared-for').html(addressHtml);

            $('#jqs-quote .jqs-quote-details').append($('<tr><td colspan="5"></td></tr>'));
            if(pricingDetails.length > 3) {
                if($.isAddressSelected() && $.isAddressValid(true) && $.isVendorCostValid()) {
                    $('.jqs-show-quote').attr("data-target", "#jqs-quote");
                    $('.jqs-show-quote').off('click.show-quote-msg');
                } else {
                    $('.jqs-show-quote').attr("data-target", "#");
                    $('.jqs-show-quote').off('click.show-quote-msg').on('click.show-quote-msg', function() {
                        if($.validateQuantities()) {
                            if($.isVendorCostValid() === false) {
                                $.showMessage("Please enter the Custom Vendor Cost to view the Quote", true);
                            } else if(!$.isAddressSelected()) {
                                $.showMessage("Please enter the Customer Address to view the Quote", true);
                            } else {
                                $.isAddressValid();
                            }
                        }
                    });
                }
            } else {
                $('.jqs-show-quote').attr("data-target", "#");
                $('.jqs-show-quote').off('click.show-quote-msg').on('click.show-quote-msg', function() {
                    $.showMessage("Please select at least one printing option to view the Quote", true);
                });
            }
            $.updateQuoteDetails(pricingDetails);
        },

        updateQuoteDetails: function(pricingDetails) {
            $('#jqs-price-breakdown .jqs-pricing-details').html('');
            for(var i = 0; i < pricingDetails.length; i ++) {
                var pricingDetail = pricingDetails[i];
                var qtyBreaks = pricingDetails[i].length - 1;
                var colSpan = 3 + 4 - qtyBreaks;
                if(i == 0) {
                    var headerRow = '<tr><th class="text-xs-center">#</th><th colspan="' + colSpan + '" class="text-xs-center">Description</th>';
                    for(var j = 1; j < pricingDetail.length; j ++) {
                        headerRow += '<th class="text-xs-right" style="width: 100px">' + pricingDetail[j] + '</th>';
                    }
                    headerRow += '</tr>';
                    $('#jqs-price-breakdown .jqs-qty-breaks').html('').append($(headerRow));
                } else {
                    var detailRow = '<tr><td class="text-xs-center">' + (i) + '</td><td colspan="' + colSpan + '" class="text-xs-left">' + pricingDetail[0] + '</td>';
                    for(var j = 1; j < pricingDetail.length; j ++) {
                        detailRow += '<td>$' + pricingDetail[j] + '</td>';
                    }
                    detailRow += '</tr>';
                    $('#jqs-price-breakdown .jqs-pricing-details').append($(detailRow));
                }

            }
            $('#jqs-price-breakdown  .jqs-pricing-details').append($('<tr><td colspan="8"></td></tr>'));
        },

        stateChangeEvent : function() {
//                $('select[name="VENDOR"],select[name="STYLE_GROUP"],select[name="STYLE"],select[name="MATERIAL_TYPE"],select[name="STOCK_TYPE"],select[name="STOCK"],select[name="IMAGES"]').off().on('change', function() {
            $('.jqs-CALCULATOR').find('input,select').off().on('change', function() {
                if($(this).find('option:selected').val() === 'custom') {
                    $.buildState($.toggleCustomOption($(this), false));
                } else {
                    $.buildState($(this));
                }


            });
        },
        getCustomerAddress : function() {
            var customerAddress = {};
            if($.isAddressSelected()) {
                customerAddress['firstName'] = $('input[name="firstName"]', '.jqs-CUSTOMER-ADDRESS').val();
                customerAddress['lastName'] = $('input[name="lastName"]', '.jqs-CUSTOMER-ADDRESS').val();
                customerAddress['companyName'] = $('input[name="companyName"]', '.jqs-CUSTOMER-ADDRESS').val();
                customerAddress['address1'] = $('input[name="address1"]', '.jqs-CUSTOMER-ADDRESS').val();
                customerAddress['address2'] = $('input[name="address2"]', '.jqs-CUSTOMER-ADDRESS').val();
                customerAddress['city'] = $('input[name="city"]', '.jqs-CUSTOMER-ADDRESS').val();
                customerAddress['stateProvinceGeoId'] = $('select[name="stateProvinceGeoId"]', '.jqs-CUSTOMER-ADDRESS').val();
                customerAddress['postalCode'] = $('input[name="postalCode"]', '.jqs-CUSTOMER-ADDRESS').val();
                customerAddress['countryGeoId'] = $('select[name="countryGeoId"]', '.jqs-CUSTOMER-ADDRESS').val();
                customerAddress['userEmail'] = $('input[name="userEmail"]', '.jqs-CUSTOMER-ADDRESS').val();
                customerAddress['phone'] = $('input[name="phone"]', '.jqs-CUSTOMER-ADDRESS').val();
                customerAddress['additionalPhone'] = $('input[name="additionalPhone"]', '.jqs-CUSTOMER-ADDRESS').val();
            }
            return customerAddress;
        },

        setCustomerAddress: function(customerAddress) {
                $('input[name="firstName"]', '.jqs-CUSTOMER-ADDRESS').val(customerAddress.firstName);
                $('input[name="lastName"]', '.jqs-CUSTOMER-ADDRESS').val(customerAddress.lastName);
                $('input[name="companyName"]', '.jqs-CUSTOMER-ADDRESS').val(customerAddress.companyName);
                $('input[name="address1"]', '.jqs-CUSTOMER-ADDRESS').val(customerAddress.address1);
                $('input[name="address2"]', '.jqs-CUSTOMER-ADDRESS').val(customerAddress.address2);
                $('input[name="city"]', '.jqs-CUSTOMER-ADDRESS').val(customerAddress.city);
                $('select[name="stateProvinceGeoId"]', '.jqs-CUSTOMER-ADDRESS').val(customerAddress.stateProvinceGeoId);
                $('input[name="postalCode"]', '.jqs-CUSTOMER-ADDRESS').val(customerAddress.postalCode);
                $('select[name="countryGeoId"]', '.jqs-CUSTOMER-ADDRESS').val(customerAddress.countryGeoId);
                $('input[name="userEmail"]', '.jqs-CUSTOMER-ADDRESS').val(customerAddress.userEmail);
                $('input[name="phone"]', '.jqs-CUSTOMER-ADDRESS').val(customerAddress.phone);
                $('input[name="additionalPhone"]', '.jqs-CUSTOMER-ADDRESS').val(customerAddress.additionalPhone);
                $('.jqs-CUSTOMER-ADDRESS .panel-heading').trigger('click');
        },

        setPrintOptions: function(printOptions) {
            for(var i = 0; i < printOptions.length; i ++) {
                if(printOptions[i].PRINT_OPTION_NAME === 'OFFSET') {
                    $.setOffsetPrintOption(printOptions[i]);
                } else if(printOptions[i].PRINT_OPTION_NAME === 'FOIL_STAMPING') {
                    $.setFoilStampingPrintOption(printOptions[i]);
                } else if(printOptions[i].PRINT_OPTION_NAME === 'EMBOSSING') {
                    $.setEmbossingPrintOption(printOptions[i]);
                }
            }
        },

        getOffsetPrintOption : function() {
            var offsetPrintOption;
            if($('.jqs-offset.is-collapse').length === 0) {
                offsetPrintOption = {'PRINT_OPTION_NAME' : 'OFFSET'};
                var sides = [];
                var side1 = {};
                side1['PRINT_METHOD'] = $('select[name="PRINT_METHOD"] option:selected', '.jqs-OFFSET .jqs-SIDE1').val();
                side1['NUMBER_OF_INKS'] = $('select[name="NUMBER_OF_INKS"] option:selected', '.jqs-OFFSET .jqs-SIDE1').val();
                side1['HEAVY_COVERAGE'] = $('select[name="HEAVY_COVERAGE"] option:selected', '.jqs-OFFSET .jqs-SIDE1').val();
                side1['COLOR_WASHES'] = $('select[name="COLOR_WASHES"] option:selected', '.jqs-OFFSET .jqs-SIDE1').val();
                side1['PLATE_CHANGES'] = $('select[name="PLATE_CHANGES"] option:selected', '.jqs-OFFSET .jqs-SIDE1').val();
                sides.push(side1);
                if($('.jqs-OFFSET .jqs-side2.is-collapse').length === 0) {
                    var side2 = {};
                    side2['PRINT_METHOD'] = $('select[name="PRINT_METHOD"] option:selected', '.jqs-OFFSET .jqs-SIDE2').val();
                    side2['NUMBER_OF_INKS'] = $('select[name="NUMBER_OF_INKS"] option:selected', '.jqs-OFFSET .jqs-SIDE2').val();
                    side2['HEAVY_COVERAGE'] = $('select[name="HEAVY_COVERAGE"] option:selected', '.jqs-OFFSET .jqs-SIDE2').val();
                    side2['COLOR_WASHES'] = $('select[name="COLOR_WASHES"] option:selected', '.jqs-OFFSET .jqs-SIDE2').val();
                    side2['PLATE_CHANGES'] = $('select[name="PLATE_CHANGES"] option:selected', '.jqs-OFFSET .jqs-SIDE2').val();
                    sides.push(side2);
                }
                offsetPrintOption['SIDES'] = sides;
            }

            return offsetPrintOption;
        },

        setOffsetPrintOption : function(offsetPrintOption) {
            var sides = offsetPrintOption.SIDES;
            $('select[name="PRINT_METHOD"]', '.jqs-OFFSET .jqs-SIDE1').val(sides[0].PRINT_METHOD);
            var el = $('select[name="PRINT_METHOD"]', '.jqs-OFFSET .jqs-SIDE1');
            if($(el).val() === 'PMS_OFFSET') {
                $(el).parent().parent().parent().find('select[name="NUMBER_OF_INKS"]').find('option[value="0"]').remove().val('1');
            } else {
                $(el).parent().parent().parent().find('select[name="NUMBER_OF_INKS"]').prepend('<option value="0">0</option>').val('0');
            }

            $('select[name="NUMBER_OF_INKS"]', '.jqs-OFFSET .jqs-SIDE1').val(sides[0].NUMBER_OF_INKS);
            $('select[name="HEAVY_COVERAGE"]', '.jqs-OFFSET .jqs-SIDE1').val(sides[0].HEAVY_COVERAGE);
            $('select[name="COLOR_WASHES"]', '.jqs-OFFSET .jqs-SIDE1').val(sides[0].COLOR_WASHES);
            $('select[name="PLATE_CHANGES"]', '.jqs-OFFSET .jqs-SIDE1').val(sides[0].PLATE_CHANGES);
            $('.jqs-OFFSET .jqs-offset > div.panel-heading').trigger('click');
            if(sides.length == 2) {
                $('select[name="PRINT_METHOD"]', '.jqs-OFFSET .jqs-SIDE2').val(sides[1].PRINT_METHOD);
                var el = $('select[name="PRINT_METHOD"]', '.jqs-OFFSET .jqs-SIDE2');
                if($(el).val() === 'PMS_OFFSET') {
                    $(el).parent().parent().parent().find('select[name="NUMBER_OF_INKS"]').find('option[value="0"]').remove().val('1');
                } else {
                    $(el).parent().parent().parent().find('select[name="NUMBER_OF_INKS"]').prepend('<option value="0">0</option>').val('0');
                }
                $('select[name="NUMBER_OF_INKS"]', '.jqs-OFFSET .jqs-SIDE2').val(sides[1].NUMBER_OF_INKS);
                $('select[name="HEAVY_COVERAGE"]', '.jqs-OFFSET .jqs-SIDE2').val(sides[1].HEAVY_COVERAGE);
                $('select[name="COLOR_WASHES"]', '.jqs-OFFSET .jqs-SIDE2').val(sides[1].COLOR_WASHES);
                $('select[name="PLATE_CHANGES"]', '.jqs-OFFSET .jqs-SIDE2').val(sides[1].PLATE_CHANGES);
                $('.jqs-OFFSET .jqs-SIDE2 .panel-heading').trigger('click');
            }
        },

        getFoilStampingPrintOption : function() {
            var foilStampingPrintOption;
            if($('.jqs-foil-stamp.is-collapse').length === 0) {
                foilStampingPrintOption = {'PRINT_OPTION_NAME': 'FOIL_STAMPING'};
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

        setFoilStampingPrintOption : function(foilStampingPrintOption) {
            var runs = foilStampingPrintOption.RUNS;
            for(var i = 0; i < runs.length; i ++) {
                if(i > 0) {
                    $('.jqs-add-run', '.jqs-FOIL-STAMP').trigger('click');
                }
                var images = runs[i].IMAGES;
                for(var j = 0; j < images.length; j ++) {
                    if(j > 0) {
                        $('.jqs-add-image:eq(' + i +')', '.jqs-FOIL-STAMP').trigger('click');
                    }
                    $('select[name="IMAGES"]:eq(' + j +')', '.jqs-FOIL-STAMP .jqs-run:eq(' + i + ')').val(images[j]);
                }
            }
            $('.jqs-FOIL-STAMP .panel-heading').trigger('click');
        },

        getEmbossingPrintOption : function() {
            var embossingPrintOption;
            if($('.jqs-emboss.is-collapse').length === 0) {
                embossingPrintOption = {'PRINT_OPTION_NAME': 'EMBOSSING'};
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

        setEmbossingPrintOption : function(embossingPrintOption) {
            var sides = embossingPrintOption.SIDES;
            for(var k = 0; k < sides.length; k ++) {
                var runs = sides[k].RUNS;
                for (var i = 0; i < runs.length; i++) {
                    if (i > 0) {
                        $('.jqs-add-run', '.jqs-EMBOSS .jqs-SIDE' + (k + 1)).trigger('click');
                    }
                    var images = runs[i].IMAGES;
                    for (var j = 0; j < images.length; j++) {
                        if (j > 0) {
                            $('.jqs-add-image:eq(' + i + ')', '.jqs-EMBOSS .jqs-SIDE' + (k + 1)).trigger('click');
                        }
                        $('select[name="IMAGES"]:eq(' + j + ')', '.jqs-EMBOSS .jqs-SIDE' + (k + 1) + ' .jqs-run:eq(' + i + ')').val(images[j]);
                    }
                }
                if(k == 1) {
                    $('.jqs-EMBOSS .jqs-SIDE2 .panel-heading').trigger('click');
                }
            }
            $('.jqs-EMBOSS .panel-heading:eq(0)').trigger('click');
        },

        getQuantityBreaks: function() {

        },

        getMarkupsAndDiscounts: function() {
            var markupsAndDiscounts = [];
            if($('.jqs-markups-and-discounts.is-collapse').length === 0) {
                var markupDiscounts = $('.jqs-MARKUP').find('.jqs-markup-discount');
                $(markupDiscounts).each(function(i, markupDiscount){
                    var markupDiscountType =  $(markupDiscount).find('select[name="MARKUP_DISCOUNT_TYPE"]').val();
                    if(markupDiscountType !== '') {
                        var vals = [];
                        var markupDiscountValues = $(markupDiscount).find('[name^="qtyBreak"]');
                        $(markupDiscountValues).each(function(i, markupDiscountValue) {
                            var val = $(markupDiscountValue).val();
                            if(!isNaN(val) && parseFloat(val) > 0) {
                                vals.push(parseFloat(val));
                            } else {
                                vals.push(0.0);
                            }
                        });
                        if(vals.length > 0) {
                            var markupOrDiscount = {};
                            markupOrDiscount['MARKUP_DISCOUNT_TYPE'] = markupDiscountType;
                            markupOrDiscount['MARKUP_DISCOUNT_VALUE'] = vals;
                            markupsAndDiscounts.push(markupOrDiscount);
                        }

                    }
                });
            }
            return markupsAndDiscounts;
        },

        setMarkupsAndDiscounts: function(markupsAndDiscounts) {
            for(var i = 0; i < markupsAndDiscounts.length; i ++) {
                if(i == 0) {
                    $('.jqs-MARKUP .panel-heading').trigger('click');
                }
                var markupOrDiscount = markupsAndDiscounts[i];
                if(i > 0) {
                    $('.jqs-add-addl-markup-discount').trigger('click');
                }
                $('select[name="MARKUP_DISCOUNT_TYPE"]', '.jqs-markup-discount:eq(' + i + ')').val(markupOrDiscount.MARKUP_DISCOUNT_TYPE);
                var markupOrDiscountValues = markupOrDiscount.MARKUP_DISCOUNT_VALUE;
                for(var j = 0; j < markupOrDiscountValues.length; j ++) {
                    $('input[name="qtyBreak' + (j + 1) +'"]', '.jqs-markup-discount:eq(' + i + ')').val(markupOrDiscountValues[j]);
                }
            }
        },

        getVendorCost: function() {
            var cost = [];
            var costValues = $('.jqs-VENDOR-COST .jqs-vendor-cost').find('[name^="qtyBreak"]');
            $(costValues).each(function(i, costValue) {
                var val = $(costValue).val();
                if(!isNaN(val) && parseFloat(val) > 0) {
                    cost.push(parseFloat(val));
                } else {
                    cost.push(0.0);
                }
            });
            return cost;
        },

        setVendorCost: function(cost) {
            if(typeof cost !== 'undefined') {
                $.each(cost, function(i, costVal) {
                    $('.jqs-VENDOR-COST .jqs-vendor-cost').find('[name="qtyBreak' + (i + 1) + '"]').val(costVal);
                });
            }
        },

        getAddons: function() {
            var addons = [];
            var coatings = [];
            if($('.jqs-ADDON div.jqs-custom').hasClass('jqs-hidden') === true) {
                if($('.jqs-ADDON div.jqs-regular .jqs-addon.is-collapse').length === 0) {
                    if($('.jqs-addon-coating-side1.is-collapse').length === 0) {
                        var coatingSide1 = $('.jqs-addon-coating-side1').find('input[name="COATING_SIDE_1"]:checked').val();
                        if(typeof coatingSide1 !== 'undefined' && coatingSide1 != '') {
                            coatings.push(coatingSide1);
                        }
                    }
                    if($('.jqs-addon-coating-side2.is-collapse').length === 0) {
                        var coatingSide2 = $('.jqs-addon-coating-side2').find('input[name="COATING_SIDE_2"]:checked').val();
                        if(typeof coatingSide2 !== 'undefined' && coatingSide2 != '') {
                            coatings.push(coatingSide2);
                        }
                    }
                    if(coatings.length > 0) {
                        var coating = {};
                        coating['ADDON_NAME'] = 'COATING';
                        coating['SIDES'] = coatings;
                        addons.push(coating);
                    }
                }
            } else {
                if($('.js-ADDON div.jqs-custom .jqs-custom-addon.is-collapse').length === 0) {
                    var addonEls =  $('.jqs-ADDON div.jqs-custom').find('.jqs-addon');
                    $(addonEls).each(function(i, addonEl) {
                        if($(addonEl).find('input[name="CUSTOM_ADDON"]').hasClass('jqs-hidden')) {
                            var addon = $(addonEl).find('select[name="ADDON"]').val();
                            if(addon !== '') {
                                addons.push(addon);
                            }
                        } else {
                            var addon = $(addonEl).find('input[name="CUSTOM_ADDON"]').val();
                            if(addon !== '') {
                                addons.push(addon);
                            }
                        }
                    });
                }
            }

            return addons;
        },

        setAddons: function(addons, customAddons) {
            if(typeof addons !== 'undefined') {
                for (var i = 0; i < addons.length; i++) {
                    if (i == 0) {
                        $('.jqs-ADDON .panel-heading').trigger('click');
                    }
                    var addon = addons[i];
                    if (addon.ADDON_NAME === 'COATING') {
                        var sides = addon.SIDES;
                        for (var j = 0; j < sides.length; j++) {
                            $('.jqs-ADDON .jqs-ADDON-COATING-SIDE' + (j + 1) + ' .panel-heading').trigger('click');
                            $('.jqs-ADDON .jqs-ADDON-COATING-SIDE' + (j + 1)).find('input[name="COATING_SIDE_' + (j + 1) + '"][value="' + sides[j] + '"]').prop('checked', true);

                        }
                    }
                }
            } else {
                var addonEl = $('.jqs-ADDON div.jqs-custom').find('.jqs-addon:eq(0) select');
                for (var i = 0; i < customAddons.length; i++) {
                    if (i == 0) {
                        $('.jqs-ADDON .panel-heading').trigger('click');
                    }
                    var customAddon = customAddons[i];

                    if(i > 0) {
                        $('.jqs-add-addl-custom-addon').trigger('click');
                        addonEl =  $('.jqs-ADDON div.jqs-custom').find('.jqs-addon:eq(' + i + ') select');
                    }
                    if($('.jqs-ADDON div.jqs-custom').find('.jqs-addon:eq(0) option[value="' + customAddon + '"]').length === 0) {
                        $($.toggleCustomOption($(addonEl), false)).val(customAddon);

                    } else {
                        $(addonEl).val(customAddon);
                    }
                    /*if (addon.ADDON_NAME === 'COATING') {
                        var sides = addon.SIDES;
                        for (var j = 0; j < sides.length; j++) {
                            $('.jqs-ADDON .jqs-ADDON-COATING-SIDE' + (j + 1) + ' .panel-heading').trigger('click');
                            $('.jqs-ADDON .jqs-ADDON-COATING-SIDE' + (j + 1)).find('input[name="COATING_SIDE_' + (j + 1) + '"][value="' + sides[j] + '"]').prop('checked', true);

                        }
                    }*/
                }
            }
        },

        getComments : function() {
            return $('textarea[name="quoteComments"]').val();
        },

        setComments : function(comments) {
            $('textarea[name="quoteComments"]').val(comments);
        },

        getInternalComments : function() {
            return $('textarea[name="quoteInternalComments"]').val();
        },

        setInternalComments : function(internalComment) {
            $('textarea[name="quoteInternalComments"]').val(internalComment);
        },

        getProduction : function() {
            return $('textarea[name="quoteProduction"]').val();
        },

        setProduction : function(production) {
            $('textarea[name="quoteProduction"]').val(production);
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
        showMessage: function (message, isError) {
            $.closeAllModal();
            var dialog;
            if(isError === true) {
                dialog = $('#messageDialog').removeClass('modal-success').addClass('modal-danger');
            } else {
                dialog = $('#messageDialog').removeClass('modal-danger').addClass('modal-success');
            }
            $(dialog).find('.jqs-dialog-body').html(message);
            $(dialog).modal('show');
        },

        setQuantities: function(quantities) {
            $('input[name="QUANTITIES"]').val('');
            for(var i = 4 - quantities.length, k = 0; i < 4; i ++, k ++) {
                $('input[name="QUANTITIES"]:eq(' + i +')').val(quantities[k]);
            }
        },

        init: function(pricingRequest) {
            if(typeof pricingRequest !== 'undefined') {
                if(typeof pricingRequest.CUSTOM_STYLE !== 'undefined' || typeof pricingRequest.CUSTOM_STYLE_GROUP !== 'undefined' || typeof pricingRequest.CUSTOM_VENDOR !== 'undefined') {
                    if(typeof pricingRequest.CUSTOM_STYLE_GROUP !== 'undefined') {
                        $.toggleCustomOption($('select[name="STYLE_GROUP"]'), false);
                        $('input[name="CUSTOM_STYLE_GROUP"]').val(pricingRequest.CUSTOM_STYLE_GROUP);
                        $('select[name="VENDOR"]').append('<option value="ADMORE">Admore</option>');
                    } else {
                        $.buildState($('select[name="STYLE_GROUP"]').val(pricingRequest.STYLE_GROUP));
                    }

                    if(typeof pricingRequest.CUSTOM_STYLE !== 'undefined') {
                        $.toggleCustomOption($('select[name="STYLE"]'), false);
                        $('input[name="CUSTOM_STYLE"]').val(pricingRequest.CUSTOM_STYLE);
                    } else {
                        $.buildState($('select[name="STYLE"]').val(pricingRequest.STYLE));
                    }

                    if(typeof pricingRequest.CUSTOM_VENDOR !== 'undefined') {
                        $.toggleCustomOption($('select[name="VENDOR"]'), false);
                        $('input[name="CUSTOM_VENDOR"]').val(pricingRequest.CUSTOM_VENDOR);
                    } else {
                        $('select[name="VENDOR"]').val(pricingRequest.VENDOR);
                    }
                } else if(typeof pricingRequest.STYLE !== 'undefined') {
                        $.buildState($('select[name="STYLE"]').val(pricingRequest.STYLE));
                }

                if(typeof pricingRequest.CUSTOM_STOCK !== 'undefined' || typeof pricingRequest.CUSTOM_STOCK_TYPE !== 'undefined') {
                    if(typeof pricingRequest.CUSTOM_STOCK_TYPE !== 'undefined') {
                        $.toggleCustomOption($('select[name="STOCK_TYPE"]'), false);
                        $('input[name="CUSTOM_STOCK_TYPE"]').val(pricingRequest.CUSTOM_STOCK_TYPE);
                    } else {
                        $.buildState($('select[name="STOCK_TYPE"]').val(pricingRequest.STOCK_TYPE));
                    }

                    if(typeof pricingRequest.CUSTOM_STOCK !== 'undefined') {
                        $.toggleCustomOption($('select[name="STOCK"]'), false);
                        $('input[name="CUSTOM_STOCK"]').val(pricingRequest.CUSTOM_STOCK);
                    } else {
                        $.buildState($('select[name="STOCK"]').val(pricingRequest.STOCK));
                    }
                } else if(typeof pricingRequest.STOCK !== 'undefined') {
                    $.buildState($('select[name="STOCK"]').val(pricingRequest.STOCK));
                }


                $.setQuantities(pricingRequest.QUANTITIES);
                $.setPrintOptions(pricingRequest.PRINT_OPTIONS);
                $.setAddons(pricingRequest.ADDONS, pricingRequest.CUSTOM_ADDONS);
                $.setVendorCost(pricingRequest.CUSTOM_VENDOR_COST);
                $.setMarkupsAndDiscounts(pricingRequest.MARKUPS_AND_DISCOUNTS);
                $.setCustomerAddress(pricingRequest.CUSTOMER_ADDRESS);
                $.buildState(undefined, false, pricingResponseJSON);
                $.setComments(comment);
                $.setInternalComments(internalComment);
                $.setProduction(production);

                 $('.jqs-show-quote').trigger('click');
            }
        }

    });
    $.bindDefaultMessages();
    $.bindPanelToggleEvent();
    $.bindRegularOptionsLink();
    $.bindAddRunToPrintOptionEvent();
    $.bindRemoveRunFromPrintOptionEvent();
    $.bindAddImageToRunEvent();
    $.bindRemoveImageFromRunEvent();
    $.bindAddAddlCustomAddon();
    $.bindRemoveAddlCustomAddon();
    $.bindAddAddlMarkupDiscount();
    $.bindRemoveAddlMarkupDiscount();
    $.bindGenerateQuote();
    $.stateChangeEvent();
    var pricingRequestJSON = pricingRequest === '' ? undefined : $.parseJSON(pricingRequest);
    var pricingResponseJSON = pricingResponse === '' ? undefined : $.parseJSON(pricingResponse);
    if(typeof pricingResponseJSON !== 'undefined') {
        pricingResponseJSON.quoteId = quoteId;
    }
    $.init(pricingRequestJSON);

})(jQuery);


