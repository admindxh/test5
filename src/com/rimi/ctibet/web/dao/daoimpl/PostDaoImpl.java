package com.rimi.ctibet.web.dao.daoimpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.ListUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.Uuid;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Praise;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.domain.Reply;
import com.rimi.ctibet.portal.controller.vo.PostVo;
import com.rimi.ctibet.portal.controller.vo.TssqPortalPostVo;
import com.rimi.ctibet.web.controller.vo.PostPassVo;
import com.rimi.ctibet.web.dao.IPlateDao;
import com.rimi.ctibet.web.dao.IPostDao;
import com.rimi.ctibet.web.dao.IPraiseDao;
@Repository("postDao")
public class PostDaoImpl extends BaseDaoImpl<Content> implements IPostDao{

	@Resource
	private IPlateDao plateDao;
	@Resource
	private IPraiseDao praiseDao;
	@Override
	public Pager UncheckedPostList(Pager pager,int state,String type) {
	    List<Object> params =new ArrayList<Object>();
		String sql = "select c.* ,p.programaName , me.email,mm.mobile,mi.name " +
				"FROM content c ,programa p ,member m ,member_email me,member_mobile mm,member_info mi " +
				"WHERE c.programaCode = p.code AND c.createuserCode = m.code " +
				"AND (me.memberCode =m.code OR mi.memberCode = m.code) " +
				"AND mm.memberCode =m.code AND c.avaliable = 1 AND c.state = ? AND c.contentType = ? ";
		params.add(state);
		params.add(type);
        return findByJDBCSql(sql,params, pager);
	}

	@Override
	public void checkPost(Content post, int state) {
		post.setState(state);
		updateAsHibernate(post);
	}
	
	@Override
	public void deletePost(Content post) {
    	post.setAvaliable(0);
    	final String sql = "delete from Reply  bean WHERE bean.postCode = '" + post.getCode()+"'";
    	if("post".equals(post.getContentType())){
    		//删除关联评论数据
    		final String deleteReply="update content set avaliable=0 where code in(select contentCode from reply where postCode='"+post.getCode()+"')";
    		getHibernateTemplate().execute(
    				new HibernateCallback<Object>() {
    					@Override
    					public Object doInHibernate(org.hibernate.Session arg0)
    					throws HibernateException, SQLException {
    						Query query = arg0.createSQLQuery(deleteReply);
    						return query.executeUpdate();
    					}
    				});
    	}
    	   //将reply表中的postCode设置0，标示不再关联回复。
    	   getHibernateTemplate().execute(
					new HibernateCallback<Object>() {
						@Override
						public Object doInHibernate(org.hibernate.Session arg0)
								throws HibernateException, SQLException {
							Query query = arg0.createQuery(sql);
							return query.executeUpdate();
						}
			});
    	 
    	   final String sqlreply = "delete from Reply  bean WHERE bean.contentCode = '" + post.getCode()+"'";
    	   //删除回复
    	   getHibernateTemplate().execute(
					new HibernateCallback<Object>() {
						@Override
						public Object doInHibernate(org.hibernate.Session arg0)
								throws HibernateException, SQLException {
							Query query = arg0.createQuery(sqlreply);
							return query.executeUpdate();
						}
			});
    	updateAsHibernate(post);
	}

	@Override
	public void savePost(final Content post,final String postCode) {
		
		if("reply".equals(post.getContentType())&&StringUtils.isNotBlank(postCode)){
			final String replyCode = Uuid.uuid();
			String contentCode =post.getCode();
			getHibernateTemplate().execute(
					new HibernateCallback<Object>() {
						@Override
						public Object doInHibernate(org.hibernate.Session arg0)
								throws HibernateException, SQLException {
							    Reply reply = new Reply();
							    reply.setCode(replyCode);
							    reply.setContentCode(post.getCode());
							    reply.setPostCode(postCode );
							    return  arg0.save(reply);
						}
					});
		}
		//关联praise表
		Praise p = new Praise();
		p.setCode(CodeFactory.create("reply"));
		p.setFalsePraise(0);
		p.setTruePraise(0);
		p.setContentCode(post.getCode());
		Content oldPost=null;
		if(post.getId()==0){//新增
			praiseDao.save(p);
			save(post);
	    }else{//修改
			oldPost=this.findById(post.getId());
			oldPost.setProgramaCode(post.getProgramaCode());
			oldPost.setContentTitle(post.getContentTitle());
			oldPost.setContent(post.getContent());
			this.update(oldPost);
		}
	}

