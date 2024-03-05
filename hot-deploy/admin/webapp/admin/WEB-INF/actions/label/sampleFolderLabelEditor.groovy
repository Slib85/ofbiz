import com.envelopes.label.*;

Object result = LabelPrintHelper.getJobData(delegator, dispatcher, request.getParameter("id"));

context.hasResult = result != null;
context.result = result;
context.jobNumber=request.getParameter("id");

