package com.rimi.ctibet.web.dao.daoimpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Activity;
import com.rimi.ctibet.web.controller.vo.ActivetyTotalVo;
import com.rimi.ctibet.web.controller.vo.ActivityVO;
import com.rimi.ctibet.web.controller.vo.MemberEnrollDetailVO;
import com.rimi.ctibet.web.dao.IActivityDao;

@Repository("activityDao")
public class ActivityDaoImpl extends BaseDaoImpl<Activity> implements IActivityDao {
	
	/**
	 * 搜索活动信息管理列表
	 * @param name
	 * @param block
	 * @param isEnd
	 * @param orderby
	 * @return
	 */
	public Pager searchActivityByName(Pager pager, String name, int block, int isEnd, int orderby){
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");
		sql.append(" *, ");
		sql.append(" CASE WHEN DATE_FORMAT(NOW(), '%Y%m%d') < DATE_FORMAT(startDate, '%Y%m%d') THEN '未开始' WHEN date_format(now(),'%Y%m%d') <= date_format(endDate,'%Y%m%d') THEN '进行中' WHEN date_format(now(),'%Y%m%d') > date_format(endDate,'%Y%m%d') THEN '已结束' END AS activityState ");
		sql.append(" FROM activity WHERE avaliable=1 ");
		if(name!=null && !name.equals("")){
			sql.append(" AND name LIKE ? ");
			paramList.add("%"+name+"%");
		}
		if(block > 0){
			if(block==(Activity.isUploadName)){
				sql.append(" AND isUpload=1 ");
			}
			if(block==(Activity.isVoteName)){
				sql.append(" AND isVote=1 ");
			}
			if(block==(Activity.isEnrollName)){
				sql.append(" AND isEnroll=1 ");
			}
			if(block==(Activity.isEnrollPayName)){
				sql.append(" AND isEnrollPay=1 ");
			}
			if(block==(Activity.isPayName)){
				sql.append(" AND isPay=1 ");
			}
		}
		if(isEnd > 0){
		    if(isEnd==(Activity.ACTIVITYDATE_NOT_START)){
		        sql.append(" AND DATE_FORMAT(NOW(), '%Y%m%d') < DATE_FORMAT(startDate, '%Y%m%d') ");
		    }
			if(isEnd==(Activity.ACTIVITYDATE_START)){
				sql.append(" AND (date_format(now(),'%Y%m%d')<=date_format(endDate,'%Y%m%d') AND DATE_FORMAT(NOW(), '%Y%m%d') >= DATE_FORMAT(startDate, '%Y%m%d')) ");
			}
			if(isEnd==(Activity.ACTIVITYDATE_END)){
				sql.append(" AND date_format(now(),'%Y%m%d')>date_format(endDate,'%Y%m%d') ");
			}
		}
		if(orderby > 0){
			if(orderby==Activity.ORDERBY_DATE){
				sql.append(" ORDER BY createTime DESC ");
			}
			if(orderby==Activity.ORDERBY_ENROLLNUM){
				sql.append(" ORDER BY enrollNum DESC ");
			}
			if(orderby==Activity.ORDERBY_UPLOADNUM){
				sql.append(" ORDER BY uploadNum DESC ");
			}
			if(orderby==Activity.ORDERBY_CHECKNUM){
				sql.append(" ORDER BY fakeCheckNum DESC ");
			}
		}else{
			sql.append(" ORDER BY sortNum ASC, createTime DESC ");
		}
		
		return findListPagerBySql(ActivityVO.class, pager, sql.toString(), paramList);
	}
	
