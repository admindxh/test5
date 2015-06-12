package com.rimi.ctibet.web.service.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.constants.Constants;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.HTMLTagUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.SysMessage;
import com.rimi.ctibet.portal.controller.vo.PostVo;
import com.rimi.ctibet.portal.controller.vo.TssqPortalPostVo;
import com.rimi.ctibet.web.controller.vo.PostPassVo;
import com.rimi.ctibet.web.dao.IPostDao;
import com.rimi.ctibet.web.service.IPostService;
import com.rimi.ctibet.web.service.ISysMessageService;
import com.rimi.ctibet.web.service.IUserFavoriteService;
   
@Service("postService")
@Transactional
public class PostServiceImpl implements IPostService{

	@Resource
	private IPostDao postDao;
	@Resource
	private IUserFavoriteService userFavoriteService;
	@Resource
	private ISysMessageService sysMessageService;
	@Override
	public Pager UncheckedPostList(Pager pager,int state,String type) {
        return  postDao.UncheckedPostList(pager,state,type);
	}
   
	@Override
	public void checkPost(Content post, int state) {
        if(post!=null) 
		    postDao.checkPost(post, state);		
	}

	@Override
	public void deletePost(Content post) {
        if(post!=null){
        	postDao.deletePost(post);
        
        	//删除关联的收藏表
        	userFavoriteService.deleteByCode(post.getCode());
        	
        	SysMessage sm = new SysMessage();
    		sm.setAvaliable(1);
    		sm.setCode(CodeFactory.create("sysMsg"));
    		sm.setContent("您有一个帖子被管理员删除");
    		sm.setContentCode(post.getCode());
    		sm.setMsgTo(post.getCreateuserCode());
    		sm.setTitle("帖子删除提醒");
    		sm.setUrl(post.getUrl());
    		sm.setType(Constants.Post_Delete);
    		sm.setCreateDate(new Date());
    		sm.setContentTitle(post.getContentTitle());
            sysMessageService.save(sm);
        }
	}
	
	@Override
	public void savePost(Content post,String postCode) {
        if(post!=null)
        	postDao.savePost(post,postCode);
	}

	@Override
	public Pager searchPost(int state,String contentType ,String programaCode,String keyword,Pager pager) {
		Pager pr = postDao.searchPost(pager,state, contentType, programaCode, keyword);
		List<Map<String,Object>> result = pr.getDataList();
		if (result!=null&&result.size()>=1) {
			for (Map<String, Object> map : result) {
				if(map.get("content")!=null)
				   map.put("content", HTMLTagUtil.delHTMLTag(map.get("content").toString()));
			}
			pr.setDataList(result);
		}
		return pr;
	}

	@Override
	public void updatePost(Content post) {
		if(post!=null)
			postDao.update(post);
		
	}

	@Override
	public Content getPostByCode(String code) {
		if(StringUtils.isNotBlank(code))
			return postDao.getPostByCode(code);
		return null;
	}
	@Override
	public Pager findAllPost(String memberCode,Pager pager) {
		// TODO Auto-generated method stub
		return postDao.findAllPost(memberCode, pager);
	}

	@Override
	public Pager findWithPagerByMap(String hql, Map<String, Object> Param,
			Pager pager) {
		// TODO Auto-generated method stub
		return postDao.findWithPagerByMap(hql, Param, pager);
	}

	@Override
	public Pager findAllPassPost(Integer state, String programcode,
			String keyword, String order, Pager pager) {
		// TODO Auto-generated method stub
		return postDao.findAllPassPost(state, programcode, keyword, order, pager);
	}
	//获取帖子的详细和回复
	@Override
	public Pager getAllPassPostInProtal(Pager pager,String rule,String plateCode,String isTop) {
		Pager pager2 = postDao.getAllPassPostInProtal(pager,rule,plateCode,isTop);
		List<PostPassVo> ppvs = pager2.getDataList();
		for (PostPassVo map : ppvs) {
		    String replyinfo = (String)map.getReplyinfo();
		    String[] replyInfo;
		    if(StringUtils.isNotBlank(replyinfo)){
			   replyInfo = replyinfo.split("_");
				if(StringUtils.isNotBlank(replyInfo[0]))
				       map.setReplyName(replyInfo[0]);
				if(StringUtils.isNotBlank(replyInfo[1]))
					   map.setReplyTime(replyInfo[1]);
				if(StringUtils.isNotBlank(replyInfo[2]))
					   map.setReplyPic(replyInfo[2]);
				if(StringUtils.isNotBlank(replyInfo[3]))
					map.setReplysex(replyInfo[3]);
		    }
		}
		return pager2;
	}

	@Override
	public Pager getReplysInfoByPostCode(String code,Pager pager) {
	    pager = postDao.getReplysInfoByPostCode(code,pager);
//		List<PostVo> pvos = new ArrayList<PostVo>();
//		
//		List<Map<String,Object>> dataList = pager.getDataList();
//		for (Map<String, Object> map : dataList) {
//			PostVo pvo = new PostVo();
//			
//			String replyCode = "";
//			if(map.get("code")!=null)
//				replyCode = (String)map.get("code");
//			pvo.setReplyCode(replyCode);
//			
//			String content = "";
//			if(map.get("content")!=null)
//				content = (String)map.get("content");
//			pvo.setContent(content);
//		
//			pvo.setLevel("");
//			
//			Integer falseprise = 0;
//			if(map.get("falsepraise")!=null)
//				falseprise = (Integer)map.get("falsepraise");
//			pvo.setPraiseCount((Integer)map.get("falsepraise"));
//			
//			String postusername = "";
//			if(map.get("postusername")!=null)
//				postusername = (String)map.get("postusername");
//			pvo.setReplyerName(postusername);
//			
//			String postuserpic = "";
//			if(map.get("postuserpic")!=null)
//				postuserpic = (String)map.get("postuserpic");
//			pvo.setReplyerPic(postuserpic);
//			
//			String createtime = "";
//			if(map.get("createtime")!=null)
//				createtime = map.get("createtime").toString();
//			pvo.setReplyTime(createtime);
//			
//			pvos.add(pvo);
//		}
//		pager.setDataList(pvos) ;
		
 		return pager;
	}

