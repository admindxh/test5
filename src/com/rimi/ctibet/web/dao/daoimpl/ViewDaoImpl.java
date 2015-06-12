package com.rimi.ctibet.web.dao.daoimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.domain.Destination;
import com.rimi.ctibet.domain.View;
import com.rimi.ctibet.web.dao.IViewDao;

@Repository("viewDao")
public class ViewDaoImpl extends BaseDaoImpl<View> implements IViewDao {

	/**
	 * 通过destinationCode来查询有效景点
	 * @param destinationCode
	 * @return
	 */
	public List<View> findViewByDestinationCode(final String destinationCode){
		/*Criteria criteria = getSession().createCriteria(View.class);
		//修改人邓晓辉   当目的地code 为空 查询所有的景点，考虑全面性
		if (destinationCode!=null&&!destinationCode.equals("")) {
			criteria.add(Restrictions.eq("destinationCode", destinationCode));
		}
		return findByCriteria(criteria);*/
		/*List<View> list = null;
		return getHibernateTemplate().execute(new HibernateCallback<List<View>>() {
			public List<View> doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = getSession().createCriteria(View.class);
				if (destinationCode!=null&&!destinationCode.equals("")) {
					criteria.add(Restrictions.eq("destinationCode", destinationCode));
				}
				return criteria.list();
			}
		});*/
	    List<Object> params = new ArrayList<Object>();
		String sql = " SELECT * FROM tview WHERE destinationCode=? AND avaliable=1 ";
		params.add(destinationCode);
		return findListBySql(View.class, sql, params);
	}
	 
	public Pager testPager(Pager pager){
		String sql = "SELECT *,viewname as name,viewname as testname FROM tview";
		return findListPagerBySql(View.class, pager, sql);
	}
	   
	 
	/*@Override
	public List<View> searchViewByIdName(View view) {
		String destinationCode = view.getDestinationCode();
		String viewName = view.getViewName();
		Criteria criteria = getSession().createCriteria(View.class);
		if(destinationCode!=null && !destinationCode.equals("")){
			criteria.add(Restrictions.eq("destinationCode", destinationCode));
		}
		if(viewName!=null && !viewName.equals("")){
			criteria.add(Restrictions.like("viewName", "%"+viewName+"%"));
		}
		return findByCriteria(criteria);
	}*/
	/**
	 * 景点信息管理分页
	 */
	@Override
	public Pager getViewPager(String destinationCode, String viewName, Pager pager) {
		List<Object> param = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("SELECT d.destinationName,v.viewName,v.isHaveGateTicket,v.goneCount,v.wantCount FROM destination d,tview v WHERE d.avaliable=1 AND v.avaliable=1 AND d.code=v.destinationCode ");
		if(StringUtils.isNotBlank(destinationCode)){
			sql.append("AND v.destinationCode=? ");
			param.add(destinationCode);
		}
		if(StringUtils.isNotBlank(viewName)){
			sql.append("AND v.viewName like ? ");
			param.add("%"+StringUtil.removeSpace(viewName)+"%");
		}
		sql.append("ORDER BY v.createTime desc");
		//return findPagerBySQL(sql.toString(), param, pager);
		return findByJDBCSql(sql.toString(), param, pager);
	}
	
