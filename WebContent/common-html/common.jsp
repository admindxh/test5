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
		String basePathIndx = request.getScheme()+"://"+request.getServerName()+port+path+"";
	    request.setAttribute("ctx",basePath);
	    request.setAttribute("ctxIndex",basePathIndx);
	    request.setAttribute("ctxManage",basePath+"/manage/");
	    request.setAttribute("ctxPortal",basePath+"/portal/"); //xiangwq 
	    request.setAttribute("ctxMRead", basePath+"manage/html/read/");//csl
	    request.setAttribute("ctxApp",basePath+"portal/app/"); //
	    request.setAttribute("root","/");
%>
<script type="text/javascript">
	var contextPath='<%=basePath%>';
	window.onload = function(){
			  var i  = $("input").eq(0);
			  $(i).focus();

		}
</script>
