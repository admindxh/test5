package com.rimi.ctibet.portal.controller.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.rimi.ctibet.domain.Programa;

/**
 * 用于封装天上社区首页的栏目及其前三个帖子
 * @author xiaozhen
 */

public class TssqPortalPostVo {
    private Programa plate;
    private List<Map<String,Object>> posts = new ArrayList<Map<String,Object>>();
	public Programa getPlate() {
		return plate;
	}
	public void setPlate(Programa plate) {
		this.plate = plate;
	}
	public List<Map<String, Object>> getPosts() {
		return posts;
	}
	public void setPosts(List<Map<String, Object>> posts) {
		this.posts = posts;
	}
}
