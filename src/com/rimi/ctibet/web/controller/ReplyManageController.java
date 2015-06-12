package com.rimi.ctibet.web.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rimi.ctibet.common.constants.Constants;
import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Praise;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.domain.Reply;
import com.rimi.ctibet.domain.ScoreManager;
import com.rimi.ctibet.domain.SysMessage;
import com.rimi.ctibet.web.controller.vo.Culture;
import com.rimi.ctibet.web.controller.vo.ReplyManageVO;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IPraiseService;
import com.rimi.ctibet.web.service.IProgramaService;
import com.rimi.ctibet.web.service.IReplyService;
import com.rimi.ctibet.web.service.IScoreManagerService;
import com.rimi.ctibet.web.service.ISysMessageService;

/**
 * 后台评论管理
 */
@Controller
@RequestMapping("web/replyManageController")
public class ReplyManageController extends BaseController {
    
    @Resource IContentService contentService;
    @Resource IProgramaService programaService;
    @Resource IScoreManagerService scoreManagerService;
    @Resource
	private ISysMessageService sysMessageService;
    @Resource
    private IReplyService replyService;
    @Resource
    private IPraiseService praiseService;
    /**
     * 跳转到 评论回复设置
     */
    @RequestMapping("forReplyManageSettingList")
    public String forReplyManageSettingList(Model model) {
        List<Programa> listPrograma = programaService.getReplyManagePrograma();
        model.addAttribute("listPrograma", listPrograma);
        return "manage/html/settings/comment-set";
    }
    /**
     * 保存 评论回复设置
     */
    @RequestMapping("updateReplyManageSetting")
    public String updateReplyManageSetting(ReplyManageVO replyManageVO){
        //使用remark字段存是否开启评论
        programaService.updateReplyManageSetting(replyManageVO.getListPrograma());
        return redirect("forReplyManageSettingList");
    }
    
    /**
     * 跳转到 待审核列表
     */
    @RequestMapping("forReviewReplyManageList")
    public String forReviewReplyManage(Model model, String replyType) {
        List<Programa> listPrograma = programaService.getReplyManagePrograma();
        model.addAttribute("listPrograma", listPrograma);
        model.addAttribute("state", Content.REVIEW_STATE_WAIT);
        model.addAttribute("replyType", replyType);
        return "manage/html/settings/comment-nopass";
    }
    /**
     * 跳转到 已审核列表
     */
    @RequestMapping("forAlreadyReviewReplyManage")
    public String forAlreadyReviewReplyManage(Model model) {
        List<Programa> listPrograma = programaService.getReplyManagePrograma();
        model.addAttribute("listPrograma", listPrograma);
        model.addAttribute("state", Content.REVIEW_STATE_PASS);
        return "manage/html/settings/comment-pass";
    }
    /**
     * 根据 审核状态 评论类型 获取评论回复列表
     */
    @RequestMapping(value="loadReplyManageList", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String loadReplyManageList(Pager pager, String contentType, int state) {
        pager = contentService.searchReplyByContentTypeState(pager, contentType, state);
        return obj2json(pager);
    }
    
    /**
     * 通过审核
     */
    @RequestMapping(value="reviewReply", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String reviewReply(String contentCode) {
        try {
            Content content = contentService.findByCode(contentCode);
            int score= scoreManagerService.updateMemberScoreByMemberCode(content.getCreateuserCode(), ScoreManager.type_hfandpl);
            //通过审核
            contentService.updateState(contentCode, Content.REVIEW_STATE_PASS);
            //发送消息
            if(content!=null){
            	SysMessage sm = new SysMessage();
        		sm.setAvaliable(1);
        		sm.setCode(CodeFactory.create("sysMsg"));
        		if(content.getContent()!=null)
        		sm.setContent("您有一个回复被审核通过");
        		if(content.getCode()!=null)
        		sm.setContentCode(content.getCode());
        		if(content.getCreateuserCode()!=null)
        		sm.setMsgTo(content.getCreateuserCode());
        		sm.setTitle("审核通过提醒");
        		if(content.getUrl()!=null)
        		sm.setUrl(content.getUrl());
        		sm.setType(Constants.Reply_Judge_Ok);
        		sm.setCreateDate(new Date());
        		if(content.getContentTitle()!=null)
        		sm.setContentTitle(content.getContentTitle());
                sm.setScore(score);
                sysMessageService.save(sm);
        	}
            updateCultureCommentNum(contentCode, content);
            //商户评论数+1
            if(content!=null && content.getContentType().equals(Content.DETAIL_MERCHANT_REPLY)){
                //
            	Reply r = replyService.findByCodeWithoutVali(content.getCode());
            	if(r!=null){
            	   Praise p = praiseService.getPraiseContentCode(r.getContentCode());
            	   if(p!=null){
	            	   p.setReplyNum(p.getReplyNum()+1);
	            	   p.setFalseReplyNum(p.getFalseReplyNum()+1);
	            	   praiseService.update(p);
            	   }
            	}
            }
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
	private void updateCultureCommentNum(String contentCode, Content content) {
		if(content!=null && (isCulture(content.getContentType()))){
			Map map = contentService.findOthers(contentCode);
			String cm = (String) map.get(Culture.FEILD_COMMENT);
			String rlcm = (String) map.get(Culture.FEILD_RL_COMMENT);
			try {
				cm = checkNull2zero(cm);
				rlcm = checkNull2zero(rlcm);
				int x = Integer.parseInt(cm) + 1;
				int y = Integer.parseInt(rlcm) + 1;
				map.clear();
				map.put(Culture.FEILD_COMMENT, "" + x);
				map.put(Culture.FEILD_RL_COMMENT, "" + y);
				contentService.updateOthers(contentCode, map);
			} catch (Exception e) {
				e.printStackTrace();
			}
			contentService.updateOthers(contentCode,map );
		}
	}
	private String checkNull2zero(String pr) {
		if (!StringUtil.isNotNull(pr) || pr.equals("null")) {
			pr = "0";
		}
		return pr;
	}
	private boolean isCulture(String contentType){
		try {
			int x=Integer.parseInt(contentType);
			if(x<3000)
			{
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
    /**
     * 删除
     */
    @RequestMapping(value="deleteReply", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String deleteReply(String[] contentCodes) {
        try {
            contentService.deleteByCodes(contentCodes);
            for(int i=0;i<contentCodes.length;i++){
            	//System.out.println(contentCodes[i]);
            	Content con =contentService.findByCodeWithoutVali(contentCodes[i]);
            	if(con!=null){
	            	SysMessage sm = new SysMessage();
	        		sm.setAvaliable(1);
	        		sm.setCode(CodeFactory.create("sysMsg"));
	        		if(con.getContent()!=null)
	        		sm.setContent("您有一个回复被管理员删除");
	        		if(con.getCode()!=null)
	        		sm.setContentCode(con.getCode());
	        		if(con.getCreateuserCode()!=null)
	        		sm.setMsgTo(con.getCreateuserCode());
	        		sm.setTitle("回复删除提醒");
	        		if(con.getUrl()!=null)
	        		sm.setUrl(con.getUrl());
	        		sm.setType(Constants.Reply_Delete);
	        		sm.setCreateDate(new Date());
	        		if(con.getContentTitle()!=null)
	        		sm.setContentTitle(con.getContentTitle());
	               
	                sysMessageService.save(sm);
            	}
            }
            
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    
    /**
     * 通过回复类型判断是否打开评论
     */
    @RequestMapping(value="isOpen", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String isOpen(String type){
        return programaService.isOpenReply(type)+"";
    }
    
}
