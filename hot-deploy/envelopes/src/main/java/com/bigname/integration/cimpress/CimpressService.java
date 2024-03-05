package com.bigname.integration.cimpress;

import com.bigname.core.logging.BlackBox;
import com.bigname.job.JobHelper;
import com.envelopes.util.EnvUtil;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;

import java.util.Map;

public class CimpressService {
    public static final String module = CimpressService.class.getName();

    public static Map<String, Object> getNewOrdersFromCimpress(DispatchContext dctx, Map<String, ? extends Object> context)  {
        String jobName = "Cimpress Order Import";
        Delegator delegator = dctx.getDelegator();
        LocalDispatcher dispatcher = dctx.getDispatcher();
        BlackBox logger = new BlackBox();
        boolean success = false;
        try {
            GenericValue job = JobHelper.startJob(delegator, jobName, logger);
            if(UtilValidate.isNotEmpty(job)) {
                Map<String, Object> result = CimpressHelper.getNewOrdersFromCimpress(delegator, dispatcher);
                BlackBox jobMessages = (BlackBox) result.get("logger");
                String importStatus = (String) result.get("importStatus");
                if((boolean) result.get("success")) {
                    JobHelper.finishJob(delegator, job, importStatus, jobMessages, logger);
                    logger.info(jobName + " completed");
                    success = true;
                } else {
                    JobHelper.finishJob(delegator, job, "FAILED", jobMessages, logger);
                    logger.error("New Cimpress order import failed");
                }
            } else {
                logger.error("Can't start the job - " + jobName);
            }

        } catch (Exception e) {
            String message ="An error occurred while running the getNewOrdersFromCimpress service";
            logger.error(message);
            EnvUtil.reportError(e);
            Debug.logError(message + ":" + e, module);
        }
        logger.print();
        return success ? ServiceUtil.returnSuccess() : ServiceUtil.returnFailure();

    }

    public static Map<String, Object> getCancelledOrdersFromCimpress(DispatchContext dctx, Map<String, ? extends Object> context) {
        String jobName = "Cimpress Order Cancellation Import";
        Delegator delegator = dctx.getDelegator();
        LocalDispatcher dispatcher = dctx.getDispatcher();
        BlackBox logger = new BlackBox();
        boolean success = false;

        try {
            GenericValue job = JobHelper.startJob(delegator, jobName, logger);
            if(UtilValidate.isNotEmpty(job)) {
                Map<String, Object> result = CimpressHelper.getCancelledOrdersFromCimpress(delegator, dispatcher);
                BlackBox jobMessages = (BlackBox) result.get("logger");
                String importStatus = (String) result.get("importStatus");
                if((boolean) result.get("success")) {
                    JobHelper.finishJob(delegator, job, importStatus, jobMessages, logger);
                    logger.info(jobName + " completed");
                    success = true;
                } else {
                    JobHelper.finishJob(delegator, job, "FAILED", jobMessages, logger);
                    logger.error("Cimpress order cancellation import failed");
                }
            } else {
                logger.error("Can't start the job - " + jobName);
            }

        } catch (Exception e) {
            String message = "An error occurred while running the getCancelledOrdersFromCimpress service";
            logger.error(message);
            EnvUtil.reportError(e);
            Debug.logError(message + ":" + e, module);
        }
        logger.print();
        return success ? ServiceUtil.returnSuccess() : ServiceUtil.returnFailure();
    }

    public static Map<String, Object> getShippingCarrierChangesFromCimpress(DispatchContext dctx, Map<String, ? extends Object> context) {
        String jobName = "Cimpress Shipping Carrier Modification Import";
        Delegator delegator = dctx.getDelegator();
        LocalDispatcher dispatcher = dctx.getDispatcher();
        BlackBox logger = new BlackBox();
        boolean success = false;

        try {
            GenericValue job = JobHelper.startJob(delegator, jobName, logger);
            if(UtilValidate.isNotEmpty(job)) {
                Map<String, Object> result = CimpressHelper.getShippingCarrierChangesFromCimpress(delegator, dispatcher);
                BlackBox jobMessages = (BlackBox) result.get("logger");
                String importStatus = (String) result.get("importStatus");
                if((boolean) result.get("success")) {
                    JobHelper.finishJob(delegator, job, importStatus, jobMessages, logger);
                    logger.info(jobName + " completed");
                    success = true;
                } else {
                    JobHelper.finishJob(delegator, job, "FAILED", jobMessages, logger);
                    logger.error("Cimpress shipping carrier modification import failed");
                }
            } else {
                logger.error("Can't start the job - " + jobName);
            }

        } catch (Exception e) {
            String message ="An error occurred while running the getShippingCarrierChangesFromCimpress service";
            logger.error(message);
            EnvUtil.reportError(e);
            Debug.logError(message + ":" + e, module);
        }
        logger.print();
        return success ? ServiceUtil.returnSuccess() : ServiceUtil.returnFailure();
    }

    public static Map<String, Object> getAddressChangesFromCimpress(DispatchContext dctx, Map<String, ? extends Object> context) {
        String jobName = "Cimpress Address Modification Import";
        Delegator delegator = dctx.getDelegator();
        LocalDispatcher dispatcher = dctx.getDispatcher();
        BlackBox logger = new BlackBox();
        boolean success = false;

        try {
            GenericValue job = JobHelper.startJob(delegator, jobName, logger);
            if(UtilValidate.isNotEmpty(job)) {
                Map<String, Object> result = CimpressHelper.getAddressChangesFromCimpress(delegator, dispatcher);
                BlackBox jobMessages = (BlackBox) result.get("logger");
                String importStatus = (String) result.get("importStatus");
                if((boolean) result.get("success")) {
                    JobHelper.finishJob(delegator, job, importStatus, jobMessages, logger);
                    logger.info(jobName + " completed");
                    success = true;
                } else {
                    JobHelper.finishJob(delegator, job, "FAILED", jobMessages, logger);
                    logger.error("Cimpress address modification import failed");
                }
            } else {
                logger.error("Can't start the job - " + jobName);
            }

        } catch (Exception e) {
            String message = "An error occurred while running the getAddressChangesFromCimpress service";
            logger.error(message);
            EnvUtil.reportError(e);
            Debug.logError(message + ":" + e, module);
        }
        logger.print();
        return success ? ServiceUtil.returnSuccess() : ServiceUtil.returnFailure();
    }

    public static Map<String, Object> setProductionStarted(DispatchContext dctx, Map<String, ? extends Object> context) {
        Delegator delegator = dctx.getDelegator();
        LocalDispatcher dispatcher = dctx.getDispatcher();

        Map<String, Object> result = ServiceUtil.returnSuccess();
        CimpressHelper.setProductionStarted(delegator, dispatcher, context);
        return result;
    }

    public static Map<String, Object> createShipment(DispatchContext dctx, Map<String, ? extends Object> context) {
        Delegator delegator = dctx.getDelegator();
        LocalDispatcher dispatcher = dctx.getDispatcher();

        Map<String, Object> result = ServiceUtil.returnSuccess();
        CimpressHelper.createShipment(delegator, dispatcher, context);
        return result;
    }
}
