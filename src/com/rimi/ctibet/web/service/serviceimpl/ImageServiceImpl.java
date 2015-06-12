package com.rimi.ctibet.web.service.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.rimi.ctibet.common.constants.Constants;
import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.DateUtil;
import com.rimi.ctibet.common.util.FileUtil;
import com.rimi.ctibet.common.util.ImagePager;
import com.rimi.ctibet.common.util.ListUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.Uuid;
import com.rimi.ctibet.common.util.cache.CacheOperation;
import com.rimi.ctibet.domain.Image;
import com.rimi.ctibet.domain.Praise;
import com.rimi.ctibet.web.dao.IPraiseDao;
import com.rimi.ctibet.web.dao.ImageDao;
import com.rimi.ctibet.web.service.ImageService;


/**
 * 
 * @author xiangwq
 *@date 2014/11/21
 */
@Transactional
@Repository("ImageService")
public class ImageServiceImpl extends BaseServiceImpl<Image> implements ImageService {
	private Logger log = Logger.getLogger(getClass());
	@Resource
	private ImageDao imageDao;
    @Resource
    private IPraiseDao praiseDao;


	
	/**
	 * 通过图集ID获取图片
	 */
	@Override
	public List<Image> getImageByMutiImageCode(String MutiImageCode) {
		return imageDao.findImageByMutiImageCode(MutiImageCode);
	}
    
	

