package com.rimi.ctibet.web.dao.daoimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.HTMLTagUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Destination;
import com.rimi.ctibet.domain.GroupTravel;
import com.rimi.ctibet.domain.View;
import com.rimi.ctibet.web.controller.vo.GroupTravelVO;
import com.rimi.ctibet.web.dao.DestinationDao;
import com.rimi.ctibet.web.dao.IGroupTravelDao;
import com.rimi.ctibet.web.dao.IViewDao;

@Repository("groupTravelDao")
public class GroupTravelDaoImpl extends BaseDaoImpl<GroupTravel> implements IGroupTravelDao{

	@Resource
	private DestinationDao  destinationDao;
	@Resource
	private IViewDao viewDao;
	
	@Override
	public Pager orderByGroupTravel(Pager pager,String rule) {
		String sql = "SELECT gt.*,v.viewName,d.destinationName AS desname " +
				"FROM grouptravel gt,grouptraveldestination gtd,grouptravelview gtv,tview v,destination d  " +
				"WHERE gt.`code` = gtd.groupTravelCode AND gtd.destinationCode = d.`code` " +
				"AND gt.`code` = gtv.groupTravelCode AND gtv.viewCode = v.`code` AND gt.avaliable = 1 ";
        sql +=" group by gt.`code` ";
		if(GroupTravel.RULE_COLLECT.equals(rule))
			sql += "ORDER BY gt.collectNum DESC";
		else if(GroupTravel.RULE_PRAISE.equals(rule))
			sql += "ORDER BY gt.praiseNum DESC";
		else if(GroupTravel.RULE_VIEW.equals(rule))
			sql += "ORDER BY gt.viewNum DESC";

		return findByJDBCSql(sql,null, pager);
	}

	@Override
	public Pager searchGroupTravel(Pager pager,String keyWord,
			String destinationCode, String viewCode,String  isRecommend) {
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT gt.*,v.viewName,group_concat(DISTINCT d.destinationName ) AS desname " +
				"FROM grouptravel gt,grouptraveldestination gtd,grouptravelview gtv,tview v,destination d " +
				"WHERE gt.`code` = gtd.groupTravelCode AND gtd.destinationCode = d.`code` " +
				"AND gt.`code` = gtv.groupTravelCode AND gtv.viewCode = v.`code` AND gt.avaliable = 1 " ;
		if(StringUtils.isNotBlank(destinationCode)){
			params.add(destinationCode);
			sql +="AND d.`code` = ? ";
		}
		if(StringUtils.isNotBlank(isRecommend)){
			params.add(isRecommend);
			sql +="AND gt.isRecommend = ? ";
		}
		if(StringUtils.isNotBlank(viewCode)){
			params.add(viewCode);
			sql +="AND v.`code` = ? ";
		}
		if(StringUtils.isNotBlank(keyWord)){
			params.add("%"+keyWord+"%");
			params.add("%"+keyWord+"%");
			sql +="AND (gt.`name` LIKE ? OR gt.detail LIKE ? ) ";
		}
		sql += "GROUP BY gt.`code`";
		return findByJDBCSql(sql, params, pager);
	}

	@Override
	public List<Destination> getDestinations(String groupTravelCode) {
		String sql = "SELECT  d.`code` " +
				"FROM grouptravel gt ,grouptraveldestination gtd ,destination d " +
				"WHERE gt.`code` = gtd.groupTravelCode AND gt.`code` = ? ANd  d.`code` = gtd.destinationCode AND gt.avaliable = 1 GROUP BY d.`code`";
		List<Object> params = new ArrayList<Object>();
		params.add(groupTravelCode);
		List<Map<String,Object>> codes = findListBysql(sql, params);
		List<Destination> des = new ArrayList<Destination>();
		for (Map<String,Object> code : codes) {
			Destination d = destinationDao.findByCode(code.get("code").toString());
			des.add(d);
		}
		return des;
	}

