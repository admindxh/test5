package com.rimi.ctibet.web.dao.daoimpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.FrontContentStatis;
import com.rimi.ctibet.domain.MerchantType;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.web.controller.vo.BaseCodeVo;
import com.rimi.ctibet.web.controller.vo.FrontContentVo;
import com.rimi.ctibet.web.dao.IFrontContentStaticDao;
import com.rimi.ctibet.web.dao.IMerchantDao;
import com.rimi.ctibet.web.dao.IMerchantTypeDao;
import com.rimi.ctibet.web.dao.IProgramaDao;

/**
 * 统计日期
 * @author dengxh
 *
 */
@Repository("frontContentStatisDao")
public class FrontContentStaticDaoIml extends BaseDaoImpl<FrontContentStatis> implements IFrontContentStaticDao{
	
	@Resource
	IMerchantDao merchantDao;
	
	@Resource
	IMerchantTypeDao merchantTypeDao;
	
	@Resource
	IProgramaDao programaDao;

	public List<BaseCodeVo> getBaseCodeListByPcode(final String pcode){
		String sql  = "select code as code,typename as typename  from  basecode where pcode=? ";
		List<Object> pramas=new ArrayList<Object>();
		pramas.add(pcode);
	    List<BaseCodeVo> list  = this.findListBySql(BaseCodeVo.class, sql,pramas);
		
		return list;
	}
	/**
	 * 内容 统计全部信息
	 * @param start
	 * @param end
	 * @param proType
	 * @return
	 */
	public FrontContentVo getAllFrontContent(String start,String end,String proType	){
		StringBuffer sb = new StringBuffer("select ");
		
		this.getSql(sb, "click", start, end, proType, ",");
		this.getSql(sb, "favate", start, end, proType, ",");
		this.getSql(sb, "praise", start, end, proType, ",");
		this.getSql(sb, "reply", start, end, proType, "");
		sb.append(" ");
		List<FrontContentVo> list = this.findListBySql(FrontContentVo.class,sb.toString());
		return  list!=null&&list.size()>=1?list.get(0):null;
	}
	
