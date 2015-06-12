package com.rimi.ctibet.web.service.serviceimpl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.constants.Constants;
import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.KeyWordFilter;
import com.rimi.ctibet.common.util.LuceneUtil2;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.Uuid;
import com.rimi.ctibet.common.util.WebSearch;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.GroupTravel;
import com.rimi.ctibet.domain.Image;
import com.rimi.ctibet.domain.Merchant;
import com.rimi.ctibet.domain.MutiImage;
import com.rimi.ctibet.domain.Praise;
import com.rimi.ctibet.domain.ProgramaContent;
import com.rimi.ctibet.domain.Reply;
import com.rimi.ctibet.domain.StrategyView;
import com.rimi.ctibet.domain.SysMessage;
import com.rimi.ctibet.domain.View;
import com.rimi.ctibet.portal.controller.vo.FrontViewAndDesVo;
import com.rimi.ctibet.portal.controller.vo.TravalFrontPageVo;
import com.rimi.ctibet.web.controller.vo.ReplyManageVO;
import com.rimi.ctibet.web.dao.IContentDao;
import com.rimi.ctibet.web.dao.IPraiseDao;
import com.rimi.ctibet.web.dao.IProgramaContentDao;
import com.rimi.ctibet.web.dao.IReplyDao;
import com.rimi.ctibet.web.dao.IStrategyViewDao;
import com.rimi.ctibet.web.dao.MutiImageDao;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IGroupTravelService;
import com.rimi.ctibet.web.service.ISysMessageService;
import com.rimi.ctibet.web.service.IViewService;
import com.rimi.ctibet.web.service.ImageService;
@Transactional
@Service("contentService")
public class ContentServiceImpl extends BaseServiceImpl<Content> implements IContentService {
	@Resource
	IContentDao contentDao;
	@Resource
	IStrategyViewDao strategyViewDao;
	@Resource
	IProgramaContentDao programaContentDao;
	@Resource
	IReplyDao replyDao;
	@Resource
	IPraiseDao praiseDao;
	@Resource
	IViewService viewService;
	@Resource 
	MutiImageDao mutiImageDao;
	@Resource
	ImageService imageService;
	//退回帖子返回用户一个信息
	@Resource
	private ISysMessageService sysMessageService;
	@Resource
	private IGroupTravelService groupTravelService;
	
	private static Logger log = Logger.getLogger(ContentServiceImpl.class);

	/**
	 * 保存内容和栏目内容中间表 如果栏目code为空则只保存内容
	 * 
	 * @param content
	 * @param programaCode
	 */
	public void saveContentAndProgramaContent(Content content,
			String programaCode) {
		String contentCode = content.getCode();
		if (contentCode == null || contentCode.equals("")) {
			contentCode = Uuid.uuid();
			content.setCode(contentCode);
		}
		content.setCreateTime(new Date(System.currentTimeMillis()));
		content.setAvaliable(1);
		// 保存内容
		contentDao.save(content);

		// 保存栏目内容
		if (programaCode != null && !programaCode.equals("")) {
			ProgramaContent programaContent = new ProgramaContent(0,
					programaCode, contentCode);
			programaContentDao.save(programaContent);
		}
	}

	/**
	 * 删除内容和 栏目内容关联关系
	 * 
	 * @param content
	 */
	public void deleteContentAndProgramaContent(Content content) {
		String contentCode = content.getCode();
		// 删除内容
		contentDao.deleteLogicalByCode(contentCode);
		// 删除内容和栏目内容的关联关系
		programaContentDao.deleteByProperty("conCode", contentCode);
	}

	/**
	 * 批量删除内容和 栏目内容关联关系
	 * 
	 * @param contentCodes
	 */
	public void deleteContentAndProgramaContent(String[] contentCodes) {
		for (String contentCode : contentCodes) {
			// 删除内容
			contentDao.deleteLogicalByCode(contentCode);
			// 删除内容和栏目内容的关联关系
			programaContentDao.deleteByProperty("conCode", contentCode);
		}
	}
	public void deleteByCodes(List<String> codes)
	{
		for (String string : codes) {
			contentDao.deleteReplyByPostCodeLogical(string,null);
			contentDao.deleteLogicalByCode(string);
		}
	}
	public void deleteByCodes(String[] codes){
		for (String code : codes) {
			contentDao.deleteLogicalByCode(code);
			WebSearch webSearch = new WebSearch();
	        webSearch.setCode(code);
	        LuceneUtil2.delete(webSearch);
	        //删除评论中间表
	        replyDao.deleteBySql("delete  from reply  where  contentCode='"+code+"'");
	        
		}
	}

