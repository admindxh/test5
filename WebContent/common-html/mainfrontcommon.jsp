<%@ page language="java"  contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jstl/fmt_rt" %>   
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    String path = request.getContextPath().trim().equals("/")?request.getContextPath():request.getContextPath()+"/";
    String port  =  ":"+request.getServerPort() ;
    if("80".equals( ""+request.getServerPort())){
        port = ""  ;
    }
    String basePath = request.getScheme()+"://"+request.getServerName()+port+path;
    String basePathIndex = request.getScheme()+"://"+request.getServerName()+port+request.getContextPath();
    request.setAttribute("ctx",basePath);
    request.setAttribute("ctxIndex",basePathIndex);
    request.setAttribute("ctxManage",basePath+"/manage/");
    request.setAttribute("ctxPortal",basePath+"portal/"); 	//xiangwq
    String url  = request.getServletPath();
    request.setAttribute("ctxMRead", basePath+"manage/html/read/");//csl
    request.setAttribute("ctxApp",basePath+"portal/app/"); //
    request.setAttribute("pageurl",basePath+url);
    request.setAttribute("root","/");
%>
<script src="${ctxPortal}assets/js/Alert.js"></script>
<script>
	var contentPath = "${ctx}";
	var ctxPortal = "${ctxPortal}";
	var  loguser = "${logUser}";
	/**
	 *  js 中获取会员用户
	 * @return
	 */
	function getFrontUser(){
		var  user  = "${logUser}";
		return user;
	}
	/**
	 * 判断当前会员用户是否登录
	 * @return
	 */
	function checkPortalUserLongin(){
		var  user  = "${logUser}";
		if (user) {
			return true;
		}else{
			return false;
		}
	}
</script>
