/**
 * Envelopes.com Product Object
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

/**
 * Envelopes.com Product Page
 * @constructor
 */
function ProductPage() {
    /**
     * Array of all products on the page
     * Sequence of products are the sequence in which they should be displayed
     * @type {Array}
     */
    var self = this;
    this.documentPageCount = 1;
    this.productOptions = {};
    var products = [];

    this.activeProductIndex = products.length;

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
    this.savedFileTemplate = $('.jqs-saved-file-template');
}

ProductPage.prototype = {
    constructor: ProductPage,
    /**
     * Create a new Product object and insert into array
     * @param id
     */
    getActiveProductAttributes: function () {
        return this.getProducts()[this.activeProductIndex].getProductAttributes();
    },

    setActiveProductAttributes: function (data) {
        this.getProducts()[this.activeProductIndex].setProductAttributes(data);
    },

    getActiveProductIndex: function () {
        return this.activeProductIndex;
    },

    setActiveProductIndex: function (index) {
        this.activeProductIndex = index;
    },

    getActiveProduct: function () {
        return products[this.activeProductIndex];
    },

    addProduct: function (data) {
        var product = new Product();
        $.extend(true, product.getProductAttributes(), data);
        this.getProducts().push(product);
    },

    setProductOptions: function(options) {
        this.productOptions = $.parseJSON(options);
    },

    getProductOptions: function() {
        return this.productOptions;
    },

    initPageActions: function() {
        $.initializeProductOptions();
        $.updateProductPrice();
        $.getEstimatedCost();
        $.updateDocument();
        var returnAddress = productPage.getActiveProductAttributes().returnAddress;
        $('[name="returnAddressName"]').val(returnAddress.rtnName);
        $('[name="returnAddressCompany"]').val(returnAddress.rtnOrganization);
        $('[name="returnAddressAddress1"]').val(returnAddress.rtnAddress1);
        $('[name="returnAddressAddress2"]').val(returnAddress.rtnAddress2);
        $('[name="returnAddressCity"]').val(returnAddress.rtnCity);
        $('[name="returnAddressState"]').val(returnAddress.rtnState);
        $('[name="returnAddressZip"]').val(returnAddress.rtnZip);
        $.updateReturnAddressPreview();
        $.checkForSlideOut();
    }
};

