$(document).ready(function(){

    // Initialize Product Images Slider
    $('.product-images').slick({
        arrows: false,
        accessibility: false,
        dots: true
    });

    // Color selection
    $('.colorWheel *[data-color-group]').click(function(){
        $('.colorWheel *[data-color-group]').removeClass('activeColor');
        $(this).addClass('activeColor');
        $('.colorFilterClear').removeClass('hidden');
        var colorGroup = $(this).data('color-group');
        $('.colorList .selectListItem').removeClass('hidden');
        if(typeof colorGroup !== "undefined"){
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

    // Color Selection slider initialize
    $('.colorWheel').slick({
        slidesToShow: 8,
        slidesToScroll: 4,
        prevArrow: '<i class="fa fa-chevron-left slide-arrow previous-slide"></i>',
        nextArrow: '<i class="fa fa-chevron-right slide-arrow next-slide"></i>'
    });

    // Make thumbnails clickable to control slider
    $('.productThumbs').on('click', '.productThumb', function(){
        $('.productThumb').removeClass('active');
        $(this).addClass('active');
        var slideNum = $(this).index();
        $('.product-images').slick('slickGoTo', slideNum, true);
    });
    $('.product-images').on('beforeChange', function(event, slick, currentSlide, nextSlide){
        $('.productThumb').removeClass('active');
        $('.productThumb').eq(nextSlide).addClass('active');
    });
    // Initialize Related Products Slider
    $('.related-slider').slick({
        infinite: true,
        slidesToShow: 5,
        slidesToScroll: 5,
        arrows: false,
        accessibility: false,
        responsive: [
            {
                breakpoint: 1020,
                settings: {
                    slidesToShow: 3,
                    slidesToScroll: 1,
                    centerMode: true,
                    arrows: true,
                    prevArrow: '<i class="fa fa-chevron-left slide-arrow previous-slide"></i>',
                    nextArrow: '<i class="fa fa-chevron-right slide-arrow next-slide"></i>',
                }
            },
            {
                breakpoint: 670,
                settings: {
                    slidesToShow: 2,
                    slidesToScroll: 1
                }
            },
            {
                breakpoint: 520,
                settings: {
                    slidesToShow: 1,
                    slidesToScroll: 1
                }
            }
        ]
    });

    // Fixed table header on samples modal
    $('.samplesTableWrap').scroll(function(){
        var translate = 'translate(0,' + this.scrollTop + 'px)';
        $(this).find('thead').css('transform', translate);
    });

    // Calculate scrollable window height to fill height of browser
    function scrollWindowCalculation(){
      $('.sidebarPanel').each(function(){
        var headerHeight = $(this).find('.colorTextureHeading').outerHeight();
        var windowHeight = $(window).height();
        var scrollHeight = windowHeight - headerHeight - 15;
        var innerBody = $(this).find('.colorTextureBodyInner');
        innerBody.css('height', 'auto');
        var innerBodyContents = $(innerBody).height();
        innerBody.css('height', scrollHeight + 'px');
        console.log(innerBodyContents);
      });
    }
    $(window).bind('load resize',function(){
      scrollWindowCalculation();
    });
    $('.jqs-sidebarToggle, *[data-color-group]').click(function(){
      setTimeout(
        function(){
          scrollWindowCalculation();
        }, 20);
    });
    // Ensure you can't scroll the page
    // while hovering over sidebar panel
    $(".sidebarPanel").hover(function(){
        clearTimeout($(this).data('timeoutId'));
        console.log('now hovering');
        $('body').addClass('scrollJack');
    }, function(){
        var someElement = $(this),
            timeoutId = setTimeout(function(){
              $('body').removeClass('scrollJack');
            }, 50);
        someElement.data('timeoutId', timeoutId); 
    });

    $('#dropdown-colorList, #dropdown-quantityList').css('transition', 'transform .25s ease, opacity .25s ease');

    $('.sidebarToggle').click(function(){
        var dropDown = $(this).data('dropdown-target');
        $('#' + dropDown).addClass('activatedPanel');
    });
    $('.productSidebar').on('click', '.sidebarPanel h4 .fa, .jqs-selectListItem', function(){
        $(this).parents('.activatedPanel').removeClass('activatedPanel');
    });

    $(document).mousedown(function(e){
        var container = $('.sidebarPanel.activatedPanel');
        if (!container.is(e.target) && container.has(e.target).length === 0){
            container.removeClass('activatedPanel');
        }
    });

    $('#dropdown-colorList .selectListItem').lazy();

    $('#dropdown-colorList .selectListItem').each(function(){
        var colorName = $(this).attr('data-product-color').replace(/[^\w\s]/gi, '');
        colorName = 'PC-' + colorName.replace(/\s+/g, '_');
        var textColor = $(this).attr('data-hex');
        textColor = getColorContrast(textColor);
        if(textColor < 127.5){
            $(this).find('p').css('color', '#ffffff');
        }
        var imgSrc = '//actionenvelope.scene7.com/is/image/ActionEnvelope/' + colorName;
        $(this).css('background-image', 'url(' + imgSrc + ')');
    });

    $('.selectListParent.colorSelection').each(function(){
        var el = $(this);
        var textColor = $(this).attr('data-hex');
        textColor = getColorContrast(textColor);
        if(textColor < 127.5){
            $(this).find('p').css('color', '#ffffff');
        }
    })
});



var pixels_per_star = 22;
var current_rating = 1;

$("#reviewForm [class^='rating']").on("mousemove", function(e) {
    rating = Math.ceil((e.pageX - $(this).offset().left) / pixels_per_star);
    $(this).removeClass().addClass("rating-" + rating + "_0");
}).on("mouseleave", function() {
    $(this).removeClass().addClass("rating-" + current_rating + "_0");
}).on("click", function(e) {
    current_rating = Math.ceil((e.pageX - $(this).offset().left) / pixels_per_star)
    $("[name='productRating']").val(current_rating);
});

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
    this.productSlider = 'jqs-prodslides';
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

            // Disable slider and remove all images except the main image.
            // Slider is re-enabled
            $('.product-images').slick('unslick');
            $('.removable').remove();

            var img = $("<img />").attr('src', source).on('load', function() {
                if (!this.complete || typeof this.naturalWidth == "undefined" || this.naturalWidth == 0) {
                    // Image did not load successfully
                    console.log('Image Error');
                } else {
                    // Image source loaded successfully
                    $('.' + self.heroimage).attr('src', source);
                    if(source.indexOf('base64') == -1) {
                        if(source.indexOf('hei=') != -1) {
                            source = source.match(/(.*?hei=)\d+(.*)/);
                            $('.jqs-enhancedImage').attr('src', source[1] + '800' + source[2]);
                        } else {
                            $('.jqs-enhancedImage').attr('src', source + '&wid=600');
                        }
                    } else {
                        $('.jqs-enhancedImage').attr('src', source);
                    }
                }
            });

            // $('.' + self.heroimage).attr('src', source);
            // if(source.indexOf('base64') == -1) {
            // 	if(source.indexOf('hei=') != -1) {
            // 		source = source.match(/(.*?hei=)\d+(.*)/);
            // 		$('.jqs-enhancedImage').attr('src', source[1] + '800' + source[2]);
            // 	} else {
            // 		$('.jqs-enhancedImage').attr('src', source + '&wid=600');
            // 	}
            // } else {
            // 	$('.jqs-enhancedImage').attr('src', source);
            // }
        }, 'updateImage', 50);
    },
    /**
     * Bind all the click events on the product page for options
     */
    bindClickEvents: function() {
        var self = this;

        function scrollToButton(obj) {
            $.scrollTo($(obj), 500, {offset:-250});
        }

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
        var cqty = $('.' + this.customQty);
        $('.qtyInput .foldersButton').click(function(){
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
                    } else {
                        warningBox.addClass('hidden');
                    }
                    stickyCalculation();
                    setTimeout(
                        function(){
                            stickyCalculation();
                        }, 610);
                }
            }, 250, 'customQty');
        });

        // Trigger custom quantity search on enter key
        $('.customQty').on('keyup', function (e) {
            if (e.keyCode == 13) {
                $('.qtyInput .foldersButton').trigger('click');
            }
        });

        if (window.location.href.match(/(?:\?|\&)ss=true/) != null) {
            $('.' + self.orderSamplesQuickLink).trigger('click');
        }

        /**
         * addToCart
         */
        $('.' + self.cart).on('click', function() {
            self.addToCart(this);
        });

        window["updateSelectListSelection"] = function(element) {
            self.updateSelection(element);
            var colorImg = $('.slSelected').css('background-image');
            var textColor = $('.slSelected').find('p').css('color');
            $('.selectListParent.colorSelection').css('background-image', colorImg).css('color', textColor);
            if(textColor == 'rgb(255, 255, 255)'){
                $('.selectListParent.colorSelection').addClass('knockout');
            } else {
                $('.selectListParent.colorSelection').removeClass('knockout');
            }
        }

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
            }
        });
    },
    /**
     * Update selection for select list
     */
    updateSelection: function(element) {
        var self = this;
        $(element).siblings().attr('data-selected', 'false');
        $(element).attr('data-selected', 'true');

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
                    texelDiv.texel('paintTexel', 0, true);
                    texelDiv.texel('preview', (self.returnActiveProduct(true).templateType == 'FLAP') ? '1' : '0', 520, null, function (data, fmt) {
                        self.updateImage('data:image/' + fmt + ';base64,' + data.data);
                    });
                } else if(plainOrPrinted == 'printed' && self.returnActiveProduct(true).designMethod && self.returnActiveProduct(true).design != null && typeof startedAsDesign !== 'undefined' && startedAsDesign) {
                    //this is a design template
                    changeURL = false;
                    self.updateImage('//texel.envelopes.com/getBasicImage?id=' + self.returnActiveProduct(true).design + '&setElement.COLOR_bgcolor=%3Cfill%3E%3CSolidColor%20color%3D%22%23' + self.returnActiveProduct(true).hex + '%22%2F%3E%3C%2Ffill%3E' + '&hei=750&fit=stretch,1&fmt=png-alpha');
                } else {
                    self.updateImage('//actionenvelope.scene7.com/is/image/ActionEnvelope/' + self.returnActiveProduct(true).productId + '?hei=750&fit=stretch,1&fmt=png-alpha');
                }

                //update page areas
                $('.' + self.productName).html(self.returnActiveProduct(true).name);
                $('.' + self.colorName).html(self.returnActiveProduct(true).color);
                $('.' + self.printDesc).html($(element).attr('data-print-desc'));
                if($(element).attr('data-product-brand') == '' || $(element).attr('data-product-brand').indexOf('LUXPaper') == -1) {
                    $('.' + self.brandDesc).removeClass('hidden').addClass('hidden');
                } else if($(element).attr('data-product-brand').indexOf('LUXPaper') != -1) {
                    $('.' + self.brandDesc).removeClass('hidden');
                }
                $('.' + self.starRating).removeClass().addClass(self.starRating + ' rating-' + self.returnActiveProduct(true).rating);
                $('.' + self.noReview).removeClass('hidden').addClass('hidden');
                $('.' + self.hasReview).removeClass('hidden').find('a').html('Read Reviews');

                if(self.returnActiveProduct(true).maxColor >= 2) {
                    $('[data-target=inkFrontSelection][data-value=2]').removeClass('hidden');
                    $('[data-target=inkBackSelection][data-value=2]').removeClass('hidden');
                } else {
                    $('[data-target=inkFrontSelection][data-value=2]').removeClass('hidden').addClass('hidden');
                    $('[data-target=inkBackSelection][data-value=2]').removeClass('hidden').addClass('hidden');
                }

                if(self.returnActiveProduct(true).maxColor >= 3) {
                    $('[data-target=inkFrontSelection][data-value=4]').removeClass('hidden');
                    $('[data-target=inkBackSelection][data-value=4]').removeClass('hidden');
                } else {
                    $('[data-target=inkFrontSelection][data-value=4]').removeClass('hidden').addClass('hidden');
                    $('[data-target=inkBackSelection][data-value=4]').removeClass('hidden').addClass('hidden');
                }

                if(self.returnActiveProduct(true).hasWhiteInk) {
                    $('[data-target=inkFrontSelection][data-value=whiteInkFront]').removeClass('hidden');
                    $('[data-target=inkBackSelection][data-value=whiteInkBack]').removeClass('hidden');
                } else {
                    $('[data-target=inkFrontSelection][data-value=whiteInkFront]').removeClass('hidden').addClass('hidden');
                    $('[data-target=inkBackSelection][data-value=whiteInkBack]').removeClass('hidden').addClass('hidden');
                }

                if(self.returnActiveProduct(true).hasSample && self.returnActiveProduct(true).designMethod == null) {
                    $('.' + self.orderSamplesQuickLink).removeClass('hidden');
                    $('.samplesCheckboxContainer').removeClass('hidden');
                } else {
                    $('.' + self.orderSamplesQuickLink).removeClass('hidden').addClass('hidden');
                    $('.samplesCheckboxContainer').removeClass('hidden').addClass('hidden');
                }

                self.returnActiveProduct(false).getProductFeatures();
                self.returnActiveProduct(false).getProductDesc();

                var updateImageCallback = function(id) {
                    self.updateImage('//actionenvelope.scene7.com/is/image/ActionEnvelope/' + id + '?hei=750&fit=stretch,1&fmt=png-alpha');
                };

                self.returnActiveProduct(false).getProductAssets(updateImageCallback);

                break;
        }

        //get new product price
        self.returnActiveProduct(false).calculatePrice(null);
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
            } else {
                //self.processButtonError((optTest ? $('[data-key=plainOrPrinted][data-value=printed]') : $('[data-key=designOrUpload][data-value=design]')), null, 'You did not edit your design yet!');
                self.processButtonError($('[data-key=designOrUpload][data-value=design]'), null, 'You did not edit your design yet!');
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
        var duration = 500;
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
     * Add all products to the cart
     * @param el - Button being clicked
     */
    addToCart: function(el) {
        var self = this;

        if($(el).hasClass('processing')) {
            return;
        }

        $(el).addClass('processing');

        // CODE FOR OPTIMIZELY TEST.
        if ((typeof optEnvAction !== 'undefined' && optEnvAction == 'ra') && (typeof $(el).attr('data-ignore') === 'undefined' || (typeof $(el).attr('data-ignore') !== 'undefined' && $(el).attr('data-ignore') != 'true'))) {
            try {
                if ($('.jqs-uploadedfiles ul li').length == 0 && !$('[name="sendFilesLater"]').is(':checked')) {
                    $('#optEnvAction').foundation('reveal', 'open');
                }
            }
            catch (e) {}
            $(el).removeClass('processing');
            return;
        }

        for(var i = 0; i < this.getProducts().length; i++) {
            if(this.getProducts()[i].isProductEnabled()) {
                var attr = $.extend(true, {}, this.getProducts()[i].priceData);
                attr.add_product_id = this.getProducts()[i].returnProductId();
                attr.isProduct = (self.getProducts().length > 1) ? false : true;
                if(self.projectId != null) {
                    attr.scene7ParentId = self.projectId;
                }
                if(self.getProducts()[i].getProduct().comments != null) {
                    attr.itemComment = self.getProducts()[i].getProduct().comments;
                }
                if(self.getProducts()[i].getProduct().scene7DesignId != null) {
                    attr.scene7DesignId = self.getProducts()[i].getProduct().scene7DesignId;
                }

                //check order type, plain, printed
                var plainOrPrinted = $('[data-key=plainOrPrinted][data-selected=true]').attr('data-value');
                var totalAddresses = parseInt($('[data-key=addresses][data-selected=true]').attr('data-total'));
                var artworkSource = null;

                if(plainOrPrinted  == 'printed') {
                    //if its still null, lets make sure addressing is selected
                    if(!self.isCartValid(el, self.getProducts()[i], totalAddresses)) {
                        $(el).removeClass('processing');
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
    /**
     *	Load Sample List
     */
    loadSamples: function() {
        var self = this;

        function updateSamplePrice(priceElement, numberOfSamples) {
            $(priceElement).html('$' + numberOfSamples + '.00');
        }

        $('.jqs-samplesPopupBody').empty();

        $('.jqs-sampleReadableList').find('.jqs-selectListItem').each(function() {

            if ($(this).attr('data-has-sample') == 'true') {
                $('.jqs-samplesTable').append(
                	'<tr class="samplesRow" id="sampleRow-' + $(this).attr('data-product-id') + '">'
                		+ '<td class="samplesCol1">'
                			+ '<img src="//actionenvelope.scene7.com/is/image/ActionEnvelope/' + $(this).attr('data-product-id') + '?hei=50&amp;wid=50&amp;fmt=png-alpha" data-src="//actionenvelope.scene7.com/is/image/ActionEnvelope/' + $(this).attr('data-product-id') + '?hei=50&amp;wid=50&amp;fmt=png-alpha">'
                		+ '</td>'
                		+ '<td>'
                			+ '<div>' + $(this).attr('data-product-color') + '</div>'
                		+ '</td>'
                		+ '<td class="samplesCol2">' + $(this).attr('data-product-weight') + '</td>'
                		+ '<td class="samplesCol3 quantityColumn">'
                			+ '<div class="jqs-subtractSampleQuantity">-</div><input value="1"><div class="jqs-addSampleQuantity">+</div>'
                		+ '</td>'
                		+ '<td class="samplesCol4 jqs-samplePrice">$1.00</td>'
                		+ '<td class="samplesCol5">'
                			+ '<div data-product-id="' + $(this).attr('data-product-id') + '" data-product-name="' + $(this).attr('data-product-name') + '" data-product-color="' + $(this).attr('data-product-color') + '" data-adding-item="false" class="foldersButton buttonGreen jqs-addSampleToCart addSampleToCart">Add to Cart</div>'
                		+ '</td>'
                	+ '</tr>'
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
                        '<div class="productThumb active">'
                            + '<div style="background-image: url(//actionenvelope.scene7.com/is/image/ActionEnvelope/' + self.returnProductId() + '?fmt=png&wid=95&fmt=png-alpha);"></div>'
                        + '</div>'
                    );

                    // $('.product-images').slick('unslick');
                    // $('.removable').remove();

                    for(var i = 0; i < data.productAssets.length; i++) {
                        if (data.productAssets[i].assetType != 'printed') {
                            $('.' + self.assetGrid).append(
                                '<div class="productThumb">'
		                            + '<div style="background-image: url(//actionenvelope.scene7.com/is/image/ActionEnvelope/' + data.productAssets[i].assetName + '?fmt=png&wid=95&fmt=png-alpha);"></div>'
		                        + '</div>'
                            );
                            $('.' + self.productSlider).append(
                                '<div class="product-images-single removable">'
                            		+ '<a><img class="product-slide-image" property="image" src="//actionenvelope.scene7.com/is/image/ActionEnvelope/' + data.productAssets[i].assetName + '?hei=750&fit=stretch,1&fmt=png-alpha" /></a>'
                         		+ '</div>'
                            );
                        }
                    }

                    if (typeof $('.jqs-templateInfo').attr('data-templateId') != 'undefined') {
                        $('.' + self.productSlider).append(
                            '<div class="product-images-single removable">'
								+ '<a><img class="product-slide-image" property="image" src="//actionenvelope.scene7.com/is/image/ActionEnvelope/' + $('.jqs-templateInfo').attr('data-templateId') + '?hei=375&fit=stretch,1&fmt=png-alpha" /></a>'
							+ '</div>'
                        );

                        $('.' + self.assetGrid).append(
                            '<div class="productThumb">'
								+ '<div style="background-image: url(//actionenvelope.scene7.com/is/image/ActionEnvelope/' + $('.jqs-templateInfo').attr('data-templateId') + '?fmt=png-alpha&ts=20170501);"></div>'
							+ '</div>'
                        );
                    }

                    $('.product-images').slick({
                        arrows: false,
                        accessibility: false,
                        dots: true
                    });
                    self.bindAssetImageSelection();
                }
            });
        }, 250, 'productAssets');
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
                                    $('<div/>').addClass('selectListItem jqs-selectListItem jqs-selectList qpsListItems selectList' + ((key == attr.quantity) ? ' slSelected' : '')).attr('data-selectListName', 'quantitySelection').attr('data-qty', key).attr('data-price', productPriceData.priceList[key].price).attr('data-originalPrice', originalPriceData.priceList[key].price).append(
                                        $('<div />').append(
                                            $('<span />').addClass('selQty').html(numberWithCommas(key) + ' Qty')
                                        ).append(
                                            $('<span/>').addClass('originalPriceDisplay margin-left-xxs').append(
                                                $('<strike />').html(formatCurrency(originalPriceData.priceList[key].price))
                                            )
                                        ).append(
                                            $('<span/>').addClass('priceDisplay margin-left-xxs').append(
                                                $('<strong />').html(formatCurrency(productPriceData.priceList[key].price))
                                            )
                                        ).append(
                                            $('<span/>').addClass('savingsDisplay margin-left-xxs').html(productPercentSavings > 0 ? 'Save ' + productPercentSavings + '%' : '')
                                        )
                                    )
                                );
                            }
                            else {
                                if (index == 0) {
                                    $('.' + self.priceGrid).append(
                                        $('<div/>').addClass('selectListItem jqs-selectListItem jqs-selectList qpsListItems selectList' + ((key == attr.quantity) ? ' slSelected' : '')).attr('data-selectListName', 'quantitySelection').attr('data-qty', key).attr('data-price', productPriceData.priceList[key].price).append(
                                            $('<div />').append(
                                                $('<span />').addClass('selQty').html(numberWithCommas(key) + ' Qty')
                                            ).append(
                                                $('<span/>').addClass('selPrice').append(
                                                    $('<span/>').addClass('jqs-pricePerUnit pricePerUnitDisplay margin-left-xxs').html(formatCurrency(productPriceData.priceList[key].price / key) + ' ea.')
                                                ).append(
                                                    $('<span/>').addClass('priceDisplay margin-left-xxs').append(
                                                        $('<strong />').html(formatCurrency(productPriceData.priceList[key].price))
                                                    )
                                                )
                                            )
                                        )
                                    );
                                }
                                else {
                                    var percentSavings = Math.round((1 - (productPriceData.priceList[key].price / ((key / self.smallestQuantity) * self.lowestPrice))) * 100);

                                    var priceGridDiv = $('<div/>').addClass('selectListItem jqs-selectListItem jqs-selectList qpsListItems selectList' + ((key == attr.quantity) ? ' slSelected' : '')).attr('data-selectListName', 'quantitySelection').attr('data-qty', key).attr('data-price', productPriceData.priceList[key].price).append(
                                        $('<div />').append(
                                            $('<span />').addClass('selQty').html(numberWithCommas(key) + ' Qty')
                                        ).append(
                                            $('<span/>').addClass('selPrice').append(
                                                $('<span/>').addClass('jqs-pricePerUnit pricePerUnitDisplay margin-left-xxs').html(formatCurrency(productPriceData.priceList[key].price / key) + ' ea.')
                                            ).append(
                                                $('<span/>').addClass('priceDisplay margin-left-xxs').append(
                                                    $('<strong />').html(formatCurrency(productPriceData.priceList[key].price))
                                                )
                                            )
                                        )
                                    );

                                    if (percentSavings > 0) {
                                        priceGridDiv.append(
                                            $('<span/>').addClass('jqs-percentSavings percentSavingsDisplay margin-left-xxs').html('Save ' + percentSavings + '%')
                                        );
                                    }

                                    $('.' + self.priceGrid).append(priceGridDiv);
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
        } else {
            $('.' + this.selPrice).html(price.salePrice);
            var pricePer = formatCurrency(price.salePrice.replace(/[^0-9\.]/g, '') / qty.replace(/[^0-9\.]/g, '')) + ' ea.';
            $('#quantityPriceSelection .jqs-pricePerUnit').html(pricePer);
            $('.currentQuantity .jqs-pricePerUnit').html(pricePer);

            if (parseInt(qty) != this.smallestQuantity) {
                $('#quantityPriceSelection .jqs-percentSavings').remove();
                /*
                 $('#quantityPriceSelection .jqs-pricePerUnit').after(
                 $('<span/>').addClass('jqs-percentSavings percentSavingsDisplay margin-left-xxs').html('Save ' + Math.round((1 - (price.salePrice.replace(/[^0-9\.]/g, '') / ((qty.replace(/[^0-9\.]/g, '') / this.smallestQuantity) * this.lowestPrice))) * 100) + '%')
                 )
                 */
            }

            if(showShip) {
                $('.' + this.shipNotice).removeClass('hidden')
            } else {
                $('.' + this.shipNotice).removeClass('hidden').addClass('hidden');
            }
        }
    },
    /**
     * Get the product features
     */
    getProductFeatures: function() {
        var self = this;
        waitForFinalEvent(function () {
            $.ajax({
                type: "GET",
                url: '/' + websiteId + '/control/getProductFeatures',
                data: {id: self.returnProductId()},
                dataType: 'json',
                cache: true
            }).done(function (data) {
                if (data.success && typeof data.features !== 'undefined') {
                    $('.' + self.featuresGrid).empty();
                    $('.' + self.featuresGrid).append(
                        '<tr>'
                    		+ '<td>SKU</td>'
                    		+ '<td>' + data.productId + '</td>'
                    	+ '</tr>'
                    );
                    // Update URL with new product ID
                    var url = window.location.href;
                    url = url.replace(/\~product_id=.*?((?:\/|\&|$))/, '~product_id=' + data.productId + '$1');
                    window.history.pushState(null, null, url);

                    $.each(data.features, function (k, v) {
                        $('.' + self.featuresGrid).append(
                            '<tr>'
                    			+ '<td>' + k + '</td>'
                    			+ '<td>' + v + '</td>'
                    		+ '</tr>'
                        );
                    });
                }
            });
        }, 250, 'productFeatures');
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
     * set elements selectable
     */
    setSelectable: function(selectedElement, siblingElements, selectedClass) {
        if(typeof selectedClass === 'undefined') {
            selectedClass = 'selected';
        }

        $(siblingElements).removeClass(selectedClass);
        $(selectedElement).addClass(selectedClass);
    },
};
