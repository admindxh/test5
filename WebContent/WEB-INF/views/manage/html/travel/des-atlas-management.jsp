<%@page import="com.rimi.ctibet.domain.Image"%>
<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>游西藏-目的地管理-目的地信息管理-目的地图片集管理</title>
	<%@include file="/common-html/common.jsp" %>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/home/imgDescr.css"/>
    <%-- <link rel="stylesheet" href="${ctxManage}/resources/css/activity/activity-manage.css"/> --%>
    <script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
    <style>
        .floatfix:after {
            content: "";
            display: table;
            clear: both;
        }
        .lookupUploadCont{
            top: 330px !important;
        }
    </style>
</head>
<body>
<!-- main { -->
<div class="main">
    <form id="" name="">
        <!-- 页面位置-->
        <div class="location">
            <label>您当前位置为:</label>
            <span><a>游西藏</a> -</span>
            <span><a>目的地管理</a> -</span>
            <span><a href="${ctx}web/destinationController/getDestinationPager" target="_self">目的地信息管理</a> -</span>
            <span><a href="#" target="_self">目的地图片集管理</a></span>
        </div>

        <!-- 模块管理 { -->
        <div class="muduleManage filament_solid_ddd">
            <!-- 查看按钮 -->
            <div class="searchManage">
                <!-- <button id="pickId" type="button" class="lookup btn-sure">上传</button> -->
                <div id="pickId" class="disp-ib">
                	<button type="button" class="btn-sure">上传</button>
                </div>
                <button id="deleteImg" type="button" class="btn-sure">删除</button>
                <!-- <div id="pickId" class="btn-sure">上传</div>
                <div id="deleteImg" class="btn-sure">删除</div> -->
            </div>

            <!-- 选择标签 { -->
            <div class="posidTab">
                <label class="checked">官方</label>
                <label>用户</label>
            </div>
            <!-- } 选择标签 -->

            <!-- 图片集管理设置 { -->
            <div class="posidSet filament_solid_ccc">
                <!-- 官方 { -->
                <div class="posidSet-posid show floatfix" ms-important="offUploadView">
                    <div class="floatfix mb100">
                        <div class="float_l mt20 ml35 txt-ac" ms-repeat="data">
                            <img class="lookup" ms-src="{{el.url}}" alt=""
                                 style="width: 238px;height: 210px;"/><br/>
                            <input class="mt10" type="checkbox" name="imgSelect" ms-attr-value="el.url_o"/>
                        </div>
                    </div>
                    <!-- 分页 -->
                    <div id="offUploadPaging" class="paging" style="float:right"></div>
                    <div class="paging-info float_r offical" style="line-height:30px;top:16px;right:15px;">
                    	<span class="disp-ib current"></span>
                    	<span class="disp-ib totalpage"></span>
                    	<span class="disp-ib totalcount"></span>
                    </div>
                    <!-- } 分页 -->
                </div>
                <!-- } 官方 -->

                <!-- 用户上传 { -->
                <div class="posidSet-mores">
                    <div class="posidSet-posid show floatfix" ms-important="userUploadView">
                        <div class="floatfix mb100">
                            <div class="float_l mt20 ml35 txt-ac" ms-repeat="data">
	                            <img class="lookup" ms-src="{{el.url}}" alt=""
	                                 style="width: 238px;height: 210px;"/><br/>
	                            <input class="mt10" type="checkbox" name="imgSelect" ms-attr-value="el.url_o"/>
	                        </div>
                        </div>
                        <!-- 分页 -->
                        <div id="userUploadPaging" class="paging" style="float:right"></div>
	                    <div class="paging-info float_r user" style="line-height:30px;top:16px;right:15px;">
	                    	<span class="disp-ib current"></span>
	                    	<span class="disp-ib totalpage"></span>
	                    	<span class="disp-ib totalcount"></span>
	                    </div>
                        <!-- } 分页 -->
                    </div>
                </div>
                <!-- } 用户上传 -->

                <!-- 查看上传内容 { -->
                <div class="lookupUploadCont popupLayer">
                    <!-- 关闭 -->
                    <div class="close-tras_black"></div>

                    <!-- 图片 -->
                    <div class="imgBox">
                        <img src="${ctxManage}/resources/img/other/example_0.jpg" title="缩略图名称" alt="上传的图片">
                    </div>

                    <!-- 操作区 -->
                    <div class="operInfoBar">

                        <!-- 作品名称 -->
                        <div class="activName">我的西藏之旅</div>

                        <!-- 用户名 -->
                        <!--<div class="activAccount">
                            <label>by:</label>
                            <span>一只流浪的猪</span>
                        </div>

                        &lt;!&ndash; 操作按钮 &ndash;&gt;
                        <div class="activOperBtn">
                            <button type="button" class="btn-base">删除</button>
                            <button type="button" class="btn-base">审核通过</button>
                        </div>-->
                    </div>
                </div>
                <!-- } 查看上传内容 -->

            </div>
            <!-- } 图片集管理设置 -->
        </div>
        <!-- } 模块管理 -->
    </form>
