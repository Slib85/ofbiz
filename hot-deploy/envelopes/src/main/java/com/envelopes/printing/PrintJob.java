package com.envelopes.printing;

import java.util.Date;
import java.util.List;

/**
 * Created by Manu on 9/2/2016.
 */
public interface PrintJob {
    long getJobId();

    void setJobId(long jobId);

    double getArtHeight();

    void setArtHeight(double artHeight);

    double getArtWidth();

    void setArtWidth(double artWidth);

    boolean isTwoSided();

    void setTwoSided(boolean twoSided);

    boolean isReprint();

    void setReprint(boolean reprint);

    String getReprintReason();

    void setReprintReason(String reprintReason);

    long getOtherSideJobId();

    void setOtherSideJobId(long otherSideJobId);

    String getComments();

    void setComments(String comments);

    boolean isHeavyInkCoverage();

    void setHeavyInkCoverage(boolean heavyInkCoverage);

    boolean isRushJob();

    void setRushJob(boolean rushJob);

    Date getJobDueDate();

    void setJobDueDate(Date jobDueDate);

    Press getPreferredPress();

    void setPreferredPress(Press preferredPress);

    Press getAssignedPress();

    void setAssignedPress(Press assignedPress);

    JobItemDetails getItemDetails();

    void setItemDetails(JobItemDetails itemDetails);

    List<InkColor> getInkColors();

    void setInkColors(List<InkColor> inkColors);

    List<PrintJob> getRelatedJobs();

    void setRelatedJobs(List<PrintJob> relatedJobs);
}
