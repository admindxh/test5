package com.rimi.ctibet.web.dao.daoimpl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.ListUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.common.util.VeDate;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.Member;
import com.rimi.ctibet.domain.MemberInfo;
import com.rimi.ctibet.web.controller.vo.ActivityVO;
import com.rimi.ctibet.web.controller.vo.MemberVO;
import com.rimi.ctibet.web.dao.IMemberDao;
@Repository("memberDao")
public class MemberDaoImpl extends BaseDaoImpl<Member> implements IMemberDao {

	@Override
	public Member findByCode(String code){
		String hql = "from Member where code=? and avaliable=1";
		List<Member> list = getHibernateTemplate().find(hql, code);
		return (list!=null && list.size()>0)?(list.get(0)):(null);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<LogUser> login(final String username,final String password) {
		// TODO Auto-generated method stub
		final String sql="SELECT m.id AS id,m.createTime as createTime,m.status AS status,m.memberType AS memberType, m.`code` AS CODE,m.`password` AS PASSWORD ,me.`email` AS email,me.isBind AS isBind,mi.`name` AS username,mi.`memberCode` AS memberCode,mi.`phone` AS phone,mi.`pic` AS pic,mi.`sex` AS sex,mi.`score` AS score,mi.`birthday` AS birthday,mi.`province` AS province,mi.`city` AS city,mm.`mobile` AS mobile,mm.`isVerified` AS isVerified FROM member m LEFT JOIN member_email me ON m.`code`=me.`memberCode` LEFT JOIN member_mobile mm ON m.`code`=mm.`memberCode` LEFT JOIN member_info mi ON m.`code`=mi.`memberCode` WHERE m.`avaliable`='1' AND (me.`email`=? OR mm.`mobile`=?) and m.`password`=? ";
		List<LogUser> list= (List<LogUser>) getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public List doInHibernate(Session session)
					throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				SQLQuery s=session.createSQLQuery(sql).addEntity(LogUser.class);
				s.setParameter(0, username);
				s.setParameter(1, username);
				s.setParameter(2, password);
				List<Map<String,Object>> relist=(List<Map<String,Object>>)s.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
				
				//List<LogUser> relist=(List<LogUser>)s.setResultTransformer(Transformers.TO_LIST).list();
				List<LogUser> list=new ArrayList<LogUser>();
				for(int i=0;i<relist.size();i++){
					 //System.out.println(relist.get(0));
					if(relist.get(i).containsKey("LogUser")){
					   	LogUser loguser=(LogUser)relist.get(i).get("LogUser");
					   	list.add(loguser);
					}else{
						//System.out.println(1);
					}
				}
				return list;
			}
		});
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<LogUser> findLogUserByCode(final String code) {
		// TODO Auto-generated method stub
		final String sql="SELECT m.id AS id,m.createTime as createTime,m.status AS status,m.memberType AS memberType, m.`code` AS code,m.`password` AS password ,me.`email` AS email,me.isBind AS isBind,mi.`name` AS username,mi.`memberCode` AS memberCode,mi.`phone` AS phone ,mi.`pic` AS pic,mi.`sex` AS sex,mi.`score` AS score,mi.`birthday` AS birthday,mi.`province` AS province,mi.`city` AS city,mm.`mobile` AS mobile,mm.`isVerified` AS isVerified FROM member m "
		      +"LEFT JOIN member_email me ON m.`code`=me.`memberCode` "
		      +"LEFT JOIN member_mobile mm ON m.`code`=mm.`memberCode` " 
		      +"LEFT JOIN member_info mi ON m.`code`=mi.`memberCode` WHERE m.`avaliable`='1' AND m.code=?";
		List<LogUser> list=(List<LogUser>)getHibernateTemplate().execute(new HibernateCallback(){
            @Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
            	SQLQuery s=session.createSQLQuery(sql).addEntity(LogUser.class);
        		s.setParameter(0, code);
                List<Map<String,Object>> relist=(List<Map<String,Object>>)s.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        		
        		List<LogUser> list=new ArrayList<LogUser>();
        		for(int i=0;i<relist.size();i++){
        			 //System.out.println(relist.get(0));
        			if(relist.get(i).containsKey("LogUser")){
        			   	LogUser loguser=(LogUser)relist.get(i).get("LogUser");
        			   	list.add(loguser);
        			}else{
        				//System.out.println(1);
        			}
        		}
        		return list;
			}
			
		});
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<LogUser> getTopFive() {
		// TODO Auto-generated method stub
		final String sql="SELECT m.id AS id,m.createTime as createTime,m.status AS status,m.memberType AS memberType, m.`code` AS code,m.`password` AS password ,me.`email` AS email,me.isBind AS isBind"
			      +",mi.`name` AS username,mi.`memberCode` AS memberCode,mi.`phone` AS phone,mi.`pic` AS pic,mi.`sex` AS sex"
			      +",mi.`score` AS score,mi.`birthday` AS birthday,mi.`province` AS province,mi.`city` AS city,mm.`mobile` AS mobile"
			      +",mm.`isVerified` AS isVerified FROM member m LEFT JOIN member_email me ON m.`code`=me.`memberCode` " 
			      +"LEFT JOIN member_mobile mm ON m.`code`=mm.`memberCode` " 
			      +"LEFT JOIN member_info mi ON m.`code`=mi.`memberCode` WHERE m.`avaliable`='1' ORDER BY mi.`score` DESC LIMIT 0,5";
		List<LogUser> list=(List<LogUser>)getHibernateTemplate().execute(new HibernateCallback(){
            @Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
            	SQLQuery s=session.createSQLQuery(sql).addEntity(LogUser.class);
        		
                List<Map<String,Object>> relist=(List<Map<String,Object>>)s.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        		
        		List<LogUser> list=new ArrayList<LogUser>();
        		for(int i=0;i<relist.size();i++){
        			if(relist.get(i).containsKey("LogUser")){
        			   	LogUser loguser=(LogUser)relist.get(i).get("LogUser");
        			   	list.add(loguser);
        			}else{
        				//System.out.println(1);
        			}
        		}
        		return list;
			}
			
		});
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<LogUser> getTopFivByPcount() {
		// TODO Auto-generated method stub
	  final	String sql=" SELECT m.memberType as memberType,c.pCount,c.createuserCode,m.createTime as createTime,m.status as status, m.id AS id, m.`code` AS CODE,m.`password` AS PASSWORD ,me.`email` AS email,me.isBind AS isBind"
				      +",mi.`name` AS username,mi.`memberCode` AS memberCode,mi.`phone` AS phone,mi.`pic` AS pic,mi.`sex` AS sex"
				      +",mi.`score` AS score,mi.`birthday` AS birthday,mi.`province` AS province,mi.`city` AS city,mm.`mobile` AS mobile"
				      +",mm.`isVerified` AS isVerified FROM member m LEFT JOIN member_email me ON m.`code`=me.`memberCode`" 
				      +"LEFT JOIN member_mobile mm ON m.`code`=mm.`memberCode` " 
				      +"LEFT JOIN member_info mi ON m.`code`=mi.`memberCode` "
				      +"RIGHT JOIN (SELECT createuserCode AS createuserCode,COUNT(*) AS pCount FROM content WHERE createuserCode IS NOT NULL AND avaliable='1' AND contentType='post' GROUP BY createuserCode) c "
				      +" ON m.`code`=c.`createuserCode` WHERE m.`avaliable`='1' ORDER BY c.pCount DESC LIMIT 1,5";
      
	  List<LogUser> list=(List<LogUser>)getHibernateTemplate().execute(new HibernateCallback(){
          @Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
          	SQLQuery s=session.createSQLQuery(sql).addEntity(LogUser.class);
      		
              List<Map<String,Object>> relist=(List<Map<String,Object>>)s.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
      		
      		List<LogUser> list=new ArrayList<LogUser>();
      		for(int i=0;i<relist.size();i++){
      			if(relist.get(i).containsKey("LogUser")){
      			   	LogUser loguser=(LogUser)relist.get(i).get("LogUser");
      			   	list.add(loguser);
      			}else{
      				//System.out.println(1);
      			}
      		}
      		return list;
			}
			
		});
		return list;
	}
	
	/**
	 * 返回数量通过月份
	 */
	@Override
	public Integer getMemCountByTime(final Date start,final Date end) {
		// TODO Auto-generated method stub
		Integer  memCount  = getHibernateTemplate().execute(new HibernateCallback<Integer>(		) {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException,
					SQLException {
				String sql="select count(*) from (SELECT m.id AS id,m.createTime As createTime,m.status AS status,m.memberType AS memberType, m.`code` AS CODE,m.`password` AS PASSWORD ,me.`email` AS email,me.isBind AS isBind"
					+",mi.`name` AS name,mi.`memberCode` AS memberCode,mi.`phone` AS phone ,mi.`pic` AS pic,mi.`sex` AS sex,mi.`score` AS score,mi.`birthday` AS birthday"
					+",mi.`province` AS province,mi.`city` AS city,mm.`mobile` AS mobile,mm.`isVerified` AS isVerified FROM member m "
							      +"LEFT JOIN member_email me ON m.`code`=me.`memberCode` "
							      +"LEFT JOIN member_mobile mm ON m.`code`=mm.`memberCode` "
							      +"LEFT JOIN member_info mi ON m.`code`=mi.`memberCode` WHERE m.`avaliable`='1' and memberType='1'";
				 if(start!=null){
			    	  sql  =  sql +" and  DATE_FORMAT(m.createTime,'%Y-%m-%d')>='"+VeDate.toDateString(start)+"'";
			      }
			      if(end!=null){
			    	  sql  =  sql +" and  DATE_FORMAT(m.createTime,'%Y-%m-%d')<='"+VeDate.toDateString(end)+"'";
			      }
			      sql+=") t1";
				    Query query  = session.createSQLQuery(sql);
				    BigInteger  bigInteger  = (BigInteger) query.uniqueResult();
				return   Integer.valueOf(bigInteger.toString());
			}
		});
		return memCount;
	}
	@Override
	public List<LogUser> findListBysql2(final String sql,final List params) {
		// TODO Auto-generated method stub
		return (List<LogUser>) getHibernateTemplate().execute(new HibernateCallback(){

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				SQLQuery sqlQuery=	session.createSQLQuery(sql);
				sqlQuery.addEntity(LogUser.class);
				if (params != null && params.size() > 0) {
					for (int i = 0; i < params.size(); i++) {
						sqlQuery.setParameter(i, params.get(i));
					}
				}
				// TODO Auto-generated method stub
				return	sqlQuery.list();
			}});
		
		
	}
    @Override
    public MemberVO getMemberByMemberCode(String code) {
        String sql = " SELECT * FROM v_member_detail WHERE avaliable=1 AND code=? ";
        List<Object> params=new ArrayList<Object>();
        params.add(code);
        List<MemberVO> list = findListBySql(MemberVO.class, sql,params);
        return ListUtil.returnSingle(list);
    }

	
}
