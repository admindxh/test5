<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%
String path = request.getContextPath().trim().equals("/")?request.getContextPath():request.getContextPath()+"/";
String port = ":" + request.getServerPort();
if ("80".equals(""+request.getServerPort())) {
	port = "";
}
String basePath = request.getScheme() + "://"+ request.getServerName() + port + path;
/* 	String path = request.getContextPath();
String port  =  ":"+request.getServerPort() ;
if("80".equals(""+request.getServerPort())){
	port = ""  ;
}
String basePath = request.getScheme()+"://"+request.getServerName()+port+path+"/"; */
String basePathIndx = request.getScheme()+"://"+request.getServerName()+port+path+"";
	request.setAttribute("ctx", basePath);
	request.setAttribute("ctxIndex", basePathIndx);
	request.setAttribute("root", "/");
	request.setAttribute("ctxManage", basePath + "/manage/");
	request.setAttribute("ctxPortal", basePath + "/portal/"); //xiangwq 
	request.setAttribute("ctxMRead", basePath + "manage/html/read/");//csl
%>
<link rel="stylesheet" href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctxPortal}/assets/css/common.css">
<script id="seajsnode" src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script>
<script>
    // Set configuration
    seajs.config({
        alias: {
            "jquery": "jquery/jquery/1.11.1/jquery.js",
            "avalon": "avalon/1.3.7/avalon.js",
            "common/css": "${ctxPortal}/assets/css/common.css",
            "footer/css": "${ctxPortal}/assets/css/footer.css"
        }
    });
    seajs.use(['footer/css']);
    seajs.use(['avalon'], function(avalon) {
        avalon.define({
            $id: "view",
            showTag: false
        })
    })
</script>
<script type="text/javascript">
var   ctx= "${ctx}";
    
</script>