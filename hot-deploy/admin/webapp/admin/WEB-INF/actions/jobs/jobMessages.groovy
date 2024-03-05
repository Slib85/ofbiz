import com.bigname.job.JobHelper;
import java.util.*;

String module = "jobMessages.groovy";

String runId = request.getParameter("runId");
String jobId = request.getParameter("jobId");
context.put("runId", runId);
context.put("jobId", jobId);
context.put("jobName", request.getParameter("jobName"));
Map<String, Object> messageMap = JobHelper.getJobMessages(delegator, runId, jobId);
List<Map<String, Object>> messages = (List<Map<String, Object>>) messageMap.get("messages");
context.put("messages", messages);
context.put("previousRunId", messageMap.get("previousRunId"));
context.put("nextRunId", messageMap.get("nextRunId"));
