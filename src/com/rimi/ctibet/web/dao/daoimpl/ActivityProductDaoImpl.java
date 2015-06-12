package com.rimi.ctibet.web.dao.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.domain.ActivityProduct;
import com.rimi.ctibet.web.controller.vo.ActivityProductVO;
import com.rimi.ctibet.web.dao.IActivityProductDao;

@Repository("activityProductDao")
public class ActivityProductDaoImpl extends BaseDaoImpl<ActivityProduct> implements IActivityProductDao {

	/**
	 * 获取参赛作品列表
	 * @param activityCode 活动code
	 * @param isTop	是否按置顶排序
	 * @param state	审核状态
	 * @return
	 */
	public Pager getActivityProductsByActCodeIsTop(Pager pager, String activityCode, boolean isTop, int state){
	    List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("  ");
		sql.append(" SELECT ");
		sql.append(" 	 a.code AS activityCode, a.name AS activityName, ");
		sql.append("     m.code AS memberCode, m.name AS memberName,m.sex,m.pic, ");
		sql.append("     ap.code, ap.name, ap.url, ap.fileName, ap.likeNum, ap. fakeLikeNum, ap.createTime, ap.isTop, ");
		sql.append("     ap.state, case when ap.state=-1 then '未通过' when ap.state=0 then '待审核' when ap.state=1 then '已审核' end as stateName ");
		sql.append(" FROM activity_product ap , activity a, v_member_detail m  ");
		sql.append(" WHERE a.code=ap.activityCode AND ap.memberCode=m.code ");
		sql.append(" AND a.avaliable=1 AND m.avaliable=1 AND ap.avaliable=1 ");
		
		if(StringUtil.isNotNull(activityCode)){
			sql.append(" AND a.code=? ");
			params.add(activityCode);
		}
		if(state==-1 || state==0 || state==1){
			sql.append(" AND ap.state=? ");
			params.add(state);
		}
		sql.append(" ORDER BY ");
		if(isTop){
			sql.append(" ap.isTop DESC,  ");
		}
		sql.append("ap.createTime DESC ");
		return findListPagerBySql(ActivityProductVO.class, pager, sql.toString(), params);
	}
	
}