	public StringBuffer  getSql(StringBuffer sb,String actionType,String start,String end,String proType,String fh){
		sb.append("(");
		if (actionType.equals("reply")) {
			String prString = proType;
			
			//看西藏--图说
			if (StringUtils.isNotBlank(prString)&&prString.equals("14dba551-cb5b-4631-b5ef-b3838670b3a9")) {
				 prString = "picture_reply"; 
			     sb.append("  SELECT  count(1) from content   WHERE  contentType='picture_reply'  AND     content.avaliable=1  and content.state=1     ");
			     if(StringUtils.isNotBlank(start)){
			    	  sb.append(" and  DATE_FORMAT(content.createTime,'%Y-%m-%d')>='"+start+"'");
			     }	
			     if(StringUtils.isNotBlank(end)){
			    	  sb.append(" and  DATE_FORMAT(content.createTime,'%Y-%m-%d')<='"+end+"'");
			     }
				 sb.append(" ) as  "+actionType+"Count"+""+fh);
				 return  sb;
			//文化传播
			}else if(StringUtils.isNotBlank(prString)&&prString.equals("2000")){
				prString = "read_tibet_culture_reply"; 
			     sb.append("  SELECT  count(1) from content   WHERE  contentType='read_tibet_culture_reply'  AND     content.avaliable=1  and content.state=1     ");
			     if(StringUtils.isNotBlank(start)){
			    	  sb.append(" and  DATE_FORMAT(content.createTime,'%Y-%m-%d')>='"+start+"'");
			     }	
			     if(StringUtils.isNotBlank(end)){
			    	  sb.append(" and  DATE_FORMAT(content.createTime,'%Y-%m-%d')<='"+end+"'");
			     }
				 sb.append(" ) as  "+actionType+"Count"+""+fh);
				 return  sb;
			//综合攻略
			}else if(StringUtils.isNotBlank(prString)&&prString.equals("c1d94bbe-792e-11e4-b6ce-005056a05bc9")){
				prString = "strategy_reply"; 
			     sb.append("  SELECT  count(1) from content   WHERE   contentType='strategy_reply'  AND     content.avaliable=1  and content.state=1     ");
			     if(StringUtils.isNotBlank(start)){
			    	  sb.append(" and  DATE_FORMAT(content.createTime,'%Y-%m-%d')>='"+start+"'");
			     }	
			     if(StringUtils.isNotBlank(end)){
			    	  sb.append(" and  DATE_FORMAT(content.createTime,'%Y-%m-%d')<='"+end+"'");
			     }
			     sb.append(" and content.programaCode='c1d94bbe-792e-11e4-b6ce-005056a05bc9' ");
				 sb.append(" ) as  "+actionType+"Count"+""+fh);
				 return  sb;
				
			}
			//骑行攻略
		  else if(StringUtils.isNotBlank(prString)&&prString.equals("c1d8e87d-792e-11e4-b6ce-005056a05bc9")){
			prString = "strategy_reply"; 
		     sb.append("  SELECT  count(1) from content   WHERE   contentType='strategy_reply'  AND     content.avaliable=1  and content.state=1     ");
		     if(StringUtils.isNotBlank(start)){
		    	  sb.append(" and  DATE_FORMAT(content.createTime,'%Y-%m-%d')>='"+start+"'");
		     }	
		     if(StringUtils.isNotBlank(end)){
		    	  sb.append(" and  DATE_FORMAT(content.createTime,'%Y-%m-%d')<='"+end+"'");
		     }
		     sb.append(" and  content.programaCode='c1d8e87d-792e-11e4-b6ce-005056a05bc9' ");
			 sb.append(" ) as  "+actionType+"Count"+""+fh);
			 return  sb;
			
		}
			//自驾攻略
		  else if(StringUtils.isNotBlank(prString)&&prString.equals("c1d87c3d-792e-11e4-b6ce-005056a05bc9")){
			prString = "strategy_reply"; 
		     sb.append("  SELECT  count(1) from content   WHERE   contentType='strategy_reply'  AND     content.avaliable=1  and content.state=1     ");
		     if(StringUtils.isNotBlank(start)){
		    	  sb.append(" and  DATE_FORMAT(content.createTime,'%Y-%m-%d')>='"+start+"'");
		     }	
		     if(StringUtils.isNotBlank(end)){
		    	  sb.append(" and  DATE_FORMAT(content.createTime,'%Y-%m-%d')<='"+end+"'");
		     }
		     sb.append(" and content.programaCode='c1d87c3d-792e-11e4-b6ce-005056a05bc9' ");
			 sb.append(" ) as  "+actionType+"Count"+""+fh);
			 return  sb;
			
		}
			//天上社区
		  else if(StringUtils.isNotBlank(prString)&&prString.equals("tssq")){
			prString = "reply"; 
		     sb.append("  SELECT  count(1) from content   WHERE   contentType='reply'  AND     content.avaliable=1  and content.state=1     ");
		     if(StringUtils.isNotBlank(start)){
		    	  sb.append(" and  DATE_FORMAT(content.createTime,'%Y-%m-%d')>='"+start+"'");
		     }	
		     if(StringUtils.isNotBlank(end)){
		    	  sb.append(" and  DATE_FORMAT(content.createTime,'%Y-%m-%d')<='"+end+"'");
		     }
			 sb.append(" ) as  "+actionType+"Count"+""+fh);
			 return  sb;
			
		}
			sb.append("  SELECT  count(1)  from   reply     f   left join  content   on content.`code`=f.postCode    LEFT join  content   c on c.`code`=f.contentCode AND  c.avaliable=1   where c.state=1 and	content.avaliable=1     ");
			if (StringUtils.isNotBlank(prString)) {
				 sb.append("  AND      content.contentType = '"+prString+"'   ");
			} 
			if(StringUtils.isNotBlank(start)){
		    	  sb.append(" and  DATE_FORMAT(f.ctime,'%Y-%m-%d')>='"+start+"'");
		     }	
		     if(StringUtils.isNotBlank(end)){
		    	  sb.append(" and  DATE_FORMAT(f.ctime,'%Y-%m-%d')<='"+end+"'");
		     }
			sb.append(" ) as  "+actionType+"Count"+""+fh);
			return  sb;
			
		}else{
			sb.append(" select count(1) from ( SELECT distinct  fronotcontentstatis.id   from  fronotcontentstatis    where actiontype='"+actionType+"'");
		}
		if (StringUtils.isNotBlank(proType)) {
			sb.append("and  progtype ='"+proType+"'");
		}else{
			sb.append(" and  progtype  IN  (select  `code`   from  basecode where pcode = 1) ");
		}
		 if(StringUtils.isNotBlank(start)){
	    	  sb.append(" and  DATE_FORMAT(ctime,'%Y-%m-%d')>='"+start+"'");
	      }
	      if(StringUtils.isNotBlank(end)){
	    	  sb.append(" and  DATE_FORMAT(ctime,'%Y-%m-%d')<='"+end+"'");
	      }
		sb.append(" ) a ) as  "+actionType+"Count"+""+fh);
		
		return sb;
	}
	
