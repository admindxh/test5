package com.rimi.ctibet.web.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.Member;
import com.rimi.ctibet.domain.MemberInfo;
import com.rimi.ctibet.web.controller.vo.MemberVO;

public interface IMemberDao extends BaseDao<Member> {
	//用户登录
	public List<LogUser> login(String username,String password);
	public Member findByCode(String code);
	public List<LogUser> findLogUserByCode(String code);
	//取积分最高的前五个
	public List<LogUser> getTopFive();
	//根据发帖数量排名（取前5）
	public List<LogUser> getTopFivByPcount();
	
	/**
	 *通过时间查询 返回数量 
	 */
	public  Integer getMemCountByTime(Date start,Date end);
	public List<LogUser> findListBysql2(String sql,List map);
	
	/**
	 * 通过会员code获取会员信息
	 */
	public MemberVO getMemberByMemberCode(String code);
	
}