	@Override
	public void updatePost(Content post) {
       updateAsHibernate(post);
	}

	@Override
	public Pager searchPost(Pager pager,int state,String contentType,String programaCode, String keyword) {
		    List<Object> params =new ArrayList<Object>();
			String sql = "select c.*,c.content as allContent ,p.programaName ,mi.name " +
					"FROM content c ,programa p ,member m ,member_info mi " +
					"WHERE c.programaCode = p.code AND c.createuserCode = m.code " +
					" AND mi.memberCode = m.code " +
					" AND c.avaliable = 1 AND c.state = ? AND c.contentType = ? ";
			params.add(state);//1 审核ed 0未审核
			params.add(contentType); //reply
		
		if(StringUtils.isNotBlank(programaCode)){
			params.add(programaCode);
			sql+=" AND p.code = ? ";
		}
		if(StringUtils.isNotBlank(keyword)){
            params.add("%"+keyword+"%");
            params.add("%"+keyword+"%");
			sql +=" AND (  c.contenttitle LIKE ? OR c.content LIKE ?  )";
		}
		sql += " GROUP BY c.`code` ";
		sql += " order by c.createTime desc ";
		return findByJDBCSql(sql,params, pager);
	}

	@Override
	public Content getPostByCode(String code) {
		final String sql ="from Content where code ='" + code+"'"; 
		return  getHibernateTemplate().execute(
					new HibernateCallback<Content>() {
						@Override
						public Content doInHibernate(org.hibernate.Session arg0)
								throws HibernateException, SQLException {
							Query query = arg0.createQuery(sql);
							if(query.list().size()>=1)
							  return (Content) query.list().get(0);
							else 
							  return null;	
						}
					});
	}
	@Override
	public Pager findAllPost(String memberCode,Pager pager) {
		// TODO Auto-generated method stub
		List<Object> params =new ArrayList<Object>();
		String sql="SELECT * FROM content WHERE avaliable='1' AND createuserCode=? AND (contentType='post' OR contentType='reply') "
                 +"UNION SELECT * FROM content WHERE avaliable='1' AND (contentType='post' OR contentType='reply') AND createuserCode IN (SELECT memberCode FROM user_favorite WHERE memberCode=? AND TYPE='post')";
		params.add(memberCode);
		params.add(memberCode);
		return findByJDBCSql(sql,params, pager);
	}

