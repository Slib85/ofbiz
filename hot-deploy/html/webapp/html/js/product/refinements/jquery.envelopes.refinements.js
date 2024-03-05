/**
 * Created by Manu Prasad on 7/25/2014.
 */

(function($) {
    $.fn.RefinementWidget = function(element, options) {
        var elem = $(element);
        var self = this;
        var settings = $.extend({
            categoryFilters: [],
            sizeFilters: [],
            colorFilters: [],
            styleFilters: [],
            exactColorFilters: [],
            finishFilters: [],
            ratingsFilters: [],
            collectionFilters: [],
            weightFilters: [],
            sealingFilters: [],
            priceFilters: [],
            industryFilters: [],
            themeFilters: [],
            productTypeFilters: [],
            categoryFacets: [],
            sizeFacets: [],
            colorFacets: [],
            styleFacets: [],
            exactColorFacets: [],
            finishFacets: [],
            ratingsFacets: [],
            collectionFacets: [],
            weightFacets: [],
            sealingFacets: [],
            priceFacets: [],
            industryFacets: [],
            themeFacets: [],
            productTypeFacets: [],
            afData: '',
            itemUrlParameters: '',
            serviceEndpoint: 'https://envelopes.resultspage.com/search?p=Q&lbc=envelopes&ts=json-full',
            internalEndPoint: 'https://localhost:8080/envelopes/control/getSliResponse',
            ajaxTimeOut: 3000, // in milli seconds
            internalAjaxTimeOut: 15000,
            fetchSize: 300,      //The current max fetch size for an SLI request is 300
            loadingImageIcon: '../../img/common/anim_loading.gif',
            loadOnInit: false,
            productPageSize: 40,
            sort: '',
            page: 0,
            requestType: 'CATEGORY',
            keyword: '',
            c:'',
            webSiteId:'envelopes',
            pageType:''
        }, options || {});

        if(options.afData.indexOf('new:Y') != -1) {
            settings.pageType = 'NEW_ARRIVALS';
        }
		var SPINNER_TIMEOUT = "";

        /**
         * The different types of filters.
         */
        var CATEGORY_FILTER_TYPE = 'CATEGORY';
        var SIZE_FILTER_TYPE = 'SIZE';
        var COLOR_FILTER_TYPE = 'COLOR';
        var STYLE_FILTER_TYPE = 'STYLE';
        var EXACT_COLOR_FILTER_TYPE = 'EXACT_COLOR';
        var FINISH_FILTER_TYPE = 'FINISH';
        var RATING_FILTER_TYPE = 'RATING';
        var COLLECTION_FILTER_TYPE = 'COLLECTION';
        var WEIGHT_FILTER_TYPE = 'WEIGHT';
        var SEALING_FILTER_TYPE = 'SEALING';
        var PRICE_FILTER_TYPE = 'PRICE';
        var INDUSTRY_FILTER_TYPE = 'INDUSTRY';
        var THEME_FILTER_TYPE = 'THEME';
        var PRODUCT_TYPE_FILTER_TYPE = 'PRODUCT_TYPE';
        /**
         * SLI attributes that are used for refinements
         */
        var TITLE = 'title';
        var PARENT_ID = 'parent_id';
        var SIZE = 'size';
        var PRICE = 'price';
        var IMAGE_URL = 'image_url';
        var ITEM_URL = 'url';
        var UNFRIENDLY_ITEM_URL = 'unfriendlyUrl';
        var COLOR_GROUP = 'color_group';
        var COLOR = 'color';
        var PRODUCT_ID = 'product_id';
        var RANK = 'rank';
        var RATING = 'rating';
        var PRODUCT_TYPE = 'product_type';
        var PRINTABLE = 'printable';
        var NEW = 'new';
        var CLEARANCE = 'onclearance';
        var SALES_RANK = 'salesRank';
        var SALES_PERCENTAGE = 'percentSavings';
        var ON_SALE = 'onSale';

        /**
         * Product HTML template element used for rendering the result on the HTML page
         */
        var productTemplate = $('.jqs-product:first').clone();
        productTemplate.find('.jqs-variants').html('');
        productTemplate.find('.jqs-product-image').attr('src', '');
        productTemplate.find('.jqs-product-name, .jqs-product-size, .jqs-variant-count').text('');
        productTemplate.removeClass('hidden');

        var productVariantTemplate = $('.jqs-product:first').find('.jqs-variant:first').clone();
        productVariantTemplate.find('.jqs-variant-image').attr('src', '');
        productVariantTemplate.find('.jqs-variant-url').attr('href', '');
        productVariantTemplate.find('.jqs-color').html('');
        productVariantTemplate.find('.jqs-productSavings').html('');

        /**
         * A map to store the SLI facet corresponding to each Filter Types.
         * NOTE : The SLI facets for the Advanced Filter Types are not included at the moment.
         */
        var facets = {};
        facets[CATEGORY_FILTER_TYPE]     = 'use';
        facets[SIZE_FILTER_TYPE]         = 'si';
        facets[COLOR_FILTER_TYPE]        = 'cog1';
        facets[STYLE_FILTER_TYPE]        = 'st';
        facets[EXACT_COLOR_FILTER_TYPE]  = 'cog2';
        facets[FINISH_FILTER_TYPE]       = 'finish';
        facets[RATING_FILTER_TYPE]       = 'ra';
        facets[COLLECTION_FILTER_TYPE]   = 'col';
        facets[WEIGHT_FILTER_TYPE]       = 'pw';
        facets[SEALING_FILTER_TYPE]      = 'sm';
        facets[PRICE_FILTER_TYPE]        = 'pb';
        facets[INDUSTRY_FILTER_TYPE]     = 'industry';
        facets[THEME_FILTER_TYPE]        = 'theme';
        facets[PRODUCT_TYPE_FILTER_TYPE] = 'prodtype';



        var filterType = {};
        filterType[facets[CATEGORY_FILTER_TYPE]]     = CATEGORY_FILTER_TYPE;
        filterType[facets[SIZE_FILTER_TYPE]]         = SIZE_FILTER_TYPE;
        filterType[facets[COLOR_FILTER_TYPE]]        = COLOR_FILTER_TYPE;
        filterType[facets[STYLE_FILTER_TYPE]]        = STYLE_FILTER_TYPE;
        filterType[facets[EXACT_COLOR_FILTER_TYPE]]  = EXACT_COLOR_FILTER_TYPE;
        filterType[facets[FINISH_FILTER_TYPE]]       = FINISH_FILTER_TYPE;
        filterType[facets[RATING_FILTER_TYPE]]       = RATING_FILTER_TYPE;
        filterType[facets[COLLECTION_FILTER_TYPE]]   = COLLECTION_FILTER_TYPE;
        filterType[facets[WEIGHT_FILTER_TYPE]]       = WEIGHT_FILTER_TYPE;
        filterType[facets[SEALING_FILTER_TYPE]]      = SEALING_FILTER_TYPE;
        filterType[facets[PRICE_FILTER_TYPE]]        = PRICE_FILTER_TYPE;
        filterType[facets[INDUSTRY_FILTER_TYPE]]     = INDUSTRY_FILTER_TYPE;
        filterType[facets[THEME_FILTER_TYPE]]        = THEME_FILTER_TYPE;
        filterType[facets[PRODUCT_TYPE_FILTER_TYPE]] = PRODUCT_TYPE_FILTER_TYPE;


        /**
         *  The master collection of all filter options for ProductCategory, Size, Color, Style and other advanced filters.
         *  This is fetched and parsed dynamically from SLI facets, that's included in the SLI JSON response.
         */

        // Product Category Filter options
        var categoryFilterCollection = {};

        // Size Filter options
        var sizeFilterCollection = {};

        //Color Filter options
        var colorFilterCollection = {};

        //Style Filter options
        var styleFilterCollection = {};

        var exactColorFilterCollection = {};

        var finishFilterCollection = {};

        var ratingFilterCollection = {};

        var collectionFilterCollection = {};

        var weightFilterCollection = {};

        var sealingFilterCollection = {};

        var priceFilterCollection = {};

        var industryFilterCollection = {};

        var themeFilterCollection = {};

        var productTypeFilterCollection = {};

        //Pagination and sorting
        var currentPage = 0;
        var currentSort = '';
        var fetchType = '';

        /**
         * Helper method used to parse the user selected filters and converting them to SLI facets
         */
        var parseFilters = function(filters, filterType) {
            var selectedFacets = "";
            var facetId = facets[filterType];
            for(var i = 0; i < filters.length; i ++) {
                selectedFacets += (i > 0 ? ' ' : '') + facetId + ':' + filters[i];
            }

            if(filterType == CATEGORY_FILTER_TYPE) {
                settings.categoryFacets = selectedFacets;
            } else if(filterType == SIZE_FILTER_TYPE) {
                settings.sizeFacets = selectedFacets;
            } else if(filterType == COLOR_FILTER_TYPE) {
                settings.colorFacets = selectedFacets;
            } else if(filterType == STYLE_FILTER_TYPE) {
                settings.styleFacets = selectedFacets;
            } else if(filterType == EXACT_COLOR_FILTER_TYPE) {
                settings.exactColorFacets = selectedFacets;
            } else if(filterType == FINISH_FILTER_TYPE) {
                settings.finishFacets = selectedFacets;
            } else if(filterType == RATING_FILTER_TYPE) {
                settings.ratingsFacets = selectedFacets;
            } else if(filterType == COLLECTION_FILTER_TYPE) {
                settings.collectionFacets = selectedFacets;
            } else if(filterType == WEIGHT_FILTER_TYPE) {
                settings.weightFacets = selectedFacets;
            } else if(filterType == SEALING_FILTER_TYPE) {
                settings.sealingFacets = selectedFacets;
            } else if(filterType == PRICE_FILTER_TYPE) {
                settings.priceFacets = selectedFacets;
            } else if(filterType == INDUSTRY_FILTER_TYPE) {
                settings.industryFacets = selectedFacets;
            } else if(filterType == THEME_FILTER_TYPE) {
                settings.themeFacets = selectedFacets;
            } else if(filterType == PRODUCT_TYPE_FILTER_TYPE) {
                settings.productTypeFacets = selectedFacets;
            }
        };

        var loadAppliedFacets = function() {
            if(settings.afData == '') {
                return;
            }
            // if we have afData, then clear all filter values, if any
            settings.categoryFilters    = [];
            settings.sizeFilters        = [];
            settings.colorFilters       = [];
            settings.styleFilters       = [];
            settings.exactColorFilters  = [];
            settings.finishFilters      = [];
            settings.ratingsFilters     = [];
            settings.collectionFilters  = [];
            settings.weightFilters      = [];
            settings.sealingFilters     = [];
            settings.priceFilters       = [];
            settings.industryFilters    = [];
            settings.themeFilters       = [];
            settings.productTypeFilters = [];


            var appliedFacetTokens = settings.afData.split(' ');
            $.each(appliedFacetTokens, function(i, appliedFacetToken) {
                var facetEntry = appliedFacetToken.split(':');
                if(filterType[facetEntry[0]] == CATEGORY_FILTER_TYPE) {
                    settings.categoryFilters[settings.categoryFilters.length] = facetEntry[1];
                } else if(filterType[facetEntry[0]] == SIZE_FILTER_TYPE) {
                    settings.sizeFilters[settings.sizeFilters.length] = facetEntry[1];
                } else if(filterType[facetEntry[0]] == COLOR_FILTER_TYPE) {
                    settings.colorFilters[settings.colorFilters.length] = facetEntry[1];
                } else if(filterType[facetEntry[0]] == STYLE_FILTER_TYPE) {
                    settings.styleFilters[settings.styleFilters.length] = facetEntry[1];
                } else if(filterType[facetEntry[0]] == EXACT_COLOR_FILTER_TYPE) {
                    settings.exactColorFilters[settings.exactColorFilters.length] = facetEntry[1];
                } else if(filterType[facetEntry[0]] == FINISH_FILTER_TYPE) {
                    settings.finishFilters[settings.finishFilters.length] = facetEntry[1];
                } else if(filterType[facetEntry[0]] == RATING_FILTER_TYPE) {
                    settings.ratingsFilters[settings.ratingsFilters.length] = facetEntry[1];
                } else if(filterType[facetEntry[0]] == COLLECTION_FILTER_TYPE) {
                    settings.collectionFilters[settings.collectionFilters.length] = facetEntry[1];
                } else if(filterType[facetEntry[0]] == WEIGHT_FILTER_TYPE) {
                    settings.weightFilters[settings.weightFilters.length] = facetEntry[1];
                } else if(filterType[facetEntry[0]] == SEALING_FILTER_TYPE) {
                    settings.sealingFilters[settings.sealingFilters.length] = facetEntry[1];
                } else if(filterType[facetEntry[0]] == PRICE_FILTER_TYPE) {
                    settings.priceFilters[settings.priceFilters.length] = facetEntry[1];
                } else if(filterType[facetEntry[0]] == INDUSTRY_FILTER_TYPE) {
                    settings.industryFilters[settings.industryFilters.length] = facetEntry[1];
                } else if(filterType[facetEntry[0]] == THEME_FILTER_TYPE) {
                    settings.themeFilters[settings.themeFilters.length] = facetEntry[1];
                } else if(filterType[facetEntry[0]] == PRODUCT_TYPE_FILTER_TYPE) {
                    settings.productTypeFilters[settings.productTypeFilters.length] = facetEntry[1];
                }
            });
        };

        var getServerSideCategoryIds = function() {
            if(settings.afData && settings.afData != '') {
                return '&af=' + settings.afData;
            }
            var serverSideCategoryIds = '';
            if(settings.sizeFilters.length == 0 && settings.colorFilters.length == 0 && settings.finishFilters.length == 0 && settings.exactColorFilters.length == 0 && settings.ratingsFilters.length == 0 && settings.collectionFilters.length == 0 && settings.weightFilters.length == 0 && settings.sealingFilters.length == 0 && settings.priceFilters.length == 0 && settings.industryFilters.length == 0 && settings.themeFilters.length == 0 && settings.productTypeFilters.length == 0) {
                if(settings.categoryFilters.length == 1 && settings.styleFilters.length <= 1) {
                    serverSideCategoryIds = '&category_id=' + settings.categoryFilters[0];
                }
                if(settings.styleFilters.length == 1) {
                    serverSideCategoryIds += '&style_id=' + settings.styleFilters[0];
                } else if(settings.styleFilters.length == 0 && settings.categoryFilters.length == 0) {
                    serverSideCategoryIds = 'all';
                }
            }
            return serverSideCategoryIds;
        };

        /**
         * Public init function that needs to be called from the html page to initialize the RefinementsWidget
         */
        this.init = function() {

            //Bind the onchage events to all the filter dropdowns.
            bindOnchangeEventToFilters();

            if(settings.requestType == 'SEARCH') {
                bindOnchangeEventToPageLimit();
            }

            // Load any appliedFacets passed in by settings.afData
            loadAppliedFacets();

            loadAppliedFilters();

            disableEmptyFilters();

            disableAppliedFilters();

            toggleExactColorFilter();

            bindRemoveEventToAppliedFilters();

            bindPagination();

            currentSort = settings.sort;
            currentPage = settings.page;

            bindOnChangeEventToSortBy();

            if(settings.loadOnInit) {
                this.load();
            }
        };

        var load = function() {

            //Trigger the filterChangedEvent to apply the filters passed as part of the widget settings.
            filterChangedEvent();

        };

        var filterChangedEvent = function() {
            loadAppliedFilters();
            //Apply the refinements based on the selected filters.
            applyRefinements();
        };

        var loadAppliedFilters = function() {
            parseFilters(settings.categoryFilters, CATEGORY_FILTER_TYPE);
            parseFilters(settings.sizeFilters, SIZE_FILTER_TYPE);
            parseFilters(settings.colorFilters, COLOR_FILTER_TYPE);
            parseFilters(settings.styleFilters, STYLE_FILTER_TYPE);
            parseFilters(settings.exactColorFilters, EXACT_COLOR_FILTER_TYPE);
            parseFilters(settings.finishFilters, FINISH_FILTER_TYPE);
            parseFilters(settings.ratingsFilters, RATING_FILTER_TYPE);
            parseFilters(settings.collectionFilters, COLLECTION_FILTER_TYPE);
            parseFilters(settings.weightFilters, WEIGHT_FILTER_TYPE);
            parseFilters(settings.sealingFilters, SEALING_FILTER_TYPE);
            parseFilters(settings.priceFilters, PRICE_FILTER_TYPE);
            parseFilters(settings.industryFilters, INDUSTRY_FILTER_TYPE);
            parseFilters(settings.themeFilters, THEME_FILTER_TYPE);
            parseFilters(settings.productTypeFilters, PRODUCT_TYPE_FILTER_TYPE);

        };

        var disableEmptyFilters = function() {
            $('[class*=jqs-filter-]').each(function(i, filterDropDownElement) {
                if($('option', filterDropDownElement).length < 2) {
                    $(filterDropDownElement).prop('disabled', 'disabled');
                } else {
                    $(filterDropDownElement).prop('disabled', '');
                }
            });

        };

        var disableAppliedFilters = function() {
            $.each(settings.categoryFilters, function(i, filter){
                disableSelectedFilterOption('select.jqs-filter-category', filter);
            });

            $.each(settings.sizeFilters, function(i, filter){
                disableSelectedFilterOption('select.jqs-filter-size', filter);
            });

            $.each(settings.colorFilters, function(i, filter){
                disableSelectedFilterOption('select.jqs-filter-color', filter);
            });

            $.each(settings.styleFilters, function(i, filter){
                disableSelectedFilterOption('select.jqs-filter-style', filter);
            });

            $.each(settings.exactColorFilters, function(i, filter){
                disableSelectedFilterOption('select.jqs-filter-exact-color', filter);
            });

            $.each(settings.finishFilters, function(i, filter){
                disableSelectedFilterOption('select.jqs-filter-finish', filter);
            });

            $.each(settings.ratingsFilters, function(i, filter){
                disableSelectedFilterOption('select.jqs-filter-rating', filter);
            });

            $.each(settings.collectionFilters, function(i, filter){
                disableSelectedFilterOption('select.jqs-filter-collection', filter);
            });

            $.each(settings.weightFilters, function(i, filter){
                disableSelectedFilterOption('select.jqs-filter-weight', filter);
            });

            $.each(settings.sealingFilters, function(i, filter){
                disableSelectedFilterOption('select.jqs-filter-sealing', filter);
            });

            $.each(settings.priceFilters, function(i, filter){
                disableSelectedFilterOption('select.jqs-filter-price', filter);
            });

            $.each(settings.industryFilters, function(i, filter){
                disableSelectedFilterOption('select.jqs-filter-industry', filter);
            });

            $.each(settings.themeFilters, function(i, filter){
                disableSelectedFilterOption('select.jqs-filter-theme', filter);
            });

            $.each(settings.productTypeFilters, function(i, filter){
                disableSelectedFilterOption('select.jqs-filter-product-type', filter);
            });
        };

        /**
         * Binding the onchange event to all filter dropdowns
         */
        var bindOnchangeEventToFilters = function() {

            elem.find('select.jqs-filter-category').on('change', function(e) {
                sendFacetToGoogle(this);
                filterOnChangeEvent(CATEGORY_FILTER_TYPE, this);
            });

            elem.find('select.jqs-filter-size').on('change', function(e) {
                sendFacetToGoogle(this);
                filterOnChangeEvent(SIZE_FILTER_TYPE, this);
            });

            elem.find('select.jqs-filter-color').on('change', function(e) {
                sendFacetToGoogle(this);
                filterOnChangeEvent(COLOR_FILTER_TYPE, this);
            });

            elem.find('select.jqs-filter-style').on('change', function(e) {
                sendFacetToGoogle(this);
                filterOnChangeEvent(STYLE_FILTER_TYPE, this);
            });

            elem.find('select.jqs-filter-exact-color').on('change', function(e) {
                sendFacetToGoogle(this);
                filterOnChangeEvent(EXACT_COLOR_FILTER_TYPE, this);
            });

            elem.find('select.jqs-filter-finish').on('change', function(e) {
                sendFacetToGoogle(this);
                filterOnChangeEvent(FINISH_FILTER_TYPE, this);
            });

            elem.find('select.jqs-filter-rating').on('change', function(e) {
                sendFacetToGoogle(this);
                filterOnChangeEvent(RATING_FILTER_TYPE, this);
            });

            elem.find('select.jqs-filter-collection').on('change', function(e) {
                sendFacetToGoogle(this);
                filterOnChangeEvent(COLLECTION_FILTER_TYPE, this);
            });

            elem.find('select.jqs-filter-weight').on('change', function(e) {
                sendFacetToGoogle(this);
                filterOnChangeEvent(WEIGHT_FILTER_TYPE, this);
            });

            elem.find('select.jqs-filter-sealing').on('change', function(e) {
                sendFacetToGoogle(this);
                filterOnChangeEvent(SEALING_FILTER_TYPE, this);
            });

            elem.find('select.jqs-filter-price').on('change', function(e) {
                sendFacetToGoogle(this);
                filterOnChangeEvent(PRICE_FILTER_TYPE, this);
            });

            elem.find('select.jqs-filter-industry').on('change', function(e) {
                sendFacetToGoogle(this);
                filterOnChangeEvent(INDUSTRY_FILTER_TYPE, this);
            });

            elem.find('select.jqs-filter-theme').on('change', function(e) {
                sendFacetToGoogle(this);
                filterOnChangeEvent(THEME_FILTER_TYPE, this);
            });

            elem.find('select.jqs-filter-product-type').on('change', function(e) {
                sendFacetToGoogle(this);
                filterOnChangeEvent(PRODUCT_TYPE_FILTER_TYPE, this);
            });

        };

        var sendFacetToGoogle = function(filter) {
            GoogleAnalytics.trackEvent(settings.requestType + " Filters", $().capitalize($(filter).attr('name')), $().capitalize($(filter).find(':selected').text()));
        };

        var bindOnchangeEventToPageLimit = function() {
            elem.find('select.jqs-page-limit').on('change', function(e) {
                pageLimitOnChangeEvent(this);
            });
            elem.find('select.jqs-page-limit').val(settings.fetchSize);
        };

        var pageLimitOnChangeEvent = function (pageLimitElement) {
            if(settings.fetchSize != $(pageLimitElement).val()) {
                settings.fetchSize = $(pageLimitElement).val();
                applyRefinements();
                pushState();
            }
        };

        /**
         * The actual onchange event bound to all filter dropdowns
         * @param filterType
         * @param filterElement
         */
        var filterOnChangeEvent = function(filterType, filterElement, removedFlag) {
            reset();
            var filterChanged = true;
            var selectedValue;
            if(filterType == CATEGORY_FILTER_TYPE) {
                if(removedFlag) {
                    settings.categoryFilters = removeFilter(settings.categoryFilters, $(filterElement).data('value'));
                } else {
                    selectedValue = $(filterElement).val();
                    filterChanged = selectedValue != '' && !contains(settings.categoryFilters, $(filterElement).val());
                    if(filterChanged) {
                        settings.categoryFilters[settings.categoryFilters.length] = $(filterElement).val();
                    }
                }
            } else if(filterType == SIZE_FILTER_TYPE) {
                if(removedFlag) {
                    settings.sizeFilters = removeFilter(settings.sizeFilters, $(filterElement).data('value'));
                } else {
                    selectedValue = $(filterElement).val();
                    filterChanged = selectedValue != '' && !contains(settings.sizeFilters, $(filterElement).val());
                    if(filterChanged) {
                        settings.sizeFilters[settings.sizeFilters.length] = $(filterElement).val();
                    }
                }
            } else if(filterType == COLOR_FILTER_TYPE) {
                if(removedFlag) {
                    settings.colorFilters = removeFilter(settings.colorFilters, $(filterElement).data('value'));
                } else {
                    selectedValue = $(filterElement).val();
                    filterChanged = selectedValue != '' && !contains(settings.colorFilters, $(filterElement).val());
                    if(filterChanged) {
                        settings.colorFilters[settings.colorFilters.length] = $(filterElement).val();
                    }
                }
            } else if(filterType == STYLE_FILTER_TYPE) {
                if(removedFlag) {
                    settings.styleFilters = removeFilter(settings.styleFilters, $(filterElement).data('value'));
                } else {
                    selectedValue = $(filterElement).val();
                    filterChanged = selectedValue != '' && !contains(settings.styleFilters, $(filterElement).val());
                    if(filterChanged) {
                        settings.styleFilters[settings.styleFilters.length] = $(filterElement).val();
                    }
                }
            } else if(filterType == EXACT_COLOR_FILTER_TYPE) {
                if(removedFlag) {
                    settings.exactColorFilters = removeFilter(settings.exactColorFilters, $(filterElement).data('value'));
                } else {
                    selectedValue = $(filterElement).val();
                    filterChanged = selectedValue != '' && !contains(settings.exactColorFilters, $(filterElement).val());
                    if(filterChanged) {
                        settings.exactColorFilters[settings.exactColorFilters.length] = $(filterElement).val();
                    }
                }
            } else if(filterType == FINISH_FILTER_TYPE) {
                if(removedFlag) {
                    settings.finishFilters = removeFilter(settings.finishFilters, $(filterElement).data('value'));
                } else {
                    selectedValue = $(filterElement).val();
                    filterChanged = selectedValue != '' && !contains(settings.finishFilters, $(filterElement).val());
                    if(filterChanged) {
                        settings.finishFilters[settings.finishFilters.length] = $(filterElement).val();
                    }
                }
            } else if(filterType == RATING_FILTER_TYPE) {
                if(removedFlag) {
                    settings.ratingsFilters = removeFilter(settings.ratingsFilters, $(filterElement).data('value'));
                } else {
                    selectedValue = $(filterElement).val();
                    filterChanged = selectedValue != '' && !contains(settings.ratingsFilters, $(filterElement).val());
                    if(filterChanged) {
                        settings.ratingsFilters[settings.ratingsFilters.length] = $(filterElement).val();
                    }
                }
            } else if(filterType == COLLECTION_FILTER_TYPE) {
                if(removedFlag) {
                    settings.collectionFilters = removeFilter(settings.collectionFilters, $(filterElement).data('value'));
                } else {
                    selectedValue = $(filterElement).val();
                    filterChanged = selectedValue != '' && !contains(settings.collectionFilters, $(filterElement).val());
                    if(filterChanged) {
                        settings.collectionFilters[settings.collectionFilters.length] = $(filterElement).val();
                    }
                }
            } else if(filterType == WEIGHT_FILTER_TYPE) {
                if(removedFlag) {
                    settings.weightFilters = removeFilter(settings.weightFilters, $(filterElement).data('value'));
                } else {
                    selectedValue = $(filterElement).val();
                    filterChanged = selectedValue != '' && !contains(settings.weightFilters, $(filterElement).val());
                    if(filterChanged) {
                        settings.weightFilters[settings.weightFilters.length] = $(filterElement).val();
                    }
                }
            } else if(filterType == SEALING_FILTER_TYPE) {
                if(removedFlag) {
                    settings.sealingFilters = removeFilter(settings.sealingFilters, $(filterElement).data('value'));
                } else {
                    selectedValue = $(filterElement).val();
                    filterChanged = selectedValue != '' && !contains(settings.sealingFilters, $(filterElement).val());
                    if(filterChanged) {
                        settings.sealingFilters[settings.sealingFilters.length] = $(filterElement).val();
                    }
                }
            } else if(filterType == PRICE_FILTER_TYPE) {
                if(removedFlag) {
                    settings.priceFilters = removeFilter(settings.priceFilters, $(filterElement).data('value'));
                } else {
                    selectedValue = $(filterElement).val();
                    filterChanged = selectedValue != '' && !contains(settings.priceFilters, $(filterElement).val());
                    if(filterChanged) {
                        settings.priceFilters[settings.priceFilters.length] = $(filterElement).val();
                    }
                }
            } else if(filterType == INDUSTRY_FILTER_TYPE) {
                if(removedFlag) {
                    settings.industryFilters = removeFilter(settings.industryFilters, $(filterElement).data('value'));
                } else {
                    selectedValue = $(filterElement).val();
                    filterChanged = selectedValue != '' && !contains(settings.industryFilters, $(filterElement).val());
                    if(filterChanged) {
                        settings.industryFilters[settings.industryFilters.length] = $(filterElement).val();
                    }
                }
            } else if(filterType == THEME_FILTER_TYPE) {
                if(removedFlag) {
                    settings.themeFilters = removeFilter(settings.themeFilters, $(filterElement).data('value'));
                } else {
                    selectedValue = $(filterElement).val();
                    filterChanged = selectedValue != '' && !contains(settings.themeFilters, $(filterElement).val());
                    if(filterChanged) {
                        settings.themeFilters[settings.themeFilters.length] = $(filterElement).val();
                    }
                }
            } else if(filterType == PRODUCT_TYPE_FILTER_TYPE) {
                if(removedFlag) {
                    settings.productTypeFilters = removeFilter(settings.productTypeFilters, $(filterElement).data('value'));
                } else {
                    selectedValue = $(filterElement).val();
                    filterChanged = selectedValue != '' && !contains(settings.productTypeFilters, $(filterElement).val());
                    if(filterChanged) {
                        settings.productTypeFilters[settings.productTypeFilters.length] = $(filterElement).val();
                    }
                }
            }else {
                // Invalid or unsupported filter type, ignore and return without refining the results.
                return;
            }

            if(!removedFlag) {
                $(filterElement).val('');
            }

            // Invoke the filterChangedEvent
            if(filterChanged) {
                filterChangedEvent();
            }
        };

        var bindOnChangeEventToSortBy = function() {
            elem.find('select.jqs-sort-by').unbind('change').on('change', function(e) {
                sortByOnChangeEvent(this);
            });
        };

        var sortByOnChangeEvent = function(sortByElement) {
            var sortBy = $(sortByElement).val();
            sortProducts(sortBy);
        };

        var contains = function(values, value) {
            for(var i = 0; i < values.length; i ++) {
                if(values[i] == value) {
                    return true;
                }
            }
            return false;
        };

        var toArray = function(value) {
            if(value instanceof Array == true) {
                return value;
            } else {
                return [ value ];
            }
        };

        var removeFilter = function(selectedFilters, removedFilter) {
            var updatedFilters = [];
            var idx = 0;
            for (var i = 0; i < selectedFilters.length; i++) {
                if (selectedFilters [i] != removedFilter) {
                    updatedFilters[idx ++] = selectedFilters[i];
                }
            }
            return updatedFilters;
        };

        var removeAllFilters = function() {
            settings.categoryFilters   = [];
            settings.styleFilters      = [];
            settings.sizeFilters       = [];
            settings.colorFilters      = [];
            settings.finishFilters     = [];
            settings.exactColorFilters = [];
            settings.ratingsFilters    = [];
            settings.collectionFilters = [];
            settings.weightFilters     = [];
            settings.sealingFilters    = [];
            settings.priceFilters      = [];
            settings.industryFilters   = [];
            settings.themeFilters      = [];
            settings.productTypeFilters= [];

            filterChangedEvent();
        };

		var startSpinner = function() {
			$("body").spinner(true, false, 200);
		};

        var clearSpinner = function() {
			clearTimeout(SPINNER_TIMEOUT);
			$("body").spinner(false);
		};


        /**
         * Apply the refinements based on the selected filters. This method will make a JSONP request to SLI
         */
        var applyRefinements = function(ignoreInternal) {
        	window.scrollTo(0, $('.category').offset().top);

            settings.afData = '';
            // Hardcoded for sale facet, since we don't have any filter UI available for sale
            if(options.afData.indexOf('onsale:sale') != -1) {
                settings.afData = options.afData;
            }
            $('.pagination').html('');
            rawRefinedItems = [];
            merch = undefined;
            /*
            if(settings.loadingImageIcon != '') {
                elem.find('#jqs-refinements-result').html('<img src="' + settings.loadingImageIcon + '"> loading...');
            }
            */

            currentPage = 0;
            if(settings.requestType == 'SEARCH' || ignoreInternal || getServerSideCategoryIds() == '') {
            	SPINNER_TIMEOUT = window.setTimeout(function() {
					startSpinner();
				}, 250);

				var process = window.setTimeout(function() {
					fetchType = 'sli';
					fetchResult(processResponse);
				}, 0);
            } else {
                fetchType = 'internal';
                fetchResultInternal(processInternalResponse, getServerSideCategoryIds().replace('all', ''));
            }
        };

        var goToPage = function(page, internalCall) {
            currentPage = page;
            if(settings.requestType == 'SEARCH') {
                fetchType = 'sli';
                fetchResult(processResponse, currentPage);
            } else {
                if(internalCall) {
                    fetchResultInternal(processInternalResponse, getServerSideCategoryIds().replace('all', ''));
                } else {
                    refinedResult.products = refinedResult.getPaginatedProducts()[page];
                    renderProducts();
                    pushState();
                }
            }
            //GoogleAnalytics.trackPageview(window.location.pathname + window.location.search, document.title);
        };

        var sortProducts = function(sortBy) {
            currentSort = sortBy;
            currentPage = 0;
            if(settings.requestType == 'SEARCH') {
                fetchType = 'sli';
                fetchResult(processResponse, currentPage);
            } else {
                if (fetchType == 'sli' || getServerSideCategoryIds() == '') {
                    refinedResult.sortProducts(sortBy);
                    refinedResult.products = refinedResult.getPaginatedProducts()[currentPage];
                    renderProducts();
                    pushState();
                } else if (fetchType == 'internal' || getServerSideCategoryIds() != '') {
                    fetchResultInternal(processInternalResponse, getServerSideCategoryIds().replace('all', ''));
                }
            }
        };

        var processInternalResponse = function(data) {
            if(data.success == true) {
                // Render the updated filter options
                renderFilterOptions(data);

                // Render the applied filters
                renderAppliedFilters();

                // Render the processed results received from the internal server
                renderResult(data);
            } else {
                applyRefinements(true);
            }
        };

        /**
         * Callback function used to process the initial JSONP response
         * @param data - The JSON data received from SLI
         */
        var processResponse = function(data) {
            // Render the updated filter options
            renderFilterOptions(data);

            // Render the applied filters
            renderAppliedFilters();

            if(settings.requestType == 'SEARCH') {
                if(data.results === undefined || data.results.length == 0) {
                    renderEmptyResults(true);
                } else {
                    renderResult(data, true);
                }
            } else {
                // Fetch the remaining items, if any, for the given filer criteria from SLI.
                fetchCompleteResult(data, true);
            }
        };

        /**
         * Render the updated filter options received from SLI corresponding to the currently applied filter options
         * @param data - the updated filter options received from SLI
         */
        var renderFilterOptions = function(data) {
        	$('.filter-list .envelope-select').each(function() {
        		$(this).addClass('hidden');
        	});

            $("[class*=jqs-filter-]").prop('disabled', 'disabled');

            $.each(data.facets, function(i, filter){
                var filterOptions = filter.values;
                if(filter.id == facets[CATEGORY_FILTER_TYPE]) {
                    updateFilterOptions(elem.find('select.jqs-filter-category'), filterOptions, categoryFilterCollection);
                } else if(filter.id == facets[SIZE_FILTER_TYPE]) {
                    updateFilterOptions(elem.find('select.jqs-filter-size'), filterOptions.sort(ascendingSizeComparator), sizeFilterCollection);
                } else if(filter.id == facets[COLOR_FILTER_TYPE]) {
                    updateFilterOptions(elem.find('select.jqs-filter-color'), filterOptions.sort(genericAlphabeticComparator), colorFilterCollection);
                } else if(filter.id == facets[STYLE_FILTER_TYPE]) {
                    updateFilterOptions(elem.find('select.jqs-filter-style'), filterOptions.sort(genericAlphabeticComparator), styleFilterCollection);
                } else if(filter.id == facets[EXACT_COLOR_FILTER_TYPE]) {
                    updateFilterOptions(elem.find('select.jqs-filter-exact-color'), filterOptions.sort(genericAlphabeticComparator), exactColorFilterCollection);
                } else if(filter.id == facets[FINISH_FILTER_TYPE]) {
                    updateFilterOptions(elem.find('select.jqs-filter-finish'), filterOptions.sort(genericAlphabeticComparator), finishFilterCollection);
                } else if(filter.id == facets[RATING_FILTER_TYPE]) {
                    updateFilterOptions(elem.find('select.jqs-filter-rating'), formatRatings(filterOptions), ratingFilterCollection);
                } else if(filter.id == facets[COLLECTION_FILTER_TYPE]) {
                    updateFilterOptions(elem.find('select.jqs-filter-collection'), filterOptions.sort(genericAlphabeticComparator), collectionFilterCollection);
                } else if(filter.id == facets[WEIGHT_FILTER_TYPE]) {
                    updateFilterOptions(elem.find('select.jqs-filter-weight'), filterOptions, weightFilterCollection);
                } else if(filter.id == facets[SEALING_FILTER_TYPE]) {
                    updateFilterOptions(elem.find('select.jqs-filter-sealing'), filterOptions.sort(genericAlphabeticComparator), sealingFilterCollection);
                } else if(filter.id == facets[PRICE_FILTER_TYPE]) {
                    updateFilterOptions(elem.find('select.jqs-filter-price'), filterOptions, priceFilterCollection);
                } else if(filter.id == facets[INDUSTRY_FILTER_TYPE]) {
                    updateFilterOptions(elem.find('select.jqs-filter-industry'), filterOptions.sort(genericAlphabeticComparator), industryFilterCollection);
                } else if(filter.id == facets[THEME_FILTER_TYPE]) {
                    updateFilterOptions(elem.find('select.jqs-filter-theme'), filterOptions.sort(genericAlphabeticComparator), themeFilterCollection);
                } else if(filter.id == facets[PRODUCT_TYPE_FILTER_TYPE]) {
                    updateFilterOptions(elem.find('select.jqs-filter-product-type'), filterOptions.sort(genericAlphabeticComparator), productTypeFilterCollection);
                }
            });

            checkFilterList();
        };

        var formatRatings = function(filterOptions) {
            for(var i = 0; i < filterOptions.length; i ++) {
                filterOptions[i].name = parseFloat(filterOptions[i].name).toPrecision(2);
                if(filterOptions[i].name == '1.0') {
                    filterOptions[i].name += ' Star';
                } else {
                    filterOptions[i].name += ' Stars';
                }
            }
            return filterOptions;
        };

        /**
         * Update the filter dropdown option on the HTML page with the refined filter options received from SLI
         * @param dropDownElement
         * @param filterOptions
         * @param filterMap
         */
        var updateFilterOptions = function(dropDownElement, filterOptions, filterMap) {
        	$(dropDownElement).parent().removeClass('hidden');
            $(dropDownElement).find('option:gt(0)').remove();
            $.each(filterOptions, function(j, filterOption){
                var optionText = filterOption.name;
                if(optionText == 'Products') {
                    optionText = 'Customizable Products';
                } else if(optionText == 'Designs') {
                    optionText = 'Customizable Templates';
                }
                $(dropDownElement).append('<option value="' + filterOption.id + '">' + optionText + '</option>');
                filterMap[filterOption.id] = optionText;
            });
            $(dropDownElement).prop('disabled', '');
        };

        /**
         * Render all applied filters on the HTML page
         */
        var renderAppliedFilters = function() {
            elem.find('.jqs-selected-filter').remove();
            toggleExactColorFilter();
            $.each(settings.categoryFilters, function(i, filter){
                renderAppliedFilter(filter, categoryFilterCollection[filter], CATEGORY_FILTER_TYPE);
                disableSelectedFilterOption('select.jqs-filter-category', filter);
            });

            $.each(settings.sizeFilters, function(i, filter){
                renderAppliedFilter(filter, sizeFilterCollection[filter], SIZE_FILTER_TYPE);
                disableSelectedFilterOption('select.jqs-filter-size', filter);
            });

            $.each(settings.colorFilters, function(i, filter){
                renderAppliedFilter(filter, colorFilterCollection[filter], COLOR_FILTER_TYPE);
                disableSelectedFilterOption('select.jqs-filter-color', filter);
            });

            $.each(settings.styleFilters, function(i, filter){
                renderAppliedFilter(filter, styleFilterCollection[filter], STYLE_FILTER_TYPE);
                disableSelectedFilterOption('select.jqs-filter-style', filter);
            });

            $.each(settings.exactColorFilters, function(i, filter){
                renderAppliedFilter(filter, exactColorFilterCollection[filter], EXACT_COLOR_FILTER_TYPE);
                disableSelectedFilterOption('select.jqs-filter-exact-color', filter);
            });

            $.each(settings.finishFilters, function(i, filter){
                renderAppliedFilter(filter, finishFilterCollection[filter], FINISH_FILTER_TYPE);
                disableSelectedFilterOption('select.jqs-filter-finish', filter);
            });

            $.each(settings.ratingsFilters, function(i, filter){
                renderAppliedFilter(filter, ratingFilterCollection[filter], RATING_FILTER_TYPE);
                disableSelectedFilterOption('select.jqs-filter-rating', filter);
            });

            $.each(settings.collectionFilters, function(i, filter){
                renderAppliedFilter(filter, collectionFilterCollection[filter], COLLECTION_FILTER_TYPE);
                disableSelectedFilterOption('select.jqs-filter-collection', filter);
            });

            $.each(settings.weightFilters, function(i, filter){
                renderAppliedFilter(filter, weightFilterCollection[filter], WEIGHT_FILTER_TYPE);
                disableSelectedFilterOption('select.jqs-filter-weight', filter);
            });

            $.each(settings.sealingFilters, function(i, filter){
                renderAppliedFilter(filter, sealingFilterCollection[filter], SEALING_FILTER_TYPE);
                disableSelectedFilterOption('select.jqs-filter-sealing', filter);
            });

            $.each(settings.priceFilters, function(i, filter){
                renderAppliedFilter(filter, priceFilterCollection[filter], PRICE_FILTER_TYPE);
                disableSelectedFilterOption('select.jqs-filter-price', filter);
            });

            $.each(settings.industryFilters, function(i, filter){
                renderAppliedFilter(filter, industryFilterCollection[filter], INDUSTRY_FILTER_TYPE);
                disableSelectedFilterOption('select.jqs-filter-industry', filter);
            });

            $.each(settings.themeFilters, function(i, filter){
                renderAppliedFilter(filter, themeFilterCollection[filter], THEME_FILTER_TYPE);
                disableSelectedFilterOption('select.jqs-filter-theme', filter);
            });
            if(settings.requestType == 'SEARCH') {
                $.each(settings.productTypeFilters, function(i, filter){
                    var filterText = '';
                    if(productTypeFilterCollection[filter] == 'Products') {
                        filterText = 'Customizable Products';
                    } else if(productTypeFilterCollection[filter] == 'Designs'){
                        filterText = 'Customizable Templates';
                    } else {
                        filterText = productTypeFilterCollection[filter];
                    }
                    renderAppliedFilter(filter, filterText, PRODUCT_TYPE_FILTER_TYPE);
                    disableSelectedFilterOption('select.jqs-filter-product-type', filter);
                });
            }
            checkFilterContainerLength();
        };

        var toggleExactColorFilter = function() {
            if(settings.colorFilters.length > 0 || settings.exactColorFilters.length > 0) {
                $('select.jqs-filter-exact-color').parent().removeClass('hidden');
            } else {
                $('select.jqs-filter-exact-color').parent().addClass('hidden');
            }
        };

        var disableSelectedFilterOption = function(filterDropDownElement, filter) {
            var optionElement = $(filterDropDownElement).find('option[value="' + filter + '"]');
            optionElement.text('* ' + optionElement.text());
            optionElement.prop('disabled', 'disabled');
            $(filterDropDownElement).val('');
        };

        var renderAppliedFilter = function(value, text, type) {
            $(".applied-filter-list").each(function() {
				$(this).children('li:last').before(
					$('<li data-value="'+value+'" class="no-margin margin-bottom-xxs margin-right-xxs jqs-selected-filter filter"/>').on('click', function(e){
						filterOnChangeEvent(type, this, true);
					}).append(
						$('<div/>').text(text).append(
							$('<i class="fa fa-times margin-left-xxs jqs-remove-filter"/>')
						)
					)
				);
            });
        };

        var bindRemoveEventToAppliedFilters = function() {
            elem.find('.jqs-selected-filter').each(function(i, filterElement) {
                var value = $(filterElement).data('value');
                if(value && value.indexOf('~') != -1) {
                    var values = value.split('~');
                    $(filterElement).data('value', values[1]).on('click', function(e){filterOnChangeEvent(values[0], this, true);});
                }
            });
            elem.find('.jqs-clear-filters').on('click', function(e) {removeAllFilters()});
        };

        /**
         * The array used to store the complete variant collection received from SLI corresponding to the selected
         * filters. The items are stored with the complete attributes that we got from SLI. The processResult()
         * method will perform the sorting, grouping and ordering of this raw data and the processed data is used
         * for rendering the refined items grouped by products on the HTML page.
         **/
        var rawRefinedItems = [];

        var merch;

        /**
         * The final model object containing the refined products and their variants, after applying the
         * currently selected filters. This model object is used to render the refined products on the HTML page.
         */
        var refinedResult = {
            count: 0,
            allProducts: [],
            products: [],
            paginatedProducts: undefined,
            pages: 0,
            numOfSliItems: 0,
            numOfSliPages: 0,
            bannerName: '',
            getPaginatedProducts: function() {
                if(this.paginatedProducts === undefined) {
                    this.paginatedProducts = [];
                    var productsOnPage = [];
                    for(var i = 0; i < this.allProducts.length; i ++) {
                        if(i % settings.productPageSize == 0 && i > 0) {
                            this.paginatedProducts[this.paginatedProducts.length] = productsOnPage;
                            productsOnPage = [];
                        }
                        productsOnPage[productsOnPage.length] = this.allProducts[i];
                    }
                    if(productsOnPage.length > 0) {
                        this.paginatedProducts[this.paginatedProducts.length] = productsOnPage;
                    }
                    this.pages = this.paginatedProducts.length;
                }
                return this.paginatedProducts;
            },
            sortProducts: function(sortBy) {
                switch(sortBy) {
                    case 'SIZE_SMALL':
                        this.allProducts.sort(ascendingSizeComparator);
                        break;

                    case 'SIZE_LARGE':
                        this.allProducts.sort(descendingSizeComparator);
                        break;
                    case 'PRICE_LOW':
                        this.allProducts.sort(ascendingPriceComparator);
                        break;
                    case 'PRICE_HIGH':
                        this.allProducts.sort(descendingPriceComparator);
                        break;
                    case 'MOST_POPULAR':
                        this.allProducts.sort(popularityComparator);
                        break;
                    default :
                        sortBy = 'SIZE_SMALL';
                        currentSort = sortBy;
                        this.allProducts.sort(ascendingSizeComparator);
                        break;
                }
                this.paginatedProducts = undefined;
            },
            reset: function() {
                this.count = 0;
                this.allProducts = [];
                this.products = [];
                this.paginatedProducts = undefined;
            },
            getNumOfSliItems: function() {
                return this.numOfSliItems;
            },
            getNumOfSliPages: function() {
                return this.numOfSliPages;
            },
            getBannerName: function() {
                return this.bannerName;
            }

        };

        /**
         * The sliAjaxController used to keep track of the status of all AJAX requests, related to a given refinements filter criteria.
         * This will invoke the onDataReadyEvent, when all the AJAX request completes successfully or with error. If the primary AJAX
         * request fails, this will invoke the abortOperationEvent, to show the error message on the HTML page.
         */
        var sliAjaxCtrl = {
            totalAjaxRequests: 0,       // Total AJAX requests required to fetch the complete result set
            succeededRequests: 0,       // Number of AJAX requests finished successfully
            failedRequests: 0,          // Number of AJAX requests failed
            /**
             * Helper function used to keep track of successful AJAX requests.
             */
            success: function() {
                this.succeededRequests = this.succeededRequests + 1;
                if(this.hasFinished()) {
                   onDataReadyEvent();
                }
            },
            /**
             * Helper function used to keep track of failed AJAX requests
             * @param errorMessage - The error message that caused the failure
             */
            fail: function(errorMessage) {
                // If the initial AJAX request fails, then we have no option to proceed, so invoke the abort event.
                if(this.totalAjaxRequests == 0) {
                    abortOperationEvent(errorMessage);
                } else {
                    this.failedRequests = this.failedRequests + 1;
                    if(this.hasFinished()) {
                        onDataReadyEvent();
                    }
                }
            },
            /**
             * Helper function to set the total number of AJAX request required to fetch the complete result from SLI.
             * @param totalAjaxRequests
             */
            setAjaxRequests : function(totalAjaxRequests) {
                this.totalAjaxRequests = totalAjaxRequests;
            },
            /**
             * Helper function used to check if all the AJAX request completed successfully or failed due to error.
             * @returns {boolean}
             */
            hasFinished: function() {
                return this.totalAjaxRequests > 0 && this.totalAjaxRequests == this.succeededRequests + this.failedRequests;
            },
            reset : function() {
                this.totalAjaxRequests = 0;
                this.succeededRequests = 0;
                this.failedRequests = 0;
            }
        };

        var reset = function() {
            sliAjaxCtrl.reset();
            refinedResult.reset();
        };

        /**
         * Boolean variable used to handle aborting the current refinements execution.
         * @type {boolean}
         */
        var abort = false;

        var abortOperationEvent = function(errorMessage) {
            abort = true;
            /**
             * If aborted due to AJAX error, add the error message to the refineResult model object and show the error
             * message on the HTML page.
             */
            if(errorMessage) {
                refinedResult['error'] = true;
                refinedResult['errorMessage'] = errorMessage;
                renderResult();
            }
        };

        /**
         * For a given request to SLI, SLI will return a maximum of 300 items per response. So if the applied filters returns more than 300 items,
         * we need to make additional requests to SLI to fetch the remaining result items. If the total number of pages in initial SLI response is
         * greater than 1, then this method will call the fetchRemainingResults() method to fetch the remaining items from SLI.
         * @param data
         */
        var fetchCompleteResult = function(data) {
            Array.prototype.push.apply(rawRefinedItems,data.results);
            if(data.merch) {
                merch = data.merch;
            }

            /**
             *  Set the total number of AJAX calls required to fetch the complete result from SLI. We only need this set from the initial SLI response
             *  only. Since this value is the same in all responses, resetting this with every successful response won't do any harm. If the initial
             *  AJAX call to SLI fails, then the execution won't enter this method, rather the sliAjaxCtrl.fail() method will be invoked. Moreover, if the
             *  initial request fails, we won't be able to fetch any additional items from SLI(if any), since the metadata needed to fetch the additional items
             *  are read from the initial SLI response.
             */
            sliAjaxCtrl.setAjaxRequests(data.pages.total);
            /**
             * If the execution enter this method, then it means we got a successful AJAX response from SLI. So update the sliAjaxCtrl object's success count.
             * The 'sliAjaxCtrl.success()' method SHOULD always be called after 'sliAjaxCtrl.setAjaxRequests(data.pages.total)'.
             */
            sliAjaxCtrl.success();
            if(data.pages.current && parseInt(data.pages.current.name) == 1 && parseInt(data.pages.current.name) < parseInt(data.pages.total)) {
                fetchRemainingResults(data);
            } else if(data.pages.current === undefined) {
                renderEmptyResults();
            }
        };

        var onDataReadyEvent = function() {
            renderResult();
            clearSpinner();
            closeSlideOut($('#filters'));
        };

        /**
         * Method used to make additional calls to SLI for fetching remaining items, if any
         * @param data
         */
        var fetchRemainingResults =function(data) {
            for(var i = 1; i < parseInt(data.pages.total); i ++) {
                fetchResult(fetchCompleteResult, i/* * parseInt(settings.fetchSize)*/);
            }

        };

        /**
         * A ProductVariant Object that can be used to create new Product instances.
         */
        var ProductVariant = {};
        ProductVariant = function(sliItem, product) {

            this.name = '';
            this.size = '';
            this.price = '';
            this.imageURL = '';
            this.productVariantURL = '';
            this.colorGroup = '';
            this.color = '';
            this.rank = 999999;
            this.productType = '';
            this.rating = 0;
            this.productVariantId = '';
            this.printable = '';
            this.isNew = '';
            this.onClearance = '';
            this.salesRank = 0.0;
            this.salesPercentage = 0.0;
            this.onSale = '';
            this.productId = sliItem[PARENT_ID];

            // Public methods
            if(!ProductVariant.functionsDefined) {
                ProductVariant.prototype.getName = function() { return this.name; };
                ProductVariant.prototype.getSize = function() { return this.size; };
                ProductVariant.prototype.getPrice = function() { return this.price; };
                ProductVariant.prototype.getImageURL = function() { return this.imageURL; };
                ProductVariant.prototype.getProductVariantURL = function() { return this.productVariantURL; };
                ProductVariant.prototype.getColorGroup = function() { return this.colorGroup; };
                ProductVariant.prototype.getColor = function() { return this.color; };
                ProductVariant.prototype.getRank = function() { return this.rank;};
                ProductVariant.prototype.getProductType = function() { return this.productType; };
                ProductVariant.prototype.getRating = function() { return this.rating; };
                ProductVariant.prototype.getPrintable = function() { return typeof this.printable === 'undefined' ? 'N' : this.printable; };
                ProductVariant.prototype.getIsNew = function() { return typeof this.isNew === 'undefined' ? 'N' : this.isNew; };
                ProductVariant.prototype.getOnClearance = function() { return typeof this.onClearance === 'undefined' ? 'N' : this.onClearance; };
                ProductVariant.prototype.getOnSale = function() { return typeof this.onSale === 'undefined' ? '' : this.onSale; };
                ProductVariant.prototype.getSalesRank = function() { return typeof this.salesRank === 'undefined' ? 0 : this.salesRank; };
                ProductVariant.prototype.getSalesPercentage = function() { return typeof this.salesPercentage === 'undefined' ? 0 : this.salesPercentage; };
                ProductVariant.prototype.getProductVariantId = function() { return this.productVariantId; };

                ProductVariant.prototype.setName = function(nameArg) { this.name = nameArg; };
                ProductVariant.prototype.setSize = function(sizeArg) { this.size = sizeArg; };
                ProductVariant.prototype.setPrice = function(priceArg) { this.price = priceArg; };
                ProductVariant.prototype.setImageURL = function(imageURLArg) { this.imageURL = imageURLArg; };
                ProductVariant.prototype.setProductVariantURL = function(productVariantURLArg) { this.productVariantURL = productVariantURLArg; };
                ProductVariant.prototype.setColor = function(colorArg) { this.color = colorArg; };
                ProductVariant.prototype.setColorGroup = function(colorGroupArg) { this.colorGroup = colorGroupArg; };
                ProductVariant.prototype.setRank = function(rankArg) {this.rank = rankArg; };
                ProductVariant.prototype.setProductType = function(productTypeArg) { this.productType = productTypeArg; };
                ProductVariant.prototype.setRating = function(ratingArg) { this.rating = ratingArg; };
                ProductVariant.prototype.setPrintable = function(printableArg) { this.printable = printableArg; };
                ProductVariant.prototype.setIsNew = function(isNewArg) { this.isNew = isNewArg; };
                ProductVariant.prototype.setOnClearance = function(onClearanceArg) { this.onClearance = onClearanceArg; };
                ProductVariant.prototype.setOnSale = function(onSaleArg) { this.onSale = onSaleArg; };
                ProductVariant.prototype.setSalesRank = function(salesRankArg) { this.salesRank = salesRankArg; };
                ProductVariant.prototype.setSalesPercentage = function(salesPercentageArg) { this.salesPercentage = salesPercentageArg; };
                ProductVariant.prototype.setProductVariantId = function(productVariantIdArg) { this.productVariantId = productVariantIdArg; };

                ProductVariant.functionsDefined = true;
            }


            this.setName(sliItem[TITLE]);
            this.setProductVariantId(sliItem[PRODUCT_ID]);
            this.setSize(sliItem[SIZE]);
            this.setPrice(sliItem[PRICE]);
            if(settings.webSiteId == 'ae') {
                this.setProductVariantURL(sliItem[UNFRIENDLY_ITEM_URL].replace((sliItem[UNFRIENDLY_ITEM_URL].indexOf('https') > -1 ? 'https': 'http' )+ '://www.actionenvelope.com', location.protocol + '//' + location.hostname.replace(/:\d{1,}/, '')) + (settings.itemUrlParameters != '' ? '?' + settings.itemUrlParameters : ''));
            } else {
                this.setProductVariantURL(sliItem[ITEM_URL].replace((sliItem[ITEM_URL].indexOf('https') > -1 ? 'https' : 'http') + '://www.envelopes.com', location.protocol + '//' + location.hostname.replace(/:\d{1,}/, '')) + (settings.itemUrlParameters != '' ? '?' + settings.itemUrlParameters : ''));
            }
            this.setColorGroup(sliItem[COLOR_GROUP]);
            this.setColor(sliItem[COLOR]);
            this.setRank(sliItem[RANK]);
            this.setRating(sliItem[RATING] && sliItem[RATING] != '' ? sliItem[RATING] : '0');
            this.setOnSale(sliItem[ON_SALE] && sliItem[ON_SALE] != '' ? sliItem[ON_SALE] : '');
            this.setSalesRank(sliItem[SALES_RANK] && sliItem[SALES_RANK] != '' ? parseFloat(sliItem[SALES_RANK]) : 0);
            this.setSalesPercentage(sliItem[SALES_PERCENTAGE] && sliItem[SALES_PERCENTAGE] != '' ? parseFloat(sliItem[SALES_PERCENTAGE]) : 0);
            this.setProductType(sliItem[PRODUCT_TYPE]);
            if(this.getProductType() == 'Designs') {
                this.setPrintable('N');
            } else {
                this.setPrintable(sliItem[PRINTABLE]);
            }
            this.setIsNew(sliItem[NEW]);
            this.setOnClearance(sliItem[CLEARANCE]);

//            this.setImageURL(sliItem[IMAGE_URL].replace('small', 'medium'));
            this.setImageURL('//actionenvelope.scene7.com/is/image/ActionEnvelope/' + this.getProductVariantId() + '?wid=150&hei=96&fmt=png-alpha');

            if(product) {
                product.addProductVariant(this);
            }
        };

        /**
         * A Product Object that can be used to create new Product instances.
         */
        var Product = {};
        Product = function(sliItem) {

            this.id = '';
            this.name = '';
            this.formattedName = '';
            this.size = '';
            this.price = '';
            this.imageURL = '';
            this.productURL = '';    // This is the URL to the recommended productVariant
            this.rank = 999999;
            this.productVariantCount = 0;
            this.productVariants = [];
            this.productType = '';
            this.printable = '';
            this.isNew = '';
            this.onClearance = '';
            this.salesRank = 0;
            this.salesPercentage = 0;
            this.onSale = '';
            this.color = '';
            this.rating = '0';
            this.suppressSize = false;

            /**
             * Format the actual name of the product by stripping out the size within parenthesis.
             * @param name  -   Actual name
                 * @returns -   Cleaned up name with no size in parenthesis
             */
            var formatName = function(name) {
                /**
                 * The below regex will find size in any of these formats
                 *  ( 5 3/4 x 7 1/2 ), (10 x 13), (5-3/4 x 7-1/3), (6.5 x 9.5), etc                 *
                 */
//                return name.replace(/\s?\(\s?\d+((\s|-)\d+\/\d+|.\d+)?(\sx\s)(\d+)((\s|-)\d+\/\d+|.\d+)?\s?\)/, '');
                return name.replace(/\s?\(\s*\d+((\s|-)*\d+\/\d+|.\d+)?(\s*x\s*)(\d+)((\s|-)*\d+\/\d+|.\d+)?(\s*Closed)?\s*\)/, '');
            };

            if(!Product.functionsDefined) {

                Product.prototype.getId = function() { return this.id; };
                Product.prototype.getName = function(formatFlag) {

                    if(!formatFlag) {
                        return this.name;
                    }

                    if(this.formattedName == '') {
                        this.formattedName = formatName(this.name);
                    }
                    return this.formattedName;
                };
                Product.prototype.getSize = function() { return this.size; };
                Product.prototype.getPrice = function() { return this.price; };
                Product.prototype.getImageURL = function() { return this.imageURL; };
                Product.prototype.getProductURL = function() { return this.productURL; };
                Product.prototype.getProductVariantCount = function() { return this.productVariantCount; };
                Product.prototype.getProductVariants = function() { return this.productVariants; };
                Product.prototype.getRank = function() { return this.rank; };
                Product.prototype.getProductType = function() { return this.productType; };
                Product.prototype.getColor = function() { return this.color; };
                Product.prototype.getPrintable = function() { return typeof this.printable === 'undefined' ? 'N' : this.printable; };
                Product.prototype.getIsNew = function() { return typeof this.isNew === 'undefined' ? 'N' : this.isNew; };
                Product.prototype.getOnClearance = function() { return typeof this.onClearance === 'undefined' ? '' : this.onClearance; };
                Product.prototype.getSalesRank = function() { return typeof this.salesRank === 'undefined' ? 0 : this.salesRank; };
                Product.prototype.getSalesPercentage = function() { return typeof this.salesPercentage === 'undefined' ? 0 : this.salesPercentage; };
                Product.prototype.getOnSale = function() { return typeof this.onSale === 'undefined' ? 'N' : this.onSale; };
                Product.prototype.getRating = function() {
                    var rating = (parseFloat(this.rating) + '').replace('.', '_');
                    return rating.indexOf('_') > -1 ? rating : rating + '_0';
                };
                Product.prototype.getSuppressSize = function() { return this.suppressSize; };


                Product.prototype.setId = function(idArg) { this.id = idArg; };
                Product.prototype.setName = function(nameArg) { this.name = nameArg; };
                Product.prototype.setSize = function(sizeArg) { this.size = sizeArg; };
                Product.prototype.setPrice = function(priceArg) { this.price = priceArg; };
                Product.prototype.setImageURL = function(imageURLArg) { this.imageURL = imageURLArg; };
                Product.prototype.setProductURL = function(productURLArg) { this.productURL = productURLArg; };
                Product.prototype.addProductVariant = function(productVariantArg) {this.productVariants[this.productVariantCount++] = productVariantArg; };
                Product.prototype.setRank = function(rankArg) { this.rank = rankArg; };
                Product.prototype.setProductType = function(productTypeArg) { this.productType = productTypeArg; };
                Product.prototype.setColor = function(colorArg) { this.color = colorArg; };
                Product.prototype.setPrintable = function(printableArg) { this.printable = printableArg; };
                Product.prototype.setIsNew = function(isNewArg) { this.isNew = isNewArg; };
                Product.prototype.setOnClearance = function(onClearanceArg) { this.onClearance = onClearanceArg; };
                Product.prototype.setSalesRank = function(salesRankArg) { this.salesRank = salesRankArg; };
                Product.prototype.setSalesPercentage = function(salesPercentageArg) { this.salesPercentage = salesPercentageArg; };
                Product.prototype.setOnSale = function(onSaleArg) { this.onSale = onSaleArg; };
                Product.prototype.setRating = function(ratingArg) { this.rating = ratingArg; };

                Product.functionsDefined = true;
            }
            this.setId(sliItem[PARENT_ID]);
            this.setName(sliItem[TITLE]);
            this.setSize(sliItem[SIZE]);
            this.setPrice(sliItem[PRICE]);
            this.setRank(sliItem[RANK]);
            this.setColor(sliItem[COLOR]);
            if(settings.webSiteId == 'ae') {
                this.setProductURL(sliItem[UNFRIENDLY_ITEM_URL].replace('https://www.actionenvelope.com', location.protocol + '//' + location.hostname.replace(/:\d{1,}/, '')) + (settings.itemUrlParameters != '' ? '?' + settings.itemUrlParameters : ''));
            } else {
                this.setProductURL(sliItem[ITEM_URL].replace('https://www.envelopes.com', location.protocol + '//' + location.hostname.replace(/:\d{1,}/, '')) + (settings.itemUrlParameters != '' ? '?' + settings.itemUrlParameters : ''));
            }
            this.setRating(sliItem[RATING] && sliItem[RATING] != '' ? sliItem[RATING] : '0');
            this.setOnSale(sliItem[ON_SALE] && sliItem[ON_SALE] != '' ? sliItem[ON_SALE] : 'N');
            this.setSalesRank(sliItem[SALES_RANK] && sliItem[SALES_RANK] != '' ? parseFloat(sliItem[SALES_RANK]) : 0);
            this.setSalesPercentage(sliItem[SALES_PERCENTAGE] && sliItem[SALES_PERCENTAGE] != '' ? parseFloat(sliItem[SALES_PERCENTAGE]) : 0);
            this.setProductType(sliItem[PRODUCT_TYPE]);
//            this.setImageURL(sliItem[IMAGE_URL].replace('small', 'medium'));
            if(this.getProductType() == 'Designs') {
                this.setImageURL('//texel.envelopes.com/getBasicImage?id=' + sliItem[PRODUCT_ID] + '&fmt=png&wid=175&fmt=png-alpha');
                this.setPrintable('N');
            } else {
                this.setImageURL('//actionenvelope.scene7.com/is/image/ActionEnvelope/' + sliItem[PRODUCT_ID] + '?wid=175&hei=110&fmt=png-alpha');
                this.setPrintable(sliItem[PRINTABLE]);
            }
            this.setIsNew(sliItem[NEW]);
            this.setOnClearance(sliItem[CLEARANCE]);
            this.addProductVariant(new ProductVariant(sliItem));
            if(this.getName(true).indexOf(this.getSize()) > -1) {
                this.suppressSize = true;
            }
        };

        /**
         * The items are grouped by their parent_id. So make sure that all items in the SLI JSON response have the parent_id property.
         * Uf the parent_id property is missing, sorting will fail and grouping will have unexpected results.
         */
        var validateData = function() {
            for(var i = 0; i < rawRefinedItems.length; i ++ ) {
                if(rawRefinedItems[i].parent_id == undefined)
                    rawRefinedItems[i].parent_id = 'UNKNOWN';
            }
        };

        /**
         * Comparator used for sorting SLI items by parent_id
         */
        var parentIdComparator =  function(o1, o2) {
            return !o1.parent_id || !o2.parent_id || o1.parent_id == o2.parent_id ? 0 : o1.parent_id > o2.parent_id ? 1 : -1;
        };

        var MAX_SIZE_TOKENS = 3;

        var convertToFloat = function(number) {
            number = number.trim().replace(/\s+/g, ' ').replace(/\s\/\s?|\s?\/\s/g, '/');
            if(/^[\d\s\/\.-]*$/.test(number)) {
                var numbers = number.split(' ');
                return numbers.length == 2 ? parseFloat(numbers[0]) + eval(numbers[1]) : eval(numbers[0]);
            } else {
                return 0.0;
            }
        };

        var ascendingSizeComparator = function(o1, o2) {
            var size1 = o1.size ? o1.size : o1.name ? o1.name : '';
            var size2 = o2.size ? o2.size : o2.name ? o2.name : '';

            var size1TokensString = size1.split(/x/i);
            var size2TokensString = size2.split(/x/i);

            var size1Tokens = [];
            var size2Tokens = [];

            for(var i = 0; i < MAX_SIZE_TOKENS; i ++) {
                if(i < size1TokensString.length) {
                    size1Tokens[i] = convertToFloat(size1TokensString[i]);
                } else {
                    size1Tokens[i] = 0.0;
                }

                if(i < size2TokensString.length) {
                    size2Tokens[i] = convertToFloat(size2TokensString[i]);
                } else {
                    size2Tokens[i] = 0.0;
                }

                if(size1Tokens[i] != size2Tokens[i]) {
                    return size1Tokens[i] > size2Tokens[i] ? 1 : -1;
                }
            }
            return 0;
        };

        var salesRankComparator = function(o1, o2) {
            var rank1 = o1.salesRank;
            var rank2 = o2.salesRank;
            return rank1 == rank2 ? 0 : rank1 > rank2 ? -1 : 1;
        };

        var descendingSizeComparator = function(o1, o2) {
            return ascendingSizeComparator(o1, o2) * -1;
        };

        var ascendingPriceComparator = function(o1, o2) {
            var price1 = o1.price ? parseFloat(o1.price) : 999999;
            var price2 = o2.price ? parseFloat(o2.price) : 999999;
            return price1 == price2 ? 0 : price1 > price2 ? 1 : -1;
        };

        var descendingPriceComparator = function(o1, o2) {
            return ascendingPriceComparator(o1, o2) * -1;
            /*var price1 = o1.price ? parseFloat(o1.price) : 999999;
            var price2 = o2.price ? parseFloat(o2.price) : 999999;
            return price1 == price2 ? 0 : price1 > price2 ? -1 : 1;*/
        };

        var popularityComparator = function(o1, o2) {
            var rank1 = o1.rank ? parseFloat(o1.rank) : 999999;
            var rank2 = o2.rank ? parseFloat(o2.rank) : 999999;
            return rank1 == rank2 ? 0 : rank1 > rank2 ? 1 : -1;
        };

        var genericAlphabeticComparator = function(o1, o2) {
            var val1 = o1.name ? o1.name : '';
            var val2 = o2.name ? o2.name : '';
            return val1 == val2 ? 0 : val1 > val2 ? 1 : -1;
        };

        /**
         * Method used to group items returned by SLI by parent_id. While performing the grouping, this method will convert SLI items
         * to envelop Products and will strip out unnecessary data to reduce the memory footprint.
         */
        var groupProductVariants = function() {
            // First make sure that the raw data is valid.
            validateData();

            // Sort raw items based on their parent_id
            rawRefinedItems.sort(parentIdComparator);

            if(merch && merch.banners) {
                $.each(merch.banners, function(key, value) {

                    if(value.match(/.*?\/?(((\w|-)+)\.(?:jpg|png|gif|jpeg))/)) {
                        refinedResult.bannerName = RegExp.$2;
                    }
                    return false;
                });
            } else {
                refinedResult.bannerName = '';
            }

            // Grouping algorithm - START
            var parentId = '';
            var product;
            var rawRefinedItemsCount = rawRefinedItems.length;
            $.each(rawRefinedItems, function(i, rawRefinedItem) {

                if (parentId != rawRefinedItem[PARENT_ID]) {
                    if (product) {
                        refinedResult.allProducts[refinedResult.count] = product;
                        refinedResult.count = refinedResult.count + 1;
                    }
                    product = new Product(rawRefinedItem);
                } else {
                    var productVariant = new ProductVariant(rawRefinedItem, product);
                }
                parentId = rawRefinedItem[PARENT_ID];

                if (i == rawRefinedItemsCount - 1 && product !== undefined) {
                    refinedResult.allProducts[refinedResult.count] = product;
                    refinedResult.count = refinedResult.count + 1;
                }
            });

            // Grouping algorithm END
            refinedResult.sortProducts(currentSort);
            refinedResult.products = refinedResult.getPaginatedProducts()[0];
        };

        /**
         * Convert the response that we got from server side SLI call to Product and ProductVariant objects
         * @param data - The data that we received back from server side SLI call
         */
        var convertInternalData = function(data) {
            refinedResult.reset();
            $.each(data.products, function(i, _product) {
                var product;
                $.each(_product.productVariants, function(j, _productVariant) {
                    if(j == 0) {
                        product = new Product(_productVariant);
                    } else {
                        var productVariant = new ProductVariant(_productVariant, product);
                    }
                });
                refinedResult.products[refinedResult.count] = product;
                refinedResult.count = refinedResult.count + 1;
            });
        };

        var convertSearchResultData = function(data) {
            refinedResult.reset();
            var bannerName = '';
            if(data.merch && data.merch.banners) {
                $.each(data.merch.banners, function(key, value) {

                    if(value.match(/.*?\/?(((\w|-)+)\.(?:jpg|png|gif|jpeg))/)) {
                        refinedResult.bannerName = RegExp.$2;
                    }
                    return false;
                });
            } else {
                refinedResult.bannerName = '';
            }
            $.each(data.results, function(i, resultItem) {
                var product = new Product(resultItem);
                refinedResult.products[refinedResult.count] = product;
                refinedResult.count = refinedResult.count + 1;
            });
            refinedResult.numOfSliPages = data.pages.total;
            refinedResult.numOfSliItems = data.result_meta.total;
        };

        /**
         *  Process the results received from SLI and group product variants based on their parent.
         */
        var processResult = function(data, searchMode) {
            if(data) {
                if(searchMode) {
                    convertSearchResultData(data);
                } else {
                    convertInternalData(data);
                }
            } else {
                groupProductVariants();
            }
        };

        /**
         * Render the refined items returned by SLI after applying all the selected filters
         */
        var renderResult = function(data, searchMode) {
            processResult(data, searchMode);
            $('.filter-sort').show();
            if(searchMode) {
                renderSearchResults();
            } else {
                renderProducts(data);
                slideIt_init();
            }
        };

        var renderProducts = function(data) {

            $('#jqs-refinements-result').html('');

            $.each(refinedResult.products, function(i, product) {
                renderProduct(product, productTemplate.clone());

            });
            if(data) {
                renderPagination(data.pages, data.thisPage, true);
                renderBanner(data);
            } else {
                renderPagination(refinedResult.pages, currentPage);
                if(refinedResult.bannerName) {
                    renderBanner({description: virtualCategoryNames[refinedResult.bannerName], imageFile: refinedResult.bannerName});
                } else {
                    renderBanner({description: ''});
                }

            }
            slideIt_init();
        };

        var renderEmptyResults = function(searchMode) {
            if(searchMode) {
                $('.searchResponse, #jqs-refinements-result').html('');
                $('.searchResponse').html('<h2>Your filters returned no result.</h2>');
            } else {
                $('#jqs-refinements-result').html('<h2>Your filters returned no result.</h2>');
            }
            $('.filter-sort').hide();
            clearSpinner();
        };

        var renderSearchResults = function() {
            $('#jqs-refinements-result').html('').append($('<ul/>'));
            $.each(refinedResult.products, function(i, product) {
                renderSearchResult(product);
            });
            closeSlideOut($('#filters'));
            elem.find('.jqs-items-count').html(refinedResult.numOfSliItems + (refinedResult.numOfSliItems > 1 ? ' results' : ' result'));
            renderSearchBanner(refinedResult.getBannerName());
            renderPagination(refinedResult.numOfSliPages, currentPage);
            resizeSearchText();
            pushDataToSLISparkJS();
        };

        var renderSearchResult = function(product) {
            var itemElem = $('<li/>').addClass('jqs-item');
            if(product.getIsNew() == 'Y') {
                itemElem.append('<div class="newRibbon jqs-ribbon">New<div class="ribbonTop"></div><div class="ribbonBottom"></div></div>');
            }
            if(product.getOnSale() == 'Clearance') {
                itemElem.append('<div class="clearanceRibbon jqs-ribbon">' + product.getSalesPercentage() + '% OFF<div class="ribbonTop"></div><div class="ribbonBottom"></div></div>');
            }
            if(product.getOnSale() == 'Sale') {
                itemElem.append('<div class="saleRibbon jqs-ribbon">' + product.getSalesPercentage() + '% OFF<div class="ribbonTop"></div><div class="ribbonBottom"></div></div>');
            }
            var tabletDesktopOnly = $('<div/>').addClass('tablet-desktop-only').append(
            	$('<a/>').attr('href', product.getProductURL()).append(
            		$('<img/>').attr('src', product.getImageURL()).attr('alt', product.getName()).addClass('prodImage').addClass(product.getProductType() == 'Designs' ? 'border-gray-1' : '')
            	)
            );
            var mobileOnly = $('<div/>').addClass('mobile-only-table').append($('<div/>').append(
                $('<a/>').attr('href', product.getProductURL()).append(
                    $('<img/>').attr('src', product.getImageURL()).attr('alt', product.getName()).addClass(product.getProductType() == 'Designs' ? 'border-gray-1' : '')
                )
            ));
            tabletDesktopOnly.append(
            	$('<h2/>').append(
            		$('<a/>').attr('href', product.getProductURL()).html(product.getName(true) + '&nbsp;').append(
            			$('<span/>').addClass('item-size').html('(' + product.getSize() + ')')
            		)
            	)
            );
            var mobileOnlyInner = $('<div/>').addClass('padding-left-xxs').append(
                $('<h2/>').append(
                    $('<a/>').attr('href', product.getProductURL()).html(product.getName(true) + '&nbsp;').append(
                        $('<span/>').addClass('item-size').html(product.getSize())
                    )
                )
            );
            tabletDesktopOnly.append($('<h3/>').html(product.getProductVariants()[0].getProductVariantId()));
            mobileOnlyInner.append($('<h3/>').html(product.getProductVariants()[0].getProductVariantId()));
            tabletDesktopOnly.append($('<h3/>').html(product.getColor()));
            mobileOnlyInner.append($('<h3/>').html(product.getColor()));
            tabletDesktopOnly.append($('<div/>').addClass('ratings').addClass('rating-' + product.getRating() + (product.getRating() == "0" ? " hide-background" : "")));
            mobileOnlyInner.append($('<div/>').addClass('ratings').addClass('rating-' + product.getRating() + (product.getRating() == "0" ? " hide-background" : "")));
            tabletDesktopOnly.append($('<div/>').addClass('price').html('From $' + product.getPrice()));
            mobileOnlyInner.append($('<div/>').addClass('price').html('From $' + product.getPrice()));
            tabletDesktopOnly.append($('<div/>').css('clear', 'both'));
            if(product.getProductType() != "Designs" && product.getPrintable() == 'Y') {
                tabletDesktopOnly.append($('<div/>').addClass('top-btn').addClass('plain-btn').on('click', function() {
                    gotoProductPage(product.getProductURL());
                }).append('<img src="/html/img/search/plain.jpg"/>'));
            }

            if(product.getProductType() == "Designs" || product.getPrintable() == 'Y') {
                tabletDesktopOnly.append($('<div/>').addClass('bottom-btn').addClass('printed-btn').on('click', function() {
                    gotoProductPage(product.getProductURL(), true);
                }).append('<img src="/html/img/search/printed.jpg"/>'));
            } else if(product.getProductType() != "Designs" && product.getPrintable() == 'N') {
                tabletDesktopOnly.append($('<div/>').addClass('bottom-btn').addClass('plain-btn').on('click', function() {
                    gotoProductPage(product.getProductURL());
                }).append('<img src="/html/img/search/plain.jpg"/>'));
            }
            itemElem.append(tabletDesktopOnly);
            itemElem.append(mobileOnly.append(mobileOnlyInner));
            $('#jqs-refinements-result').find('ul').append(itemElem);
        };

        var renderBanner = function(data) {
            elem.find('.jqs-category-title').text(data.description);
            elem.find('.jqs-category-banner').html('');
            elem.find('.jqs-category-mobile-banner').html('');
            if(data.imageFile && data.imageFile != '') {
                elem.find('.jqs-category-banner').append($('<img/>').attr('src', '/html/img/category/banners/desktop/' + data.imageFile + '.jpg'));
                elem.find('.jqs-category-mobile-banner').append($('<img/>').attr('src', '/html/img/category/banners/mobile/' + data.imageFile + '.jpg'));
                elem.find('.jqs-category-header').show();
            } else {
                elem.find('.jqs-category-header').hide();
            }
        };

        var renderSearchBanner = function(bannerName) {
            if(bannerName != '') {
                elem.find('.jqs-search-banner').css('background', "url('/html/img/search/banners/desktop/" + bannerName + "_bg.jpg') repeat");
                elem.find('.jqs-search-text').html('');
                elem.find('.jqs-search-text').append($('<img/>').attr('src', '/html/img/search/banners/desktop/' + bannerName + '_text.png'));
                elem.find('.jqs-banner-image').remove();
                elem.find('.jqs-search-banner').append($('<img/>').attr('src', '/html/img/search/banners/desktop/' + bannerName + '.png').addClass('banner-image jqs-banner-image'));
                elem.find('.jqs-search-mobile-banner').html('');
                elem.find('.jqs-search-mobile-banner').append($('<img/>').attr('src', '/html/img/search/banners/mobile/' + bannerName + '.jpg'));
                elem.find('.jqs-search-header').removeClass('hidden');
            } else {
                elem.find('.jqs-search-header').addClass('hidden');
                elem.find('.jqs-search-banner').css('background', '');
                elem.find('.jqs-search-text').html('');
                elem.find('.jqs-banner-image').remove();
                elem.find('.jqs-search-mobile-banner').html('');

            }
        };

        var renderPagination = function(numOfPages, currentPage, internalCall) {
            var paginationElem = $('.pagination').html('');
            if(numOfPages > 1) {
                paginationElem.append(
                    $('<li/>').addClass('arrow arrow-left' + (currentPage == 0 ? ' unavailable' : '')).append(
                        $('<a/>').attr( {'rel': 'prev', 'data-page': currentPage - 1, 'href': '#'} ).append(
                            $('<i/>').addClass('fa fa-chevron-left')
                        ).on('click', function() {
                            if ($(this).parent().hasClass('unavailable')) {
                                return false;
                            }

                            goToPage($(this).data('page'), internalCall);
                        })
                    )
                );

                for(var i = 0; i < numOfPages; i ++) {
                    paginationElem.append(
                        $('<li/>').addClass(i == currentPage ? 'current' : '').append(
                            $('<a/>').attr( {'rel': (i < currentPage ? 'prev' : 'next'), 'data-page': i, 'href': '#'} ).text(i + 1).on('click', function() {
                                goToPage($(this).data('page'), internalCall);
                            })));
                }

                paginationElem.append(
                    $('<li/>').addClass('arrow arrow-right' + (currentPage == (numOfPages - 1) ? ' unavailable' : '')).append(
                        $('<a/>').attr( {'rel': 'next', 'data-page': (currentPage + 1), 'href': '#'} ).append(
                            $('<i/>').addClass('fa fa-chevron-right')
                        ).on('click', function() {
                            if ($(this).parent().hasClass('unavailable')) {
                                return false;
                            }

                            goToPage($(this).data('page'), internalCall);
                        })
                    )
                );

                $().formatPagination();
                paginationElem.show();
            } else {
                paginationElem.hide();
            }
        };

        var bindPagination = function() {
            $('.pagination li a').on('click', function() {
				if ($(this).parent().hasClass('unavailable')) {
					return false;
				}

				goToPage($(this).data('page'), true);
            });
        };


        var renderProduct = function(product, productTemplate) {
            productTemplate.find('.jqs-product-url').attr('href', product.getProductURL());
            productTemplate.find('.jqs-product-name').html(product.getName(true));
            if(!product.getSuppressSize()) {
                productTemplate.find('.jqs-product-size').html('&nbsp; - ' + product.getSize());
            }
            productTemplate.find('.jqs-variant-count').html(product.getProductVariantCount() + '(' + product.getPrice() + ')'  + '(' + product.getRank() + ')');
            if(product.getProductVariantCount() > 1) {
                productTemplate.find('.jqs-product-color-link').html('See All ' + product.getProductVariantCount());
            } else {
                productTemplate.find('.jqs-product-color-link').html('');
            }

            $.each(product.getProductVariants().sort(salesRankComparator), function(i, productVariant) {
                renderProductVariant(productVariant, productTemplate, productVariantTemplate.clone());
            });
            $(productTemplate).appendTo('#jqs-refinements-result');

        };

        var renderProductVariant = function(productVariant, productElement, productVariantTemplate) {
            productVariantTemplate.find('.jqs-variant-url').attr('href', productVariant.getProductVariantURL());
            productVariantTemplate.find('.jqs-variant-image').attr('src', productVariant.getImageURL());
            productVariantTemplate.find('.jqs-color').html(productVariant.getColor());
            if (productVariant.getOnSale() != '') {
            	productVariantTemplate.find('.jqs-productSavings').removeClass('clearanceBGColor').removeClass('saleBGColor').addClass(productVariant.getOnSale() == 'Clearance' ? 'clearanceBGColor' : 'saleBGColor').html(productVariant.getSalesPercentage() + '% Off');
            }
            $(productVariantTemplate).appendTo(productElement.find('.jqs-variants'));
        };

        /**
         * Helper method used to generate the SLI appliedFacet (af=) parameter from the list of selected filters.
         * @returns {string} - The SLI appliedFacet parameter string corresponding to the selected filters
         */
        var getAppliedFacets = function(suppressParamNameFlag, suppressProdType) {
            var appliedFacets = suppressProdType ? '' : 'prodtype:products';
            if(settings.pageType == 'NEW_ARRIVALS') {
                appliedFacets += (appliedFacets.length > 0 ? ' ' : '') + 'new:Y';
            }
            appliedFacets += (!settings.afData ? '' : ((appliedFacets.length > 0 ? ' ' : '') + settings.afData));
            appliedFacets += (!settings.categoryFacets ? '' : ((appliedFacets.length > 0 ? ' ' : '') + settings.categoryFacets));
            appliedFacets += (!settings.sizeFacets ? '' : ((appliedFacets.length > 0 ? ' ' : '') + settings.sizeFacets));
            appliedFacets += (!settings.colorFacets ? '' : ((appliedFacets.length > 0 ? ' ' : '') + settings.colorFacets));
            appliedFacets += (!settings.styleFacets ? '' : ((appliedFacets.length > 0 ? ' ' : '') + settings.styleFacets));
            appliedFacets += (!settings.exactColorFacets ? '' : ((appliedFacets.length > 0 ? ' ' : '') + settings.exactColorFacets));
            appliedFacets += (!settings.finishFacets ? '' : ((appliedFacets.length > 0 ? ' ' : '') + settings.finishFacets));
            appliedFacets += (!settings.ratingsFacets ? '' : ((appliedFacets.length > 0 ? ' ' : '') + settings.ratingsFacets));
            appliedFacets += (!settings.collectionFacets ? '' : ((appliedFacets.length > 0 ? ' ' : '') + settings.collectionFacets));
            appliedFacets += (!settings.weightFacets ? '' : ((appliedFacets.length > 0 ? ' ' : '') + settings.weightFacets));
            appliedFacets += (!settings.sealingFacets ? '' : ((appliedFacets.length > 0 ? ' ' : '') + settings.sealingFacets));
            appliedFacets += (!settings.priceFacets ? '' : ((appliedFacets.length > 0 ? ' ' : '') + settings.priceFacets));
            appliedFacets += (!settings.industryFacets ? '' : ((appliedFacets.length > 0 ? ' ' : '') + settings.industryFacets));
            appliedFacets += (!settings.themeFacets ? '' : ((appliedFacets.length > 0 ? ' ' : '') + settings.themeFacets));
            appliedFacets += (!settings.productTypeFacets ? '' : ((appliedFacets.length > 0 ? ' ' : '') + settings.productTypeFacets));
            return (appliedFacets.length > 0 ? (!suppressParamNameFlag ? 'af=' : '') : '') + appliedFacets;
        };

        /**
         * Method used to fetch the result from the search engine (currently SLI).
         * @param callback  - The callback function that will be called on success.
         * @param startOffSet - The starting offset. If this is not present, start from the first position.
         */
        var fetchResult = function(callback, startOffSet) {
            pushState();
            var sortBy = '';
            if(settings.requestType == 'SEARCH') {
                if(currentSort != '') {
                    if(currentSort == 'popular') {
                        sortBy = '';
                    } else {
                        sortBy = currentSort;
                    }
                }
            } else {
                if(currentSort == '') {
                    sortBy = 'sales';
                } else {
                    sortBy = currentSort;
                }
            }

            $.ajax({
                url: settings.serviceEndpoint,
                data: '&' + getAppliedFacets(false, settings.requestType == 'SEARCH') + (settings.requestType == 'CATEGORY' ? '&w=*' : '&w=' + (settings.keyword == '&#x2a;' ? '*' : settings.keyword.replace('#', '%23'))) + '&cnt=' + settings.fetchSize + '&srt=' + (!startOffSet ? 0 : startOffSet * settings.fetchSize) + '&isort=' + sortBy,
                dataType: 'jsonp',
                timeout: settings.ajaxTimeOut
            }).done(function(data) {
                callback(data);
                if (settings.requestType == 'SEARCH') {
                	clearSpinner();
                }
            }).fail(function(jqXHR, textStatus){
                sliAjaxCtrl.fail("An error occurred while getting result from SLI." + (textStatus == 'timeout' ? ' Failed due to timeout.' : ''));
            });
        };

        /*var getDefaultSortForSearchresults = function(keyword) {
            var defaultSort = 'sales';
            if(typeof keyword !== 'undefined') {
                $.each(bestSellerOverrides, function(index, value) {
                    if(keyword.indexOf(value) != -1) {
                        defaultSort = '';
                        return false;
                    }
                });
            }
            return defaultSort;
        };*/

        var fetchResultInternal = function(callback, serverSideCategoryIds) {

//            console.log(getAppliedFacets());   //TODO - debugging statement
            pushState();
            $.ajax({
                url: settings.internalEndPoint,
                data: 'page=' + currentPage + serverSideCategoryIds + (currentSort !== undefined && currentSort != '' ? '&sort=' + currentSort : ''),
                dataType: 'json',
                timeout: settings.internalAjaxTimeOut
            }).done(function(data) {
                callback(data);
            }).fail(function(jqXHR, textStatus){
                callback({success: false});
            });
        };


		var getMostRelevantTitle = function() {
			var most_relevant = [];

			$('#jqs-refinements-filter .filter-list').find('select[class*="jqs-filter-"]').each(function() {
				for (i = 0; i < settings[$(this).attr('name') + 'Filters'].length; i++) {
					var content = $(this).find('option[value="' + settings[$(this).attr('name') + 'Filters'][i] + '"]').html();
					if (content) {
						most_relevant.push(content.replace('* ', '').replace('&amp;', '&'));
					}
				}
			});

			return (most_relevant.length > 0 ? 'Products in ' + most_relevant.join(', ') : 'All Products') + ' at Envelopes.com';
		};

        var pushState = function() {
            history.replaceState({af : getAppliedFacets(true, true), page: currentPage, sort: currentSort}, getMostRelevantTitle(), (settings.requestType == 'CATEGORY' ? '/envelopes/control/category?':'/search?') + getAppliedFacets(false, true) + (settings.pageType == 'NEW_ARRIVALS' ? '&newArrivals=Y' : '') + (currentPage !== undefined && currentPage > 0 ? '&page=' + currentPage : '') + (currentSort !== undefined && currentSort != '' ? '&sort=' + currentSort : '') + (settings.requestType == 'SEARCH' ? '&w=' + (settings.keyword == '&#42;' ? '*' : settings.keyword) : '') + (settings.requestType == 'SEARCH' && settings.fetchSize > 50 ? '&cnt=' + settings.fetchSize : '') + (settings.c != '' ? '&c=' + settings.c : ''));
        };

        var pushDataToSLISparkJS = function() {
            if (settings.requestType == 'SEARCH') {
                spark.addSearch(settings.keyword, refinedResult.numOfSliItems, currentPage + 1, getAppliedFacets(true, true), currentSort);
                $.each(refinedResult.products, function(i, product) {
                    spark.addPageSku(product.getProductVariants()[0].getProductVariantId());
                });
                spark.writeTrackCode();
            }
        };

        var checkFilterContainerLength = function() {
			// We divide this by 2 since there is a mobile version which effectively doubles the amount of children.
			if ($(".applied-filter-list").children().length / 2 <= 2) {
				$(".applied-filters-container").css("visibility", "hidden");
			}
			else {
				$(".applied-filters-container").css("visibility", "visible");
			}
		};


		var checkFilterList = function() {
			var displayed_width = 0;

			$(".filter-list").children("div").each(function() {
				displayed_width += $(this).css("display") != "none" ? getFullWidth($(this)) : 0;
			});

			if (getFullWidth($(".filter-list")) > displayed_width && $(window).width() > 1000) {
				$(".more-filters-container > div").css("display", "none");
			}
			else {
				$(".more-filters-container > div").css("display", "block");
			}
		};

		$(document).ready(function() {
			checkFilterList();
			checkFilterContainerLength();

			$(window).on("resize", function() {
				waitForFinalEvent(function(){
					checkFilterList();
				}, 200, "filterList_Id");
			});


			$(".more-filters-container > .button-regular").on("click", function() {
				if ($(".filter-list").css("overflow") == "hidden") {
					$(".filter-list").css({
						"overflow":"auto",
						"height":"auto"
					}).addClass("show");

					$(this).html("Less Filters");
				}
				else {
					$(".filter-list").css({
						"overflow":"hidden",
						"height":"35px"
					}).removeClass("show");

					$(this).html("More Filters");
				}
			});
		});

        return this;
    };
})(jQuery);

var virtualCategoryNames = {paper_cardstock_category : 'Paper & Cardstock'};
var bestSellerOverrides = ['square envelopes', 'wedding envelopes', 'lined', 'cards', 'liner', 'rfid'];