	@Override
	public Pager getAllActivityOrder(Pager pager, int orderby, boolean isShowNotBegin, boolean isShowNotOffical){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT");
		sql.append(" 	*,");
		sql.append(" 	CASE WHEN DATE_FORMAT(NOW(), '%Y%m%d') < DATE_FORMAT(startDate, '%Y%m%d') THEN '未开始' WHEN date_format(now(),'%Y%m%d') <= date_format(endDate,'%Y%m%d') THEN '进行中' WHEN date_format(now(),'%Y%m%d') > date_format(endDate,'%Y%m%d') THEN '已结束' END AS activityState ");
		sql.append(" FROM activity WHERE avaliable=1 ");
		if(!isShowNotBegin){
		    sql.append(" AND DATE_FORMAT(NOW(), '%Y%m%d') >= DATE_FORMAT(startDate, '%Y%m%d') ");
		}
		//是否显示非官网显示的数据（true显示 false不显示）
		if(!isShowNotOffical){
		    sql.append(" AND description = '1' ");
		}
		if(orderby > 0){
			if(orderby==Activity.ORDERBY_DATE){
				sql.append(" ORDER BY createTime DESC ");
			}
			if(orderby==Activity.ORDERBY_ENROLLNUM){
				sql.append(" ORDER BY enrollNum DESC ");
			}
			if(orderby==Activity.ORDERBY_UPLOADNUM){
				sql.append(" ORDER BY uploadNum DESC ");
			}
			if(orderby==Activity.ORDERBY_CHECKNUM){
				sql.append(" ORDER BY fakeCheckNum DESC ");
			}
		}else{
			sql.append(" ORDER BY activityState DESC, sortNum ASC, createTime DESC ");
		}
		return findListPagerBySql(ActivityVO.class, pager, sql.toString());
	}
	
	/**
	 * 通过活动code获取活动信息
	 * @param code
	 * @return
	 */
	public Activity getActivityByCode(String code){
		return findByCode(code);
	}
	