	/**
	 * 上传图片
	 * @param images 页面获取的要上传的图片
	 * @param path 在controller获取的跟目录
	 * @param tsp 获取点击上传时的时间戳
	 * @return 返回图片保存到服务器的地址List
	 */
	@Override
	public List<String> uploadImg(List<MultipartFile> images,
			String path,Timestamp tsp) {
		List<String> listPath = new ArrayList<String>();
		String s =  "/upload" + "/img/home/" + tsp.getTime();
		for (MultipartFile img : images) {
			String imgPath = path+ s;  //存入图片的路径
			try {
				// img.getOriginalFilename()  不能为中文
				String imgShowPath = "";
				 imgShowPath = CodeFactory.createMutiCode()+img.getOriginalFilename().substring(img.getOriginalFilename().lastIndexOf("."));
				FileUtils.copyInputStreamToFile(img.getInputStream(), new File(
						imgPath, imgShowPath));
				//这里不用存 http.../ctibet 项目路径，，在前台获取就行了。这样更灵活 中文路径图片，经常找不到，使用英文的方式
				String serverPath =  s +"/"+imgShowPath;
				//System.out.println(serverPath+"--------------------");
				//String serverPath =  s +"/"+CodeFactory.createMutiCode();
				listPath.add(serverPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//System.out.println("uploadImg(serverPath)----------------------------->"+listPath.get(0));
		return listPath;
	}
	/**
	 * 文件保存 到硬盘
	 * @param images 要保存的文件
	 * @param dir 保存的目录
	 * @return 返回  对应文件保存的路径和名称; 不包含传入的dir，eg:201406/mmfile.jsp
	 */

	@Override
	public List<String> outPutFile(List<MultipartFile> images,
			String dir) {
		List<String> listPath = new ArrayList<String>();
		Date date = new Date();
		String filePre=(date.getTime()+"").substring(6);
		String yyyyMM= DateUtil.formatDateTime(date, "yyyyMM");
		String currDir="/"+yyyyMM;
		String path =  dir+currDir;
		File fileDir= new File(path);
		if(!fileDir.exists())
		{
			fileDir.mkdirs();
		}
		for (MultipartFile img : images) {
			try {
				String filename=filePre+img.getOriginalFilename();
				FileUtils.copyInputStreamToFile(img.getInputStream(), new File(
						path, filename));
				listPath.add(currDir+"/"+filename);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		log.info("--------------------"+listPath.get(0));
		return listPath;
	}
	/**
	 * 保存图片（新增和修改）
	 */
	@Override
	public void saveImage(Image image) {
		    // *************************************如果传入的图片有CODE，说明是修改***************************//
			if (StringUtils.isNotBlank(image.getCode())) {
				 imageDao.deleteBySql("delete from image where  code='"+image.getCode()+"'");
			} 
			// ********************************传入没CODE，新增图片***********************************//
			Image newImage = replaceImage(image);
			newImage.setCode(Uuid.uuid());
			newImage.setCreateTime(new Timestamp(new Date().getTime()));
			newImage.setUrl(image.getUrl());
			newImage.setName(image.getName());
			newImage.setSummary(image.getSummary());
			newImage.setHyperlink(image.getHyperlink());
			newImage.setIsshow(image.getIsshow());
			newImage.setGoCount(image.getGoCount());
			newImage.setWantCount(image.getWantCount());
			newImage.setDestinationCode(image.getDestinationCode());
			newImage.setViewCode(image.getViewCode());
			newImage.setSmimg(image.getSmimg());
			imageDao.save(newImage);
			//再建立一个图片的praise
			Praise p = new Praise();
			p.setAvaliable(1);
			p.setCode(Uuid.uuid());
			p.setCreateTime(new Timestamp(new Date().getTime()));
			p.setContentCode(newImage.getCode());
			p = setNum(p);		
			praiseDao.save(p);
			
		
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
	 * 新旧图片交换
	 * 
	 * @param image
	 */
	public Image replaceImage(Image image) {
		Image newImage = new Image();
		newImage.setCode(image.getCode());
		newImage.setAvaliable(1);
		newImage.setCreateTime(image.getCreateTime());
		newImage.setMutiCode(image.getMutiCode());
		newImage.setLastEditTime(new Timestamp(new Date().getTime()));
		newImage.setName(image.getName());
		newImage.setSummary(image.getSummary());
		newImage.setCreateUserCode(image.getCreateUserCode());
		newImage.setEditUserCode(image.getEditUserCode());
		newImage.setUrl(image.getUrl());
		newImage.setHyperlink(image.getHyperlink());
		return newImage;
	}
   /**
    * 通过url查询图片
    * @param url
    * @return
    */
	public List<Image> findImageByUrl(String url){
		return imageDao.findImageByUrl(url);		
	}
	
	/**
	 * 删除选中图片
	 */
	@Override
	public void deleteSelect(String[] codes) {
		for (int i = 0; i < codes.length; i++) {
			// **************************************通过Code获取图片对象，置为无效，再保存图片**************************//
			Image image = imageDao.findByCode(codes[i]);
			//System.out.println("imgurl-------------->"+image.getUrl());
			List<Image> imageList = this.findImageByUrl(image.getUrl());
			if(imageList.size()>0){
				for(Image img : imageList){
					img.setAvaliable(0);
					img.setLastEditTime(new Timestamp(new Date().getTime()));
					imageDao.update(img);
				}
			}
			
		}
	}

	/**
	 * 获取图片列表并分页
	 */
	@Override
	public ImagePager getImagePager(ImagePager pager,
			int pageSize, String MutiImageCode) {
		return imageDao.getImagePager(pager, pageSize, MutiImageCode);
	}
    
	
	public Map<String, List<Image>> getTravelHomeImg(){
		Map<String, List<Image>> travelImageMap = new HashMap<String, List<Image>>();
		List<Image> travelBannerImageList = new ArrayList<Image>();
		List<Image> travelrecomOneImageList = new ArrayList<Image>();
		List<Image> travelrecomTwoImageList = new ArrayList<Image>();
		List<Image> travelrecomThreeImageList = new ArrayList<Image>();
	    travelBannerImageList = this.getImageByMutiImageCode("ac32abcc-235f-424a-9553-55ab647c86443");
	    travelrecomOneImageList = this.getImageByMutiImageCode("ad13bd22-a3cd-521a-a8c9-beb96e25ed89");
	    travelrecomTwoImageList = this.getImageByMutiImageCode("ca35adc9-b2bc-42cd-a8c9-abc42e34ab12");
	    travelrecomThreeImageList = this.getImageByMutiImageCode("ad1ae219-123c-a2cd-b8c9-aeb96e25e3d9");
	    travelImageMap.put("travelBannerList", travelBannerImageList); //游西藏首页banner图片列表
	    travelImageMap.put("travelRecomOneList", travelrecomOneImageList); //游西藏首页推荐位1图片列表
	    travelImageMap.put("travelRecomTwoList", travelrecomTwoImageList); //游西藏首页推荐位2图片列表
	    travelImageMap.put("travelRecomThreeList", travelrecomThreeImageList);//游西藏首页推荐位3图片列表
	    return travelImageMap;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, List<Image>> getHomeImg() {
		return (Map<String, List<Image>>) CacheOperation.getInstance().getCacheData(this, "getHomeImgHandler", null, Constants.intervalTime, Constants.maxVisitCount);
	}
	/**
	 * 获取首页管理的数据
	 */
	public Map<String, List<Image>> getHomeImgHandler() {
		Map<String, List<Image>> homeImageMap = new HashMap<String, List<Image>>();
		List<Image> bannerImageList = new ArrayList<Image>();
		List<Image> viewImageList = new ArrayList<Image>();
		List<Image> recomOneImageList = new ArrayList<Image>();
		List<Image> recomTwoImageList = new ArrayList<Image>();
		List<Image> recomThreeImageList = new ArrayList<Image>();
		List<Image> readImageList = new ArrayList<Image>();
		List<Image> customImageList = new ArrayList<Image>();
		List<Image> historyImageList = new ArrayList<Image>();
		List<Image> religionImageList = new ArrayList<Image>();
		List<Image> otherImageList = new ArrayList<Image>();
		List<Image> pictureImageList = new ArrayList<Image>();
		List<Image> windowImageList = new ArrayList<Image>();
		//TODO do something
		bannerImageList = getImageByMutiImageCode("77ffa15c-0192-4ded-91f5-9ac29a651ee4");
		homeImageMap.put("homeBannerList", bannerImageList); //首页banner图片列表
		viewImageList = getImageByMutiImageCode("fe33dccc-415f-424a-9553-775647c86443");
		homeImageMap.put("homeViewList", viewImageList);   //首页景点
		recomOneImageList = getImageByMutiImageCode("d321de41-cd1e-4576-ba3a-4d1183ff714b");
		homeImageMap.put("homeRecomOneList", recomOneImageList); //推荐1
		recomTwoImageList = getImageByMutiImageCode("4debe6ec-dbbf-45ee-86a4-fbeb3d1b28af");
		homeImageMap.put("homeRecomTwoList", recomTwoImageList); //推荐2
		recomThreeImageList = getImageByMutiImageCode("32713644-32f0-4486-8b4b-048e43031035");
		homeImageMap.put("homeRecomThreeList", recomThreeImageList); //推荐3
		readImageList = getImageByMutiImageCode("0f533b63-8397-4847-a225-2d9e1352901e");
		homeImageMap.put("homeReadList", readImageList); //读西藏
		customImageList = getImageByMutiImageCode("64e186ad-2dae-4b43-94cb-27b872bc3ecd");
		homeImageMap.put("homeCustomList", customImageList); //风俗
		historyImageList = getImageByMutiImageCode("d4b4d070-3c54-41f7-95bb-cd11f0c973ab");
		homeImageMap.put("homeCultureList", historyImageList); //文化
		religionImageList = getImageByMutiImageCode("7eb1978f-07d7-4a3e-a74b-ae44a584cb85");
		homeImageMap.put("homeReligionList", religionImageList); //宗教
		otherImageList = getImageByMutiImageCode("df543ag3-a397-a84w-aa25-gd9e2352901e");
		homeImageMap.put("homeOtherList", otherImageList); //其他				
		pictureImageList = getImageByMutiImageCode("789g65c5-99d8-4a37-9d13-414b80qe36g6");
		homeImageMap.put("homePictureList", pictureImageList); //图说西藏
		windowImageList = getImageByMutiImageCode("648fw15c-0192-4ded-91f5-9ac29a651ee4");
		homeImageMap.put("homeWindowList", windowImageList);  //浮窗

		return homeImageMap;
	}

	/**
	 * 通过活动code查询对应的其他模块信息
	 * @param ActivityCode
	 * @return
	 */
	public List<Image> getActivityOtherBlockByActivityCode(String ActivityCode){
		return  imageDao.getActivityOtherBlockByActivityCode(ActivityCode);
	}
	/**
	 * 通过活动code查询对应的其他模块信息
	 * @param ActivityCode
	 * @return
	 */
	public Pager getActivityOtherBlockByActivityCode(Pager pager, String activityCode){
		return imageDao.getActivityOtherBlockByActivityCode(pager, activityCode);
	}
	
	/**
	 * 通过图集code获取图片
	 * @param mutiCode
	 */
	public List<Image> getImageByMutiCode(String mutiCode){
		return imageDao.findImageByMutiImageCode(mutiCode);
	}
	
	/**
	 * 通过图集code获取图片 并初始化指定数量的image
	 */
	public List<Image> getImageByMutiCodeImageNum(String mutiCode, int imageNum, String name, String summary){
		int size = imageNum;
		int listSize = 0;
		List<Image> list = imageDao.getImageByMutiCode(mutiCode);
		if(ListUtil.isNotNull(list)){
			listSize = list.size();
		}
		if(listSize > size){
			for (int i = 0; i < (listSize - size); i++) {
				imageDao.delete(list.get(i));
			}
		}else if(listSize < size){
			for (int i = 0; i < (size - listSize); i++) {
				Image image = new Image(1, Uuid.uuid(), mutiCode, name, summary);
				imageDao.save(image);
				list.add(image);
			}
		}
		return imageDao.getImageByMutiCode(mutiCode);
	}
	
	/**
	 * 删除Image
	 * @param listImage
	 */
	public void updateImageList(List<Image> listImage){
		for (Image image : listImage) {
			imageDao.update(image);
		}
	}
	/**
	 * 图片分页
	 */
	@Override
	public Pager findImagePager(String code, Pager pager) {
		return imageDao.findImagePager(code,pager);
	}
	/**
	 * 用户上传图片的分页
	 */
	@Override
	public Pager findUserImgPager(String destinationCode,String mutiCode,Pager pager) {
		return imageDao.findUserImgPager(destinationCode,mutiCode,pager);
	}

	@Override
    public Pager getViewAtlasByCodeType(Pager pager, String viewCode, String type){
	    return imageDao.getViewAtlasByCodeType(pager, viewCode, type);
	}

	@Override
	public void deleteAtlasByMutiCode(String mutiCode) {
	    imageDao.deleteAtlasByMutiCode(mutiCode);
	}

    @Override
    public void deleteImages(String basePath, String[] codes) {
        for (String code : codes) {
            /*//把图片移动到删除目录
            Image image = findByCode(code);
            if(image!=null){
                String filepath = basePath + image.getUrl();
                File file = new File(filepath);
                String parent = file.getParent();
                String name = file.getName();
                FileUtil.moveFile(filepath, parent + "/delete/" + name);
                new File(filepath).delete();
            }*/
            deleteLogicalByCode(code);
        }
    }
    
    @Override
    public Pager getDestinationCodeAtlasByCodeType(Pager pager, String destinationCode, String type) {
        return imageDao.getDestinationCodeAtlasByCodeType(pager, destinationCode, type);
    }
    
    @Override
    public void deleteAtlasByUrl(String url){
        imageDao.deleteAtlasByUrl(url);
    }
    @Override
    public void deleteAtlasByUrls(String basePath, String[] urls){
        for (String url : urls) {
            /*String filepath = basePath + url;
            File file = new File(filepath);
            String parent = file.getParent();
            String name = file.getName();
            FileUtil.moveFile(filepath, parent + "/delete/" + name);*/
            imageDao.deleteAtlasByUrl(url);
        }
    }
	
	/********************************************
     * Setter Getter
     ********************************************/
    public ImageDao getImageDao() {
        return imageDao;
    }



    public void setImageDao(ImageDao imageDao) {
        this.imageDao = imageDao;
    }



    public IPraiseDao getPraiseDao() {
        return praiseDao;
    }



    public void setPraiseDao(IPraiseDao praiseDao) {
        this.praiseDao = praiseDao;
    }



    public static void main(String[] args) {
        File file = new File("D:\\t.png");
        String parent = file.getParent();
        String name = file.getName();
        FileUtil.moveFile("D:\\t.png", parent + "/delete/" + name);
    }


	
}
