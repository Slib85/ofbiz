/**
 * FUNCTION TO ADD CUSTOM EVENT SUPPORT TO IE
 */
(function () {
    if ( typeof window.CustomEvent === 'function' ) return false; //If not IE

    function CustomEvent ( event, params ) {
        params = params || { bubbles: false, cancelable: false, detail: undefined };
        var evt = document.createEvent( 'CustomEvent' );
        evt.initCustomEvent( event, params.bubbles, params.cancelable, params.detail );
        return evt;
    }

    CustomEvent.prototype = window.Event.prototype;

    window.CustomEvent = CustomEvent;
})();

window.loadDoogma = true;
window.doogmaParameters = window.doogmaParameters || {};
window.doogmaParameters.productview = 'perspective';
window.doogmaParameters.papercolorandtexture = 'brightwhite80lb';
window.doogmaParameters.pocketshape = 'standard';
window.doogmaParameters.togglegrid = 'off';
window.doogmaParameters.doogmasaveddesign = null;
window.doogmaParameters.doogmaprintready = null;
window.doogmaParameters.doogmauploadedimage = null;
window.doogmaParameters.doogmathumb = null;

var doogmaInitEvent = new CustomEvent('doogmaparametersinitialize', {
    detail: window.doogmaParameters,
    bubbles: true,
    cancelable: true
});
document.dispatchEvent(doogmaInitEvent);

/* ############################################################
 * Dependencies
 * jQuery
 */

/**
 * Folders.com Product Page
 * @constructor
 */
function ProductPage() {
    /**
     * Array of all products on the page
     * Sequence of products are the sequence in which they should be displayed
     * @type {Array}
     */
    var self = this;
    var products = [];

    this.stepList = [];
    this.urlTranslator = {
        'CURVED': 'curved',
        'VERTICAL WAVE': 'verticalwave',
        'WAVY': 'wavy',
        'TALL': 'tall',
        'DIAGONAL': 'diagonal',
        'STANDARD': 'standard',
        'PMS': 'spot-color-printing',
        'FOUR_COLOR': 'four-color-printing',
        'foilStamping': 'foil-stamping',
        'embossing': 'embossed'
    };
    this.activeProductIndex = 0;
    this.activePageContent = {
        productPrinting: {
            buildFolderHeader: false,
            buildQuoteHeader: false,
            paperColor: false,
            foilColor: false,
            pockets: false,
            printMethodSelection: {
                pm_blank: false,
                pm_fourColor: false,
                pm_spotColor: false,
                pm_printing: false,
                pm_embossing: false,
                pm_foil: false
            },
            numberOfImages: false,
            inkColors: false,
            spotColors: false,
            attachments: false,
            corners: false,
            cardSlits: false
        },
        folderDesign: {
            onlineDesigner: false,
            uploadDesign: false,
            reuseArtwork: false,
            designServices: false
        },
    };

    /**
     * Return all products
     * @returns {Array}
     */
    this.getProducts = function() {
        return products;
    };

    /**
     * Set all products
     * @param data
     */
    this.setProducts = function(data) {
        products = data;
    };

    this.fileGrid = 'jqs-filecontainer';
    this.newreuseId = 'startUpload';
    this.uploadedFiles = 'jqs-uploadedfiles';
    this.selectionExistsList = ['cardSlits'];
}

