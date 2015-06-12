package com.rimi.ctibet.web.dao.daoimpl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.aspectj.weaver.patterns.IfPointcut;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.Uuid;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Praise;
import com.rimi.ctibet.web.dao.IPraiseDao;
import com.rimi.ctibet.web.dao.IProgramaContentDao;
import com.rimi.ctibet.web.dao.IReplyDao;
import com.rimi.ctibet.web.dao.IStrategyViewDao;
import com.rimi.ctibet.web.service.IContentService;

/**
 * 赞的回复页面
 * @author dengxh
 *
 */
/**
 * @author dengxh
 *
 */
@Repository(value="praiseDao")
public class PraiseDaoImpl extends BaseDaoImpl<Praise> implements IPraiseDao{
	@Resource  IContentService contentService;
	@Resource IStrategyViewDao strategyViewDao;
	@Resource IProgramaContentDao programaContentDao;
	@Resource IReplyDao replyDao;
	
	@Override
	public void cancelPraiseCount(String contentCode) {
		/*// TODO Auto-generated method stub
		Query   query  = this.getSession().createQuery("update from  Praise  p set truePraise = truePraise-1   where  p.contentcode=:contentcode and p.avaliable=1 ");
		query.setParameter("contentcode", contentCode);
		query.executeUpdate();*/
		
	}

	@Override
	public Pager findContentByProCode(Pager pager, String proCode) {
		// TODO Auto-generated method stub
		return contentService.findContentByProCode(pager, proCode);
	}

	@Override
	public void saveContentAndProgramaContent(Content content,
			String programaCode) {
		// TODO Auto-generated method stub
		contentService.saveContentAndProgramaContent(content,programaCode);
		
	}

	@Override
	public void savePraise(Praise praise) {
		// TODO Auto-generated method stub
		this.save(praise);
	}

	/**
	 * 后台修改赞
	 */
	@Override
	public void updatePraise(Praise praise) {
		// TODO Auto-generated method stub
		//this.updateLogical(praise);
		Praise  p  =  this.findByCode(praise.getCode());
		if (this.isValidExitPraise(praise.getContentCode())) {
			//p.setTruePraise(praise.getTruePraise());
			p.setFalsePraise(praise.getFalsePraise());
			p.setFalseFavoriteNum(praise.getFalseFavoriteNum());
			p.setFalseViewcount(praise.getFalseViewcount());
			this.update(p);
		}else{
			this.savePraise(praise);
			
		}
	}
	
	
	
