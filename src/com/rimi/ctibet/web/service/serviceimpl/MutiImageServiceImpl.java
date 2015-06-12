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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.rimi.ctibet.common.util.FileHelper;
import com.rimi.ctibet.common.util.ListUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.ReadSettingProperties;
import com.rimi.ctibet.common.util.Uuid;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Image;
import com.rimi.ctibet.domain.MutiImage;
import com.rimi.ctibet.domain.Praise;
import com.rimi.ctibet.web.controller.vo.MutiImageVO;
import com.rimi.ctibet.web.dao.IContentDao;
import com.rimi.ctibet.web.dao.IPraiseDao;
import com.rimi.ctibet.web.dao.ImageDao;
import com.rimi.ctibet.web.dao.MutiImageDao;
import com.rimi.ctibet.web.service.ImageService;
import com.rimi.ctibet.web.service.MutiImageService;

/**
 * 
 * @author xiangwq
 * 
 */
@Repository("MutiImageService")
@Transactional
public class MutiImageServiceImpl implements MutiImageService {
	@Resource
	private MutiImageDao mutiImageDao;
	@Resource
	private ImageService imageService;
	@Resource
	private ImageDao imageDao;
	@Resource
	private IPraiseDao praiseDao;
	@Resource
	private IContentDao contentDao;