	public BaseCodeVo getBaeCodeVo(String code) {
		// TODO Auto-generated method stub
		 String sql  =  "select code as code, typename from basecode where code=? ";
		 List<Object> pramas=new ArrayList<Object>();
		pramas.add(code);
		 List<BaseCodeVo> list  =  this.findListBySql(BaseCodeVo.class, sql,pramas);
		 return   list!=null&&list.size()>=1?list.get(0):null;
	}
	
	/**
	 * 分页统计内容信息，等
	 * @return
	 */
	public Pager getPageFrontContentByCon(String start,String end,String proType,Pager pager){
		 
		 StringBuffer sb = new StringBuffer("select distinct ");
		 List<BaseCodeVo> bs  = this.getBaseCodeListByPcode("1");
		  if (StringUtils.isNotBlank(proType)) {
			 bs  = new ArrayList<BaseCodeVo>();
			 bs.add(this.getBaeCodeVo(proType));
		}
		 String [] as  =  new String[]{"click","favate","praise","reply"};
		 for (int j = 0; j < bs.size(); j++) {
			 sb.append("'"+bs.get(j).getTypename()+"' as proName ,");
			 for (int i = 0; i < as.length; i++) {
				String string = as[i];
				this.getSql(sb, string, start, end, bs.get(j).getCode(), "");
				sb.append(",");
			 }
			 sb.append("1");
			 sb.append("   from  basecode bs where pcode = 1 ");
			 if (bs.size()-1==j) {
				break;
			}
			 sb.append(" union select ");
		}
		
		return  this.findListPagerBySql(FrontContentVo.class, pager, sb.toString());
		
	}
	
	
	public StringBuffer  getMerchantSql(StringBuffer sb,String actionType,String start,String end,String proType,String fh,String merchantName){
		
			
			sb.append("(");
			sb.append("  SELECT  count(*)   from  fronotcontentstatis fs    ");
			if (true) {
				sb.append(" INNER join merchant m on m.code  = fs.code and  m.avaliable=1 ");
			}
			sb.append( " where 1=1  and fs.actiontype='"+actionType+"'");
			
			
			
			if (StringUtils.isNotBlank(merchantName)) {
			   sb.append(" and  m.merchantName like '%"+merchantName+"%'");
			}
			
			if (StringUtils.isNotBlank(proType)) {
				sb.append("  and  fs.progtype ='"+proType+"'");
			}else{
				sb.append(" and  fs.progtype  IN  (select  `code`   from  merchant_type where avaliable = 1) ");
			}
			 if(start!=null&&!start.equals("")){
		    	  sb.append(" and  DATE_FORMAT(fs.ctime,'%Y-%m-%d')>='"+start+"'");
		      }
		      if(end!=null&&!start.equals("")){
		    	  sb.append(" and  DATE_FORMAT(fs.ctime,'%Y-%m-%d')<='"+end+"'");
		      }
			sb.append(") as  "+actionType+"Count"+""+fh);
			
			return sb;
		}
	