	/**
	 * 保存攻略 和栏目内容中间表 如果栏目code为空则只保存内容
	 * 
	 * @param content
	 * @param programaCode
	 * @param views
	 *            (需要code 和 destinationCode属性)
	 * @param destinationCodes
	 */
	public void saveStrategy(Content content, String programaCode, String[] viewCodes, String[] destinationCodes) {
		String contentCode = content.getCode();
		if (contentCode == null || contentCode.equals("")) {
			contentCode = Uuid.uuid();
			content.setCode(contentCode);
		     //content.setCode(CodeFactory.createCode(name))
		}
		content.setContentType(Content.CONTENTTYPE_STRATEGY);
		content.setCreateTime(new Date(System.currentTimeMillis()));
		content.setAvaliable(1);
		// 保存内容
		this.save(content);
		
		if (viewCodes!=null) {
			// 攻略关联景点
			for (String viewCode : viewCodes) {
				View view = findByCode(View.class, viewCode);
				String destinationCode = "";
				try {
					destinationCode = view.getDestinationCode();
				} catch (Exception e) {
					log.error("viewCode=" + viewCode + ",没有查询到景点,无法获取到景点所属目的地。", e);
				}
				StrategyView strategyView = new StrategyView(0, contentCode, viewCode, destinationCode);
				strategyViewDao.save(strategyView);
			}
		}
		if (destinationCodes!=null) {
			// 攻略关联目的地
			for (String destinationCode : destinationCodes) {
				StrategyView strategyView = new StrategyView(0, contentCode, "", destinationCode);
				strategyViewDao.save(strategyView);
			}
		}
		if (programaCode != null && !programaCode.equals("")) {
			ProgramaContent programaContent = new ProgramaContent(0, programaCode, contentCode);
			programaContentDao.save(programaContent);
		}
		
	}

	/**
	 * 修改攻略
	 * 
	 * @param content
	 * @param viewCodes
	 * @param destinationCodes
	 */
	public void updateStrategy(Content content, String[] viewCodes, String[] destinationCodes, String contextPath) {
		String contentCode = content.getCode();
		contentDao.updateLogical(content);
		// 保存攻略关联景点目的地时先删除旧数据
		strategyViewDao.deleteByContentCode(contentCode);
		// 攻略关联景点
		for (String viewCode : viewCodes) {
			View view = findByCode(View.class, viewCode);
			String destinationCode = "";
			try {
				destinationCode = view.getDestinationCode();
			} catch (Exception e) {
				log.error("viewCode=" + viewCode + ",没有查询到景点,无法获取到景点所属目的地。", e);
			}
			StrategyView strategyView = new StrategyView(0, contentCode,
					viewCode, destinationCode);
			strategyViewDao.save(strategyView);
		}
		// 攻略关联目的地
		for (String destinationCode : destinationCodes) {
			StrategyView strategyView = new StrategyView(0, contentCode, "",
					destinationCode);
			strategyViewDao.save(strategyView);
		}
		
	this.saveAtlas(contextPath, content);
	}

	/**
	 * 通过攻略code删除攻略以及攻略景点目的地的关联
	 * 
	 * @param code
	 */
	public void deleteStrategyByCode(String code) {
		// 删除攻略和栏目的关联关系
		programaContentDao.deleteByProperty("conCode", code);
		// 逻辑删除攻略
		contentDao.deleteLogicalByCode(code);
		// 删除攻略景点目的地的关联关系
		strategyViewDao.deleteByContentCode(code);
	}