	@Override
	public Pager searchViewByKeyWords(Pager pager, String destinationCode, String viewCode, String viewName, String keyWords, int orderBy){
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("  ");
		sql.append(" SELECT * FROM( ");
	    sql.append("     SELECT ");
	    sql.append("         id, avaliable, code, viewName, destinationCode, img, viewImage, view_360full, viewIntroduce, ticketPrice, ticketUrl, isHaveGateTicket, createTime, lastEditTime, keyword, fakeGoneCount, fakeWantCount, checkNum, fakeCheckNum, likeNum, fakeLikeNum, guide, traffic, notice, address, hdFullUrl, realSceneVideoUrl, linkUrl, xy, ");
	    sql.append("         (SELECT count(c.id) FROM content c, reply r  ");
	    sql.append("             WHERE c.code=r.contentCode  ");
	    sql.append("             AND c.avaliable = 1  ");
	    sql.append("             AND c.contentType = 'view_reply' "); 
	    sql.append("             AND v.code=r.postCode AND c.state=1 ");
	    sql.append("         ) AS replyNum, ");
	    sql.append("         (SELECT count(id) FROM user_view WHERE avaliable=1 AND viewCode=v.code AND type='wanna') AS wantCount, ");
	    sql.append("         (SELECT count(id) FROM user_view WHERE avaliable=1 AND viewCode=v.code AND type='gone') AS goneCount, ");
	    sql.append("         (SELECT destinationName FROM destination WHERE avaliable=1 and code=v.destinationCode) AS destinationName ");
	    sql.append("     FROM tview v ");
	    sql.append(" ) t ");
	    sql.append(" WHERE avaliable=1 ");
		/*sql.append(" SELECT ");
		sql.append(" 	* ");
		sql.append(" FROM tview  ");
		sql.append(" WHERE avaliable=1  ");*/
		if(StringUtil.isNotNull(destinationCode)){
			sql.append(" 	AND destinationCode=? ");
			params.add(destinationCode);
		}
		if(StringUtil.isNotNull(viewCode)){
			sql.append(" 	AND code=? ");
			params.add(viewCode);
		}
		if(StringUtil.isNotNull(viewName)){
			sql.append(" 	AND viewName LIKE ? ");
			params.add("%" + viewName + "%");
		}
		if(StringUtil.isNotNull(keyWords)){
		    sql.append(" 	AND concat(ifnull(viewName,''), ifnull(viewIntroduce,'')) LIKE ? ");
		    params.add("%" + keyWords + "%");
		}
		if(orderBy > 0){
			if(orderBy==View.ORDER_WANTCOUNT){
				sql.append(" ORDER BY fakeWantCount DESC ");
			}
			if(orderBy==View.ORDER_GONECOUNT){
				sql.append(" ORDER BY fakeGoneCount DESC ");
			}
			if(orderBy==View.ORDER_CHECKNUM){
				sql.append(" ORDER BY fakeCheckNum DESC ");
			}
			if(orderBy==View.ORDER_REPLYNUM){
				sql.append(" ORDER BY replyNum DESC ");
			}
			if(orderBy==View.ORDER_REAL_WANTCOUNT){
			    sql.append(" ORDER BY wantCount DESC ");
			}
			if(orderBy==View.ORDER_REAL_GONECOUNT){
			    sql.append(" ORDER BY goneCount DESC ");
			}
			if(orderBy==View.ORDER_REAL_CHECKNUM){
			    sql.append(" ORDER BY checkNum DESC ");
			}
			if(orderBy==View.ORDER_EDITTIME){
			    sql.append(" ORDER BY lastEditTime DESC ");
			}
		}else{
			sql.append(" ORDER BY createTime DESC ");
		}
		return findListPagerBySql(View.class, pager, sql.toString(), params);
	}
	/**
	 * 收藏数排序,取前i个
	 */
	@Override
	public List<View> orderByFav(int i) {
		String sql = "SELECT tv.code as code,tv.viewName as viewName,tv.destinationCode as destinationCode FROM" +
				" tview tv,praise p WHERE tv.avaliable=1 and p.available=1 and tv.code=p.contentCode " +
				"ORDER BY p.favoriteNum DESC limit "+i+" ";

		return findListBySql(View.class,sql);
	}
	
	@Override
	public Destination getDestinationByView(String code) {
	    List<Object> params = new ArrayList<Object>();
		String sql = "SELECT d.* FROM destination d,tview tv WHERE d.avaliable=1 and tv.destinationCode=d.code and tv.code=? ";
		params.add(code);
		List<Destination> dlist = findListBySql(Destination.class, sql, params);
		if(dlist.size()>0){
			return dlist.get(0);
		}
		else return null;
		
	}

