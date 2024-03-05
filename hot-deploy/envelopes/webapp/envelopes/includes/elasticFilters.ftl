<div data-slideout_name="filters" class="slideout_trigger button button-non-cta no-margin mobile-tablet-only-inline-block round-btn navyblue-bckgrd">Filters <span bns-numberoffilters></span> <i class="fa fa-caret-right"></i></div>
<#if !hideFilters?exists || hideFilters?c == "false">
<div bns-filtercontainer class="jqs-fixed" data-fixed-options="border-bottom-e0e0e0" style="left: 50px; background-color: #fff; width: initial; border: initial; padding-left: initial; padding-right: initial;">
    <div class="jqs-fixed-content">      
        <div id="filters" class="slideout_result">
            <div class="slideout_header mobile-tablet-only">
                <div data-go_back="">&lt; Back</div>
                <h2>Filters</h2>
            </div>
            <div class="slideout_body">
                <div class="row filter-select-container padding-top-xxs">
                    <div class="filter-head-container">
                        <span>Narrow your results:</span>
                    </div>
                    <div id="jqs-refinements-filter">
                        <div class="filter-list">
                            <#assign filterableMap = Static["com.bigname.search.elasticsearch.SearchField"].getFilterableMap(globalContext.webSiteId) />
                            <#list filterableMap.keySet() as filter>
                                <#assign filterId = filterableMap.get(filter) />
                                <#if aggregations.get(filterId)?exists && aggregations.get(filterId).isEmpty()?c == "false">
                                    <div class="envelope-select">
                                        <select bns-searchresults="change" data-paramname="af" name="${filterId}">
                                            <option value="">${Static["com.bigname.search.elasticsearch.SearchField"].getFilterDescription(filterId, globalContext.webSiteId)}</option>
                                            <#list aggregations[filterId].entrySet() as aggFilter>
                                            <option value="${aggFilter.getKey().facetId}"
                                                <#if filters.get(filterId)?has_content>
                                                    <#assign foundFilter = false />
                                                    <#list filters.get(filterId) as filterMap>
                                                        <#if filterMap.get(aggFilter.getKey().facetId)?has_content>
                                                            <#assign foundFilter = true />
                                                        </#if>
                                                    </#list>
                                                    <#if foundFilter> disabled>* <#else>></#if>
                                                <#else>
                                                    >
                                                </#if>
                                                ${aggFilter.getKey().facetName?capitalize} (${aggFilter.getValue()?if_exists})</option>
                                        </#list>
                                        </select>
                                    </div>
                                </#if>
                            </#list>
                        </div>
                    </div>
                    <div class="more-filters-container desktop-only">
                        <div class="button-regular button-non-cta round-btn" style="display: none;">More Filters</div>
                    </div>
                </div>
                <div class="row margin-top-xxs filter-list-container desktop-only" style="background-color: #fff;">
                    <span class="applied-filters-container<#if filters.keySet()?size == 0> hidden</#if>">
                        <div class="filter-list-head-container desktop-only-cell">
                            <span>Filters:</span>
                        </div>
                        <div class="filter-list-body">
                            <ul class="no-margin inline-list applied-filter-list">
                                <li class="no-margin margin-bottom-xxs margin-right-xxs mobile-tablet-only-inline-block">
                                    <span>Filters:</span>
                                </li>
                                <#list filters.keySet() as filter>
                                    <#list filters.get(filter) as filterMap>
                                        <#list filterMap.keySet() as key>
                                            <li bns-removefilter="${filter}:${key}" class="no-margin margin-bottom-xxs margin-right-xxs filter">
                                                <div class="navyblue">${Static["com.bigname.search.elasticsearch.SearchField"].getFilterDescription(filter, globalContext.webSiteId)}: ${filterMap.get(key)?capitalize} <i class="fa fa-times margin-left-xxs"></i></div>
                                            </li>
                                        </#list>
                                    </#list>
                                </#list>
                                <li bns-removefilter="all" class="no-margin margin-left-xxs margin-bottom-xxs margin-right-xs filter-clear">
                                    <div class="navyblue">
                                        Clear Filters
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </span>
                </div>
            </div>
        </div>
        <div class="row margin-top-xxs filter-list-container mobile-tablet-only" style="background-color: #fff;">
            <span bns-mobilefilters class="applied-filters-container<#if filters.keySet()?size == 0> hidden</#if>">
                <div class="filter-list-body">
                    <ul class="no-margin inline-list applied-filter-list">
                        <li class="no-margin margin-bottom-xxs margin-right-xxs">
                            <span>Filters:</span>
                        </li>
                        <#list filters.keySet() as filter>
                            <#list filters.get(filter) as filterMap>
                                <#list filterMap.keySet() as key>
                                    <li bns-removefilter="${filter}:${key}" class="no-margin margin-bottom-xxs margin-right-xxs filter">
                                        <div>${Static["com.bigname.search.elasticsearch.SearchField"].getFilterDescription(filter, globalContext.webSiteId)}: ${filterMap.get(key)?capitalize} <i class="fa fa-times margin-left-xxs"></i></div>
                                    </li>
                                </#list>
                            </#list>
                        </#list>
                        <li bns-removefilter="all" class="no-margin margin-left-xxs margin-bottom-xxs margin-right-xs filter-clear">
                            <div>
                                Clear Filters
                            </div>
                        </li>
                    </ul>
                </div>
            </span>
            <div bns-showallfilters>Show More Filters</div>
        </div>
    </div>
