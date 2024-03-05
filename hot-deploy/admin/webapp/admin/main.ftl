<link rel="stylesheet" href="/html/themes/global/vendor/chartist/chartist.css">
<link rel="stylesheet" href="/html/themes/base/assets/examples/css/widgets/statistics.css">
<link rel="stylesheet" href="/html/themes/base/assets/examples/css/widgets/chart.css">
<link rel="stylesheet" href="/html/themes/global/vendor/chartist-plugin-tooltip/chartist-plugin-tooltip.css">
<link rel="stylesheet" href="/html/themes/base/assets/examples/css/dashboard/analytics.css">
<link rel="stylesheet" href="/html/themes/global/vendor/jvectormap/jquery-jvectormap.css">
<link rel="stylesheet" href="/html/themes/base/assets/examples/css/dashboard/v1.css">

<style>
	#widgetJvmap, #widgetJvmap .jvectormap-container {
		height: 500px;
	}
</style>

<!-- Card -->
<#assign orderPercentChange = (1 - ((dashData.todayCurrent[1])?default("1")?number / (dashData.todayPrevious[1])?default("1")?number))*100 />
<#assign revenuePercentChange = (1 - ((dashData.todayCurrent[2])?default("1")?number / (dashData.todayPrevious[2])?default("1")?number))*100 />
<#--<#assign crChange = (1 - ((requestAttributes.googleMetricsToday.get("ga:transactionsPerVisit"))?default("1")?number / (requestAttributes.googleMetricsPrevious.get("ga:transactionsPerVisit"))?default("1")?number))*100 />
<#assign speedChange = (1 - ((requestAttributes.googleMetricsToday.get("ga:avgPageLoadTime"))?default("1")?number / (requestAttributes.googleMetricsPrevious.get("ga:avgPageLoadTime"))?default("1")?number))*100 />-->

<div class="row">
    <div class="col-lg-6 col-xs-12">
        <div class="card-group">
            <div class="card card-block p-0">
                <div class="counter counter-md vertical-align bg-white h-150">
                    <div class="counter-icon p-30 blue-600" style="position:absolute;top:0;left:0;">
                        <i class="icon wb-shopping-cart" aria-hidden="true"></i>
                    </div>
                    <div class="counter-number-group font-size-30 vertical-align-middle">
                        <span class="counter-number">${(dashData.todayCurrent[1])?default("1")}</span>
                        <span class="counter-number-related">Orders</span>
                        <span class="counter-icon <#if (dashData.todayCurrent[1])?default("1")?number gt (dashData.todayPrevious[1])?default("1")?number>green<#else>red</#if>-600 m-r-10"><i class="wb-graph-<#if (dashData.todayCurrent[1])?default("1")?number gt (dashData.todayPrevious[1])?default("1")?number>up<#else>down</#if>"></i></span>
                        <div class="font-size-20 m-t-3"><#if (dashData.todayCurrent[1])?default("1")?number gt (dashData.todayPrevious[1])?default("1")?number>Up<#else>Down</#if> ${orderPercentChange?abs?string["0.##"]}%</div>
                        <div class="font-size-10 m-t-3">${(dashData.todayPrevious[1])?default("1")}  orders last period</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-lg-6 col-xs-12">
        <div class="card-group">
            <div class="card card-block p-0">
                <div class="counter counter-md vertical-align bg-white h-150">
                    <div class="counter-icon p-30 blue-600" style="position:absolute;top:0;left:0;">
                        <i class="icon fa-money" aria-hidden="true"></i>
                    </div>
                    <div class="counter-number-group font-size-30 vertical-align-middle">
                        <span class="counter-number">${(dashData.todayCurrent[2])?default("1")?number?string.currency}</span>
                        <span class="counter-icon <#if (dashData.todayCurrent[2])?default("1")?number gt (dashData.todayPrevious[2])?default("1")?number>green<#else>red</#if>-600 m-r-10"><i class="wb-graph-<#if (dashData.todayCurrent[2])?default("1")?number gt (dashData.todayPrevious[2])?default("1")?number>up<#else>down</#if>"></i></span>
                        <div class="font-size-20 m-t-3"><#if (dashData.todayCurrent[2])?default("1")?number gt (dashData.todayPrevious[2])?default("1")?number>Up<#else>Down</#if> ${revenuePercentChange?abs?string["0.##"]}%</div>
                        <div class="font-size-10 m-t-3">${(dashData.todayPrevious[2])?default("1")?number?string.currency} last period</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <#--<div class="col-lg-3 col-xs-12">
        <div class="card-group">
            <div class="card card-block p-0">
                <div class="counter counter-md vertical-align bg-white h-150">
                    <div class="counter-icon p-30 blue-600" style="position:absolute;top:0;left:0;">
                        <i class="icon fa-dashboard" aria-hidden="true"></i>
                    </div>
                    <div class="counter-number-group font-size-30 vertical-align-middle">
                        <span class="counter-number">${(requestAttributes.googleMetricsToday.get("ga:transactionsPerVisit"))?default("1")?number?string['0.##']}% CR</span>
                        <span class="counter-icon <#if (requestAttributes.googleMetricsToday.get("ga:transactionsPerVisit"))?default("1")?number gt (requestAttributes.googleMetricsPrevious.get("ga:transactionsPerVisit"))?default("1")?number>green<#else>red</#if>-600 m-r-10"><i class="wb-graph-<#if (requestAttributes.googleMetricsToday.get("ga:transactionsPerVisit"))?default("1")?number gt (requestAttributes.googleMetricsPrevious.get("ga:transactionsPerVisit"))?default("1")?number>up<#else>down</#if>"></i></span>
                        <div class="font-size-20 m-t-3"><#if (requestAttributes.googleMetricsToday.get("ga:transactionsPerVisit"))?default("1")?number gt (requestAttributes.googleMetricsPrevious.get("ga:transactionsPerVisit"))?default("1")?number>Up<#else>Down</#if> ${crChange?abs?string["0.##"]}%</div>
                        <div class="font-size-10 m-t-3">${(requestAttributes.googleMetricsPrevious.get("ga:transactionsPerVisit"))?default("1")?number?string['0.##']}% CR last period</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-lg-3 col-xs-12">
        <div class="card-group">
            <div class="card card-block p-0">
                <div class="counter counter-md vertical-align bg-white h-150">
                    <div class="counter-icon p-30 blue-600" style="position:absolute;top:0;left:0;">
                        <i class="icon fa-spinner" aria-hidden="true"></i>
                    </div>
                    <div class="counter-number-group font-size-30 vertical-align-middle">
                        <span class="counter-number">${(requestAttributes.googleMetricsToday.get("ga:avgPageLoadTime"))?default("1")?number?string['0.##']}s</span>
                        <span class="counter-icon <#if (requestAttributes.googleMetricsToday.get("ga:avgPageLoadTime"))?default("1")?number gt (requestAttributes.googleMetricsPrevious.get("ga:avgPageLoadTime"))?default("1")?number>red<#else>green</#if>-600 m-r-10"><i class="wb-graph-<#if (requestAttributes.googleMetricsToday.get("ga:avgPageLoadTime"))?default("1")?number gt (requestAttributes.googleMetricsPrevious.get("ga:avgPageLoadTime"))?default("1")?number>down<#else>up</#if>"></i></span>
                        <div class="font-size-20 m-t-3">${speedChange?abs?string["0.##"]}% <#if (requestAttributes.googleMetricsToday.get("ga:avgPageLoadTime"))?default("1")?number gt (requestAttributes.googleMetricsPrevious.get("ga:avgPageLoadTime"))?default("1")?number>slower<#else>Faster</#if></div>
                        <div class="font-size-10 m-t-3">${(requestAttributes.googleMetricsPrevious.get("ga:avgPageLoadTime"))?default("1")?number?string['0.##']}s last period</div>
                    </div>
                </div>
            </div>
        </div>
    </div>-->
