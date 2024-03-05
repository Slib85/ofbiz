<link href="<@ofbizContentUrl>/html/css/account/account.css</@ofbizContentUrl>" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/account/designs.css</@ofbizContentUrl>" rel="stylesheet" />

<div class="content account">
    <div class="content-breadcrumbs">
        <a href="<@ofbizUrl>/account</@ofbizUrl>">Your Account</a> > Saved Designs
    </div>
    <div class="container designs padding-xs">
        <div id="design-message" data-alert class="alert-box success radius margin-top-xxs hidden">Job has been successfully deleted.</div>
        <div id="design-error" data-alert class="alert-box alert radius margin-top-xxs hidden">An error occurred while deleting the job.</div>
        <div class="row design-list">
        <#list jobs as job>

            <div id="job-${job.jobNumber!}" class="padding-xxs margin-right-xxs margin-top-xxs left" style="min-width: 200px">
                JobNumber : ${job.jobNumber!}
                <div>
					<span class="jqs-name">
                    ${job.lastUpdatedStamp}
					</span>
                    <div>
                           <span>
                                <a href="javascript:void(0)" class="jqs-delete-design" data-job-number="${job.jobNumber}">delete</a>
                           </span>
                        <span>
                                <a href="${job.editUrl!}" class="jqs-edit-design" data-edit-url="${job.editUrl!}">edit</a>
                        </span>
                    </div>

                </div>
            </div>
        </#list>
        </div>
    </div>
</div>
<script>
    var deleteJobEndPoint = '<@ofbizUrl>/deleteDirectMailingJob</@ofbizUrl>';
</script>
<script src="<@ofbizContentUrl>/html/js/account/saved-direct-mailing-jobs.js</@ofbizContentUrl>"></script>