ProductPage.prototype = {
    constructor: ProductPage,
    /**
     * Create a new Product object and insert into array
     * @param id
     */
    getActiveProductAttributes: function() {
        return this.getProducts()[this.activeProductIndex].getProductAttributes();
    },

    getActiveProductIndex: function() {
        return this.activeProductIndex;
    },

    setActiveProductIndex: function(index) {
        this.activeProductIndex = index;
    },

    getActiveProduct: function() {
        return this.getProducts()[this.activeProductIndex];
    },

    addProduct: function(data) {
        var product = new Product();
        $.extend(true, product.getProductAttributes(), data);
        this.getProducts().push(product);
    },

    updateMode: function() {
        var self = this;

        self.saveRestrictedDoogmaValues();

        if (self.getActiveProductAttributes().selectionData.printMethod == 'foilStamping' || self.getActiveProductAttributes().selectionData.printMethod == 'embossing' || self.getActiveProductAttributes().selectionData.printMethod == 'FOUR_COLOR' || self.getActiveProductAttributes().selectionData.printMethod == 'PMS') {
            self.getProducts()[self.activeProductIndex].setProductAttributes({
                'mode': 'designer'
            });

            if(window.loadDoogma) {
                $.ajax({
                    url: '//cdne2im.doogma.com/smartmobile-v2/loader.js',
                    dataType: "script",
                    cache: true,
                    async: true
                }).done(function(data) {
                    window.loadDoogma = false;
                });
            }
        } else {
            self.getProducts()[self.activeProductIndex].setProductAttributes({
                'mode': 'regular'
            });
        }

        if (self.getActiveProductAttributes().mode == 'designer') {
            $('[bns-heroimagecontainer]').removeClass('hidden').addClass('hidden');
            $('[bns-doogmacontainer]').removeClass('hidden');
            $('.doogmaplugin').appendTo($('[bns-doogmacontainer]'));
            if ($('[bns-doogmacontainer]').has('.creditCardRelax').length > 0 || $('[bns-doogmacontainer]').has('.prePromise').length > 0) {
                $('.creditCardRelax').remove();
                $('.prePromise').remove();
            }
            // var preOrderPromiseBlock = $('<div />').addClass('foldersTabularRow textCenter prePromise').append(
            //     $('<img />').attr({
            //         'data-bnreveal': 'preOrderPromise',
            //         'src': 'https://actionenvelope.scene7.com/is/image/ActionEnvelope/peaceOfMindPromiseProductPage?fmt=png-alpha&amp;wid=179',
            //         'alt': 'Pre Order Promise'
            //     })
            // );
            // preOrderPromiseBlock.insertBefore($('.doogmaplugin'));
            var uploadVectorBlock = $('<div />').addClass('fbc-white prePromise uploadArtwork').attr('data-bnreveal','startUpload').append(
                    $('<i />').addClass('fa fa-cloud-upload')
                ).append(
                    $('<div />').addClass('uploadArtworkText').append(
                        $('<p />').html('Upload Vector Art Here').append(
                            $('<i />').addClass('fa fa-caret-right').css({
                                'position': 'relative',
                                'font-size': '18px',
                                'top': '1px',
                                'width': 'initial',
                                'height': 'initial',
                                'padding': '0 0 0 5px'
                            })
                        )
                    ).append(
                        $('<span />').addClass('ftc-lightBlue').css('font-size', '13px').html('.EPS, .PDF, .AI Files')
                    )
                );
            uploadVectorBlock.insertBefore($('.doogmaplugin'));
            var creditCardRelaxBlock = $('<div />').addClass('foldersTabularRow creditCardRelax textCenter').append(
                $('<i />').addClass('fa fa-credit-card')
            ).append(
                $('<p />').addClass('ftc-blue textCenter').html('Relax, we will not charge your card until your proof has been approved!')
            );
            creditCardRelaxBlock.insertAfter($('.doogmaplugin'));
        } else {
            if (productPage.stepList[productPage.stepList.length - 1] != 'selectPrintMethod') {
                $('[bns-heroimagecontainer]').removeClass('hidden');
            }
            $('[bns-doogmacontainer]').removeClass('hidden').addClass('hidden');
        }

        if (self.getActiveProductAttributes().selectionData.printMethod != 'foilStamping') {
            //if(($.inArray('printedFolders', productPage.stepList) !== -1) && ($.inArray('folderDesign', productPage.stepList) !== -1)) {
            $('.jqs-designLayout').addClass('hidden');
            $('.jqs-uploadDesign').removeClass('hidden');
            $('.jqs-sampleReadableList').find('[selection-value="onlineDesigner"]').addClass('hidden');
        }

        if (self.getActiveProductAttributes().selectionData.printMethod == 'embossing') {
            //if(($.inArray('embossing', productPage.stepList) !== -1) && ($.inArray('folderDesign', productPage.stepList) !== -1)) {
            $('.jqs-sampleReadableList').find('[selection-value="onlineDesigner"]').addClass('hidden');
        }

        //window.dispatchEvent(new Event('resize')); //bugfix for doogma
    },

    loadProductStep: function(stepName) {
        var self = this;

        if (typeof stepName != 'undefined' && stepName != '') {
            if (stepName == 'folderDesign') {
                $('[bns-productperspective]').trigger('click');
            }

            if ($.inArray(stepName, self.stepList) < 0) {
                self.stepList.push(stepName);
            }

            $('[bns-productStep]').attr('bns-productStep', stepName);

            $.ajax({
                type: 'POST',
                url: '/' + websiteId + '/control/' + stepName,
                data: {
                    'product_id': self.getActiveProductAttributes().selectionData.productId,
                    'productAttributes': JSON.stringify(self.getActiveProductAttributes())
                },
                dataType: 'html',
                cache: false,
                async: false
            }).done(function (data) {
                $('[bns-productStep]').html(data);
                self.getProductPrice();
                self.initPageActions();
                self.rebuildSelections();
                self.updateMode();
                $('.jqs-printMethodInfo').addClass('hidden');

                //load doogma
                if(window.loadDoogma) {
                    $.ajax({
                        url: '//cdne2im.doogma.com/smartmobile-v2/loader.js',
                        dataType: "script",
                        cache: true,
                        async: true
                    }).done(function(data) {
                        window.loadDoogma = false;
                    });
                }

                if ($('.productSidebar').offset().top < $(window).scrollTop()) {
                    $(window).scrollTop($('.productSidebar').offset().top);
                }
            });
        }
    },

    loadPreviousProductStep: function(stepName) {
        var self = this;

        if (typeof stepName == 'undefined' && self.stepList.length >= 2) {
            stepName = self.stepList[self.stepList.length - 2];
        }

        if (typeof stepName != 'undefined' && $.inArray(stepName, self.stepList) >= 0) {
            self.stepList.splice($.inArray(stepName, self.stepList), self.stepList.length);
            self.loadProductStep(stepName);
        }
    },

    /**
     * Remove a Product object from the array
     * @param id
     */
    removeProduct: function(id) {
        for(var i = 0; i < this.getProducts().length; i++) {
            if(this.getProducts()[i].returnProductId() == id) {
                this.getProducts().splice(i);
                break;
            }
        }
    },

    updatePageContent: function(data) {
        data = typeof data == 'object' ? data : {};

        function updatePage(activePageContentData, newData, reset) {
            reset = typeof reset != 'undefined' ? reset : true;
            var hasChildren = false;

            if (reset) {
                $.extend(true, activePageContentData, newData)
                updatePage(activePageContentData, newData, false);
            }

            for (var key in activePageContentData) {
                var showPreviousKeysContent = false;

                if (typeof activePageContentData[key] == 'object') {
                    showPreviousKeysContent = updatePage(activePageContentData[key], newData, false).hasChildren;
                } else if (reset) {
                    activePageContentData[key] = false;
                }

                if ((typeof activePageContentData[key] == 'boolean' && activePageContentData[key]) || showPreviousKeysContent) {
                    $('.jqs-' + key).removeClass('hidden');
                    hasChildren = true;
                } else {
                    $('.jqs-' + key).removeClass('hidden').addClass('hidden');
                }
            }

            return {'data': activePageContentData, 'hasChildren': hasChildren};
        }

        this.activePageContent = updatePage(this.activePageContent, data).data;
    },

    updateURL: function() {
        var self = this;
        var productName = self.getActiveProductAttributes().productName;
        var colorDesc = self.getActiveProductAttributes().colorDesc;
        var title = document.title;

        // Hard Coded standard folder because that is all we have to work with right now.  There is nothing that specifies what it is...
        var category = self.getActiveProductAttributes().categoryId;
        var productId = self.getActiveProductAttributes().selectionData.productId;
        var pocket = self.getActiveProductAttributes().selectionData.pocket;
        var printMethod = self.getActiveProductAttributes().selectionData.printMethod;
        var orderSamples = self.getActiveProductAttributes().orderSample == 'true' ? 'true' : '';


        if ((self.getActiveProductAttributes().selectionData.productUrl).match('\/' + websiteId + '\/control') != null && typeof self.getActiveProductAttributes().selectionData.productUrl !=' undefined') {
            history.replaceState(null, title, '/folders/control/product' +
                (typeof category != 'undefined' ? '/~category_id=' + category : '') +
                (typeof productId != 'undefined' ? '/~product_id=' + productId : '') +
                (typeof pocket != 'undefined' ? '/~pocket_type=' + pocket.replace(' ', '_') : '') +
                (typeof printMethod != 'undefined' && printMethod != '' ? '/~print_method=' + self.getAlternatePrintMethodName(printMethod, false) : '') +
                (orderSamples == 'true' ? '/~order_samples=true': '') +
                ((window.location.href).match(/(\?.*?$)/) != null ? (window.location.href).match(/(\?.*?$)/)[1] : '')
            );
        } else {
            history.replaceState(null, title, self.getActiveProductAttributes().selectionData.productUrl +
                (typeof pocket != 'undefined' ? '/' + self.urlTranslator[pocket] : '') +
                (typeof printMethod != 'undefined' && printMethod != '' ? '/' + self.urlTranslator[printMethod] : '') +
                ((window.location.href).match(/(\?.*?$)/) != null ? (window.location.href).match(/(\?.*?$)/)[1] : '')
            );
        }
    },

    getAlternatePrintMethodName: function(printMethod, corrected) {
        var namingMap = [
            {
                'PMS': 'SPOT',
                'foilStamping': 'FOIL',
                'embossing': 'EMBOSS'
            },
            {
                'SPOT': 'PMS',
                'FOIL': 'foilStamping',
                'EMBOSS': 'embossing'
            }
        ];

        return (typeof corrected == 'boolean' && typeof namingMap[(corrected ? 1 : 0)][printMethod] != 'undefined' ? namingMap[(corrected ? 1 : 0)][printMethod] : printMethod);
    },

    updateSelection: function(element, event) {
        var self = this;

        if (element == null) {
            var previousStep = self.stepList[self.stepList.length - 2];
            self.loadPreviousProductStep(previousStep);
        } else {
            var selectionName = $(element).attr('selection-name');
            var selectionValue = $(element).attr('selection-value');
            var selectionMultiSelect = $('[selection-selectlistname="' + $(element).attr('selection-target') + '"]').attr('selection-multiselect');

            if(event != null && event.isTrigger == undefined) {
                GoogleAnalytics.trackEvent('Product Page', selectionName, selectionValue);
            }

            if(selectionName == 'productId' && self.getActiveProductAttributes().selectionData.productId !== selectionValue) {
                dataLayer.push({
                    'sku': selectionValue,
                    'event': 'productupdate'
                });
            }

            if(self.getProducts()[self.activeProductIndex].getProductAttributes().selectionData.printMethod == 'foilStamping' && selectionName == 'designMethod' && selectionValue == 'onlineDesigner') {
                self.updateDoogmaByValue('showlogo', 'normal');
                self.updateDoogmaByValue('textline1', 'Your Text Here');
            } if(self.getProducts()[self.activeProductIndex].getProductAttributes().selectionData.printMethod == 'foilStamping' && selectionName == 'designMethod' && selectionValue != 'onlineDesigner') {
                self.updateDoogmaByValue('showlogo', 'folders-default');
                self.updateDoogmaByValue('textline1', '');
            }

            if (typeof selectionMultiSelect != 'undefined') {
                if ($(element).attr('selection-selected') != 'true' && $('[selection-name="' + selectionName + '"][selection-selected="true"]').length == selectionMultiSelect) {
                    return;
                    //$($('[selection-name="' + selectionName + '"][selection-selected="true"]')[selectionMultiSelect - 1]).removeAttr('selection-selected')
                }

                $(element).attr('selection-selected', ($(element).attr('selection-selected') == 'true' ? 'false' : 'true'));

                selectionValue = '';
                $('[selection-name="' + selectionName + '"][selection-selected="true"]').each(function() {
                    selectionValue += (selectionValue != '' ? ', ' : '') + $(this).attr('selection-value');
                });
            } else {
                $('[selection-name="' + selectionName + '"]').removeAttr('selection-selected');
                $(element).attr('selection-selected', 'true');
            }

            //get product features
            if(selectionName == 'productId' && self.getActiveProductAttributes().selectionData.productId != selectionValue) {
                self.getProductDesc(selectionValue);
                self.getProductFeatures(selectionValue);
            } else if(selectionName == 'pocket') {
                self.getProductFeatures(self.getActiveProductAttributes().selectionData.productId);
            }

            if (typeof selectionName != 'undefined' && typeof selectionValue != 'undefined') {
                var productAttribute = {};
                var selectionData = {};
                selectionData[selectionName] = selectionValue;
                productAttribute['selectionData'] = selectionData;
                self.getProducts()[self.activeProductIndex].setProductAttributes(productAttribute);
            }

            if (typeof $(element).attr('data-group') != 'undefined') {
                self.getProducts()[self.activeProductIndex].setProductAttributes({'colorGroup': $(element).attr('data-group')});
            }

            if (typeof $(element).attr('data-product-name') != 'undefined') {
                self.getProducts()[self.activeProductIndex].setProductAttributes({'productName': $(element).attr('data-product-name')});
            }

            if (typeof $(element).attr('data-product-color') != 'undefined') {
                self.getProducts()[self.activeProductIndex].setProductAttributes({'colorDesc': $(element).attr('data-product-color')});
            }

            if (typeof $(element).attr('data-weight') != 'undefined') {
                self.getProducts()[self.activeProductIndex].setProductAttributes({'paperWeight': $(element).attr('data-weight')});
            }

            if (typeof $(element).attr('data-texture') != 'undefined') {
                self.getProducts()[self.activeProductIndex].setProductAttributes({'paperTexture': $(element).attr('data-texture')});
            }

            if (typeof $(element).attr('data-has-sample') != 'undefined') {
                self.getProducts()[self.activeProductIndex].setProductAttributes({'hasSample': $(element).attr('data-has-sample') == 'false' ? false : true});
            }

            if (typeof $(element).attr('data-percent-savings') != 'undefined') {
                self.getProducts()[self.activeProductIndex].setProductAttributes({'percentSavings': $(element).attr('data-percent-savings')});
            }

            if (typeof $(element).attr('data-hasCustomQty') != 'undefined') {
                self.getProducts()[self.activeProductIndex].setProductAttributes({'hasCustomQty': $(element).attr('data-hasCustomQty')});
                if(self.getActiveProductAttributes().hasCustomQty == 'false') {
                    $('#sidebar-quantityList .stickyTextureHeading .customQuantity').removeClass('hidden').addClass('hidden');
                } else {
                    $('#sidebar-quantityList .stickyTextureHeading .customQuantity').removeClass('hidden');
                }
            }

            var htmlContent = $(element).clone();
            htmlContent.find('[selection-removeonselect]').remove();

            if (typeof selectionMultiSelect != 'undefined') {
                htmlContent.find('[bns-multiselectvaluelocation]').html(selectionValue.replace(',', ', '));
            }

            if (selectionName == 'quoteOutsideColor' || selectionName == 'quoteInsideColor') {
                $('[selection-name="materialDescription"]').removeClass('hidden');
                if ((typeof self.getActiveProductAttributes().selectionData.quoteOutsideColor != 'undefined' && self.getActiveProductAttributes().selectionData.quoteOutsideColor == 'Full Color Printing') || (typeof self.getActiveProductAttributes().selectionData.quoteInsideColor != 'undefined' && self.getActiveProductAttributes().selectionData.quoteInsideColor == 'Full Color Printing')) {
                    $('[selection-name="materialDescription"]').addClass('hidden');
                    var fourColorPrintingList = ['Vellum Vanilla 80lb.', 'Fiber Natural 80lb.', 'Linen Natural 100lb.', 'Smooth Eco Ivory 80lb.', 'Felt Pumice 80lb.', 'Semi-Gloss White 12 PT', 'Semi-Gloss White 14 PT', 'Semi-Gloss White 16 PT', 'Semi-Gloss White 18 PT', 'Smooth White 80lb.', 'Smooth White 100lb.', 'Gloss White 120lb.', 'Dull White 130lb.', 'Cast Coated White 12 PT', 'Marble Crush White 10 PT', 'Bright White 80lb. Felt', 'Fiber White 80lb.', 'Felt Warm White 80lb. ', 'Linen White 100lb. ', 'Hopsack White 90lb.', 'Cordwain White 90lb. ', 'Smooth Eco White 80lb.', 'White Eggshell 80lb'];
                    for (var i = 0; i < fourColorPrintingList.length; i++) {
                        $('[selection-name="materialDescription"][selection-value^="' + fourColorPrintingList[i] + '"]').removeClass('hidden');
                        if (i == 0) {
                            $('[selection-name="materialDescription"][selection-value="Semi-Gloss White 12 PT C1S"]').trigger('click');
                            //$('[selection-name="materialDescription"][selection-value="' + fourColorPrintingList[i] + '"]').trigger('click');
                        }
                    }
                }
            }

            $('[selection-selectlistname="' + $(element).attr('selection-target') + '"]').html(htmlContent.html()).append($('.' + $(element).attr('selection-target')).find('.slDownArrow')).attr('selected-userselection', 'true');
            // Update Color in Header on Change
            if($(element).attr('selection-target') == 'colorSelection') {
                $('[bns-headercolorname]').html((self.getProducts()[productPage.activeProductIndex].getProductAttributes().colorDesc) + ' ' + (self.getProducts()[productPage.activeProductIndex].getProductAttributes().paperWeight) + ($(element).attr('data-new') == 'true' ? ' <span class="newArrivalRibbon">NEW!</span>' : ''));
            }

            switch (selectionName) {
                case 'editLayout':
                    if (selectionValue.match('w/ Text') == null) {
                        $('[bns-edittextbutton]').parent().removeClass('hidden').addClass('hidden').siblings('.foldersColumn').removeClass('large6').removeClass('large12').addClass('large12');
                    } else {
                        $('[bns-edittextbutton]').parent().removeClass('hidden').siblings('.foldersColumn').removeClass('large12').removeClass('large6').addClass('large6');
                    }

                    self.updateDoogmaByValue('textline1', 'Your Text Here');
                    self.updateDoogmaByValue('textline1x', 298);
                    
                    if (selectionValue == 'Top Center 15sq' || selectionValue == 'Top Center 15sq w/ Text') {
                        self.updateDoogmaByValue('chooseartlocation', '15squareincharttopcenter');
                        self.getProducts()[self.activeProductIndex].setProductAttributes({'imageSize': '15'});
                        if (self.getActiveProductAttributes().parentProductId == 'LF_118_C') {
                            self.updateDoogmaByValue('textline1y', 210);
                        } else if (self.getActiveProductAttributes().parentProductId == 'MF-4801_C' || self.getActiveProductAttributes().parentProductId == 'MF-135_C' || self.getActiveProductAttributes().parentProductId == 'MF-4804_C') {
                            self.updateDoogmaByValue('textline1y', 225);
                        } else if (self.getActiveProductAttributes().parentProductId == 'HF-6851_C') {
                            self.updateDoogmaByValue('textline1y', 188);
                        } else if (self.getActiveProductAttributes().parentProductId == 'CKH-2704_C') {
                            self.updateDoogmaByValue('textline1y', 198);
                        } else {
                            self.updateDoogmaByValue('textline1y', 148);
                        }
                    } else if (selectionValue == 'Center 15sq' || selectionValue == 'Center 15sq w/ Text') {
                        self.updateDoogmaByValue('chooseartlocation', '15squareinchartcenter');
                        self.getProducts()[self.activeProductIndex].setProductAttributes({'imageSize': '15'});
                        if (self.getActiveProductAttributes().parentProductId == 'LF_118_C') {
                            self.updateDoogmaByValue('textline1y', 460);
                        } else if (self.getActiveProductAttributes().parentProductId == 'MF-4801_C' || self.getActiveProductAttributes().parentProductId == 'MF-135_C' || self.getActiveProductAttributes().parentProductId == 'MF-4804_C') {
                            self.updateDoogmaByValue('textline1y', 460);
                        } else if (self.getActiveProductAttributes().parentProductId == 'HF-6851_C') {
                            self.updateDoogmaByValue('textline1y', 260);
                        } else if (self.getActiveProductAttributes().parentProductId == 'CKH-2704_C') {
                            self.updateDoogmaByValue('textline1y', 225);
                        } else {
                            self.updateDoogmaByValue('textline1y', 317);
                        }
                    } else if (selectionValue == 'Bottom Center 15sq' || selectionValue == 'Bottom Center 15sq w/ Text') {
                        self.updateDoogmaByValue('chooseartlocation', '15squareinchartbottomcenter');
                        self.getProducts()[self.activeProductIndex].setProductAttributes({'imageSize': '15'});
                        if (self.getActiveProductAttributes().parentProductId == 'LF_118_C') {
                            self.updateDoogmaByValue('textline1y', 726);
                        } else if (self.getActiveProductAttributes().parentProductId == 'MF-4801_C' || self.getActiveProductAttributes().parentProductId == 'MF-135_C' || self.getActiveProductAttributes().parentProductId == 'MF-4804_C') {
                            self.updateDoogmaByValue('textline1y', 675);
                        } else if (self.getActiveProductAttributes().parentProductId == 'HF-6851_C') {
                            self.updateDoogmaByValue('textline1y', 327);
                        } else if (self.getActiveProductAttributes().parentProductId == 'CKH-2704_C') {
                            self.updateDoogmaByValue('textline1y', 247);
                        } else {
                            self.updateDoogmaByValue('textline1y', 486);
                        }
                    } else if (selectionValue == 'Top Center 36sq' || selectionValue == 'Top Center 36sq w/ Text') {
                        self.updateDoogmaByValue('chooseartlocation', '36squareincharttopcenter');
                        self.getProducts()[self.activeProductIndex].setProductAttributes({'imageSize': '36'});
                        switch (self.getActiveProductAttributes().parentProductId) {
                            case 'LF_118_C':
                                self.updateDoogmaByValue('textline1y', 312);
                                break;
                            default:
                                self.updateDoogmaByValue('textline1y', 250);
                                break;
                        }
                    } else if (selectionValue == 'Center 36sq' || selectionValue == 'Center 36sq w/ Text') {
                        self.updateDoogmaByValue('chooseartlocation', '36squareinchartcenter');
                        self.getProducts()[self.activeProductIndex].setProductAttributes({'imageSize': '36'});
                        switch (self.getActiveProductAttributes().parentProductId) {
                            case 'LF_118_C':
                                self.updateDoogmaByValue('textline1y', 512);
                                break;
                            default:
                                self.updateDoogmaByValue('textline1y', 361);
                                break;
                        }
                    } else if (selectionValue == 'Bottom Center 36sq' || selectionValue == 'Bottom Center 36sq w/ Text') {
                        self.updateDoogmaByValue('chooseartlocation', '36squareinchartbottomcenter');
                        self.getProducts()[self.activeProductIndex].setProductAttributes({'imageSize': '36'});
                        switch (self.getActiveProductAttributes().parentProductId) {
                            case 'LF_118_C':
                                self.updateDoogmaByValue('textline1y', 728);
                                break;
                            default:
                                self.updateDoogmaByValue('textline1y', 486);
                                break;
                        }
                    }
                    break;
                case 'productId':
                    var imprintMethods = element.attr('imprintmethods').split('|');

                    $('[selection-name="printMethod"]').removeClass('hiddenSoft').addClass('hiddenSoft').removeAttr('bns-selection').off('click');

                    for (var x = 0; x < imprintMethods.length; x++) {
                        $('[selection-name="printMethod"][bns-imprintname="' + imprintMethods[x] + '"]').removeClass('hiddenSoft').attr('bns-selection', '');
                    }

                    self.bindSelection();

                    if ($('[selection-name="printMethod"][selection-value="' + self.getActiveProductAttributes().selectionData.printMethod + '"]').hasClass('hidden') || $('[selection-name="printMethod"][selection-value="' + self.getActiveProductAttributes().selectionData.printMethod + '"]').hasClass('hiddenSoft')) {
                        $('[bns-selection][selection-name="printMethod"]:not(.hidden):first').trigger('click');
                    }

                    // Load Reviews
                    $.ajax({
                        type: 'POST',
                        url: '/folders/control/reviews',
                        data: {'productId': selectionValue},
                        dataType: 'html',
                        async: false
                    }).done(function(data) {
                        $('.jqs-productReviewsContent').html(data);
                    });

                    self.updateAssets();

                    $('[bns-assetlist] [bns-selection]:first-child').trigger('click');
                    $('[selection-name="coatingSide1"]').removeAttr('selection-selected');

                    if ($('[selection-name="productId"][selection-selected="true"]').attr('data-coating') != '') {
                        var numberOfSides = $('[selection-name="productId"][selection-selected="true"]').attr('data-coating');
                        self.getProducts()[self.activeProductIndex].setProductAttributes({'selectionData': {'numberOfCoatings': numberOfSides}});
                        $('[bns-coatingselection]').removeClass('hidden');

                        if (productPage.getProducts()[productPage.activeProductIndex].getProductAttributes().selectionData.coatingSide1 == 'None' || (typeof numberOfSides != 'undefined' && numberOfSides == 2 && productPage.getProducts()[productPage.activeProductIndex].getProductAttributes().selectionData.coatingSide2 == 'None') || (productPage.getProducts()[productPage.activeProductIndex].getProductAttributes().selectionData.coatingSide2 != 'None' && numberOfSides == 1)) {
                            $('[selection-name="coatingSide1"]:not(.hidden):first-child').trigger('click');
                        }
                    } else {
                        $('[bns-coatingselection]').removeClass('hidden').addClass('hidden');
                        self.getProducts()[self.activeProductIndex].setProductAttributes({'selectionData': {'numberOfCoatings': 0}});
                        self.getProducts()[self.activeProductIndex].setProductAttributes({'selectionData': {'coatingSide1': 'None', 'coatingSide2': 'None'}});
                    }

                    self.getProducts()[self.activeProductIndex].setProductAttributes({'selectionData': {'productUrl': $('[selection-name="productId"][selection-selected="true"]').attr('data-url')}});
                    if ($('[bns-selectedcolortext] [bns-colorselecttext]').find('img').length > 0) {
                        self.selectionFontResize($('[bns-selectedcolortext] .productColorSelection'), 74);
                    } else {
                        self.selectionFontResize($('[bns-selectedcolortext] .productColorSelection'), 57);
                    }

                    if (typeof window.location.pathname != 'undefined' && typeof event.isTrigger == 'undefined') {
                        ga('set', 'page', window.location.pathname);
                        ga('send', 'pageview');
                    }
                    break;
                case 'printMethod':
                    $('[bns-hideFoilColorsContent]').attr('bns-hideFoilColorsContent', 'false');
                    $('[bns-hideCoatingsContent]').attr('bns-hideCoatingsContent', 'false');
                    $('[bns-spotColors]').removeClass('hidden').addClass('hidden');

                    if (selectionValue == 'PMS') {
                        $('[bns-spotColors]').removeClass('hidden');
                    } else if (selectionValue == 'foilStamping') {
                        $('[bns-hideCoatingsContent]').attr('bns-hideCoatingsContent', 'true');
                        $('[data-doogma][selection-name="foilColor"][selection-selected="true"]').trigger('click.doogma');
                    }

                    if (selectionValue != 'foilStamping') {
                        self.getProducts()[self.activeProductIndex].setProductAttributes({'selectionData': {'designMethod': 'uploadDesign'}});
                        $('[bns-hideFoilColorsContent]').attr('bns-hideFoilColorsContent', 'true');
                    }
                    self.updateMode();

                    break;
                case 'pocket':
                    break;
                case 'cardSlits':
                    //$('[bns-productassetinside]').trigger('click');
                    break;
                case 'coatingSide1':
                    var numberOfSides = $('[selection-name="productId"][selection-selected="true"]').attr('data-coating');
                    if (numberOfSides != null && numberOfSides == 2) {
                        self.getProducts()[self.activeProductIndex].setProductAttributes({'selectionData': {'coatingSide2': selectionValue}});
                    } else {
                        self.getProducts()[self.activeProductIndex].setProductAttributes({'selectionData': {'coatingSide2': 'None'}});
                    }
                    break;
                case 'assetName':
                    for (var key in self.getActiveProductAttributes().selectionData.designerValues) {
                        self.getActiveProductAttributes().selectionData.designerValues[key].productview = $(element).find('img').attr('data-doogma-value');
                    }
                    self.updateDoogmaSettings(false);
                    break;
            }

            // TODO: Switch these to selectionName from target...
            switch ($(element).attr('selection-target')) {
                case 'designMethodSelection':
                    if (selectionValue == 'onlineDesigner') {
                        this.updatePageContent({
                            folderDesign: {
                                onlineDesigner: true,
                                uploadDesign: false,
                                reuseArtwork: false,
                                designServices: false,
                                emailArtworkLater: false
                            }
                        });
                    } else if (selectionValue == 'uploadDesign') {
                        this.updatePageContent({
                            folderDesign: {
                                onlineDesigner: false,
                                uploadDesign: true,
                                reuseArtwork: false,
                                designServices: false,
                                emailArtworkLater: false
                            }
                        });
                    } else if (selectionValue == 'reuseArtwork') {
                        this.updatePageContent({
                            folderDesign: {
                                onlineDesigner: false,
                                uploadDesign: false,
                                reuseArtwork: true,
                                designServices: false,
                                emailArtworkLater: false
                            }
                        });
                    } else if (selectionValue == 'designServices') {
                        this.updatePageContent({
                            folderDesign: {
                                onlineDesigner: false,
                                uploadDesign: false,
                                reuseArtwork: false,
                                designServices: true,
                                emailArtworkLater: false
                            }
                        });
                    } else if (selectionValue == 'emailArtworkLater') {
                        this.updatePageContent({
                            folderDesign: {
                                onlineDesigner: false,
                                uploadDesign: false,
                                reuseArtwork: false,
                                designServices: false,
                                emailArtworkLater: true
                            }
                        });
                    }

                    this.updatePageContent(self.activePageContent);
                    break;
                case 'pocketSelection':
                    $.ajax({
                        type: 'GET',
                        url: '/' + websiteId + '/control/cardSlits',
                        data: {
                            'product_id': productPage.getProducts()[productPage.activeProductIndex].getProductAttributes().selectionData.productId,
                            'pocketStylesFeatureId': selectionValue,
                            'cardSlitsDescription': (typeof self.getActiveProductAttributes().selectionData.cardSlits != 'undefined' ? self.getActiveProductAttributes().selectionData.cardSlits : ''),
                            'pocketDescription': (typeof self.getActiveProductAttributes().selectionData.pocket != 'undefined' ? self.getActiveProductAttributes().selectionData.pocket : '')
                        },
                        dataType: 'html',
                        cache: true,
                        async: false
                    }).done(function (data) {
                        $('[bns-styleParent="cardSlits"]').html(data);

                        var productAttribute = {};
                        var selectionData = {};
                        selectionData['cardSlits'] = $('.slSelected[selection-name="cardSlits"]').attr('selection-value');
                        productAttribute['selectionData'] = selectionData;
                        self.getProducts()[self.activeProductIndex].setProductAttributes(productAttribute);

                        $('[bns-cardslitimage]').each(function () {
                            $(this).attr('src', $(this).attr('src').replace(/CS-POCKET_\d+/, 'CS-POCKET_' + self.getActiveProductAttributes().selectionData.pocket));
                        });

                        self.updateAssets();
                        self.initPageActions();
                        self.rebuildSelections();
                    });

                    break;
            }

            if (typeof $(element).attr('selection-target') != 'undefined' && $(element).attr('selection-target') != 'quantitySelection') {
                self.getProductPrice();
            }

            self.updateURL();
            self.updateHeroImage();
        }
    },

    updateAssets: function() {
        var self = this;
        var productId = (self.getActiveProductAttributes().selectionData.productId).toUpperCase();

        $('[bns-assetlist] > *').remove();

        if (self.getActiveProductAttributes().initialProductStep == 'plain' || self.getActiveProductAttributes().initialProductStep == 'getQuoteForm') {
            $.ajax({
                type: 'GET',
                url: '/' + websiteId + '/control/getProductAssets',
                data: {productId: self.getProducts()[self.activeProductIndex].getProductAttributes().selectionData.productId},
                dataType: 'json',
                cache: true,
                async: false
            }).done(function (data) {
                //show
                if (data.success && typeof data.productAssets !== 'undefined') {
                    var hasDefault = 'n';

                    for (var i = 0; i < data.productAssets.length; i++) {
                        if (typeof data.productAssets[i].assetDefault !== 'undefined' && data.productAssets[i].assetDefault == 'Y') {
                            hasDefault = 'y';
                        }
                    }

                    $('[bns-assetlist]').empty();
                    $('[bns-assetlist]').append(
                        $('<div />').attr({
                            'bns-selection': '',
                            'selection-autoupdate': 'false',
                            'selection-value': self.getProducts()[productPage.activeProductIndex].getProductAttributes().selectionData.productId,
                            'selection-name': 'assetName'
                        }).addClass('textCenter marginBottom10 cursorPointer').append(
                            $('<img />').addClass('padding10').attr('src', 'https://actionenvelope.scene7.com/is/image/ActionEnvelope/' + self.getProducts()[productPage.activeProductIndex].getProductAttributes().selectionData.productId + '?ts=20170505&fmt=png-alpha&wid=48')
                        )
                    );

                    for (var i = 0; i < data.productAssets.length; i++) {
                        $('[bns-assetlist]').append(
                            $('<div />').attr({
                                'bns-selection': '',
                                'selection-autoupdate': 'false',
                                'selection-value': data.productAssets[i].assetName,
                                'selection-name': 'assetName'
                            }).addClass('textCenter marginBottom10 cursorPointer').append(
                                $('<img />').addClass('padding10').attr('src', 'https://actionenvelope.scene7.com/is/image/ActionEnvelope/' + data.productAssets[i].assetName + '?ts=20170505&fmt=png-alpha&wid=48')
                            )
                        );
                    }
                }
            });
        } else {
            var pocket = (self.getActiveProductAttributes().selectionData.pocket).toUpperCase().replace(/[^A-Za-z0-9]/, '');

            // Front
            $('[bns-assetlist]').append(
                $('<div />').attr({
                    'bns-selection': '',
                    'selection-autoupdate': 'false',
                    'selection-value': productId + '_' + pocket + '_FRONT',
                    'selection-name': 'assetName',
                }).addClass('textCenter marginBottom10 cursorPointer').append(
                    $('<img />').attr({
                        'data-doogma': '',
                        'data-doogma-key': 'productview',
                        'data-doogma-value': 'front',
                        'property': 'image',
                        'src': 'https://actionenvelope.scene7.com/is/image/ActionEnvelope/' + productId + '_' + pocket + '_FRONT?ts=20170505&fmt=png-alpha&wid=100',
                    })
                ).append(
                    $('<p />').html('Front')
                )
            );

            // Perspective
            $('[bns-assetlist]').append(
                $('<div />').attr({
                    'bns-selection': '',
                    'selection-autoupdate': 'false',
                    'selection-value': productId + '_' + pocket,
                    'selection-name': 'assetName',
                }).addClass('textCenter marginBottom10 cursorPointer').append(
                    $('<img />').attr({
                        'bns-productperspective': '',
                        'data-doogma': '',
                        'data-doogma-key': 'productview',
                        'data-doogma-value': 'perspective',
                        'property': 'image',
                        'src': 'https://actionenvelope.scene7.com/is/image/ActionEnvelope/' + productId + '_' + pocket + '?ts=20170505&fmt=png-alpha&wid=100',
                    })
                ).append(
                    $('<p />').html('Perspective')
                )
            );

            // Open
            $('[bns-assetlist]').append(
                $('<div />').attr({
                    'bns-selection': '',
                    'selection-autoupdate': 'false',
                    'selection-value': productId + '_' + pocket + '_OPEN',
                    'selection-name': 'assetName',
                }).addClass('textCenter marginBottom10 cursorPointer').append(
                    $('<img />').attr({
                        'bns-productassetinside': '',
                        'data-doogma': '',
                        'data-doogma-key': 'productview',
                        'data-doogma-value': 'inside',
                        'property': 'image',
                        'src': 'https://actionenvelope.scene7.com/is/image/ActionEnvelope/' + productId + '_' + pocket + '_OPEN?ts=20170505&fmt=png-alpha&wid=100',
                    })
                ).append(
                    $('<p />').html('Open')
                )
            );

            // Back
            $('[bns-assetlist]').append(
                $('<div />').attr({
                    'bns-selection': '',
                    'selection-autoupdate': 'false',
                    'selection-value': productId + '_' + pocket + '_BACK',
                    'selection-name': 'assetName',
                }).addClass('textCenter marginBottom10 cursorPointer').append(
                    $('<img />').attr({
                        'data-doogma': '',
                        'data-doogma-key': 'productview',
                        'data-doogma-value': 'back',
                        'property': 'image',
                        'src': 'https://actionenvelope.scene7.com/is/image/ActionEnvelope/' + productId + '_' + pocket + '_BACK?ts=20170505&fmt=png-alpha&wid=100',
                    })
                ).append(
                    $('<p />').html('Back')
                )
            );
        }/* else if (self.getActiveProductAttributes().initialProductStep == 'getQuoteForm') {

            $('[bns-assetlist]').append(
                $('<div />').attr({
                    'bns-selection': '',
                    'selection-autoupdate': 'false',
                    'selection-value': productId,
                    'selection-name': 'assetName',
                }).addClass('textCenter marginBottom10 cursorPointer').append(
                    $('<img />').attr({
                        'src': 'https://actionenvelope.scene7.com/is/image/ActionEnvelope/' + productId + '_LS?ts=20170505&fmt=png-alpha&wid=100',
                    }).addClass('padding10')
                )
            );

            $('[bns-assetlist]').append(
                $('<div />').attr({
                    'bns-selection': '',
                    'selection-autoupdate': 'false',
                    'selection-value': productId,
                    'selection-name': 'assetName',
                }).addClass('textCenter marginBottom10 cursorPointer').append(
                    $('<img />').attr({
                        'src': 'https://actionenvelope.scene7.com/is/image/ActionEnvelope/' + productId + '?ts=20170505&fmt=png-alpha&wid=100',
                    }).addClass('padding10')
                )
            );
        }
         */

        self.initPageActions();
    },

    updateHeroImage: function() {
        var self = this;
        var assetName = (self.getActiveProductAttributes().selectionData.assetName);
        var imageParams = $('[bns-heroimage]').attr('src').match(/.*?(\?.*?)$/);

        $('[bns-heroimage], .jqs-enhancedImage').attr('src', 'https://actionenvelope.scene7.com/is/image/ActionEnvelope/' + assetName + (imageParams != null && imageParams.length >= 2 ? imageParams[1] : '?fmt=png-alpha'));
    },

    loadReuseData: function() {
        var self = this;

        function doLoadReuseDataLogin() {
            // $('[bns-previousartworkcontainer]').append(
            //     $('<div />').addClass('jqs-login-button foldersButton buttonDarkGray noMargin').attr('data-bnreveal', 'secure-layer').attr('data-dest', 'login-layer').html('Click Here to Log In').off('click.reuseData').on('click.reuseData', function() {
            //         $('.jqs-hidden-login-button').trigger('click', self.loadReuseData.bind(self));
            //     })
            // );

            // bnRevealInit($('[bns-previousartworkcontainer] [data-bnReveal]'));
            $('.selectListItem[selection-value="reuseArtwork"]').off('click').on('click', function() {
                if (!(typeof $.cookie('__ES_ll') !== 'undefined' && $.cookie('__ES_ll') != 'false')) {
                    $('.jqs-hidden-login-button').trigger('click', self.loadReuseData.bind(self));
                }
            });
        }

        $('[bns-previousartworkcontainer]').html();
        if(typeof $.cookie('__ES_ll') !== 'undefined' && $.cookie('__ES_ll') != 'false') {
            $.ajax({
                type: 'GET',
                url: '/' + websiteId + '/control/getOrderAndContent',
                data: {
                    contentEnumTypeId: 'OIACPRP_PDF'
                },
                dataType: 'json',
                cache: true
            }).done(function(data) {
                if(data.success && typeof data.orderAndContent !== 'undefined') {
                    $.each(data.orderAndContent, function(i, v) {
                        $.each(v.orderItemContents, function(i2, v2) {
                            $('[bns-previousartworkcontainer]').append(
                                $('<div />').addClass('selectListItem').attr('bns-selection', '').attr('selection-name', 'reuseArtwork').attr('selection-value', v2.contentName).attr('bns-previousartworkselection','').attr('data-uploadpath', v2.contentPath).append(
                                    $('<div />').addClass('foldersTabularRow folderDisplayTable').append(
                                        $('<div />').addClass('textCenter paddingLeft5').append(
                                            $('<span />').addClass('selectCheckbox')
                                        )
                                    ).append(
                                        $('<div />').append(
                                            $('<p />').html(((v2.itemJobName != null) ? v2.itemJobName : v2.itemDescription) + '<br />' + v2.contentName)
                                        )
                                    )
                                )
                            )
                        });
                    });
                    self.initPageActions();
                    self.rebuildSelections();
                    $('[bns-previousartworkcontainer] .jqs-login-button').addClass('hidden');
                } else {
                    doLoadReuseDataLogin();
                }
            });
        } else {
            doLoadReuseDataLogin();
        }
    },

    getProductPrice: function() {
        var self = this;

        var priceAttr = {};
        if (self.getActiveProductAttributes().stepName != 'getQuoteForm') {
            waitForFinalEvent(function() {
                //blank pricing
                // Look to minimize code here, reuse it like a boss.
                if(typeof self.getActiveProductAttributes().selectionData.printMethod == 'undefined'/* || self.getActiveProductAttributes().selectionData.printMethodCustomization == 'getQuoteForm'*/) {
                    priceAttr = self.buildPriceAttribute(false, null);
                    $.ajax({
                        type: 'GET',
                        url: '/' + websiteId + '/control/getProductPrice',
                        data: priceAttr,
                        dataType: 'json',
                        cache: true,
                        async: true
                    }).done(function (data) {
                        if (data.success && typeof data.priceList !== 'undefined') {
                            var first = 0;
                            var currentQuantitySelected = self.getProducts()[productPage.activeProductIndex].getProductAttributes().selectionData.quantity;

                            $('#quantityPriceSelection').empty();
                            $('[bns-quantitylist]').empty();

                            if (parseInt(self.getActiveProductAttributes().percentSavings) > 0) {
                                $.ajax({
                                    type: "GET",
                                    url: '/' + websiteId + '/control/getOriginalPrice',
                                    data: priceAttr,
                                    dataType: 'json',
                                    cache: true,
                                    async: false
                                }).done(function(originalPriceDataReturn) {
                                    if (originalPriceDataReturn.success && typeof originalPriceDataReturn.priceList !== 'undefined') {
                                        originalPriceData = originalPriceDataReturn;
                                    }
                                });
                            }

                            function doListAppend(key, price, first) {
                                if (parseInt(self.getActiveProductAttributes().percentSavings) > 0 && typeof originalPriceData.priceList !== 'undefined') {
                                    $('[bns-quantitylist]').append(
                                        $('<div />').addClass('selectListItem selectList qpsListItems').attr('selection-selected', ((typeof currentQuantitySelected != 'undefined' && currentQuantitySelected == key) || (typeof currentQuantitySelected == 'undefined' && first == 0) ? 'true' : 'false')).attr('bns-selection', '').attr('selection-target', 'quantitySelection').attr('selection-name', 'quantity').attr('selection-value', key).append(
                                            $('<div />').addClass('foldersTabularRow').append(
                                                $('<div />').append(
                                                    $('<span />').attr('selection-removeonselect', '').addClass('selectCheckbox')
                                                ).append(
                                                    $('<span />').addClass('selQty').html(numberWithCommas(key) + ' Qty')
                                                ).append(
                                                    $('<span />').attr('selection-removeonselect', '').addClass('selPrice').append(
                                                        $('<strike />').addClass('').html(formatCurrency(originalPriceData.priceList[key].price))
                                                    )
                                                ).append(
                                                    $('<span />').addClass('selPrice').append(
                                                        $('<strong />').addClass('priceDisplay').html(formatCurrency(price))
                                                    )
                                                ).append(
                                                    $('<span />').addClass('textRight percentOffText pullRight').html('Save ' + parseInt(self.getActiveProductAttributes().percentSavings) + '%')
                                                )
                                            ).append(
                                                $('<div />').attr('selection-removeonselect', '').attr('bns-freeshippingtext', '').addClass(data.priceList[key].price >= freeShippingAmount ? '' : 'hidden').html('Free Shipping!')
                                            )
                                        )
                                    )
                                } else {
                                    $('[bns-quantitylist]').append(
                                        $('<div />').addClass('selectListItem selectList qpsListItems').attr('selection-selected', ((typeof currentQuantitySelected != 'undefined' && currentQuantitySelected == key) || (typeof currentQuantitySelected == 'undefined' && first == 0) ? 'true' : 'false')).attr('bns-selection', '').attr('selection-target', 'quantitySelection').attr('selection-name', 'quantity').attr('selection-value', key).append(
                                            $('<div />').addClass('foldersTabularRow').append(
                                                $('<div />').append(
                                                    $('<span />').attr('selection-removeonselect', '').addClass('selectCheckbox')
                                                ).append(
                                                    $('<span />').addClass('selQty').html(numberWithCommas(key) + ' Qty')
                                                ).append(
                                                    $('<span />').addClass('selPrice').append(
                                                        $('<strong />').addClass('priceDisplay').html(formatCurrency(price))
                                                    ).append(
                                                        $('<div />').addClass('priceForEach').attr('selection-removeonselect','').html(formatCurrency(data.priceList[key].price/key) + '/each')
                                                    )
                                                )
                                            ).append(
                                                $('<div />').attr('selection-removeonselect', '').attr('bns-freeshippingtext', '').addClass(self.getActiveProductAttributes().initialProductStep === "plain" && freeShippingAmount > data.priceList[key].price ? 'textRight hidden' : 'textRight').attr('bns-freeshippingtext', '').html('Free Shipping!')
                                            )
                                        )
                                    )
                                }
                            }

                            Object.keys(data.priceList).forEach(function(key, index) {
                                if ((typeof currentQuantitySelected != 'undefined' && currentQuantitySelected == key) || (typeof currentQuantitySelected == 'undefined' && first == 0)) {
                                    if (parseInt(self.getActiveProductAttributes().percentSavings) > 0 && typeof originalPriceData.priceList !== 'undefined') {
                                        $('#quantityPriceSelection').append(
                                            $('<div />').addClass('foldersTabularRow').append(
                                                $('<div />').append(
                                                    $('<span />').addClass('selQty').html(numberWithCommas(key) + ' Qty')
                                                ).append(
                                                    $('<span />').addClass('selPrice').append(
                                                        $('<strong />').addClass('priceDisplay').html(formatCurrency(data.priceList[key].price))
                                                    )
                                                ).append(
                                                    $('<span />').addClass('textRight percentOffText pullRight').html('Save ' + parseInt(self.getActiveProductAttributes().percentSavings) + '%')
                                                )
                                            )
                                        );
                                    } else {
                                        $('#quantityPriceSelection').append(
                                            $('<div />').addClass('foldersTabularRow').append(
                                                $('<div />').append(
                                                    $('<span />').addClass('selQty').html(numberWithCommas(key) + ' Qty')
                                                ).append(
                                                    $('<span />').addClass('selPrice').append(
                                                        $('<strong />').addClass('priceDisplay').html(formatCurrency(data.priceList[key].price))
                                                    )
                                                )
                                            )
                                        );
                                    }
                                }

                                if((parseInt(key) < self.getActiveProductAttributes().minimumQuantity && self.getActiveProductAttributes().minimumQuantity != null) || self.getActiveProductAttributes().minimumQuantity == null) {
                                    self.getProducts()[self.activeProductIndex].setProductAttributes({'minimumQuantity': parseInt(key)});
                                }

                                doListAppend(key, data.priceList[key].price, first);
                                first = 1;
                            });

                            var plainOrderSamples = $('<div />').addClass('selectListItem selectList qpsListItems').attr('selection-selected', 'false').attr('bns-selection', '').attr('selection-target', 'quantitySelection').attr('selection-name', 'quantity').attr('selection-value', '1').append(
                                $('<div />').addClass('foldersTabularRow').append(
                                    $('<div />').append(
                                        $('<span />').attr('selection-removeonselect', '').addClass('selectCheckbox')
                                    ).append(
                                        $('<span />').addClass('selQty').html('1 Qty')
                                    ).append(
                                        $('<span />').addClass('selPrice').append(
                                            $('<strong />').addClass('priceDisplay').html(productPage.getActiveProductAttributes().type == "FOLDER" ? '$3.00' : '$1.00')
                                        )
                                    )
                                ).append(
                                    $('<div />').addClass('textRight').attr('bns-freeshippingtext', '').html('Free Shipping!')
                                )
                            );

                            productPage.getActiveProductAttributes().hasSample == true ? $(plainOrderSamples).insertBefore($('#sidebar-quantityList .selectListItem:first-child')) : "";

                            if (recentlyViewedCounter = 1) {
                                productPage.storeRecentlyViewed();
                                recentlyViewedCounter = 0;
                            }

                            self.initPageActions();

                            if ($('[bns-quantitylist] [selection-selected="true"]').length == 0 && $('[bns-quantitylist] [selection-selected="false"]').length > 0) {
                                $($('[bns-quantitylist] [selection-selected="false"]')[0]).trigger('click');
                            }
                        }
                    });
                } else { //printed pricing
                    //get the vendor sku first
                    $.ajax({
                        type: 'GET',
                        url: '/' + websiteId + '/control/getVendorProductId',
                        data: {
                            'productId': self.getActiveProductAttributes().parentProductId,
                            'vendorPartyId': 'V_ADMORE',
                            'description': self.getActiveProductAttributes().selectionData.pocket
                        },
                        dataType: 'json',
                        cache: true,
                        async: true
                    }).done(function (data) {
                        if (data.vendorProductId != null) {
                            priceAttr = self.buildPriceAttribute(true, data.vendorProductId);
                            self.getProducts()[self.activeProductIndex].setProductAttributes({'selectionData': {'vendorProductId': data.vendorProductId}});
                            $('[bns-templateimage]').attr('src', '//actionenvelope.scene7.com/is/image/ActionEnvelope/' + data.vendorProductId + ($('[bns-templateimage]').attr('src').match(/.*?(\?.*?)$/) != null ? $('[bns-templateimage]').attr('src').match(/.*?(\?.*?)$/)[1] : ''));
                            $('[bns-templatedownload]').attr('href', '/html/files/folders/templates/' + data.vendorProductId + '.zip');

                            $.ajax({
                                type: 'POST',
                                url: '/' + websiteId + '/control/getProductEnginePrice',
                                data: {
                                    'pricingRequest': JSON.stringify(priceAttr)
                                },
                                dataType: 'json',
                                cache: true,
                                async: true
                            }).done(function (data) {
                                if (data.success) {
                                    var first = 0;
                                    var currentQuantitySelected = self.getProducts()[productPage.activeProductIndex].getProductAttributes().selectionData.quantity;

                                    $('#quantityPriceSelection').empty();
                                    $('[bns-quantitylist]').empty();

                                    function doListAppend(key, data, first) {
                                        if (parseInt(self.getActiveProductAttributes().percentSavings) > 0 && typeof originalPriceData.priceList !== 'undefined') {
                                            $('[bns-quantitylist]').append(
                                                $('<div />').addClass('selectListItem selectList qpsListItems').attr('selection-selected', ((typeof currentQuantitySelected != 'undefined' && currentQuantitySelected == key) || (typeof currentQuantitySelected == 'undefined' && first == 0) ? 'true' : 'false')).attr('bns-selection', '').attr('selection-target', 'quantitySelection').attr('selection-name', 'quantity').attr('selection-value', key).append(
                                                    $('<div />').addClass('foldersTabularRow').append(
                                                        $('<div />').append(
                                                            $('<span />').attr('selection-removeonselect', '').addClass('selectCheckbox')
                                                        ).append(
                                                            $('<span />').addClass('selQty').html(numberWithCommas(key) + ' Qty')
                                                        ).append(
                                                            $('<span />').addClass('selPrice').append(
                                                                $('<strike />').addClass('').html(formatCurrency(originalPriceData.priceList[key].price))
                                                            )
                                                        ).append(
                                                            $('<span />').addClass('selPrice').append(
                                                                $('<strong />').addClass('priceDisplay').html(formatCurrency(data[key]))
                                                            )
                                                        ).append(
                                                            $('<span />').addClass('textRight percentOffText pullRight').html('Save ' + parseInt(self.getActiveProductAttributes().percentSavings) + '%')
                                                        )
                                                    ).append(
                                                        $('<div />').attr('selection-removeonselect', '').attr('bns-freeshippingtext', '').addClass(data[key] >= freeShippingAmount ? '' : 'hidden').html('Free Shipping!')
                                                    )
                                                )
                                            )
                                        } else {
                                            if (productPage.getActiveProduct().getProductAttributes().printedDiscount > 0) {
                                                $('[bns-quantitylist]').append(
                                                    $('<div />').addClass('selectListItem selectList qpsListItems').attr('selection-selected', ((typeof currentQuantitySelected != 'undefined' && currentQuantitySelected == key) || (typeof currentQuantitySelected == 'undefined' && first == 0) ? 'true' : 'false')).attr('bns-selection', '').attr('selection-target', 'quantitySelection').attr('selection-name', 'quantity').attr('selection-value', key).append(
                                                        $('<div />').addClass('foldersTabularRow').append(
                                                            $('<div />').append(
                                                                $('<span />').attr('selection-removeonselect', '').addClass('selectCheckbox')
                                                            ).append(
                                                                $('<span />').addClass('selQty').html(numberWithCommas(key) + ' Qty')
                                                            ).append(
                                                                $('<span />').attr('selection-removeonselect', '').addClass('selPrice').append(
                                                                    $('<strike />').html(formatCurrency(data[key] / (1 - productPage.getActiveProduct().getProductAttributes().printedDiscount)))
                                                                )
                                                            ).append(
                                                                $('<span />').addClass('selPrice').append(
                                                                    $('<strong />').addClass('priceDisplay').html(formatCurrency(data[key]))
                                                                )
                                                            ).append(
                                                                $('<span />').addClass('priceForEach').attr('selection-removeonselect', '').html(formatCurrency(data[key]/key) + '/each')
                                                            ).append(
                                                                $('<span />').addClass('textRight percentOffText pullRight').html('Save ' + Math.floor(productPage.getActiveProduct().getProductAttributes().printedDiscount * 100) + '%')
                                                            )
                                                        ).append(
                                                            $('<div />').attr('selection-removeonselect', '').addClass('textRight').attr('bns-freeshippingtext', '').addClass(data[key] >= freeShippingAmount ? '' : 'hidden').html('Free Shipping!')
                                                        )
                                                    )
                                                )
                                            } else {
                                                $('[bns-quantitylist]').append(
                                                    $('<div />').addClass('selectListItem selectList qpsListItems').attr('selection-selected', ((typeof currentQuantitySelected != 'undefined' && currentQuantitySelected == key) || (typeof currentQuantitySelected == 'undefined' && first == 0) ? 'true' : 'false')).attr('bns-selection', '').attr('selection-target', 'quantitySelection').attr('selection-name', 'quantity').attr('selection-value', key).append(
                                                        $('<div />').addClass('foldersTabularRow').append(
                                                            $('<div />').append(
                                                                $('<span />').attr('selection-removeonselect', '').addClass('selectCheckbox')
                                                            ).append(
                                                                $('<span />').addClass('selQty').html(numberWithCommas(key) + ' Qty')
                                                            ).append(
                                                                $('<span />').addClass('selPrice').append(
                                                                    $('<strong />').addClass('priceDisplay').html(formatCurrency(data[key]))
                                                                )
                                                            ).append(
                                                                $('<span />').addClass('priceForEach').attr('selection-removeonselect', '').html(formatCurrency(data[key]/key) + '/each')
                                                            )
                                                        ).append(
                                                            $('<div />').attr('selection-removeonselect', '').addClass('textRight').attr('bns-freeshippingtext', '').addClass(data[key] >= freeShippingAmount ? '' : 'hidden').html('Free Shipping!')
                                                        )
                                                    )
                                                )
                                            }
                                        }
                                    }

                                    for (var key in data.output.VENDORS_SIMPLE_PRICING.V_ADMORE) {
                                        if ((typeof currentQuantitySelected != 'undefined' && currentQuantitySelected == key) || (typeof currentQuantitySelected == 'undefined' && first == 0)) {
                                            self.getProducts()[self.activeProductIndex].setProductAttributes({'selectionData': {'quantity': key}});

                                            if (parseInt(self.getActiveProductAttributes().percentSavings) > 0 && typeof originalPriceData.priceList !== 'undefined') {
                                                $('#quantityPriceSelection').append(
                                                    $('<div />').addClass('foldersTabularRow').append(
                                                        $('<div />').append(
                                                            $('<span />').addClass('selQty').html(numberWithCommas(key) + ' Qty')
                                                        ).append(
                                                            $('<span />').addClass('selPrice').append(
                                                                $('<strike />').addClass('').html(formatCurrency(originalPriceData.priceList[key].price))
                                                            )
                                                        ).append(
                                                            $('<span />').addClass('selPrice').append(
                                                                $('<strong />').addClass('priceDisplay').html(formatCurrency(data.output.VENDORS_SIMPLE_PRICING.V_ADMORE[key]))
                                                            )
                                                        ).append(
                                                            $('<span />').addClass('textRight percentOffText pullRight').html('Save ' + parseInt(self.getActiveProductAttributes().percentSavings) + '%')
                                                        )
                                                    )
                                                );
                                            } else {
                                                $('#quantityPriceSelection').append(
                                                    $('<div />').addClass('foldersTabularRow').append(
                                                        $('<div />').append(
                                                            $('<span />').addClass('selQty').html(numberWithCommas(key) + ' Qty')
                                                        ).append(
                                                            $('<span />').addClass('selPrice').append(
                                                                $('<strong />').addClass('priceDisplay').html(formatCurrency(data.output.VENDORS_SIMPLE_PRICING.V_ADMORE[key]))
                                                            )
                                                        )
                                                    )
                                                );
                                            }
                                        }

                                        doListAppend(key, data.output.VENDORS_SIMPLE_PRICING.V_ADMORE, first);
                                        first = 1;

                                        if((data.output.VENDORS_SIMPLE_PRICING.V_ADMORE[key] < self.getActiveProductAttributes().minimumPrice && self.getActiveProductAttributes().minimumPrice != null) || self.getActiveProductAttributes().minimumPrice == null) {
                                            self.getProducts()[self.activeProductIndex].setProductAttributes({'minimumPrice': data.output.VENDORS_SIMPLE_PRICING.V_ADMORE[key]});
                                        }

                                        if((parseInt(key) < self.getActiveProductAttributes().minimumQuantity && self.getActiveProductAttributes().minimumQuantity != null) || self.getActiveProductAttributes().minimumQuantity == null) {
                                            self.getProducts()[self.activeProductIndex].setProductAttributes({'minimumQuantity': parseInt(key)});
                                        }
                                    }

                                    if (recentlyViewedCounter = 1) {
                                        productPage.storeRecentlyViewed();
                                        recentlyViewedCounter = 0;
                                    }

                                    self.initPageActions();

                                    if ($('[bns-quantitylist] [selection-selected="true"]').length == 0 && $('[bns-quantitylist] [selection-selected="false"]').length > 0) {
                                        $($('[bns-quantitylist] [selection-selected="false"]')[0]).trigger('click');
                                    }
                                }
                            });
                        }
                    });
                }
            }, 250, 'getProductPrice');
        }
    },

    getProductFeatures: function(sku) {
        var self = this;

        waitForFinalEvent(function() {
            $.ajax({
                type: "GET",
                url: '/' + websiteId + '/control/getProductFeatures',
                data: { id: ((sku != null) ? sku : self.getActiveProductAttributes().selectionData.productId) },
                dataType: 'json',
                cache: true
            }).done(function(data) {
                if(data.success && typeof data.features !== 'undefined') {
                    $('[bns-productfeatures]').empty();
                    $('[bns-productfeatures]').append(
                        $('<tr/>').append(
                            $('<td/>').html('SKU')
                        ).append(
                            $('<td/>').html(data.productId)
                        )
                    );

                    $.each(data.features, function(k, v) {
                        $('[bns-productfeatures]').append(
                            $('<tr/>').append(
                                $('<td/>').html(k)
                            ).append(
                                $('<td/>').html(v)
                            )
                        );
                    });

                    //check for the pocket specific features
                    if(typeof window.productFeatureData !== 'undefined' && typeof window.pocketFeatureId !== 'undefined') {
                        if(typeof window.productFeatureData[window.pocketFeatureId]['assocs'] !== 'undefined') {
                            Object.keys(window.productFeatureData[window.pocketFeatureId]['assocs']).forEach(function(k, i) {
                                if(k == 'CARD_SLITS') {
                                    return;
                                }
                                $('[bns-productfeatures]').append(
                                    $('<tr/>').append(
                                        $('<td/>').html(window.productFeatureData[window.pocketFeatureId]['assocs'][k][0]['name'])
                                    ).append(
                                        $('<td/>').html(window.productFeatureData[window.pocketFeatureId]['assocs'][k][0]['desc'])
                                    )
                                );
                            });
                        }
                    }
                }
            });
        }, 250, 'productFeatures');
    },

    getProductDesc: function(sku) {
        var self = this;
        waitForFinalEvent(function() {
            $.ajax({
                type: "GET",
                url: '/' + websiteId + '/control/getProductDesc',
                data: { id: ((sku != null) ? sku : self.getActiveProductAttributes().selectionData.productId) },
                dataType: 'json',
                cache: true
            }).done(function(data) {
                if(data.success) {
                    if(data.desc != null && data.desc != '') {
                        $('[bns-longdesc]').html(data.desc);
                    }
                } else {
                    //TODO throw error
                }
            });
        }, 250, 'productDesc');
    },

    buildPriceAttribute: function(isPrinted, vendorProductId) {
        var self = this;

        var priceAttr = {};
        if(isPrinted) {
            priceAttr.VENDOR_SKU = vendorProductId;
            priceAttr.VENDOR_ID = 'V_ADMORE';
            priceAttr.CARD_SLIT = self.getActiveProductAttributes().selectionData.cardSlits;
            priceAttr.COLOR_GROUP = self.getActiveProductAttributes().colorGroup;
            priceAttr.COLOR_NAME = self.getActiveProductAttributes().colorDesc;
            priceAttr.PAPER_TEXTURE = self.getActiveProductAttributes().paperTexture;
            priceAttr.PAPER_WEIGHT = self.getActiveProductAttributes().paperWeight;
            priceAttr.POCKET_TYPE = self.getActiveProductAttributes().selectionData.pocket;
            priceAttr.CUSTOM_QUANTITY = (typeof self.getActiveProductAttributes().selectionData.customQuantity != 'undefined' && self.getActiveProductAttributes().selectionData.customQuantity != '' ? parseInt(self.getActiveProductAttributes().selectionData.customQuantity) : 0); // Custom Quantity...
            priceAttr.QUANTITIES = [];
            priceAttr.RESPONSE_TYPE = 'DETAILED';
            priceAttr.ADDONS_OPTIONS = [];
            priceAttr.CUSTOM_OPTIONS = [];

            var numberOfCoatings = parseInt(self.getActiveProductAttributes().selectionData.numberOfCoatings);

            if(numberOfCoatings > 0) {
                priceAttr.PAPER_TEXTURE += ' C' + numberOfCoatings + 'S';
            }

            if (self.getActiveProductAttributes().selectionData.printMethod == 'foilStamping') {
                var customOption = {};
                customOption.CUSTOM_OPTION_COLOR = self.getActiveProductAttributes().selectionData.foilColor;
                customOption.CUSTOM_OPTION_NAME = 'FOIL_STAMPING';
                customOption.RUNS = [{'IMAGES': [self.getActiveProductAttributes().imageSize]}];
                priceAttr.CUSTOM_OPTIONS.push(customOption);
            } else if (self.getActiveProductAttributes().selectionData.printMethod == 'embossing') {
                var customOption = {};
                customOption.CUSTOM_OPTION_NAME = 'EMBOSSING';
                customOption.SIDES = [{
                    'RUNS': [{'IMAGES': ['15']}]
                }];
                priceAttr.CUSTOM_OPTIONS.push(customOption);
            } else if (self.getActiveProductAttributes().selectionData.printMethod == 'PMS' || self.getActiveProductAttributes().selectionData.printMethod == 'FOUR_COLOR') {
                var customOption = {};
                var inkColors = [];
                Object.keys(self.getActiveProductAttributes().selectionData).forEach(function(key, index) {
                    if(key.indexOf('spotColorText') != -1) {
                        inkColors.push(self.getActiveProductAttributes().selectionData[key]);
                    }
                });
                customOption.CUSTOM_OPTION_NOTES = { 'INK_COLORS': inkColors };
                customOption.CUSTOM_OPTION_NAME = 'OFFSET';
                customOption.SIDES = [{
                    'PRINT_METHOD': self.getActiveProductAttributes().selectionData.printMethod,
                    'NUMBER_OF_INKS': (self.getActiveProductAttributes().selectionData.printMethod == 'PMS') ? parseInt(self.getActiveProductAttributes().selectionData.spotColor) : 0,
                    'HEAVY_COVERAGE': 'N',
                    'COLOR_WASHES': 0,
                    'PLATE_CHANGES': 0
                }];
                priceAttr.CUSTOM_OPTIONS.push(customOption);
            }

            switch(self.getActiveProductAttributes().selectionData.attachments) {
                case 'TANGSTRIP_WHITE':
                    var addonOption = {};
                    addonOption.ADDON_NOTES = { 'ATTACHMENT_TYPE': self.getActiveProductAttributes().selectionData.attachments };
                    addonOption.ADDON_TYPE = 'ATTACHMENTS';
                    addonOption.SIDES = [['TANG_STRIP']];
                    priceAttr.ADDONS_OPTIONS.push(addonOption);
                    break;
                case 'TANGSTRIP_MATCHING':
                    var addonOption = {};
                    addonOption.ADDON_NOTES = { 'ATTACHMENT_TYPE': self.getActiveProductAttributes().selectionData.attachments };
                    addonOption.ADDON_TYPE = 'ATTACHMENTS';
                    addonOption.SIDES = [['TANG_STRIP']];
                    priceAttr.ADDONS_OPTIONS.push(addonOption);
                default:
                    break;
            }

            if(self.getActiveProductAttributes().selectionData.coatingSide1 != 'None' || self.getActiveProductAttributes().selectionData.coatingSide2 != 'None') {
                var addonOption = {};
                addonOption.ADDON_TYPE = 'COATINGS';
                addonOption.SIDES = [];
                if(self.getActiveProductAttributes().selectionData.coatingSide1 != 'None' && self.getActiveProductAttributes().selectionData.printMethod != 'foilStamping') {
                    addonOption.SIDES.push([self.getActiveProductAttributes().selectionData.coatingSide1]);
                }
                if(self.getActiveProductAttributes().selectionData.coatingSide2 != 'None' && self.getActiveProductAttributes().selectionData.printMethod != 'foilStamping') {
                    addonOption.SIDES.push([self.getActiveProductAttributes().selectionData.coatingSide2]);
                }
                priceAttr.ADDONS_OPTIONS.push(addonOption);
            }
        } else {
            priceAttr['id']= self.getActiveProductAttributes().selectionData.productId;
            priceAttr['quantity'] = (typeof self.getActiveProductAttributes().selectionData.customQuantity != 'undefined' && self.getActiveProductAttributes().selectionData.customQuantity != '' ? parseInt(self.getActiveProductAttributes().selectionData.customQuantity) : 0);
        }

        self.getProducts()[self.activeProductIndex].setProductAttributes({'pricingRequest': JSON.stringify(priceAttr)});
        return priceAttr;
    },

    selectionFontResize: function(targetedElements, allowedHeight) {
        window.setTimeout(function() {
            $.each($(targetedElements), function() {
                var counter = 0;
                var element = $(this);
                element.css({
                    'font-size': 'inherit',
                    'white-space': 'initial',
                    'overflow': 'visible',
                    'text-overflow': 'inherit'
                });
                while (getFullHeight(element) > allowedHeight && parseInt(element.css('font-size')) > 12) {
                    counter++;
                    element.css('font-size', (parseInt(element.css('font-size')) - 1) + 'px');
                    if(counter > 100) {
                        break;
                    }
                }
                if (getFullHeight(element) > allowedHeight && parseInt(element.css('font-size')) == 12) {
                    element.css({
                        'overflow': 'hidden',
                        'white-space': 'nowrap',
                        'text-overflow': 'ellipsis'
                    });
                }
            });
        }, 0);
    },

    processCartData: function(el) {
        var self = this;
        //todo ADD SPINNER TO DISABLE DOUBLE CLICKING

        if($(el).hasClass('processing')) {
            return;
        }

        $(el).addClass('processing');
        $(el).bigNameSpinner(true, false, 50, null, null, '', null, null);

        var attr = {};
        attr.add_product_id = self.getActiveProductAttributes().selectionData.productId;
        attr.quantity = parseInt(self.getActiveProductAttributes().selectionData.quantity);
        attr.itemComment = (typeof self.getActiveProductAttributes().fileUploadComments != 'undefined' ? self.getActiveProductAttributes().fileUploadComments: '');

        if(typeof self.getActiveProductAttributes().selectionData.printMethod != 'undefined'/* && self.getActiveProductAttributes().selectionData.printMethodCustomization != 'getQuoteForm'*/) {
            attr.pricingRequest = self.getActiveProductAttributes().pricingRequest;
            attr.colorsFront = 1;
            attr.colorsBack = 0;

            var artworkSource = 'ART_UPLOADED';
            var fileCount = 0;
            var fileName = [];
            var filePath = [];
            var fileOrder = [];
            var fileOrderItem = [];

            // Uploads
            if(self.getActiveProductAttributes().fileUpload) {
                $.each(self.getActiveProductAttributes().fileUpload, function(i) {
                    fileName[i]      = self.getActiveProductAttributes().fileUpload[i].name;
                    filePath[i]      = self.getActiveProductAttributes().fileUpload[i].path;
                    fileOrder[i]     = '';
                    fileOrderItem[i] = '';
                    fileCount++;
                });
            } else {
                artworkSource = 'ART_UPLOADED_LATER';
            }


            if(self.getProducts()[self.activeProductIndex].getProductAttributes().selectionData.reuseArtwork) {
                fileName[i] = self.getProducts()[self.activeProductIndex].getProductAttributes().selectionData.reuseArtwork;
                filePath[i] = $('[bns-previousartworkcontainer] .selectListItem').attr('data-uploadpath');
                artworkSource = 'ART_REUSED';
                fileCount++;
            }

            //no files found
            if(fileCount == 0) {
                artworkSource = 'ART_UPLOADED_LATER';
            }

            attr.fileName = fileName;
            attr.filePath = filePath;
            attr.fileOrder = fileOrder;
            attr.fileOrderItem = fileOrderItem;
            attr.artworkSource = artworkSource;

            var pricingRequestObj = JSON.parse(self.getActiveProductAttributes().pricingRequest);
            var productName = pricingRequestObj.VENDOR_SKU + ' ' + self.getActiveProductAttributes().productName;
            /*productName = productName + ((self.getActiveProductAttributes().colorDesc != '') ? ' - ' + self.getActiveProductAttributes().colorDesc : '');
            productName = productName + ((self.getActiveProductAttributes().selectionData.cardSlits != '') ? ' - ' + self.getActiveProductAttributes().selectionData.cardSlits : '');
            if(self.getActiveProductAttributes().selectionData.printMethod == 'foilStamping') {
                productName = productName + ((self.getActiveProductAttributes().selectionData.foilColor != '') ? ' - ' + self.getActiveProductAttributes().selectionData.foilColor + ' Foil' : '');
            }
            */
            productName = productName + ((self.getActiveProductAttributes().paperTexture != '') ? ' - ' + self.getActiveProductAttributes().paperTexture : '');
            productName = productName + ((self.getActiveProductAttributes().paperWeight != '') ? ' ' + self.getActiveProductAttributes().paperWeight : '');
            
            attr.name = productName;
        }

        //get doogma saved data
        if(self.getActiveProductAttributes().selectionData.printMethod == 'foilStamping' && self.getActiveProductAttributes().selectionData.designMethod == 'onlineDesigner') {
            window.doogmaParameters.doogmathumb = null;
            $('[data-addtocartbutton]').trigger('click');

            window.cartInterval = 0;
            var doogmaInterval = setInterval(function() {
                if(window.cartInterval > 3 || window.doogmaParameters.doogmathumb != null) {
                    clearInterval(doogmaInterval);
                    self.saveProject();

                    if(self.getActiveProductAttributes().projectId != null) {
                        attr.scene7ParentId = self.getActiveProductAttributes().projectId;
                    }
                    if(self.getActiveProductAttributes().scene7DesignId != null) {
                        attr.artworkSource = 'SCENE7_ART_ONLINE';
                        attr.scene7DesignId = self.getActiveProductAttributes().scene7DesignId;
                    }

                    //attr.printData = JSON.stringify(window.doogmaParameters);
                    self.addToCart(attr);
                }
                window.cartInterval++;
            }, 1000);
        } else {
            self.addToCart(attr);
        }

        $(el).bigNameSpinner(false);
    },

    addToCart: function(attr) {
        var self = this;

        self.cartEndPoint(attr, self.getActiveProductAttributes().productName, self.getActiveProductAttributes().colorDesc, false, null, null);

        //remove warning of page exit
        self.pageExitWarning(true);

        // $().updateMiniCart();

        //relocate to cart once all products have been added
        var crossSell = $('<form/>').attr('id', 'crossSell').attr('action', gCartUrl).attr('method', 'POST').append('<input type="hidden" name="lastVisitedURL" value="' + document.referrer + '" />').append('<input type="hidden" name="fromAddToCart" value="true" />');
        $('body').append(crossSell);
        $('#crossSell').submit();
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
            // localStorage.setItem('addToCartData', data)
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
            $().updateMiniCart(data);
        }).fail(function(jqXHR) {
            if(errorCallback != null && typeof errorCallback == 'function') {
                errorCallback();
            }
        });
    },

    saveProject: function() {
        var self = this;

        var projectObj = {
            'settings': {
                'projectId': self.getActiveProductAttributes().projectId,
                'productType': 'product',
                'product': []
            }
        };

        $.each(self.getProducts(), function(idx) {
            var tempProduct = $.extend(true, {}, self.getProducts()[idx].getProductAttributes());
            tempProduct.s7Data = {'scene7Data' : window.doogmaParameters};
            tempProduct.selectionData.designerValue = {}; //remove unused doogma data
            tempProduct.colorsFront = 0; //basic workaround for s7 storage
            tempProduct.colorsBack = 0 //basic workaround for s7 storage
            tempProduct.isFullBleed = false //basic workaround for s7 storage
            projectObj.settings.product.push(tempProduct);
        });

        $.ajax({
            type: 'POST',
            timeout: 5000,
            url: '/' + websiteId + '/control/saveProject',
            data: { 'id' : (self.getActiveProductAttributes().projectId != null ? self.getActiveProductAttributes().projectId != null : ''), 'productData' : JSON.stringify(projectObj) },
            async: false,
            dataType: 'json',
            cache: false
        }).done(function(data) {
            if(data.success) {
                self.getProducts()[self.activeProductIndex].setProductAttributes({'projectId': data.projectId});
                if(typeof data.savedDesignIds !== 'undefined') {
                    $.each(data.savedDesignIds, function(idx) {
                        self.getProducts()[self.activeProductIndex].setProductAttributes({'scene7DesignId': data.savedDesignIds[idx]});
                    });
                }
            }
        });

        return self.getActiveProductAttributes().projectId;
    },
    bindSelection: function() {
        var self = this;

        $('[bns-selection]').off('click.bnsSelection').on('click.bnsSelection', function(e) {
            if (typeof $('[selection-selectlistname="' + $(this).attr('selection-target') + '"]').attr('selection-multiselect') != 'undefined' || typeof $(this).attr('selection-selected') == 'undefined' || (typeof $(this).attr('selection-selected') != 'undefined' && $(this).attr('selection-selected') == 'false')) {
                self.updateSelection($(this), e);
            }
        });

        $('.productSidebar .sidebarPanel h4 .fa, [bns-selection]').off('click.closeSidebar').on('click.closeSidebar', function(){
            if (!(typeof $(this).attr('selection-target') != 'undefined' && typeof $('[selection-selectlistname="' + $(this).attr('selection-target') + '"]').attr('selection-multiselect') != 'undefined')) {
                $(this).parents('.sidebarPanel').animate({
                    'opacity': '0',
                    'left': '100%'
                }, 150, 'linear');

                $('.product-images').removeClass('hidden');
                $('#doogma').addClass('hidden');
            }
        });
    },
    initPageActions: function() {
        var self = this;

        function updateInnerBodyHeight() {
            $('.sidebarPanel').each(function(){
                var headerHeight = $(this).find('.colorTextureHeading').outerHeight();
                var windowHeight = $(window).height();
                var scrollHeight = windowHeight - headerHeight - 15;

                var innerBody = $(this).find('.colorTextureBodyInner');
                innerBody.css('max-height', 'initial');
                var innerBodyContents = $(innerBody).height();
                var subtractedHeight = 0;

                for (var x = 0; x < innerBody.parents('.sidebarPanel').children().length; x++) {
                    if (innerBody.parent().index() < x) {
                        subtractedHeight += getFullHeight($(innerBody.parents('.sidebarPanel').children()[x]));
                    }
                }

                innerBody.css('max-height', (scrollHeight - subtractedHeight) + 'px');
            });
        }

        updateInnerBodyHeight();

        // sidebarPanelSlideOut();

        $('.jqs-sidebarToggle').off('click.sidebarToggle').on('click.sidebarToggle', function() {
            var sidebar = $(this).attr('data-sidebar-name');

            $('#' + sidebar).animate({
                'opacity': '1',
                'left': ($('.productSidebar').offset().left / $(window).width() * 100) + '%'
            }, 150, 'linear');

            if(sidebar == 'dropdown-editText' || sidebar == 'dropdown-editImage'){
                $('.product-images').addClass('hidden');
                $('#doogma').removeClass('hidden');
            }

            /*
            if ($(this).attr('selection-selectlistname') == 'colorSelection') {
                if ($('.colorTextureBodyInner').find('[selection-target="colorSelection"]').attr('selection-selected') != 'undefined') {
                    $('.colorTextureBodyInner').scrollTop($('.colorTextureBodyInner').scrollTop() + $('.colorTextureBodyInner [selection-selected]').offset().top - 100);
                }

                if ($('.colorTextureBodyInner').find('[selection-target="quoteColorSelection"]').attr('selection-selected') != 'undefined') {
                    $('.colorTextureBodyInner').scrollTop($('.colorTextureBodyInner').scrollTop() + $('.colorTextureBodyInner [selection-selected]').offset().top - 100);
                }
            } else {
            */
            $('.colorTextureBodyInner').scrollTo('[selection-selected]');
            /*
        }
*/
            slideIt_init();
        });

        $('[bns-addtocart]').off('click.bnsAddToCart').on('click.bnsAddToCart', function(e) {
            e.stopPropagation();
            self.processCartData(this);
        });

        self.bindSelection();

        $('[bns-loadPreviousProductStep]').off('click.loadPreviousProductStep').on('click.loadPreviousProductStep', function(e) {
            e.stopPropagation();
            self.updateSelection(null, null);
        });

        $('[bns-loadProductStep]').off('click.loadProductStep').on('click.loadProductStep', function() {
            if (typeof $(this).attr('bns-loadProductStep') != 'undefined' && $(this).attr('bns-loadProductStep') != '') {
                self.loadProductStep($(this).attr('bns-loadProductStep'));
            }
        });

        $('[name="spotColor"]').off('click.numOfSpotColors').on('click.numOfSpotColors', function() {
            $('[name*="spotColorText"]').addClass('hidden');
            for (var i = 1; i <= $(this).attr('id').match(/.*(.)$/)[1]; i++) {
                $('[name="spotColorText' + i + '"]').removeClass('hidden');

                if(i == 2){
                    $('[name="spotColorText2"]').addClass('spotColor2');
                } else if(i != 2){
                    $('[name="spotColorText2"]').removeClass('spotColor2');
                }
            }
        });

        $('[bns-textinput], [bns-radioselection]').off('input.contentInput, change.contentChange').on('input.textInput, change.textChange', function() {
            var element = $(this);
            var productAttribute = {};
            var selectionData = {};

            function doAction(element, productAttribute, selectionData) {
                selectionData[element.attr('name')] = element.val();
                productAttribute['selectionData'] = selectionData;
                self.getProducts()[self.activeProductIndex].setProductAttributes(productAttribute);

                if (element.attr('name') == 'spotColor') {
                    for (var x = $('[name^="spotColorText"]').length; x > element.val(); x--) {
                        $('[name="spotColorText' + x + '"]').val('');
                        delete self.getActiveProductAttributes().selectionData['spotColorText' + x];
                    }
                }

                self.getProductPrice();
            }

            if (element.attr('name') == 'customQuantity') {
                var customQuantity = element.val();
                var minimumQuantity = self.getActiveProductAttributes().minimumQuantity < 50 ? self.getActiveProductAttributes().minimumQuantity : 50;
                var smallestBaseQuantity = parseInt($('[bns-selection][selection-target="quantitySelection"][selection-name="quantity"]:first-child').attr('selection-value'));

                if (typeof customQuantityTimeout != 'undefined') {
                    clearTimeout(customQuantityTimeout);
                }
                customQuantityTimeout = setTimeout(function() {
                    if (customQuantity < smallestBaseQuantity) {
                        $('[bns-quantityerror]').removeClass('hidden').html('Your quantity must be in increments of ' + minimumQuantity + ' and greater than or equal to ' + smallestBaseQuantity + '.');
                    } else if (customQuantity % minimumQuantity != 0) {
                        $('[bns-quantityerror]').removeClass('hidden').html('Your quantity must be in increments of ' + minimumQuantity + '.');
                    } else if (customQuantity > 500000) {
                        $('[bns-quantityerror]').removeClass('hidden').html('Please call customer service to get a quote.');
                    } else {
                        $('[bns-quantityerror]').addClass('hidden').html('');
                        selectionData['quantity'] = customQuantity;
                        productAttribute['selectionData'] = selectionData;
                        doAction(element, productAttribute, selectionData);
                    }
                }, 1000);
            } else {
                doAction(element, productAttribute, selectionData);
            }
        });

        // Have Custom Field show Quantity in Quote Builder for SF-101
        var typingTimer;
        var doneTypingInterval = 1000;

        $('[bns-customquantity]').keyup(function(){
            var minimumQuantity;

            $("#sidebar-quoteQuantitySelection [bns-selection]").each(function() {
                if ((typeof minimumQuantity === "undefined" || parseInt($(this).attr("selection-value")) < minimumQuantity) && parseInt($(this).attr("selection-value")) >= 5) {
                    minimumQuantity = parseInt($(this).attr("selection-value"));
                }
            });
            
            var customQuantity = parseInt($('[bns-customquantity]').val());
            $("[bns-min_quantity_warning]").remove();
            clearTimeout(typingTimer);

            if (customQuantity != "") {
                if (minimumQuantity <= customQuantity) {
                    typingTimer = setTimeout(doneTyping, doneTypingInterval);
                } else {
                    $("<div />").attr("bns-min_quantity_warning", "").attr("style", "color: #ff0000;").html("Please enter an amount greater than or equal to " + minimumQuantity).insertBefore($(this));
                }
            }
        });

        function doneTyping () {
            var customQuantityRow = $('<div >').addClass('selectListItem').attr({
                'bns-selection' : '',
                'selection-target' : 'quoteQuantitySelection',
                'selection-name' : 'quoteQuantitySelection',
                'selection-value' : $('[bns-customquantity]').val()
            }).append(
                $('<div >').addClass('foldersTabularRow folderDisplayTable').append(
                    $('<div >').attr('selection-removeonselect', '').append(
                        $('<span >').addClass('selectCheckbox')
                    )
                ).append(
                    $('<div >').html(' Qty').prepend(
                        $('<span >').attr('bns-multiselectvaluelocation', '').html($('[bns-customquantity]').val())
                    )
                )
            );
            $('[selection-name="quoteQuantitySelection"]').each(function() {
                if($(this).attr('selection-value') == $('[bns-customquantity]').val()) {
                    $(this).remove();
                } else {
                    customQuantityRow.insertBefore($('[bns-customquantity]'))
                }
            });
            self.initPageActions();
        }

        //### BEGIN DOOGMA CHANGES
        $('[data-doogma], [data-doogmaclick]').off('click.doogma').on('click.doogma', function() {
            self.updateDoogmaByElement($(this));
        });

        $('[data-doogmainput][data-doogma-key="textline1"]').off('input.updateTextButton').on('input.updateTextButton', function() {
            $('[bns-edittextbutton]').html('<i class="fa fa-file-text-o"></i> ' + ($(this).val() == '' ? 'Add Text' : 'Edit Text'));
        });

        $('[data-doogmainput]').off('input.doogma').on('input.doogma', function() {
            self.updateDoogmaByElement($(this));
        });

        $('[data-doogmachange]').off('change.doogma').on('change.doogma', function() {
            self.updateDoogmaByElement($(this));
        });

        $('[data-doogmacleartext]').off('click.doogma').on('click.doogma', function() {
            $('[data-doogma-key="textline1"]').val('');
            self.updateDoogmaByValue('textline1', '');
            $('[data-doogmainput][data-doogma-key="textline1"]').trigger('input.updateTextButton');
        });

        //### END DOOGMA CHANGES

        //$('[bns-productassetfront]', '[bns-productassetinside]', '[bns-productassetback]').off('click.productAsset').on('click.productAsset', function() {
        //    var productAttribute = {};
        //
        //    if ($(this).hasAttribute('bns-productassetfront')) {
        //        productAttribute['assetName'] = '';
        //    }
        //
        //    self.getProducts()[self.activeProductIndex].setProductAttributes(productAttribute);
        //});

        if (typeof createScrollableLock != 'undefined') {
            createScrollableLock();
        }

        bnRevealInit($('[data-bnReveal]'));

        // Change Font Size for Color Selection on Line Break
        $(window).off('resize.productPageResize').on('resize.productPageResize', function() {
            waitForFinalEvent(function() {
                self.selectionFontResize($('[bns-colorselecttext]'), 80);
            }, 100, 'selectionFontResize');

            updateInnerBodyHeight();
        });

        $('[data-bnreveal="startUpload"]').off('click.showUploadButtom').on('click.showUploadButtom', function() {
            $('[bns-hiddenuploadbutton]').removeClass('hidden').addClass('hidden');
        });


        $('.dropzone').off('click.dropzone').on('click.dropzone', function() {
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
                        $('<span/>').addClass('paddingLeft10 relative').append(
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
                    fileDiv.find('span.action .fa-trash-o').on('click', function (e) {
                        e.stopPropagation();
                        self.uploadDelete($(this).parents('[data-filename]').text());
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
                    $('[bns-hiddenuploadbutton]').removeClass('hidden');
                    if (data.result.success) {
                        var fileList = [];

                        $.each(data.result.files, function (idx) {
                            var fileInfo = {};
                            fileInfo['name'] = data.result.files[idx].name;
                            fileInfo['path'] = data.result.files[idx].path;
                            fileList.push(fileInfo);
                        });

                        if (typeof self.getActiveProductAttributes().fileUpload != 'undefined') {
                            fileList = self.getActiveProductAttributes().fileUpload.concat(fileList);
                        }

                        var productAttribute = {};
                        productAttribute['fileUpload'] = fileList;
                        self.getProducts()[self.activeProductIndex].setProductAttributes(productAttribute);

                        self.createFileList();
                    }
                },
                fail: function (e, data) {
                    $.each(data.files, function (idx, el) {
                        $('.' + self.fileGrid).find('.dropzone').find('[data-filename="' + data.files[idx].name + '"]').first().children('div.progress').first().find('i.fa').removeClass('fa-trash-o').addClass('fa-warning');
                    });
                }
            });


        }

        if (!self.getActiveProductAttributes().initialProductStep == 'plain') {
            $('[bns-freeshippingtext]').removeClass('hidden');
        }
        $('[bns-emailthisquote]').removeClass('hidden');
        if (self.getActiveProductAttributes().initialProductStep == 'plain') {
            $('[bns-emailthisquote]').addClass('hidden');
            $('[bns-emailthisquote]').addClass('hidden');
        }

        initBigNameFormValidation();

        $('[name="filterArtLocation"]').off('change.filterArtLocation').on('change.filterArtLocation', function() {
            $('[bns-filterartlocation-value]').removeClass('hidden').addClass('hidden');
            $('[bns-filterartlocation-value="' + $(this).val() + '"]').removeClass('hidden');
        });

        $('[name="filterArtLocation"]').trigger('change.filterArtLocation');

        $('[bns-templatetypesimplifier]').on('click', function() {
            self.getProducts()[self.activeProductIndex].setProductAttributes({'userSelectedTemplateType': $(this).attr('bns-templatetypesimplifier')});
            $(this).parent().parent().remove();
            $('[bns-hiddencontent="editLayout"]').removeClass('hidden');
            $('[name="filterArtLocation"]').val($(this).attr('bns-templatetypesimplifier')).trigger('change');
            $('[selection-name="editLayout"]:not(.hidden):first-child').trigger('click.bnsSelection');
            $('#sidebar-editLayout h4').html('<i class="fa fa-caret-left"></i>Choose Artwork Layout');
        });

        self.createFileList();
        $('[bns-assetlist]').parent().css('height', $('[bns-productStep] > *:first-child').height() + 'px');

        var sortedProductList = ['-501-C', '-505-C', '-510-C', '-511-C', '-508-C', '-546-C', '-560-C', '-540-C', '-533-C', '-554-C'];

        for (var i = sortedProductList.length - 1; i >= 0; i--) {
            var targetedElement = $('[selection-name="productId"][data-product-id*="' + sortedProductList[i] + '"]');
            if (targetedElement.length > 0) {
                targetedElement.prependTo(targetedElement.parent());
                if ($('.productListBottomBar').length == 0) {
                    targetedElement.after(
                        $('<div />').addClass('productListBottomBar textCenter').append(
                            $('<span />').addClass('textBold').html('More Colors')
                        )
                    );
                }
            }
        }

        if ($('.productListBottomBar').length > 0) {
            $('.productListTopBar').remove();
            $('[selection-name="productId"]:first-child').before(
                $('<div />').addClass('productListTopBar textCenter').append(
                    $('<span />').addClass('textBold').html('Best Sellers')
                )
            );
        }

        $('[bns-loadpreviousproductstep]').removeClass('hidden');

        if (self.stepList.length <= 1) {
            $('[bns-loadpreviousproductstep]').addClass('hidden');
        }

        $('[bns-loadsamples]').removeClass('hidden').addClass('hidden');
        if (self.getActiveProductAttributes().initialProductStep == 'getQuoteForm') {
            $('[bns-loadsamples]').removeClass('hidden');
        }

        if(($('.selectListParent.colorSelection').find('.productColorSelection').css('color')) != 'rgb(0, 0, 0)') {
            $('.sidebarToggle.colorSelection').addClass('alternateColor');
        } else {
            $('.sidebarToggle.colorSelection').removeClass('alternateColor');
        }
        if(($('.selectListParent.foilSelection').find('.productColorSelection > div').css('color')) != 'rgb(0, 0, 0)') {
            $('.sidebarToggle.foilSelection').addClass('alternateColor');
        } else {
            $('.sidebarToggle.foilSelection').removeClass('alternateColor');
        }
    },

    rebuildSelections: function() {
        var self = this;

        $('[selection-name="printMethod"][selection-value="FOIL"]').removeClass('hidden');
        $('[selection-name="printMethod"][selection-value="EMBOSS"]').removeClass('hidden');

        for (var key in self.getProducts()[0].getProductAttributes().selectionData) {
            if ($.inArray(key, self.selectionExistsList) >= 0) {
                if ($('[selection-name="' + key + '"][selection-value="' + self.getProducts()[0].getProductAttributes().selectionData[key] + '"]').length == 0) {
                    var productAttribute = {};
                    var selectionData = {};
                    selectionData[key] = $($('[selection-name="cardSlits"]')[1]).attr('selection-value');
                    productAttribute['selectionData'] = selectionData;
                    self.getProducts()[self.activeProductIndex].setProductAttributes(productAttribute);
                }
            }

            var element = $('[selection-name="' + key + '"][selection-value="' + self.getProducts()[0].getProductAttributes().selectionData[key] + '"]');

            if (element.length > 0) {
                if (typeof $(element).attr('selection-autoupdate') == 'undefined' || (typeof $(element).attr('selection-autoupdate') != 'undefined' && $(element).attr('selection-autoupdate') != 'false')) {
                    $(element).trigger('click.bnsSelection');
                    $(element).trigger('click.doogma');
                }
            } else if ($('[bns-textinput][name="' + key + '"]').length > 0) {
                // This selection data is an generic html input...so we handle it differently than the actual bns selection.
                $('[bns-textinput][name="' + key + '"]').val(self.getProducts()[0].getProductAttributes().selectionData[key]).trigger('input');
            } else if ($('[bns-radioselection][name="' + key + '"]').length > 0) {
                $('[bns-radioselection][name="' + key + '"][value="' + self.getProducts()[0].getProductAttributes().selectionData[key] + '"]').trigger('click');
            }
        }
    },

    updateDoogmaByElement: function(element) {
        var self = this;
        var designerOptions = self.getActiveProductAttributes().selectionData.designerValues[self.getActiveProductAttributes().selectionData.printMethod];

        if (typeof designerOptions == 'undefined') { return false; }

        var doogmaVal = (typeof element.attr('data-doogma-value') != 'undefined' ? element.attr('data-doogma-value') : (typeof element.val() != 'undefined' ? element.val() : null));
        var doogmaText = designerOptions.textline1fontsize;
        var doogmaValx = designerOptions.textline1x;
        var doogmaValy = designerOptions.textline1y;
        var doogmaKey = element.attr('data-doogma-key');
        var dataDirection = (typeof element.attr('data-direction') != 'undefined' ? element.attr('data-direction') : null);

        if (dataDirection != null && dataDirection == 'negative') {
            doogmaVal = parseInt(doogmaText) - 1;
            element.attr('data-doogma-value', doogmaVal);
        } else if (dataDirection != null && dataDirection == 'positive') {
            doogmaVal = parseInt(doogmaText) + 1;
            element.attr('data-doogma-value', doogmaVal);
        }

        if (dataDirection != null && dataDirection == 'left') {
            doogmaVal = parseInt(doogmaValx) - 1;
            element.attr('data-doogma-value', doogmaVal);
        } else if (dataDirection != null && dataDirection == 'right') {
            doogmaVal = parseInt(doogmaValx) + 1;
            element.attr('data-doogma-value', doogmaVal);
        } else if (dataDirection != null && dataDirection == 'up') {
            doogmaVal = parseInt(doogmaValy) - 1;
            element.attr('data-doogma-value', doogmaVal);
        } else if (dataDirection != null && dataDirection == 'down') {
            doogmaVal = parseInt(doogmaValy) + 1;
            element.attr('data-doogma-value', doogmaVal);
        }

        self.updateDoogmaByValue(doogmaKey, (isNaN(doogmaVal) == false ? (doogmaVal + '') : doogmaVal));
    },

    updateDoogmaByValue: function(doogmaKey, doogmaVal) {
        var self = this;
        var globalDoogmaKeys = ['papercolorandtexture'];


        function updateDoogmaKeyVal(printMethod, key, val) {
            var designerOptions = self.getActiveProductAttributes().selectionData.designerValues[printMethod];
            if (typeof designerOptions == 'undefined') { return false; }

            if(doogmaKey != null) {
                designerOptions[key] = val;
            }
        }

        if (doogmaKey == 'textline1' && (self.getActiveProductAttributes().selectionData.editLayout).match('w/ Text') == null) {
            doogmaVal = '';
        }

        if ($.inArray(doogmaKey, globalDoogmaKeys) >= 0) {
            for (var printMethod in productPage.getActiveProductAttributes().selectionData.designerValues) {
                updateDoogmaKeyVal(printMethod, doogmaKey, doogmaVal);
            }
        } else {
            updateDoogmaKeyVal(self.getActiveProductAttributes().selectionData.printMethod, doogmaKey, doogmaVal);
        }

        var designerOptions = self.getActiveProductAttributes().selectionData.designerValues[self.getActiveProductAttributes().selectionData.printMethod];
        if (typeof designerOptions == 'undefined') { return false; }

        if(doogmaKey != null) {
            designerOptions[doogmaKey] = doogmaVal;
        }
        self.updateDoogmaSettings(false);
    },

    setInitialDoogmaValues: function() {
        var self = this;

        //default for all designers
        var designerValues = {
            foilStamping : $.extend({}, window.doogmaParameters),
            PMS : $.extend({}, window.doogmaParameters),
            FOUR_COLOR : $.extend({}, window.doogmaParameters),
            embossing : $.extend({}, window.doogmaParameters)
        };

        // Add Defaults for each printed type
        designerValues.foilStamping.textline1 = '';
        designerValues.foilStamping.textline1font = 'minionpro';
        designerValues.foilStamping.textline1x = '298';
        designerValues.foilStamping.textline1y = '148';
        designerValues.foilStamping.textline1width = 211;
        designerValues.foilStamping.textline1height = 100;
        designerValues.foilStamping.textline1fontsize = '34';
        designerValues.foilStamping.imageuploadx = 298;
        designerValues.foilStamping.imageuploady = 61;
        designerValues.foilStamping.imageuploadwidth = 211;
        designerValues.foilStamping.imageuploadheight = 82;
        designerValues.foilStamping.showlogo = 'folders-default';
        designerValues.foilStamping.chooseartlocation = '15squareincharttopcenter';

        designerValues.embossing.showlogo = 'none';

        designerValues.FOUR_COLOR.showlogo = 'none';

        designerValues.PMS.showlogo = 'none';

        self.getActiveProductAttributes().selectionData.designerValues = designerValues;
    },

    updateDoogmaSettings: function(finalize) {
        var self = this;

        if(finalize) {
            //
        }

        var designerOptions = self.getActiveProductAttributes().selectionData.designerValues[self.getActiveProductAttributes().selectionData.printMethod];
        window.doogmaParameters = $.extend({}, designerOptions);
        var doogmaChangeEvent = new CustomEvent('doogmaparameterschange', {
            detail: {},
            bubbles: true,
            cancelable: true
        });
        
        Object.keys(window.doogmaParameters).forEach(function(key, index) {
            doogmaChangeEvent.detail[key] = window.doogmaParameters[key];
        });

        document.dispatchEvent(doogmaChangeEvent);
    },

    //certain parameters are controlled by doogma, these need to be sent back to folders object
    saveRestrictedDoogmaValues: function() {
        var self = this;

        self.updateDoogmaByValue('doogmasaveddesign', window.doogmaParameters.doogmasaveddesign);
        self.updateDoogmaByValue('doogmaprintready', window.doogmaParameters.doogmaprintready);
        self.updateDoogmaByValue('doogmauploadedimage', window.doogmaParameters.doogmauploadedimage);
        self.updateDoogmaByValue('doogmathumb', window.doogmaParameters.doogmathumb);
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

    createFileList: function() {
        var self = this;
        if (typeof self.getActiveProductAttributes().fileUpload != 'undefined') {
            this.uploadedFiles = 'jqs-uploadedfiles';

            $('.jqs-uploadedfiles').removeClass('hidden').empty();
            var completedFileArray = [];
            var myList = $('<ul />').addClass('textLeft noMargin noPadding');

            $.each(self.getActiveProductAttributes().fileUpload, function(i) {
                var fileName = self.getActiveProductAttributes().fileUpload[i].name;

                myList.append(
                    $('<li />').attr('data-filename', fileName).append(
                        fileName
                    ).append(
                        $('<i/>').addClass('fa fa-trash-o').on('click', function() {
                            self.uploadDelete($(this).parents('[data-filename]').text());
                        })
                    )
                );
            });

            $('.' + self.uploadedFiles).append(myList).append(
                $('<div />').addClass('marginTop10').html('The folder to the left does NOT represent your uploaded artwork. After checkout, you\'ll receive an emailed proof to approve within 1 business day.')
            );
        }
    },

    uploadDelete: function(fileName){
        $('[data-filename="' + fileName + '"]').remove();
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

            recentlyVisitedProducts.productList[(typeof (recentlyVisitedProducts.productList) != 'undefined' ? (recentlyVisitedProducts.productList).length : 0)] = {
                'productId': productPage.getActiveProductAttributes().selectionData.productId,
                'color' : productPage.getActiveProductAttributes().colorDesc,
                'paperTexture' : productPage.getActiveProductAttributes().paperTexture,
                'paperWeight' : productPage.getActiveProductAttributes().paperWeight,
                'name' : productPage.getActiveProductAttributes().productName,
                'href' : window.location.href,
                'minimumQuantity' : productPage.getActiveProductAttributes().selectionData.quantity,
                'price' : productPage.getActiveProductAttributes().minimumPrice,
                'rating' : productPage.getActiveProductAttributes().rating
            };

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

            // localStorage.recentlyVisitedProducts = JSON.stringify(recentlyVisitedProducts);
            localStorageEnabled ? localStorage.recentlyVisitedProducts = JSON.stringify(recentlyVisitedProducts): '';
        }
    }
}

