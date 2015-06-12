package com.rimi.ctibet.web.service.serviceimpl;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.FileOperate;
import com.rimi.ctibet.common.util.FileUtil;
import com.rimi.ctibet.common.util.LuceneUtil2;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.ReadSettingProperties;
import com.rimi.ctibet.common.util.WebSearch;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Destination;
import com.rimi.ctibet.domain.View;
import com.rimi.ctibet.web.dao.IContentDao;
import com.rimi.ctibet.web.dao.IViewDao;
import com.rimi.ctibet.web.service.IViewService;
import com.rimi.ctibet.web.service.ImageService;

@Service("viewService")
@Transactional
public class ViewServiceImpl extends BaseServiceImpl<View> implements IViewService {

	@Resource
	private IViewDao viewDao;
	@Resource
	private IContentDao contentDao;
	
	@Resource(name="ImageService")
	private  ImageService imageService;

	/**
	 * 保存 View
	 * @param view
	 * @param basePath
	 * @param viewImgs
	 * @param view360Full
	 */
	public boolean saveView(View view, String basePath, MultipartFile[] viewImgs, MultipartFile view360Full) {
		boolean bool = false;
		try {
			view = this.saveViewImgs(view, basePath, viewImgs, view360Full);
			this.save(view);
			bool = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}
	
	/**
	 * 通过destinationCode来查询有效景点
	 * @param destinationCode
	 * @return
	 */
	public List<View> findViewByDestinationCode(String destinationCode){
		return viewDao.findViewByDestinationCode(destinationCode);
	}
	
	public Pager getAll(Pager pager){
		pager = viewDao.findWithPagerByArray("from View", pager);
		return pager;
	}
	
	/*public List<View> search(View view){
		return viewDao.searchViewByIdName(view);
	}*/
	
	public Pager testPager(Pager pager){
		return viewDao.testPager(pager);
	}
	
	@Override
	public Pager searchViewByKeyWords(Pager pager, String destinationCode, String viewCode, String viewName, String keyWords, int orderBy){
		return viewDao.searchViewByKeyWords(pager, destinationCode, viewCode, viewName, keyWords, orderBy);
	}

	/********************************************
	 *  other method
	 ********************************************/
	
	/**
	 * 保存景点图片和景点360图片
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public View saveViewImgs(View view, String basePath, MultipartFile[] viewImgs, @RequestParam MultipartFile view360Full) throws IllegalStateException, IOException{
		String img_path = ReadSettingProperties.getValue("upload")+"/img/viewImg/viewImg/" + view.getCode() + "/";
		String img360_path = ReadSettingProperties.getValue("upload")+"/img/viewImg/view360/" + view.getCode() + "/";
		
		String oldimg = view.getViewImage();
		//保存景点图片
		StringBuffer viewImgsArray = new StringBuffer(oldimg!=null?oldimg:"");
		for (int i = 0; i < viewImgs.length; i++) {
			MultipartFile multipartFile = viewImgs[i];
			String time = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date(System.currentTimeMillis())) + i;
			String fileType = FileOperate.getFileExt(multipartFile.getOriginalFilename(), true);
			//if(!fileType.equals(""))fileType = "." + fileType;
			
			String filename = time + fileType;
			FileUtil.uploadFile(multipartFile, basePath+img_path, filename);
			viewImgsArray.append("," + img_path + filename);
		}
		view.setViewImage(viewImgsArray.toString());
		if(viewImgsArray.length()>0 && viewImgsArray.indexOf(",")==0){
			view.setViewImage(viewImgsArray.substring(1));
		}
		
		//保存景点360图片
		String time = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date(System.currentTimeMillis()));
		String fileType = FileOperate.getFileExt(view360Full.getOriginalFilename(), true);
		//if(!fileType.equals(""))fileType = "." + fileType;
		
		String filename = time + fileType;
		FileUtil.uploadFile(view360Full, basePath+img360_path, filename);
		view.setView_360full(img360_path + filename);
		return view;
	}
	
	/**
	 * 获得景点分页
	 */
	@Override
	public Pager getViewPager(String destinationCode, String viewName,
			Pager pager) {
		return viewDao.getViewPager(destinationCode,viewName,pager);
	}
	/**
	 * 删除选中的景点
	 */
	@Override
	public void deleteSelect(String[] codes) {
		
		for(int i = 0; i<codes.length;i++){
				View v = viewDao.findByCode(codes[i]);
				v.setAvaliable(0);
				v.setLastEditTime(new Timestamp(new Date().getTime()));
				viewDao.update(v);
			
		}		
	}
	/**
	 * 保存景点
	 */
	@Override
	public void saveView(View view) {
		if(StringUtils.isNotBlank(view.getCode())){
			View dbview = viewDao.findByCode(view.getCode());
			dbview.setAvaliable(0);
			dbview.setLastEditTime(new Timestamp(new Date().getTime()));
			viewDao.update(dbview);
			//将老数据置为无效 新增一个新的数据
			View newView = new View();
			newView.setViewName(view.getViewName());
			newView.setViewImage(view.getViewImage());
			newView.setViewIntroduce(view.getViewIntroduce());
			newView.setView_360full(view.getView_360full());
			newView.setGoneCount(dbview.getGoneCount());
			newView.setWantCount(dbview.getWantCount());
			newView.setIsHaveGateTicket(dbview.getIsHaveGateTicket());
			newView.setCreateTime(view.getCreateTime());
			newView.setCode(view.getCode());
			newView.setTicketPrice(view.getTicketPrice());
			newView.setTicketUrl(view.getTicketUrl());
			newView.setAvaliable(1);
			newView.setLastEditTime(new Timestamp(new Date().getTime()));
			newView.setKeyword(view.getKeyword());
			newView.setFakeCheckNum(dbview.getFakeCheckNum());
			newView.setFakeGoneCount(view.getFakeGoneCount());
			newView.setFakeWantCount(view.getFakeWantCount());
			newView.setLikeNum(dbview.getLikeNum());
			newView.setLinkUrl("portal/view/forViewDetail?code="+view.getCode());
			newView.setAddress(view.getAddress());
			newView.setHdFullUrl(view.getHdFullUrl());
			newView.setGuide(view.getGuide());
			newView.setNotice(view.getNotice());
			newView.setRealSceneVideoUrl(view.getRealSceneVideoUrl());
			newView.setReplyNum(dbview.getReplyNum());
			newView.setTraffic(view.getTraffic());
			newView.setView_360full(view.getView_360full());
			viewDao.save(newView);
		}else{			
			view.setAvaliable(1);
			view.setCode(CodeFactory.createCultureCode());
			view.setCreateTime(new Timestamp(new Date().getTime()));
			view.setLinkUrl("portal/view/forViewDetail?code="+view.getCode());
			viewDao.save(view);
		}
	}
	
	/**
     * 点赞
     */
    public void updateLikeNumByCode(String code){
        View view = viewDao.findByCode(code);
        view.setLikeNum(view.getLikeNum()+1);
        view.setFakeLikeNum(view.getFakeLikeNum()+1);
        viewDao.update(view);
    }
    /**
     * 取消点赞
     */
    public void updateUnLikeNumByCode(String code){
        View view = viewDao.findByCode(code);
        view.setLikeNum(view.getLikeNum()-1);
        view.setFakeLikeNum(view.getFakeLikeNum()-1);
        viewDao.update(view);
    }
    
    /**
     * 查看数+1
     */
    public void updateCheckNumByCode(String code){
        View view = viewDao.findByCode(code);
        if (view!=null) {
        	view.setCheckNum(view.getCheckNum()+1);
        	view.setFakeCheckNum(view.getFakeCheckNum()+1);
			
		}
        viewDao.update(view);
    }
    
    /**
     * 收藏数排序
     */
    @Override
    public List<View>  orderByFav(int i) {
         return viewDao.orderByFav(i);        
    }
	
    @Override
    public Destination getDestinationByView(String code) {
    	return viewDao.getDestinationByView(code);
    }
    

    @Override
    public void deleteView(String[] codes) {
        for (String code : codes) {
            deleteLogicalByCode(code);
            contentDao.deleteReplyByPostCodeLogical(code, Content.DETAIL_VIEW_REPLY);
            
            WebSearch webSearch = new WebSearch();
            webSearch.setCode(code);
            LuceneUtil2.delete(webSearch);
            
            //删除热门景点管理的数据
            imageService.deleteBySql("delete  from image where viewCode='"+code+"'");
            
        }
    }

    
	/********************************************
	 *  Setter Getter
	 ********************************************/
	public IViewDao getViewDao() {
		return viewDao;
	}
	public void setViewDao(IViewDao viewDao) {
		this.viewDao = viewDao;
	}
	
	public static void main(String[] args) {
		
	}

	@Override
	public List<Map<String, Object>> getViewByWann(int num,String descode) {
		if(num == 0)
			num = 7;
		return viewDao.getViewByWann(num,descode);
	}
	@Override
	public List<Map<String, Object>> getViewByGone(int num,String descode) {
		if(num == 0)
			num = 7;
		return viewDao.getViewByGone(num,descode);
	}

	@Override
	public boolean isFirstInHotView() {
		return viewDao.isFirstInHotView();
	}

	@Override
	public List<Map<String, Object>> getHotView() {
		return viewDao.getHotView();
	}

    @Override
    public int getViewRelationContent(String viewCode) {
        return viewDao.getViewRelationContent(viewCode);
    }

    @Override
    public int getSameViewNum(String name) {
        return viewDao.getSameViewNum(name);
    }

}
