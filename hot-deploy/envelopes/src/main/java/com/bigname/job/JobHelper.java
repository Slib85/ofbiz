package com.bigname.job;

import com.bigname.core.logging.BlackBox;
import com.google.gson.Gson;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericEntityException;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;
import org.apache.ofbiz.entity.jdbc.SQLProcessor;
import org.apache.ofbiz.entity.transaction.TransactionUtil;
import org.apache.ofbiz.entity.util.EntityQuery;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Manu on 6/11/2018.
 */
public class JobHelper {

    private static GenericValue getJob(Delegator delegator, String jobName) throws GenericEntityException {
        GenericValue jobGV = EntityQuery.use(delegator).from("BignameJob").where("jobName", jobName).queryFirst();
        if(UtilValidate.isEmpty(jobGV)) {
            jobGV = delegator.makeValue("BignameJob", "jobName", jobName, "jobId", getNextJobId(delegator), "runId", Long.valueOf(0));
            jobGV.put("statusId", "READY");
            delegator.create(jobGV);
        }
        return jobGV;
    }

    private static long getNextJobId(Delegator delegator) throws GenericEntityException {
        GenericValue jobGV = EntityQuery.use(delegator).select("jobId").from("BignameJob").orderBy("jobId DESC").queryFirst();
        if(UtilValidate.isEmpty(jobGV)) {
            return 1;
        } else {
            return jobGV.getLong("jobId") + 1;
        }
    }

    private static long getNextRunId(Delegator delegator, long jobId) throws GenericEntityException {
        GenericValue jobGV = EntityQuery.use(delegator).select("runId").from("BignameJob").where("jobId", jobId).orderBy("runId DESC").queryFirst();
        if(UtilValidate.isEmpty(jobGV)) {
            return 1;
        } else {
            return jobGV.getLong("runId") + 1;
        }
    }

    public static GenericValue startJob(Delegator delegator, String jobName, BlackBox logger) throws GenericEntityException {
        GenericValue job = getJob(delegator, jobName);
        if("RUNNING".equals(job.getString("statusId"))) {
            logger.error("The job '" + jobName + "' is already running since " + job.getTimestamp("lastStartedTimestamp") + " and cannot be started until it finishes");
            return null;
        } else if("SUSPENDED".equals(job.getString("statusId"))) {
            logger.error("The job '" + jobName + "' is SUSPENDED and cannot be started until its READY");
            return null;
        } else {
            job.put("runId", getNextRunId(delegator, job.getLong("jobId")));
            job.put("statusId", "RUNNING");
            job.put("lastStartedTimestamp", UtilDateTime.nowTimestamp());
            delegator.store(job);
            TransactionUtil.commit();
            return job;
        }
    }

    public static boolean finishJob(Delegator delegator, GenericValue job, String statusId, BlackBox jobMessages, BlackBox logger) throws Exception {
        if(!"RUNNING".equals(job.getString("statusId"))) {
            logger.error("The job '" + job.getString("jobName") + "' is not running");
            return false;
        } else {
            job.put("statusId", statusId);
            job.put("lastFinishedTimestamp", UtilDateTime.nowTimestamp());
            delegator.store(job);
            TransactionUtil.commit();
            saveMessages(delegator, job, jobMessages, logger);
            return true;
        }
    }

    private static boolean saveMessages(Delegator delegator, GenericValue job, BlackBox messages, BlackBox logger) throws Exception {
        SQLProcessor sqlProcessor = new SQLProcessor(delegator, delegator.getGroupHelperInfo("org.apache.ofbiz"));
        Timestamp ts = UtilDateTime.nowTimestamp();
        String INSERT = "INSERT INTO bigname_job_message (message_id, job_id, run_id, message, `level`, sequence_num, message_timestamp, last_updated_stamp, last_updated_tx_stamp, created_stamp, created_tx_stamp) VALUES (? , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = sqlProcessor.getConnection().prepareStatement(INSERT)) {
            List<Map<String, Object>> messageList = messages.toList();
            messageList.forEach((Map<String, Object> e) -> {

                try {
                    statement.setString(1, delegator.getNextSeqId("BignameJobMessage"));
                    statement.setLong(2, (long) job.get("jobId"));
                    statement.setLong(3, (long) job.get("runId"));
                    statement.setString(4, (String) e.get("message"));
                    statement.setString(5, (e.get("level")).toString());
                    statement.setLong(6, (long) e.get("idx"));
                    statement.setTimestamp(7, new Timestamp(((Date)e.get("timestamp")).getTime()));
                    statement.setTimestamp(8, ts);
                    statement.setTimestamp(9, ts);
                    statement.setTimestamp(10, ts);
                    statement.setTimestamp(11, ts);
                    statement.addBatch();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            });
            statement.executeBatch();
            TransactionUtil.commit();
        }finally{
            if(sqlProcessor != null) {
                sqlProcessor.close();
            }
        }
        return true;
    }

