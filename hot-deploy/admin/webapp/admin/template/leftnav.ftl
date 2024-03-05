<nav class="site-navbar navbar navbar-default navbar-fixed-top navbar-mega" role="navigation">
    <div class="navbar-header">
        <button type="button" class="navbar-toggler hamburger hamburger-close navbar-toggler-left hided"
                data-toggle="menubar">
            <span class="sr-only">Toggle navigation</span>
            <span class="hamburger-bar"></span>
        </button>
        <div class="navbar-brand navbar-brand-center site-gridmenu-toggle" data-toggle="gridmenu">
            <img class="jqs-logoImage" src="/html/img/admin/logoDesktop.png" title="BigName OS">
        </div>
        <button type="button" class="navbar-toggler collapsed" data-target="#site-navbar-search"
                data-toggle="collapse">
            <span class="sr-only">Toggle Search</span>
            <i class="icon wb-search" aria-hidden="true"></i>
        </button>
    </div>
    <div class="navbar-container container-fluid">
        <!-- Navbar Collapse -->
        <style>
        	.test:after {
        		clear: both;
        		content: " ";
        		display: table;
        	}
        </style>
        <div class="collapse navbar-collapse navbar-collapse-toolbar test" id="site-navbar-collapse">
            <!-- Navbar Toolbar -->
            <#if security.hasPermission("ORDERMGR_ADMIN", session)>
            <ul class="nav navbar-toolbar">
                <li class="nav-item hidden-float" id="toggleMenubar">
                    <a class="nav-link" data-toggle="menubar" href="#" role="button">
                        <i class="icon hamburger hamburger-arrow-left">
                            <span class="sr-only">Toggle menubar</span>
                            <span class="hamburger-bar"></span>
                        </i>
                    </a>
                </li>
                <#--
                <li class="nav-item hidden-sm-down" id="toggleFullscreen">
                    <a class="nav-link icon icon-fullscreen" data-toggle="fullscreen" href="#" role="button">
                        <span class="sr-only">Toggle fullscreen</span>
                    </a>
                </li>
               	-->
                <li class="nav-item hidden-float">
                    <a class="nav-link icon wb-search jqs-wbSearch" data-toggle="collapse" href="#" data-target="#site-navbar-search"
                       role="button">
                        <span class="sr-only">Toggle Search</span>
                    </a>
                </li>
            </ul>
            </#if>
            <!-- End Navbar Toolbar -->
            <!-- Navbar Toolbar Right -->
            <ul class="nav navbar-toolbar navbar-right navbar-toolbar-right">
                <li class="nav-item">
                    <a class="nav-link navbar-avatar" href="<@ofbizUrl>/logout</@ofbizUrl>" aria-expanded="false" data-animation="scale-up" role="button">
                        <span class="avatar avatar-online">
                            <img src="<#if session.getAttribute("externalUserLogin")?exists && session.getAttribute("externalUserLogin").photo?has_content>${session.getAttribute("externalUserLogin").photo}<#else><@ofbizContentUrl>/html/img/icon/lux_icon.png</@ofbizContentUrl></#if>" alt=""><i></i>
                            ${(session.getAttribute("userLogin").userLoginId)?if_exists} (${(session.getAttribute("userLogin").partyId)?if_exists})
                        </span>
                    </a>
                </li>
            </ul>
            <!-- End Navbar Toolbar Right -->
        </div>
        <!-- End Navbar Collapse -->
        <!-- Site Navbar Seach -->
        <div class="collapse navbar-search-overlap" id="site-navbar-search">
            <form id="global-search" name="global-search" role="search">
                <div class="form-group">
                    <div class="input-search">
                        <i class="input-search-icon wb-search" aria-hidden="true"></i>
                        <input type="text" class="form-control" name="site-search" id="global-search-input" placeholder="Search...">
                        <button type="button" class="input-search-close icon wb-close" data-target="#site-navbar-search" data-toggle="collapse" aria-label="Close"></button>
                    </div>
                </div>
            </form>
        </div>
        <!-- End Site Navbar Seach -->
    </div>
