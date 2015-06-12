package com.rimi.ctibet.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rimi.ctibet.domain.Access;
import com.rimi.ctibet.web.service.IAccessService;

@Controller
@RequestMapping("web/access")
public class AccessController {
     @Resource
     private IAccessService accessService; 
     
     //获取全部权限
     @RequestMapping("getAllAccess")
     public @ResponseBody Map getAllAccess(HttpServletRequest request,HttpServletResponse response){
    	 Map<String , Object> accessMap = new HashMap<String,Object>();
    	 Map<String,List<Access>> accessList = accessService.getAllAccess();
    	 accessMap.put(Access.ACCESSINFO,accessList);
    	 return accessMap;
     }
     
     //获取顶层权限
     @RequestMapping("getTopAccess")
     public @ResponseBody Map getTopAccess(HttpServletRequest request,HttpServletResponse response){
    	 Map<String , Object> accessMap = new HashMap<String,Object>();
    	 List<Access> accessList = accessService.getTopAccess();
    	 accessMap.put(Access.ACCESSINFO,accessList);
    	 return accessMap;
     }
     
     //获取二级权限
     @RequestMapping("getSendAccess")
     public @ResponseBody Map getSendAccess(HttpServletRequest request,HttpServletResponse response){
    	 String pCode = request.getParameter("pCode");
    	 Map<String , Object> accessMap = new HashMap<String,Object>();
    	 List<Access> accessList = accessService.getSendAccess(pCode);
    	 accessMap.put(Access.ACCESSINFO,accessList);
    	 return accessMap;
     }
	
}
