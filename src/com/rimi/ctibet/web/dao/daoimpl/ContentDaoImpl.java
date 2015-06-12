package com.rimi.ctibet.web.dao.daoimpl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.taskdefs.condition.IsFalse;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.constants.Constants;
import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.ListUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.common.util.VeDate;
import com.rimi.ctibet.domain.Activity;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.portal.controller.vo.FrontReplyVo;
import com.rimi.ctibet.portal.controller.vo.FrontViewAndDesVo;
import com.rimi.ctibet.portal.controller.vo.ReplyVO;
import com.rimi.ctibet.portal.controller.vo.TravalFrontPageVo;
import com.rimi.ctibet.web.controller.vo.ContentMemberVO;
import com.rimi.ctibet.web.controller.vo.Culture;
import com.rimi.ctibet.web.controller.vo.ReplyManageVO;
import com.rimi.ctibet.web.controller.vo.SpecialVO;
import com.rimi.ctibet.web.dao.IContentDao;

@Repository("contentDao")
public class ContentDaoImpl extends BaseDaoImpl<Content> implements IContentDao {
	/**
	 * 通过code和内容类型查找有效数据
	 * 
	 * @param code
	 * @param contentType
	 * @return
	 */
	public Content findByCodeContentType(final String code, final String contentType) {
	    return getHibernateTemplate().execute(new HibernateCallback<Content>() {
            public Content doInHibernate(Session paramSession)throws HibernateException, SQLException {
                Criteria criteria = paramSession.createCriteria(Content.class);
                criteria.add(Restrictions.eq("avaliable", 1));
                criteria.add(Restrictions.eq("code", code));
                criteria.add(Restrictions.eq("contentType", contentType));
                List list = criteria.list();
                return ((ListUtil.isNotNull(list))?(Content) list.get(0):null);
            }
        });
		/*Criteria criteria = getSession().createCriteria(Content.class);
		criteria.add(Restrictions.eq("avaliable", 1));
		criteria.add(Restrictions.eq("code", code));
		criteria.add(Restrictions.eq("contentType", contentType));
		List<Content> list = findByCriteria(criteria);
		return (list != null && list.size() > 0) ? list.get(0) : null;*/
	}

	/**
	 * 通过内容类型查找有效数据 分页
	 * 
	 * @param contentType
	 * @return
	 */
	public Pager findByContentType(final String contentType, final Pager pager) {
	    return getHibernateTemplate().execute(new HibernateCallback<Pager>() {
            public Pager doInHibernate(Session arg0) throws HibernateException, SQLException {
                Criteria criteria = arg0.createCriteria(Content.class);
                criteria.add(Restrictions.eq("avaliable", 1));
                criteria.add(Restrictions.eq("contentType", contentType));
                return findPageByCriteria(criteria, pager);
            }
	    });
		/*Criteria criteria = getSession().createCriteria(Content.class);
		criteria.add(Restrictions.eq("avaliable", 1));
		criteria.add(Restrictions.eq("contentType", contentType));
		return findPageByCriteria(criteria, pager);*/
	}

	/**
	 * 通过视频类型和标题查询有效数据
	 * 
	 * @param content
	 * @return
	 */
	public Pager findVedioByProgramaCodeTitle(final Content content, final Pager pager) {
		return getHibernateTemplate().execute(new HibernateCallback<Pager>() {
            public Pager doInHibernate(Session arg0) throws HibernateException, SQLException {
                String programaCode = content.getProgramaCode();
                String contentTitle = content.getContentTitle();
                String contentType = content.getContentType();
                Criteria criteria = arg0.createCriteria(Content.class);
                criteria.add(Restrictions.eq("avaliable", 1));
                criteria.add(Restrictions.eq("programaCode", programaCode));
                criteria.add(Restrictions.like("contentTitle", contentTitle));
                criteria.add(Restrictions.eq("contentType", contentType));
                return findPageByCriteria(criteria, pager);
            }
        });
	}