/**
 * Folders.com Product Object
 * @constructor
 */
function Product() {
    ProductPage.call(this);

    /**
     * Product attributes
     * @type {{}}
     */
    var product = {};

    /**
     * Get the product object
     * @returns {{}}
     */
    this.getProductAttributes = function() {
        return product;
    };

    /**
     * Set data to product
     * @param data
     */
    this.setProductAttributes = function(data) {
        $.extend(true, product, data);
    };
}

Product.prototype = Object.create(ProductPage.prototype, {
    constructor: Product,
    /**
     * Return the product id of Product object
     * @returns {String}
     */
    returnProductId: function () {
        return this.getProduct().productId;
    }
});

$(function() {
    productPage.initPageActions();
    productPage.rebuildSelections();
    productPage.updateMode();
    productPage.loadProductStep(productPage.getActiveProductAttributes().productStep);

    if (typeof getUrlParameters('orderId', '', false) != 'undefined' && getUrlParameters('orderId', '', false) != false) {
        bnRevealLoad('productReview');
    }
    // Color selection
    $('.colorFilterList *[data-color-group]').click(function(){
        $('.colorFilterList *[data-color-group]').removeClass('activeColor');
        $(this).addClass('activeColor');
        $('.colorFilterClear').removeClass('hidden');
        var colorGroup = $(this).data('color-group');
        $('.colorList .selectListItem').removeClass('hidden');
        if(typeof colorGroup !== 'undefined'){
            $('.colorList .selectListItem').each(function(){
                var color = $(this).data('color-groups');
                var colorArray = color.split(' ');
                if($.inArray(colorGroup, colorArray) === -1 && colorGroup !== 'any'){
                    $(this).addClass('hidden');
                }
            });
        }
    });

    // Clear color selection
    $('.colorFilterClear').click(function(){
        $('.colorList .selectListItem').removeClass('hidden');
        $('.colorWheel *[data-color-group]').removeClass('activeColor');
        $(this).addClass('hidden');
    });

    $('.jqs-selectDesignMethod').click(function(){
        var designMethod = $(this).attr('data-design-method-desc');
        var designMethodId = $(this).attr('data-design-method');
        $('.jqs-selectDesignMethod').removeClass('dmSelected');
        $(this).addClass('dmSelected');
        $(this).parents('.sidebarPanel').animate({
            'opacity': '0',
            'left': '100%'
        }, 150, 'linear');

        if (designMethodId == 'dmReuseArtwork') {
            $('#dropdown-reuseLogin').animate({
                'opacity': '1',
                'left': ($('.productSidebar').offset().left / $(window).width() * 100) + '%'
            }, 150, 'linear');
        }

        $('.onlineDesigner').find('strong').html(designMethod);

        productPage.updatePageContent({
            folderDesign: {
                designMethod: true,
                designLayout: (designMethodId == 'dmOnlineDesigner' ? true : false),
                designUpload: (designMethodId == 'dmUploadComplete' ? true : false),
                designPreviousArtwork: (designMethodId == 'dmReuseArtwork' ? true : false),
                designServiceRequest: (designMethodId == 'dmUseOurServices' ? true : false)
            }
        });
    });
});
















