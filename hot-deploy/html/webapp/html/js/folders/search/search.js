function FoldersSearchResults() {
    //this.getResults = function(filters, page, sort) {
    this.getResults = function(paramName, paramValue, removeParams) {
        waitForFinalEvent(function() {
            var splitUrl = (document.location.href).split('?');
            if (typeof splitUrl[1] == 'undefined') {
                splitUrl[1] = $('[bns-filterurl]').attr('bns-filterurl').replace(/^\&/, '');
            }

            var data = {};
            removeParams.push(paramName);
            
            if (typeof splitUrl[1] != 'undefined') {
                for (var x = 0; x < removeParams.length; x++) {
                    var re = new RegExp('(?:&|)' + removeParams[x] + '=.*?((?:\&|$))');
                    splitUrl[1] = splitUrl[1].replace(re, '$1');
                }
                
                var splitParams = splitUrl[1].split('&');
                for (var x = 0; x < splitParams.length; x++) {
                    splitValue = splitParams[x].split('=');
                    if (splitValue.length == 2) {
                        data[splitValue[0]] = splitValue[1];
                    }
                }

            }

            data[paramName] = paramValue;
            $.ajax({
                url: '/folders/control/' + (typeof searchPageType !== 'undefined' && searchPageType == 'CATEGORY_PAGE' ? 'categoryResults' : 'searchResults'),
                data: data
            }).done(function(data) {
                history.pushState('data', '', (splitUrl[0] + '?' + (typeof splitUrl[1] != 'undefined' && splitUrl[1].length > 0 ? splitUrl[1] + '&' : '') + paramName + '=' + paramValue).replace('?&', '?'));
                resultSetBackToTop();
                $('.jqs-searchResultsContainer').html(data);
            });
        }, 0, 'searchResultsSubmission');
    };

    this.doSearchResultsEvent = function(element) {
        var self = this;
        var paramName = element.attr('data-paramname');
        var paramValue = '';
        var removeParams = [];
        
        if (paramName == 'page') {
            paramValue = element.attr('data-page');
        } else if (paramName == 'af') {
            closeSlideOut($('#filters'));

            var evalFilterUrl = $('[bns-filterurl]').attr('bns-filterurl').match(/(?:\?|&)af=(.*?)(?:&|$)/);
            if (evalFilterUrl != null) {
                paramValue = evalFilterUrl[1];
            }
            
            $('[bns-searchresults][data-paramname="af"]').each(function() {
                paramValue = paramValue.replace($(this).attr('name') + ':' + $(this).val(), '');
                paramValue = paramValue.replace(/\s+/g, ' ').trim();
                if ($(this).is(':checked')) {
                    paramValue += (paramValue == '' ? '' : ' ') + $(this).attr('name') + ':' + $(this).val();
                }
            });

            removeParams.push('page');
        } else if (paramName == 'sort') {
            paramValue = element.val();
            removeParams.push('page');
        }

        if (typeof paramName != 'undefined' && typeof paramValue != 'undefined') {
            self.getResults(paramName, paramValue, removeParams);
        }
    }

    this.initSearch = function() {
        var self = this;

        $('[bns-searchresults="click"]').off('click.triggerSearchResults').on('click.triggerSearchResults', function() {
            self.doSearchResultsEvent($(this));
        });

        $('[bns-searchresults="change"]').off('change.triggerSearchResults').on('change.triggerSearchResults', function() {
            self.doSearchResultsEvent($(this));
        });

        //clear all filters
        $('.jqs-clearFilters').on('click.clearFilters', function() {
            $('input[id*="filter-"][type="checkbox"][bns-searchresults][data-paramname="af"]').prop('checked', false);
            $($('[bns-searchresults][data-paramname="af"]')[0]).trigger('change.triggerSearchResults');
        });

        twoLineFontResize($('[bns-hitname]'), 56, 12);
        twoLineFontResize($('[bns-hitsku]'), 17, 12);

        // $('[bns-hitname]').each(function(i){
        //     var counter = 0;
        //     var maxHeight = 0;

        //     $(this).hasClass('searchResultsSku') ? maxHeight = 17 : maxHeight = 56;

        //     while (getFullHeight($(this)) > maxHeight && parseInt($(this).css('font-size')) > 12) {
        //         counter++;
        //         $(this).css('font-size', (parseInt($(this).css('font-size')) - 1) + 'px');
        //         if(counter > 100) {
        //             break;
        //         }
        //     }
        //     if (getFullHeight($(this)) > maxHeight && parseInt($(this).css('font-size')) == 12) {
        //         $(this).css({
        //             'overflow': 'hidden',
        //             'text-overflow': 'ellipsis',
        //             'display': '-webkit-box',
        //             '-webkit-box-orient': 'vertical',
        //             '-webkit-line-clamp': '2',
        //             'height': '40px'
        //         });
        //     }
        // });


        // $('[bns-searchresultscolor]').each(function(i){
        //     var counter = 0;
        //     while (getFullHeight($(this)) > 40 && parseInt($(this).css('font-size')) > 12) {
        //         counter++;
        //         $(this).css('font-size', (parseInt($(this).css('font-size')) - 1) + 'px');
        //         if(counter > 100) {
        //             break;
        //         }
        //     }
        //     if (getFullHeight($(this)) > 40 && parseInt($(this).css('font-size')) == 12) {
        //         $(this).css({
        //             'overflow': 'hidden',
        //             'text-overflow': 'ellipsis',
        //             'display': '-webkit-box',
        //             '-webkit-box-orient': 'vertical',
        //             '-webkit-line-clamp': '2',
        //             'height': '40px'
        //         });
        //     }
        // });
    };

    function resultSetBackToTop() {
        $(window).scrollTop(parseInt($('.jqs-search').offset().top) - 20);
        return false;
    }
    
    $('[bns-backToTop]').on('click', function() {
		resultSetBackToTop();
	});
}

searchResults = new FoldersSearchResults();