	@Override
	public List<View> getViews(String groupTravelCode) {
		String sql = "SELECT  v.`code` " +
				"FROM grouptravel gt ,grouptravelview gtv ,tview v " +
				"WHERE gt.`code` = gtv.groupTravelCode AND gt.`code` = ? ANd  v.`code` = gtv.viewCode AND gt.avaliable = 1 GROUP BY v.`code`";
		List<Object> params = new ArrayList<Object>();
		params.add(groupTravelCode);
		List<Map<String,Object>> codes = findListBysql(sql, params);
		List<View> vs = new ArrayList<View>();
        for (Map<String,Object> code :codes) {
			View v  = viewDao.findByCode(code.get("code").toString());
			vs.add(v);
		}
		return vs;
	}
	
	public void updateGroupTravel(GroupTravel gt){
		updateAsHibernate(gt);
	}

	//商户首页显示団游信息
    public List<Map<String,Object>> getGtForMIndex(){
        String sql = "select gt.*,(select GROUP_CONCAT(viewName) from  tview  where        tview.code in  (select  viewcode  from  grouptravelview   where grouptravelcode  =  gt.code ))  as viewName," +
        		"(select   GROUP_CONCAT(destinationName)   from  destination  where        destination.code in  (select  destinationCode  from  grouptraveldestination   where grouptravelcode  =  gt.code )       )  as desname" +
        		",praise.falseFavoriteNum,ifnull(praise.viewcount,0) as viewcount  " +
        		"from  grouptravel   gt  left join  praise on praise.contentcode  =  gt.code " +
        		" where gt.avaliable = 1  GROUP BY gt.`code`  HAVING viewName IS NOT NULL AND desname IS NOT NULL " +
        		"ORDER BY gt.createTime DESC limit 0,6 ";
        		
        return findByJDBCSql(sql, null);
    }
	