	/**
	 * 通过攻略code批量删除攻略以及攻略景点目的地的关联
	 * 
	 * @param code
	 */
	public void deleteStrategyByCode(String[] codes) {
		for (String code : codes) {
			deleteStrategyByCode(code);
		}
	}

	/**
	 * 保存回复或子回复
	 * 
	 * @param content
	 * @param postCode
	 *            帖子code
	 */
	public void saveReply(Content content, String postCode) {
		// 回复内容
		String contentCode = content.getCode();
		if (contentCode == null || contentCode.equals("")) {
			contentCode = Uuid.uuid();
			content.setCode(contentCode);
		}
		String text = content.getContent();
		
		String bad = KeyWordFilter.getInstance().getReplaceStrTxtKeyWords(text, "**");
		content.setContent(bad);
		content.setCreateTime(new Date(System.currentTimeMillis()));
		content.setAvaliable(1);
		Reply reply = new Reply(0, Uuid.uuid(), postCode, contentCode, 0);
		// 保存回复
		contentDao.save(content);
		// 保存回复与帖子的关系
		replyDao.save(reply);
	}

	/**
	 * 通过code和内容类型查找有效数据
	 * 
	 * @param code
	 * @param contentType
	 * @return
	 */
	public Content findByCodeContentType(String code, String contentType) {
		return contentDao.findByCodeContentType(code, contentType);
	}

	/**
	 * 通过视频类型和标题查询有效数据
	 * 
	 * @param content
	 * @return
	 */
	public Pager findVedioByProgramaCodeTitle(Content content, Pager pager) {
		return contentDao.findVedioByProgramaCodeTitle(content, pager);
	}

	/**
	 * 通过帖子code删除帖子，并删除其回复和子回复 和关联关系
	 * 
	 * @param postCode
	 */
	public void deletePostByPostCode(String postCode) {
		// 删除帖子及其所有回复
		contentDao.deletePostByPostCode(postCode);
		// 删除帖子和所有回复的关联
		replyDao.deletePostByPostCode(postCode);
		// 删除帖子与栏目的关联
		programaContentDao.deleteByProperty("conCode", postCode);
	}

	/**
	 * 通过回复code删除回复，并删除其子回复 和关联关系
	 * 
	 * @param replyCode
	 */
	public void deleteReplyByReplyCode(String replyCode) {
		// 删除回复及其 子回复
		contentDao.deleteReplyByReplyCode(replyCode);
		// 删除回复和所有子回复的关联
		replyDao.deleteByProperty("contentCode", replyCode);
	}

	/**
	 * 通过子回复code删除子回复，并删除与回复的关联关系
	 * 
	 * @param subReplyCode
	 */
	public void deleteSubReplyBySubReplyCode(String subReplyCode) {
		// 删除子回复
		contentDao.deleteLogicalByCode(subReplyCode);
		// 删除子回复与回复的关系
		replyDao.deleteByProperty("contentCode", subReplyCode);
	}

	/**
	 * 通过栏目内容中间表中的栏目code获取content
	 * 
	 * @return
	 */
	public List<Content> findContentByProCode(String proCode) {
		return contentDao.findContentByProCode(proCode);
	}

	/**
	 * 通过栏目内容中间表中的栏目code获取指定行数的content
	 * 
	 * @return
	 */
	public List<Content> findContentByProCodeRow(String proCode, int row) {
		return contentDao.findContentByProCodeRow(proCode, row);
	}

	/**
	 * 通过栏目内容中间表中的栏目code获取content 分页
	 * 
	 * @return
	 */
	public Pager findContentByProCode(Pager pager, String proCode) {
		return contentDao.findContentByProCode(pager, proCode);
	}

