package com.rimi.ctibet.web.dao.daoimpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.web.dao.IFrontPostInfoDao;

@Repository("frontPostDao")
public class FrontPostDaoImpl   extends BaseDaoImpl<Content> implements IFrontPostInfoDao{

	/**
	 * 查找帖子或者内容 对象
	 * @param code
	 * @param contentType
	 * @return
	 */
	@Override
	public Content  findByCodeContentType(final String code, final String contentType){
		return getHibernateTemplate().execute(new HibernateCallback<Content>() {
            public Content doInHibernate(Session arg0) throws HibernateException, SQLException {
                Criteria criteria = arg0.createCriteria(Content.class);
                criteria.add(Restrictions.eq("avaliable", 1));
                criteria.add(Restrictions.eq("code", code));
                criteria.add(Restrictions.eq("contentType", contentType));
                List<Content> list = findByCriteria(criteria);
                return (list!=null && list.size()>0)?list.get(0):null;
            }
        });
	}
	
	/**
	 * 根据帖子查找回复对象
	 * @param code
	 * @param contentType 可以为nul 保留
	 * @return
	 */
	@Override
	public  Pager findbyFatherCode(String code, String contentType,Pager pager){
		 //String sql  = "select   c.content content,m.name username  from content c LEFT join  programa p  on p.code  = c.programaCode  left join member_info  m on m.memberCode =  c.createuserCode where c.code in (select contentcode from  reply  where  postCode ='"+code+"' )   ";
		 String sql  = "select   c.content content,m.name username  from content c LEFT join  programa p  on p.code  = c.programaCode  left join member_info  m on m.memberCode =  c.createuserCode where c.code in (select contentcode from  reply  where  postCode =? )   ";
		List<Object> parmas=new ArrayList<Object>();
		parmas.add(code);
		 return this.findByJDBCSql(sql,parmas, pager);
	}
	/**
	 * 查询 板块和标题和 查看数和回复数
	 * @param code 帖子code 
	 * @param contentType
	 * @return
	 */
	public  List<Map<String,Object>>  findToInfo(String code, String contentType){
		
		String sql  = "select c.title  title,p.programaName programname,(select  count(viewcount) from praise  WHERE  contentcode = ? )  viewcount,(select  count(contentcode) from  reply  where  postCode = ? )  replycountfrom content c LEFT join  programa   p  on p.code  = c.programaCode     where c.code = ? and c.avaliable=1 ";
		List<Object> parmas=new ArrayList<Object>();
		parmas.add(code);
		parmas.add(code);
		parmas.add(code);
		return this.findByJDBCSql(sql,parmas);
		
	}
	public   Pager findbyMemberCode(String createusercode,Pager pager){
		
		String sql  =  "select   content.title title,p.programaName programaName ,m.name username ,mb.createTime,( select  m1.name+','+ct.createtime  nameAndcreatetime  from content ct left join member_info m1  on  m1.memberCode = ct.createuserCode  where ct.code   in  (select  contentcode  from reply   where   postcode  = content.`code` )  ORDER BY  ct.createTime desc    LIMIT 0,1)   nameandtimefrom content left join programa p  on p.`code` =  content.programaCode LEFT join member mb on mb.`code` = content.createuserCode   left join member_info m on m.memberCode = content.createuserCode  where content.contentType='post' and  content.avaliable=1 and content.createuserCode = ?";
		List<Object> param=new ArrayList<Object>();
		param.add(createusercode);
		
		return this.findByJDBCSql(sql, param, pager);
	}

}
