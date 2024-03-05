function ElasticSearchResults() {
    this.getResults = function(paramName, paramValue, removeParams, searchState) {
        waitForFinalEvent(function() {
            elasticData = {};

            if(searchState) {
                if ((searchState).replace(' ', '') != '') {
                    var splitUrlParams = (searchState).split('&');
                    for (var i = 0; i < splitUrlParams.length; i++) {
                        var splitUrlParam = splitUrlParams[i].split('=');
                        elasticData[splitUrlParam[0]] = splitUrlParam[1];
                    }
                }
            } else {
                if ($('[bns-filterurl]').attr('bns-filterurl').replace(' ', '') != '') {
                    var splitUrlParams = $('[bns-filterurl]').attr('bns-filterurl').split('&');
                    for (var i = 0; i < splitUrlParams.length; i++) {
                        var splitUrlParam = splitUrlParams[i].split('=');
                        elasticData[splitUrlParam[0]] = splitUrlParam[1];
                    }
                }

                for (var i = 0; i < removeParams.length; i++) {
                    delete elasticData[removeParams[i]];
                }

                if (typeof elasticData[paramName] != 'undefined' && paramName == 'af') {
                    elasticData[paramName] += ' ' + paramValue;
                } else {
                    elasticData[paramName] = paramValue;
                } 
            }

            $.ajax({
                url: '/envelopes/control/' + (typeof searchPageType !== 'undefined' && searchPageType == 'CATEGORY_PAGE' ? 'categoryResults' : 'searchResults'),
                data: elasticData
            }).done(function(responseData) {
                var newUrlParams = '';
                for (var key in elasticData) {
                    if (key != 'page' || (key == 'page' && elasticData[key] != '0')) {
                        newUrlParams += (newUrlParams == '' ? '?' : '&') + key + '=' + elasticData[key];
                    }
                }

                if(!searchState) {
                    history.pushState({'request': newUrlParams}, '', window.location.pathname + newUrlParams)
                }
                resultSetBackToTop();
                $('.jqs-searchResultsContainer').html(responseData);
                $('[bns-elasticsearchcontainer]').spinner(false);

                dataLayer.push({
                    'event': (typeof searchPageType !== 'undefined' && searchPageType == 'CATEGORY_PAGE' ? 'category' : 'search') + 'update'
                });
            });
        }, 0, 'searchResultsSubmission');
    };

    this.doSearchResultsEvent = function(element) {
        var self = this;
        var paramName = element.attr('data-paramname');
        var paramValue = '';
        var removeParams = [];
        
        $('[bns-elasticsearchcontainer]').spinner(true, false, 50, null, null, '', null, null);

        if (paramName == 'page') {
            paramValue = element.attr('data-page');
        } else if (paramName == 'af') {
            paramValue = $(element).attr('name') + ':' + $(element).val();
            removeParams.push('page');
        } else {
            paramValue = element.val();
            removeParams.push('page');
        }

        if (typeof paramName != 'undefined' && typeof paramValue != 'undefined') {
            self.getResults(paramName, paramValue, removeParams);
        }
    }

    this.removeFilter = function(filterToRemove) {
        var self = this;
        var re = new RegExp('(?: |)' + filterToRemove);

        $('[bns-filterurl]').attr('bns-filterurl', (filterToRemove == 'all' ? '' : $('[bns-filterurl]').attr('bns-filterurl').replace(re, '')));
        self.getResults('af', '', ['page', 'category_id']);
    };

    this.initSearch = function() {
        var self = this;

        splitURL = (document.location.href).split('?');
        splitURLFilters = '';
        if (splitURL != null && splitURL[1] != null && splitURL[1].match(/af\=(.*?)(?:\&|$)/) != null) {
            splitURLFilters = splitURL[1].match(/af\=(.*?)(?:\&|$)/)[1].split('%20');
        }

        $('[bns-searchresults="click"]').off('click.triggerSearchResults').on('click.triggerSearchResults', function() {
            self.doSearchResultsEvent($(this));
        });

        $('[bns-searchresults="change"]').off('change.triggerSearchResults').on('change.triggerSearchResults', function() {
            self.doSearchResultsEvent($(this));
        });
    }

    function resultSetBackToTop() {
        $(window).scrollTop(parseInt($('.jqs-search').offset().top) - 20);
        return false;
    };
}

elasticSearchResults = new ElasticSearchResults();

history.replaceState({'request': $('[bns-filterurl]').attr('bns-filterurl')}, document.title, window.location.href);