(function () {
    var steps = ['selectproduct', 'selectdocument', 'addressing', 'proofproduct'];
    var stepIndex = 0;
    var addressIndex = 0;
    var saveAndExitFlag = false;
    var progressBarTimeOutId = 0;
    var progressBarTimeOut = 0;
    var progressBarEvent = 'Uploading the Document';
    var progressModal = document.getElementById('documentModal');
    var progressStatus = '';

    $.extend({
        getEstimatedCost: function () {
            productPage.documentPageCount = productPage.getActiveProductAttributes().numberOfPages;
            $('.quantityPrice').spinner(true, false, 50, 3, null, 'Updating...', null, 20000);
            var url = '/' + websiteId + '/control/getCostEstimate';
            $.ajax({
                type: 'GET',
                url: url,
                data: {
                    'documentClass' : productPage.getActiveProductAttributes().documentClass,
                    'layout'        : $.getOptionValue('layoutSelection'),
                    'productionTime': $.getOptionValue('productionTime'),
                    'envelope'      : $.getOptionValue('envelope'),
                    'color'         : $.getOptionValue('printColor'),
                    'paperType'     : $.getOptionValue('paperColor'),
                    'printOption'   : $.getOptionValue('printOption'),
                    'mailClass'     : $.getOptionValue('mailClass'),
                    // 'quantity'      : productPage.getActiveProductAttributes().addressingData.data.length > 0 ? productPage.getActiveProductAttributes().addressingData.data.length : $.getOptionValue('quantity'),
                    'quantity'      : $.getOptionValue('quantity').replace(/[^0-9]/, ''),
                    'quantityList'  : '50,100,250,500,1000,2500,5000,10000',
                    'numberOfPages' : productPage.getActiveProductAttributes().numberOfPages
                },
                dataType: 'json',
                cache: true
            }).done(function (data) {
                if (data.success && data.price !== '') {
                    $('.quantityPrice').spinner(false);
                    var selectedQuantity = $('.selectListParent[selection-name="quantity"]').data('value');
                    var quantityList = $.parseJSON(data.price);
                    productPage.setActiveProductAttributes({'estimatedPrice': quantityList[selectedQuantity]});
                    $.buildQuantityList(quantityList);
                    $.updateProductPrice();
                    $.bindSelectListOptionClickEvent();
                } else {
                    alert("something went wrong..");
                }
            }).fail(function () {
                alert("something went wrong..");
            });
        },

        getJobNumber: function () {
            if(saveAndExitFlag && productPage.getActiveProductAttributes().partyId === '') {
                $.loginEvent(true);
            } else {
                var url = '/' + websiteId + '/control/getJobNumber';
                if(!saveAndExitFlag) {
                    $.hasJobModified();
                }
                $.ajax({
                    type: 'GET',
                    url: url,
                    data: {jobNumber: productPage.getActiveProductAttributes().jobNumber},
                    dataType: 'json',
                    cache: false
                }).done(function (data) {
                    if (data.success === true) {
                        productPage.setActiveProductAttributes({'jobNumber': data.jobNumber});
                        $.saveJob();
                        $.getSaveJobProgress(data.jobNumber);
                    } else {
                        progressBarEvent = 'Completed';
                        $.clearProgressBar();
                        alert("An error occurred while saving the job. Please try again.");
                    }
                }).fail(function () {
                    progressBarEvent = 'Completed';
                    $.clearProgressBar();
                    alert("An error occurred while saving the job. Please try again...");
                });
            }
        },

        saveJob: function () {
            var url = '/' + websiteId + '/control/saveJob';
            $.ajax({
                type: 'POST',
                url: url,
                data: $.getJobData(),
                dataType: 'json',
                cache: false,
                timeout: '120000'
            }).done(function (data) {

            });
        },

        getSaveJobProgress: function (jobNumber) {
            var url = '/' + websiteId + '/control/getSaveJobStatus';
            $.ajax({
                type: 'GET',
                url: url,
                data: {'jobNumber' : jobNumber},
                dataType: 'json',
                cache: false
            }).done(function (data) {
                if (data.success === true) {
                    var status = data.result.progressStatus;
                    if (status.startsWith("ERROR")) {
                        $.updateErrorResponse(status, data.result.resultData);
                    } else if (status === 'COMPLETED_SUCCESSFULLY') {
                        $.updateJobDetails(data.result.resultData);
                    } else {
                        $.updateProgressBar(status, jobNumber);
                    }
                }
            });
        },

        updateErrorResponse : function (status, response) {
            progressBarEvent = 'Completed';
            $.clearProgressBar();
            alert(status);
        },

        updateJobDetails : function (result) {
            if (saveAndExitFlag) {
                window.location = '/' + websiteId + '/control/directMailingJobs';
            } else {
                progressBarEvent = 'Completed';
                $.clearProgressBar();
                productPage.setActiveProductAttributes({'partyId': result.partyId});
                productPage.setActiveProductAttributes({'jobId': result.jobId});
                productPage.setActiveProductAttributes({'jobNumber': result.jobNumber});
                productPage.setActiveProductAttributes({'documentId': result.documentId});
                productPage.setActiveProductAttributes({'documentName': result.contentName});
                productPage.setActiveProductAttributes({'documentPath': result.contentPath});
                productPage.setActiveProductAttributes({'addressId': result.addressId});
                //productPage.setActiveProductAttributes({'addressListCreatedTimestamp': result.addressListCreatedTimestamp});
                productPage.setActiveProductAttributes({'hasAddressModified': false});
                if (typeof result.price !== 'undefined') {
                    productPage.setActiveProductAttributes({'price': result.price});
                    delete productPage.getActiveProductAttributes().estimatedPrice;
                    $.updateProductPrice();
                }
                if (typeof result.proof !== 'undefined') {
                    $('.downloadProofButton').attr('id', result.proof);
                }
                if (productPage.getActiveProductAttributes().fileUpload.length !== 0) {
                    productPage.getActiveProductAttributes().fileUpload.splice(0, 1);
                }
            }
        },

        updateProgressBar: function (status, jobNumber) {
            if (!saveAndExitFlag) {
                switch (status) {

                    case 'DOCUMENT_UPLOAD_COMPLETE':
                        if (progressStatus !== 'DOCUMENT_UPLOAD_COMPLETE') {
                            var hasAddressModified = productPage.getActiveProductAttributes().hasAddressModified;
                            if (hasAddressModified) {
                                progressBarEvent = 'Merging the Addresses';
                            } else {
                                progressBarEvent = 'Updating Order Details';
                            }
                            $.clearProgressBar();
                        }
                        break;
                    case 'ADDRESS_LIST_COMPLETE':
                        if (progressStatus !== 'ADDRESS_LIST_COMPLETE') {
                            progressBarEvent = 'Updating Order Details';
                            $.clearProgressBar();
                        }
                        break;
                    case 'JOB_CREATED':
                        if (progressStatus !== 'JOB_CREATED') {
                            progressBarEvent = 'Calculating Actual Cost';
                            $.clearProgressBar();
                        }
                        break;
                    case 'PRICE_CALCULATED':
                        if (progressStatus !== 'PRICE_CALCULATED') {
                            progressBarEvent = 'Generating Proof';
                            $.clearProgressBar();
                        }
                        break;
                }
            }
            progressStatus = status;
            setTimeout(function() {$.getSaveJobProgress(jobNumber);}, 2000);
        },

        isUserLoggedIn: function(callback) {
            var url = '/' + websiteId + '/control/getLoggedInPartyId';
            $.ajax({
                type: 'POST',
                url: url,
                data: {},
                dataType: 'json',
                cache: false
            }).done(function (data) {
                if (data.success === true) {
                    productPage.setActiveProductAttributes({'partyId' : data.partyId});
                    callback();
                }
            });
        },

        progressBar: function (event) {
            $(progressModal).find("p").text(event);
            progressModal.style.display = "block";
            var elem = document.getElementById("documentProgressBar");
            elem.style.width = '1%';
            $(elem).text('');
            var width = 1;
            progressBarTimeOutId = setInterval(frame, progressBarTimeOut);
            function frame() {
                if (width >= 90) {
                    if (event == 'Processing the Document'  || event == 'Merging the Addresses' || event == 'Generating Proof') {
                        clearInterval(progressBarTimeOutId);
                    } else {
                        width++;
                        $(elem).text(width + '%');
                        elem.style.width = width + '%';
                        if (width == 100){
                            $.clearProgressBar();
                        }
                    }
                } else {
                    width++;
                    $(elem).text(width + '%');
                    elem.style.width = width + '%';
                }
            }
        },

        clearProgressBar: function () {

            clearInterval(progressBarTimeOutId);
            progressModal.style.display = "none";
            $.progressBarTimingEvent();
        },

        hasJobModified: function () {
            var hasAddressModified = productPage.getActiveProductAttributes().hasAddressModified;
            if (productPage.getActiveProductAttributes().fileUpload.length === 0) {
                if (hasAddressModified) {
                    progressBarEvent = 'Merging the Addresses';
                } else {
                    progressBarEvent = 'Updating Order Details';
                }
            }
            $.progressBarTimingEvent();
        },

        progressBarTimingEvent: function () {
            switch (progressBarEvent) {
                case 'Uploading the Document':
                    progressBarTimeOut = 250;
                    $.progressBar(progressBarEvent);
                    progressBarEvent = 'Validating the Document';
                    break;
                case 'Validating the Document':
                    progressBarTimeOut = 350;
                    $.progressBar(progressBarEvent);
                    progressBarEvent = 'Processing the Document';
                    break;
                case 'Processing the Document':
                    progressBarTimeOut = 70;
                    $.progressBar(progressBarEvent);
                    progressBarEvent = 'Merging the Addresses';
                    break;
                case 'Merging the Addresses':
                    progressBarTimeOut = 100;
                    $.progressBar(progressBarEvent);
                    progressBarEvent = 'Updating Order Details';
                    break;
                case 'Updating Order Details':
                    progressBarTimeOut = 100;
                    $.progressBar(progressBarEvent);
                    progressBarEvent = 'Calculating Actual Cost';
                    break;
                case 'Calculating Actual Cost':
                    progressBarTimeOut = 50;
                    $.progressBar(progressBarEvent);
                    progressBarEvent = 'Generating Proof';
                    break;
                case 'Generating Proof':
                    progressBarTimeOut = 10;
                    $.progressBar(progressBarEvent);
                    progressBarEvent = 'Completed';
                    break;
                case 'Completed':
                    progressBarEvent = 'Uploading the Document';
                    progressStatus = '';
                    break;
            }

        },

        loadSavedDocuments: function () {
            var url = '/' + websiteId + '/control/getSavedDocuments';
            $.ajax({
                type: 'POST',
                url: url,
                data: {'productId' : productPage.getActiveProductAttributes().productId},
                dataType: 'json',
                cache: false
            }).done(function (data) {
                if (data.success === true) {
                    $.each(data.documents, function(index, document) {
                        var el = productPage.savedFileTemplate.clone();
                        el.removeClass('jqs-saved-file-template').removeClass("hidden");
                        el.find('input').val(document.documentId).attr("contentPath", document.contentPath);
                        el.find('.jqs-content-name').html(document.contentName);
                        el.find('.jqs-updated-time').html(document.lastUpdatedStamp);
                        $('.jqs-upload-body').append(el);
                    });
                    $('.jqs-not-logged-in').addClass('hidden');
                    $('.jqs-logged-in').removeClass('hidden');
                    $.documentOnCheckEvent();
                }
            });
        },

        loginEvent: function(saveAndExitFlag) {
            var self = this;
            if(saveAndExitFlag) {
                $('.jqs-hidden-login-button').trigger('click', self.isUserLoggedIn.bind(this, self.updateJobDetails));
            } else {
                $('.jqs-hidden-login-button').trigger('click', self.isUserLoggedIn.bind(this, self.loadSavedDocuments));
            }

        },

        bindLoginEvent: function () {

            $('[bns-login]').on('click', function () {
                $.loginEvent();
            });

            var self = this;
            $('.jqs-login-button').on('click', function(e) {
                e.stopPropagation();
                $('.jqs-hidden-login-button').trigger('click', self.isUserLoggedIn.bind(this, self.loadSavedDocuments));

            });
        },

        optionChangedEvent: function(selectionName) {
            productPage.setActiveProductAttributes($.getSelectedProductOptions());
            this.initializeProductOptions();
            if(selectionName != "quantity") {
                $.getEstimatedCost();

            }
        },

        bindSelectListOptionClickEvent: function() {
            $('[bns-selection]').off('click.bnsSelection').on('click.bnsSelection', function() {
                if (typeof $(this).attr('selection-selected') === 'undefined' || (typeof $(this).attr('selection-selected') !== 'undefined' && $(this).attr('selection-selected') === 'false')) {
                    $('[bns-selection]').each(function () {
                        $(this).removeAttr('selection-selected');
                    });
                    $(this).attr('selection-selected', 'true');
                    $('.sidebarPanel h4 .fa').trigger('click.closeSidebar');
                }

                var selectionName = $(this).attr('selection-target');
                var selectedText;
                var selectedPrice = $(this).data('price');
                var selectedPriceEach = $(this).data('priceeach');
                var pricingData = productPage.getActiveProductAttributes().estimatedPrice;
                selectionName == 'quantity' ? selectedText = $(this).data('qty').toString() : selectedText = $(this).find('div div').text();
                var el = $('.productSideBarSection').find('[selection-name="' + selectionName + '"]');
                if (selectionName === 'quantity') {
                    $(el).find('.placeholder').text(selectedText + ' Qty.');
                    pricingData.totalPrice = selectedPrice;
                    pricingData.unitPrice = selectedPriceEach;
                    pricingData.quantity = selectedText;
                    $.updateProductPrice();
                } else {
                    $(el).text(selectedText);
                }
                if(selectionName == 'quoteQuantity') {
                    $(el).attr('bns-dmqty', $(this).attr('selection-value'))
                }
                if(selectionName === 'layoutSelection' && selectedText.trim() === "Address on First Page") {
                    $('[bns-updateTemplateLink]').attr('href', '<@ofbizUrl>/downloadFile</@ofbizUrl>?filePath=/hot-deploy/html/webapp/html/files/directMailing/c2m-letterFirst.zip&amp;downLoad=Y');
                } else if (selectionName === 'layoutSelection' && selectedText.trim() === "Picture and Address First Page") {
                    $('[bns-updateTemplateLink]').attr('href', '<@ofbizUrl>/downloadFile</@ofbizUrl>?filePath=/hot-deploy/html/webapp/html/files/directMailing/c2m-letterPicture.zip&amp;downLoad=Y');
                } else if (selectionName === 'layoutSelection' && selectedText.trim() === "Address Back Page") {
                    $('[bns-updateTemplateLink]').attr('href', '<@ofbizUrl>/downloadFile</@ofbizUrl>?filePath=/hot-deploy/html/webapp/html/files/directMailing/c2m-letterBack.zip&amp;downLoad=Y');
                } else {
                    $('[bns-updateTemplateLink]').attr('href', '<@ofbizUrl>/downloadFile</@ofbizUrl>?filePath=/hot-deploy/html/webapp/html/files/directMailing/c2m-letterSeparate.zip&amp;downLoad=Y');
                }
                $(el).data('value', selectedText.trim());
                $.optionChangedEvent(selectionName);
            });
        },

        initializeProductOptions: function() {
            var productOptions = productPage.getProductOptions();
            var selectedLayoutOption = productPage.getActiveProductAttributes().layout;
            var selectedProductionTimeOption = productPage.getActiveProductAttributes().productionTime;
            var selectedEnvelopeOption = productPage.getActiveProductAttributes().envelope;
            var selectedPrintColorOption = productPage.getActiveProductAttributes().color;
            var selectedPaperColorOption = productPage.getActiveProductAttributes().paperType;
            var selectedPrintOption = productPage.getActiveProductAttributes().printOption;
            var selectedMailClassOption = productPage.getActiveProductAttributes().mailClass;
            var optionValues = [];

            /* DOCUMENT_CLASS */
            productPage.setActiveProductAttributes({'documentClass' : productOptions['DOCUMENT_CLASS']});

            /*LAYOUT OPTIONS*/
            var layoutOptions = productOptions['LAYOUT_OPTIONS'];
            var layoutOption = layoutOptions[selectedLayoutOption];
            for(var prop in layoutOptions) {
                optionValues.push(prop);
            }
            if(typeof layoutOption === 'undefined' && optionValues.length > 0) {
                selectedLayoutOption = optionValues[0];
                layoutOption = layoutOptions[selectedLayoutOption];
            }
            this.initializeSelectListItems($('#sidebar-layoutOptions'), 'layoutSelection',  optionValues, selectedLayoutOption);
            if(selectedLayoutOption === 'Address on Separate Page') {
                $('.jqs-enhancedImage,.jqs-imagehero').attr('src', '/html/img/product/C2M-L85X11_address_on_separate_page.png');
            } else if(selectedLayoutOption === 'Address on First Page') {
                $('.jqs-enhancedImage,.jqs-imagehero').attr('src', '/html/img/product/C2M-L85X11_address_on_first_page.png');
            } else if(selectedLayoutOption === 'Picture and Address First Page') {
                $('.jqs-enhancedImage,.jqs-imagehero').attr('src', '/html/img/product/C2M-L85X11_picture_and_address_on_first_page.png');
            } else if(selectedLayoutOption === 'Address Back Page') {
                $('.jqs-enhancedImage,.jqs-imagehero').attr('src', '/html/img/product/C2M-L85X11_address_on_back_page.png');
            } else if(selectedLayoutOption === 'Notecard -Single Sided') {
                $('.jqs-enhancedImage,.jqs-imagehero').attr('src', '/html/img/product/C2M-N425X55_single_sided.png');
            } else if(selectedLayoutOption === 'Notecard - Folded') {
                $('.jqs-enhancedImage,.jqs-imagehero').attr('src', '/html/img/product/C2M-FN425X55_folded.png');
            }

            /*PRODUCTION_TIME OPTIONS*/
            var productionTimeOptions = layoutOption['PRODUCTION_TIME_OPTIONS'];
            var productionTimeOption = productionTimeOptions[selectedProductionTimeOption];
            optionValues = [];
            for(var prop in productionTimeOptions) {
                optionValues.push(prop);
            }
            if(typeof productionTimeOption === 'undefined' && optionValues.length > 0) {
                selectedProductionTimeOption = optionValues[0];
                // productionTimeOption = productionTimeOptions[selectedProductionTimeOption];
            }
            this.initializeSelectListItems($('#sidebar-productionTime'), 'productionTime',  optionValues, selectedProductionTimeOption);

            /*ENVELOPE_OPTIONS*/
            var envelopeOptions = layoutOption['ENVELOPE_OPTIONS'];
            var envelopeOption = envelopeOptions[selectedEnvelopeOption];
            optionValues = [];
            for(var prop in envelopeOptions) {
                optionValues.push(prop);
            }
            if(typeof envelopeOption === 'undefined' && optionValues.length > 0) {
                selectedEnvelopeOption = optionValues[0];
                envelopeOption = envelopeOptions[selectedEnvelopeOption];
            }
            this.initializeSelectListItems($('#sidebar-envelope'), 'envelope',  optionValues, selectedEnvelopeOption);

            /*PRINT_COLOR_OPTIONS*/
            var printColorOptions = envelopeOption['PRINT_COLOR_OPTIONS'];
            var printColorOption = printColorOptions[selectedPrintColorOption];
            optionValues = [];
            for(var prop in printColorOptions) {
                optionValues.push(prop);
            }
            if(typeof printColorOption === 'undefined' && optionValues.length > 0) {
                selectedPrintColorOption = optionValues[0];
                printColorOption = printColorOptions[selectedPrintColorOption];
            }
            this.initializeSelectListItems($('#sidebar-printColor'), 'printColor',  optionValues, selectedPrintColorOption);

            /*PAPER_COLOR_OPTIONS*/
            var paperColorOptions = printColorOption['PAPER_COLOR_OPTIONS'];
            var paperColorOption = paperColorOptions[selectedPaperColorOption];
            optionValues = [];
            for(var prop in paperColorOptions) {
                optionValues.push(prop);
            }
            if(typeof paperColorOption === 'undefined' && optionValues.length > 0) {
                selectedPaperColorOption = optionValues[0];
                paperColorOption = paperColorOptions[selectedPaperColorOption];
            }
            this.initializeSelectListItems($('#sidebar-paperColor'), 'paperColor',  optionValues, selectedPaperColorOption);

            /*PRINT_OPTIONS*/
            var printOptions = envelopeOption['PRINT_OPTIONS'];
            var printOption = printOptions[selectedPrintOption];
            optionValues = [];
            for(var prop in printOptions) {
                optionValues.push(prop);
            }
            if(typeof printOption === 'undefined' && optionValues.length > 0) {
                selectedPrintOption = optionValues[0];
                printOption = printOptions[selectedPrintOption];
            }
            this.initializeSelectListItems($('#sidebar-printOption'), 'printOption',  optionValues, selectedPrintOption);

            //console.log(printOption['MAX_PAGES']);
            $.initializePageCounter(printOption['MAX_PAGES']);

            $.initializeQuantity(productPage.getActiveProductAttributes().quantity);

            /*MAIL_CLASS_OPTIONS*/
            var mailClassOptions = envelopeOption['MAIL_CLASS_OPTIONS'];
            var mailClassOption = mailClassOptions[selectedMailClassOption];
            optionValues = [];
            for(var prop in mailClassOptions) {
                optionValues.push(prop);
            }
            if(typeof mailClassOption === 'undefined' && optionValues.length > 0) {
                selectedMailClassOption = optionValues[0];
                mailClassOption = mailClassOptions[selectedMailClassOption];
            }
            this.initializeSelectListItems($('#sidebar-mailClass'), 'mailClass',  optionValues, selectedMailClassOption);

            $.bindSelectListOptionClickEvent();

        },

        initializeSelectListItems: function(selectListEl, parentElementName, selectListOptions, colorTextureBodyInner) {
            var listItemEl = $(selectListEl).find('.jqs-selectListItem:eq(0)').clone();
            $(listItemEl).find('.jqs-selectElement').text('');
            $(selectListEl).find('.jqs-selectListItem').remove();
            $.each(selectListOptions, function(index, option) {
                $(listItemEl).find('.jqs-selectElement').text(option);
                $(selectListEl).find('.colorTextureBodyInner').append($(listItemEl).clone());
            });
            var el = $('.selectListParent[selection-name="' + parentElementName + '"]');
            $(el).data('value', colorTextureBodyInner);
            $(el).text(colorTextureBodyInner);
        },

        productOptionCheckBoxSelectEvent: function () {
            $('.selectListParent').on('click', function () {
                var select = $(this).attr('selection-name');
                var value = $(this).data('value');
                $('[bns-selection]').each(function(){
                    $(this).removeAttr('selection-selected');
                    if($(this).attr('selection-target') == 'quantity') {
                        if ($(this).attr('selection-target') == select && $(this).data('qty') == value) {
                            $(this).attr('selection-selected', 'true');
                        }
                    } else {
                        if ($(this).attr('selection-target') == select && $(this).text().trim() == value) {
                            $(this).attr('selection-selected', 'true');
                        }
                    }
                });
            });
        },

        updateProductPrice: function () {
            var pricingData = typeof productPage.getActiveProductAttributes().estimatedPrice !== 'undefined' ? productPage.getActiveProductAttributes().estimatedPrice : productPage.getActiveProductAttributes().price;
            var totalPrice = pricingData.totalPrice != null || pricingData.totalPrice != undefined ? pricingData.totalPrice : '0.00';
            var quantity = pricingData.quantity != null || pricingData.quantity != undefined ? pricingData.quantity : '50.00';
            var unitPrice = pricingData.unitPrice != null || pricingData.unitPrice != undefined ? pricingData.unitPrice : '0.00';
            $('.headerPrice').html(' <h4>$ ' + numberWithCommas(totalPrice) + ' </h4><p>( ' + numberWithCommas(quantity) + ' Quantity  $ ' + numberWithCommas(unitPrice) + ' each)</p>');
            $('.quantityPrice').html(' <h4>$ ' + numberWithCommas(totalPrice) + ' </h4><p>( ' + numberWithCommas(quantity) + ' Quantity  $ ' + numberWithCommas(unitPrice) + ' each)</p>');
            $('.finalPrice').html(' <h4>$ ' + numberWithCommas(totalPrice) + ' </h4><p>( ' + numberWithCommas(quantity) + ' Quantity  $ ' + numberWithCommas(unitPrice) + ' each)</p>');
        },

        buildQuantityList: function(quantityList) {
            $('[bns-pricelist]').empty();
            $.each(quantityList, function(key, value){
                var selectedQuantity = productPage.getActiveProductAttributes().quantity;
                var selectionSelected;
                if(key == selectedQuantity) {
                    selectionSelected = {
                            'data-qty': key,
                            'selection-target': 'quantity',
                            'bns-selection': '',
                            'selection-selected': 'true',
                            'data-price': formatCurrency(quantityList[key].totalPrice),
                            'data-priceeach': formatCurrency(quantityList[key].unitPrice)
                    }
                } else {
                    selectionSelected = {
                            'data-qty': key,
                            'selection-target': 'quantity',
                            'bns-selection': '',
                            'data-price': quantityList[key].totalPrice,
                            'data-priceeach': formatCurrency(quantityList[key].unitPrice)
                    }
                }
                $('[bns-pricelist]').append(
                    $('<div />').addClass('selectListItem jqs-selectListItem').attr(selectionSelected).append(
                        $('<div />').attr('selection-removeonselect', '').addClass('checkbox').append(
                            $('<span />').addClass('selectCheckbox')
                        )
                    ).append(
                        $('<div />').addClass('width95 quantityDisplay selectElement jqs-selectElement').html(numberWithCommas(key) + ' Qty')
                    ).append(
                        $('<div />').addClass('priceDisplay').html(formatCurrency(quantityList[key].totalPrice))
                    ).append(
                        $('<div />').addClass('jqs-pricePerUnit pricePerUnitDisplay').html(formatCurrency(quantityList[key].unitPrice) + '/each')
                    )
                );
            })
        },

        getSelectedProductOptions: function() {
            return {
                'layout'        : $.getOptionValue('layoutSelection'),
                'productionTime': $.getOptionValue('productionTime'),
                'envelope'      : $.getOptionValue('envelope'),
                'mailClass'     : $.getOptionValue('mailClass'),
                'color'         : $.getOptionValue('printColor'),
                'paperType'     : $.getOptionValue('paperColor'),
                'printOption'   : $.getOptionValue('printOption'),
                'quantity'      : $.getOptionValue('quantity')
            };
        },

        getJobData: function() {

            return {
                'documentClass' : productPage.getActiveProductAttributes().documentClass,
                'layout' : productPage.getActiveProductAttributes().layout,
                'productionTime' : productPage.getActiveProductAttributes().productionTime,
                'envelope' : productPage.getActiveProductAttributes().envelope,
                'color' : productPage.getActiveProductAttributes().color,
                'paperType' : productPage.getActiveProductAttributes().paperType,
                'printOption' : productPage.getActiveProductAttributes().printOption,
                'mailClass' : productPage.getActiveProductAttributes().mailClass,
                'numberOfPages' : productPage.getActiveProductAttributes().numberOfPages,
                'quantity' : productPage.getActiveProductAttributes().quantity,
                'fileUpload' : productPage.getActiveProductAttributes().fileUpload,
                'documentId' : productPage.getActiveProductAttributes().documentId,
                'contentName' : productPage.getActiveProductAttributes().documentName,
                'contentPath' : productPage.getActiveProductAttributes().documentPath,
                'dataGroupId' : productPage.getActiveProductAttributes().dataGroupId,
                'returnAddress' : productPage.getActiveProductAttributes().returnAddress,
                'addressId' : productPage.getActiveProductAttributes().addressId,
                'jobId' : productPage.getActiveProductAttributes().jobId,
                'jobNumber' : productPage.getActiveProductAttributes().jobNumber,
                'estimatedPrice' : productPage.getActiveProductAttributes().estimatedPrice,
                'price' : productPage.getActiveProductAttributes().price,
                //'addressListCreatedTimestamp' : productPage.getActiveProductAttributes().addressListCreatedTimestamp,
                'hasAddressModified' : productPage.getActiveProductAttributes().hasAddressModified,
                'productId' : productPage.getActiveProductAttributes().productId,
                'saveAndExitMode' : saveAndExitFlag,
                'proof' : $('.downloadProofButton').attr('id')

            };
        },


        getOptionValue: function(name) {
            return $('.selectListParent[selection-name="' + name + '"]').data('value');
        },

        getPages: function() {
            return parseInt($('.jqs-numberOfPages').data('value'));
        },

        documentOnCheckEvent: function () {
            $('.filesAfterUpload').find('.radioButton').on('click', function(){
                if (this.checked) {
                    $.unCheckDocuments();
                    this.checked = true;
                    /*productPage.getDocumentPageCount($(this).attr('contentPath'));*/
                    productPage.setActiveProductAttributes({'documentId': this.value});
                }
            });
        },

        updateDocument: function () {
            var documentId = productPage.getActiveProductAttributes().documentId;
            $.unCheckDocuments();
            $('.filesAfterUpload').find('.radioButton').each(function(){
                if (this.value == documentId) {
                    this.checked = true;
                }
            });
        },

        unCheckDocuments: function () {
            $('.filesAfterUpload').find('.radioButton').each(function(){
                $(this).attr('checked', false);
            });
        },

        hasValidAttributes: function() {
            switch(stepIndex) {
                case 0:
                    return true;
                    break;

                case 1:
                    if(productPage.getActiveProductAttributes().documentId !== '0' || productPage.getActiveProductAttributes().fileUpload.length === 1) {
                        return true;
                    } else {
                        alert('Please upload a document');
                    }
                    break;
                case 2:
                    if(productPage.getActiveProductAttributes().addressingData.data.length  > 0) {
                        return $.isReturnAddressValid();
                    } else {
                        alert('Please provide at least one Mailing Address');
                    }
                    break;

                case 3:
                    if($('div.proofApproval').has(":checked").length > 0) {
                        return true;
                    } else {
                        alert('Please review and approve the proof');
                    }
                    break;
            }
        },

        isReturnAddressValid: function () {
            var name = $('[name="returnAddressName"]').val();
            var company = $('[name="returnAddressCompany"]').val();
            var address1 = $('[name="returnAddressAddress1"]').val();
            var city = $('[name="returnAddressCity"]').val();
            var state = $('[name="returnAddressState"]').val();
            var zip = $('[name="returnAddressZip"]').val();
            if(name === '') {
                alert("Return address Name is required");
                return false;
            }
            else if(company === '') {
                alert("Return address Company is required");
                return false;
            }
            else if(address1 === '') {
                alert("Return address Address Line1 is required");
                return false;
            }
            else if(city === '') {
                alert("Return address City is required");
                return false;
            }
            else if(state === '') {
                alert("Return address State is required");
                return false;
            }
            else if(zip === '') {
                alert("Return address Zip Code is required");
                return false;
            }else {
                return true;
            }
        },

        bindNextButtonEvent: function() {
            $('.nextButton,.jqs-stepNext').on('click.loadnextstep', function() {
                if($.hasValidAttributes() && stepIndex < 3) {
                    $('[bns-' + steps[stepIndex++] + ']').addClass('hidden');
                    $('[bns-' + steps[stepIndex] + ']').removeClass('hidden');
                    $.stepChangedEvent();
                }
            });
        },


        /**
         * Method for initializing the PageCounter UI with maxPages. If the current number of pages is greater than the max_pages,
         * then the current number of pages will be reset to 1.
         *
         * @param maxPages - Maximum number of pages supported by the selected production options
         */
        initializePageCounter: function(maxPages) {
            var el = $('.jqs-numberOfPages');
            var max = parseInt(maxPages);
            var pages = parseInt($(el).data('value'));
            $(el).data('max', max);
            if(pages > max) {
                pages = 1;
                $(el).data('value', pages);
                $(el).find('.placeholder').text(pages + ( pages > 1 ? ' Pages' : ' Page'));
                $.pageCountChangedEvent(pages, false);
            }
        },


        initializeQuantity: function(quantity) {
            var el = $('.productSideBarSection').find('[selection-name="quantity"]');
            $(el).find('.placeholder').text(quantity + ' Qty.');
            $(el).data('value', quantity.trim());
        },

        initializeFileUploader: function() {
            $('.jqs-filecontainer').off('click.dropzone').on('click.dropzone', function() {
                $('.jqs-fileupload').trigger('click');
            });

            $('#uploadDocumentFile').fileupload({
                url: '/' + websiteId + '/control/uploadDirectMailingDocumentFile',
                dataType: 'json',
                dropZone: $('.' + productPage.fileGrid).find('.dropzone'),
                sequentialUploads: true,
                add: function (e, data) {
                    if(data.originalFiles.length > 1) {
                        alert("Only one file can be uploaded");
                        return false;
                    } else {
                        $('[data-filename]').remove();
                        //remove the placeholder div
                        $('.dropzone [bns-dropzoneplaceholders]').remove();

                        //create the file progress
                        var fileDiv = $('<div/>').addClass('text-size-md relative design-file text-left inprogress').append(
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
                            $(this).parents('[data-filename]').remove();
                            productPage.getActiveProductAttributes().fileUpload.pop();
                        });

                        data.context = fileDiv.appendTo($('.' + productPage.fileGrid).find('.dropzone'));
                        var jqXHR = data.submit();

                        $('#uploadDocumentFile').parent().spinner(true, false, 250, 6, null, 'Uploading...', null, 10000);
                    }
                },
                progress: function (e, data) {
                    // Calculate the completion percentage of the upload
                    var progress = parseInt(data.loaded / data.total * 100, 10);
                    if (progress == 100) {
                        $('.' + productPage.fileGrid).find('.dropzone').find('[data-filename="' + data.files[0].name + '"]').first().removeClass('inprogress').children('div.progress').fadeOut();
                    } else {
                        $('.' + productPage.fileGrid).find('.dropzone').find('[data-filename="' + data.files[0].name + '"]').first().children('div.progress').width(progress + '%');
                    }
                },
                done: function (e, data) {
                    $('#uploadDocumentFile').parent().spinner(false);
                    $('[bns-hiddenuploadbutton]').removeClass('hidden');
                    if (data.result.success) {
                        var fileList = [];

                        $.each(data.result.files, function (idx) {
                            var fileInfo = {};
                            fileInfo['name'] = data.result.files[idx].name;
                            fileInfo['path'] = data.result.files[idx].path;
                            fileList.push(fileInfo);
                            return false;
                        });
                        productPage.setActiveProductAttributes({'fileUpload' : fileList});
                        $.updateDocument();
                    }
                },
                fail: function (e, data) {
                    $.each(data.files, function (idx, el) {
                        $('.' + self.fileGrid).find('.dropzone').find('[data-filename="' + data.files[idx].name + '"]').first().children('div.progress').first().find('i.fa').removeClass('fa-trash-o').addClass('fa-warning');
                    });
                }
            });
        },

        createAddressingGrid: function (dataGroupId) {
            var gridData = [[]];
            var method = 'manual';
            var partyId = productPage.getActiveProductAttributes().partyId;

            var gridOptions = $.extend({
                gridData: typeof gridData != 'undefined' ? gridData : [[]],
                partyId: typeof partyId === 'undefined' || partyId == null ? '' : partyId,
                dataGroupId: dataGroupId,
                onGridApply: function (event, data) {
                    productPage.setActiveProductAttributes({'dataGroupId': data.dataGroupId});
                    productPage.setActiveProductAttributes({'addressingData': data});
                    $.updateMailingList(0);
                    if (stepIndex === 2) {
                        productPage.setActiveProductAttributes({'hasAddressModified': true});
                        productPage.setActiveProductAttributes({'quantity': data.data.length});
                    }
                },
                onGridClose: function (event, data) {
                    // Not sure if we will use this one day.
                },
                dataGroupMode: method
            }, {
                responsive: true,
                debug: false,
                usesDesign: false
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

        showGrid: function (method, index) {
            $(window).scrollTop(0);
            $('#env-variabledata-grid').VariableDataGrid('showGrid', 'manual');
        },

        updateMailingList: function (index) {
            var addressData = productPage.getActiveProductAttributes().addressingData;

            if (typeof index != 'undefined' && addressData.data.length > index) {
                index = (index >= addressData.data.length ? 0 : (index < 0 ? addressData.data.length - 1 : index));
                addressIndex = index;

                $('textArea[name="mailingList').val(
                    (addressData.data[index][0] !== '' ? addressData.data[index][0] + '\n' : '') +
                    (addressData.data[index][1] !== '' ? addressData.data[index][1] + '\n' : '') +
                    (addressData.data[index][2] !== '' ? addressData.data[index][2] + '\n' : '') +
                    (addressData.data[index][3] !== '' ? addressData.data[index][3] + '\n' : '') +
                    (addressData.data[index][4] !== '' ? addressData.data[index][4] + ', ' : '') + (addressData.data[index][5] !== '' ? addressData.data[index][5] + ' ' : '') + (addressData.data[index][6] !== '' ? addressData.data[index][6] : '') + (addressData.data[index][4] !== '' || addressData.data[index][5] !== '' || addressData.data[index][6] !== '' ? '\n' : '') +
                    (addressData.data[index][7] !== '' ? addressData.data[index][7] : '')
                );
            }
        },

        updateReturnAddress: function () {
            var returnAddress = {
                'rtnName': $('[name="returnAddressName"]').val(),
                'rtnOrganization': $('[name="returnAddressCompany"]').val(),
                'rtnAddress1': $('[name="returnAddressAddress1"]').val(),
                'rtnAddress2': $('[name="returnAddressAddress2"]').val(),
                'rtnCity': $('[name="returnAddressCity"]').val(),
                'rtnState': $('[name="returnAddressState"]').val(),
                'rtnZip': $('[name="returnAddressZip"]').val()
            };
            productPage.setActiveProductAttributes({'returnAddress' : returnAddress});
            $.updateReturnAddressPreview();
        },

        updateReturnAddressPreview: function() {
            var returnAddress = productPage.getActiveProductAttributes().returnAddress;
            $('textarea[name="returnAddress"]').val(
                (returnAddress.rtnName !== '' ? returnAddress.rtnName + '\n' : '') +
                (returnAddress.rtnOrganization !== '' ? returnAddress.rtnOrganization + '\n' : '') +
                (returnAddress.rtnAddress1 !== '' ? returnAddress.rtnAddress1 + '\n' : '') +
                (returnAddress.rtnAddress2 !=='' ? returnAddress.rtnAddress2 + '\n' : '') +
                (returnAddress.rtnCity !== '' ? returnAddress.rtnCity + ', ' : '') + (returnAddress.rtnState !== '' ? returnAddress.rtnState + ' ' : '') + (returnAddress.rtnZip !== '' ? returnAddress.rtnZip : '') + (returnAddress.rtnCity !== '' || returnAddress.rtnState !== '' || returnAddress.rtnZip !== '' ? '\n' : '')
            );
        },

        bindPageCounterEvents: function() {
            $('.jqs-plus').on('click', function() {
                var el = $(this).closest('.jqs-numberOfPages');
                var pages = parseInt($(el).data('value'));
                var max = parseInt($(el).data('max'));
                if(pages < max) {
                    pages ++;
                    $(el).data('value', pages);
                    $(el).find('.placeholder').text(pages + ( pages > 1 ? ' Pages' : ' Page'));
                    $.pageCountChangedEvent(pages);
                } else {
                    alert('Max pages allowed is ' + max);
                }
            });

            $('.jqs-minus').on('click', function() {
                var el = $(this).closest('.numberOfPages');
                var pages = parseInt($(el).data('value'));
                var max = parseInt($(el).data('max'));
                if (pages > 1) {
                    pages--;
                    $(el).data('value', pages);
                    $('.numberOfPages').find('.placeholder').text(pages + ( pages > 1 ? ' Pages' : ' Page'));
                    $.pageCountChangedEvent(pages);
                }
            });
        },

        bindAddressNavButtonEvents: function() {
            $('[bns-nextaddress]').off('click.nextAddress').on('click.nextAddress', function() {
                $.updateMailingList(addressIndex + 1);
            });

            $('[bns-previousaddress]').off('click.previousaddress').on('click.previousaddress', function() {
                $.updateMailingList(addressIndex - 1);
            });
        },

        bindReturnAddressChangeEvent: function() {
            $('[bns-returnaddressinput]').off('input.returnAddressInput').on('input.returnAddressInput', function() {
                $.updateReturnAddress();
            });
        },

        pageCountChangedEvent: function(pages, costCalculationFlag) {
            productPage.setActiveProductAttributes({'numberOfPages' : pages});
            if(typeof costCalculationFlag === 'undefined' || costCalculationFlag) {
                $.getEstimatedCost();
            }
        },

        bindSaveJobEvent: function() {
            $('.saveExitButton').on('click', function () {
                saveAndExitFlag = true;
                $.isUserLoggedIn($.getJobNumber);
            });
        },

        bindBackButtonEvent: function() {
            $('.jqs-stepBack').on('click.loadpreviousstep', function () {
                if(stepIndex > 0) {
                    $('[bns-' + steps[stepIndex--] + ']').addClass('hidden');
                    $('[bns-' + steps[stepIndex] + ']').removeClass('hidden');
                    $.stepChangedEvent();
                }
            });
        },
        stepChangedEvent: function() {
            if(stepIndex === 3) {
                $.getJobNumber();
                $.populateProductSummary();
            }
            $.toggleNextLink();
            $.toggleBackLink();
            $.toggleAddToCartButton();
        },
        toggleNextLink: function() {
            if (stepIndex < 3) {
                $('.jqs-stepNext').removeClass('hidden');
            } else {
                $('.jqs-stepNext').addClass('hidden');
            }
        },
        toggleBackLink: function() {
            if (stepIndex > 0) {
                $('.jqs-stepBack').removeClass('invisible');
            } else {
                $('.jqs-stepBack').addClass('invisible');
            }
        },
        toggleAddToCartButton: function() {
            if(stepIndex === 3) {
                $('.nextButton').text('Add to Cart').on('click.addToCart', function(e) {
                    $.addToCart(e, this);
                });
            } else {
                $('.nextButton').text('Next').off('click.addToCart');
            }
        },
        addToCart: function(event, element) {
            var attr = {};

            if(productPage.getActiveProductAttributes().jobNumber != '0') {
                attr.token = randomToken();
            }

            var colorsFront = (productPage.getActiveProductAttributes().color == 'Full Color') ? 4 : 1;
            var colorsBack = 0;
            if(productPage.getActiveProductAttributes().printOption == 'Printing Both Sides') {
                colorsBack = (productPage.getActiveProductAttributes().color == 'Full Color') ? 4 : 1;
            }

            attr.add_product_id = productPage.getActiveProductAttributes().productId;
            attr.isProduct = true;
            attr.colorsFront = colorsFront;
            attr.colorsBack = colorsBack;
            attr.artworkSource = 'ART_UPLOADED';
            attr.fileName = [productPage.getActiveProductAttributes().documentName];
            attr.filePath = [productPage.getActiveProductAttributes().documentPath];
            attr.fileOrder = [];
            attr.fileOrderItem = [];
            attr.isRush = true;
            attr.price = productPage.getActiveProductAttributes().price.totalPrice;
            attr.quantity = productPage.getActiveProductAttributes().price.quantity;
            //attr.jobId = productPage.getActiveProductAttributes().jobId;
            attr.directMailJobId = productPage.getActiveProductAttributes().jobNumber;

            $.cartEndPoint(attr, productPage.getActiveProductAttributes().documentClass, productPage.getActiveProductAttributes().paperType, false, $.setCartId, null);

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
                localStorage.setItem('addToCartData', data)
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

                $().updateMiniCart();
                if(successCallback != null && typeof successCallback == 'function') {
                    successCallback();
                }
            }).fail(function(jqXHR) {
                if(errorCallback != null && typeof errorCallback == 'function') {
                    errorCallback();
                }
            });

        },

        setCartId: function() {
            var url = '/' + websiteId + '/control/setCartId';
            $.ajax({
                type: 'POST',
                url: url,
                data: {'jobNumber' :  productPage.getActiveProductAttributes().jobNumber, 'fromAddToCart' : true},
                dataType: 'json',
                cache: false,
                async: false
            }).done(function(data) {
                //console.log(data);
            });
        },

        getProductPageInstance: function() {
            return productPage;
        },

        populateProductSummary: function() {
            var documentId = productPage.getActiveProductAttributes().documentId;
            var documentName = '';
            $('.filesAfterUpload').find('.radioButton').each(function(){
                if (this.value == documentId) {
                    documentName = $(this).data('document-content');
                }
            });
            var layout = productPage.getActiveProductAttributes().layout;
            var productionTime = productPage.getActiveProductAttributes().productionTime;
            var envelope = productPage.getActiveProductAttributes().envelope;
            var color = productPage.getActiveProductAttributes().color;
            var paperType = productPage.getActiveProductAttributes().paperType;
            var quantity = productPage.getActiveProductAttributes().quantity;
            var printOption = productPage.getActiveProductAttributes().printOption;
            var mailClass = productPage.getActiveProductAttributes().mailClass;
            var rtnName = $('[name="returnAddressName"]').val();
            var rtnOrganization =  $('[name="returnAddressCompany"]').val();
            var rtnAddress1 = $('[name="returnAddressAddress1"]').val();
            var rtnAddress2 = $('[name="returnAddressAddress2"]').val();
            var rtnCity = $('[name="returnAddressCity"]').val();
            var rtnState = $('[name="returnAddressState"]').val();
            var rtnZip = $('[name="returnAddressZip"]').val();
            var mailingListName = '';
            $('.productSummaryLeft').find('p.layoutOption').text(layout);
            $('.productSummaryLeft').find('p.productionTIme').text(productionTime);
            $('.productSummaryLeft').find('p.envelopeName').text(envelope);
            $('.productSummaryLeft').find('p.printColor').text(color);
            $('.productSummaryLeft').find('p.paperColor').text(paperType);
            $('.productSummaryLeft').find('p.printOption').text(printOption);
            $('.productSummaryLeft').find('p.mailClass').text(mailClass);
            $('.productSummaryLeft').find('p.quantity span').text(quantity);
            //$('.productSummaryRight').find('p.documentName').text(documentName);
            $('.productSummaryRight').find('p.rtnName').text(rtnName);
            $('.productSummaryRight').find('p.rtnOrganization').text(rtnOrganization);
            $('.productSummaryRight').find('p.rtnAddress1').text(rtnAddress1);
            $('.productSummaryRight').find('p.rtnAddress2').text(rtnAddress2);
            $('.productSummaryRight').find('p.rtnCity').text(rtnCity);
            $('.productSummaryRight').find('p.rtnState').text(rtnState);
            $('.productSummaryRight').find('p.rtnZip').text(rtnZip);
            $('.productSummaryRight').find('p.mailingListName').text(mailingListName);
        },

        bindGetJobProof: function() {
            $('.downloadProofButton').on('click', function () {
                var proof = $(this).attr('id');
                if (proof !== '') {
                    window.open(proof);
                } else {
                    alert("An error occured while generating proof");
                }
            });
        },

        checkForSlideOut: function() {
            $('.colorTextureBodyInner').each(function(){
                if($(this).children().length == 1 && $(this).children().attr('selection-name') != 'quantity') {
                    var sidebarName = $(this).parents('.sidebarPanel').attr('id');
                    sidebarName != 'sidebar-quantity' ? $('[data-sidebar-name="'+sidebarName+'"]').removeClass('noSlideOut').addClass('noSlideOut') : '';
                }                   

            })
        }
    });
    var productPage = new ProductPage();
    // productPage.setProductOptions(optionsJSON);
    $.documentOnCheckEvent();
    $.productOptionCheckBoxSelectEvent();
    $.bindNextButtonEvent();
    $.bindBackButtonEvent();
    $.bindSaveJobEvent();
    $.bindLoginEvent();
    $.bindPageCounterEvents();
    $.initializeFileUploader();
    $.bindAddressNavButtonEvents();
    $.bindReturnAddressChangeEvent();
    $.bindGetJobProof();
})();



