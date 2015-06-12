package com.rimi.ctibet.web.dao.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.common.util.VeDate;
import com.rimi.ctibet.domain.DesAndViewStatis;
import com.rimi.ctibet.web.controller.vo.DesAndViewStatisVo;
import com.rimi.ctibet.web.dao.IDesAndViewStatisDao;

@Repository("viewStatisDao")
public class DesAndViewStatisDaoImpl extends BaseDaoImpl<DesAndViewStatis> implements IDesAndViewStatisDao {

	@Override
	public DesAndViewStatisVo getDesViewVo(String start, String end,
			String desCode, String viewCode) {
		// TODO Auto-generated method stub
		List<Object> params=new ArrayList<Object>();
		StringBuffer sb  = new StringBuffer();
		//目的地
		sb.append(" select ");
		if (StringUtil.isNotNull(desCode)) {
			sb.append(" (  select  des.destinationName as desName  from destination  des  where  des.avaliable=1 and des.code=? )  ");
			params.add(desCode);
			
		}else{
			sb.append(" '全部目的地 ' ");
		}
		 
		sb.append(" as desName ,(select    count(descode) as desGoCount  from desandviewstatis  where descode  is not NULL ");
		if (StringUtil.isNotNull(desCode)) {
			sb.append(" and descode =? ");
			params.add(desCode);
		}
		
		  if(StringUtil.isNotNull(start)&&start!=null){
	    	  sb.append(" and  DATE_FORMAT(desandviewstatis.ctime,'%Y-%m-%d')>=? ");
	    	  params.add(start);
	      }
	      if(StringUtil.isNotNull(end)&&end!=null){
	    	  sb.append(" and  DATE_FORMAT(desandviewstatis.ctime,'%Y-%m-%d')<=? ");
	    	  params.add(end);
	      }
		
		sb.append(" and type =1 ");
		if (StringUtil.isNotNull(desCode)) {
			sb.append("   GROUP BY  descode   ");
			
		}
		sb.append(" )");
		sb.append(" as  desGoCount ,(select    count(descode) as desWantCount  from desandviewstatis  where descode  is not NULL ");
		if (StringUtil.isNotNull(desCode)) {
			sb.append(" and descode =? ");  
			params.add(desCode);
		}
		 if(StringUtil.isNotNull(start)&&start!=null){
	    	  sb.append(" and  DATE_FORMAT(desandviewstatis.ctime,'%Y-%m-%d')>=? ");
	    	  params.add(start);
	      }
	      if(StringUtil.isNotNull(end)&&end!=null){
	    	  sb.append(" and  DATE_FORMAT(desandviewstatis.ctime,'%Y-%m-%d')<=? ");
	    	  params.add(end);
	      }
		
		sb.append(" and type =0  ");
		if (StringUtil.isNotNull(desCode)) {
			sb.append("   GROUP BY  descode   ");
			
		}
		sb.append("  ) as desWantCount ");
		
		//景点
		
		
		if (StringUtil.isNotNull(viewCode)) {
			sb.append(" ,(  select    tv.viewName    as viewName  from tview  tv  where tv.avaliable=1  and tv.code=? )  ");
			 params.add(viewCode);
			
		}else{
			sb.append(" ,'全部景点 ' ");
		}
		
		
		sb.append(" as   viewName,(select    count(viewcode) as viewGoCount  from desandviewstatis  where viewcode  is not NULL  ");
		
		if (StringUtil.isNotNull(viewCode)) {
			sb.append(" and viewcode =? ");
			 params.add(viewCode);
		}
		
		 if(StringUtil.isNotNull(start)&&start!=null){
	    	  sb.append(" and  DATE_FORMAT(desandviewstatis.ctime,'%Y-%m-%d')>=? ");
	    	  params.add(start);
	      }
	      if(StringUtil.isNotNull(end)&&end!=null){
	    	  sb.append(" and  DATE_FORMAT(desandviewstatis.ctime,'%Y-%m-%d')<=? ");
	    	  params.add(end);
	      }
		
		
		sb.append(" and type =1  ");
		if (StringUtil.isNotNull(viewCode)) {
			sb.append("   GROUP BY  viewcode   ");
			
		}
		sb.append("  )  ");
		
		
		sb.append("  as  viewGoCount,(select    count(viewcode) as viewWantCount  from desandviewstatis  where viewcode  is not NULL  ");
		
		if (StringUtil.isNotNull(viewCode)) {
			sb.append(" and viewcode =? ");
			params.add(viewCode);
		}
		
		 if(StringUtil.isNotNull(start)&&start!=null){
	    	  sb.append(" and  DATE_FORMAT(desandviewstatis.ctime,'%Y-%m-%d')>=? "); 
	    	  params.add(start);
	      }
	      if(StringUtil.isNotNull(end)&&end!=null){
	    	  sb.append(" and  DATE_FORMAT(desandviewstatis.ctime,'%Y-%m-%d')<=? "); 
	    	  params.add(end);
	      }
		
		
		
		sb.append(" and type =0  ");
		if (StringUtil.isNotNull(viewCode)) {
			sb.append("   GROUP BY  viewcode   ");
			
		}
		sb.append(" ) as viewWantCount  ");
		
		
		sb.append("   ");
		
		List<DesAndViewStatisVo> list  = this.findListBySql(DesAndViewStatisVo.class, sb.toString(),params);
		
		
		return list!=null&&list.size()>=1?list.get(0):null;
	}


		
}