</div>
<!-- } main -->

<!-- JS引用部分 -->
<script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>

<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js"></script>
<script src="${ctxManage}/resources/js/dataValidation.js"></script>

<!-- jsp -->
<%-- <script src="${ctxManage}/webuploader/webuploader.js" type="text/javascript"></script> --%>
<script src="${ctx}manage/webuploaderbase/webuploader.min.js" type="text/javascript"></script>
<script src="${ctxManage}/webuploader/checkflash.js" type="text/javascript"></script>
<script src="${ctxManage}resources/js/avalon.js" type="text/javascript"></script>
<script src="${ctxManage}resources/js/bootstrap-paginator.js" type="text/javascript"></script>
<!-- 上传组件 -->
<script type="text/javascript">
	//var paths = new Array();
	var isUp = true;
  	function addEvent(up){
  		up.on( 'uploadSuccess', function(file, response) {
			//paths.push(response.filePath);
	    });
  		up.on( 'uploadFinished', function(file, response) {
  			if(isUp){
				//$("#hiddenId").val(paths.join(","));
				getoffUploadList(1);
				msgBox("上传成功！", "pass", 1200);
  			}
			//paths = new Array();
			up.reset();
			isUp = true;
	    });
  		up.on( 'uploadError', function(file, reason) {
  			msgBox("上传格式错误", "info", 1200);
  			this.reset();
  		});
  		up.on( 'error', function(type) {
			//alert("错误信息:"+type);
			isUp = false;
  			this.reset();
			if(type=="Q_EXCEED_SIZE_LIMIT" || type=="F_EXCEED_SIZE"){
				msgBox("文件超过大小", "info", 1200);
			}else if(type=="Q_TYPE_DENIED"){
				msgBox("上传格式错误", "info", 1200);
  			}else if(type=="Q_EXCEED_NUM_LIMIT"){
				msgBox("最多上传10张图片，请重新选择文件。", "info", 1200);
  			}else {
  				msgBox("上传错误，请重试。", "info", 1200);
  			}
	    });
  	}
  	var fileSingleSizeLimit = 10 * 1024 * 1024;
  	var compress = false;
	/* var compress = {
	    width: 1000,
	    height: 200,
	    allowMagnify: true,
	    crop: true
	}; */
	function cteateUploder(){
  		var contextPath = "${ctx}";
  		var options_={
			swf :  contextPath+'/manage/webuploader/Uploader.swf',
			server : contextPath+'/web/destinationController/uploadImages',
			runtimeOrder : "flash",
			accept : {extensions : 'jpg,jpeg,bmp,png'},
			formData:{
				destinationCode: '${destinationCode}'
			},
			pick : {
				id:'#pickId'
			},
			fileVal : 'file',
			auto : true,
			resize: false,
			//fileNumLimit: 10,
			fileSingleSizeLimit: fileSingleSizeLimit,
			compress: compress
		};
		addEvent(new WebUploader.Uploader(options_));
	}
	cteateUploder();