	/**
	 * 通过目的地获取攻略列表
	 */
	public List<Content> findStrategyByDestinationCode(String destinationCode) {
		List<StrategyView> svList = strategyViewDao.findByProperty("destinationCode", destinationCode);
		List<Content> strategyList = new ArrayList<Content>();
		if(svList.size()>0){
			for(StrategyView sv : svList){
				strategyList.add(contentDao.findByCode(sv.getContentCode()));
			}
		}
		return strategyList;
	}
	
	
	/**
	 * 通过 审核状态、版块、目的地、景点、官方标记、[会员名、手机、邮箱、攻略标题(keyWord)]、攻略有效性 查询攻略列表
	 * 
	 * @param state
	 *            审核状态
	 * @param proCode
	 *            板块
	 * @param destinationCode
	 *            目的地
	 * @param viewCode
	 *            景点
	 * @param isOfficial
	 *            官方标记
	 * @param keyWord
	 *            关键字[会员名、手机、邮箱、攻略标题]
	 * @param avaliable
	 *            有效性
	 * @return
	 */
	public Pager searchStrategyByContentMember(Pager pager,
			Map<String, Object> paramMap,String order) {
		return contentDao.searchStrategyByContentMember(pager, paramMap,order);
	}

	/**
	 * 通过code更新审核状态
	 * 
	 * @param state
	 * @return
	 */
	public void updateState(String code, int state) {
		contentDao.updateState(code, state);
	}
	
	/**
	 * 查询后台 专题列表
	 * @param name
	 * @param time
	 * @param orderBy
	 * @return
	 */
	public Pager searchSpecailListByNameTime(Pager pager, String name, String time, int orderBy){
		return contentDao.searchSpecailListByNameTime(pager, name, time, orderBy);
	}
	
	/**
	 * 保存专题
	 * @param content
	 */
	public void saveSpecial(Content content){
		content.setAvaliable(1);
		content.setContentType(Content.CONTENTTYPE_SPECIAL);
		content.setCreateTime(new Date(System.currentTimeMillis()));
		save(content);
		
		Praise praise = new Praise();
		praise.setAvaliable(1);
		praise.setCode(Uuid.uuid());
		praise.setContentCode(content.getCode());
		praise.setCreateTime(new Date(System.currentTimeMillis()));
		praiseDao.save(praise);
	}
	
	/**
	 * 修改专题
	 * @param content
	 */
	public void updateSpecial(Content content, Praise praise){
		int falseViewcount = praise.getFalseViewcount();
		praise = praiseDao.findByCode(praise.getCode());
		if(praise!=null){
		    praise.setFalseViewcount(falseViewcount);
		    praiseDao.update(praise);
		}else{
		    praise = new Praise();
	        praise.setAvaliable(1);
	        praise.setCode(Uuid.uuid());
	        praise.setViewCount(0);
	        praise.setFalseViewcount(falseViewcount);
	        praise.setContentCode(content.getCode());
	        praise.setCreateTime(new Date(System.currentTimeMillis()));
	        praiseDao.save(praise);
		}
		
		Content oldContent = contentDao.findByCode(content.getCode());
		
		content.setAvaliable(oldContent.getAvaliable());
		content.setContentType(oldContent.getContentType());
		//content.setUrl(oldContent.getUrl());
		content.setCreateTime(oldContent.getCreateTime());
		updateLogical(content);
		
	}
	/**
	 * 通过目的地查询热门景点
	 */
	@Override
	public List<Content> findContentByDestinationCodeAndType(String code,String type) {
		return contentDao.findContentByDestinationCodeAndType(code,type);

	}
	/**
	 * 保存游西藏首页的推荐景点
	 */
	@Override
	public void saveTravelViews(String viewcode) {
		
		contentDao.deleteByProperty("contentType", "travelview");
		Content c = new Content();
		c.setAvaliable(1);
		c.setCode(CodeFactory.createCultureCode());
		c.setContent(viewcode); //保存以“，”分开的目的地code
		c.setCreateTime(new Date(System.currentTimeMillis()));
		c.setContentType("travelview");
		c.setProgramaCode("25b327a5-7e8c-12e4-b6ce-005056b896a3");
		contentDao.save(c);	
		
	}
	/**
	 * 获取游西藏首页的推荐景点list 
	 * @return
	 */
	public List<View> getTravelView(){
		Content c = contentDao.findTravelView();
		if (c!=null) {
			String viewcode = c.getContent();
			String[] viewcodes = viewcode.split(",");
			List<View> viewList = new ArrayList<View>();
			for(int i = 0 ; i <viewcodes.length ; i++){
			    View view = viewService.findByCode(viewcodes[i]);
			    //if(view!=null){
			        viewList.add(view);
			    //}
			}
			return viewList;
		}
		return null;
		
	}
   /**
    * 通过栏目号获取content
    */
	@Override
	public List<Content> findContentByProgrmaCode(String programaCode) {
		return contentDao.findContentByProgramaCode(programaCode);
	}
	
