package com.rimi.ctibet.domain;

/**
 * 活动投票选项
 */
public class VoteOption {
	
	private int id;
    private String code;
    // 选项
    private String options;
    // 选项投票数
    private int counts;
    // 伪选项投票数
    private int fakeCounts;
    // 所属活动code
    private String activityCode;
    
    public VoteOption(){}
    
	@Override
	public String toString() {
		return "VoteOption [id=" + id + ", code=" + code + ", options="
				+ options + ", counts=" + counts + ", fakeCounts=" + fakeCounts
				+ ", activityCode=" + activityCode + "]";
	}

	public VoteOption(int id, String code, String options, int counts,
			int fakeCounts, String activityCode) {
		super();
		this.id = id;
		this.code = code;
		this.options = options;
		this.counts = counts;
		this.fakeCounts = fakeCounts;
		this.activityCode = activityCode;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	public int getCounts() {
		return counts;
	}
	public void setCounts(int counts) {
		this.counts = counts;
	}
	public String getActivityCode() {
		return activityCode;
	}
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
	public int getFakeCounts() {
		return fakeCounts;
	}
	public void setFakeCounts(int fakeCounts) {
		this.fakeCounts = fakeCounts;
	}
	
}
