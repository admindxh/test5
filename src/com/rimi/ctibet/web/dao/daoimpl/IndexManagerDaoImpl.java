package com.rimi.ctibet.web.dao.daoimpl;

import java.net.IDN;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.ehcache.store.FifoMemoryStore;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.Connection;
import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.HtmlRegexpUtil;
import com.rimi.ctibet.common.util.ImgUrlUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.Uuid;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.IndexManager;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.Praise;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.portal.controller.vo.BbsIndexManagerP;
import com.rimi.ctibet.web.controller.vo.Culture;
import com.rimi.ctibet.web.dao.IContentDao;
import com.rimi.ctibet.web.dao.IMemberDao;
import com.rimi.ctibet.web.dao.IPraiseDao;
import com.rimi.ctibet.web.dao.IProgramaDao;
import com.rimi.ctibet.web.dao.IndexManagerDao;

import freemarker.template.SimpleDate;

@Repository("indexManagerDao")
public class IndexManagerDaoImpl extends BaseDaoImpl<IndexManager> implements
		IndexManagerDao {

	@Resource
	private IProgramaDao programaDao;

	@Resource
	private IMemberDao memberDao;

	@Resource
	private IPraiseDao praiseDao;

	@Resource
	private IContentDao contentDao;

	@Override
	public void initIndexManager(final String proramCode,
			final String contentType, final Integer row) {
		@SuppressWarnings("unused")
		Object o = getHibernateTemplate().execute(
				new HibernateCallback<Object>() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						String hql = "delete  from  IndexManager index where index.avaliable=1 and    index.pramaCode=:pramaCode";
						Query query = session.createQuery(hql);
						query.setParameter("pramaCode", proramCode);
						return query.executeUpdate();
					}
			});
		  List<Content> clist = getHibernateTemplate().execute(
				new HibernateCallback<List<Content>>() {
					@Override
					public List<Content> doInHibernate(Session session)
							throws HibernateException, SQLException {
						// 创建取新的数据
						String hqlnew = " from Content c where c.avaliable=1   ";
						if (contentType != null && !contentType.equals("")) {
							hqlnew = hqlnew
									+ "   and c.contentType =:contentType  ";
						}
						hqlnew += "  order by createTime desc limit 0," + row;
						Query hqlnewQuery = session.createQuery(hqlnew);
						if (contentType != null && !contentType.equals("")) {
							hqlnewQuery
									.setParameter("contentType", contentType);
						}
						hqlnewQuery.setMaxResults(row);
						hqlnewQuery.setFirstResult(0);
						return hqlnewQuery.list();
					}
				});

		for (Content content : clist) {
			IndexManager indexManager = new IndexManager();
			indexManager.setAvaliable(1);
			indexManager.setCode(Uuid.uuid());
			indexManager.setNumber(content.getCode());
			indexManager.setUrl(content.getUrl());
			indexManager.setPramaCode(proramCode);
			indexManager.setCreateTime(new Date());
			this.save(indexManager);
		}
	}

	@Override
	public void initIndexManagerbyScore(final String proramCode,
			String contentType, Integer row) {
		Object o = getHibernateTemplate().execute(
				new HibernateCallback<Object>() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						String hql = "delete  from  IndexManager index where index.avaliable=1 and    index.pramaCode=:pramaCode";
						Query query = session.createQuery(hql);
						query.setParameter("pramaCode", proramCode);
						return query.executeUpdate();
					}
				});

		// 创建取新的数据
		/*
		 * String hqlnew = " from Content c where c.avaliable=1  left join   ";
		 * if (contentType!=null&&!contentType.equals("")) { hqlnew = hqlnew +
		 * "   and c.contentType =:contentType  "; } hqlnew+=
		 * "  order by createTime desc limit 0,"+row; Query hqlnewQuery =
		 * this.getSession().createQuery(hqlnew); if
		 * (contentType!=null&&!contentType.equals("")) {
		 * hqlnewQuery.setParameter("contentType", contentType); }
		 * query.setMaxResults(row); query.setFirstResult(0);
		 */
		String sql = "select * from ";

		Pager pager = new Pager();
		pager.setPageSize(row);
		contentDao.findContent(pager, new Content(), contentType, "205");
		List<Content> clist = pager.getDataList();

		for (Content content : clist) {
			IndexManager indexManager = new IndexManager();
			indexManager.setAvaliable(1);
			indexManager.setCode(Uuid.uuid());
			indexManager.setUrl(content.getUrl());
			indexManager.setNumber(content.getCode());
			indexManager.setPramaCode(proramCode);
			indexManager.setCreateTime(new Date());
			this.save(indexManager);
		}
	}

	@Override
	public void updateIndexManager(IndexManager indexManager) {
		// TODO Auto-generated method stub

		IndexManager upIndex = null;
		String hql = "from " + domainClass.getName()
				+ " where code=? and avaliable=" + indexManager.getAvaliable();
		List<IndexManager> list = getHibernateTemplate().find(hql,
				indexManager.getCode());
		upIndex = (list != null && list.size() > 0) ? (list.get(0)) : (null);
		if (upIndex != null) {
			upIndex.setUrl(indexManager.getUrl());
			upIndex.setAvaliable(indexManager.getAvaliable());
			upIndex.setNumber(indexManager.getNumber());

			upIndex.setReplycontent(indexManager.getReplycontent());
			upIndex.setReplyname(indexManager.getReplyname());
			upIndex.setPraise(indexManager.getPraise());
			this.update(upIndex);
		} else {
			IndexManager indexManager1 = new IndexManager();
			indexManager1.setAvaliable(indexManager.getAvaliable());
			indexManager1.setCode(Uuid.uuid());
			indexManager1.setUrl(indexManager.getUrl());
			indexManager1.setNumber(indexManager.getNumber());
			indexManager1.setPramaCode(indexManager.getPramaCode());
			indexManager1.setReplycontent(indexManager.getReplycontent());
			indexManager1.setReplyname(indexManager.getReplyname());
			indexManager1.setPraise(indexManager.getPraise());
			indexManager1.setCreateTime(new Date());
			this.save(indexManager1);
		}
	}

	/**
	 * 内容类型查询 设置为 无效和有效的数据
	 */
	@Override
	public List<IndexManager> getListIndexManager(final String proramCode,
			final String avaliable) {
		// TODO Auto-generated method stub

		List<IndexManager> clist = getHibernateTemplate().execute(
    		new HibernateCallback<List<IndexManager>>() {
    			@Override
    			public List<IndexManager> doInHibernate(Session session) throws HibernateException, SQLException {
    				Query query = session.createQuery(" from  IndexManager index where  index.avaliable = 1    and  index.pramaCode=:pramaCode  order by  index.id  ");
    				if (avaliable != null && !avaliable.equals("")&& avaliable.length() <= 1) {
    					Integer a = 1;
    					a = Integer.valueOf(avaliable);
    					query = session.createQuery(" from IndexManager index where 1=1   and   index.pramaCode=:pramaCode  and  index.avaliable=" + a + " order by praise desc ");
    				}
    				query.setParameter("pramaCode", proramCode);
    				return query.list();
    			}
    		}
		);

		return clist;
	}

	@Override
	public List<Content> getListContent(String programcode, String contentType) {
		// TODO Auto-generated method stub
		List<IndexManager> list = this.getListIndexManager(programcode,
				contentType);
		List<Content> clist = new ArrayList<Content>();
		for (IndexManager indexManager : list) {
			String hql = " SELECT c.id, c.avaliable, c.code,   p.programaName as programaCode,    c.contentType,   c.contentTitle,    c.authorCode,    c.content,  c.tag,   c.createuserCode,  c.changeuserCode,    c.state,   c.checkuserCode,   c.createTime,   c.lastEditTime,   c.title,   c.description,   c.keywords,    c.isOfficial,    c.summary,    c.url,    c.sortNum,c.isTop FROM content c left join programa p on c.programaCode=p.code where c.avaliable=1  ";
			if (indexManager.getUrl() != null
					&& indexManager.getUrl().lastIndexOf(".html") != -1) {
				String code = indexManager.getUrl().substring(
						indexManager.getUrl().lastIndexOf("/") + 1,indexManager.getUrl().lastIndexOf(".html"));
				hql = hql + " and c.code='" + code + "'   ";
				List<Content> contentlist = this.findListBySql(Content.class,
						hql);
				if (contentlist != null && contentlist.size() >= 1) {
					contentlist.get(0).setImgUrl(
							ImgUrlUtil.getContentImgSrc(contentlist.get(0)
									.getContent()));
					
					if (programcode.equals("1327011a-75da-11e4-b6ce-005056a05bc9")) {
						
						contentlist.get(0).setImgUrl(
								contentlist.get(0).getTitle());
					}
					String  c  =  contentlist.get(0).getContent();
					contentlist.get(0).setContent(HtmlRegexpUtil.filterHtml(c));
					Content l  = contentlist.get(0);
					l.setOthers(contentDao.findContentOthers(l.getCode()));
					clist.add(contentlist.get(0));
				} else {
					String code1 = indexManager.getNumber();
					List<Object> pramas=new ArrayList<Object>();
					hql = " SELECT c.id, c.avaliable, c.code,   p.programaName as programaCode,    c.contentType,   c.contentTitle,    c.authorCode,    c.content,  c.tag,   c.createuserCode,  c.changeuserCode,    c.state,   c.checkuserCode,   c.createTime,   c.lastEditTime,   c.title,   c.description,   c.keywords,    c.isOfficial,    c.summary,    c.url,    c.sortNum,c.isTop FROM content c left join programa p on c.programaCode=p.code where c.avaliable=1  ";
					hql = hql + " and c.code=? ";
					pramas.add(code1);
					List<Content> contentlist1 = this.findListBySql(
							Content.class, hql,pramas);
					if (contentlist1 != null && contentlist1.size() >= 1) {
						String  c  =  contentlist1.get(0).getContent();
						contentlist1.get(0).setContent(HtmlRegexpUtil.filterHtml(c));
						contentlist1.get(0).setImgUrl(
								ImgUrlUtil.getContentImgSrc(contentlist1.get(0)
										.getContent()));
						if (programcode.equals("1327011a-75da-11e4-b6ce-005056a05bc9")) {
							
							contentlist1.get(0).setImgUrl(
									contentlist1.get(0).getTitle());
						}
						Content l  = contentlist1.get(0);
						l.setOthers(contentDao.findContentOthers(l.getCode()));
						clist.add(contentlist1.get(0));
					}
				}
			} else if (indexManager.getNumber() != null) {
				String code = indexManager.getNumber();
				List<Object> pramas=new ArrayList<Object>();
				hql = hql + " and c.code=?   ";
				pramas.add(code);
				List<Content> contentlist = this.findListBySql(Content.class,
						hql,pramas);
				if (contentlist != null && contentlist.size() >= 1) {
					contentlist.get(0).setImgUrl(
							ImgUrlUtil.getContentImgSrc(contentlist.get(0)
									.getContent()));
					if (programcode.equals("1327011a-75da-11e4-b6ce-005056a05bc9")) {
						contentlist.get(0).setImgUrl(
								contentlist.get(0).getTitle());
					}
					//取文本内容过滤
					//contentlist.get(0).setfals
					String  c  =  contentlist.get(0).getContent();
					Content l  = contentlist.get(0);
					l.setOthers(contentDao.findContentOthers(l.getCode()));
					contentlist.get(0).setContent(HtmlRegexpUtil.filterHtml(c));
					clist.add(contentlist.get(0));
				}

			}
			// 编号待定
		}
		return clist;
	}

	@Override
	public List<BbsIndexManagerP> getListPraiseContent(String programcode,
			String contentType) {
		// TODO Auto-generated method stub
		List<IndexManager> list = this.getListIndexManager(programcode,
				contentType);
		List<Content> clist = new ArrayList<Content>();
		List<BbsIndexManagerP> bbs = new ArrayList<BbsIndexManagerP>();
		for (IndexManager indexManager : list) {
			String hql = " SELECT c.id, c.avaliable, c.code,   p.programaName as programaCode,    c.contentType,   c.contentTitle,    c.authorCode,    c.content,  c.tag,   c.createuserCode,  c.changeuserCode,    c.state,   c.checkuserCode,   c.createTime,   c.lastEditTime,   c.title,   c.description,   c.keywords,    c.isOfficial,    c.summary,    c.url,    c.sortNum,c.isTop FROM content c left join programa p on c.programaCode=p.code where c.avaliable=1  ";
			if (indexManager.getUrl() != null
					&& indexManager.getUrl().lastIndexOf(".html") != -1) {
				String code = indexManager.getUrl().substring(
						indexManager.getUrl().lastIndexOf("/") + 1,indexManager.getUrl().lastIndexOf(".html"));
				List<Object> params=new ArrayList<Object>();
				hql = hql + " and c.code=? ";
				params.add(code);
				List<Content> contentlist = this.findListBySql(Content.class,
						hql,params);
				if (contentlist != null && contentlist.size() >= 1) {
					BbsIndexManagerP bbsIndexManagerP = new BbsIndexManagerP();
					bbsIndexManagerP.setIndexManager(indexManager);
					bbsIndexManagerP.setPost(contentlist.get(0));
					bbs.add(bbsIndexManagerP);
				}
			} else if (indexManager.getNumber() != null) {
				String code = indexManager.getNumber();
				List<Object> params=new ArrayList<Object>();
				hql = hql + " and c.code=?   ";
				params.add(code);
				List<Content> contentlist = this.findListBySql(Content.class,
						hql,params);
				if (contentlist != null && contentlist.size() >= 1) {
					BbsIndexManagerP bbsIndexManagerP = new BbsIndexManagerP();
					bbsIndexManagerP.setIndexManager(indexManager);
					bbsIndexManagerP.setPost(contentlist.get(0));
					bbs.add(bbsIndexManagerP);
				}
			}
			// 编号待定
		}
		return bbs;
	}

	@Override
	public void deleteProgram(final String pro,Integer avlable) {
		// TODO Auto-generated method stub
		final String hql1 = "delete  from  IndexManager index where index.avaliable="+avlable+" and   index.pramaCode=:pramaCode";
		
		getHibernateTemplate().execute(new HibernateCallback<Object>() {
				@Override
				public Object doInHibernate(Session arg0) throws HibernateException,
						SQLException {
					Query query1 = arg0.createQuery(hql1);
					query1.setParameter("pramaCode", pro);
				    return 	query1.executeUpdate();
				}
		});
		
		
		
	}

	@Override
	public void initIndexManagerbyPraise(final String proramCode, final String contentType,
			final Integer row) {
    	Object  o  =    getHibernateTemplate().execute(
					new HibernateCallback<Object>() {
						@Override
						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {
							String hql = "delete  from  IndexManager index where index.avaliable=1 and    index.pramaCode=:pramaCode";
							Query query = session.createQuery(hql);
							query.setParameter("pramaCode", proramCode);
						  return 	query.executeUpdate();
						}
					});
		List<Object[]> clist =    getHibernateTemplate().execute(
				new HibernateCallback<List<Object[]>>() {
					@Override
					public List<Object[]> doInHibernate(Session session)
							throws HibernateException, SQLException {
						// 创建取新的数据
						String hqlnew = "select c.code,c.content,(select   content.url from Content content  where  content.code     =  ((select  postCode   from Reply r where contentCode  = c.code  ) )) as url,c.createuserCode,date_format(c.createTime,'%Y-%m-%d %h-%m-%s')   from Content c,Praise praise  where c.avaliable=1   ";
						if (contentType != null && !contentType.equals("")) {
							hqlnew = hqlnew + "   and c.contentType =:contentType  ";
						}
						hqlnew += " and  praise.contentCode=c.code  order by praise.falsePraise desc limit 0,"
								+ row;
						
						Query hqlnewQuery = session.createQuery(hqlnew);
						if (contentType != null && !contentType.equals("")) {
							hqlnewQuery.setParameter("contentType", contentType);
						}
						hqlnewQuery.setFirstResult(0);
						hqlnewQuery.setMaxResults(row);
						
						return hqlnewQuery.list();
					}
				});   
			
			
		 for (Object[] c : clist) {
			final String[] content = Arrays.asList(c).toArray(new String[0]);
			IndexManager indexManager = new IndexManager();
			indexManager.setAvaliable(1);
			indexManager.setCode(Uuid.uuid());
			indexManager.setUrl(content[2]);
			indexManager.setPramaCode(proramCode);
			// 回复内容
			indexManager.setReplycontent(content[1]);
			// 回复人
			List<LogUser> loguser = memberDao.findLogUserByCode(content[3]);
			LogUser user = loguser != null && loguser.size() >= 1 ? loguser
					.get(0) : null;
			if (user != null) {
				indexManager.setReplyname(user.getUsername());
			}
			// 赞数
			Praise p = praiseDao.getPraiseContentCode(content[0]);
			if (p != null) {
				if (p.getFalsePraise()!=null) {
					
					indexManager.setPraise(p.getFalsePraise().toString());
				}
			} else {
				indexManager.setPraise("0");
			}
			List list =  getHibernateTemplate().execute(
					new HibernateCallback<List>() {
						@Override
						public List  doInHibernate(Session session)
								throws HibernateException, SQLException {
							// 帖子地址
							Query q = session.createSQLQuery("SELECT content.url from content WHERE  content.`code`  = (SELECT  postCode  from reply where reply.contentCode  =? LIMIT 0,1)");
							q.setParameter(0, content[0]);
							return  q.list();
						}
					});
			String url = (String) (list != null && list.size() >= 1 ? list
					.get(0) : "");
			indexManager.setUrl(url);
			if (indexManager.getUrl() != null
					&& indexManager.getUrl().lastIndexOf(".html") != -1) {
				String code = indexManager.getUrl().substring(
						indexManager.getUrl().lastIndexOf("/") + 1,indexManager.getUrl().lastIndexOf(".html"));
				indexManager.setNumber(code);
			}
			String  date  =  content[4];
			
				indexManager.setNumber(date);
			this.save(indexManager);
		}

	}

	@Override
	public  List<Object[]> getOrderManager(String proramCode, final String contentType,
			final Integer row) {
		List<Object[]> clist =    getHibernateTemplate().execute(
				new HibernateCallback<List<Object[]>>() {
					@Override
					public List<Object[]> doInHibernate(Session session)
							throws HibernateException, SQLException {
						
						// 创建取新的数据
						StringBuffer sb  = new StringBuffer();
						sb.append("SELECT  count(c.falsepraise),GROUP_CONCAT(rowNo),GROUP_CONCAT(c.code),GROUP_CONCAT(date_format(c.createTime,'%Y-%m-%d %h-%m-%s'))     from   ");
						sb.append(" ( ");
						sb.append("  SELECT   * from  ( ");
						
						sb.append("select  c.code as  code,c.createTime  as createTime ,praise.falsepraise  as  falsepraise ,(@rowNum:=@rowNum+1) as rowNo  from Content c,Praise praise, ");
						sb.append(" (Select (@rowNum :=-1) ) b where c.avaliable=1    ");
						if (contentType != null && !contentType.equals("")) {
							sb.append("  and c.contentType =? ");
						}
						sb.append(" and  praise.contentCode=c.code   order by praise.falsePraise desc limit 0,"
								+ row);
						sb.append(" )   h     order by  h.createTime DESC ");
						
						sb.append(" ) ");
						sb.append(" c   GROUP BY c.falsepraise   HAVING count(c.falsepraise)>=2    ");
						
						  java.sql.Connection con = session.connection();        
                         PreparedStatement ps = con.prepareStatement(sb.toString());     
                         if (contentType != null && !contentType.equals("")) {
                        	 ps.setObject(1, contentType);
 							}
                         ResultSet rs = ps.executeQuery();     
                         String rowNum = "";  
                         List<Object[]> list = new ArrayList<Object[]>();  
                         Object[] obj = null;  
                       while(rs.next()){   
		                     obj = new Object[4];  
		                     rowNum = rs.getString(1);  
		                     obj[0] = rowNum ; 
		                     rowNum = rs.getString(2);   
		                     obj[1] = rowNum ;  
		                     rowNum = rs.getString(3);   
		                     obj[2] = rowNum ;  
		                     rowNum = rs.getString(4);   
		                     obj[3] = rowNum ;  
		                     
		                     list.add(obj);  
                       }        
                        rs.close();        
                        ps.close();        
                        session.flush();        
                         session.close();  
                         	return list;  
						
						
						
						
					}
				});   
		  return clist;
	}

	@Override
	public List<Content> getContentListByNumber(final int row,final String order, final String contentType,String desCode) {
		// 创建取新的数据
		List<Object> params=new ArrayList<Object>();
		String hqlnew = "select c.code as content,c.url from Content c left   join  praise  p  on p.contentcode =  c.code       where c.avaliable=1 AND  c.state =1  ";
		if (contentType != null && !contentType.equals("")) {
			hqlnew = hqlnew
					+ "    and c.contentType =? ";
			params.add(contentType);
		}
		if (StringUtils.isNotBlank(desCode)) {
			 hqlnew+= " AND   (   c.code  in  (select contentCode  from strategy_view  sv where   sv.destinationCode ='"+desCode+"' ) ) ";
		}
		//表示 收藏数
		if (StringUtils.isNotBlank(order)&&order.equals("1")) {
			 hqlnew  =  hqlnew  +" order by   p.falseFavoriteNum      desc  ";
			 //查看数
		}else if(StringUtils.isNotBlank(order)&&order.equals("2")){
			hqlnew  =  hqlnew  +" order by   p.falseViewcount     desc  ";
		}
		
		
		hqlnew += "   limit 0," + row;;
	    
		
		return  super.findListBySql(Content.class, hqlnew,params);
	}


    @Override
    public List<IndexManager> getMostPraise(int row) {
        StringBuffer sql = new StringBuffer();
        sql.append("  ");
        sql.append(" SELECT ");
        sql.append("     post.url AS url, ");
        sql.append("     re.content AS replycontent, ");
        sql.append("     member.name AS replyname, ");
        sql.append("     p.truepraise AS praise ");
        sql.append(" FROM content re  ");
        sql.append(" LEFT JOIN praise p ON re.code = p.contentcode AND p.available = 1 ");
        sql.append(" INNER JOIN reply r ON re.code = r.contentCode ");
        sql.append(" INNER JOIN content post ON post.avaliable = 1 AND post.state = 1 AND post.code = r.postCode ");
        sql.append(" INNER JOIN v_member_base member ON re.createuserCode = member.code AND member.avaliable=1 ");
        sql.append(" WHERE re.avaliable = 1 AND re.state = 1 AND re.contentType='reply' ");
        sql.append(" ORDER BY p.truepraise DESC, re.createTime DESC ");
        return findListBySqlRow(IndexManager.class, row, sql.toString());
    }

}
