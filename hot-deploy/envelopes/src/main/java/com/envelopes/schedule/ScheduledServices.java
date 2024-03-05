/*******************************************************************************
 * Envelopes.com
 *******************************************************************************/
package com.envelopes.schedule;

import java.util.Map;

import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericDataSourceException;
import org.apache.ofbiz.entity.jdbc.SQLProcessor;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;

import com.envelopes.util.*;

public class ScheduledServices {
	public static final String module = ScheduledServices.class.getName();

	/**
	 * Clean the job sandbox
	 * @param dctx
	 * @param context
	 * @return
	 */
	public static Map<String, Object> cleanJobSandbox(DispatchContext dctx, Map<String, ? extends Object> context) {
		LocalDispatcher dispatcher = (LocalDispatcher) dctx.getDispatcher();
		Delegator delegator = dctx.getDelegator();

		Map<String, Object> result = ServiceUtil.returnSuccess();

		Debug.logInfo("JobSandbox cleaning process started.", module);

		SQLProcessor sqlProcessor = null;
		try {
			sqlProcessor = new SQLProcessor(delegator, delegator.getGroupHelperInfo("org.apache.ofbiz"));

			//clear out job sandbox and related entities
			Debug.logInfo("Removing finished/failed/crashed jobs.", module);
			String sqlCommand = "delete from job_sandbox where status_id in ('SERVICE_FINISHED','SERVICE_FAILED','SERVICE_CRASHED')";
			sqlProcessor.prepareStatement(sqlCommand);
			int rows = sqlProcessor.executeUpdate();

			Debug.logInfo("Removing stale runtime data.", module);
			sqlCommand = "delete from runtime_data where runtime_data_id not in (select distinct runtime_data_id from job_sandbox where runtime_data_id is not null)";
			sqlProcessor.prepareStatement(sqlCommand);
			rows = sqlProcessor.executeUpdate();

			Debug.logInfo("Removing stale recurrence info.", module);
			sqlCommand = "delete from recurrence_info where recurrence_info_id not in (select distinct recurrence_info_id from job_sandbox where recurrence_info_id is not null)";
			sqlProcessor.prepareStatement(sqlCommand);
			rows = sqlProcessor.executeUpdate();

			Debug.logInfo("Removing stale recurrence rule.", module);
			sqlCommand = "delete from recurrence_rule where recurrence_rule_id not in (select recurrence_rule_id from recurrence_info where recurrence_info_id in (select distinct recurrence_info_id from job_sandbox where recurrence_info_id is not null))";
			sqlProcessor.prepareStatement(sqlCommand);
			rows = sqlProcessor.executeUpdate();

			/*
			//retrieve all finished, failed and crashed jobs
			Debug.logInfo("Removing finished jobs.", module);
			delegator.removeAll(EntityQuery.use(delegator).from("JobSandbox").where("statusId", "SERVICE_FINISHED").queryList());

			Debug.logInfo("Removing failed jobs.", module);
			delegator.removeAll(EntityQuery.use(delegator).from("JobSandbox").where("statusId", "SERVICE_FAILED").queryList());

			Debug.logInfo("Removing crashed jobs.", module);
			delegator.removeAll(EntityQuery.use(delegator).from("JobSandbox").where("statusId", "SERVICE_CRASHED").queryList());

			//remaining jobs
			List<GenericValue> remainingJobs = EntityQuery.use(delegator).from("JobSandbox").queryList();

			//retrieve all runtimeDataIds from remaining jobs
			List<String> runtimeDataIds = EntityUtil.getFieldListFromEntityList(remainingJobs, "runtimeDataId", true);
			//retrieve all recurrenceInfoIds from remaining jobs
			List<String> recurrenceInfoIds = EntityUtil.getFieldListFromEntityList(remainingJobs, "recurrenceInfoId", true);
			//retrieve all recurrenceRuleIds from remaining RecurrenceInfos
			List<String> recurrenceRuleIds = EntityUtil.getFieldListFromEntityList(EntityQuery.use(delegator).from("RecurrenceInfo").where(EntityCondition.makeCondition("recurrenceInfoId", EntityOperator.IN, recurrenceInfoIds)).queryList(), "recurrenceRuleId", true);


			Debug.logInfo("Removing stale runtime data.", module);
			if(UtilValidate.isNotEmpty(runtimeDataIds)) {
				delegator.removeAll(EntityQuery.use(delegator).from("RuntimeData").where(EntityCondition.makeCondition("runtimeDataId", EntityOperator.NOT_IN, runtimeDataIds)).queryList());
			} else {
				delegator.removeAll(EntityQuery.use(delegator).from("RuntimeData").queryList());
			}

			Debug.logInfo("Removing stale recurrence info.", module);
			if(UtilValidate.isNotEmpty(recurrenceInfoIds)) {
				delegator.removeAll(EntityQuery.use(delegator).from("RecurrenceInfo").where(EntityCondition.makeCondition("recurrenceInfoId", EntityOperator.NOT_IN, recurrenceInfoIds)).queryList());
			} else {
				delegator.removeAll(EntityQuery.use(delegator).from("RecurrenceInfo").queryList());
			}

			Debug.logInfo("Removing stale recurrence rule.", module);
			if(UtilValidate.isNotEmpty(recurrenceRuleIds)) {
				delegator.removeAll(EntityQuery.use(delegator).from("RecurrenceRule").where(EntityCondition.makeCondition("recurrenceRuleId", EntityOperator.NOT_IN, recurrenceRuleIds)).queryList());
			} else {
				delegator.removeAll(EntityQuery.use(delegator).from("RecurrenceRule").queryList());
			}*/
		} catch(Exception e) {
			EnvUtil.reportError(e);
			Debug.logError(e, "An error occurred while trying to clean out job sandbox.", module);
		} finally {
			if(sqlProcessor != null) {
				try {
					sqlProcessor.close();
				} catch (GenericDataSourceException gdse) {
					Debug.logError(gdse, "An error occurred while trying to clean out job sandbox.", module);
				}
			}
		}

		return result;
	}
}