package com.bigname.analytics;

import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
import com.google.api.services.analyticsreporting.v4.model.*;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

public class GoogleAnalyticsHelper {
    public static final String module = GoogleAnalyticsHelper.class.getName();

    /*public static String getCampaignAndTransactionReport(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Map<String, String>> orderData = new HashMap<>();

        try {
            AnalyticsReporting service = GoogleAnalytics.initializeAnalyticsReporting();

            // Create the DateRange object.
            DateRange dateRange = new DateRange();
            dateRange.setStartDate(EnvConstantsUtil.yyyyMMddDash.format(EnvUtil.getNDaysBeforeOrAfterDate(UtilDateTime.nowTimestamp(), -1, false, false)));
            dateRange.setEndDate(EnvConstantsUtil.yyyyMMddDash.format(EnvUtil.getNDaysBeforeOrAfterDate(UtilDateTime.nowTimestamp(), -1, true, false)));

            List<Dimension> reportDimensions = new ArrayList<>();
            reportDimensions.add(new Dimension().setName("ga:transactionId"));
            reportDimensions.add(new Dimension().setName("ga:source"));
            reportDimensions.add(new Dimension().setName("ga:medium"));
            reportDimensions.add(new Dimension().setName("ga:campaign"));
            reportDimensions.add(new Dimension().setName("ga:adMatchedQuery"));

            List<Metric> reportMetrics = new ArrayList<>();
            reportMetrics.add(new Metric().setExpression("ga:transactionRevenue"));

            ArrayList<ReportRequest> reportRequests = new ArrayList<ReportRequest>();
            reportRequests.add(new ReportRequest().setViewId(GoogleAnalytics.ENV_VIEW_ID).setDateRanges(Arrays.asList(dateRange)).setMetrics(reportMetrics).setDimensions(reportDimensions).setPageSize(100000));

            // Create the GetReportsRequest object.
            GetReportsRequest reports = new GetReportsRequest().setReportRequests(reportRequests);

            // Call the batchGet method.
            GetReportsResponse reportResponse = service.reports().batchGet(reports).execute();

            for (Report report: reportResponse.getReports()) {
                ColumnHeader header = report.getColumnHeader();
                List<String> dimensionHeaders = header.getDimensions();
                List<ReportRow> rows = report.getData().getRows();

                if (rows == null) {
                    System.out.println("No data found for " + GoogleAnalytics.ENV_VIEW_ID);
                    return "success";
                }

                for (ReportRow row: rows) {
                    List<String> dimensions = row.getDimensions();
                    for (int i = 0; i < dimensionHeaders.size() && i < dimensions.size(); i++) {
                        System.out.println(dimensionHeaders.get(i) + ": " + dimensions.get(i));
                    }
                }
            }
        } catch (Exception e) {
            //EnvUtil.reportError(e);
            Debug.logError(e, "Error trying to get campaign and transaction report.", module);
        }

        return "success";
    }*/

    /**
     * Get all data from Google Analytics for product level stats
     * @param delegator
     */
    public static void getProductStatReport(Delegator delegator) {
        Map<String, Map<String, String>> orderData = new HashMap<>();

        try {
            AnalyticsReporting service = GoogleAnalytics.initializeAnalyticsReporting();

            // Create the DateRange object.
            DateRange dateRange = new DateRange();
            dateRange.setStartDate(EnvConstantsUtil.yyyyMMddDash.format(EnvUtil.getNDaysBeforeOrAfterDate(UtilDateTime.nowTimestamp(), -90, false, false)));
            dateRange.setEndDate(EnvConstantsUtil.yyyyMMddDash.format(EnvUtil.getNDaysBeforeOrAfterDate(UtilDateTime.nowTimestamp(), -1, true, false)));

            List<Dimension> reportDimensions = new ArrayList<>();
            reportDimensions.add(new Dimension().setName("ga:productSku"));

            List<Metric> reportMetrics = new ArrayList<>();
            reportMetrics.add(new Metric().setExpression("ga:productAddsToCart"));
            reportMetrics.add(new Metric().setExpression("ga:productCheckouts"));
            reportMetrics.add(new Metric().setExpression("ga:productDetailViews"));
            reportMetrics.add(new Metric().setExpression("ga:buyToDetailRate"));
            reportMetrics.add(new Metric().setExpression("ga:quantityCheckedOut"));
            reportMetrics.add(new Metric().setExpression("ga:itemRevenue"));

            for(String viewId : GoogleAnalytics.VIEW_LIST) {
                ArrayList<ReportRequest> reportRequests = new ArrayList<ReportRequest>();
                reportRequests.add(new ReportRequest().setViewId(viewId).setDateRanges(Arrays.asList(dateRange)).setMetrics(reportMetrics).setDimensions(reportDimensions).setPageSize(100000));

                // Create the GetReportsRequest object.
                GetReportsRequest reports = new GetReportsRequest().setReportRequests(reportRequests);

                // Call the batchGet method.
                GetReportsResponse reportResponse = service.reports().batchGet(reports).execute();

                //delegator.removeAll("ProductStatistic"); //remove old data
                for (Report report: reportResponse.getReports()) {
                    ColumnHeader header = report.getColumnHeader();
                    List<String> dimensionHeaders = header.getDimensions();
                    List<MetricHeaderEntry> metricHeaders = header.getMetricHeader().getMetricHeaderEntries();
                    List<ReportRow> rows = report.getData().getRows();

                    if (rows != null) {
                        for (ReportRow row : rows) {
                            List<String> dimensions = row.getDimensions();
                            List<DateRangeValues> metrics = row.getMetrics();

                            GenericValue product = null;
                            for (int i = 0; i < dimensionHeaders.size() && i < dimensions.size(); i++) {
                                //System.out.println(dimensionHeaders.get(i) + ": " + dimensions.get(i));
                                product = delegator.makeValidValue("ProductStatistic");
                                product.set("productId", dimensions.get(i));
                            }

                            for (int j = 0; j < metrics.size(); j++) {
                                DateRangeValues values = metrics.get(j);
                                for (int k = 0; k < values.getValues().size() && k < metricHeaders.size(); k++) {
                                    //System.out.println(metricHeaders.get(k).getName() + ": " + values.getValues().get(k));
                                    switch (metricHeaders.get(k).getName()) {
                                        case "ga:productAddsToCart":
                                            product.set("addedToCart", Long.valueOf(values.getValues().get(k)));
                                            break;
                                        case "ga:productCheckouts":
                                            product.set("purchased", Long.valueOf(values.getValues().get(k)));
                                            break;
                                        case "ga:productDetailViews":
                                            product.set("views", Long.valueOf(values.getValues().get(k)));
                                            break;
                                        case "ga:quantityCheckedOut":
                                            product.set("quantityPurchased", Long.valueOf(values.getValues().get(k)));
                                            break;
                                        case "ga:itemRevenue":
                                            product.set("revenue", new BigDecimal(values.getValues().get(k)));
                                            break;
                                        case "ga:buyToDetailRate":
                                            product.set("conversionRate", new BigDecimal(values.getValues().get(k)));
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }

                            try {
                                if (UtilValidate.isNotEmpty(product)) {
                                    delegator.createOrStore(product);
                                }
                            } catch (Exception gv) {
                                Debug.logError(gv, "Error trying to store stats for: " + product.getString("productId"), module);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            //EnvUtil.reportError(e);
            Debug.logError(e, "Error trying to store stats report.", module);
        }
    }
}