</nav>
<div class="site-menubar">
    <div class="site-menubar-body">
        <div>
            <div>
                <ul class="site-menu" data-plugin="menu">
                    <li class="site-menu-category">General</li>
                    <#if security.hasPermission("ORDERMGR_ADMIN", session)>
                    <li class="site-menu-item <#if !screenGroup?exists>active</#if>">
                        <a href="<@ofbizUrl>/main</@ofbizUrl>">
                            <i class="site-menu-icon wb-pie-chart" aria-hidden="true"></i>
                            <span class="site-menu-title">Dashboard</span>
                        </a>
                    </li>
                    <li class="site-menu-item has-sub <#if screenGroup?has_content && screenGroup == "orders">open active</#if>">
                        <a href="javascript:void(0)">
                            <i class="site-menu-icon wb-shopping-cart" aria-hidden="true"></i>
                            <span class="site-menu-title">Orders</span>
                            <span class="site-menu-arrow"></span>
                        </a>
                        <ul class="site-menu-sub">
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/orderList?webSiteId=ae,envelopes&start=${nowTimestamp?string("yyyy-MM-dd")}&end=${nowTimestamp?string("yyyy-MM-dd")}</@ofbizUrl>">
                                    <span class="site-menu-title">Envelopes.com Orders</span>
                                </a>
                            </li>
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/orderList?webSiteId=folders&start=${nowTimestamp?string("yyyy-MM-dd")}&end=${nowTimestamp?string("yyyy-MM-dd")}</@ofbizUrl>">
                                    <span class="site-menu-title">Folders.com Orders</span>
                                </a>
                            </li>
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/cimpressOrders</@ofbizUrl>">
                                    <span class="site-menu-title">Cimpress Orders</span>
                                </a>
                            </li>
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/channelSalesReport</@ofbizUrl>">
                                    <span class="site-menu-title">Channel Report</span>
                                </a>
                            </li>
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/createOrder</@ofbizUrl>">
                                    <span class="site-menu-title">Create Order</span>
                                </a>
                            </li>
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/sampleOrder</@ofbizUrl>">
                                    <span class="site-menu-title">Folders Sample</span>
                                </a>
                            </li>
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/reAuth</@ofbizUrl>">
                                    <span class="site-menu-title">ReAuths</span>
                                </a>
                            </li>
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/persistentCartAssist</@ofbizUrl>">
                                    <span class="site-menu-title">Cart Assist</span>
                                </a>
                            </li>
                            <li class="site-menu-item has-sub">
                                <a href="javascript:void(0)">
                                    <span class="site-menu-title">Work Orders</span>
                                    <span class="site-menu-arrow"></span>
                                </a>
                                <ul class="site-menu-sub">
                                    <li class="site-menu-item">
                                        <a class="animsition-link" href="<@ofbizUrl>/workOrder</@ofbizUrl>">
                                            <span class="site-menu-title">Manage Work Order</span>
                                        </a>
                                    </li>
                                    <#if security.hasPermission("WAREHOUSEEMPLOYEEADMIN_ADMIN", session)>
                                        <li class="site-menu-item">
                                            <a class="animsition-link" href="<@ofbizUrl>/workOrderAdmin</@ofbizUrl>">
                                                <span class="site-menu-title">Work Order Admin</span>
                                            </a>
                                        </li>
                                    </#if>
                                </ul>
                            </li>
                        </ul>
                    </li>
                    <li class="site-menu-item has-sub <#if screenGroup?has_content && (screenGroup == "quotes" || screenGroup == "quoteSettings")>open active</#if>">
                        <a href="javascript:void(0)">
                            <i class="site-menu-icon fa-calculator" aria-hidden="true"></i>
                            <span class="site-menu-title">Quotes</span>
                            <span class="site-menu-arrow"></span>
                        </a>
                        <ul class="site-menu-sub">
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/envelopesQuoteList</@ofbizUrl>?webSiteId=envelopes">
                                    <span class="site-menu-title">Envelopes Quotes</span>
                                </a>
                            </li>
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/foldersQuoteList</@ofbizUrl>?webSiteId=folders">
                                    <span class="site-menu-title">Folders Quotes</span>
                                </a>
                            </li>
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/quoteCalculator</@ofbizUrl>">
                                    <span class="site-menu-title">Quote Calculator</span>
                                </a>
                            </li>
                            <li class="site-menu-item has-sub <#if screenGroup?has_content && screenGroup == "quoteSettings">open active</#if>">
                                <a href="javascript:void(0)">
                                    <span class="site-menu-title">Settings</span>
                                    <span class="site-menu-arrow"></span>
                                </a>
                                <ul class="site-menu-sub" style="padding-left: 20px;">
                                    <li class="site-menu-item <#if screenName?has_content && screenName == "styles">active</#if>">
                                        <a class="animsition-link" href="<@ofbizUrl>/qcStyles</@ofbizUrl>">
                                            <span class="site-menu-title">Styles</span>
                                        </a>
                                    </li>
                                    <li class="site-menu-item <#if screenName?has_content && screenName == "styleGroups">active</#if>">
                                        <a class="animsition-link" href="<@ofbizUrl>/qcStyleGroups</@ofbizUrl>">
                                            <span class="site-menu-title">Style Groups</span>
                                        </a>
                                    </li>
                                    <li class="site-menu-item  <#if screenName?has_content && screenName == "stocks">active</#if>">
                                        <a class="animsition-link" href="<@ofbizUrl>/qcStocks</@ofbizUrl>">
                                            <span class="site-menu-title">Stocks</span>
                                        </a>
                                    </li>
                                    <li class="site-menu-item  <#if screenName?has_content && screenName == "stockTypes">active</#if>">
                                        <a class="animsition-link" href="<@ofbizUrl>/qcStockTypes</@ofbizUrl>">
                                            <span class="site-menu-title">Stock Types</span>
                                        </a>
                                    </li>
                                    <li class="site-menu-item  <#if screenName?has_content && screenName == "materialTypes">active</#if>">
                                        <a class="animsition-link" href="<@ofbizUrl>/qcMaterialTypes</@ofbizUrl>">
                                            <span class="site-menu-title">Material Types</span>
                                        </a>
                                    </li>
                                    <li class="site-menu-item  <#if screenName?has_content && screenName == "vendors">active</#if>">
                                        <a class="animsition-link" href="<@ofbizUrl>/qcVendors</@ofbizUrl>">
                                            <span class="site-menu-title">Vendors</span>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </li>
                    </#if>
                    <#if security.hasPermission("PARTYMGR_ADMIN", session)>
                    <li class="site-menu-item has-sub <#if screenGroup?has_content && screenGroup == "customers">open active</#if>">
                        <a href="javascript:void(0)">
                            <i class="site-menu-icon wb-user" aria-hidden="true"></i>
                            <span class="site-menu-title">Customer</span>
                            <span class="site-menu-arrow"></span>
                        </a>
                        <ul class="site-menu-sub">
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/customers</@ofbizUrl>">
                                    <span class="site-menu-title">Customers</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    </#if>
                    <#if security.hasPermission("ORDERMGR_ADMIN", session)>
                    <li class="site-menu-item has-sub <#if screenGroup?has_content && screenGroup == "prepress">open active</#if>">
                        <a href="javascript:void(0)">
                            <i class="site-menu-icon wb-file" aria-hidden="true"></i>
                            <span class="site-menu-title">Print</span>
                            <span class="site-menu-arrow"></span>
                        </a>
                        <ul class="site-menu-sub">
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/prepress?webSiteId=envelopes,ae</@ofbizUrl>">
                                    <span class="site-menu-title">Envelopes Queue</span>
                                </a>
                            </li>
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/prepress?webSiteId=folders</@ofbizUrl>">
                                    <span class="site-menu-title">Folders Queue</span>
                                </a>
                            </li>
                            <li class="site-menu-item has-sub">
                                <a href="javascript:void(0)">
                                    <span class="site-menu-title">Plates</span>
                                    <span class="site-menu-arrow"></span>
                                </a>
                                <#assign printPressList = delegator.findAll("PrintPress", true) />
                                <#if printPressList?has_content>
                                <ul class="site-menu-sub">
                                	<li class="site-menu-item">
                                        <a class="animsition-link" href="<@ofbizUrl>/plateSchedule</@ofbizUrl>">
                                            <span class="site-menu-title">All Plates</span>
                                        </a>
                                    </li>
                                    <#list printPressList as press>
                                    <li class="site-menu-item">
                                        <a class="animsition-link" href="<@ofbizUrl>/plateSchedule</@ofbizUrl>?printPressId=${press.printPressId}">
                                            <span class="site-menu-title">${press.description}</span>
                                        </a>
                                    </li>
                                    </#list>
                                </ul>
                                </#if>
                            </li>
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/pressMan</@ofbizUrl>">
                                    <span class="site-menu-title">Stats</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    </#if>
                    <#if security.hasPermission("CHANNELS_ADMIN", session)>
                    <li class="site-menu-item has-sub <#if screenGroup?has_content && screenGroup == "channels">open active</#if>">
                        <a href="javascript:void(0)">
                            <i class="site-menu-icon wb-envelope" aria-hidden="true"></i>
                            <span class="site-menu-title">Channels</span>
                            <span class="site-menu-arrow"></span>
                        </a>
                        <ul class="site-menu-sub">
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/channelsQuantityOverride</@ofbizUrl>">
                                    <span class="site-menu-title">Quantity Override</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    </#if>
                    <#if security.hasPermission("CATALOG_ADMIN", session)>
                    <li class="site-menu-item has-sub <#if screenGroup?has_content && screenGroup == "products">open active</#if>">
                        <a href="javascript:void(0)">
                            <i class="site-menu-icon wb-envelope" aria-hidden="true"></i>
                            <span class="site-menu-title">Product</span>
                            <span class="site-menu-arrow"></span>
                        </a>
                        <ul class="site-menu-sub">
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/productEditor</@ofbizUrl>">
                                    <span class="site-menu-title">Editor</span>
                                </a>
                            </li>
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/productImport</@ofbizUrl>">
                                    <span class="site-menu-title">Importer</span>
                                </a>
                            </li>
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/productExport</@ofbizUrl>">
                                    <span class="site-menu-title">Exporter</span>
                                </a>
                            </li>
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/reviews</@ofbizUrl>">
                                    <span class="site-menu-title">Reviews</span>
                                </a>
                            </li>
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/productCategoryEditor</@ofbizUrl>">
                                    <span class="site-menu-title">Categories</span>
                                </a>
                            </li>
                            <li class="site-menu-item has-sub">
                                <a href="javascript:void(0)">
                                    <span class="site-menu-title">Outsourcing</span>
                                    <span class="site-menu-arrow"></span>
                                </a>
                                <ul class="site-menu-sub">
                                    <li class="site-menu-item">
                                        <a class="animsition-link" href="<@ofbizUrl>/vendors</@ofbizUrl>">
                                            <span class="site-menu-title">Vendors</span>
                                        </a>
                                    </li>
                                    <li class="site-menu-item">
                                        <a class="animsition-link" href="<@ofbizUrl>/outsourceableProducts</@ofbizUrl>">
                                            <span class="site-menu-title">Products</span>
                                        </a>
                                    </li>
                                    <li class="site-menu-item">
                                        <a class="animsition-link" href="<@ofbizUrl>/productVendor</@ofbizUrl>">
                                            <span class="site-menu-title">Vendor Products</span>
                                        </a>
                                    </li>
                                    <li class="site-menu-item">
                                        <a class="animsition-link" href="<@ofbizUrl>/outsourceZones</@ofbizUrl>">
                                            <span class="site-menu-title">Zones & Cost</span>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </li>
                    <li class="site-menu-item has-sub <#if screenGroup?has_content && screenGroup == "labels">open active</#if>">
                        <a href="javascript:void(0)">
                            <i class="site-menu-icon wb-file" aria-hidden="true"></i>
                            <span class="site-menu-title">Labels</span>
                            <span class="site-menu-arrow"></span>
                        </a>
                        <ul class="site-menu-sub">
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/sampleFolderLabelEditor</@ofbizUrl>">
                                    <span class="site-menu-title">Folder Samples</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    </#if>
                    <#if security.hasPermission("ORDERMGR_ADMIN", session)>
                    <li class="site-menu-item has-sub <#if screenGroup?has_content && screenGroup == "promos">open active</#if>">
                        <a href="javascript:void(0)">
                            <i class="site-menu-icon wb-tag" aria-hidden="true"></i>
                            <span class="site-menu-title">Coupons</span>
                            <span class="site-menu-arrow"></span>
                        </a>
                        <ul class="site-menu-sub">
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/createPromo</@ofbizUrl>">
                                    <span class="site-menu-title">Create</span>
                                </a>
                            </li>
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/promoList</@ofbizUrl>">
                                    <span class="site-menu-title">Find</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <!--
                    <li class="site-menu-item has-sub <#if screenGroup?has_content && screenGroup == "marketplace">open active</#if>">
                        <a href="javascript:void(0)">
                            <i class="site-menu-icon wb-shopping-cart" aria-hidden="true"></i>
                            <span class="site-menu-title">Marketplace</span>
                            <span class="site-menu-arrow"></span>
                        </a>
                        <ul class="site-menu-sub">
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/marketplaceOrders</@ofbizUrl>">
                                    <span class="site-menu-title">Orders</span>
                                </a>
                            </li>
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/marketplaceSellers</@ofbizUrl>">
                                    <span class="site-menu-title">Sellers</span>
                                </a>
                            </li>
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/marketplaceCategories</@ofbizUrl>">
                                    <span class="site-menu-title">Categories</span>
                                </a>
                            </li>
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/marketplaceHierarchy</@ofbizUrl>">
                                    <span class="site-menu-title">Hierarchy</span>
                                </a>
                            </li>
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/marketplaceProductAttributes</@ofbizUrl>">
                                    <span class="site-menu-title">Product Attributes</span>
                                </a>
                            </li>
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/marketplaceProducts</@ofbizUrl>">
                                    <span class="site-menu-title">Products</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    -->
                    <li class="site-menu-item has-sub <#if screenGroup?has_content && screenGroup == "editors">open active</#if>">
                        <a href="javascript:void(0)">
                            <i class="site-menu-icon wb-layout" aria-hidden="true"></i>
                            <span class="site-menu-title">Content Editors</span>
                            <span class="site-menu-arrow"></span>
                        </a>
                        <ul class="site-menu-sub">
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/homepageEditor</@ofbizUrl>">
                                    <span class="site-menu-title">Homepage</span>
                                </a>
                            </li>
                        </ul>
                        <ul class="site-menu-sub">
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/navigationEditor</@ofbizUrl>">
                                    <span class="site-menu-title">Navigation</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    </#if>
                    <#if security.hasPermission("SITESEARCHMGR_ADMIN", session)>
                    <li class="site-menu-item has-sub <#if screenGroup?has_content && screenGroup == "search">open active</#if>">
                        <a href="javascript:void(0)">
                            <i class="site-menu-icon wb-search" aria-hidden="true"></i>
                            <span class="site-menu-title">Site Search</span>
                            <span class="site-menu-arrow"></span>
                        </a>
                        <ul class="site-menu-sub">
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/searchSettings</@ofbizUrl>">
                                    <span class="site-menu-title">Search Settings</span>
                                </a>
                            </li>
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/createSynonym</@ofbizUrl>">
                                    <span class="site-menu-title">Synonyms</span>
                                </a>
                            </li>
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/createRedirect</@ofbizUrl>">
                                    <span class="site-menu-title">Redirects</span>
                                </a>
                            </li>
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/createBanner</@ofbizUrl>">
                                    <span class="site-menu-title">Banners</span>
                                </a>
                            </li>
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/createTunedResult</@ofbizUrl>">
                                    <span class="site-menu-title">Tune Results</span>
                                </a>
                            </li>
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/createSearchPage</@ofbizUrl>">
                                    <span class="site-menu-title">Landing Pages</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    </#if>
                    <#if security.hasPermission("SEOMGR_ADMIN", session)>
                    <li class="site-menu-item has-sub <#if screenGroup?has_content && screenGroup == "seo">open active</#if>">
                        <a href="javascript:void(0)">
                            <i class="site-menu-icon wb-search" aria-hidden="true"></i>
                            <span class="site-menu-title">SEO</span>
                            <span class="site-menu-arrow"></span>
                        </a>
                        <ul class="site-menu-sub">
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/seoRules</@ofbizUrl>">
                                    <span class="site-menu-title">SEO Rules</span>
                                </a>
                            </li>
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/rewriteRules</@ofbizUrl>">
                                    <span class="site-menu-title">Rewrite</span>
                                </a>
                            </li>
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/canonicalRules</@ofbizUrl>">
                                    <span class="site-menu-title">Canonicalization</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    </#if>
                    <#if security.hasPermission("CATALOG_ADMIN", session)>
                    <li class="site-menu-item has-sub <#if screenGroup?has_content && (screenGroup == "pricingEngine" || screenGroup == "pricingEngineSettings")>open active</#if>">
                        <a href="javascript:void(0)">
                            <i class="site-menu-icon fa-dollar" aria-hidden="true"></i>
                            <span class="site-menu-title">Pricing Engine</span>
                            <span class="site-menu-arrow"></span>
                        </a>
                        <ul class="site-menu-sub">
                            <li class="site-menu-item <#if screenName?has_content && screenName == "dashboard">active</#if>">
                                <a class="animsition-link" href="<@ofbizUrl>/pricingEngineDashboard</@ofbizUrl>?webSiteId=envelopes">
                                    <span class="site-menu-title">Dashboard</span>
                                </a>
                            </li>
                            <li class="site-menu-item has-sub <#if screenGroup?has_content && screenGroup == "pricingEngineSettings">open active</#if>">
                                <a href="javascript:void(0)">
                                    <span class="site-menu-title">Settings</span>
                                    <span class="site-menu-arrow"></span>
                                </a>
                                <ul class="site-menu-sub" style="padding-left: 20px;">
                                    <li class="site-menu-item <#if screenName?has_content && screenName == "products">active</#if>">
                                        <a class="animsition-link" href="<@ofbizUrl>/peProducts</@ofbizUrl>">
                                            <span class="site-menu-title">Products</span>
                                        </a>
                                    </li>
                                    <li class="site-menu-item <#if screenName?has_content && screenName == "vendors">active</#if>">
                                        <a class="animsition-link" href="<@ofbizUrl>/peVendors</@ofbizUrl>">
                                            <span class="site-menu-title">Vendors</span>
                                        </a>
                                    </li>
                                    <li class="site-menu-item  <#if screenName?has_content && screenName == "colors">active</#if>">
                                        <a class="animsition-link" href="<@ofbizUrl>/peColors</@ofbizUrl>">
                                            <span class="site-menu-title">Colors</span>
                                        </a>
                                    </li>
                                    <li class="site-menu-item <#if screenName?has_content && screenName == "pricingAttributes">active</#if>">
                                        <a class="animsition-link" href="<@ofbizUrl>/pePricingAttributes</@ofbizUrl>">
                                            <span class="site-menu-title">Pricing Attributes</span>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </li>
                    </#if>
                    <#if security.hasPermission("ORDERMGR_ADMIN", session)>
                    <li class="site-menu-item <#if !screenGroup?exists>active</#if>">
                        <a href="<@ofbizUrl>/jobs</@ofbizUrl>">
                            <i class="site-menu-icon wb-settings" aria-hidden="true"></i>
                            <span class="site-menu-title">Jobs</span>
                        </a>
                    </li>
                    </#if>
                    <#if security.hasPermission("VENDORORDERMGR_ADMIN", session)>
                    <#if userLogin?exists && userLogin.userLoginId.contains("kaylen_rachwal@admorefolders.com")>
                        <#assign vendorId = "V_ADMORE" />
                    </#if>
                    <#if vendorId?exists>
                    <li class="site-menu-item has-sub <#if screenGroup?has_content && screenGroup == "orders">open active</#if>">
                        <a href="javascript:void(0)">
                            <i class="site-menu-icon wb-shopping-cart" aria-hidden="true"></i>
                            <span class="site-menu-title">Orders</span>
                            <span class="site-menu-arrow"></span>
                        </a>
                        <ul class="site-menu-sub">
                            <li class="site-menu-item">
                                <a class="animsition-link" href="<@ofbizUrl>/vendorOrderList?partyId=${vendorId?if_exists}</@ofbizUrl>">
                                    <span class="site-menu-title">Orders</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    </#if>
                    </#if>
                </ul>
            </div>
        </div>
    </div>
</div>