	/**
	 * 前台赞更新
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updatePraiseCount(final String contentCode) {
		if (!this.isValidExitPraise(contentCode)) {
			Praise  praise = new Praise();
			praise.setAvaliable(1);
			praise.setCreateTime(new Date());
			praise.setCode(Uuid.uuid());
			praise.setContentCode(contentCode);
			praise.setTruePraise(1);
			praise.setFalsePraise(1);
			
			this.save(praise);
		}else{
			getHibernateTemplate().execute(
				new HibernateCallback() {
				  public Object doInHibernate(org.hibernate.Session session) throws org.hibernate.HibernateException ,java.sql.SQLException {
					  Query   query  = session.createQuery("update from  Praise  p set truePraise = (  IF(truepraise,truepraise,0)+1 ),falsePraise = (  IF(falsePraise,falsePraise,0)+1 )   where  p.contentCode=:contentcode and p.avaliable=1 ");
						query.setParameter("contentcode", contentCode);
						return query.executeUpdate();
				  };
				
				}
			);
		}
	}
	/**
	 * 前台浏览查看
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateViewCount(final String contentCode) {
		if (!this.isValidExitPraise(contentCode)) {
				Praise  praise = new Praise();
				praise.setAvaliable(1);
				praise.setCreateTime(new Date());
				praise.setCode(Uuid.uuid());
				praise.setContentCode(contentCode);
				praise.setViewCount(1);
				praise.setFalseViewcount(1);
				this.save(praise);
		}else{
			getHibernateTemplate().execute(
					new HibernateCallback() {
					  public Object doInHibernate(org.hibernate.Session session) throws org.hibernate.HibernateException ,java.sql.SQLException {
						    Query   query  = session.createQuery("update from  Praise  p set viewCount = viewCount+1,falseViewcount=falseViewcount+1   where  p.contentCode=:contentcode and p.avaliable=1 ");
							query.setParameter("contentcode", contentCode);
						    return	query.executeUpdate();
					  };
					
					}
				);
		 }
	}


	@Override
	public Praise findPraise(final Praise praise) {
		// TODO Auto-generated method stub
		
		
		
		
		List<Praise>   pList  = getHibernateTemplate().execute(new HibernateCallback<List<Praise>>() {
					@Override
					public List<Praise> doInHibernate(Session session) throws HibernateException,
							SQLException {
						Query query  =  session.createQuery(" from Praise  p  where p.avaliable = 1  and p.code =:code");
						query.setParameter("code", praise.getCode());
						return query.list();
					}
		});
		if (pList!=null&&pList.size()>=1) {
			return pList.get(0);
		}
		return null;
	}
	
	/**
	 * 查询赞分页后台
	 */
	@Override
	public Pager findPraiseList(List<Object> params, Pager pager) {
		// TODO Auto-generated method stub
		String sql  = "select  c.title title, (p.truepraise+p.falsepraise) vc  from content c  left join praise  p on c.code  = p.contentcode  AND c.contentType='reply'  and p.avaliable=1  where content.avaliable=1 ORDER BY  vc DESC";
		return this.findByJDBCSql(sql, params, pager);
	}
	
	
	@Override
	public void deleteContentAndProgramaContent(String[] contentCodes) {
		// TODO Auto-generated method stub
		contentService.deleteContentAndProgramaContent(contentCodes);
	}
	
	@Override
	public List<Map<String, Object>> getPraiseList(int row,List<Object> params) {
		String sql  = "select  c.title title, (p.truepraise+p.falsepraise) vc ,(select title  from content where code  = (select postCode  from reply  where contentCode = c.code )) tztitle,(select url  from content where  content.avaliable=1  and code  = (select postCode  from reply  where contentCode = c.code )) tzurl   from content c  left join praise  p on c.code  = p.contentcode  AND c.contentType='reply'  and p.avaliable=1  LEFT JOIN  member_info m on m.memberCode  = c.createuserCode   ORDER BY  vc DESC  limit 0,"+row;
		return this.findByJDBCSql(sql,params);
	}
	
	public IStrategyViewDao getStrategyViewDao() {
		return strategyViewDao;
	}

	public void setStrategyViewDao(IStrategyViewDao strategyViewDao) {
		this.strategyViewDao = strategyViewDao;
	}

	public IProgramaContentDao getProgramaContentDao() {
		return programaContentDao;
	}

	public void setProgramaContentDao(IProgramaContentDao programaContentDao) {
		this.programaContentDao = programaContentDao;
	}

	public IReplyDao getReplyDao() {
		return replyDao;
	}

	public void setReplyDao(IReplyDao replyDao) {
		this.replyDao = replyDao;
	}

	public IContentService getContentService() {
		return contentService;
	}

	public void setContentService(IContentService contentService) {
		this.contentService = contentService;
	}
	
	
	@Override
	public List<Map<String, Object>> getPraiseListReply(int row,
			List<Object> params) {
		String sql  = "select  c.title, ( select  (praise.truepraise+praise.falsepraise)  as  praisecount  from praise where contentcode  = c.code ) ,c.url url  from  content  c   where   c.avaliable=1 and  c.contentType  = 'post' and  c.code   in  ( select postcode  from reply   GROUP BY  postcode   ORDER BY     COUNT(postcode) DESC   )  limit 0,"+row;
		return this.findByJDBCSql(sql,params);
	}
	

	@Override
	public List<Map<String, Object>> getPraiseListPost(int row,
			List<Object> params) {
		// TODO Auto-generated method stub
		String  sql  = "select c.title title,(praise.truepraise+praise.falsepraise) praisecount, from content  c  left join    praise on  c.`code`  =  praise.contentcode  and  c.contentType  = 'post'  where c.avaliable=1 and order by   (praise.truepraise+praise.falsepraise)  DESC LIMIT 0,"+row;
		return this.findByJDBCSql(sql,params);
	}

