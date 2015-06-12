<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jstl/fmt_rt" %>   
<%
   	String path = request.getContextPath();
String port  =  ":"+request.getServerPort() ;
if("80".equals(""+request.getServerPort())){
	port = ""  ;
	
}
String basePath = request.getScheme()+"://"+request.getServerName()+port+path+"/";
   	request.setAttribute("ctx", basePath);
   	request.setAttribute("ctxManage", basePath + "/manage/");
   	request.setAttribute("ctxPortal", basePath + "/portal/"); //xiangwq 
   	request.setAttribute("ctxMRead", basePath + "manage/html/read/");//csl
   	request.setAttribute("ctxApp", basePath + "portal/app/"); //
   	request.setAttribute("root", "/");
   %>
<%--<link rel="stylesheet" href="${ctxManage}/resources/css/bootstrap.min.css" />--%>
<link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
<link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css" />
<link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
<link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
<link rel="stylesheet" href="${ctxManage}/resources/css/home/banner.css" />
<link rel="stylesheet" href="${ctxManage}/webuploader/webuploader.css" />
<link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
<link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
<link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css" />
<!-- JS引用部分 -->
<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base-tabel.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/common-js/common.js"></script>
<script src="${ctxManage}/webuploader/webuploader.js" type="text/javascript"></script>
<script src="${ctxManage}/webuploader/upload.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/common-js/common.js"></script> 
	

<script type="text/javascript">
var contextPath='<%=path%>';

/**
 * js 中获取会员用户
 * @return
 */
function getFrontUser(){
	var  user  = "${logUser}";
	return user;
}
</script>