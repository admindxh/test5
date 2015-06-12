package com.rimi.ctibet.web.dao.daoimpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.web.dao.IPlateDao;

@Repository("plateDao")
public class PlateDaoImpl extends BaseDaoImpl<Programa> implements IPlateDao{

	@Override
	public void deletePlate(final Programa programa) {
		programa.setAvailable(0);
		getHibernateTemplate().execute(new HibernateCallback<Object>() {
					@Override
					public Object doInHibernate(Session arg0) throws HibernateException,
							SQLException {
						// TODO Auto-generated method stub
						arg0.createSQLQuery("UPDATE programa_content pc SET  pc.proCode = 0 WHERE pc.proCode = ? ").setString(0, programa.getCode()).executeUpdate();
						return null;
					}
		});
		this.updateAsHibernate(programa);
	}

	@Override
	public List<Map<String, Object>> getContentByProgramaCode(String code) {
		List<Object> list = new ArrayList<Object>();
		String sql = "SELECT c.*,p.programaName FROM programa p,content c,programa_content pc WHERE p.`code` = ? AND p.`code` = pc.proCode AND c.`code` = pc.conCode";
		StringUtils.isNotBlank(code);{
			list.add(code);
		}
		return findByJDBCSql(sql,list);
	}
	public List<Content> getContentByProgramaCodeForPlate(final String code){
		   
		List<Content> list  =	 getHibernateTemplate().execute(new HibernateCallback<List<Content>>() {
			   @Override
			public List<Content> doInHibernate(Session session) throws HibernateException,
					SQLException {
				   List<Content> contents  = session.createQuery("from Content c where c.programaCode = ? limit 0 , 3").setString(0,code).list();
				   return contents;
			 }
		   });
		
		
	    return  list;
	}
    //获取到普通的论坛板块
	@Override
	public List<Programa> getSendPrograma() {
		Pager pager =new Pager();
		Map<String, Object> byMap = new HashMap<String, Object>();
		String hql="select p  from Programa p  where pCode = '0c60136a-91f8-48b6-bd3b-1bee73f0114f' and p.available = 1";
		pager = findWithPagerByMap(hql, byMap, pager);
		List<Programa> programas =(List<Programa>)pager.getDataList();
		return programas; 
	}

	@Override
	public List<Programa> getTopPlate() {
		Pager pager =new Pager();
		pager.setPageSize(10000);
		Map<String, Object> byMap = new HashMap<String, Object>();
		String hql="select p  from Programa p  where (pCode = '0c60136a-91f8-48b6-bd3b-1bee73f0114f' or pCode = 'qxzq')and p.available = 1";
		pager = findWithPagerByMap(hql, byMap, pager);
		List<Programa> programas =(List<Programa>)pager.getDataList();
		return programas;
	}

	@Override
	public void savePlate(Programa programa) {
		save(programa);
		
	}

	@Override
	public void updatePlate(Programa programa) {
		updateAsHibernate(programa);
		
	}
	@Override
	public Programa findByCode(String code){
		return findByCodeAsHibernate(code);
	}

	@Override
	public List<Map<Programa, List<Content>>> getProtalPlate() {
		List<Map<Programa,List<Content>>> platesAndContents = new ArrayList<Map<Programa,List<Content>>>();
		List<Programa> plates = this.getTopPlate();
		for (Programa plate : plates) {
			List<Content> contents = this.getContentByProgramaCodeForPlate(plate.getCode());
			Map<Programa,List<Content>> plateAndContent = new HashMap<Programa,List<Content>>();
			plateAndContent.put(plate, contents);
			platesAndContents.add(plateAndContent);
		}
		return platesAndContents;
	}

	@Override
	public Pager plateList(Pager pager) {
		String sql = "select p.programaName,p.`code`,p.programaUrl,(select count(c.id)  from content c  " +
				"where c.programaCode =p.`code` and c.contentType='post' and c.avaliable=1 and c.state = 1) as postnum " +
				"from programa  p  where p.available = 1 and  (p.pCode = '0c60136a-91f8-48b6-bd3b-1bee73f0114f' OR p.pCode = 'qzzdq' OR p.pCode = 'qxzq') AND p.`code`<>'1d220cb7-5a5d-4b6b-b79f-64cfbedb8f58' ORDER BY p.createTime desc";
		return super.findByJDBCSql(sql, null, pager);
	}
	@Override
	public List<Programa> getProList() {
		String sql = "select p.programaName,p.`code`,p.programaUrl,(select count(c.id)  from content c  " +
				"where c.programaCode =p.`code` and c.contentType='post' and c.avaliable=1 and c.state = 1) as postnum " +
				"from programa  p  where p.available = 1 and  (p.pCode = '0c60136a-91f8-48b6-bd3b-1bee73f0114f' OR p.pCode = 'qxzq') AND p.`code`<>'1d220cb7-5a5d-4b6b-b79f-64cfbedb8f58' ORDER BY p.createTime desc";
		List<Object> param=new ArrayList<Object>();
		return super.findListBySql(Programa.class, sql, param);
	}
	@Override
	public List<Map<String,Object>> getFrontTsSqShow(String plateCode,int isTop) {
        List<Object> params = new ArrayList<Object>();
        params.add(plateCode);
        params.add(isTop);
		String sql="SELECT  c.contentTitle,c.content, member_info.`name` as postusername, praise.falsepraise ,praise.falseViewcount, " +
				"(SELECT   CONCAT(member_info.name,'-',content.createTime)  from content LEFT JOIN  member_info on member_info.CODE  = content.createuserCode  where  content.code in  " +
				"(select contentCode   from reply WHERE  postcode   = c.`code` )    ORDER BY  content.createTime  DESC   LIMIT 0,1 ) as replyinfo  from  content c " +
				"LEFT JOIN  member_info on member_info.CODE  = c.createuserCode LEFT JOIN praise on praise.contentcode = c.`code` " +
				"where   c.contentType = 'post' and c.avaliable=1 " +
				"AND c.isTop = ? " +
				"AND c.programaCode = ? " +
				"LIMIT 0 , 3";
		Pager pager = new Pager();
        pager = findByJDBCSql(sql, params, pager);                               		
		List<Map<String,Object>> dataList = pager.getDataList();
        return dataList;
	}

	@Override
	public String postCountByIsTop() {
        String sql = "SELECT  COUNT(c.`code`) as topcount FROM content c WHERE c.contentType = 'post' AND  c.avaliable =1 AND c.state = 1 AND c.isTop =1 ";
        List<Map<String,Object>> result = findByJDBCSql(sql, null);
        if(result!=null&&result.size()>0){
        	return result.get(0).get("topcount").toString();
        }
		return "0";
	}
}
