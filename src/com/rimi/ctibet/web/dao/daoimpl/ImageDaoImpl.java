package com.rimi.ctibet.web.dao.daoimpl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.ImagePager;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.domain.Image;
import com.rimi.ctibet.portal.controller.vo.ImageStrageVo;
import com.rimi.ctibet.web.dao.ImageDao;

/**
 * 
 * @author xiangwq
 * 
 */

@Repository("ImageDao")
public class ImageDaoImpl extends BaseDaoImpl<Image> implements ImageDao {
	/**
	 *通过图集CODE获取图片列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Image> findImageByMutiImageCode(String mutiImageCode) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String hql = "FROM Image i WHERE i.mutiCode=:mutiImageCode and i.avaliable=1";
		paraMap.put("mutiImageCode", mutiImageCode);
		return find(hql, paraMap);
	}
	/**
	 * 获取图片列表并分页
	 */
	@Override
	public ImagePager getImagePager(ImagePager pager,int pageSize, String mutiImageCode) {
		pager.setPageSize(pageSize); // 设置页面大小
		Map<String, Object> paraMap = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer("FROM Image i WHERE i.avaliable=1 ");
		if (StringUtils.isNotBlank(mutiImageCode)) {
			hql.append("and i.mutiCode=:mutiImageCode ");
			paraMap.put("mutiImageCode", mutiImageCode);
		}
		hql.append("order by i.createTime desc");
        pager = findImageWithPagerByMap(hql.toString(), paraMap, pager);
        //System.out.println("getImagePager=================>"+pager.getDataList().size());
		return pager;

	}
  
	
	
	/**
	 * 重写baseDao的findWithPagerByMap方法
	 */
	@SuppressWarnings("unchecked")
	public ImagePager findImageWithPagerByMap(final String hql,
			final Map<String, Object> Param, final ImagePager pager) {
		
		
		
		getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session arg0)
					throws HibernateException, SQLException {
				Query query = arg0.createQuery(hql);
				Iterator<String> keyIt = Param.keySet().iterator();
				//System.out.println("HQL--------------------------------------->" + hql);
				while (keyIt.hasNext()) {
					String property = keyIt.next();
					//System.out.println("属性名：" + property + "属性值" + Param.get(property));
					query.setParameter(property, Param.get(property));
				}
				query.setFirstResult(pager.getStartIndex());
				query.setMaxResults(pager.getPageSize());
				//System.out.println("开始==结束--------------------------------------------------"+ pager.getStartIndex() + "==" + pager.getEndIndex());

				List dataList = query.list();
				String mhql="";
				if (hql.toLowerCase().trim().startsWith("from")) {
					mhql = "select count(*) " + hql;
				} else {
					mhql = "select count(*)  "
							+ hql.replaceFirst("select[\\s\\S]*from", "from") + " ";
				}
				pager.setDataList(dataList);
				//System.out.println("DAO里的datalist=======================+>"+pager.getDataList().size());
				pager.setTotalCount(Integer.parseInt(find(mhql, Param).get(0).toString()));
				return null;
			}
		});
