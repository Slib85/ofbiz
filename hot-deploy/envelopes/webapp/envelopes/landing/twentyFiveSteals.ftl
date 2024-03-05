<#assign now = Static["org.apache.ofbiz.base.util.UtilDateTime"].nowTimestamp()>
<link href="<@ofbizContentUrl>/html/css/landing/twenty-five-steals.css?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>" rel="stylesheet" />
<div id="twentyFiveStealsWrap" class="content container">

    <div class="twentyFiveStealsContent">

        <#--  <a href="<@ofbizUrl>#</@ofbizUrl>">
            <img src="<@ofbizScene7Url></@ofbizScene7Url>" alt="">
        </a>  -->
        <#--  <div class="bnc_deal_box" id="bnc_deal_box"></div>  -->

        <div id="twenty_five_days_holiday_banner" class="">
            <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/twenty-five-steals-of-the-season-main-banner-v3.jpg</@ofbizContentUrl>" alt="25 Steals of the Season | Envelopes.com" />
            <div class="twenty_five_days_holiday_description">
                <p>
                    <span>'Tis the season of giving and we are giving you a surprise product deal every day.</span> 
                    <span>Click today's envelope to reveal the steal and save big!</span>
                </p>
            </div>
        </div>

        <div id="twenty_five_days_holiday_deals" class="flex_row flex_center flex_count-5">
            <div class="deal_glow">
                <#if (currentTimestamp?default(now?datetime) gte "2020-11-27 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-11-27 23:59:59.000"?datetime)>
                    <a href="<@ofbizUrl>/search?w=clearance+a7+invitation+envelopes</@ofbizUrl>" title="Day 1 | 25 Steals of the Season | Envelopes.com">
                        <div class="inner_ribbon_wrap"><span>Today's Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-01.jpg</@ofbizContentUrl>" alt="Day 1 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                <#elseif (currentTimestamp?default(now?datetime) gte "2020-11-24 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-11-26 23:59:59.000"?datetime)>
                    <a href="javascript:void(0)" title="Day 1 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Upcoming Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-01.jpg</@ofbizContentUrl>" alt="Day 1 | 25 Steals of the Season | Envelopes.com" style="opacity:50%;" />
                    </a>
                <#else>
                    <a href="javascript:void(0)" title="Day 1 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Expired</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-01-inactive.png</@ofbizContentUrl>" alt="Day 1 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                </#if>
            </div>
            <div class="deal_glow">
                <#if (currentTimestamp?default(now?datetime) gte "2020-11-28 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-11-28 23:59:59.000"?datetime)>
                    <a href="<@ofbizUrl>/search?w=clearance+8+1%2F2+x+11+paper</@ofbizUrl>" title="Day 2 | 25 Steals of the Season | Envelopes.com">
                        <div class="inner_ribbon_wrap"><span>Today's Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-02.jpg</@ofbizContentUrl>" alt="Day 2 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                <#elseif (currentTimestamp?default(now?datetime) gte "2020-11-24 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-11-27 23:59:59.000"?datetime)>
                    <a href="javascript:void(0)" title="Day 2 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Upcoming Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-02.jpg</@ofbizContentUrl>" alt="Day 2 | 25 Steals of the Season | Envelopes.com" style="opacity:50%;" />
                    </a>
                <#else>
                    <a href="javascript:void(0)" title="Day 2 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Expired</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-02-inactive.png</@ofbizContentUrl>" alt="Day 2 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                </#if>
            </div>
            <div class="deal_glow">
                <#if (currentTimestamp?default(now?datetime) gte "2020-11-29 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-11-29 23:59:59.000"?datetime)>
                    <a href="<@ofbizUrl>/search?w=clearance+%231+coin+envelopes</@ofbizUrl>" title="Day 3 | 25 Steals of the Season | Envelopes.com">
                        <div class="inner_ribbon_wrap"><span>Today's Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-03.jpg</@ofbizContentUrl>" alt="Day 3 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                <#elseif (currentTimestamp?default(now?datetime) gte "2020-11-24 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-11-28 23:59:59.000"?datetime)>
                    <a href="javascript:void(0)" title="Day 3 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Upcoming Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-03.jpg</@ofbizContentUrl>" alt="Day 3 | 25 Steals of the Season | Envelopes.com" style="opacity:50%;" />
                    </a>
                <#else>
                    <a href="javascript:void(0)" title="Day 3 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Expired</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-03-inactive.png</@ofbizContentUrl>" alt="Day 3 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                </#if>
            </div>
            <div class="deal_glow">
                <#if (currentTimestamp?default(now?datetime) gte "2020-11-30 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-11-30 23:59:59.000"?datetime)>
                    <a href="<@ofbizUrl>/search?w=clearance folded card&af=si:414x512</@ofbizUrl>" title="Day 4 | 25 Steals of the Season | Envelopes.com">
                        <div class="inner_ribbon_wrap"><span>Today's Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-04.jpg</@ofbizContentUrl>" alt="Day 4 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                <#elseif (currentTimestamp?default(now?datetime) gte "2020-11-24 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-11-29 23:59:59.000"?datetime)>
                    <a href="javascript:void(0)" title="Day 4 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Upcoming Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-04.jpg</@ofbizContentUrl>" alt="Day 4 | 25 Steals of the Season | Envelopes.com" style="opacity:50%;" />
                    </a>
                <#else>
                    <a href="javascript:void(0)" title="Day 4 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Expired</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-04-inactive.png</@ofbizContentUrl>" alt="Day 4 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                </#if>
            </div>
            <div class="deal_glow">
                <#if (currentTimestamp?default(now?datetime) gte "2020-12-01 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-01 23:59:59.000"?datetime)>
                    <a href="<@ofbizUrl>/search?w=clearance+a7+contour+flap</@ofbizUrl>" title="Day 5 | 25 Steals of the Season | Envelopes.com">
                        <div class="inner_ribbon_wrap"><span>Today's Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-05.jpg</@ofbizContentUrl>" alt="Day 5 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                <#elseif (currentTimestamp?default(now?datetime) gte "2020-11-24 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-00 23:59:59.000"?datetime)>
                    <a href="javascript:void(0)" title="Day 5 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Upcoming Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-05.jpg</@ofbizContentUrl>" alt="Day 5 | 25 Steals of the Season | Envelopes.com" style="opacity:50%;" />
                    </a>
                <#else>
                    <a href="javascript:void(0)" title="Day 5 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Expired</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-05-inactive.png</@ofbizContentUrl>" alt="Day 5 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                </#if>
            </div>
            <div class="deal_glow">
                <#if (currentTimestamp?default(now?datetime) gte "2020-12-02 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-02 23:59:59.000"?datetime)>
                    <a href="<@ofbizUrl>/search?w=clearance+a7+pocket+invitations</@ofbizUrl>" title="Day 6 | 25 Steals of the Season | Envelopes.com">
                        <div class="inner_ribbon_wrap"><span>Today's Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-06.jpg</@ofbizContentUrl>" alt="Day 6 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                <#elseif (currentTimestamp?default(now?datetime) gte "2020-11-24 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-01 23:59:59.000"?datetime)>
                    <a href="javascript:void(0)" title="Day 6 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Upcoming Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-06.jpg</@ofbizContentUrl>" alt="Day 6 | 25 Steals of the Season | Envelopes.com" style="opacity:50%;" />
                    </a>
                <#else>
                    <a href="javascript:void(0)" title="Day 6 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Expired</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-06-inactive.png</@ofbizContentUrl>" alt="Day 6 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                </#if>
            </div>
            <div class="deal_glow">
                <#if (currentTimestamp?default(now?datetime) gte "2020-12-03 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-03 23:59:59.000"?datetime)>
                    <a href="<@ofbizUrl>/search?w=clearance+6+1%2F2+x+6+1%2F2+square+envelopes</@ofbizUrl>" title="Day 7 | 25 Steals of the Season | Envelopes.com">
                        <div class="inner_ribbon_wrap"><span>Today's Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-07.jpg</@ofbizContentUrl>" alt="Day 7 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                <#elseif (currentTimestamp?default(now?datetime) gte "2020-11-24 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-02 23:59:59.000"?datetime)>
                    <a href="javascript:void(0)" title="Day 7 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Upcoming Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-07.jpg</@ofbizContentUrl>" alt="Day 7 | 25 Steals of the Season | Envelopes.com" style="opacity:50%;" />
                    </a>
                <#else>
                    <a href="javascript:void(0)" title="Day 7 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Expired</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-07-inactive.png</@ofbizContentUrl>" alt="Day 7 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                </#if>
            </div>
            <div class="deal_glow">
                <#if (currentTimestamp?default(now?datetime) gte "2020-12-04 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-04 23:59:59.000"?datetime)>
                    <a href="<@ofbizUrl>/search?w=clearance+6x9+booklet+envelopes</@ofbizUrl>" title="Day 8 | 25 Steals of the Season | Envelopes.com">
                        <div class="inner_ribbon_wrap"><span>Today's Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-08.jpg</@ofbizContentUrl>" alt="Day 8 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                <#elseif (currentTimestamp?default(now?datetime) gte "2020-11-24 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-03 23:59:59.000"?datetime)>
                    <a href="javascript:void(0)" title="Day 8 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Upcoming Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-08.jpg</@ofbizContentUrl>" alt="Day 8 | 25 Steals of the Season | Envelopes.com" style="opacity:50%;" />
                    </a>
                <#else>
                    <a href="javascript:void(0)" title="Day 8 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Expired</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-08-inactive.png</@ofbizContentUrl>" alt="Day 8 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                </#if>
            </div>
            <div class="deal_glow">
                <#if (currentTimestamp?default(now?datetime) gte "2020-12-05 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-05 23:59:59.000"?datetime)>
                    <a href="<@ofbizUrl>/search?w=clearance+%2317+mini+envelopes</@ofbizUrl>" title="Day 9 | 25 Steals of the Season | Envelopes.com">
                        <div class="inner_ribbon_wrap"><span>Today's Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-09.jpg</@ofbizContentUrl>" alt="Day 9 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                <#elseif (currentTimestamp?default(now?datetime) gte "2020-11-24 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-04 23:59:59.000"?datetime)>
                    <a href="javascript:void(0)" title="Day 9 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Upcoming Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-09.jpg</@ofbizContentUrl>" alt="Day 9 | 25 Steals of the Season | Envelopes.com" style="opacity:50%;" />
                    </a>
                <#else>
                    <a href="javascript:void(0)" title="Day 9 | 25 Steals of the Season | Envelopes.com">
                        <div class="inner_ribbon_wrap"><span>Expired</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-09-inactive.png</@ofbizContentUrl>" alt="Day 9 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                </#if>
            </div>
            <div class="deal_glow">
                <#if (currentTimestamp?default(now?datetime) gte "2020-12-06 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-06 23:59:59.000"?datetime)>
                    <a href="<@ofbizUrl>/search?w=clearance+9+x+12+open+end</@ofbizUrl>" title="Day 10 | 25 Steals of the Season | Envelopes.com">
                        <div class="inner_ribbon_wrap" style="color: #000 !important;"><span>Today's Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-10.jpg</@ofbizContentUrl>" alt="Day 10 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                <#elseif (currentTimestamp?default(now?datetime) gte "2020-11-24 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-05 23:59:59.000"?datetime)>
                    <a href="javascript:void(0)" title="Day 10 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Upcoming Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-10.jpg</@ofbizContentUrl>" alt="Day 10 | 25 Steals of the Season | Envelopes.com" style="opacity:50%;" />
                    </a>
                <#else>
                    <a href="javascript:void(0)" title="Day 10 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Expired</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-10-inactive.png</@ofbizContentUrl>" alt="Day 10 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                </#if>
            </div>
            <div class="deal_glow">
                <#if (currentTimestamp?default(now?datetime) gte "2020-12-07 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-07 23:59:59.000"?datetime)>
                    <a href="<@ofbizUrl>/search?w=clearance+%2310+square+flap+envelopes</@ofbizUrl>" title="Day 11 | 25 Steals of the Season | Envelopes.com">
                        <div class="inner_ribbon_wrap"><span>Today's Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-11.jpg</@ofbizContentUrl>" alt="Day 11 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                <#elseif (currentTimestamp?default(now?datetime) gte "2020-11-24 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-06 23:59:59.000"?datetime)>
                    <a href="javascript:void(0)" title="Day 11 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Upcoming Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-11.jpg</@ofbizContentUrl>" alt="Day 11 | 25 Steals of the Season | Envelopes.com" style="opacity:50%;" />
                    </a>
                <#else>
                    <a href="javascript:void(0)" title="Day 11 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Expired</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-11-inactive.png</@ofbizContentUrl>" alt="Day 11 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                </#if>
            </div>
            <div class="deal_glow">
                <#if (currentTimestamp?default(now?datetime) gte "2020-12-08 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-08 23:59:59.000"?datetime)>
                    <a href="<@ofbizUrl>/search?w=clearance+a9+envelopes</@ofbizUrl>" title="Day 12 | 25 Steals of the Season | Envelopes.com">
                        <div class="inner_ribbon_wrap"><span>Today's Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-12.jpg</@ofbizContentUrl>" alt="Day 12 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                <#elseif (currentTimestamp?default(now?datetime) gte "2020-11-24 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-07 23:59:59.000"?datetime)>
                    <a href="javascript:void(0)" title="Day 12 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Upcoming Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-12.jpg</@ofbizContentUrl>" alt="Day 12 | 25 Steals of the Season | Envelopes.com" style="opacity:50%;" />
                    </a>
                <#else>
                    <a href="javascript:void(0)" title="Day 12 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Expired</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-12-inactive.png</@ofbizContentUrl>" alt="Day 12 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                </#if>
            </div>
            <div class="deal_glow">
                <#if (currentTimestamp?default(now?datetime) gte "2020-12-09 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-09 23:59:59.000"?datetime)>
                    <a href="<@ofbizUrl>/search?w=clearance+credit+card+sleeves</@ofbizUrl>" title="Day 13 | 25 Steals of the Season | Envelopes.com">
                        <div class="inner_ribbon_wrap"><span>Today's Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-13.jpg</@ofbizContentUrl>" alt="Day 13 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                <#elseif (currentTimestamp?default(now?datetime) gte "2020-11-24 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-08 23:59:59.000"?datetime)>
                    <a href="javascript:void(0)" title="Day 13 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Upcoming Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-13.jpg</@ofbizContentUrl>" alt="Day 13 | 25 Steals of the Season | Envelopes.com" style="opacity:50%;" />
                    </a>
                <#else>
                    <a href="javascript:void(0)" title="Day 13 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Expired</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-13-inactive.png</@ofbizContentUrl>" alt="Day 13 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                </#if>
            </div>
            <div class="deal_glow">
                <#if (currentTimestamp?default(now?datetime) gte "2020-12-10 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-10 23:59:59.000"?datetime)>
                    <a href="<@ofbizUrl>/search?w=clearance+%23+10+envelopes</@ofbizUrl>" title="Day 14| 25 Steals of the Season | Envelopes.com">
                        <div class="inner_ribbon_wrap"><span>Today's Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-14.jpg</@ofbizContentUrl>" alt="Day 14 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                <#elseif (currentTimestamp?default(now?datetime) gte "2020-11-24 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-09 23:59:59.000"?datetime)>
                    <a href="javascript:void(0)" title="Day 14 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Upcoming Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-14.jpg</@ofbizContentUrl>" alt="Day 14 | 25 Steals of the Season | Envelopes.com" style="opacity:50%;" />
                    </a>
                <#else>
                    <a href="javascript:void(0)" title="Day 14| 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Expired</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-14-inactive.png</@ofbizContentUrl>" alt="Day 14 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                </#if>
            </div>
            <div class="deal_glow">
                <#if (currentTimestamp?default(now?datetime) gte "2020-12-11 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-11 23:59:59.000"?datetime)>
                    <a href="<@ofbizUrl>/search?w=clearance invitation envelopes&af=si:438x534</@ofbizUrl>" title="Day 15 | 25 Steals of the Season | Envelopes.com">
                        <div class="inner_ribbon_wrap"><span>Today's Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-15.jpg</@ofbizContentUrl>" alt="Day 15 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                <#elseif (currentTimestamp?default(now?datetime) gte "2020-11-24 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-10 23:59:59.000"?datetime)>
                    <a href="javascript:void(0)" title="Day 15 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Upcoming Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-15.jpg</@ofbizContentUrl>" alt="Day 15 | 25 Steals of the Season | Envelopes.com" style="opacity:50%;" />
                    </a>
                <#else>
                    <a href="javascript:void(0)" title="Day 15 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Expired</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-15-inactive.png</@ofbizContentUrl>" alt="Day 15 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                </#if>
            </div>
            <div class="deal_glow">
                <#if (currentTimestamp?default(now?datetime) gte "2020-12-12 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-12 23:59:59.000"?datetime)>
                    <a href="<@ofbizUrl>/search?w=clearance+%23+10+full+face+window+envelopes</@ofbizUrl>" title="Day 16 | 25 Steals of the Season | Envelopes.com">
                        <div class="inner_ribbon_wrap"><span>Today's Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-16.jpg</@ofbizContentUrl>" alt="Day 16 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                <#elseif (currentTimestamp?default(now?datetime) gte "2020-11-24 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-11 23:59:59.000"?datetime)>
                    <a href="javascript:void(0)" title="Day 16 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Upcoming Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-16.jpg</@ofbizContentUrl>" alt="Day 16 | 25 Steals of the Season | Envelopes.com" style="opacity:50%;" />
                    </a>
                <#else>
                    <a href="javascript:void(0)" title="Day 16 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Expired</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-16-inactive.png</@ofbizContentUrl>" alt="Day 16 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                </#if>
            </div>
            <div class="deal_glow">
                <#if (currentTimestamp?default(now?datetime) gte "2020-12-13 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-13 23:59:59.000"?datetime)>
                    <a href="<@ofbizUrl>/search?w=clearance+a6+invitation+envelopes</@ofbizUrl>" title="Day 17 | 25 Steals of the Season | Envelopes.com">
                        <div class="inner_ribbon_wrap"><span>Today's Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-17.jpg</@ofbizContentUrl>" alt="Day 17 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                <#elseif (currentTimestamp?default(now?datetime) gte "2020-11-24 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-12 23:59:59.000"?datetime)>
                    <a href="javascript:void(0)" title="Day 17 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Upcoming Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-17.jpg</@ofbizContentUrl>" alt="Day 17 | 25 Steals of the Season | Envelopes.com" style="opacity:50%;" />
                    </a>
                <#else>
                    <a href="javascript:void(0)" title="Day 17 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Expired</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-17-inactive.png</@ofbizContentUrl>" alt="Day 17 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                </#if>
            </div>
            <div class="deal_glow">
                <#if (currentTimestamp?default(now?datetime) gte "2020-12-14 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-14 23:59:59.000"?datetime)>
                    <a href="<@ofbizUrl>/search?w=clearance folded cards&af=si:518x7</@ofbizUrl>" title="Day 18 | 25 Steals of the Season | Envelopes.com">
                        <div class="inner_ribbon_wrap"><span>Today's Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-18.jpg</@ofbizContentUrl>" alt="Day 18 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                <#elseif (currentTimestamp?default(now?datetime) gte "2020-11-24 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-13 23:59:59.000"?datetime)>
                    <a href="javascript:void(0)" title="Day 18 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Upcoming Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-18.jpg</@ofbizContentUrl>" alt="Day 18 | 25 Steals of the Season | Envelopes.com" style="opacity:50%;" />
                    </a>
                <#else>
                    <a href="javascript:void(0)" title="Day 18 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Expired</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-18-inactive.png</@ofbizContentUrl>" alt="Day 18 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                </#if>
            </div>
            <div class="deal_glow">
                <#if (currentTimestamp?default(now?datetime) gte "2020-12-15 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-15 23:59:59.000"?datetime)>
                    <a href="<@ofbizUrl>/search?w=clearance%206%20x%209%20open%20end%20envelopes&af=st:openend</@ofbizUrl>" title="Day 19 | 25 Steals of the Season | Envelopes.com">
                        <div class="inner_ribbon_wrap"><span>Today's Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-19.jpg</@ofbizContentUrl>" alt="Day 19 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                <#elseif (currentTimestamp?default(now?datetime) gte "2020-11-24 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-14 23:59:59.000"?datetime)>
                    <a href="javascript:void(0)" title="Day 19 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Upcoming Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-19.jpg</@ofbizContentUrl>" alt="Day 19 | 25 Steals of the Season | Envelopes.com" style="opacity:50%;" />
                    </a>
                <#else>
                    <a href="javascript:void(0)" title="Day 19 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Expired</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-19-inactive.png</@ofbizContentUrl>" alt="Day 19 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                </#if>
            </div>
            <div class="deal_glow">
                <#if (currentTimestamp?default(now?datetime) gte "2020-12-16 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-16 23:59:59.000"?datetime)>
                    <a href="<@ofbizUrl>/search?w=clearance+%23+10+window+envelopes</@ofbizUrl>" title="Day 20 | 25 Steals of the Season | Envelopes.com">
                        <div class="inner_ribbon_wrap"><span>Today's Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-20.jpg</@ofbizContentUrl>" alt="Day 20 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                <#elseif (currentTimestamp?default(now?datetime) gte "2020-11-24 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-15 23:59:59.000"?datetime)>
                    <a href="javascript:void(0)" title="Day 20 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Upcoming Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-20.jpg</@ofbizContentUrl>" alt="Day 20 | 25 Steals of the Season | Envelopes.com" style="opacity:50%;" />
                    </a>
                <#else>
                    <a href="javascript:void(0)" title="Day 20 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Expired</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-20-inactive.png</@ofbizContentUrl>" alt="Day 20 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                </#if>
            </div>
            <div class="deal_glow">
                <#if (currentTimestamp?default(now?datetime) gte "2020-12-17 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-17 23:59:59.000"?datetime)>
                    <a href="<@ofbizUrl>/search?w=clearance a1 invitation envelopes&af=si:358x518</@ofbizUrl>" title="Day 21 | 25 Steals of the Season | Envelopes.com">
                        <div class="inner_ribbon_wrap"><span>Today's Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-21.jpg</@ofbizContentUrl>" alt="Day 21 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                <#elseif (currentTimestamp?default(now?datetime) gte "2020-11-24 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-16 23:59:59.000"?datetime)>
                    <a href="javascript:void(0)" title="Day 21 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Upcoming Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-21.jpg</@ofbizContentUrl>" alt="Day 21 | 25 Steals of the Season | Envelopes.com" style="opacity:50%;" />
                    </a>
                <#else>
                    <a href="javascript:void(0)" title="Day 21 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Expired</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-21-inactive.png</@ofbizContentUrl>" alt="Day 21 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                </#if>
            </div>
            <div class="deal_glow">
                <#if (currentTimestamp?default(now?datetime) gte "2020-12-18 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-18 23:59:59.000"?datetime)>
                    <a href="<@ofbizUrl>/search?w=clearance a4 invitation envelopes&af=si:414x614</@ofbizUrl>" title="Day 22 | 25 Steals of the Season | Envelopes.com">
                        <div class="inner_ribbon_wrap"><span>Today's Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-22.jpg</@ofbizContentUrl>" alt="Day 22 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                <#elseif (currentTimestamp?default(now?datetime) gte "2020-11-24 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-17 23:59:59.000"?datetime)>
                    <a href="javascript:void(0)" title="Day 22 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Upcoming Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-22.jpg</@ofbizContentUrl>" alt="Day 22 | 25 Steals of the Season | Envelopes.com" style="opacity:50%;" />
                    </a>
                <#else>
                    <a href="javascript:void(0)" title="Day 22 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Expired</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-22-inactive.png</@ofbizContentUrl>" alt="Day 22 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                </#if>
            </div>
            <div class="deal_glow">
                <#if (currentTimestamp?default(now?datetime) gte "2020-12-19 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-19 23:59:59.000"?datetime)>
                    <a href="<@ofbizUrl>/search?w=clearance 5 x 5 square envelopes&af=si:5x5</@ofbizUrl>" title="Day 23 | 25 Steals of the Season | Envelopes.com">
                        <div class="inner_ribbon_wrap" style="color: #000 !important;"><span>Today's Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-23.jpg</@ofbizContentUrl>" alt="Day 23 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                <#elseif (currentTimestamp?default(now?datetime) gte "2020-11-24 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-18 23:59:59.000"?datetime)>
                    <a href="javascript:void(0)" title="Day 23 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Upcoming Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-23.jpg</@ofbizContentUrl>" alt="Day 23 | 25 Steals of the Season | Envelopes.com" style="opacity:50%;" />
                    </a>
                <#else>
                    <a href="javascript:void(0)" title="Day 23 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Expired</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-23-inactive.png</@ofbizContentUrl>" alt="Day 23 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                </#if>
            </div>
            <div class="deal_glow">
                <#if (currentTimestamp?default(now?datetime) gte "2020-12-20 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-20 23:59:59.000"?datetime)>
                    <a href="<@ofbizUrl>/search?w=clearance+9+x+12+booklet+envelopes</@ofbizUrl>" title="Day 24 | 25 Steals of the Season | Envelopes.com">
                        <div class="inner_ribbon_wrap"><span>Today's Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-24.jpg</@ofbizContentUrl>" alt="Day 24 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                <#elseif (currentTimestamp?default(now?datetime) gte "2020-11-24 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-19 23:59:59.000"?datetime)>
                    <a href="javascript:void(0)" title="Day 24 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Upcoming Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-24.jpg</@ofbizContentUrl>" alt="Day 24 | 25 Steals of the Season | Envelopes.com" style="opacity:50%;" />
                    </a>
                <#else>
                    <a href="javascript:void(0)" title="Day 24 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Expired</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-24-inactive.png</@ofbizContentUrl>" alt="Day 24 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                </#if>
            </div>
            <div class="deal_glow">
                <#if (currentTimestamp?default(now?datetime) gte "2020-12-21 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-21 23:59:59.000"?datetime)>
                    <a href="<@ofbizUrl>/search?w=clearance+8+1%2F2+x+11+cardstock</@ofbizUrl>" title="Day 25 | 25 Steals of the Season | Envelopes.com">
                        <div class="inner_ribbon_wrap"><span>Today's Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-25.jpg</@ofbizContentUrl>" alt="Day 25 | 25 Steals of the Season | Envelopes.com" />
                    </a>
                <#elseif (currentTimestamp?default(now?datetime) gte "2020-11-24 00:00:00.000"?datetime && currentTimestamp?default(now?datetime) lte "2020-12-20 23:59:59.000"?datetime)>
                    <a href="javascript:void(0)" title="Day 25 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Upcoming Deal!</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-25.jpg</@ofbizContentUrl>" alt="Day 25 | 25 Steals of the Season | Envelopes.com" style="opacity:50%;" />
                    </a>
                <#else>
                    <a href="javascript:void(0)" title="Day 25 | 25 Steals of the Season | Envelopes.com" style="cursor:default;">
                        <div class="inner_ribbon_wrap"><span>Expired</span></div>
                        <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/promo-day-25-inactive.png</@ofbizContentUrl>" alt="Day 25 | 25 Steals of the Season | Envelopes.com" />
                    </a> 
                </#if>
            </div>
            <div class="bnc_icon_ui">
                <img src="<@ofbizContentUrl>/html/img/landing/25Steals/holiday/white-envelope-icon.png</@ofbizContentUrl>" alt="Envelope Icon | Envelopes.com" />
            </div>
        </div>

    </div>

</div>
<script src="<@ofbizContentUrl>/html/js/landing/twenty-five-steals.js?ts=${pageTimestamp?default("65535")}</@ofbizContentUrl>"></script>