	/**
	 * 保存看西藏banner图片content
	 */
	@Override
	public void updateSeeBanner(List<Content> contentList,String programaCode) {
		List<Content> cList = findContentByProgrmaCode(programaCode);
		for(Content c : cList){
			c.setAvaliable(0);
			this.update(c);
		}
		for(Content c : contentList){
			c.setCode(Uuid.uuid());
			c.setCreateTime(new Timestamp(new Date().getTime()));
			this.save(c);
		}
	}

	

	/********************************************
	 * Setter Getter
	 ********************************************/
	public IContentDao getContentDao() {
		return contentDao;
	}

	public void setContentDao(IContentDao contentDao) {
		this.contentDao = contentDao;
	}

	public IStrategyViewDao getStrategyViewDao() {
		return strategyViewDao;
	}

	public void setStrategyViewDao(IStrategyViewDao strategyViewDao) {
		this.strategyViewDao = strategyViewDao;
	}

	public IProgramaContentDao getProgramaContentDao() {
		return programaContentDao;
	}

	public void setProgramaContentDao(IProgramaContentDao programaContentDao) {
		this.programaContentDao = programaContentDao;
	}

	public IReplyDao getReplyDao() {
		return replyDao;
	}

	public void setReplyDao(IReplyDao replyDao) {
		this.replyDao = replyDao;
	}

	@Override
	public Pager findContentsByType(Pager page, Content content) {
		return contentDao.findByContentType(content.getContentType(), page);
	}

	@Override
	public Pager search(Pager page, Content content,
			String orderField) {
		if(checkSqlInject(content)){
				return page;
		}
		return  contentDao.findContent(page,content,content.getContentType(), orderField);
	}
	/**
	 * 重写父类 方法 带有others 信息
	 */
	@Override 
	public  Content findByCode(String code)
	{	
			Content content =	contentDao.findByCode(code);
			findOthersInfo(content);
			return content;
	}

	private void findOthersInfo(Content content) {
		if(null!=content&&null!=content.getCode())
		content.setOthers(contentDao.findContentOthers(content.getCode()));
	}
    
	public Content getByCode(String code){
		return contentDao.findByCode(code);
	}

	@Override
	public Pager searchOnOthers(Pager page, Content content, String orderField) {
		if(checkSqlInject(content)){
			return page;
		}
		page =contentDao.findContent(page, content, content.getContentType(), orderField);
		List<Content> contents = new ArrayList<Content>();
	 	for (Content content2 : (List<Content>)page.getDataList()) {
	 		 findOthersInfo(content2);
	 		 content2.getContetNotHtml();
	 		 contents.add(content2);
		} 
		page.setDataList(contents);
		return page;
	}


	@Override
	public Pager findPagerTravel(Pager pager, Integer order, String programCode,String des,String view,String keyword,Integer ifoffical) {
		// TODO Auto-generated method stub
		return contentDao.findPagerTravel(pager, order, programCode,des,view,keyword,ifoffical);
	}
	
	/**
     * 通过 postCode和审核状态获取回复列表
     */
	public Pager getReplyByPostCodeState(Pager pager, String postCode, String contentType, int state){
        return contentDao.getReplyByPostCodeState(pager, postCode, contentType, state);
    }
    public TravalFrontPageVo getTravalFrontPageVo(String code){
        return contentDao.getTravalFrontPageVo(code);
    }

    @Override
    public Pager getFrontReplyVo(Pager pager, String postCode) {
        // TODO Auto-generated method stub
        return contentDao.getFrontReplyVo(pager, postCode);
    }
    public List<TravalFrontPageVo> getStrageList(Integer row, String code){
        return contentDao.getStrageList(row, code);
    }
    
    public List<FrontViewAndDesVo>  getFrontViewAndDesList(String searchType,String code){
        return contentDao.getFrontViewAndDesList(searchType, code);
        
    }