    @Override
    public List<GroupTravelVO> getGroupTravelRecommendByViewCode(int row, String viewCode) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("  ");
        sql.append(" SELECT ");
        sql.append("     gt.*, ");
        sql.append("     v.code as viewCode,v.viewName, ");
        sql.append("     IFNULL(p.falseFavoriteNum,0) as falseFavoriteNum ");
        sql.append(" FROM grouptravel gt ");
        sql.append(" INNER JOIN grouptravelview gtv ON gt.code=gtv.groupTravelCode ");
        sql.append(" INNER JOIN tview v ON v.code=gtv.viewCode AND v.avaliable=1 ");
        sql.append(" LEFT JOIN praise p ON p.contentcode=gt.code AND p.available=1 ");
        sql.append(" WHERE gt.avaliable=1 AND gt.isRecommend=1 ");
        sql.append(" AND v.code=? ");
        params.add(viewCode);
        sql.append(" ORDER BY gt.createTime DESC ");
        return findListBySqlRow(GroupTravelVO.class, row, sql.toString(), params);
    }

	@Override
	public Pager getGroupTravelList(Pager pager,String destCode, String viewCode,String keyWord,String rule) {
		List<Object> params = new ArrayList<Object>();
		String sql = " SELECT  gt.*,d.destinationName AS destName ,d.`code` as  destCode,v.`code` AS viewCode,gt.praiseNum as favoriteNum " +
				" FROM grouptravel gt ,grouptraveldestination gtd,grouptravelview gtv ,destination d,tview v ,praise p " +
				" WHERE gt.`code` = gtd.groupTravelCode  AND d.`code` = gtd.destinationCode AND gt.`code` = gtv.groupTravelCode " +
				" AND v.`code` = gtv.viewCode AND p.contentcode = gt.`code` AND gt.avaliable = 1 ";
		if(StringUtils.isNotBlank(destCode)){
			sql += " AND d.`code` = ? ";
			params.add(destCode);
		}
		if(StringUtils.isNotBlank(viewCode)){
			sql += " AND v.`code` = ? ";
			params.add(viewCode);
		}
		if(StringUtils.isNotBlank(keyWord)){
			sql += " AND gt.name =  ? ";
			params.add("%"+keyWord+"%");
		}
		
		sql+=" GROUP BY gt.`code` ";
        //排序
		if("favorite".equals(rule)){
			sql += " ORDER BY p.viewcount desc";
		}
		else if("collect".equals(rule)){
			sql += " ORDER BY favoriteNum desc";
		}
		List<Map<String,Object>> ms = new ArrayList<Map<String,Object>>();
		ms =  findByJDBCSql(sql, params, pager).getDataList();
		if(ms!=null&&ms.size()>0){
			for (Map<String, Object> map : ms) {
                   map.put("name",HTMLTagUtil.delHTMLTag(map.get("name").toString()));
                   map.put("detail",HTMLTagUtil.delHTMLTag(map.get("detail").toString()));
                   map.put("detail",map.get("detail").toString().replace("&nbsp;", ""));
                   String priceRef  = map.get("price").toString();
                   if(StringUtils.isNotBlank(priceRef)&&priceRef.indexOf("暂无")>-1){
                	   map.put("price", "0");
                   }
			}
		}
		Pager resultPpager = findByJDBCSql(sql, params, pager);
		resultPpager.setDataList(ms);
		return resultPpager;
	}

    @Override
    public List<GroupTravelVO> getGroupTravelRecommendBydestinationCode(int row, String destinationCode) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("  ");
        sql.append(" SELECT  ");
        sql.append("     gt.*, ");
        sql.append("     p.falseFavoriteNum AS falseFavoriteNum ");
        sql.append(" FROM grouptravel gt ");
        sql.append(" INNER JOIN grouptraveldestination gtd ON gt.code=gtd.groupTravelCode ");
        sql.append(" LEFT JOIN praise p ON p.contentcode = gt.code AND p.available = 1 ");
        sql.append(" WHERE gt.avaliable = 1 AND gt.isRecommend = 1  ");
        sql.append(" AND gtd.destinationCode=? ");
        params.add(destinationCode);
        sql.append(" ORDER BY gt.createTime DESC ");
        return findListBySqlRow(GroupTravelVO.class, row, sql.toString(), params);
    }

	@Override
	public Pager gtReplyInfo(Pager pager, String gtCode) {
		List<Object> params = new ArrayList<Object>();
		params.add(gtCode);
		String sql = " SELECT c.createTime,c.content,mi.`name`,mi.pic,mi.sex,c.title "
				+ " FROM grouptravel m, reply r,content c,member me,member_info mi "
				+ " WHERE  c.avaliable = 1 and c.state=1 and m.`code` = r.postCode AND c.`code` = r.contentCode " +
				  " AND c.createuserCode = mi.memberCode " +
				  " AND me.`code` = mi.memberCode AND m.`code` = ? "
				+ " GROUP BY  c.createTime ORDER BY  c.createTime DESC";
		return findByJDBCSql(sql, params, pager);
	}
	
    @Override
    public float getScore(String code) {
        StringBuffer sql = new StringBuffer();
        //sql.append("  ");
        sql.append(" SELECT  ");
        //sql.append("     sum(c.title),count(c.title), ");
        sql.append("     ifnull(sum(c.title)/count(c.title), 0) as score ");
        sql.append(" FROM grouptravel g ");
        sql.append("     LEFT JOIN reply r ON r.postCode=g.code ");
        sql.append("     LEFT JOIN content c ON r.contentCode=c.code and c.state=1 and c.avaliable=1 ");
        sql.append(" WHERE g.avaliable=1 ");
        sql.append("     AND g.code=? ");
        sql.append(" GROUP BY g.code ");
        return getJdbcTemplate().queryForObject(sql.toString(), new Object[]{code}, Float.class);
    }
	
}