	/**
	 * 更新图集url
	 * 
	 * @param muti
	 * @param path
	 * @param mutis
	 * @return
	 */
	public MutiImage updateUrl(MutiImage muti, String path,
			List<MultipartFile> mutis) {
		// 将老图片全部删除
		if (StringUtils.isNotBlank(muti.getCode())) {
			String[] oldImgs = muti.getCoverUrl().split(",");
			//System.out.println(oldImgs.length);
			for (int j = 0; j < oldImgs.length; j++) {
				String oldImg = oldImgs[j];
				if (StringUtils.isNotBlank(oldImg)) {
					File oldFile = new File(path + oldImg);
					//System.out.println("-------------------------------"
					//		+ oldFile.getAbsolutePath()
					//		+ "--------------------------------");
					try {
						oldFile.delete();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		muti.setCoverUrl("");
		// 给图片赋值新路径
		for (MultipartFile mu : mutis) {
			String imgPath = path + muti.getCode();
			try {
				FileUtils.copyInputStreamToFile(mu.getInputStream(), new File(
						imgPath, mu.getOriginalFilename()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			muti.setCoverUrl(muti.getCoverUrl() + muti.getCode() + "/"
					+ mu.getOriginalFilename() + ",");
		}
		return muti;
	}

	/**
	 * 上传图片并设置Url
	 * 
	 * @param image
	 * @param path
	 * @param images
	 * @return
	 */
	public MutiImage uploadImage(MutiImage muti, String path,
			List<MultipartFile> images) {
		String mutiCode = Uuid.uuid();
		for (MultipartFile img : images) {
			String imgPath = path + mutiCode;
			try {
				FileUtils.copyInputStreamToFile(img.getInputStream(), new File(
						imgPath, img.getOriginalFilename()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			muti.setCoverUrl(muti.getCoverUrl() + muti.getCode() + "/"
					+ img.getOriginalFilename() + ",");
		}
		return muti;
	}

	/**
	 * 保存图集
	 */
	@Override
	public void saveMutiImage(HttpServletRequest request,
			HttpServletResponse response, MutiImage muti) {
		// ***********************************************从名为coverImgs的上传空间获取的LIST************************
		List<MultipartFile> coverImgs = FileHelper.getFileSet(request,
				1024 * 20, null, "coverImgs");
		String path = request.getSession().getServletContext().getRealPath("/")
				+ "/WebRoot" + ReadSettingProperties.getValue("upload")
				+ "/img" + "/coverimg" + "/";

		// **************************************如果传入的code不为空，则是修改************************************
		if (StringUtils.isNotBlank(muti.getCode())) {

			MutiImage dbMuti = mutiImageDao.findByCode(muti.getCode());
			dbMuti.setAvaliable(0);
			dbMuti.setLastEditTime(new Timestamp(new Date().getTime()));
			mutiImageDao.save(dbMuti);
			// ***************************************因为没有建立user对象，创建人，修改人先不设置*****************************
			// ******************************************dbMuti.setEditUserCode*************************************
			MutiImage newmuti = replaceMuti(muti);
			updateUrl(newmuti, path, coverImgs);
			mutiImageDao.save(newmuti);
		} else {
			MutiImage newmuti = replaceMuti(muti);
			uploadImage(newmuti, path, coverImgs);
			newmuti.setCode(Uuid.uuid());
			newmuti.setCreateTime(new Timestamp(new Date().getTime()));
			mutiImageDao.save(newmuti);
		}

	}

	@Override
	public void saveMuti(MutiImage muti) {
		if (muti.getId() != 0) {
			MutiImage dbMuti = mutiImageDao.findByCode(muti.getCode());
			dbMuti.setAvaliable(0);
			dbMuti.setLastEditTime(new Timestamp(new Date().getTime()));
			mutiImageDao.update(dbMuti);
			MutiImage newmuti = replaceMuti(muti);
			mutiImageDao.save(newmuti);
		} else {
			MutiImage newmuti = replaceMuti(muti);
			List<Image> imglist =imageDao.getImageByMutiCode(newmuti.getCode());
			mutiImageDao.save(newmuti);
		}
	}



	/**
	 * 交换新旧对象
	 * 
	 * @param muti
	 * @return
	 */
	public MutiImage replaceMuti(MutiImage muti) {
		MutiImage newm = new MutiImage();
		newm.setAvaliable(1);
		newm.setCode(muti.getCode());
		newm.setCoverUrl(muti.getCoverUrl());
		newm.setCreateTime(muti.getCreateTime());
		newm.setCreateUserCode(muti.getCreateUserCode());
		newm.setDescription(muti.getDescription());
		newm.setDetail(muti.getDetail());
		newm.setDirectory(muti.getDirectory());
		newm.setEditUserCode(muti.getEditUserCode());
		newm.setId(muti.getId());
		newm.setKeywords(muti.getKeywords());
		newm.setLastEditTime(new Timestamp(new Date().getTime()));
		newm.setName(muti.getName());
		newm.setProgramaCode(muti.getProgramaCode());
		newm.setSummary(muti.getSummary());
		newm.setTitle(muti.getTitle());
		newm.setIsOfficial(muti.getIsOfficial());
		newm.setHyperlink(muti.getHyperlink());
		newm.setSeeCount(muti.getSeeCount());
		newm.setTdkTitle(muti.getTdkTitle());
		newm.setTdkDescription(muti.getTdkDescription());
		newm.setTdkKeywords(muti.getTdkKeywords());
		return newm;
	}

	/**
	 * 删除选择的图集
	 */
	@Override
	public void deleteSelect(String[] codes) {
		for (int i = 0; i < codes.length; i++) {
			MutiImage muti = mutiImageDao.findByCode(codes[i]);
			muti.setAvaliable(0);
			muti.setLastEditTime(new Timestamp(new Date().getTime()));
			List<Image> imageList = imageService.getImageByMutiImageCode(muti
					.getCode());
			
			for (Image img : imageList) {
				img.setAvaliable(0);
				img.setLastEditTime(new Timestamp(new Date().getTime()));
				imageDao.update(img);
			}
			contentDao.deleteReplyByPostCodeLogical(muti.getCode(), Content.DETAIL_PICTURE_REPLY);//删除评论
			mutiImageDao.update(muti);
		}
	}

	/**
	 * 获取图集列表并分页
	 */
	@Override
	public Pager getMutiImagePager(Pager pager, String programaCode) {
		return mutiImageDao.getMutiImagePager(pager, programaCode);
	}

	/**
	 * 携带图片和查看数量信息
	 * 
	 */
	@Override
	public Pager findMutiPagerOnImages(Pager pager, String programaCode) {
		pager = mutiImageDao.getMutiImagePager(pager, programaCode);
		List<MutiImage> mutis = pager.getDataList();
		if (null == mutis)
			return pager;
		List<MutiImage> mutiImages = new ArrayList<MutiImage>();
		for (MutiImage mutiImage : mutis) {
			mutiImage.setPraise(praiseDao.getPraiseContentCode(mutiImage
					.getCode()));
			mutiImage
					.setImages(imageDao.getImageByMutiCode(mutiImage.getCode()));
		}
		return pager;
	}

	/**
	 * 通过栏目code获取图集
	 */
	@Override
	public List<MutiImage> getMutiImageByProgramaCode(String programaCode) {
		List<MutiImage> mutiList = mutiImageDao
				.findMutiImageByProgramaCode(programaCode);
		//System.out.println("mutiService-------mutilist>" + mutiList.size());
		if (mutiList.size() > 0) {
			return mutiList;
		} else
			return null;
	}

	@Override
	public MutiImage getMutiImageByCode(String code) {
		return mutiImageDao.findByCode(code);
	}

	/**
	 * 通过 programaCode获取图集信息MutiImageVO （包含 MutiImage， List<Image>）
	 */
	public MutiImageVO getMutiImageVO(String programaCode) {
		MutiImageVO mutiImageVO = mutiImageDao
				.getMutiImageByProgramaCode(programaCode);
		if (mutiImageVO != null) {
			String mutiCode = mutiImageVO.getCode();
			List<Image> listImage = imageDao.getImageByMutiCode(mutiCode);
			if (ListUtil.isNotNull(listImage)) {
				mutiImageVO.setListImage(listImage);
			}
		}
		return mutiImageVO;
	}

	/**
	 * 前台分页
	 */
	@Override
	public Pager getFrontMutiPager(Pager pager, String action) {
		return mutiImageDao.getFrontMutiPager(pager, action);
	}
	
	@Override
	public Pager findListByPager(String sql, List param, Pager pager) {
		// TODO Auto-generated method stub
		return mutiImageDao.findListPagerBySql(MutiImage.class, pager, sql,
				param);
	}

	@Override
	public Map preAndNextMuti(MutiImage mutiImage) {
		int rowNo = mutiImageDao.findRowNo(mutiImage);
		MutiImage premuti = null;
		MutiImage nextmuti = null;
		try {
			premuti = mutiImageDao.findbyRowNo(mutiImage, rowNo - 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			nextmuti = mutiImageDao.findbyRowNo(mutiImage, rowNo + 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map map = new HashMap<String,MutiImage>();
		map.put("pre", premuti);
		map.put("next",nextmuti);
		return map;
	}
	
	@Override 
	public Pager search(Pager pager,MutiImage mutiImage,String orderField) {
	 return mutiImageDao.search(pager, mutiImage, orderField);
	}

}
