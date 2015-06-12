package com.rimi.ctibet.web.dao.daoimpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.ListUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.domain.MemberEnrollDetail;
import com.rimi.ctibet.web.controller.vo.MemberEnrollDetailVO;
import com.rimi.ctibet.web.dao.IMemberEnrollDetailDao;

@Repository("memberEnrollDetailDao")
public class MemberEnrollDetailDaoImpl extends BaseDaoImpl<MemberEnrollDetail> implements IMemberEnrollDetailDao {
	
	/**
	 * 获取最新报名活动会员列表
	 * @param isTop 是否按置顶数据排序
	 * @return
	 */
	public List<MemberEnrollDetailVO> getNewEnrollMemberByActivityCodeRow(String activityCode, int row, boolean isTop){
	    List<Object> params = new ArrayList<Object>();
	    StringBuffer sql = new StringBuffer();
		sql.append(" ");
		sql.append(" SELECT  ");
		sql.append(" 	 a.code as activityCode, a.name as activityName, ");
		sql.append("     m.code as memberCode, m.name as memberName,m.sex,m.pic, ");
		sql.append("     p.createTime as enrollTime ");
		sql.append(" FROM activity a ");
		sql.append(" INNER JOIN( ");
		sql.append(" 	SELECT activityCode,memberCode,min(createTime) AS createTime,max(isTop) AS isTop ");
		sql.append(" 	FROM  member_enroll_detail where avaliable=1 ");
		sql.append(" 	GROUP BY activityCode,memberCode ");
		sql.append(" ) p ON a.code=p.activityCode ");
		sql.append(" INNER JOIN v_member_detail m ON p.memberCode=m.code AND m.avaliable=1 ");
		sql.append(" WHERE a.avaliable=1 ");
		if(StringUtil.isNotNull(activityCode)){
			sql.append(" and a.code=? ");
			params.add(activityCode);
		}
		//排序
		sql.append(" ORDER BY ");
		if(isTop){
			sql.append(" p.isTop DESC, ");
		}
		sql.append(" enrollTime DESC ");
		return findListBySqlRow(MemberEnrollDetailVO.class, row, sql.toString(), params);
	}
	
	/**
	 * 获取最新报名活动会员列表 分页
	 * @param isTop 是否按置顶数据排序
	 * @return
	 */
	public Pager getNewEnrollMemberByActivityCode(Pager pager, String activityCode, String orderChannelSourceCode, boolean isTop){
	    List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" ");
		sql.append(" SELECT  ");
		sql.append(" 	 a.code as activityCode, a.name as activityName, ");
		sql.append(" 	 a.isEnrollPay, CASE WHEN a.isEnrollPay=0 THEN '否' WHEN a.isEnrollPay=1 THEN '是' end AS isEnrollPayName, ");
		sql.append("     m.code as memberCode, m.name as memberName,m.sex, ");
		sql.append("     p.createTime as enrollTime, p.isTop, ");
		sql.append("     o.payState, CASE WHEN o.payState=0 THEN '未付款' WHEN o.payState=1 THEN '已付款' end AS payStateName, ");
		sql.append("     o.orderChannelSourceCode, CASE WHEN o.orderChannelSourceCode = '-1' THEN '官方网站' ELSE ocs.name   END AS orderChannelSourceName ");
		sql.append(" FROM activity a ");
		sql.append(" INNER JOIN( ");
		sql.append(" 	SELECT activityCode,memberCode,min(createTime) AS createTime,max(isTop) AS isTop ");
		sql.append(" 	FROM  member_enroll_detail WHERE avaliable=1 ");
		sql.append(" 	GROUP BY activityCode,memberCode ");
		sql.append(" ) p ON a.code=p.activityCode ");
		sql.append(" INNER JOIN v_member_detail m ON p.memberCode=m.code AND m.avaliable=1 ");
		//sql.append(" LEFT JOIN t_order o ON a.code=o.activityCode AND m.code=o.memberCode AND o.avaliable=1 ");
		sql.append(" LEFT JOIN v_new_order o ON a.code=o.activityCode AND m.code=o.memberCode AND o.avaliable=1 ");
		