</script>
<script type="text/javascript">

	function checkHttp(response){
		for(var i= 0, length = response.dataList.length; i<length; i++){
	  		var _data = response.dataList[i]
	  		_data.url_o = _data.url
	  		if(_data.url.indexOf('http') != 0){
	  			_data.url = '${ctx}' + _data.url
	  		}
	  	}
	}
	//初始化分页信息
	function initPageInfo(name, pager){
		var $pageInfo = $(name);
		$pageInfo.find(".current").text("当前第 "+pager.currentPage+" 页");
		$pageInfo.find(".totalpage").text("共 "+pager.totalPage+" 页");
		$pageInfo.find(".totalcount").text("共 "+pager.totalCount+" 条");
	}
	
	var officalCurrentpage = 1;
	var userCurrentpage = 1;
	
	function servicesPage(pageId, currentPage, totalPage, call_){
		var options = {
	     	currentPage: currentPage || 1,
	     	totalPages: totalPage || 1,
	     	itemTexts: function (type, page, current) {
                switch (type) {
                    case "first":
                        return "首页";
                    case "prev":
                        return "上一页";
                    case "next":
                        return "下一页";
                    case "last":
                        return "末页";
                    case "page":
                        return page;
                }
            },
	     	//removeClass:'paging-white',
	     	onPageChanged: function(e,oldPage,newPage){
	     		call_(newPage);
	    	}
		}
		//分页按钮
		$('#'+pageId).bootstrapPaginator(options);
	}
	//官方
	var offUploadVM = avalon.define({
		$id:'offUploadView',
		data:[],
		$cacheData:{}
	});
	function getoffUploadList(currentPage){
		var thisCall = getoffUploadList;
		$.post('${ctx}web/destinationController/loadDestinationAtlas', {type: '<%=Image.ATLAS_OFFICAL%>', destinationCode: '${destinationCode}', currentPage: currentPage, pageSize: 20}, function(response){
		  	servicesPage("offUploadPaging", response.currentPage, response.totalPage, thisCall);
		  	checkHttp(response);
		  	offUploadVM.data = response.dataList;
		  	avalon.scan();
		  	initPageInfo(".offical", response);
		  	officalCurrentpage = response.currentPage;
		}, 'json');
	}
	//用户
	var userUploadVM = avalon.define({
		$id:'userUploadView',
		data:[],
		$cacheData:{}
	});
	function getuserUploadList(currentPage){
		var thisCall = getuserUploadList;
		$.post('${ctx}web/destinationController/loadDestinationAtlas', {type: '<%=Image.ATLAS_USER%>', destinationCode: '${destinationCode}', currentPage: currentPage, pageSize: 20}, function(response){
		  	servicesPage("userUploadPaging", response.currentPage, response.totalPage, thisCall);
		  	checkHttp(response);
		  	userUploadVM.data = response.dataList;
		  	avalon.scan();
		  	initPageInfo(".user", response);
			userCurrentpage = response.currentPage;
		}, 'json');
	}
	
	function loadData(){
		getoffUploadList(1);
		getuserUploadList(1);
	}
	window.onload=loadData;
	
</script>


<script type="text/javascript">
    /* 标签页切换 */
    $(".posidTab label").click(function () {
        var $this = $(this),
            thisIndex = $this.index();
        $this.addClass("checked").siblings().removeClass("checked");
        $(".posidSet > div").eq(thisIndex).addClass("show").siblings("div[class^='posidSet-']").removeClass("show");
    });

    /* 删除图片 */
    $("#deleteImg").click(function(){
        var radioCheck = $(".posidSet input[type=checkbox]:checked");
        if(radioCheck.length == 0){
            msgBox("请选择要删除的图片","info",1000);
        }else{
        	popupLayer(
   				'',
   				'<div style="width: 320px; text-align:center; margin: 0 auto;">您确定要删除该项？</div>',
   				'<button type="button" class="btn-sure sure mr15">确定</button>' +
   				'<button type="button" class="btn-sure cancel ml15">取消</button>'
   			);
   			$(document).one('click', '.sure', function(){
   				//$.post('${ctx}web/destinationController/deleteImage', {codes: radioCheck.val()}, function(response){
   					
   				var params = new Array();
   				var i=0;
   				radioCheck.each(function(){
   					params[i]=$(this).val();
   					i=i+1;
   				})
   				
   				$.ajax({
   					traditional: true,
                    type: 'POST',
                    url: '${ctx}web/destinationController/deleteImage' ,
                    data: {urls: params} ,
                    success: function(response){
       	    			if(response=="success"){
       	    				msgBox("删除成功！", "pass", 1200);
       	    				getoffUploadList(1);
       	    				getuserUploadList(1);
       	    			}else{
       	    				msgBox("删除失败！", "erro", 1200);
       	    			}
       	    		} 
           });
//    				$.post('${ctx}web/destinationController/deleteImage',{urls: params}, function(response){
//    	    			if(response=="success"){
//    	    				msgBox("删除成功！", "pass", 1200);
//    	    				getoffUploadList(1);
//    	    				getuserUploadList(1);
//    	    			}else{
//    	    				msgBox("删除失败！", "erro", 1200);
//    	    			}
//    	    		});
   				closePopup();
   			});
        }
    });
	
	/* 查看图片 */
	$(document).on("click", ".lookup", function() {
		var thisPath = $(this).attr("src");
		popupImg('<img src=' + thisPath + '>');
	});
</script>

<!-- 利用空闲时间预加载指定页面 -->
<link rel="prefetch">
<!-- IE10+ -->
<link rel="next">
<!-- Firefox -->
<link rel="prerender">
<!-- Chrome -->
</body>
</html>