	@Override
	public Pager getMemberStraByState(String memberCode, String state,
			String programaCode, Pager pager) {
		// TODO Auto-generated method stub
		return contentDao.getMemberStraByState(memberCode, state, programaCode, pager);
	}


	@Override
	public Map findOthers(String code) {
		return contentDao.findContentOthers(code);
	}
	@Override 
	public boolean updateOthers(String contentCode,Map map)
	{
		contentDao.updateOrSaveOthers(contentCode,map);
		return true;
	}

	@Override
	public Pager getMyReplyByType(String sql, List params,Pager pager) {
		// TODO Auto-generated method stub
		return contentDao.findListPagerBySql(Content.class, pager, sql, params);
	}
	public Integer getReplyCount(final Date start, final Date end){
		
		return  contentDao.getReplyCount(start, end);
		
	}

	
    
    /**
     * 通过评论类型和审核状态搜索 评论回复管理列表
     */
    public Pager searchReplyByContentTypeState(Pager pager, String contentType, int state){
        pager = contentDao.searchReplyByContentTypeState(pager, contentType, state);
        List<ReplyManageVO> list = pager.getDataList();
        // 获取详情页的 title和链接
        for (ReplyManageVO vo : list) {
            // 读西藏 详情页
            if(vo.getContentType().equals(Content.DETAIL_READ_TIBET_REPLY)){
                String code = vo.getPostCode();
                Content con = contentDao.findByCode(Content.class, code);
                if(con!=null){
                    vo.setTitle(con.getContentTitle());
                    vo.setLinkUrl(con.getUrl());
                }
            }
            // 读西藏文化传播 详情页
            else if(vo.getContentType().equals(Content.DETAIL_READ_TIBET_CULTURE_REPLY)){
                String code = vo.getPostCode();
                Content con = contentDao.findByCode(Content.class, code);
                if(con!=null){
                    vo.setTitle(con.getContentTitle());
                    vo.setLinkUrl(con.getUrl());
                }
            }
            // 景点 详情页
            else if(vo.getContentType().equals(Content.DETAIL_VIEW_REPLY)){
                String code = vo.getPostCode();
                View view = contentDao.findByCode(View.class, code);
                if(view!=null){
                    vo.setTitle(view.getViewName());
                    //vo.setLinkUrl(view.getLinkUrl());
                   // vo.setLinkUrl(View.LINK_URL_PORTAL_VIEW_DETAIL + view.getCode());
                    vo.setLinkUrl("tourism/view/detail/"+ view.getCode()+".html");
                }
            }
            // 商户 详情页
            else if(vo.getContentType().equals(Content.DETAIL_MERCHANT_REPLY)){
                String code = vo.getPostCode();
                Merchant m = contentDao.findByCode(Merchant.class, code);
                if(m!=null){
                    vo.setTitle(m.getMerchantName());
                    vo.setLinkUrl(m.getUrl());
                }
            }
            // 攻略 详情页
            else if(vo.getContentType().equals(Content.DETAIL_STRATEGY_REPLY)){
                String code = vo.getPostCode();
                Content con = contentDao.findByCode(Content.class, code);
                if(con!=null){
                    vo.setTitle(con.getContentTitle());
                    vo.setLinkUrl(con.getUrl());
                }
            }
            // 看西藏视频
            else if(vo.getContentType().equals(Content.DETAIL_VEDIO_REPLY)){
                String code = vo.getPostCode();
                Content con = contentDao.findByCode(Content.class, code);
                if(con!=null){
                    vo.setTitle(con.getContentTitle());
                    vo.setLinkUrl(con.getUrl());
                }
            }
            // 看西藏图说西藏
            else if(vo.getContentType().equals(Content.DETAIL_PICTURE_REPLY)){
                String code = vo.getPostCode();
                MutiImage mutiImage = mutiImageDao.findByCode(code);
                if(mutiImage!=null){
                    vo.setTitle(mutiImage.getName());
                    vo.setLinkUrl(mutiImage.getHyperlink());
                }
            }
            // 团游
            else if(vo.getContentType().equals(Content.DETAIL_TOUR_GROUP_REPLY)){
                String code = vo.getPostCode();
                GroupTravel groupTravel = groupTravelService.getGroupTravelByCode(code);
                if(groupTravel!=null){
                    vo.setTitle(groupTravel.getName());
                    vo.setLinkUrl(groupTravel.getUrl());
                }
            }
            
        }
        return pager;
    }
    @Override
    public int findReplyNumByContentTypeState(String contentType, int state){
        return contentDao.findReplyNumByContentTypeState(contentType, state);
    }