		sql.append(" LEFT JOIN order_channel_source ocs ON ocs.code=o.orderChannelSourceCode AND ocs.avaliable=1 ");
		
		sql.append(" WHERE a.avaliable=1 ");
		if(StringUtil.isNotNull(activityCode)){
			sql.append(" and a.code=? ");
			params.add(activityCode);
		}
		if(StringUtil.isNotNull(orderChannelSourceCode)){
		    sql.append(" and o.orderChannelSourceCode=? ");
		    params.add(orderChannelSourceCode);
		}
		//排序
		sql.append(" ORDER BY ");
		if(isTop){
			sql.append(" p.isTop DESC, ");
		}
		sql.append(" enrollTime DESC ");
		if(pager.getPageSize()==-1){
		    //全部数据
		    pager.setDataList(findListBySql(MemberEnrollDetailVO.class, sql.toString(),params));
		    return pager;
		}else{
		    return findListPagerBySql(MemberEnrollDetailVO.class, pager, sql.toString(),params);
		}
	}
	
	/**
	 * 通过活动code和会员code获取报名信息
	 * @param activityCode
	 * @param memberCode
	 * @return
	 */
	public List<MemberEnrollDetailVO> getMemberEnrollDetailByActCodeMemberCode(String activityCode, String memberCode){
		StringBuffer sql = new StringBuffer();
		sql.append("  ");
		sql.append(" SELECT ");
		sql.append(" 	ef.property,ef.propertyType, ");
		sql.append("    ed.code,ed.propertyValue,ed.fileName ");
		sql.append(" FROM enroll_form ef, member_enroll_detail ed ");
		sql.append(" WHERE ef.code=ed.enrollFormCode AND ed.avaliable=1 ");
		sql.append(" AND ed.activityCode=? ");
		sql.append(" AND ed.memberCode=? ");
		return findListBySql(MemberEnrollDetailVO.class, sql.toString(), new Object[]{activityCode, memberCode});
	}
	
	/**
	 * 通过活动code和会员code 删除报名信息
	 * @param activityCode
	 * @param memberCode
	 */
	public void deleteMemberEnrollDetailByActCodeMemberCode(final String activityCode, final String memberCode){
		HibernateCallback<Object> hibernateCallback = new HibernateCallback<Object>(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = " UPDATE member_enroll_detail SET avaliable=0 WHERE activityCode=:activityCode AND memberCode=:memberCode ";
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				sqlQuery.setParameter("activityCode", activityCode);
				sqlQuery.setParameter("memberCode", memberCode);
				sqlQuery.executeUpdate();
				return null;
			}
		};
		getHibernateTemplate().execute(hibernateCallback);
	}
	
	/**
	 * 通过活动code和会员code 置顶
	 * @param activityCode
	 * @param memberCode
	 */
	public void updateMemberEnrollDetailIsTop(final String activityCode, final String memberCode, final int isTop){
		HibernateCallback<Object> hibernateCallback = new HibernateCallback<Object>(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = " UPDATE member_enroll_detail SET isTop=:isTop WHERE memberCode=:memberCode AND activityCode=:activityCode ";
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				sqlQuery.setParameter("isTop", isTop);
				sqlQuery.setParameter("activityCode", activityCode);
				sqlQuery.setParameter("memberCode", memberCode);
				sqlQuery.executeUpdate();
				return null;
			}
		};
		getHibernateTemplate().execute(hibernateCallback);
	}

    @Override
    public MemberEnrollDetail getMemberEnrollDetailByActCodePropertyCodeMemberCode(String activityCode, String propertyCode, String memberCode) {
        String sql = " SELECT * FROM member_enroll_detail WHERE avaliable=1 AND activityCode=? AND enrollFormCode=? AND memberCode=? ";
        return ListUtil.returnSingle(findListBySql(MemberEnrollDetail.class, sql, new Object[]{activityCode, propertyCode, memberCode}));
    }
}