</div>
<div bns-filtercontainer class="result-options">
    <div class="row">
        <div class="filter-sort">
            <div class="envelope-select">
                <select bns-searchresults="change" data-paramname="sort" name="sortBy">
                    <option value="">Most Popular</option>
                    <option value="price+asc" <#if requestParameters.sort?has_content && (requestParameters.sort?replace(" ", "+") == "price&#x2b;asc" || requestParameters.sort?replace(" ", "+") == "price+asc")>selected</#if>>Price: Low to High</option>
                    <option value="price+desc" <#if requestParameters.sort?has_content && (requestParameters.sort?replace(" ", "+") == "price&#x2b;desc" || requestParameters.sort?replace(" ", "+") == "price+desc")>selected</#if>>Price: High to Low</option>
                    <option value="avgrating+desc" <#if requestParameters.sort?has_content && (requestParameters.sort?replace(" ", "+") == "avgrating&#x2b;desc" || requestParameters.sort?replace(" ", "+") == "avgrating+desc")>selected</#if>>Most Reviewed</option>
                    <option value="createdstamp+desc" <#if requestParameters.sort?has_content && (requestParameters.sort?replace(" ", "+") == "createdstamp&#x2b;desc" || requestParameters.sort?replace(" ", "+") == "createdstamp+desc")>selected</#if>>New Arrivals</option>
                    <option value="measurement+asc" <#if requestParameters.sort?has_content && (requestParameters.sort?replace(" ", "+") == "measurement&#x2b;asc" || requestParameters.sort?replace(" ", "+") == "measurement+asc")>selected</#if>>Size: Small to Large</option>
                    <option value="measurement+desc" <#if requestParameters.sort?has_content && (requestParameters.sort?replace(" ", "+") == "measurement&#x2b;desc" || requestParameters.sort?replace(" ", "+") == "measurement+desc")>selected</#if>>Size: Large to Small</option>
                </select>
            </div>
        </div>
        <#if searchPageType?has_content && searchPageType != "CATEGORY_PAGE">
            <div class="limit-results">
                <div class="envelope-select">
                    <select bns-searchresults="change" data-paramname="cnt" class="jqs-page-limit" name="limitResults">
                        <option value="50"<#if (requestParameters.cnt?has_content && requestParameters.cnt == "50") || !requestParameters.cnt?has_content> selected</#if>>Show - 50</option>
                        <option value="100"<#if requestParameters.cnt?has_content && requestParameters.cnt == "100"> selected</#if>>Show - 100</option>
                        <option value="150"<#if requestParameters.cnt?has_content && requestParameters.cnt == "150"> selected</#if>>Show - 150</option>
                    </select>
                </div>
            </div>
        </#if>
    </div>
</div>
</#if>

<script type="text/javascript">
    if (typeof initSlideout == 'function') {
        initSlideout();
    }

    if(getFullHeight($('[bns-mobilefilters]')) > 90) {
        $('[bns-mobilefilters]').css({
            'height': '90px',
            'overflow': 'hidden'
        });
    } else if (getFullHeight($('[bns-mobilefilters]')) < 90) {
        $('[bns-mobilefilters]').css({
            'height': '100%',
            'overflow': 'initial'
        });
        $('[bns-showallfilters]').css('display', 'none');
    } else {
        $('[bns-mobilefilters]').css({
            'height': '90px',
            'overflow': 'hidden'
        });
        $('[bns-showallfilters]').css('display', 'none');
    }
    
    $('[bns-showallfilters]').on('click', function() {
        if(getFullHeight($('[bns-mobilefilters]')) == 90) {
            $('[bns-mobilefilters]').css({
                'height': '100%',
                'overflow': 'initial'
            });
            $('[bns-showallfilters]').html('Show Less Filters');
        } else {
            $('[bns-mobilefilters]').css({
                'height': '90px',
                'overflow': 'hidden'
            });
            $('[bns-showallfilters]').html('Show More Filters')
        }
    });

    var appliedFilters = $('.mobile-tablet-only .applied-filter-list').children().length - 2;
    $('[bns-numberoffilters]').html('( ' + appliedFilters + ' )');

</script>

<script type="text/javascript">
    if (typeof initSlideout == 'function') {
        initSlideout();
    }

    if(getFullHeight($('[bns-mobilefilters]')) > 90) {
        $('[bns-mobilefilters]').css({
            'height': '90px',
            'overflow': 'hidden'
        });
    } else if (getFullHeight($('[bns-mobilefilters]')) < 90) {
        $('[bns-mobilefilters]').css({
            'height': '100%',
            'overflow': 'initial'
        });
        $('[bns-showallfilters]').css('display', 'none');
    } else {
        $('[bns-mobilefilters]').css({
            'height': '90px',
            'overflow': 'hidden'
        });
        $('[bns-showallfilters]').css('display', 'none');
    }
    
    $('[bns-showallfilters]').on('click', function() {
        if(getFullHeight($('[bns-mobilefilters]')) == 90) {
            $('[bns-mobilefilters]').css({
                'height': '100%',
                'overflow': 'initial'
            });
            $('[bns-showallfilters]').html('Show Less Filters');
        } else {
            $('[bns-mobilefilters]').css({
                'height': '90px',
                'overflow': 'hidden'
            });
            $('[bns-showallfilters]').html('Show More Filters')
        }
    });

    var appliedFilters = $('.mobile-tablet-only .applied-filter-list').children().length - 2;
    $('[bns-numberoffilters]').html('( ' + appliedFilters + ' )');

</script>