	@Override
	public boolean isValidExitPraise(String coentcode) {
		// TODO Auto-generated method stub
		Praise p  = getPraiseContentCode(coentcode);
		return  p==null?false:true;
	}

	/**
	 * 通过contentCode获取Praise
	 * @param contentCode
	 * @return
	 */
	public Praise getPraiseContentCode(final String contentCode){
		final String hql = " from Praise where avaliable=1 and contentCode=? ";
		List<Praise> list = getHibernateTemplate().execute(new HibernateCallback<List<Praise>>() {
			   @SuppressWarnings("unchecked")
			@Override
			public List<Praise> doInHibernate(Session arg0)
					throws HibernateException, SQLException {
				    Query query  =	arg0.createQuery(hql);
				    query.setParameter(0, contentCode);
				   return query.list();
			}
		});
		return (list!=null && list.size()>0)?(list.get(0)):(null);
	}

	@Override
	public Object[] searchPVCount(final String postCode) {
		
		
		
		
		
		List<Object[]>  obj  =  getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			   @Override
			public List<Object[]> doInHibernate(Session session)
					throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String  sql  =  "select  p.falseViewcount ,(select count(*)  from reply  where  reply.postCode='"+postCode+"')   from  praise  p  where   p.contentcode='"+postCode+"'  ";
				Query query  =   session.createSQLQuery(sql);
				return query.list();
			}
		}) ;
			
			
			
			
		return obj!=null&&obj.size()>=1?obj.get(0):null;
	}


	@Override
	public void updateReplyCount(final String contentCode) {
		// TODO Auto-generated method stub
				if (!this.isValidExitPraise(contentCode)) {
					Praise  praise = new Praise();
					praise.setAvaliable(1);
					praise.setCreateTime(new Date());
					praise.setReplyNum(1);
					praise.setFalseReplyNum(1);
					praise.setCode(Uuid.uuid());
					praise.setContentCode(contentCode);
					this.save(praise);
			 }else{
				getHibernateTemplate().execute(
						new HibernateCallback() {
						  public Object doInHibernate(org.hibernate.Session session) throws org.hibernate.HibernateException ,java.sql.SQLException {
							    Query   query  = session.createQuery("update from  Praise  p set  FalseReplyNum=FalseReplyNum+1, ReplyNum=ReplyNum+1,  where  p.contentCode=:contentcode and p.avaliable=1 ");
								query.setParameter("contentcode", contentCode);
							    return	query.executeUpdate();
						  };
						
						}
					);
			}
		
		
	}

	@Override
	public void updateFavateCount(final String contentCode) {
		// TODO Auto-generated method stub
		if (!this.isValidExitPraise(contentCode)) {
			Praise  praise = new Praise();
			praise.setAvaliable(1);
			praise.setCreateTime(new Date());
			praise.setCode(Uuid.uuid());
			praise.setContentCode(contentCode);
			praise.setFalseFavoriteNum(1);
			praise.setFavoriteNum(1);
			this.save(praise);
		}else{
			getHibernateTemplate().execute(
				new HibernateCallback() {
				  public Object doInHibernate(org.hibernate.Session session) throws org.hibernate.HibernateException ,java.sql.SQLException {
					  Query   query  = session.createQuery("update from  Praise  p set favoriteNum = (  IF(favoriteNum,favoriteNum,0)+1 ),falseFavoriteNum = (  IF(falseFavoriteNum,falseFavoriteNum,0)+1 )   where  p.contentCode=:contentcode and p.avaliable=1 ");
						query.setParameter("contentcode", contentCode);
						return query.executeUpdate();
				  };
				
				}
			);
		}
	}

/*	@Override
	public <X> List<X> findListBySql(Class<X> clazz, String sql,
			List<Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <X> List<X> findListBySqlRow(Class<X> clazz, int row, String sql,
			List<Object> params) {
		// TODO Auto-generated method stub
		return null;
	}*/

	

	
}