	@Override
	public List<Map<String, Object>> getViewByWann(int num,String descode) {
		String sqlD  =  "";
		if (StringUtils.isNotBlank(descode)) {
			sqlD   =  " and   uv.areaCode='"+descode+"'  ";
		}else{
			sqlD  = " and  uv.viewCode is not NULL " ;
			
		}
	    String sql = "select  IFnull(uv.viewCode,null) as viewcode,des.code as  descode,ifnull(tv.viewName,'请选择景点') as viewname ,ifnull(des.destinationName,'请选择地区')  as desname    from   user_view  uv left join  tview  tv  on  tv.`code` = uv.viewCode  LEFT JOIN  destination  des  on  des.code = tv.destinationCode   where  uv.type='wanna'  " +
	    sqlD+
	    "   and   des.avaliable=1 and  tv.avaliable=1  GROUP BY uv.viewCode  ORDER BY  tv.fakeWantCount  desc  LIMIT 0,? ";
	    List<Object> params = new ArrayList<Object>();
	    params.add(num);
		return findByJDBCSql(sql, params);
	}
	@Override
	public List<Map<String, Object>> getViewByGone(int num,String descode) {
		String sqlD  =  "";
		if (StringUtils.isNotBlank(descode)) {
			sqlD   =  " and   uv.areaCode='"+descode+"' ";
		}else{
			sqlD  = " and  uv.viewCode is not NULL " ;
		}
	    String sql = "select  IFnull(uv.viewCode,null) as viewcode,des.code as  descode,ifnull(tv.viewName,'请选择景点') as viewname ,ifnull(des.destinationName,'请选择地区')  as desname    from   user_view  uv left join  tview  tv  on  tv.`code` = uv.viewCode  LEFT JOIN  destination  des  on  des.code = tv.destinationCode   where  uv.type='gone'  " +
	    sqlD+
	    "   and   des.avaliable=1 and  tv.avaliable=1 GROUP BY uv.viewCode  ORDER BY  tv.fakeGoneCount    LIMIT 0,? ";
	    List<Object> params = new ArrayList<Object>();
	    params.add(num);
		return findByJDBCSql(sql, params);
	}

	@Override
	public boolean isFirstInHotView() {
		String sql = "SELECT c.* FROM content c WHERE c.contentType = 'travelview'";
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
	    result = findByJDBCSql(sql, null);
		if(result!=null&&result.size()>0)
		   return false;
		return true;
	}

	@Override
	public List<Map<String, Object>> getHotView() {
		String sql = "SELECT c.content AS viewcodes FROM content c WHERE c.contentType = 'traveview'";
		return findByJDBCSql(sql, null);
	}

    @Override
    public int getViewRelationContent(String viewCode) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("  ");
        sql.append(" SELECT sum(cnt) FROM ( ");
        //sql.append("     SELECT count(viewCode) AS cnt FROM merchant_view WHERE viewCode='"+viewCode+"' ");
        sql.append("     SELECT count(mv.viewCode) AS cnt FROM merchant_view mv INNER JOIN merchant m ON mv.merchantCode=m.code WHERE m.avaliable=1 AND mv.viewCode=? ");
        params.add(viewCode);
        sql.append("     UNION ALL ");
        //sql.append("     SELECT count(viewCode) as cnt FROM grouptravelview WHERE viewCode='"+viewCode+"' ");
        sql.append("     SELECT count(gv.viewCode) AS cnt FROM grouptravelview gv INNER JOIN grouptravel gt ON gv.groupTravelCode=gt.code WHERE gt.avaliable=1 AND gv.viewCode=? ");
        params.add(viewCode);
        sql.append("     UNION ALL ");
        //sql.append("     SELECT count(viewCode) as cnt FROM strategy_view WHERE viewCode='"+viewCode+"' ");
        sql.append("     SELECT count(sv.viewCode) AS cnt FROM strategy_view sv INNER JOIN content c ON sv.contentCode=c.code WHERE c.avaliable=1 AND sv.viewCode=? ");
        params.add(viewCode);
        sql.append(" ) t ");
        return getJdbcTemplate().queryForObject(sql.toString(), new Object[]{viewCode,viewCode,viewCode}, Integer.class);
    }

    @Override
    public int getSameViewNum(String name) {
        List<Object> params = new ArrayList<Object>();
        String sql = "SELECT count(viewName) FROM tview WHERE avaliable=1 AND viewName=? ";
        params.add(name);
        return getJdbcTemplate().queryForObject(sql,params.toArray(),Integer.class);
    }
}