$(document).ready(function(){

    // // New slider for Product Images

    // Fade speed
    var speed = 300;

    // Add initial active class
    $('.slide').first().addClass('active');

    // Hide all
    $('.slide').hide();

    // Show first slide
    $('.active').show();

    $('.next-slide').on('click', function() { changeSlide(++currentIndex); });

    $('.previous-slide').on('click', function() { changeSlide(--currentIndex); });

    var imageIndex = $('.jqs-prodslides').children().size();
    var currentIndex = 1;

    function changeSlide(index) {
        if(imageIndex < index) {
            index = 1;
        } else if(index < 1) {
            index = imageIndex;
        }
        currentIndex = index;

        $('.active').fadeOut().removeClass('active');
        $($('.jqs-prodslides').children()[--index]).hide().addClass('active').fadeIn();

        var sliderDotCheck = $('.sliderDots i').removeClass('fa-circle').addClass('fa-circle-o');
        $(this).addClass('fa-circle');
        $(sliderDotCheck[index]).addClass('fa-circle');
    }

    $('.sliderDots i').on('click', function(){
        changeSlide($(this).attr('data-key'));
    });

    // Fixed table header on samples modal
    $('.samplesTableWrap').scroll(function(){
        var translate = 'translate(0,' + this.scrollTop + 'px)';
        $(this).find('thead').css('transform', translate);
    });

    // Ensure you can't scroll the page
    // while hovering over sidebar panel
    $('.sidebarPanel').hover(function(){
        clearTimeout($(this).data('timeoutId'));
        $('body').addClass('scrollJack');
    }, function(){
        var someElement = $(this),
            timeoutId = setTimeout(function(){
                $('body').removeClass('scrollJack');
            }, 50);
        someElement.data('timeoutId', timeoutId);
    });

    $('.jqs-selectCardSlits').click(function(){
        $('.jqs-selectCardSlits').removeClass('csSelected');
        $(this).addClass('csSelected');
        var cardSlits = $(this).attr('data-card-slits');
        if(cardSlits == 'Yes'){
            $('.cardSlitOptions').show();
        } else {
            $('.cardSlitOptions').hide();
        }
    });

    $('.jqs-directionalButtonSelect').click(function(){
        $(this).toggleClass('activatedSelect');
        $(this).next('.jqs-hiddenDropdown').slideToggle(200);
    });
    $(window).click(function(){
        $('.jqs-directionalButtonSelect').removeClass('activatedSelect');
        $('.jqs-hiddenDropdown').slideUp(200);
    });
    $('.jqs-hiddenDropdown > div').click(function(){
        var updatedVal = $(this).html();
        var updatedFont = $(this).css('font-family');
        $(this).parents('.jqs-hiddenDropdown').prev('.jqs-directionalButtonSelect').html(updatedVal).css('font-family', updatedFont);
        $('.jqs-directionalButtonSelect').removeClass('activatedSelect');
        $('.jqs-hiddenDropdown').slideUp(200);
    });
    $('.sidebarPanel').on('click', '.jqs-directionalButtonSelect, .jqs-hiddenDropdown', function(e){
        e.stopPropagation();
    });

    $(function(){
        $('#sizeSlider').slider();
        $('#datepicker').datepicker();
    });

    $('.dragAndDrop').click(function(){
        $(this).hide();
        $('.editImage').show();
    });

    $('.onlineDesignerAlternate').click(function(){
        $('.dmOnlineDesigner1, .dmOnlineDesigner2').toggleClass('hidden');
    });

    $('.folderDesignOption, .folderDesignOption2').click(function(){
        $('.folderDesignOption, .folderDesignOption2').removeClass('doSelected');
        $(this).addClass('doSelected');
    });

    $(document).mousedown(function(e){
        var container = $('.sidebarPanel');
        if (!container.is(e.target) && container.has(e.target).length === 0){
            container.animate({
                'opacity': '0',
                'left': '100%'
            }, 150, 'linear');

            $('.product-images').removeClass('hidden');
            $('#doogma').addClass('hidden');
        }
    });

    // Product Specs / Slit Options Button Click
    $('.slitOptionsButton').on('click', function(){
        $('.slitOptions').removeClass('hidden');
        $('.slitOptionsButton').css('background-color','#0d3a5e');
        $('.productSpecsRow').addClass('hidden');
        $('.productSpecsButton').css('background-color', '#707070');
    });

    $('.productSpecsButton').on('click', function() {
        $('.productSpecsRow').removeClass('hidden');
        $('.productSpecsButton').css('background-color', '#0d3a5e');
        $('.slitOptions').addClass('hidden');
        $('.slitOptionsButton').css('background-color', '#707070');
    });

    $('[bns-hidewhenclicked').on('click', function(){
        $(this).remove();
    });
});

