/* ############################################################
 * ############################################################
 * ############################################################
 * BASE JS FOR PRODUCT
 * Dependencies
 * jQuery
 * GoogleAnalytics
 * ############################################################
 * ############################################################
 * ############################################################
 * ############################################################
 */
function triggerAddressing() {
    $('[data-key="addresses"][data-value="true"]').trigger('click');
}

//MOVE THESE TO PRODUCT PAGE OBJECT
//###############################
//###############################
function adjustPageContent() {
    if (window.outerWidth <= 767 && $('.productContentLeft').find('.jqs-longdescParent').length == 0) {
        $('.jqs-longdescParent').appendTo('.productContentLeft');
        $('.jqs-colordescParent').appendTo('.productContentLeft');
    }
    else if (window.outerWidth > 767 && $('.productContentRight').find('.jqs-longdescParent').length == 0) {
        $('.jqs-longdescParent').appendTo('.productContentRight');
        $('.jqs-colordescParent').appendTo('.productContentRight');
    }

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
        $('.jqs-samplesPopupBody').scrollTo(($('#sampleRow-' + $('.jqs-productfeatures > div:first-child > .specsCol2').html()).offset().top - $('.jqs-samplesPopupBody').offset().top) + $('.jqs-samplesPopupBody').scrollTop());
    });
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

    $('.jqs-scrollToSelected').each(function() {
        $(this).on('click', function() {
            var selectListContainer = $('#' + $(this).attr('data-dropdown-target')).find('.selectListContainer');
            $(selectListContainer).scrollTop(($(selectListContainer).find('.slSelected').offset().top - $(selectListContainer).offset().top) + $(selectListContainer).scrollTop() - 1);
        });
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
        GoogleAnalytics.trackEvent('Product Page v1', 'Cross-Sell', $(this).attr('data-sku'));
        window.location.href = $(this).attr('href');
    });

    if (window.location.href.match(/(?:\?|\&)ss=true/) != null) {
        $('.jqs-orderSamplesQuickLink').trigger('click');
    }

    $('.jqs-optTestDesignsLeftSide .jqs-optTestDesignsLeftSideTemplate').css({
        'color': '#' + (isColorAllowed(productPage.returnActiveProduct(true).hex, '000000') ? '000000' : 'ffffff')
    });


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
    this.productName = 'jqs-productname';
    this.colorName = 'jqs-colorname';
    this.inventoryDom = 'jqs-inventoryDom';
    this.getItQuick = 'jqs-getItQuick';
    this.selected = 'optionSelected';
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
    callTexel: function(element) {
        var self = this;

        var texelDiv = $('#texel_' + self.activeProductIndex);
        if(typeof texelDiv.data('envTexel') === 'undefined') {
            texelDiv = self.startTexel(texelDiv);
        }

        self.updateDesignButton(true);
        GoogleAnalytics.trackEvent('Product Page v1', 'Designer', 'Launched');

        if(element.hasClass(self.variableDataGrid)) {
            texelDiv.texel('onLoad');
            self.startGrid(texelDiv, $('.' + self.addressOptions + ' .selected').attr('data-addressing-option'), true);
            GoogleAnalytics.trackEvent('Product Page v1', 'Addressing', 'Launched');
        }
    },
    bindTexel: function() {
        var self = this;

        $('.' + self.designNow).on('click', function() {
            if(typeof $(this).attr('data-reveal-id') == 'undefined') {
                var texelDiv = $('#texel_' + self.activeProductIndex);

                if(texelDiv.length == 0 && self.returnActiveProduct(true).design != null) {
                    self.callTexel($(this));
                } else {
                    texelDiv.texel('onLoad');
                    texelDiv.texel('option', 'backgroundColor', '#' + self.returnActiveProduct(true).hex);
                    texelDiv.texel('option', 'colorName', self.returnActiveProduct(true).color);
                }
            }
        });

        $('.jqs-calltexel').on('click', function() {
            self.callTexel($(this));
        });

        //launch grid if addresses have already been applied
        $('.' + self.addAddress).on('click', function() {
            if(typeof $(this).attr('data-reveal-id') == 'undefined') {
                var texelDiv = $('#texel_' + self.activeProductIndex);
                if(typeof texelDiv.data('envTexel') === 'undefined') {
                    texelDiv = self.startTexel(texelDiv);
                    texelDiv.texel('showGrid', 'manual');
                } else {
                    texelDiv.texel('onLoad');
                    texelDiv.texel('option', 'backgroundColor', '#' + self.returnActiveProduct(true).hex);
                    texelDiv.texel('option', 'colorName', self.returnActiveProduct(true).color);
                    texelDiv.texel('showGrid', 'manual');
                }
            } else {
                if(self.partyId == null) {
                    self.getUserLogin();
                }
            }
        });
    },
    startTexel: function(texelDiv) {
        var self = this;

        if(texelDiv.length == 0) {
            texelDiv = $('<div/>').attr('id', 'texel_' + self.activeProductIndex).addClass('texel');
            $('body').append(texelDiv);
        }

        var texelSettings = {
            'designs': {
                '0': {
                    'templateId': self.returnActiveProduct(true).design,
                    'templateType': (self.returnActiveProduct(true).templateType == 'FLAP') ? 'BLANK' : self.returnActiveProduct(true).templateType,
                    'templateSide': 'FRONT'
                },
                '1': {
                    'templateId': self.returnActiveProduct(true).backDesign,
                    'templateType': (self.returnActiveProduct(true).templateType != 'FLAP') ? 'BLANK' : '',
                    'templateSide': 'BACK'
                }
            },
            'activeDesign': (self.returnActiveProduct(true).templateType == 'FLAP') ? '1' : '0',
            'backgroundColor': '#' + self.returnActiveProduct(true).hex,
            'colorName': self.returnActiveProduct(true).color
        };
        if(typeof self.returnActiveProduct(true).s7Data !== 'undefined') {
            texelSettings = self.returnActiveProduct(true).s7Data.scene7Data.settings;
            $.extend(true, texelSettings.designs, self.returnActiveProduct(true).s7Data.scene7Data.designs);
        }

        texelDiv.texel(texelSettings);
        texelDiv.texel('onLoad');
        texelDiv.texel('setInkSelector', $('[data-key=colorsFront]'), $('[data-key=colorsBack]'));
        texelDiv.texel('setProduct', self.returnActiveProduct(false));
        texelDiv.texel('allowAddressing', self.returnActiveProduct(false).getProduct().hasAddressingAbility);
        texelDiv.texel('setExitFunction', function() {
            self.updateAddressingButton(texelDiv.texel('getVariableDataLength'));
            texelDiv.texel('preview', (self.returnActiveProduct(true).templateType == 'FLAP') ? '1' : '0', 520, null, function(data, fmt) {
                self.updateImage('data:image/' + fmt + ';base64,' + data.data);
            });
            self.returnActiveProduct(false).calculatePrice(null);
        });

        self.pageExitWarning(false);

        return texelDiv;
    },
    startGrid: function(texelDiv, method, show) {
        var self = this;

        var grid = texelDiv.texel('createAddressingGrid', [[]], method,  self.partyId, '0', false);
        if(show) {
            grid.VariableDataGrid('showGrid', method);
        }
    },
    updateDesignButton: function(close) {
        if(close) {
            $('#startDesign').foundation('reveal', 'close');
        }
        $('.' + this.designNow).html('Edit Your Design&nbsp;').append(
            $('<i />').addClass('fa fa-caret-right')
        );
    },
    updateAddressingButton: function(totalAddresses) {
        if(totalAddresses > 0) {
            $('.' + this.addAddress).addClass('jqs-hasdata').removeAttr('data-reveal-id').html(totalAddresses + ' Addresses (Edit)');
            $('[data-key=addresses][data-holder]').attr('data-total', totalAddresses);
        } else {
            $('#texAddAddressing').removeClass('jqs-hasdata').attr('data-reveal-id', 'startAddressing');
            $('.' + this.addAddress).removeClass('jqs-hasdata').attr('data-reveal-id', 'startAddressing').html('Add Recipient Addressing');
            $('[data-key=addresses][data-holder]').attr('data-total', 0);
        }
    },
    loadDesignOrUploadAssets: function(type) {
        var self = this;

        switch(type) {
            case 'design':
                $('.productImageContainer .' + self.designNow).removeClass('hidden');
                self.returnActiveProduct(true).designMethod = 'online';
                break;
            case 'upload':
                $('.productImageContainer .' + self.designNow).removeClass('hidden').addClass('hidden');
                self.returnActiveProduct(true).designMethod = 'upload';
                $('[data-options=upload]').removeClass('hidden');
                break;
        }
    },
    /**
     * Bind all the click events on the product page for options
     */
    bindClickEvents: function() {
        var self = this;

        function scrollToButton(obj) {
            $.scrollTo($(obj), 500, {offset:-250});
        }

        /**
         * This will toggle the main selectors on and off, if noSelection is true, this option allows no selections to be made
         * Will return true if a change in selection is made, else false
         * @param dataKey
         * @param dataValue
         * @param obj
         * @param noSelection
         * @returns {boolean}
         */
        var toggleSelector = function(dataKey, dataValue, obj, noSelection) {
            var currentlySelected = $('[data-key=' + dataKey + '].' + self.selected).attr('data-value');

            if(currentlySelected != dataValue) {
                $('[data-key=' + dataKey + ']').removeClass(self.selected).attr('data-selected', 'false');
                $(obj).addClass(self.selected).attr('data-selected', 'true');

                scrollToButton(obj);
                return true;
            } else if(currentlySelected == dataValue && noSelection) {
                $('[data-key=' + dataKey + ']').removeClass(self.selected);
                $(obj).removeClass(self.selected).attr('data-selected', 'false');

                scrollToButton(obj);
                return true;
            }

            return false;
        };

        $('.jqs-customizeTemplateButton').on('click', function() {
            $('.jqs-editDesign').removeClass('hidden');
            $('.jqs-editDesign .jqs-designnow').removeAttr('data-reveal-id');
            $('.productImageContainer .jqs-designnow').removeAttr('data-reveal-id');
            $('.jqs-editUploadedFiles').removeClass('hidden').addClass('hidden');
            $('.jqs-designOnlineOptions .jqs-designnow').removeAttr('data-reveal-id'); // REmove this when we go live with the optimizely stuff
            $('#texel_' + self.activeProductIndex).texel('destroy');//remove();
            delete self.returnActiveProduct(true).s7Data;

            if(toggleSelector('designOrUpload', 'design', this, false)) {
                GoogleAnalytics.trackEvent('Product Page v1', 'Design or Upload', $().capitalize('design'));
                self.loadDesignOrUploadAssets('design');
            }

            self.callTexel($(this));
        });

        $('.jqs-uploadPopupButton').on('click', function() {
            $(window).scrollTop(0);
            $('.jqs-editDesign').removeClass('hidden').addClass('hidden');
            $('.jqs-editUploadedFiles').removeClass('hidden');
            $('#texel_' + self.activeProductIndex).remove();

            self.loadDesignOrUploadAssets('upload');
        });

        /**
         * Handle all the necessary hardcoded show/hide events
         * data-key = the main header blocks
         * data-value = value for the keys
         * data-options = options
         */
        var firstToggle = true;
        $('[data-key]').on('click', function(e, showConfirm) {
            var selfDataKey = $(this);
            var dataKey = $(this).attr('data-key');
            var dataValue = $(this).attr('data-value');
            var element = $(this);

            switch(dataKey) {
                case 'plainOrPrinted':
                    if(toggleSelector(dataKey, dataValue, this, false)) {
                        GoogleAnalytics.trackEvent('Product Page v1', 'Plain Or Printed', $().capitalize(dataValue));

                        if(dataValue == 'plain') {
                            $('[data-options=printed]').removeClass('hidden').addClass('hidden');
                            $('.jqs-longdescParent').appendTo('.productContentRight');
                            $('.jqs-colordescParent').appendTo('.productContentRight');
                            $('.productImageContainer .' + self.designNow).removeClass('hidden').addClass('hidden');
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
                                $('.productImageContainer .' + self.designNow).removeClass('hidden');
                            }

                            if(firstToggle) {
                                if(self.returnActiveProduct(true).design == null) {
                                    self.returnActiveProduct(false).getProductTemplates(true);
                                }
                                self.returnActiveProduct(true).designMethod = $('[data-key="designOrUpload"][data-selected="true"]').attr('data-value');
                                firstToggle = false;
                            }

                            $('[data-options=printed]').removeClass('hidden');
                            $('.jqs-longdescParent').appendTo('.productContentLeft');
                            $('.jqs-colordescParent').appendTo('.productContentLeft');

                            var texelDiv = $('#texel_' + self.activeProductIndex);
                            if(texelDiv.length != 0 && typeof texelDiv.data('envTexel') != 'undefined') {
                                texelDiv.texel('preview', (self.returnActiveProduct(true).templateType == 'FLAP') ? '1' : '0', 520, null, function(data, fmt) {
                                    self.updateImage('data:image/' + fmt + ';base64,' + data.data);
                                });
                            } else if(self.returnActiveProduct(true).designMethod && self.returnActiveProduct(true).design != null && typeof startedAsDesign !== 'undefined' && startedAsDesign) {
                                //this is a design template
                                changeURL = false;
                                self.updateImage('//texel.envelopes.com/getBasicImage?id=' + self.returnActiveProduct(true).design + '&setElement.COLOR_bgcolor=%3Cfill%3E%3CSolidColor%20color%3D%22%23' + self.returnActiveProduct(true).hex + '%22%2F%3E%3C%2Ffill%3E' + '&hei=413&fmt=png-alpha');
                            }
                        }
                    }
                    break;
                case 'designOrUpload':
                    if(toggleSelector(dataKey, dataValue, this, false)) {
                        showConfirm = typeof showConfirm == 'undefined' ? !element.parentsUntil('.jqs-optTestShow').parent().hasClass('jqs-optTestShow') : showConfirm;
                        GoogleAnalytics.trackEvent('Product Page v1', 'Design or Upload', $().capitalize(dataValue));

                        if(dataValue == 'design') {
                            var continueEvent = function() {
                                $('[data-options=design]').removeClass('hidden').addClass('hidden');
                                $('[data-options=upload]').removeClass('hidden').addClass('hidden');
                                $('.productImageContainer .' + self.designNow).removeClass('hidden').addClass('hidden');
                                if($(selfDataKey).attr('data-selected') == 'true') {
                                    $('.productImageContainer .' + self.designNow).removeClass('hidden');
                                    self.returnActiveProduct(true).designMethod = 'online';
                                    $('[data-options=design]').removeClass('hidden');
                                }
                            };

                            if (($('.' + self.uploadedFiles + ' ul li').length > 0 || $('.' + self.reusedFiles).html() != '') && showConfirm) {
                                envConfirm('Continue With Choosing a Template?', 'Are you sure you want to switch printing methods? You\'ve already uploaded a print-ready file.', 'Yes, choose template', 'No, keep uploads', function(success) {
                                    if (success != true) {
                                        skip = true;
                                        $('[data-key="designOrUpload"][data-value="upload"]').trigger('click', false);
                                    } else {
                                        continueEvent();
                                    }
                                });
                            } else {
                                continueEvent();
                            }
                        } else {
                            var texelDiv = $('#texel_' + self.activeProductIndex);

                            var continueEvent = function() {
                                $('[data-options=design]').removeClass('hidden').addClass('hidden');
                                $('[data-options=upload]').removeClass('hidden').addClass('hidden');
                                $('.productImageContainer .' + self.designNow).removeClass('hidden').addClass('hidden');
                                if($(selfDataKey).attr('data-selected') == 'true') {
                                    self.returnActiveProduct(true).designMethod = 'upload';
                                    $('[data-options=upload]').removeClass('hidden');
                                }
                            };

                            if(texelDiv.length > 0 && self.returnActiveProduct(true).design != null && showConfirm) {
                                envConfirm('Continue With Upload?', 'Are you sure you want to switch printing methods?  You\'ve already customized your design.', 'Yes, upload a file', 'No, keep design', function(success) {
                                    if (success != true) {
                                        skip = true;
                                        $('[data-key="designOrUpload"][data-value="design"]').trigger('click', false);
                                    }
                                    else {
                                        continueEvent();
                                    }
                                });
                            } else {
                                continueEvent();
                            }
                        }
                    }

                    if($('[data-key=' + dataKey + '][data-selected=true]').length == 0) {
                        self.returnActiveProduct(true).designMethod = null;
                    }
                    break;
                case 'addresses':
                    GoogleAnalytics.trackEvent('Product Page v1', 'Add Addressing', (dataValue == 'true') ? 'Yes' : 'No');
                    if(toggleSelector(dataKey, dataValue, this, false)) {
                        var texelDiv = $('#texel_' + self.activeProductIndex);
                        if(dataValue == 'false') {
                            self.returnActiveProduct(true).addAddressing = false;
                            $('[data-options=addressing]').removeClass('hidden').addClass('hidden');
                            texelDiv.texel('deactivateOrActivateAddressing', false);
                        } else {
                            $('#recipientAddressing').foundation('reveal', 'open');
                            self.returnActiveProduct(true).addAddressing = true;
                            $('[data-options=addressing]').removeClass('hidden');
                            texelDiv.texel('deactivateOrActivateAddressing', true);
                        }

                        texelDiv.texel('preview', (self.returnActiveProduct(true).templateType == 'FLAP') ? '1' : '0', 520, null, function (data, fmt) {
                            self.updateImage('data:image/' + fmt + ';base64,' + data.data);
                        });
                    }
                    break;
                case 'isRush':
                    GoogleAnalytics.trackEvent('Product Page v1', 'Add Rush', (dataValue == 'true') ? 'Yes' : 'No');
                    if(toggleSelector(dataKey, dataValue, this, false)) {
                        //
                    }
                    break;
            }

            //get new product price
            self.returnActiveProduct(false).calculatePrice(null);
        });

        function createFileListNew() {
            $('.' + self.uploadedFiles).removeClass('hidden').empty();
            var fileList = $('.design-file');
            var completedFileArray = [];

            $.each(fileList, function() {
                completedFileArray.push({
                    'name': $(this).attr('data-filename'),
                    'path': $(this).attr('data-filepath')
                });

                $('.' + self.uploadedFiles).append(
                    $('<div>').append(
                        $(this).attr('data-filename')
                    ).append(
                        $('<i/>').addClass('fa fa-times').on('click', function() {
                            self.returnActiveProduct(true).files.splice($(this).parent().index(), 1);
                            $(this).parent().remove();
                            if(self.returnActiveProduct(true).files.length == 0 && self.returnActiveProduct(true).oldFiles.length == 0) {
                                $('.' + self.uploadedFiles).removeClass('hidden').addClass('hidden');
                            }
                        })
                    )
                );
            });

            self.returnActiveProduct(true).files = completedFileArray;

            var selectedReuseFile = $('[name="reuseFiles"]:checked');

            if (selectedReuseFile.length > 0) {
                $('.' + self.reusedFiles).append(
                    $('<div />').append(
                        'Reuse from ' + selectedReuseFile.attr('data-order')
                    ).append(
                        $('<i/>').addClass('fa fa-times').on('click', function() {
                            self.returnActiveProduct(true).oldFiles.splice(0, 1);
                            $(this).parent().empty();
                            if(self.returnActiveProduct(true).files.length == 0 && self.returnActiveProduct(true).oldFiles.length == 0) {
                                $('.' + self.reusedFiles).removeClass('hidden').addClass('hidden');
                            }
                        })
                    )
                );

                self.returnActiveProduct(true).oldFiles = [{
                    'name': $(selectedReuseFile).attr('data-filename'),
                    'path': $(selectedReuseFile).attr('data-filepath'),
                    'order': $(selectedReuseFile).attr('data-order'),
                    'orderItem': $(selectedReuseFile).attr('data-order-item')
                }];
            }
        }

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
            //$('#' + (optTest ? self.newreuseId : self.reuseId)).foundation('reveal', 'open');
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
                        //$('#' + (optTest ? self.newreuseId : self.reuseId)).find('.not-logged-in').addClass('hidden');
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
                                                //if (optTest){
                                                //	createFileListNew($(this));
                                                //}
                                                //else {
                                                createReuseFileList($(this));
                                                //}
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

        //$('#' + (optTest ? self.newreuseId : self.reuseId)).find('.not-logged-in').on('click', function() {
        $('#' + self.reuseId).find('.not-logged-in').on('click', function() {
            $('.jqs-hidden-login-button').trigger('click', loadReuseData);
        });

        //$('[data-reveal-id=' + (optTest ? self.newreuseId : self.reuseId) + ']').on('click', function() {
        $('[data-reveal-id=' + self.reuseId + ']').on('click', function() {
            //if(!$('#' + (optTest ? self.newreuseId : self.reuseId)).find('.not-logged-in').hasClass('hidden')) {
            if(!$('#' + self.reuseId).find('.not-logged-in').hasClass('hidden')) {
                loadReuseData();
            }
        });
        //###########################################
        //################END RE USE#################
        //###########################################

        //bind file uploading to product
        //###########################################
        //##############BEGIN FILE UPLOAD############
        //###########################################
        function createFileList(fileList) {
            $('.' + self.uploadedFiles).removeClass('hidden').empty();
            var completedFileArray = [];
            var myList = $('<ul />').addClass('text-left no-margin margin-left-xs');

            $.each(fileList, function() {
                var fileIndex = completedFileArray.push( { 'name' : $(this).attr('data-filename'), 'path' : $(this).attr('data-filepath') } );

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

            myList.append(
                $('<div />').css({
                    'margin-top': '10px',
                    'color': '#fc7e22'
                }).html('After checkout, you\'ll receive an emailed proof within 24 hours.  Once you approve your proof, your order will enter print production.')
            );

            $('.' + self.uploadedFiles).append(myList);

            self.returnActiveProduct(true).files = completedFileArray;
        }

        $('.dropzone').on('click', function() {
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

                        //loop through the dom and create data for the slide
                        //if (optTest) {
                        //	createFileListNew();
                        //}
                        //else {
                        createFileList($('.dropzone > div'));
                        //}
                    });

                    data.context = fileDiv.appendTo($('.' + self.fileGrid).find('.dropzone'));
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

                        //loop through the dom and create data for the slide
                        //if (optTest) {
                        //	createFileListNew();
                        //}
                        //else {
                        createFileList($('.dropzone > div'));
                        //}
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
        $('.' + self.cart).on('click', function(e) {
            self.addToCart(this);
        });

        //close select boxes
        $('.jqs-selectList').off('click.updateSelection').on('click.updateSelection', function(e) {
            if (!$(this).hasClass('slSelected')) {
                self.updateSelection($(this), e);
                hideAllDropDown(0, true);
            }
        });

        //close modals
        $('.' + self.closeModal).on('click', function() {
            $(this).closest('.reveal-modal').foundation('reveal', 'close');
        });

        //bind comments for item
        $('.' + self.comments).on('keyup blur', function() {
            self.returnActiveProduct(true).comments = ($(this).val() == '') ? null : $(this).val();
        });

        //bind color filter
        $('.' + self.productFilter + ' [data-color-group], ' + '.' + self.productFilter + ' [data-collection-group], .jqs-clearProductFilter').on('click', function(){
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

        // Bind filter type selection.
        $('.' + self.filterOption).on('click', function() {
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
    },
    /**
     * Update selection for select list
     */
    updateSelection: function(element, e) {
        var self = this;
        $(element).siblings().removeClass('slSelected').attr('data-selected', 'false');
        $(element).addClass('slSelected').attr('data-selected', 'true');

        var asset = $('.' + $(element).attr('data-target')).find('.slDownArrow');
        $('.' + $(element).attr('data-target')).html($(element).html()).append(asset);

        switch($(element).attr('data-target')) {
            case 'colorSelection':
                self.returnActiveProduct(true).productId   		= $(element).attr('data-product-id');
                self.returnActiveProduct(true).name        		= $(element).attr('data-product-name');
                self.returnActiveProduct(true).color       		= $(element).attr('data-product-color');
                self.returnActiveProduct(true).hex         		= $(element).attr('data-hex');
                self.returnActiveProduct(true).printable   		= $.parseJSON($(element).attr('data-printable'));
                self.returnActiveProduct(true).minColor    		= parseInt($(element).attr('data-min-color'));
                self.returnActiveProduct(true).maxColor    		= parseInt($(element).attr('data-max-color'));
                self.returnActiveProduct(true).hasWhiteInk 		= $.parseJSON($(element).attr('data-has-white-ink'));
                self.returnActiveProduct(true).hasSample   		= $.parseJSON($(element).attr('data-has-sample'));
                self.returnActiveProduct(true).paperWeight 		= $(element).attr('data-product-weight');
                self.returnActiveProduct(true).brand       		= $(element).attr('data-product-brand');
                self.returnActiveProduct(true).rating      		= $(element).attr('data-rating');
                self.returnActiveProduct(true).percentSavings	= parseInt($(element).attr('data-percent-savings'));
                self.returnActiveProduct(true).hasPeelAndPress  = $(element).attr('data-haspeelandpress');

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

                //change image (need to check if printed or not later
                var plainOrPrinted = $('[data-key=plainOrPrinted][data-selected=true]').attr('data-value');
                var totalAddresses = parseInt($('[data-key=addresses][data-selected=true]').attr('data-total'));
                var texelDiv = $('#texel_' + self.activeProductIndex);
                var changeURL = true;

                if(plainOrPrinted == 'printed' && texelDiv.length != 0 && typeof texelDiv.data('envTexel') != 'undefined' && (self.returnActiveProduct(true).designMethod == 'online' || (self.returnActiveProduct(true).addAddressing && totalAddresses > 0))) {
                    texelDiv.texel('option', 'backgroundColor', '#' + self.returnActiveProduct(true).hex);
                    texelDiv.texel('option', 'colorName', self.returnActiveProduct(true).color);
                    texelDiv.texel('paintTexel', 0, true);
                    texelDiv.texel('preview', (self.returnActiveProduct(true).templateType == 'FLAP') ? '1' : '0', 520, null, function (data, fmt) {
                        self.updateImage('data:image/' + fmt + ';base64,' + data.data);
                    });
                } else if(plainOrPrinted == 'printed' && self.returnActiveProduct(true).designMethod && self.returnActiveProduct(true).design != null && typeof startedAsDesign !== 'undefined' && startedAsDesign) {
                    //this is a design template
                    changeURL = false;
                    self.updateImage('//texel.envelopes.com/getBasicImage?id=' + self.returnActiveProduct(true).design + '&setElement.COLOR_bgcolor=%3Cfill%3E%3CSolidColor%20color%3D%22%23' + self.returnActiveProduct(true).hex + '%22%2F%3E%3C%2Ffill%3E' + '&hei=413&fmt=png-alpha');
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
                    var frontInkToTrigger = null;
                    var backInkToTrigger = null;
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

                self.getDueDates();
                self.returnActiveProduct(false).getProductInventory();
                self.returnActiveProduct(false).getProductReviews();
                self.returnActiveProduct(false).getProductFeatures();
                self.returnActiveProduct(false).getProductDesc();

                var updateImageCallback = function(id) {
                    self.updateImage('//actionenvelope.scene7.com/is/image/ActionEnvelope/' + id + '?hei=413&wid=510&fmt=png-alpha');
                };

                self.returnActiveProduct(false).getProductAssets(updateImageCallback);
                if(self.returnActiveProduct(true).printable) {
                    self.returnActiveProduct(false).getProductTemplates(false);
                }

                //change url
                if(changeURL) { history.replaceState(null, $(element).attr('data-product-name') + ' | ' + $(element).attr('data-product-color'), $(element).attr('data-url')); }

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
                
                GoogleAnalytics.trackEvent('Product Page v1', 'Color Selection', (typeof $(element).attr('data-product-id') != 'undefined' ? $(element).attr('data-product-id') : 'No Sku Specified'));

                //analytics virtual page view tracking for individual colors
                if (typeof window.location.pathname != 'undefined' && typeof e.isTrigger == 'undefined') {
                    GoogleAnalytics.trackPageview(window.location.pathname, window.document.title);
                    GoogleAnalytics.trackProductView(self.returnActiveProduct(true).productId, self.returnActiveProduct(true).name, self.returnActiveProduct(true).color);
                }

                break;
            //case 'inkFrontSelection':
            //	if (optTest) {
            //		$('.jqs-colorsFrontText').html($(element).children('span').html());
            //	}
            //	break;
            //case 'inkBackSelection':
            //	if (optTest) {
            //		$('.jqs-colorsBackText').html($(element).children('span').html());
            //	}
            //	break;
        }

        //get new product price
        self.returnActiveProduct(false).calculatePrice(null);
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

        if(product.getProduct().addAddressing && totalAddresses == 0) {
            //addressing error
            self.processButtonError($('[data-key=addresses][data-value=true]'), null, 'You did not add any addresses yet!');
            return false;
        }

        if(product.getProduct().designMethod == 'online' && product.getProduct().scene7DesignId == null) {
            //design error
            if(typeof startedAsDesign !== 'undefined' && startedAsDesign) {
                //self.processButtonError((optTest ? $('[data-key=plainOrPrinted][data-value=printed]') : $('.jqs-designnow.jqs-mainDN')), null, 'You did not edit your design yet!');
                self.processButtonError($('.jqs-designnow.jqs-mainDN'), null, 'You did not edit your design yet!');
                $(window).scrollTop($('.productTools .subOptions').offset().top);
            } else {
                //self.processButtonError((optTest ? $('[data-key=plainOrPrinted][data-value=printed]') : $('[data-key=designOrUpload][data-value=design]')), null, 'You did not edit your design yet!');
                self.processButtonError($('[data-key=designOrUpload][data-value=design]'), null, 'You did not edit your design yet!');
                $(window).scrollTop($('.productTools .subOptions').offset().top);
            }

            return false;
        }

        if(product.getProduct().designMethod == null && product.getProduct().addAddressing == false) {// && optTest == false) {
            //convert to plain
            self.processButtonError($('[data-key=plainOrPrinted][data-value=printed]'), null, 'You chose to print, but did not select any print options!');
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

                if(plainOrPrinted  == 'printed') {
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

        if($(el).hasClass('testA')) {
           $('[data-dropdown-target="dropdown-addtocartTestA"]').trigger('click.dropdown');
        }

        //relocate to cart once all products have been added
        if( $(el).hasClass('testA') || $(el).hasClass('testB')) {
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
            
            if(successCallback != null && typeof successCallback == 'function') {
                successCallback();
            }
        }).fail(function(jqXHR) {
            if(errorCallback != null && typeof errorCallback == 'function') {
                errorCallback();
            }
        });
        
    },
    //save the entire project
    saveProject: function() {
        var self = this;

        var scene7Order = false;

        $.each(self.getProducts(), function(idx) {
            if((self.getProducts()[idx].getProduct().colorsFront > 0 || self.getProducts()[idx].getProduct().colorsBack > 0) && typeof self.getProducts()[idx].getProduct().s7Data !== 'undefined' && (self.getProducts()[idx].getProduct().designMethod == 'online' || self.getProducts()[idx].getProduct().addAddressing)) {
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
            });

            $.ajax({
                type: 'POST',
                timeout: 5000,
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
                }
            });
        }

        return self.projectId;
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
            success: function(data) {
                if(isProject && data.success && typeof data.settings !== 'undefined') {
                    $.each(data.settings.product, function(index) {
                        $.extend(true, self.getProducts()[index].getProduct(), data.settings.product[index]);
                        self.loadProject(data.settings.product[index].scene7DesignId, false, index);
                    });
                } else if(!isProject && data.success && typeof data.settings != 'undefined') {
                    self.getProducts()[index].getProduct().s7Data = { 'scene7Data' : data };
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
        'addOnProduct': null
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

            localStorage.recentlyVisitedProducts = JSON.stringify(recentlyVisitedProducts);
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
        var plainOrPrinted = $('[data-key=plainOrPrinted]' + '.' + this.selected);
        switch(plainOrPrinted.attr('data-value')) {
            case 'plain':
                //do nothing
                break;
            case 'printed':
                //
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
                                var productPercentSavings = Math.round((1 - (productPriceData.priceList[key].price / originalPriceData.priceList[key].price)) * 100);
                                $('.' + self.priceGrid).append(
                                    $('<div/>').addClass('jqs-selectList qpsListItems selectList' + ((key == attr.quantity) ? ' slSelected' : '')).attr('data-qty', key).attr('data-price', productPriceData.priceList[key].price).attr('data-originalPrice', originalPriceData.priceList[key].price).append(
                                        $('<span/>').html(numberWithCommas(key) + ' Qty')
                                    ).append(
                                        $('<span/>').addClass('originalPriceDisplay margin-left-xxs').append(
                                            $('<strike />').html(formatCurrency(originalPriceData.priceList[key].price))
                                        )
                                    ).append(
                                        $('<span/>').addClass('priceDisplay margin-left-xxs').html(formatCurrency(productPriceData.priceList[key].price))
                                    ).append(
                                        $('<span/>').addClass('savingsDisplay margin-left-xxs').html(productPercentSavings > 0 ? 'Save ' + productPercentSavings + '%' : '')
                                    ).append(
                                        $('<span/>').addClass('jqs-subShipNotice freeshipnote' + ((productPriceData.priceList[key].price < freeShippingAmount ) ? ' hidden' : '')).html(' + ').append(
                                            $('<i/>').addClass('fa fa-truck')
                                        ).append(' Ships FREE w/code')
                                    )
                                );
                            }
                            else {
                                if (index == 0) {
                                    $('.' + self.priceGrid).append(
                                        $('<div/>').addClass('jqs-selectList qpsListItems selectList' + ((key == attr.quantity) ? ' slSelected' : '')).attr('data-qty', key).attr('data-price', productPriceData.priceList[key].price).append(
                                            $('<span/>').html(numberWithCommas(key) + ' Qty')
                                        ).append(
                                            $('<span/>').addClass('priceDisplay margin-left-xxs').html(formatCurrency(productPriceData.priceList[key].price))
                                        ).append(
                                            $('<span/>').addClass('jqs-pricePerUnit pricePerUnitDisplay margin-left-xxs').html(formatCurrency(productPriceData.priceList[key].price / key) + '/each')
                                        ).append(
                                            $('<span/>').addClass('jqs-subShipNotice freeshipnote' + ((productPriceData.priceList[key].price < freeShippingAmount ) ? ' hidden' : '')).html(' + ').append(
                                                $('<i/>').addClass('fa fa-truck')
                                            ).append(' Ships FREE w/code')
                                        )
                                    );
                                }
                                else {
                                    $('.' + self.priceGrid).append(
                                        $('<div/>').addClass('jqs-selectList qpsListItems selectList' + ((key == attr.quantity) ? ' slSelected' : '')).attr('data-qty', key).attr('data-price', productPriceData.priceList[key].price).append(
                                            $('<span/>').html(numberWithCommas(key) + ' Qty')
                                        ).append(
                                            $('<span/>').addClass('priceDisplay margin-left-xxs').html(formatCurrency(productPriceData.priceList[key].price))
                                        ).append(
                                            $('<span/>').addClass('jqs-pricePerUnit pricePerUnitDisplay margin-left-xxs').html(formatCurrency(productPriceData.priceList[key].price / key) + '/each')
                                        ).append(
                                            $('<span/>').addClass('jqs-percentSavings percentSavingsDisplay margin-left-xxs').html('Save ' + Math.round((1 - (productPriceData.priceList[key].price / ((key / self.smallestQuantity) * self.lowestPrice))) * 100) + '%')
                                        ).append(
                                            $('<span/>').addClass('jqs-subShipNotice freeshipnote' + ((productPriceData.priceList[key].price < freeShippingAmount ) ? ' hidden' : '')).html(' + ').append(
                                                $('<i/>').addClass('fa fa-truck')
                                            ).append(' Ships FREE w/code')
                                        )
                                    );
                                }
                            }
                        }

                        if(key == attr.quantity) {
                            self.showSelPrice(numberWithCommas(key) + ' Qty', {
                                salePrice: formatCurrency(productPriceData.priceList[key].price),
                                originalPrice: self.getProduct().percentSavings > 0 ? formatCurrency(originalPriceData.priceList[key].price) : formatCurrency(productPriceData.priceList[key].price)
                            }, productPriceData.priceList[key].price >= freeShippingAmount);
                        }
                    });

                    self.getFirstQuantity(); //if no price is selected for whatever reason, select the first
                    self.bindPrices();
                }
            });
        }, 250, 'calcPrice');
    },
    getFirstQuantity: function() {
        var self = this;

        if($('.' + self.priceGrid).find('.slSelected').length == 0) {
            var firstPrice = $('.' + self.priceGrid + ' > [data-qty]')[0];
            self.priceData.quantity = parseInt($(firstPrice).attr('data-qty'));
            $(firstPrice).addClass('slSelected');
            self.showSelPrice(numberWithCommas($(firstPrice).attr('data-qty')) + ' Qty', {
                salePrice: formatCurrency(parseFloat($(firstPrice).attr('data-price'))),
                originalPrice: formatCurrency(parseFloat($(firstPrice).attr('data-originalPrice')))
            }, parseFloat($(firstPrice).attr('data-price')) >= freeShippingAmount);
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

        $('.' + self.priceGrid + ' [data-qty]').off('click').on('click', function() {
            self.showSelPrice(numberWithCommas(parseInt($(this).attr('data-qty'))) + ' Qty', {
                salePrice: formatCurrency($(this).attr('data-price')*1),
                originalPrice: formatCurrency($(this).attr('data-originalPrice')*1)
            }, $(this).attr('data-price')*1 >= freeShippingAmount);

            self.priceData.quantity = $(this).attr('data-qty').replace(/[^\d\.]+/g, '')*1;
            self.setSelectable($(this), $('.' + self.priceGrid + ' [data-qty]'), 'slSelected');
            hideAllDropDown(0, true);
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
                            'data-src': '//actionenvelope.scene7.com/is/image/ActionEnvelope/' + self.returnProductId() + '?hei=413&wid=510&fmt=png-alpha&align=0,-1'
                        }).append($('<img/>').attr('src', '//actionenvelope.scene7.com/is/image/ActionEnvelope/' + self.returnProductId() + '?hei=48&fmt=png-alpha'))
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
            //if (optTest) {
            //	templateGrid.append(
            //		$('<div/>').append(
            //			$('<div/>').addClass('uploadFile jqs-uploadFile').append(
            //				$('<div />').addClass('row icon').append(
            //					$('<i />').addClass('fa fa-cloud-upload')
            //				).append('/').append(
            //					$('<i />').addClass('fa fa-recycle')
            //				)
            //			).append(
            //				$('<div />').addClass('row iconText').append(
            //					$('<span />').addClass('padding-right-xs').html('Upload')
            //				).append(
            //					$('<span />').addClass('padding-left-xs').html('Reuse')
            //				)
            //			).append(
            //				$('<div />').html('Accepted Formats')
            //			).append(
            //				$('<div />').addClass('allowedFileTypes').append(
            //					$('<div />').addClass('pdf').html('pdf')
            //				).append(
            //					$('<div />').addClass('psd').html('psd')
            //				).append(
            //					$('<div />').addClass('png').html('png')
            //				).append(
            //					$('<div />').addClass('jpg').html('jpg')
            //				).append(
            //					$('<div />').addClass('ai').html('ai')
            //				).append(
            //					$('<div />').addClass('tiff').html('tiff')
            //				)
            //			).on('click', function () {
            //				$(this).parentsUntil('.designTemplateContainer').find('.jqs-customizeTemplateButton').removeClass('hidden').addClass('hidden');
            //				$(this).parentsUntil('.designTemplateContainer').find('.jqs-uploadPopupButton').removeClass('hidden');
            //				self.setSelectable($(this), $('.designTemplateList > div > div'));
            //			})
            //		).append(
            //			$('<p/>').html('UPLOAD/REUSE A PRINT READY FILE')
            //		)
            //	);
            //}

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
            waitForFinalEvent(function () {
                $.ajax({
                    type: "GET",
                    url: '/' + websiteId + '/control/getDesignsForProduct',
                    data: {id: self.returnProductId(), vId: self.getProduct().parentProductId},
                    dataType: 'json',
                    cache: true
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
            }, 250, 'productTemplates');
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