	/**
	 * 商户  统计全部信息
	 * @param start
	 * @param end
	 * @param proType
	 * @return
	 */
	public FrontContentVo getAllFrontMerchant(String start,String end,String proType,String merchantName	){
		
		StringBuffer sb = new StringBuffer("select ");
		this.getMerchantSql(sb, "click", start, end, proType, ",",merchantName);
		this.getMerchantSql(sb, "favate", start, end, proType, ",",merchantName);
		//this.getSql(sb, "praise", start, end, proType, ",");
		this.getMerchantSql(sb, "reply", start, end, proType, ",",merchantName);
		this.getMerchantSql(sb, "href", start, end, proType, "",merchantName);
		sb.append(" ");
		List<FrontContentVo> list = this.findListBySql(FrontContentVo.class,sb.toString());
		return  list!=null&&list.size()>=1?list.get(0):null;
	}
	
	/**
	 * 查询商户分页
	 */
	public Pager getPageFrontMerchanttByCon(String start,String end,String proType,String merchantName,Pager pager){
		
		StringBuffer sb  =  new StringBuffer();
		
		sb.append(" select  ");
		
		if (StringUtils.isNotBlank(proType)) {
			    MerchantType merchantType =  merchantTypeDao.findByCode(proType);
			 	sb.append("'"+merchantType.getName()+"' as  proName,");
		}else{
			sb.append(" '全部类型' as  proName, ");
		}
		sb.append(" m.merchantName  as merchantName, ");
		sb.append(" m.url  as url, ");
		
		String aps  =  "  ";
		if (StringUtils.isNotBlank(proType)) {
			 aps += "  and  f.progtype ='"+proType+"' ";
		}
		if(start!=null&&!start.equals("")){
			aps+=" and  DATE_FORMAT(f.ctime,'%Y-%m-%d')>='"+start+"'";
	      }
	      if(end!=null&&!start.equals("")){
	    	  aps +=" and  DATE_FORMAT(f.ctime,'%Y-%m-%d')<='"+end+"'";
	      }
		
		sb.append(" (select  count(1)  from fronotcontentstatis  f  INNER  join  merchant     m  on   m.`code` =   f.code  and m.avaliable=1   where  f.`code` = fs.`code`  and  f.actiontype = 'click'   "+aps+"  )  as clickCount, ");
		
		sb.append("  (SELECT  count(1)  from   content     content      where    content.avaliable=1  and content.state=1     and   content.contentType='merchant_reply'  ");
		if(start!=null&&!start.equals("")){
			sb.append(" and  DATE_FORMAT(content.createTime,'%Y-%m-%d')>='"+start+"'");
	     }
	     if(end!=null&&!start.equals("")){
	    	  sb.append(" and  DATE_FORMAT(content.createTime,'%Y-%m-%d')<='"+end+"'");
	     }
		sb.append("   )  as  replyCount, ");
		
		sb.append(" (select  count(1)  from fronotcontentstatis  f INNER  join  merchant     m  on   m.`code` =   f.code  and m.avaliable=1   where  f.`code` = fs.`code`  and  f.actiontype = 'favate'  "+aps+" )  as favateCount ,");
		
		sb.append(" (	select  count(1)  from fronotcontentstatis  f   INNER  join  merchant     m  on   m.`code` =   f.code  and m.avaliable=1   where  f.`code` = fs.`code`  and  f.actiontype = 'href'  "+aps+" )  as hrefCount  ");
		
		sb.append(" from  fronotcontentstatis  fs   inner join  merchant m on m.code  = fs.code  and  m.avaliable=1   where 1=1  ");
		
		if (StringUtils.isNotBlank(proType)) {
			sb.append("and  fs.progtype ='"+proType+"'");
		}else{
			sb.append(" and  fs.progtype  IN  (select  `code`   from  merchant_type where avaliable = 1) ");
		}
		
		 if(start!=null&&!start.equals("")){
	    	  sb.append(" and  DATE_FORMAT(fs.ctime,'%Y-%m-%d')>='"+start+"'");
	      }
	      if(end!=null&&!start.equals("")){
	    	  sb.append(" and  DATE_FORMAT(fs.ctime,'%Y-%m-%d')<='"+end+"'");
	      }
	      if (StringUtils.isNotBlank(merchantName)) {
			   sb.append(" and  m.merchantName like '%"+merchantName+"%'");
			}
		sb.append(" GROUP BY   fs.`code`   ");
		return this.findListPagerBySql(FrontContentVo.class, pager, sb.toString());
	}
	
	
	
	
	@Override
	public FrontContentVo getAllFrontAderv(String start, String end,
			String proType) {
			List<Object> pramas=new ArrayList<Object>();
			StringBuffer sb  = new StringBuffer();
			sb.append(" select  count(1) as clickCount  from fronotcontentstatis  fs  where    fs.actiontype='click' ");
			if (StringUtils.isNotBlank(proType)) {
				sb.append(" and fs.progtype=? ");
				pramas.add(proType);
			}else{
			   sb.append(" and  fs.progtype in (select code from programa where  pcode  = 'ggwlx' )  ");
			}
			if(start!=null&&StringUtils.isNotBlank(start)){
		    	  sb.append(" and  DATE_FORMAT(fs.ctime,'%Y-%m-%d')>='"+start+"'");
		     }
		    if(end!=null&&StringUtils.isNotBlank(end)){
		    	  sb.append(" and  DATE_FORMAT(fs.ctime,'%Y-%m-%d')<='"+end+"'");
		    }
				
		      List<FrontContentVo> list = this.findListBySql(FrontContentVo.class,sb.toString(),pramas);
				return  list!=null&&list.size()>=1?list.get(0):null;
	}
	@Override
	public Pager getPageFrontAdervByCon(String start, String end,
			String proType, Pager pager) {
		List<Object> params=new ArrayList<Object>();
		StringBuffer sb  = new StringBuffer();
		sb.append("  select distinct  ar.imgUrl as imgUrl,p.programaUrl as url,p.programaName as 	proName,(select count(1) from  fronotcontentstatis   WHERE  `code` =  fs.`code` and fs.actiontype='click' ) as clickCount  ");
		sb.append("     from fronotcontentstatis fs  left JOIN  programa p on   p.code  = fs.progtype ");
		sb.append(" left join   ad_area  ar on ar.`code` = fs.`code`   where  1=1   ");
		
		if (StringUtils.isNotBlank(proType)) {
			sb.append(" and  fs.progtype=? ");
			params.add(proType);
		}else{
		   sb.append(" and  fs.progtype in (select code from programa where  pcode  = 'ggwlx' )  ");
		}
		if(start!=null&&StringUtils.isNotBlank(start)){
	    	  sb.append(" and  DATE_FORMAT(fs.ctime,'%Y-%m-%d')>='"+start+"'");
	     }
	    if(end!=null&&StringUtils.isNotBlank(end)){
	    	  sb.append(" and  DATE_FORMAT(fs.ctime,'%Y-%m-%d')<='"+end+"'");
	    }
		return this.findListPagerBySql(FrontContentVo.class, pager, sb.toString(),params);
	}
	
	
	
	
	public IMerchantDao getMerchantDao() {
		return merchantDao;
	}
	public void setMerchantDao(IMerchantDao merchantDao) {
		this.merchantDao = merchantDao;
	}
	public IMerchantTypeDao getMerchantTypeDao() {
		return merchantTypeDao;
	}
	public void setMerchantTypeDao(IMerchantTypeDao merchantTypeDao) {
		this.merchantTypeDao = merchantTypeDao;
	}
	
	
	
	
	
	
}
