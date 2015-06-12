package com.rimi.ctibet.domain;


public class StrategyView {
	private int id;
	// 内容code
    private String contentCode;
    // 景点code
    private String viewCode;
    // 目的地code
    private String destinationCode;
    
    public StrategyView(){}

	public StrategyView(int id, String contentCode, String viewCode,
			String destinationCode) {
		super();
		this.id = id;
		this.contentCode = contentCode;
		this.viewCode = viewCode;
		this.destinationCode = destinationCode;
	}

	@Override
	public String toString() {
		return "StrategyView [id=" + id + ", contentCode=" + contentCode
				+ ", viewCode=" + viewCode + ", destinationCode="
				+ destinationCode + "]";
	}
	
	/********************************************
	 * Setter Getter
	 ********************************************/
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContentCode() {
		return contentCode;
	}

	public void setContentCode(String contentCode) {
		this.contentCode = contentCode;
	}

	public String getViewCode() {
		return viewCode;
	}

	public void setViewCode(String viewCode) {
		this.viewCode = viewCode;
	}

	public String getDestinationCode() {
		return destinationCode;
	}

	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}
    
    
}