//		Query query = getSession().createQuery(hql);
//		Iterator<String> keyIt = Param.keySet().iterator();
//		//System.out.println("HQL--------------------------------------->" + hql);
//		while (keyIt.hasNext()) {
//			String property = keyIt.next();
//			//System.out.println("属性名：" + property + "属性值" + Param.get(property));
//			query.setParameter(property, Param.get(property));
//		}
//		query.setFirstResult(pager.getStartIndex());
//		query.setMaxResults(pager.getPageSize());
//		//System.out.println("开始==结束--------------------------------------------------"+ pager.getStartIndex() + "==" + pager.getEndIndex());
//
//		List dataList = query.list();
//		if (hql.toLowerCase().trim().startsWith("from")) {
//			hql = "select count(*) " + hql;
//		} else {
//			hql = "select count(*)  "
//					+ hql.replaceFirst("select[\\s\\S]*from", "from") + " ";
//		}
//		pager.setDataList(dataList);
//		//System.out.println("DAO里的datalist=======================+>"+pager.getDataList().size());
//		pager.setTotalCount(Integer.parseInt(find(hql, Param).get(0).toString()));
		return pager;
	}
	
	/**
	 * 通过活动code查询对应的其他模块信息
	 * @param ActivityCode
	 * @return
	 */
	public List<Image> getActivityOtherBlockByActivityCode(String ActivityCode){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT i.* FROM mutiimage m  LEFT JOIN image i ON m.code=i.mutiCode AND i.avaliable=1 ");
		sql.append(" WHERE m.avaliable=1 AND m.programaCode=? ");
		List<Object> list=new ArrayList<Object>();
		list.add(ActivityCode);
		return findListBySql(Image.class, sql.toString(),list);
	}
	/**
	 * 通过活动code查询对应的其他模块信息
	 * @param ActivityCode
	 * @return
	 */
	public Pager getActivityOtherBlockByActivityCode(Pager pager, String ActivityCode){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT i.* FROM mutiimage m  LEFT JOIN image i ON m.code=i.mutiCode AND i.avaliable=1 ");
		sql.append(" WHERE m.avaliable=1 AND m.programaCode=?");
		List<Object> list=new ArrayList<Object>();
		list.add(ActivityCode);
		return findListPagerBySql(Image.class, pager, sql.toString(),list);
	}
	
	/**
	 * 通过图集code删除图片
	 * @param mutiCode
	 */
	public void deleteImageByMutiCode(final String mutiCode){
		HibernateCallback<Object> callback = new HibernateCallback<Object>(){
			public Object doInHibernate(Session arg0) throws HibernateException, SQLException {
				String sql = " UPDATE image SET avaliable=0 WHERE mutiCode=:mutiCode ";
				SQLQuery sqlQuery = arg0.createSQLQuery(sql);
				sqlQuery.setParameter("mutiCode", mutiCode);
				sqlQuery.executeUpdate();
				return null;
			}
		};
		getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 通过图集code获取图片
	 * @param mutiCode
	 */
	public List<Image> getImageByMutiCode(String mutiCode){
		String sql = " SELECT * FROM image WHERE avaliable=1 AND mutiCode=? ";
		List<Object> list=new ArrayList<Object>();
		list.add(mutiCode);
		return findListBySql(Image.class, sql,list);
	}
	/**
	 * 通过url获取图片
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Image> findImageByUrl(String url) {
		StringBuffer hql = new StringBuffer("FROM Image i WHERE i.avaliable=1 and i.url=:url");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("url", url);
		return find(hql.toString(), param);
	}
	
	/**
	 * 获取内容中的图片
	 * @param reg
	 * @param content
	 * @return
	 */
	public List<String>  getContentImgSrc(String content){
		List<String>  src  = new ArrayList<String>();
		if(StringUtils.isNotBlank(content)){		
			String  a  =  content;
			Pattern  pattern   = Pattern.compile("<img.*?>");
			Matcher  m = pattern.matcher(a);
			Pattern pSrc  = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)");
			// Matcher mSrc  = pSrc.matcher(input)
			while(m.find()){
				//System.out.println(m.group());
				String img  = m.group();
				Matcher mScr  = pSrc.matcher(img);
				if(mScr.find()) {
					String str = mScr.group().replaceAll("src=\"", "").replaceAll("\"", "");
					str = str.replace("/ctibet", "");
					//System.out.println(str);
					src.add(mScr.group().replaceAll("src=\"", "").replaceAll("\"", ""));
					//获取地址
				}
			}
		}
		 return   src;
	}
	
	@Override
	public Pager findUserImgPager(String destinationCode,String mutiCode,Pager pager) {
		StringBuffer sql = new StringBuffer(); 
		sql.append("SELECT DISTINCT(c.id) as ID,c.contentTitle as strategyName,c.content as content," +
				"c.url as hyperlink,mi.`name` as name,c.createTime as createTime" +
				" FROM content c,strategy_view sv,member_info mi WHERE c.avaliable=1 and mi.avaliable=1 " +
				"and c.createuserCode= mi.memberCode and sv.destinationCode=? and  c.contentType='strategy' order by c.createTime desc");
		List<Object> list=new ArrayList<Object>();
		list.add(destinationCode);
		List<ImageStrageVo> voList = findListBySql(ImageStrageVo.class, sql.toString(),list);
		List<Image> imgList = new ArrayList<Image>();
		
		for(ImageStrageVo vo:  voList){	
			List<String> urls =  getContentImgSrc(vo.getContent());
			 for(String url : urls){
				 Image img = new Image();
				 //把vo转化到img对象里
				 img.setUrl(url);
				 img.setAvaliable(1);
				 img.setHyperlink(vo.getHyperlink());//
				 img.setCreateUserCode(vo.getName()); //用户name
				 img.setCreateTime(new Timestamp(vo.getCreateTime().getTime()));
				 img.setMutiCode(mutiCode);
				 img.setSummary(vo.getStrategyName()); //文章名字
				 imgList.add(img);
			 }
		}
//		String sql2 = "SELECT i.url,i.hyperlink,i.createUserCode FROM Image i WHERE i.avaliable=1 and  and i.mutiCode='"+mutiCode+"' order by i.createTime desc";
//		List<Image> list = findListBySql(Image.class,sql2);
//		for(Image img : imgList){
//			list.add(img);
//		}
		int start = pager.getPageSize() * (pager.getCurrentPage()-1) + 1;
		int end = pager.getPageSize() * pager.getCurrentPage();
		List<Image> dataList = new ArrayList<Image>();
		for(int i = (start-1);i<end;i++){
			 if(i<imgList.size()){				 
				 dataList.add(imgList.get(i));
			 }else{
				 break;
			 }
		}
		pager.setDataList(dataList);
		int totalPage = (int) Math.ceil(new Double(imgList.size()) / pager.getPageSize());
		pager.setTotalPage(totalPage);

		return pager;
	}

	
	@Override
	public Pager findImagePager(String code, Pager pager) {
		StringBuffer sql = new StringBuffer("select * FROM image i WHERE i.avaliable=1 and mutiCode=? ORDER BY i.createTime desc ");
		List<Object> list=new ArrayList<Object>();
		list.add(code);
		return findListPagerBySql(Image.class, pager, sql.toString());
	}
	
    @Override
    public Pager getViewAtlasByCodeType(Pager pager, String viewCode, String type) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("  ");
        
        sql.append(" SELECT i.*,m.name as createUserName FROM image i  ");
        sql.append(" LEFT JOIN v_member_detail m ON i.createUserCode = m.code AND m.avaliable=1 ");
        sql.append(" WHERE i.avaliable=1 ");
        
        //sql.append(" SELECT * FROM image WHERE avaliable=1  ");
        if(StringUtil.isNotNull(type)){
            sql.append(" AND i.summary=?  ");
            params.add(type);
        }
        if(StringUtil.isNotNull(viewCode)){
            sql.append(" AND i.viewCode=?  ");
            params.add(viewCode);
        }
        /*if(StringUtil.isNotNull(destinationCode)){
            sql.append(" AND i.destinationCode=? ");
            params.add(destinationCode);
        }*/
        sql.append(" ORDER BY i.createTime DESC ");
        
        return findListPagerBySql(Image.class, pager, sql.toString(), params);
    }
    
    @Override
    public void deleteAtlasByMutiCode(final String mutiCode) {
        getHibernateTemplate().execute(new HibernateCallback<Object>() {
            public Object doInHibernate(Session arg0) throws HibernateException, SQLException {
                String sql = "UPDATE image SET avaliable=0 WHERE (summary='"+Image.ATLAS_OFFICAL+"' OR summary='"+Image.ATLAS_USER+"') AND mutiCode='"+ mutiCode +"'";
                SQLQuery sqlQuery = arg0.createSQLQuery(sql);
                sqlQuery.executeUpdate();
                return null;
            }
        });
    }
    
    @Override
    public Pager getDestinationCodeAtlasByCodeType(Pager pager, String destinationCode, String type) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        
        sql.append("  ");
        sql.append(" SELECT i.*,m.name AS createUserName FROM ( ");
        sql.append("     SELECT ");
        sql.append("         destinationCode, url,max(name) AS name ,max(createuserCode) AS createuserCode, max(createTime) AS createTime ");
        sql.append("     FROM image " +
        		" WHERE avaliable=1 ");
        //sql.append("     AND (summary='atlas_user' OR summary='atlas_offical') ");
        if(StringUtil.isNotNull(type)){
            sql.append(" AND summary=?  ");
            params.add(type);
        }
        
        sql.append("     GROUP BY destinationCode, url ");
        sql.append(" ) i ");
        sql.append(" LEFT JOIN v_member_detail m ON m.avaliable=1 AND m.code=i.createuserCode ");
        
        if(StringUtil.isNotNull(destinationCode)){
            sql.append(" WHERE i.destinationCode=? ");
            params.add(destinationCode);
        }
        
        sql.append(" ORDER BY createTime DESC ");
        return findListPagerBySql(Image.class, pager, sql.toString(), params);
    }
    @Override
    public void deleteAtlasByUrl(final String url) {
        getHibernateTemplate().execute(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session arg0) throws HibernateException, SQLException {
                String sql = " update image set avaliable=0 where (summary='atlas_user' or summary='atlas_offical') and url = '" + url + "' ";
                arg0.createSQLQuery(sql ).executeUpdate();
                return null;
            }
        });
    }
	
}