	/**
	 * 通过帖子code删除帖子，并删除其回复和子回复
	 * 
	 * @param postCode
	 */
	public int deletePostByPostCode(final String postCode) {
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session arg0) throws HibernateException, SQLException {
                StringBuffer hql = new StringBuffer();
                hql.append(" update Content set avaliable=0 where ");
                hql.append("    code in (  ");
                hql.append("        select contentCode from Reply where postCode=:postCode ");
                hql.append("    ) ");
                hql.append("    or ");
                hql.append("    code in ( ");
                hql.append("        select contentCode from Reply where postCode in ( ");
                hql.append("            select contentCode from Reply where postCode=:postCode ");
                hql.append("        ) ");
                hql.append("    ) ");
                hql.append("    or code =:postCode ");
                Query query = arg0.createQuery(hql.toString());
                return query.setParameter("postCode", postCode).executeUpdate();
            }
        });
	}

	/**
	 * 通过回复code删除回复，并删除其子回复
	 * 
	 * @param replyCode
	 */
	public int deleteReplyByReplyCode(final String replyCode) {
	    return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session arg0) throws HibernateException, SQLException {
                StringBuffer hql = new StringBuffer();
                hql.append(" update Content set avaliable=0 where ");
                hql.append("    code in (  ");
                hql.append("        select contentCode from Reply where postCode=:replyCode ");
                hql.append("    ) ");
                hql.append("    or code =:replyCode ");
                Query query = arg0.createQuery(hql.toString());
                return query.setParameter("replyCode", replyCode).executeUpdate();
            }
        });
	}

	/**
	 * 通过栏目内容中间表中的栏目code获取content
	 * 
	 * @return
	 */
	public List<Content> findContentByProCode(String proCode) {
		String sql = "SELECT c.* FROM content c INNER JOIN programa_content pc ON c.code = pc.conCode WHERE c.avaliable = 1 AND pc.proCode = '"
				+ proCode + "'";
		return findListBySql(Content.class, sql);
	}

	/**
	 * 通过栏目内容中间表中的栏目code获取指定行数的content
	 * 
	 * @return
	 */
	public List<Content> findContentByProCodeRow(String proCode, int row) {
		String sql = "SELECT c.* FROM content c INNER JOIN programa_content pc ON c.code = pc.conCode WHERE c.avaliable = 1 AND pc.proCode = '"
				+ proCode + "'";
		return findListBySqlRow(Content.class, row, sql);
	}

	/**
	 * 通过栏目内容中间表中的栏目code获取content 分页
	 * 
	 * @return
	 */
	public Pager findContentByProCode(Pager pager, String proCode) {
		String sql = "SELECT c.* FROM content c INNER JOIN programa_content pc ON c.code = pc.conCode WHERE c.avaliable = 1 AND pc.proCode = '"
				+ proCode + "'";
		return findListPagerBySql(Content.class, pager, sql);
	}

	/**
	 * 通过 审核状态、版块、目的地、景点、官方标记、[会员名、手机、邮箱、攻略标题(keyWord)]、攻略有效性 查询攻略列表
	 * 
	 * @param state
	 *            审核状态
	 * @param proCode
	 *            板块
	 * @param destinationCode
	 *            目的地
	 * @param viewCode
	 *            景点
	 * @param isOfficial
	 *            官方标记
	 * @param keyWord
	 *            关键字[会员名、手机、邮箱、攻略标题]
	 * @param avaliable
	 *            有效性
	 * @return
	 */
	public Pager searchStrategyByContentMember(Pager pager,
			Map<String, Object> paramMap,String order) {
		if (paramMap == null)
			paramMap = new HashMap<String, Object>();
		ArrayList<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  distinct ");
		sql.append(" 	 c.url as url,c.code, c.contentTitle, c.content, c.createTime, c.authorCode AS authorName, ");
		sql.append("     c.state, CASE WHEN c.state=0 THEN '未审核' WHEN c.state=1 THEN '已审核' WHEN c.state=-1 THEN '未通过' END AS stateName, ");
		sql.append("     c.isOfficial, CASE WHEN c.isOfficial=0 THEN '用户上传' ELSE '官方上传' END as isOfficialName, ");
		sql.append("     p.programaName, ");
		sql.append("     m.name, m.email, m.mobile ");
		sql.append(" FROM content c   ");
		sql.append(" 	 LEFT JOIN v_member_detail m ON c.createuserCode = m.code  left join praise  ps on  ps.contentcode  = c.code  ");
		sql.append(" 	 LEFT JOIN programa p ON c.programaCode=p.code AND p.pCode='098bf91c-792e-11e4-b6ce-005056a05bc9' ");
		// sql.append("     LEFT JOIN strategy_view sv ON sv.contentCode=c.code ");
		sql.append(" WHERE c.contentType='strategy' and  c.avaliable=1  ");
		sql.append(" AND c.code IN ( SELECT contentCode FROM strategy_view WHERE 1=1 ");
		if (paramMap.get("destinationCode") != null
				&& !paramMap.get("destinationCode").equals("")) {
			sql.append("     AND destinationCode=? ");
			paramList.add(paramMap.get("destinationCode"));
		}
		if (paramMap.get("viewCode") != null
				&& !paramMap.get("viewCode").equals("")) {
			sql.append("     AND viewCode=? ");
			paramList.add(paramMap.get("viewCode"));
		}
		sql.append(" GROUP BY contentCode) ");
		if (paramMap.get("state") != null && !paramMap.get("state").equals("")) {
			sql.append(" 	 AND c.state=? ");
			paramList.add(paramMap.get("state"));
		}
		if (paramMap.get("proCode") != null
				&& !paramMap.get("proCode").equals("")) {
			sql.append(" 	 AND p.code=? ");
			paramList.add(paramMap.get("proCode"));
		}
		if (paramMap.get("isOfficial") != null
				&& !paramMap.get("isOfficial").equals("")) {
			sql.append("     AND c.isOfficial=? ");
			paramList.add(paramMap.get("isOfficial"));
		}
		if (paramMap.get("keyWord") != null
				&& !paramMap.get("keyWord").equals("")) {
			sql.append("     AND concat( IFNULL(m.name,''),IFNULL(m.mobile,''),IFNULL(m.email,''),'',IFNULL(c.contentTitle,'')  )  LIKE ? ");
			paramList.add("%" + paramMap.get("keyWord") + "%");
		}
		if (paramMap.get("avaliable") != null
				&& !paramMap.get("avaliable").equals("")) {
			sql.append(" 	 AND c.avaliable=? ");
			paramList.add(paramMap.get("avaliable"));
		}
		if (StringUtils.isNotBlank(order)&&order.equals("createTime")) {
			sql.append(" ORDER BY c.createTime DESC ");
		}else if(StringUtils.isNotBlank(order)&&order.equals("favatecount")){
			sql.append(" order by      ps.falseFavoriteNum    desc  ");
		}else if(StringUtils.isNotBlank(order)&&order.equals("praisecount")){
			sql.append(" order by      ps. falsepraise desc   ");
		}else if(StringUtils.isNotBlank(order)&&order.equals("replycount")){
			sql.append(" order by     (select count(1) from reply where reply.postCode =  c.code) desc  ");
		}else{
			sql.append(" ORDER BY c.createTime DESC ");
			
		}
		return findListPagerBySql(ContentMemberVO.class, pager, sql.toString(),
				paramList);
	}

	/**
	 * 通过code更新审核状态
	 * 
	 * @param state
	 * @return
	 */
	public int updateState(final String code, final int state) {
	    return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            public Integer doInHibernate(Session arg0) throws HibernateException, SQLException {
                String hql = " update from Content set state=:state where code=:code ";
                Query query = arg0.createQuery(hql);
                query.setParameter("code", code);
                query.setParameter("state", state);
                return query.executeUpdate();
            }
        });
	}

	/**
	 * 查询后台 专题列表
	 * 
	 * @param name
	 * @param time
	 * @param orderBy
	 * @return
	 */
	public Pager searchSpecailListByNameTime(Pager pager, String name,
			String time, int orderBy) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("  ");
		sql.append(" SELECT ");
		sql.append(" 	c.code,c.contentTitle as name,c.summary,c.url,c.title,c.keywords,c.createTime,c.sortNum,c.tag, ");
		sql.append("     p.viewcount, p.falseViewcount, ");
		sql.append("     p.favoriteNum,p.falseFavoriteNum, ");
		sql.append("     p.replyNum ");
		sql.append(" FROM content c LEFT JOIN praise p ON c.code=p.contentcode AND  p.available=1 ");
		sql.append(" WHERE c.contentType='")
				.append(Content.CONTENTTYPE_SPECIAL)
				.append("' AND c.avaliable=1 ");
		if (StringUtil.isNotNull(name)) {
			sql.append(" AND c.contentTitle LIKE ? ");
			params.add("%" + name + "%");
		}
		if (StringUtil.isNotNull(time)) {
			sql.append(" AND date_format(c.createTime,'%Y-%m-%d') = ? ");
			params.add(time);
		}
		sql.append(" ORDER BY  ");
		if (orderBy > 0) {
			if (orderBy == Activity.ORDERBY_DATE) {
				sql.append(" c.createTime DESC ");
			}
			if (orderBy == Activity.ORDERBY_FAVORITE) {
				sql.append(" p.falseViewcount DESC ");
			}
			if (orderBy == Activity.ORDERBY_REPLY) {
				sql.append(" p.replyNum DESC ");
			}
			if (orderBy == Activity.ORDERBY_CHECKNUM) {
				sql.append(" p.falseViewcount DESC ");
			}
		} else {
			sql.append(" c.sortNum ASC,c.createTime DESC ");
		}
		return findListPagerBySql(SpecialVO.class, pager, sql.toString(),
				params);
	}

	@Override
	public void test() {
		final String HqlAndSql = "select c.* From Content c left join OTHER  o on c.code = o.contentCode and c.avaliable=1";
		final String HqlAndSql1 = "select c.* From Content c ,OTHER  where o on c.code = o.contentCode and c.avaliable=1";
		String sql = "select c.* From  Content c left join OTHER  o  on c.code = o.contentCode  " +
				"{fieldFilter}" +//= and fieldName ='view' 
				"where c.avaliable=1 "+// and c.contentType like '{type}' "
				"order by {orderFilter} ";//fieldValue+0 desc //createtime
	}




	private String parseContentType(String contentType, String sql) {
		//and c.contentType like 'type'
		//{typeFilter} 替换掉
		String type=null;
		if (StringUtil.isNotNull(contentType)) {
			 type = contentType;
			try {
				int intType = Integer.parseInt(type);
				if (intType % 1000 == 0) {
					type = type.replaceAll("0", "_");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else
		{
			type="%";
		}
		return sql.replaceAll("\\{typeFilter\\}", " and c.contentType like '"+type+"'");
	}

	private String parseOrderAndField(String type, String sql) {
		//{fieldFilter} 替换成 and fieldName ='view'
		//{orderFilter}  替换成 fieldValue+0 desc 
		String order="";
		String field="";
		if (StringUtil.isNotNull(type)) {
			try {
				int indexType = Integer.parseInt(type);
				int pos=indexType%100;
				String odrerField="" + Culture.ORDER_TYPES[pos][2];
				if(indexType/100==1)//content 表
				{
					order=odrerField;
					field="";
				}else if(indexType/100==2) {//other 表
					//odrerField.split(" +");
					order="fieldValue+0 "+odrerField.split(" +")[1];
					field="and fieldName ='"+odrerField.split(" +")[0]+"'";
				}else
				{
					order="createTime desc";	
				}
			} catch (Exception e) {
				order="createTime desc";
			}
		}else{
			order="createTime desc";
		}
		return sql.replaceAll("\\{fieldFilter\\}", field).replaceAll("\\{orderFilter\\}", order);
	}

	/**
	 * 通过目的地查询热门景点
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Content> findContentByDestinationCodeAndType(String code,
			String type) {
		String hql = "FROM Content c WHERE c.contentType=:type and c.authorCode=:code and c.avaliable=1";
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("type", type);
		paraMap.put("code", code);
		return (List<Content>) find(hql, paraMap);
	}
	/**
	 * 重载 父类 如果有 others 将保持others 信息
	 * @param content
	 * @return
	 */
	@Override
	public Content updateLogical(Content content) {
		super.updateLogical(content);
		Map map=content.getOthers();
		String contentCode = content.getCode();
		operateOthers(map, contentCode);
		return content;
	}
	@Override
	public void save(Content content) {
		super.save(content);
		Map map=content.getOthers();
		String contentCode = content.getCode();
		operateOthers(map, contentCode);
	}

	/**
	 * 保存或更新others 信息
	 * @param others
	 * @param contentCode
	 */
	private void operateOthers(Map others, String contentCode) {
		if (null != others) {
			Iterable iterable = others.keySet();
			for (Object object : iterable) {
				String fieldName = (String) object;
				String fieldValue = (String) others.get(object);
				setField(contentCode, fieldName, fieldValue);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void setField(final String contentCode, final String fieldName,
			final String fieldValue) {
		
		final String sqlupdate = "update other set fieldValue =?  where contentCode=? and fieldName=? ";

		final String sqlinsert = "insert into other(contentCode,fieldName,fieldValue) values(?,?,?) ";
		getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				try {
					Query query = session.createSQLQuery(sqlupdate);
					query.setParameter(0, fieldValue);
					query.setParameter(1, contentCode);
					query.setParameter(2, fieldName);
					int m = query.executeUpdate();
					if (m == 0) {
						Query queryinsert = session.createSQLQuery(sqlinsert);
						queryinsert.setParameter(0, contentCode);
						queryinsert.setParameter(1, fieldName);
						queryinsert.setParameter(2, fieldValue);
						queryinsert.executeUpdate();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}

	@Override
	public Map findContentOthers(final String code) {
		final String sqlOther = "select fieldname,fieldvalue from other where contentcode=?";
		final Map map = new HashMap();
		return getHibernateTemplate().execute(new HibernateCallback<Map>() {
			@Override
			public Map doInHibernate(Session arg0) throws HibernateException,
					SQLException {
				Query queryOther = arg0.createSQLQuery(sqlOther);
				queryOther.setParameter(0, code);
				queryOther
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List otherDate = queryOther.list();
				int i = 0;

				while (i < otherDate.size()) {
					Map rsRow = (Map) otherDate.get(i);
					map.put(rsRow.get("fieldName"), rsRow.get("fieldValue"));
					i++;
				}
				return map;
			}
		});
		// return map;
	}
	/**
	 * 获取游西藏首页推荐景点
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Content findTravelView() {
		String hql = "FROM Content c WHERE c.contentType='travelview' and c.avaliable=1 AND c.programaCode='25b327a5-7e8c-12e4-b6ce-005056b896a3' ";
		List<Content> list = getHibernateTemplate().find(hql);
		/*Query query = getSession().createQuery(hql);
		List<Content> list = findByQuery(query);*/
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	//功能不健全，只能查询 当前类型 以外的数据
	@Override
	public Pager findOtherContent(final Pager pager, Content content, String contentType,
			String orderType) {
		String sql = "select c.*  From  Content c left join OTHER  o  on c.code = o.contentCode  " +
				" {fieldFilter} " +
				" where 1=1 and c.avaliable=1 and contentType like '1___'   and contentType <>"+contentType+"  {keyword} " +  
				"GROUP BY c.`code` order by {orderFilter} ";
		sql = parseOrderAndField(orderType,sql);
		sql = parseKeyword(content,sql);
		final String sqlStr=sql;
		this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session arg0)
					throws HibernateException, SQLException {
				SQLQuery query = arg0.createSQLQuery(sqlStr);
				query.addEntity(Content.class);
				query.setMaxResults(pager.getPageSize());
				query.setFirstResult(pager.getStartIndex());
				String pageSql="select count(*) from ( "+sqlStr+" ) t";
				pager.setTotalCount(getJdbcTemplate().queryForInt(pageSql));
				if (query.list().size() > 0) {
					pager.setDataList(query.list());
				} else {
					pager.setDataList(new ArrayList());
				}
				return null;
			}
		});
		return pager;
	}
	
	
	@Override
	public Pager findContent(final Pager pager, Content content, String contentType,
			String orderType) {
		String sql = "select c.*  From  Content c left join OTHER  o  on c.code = o.contentCode  " +
				" {fieldFilter} " +
				" where 1=1 and c.avaliable=1  {typeFilter} {keyword} " +  
				"GROUP BY c.`code` order by {orderFilter} ";
		//System.out.println(contentType);
		//System.out.println(orderType);
		sql = parseContentType(contentType,sql);
		sql = parseOrderAndField(orderType,sql);
		sql = parseKeyword(content,sql);
		final String sqlStr=sql;
		this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session arg0)
					throws HibernateException, SQLException {
				SQLQuery query = arg0.createSQLQuery(sqlStr);
				query.addEntity(Content.class);
				query.setMaxResults(pager.getPageSize());
				query.setFirstResult(pager.getStartIndex());
				String pageSql="select count(*) from ( "+sqlStr+" ) t";
				pager.setTotalCount(getJdbcTemplate().queryForInt(pageSql));
				if (query.list().size() > 0) {
					pager.setDataList(query.list());
				} else {
					pager.setDataList(new ArrayList());
				}
				return null;
			}
		});
		return pager;
	}

	private String parseKeyword(Content content, String sql) {
		//{keyword} and (title like "zhang" or title like "wang" ) 
	
		boolean hasKeyword=false;
		String rs = "" ;
		if(StringUtils.isNotBlank(content.getContent()))
		{
			hasKeyword=true;
			rs += createliks(content.getContent() ,"content");			
		}
		if(StringUtils.isNotBlank(content.getKeywords()))
		{
			hasKeyword=true;
			rs += createliks(content.getKeywords() ,"keywords");			
		}
		if(StringUtils.isNotBlank(content.getAuthorCode()))
		{
			hasKeyword=true;
			rs+=createliks(content.getAuthorCode() ,"authorCode");			
		}
		if(StringUtils.isNotBlank(content.getContentTitle()))
		{
			hasKeyword=true;
			rs+=createliks(content.getContentTitle() ,"contentTitle");			
		}
		if(StringUtils.isNotBlank(content.getCode()))
		{
			hasKeyword=true;
			rs+=createliks(content.getCode() ,"c.code");			
		}
		String	sqlLike="";
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


	@Override
	public Pager findPagerTravel(Pager pager, Integer order, String programCode,String des,String view,String keyword,Integer isoffical) {
		StringBuffer sb  = new StringBuffer();
		sb.append("select  st.*,(select count(*)  from reply WHERE  reply.postCode  =  st.`code`) as replyCount  from (");
		sb.append("SELECT distinct c.isOfficial as isOfficial,c.url as url, m.pic as pic,c.contentTitle  as   travelTitle,m.name userName,m.memberCode memberCode ,c.createTime as createTime,c.code, ");
		sb.append("  p.falseViewcount as  viewCount,  p.falseFavoriteNum  as faveteCount,p.falsepraise as  praiseCount , ");
		sb.append("  c.content as  travelContent ");
		sb.append("   from content  c  left join   praise  p on   p.contentcode = c.code left join    member_info m  on m.memberCode    =  c.createuserCode    ");
		sb.append("  where 1=1 and  c.state=1  and  c.avaliable=1  and c.contentType='strategy' ");
		if (isoffical!=null) {
			sb.append(" and  c.isOfficial="+isoffical);
		}
		if (programCode!=null&&!programCode.equals("")) {
			sb.append(" and  c.programaCode='"+programCode+"'");
		}
		
		sb.append(" ) st  where 1=1 ");
		
		sb.append(" and  st.code  IN( select contentcode  from  strategy_view  sv WHERE   1 = 1  ");
		
		
		if (des!=null&&!des.equals("")) {
			sb.append(" and sv.destinationCode ='"+des+"'");
		}
		if (view!=null&&!view.equals("")) {
			sb.append(" and sv.viewCode  ='"+view+"'");
		}
		sb.append(" ) ");
		if (keyword!=null&&!keyword.equals("")) {
			sb.append(" and (st.travelTitle like '%"+keyword+"%' )");
		}
		
		if(order!=null&&order==3){
			//评论
			sb.append(" order by (select count(*)  from reply WHERE  reply.postCode  =  st.`code` ) desc ");
		}
		else if (order!=null&&order==1) {
			//热门
			//sb.append(" order by st.viewCount desc");
			sb.append(" order by st.createTime desc");
		}else if(order!=null&&order==2){
			//收藏
			sb.append(" order by st.faveteCount  desc");
		}else {
			sb.append(" order by st.viewCount desc");
		}
		if(order==null){
		    //sb.append(" order by createTime desc");
		}
		return this.findListPagerBySql(TravalFrontPageVo.class, pager,sb.toString());
	}
	
    /**
     * 通过 postCode和审核状态 内容类型获取回复列表
     */
    public Pager getReplyByPostCodeState(Pager pager, String postCode, String contentType, int state){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("  ");
        sql.append(" SELECT ");
        sql.append("     c.code as contentCode,c.content as content, c.state as state, c.createTime as createTime, ");
        sql.append("     m.code as memberCode,m.name as memberName,m.sex as memberSex,m.pic as memberPic ");
        sql.append(" FROM content c, reply r, v_member_detail m ");
        sql.append(" WHERE c.avaliable=1  ");
        if(StringUtil.isNotNull(contentType)){
            sql.append(" AND c.contentType=? "); 
            params.add(contentType);
        }
        sql.append(" AND c.code=r.contentCode  ");
        sql.append(" AND c.createuserCode=m.code ");
        sql.append(" AND r.postCode=? ");
        params.add(postCode);
        sql.append(" AND r.postCode is not null AND r.postCode!='' ");
        if(state==0){
            sql.append(" AND c.state=0 ");
        }else if(state==1){
            sql.append(" AND c.state=1 ");
        }else if(state==-1){
            sql.append(" AND c.state=-1 ");
        }
        sql.append(" ORDER BY createTime DESC ");
        //sql.append(" ORDER BY createTime ASC ");
        return findListPagerBySql(ReplyVO.class, pager, sql.toString(), params);
    }
	

	@Override
	public TravalFrontPageVo getTravalFrontPageVo(String code) {
		// TODO Auto-generated method stub
		StringBuffer sb  = new StringBuffer();
		sb.append("select  st.*,(select count(*)  from reply left join content c on c.`code` = reply.contentCode  WHERE  reply.postCode  =  st.`code`  and c.state=1) as replyCount  from (");
		sb.append("SELECT distinct c.programaCode as programaCode,c.isOfficial as isOfficial, c.url as url,m.sex as sex,m.pic as pic , c.contentTitle  as   travelTitle,m.name userName,c.createTime as createTime,c.code code,m.memberCode as memberCode ,");
		sb.append("  p.falseViewcount as  viewCount,  p.falseFavoriteNum  as faveteCount,p.falsepraise as  praiseCount , ");
		sb.append("  c.content as  travelContent ");
		sb.append("   from content  c  left join   praise  p on   p.contentcode = c.code left join    member_info m  on m.memberCode    =  c.createuserCode    ");
		sb.append("  where 1=1 and  c.state=1  and  c.avaliable=1  and c.contentType='strategy' ");
		
		sb.append(" ) st  where 1=1 ");
		
		sb.append(" and  st.code  IN( select contentcode  from  strategy_view  sv WHERE   1 = 1  ");
		sb.append(" ) ");
		
		if (code!=null&&!code.equals("")) {
			sb.append(" and st.code ='"+code+"'");
		}
		
		List<TravalFrontPageVo> travalFrontPageVos  = this.findListBySql(TravalFrontPageVo.class, sb.toString());
		if (travalFrontPageVos!=null&&travalFrontPageVos.size()>=1) {
			return travalFrontPageVos.get(0);
		}
		return null;
	}

	@Override
	public Pager getFrontReplyVo(Pager pager,String postCode) {
		// TODO Auto-generated method stub
		StringBuffer sb   = new StringBuffer();
		sb.append("SELECT  c.createuserCode as replyUserCode,m.sex as replYUserSex,c.content  as  replyContent,m.`name`  as  userName,m.pic  as userImgUrl ,c.createTime  as  createTime from content c  LEFT  join  member_info m  on   m.memberCode =  c.createuserCode   where  1=1 and c.state=1 ");
		if (postCode!=null&&!postCode.equals("")) {
			sb.append(" and c.code    in  (");
			sb.append(" SELECT r.contentCode   from reply   r  where   r.postcode  =?");
			sb.append(")");
		}
		sb.append(" order by c.createTime desc ");
		return findListPagerBySql(FrontReplyVo.class, pager, sb.toString(),postCode);
	}
	

	@Override
	public List<TravalFrontPageVo> getStrageList(Integer row, String code) {
	
		// TODO Auto-generated method stub
		StringBuffer sb   = new StringBuffer();
		sb.append(" select distinct c.url  as  url, c.contentTitle as travelTitle   from  content  c     left join    praise p on c.code  =   p.contentcode ");
		sb.append(" where 1 = 1  and c.state=1  and avaliable=1 and  c.code!='"+code+"'  and  c.`code` in   (");
		
		sb.append(" select DISTINCT  s.contentCode  from  strategy_view  s where  s.destinationCode   ");
		
		
		sb.append(" in  (select  destinationCode  FROM  strategy_view  where   contentCode = '"+code+"' and destinationCode is not  NULL AND destinationCode!=''  and ( viewCode  is NULL or viewCode =''  ) ) ");
		
		sb.append("or s.viewCode  IN ");
		
		sb.append(" (select  viewCode  FROM  strategy_view  where   contentCode = '"+code+"' and viewCode is not  NULL   and viewCode!='' and  destinationCode is   not null and destinationCode !='' ) ");
		
		
		sb.append("   ) ");
		sb.append(" order by p.falseViewcount desc  ");
		sb.append("   limit  0,"+row);
		return this.findListBySql(TravalFrontPageVo.class, sb.toString());
	}
	@Override
	public List<FrontViewAndDesVo> getFrontViewAndDesList(String searchType,
			String code) {
		// TODO Auto-generated method stub
		StringBuffer sb  =  new StringBuffer();
		sb.append(" select    des.destinationName as desName , des.linkUrl  as desUrl  ,v.viewName as viewName,v.linkUrl as viewUrl,v.img as viewImg   from  strategy_view   s    ");
		sb.append("  left join  destination des on  s.destinationCode = des.code and  des.avaliable=1  ");
		sb.append(" left join   tview   v  on      v.code  =   s.viewCode   and  v.avaliable=1    ");
		sb.append(" where 1=1 ");
		if (StringUtil.isNotNull(code)) {
			sb.append("  and s.contentCode='"+code+"'");
		}
		if (searchType.equals(Constants.SEARCH_DES)) {
			sb.append(" and  s.destinationCode is  NOT NULL and  ( s.viewCode is null   OR  s.viewCode  = '' ) ");
			
		}else{
			sb.append(" and  s.destinationCode is  NOT NULL and ( s.viewCode is not  null and  s.viewCode !='' )");
			
		}
		return this.findListBySql(FrontViewAndDesVo.class, sb.toString());
	}

	@Override
	public Pager getMemberStraByState(String memberCode, String state,
			String programaCode, Pager pager) {
		// TODO Auto-generated method stub
		List<Object> params =new ArrayList<Object>();
		String sql = "SELECT c.contentTitle,c.code,c.state,c.url,p.programaName as programaName,p.`code` AS programaCode "
			+"FROM content c ,programa p "
			+"WHERE c.programaCode = p.code "
			 +"AND c.avaliable = '1'  AND c.contentType ='strategy' AND c.createuserCode=?";
			params.add(memberCode);
		if(StringUtils.isNotBlank(state)){
			params.add(state);
			sql+=" AND c.state = ?";
		}
		if(StringUtils.isNotBlank(programaCode)){
			params.add(programaCode);
			sql+=" AND p.code = ? ";
		}
		sql+="order by c.createTime desc";
		  return findListPagerBySql(Content.class, pager, sql, params);
			
	}

	@Override
	public Integer getReplyCount(final Date start, final Date end) {
		Integer  replyCount  = getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException,
					SQLException {
				      String sql  = "select   count(*)  from  content  c      where c.state = 1  and c.contentType  in  (select   enName from programa  where   programaSummary ='reply_manage' and programa.available = 1)  and c.avaliable =1  " ;
				      if(start!=null){
				    	  sql  =  sql +" and  DATE_FORMAT(c.createTime,'%Y-%m-%d')>='"+VeDate.toDateString(start)+"'";
				      }
				      if(end!=null){
				    	  sql  =  sql +" and  DATE_FORMAT(c.createTime,'%Y-%m-%d')<='"+VeDate.toDateString(end)+"'";
				      }
				      Query query  = session.createSQLQuery(sql);
				      BigInteger  bigInteger  = (BigInteger) query.uniqueResult();
						return   Integer.valueOf(bigInteger.toString());
			}
		});
		return replyCount; 
	}

	
	
	@Override
	public void updateOrSaveOthers(String contentCode, Map map) {
		operateOthers(map, contentCode);
	}
	
	
    /**
     * 通过评论类型和审核状态搜索 评论回复管理列表
     */
    public Pager searchReplyByContentTypeState(Pager pager, String contentType, int state){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("  ");
        sql.append(" SELECT ");
        sql.append("     r.postCode, ");
        sql.append("     p.code as programaCode, p.programaName, p.enName as programaEnName, ");
        sql.append("     c.code as contentCode, c.content, c.contentType, c.createTime, c.state, ");
        sql.append("     m.code as memberCode, m.name as memberName, m.pic as memberPic, m.sex as memberSex ");
        sql.append(" FROM programa p  ");
        sql.append("     inner join content c ON p.enName=c.contentType AND p.programaSummary='reply_manage' ");
        sql.append("     inner JOIN v_member_detail m ON c.createuserCode=m.code ");
        sql.append("     inner join reply r ON c.code=r.contentCode ");
        sql.append(" WHERE p.available=1 AND c.avaliable=1 AND m.avaliable=1 ");
        if(state==Content.REVIEW_STATE_WAIT){
            sql.append("     AND c.state=? ");
            params.add(Content.REVIEW_STATE_WAIT);
        }
        if(state==Content.REVIEW_STATE_PASS){
            sql.append("     AND c.state=? ");
            params.add(Content.REVIEW_STATE_PASS);
        }
        if(state==Content.REVIEW_STATE_NOT_PASS){
            sql.append("     AND c.state=? ");
            params.add(Content.REVIEW_STATE_NOT_PASS);
        }
        if(StringUtil.isNotNull(contentType)){
            sql.append("     AND c.contentType=? ");
            params.add(contentType);
        }
        sql.append(" ORDER BY c.id DESC ");
        return findListPagerBySql(ReplyManageVO.class, pager, sql.toString(), params);
    }
    
    @Override
    public int findReplyNumByContentTypeState(String contentType, int state){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("  ");
        sql.append(" SELECT ");
        sql.append("     r.postCode, ");
        sql.append("     p.code as programaCode, p.programaName, p.enName as programaEnName, ");
        sql.append("     c.code as contentCode, c.content, c.contentType, c.createTime, c.state, ");
        sql.append("     m.code as memberCode, m.name as memberName, m.pic as memberPic, m.sex as memberSex ");
        sql.append(" FROM programa p  ");
        sql.append("     inner join content c ON p.enName=c.contentType AND p.programaSummary='reply_manage' ");
        sql.append("     inner JOIN v_member_detail m ON c.createuserCode=m.code ");
        sql.append("     inner join reply r ON c.code=r.contentCode ");
        sql.append(" WHERE p.available=1 AND c.avaliable=1 AND m.avaliable=1 ");
        if(state==Content.REVIEW_STATE_WAIT){
            sql.append("     AND c.state=? ");
            params.add(Content.REVIEW_STATE_WAIT);
        }
        if(state==Content.REVIEW_STATE_PASS){
            sql.append("     AND c.state=? ");
            params.add(Content.REVIEW_STATE_PASS);
        }
        if(state==Content.REVIEW_STATE_NOT_PASS){
            sql.append("     AND c.state=? ");
            params.add(Content.REVIEW_STATE_NOT_PASS);
        }
        if(StringUtil.isNotNull(contentType)){
            sql.append("     AND c.contentType=? ");
            params.add(contentType);
        }
        sql.append(" ORDER BY c.id DESC ");
        return findCountBySql(sql.toString(), params);
    }
    
    
    
    @Override
	public Integer getStrageCount(final Date start, final Date end) {
		Integer  replyCount  = getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException,
					SQLException {
				      String sql  = "select   count(*)  from  content  c      where  c.state=1 and c.contentType='strategy'   and c.avaliable =1  " ;
				      if(start!=null){
				    	  sql  =  sql +" and  DATE_FORMAT(c.createTime,'%Y-%m-%d')>='"+VeDate.toDateString(start)+"'";
				      }
				      if(end!=null){
				    	  sql  =  sql +" and  DATE_FORMAT(c.createTime,'%Y-%m-%d')<='"+VeDate.toDateString(end)+"'";
				      }
				      Query query  = session.createSQLQuery(sql);
				      BigInteger  bigInteger  = (BigInteger) query.uniqueResult();
						return   Integer.valueOf(bigInteger.toString());
			}
		});
		return replyCount; 
	}
    
    @SuppressWarnings("unchecked")
	@Override
    public List<Content> findContentByProgramaCode(String programaCode) {
    	Map<String, Object> Param = new HashMap<String, Object>();
    	StringBuffer hql = new StringBuffer("FROM Content c WHERE c.avaliable=1 AND c.programaCode=:programaCode ");
    	Param.put("programaCode", programaCode);
    	return find(hql.toString(), Param);
    }

    @Override
    public int getMemberTodayScore(String memberCode) {
       // StringBuffer sql = new StringBuffer();
       String sql="SELECT ifnull(SUM(score),0) FROM sysmessage WHERE msgTo='" + memberCode + "' AND DATE_FORMAT(createDate,'%Y%m%d')=DATE_FORMAT(NOW(),'%Y%m%d')";
        return getJdbcTemplate().queryForObject(sql, Integer.class);
    }
	
    @Override
    public int getSpecialMaxSortNum() {
        String sql= "SELECT ifnull(max(sortNum),0) FROM content WHERE avaliable=1 AND contentType='special'";
        return getJdbcTemplate().queryForObject(sql, Integer.class);
    }

    public static void main(String[] args) {
    	
    }

    @Override
    public void deleteReplyByPostCodeLogical(final String postCode, final String contentType) {
        getHibernateTemplate().execute(new HibernateCallback<Object>() {
            public Object doInHibernate(Session arg0) throws HibernateException, SQLException {
                StringBuffer sql = new StringBuffer();
                sql.append("  ");
                sql.append(" UPDATE content SET avaliable=0 WHERE avaliable=1");
                
                if(StringUtil.isNotNull(contentType)){
                    sql.append(" AND contentType=:contentType ");
                } 
                
                sql.append(" AND code in ( ");
                sql.append("     SELECT contentCode FROM reply WHERE postCode=:postCode ");
                sql.append(" ) ");
                SQLQuery sqlQuery = arg0.createSQLQuery(sql.toString());
                sqlQuery.setParameter("postCode", postCode);
                
                if(StringUtil.isNotNull(contentType)){
                    sqlQuery.setParameter("contentType", contentType);
                }
                return sqlQuery.executeUpdate();
            }
        });
    }
   public static void testPa(String a,Object...array){
	   for (int i = 0; i < array.length; i++) {
	   }
   }
  
}