</div>
<!-- End Card -->

<div class="row">
    <div class="col-ms-12 col-xs-12 col-md-12" id="ecommerceChartView">
        <div class="card card-shadow">
            <div class="card-header card-header-transparent p-y-20">
                <div class="btn-group dropdown">
                    <a href="#" class="text-body blue-grey-700">Orders</a>
                </div>
            </div>
            <div class="widget-content tab-content bg-white p-20">
                <div class="ct-chart tab-pane active" id="scoreLineToDay"></div>
            </div>
        </div>
    </div>
</div>

<script>
    jQuery(document).ready(function($) {
        //total orders chart
        var dayList = [<#list dashData.currentPeriod.keySet() as key>'${key}'<#if key_has_next>,</#if></#list>];
        var currentOrdersList = {
            name: 'Current Period',
            data: [<#list dashData.currentPeriod.keySet() as key>'${dashData.currentPeriod.get(key)[0]}'<#if key_has_next>,</#if></#list>]
        };
        var previousOrdersList = {
            name: 'Previous Period',
            data: [<#list dashData.previousPeriod.keySet() as key>'${dashData.previousPeriod.get(key)[0]}'<#if key_has_next>,</#if></#list>]
        };

        new Chartist.Line('#scoreLineToDay', {
            labels: dayList,
            series: [currentOrdersList, previousOrdersList]
        }, {
            showArea: false,
            showLine: true,
            showPoint: true,
            fullWidth: true,
            axisX: {
                offset: 40,
                showLabel: true
            },
            axisY: {
                offset: 30,
                labelInterpolationFnc: function(value) {
                    if (value == 0) {
                        return null;
                    }
                    return value;
                },
                scaleMinSpace: 40,
                showLabel: true
            },
            plugins: [
                Chartist.plugins.tooltip()
            ]
        });

        //call for server stats from nginx
        //(function serverstats() {
        //    $.ajax({
        //        type: 'GET',
        //        url: 'https://www.envelopes.com/status',
        //        dataType: 'json',
        //        cache: false
        //    }).done(function(data) {
        //        console.log(data);
        //        new Chartist.Pie('#exampleGaugePie', {
        //            series: [20]
        //        }, {
        //            donut: true,
        //            donutWidth: 60,
        //            startAngle: 270,
        //            total: 180,
        //            showLabel: false
        //        });
        //        setTimeout(serverstats, 5000);
        //    }).fail(function(xhr) {
        //        //console.log(xhr);
        //    });
        //})();
    });
</script>

<script src="/html/themes/global/vendor/chartist/chartist.min.js"></script>
<script src="/html/themes/global/vendor/chartist-plugin-tooltip/chartist-plugin-tooltip.min.js"></script>