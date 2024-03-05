package com.envelopes.printing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Manu on 8/16/2016.
 */
public class PrintJobImpl implements PrintJob {
	private long jobId;

	private double artHeight;
	private double artWidth;
	private boolean twoSided;
	private boolean reprint;
	private String reprintReason;
	private long otherSideJobId;
	private boolean heavyInkCoverage;
	private boolean rushJob;
	private Date jobDueDate;
	private Press preferredPress;
	private Press assignedPress;
	private JobItemDetails itemDetails;
	private String comments;
	private List<InkColor> inkColors = new ArrayList<>();
	private List<PrintJob> relatedJobs = new ArrayList<>();

	public PrintJobImpl() {
	}

	public PrintJobImpl(double artHeight, double artWidth) {
		this.artHeight = artHeight;
		this.artWidth = artWidth;
	}

	@Override
	public long getJobId() {
		return jobId;
	}

	@Override
	public void setJobId(long jobId) {
		if(jobId == Long.MIN_VALUE) {
			this.jobId = jobId;
		}
	}

	@Override
	public double getArtHeight() {
		return artHeight;
	}

	@Override
	public void setArtHeight(double artHeight) {
		this.artHeight = artHeight;
	}

	@Override
	public double getArtWidth() {
		return artWidth;
	}

	@Override
	public void setArtWidth(double artWidth) {
		this.artWidth = artWidth;
	}

	public boolean isTwoSided() {
		return twoSided;
	}

	public void setTwoSided(boolean twoSided) {
		this.twoSided = twoSided;
	}

	public boolean isReprint() {
		return reprint;
	}

	public void setReprint(boolean reprint) {
		this.reprint = reprint;
	}

	public String getReprintReason() {
		return reprintReason;
	}

	public void setReprintReason(String reprintReason) {
		this.reprintReason = reprintReason;
	}

	public long getOtherSideJobId() {
		return otherSideJobId;
	}

	public void setOtherSideJobId(long otherSideJobId) {
		this.otherSideJobId = otherSideJobId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public boolean isHeavyInkCoverage() {
		return heavyInkCoverage;
	}

	@Override
	public void setHeavyInkCoverage(boolean heavyInkCoverage) {
		this.heavyInkCoverage = heavyInkCoverage;
	}

	@Override
	public boolean isRushJob() {
		return rushJob;
	}

	@Override
	public void setRushJob(boolean rushJob) {
		this.rushJob = rushJob;
	}

	@Override
	public Date getJobDueDate() {
		return jobDueDate;
	}

	@Override
	public void setJobDueDate(Date jobDueDate) {
		this.jobDueDate = jobDueDate;
	}

	@Override
	public Press getPreferredPress() {
		return preferredPress;
	}

	@Override
	public void setPreferredPress(Press preferredPress) {
		this.preferredPress = preferredPress;
	}

	@Override
	public Press getAssignedPress() {
		return assignedPress;
	}

	@Override
	public void setAssignedPress(Press assignedPress) {
		this.assignedPress = assignedPress;
	}

	@Override
	public JobItemDetails getItemDetails() {
		return itemDetails;
	}

	@Override
	public void setItemDetails(JobItemDetails itemDetails) {
		this.itemDetails = itemDetails;
		if(itemDetails.isRushItem() && !isRushJob()) {
			setRushJob(true);
		}
	}

	@Override
	public List<InkColor> getInkColors() {
		return inkColors;
	}

	@Override
	public void setInkColors(List<InkColor> inkColors) {
		this.inkColors = inkColors;
	}

	@Override
	public List<PrintJob> getRelatedJobs() {
		return relatedJobs;
	}

	@Override
	public void setRelatedJobs(List<PrintJob> relatedJobs) {
		this.relatedJobs = relatedJobs;
	}
}
