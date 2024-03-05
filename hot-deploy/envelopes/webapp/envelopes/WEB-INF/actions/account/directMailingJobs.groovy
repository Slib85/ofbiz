import com.bigname.integration.directmailing.DirectMailingHelper;

String module = "directMailingJobs.groovy"
context.jobs = DirectMailingHelper.getSavedJobs(request);