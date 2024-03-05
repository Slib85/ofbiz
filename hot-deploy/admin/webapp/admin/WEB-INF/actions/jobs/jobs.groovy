import com.bigname.job.JobHelper;
import java.util.*;

String module = "jobs.groovy";

List<Map<String, Object>> jobs = JobHelper.getJobs(delegator);
context.put("jobs", jobs);