	@Override
	public Map<String, Object> getPostDetailByCode(String postCode) {
		if(StringUtils.isNotBlank(postCode))
            return postDao.getPostDetailByCode(postCode);	
		return null;
	}
	//===============================================门户首页相关===========================================

	@Override
	public List<Map<String, Object>> getBestPraise() {
		return postDao.getBestPraise();
	}

	@Override
	public List<Map<String,Object>> getDrivAannouncements() {
		return postDao.getDrivAannouncements();
	}

	@Override
	public List<Map<String,Object>> getDriveEquipments() {
		return postDao.getDriveEquipments();
	}

	@Override
	public List<Map<String,Object>> getDriveRecruits() {
		return postDao.getDriveRecruits();
	}

	@Override
	public List<Map<String,Object>> getDriveStorys() {
		return postDao.getDriveStorys();
	}

	@Override
	public List<Map<String, Object>> getMostPraise() {
		return postDao.getMostPraise();
	}

	@Override
	public List<Map<String, Object>> getMostRplys() {
		return postDao.getMostRplys();
	}

	@Override
	public List<Map<String, Object>> getTopPost() {
		List<Map<String,Object>> dataList = postDao.getTopPost();
		//数据库中查询出来的回复信息使用-连接，这里将其分开存入dataList
		for (Map<String, Object> map : dataList) {
		    String replyinfo = (String)map.get("replyinfo");
		    String[] replyInfo;
		    if(StringUtils.isNotBlank(replyinfo)){
			   replyInfo = replyinfo.split("_");
				if(StringUtils.isNotBlank(replyInfo[0]))
				       map.put("replyerName",replyInfo[0]);
				if(StringUtils.isNotBlank(replyInfo[1]))
					   map.put("replyCreatetime",replyInfo[1]);
				if(StringUtils.isNotBlank(replyInfo[2]))
					   map.put("replyerPic",replyInfo[2]);
				if(StringUtils.isNotBlank(replyInfo[4]))
					   map.put("replyUserCode",replyInfo[4]);
		    }
		}
		return dataList;
	}

	@Override
	public List<TssqPortalPostVo> normalPost() {
        List<TssqPortalPostVo> pvos = postDao.normalPost();
        for (TssqPortalPostVo pvo : pvos) {
			List<Map<String,Object>> dataList = pvo.getPosts();
			for (Map<String, Object> map : dataList) {
				 String replyinfo = (String)map.get("replyinfo");
				    String[] replyInfo;
				    if(StringUtils.isNotBlank(replyinfo)){
					   replyInfo = replyinfo.split("_");
						if(StringUtils.isNotBlank(replyInfo[0]))
						       map.put("replyerName",replyInfo[1]);
						if(StringUtils.isNotBlank(replyInfo[1]))
							   map.put("replyCreatetime",replyInfo[0]);
						if(StringUtils.isNotBlank(replyInfo[2]))
							   map.put("replyerPic",replyInfo[2]);
						if(StringUtils.isNotBlank(replyInfo[4]))
							   map.put("replyUserCode",replyInfo[4]);
				    }
			}
		}
		return pvos;
	}

	@Override
	public Pager getMemberPostByState(String memberCode, String state,
			String programaCode, Pager pager) {
		// TODO Auto-generated method stub
		return postDao.getMemberPostByState(memberCode, state, programaCode, pager);
	}
	/**
	 *图说西藏分页
	 */
	@Override
	public Pager getMutiPost(Pager pager, String mutiCode) {
		return postDao.getMutiPost(pager, mutiCode);
	}
	/**
	 * 查找图集的所有评论
	 */
	@Override
	public List<Content> findPostByMutiCode(String mutiCode) {
		return postDao.findPostByMutiCode(mutiCode);
	}

	@Override
	public int getUncheckedPostAndReplyNum() {
		return postDao.getUncheckedPostAndReplyNum();
	}

	@Override
	public List<Map<String, Object>> getTopFive() {
		return postDao.getTopFive();
	}

	@Override
	public List<Map<String, Object>> getTopFiveBuPCount() {
		return postDao.getTopFiveBuPCount();
	}

	@Override
	public String postCountByMemCode(String mCode) {
		return postDao.postCountByMemCode(mCode);
	}

	@Override
	public List<String> praiseCode(String ip) {
		if(StringUtils.isBlank(ip))
			return null;
		return postDao.praiseCode(ip);
	}

    @Override
    public List<Map<String, Object>> getMostReply(int row) {
        return postDao.getMostReply(row);
    }

    @Override
    public int getPostCountByPrograma(String p) {
        return postDao.getPostCountByPrograma(p);
    }

    @Override
    public int getPostcheckNumByPrograma(String p) {
        // TODO Auto-generated method stub
        return postDao.getPostcheckNumByPrograma(p);
    }

    @Override
    public int getPostReplyNumByPrograma(String p) {
        // TODO Auto-generated method stub
        return postDao.getPostReplyNumByPrograma(p);
    }

    @Override
    public Content getPostNewReplyByPrograma(String p) {
        // TODO Auto-generated method stub
        return postDao.getPostNewReplyByPrograma(p);
    }

    @Override
    public List<PostVo> getPostByPrograma(String programCode, int orderType, int row) {
        return postDao.getPostByPrograma(programCode, orderType, row);
    }
}