function emailThisQuoteSubmission() {
    var email = $('[name="quoteEmailAddress"]').val();
    var googleAnalyticsName = 'EM Quote Request';

    if (typeof email == 'undefined' || email == '') {
        email = $('[name="quoteEmailAddressWithContact"]').val();
        googleAnalyticsName = 'PP Quote Request';
        quoteCallback = function(quoteId) {
            $('#quoteId').html(quoteId);
            $('[bns-folderdesignquoterequestcontent]').addClass('hidden');
            $('[bns-quotecompleted]').removeClass('hidden');
        }
    }
    var emailThisQuoteData = [{'Email': email}];
    emailThisQuoteData.push({'Color': productPage.getActiveProductAttributes().colorDesc});

    if (productPage.getActiveProductAttributes().selectionData.printMethod == 'PMS') {
        emailThisQuoteData.push({'Print Method': 'Spot Color'});
        emailThisQuoteData.push({'Spot Color': productPage.getActiveProductAttributes().selectionData.spotColor});
        if (typeof productPage.getActiveProductAttributes().selectionData.spotColorText1 != 'undefined') {
            emailThisQuoteData.push({'Spot Color Text 1': productPage.getActiveProductAttributes().selectionData.spotColorText1});
        }
        if (typeof productPage.getActiveProductAttributes().selectionData.spotColorText2 != 'undefined') {
            emailThisQuoteData.push({'Spot Color Text 2': productPage.getActiveProductAttributes().selectionData.spotColorText2});
        }
        if (typeof productPage.getActiveProductAttributes().selectionData.spotColorText3 != 'undefined') {
            emailThisQuoteData.push({'Spot Color Text 3': productPage.getActiveProductAttributes().selectionData.spotColorText3});
        }
    } else if (productPage.getActiveProductAttributes().selectionData.printMethod == 'FOUR_COLOR') {
        emailThisQuoteData.push({'Print Method': 'Full Color'});
    } else {
        productPage.getActiveProductAttributes().selectionData.foilColor;
    }

    emailThisQuoteData.push({'Pocket': productPage.getActiveProductAttributes().selectionData.pocket});
    emailThisQuoteData.push({'Card Slits': productPage.getActiveProductAttributes().selectionData.cardSlits});
    emailThisQuoteData.push({'Attachments': productPage.getActiveProductAttributes().selectionData.attachments});
    emailThisQuoteData.push({'Quantity': productPage.getActiveProductAttributes().selectionData.quantity});
    emailThisQuoteData.push({'Price': $('#quantityPriceSelection .selPrice .priceDisplay').html()});
    emailThisQuoteData.push({'SKU': productPage.getActiveProductAttributes().selectionData.productId});

    $.ajax({
        type: 'POST',
        url: '/' + websiteId + '/control/emailThisQuote',
        data: {
            email: email,
            quoteData: JSON.stringify(emailThisQuoteData)
        },
        dataType: 'json',
        cache: true,
        async: true
    }).done(function (response) {
        $('form[name="emailThisQuote"]').addClass('hidden');
        $('[bns-emailthisquoteemailsubmissiontext]').removeClass('hidden').find('span').html(email);

        window.setTimeout(function() {
            bnRevealClose(function() {
                $('form[name="emailThisQuote"]').removeClass('hidden');
                $('[bns-emailthisquoteemailsubmissiontext]').addClass('hidden').find('span').html('');
                $('[name="quoteEmailAddress"]').val('');
            });
        }, 5000);

        $().addOrUpdateBrontoEmail(email, function(success) {
            if(success) {
                response = 'success';
            }
            else {
                response = 'bronto_error';
            }
        }, 'emailThisQuote');

        $.ajax({
            type: "POST",
            url: '/folders/control/quoteRequestSubmission',
            data: {
                email: email,
                additonalInfo: JSON.stringify(emailThisQuoteData),
                webSiteId: 'folders',
                productStyle: productPage.getActiveProductAttributes().selectionData.vendorProductId,
                source: 'emailThisQuote',
                firstName: $('input[name="quoteFirstName"]').val(),
                lastName: $('input[name="quoteLastName"]').val(),
                phone: $('input[name="quotePhoneNumber"]').val(),
                companyName: $('input[name="quoteCompanyName"]').val(),
                stateProvinceGeoId: $('select[name="quoteState"]').val(),
                postalCode: $('input[name="quoteZip"]').val()
            },
            dataType:'json',
            cache: false
        }).done(function(data) {
            if (data.success) {
                GoogleAnalytics.trackEvent(googleAnalyticsName, 'Finish', data.quoteId);

                if (typeof quoteCallback == 'function') {
                    quoteCallback(data.quoteId);
                }
            } else {
                GoogleAnalytics.trackEvent(googleAnalyticsName, 'Finish', 'Error');
            }
        });
    });
}