	@Override
	public Pager findAllPassPost(Integer state, String programcode,
			String keyword, String order,Pager pager) {
		// TODO Auto-generated method stub
		StringBuffer sb  = new  StringBuffer();
		sb.append("  select  member_info.pic as    postuserpic,member_info.name as userName ,c.contenttitle as ctitle,c.url as url,c.code as code,p.programaName as pname,c.createTime as ctime,c.content as content,ps.falseViewcount as  falseViewcount,ps.falsepraise  as falsepraise,c.isTop as isTop");
		sb.append("   from content c  ");
		sb.append("   left join  programa  p on p.code  = c.programaCode  ");
		sb.append("   left join praise  ps on  ps.contentcode  = c.code  ");
		sb.append("   left join member_info on    member_info.memberCode  = c.createusercode ");
		sb.append("   where c.avaliable=1   and c.contentType='post' and c.state  = "+state) ;
		if (keyword!=null&&!keyword.equals("")) {
			sb.append(" and ( ");
			sb.append("  c.contentTitle like '%"+keyword+"%'");
			sb.append(" or c.content like '%"+keyword+"%'");
			sb.append(" ) ");
		}
		if(programcode!=null&&!programcode.equals("")){
			sb.append(" and  c.programaCode='"+programcode+"'");
		}
		if (order!=null&&order.equals("createTime")) {
			sb.append(" order by c.createTime desc ");	
		}else if(order!=null&&order.equals("replyTime")){
			sb.append(" order by (  select cr.createTime  from  content  cr   where  cr.CODE  in (SELECT contentcode from reply  where reply.postCode=c.`code` )  ORDER BY cr.createTime DESC  LIMIT 0,1) DESC");
		}else if(order!=null&&order.equals("viewcount")){
			sb.append(" order by ps.falseViewcount desc");
		}else if(order!=null&&order.equals("praisecount")){
			sb.append(" order by ps.falsepraise desc");
		}
		else{
			//置顶排序
			sb.append("    order by  c.isTop desc,c.createTime desc ");
		}
		return  findListPagerBySql(PostPassVo.class, pager, sb.toString());
	}

