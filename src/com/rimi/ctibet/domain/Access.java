package com.rimi.ctibet.domain;

public class Access {
     private Integer id;
     private String code;
	 private String name;
	 private String summary;
	 private String remark;
	 public final static String ACCESSINFO = "accessInfo";
	 public Access(){}
	 public Access(Integer id, String code, String name, String summary,
			String remark) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.summary = summary;
		this.remark = remark;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	};
	 

	 
	
	 
}
