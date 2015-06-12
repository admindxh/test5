package com.rimi.ctibet.web.service.serviceimpl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;




import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;




import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.Uuid;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Destination;
import com.rimi.ctibet.domain.MutiImage;
import com.rimi.ctibet.domain.Praise;
import com.rimi.ctibet.domain.View;
import com.rimi.ctibet.web.dao.DestinationDao;
import com.rimi.ctibet.web.dao.IContentDao;
import com.rimi.ctibet.web.dao.IPraiseDao;
import com.rimi.ctibet.web.service.DestinationService;
import com.rimi.ctibet.web.service.MutiImageService;

@Transactional
@Repository("DestinationService")
public class DestinationServiceImpl extends BaseServiceImpl<Destination> implements DestinationService{
	
	@Resource
	private DestinationDao destinationDao;
	@Resource
	private IContentDao contentDao;
	@Resource
	private MutiImageService mutiService;
	@Resource
	private IPraiseDao praiseDao;
	
	@Override
	public List<Destination> getAllDestination() {
		return destinationDao.getALLDestination();
	}
	
	/**
	 * 得到目的地列表并分页
	 */
     @Override
    public Pager getDestinationPager(Pager pager,
    		ModelMap model) {
    	return destinationDao.getDestinationPager(pager,model);
    }
     /**
      * 通过目的地ID获取目的地详情
      * @param destination_id
      * @param model
      * @return
      */
	@Override
	public Destination getDestinationById(String destinationId) {
		return destinationDao.getDestinationById(destinationId);
	}
	

	

	
	/**
	 * 保存目的地
	 * @throws IOException 
	 */
	@Override
	public void saveDestination(Destination destination) {
		if(StringUtils.isNotBlank(destination.getCode())){
			Destination dbDestination = destinationDao.findByCode(destination.getCode());
			dbDestination.setAvaliable(0);
			dbDestination.setLastEditTime(new Timestamp(new Date().getTime()));
			destinationDao.update(dbDestination);
			Destination newDestination = new Destination();
			newDestination.setCreateTime(dbDestination.getCreateTime());
			newDestination.setAvaliable(1);
			newDestination.setCode(dbDestination.getCode());
			newDestination.setDestinationName(destination.getDestinationName());
			newDestination.setDestinationImage(destination.getDestinationImage());
			newDestination.setDestinationSummary(destination.getDestinationSummary());
			newDestination.setDestinationIntroduce(destination.getDestinationIntroduce());
			newDestination.setJp(destination.getJp());
			newDestination.setKeyword(destination.getKeyword());
			newDestination.setFalseGoneCount(destination.getFalseGoneCount());
			newDestination.setFalseWantCount(destination.getFalseWantCount());
			newDestination.setGoneCount(dbDestination.getGoneCount());
			newDestination.setWantCount(dbDestination.getWantCount());
			newDestination.setEnName(dbDestination.getEnName());
			newDestination.setTibetName(dbDestination.getTibetName());
			//newDestination.setLinkUrl(dbDestination.getLinkUrl());
			newDestination.setLinkUrl("/tourism/des/detail/"+destination.getCode()+".html");
			destinationDao.save(newDestination);
		}else{
			destination.setAvaliable(1);
			destination.setCode(CodeFactory.create("dest"));
			destination.setCreateTime(new Timestamp(new Date().getTime()));
			destination.setLinkUrl("/tourism/des/detail/"+destination.getCode()+".html");
			destinationDao.save(destination);
			//新增目的地后，新建目的地图集
			MutiImage mutiImage = new MutiImage();
			mutiImage.setName(destination.getDestinationName()+"目的地的官方图集");
			mutiImage.setProgramaCode(destination.getCode());//图集的栏目code 存放 目的地code
			mutiImage.setIsOfficial("1");//1为官方推荐 ， 0为用户图集
			mutiService.saveMuti(mutiImage);
			MutiImage mutiImage2 = new MutiImage();
			mutiImage2.setName(destination.getDestinationName()+"目的地的用户图集");
			mutiImage2.setProgramaCode(destination.getCode());//图集的栏目code 存放 目的地code
			mutiImage.setIsOfficial("0");
			mutiService.saveMuti(mutiImage2);
			//新增praise
			Praise p =new Praise();
			p.setAvaliable(1);
			p.setCode(Uuid.uuid());
			p.setContentCode(destination.getCode());
			p.setCreateTime(new Date());
			p=setNum(p);
			praiseDao.save(p);
			
		}
	}
	public Praise setNum(Praise p){
		p.setFalseFavoriteNum(1);
		p.setFalsePraise(1);
		p.setFalseReplyNum(1);
		p.setFalseViewcount(1);
		p.setFavoriteNum(1);
		return p;
	}
	
	
	/**
	 * 判断删除的目的地以下是否有景点 
	 * @return 有返回true 没有返回false 
	 */
	boolean isHaveView(String destinationCode){
	    List<View> viewList = destinationDao.getViewsByDesCode(destinationCode);          
		if(viewList.size()!=0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 删除所选择的目的地（将选择的目的地置为无效）
	 * @throws Exception 
	 */
	@Override
	public void deleteSelect(String[] destinationcodes) throws Exception {
		
		for(int i = 0; i<destinationcodes.length;i++){
			if(isHaveView(destinationcodes[i])){
				throw new Exception("目的地以下还有景点，不能删除!!");
			}else{
				Destination d = destinationDao.findByCode(destinationcodes[i]);
				d.setAvaliable(0);
				d.setLastEditTime(new Timestamp(new Date().getTime()));
				destinationDao.update(d);
			}
		}
		
	}
	
	/**
	 * 通过目的地Code获取目的地信息
	 */
	@Override
	public Destination getDestinationByCode(String destinationCode) {
		return destinationDao.findByCode(destinationCode);
	}
	/**
	 * 保存热门景点
	 * @param dCode 目的地
	 * @param pCode 栏目
	 * @param hotviewCode 修改的热门景点
	 * @param viewCode 存入的景点Code
	 */
	@Override
	public void saveHotView(String dCode, String pCode, String hotviewCode,
			String[] viewCode) {
		Destination des = destinationDao.findByCode(dCode);
		String strViewCode = "";
		StringBuffer sb  = new StringBuffer(); 
		for (int i = 0; i < viewCode.length; i++) {
			sb.append(viewCode[i]);
			if (i!=viewCode.length-1) {
				 sb.append(",");
			}
		}
		strViewCode = sb.toString();
   //hotview的属性：  code:自然生成的code  content:存入的景点code,contentTitle:目的地名+目的地经典推荐，authorCode:目的地code,contentType:hotview
		if(StringUtils.isBlank(hotviewCode)){
			Content hotview = new Content();
			hotview.setAvaliable(1);
			hotview.setCode(CodeFactory.createCultureCode());
			hotview.setContent(strViewCode);
			hotview.setContentTitle(des.getDestinationName() + "目的地详情页的热门景点推荐");
			hotview.setCreateTime(new Timestamp(new Date().getTime()));
			hotview.setProgramaCode(pCode);
			hotview.setAuthorCode(dCode);
			hotview.setContentType("hotview");
			contentDao.save(hotview);
		}else{
			Content dbContent = contentDao.findByCode(hotviewCode);
			Content newContent = new Content();
			newContent.setAuthorCode(dbContent.getAuthorCode());
			newContent.setAvaliable(1);
			newContent.setCode(dbContent.getCode());
			newContent.setContent(strViewCode);
			newContent.setCreateTime(dbContent.getCreateTime());
			newContent.setProgramaCode(pCode);
			newContent.setAuthorCode(dCode);
			newContent.setContentType("hotview");
			newContent.setContentTitle(des.getDestinationName() + "目的地详情页的热门景点推荐");
			contentDao.save(newContent);
			
			dbContent.setAvaliable(0);
			dbContent.setLastEditTime(new Timestamp(new Date().getTime()));
			contentDao.update(dbContent);
		}		
	}
	/**
	 * 保存精彩旅程
	 */
	@Override
	public void saveTravel(String dCode, String pCode, String[] contentCode,
			String[] urls, String[] codes) {
		Destination des = destinationDao.findByCode(dCode);
    //travel的属性： url：用户添加的url content:用户添加的code code:自然生产的code author:目的地code 
		contentDao.deleteByProperty("title",dCode+"travel");
        for(int i = 0; i< urls.length ;i++){
        	Content content = new Content();
    		content.setUrl(urls[i]);
    		content.setCode(CodeFactory.createCultureCode());
    		content.setContent(codes[i]);
    		content.setAuthorCode(dCode);     	
    		content.setCreateTime(new Timestamp(new Date().getTime()));
    		content.setContentTitle(des.getDestinationName()+"目的地详情页的精彩旅程");
    		content.setProgramaCode(pCode);
    		content.setContentType("travel");
    		content.setTitle(""+dCode+"travel");
    		content.setAvaliable(1);
    		contentDao.save(content);
        }
		
	}
	/**
	 * 保存推荐商户
	 */
	@Override
	public void saveRecomMerchant(String order,String dCode, String pCode,
			String[] contentCode, String[] urls, String[] codes,String type,String typeCode) {
		Destination des = destinationDao.findByCode(dCode);
    //recomMerchant属性： url:用户输入的url content：用户输入的code createuserCode:商户类型code
		if (StringUtils.isNotBlank(dCode)) {
			contentDao.deleteByProperty("title",dCode+"merchant"+order);
			contentDao.deleteBySql("delete from content  where  AuthorCode='"+dCode+"' and contentType='"+type+"'");
		}
        for(int i = 0; i< urls.length ;i++){
        		Content content = new Content();
        		content.setUrl(urls[i]);
        		content.setContent(codes[i]);
        		content.setCode(CodeFactory.createCultureCode());
        		content.setAuthorCode(dCode);     	
        		content.setCreateTime(new Timestamp(new Date().getTime()));
        		content.setContentTitle(des.getDestinationName()+"目的地详情页的推荐商户"+i);
        		content.setProgramaCode(pCode);
        		content.setContentType(type);
        		content.setAvaliable(1);
        		content.setTitle(""+dCode+"merchant"+order);
        		content.setCreateuserCode(typeCode);
        		contentDao.save(content);
        }	
		
	}
	/**
	 * 去过数和想去数的保存
	 */
	@Override
	public void saveGoneWant(Destination destination) {
		destinationDao.update(destination);		
	}

	
	/********************************************
	 * Setter Getter
	 ********************************************/
    public DestinationDao getDestinationDao() {
        return destinationDao;
    }

    public void setDestinationDao(DestinationDao destinationDao) {
        this.destinationDao = destinationDao;
    }
	
} 
