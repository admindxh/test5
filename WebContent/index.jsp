<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/common-html/common.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta name="keywords" content="西藏旅游，西藏自驾游，西藏自助游，西藏骑行，西藏文化"/>
<meta name="description" content="天上西藏网为您提供全面靠谱的西藏旅游资讯，旅游攻略，西藏自助游，西藏自驾游，西藏骑行，全面权威的西藏文化介绍。打造权威的西藏文化旅游网。"/>
<title>【天上西藏】西藏旅游-感受西藏魅力，体验西藏生活-天上西藏官网</title>
<script type="text/javascript" src="manage/resources/plugin/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="manage/resources/plugin/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctx}/common-js/common.js"></script>
<script type="text/javascript">

	function fileUpload() {
		var url = "manage/html/home/uploadImage";
		// 开始上传文件时显示一个图片
		$("#wait_loading").ajaxStart(function() {
			$(this).show();
			// 文件上传完成将图片隐藏起来
		}).ajaxComplete(function() {
			$(this).hide();
		});
		$.ajaxFileUpload({
			url : url,
			type : 'post',
			secureuri : false, //一般设置为false
			fileElementId : "p_pic", // 上传文件的id、name属性名
			dataType : 'text', //返回值类型，一般设置为json、application/json
			success : function(data, status) {
			//上传完成后回调
			//console.log("返回");
			},
			error : function(data, status, e) {
			//上传错误回调该函数
			}
		});
	}
</script>
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
-->
</head>	

<body>
			<script type="text/javascript">
				//跳转请求到首页
			    skipUrl('${ctx}home');
			</script>
</body>
</html>