	/**
	 * 活动查看数+1
	 * @param activityCode
	 */
	public void activityCheckNumPlusOne(final String activityCode){
	    getHibernateTemplate().execute(new HibernateCallback<Object>(){
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
        		SQLQuery sqlQuery = session.createSQLQuery(" update activity set checkNum = checkNum + 1,fakeCheckNum = fakeCheckNum + 1 where code=:code ");
        		sqlQuery.setParameter("code", activityCode);
        		return sqlQuery.executeUpdate();
            }
        });
	}
	
	/**
	 * 指定字段 数量+1
	 * @param columns（字段，可以多个）
	 */
	public void updateActivityNumByColum(final String activityCode, final String[] columns){
		HibernateCallback<Object> hibernateCallback = new HibernateCallback<Object>(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer sql = new StringBuffer();
				sql.append(" UPDATE activity SET ");
				for (int i = 0; i < columns.length; i++) {
					String colum = columns[i];
					sql.append(colum).append("=").append(colum).append("+1");
					if(i<columns.length-1){
						sql.append(", ");
					}
				}
				sql.append(" WHERE code=:code AND avaliable=1 ");
				SQLQuery sqlQuery = session.createSQLQuery(sql.toString());
				sqlQuery.setParameter("code", activityCode);
				sqlQuery.executeUpdate();
				return null;
			}
		};
		getHibernateTemplate().execute(hibernateCallback);
	}

	@Override
	public ActivetyTotalVo getActivetyTotal(String start, String end) {
		// TODO Auto-generated method stub
		StringBuffer sb  = new StringBuffer();
		 sb.append(" select    sum(ac.checkNum)  as checkNum ,    (select count(DISTINCT med.memberCode))   as  acvisitCount , (''+ROUND(sum(td.money),2) ) as totalMoney ");
	 	 sb.append("   from activity  ac left join member_enroll_detail   med on  med.activityCode =    ac.`code`  and  med.memberCode is  not NULL    ");
		 sb.append("  	left  join   t_order   td  on   td.activityCode =   ac.code   and td.paystate = 1 ");
         sb.append("  where  ac.avaliable = 1        ");
         if (StringUtils.isNotBlank(start)) {
			 sb.append(" and date_format(ac.createTime,'%Y-%m-%d') >='"+start+"'");
		 }
         if (StringUtils.isNotBlank(end)) {
			 sb.append(" and date_format(ac.createTime,'%Y-%m-%d') <='"+end+"'");
		 }
         List<ActivetyTotalVo>  list  =  super.findListBySql(ActivetyTotalVo.class, sb.toString());
         final StringBuffer sb1 =  new StringBuffer();
 		sb1.append("select  IFNULL(sum((select count(distinct med.memberCode) from   member_enroll_detail med where  med.activityCode = ac.code  ) ),0)  ");
 		sb1.append("  ");
 		sb1.append(" from  activity   ac  ");
 		sb1.append("  where     ac.avaliable = 1   ");
 		if (StringUtils.isNotBlank(start)) {
 			 sb1.append("  and date_format(ac.createTime,'%Y-%m-%d') >='"+start+"'");
 		 }
         if (StringUtils.isNotBlank(end)) {
 			 sb1.append(" and date_format(ac.createTime,'%Y-%m-%d') <='"+end+"'");
 		 }
         Object activcount  =  this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
        	 	@Override
        	 	public Object doInHibernate(Session arg0) throws HibernateException,
        	 			SQLException {
        	 		// TODO Auto-generated method stub
        	 		return  arg0.createSQLQuery(sb1.toString()).uniqueResult();
        	 	}
         });
         ActivetyTotalVo  ac =list==null ?null:list.get(0) ;
         if (ac!=null) {
        	 	ac.setAcvisitCount(Integer.valueOf(activcount.toString()));
		}
         
         
		return    ac;
	 }
	/**
	 * 获取活动分页
	 */
	public Pager getActivetyTotalPager(String start,String end,Pager pager){
	    List<Object> params = new ArrayList<Object>();
		StringBuffer sb =  new StringBuffer();
		sb.append("select  ac.linkUrl as acurl,ac.`name`  as acname ,ac.checkNum as checkNum,(select count(distinct med.memberCode)  from   member_enroll_detail med where  med.activityCode = ac.code  )  as acvisitCount ,");
		sb.append(" (''+(select round(SUM(money),2)  as totalMoney from t_order  where t_order.activityCode = ac.code and t_order.payState=1) ) as totalMoney  ");
		sb.append(" from  activity   ac  ");
		sb.append("  where     ac.avaliable = 1   ");
		if (StringUtils.isNotBlank(start)) {
//		    sb.append("  and date_format(ac.createTime,'%Y-%m-%d') >='"+start+"'");
		    sb.append("  and date_format(ac.createTime,'%Y-%m-%d') >=? ");
		    params.add(start);
		}
        if (StringUtils.isNotBlank(end)) {
//            sb.append(" and date_format(ac.createTime,'%Y-%m-%d') <='"+end+"'");
            sb.append(" and date_format(ac.createTime,'%Y-%m-%d') <=? ");
            params.add(end);
        }
		return super.findListPagerBySql(ActivetyTotalVo.class, pager, sb.toString(), params);
	}

    @Override
    public List<MemberEnrollDetailVO> getJoinMemberList(int row) {
        StringBuffer sql = new StringBuffer();
        sql.append("  ");
        sql.append(" SELECT ");
        sql.append("     a.code as activityCode, a.name as activityName, ");
        sql.append("     m.code as memberCode, m.name as memberName,m.sex,m.pic, ");
        sql.append("     p.createTime as enrollTime, p.isTop ");
        sql.append(" FROM activity a ");
        sql.append(" INNER JOIN ( ");
        sql.append("     SELECT activityCode,memberCode,min(createTime) AS createTime,max(isTop) AS isTop ");
        sql.append("     FROM  member_enroll_detail WHERE avaliable = 1 ");
        sql.append("     GROUP BY activityCode,memberCode ");
        sql.append(" ) p ON a.code=p.activityCode ");
        sql.append(" INNER JOIN v_member_detail m ON p.memberCode=m.code AND m.avaliable=1 ");
        sql.append(" WHERE a.avaliable=1 ");
        sql.append(" UNION ");
        sql.append(" SELECT ");
        sql.append("     a.code as activityCode, a.name as activityName, ");
        sql.append("     m.code as memberCode, m.name as memberName,m.sex,m.pic, ");
        sql.append("     ap.createTime as enrollTime, ap.isTop ");
        sql.append(" FROM activity a  ");
        sql.append(" INNER JOIN ( ");
        sql.append("     SELECT activityCode,memberCode,max(createTime) as createTime,max(isTop) as isTop ");
        sql.append("     FROM activity_product ap WHERE avaliable=1  ");
        sql.append("     GROUP BY activityCode,memberCode ");
        sql.append(" ) ap ON a.code=ap.activityCode ");
        sql.append(" INNER JOIN v_member_detail m on ap.memberCode=m.code AND m.avaliable=1 ");
        sql.append(" WHERE a.avaliable=1 ");
        sql.append(" ORDER BY enrollTime DESC ");
        return findListBySqlRow(MemberEnrollDetailVO.class, row, sql.toString());
    }

    @Override
    public int getMaxNum() {
        return getJdbcTemplate().queryForObject(" SELECT max(sortNum) FROM activity WHERE avaliable=1 ", Integer.class);
    }

	
	
}
