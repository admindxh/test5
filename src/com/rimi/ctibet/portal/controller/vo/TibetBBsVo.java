package com.rimi.ctibet.portal.controller.vo;

import java.util.List;
import java.util.Map;

import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Programa;

/**
 * 天上社区帮助类
 * @author dengxh
 *
 */
public class TibetBBsVo {
	private  Programa  programa;//栏目对象
	private  String isChidren;//是否存在子栏目
	private  Map<String,List<Content>>  chidrenReplyList;//回复的帖子String(图片路径),和帖子list content
	private  List<Content> fatherReplyList;//父类帖子conteList;
	public Programa getPrograma() {
		return programa;
	}
	public void setPrograma(Programa programa) {
		this.programa = programa;
	}
	public String getIsChidren() {
		return isChidren;
	}
	public void setIsChidren(String isChidren) {
		this.isChidren = isChidren;
	}
	public Map<String, List<Content>> getChidrenReplyList() {
		return chidrenReplyList;
	}
	public void setChidrenReplyList(Map<String, List<Content>> chidrenReplyList) {
		this.chidrenReplyList = chidrenReplyList;
	}
	public List<Content> getFatherReplyList() {
		return fatherReplyList;
	}
	public void setFatherReplyList(List<Content> fatherReplyList) {
		this.fatherReplyList = fatherReplyList;
	}
	
	
}
