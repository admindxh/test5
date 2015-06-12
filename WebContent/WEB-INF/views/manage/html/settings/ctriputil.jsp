<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>系统设置-缓存管理</title>
    <%@ include file="/common-html/common.jsp" %>
    <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css"/>
    <script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
	<script type="text/javascript">
	function  syncCtrip(){
		//禁用按钮
		$("#ctrip").attr("disabled","disabled");
		 dataLoading("body");
		$.ajax({
				type:"post",
				url:'${ctx}ctrip/test',
				success:function(data){
					 closeLoading("body");
					 	if(data){
								msgBox("携程酒店数据同步成功","pass");
						}else{
							 msgBox("携程酒店数据同步失败,请重新同步","erro");
						}
					 	//启用按钮
						 $("#ctrip").removeAttr("disabled");
			    }
			});
	}
	
	</script>
	    
</head>
<body>
<!-- main -->
<div class="main">
    <!-- 页面位置-->
    <div class="location">
        <label>您当前位置为:</label>
        <span><a>系统设置</a> -</span>
        <span><a>携程酒店同步</a></span>
    </div>
    <button type="button" id="ctrip" class="btn-base mt15" onclick="syncCtrip()">携程酒店同步</button>
</div>

<!-- JS引用部分 -->
<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script>

</script>
</body>
</html>