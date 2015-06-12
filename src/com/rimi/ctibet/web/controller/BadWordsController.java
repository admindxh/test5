package com.rimi.ctibet.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rimi.ctibet.common.util.KeyWordFilter;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.Uuid;
import com.rimi.ctibet.domain.BadWords;
import com.rimi.ctibet.web.service.BadWordsService;

@Controller
@RequestMapping("/web/badwords/")
public class BadWordsController {
    @Resource
    private BadWordsService badWordsService;
    
    /**
     * 查询列表
     * @param req
     * @param currentPage
     * @return
     */
    @RequestMapping("list")
    public String list(HttpServletRequest req,@RequestParam(defaultValue="1",required=false)int currentPage){
    	Pager pager=new Pager();
    	  pager.setCurrentPage(currentPage);
    	  pager.setPageSize(20);
    	String sql="select * from badwords order by createDate desc";
    	badWordsService.findByPager(sql, null, pager);
    	req.setAttribute("pager", pager); 
    	req.setAttribute("currentPage",currentPage);
    	return "manage/html/settings/sensitive";
    }
    
    
    /**
     * 添加敏感词
     * @param req
     * @param badword
     * @return
     */
    @RequestMapping("save")
    public String save(HttpServletRequest req,String badword){
    	if(StringUtils.isBlank(badword)){
    		req.setAttribute("error", 1);
    		return list(req,1);
    	}
    	List<BadWords> list=badWordsService.findbyPro("badword", badword.trim());
    	if(list.size()>0){
    		req.setAttribute("error", 2);
    		return list(req,1);
    	}
    	BadWords bw=new BadWords();
    	 bw.setCode(Uuid.uuid());
    	 bw.setCreateDate(new Date());
    	 bw.setBadword(badword.trim());
    	 bw.setAvaliable(1);
    	try {
    		badWordsService.save(bw);
    		List<String> key=new ArrayList();
    		key.add(badword);
    		KeyWordFilter.addKeywords(key);
    		req.setAttribute("saveflag", 1);
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
			req.setAttribute("saveflag", 0);
		}
    	return list(req,1);
    }
    
    
    /**
     * 删除
     * @param req
     * @param code
     * @return
     */
    @RequestMapping("deleteSingle")
    public String deleteSingle(HttpServletRequest req,String code){
    	if(StringUtils.isBlank(code)){
    		req.setAttribute("noCode",1 );
    		return list(req,1);
    	}
    	try {
    		badWordsService.deleteByCode(code);
    		badWordsService.initBadWords();
    		req.setAttribute("delflag", 1);
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
			req.setAttribute("delflag", 0); 
		}
    	return list(req,1);
    }
    
    /**
     * 批量删除
     * @param req
     * @param codes
     * @return
     */
    @RequestMapping("delete")
    public String delete(HttpServletRequest req,String[] codes){
    	if(codes.length==0){
    		req.setAttribute("noCode",1 );
          return list(req,1);
    	}
    	for(int i=0;i<codes.length;i++){
    		try {
    			badWordsService.deleteByCode(codes[i]);
    			req.setAttribute("delflag", 1);
    		} catch (Exception e) {
				e.printStackTrace();// TODO: handle exception
				req.setAttribute("delflag", 0);
			}
    	}
    	return list(req,1);
    }
}