	@Override
	public Pager getAllPassPostInProtal(Pager pager,String rule,String plateCode,String isTop) {
		String sql = "SELECT  c.url,c.code,c.createTime as ctime,c.contentTitle as ctitle,c.content as  content, member_info.`name` as userName, member_info.pic as postuserpic, member_info.`memberCode` AS usercode,member_info.`sex` AS usersex, praise.falseReplyNum  as falsereplynum, praise.falsepraise  as  falsepraise,praise.falseViewcount as falseViewcount, " +
				"(SELECT   CONCAT(member_info.name,'_',content.createTime,'_',member_info.pic,'_',member_info.sex)  as mcp  from content " +
				"LEFT JOIN  member_info on member_info.memberCode  = content.createuserCode  where  content.code in  (select contentCode   from reply WHERE  postcode   = c.`code` )  " +
				"  ORDER BY  content.createTime  DESC   LIMIT 0,1 ) as replyinfo  from  content c " +
				"LEFT JOIN  member_info on member_info.memberCode  = c.createuserCode " +
				"LEFT JOIN praise on praise.contentcode = c.`code`  where  c.state = 1 AND c.contentType = 'post' and c.avaliable=1 ";
		if(StringUtils.isNotBlank(isTop)){
			sql += " AND c.isTop = 1 ";
		}
		if (StringUtils.isNotBlank(plateCode)) {
			 sql  += " AND c.programaCode='"+plateCode+"'";
		}
		if("mostPraise".equals(rule))
		      sql +=" ORDER BY falsepraise DESC ";
		if("createTime".equals(rule))
		      sql +=" ORDER BY c.createTime DESC ";
		if("newReply".equals(rule)){
			  //截取replyinfo前面的日期进行比较
//			  sql +=" ORDER BY SUBSTR(replyinfo,1,19) DESC ";
			  sql +=" ORDER BY substring_index(replyinfo,'_',-3) DESC ";}
//		sql +=" order by  c.createTime desc ";
		return  findListPagerBySql(PostPassVo.class, pager, sql);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Pager getReplysInfoByPostCode(String code,Pager pager) {
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT  c.createuserCode ,c.code,c.contentTitle,c.createTime,c.content, member_info.sex as sex, member_info.`name` as postusername,member_info.pic as postuserpic,member_info.score as  score ,praise.falsepraise ,praise.falseViewcount ,praise.falseReplyNum " +
				"from content c " +
				" LEFT JOIN  reply  on reply.postCode =  c.code    " +
				"left join    member_info on member_info.memberCode  = c.createuserCode   " +
				"LEFT JOIN praise on praise.contentcode = c.`code` where  c.`code` " +
				"IN( select contentcode from reply where reply.postCode = ? ) AND c.contentType = 'reply' and c.state = 1  and c.avaliable=1 order by c.createTime asc";
		params.add(code);
		Pager data=super.findByJDBCSql(sql, params, pager);
		//刘洪兵新增
		List<Map<String, Object>> list=data.getDataList();
		for(int i=0;i<list.size();i++){
			Map<String, Object> temp=list.get(i);//获取原结果集
			StringBuilder builder=new StringBuilder();
			builder.append(" select count(*) as count from content c ");
			builder.append(" where c.createuserCode='"+temp.get("createuserCode")+"' ");
			builder.append(" and c.avaliable=1  ");
			builder.append(" AND c.state = 1 ");
			builder.append(" and c.contentType ='post' ");
			List<Map<String, Object>> sum=super.findByJDBCSql(builder.toString(), null);//获取当前用户发帖总数
			if(sum!=null&&sum.size()>0){
				temp.put("postSum", sum.get(0).get("count"));
			}
			temp.put("currentPage",pager.getCurrentPage());
			temp.put("pageSize",pager.getPageSize());
			list.set(i, temp);//填充数据
		}
		
		data.setDataList(list);//替换原数据
		return data; 
	}
    //帖子详细接口
	@Override
	public Map<String, Object> getPostDetailByCode(String postCode) {
		//封装结果
		Map<String,Object> result = new HashMap<String,Object>();
		
		List<Object> param = new ArrayList<Object>();
		param.add(postCode);
		String sql = 

			"SELECT   c.*,mi.memberCode as mcode,mi.sex,mi.`name`,mi.pic,mi.score,p.falsepraise,p.falseViewcount,p.falseReplyNum   from    content   c "+
			 "left join   member_info  mi on  mi.memberCode = c.createuserCode LEFT join    member_email  me on me.memberCode = c.createuserCode "+
			 " left join  member_mobile  mm on mm.memberCode =  c.createuserCode  LEFT    join  praise   p  on p.contentcode = c.`code`"+   
			" where   c.contentType = 'post' and c.`code` = ?   "; 
		//得到帖子
		
		List<Map<String,Object>>   postList   =  super.findByJDBCSql(sql, param);
		Map<String,Object> post =  new HashMap<String, Object>();
		if (postList!=null&&postList.size()>=1) {
			post = postList.get(0);
		}
		result.put("post", post);
		return result;
	}
//=================================================门户首页相关=================================================================

	@Override
	public List<Map<String, Object>> getBestPraise() {
		String sql = "SELECT  c.content,c.url,mi.`name`,(SELECT   p.contentTitle   from  content  p WHERE p.code =  r.postCode) AS replyername ,praise.falsepraise "+
				"FROM content c "+ 
				"left JOIN member_info  mi on mi.memberCode =  c.createuserCode "+ 
				"LEFT JOIN  reply r   on  r.contentCode  =   c.`code` "+ 
                "LEFT JOIN praise on c.`code` = praise.contentcode "+
				"WHERE c.`code` IN (SELECT c.`code` FROM content c,praise p WHERE c.`code` = p.contentcode ORDER BY p.falsepraise DESC) "+ 
				"AND c.contentType = 'reply' "+
                "LIMIT 0,3";
		return findByJDBCSql(sql,null);
	}
	@Override
	public Pager getMemberPostByState(String memberCode,String state,
			String programaCode, Pager pager) {
		List<Object> params =new ArrayList<Object>();
		String sql = "SELECT c.contentTitle,c.code,c.url,c.state,p.programaName as programaName,p.`code` AS programaCode "
				+"FROM content c ,programa p "
				+"WHERE c.programaCode = p.code "
				 +"AND c.avaliable = '1'  AND c.contentType ='post' AND c.createuserCode=?";
		
		params.add(memberCode);
	if(StringUtils.isNotBlank(state)){
		params.add(state);
		sql+=" AND c.state = ?";
	}
	if(StringUtils.isNotBlank(programaCode)){
		params.add(programaCode);
		sql+=" AND p.code = ? ";
	}
	sql+=" order by c.createTime desc";
	  return findListPagerBySql(Content.class, pager, sql, params);
	}

	@Override
	public List<Map<String,Object>> getDrivAannouncements() {
		String sql = "SELECT c.contentTitle , c.url,c.createTime " +
				"FROM content c " +
				"WHERE  c.avaliable = 1 AND c.state = 1 AND c.programaCode = '409fb234-8639-452a-a440-88c210f5ce6f' AND c.contentType='post'  order by c.createTime desc LIMIT 0,4";
		return findByJDBCSql(sql,null);
	}

	@Override
	public List<Map<String,Object>> getDriveEquipments() {
		String sql = "SELECT c.contentTitle , c.url,c.createTime " +
	            "FROM content c " +
	            "WHERE c.avaliable = 1 AND c.state = 1 AND c.programaCode = 'd8e55a99-146b-43f1-be0c-9381b03f862f' AND c.contentType='post'  order by c.createTime desc LIMIT 0,4";
         return findByJDBCSql(sql,null);
	}

	@Override
	public List<Map<String,Object>> getDriveRecruits() {
		String sql = "SELECT c.contentTitle , c.url,c.createTime " +
	             "FROM content c " +
	             "WHERE c.avaliable = 1 AND c.state = 1 AND c.programaCode = '2a13c8e4-e657-44e9-a29c-c7d13b9f1085' AND c.contentType='post'  order by c.createTime desc LIMIT 0,4";
       return findByJDBCSql(sql,null);
	}

	@Override
	public List<Map<String,Object>> getDriveStorys() {
		String sql = "SELECT c.contentTitle , c.url,c.createTime " +
		         "FROM content c " +
		         "WHERE  c.avaliable = 1 AND c.state = 1 AND c.programaCode = '1e7e7437-5858-42a7-bd15-1d29f93326f0' AND c.contentType='post' order by c.createTime desc LIMIT 0,4";
       return findByJDBCSql(sql,null);
	}

	@Override
	public List<Map<String, Object>> getMostPraise() {
		String sql = "SELECT c.contentTitle , c.url,p.falsepraise " +
				"FROM content c,praise p " +
				"WHERE c.`code` = p.contentcode AND c.avaliable = 1 AND c.`contentType` = 'post'" +
				"ORDER BY p.falsepraise DESC " +
				"LIMIT 0,5";
		return findByJDBCSql(sql,null);
	}

	@Override
	public List<Map<String, Object>> getMostRplys() {
		String sql = "SELECT c.contentTitle , c.url,p.falseReplyNum " +
				"FROM content c,praise p " +
				"WHERE c.`code` = p.contentcode AND c.avaliable = 1  AND c.`contentType` = 'post'" +
				"ORDER BY p.falseReplyNum DESC  " +
				"LIMIT 0,5";
		return findByJDBCSql(sql,null);
	}

	@Override
	public List<Map<String, Object>> getTopPost() {
		String sql = "SELECT  c.code,c.url,c.createTime,c.contentTitle,c.content,member_info.memberCode as usercode,member_info.`sex` AS usersex, member_info.`name` as postusername, member_info.pic AS postuserpic, praise.falsepraise ,praise.falseViewcount,praise.falseReplyNum, " +
				"(SELECT   CONCAT(member_info.name,'_',DATE_FORMAT(content.createTime,'%Y-%m-%d %T'),'_',member_info.pic,'_',member_info.sex,'_',member_info.memberCode)  from content " +
				"LEFT JOIN  member_info on member_info.memberCode  = content.createuserCode  where  content.code in  (select contentCode   from reply WHERE  postcode   = c.`code` )  AND content.state=1 " +
				"ORDER BY  content.createTime  DESC   LIMIT 0,1 ) as replyinfo  from  content c  " +
				"LEFT JOIN  member_info on member_info.memberCode  = c.createuserCode " +
				"LEFT JOIN praise on praise.contentcode = c.`code`  where   c.contentType = 'post' and c.avaliable=1 and c.isTop = 1 order by c.createTime desc limit 0,5";
				return findByJDBCSql(sql, null);
	}

	@Override
	public List<TssqPortalPostVo> normalPost() {
		List<TssqPortalPostVo> pvos = new ArrayList<TssqPortalPostVo>();
		List<Programa> plates = new ArrayList<Programa>();
		 //获取到普通的论坛板块
		plates = plateDao.getSendPrograma(); 
		if(plates.size()>1){
			for (Programa plate : plates) {
				TssqPortalPostVo pvo =new TssqPortalPostVo();
	            pvo.setPlate(plate);
	            pvo.setPosts(this.getPostByPlateCode(plate.getCode()));
			    pvos.add(pvo);
			}
		}
		return pvos;
	}
	//根据板块获取到其下3个帖子(辅助normalPost方法)
	public List<Map<String,Object>> getPostByPlateCode(String plateCode){
		List<Object> params = new ArrayList<Object>();
		params.add(plateCode);
		String sql = "SELECT  c.code,c.url,c.createTime,c.contentTitle,c.content,member_info.memberCode as usercode,member_info.`sex` AS usersex ,member_info.`name` as postusername, member_info.pic AS postuserpic, praise.falsepraise ,praise.falseViewcount, praise.falseReplyNum, " +
				"(SELECT   CONCAT(member_info.name,'_',DATE_FORMAT(content.createTime,'%Y-%m-%d %T'),'_',member_info.pic,'_',member_info.sex,'_',member_info.memberCode)  from content " +
				"LEFT JOIN  member_info on member_info.memberCode  = content.createuserCode  where  content.code in  (select contentCode   from reply WHERE  postcode   = c.`code` )  AND content.state=1  " +
				"ORDER BY  content.createTime  DESC   LIMIT 0,1 ) as replyinfo  from  content c " +
				"LEFT JOIN  member_info on member_info.memberCode  = c.createuserCode " +
				"LEFT JOIN praise on praise.contentcode = c.`code`  where   c.contentType = 'post' and c.avaliable=1 and c.state =1 and c.programaCode = ?  ORDER BY c.createTime desc limit 0,3";
		return findByJDBCSql(sql, params);
	}
	/**
	 * 图说西藏评论分页
	 */
	@Override
	public Pager getMutiPost(Pager pager, String mutiCode) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT mi.name,mi.sex,mi.pic,c.content,c.createTime " +
				"FROM member_info mi,content c " +
				"WHERE  c.programaCode= ? " +
				"and c.createuserCode= mi.memberCode and mi.avaliable=1 and c.avaliable=1 and c.contentType='"+Content.DETAIL_PICTURE_REPLY+"' and c.state = 1 " +
				"ORDER BY c.createTime DESC");
		params.add(mutiCode);
		return findByJDBCSql(sql.toString(), params, pager);
	}
	/**
	 * 查询图集的所有评论
	 */
    @Override
    public List<Content> findPostByMutiCode(String mutiCode) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT c.content FROM content c WHERE c.programaCode=? and c.avaliable=1 and c.contentType='reply'");
		params.add(mutiCode);
		return findListBySql(Content.class, sql.toString(), params);   	
    }

	@Override
	public int getUncheckedPostAndReplyNum() {
		String sql = "SELECT COUNT(c.`code`) AS num " +
				"FROM content c ,praise p ,programa pr ,member_info mi WHERE c.avaliable=1  and (c.contentType = 'post' OR c.contentType= 'reply' ) AND c.state = 0 AND  p.contentcode = c.`code`  AND c.programaCode = pr.`code` AND c.createuserCode = mi.memberCode";
		List result = findByJDBCSql(sql, null);
		Map<String,Object> map = (Map<String, Object>) result.get(0);
		
		Integer num = Integer.valueOf((map.get("num").toString())); 
		return num;
	}
	//积分排行前五

	@Override
	public List<Map<String, Object>> getTopFive() {
		String sql = "SELECT m.id AS id,m.createTime as createTime,m.status AS status,m.memberType AS memberType, m.`code` AS code,m.`password` AS password ,me.`email` AS email,me.isBind AS isBind"
		      +",mi.`name` AS username,mi.`memberCode` AS memberCode,mi.`phone` AS phone,mi.`pic` AS pic,mi.`sex` AS sex"
		      +",mi.`score` AS score,mi.`birthday` AS birthday,mi.`province` AS province,mi.`city` AS city,mm.`mobile` AS mobile"
		      +",mm.`isVerified` AS isVerified FROM member m LEFT JOIN member_email me ON m.`code`=me.`memberCode` " 
		      +"LEFT JOIN member_mobile mm ON m.`code`=mm.`memberCode` " 
		      +"LEFT JOIN member_info mi ON m.`code`=mi.`memberCode` WHERE m.`avaliable`='1' ORDER BY mi.`score` DESC LIMIT 0,5";
		return findByJDBCSql(sql, null);
	}

	@Override
	public List<Map<String, Object>> getTopFiveBuPCount() {
		String sql=" SELECT m.memberType as memberType,c.pCount,c.createuserCode,m.createTime as createTime,m.status as status, m.id AS id, m.`code` AS CODE,m.`password` AS PASSWORD ,me.`email` AS email,me.isBind AS isBind"
		      +",mi.`name` AS username,mi.`memberCode` AS memberCode,mi.`phone` AS phone,mi.`pic` AS pic,mi.`sex` AS sex"
		      +",mi.`score` AS score,mi.`birthday` AS birthday,mi.`province` AS province,mi.`city` AS city,mm.`mobile` AS mobile"
		      +",mm.`isVerified` AS isVerified FROM member m LEFT JOIN member_email me ON m.`code`=me.`memberCode`" 
		      +"LEFT JOIN member_mobile mm ON m.`code`=mm.`memberCode` " 
		      +"LEFT JOIN member_info mi ON m.`code`=mi.`memberCode` "
		      +"RIGHT JOIN (SELECT createuserCode AS createuserCode,COUNT(*) AS pCount FROM content WHERE createuserCode IS NOT NULL AND avaliable='1' AND contentType='post' GROUP BY createuserCode) c "
		      +" ON m.`code`=c.`createuserCode` WHERE m.`avaliable`='1' ORDER BY c.pCount DESC LIMIT 0,5";
		return findByJDBCSql(sql, null);
	}

	@Override
	public String postCountByMemCode(String mCode) {
		String sql = "SELECT COUNT(c.`code`) AS pcount FROM content c WHERE c.createuserCode = ? AND c.contentType='post' AND c.avaliable =1 AND c.state =1 ";
		List<Object> params = new ArrayList<Object>();
		params.add(mCode);
		List<Map<String,Object>> result = findByJDBCSql(sql, params);
		String count = "0";
		if(result!=null&&result.size()>0){
			count = String.valueOf((Long)result.get(0).get("pcount"));
		}
		return count;
	}

	@Override
	public List<String> praiseCode(String ip) {
		
		String sql = "SELECT pv.contentCode FROM praiseandviewmanager pv WHERE pv.ip = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(ip);
		List<String> codes= new ArrayList<String>();
		List<Map<String,Object>> result = findByJDBCSql(sql, params);
		if (result!=null&&result.size()>=1) {
			for (Map<String,Object> obj : result) {
				if (obj.get("contentcode")!=null) {
					codes.add("'"+obj.get("contentcode").toString()+"'");
				}
			}
			
		}
		return codes;
	}

    @Override
    public List<Map<String, Object>> getMostReply(int row) {
        StringBuffer sql = new StringBuffer();
        sql.append("  ");
        sql.append(" SELECT "); 
        sql.append("     max(c.contentTitle) as contentTitle, max(c.url) as url , count(r.postCode) AS falseReplyNum ");
        sql.append(" FROM content c  ");
        sql.append(" INNER JOIN reply r ON c.code = r.postCode ");
        sql.append(" INNER JOIN content cr ON cr.code = r.contentCode AND cr.avaliable=1 AND cr.state=1 ");
        sql.append(" WHERE c.avaliable=1 AND c.contentType='post' ");
        sql.append(" GROUP BY r.postCode ");
        sql.append(" ORDER BY falseReplyNum DESC ");
        sql.append(" LIMIT 0, ? ");
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(row);
        return findByJDBCSql(sql.toString(), params);
    }

    @Override
    public int getPostCountByPrograma(String p) {
        String sql = "select * from content post where post.avaliable=1 and post.state=1 and post.contentType='post' and post.programaCode=?";
        return findCountBySql(sql, new Object[]{p});
    }

    @Override
    public int getPostcheckNumByPrograma(String p) {
        String sql = "select ifnull(sum(p.falseViewcount),0) as num from content post left join praise p on p.available=1 and p.contentcode=post.code where post.avaliable=1 and post.state=1 and post.contentType='post' and post.programaCode=?";
        return getJdbcTemplate().queryForObject(sql, Integer.class, new Object[]{p});
    }

    @Override
    public int getPostReplyNumByPrograma(String p) {
        String sql = "select re.* from content re inner join reply r on re.code=r.contentCode inner join content post on post.avaliable=1 and post.state=1 and post.code=r.postCode where re.avaliable=1 and re.state=1 and re.contentType='reply' and post.programaCode=? order by re.createTime desc";
        return findCountBySql(sql, new Object[]{p});
    }

    @Override
    public Content getPostNewReplyByPrograma(String p) {
        String sql = "select post.code as postCode,post.contentTitle as postTitle,post.url as postUrl,re.* from content re inner join reply r on re.code=r.contentCode inner join content post on post.avaliable=1 and post.state=1 and post.code=r.postCode where re.avaliable=1 and re.state=1 and re.contentType='reply' and post.programaCode=? order by re.createTime desc";
        return ListUtil.returnSingle(findListBySqlRow(Content.class, 1, sql, new Object[]{p}));
    }

    @Override
    public List<PostVo> getPostByPrograma(String programCode, int orderType, int row) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("  ");
        sql.append(" SELECT ");
        sql.append("     post.code, max(post.contentTitle) AS title, max(post.url) AS url, ");
        sql.append("     count(re.code) AS replyNum, ");
        sql.append("     max(p.falsepraise) AS praiseCount ");
        sql.append(" FROM content post  ");
        sql.append(" LEFT JOIN praise p ON p.available=1 AND post.code = p.contentcode ");
        sql.append(" LEFT JOIN reply r ON post.avaliable=1 AND post.contentType='post' AND r.postCode=post.code ");
        sql.append(" LEFT JOIN content re ON re.avaliable=1 AND re.state=1 AND r.contentCode=re.code ");
        sql.append(" WHERE post.avaliable = 1 ");
        sql.append(" AND post.contentType='post' ");
        sql.append(" AND post.state=1 ");
        
        sql.append(" AND post.programaCode=? ");
        params.add(programCode);
        
        sql.append(" GROUP BY post.code ");
        
        switch (orderType) {
            case Content.ORDER_CREATETIME_DESC:
                sql.append(" ORDER by post.createTime DESC ");
                break;
            case Content.ORDER_REPLYNUM_DESC:
                sql.append(" ORDER BY replyNum DESC ");
                break;
            case Content.ORDER_PRAISE_DESC:
                sql.append(" ORDER BY praiseCount DESC ");
                break;
        }
        return findListBySqlRow(PostVo.class, row, sql.toString(), params);
    }

}
