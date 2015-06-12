package com.rimi.ctibet.domain;

public class ScoreManager {
	
	public static String type_savestrage = "savestrage";//发布攻略
	public static String type_hfandpl = "hfandpl";//回复和评论
	public static String type_savepost = "savepost";//发布帖子
	public static String type_replypost = "replypost";//回复帖子
	public static String type_posttop = "posttop";//置顶帖子
	
			
	private Integer id;
	private String scorename;
	private Integer scorecount;
	private Integer scoremax;
	private String scoreType;
	private Integer avaliable;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getScorename() {
		return scorename;
	}
	public void setScorename(String scorename) {
		this.scorename = scorename;
	}
	public Integer getScorecount() {
		return scorecount;
	}
	public void setScorecount(Integer scorecount) {
		this.scorecount = scorecount;
	}
	public String getScoreType() {
		return scoreType;
	}
	public void setScoreType(String scoreType) {
		this.scoreType = scoreType;
	}
    public Integer getScoremax() {
        return scoremax;
    }
    public void setScoremax(Integer scoremax) {
        this.scoremax = scoremax;
    }
	public Integer getAvaliable() {
		return avaliable;
	}
	public void setAvaliable(Integer avaliable) {
		this.avaliable = avaliable;
	}
	
}
