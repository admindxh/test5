package com.rimi.ctibet.domain;

public class ProgramaContent {
	
	private int id;
	// 栏目code
    private String proCode;
    // 内容 code
    private String conCode;
    
    public ProgramaContent() {}
    public ProgramaContent(int id, String proCode, String conCode) {
    	super();
    	this.id = id;
    	this.proCode = proCode;
    	this.conCode = conCode;
    }
	@Override
	public String toString() {
		return "ProgramaContent [id=" + id + ", proCode=" + proCode
				+ ", conCode=" + conCode + "]";
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
	public String getProCode() {
		return proCode;
	}
	public void setProCode(String proCode) {
		this.proCode = proCode;
	}
	public String getConCode() {
		return conCode;
	}
	public void setConCode(String conCode) {
		this.conCode = conCode;
	}
}