	@Override
	public Integer getStrageCount(Date start, Date end) {
		// TODO Auto-generated method stub
		return  contentDao.getStrageCount(start, end);
	}
	
	@Override
    public int getAllReply(String memberCode) {
        // TODO Auto-generated method stub
        List<Object> param=new ArrayList<Object>();
        param.add(memberCode);
        String sql="SELECT * FROM `content` WHERE avaliable='1' AND "
        	+"(contentType='reply' OR contentType='read_tibet_reply' OR contentType='read_tibet_culture_reply' "
        	+"OR contentType='view_reply' OR contentType='strategy_reply' OR contentType='merchant_reply' OR contentType='tour_group_reply' "
        	+"OR contentType='vedio_reply' OR contentType='picture_reply') AND createuserCode=? AND state='1'";
        return contentDao.findCountBySql(sql, param);//(Content.class, sql, param);
        //return list.size();
    }

    @Override
    public int getMemberTodayScore(String memberCode) {
        return contentDao.getMemberTodayScore(memberCode);
    }
    
    
    /********************************************
     * other method
     ********************************************/
    
    @Override
    public void saveAtlas(String contextPath, Content content){
        //删除图片
        imageService.deleteAtlasByMutiCode(content.getCode());
        //String[] destinationCodes, String[] viewCodes;
        List<StrategyView> destinationCodes = strategyViewDao.findDestinationCodeByContentCode(content.getCode());
        List<StrategyView> viewCodes = strategyViewDao.findViewCodeByContentCode(content.getCode());
        
        List<StrategyView> listDecViewCodes = new ArrayList<StrategyView>();
        
        // 区分官方上传和用户上传
        String type = (content.getIsOfficial()==1)?Image.ATLAS_OFFICAL:Image.ATLAS_USER;
        
        // 分两种情况 1.只有目的地。2.有景点有目的地
        if(viewCodes==null||viewCodes.size()==0){
            // 只有目的地
            if(destinationCodes!=null && destinationCodes.size()>0){
                for (StrategyView sv : destinationCodes) {
                    String desCode = sv.getDestinationCode();
                    listDecViewCodes.add(new StrategyView(0, content.getCode(), null, desCode));
                }
            }
        }else if(viewCodes!=null||viewCodes.size()>0){
            // 有景点有目的地（不保存景点已有的目的地）
            if(destinationCodes!=null && destinationCodes.size()>0){
                for (StrategyView sv : viewCodes) {
                    if(sv!=null){
                        listDecViewCodes.add(new StrategyView(0, content.getCode(), sv.getViewCode(), sv.getDestinationCode()));
                    }
                }
                for (StrategyView svdes : destinationCodes) {
                    boolean bool = true;    //景点中的目的地和选中的目的地对比，景点中没有才保存这个目的地
                    String desCode = "";
                    for (StrategyView sv : viewCodes) {
                        desCode = svdes.getDestinationCode();
                        if(sv.getDestinationCode().equals(desCode)){
                            bool = false;
                            break;
                        }
                    }
                    if(bool){
                        listDecViewCodes.add(new StrategyView(0, content.getCode(), null, desCode));
                    }
                }
            }
        }
        
        // 获取富文本中的图片url
        Pattern pattern = Pattern.compile("<img.*?>");
        Matcher matcher = pattern.matcher(content.getContent());
        Pattern pSrc = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)");
        // Matcher mSrc = pSrc.matcher(input)
        while (matcher.find()) {
            //System.out.println(matcher.group());
            String img = matcher.group();
            Matcher mScr = pSrc.matcher(img);
            if (mScr.find()) {
                String src = mScr.group().replaceAll("src=\"", "").replaceAll("\"", "");
                src = src.replace(contextPath, "");
                for (StrategyView sv : listDecViewCodes) {
                    Image image = new Image();
                    image.setAvaliable(1);
                    image.setCode(Uuid.uuid());
                    image.setName(content.getContentTitle());
                    image.setMutiCode(content.getCode());
                    image.setDestinationCode(sv.getDestinationCode());
                    image.setViewCode(sv.getViewCode());
                    image.setUrl(src);
                    image.setSummary(type);
                    if(type.equals(Image.ATLAS_USER)){
                        image.setCreateUserCode(content.getCreateuserCode());
                    }
                    image.setCreateTime(new Timestamp(System.currentTimeMillis()));
                    imageService.save(image);
                }
            }
        }
        
    }
    
	public static void main(String[] args) {
	    new ContentServiceImpl().hasSqlInject("zhong,'");
    }
    private boolean hasSqlInject(String str){
    	str=str==null?"":str;
    	String reg="(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"  
                + "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";
    	 Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
    	 str=URLDecoder.decode(str);
    	  if (sqlPattern.matcher(str).find()) {  
    	        log.error("参数含有sql注入：" + str);  
    	        return true;  
    	    }
    	return false;
    }

	
	@Override
	public void returnPost(Content post) {
		SysMessage sm = new SysMessage();
		sm.setAvaliable(1);
		sm.setCode(CodeFactory.create("sysMsg"));
		sm.setContent("您有一个帖子未通过审核");
		sm.setContentCode(post.getCode());
		sm.setMsgTo(post.getCreateuserCode());
		sm.setTitle("帖子审核未通过");
		sm.setUrl(post.getUrl());
		sm.setType(Constants.Post_Judge_Back);
		sm.setCreateDate(new Date());
		sm.setContentTitle(post.getContentTitle());
        post.setState(-1);
        this.update(post);
		sysMessageService.save(sm);
	}

	@Override
	public int getMemberStraNum(String memberCode) {
		// TODO Auto-generated method stub
		String sql="SELECT * FROM `content` WHERE avaliable='1' and contentType='strategy' AND createuserCode=?";
		List param=new ArrayList();
		param.add(memberCode);
		return contentDao.findListBySql(Content.class, sql, param).size();
	}

    @Override
    public int getSpecialMaxSortNum() {
        return contentDao.getSpecialMaxSortNum();
    }

    @Override
    public void deleteReplyByPostCodeLogical(String postCode, String contentType) {
        contentDao.deleteReplyByPostCodeLogical(postCode, contentType);
    }
    
    @Override 
    public Pager findOtherContent(Pager pager, Content content, String contentType,
			String orderType){
		if (hasSqlInject(contentType)) {
			return pager;
		}
		if(checkSqlInject(content)){
			return pager;
		}
    	return contentDao.findOtherContent(pager, content, contentType, orderType);
    }

	private boolean checkSqlInject(Content content) {
		List<Field> fields=new ArrayList<Field>();
		getField(fields, Content.class);
		try {
			for (Field field : fields) {
				if (checkStaticParam(field)) {
					continue;
				}
				String paramName = upperHeadChar(field.getName());
				Method fathermeMethod = Content.class.getMethod("get" + paramName);
				
				Object obj = fathermeMethod.invoke(content);//取出属性值
				
				if(hasSqlInject(obj+"")){
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	private static String upperHeadChar(String in) {
		String head = in.substring(0, 1);
		String out = head.toUpperCase() + in.substring(1, in.length());
		return out;
	}
	private static boolean checkStaticParam(Field field) {
		if (Modifier.isStatic(field.getModifiers())) {
			return true;
		}
		return false;
	}
	public static void getField(List<Field> fields,Class  classname){
		if(classname!=Object.class){
			getField(fields, classname.getSuperclass());
		}else{
			return;
		}
		Field[] fs=classname.getDeclaredFields();
		for (int i = 0; i < fs.length; i++) {
			fields.add(fs[i]);
		}
	}
	
	
}