    public static List<Map<String, Object>> getJobs(Delegator delegator) throws Exception {
        List<Map<String, Object>> jobs = new ArrayList<>();
        List<GenericValue> jobGVs = EntityQuery.use(delegator).from("BignameJob").orderBy("jobName").queryList();
        jobGVs.forEach(e -> {
            Map<String, Object> job = new HashMap<>();
            job.put("jobId", e.getLong("jobId"));
            job.put("jobName", e.getString("jobName"));
            job.put("runId", e.getLong("runId"));
            job.put("jobStartTime", e.getTimestamp("lastStartedTimestamp"));
            job.put("jobEndTime", e.getTimestamp("lastFinishedTimestamp"));
            job.put("jobStatus", e.getString("statusId"));
            jobs.add(job);
        });
        return jobs;
    }

    public static Map<String, Object> getJobMessages(Delegator delegator, String runId, String jobId) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss,SSS a");

        Map<String, Object> messageMap = new HashMap<>();

        List<Map<String, Object>> messages = new ArrayList<>();
        List<EntityCondition> conditionList = new ArrayList<>();
        conditionList.add(EntityCondition.makeCondition("level", EntityOperator.NOT_EQUAL, "SUMMARY"));
        conditionList.add(EntityCondition.makeCondition("runId", EntityOperator.EQUALS, Long.parseLong(runId)));
        conditionList.add(EntityCondition.makeCondition("jobId", EntityOperator.EQUALS, Long.parseLong(jobId)));
        List<GenericValue> messageGVs = EntityQuery.use(delegator).from("BignameJobMessage").where(conditionList).orderBy("messageId").queryList();
        messageGVs.forEach(e -> {
            Map<String, Object> message = new HashMap<>();
            message.put("messageId", e.getLong("messageId"));
            message.put("message", e.getString("message"));
            message.put("level", e.getString("level"));
            message.put("sequenceNum", e.getLong("sequenceNum"));
            message.put("messageTimestamp", sdf.format(e.getTimestamp("messageTimestamp")));
            messages.add(message);
        });
        List<EntityCondition> conditionList2 = new ArrayList<>();
        conditionList2.add(EntityCondition.makeCondition("level", EntityOperator.EQUALS, "SUMMARY"));
        conditionList2.add(EntityCondition.makeCondition("runId", EntityOperator.EQUALS, Long.parseLong(runId)));
        conditionList2.add(EntityCondition.makeCondition("jobId", EntityOperator.EQUALS, Long.parseLong(jobId)));
        List<GenericValue> messageGVs2 = EntityQuery.use(delegator).from("BignameJobMessage").where(conditionList2).orderBy("messageId").queryList();
        messageGVs2.forEach(e -> {
            Map<String, Object> message = new HashMap<>();
            message.put("messageId", e.getLong("messageId"));
            message.put("message", e.getString("message"));
            message.put("level", e.getString("level"));
            message.put("sequenceNum", e.getLong("sequenceNum"));
            message.put("messageTimestamp", sdf.format(e.getTimestamp("messageTimestamp")));
            messages.add(message);
        });
        messageMap.put("messages", messages);
        long[] runIds = getRunIds(delegator, Long.parseLong(runId), Long.parseLong(jobId));
        messageMap.put("nextRunId", runIds[0]);
        messageMap.put("previousRunId", runIds[1]);
        return messageMap;
    }

    public static long[] getRunIds(Delegator delegator, long runId, long jobId) throws Exception {
        long next = -1, previous = -1;
        List<GenericValue> runIds = EntityQuery.use(delegator).select("runId").from("BignameJobMessage").distinct().where("jobId", jobId).orderBy("runId DESC").queryList();
        for (int i = 0; i < runIds.size(); i++) {
            long currentRunId = runIds.get(i).getLong("runId");
            next = i > 0 ? runIds.get(i-1).getLong("runId") : -1;
            previous = i < runIds.size()-1 ? runIds.get(i+1).getLong("runId") : -1;
            if (currentRunId == runId) {
                break;
            }
        }
        return new long[]{next, previous};
    }

 }
