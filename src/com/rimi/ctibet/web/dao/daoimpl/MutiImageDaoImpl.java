package com.rimi.ctibet.web.dao.daoimpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import com.mysql.jdbc.RowData;
import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.ListUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Activity;
import com.rimi.ctibet.domain.MutiImage;
import com.rimi.ctibet.web.controller.vo.MutiImageVO;
import com.rimi.ctibet.web.dao.MutiImageDao;

/**
 * 
 * @author xiangwq
 * @date 2014/11/21
 */
@Repository("MutiImageDao")
public class MutiImageDaoImpl extends BaseDaoImpl<MutiImage> implements
		MutiImageDao {
	/**
	 * 查询图集列表并分页
	 */
	@Override
	public Pager getMutiImagePager(Pager pager, String programaCode) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer(
				"from MutiImage m where m.avaliable=1 and m.programaCode=:programaCode ");
		paraMap.put("programaCode", programaCode);
		// if(StringUtils.isNotBlank(name)){
		// hql.append("and m.name=:name");
		// paraMap.put("name", name);
		// }
		hql.append(" order by m.createTime desc");
		return findWithPagerByMap(hql.toString(), paraMap, pager);
	}

	/**
	 * 通过活动code查询
	 * 
	 * @param activityCode
	 * @return
	 */
	public MutiImage getMutiImageByActCode(String activityCode) {
		String sql = " SELECT * FROM mutiimage WHERE programaCode='"
				+ activityCode + "' AND summary='"
				+ Activity.ACTIVITY_OTHERBLOCK + "' AND avaliable=1 ";
		List<MutiImage> list = findListBySql(MutiImage.class, sql);
		return ListUtil.returnSingle(list);
	}

	/**
	 * 通过programaCode获取图集对象 不包含List<Image>
	 */
	public MutiImageVO getMutiImageByProgramaCode(String programaCode) {
		String sql = " SELECT * FROM mutiimage WHERE programaCode='"
				+ programaCode + "' AND avaliable=1 ";
		List<MutiImageVO> list = findListBySql(MutiImageVO.class, sql);
		return ListUtil.returnSingle(list);
	}

	/**
	 * 通过programaCode获取图集对象LIST
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MutiImage> findMutiImageByProgramaCode(String programaCode) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer(
				"from MutiImage m where m.avaliable=1 and m.programaCode=:programaCode ");
		paraMap.put("programaCode", programaCode);
		hql.append(" order by m.createTime desc");
		return find(hql.toString(), paraMap);
	}

	/**
	 * 获取前台分页
	 */
	@Override
	public Pager getFrontMutiPager(Pager pager, String action) {
		//System.out.println("dao里-------------->action=" + action);
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("SELECT ");
		hql.append(" m.code as code,m.hyperlink as hyperlink,m.programaCode as programaCode,m.coverUrl as coverUrl,m.name as name,m.summary as summary,p.falseViewcount as falseViewcount,p.falseFavoriteNum as falseFavoriteNum");
		hql.append(" FROM ");
		hql.append(" mutiimage m,praise p ");
		hql.append(" WHERE ");
		hql.append(" m.avaliable=1 AND m.code=p.contentCode AND m.programaCode=? ");
		// action 1:最多发布 2:最多评论 3：最多收藏
		if (action.equals("1")) {
			hql.append(" ORDER BY m.createTime DESC");
		}
		if (action.equals("2")) {
			hql.append(" ORDER BY p.replyNum DESC");
		}
		if (action.equals("3")) {
			hql.append(" ORDER BY p.favoriteNum DESC");
		}
		params.add("14dba551-cb5b-4631-b5ef-b3838670b3a9");
		return this.findByJDBCSql(hql.toString(), params, pager);
	}

	/**
	 * 获取指定行号图集，
	 * 
	 * @param mutiImage
	 *            限制条件
	 * @param rowNo
	 * @return
	 */
	@Override
	public MutiImage findbyRowNo(MutiImage mutiImage, int rowNo) {
		final String sql = "select t.* from ( "
				+ "select  mutiimage.*,(@rowNum:=@rowNum+1) as rowNo"
				+ " from mutiImage,(Select (@rowNum:=0) ) b"
				+ " where avaliable=1 and programaCode='"
				+ mutiImage.getProgramaCode() + "') t where t.rowNo=" + rowNo;
		List<MutiImage> mutis = findListBySql(MutiImage.class, sql,
				new ArrayList<Object>());
		return mutis.size()>0?mutis.get(0):null;
	}
	@Override
	public int findRowNo(MutiImage mutiImage) {
		final String sql = "select t.rowNo from(select code , (@rowNum:=@rowNum+1) as rowNo "
				+ "from mutiImage,(Select (@rowNum:=0) ) b"
				+ " where avaliable=1 and programaCode='"
				+ mutiImage.getProgramaCode() + "' ) t where code='"+mutiImage.getCode()+"'";
		List l = getJdbcTemplate().queryForList(sql);
		Map map = (Map) l.get(0);
		String m = map.get("rowNo") + "";
		return new Float(m).intValue();
	}

	@Override
	public Pager search(final Pager pager, MutiImage mutiImage, String orderField) {
		
		String sql="SELECT  * FROM  mutiimage m LEFT JOIN praise p on m.`code` = p.contentcode " +
				"where  m.avaliable=1  and m.programaCode='"+mutiImage.getProgramaCode()+"' {keyword}  GROUP BY m.code ";
		if(StringUtils.isNotBlank(orderField))
		{
			sql+=" order by "+orderField;
		}
		sql=parseKeyword(mutiImage,sql);
		final String fsql=sql;
		return getHibernateTemplate().execute(new HibernateCallback<Pager>() {
			@Override
			public Pager doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(fsql);
				sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				pager.setTotalCount(sqlQuery.list().size());
				sqlQuery.setMaxResults(pager.getPageSize());
				sqlQuery.setFirstResult(pager.getStartIndex());
				pager.setDataList(sqlQuery.list());
				return pager;
			}
		});
	}

	private String parseKeyword(MutiImage mutiImage, String sql) {
		boolean hasKeyword=false;
		String rs = "" ;
		if(StringUtils.isNotBlank(mutiImage.getCode())){
			hasKeyword=true;
			rs += createliks(mutiImage.getCode(),"m.code");
		}
		if(StringUtils.isNotBlank(mutiImage.getName())){
			hasKeyword=true;
			rs += createliks(mutiImage.getName(),"name");
		}
		if(StringUtils.isNotBlank(mutiImage.getSummary())){
			hasKeyword=true;
			rs += createliks(mutiImage.getSummary(),"summary");
		}
		if(StringUtils.isNotBlank(mutiImage.getKeywords())){
			hasKeyword=true;
			rs += createliks(mutiImage.getKeywords(),"keywords");
		}
		String sqlLike="";
		if(hasKeyword)
		{
			rs=rs.substring(0,rs.lastIndexOf("or"));
			sqlLike=" and ("+rs+")";
		}
		return sql.replaceAll("\\{keyword\\}", sqlLike);
	}
	private String createliks(String keywords, String columName) {
		keywords=keywords.replaceAll(" +", ";");
		keywords=keywords.replaceAll(",+", ";");
		String words[]=keywords.split(";");
		String rs="";
		for (String string : words) {
			if(StringUtils.isNotBlank(string))
			{
			 rs+=columName+"  like '"+"%"+string+"%' or ";
			}
		}
		return rs;
	}
}
