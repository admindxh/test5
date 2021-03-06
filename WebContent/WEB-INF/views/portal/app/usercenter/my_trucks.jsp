<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
	String port = ":" + request.getServerPort();
	if ("80".equals(""+request.getServerPort())) {
		port = "";

	}
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + port + path + "/";
%>
<%@ taglib   prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="author" content="quansenx">
    <meta name="keywords" content="西藏旅游，西藏自驾游，西藏自助游，西藏骑行，西藏文化 " />
    <meta name="description" content="天上西藏网为您提供全面靠谱的西藏旅游资讯，旅游攻略，西藏自助游，西藏自驾游，西藏骑行，全面权威的西藏文化介绍。打造权威的西藏文化旅游网。" />
    <title>【天上西藏】西藏旅游-感受西藏魅力，体验西藏生活-天上西藏官网</title>
    <jsp:include page="/common-html/frontcommon.jsp"></jsp:include>
    <link rel="stylesheet" href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctxPortal}/assets/css/my-center/my_tracks.css"/>
    <!--[if lt IE 9]>
    <script src="${ctxPortal}/modules/fix/html5shiv.min.js"></script>
    <script src="${ctxPortal}/modules/fix/respond.min.js"></script>
    <![endif]-->
    <script id="seajsnode" src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script>
    <script type="text/javascript">
     // Set configuration
    seajs.config({
        alias: {
            "jquery": "jquery/jquery/1.11.1/jquery.js",
            "avalon": "avalon/1.3.7/avalon.js",
            "map": "${ctxPortal}/assets/js/map.js",
            "header/css": "${ctxPortal}/assets/css/common.css",
            "footer/css": "${ctxPortal}/assets/css/footer.css",
            "paginator":"${ctxPortal }/modules/paginator/0.5/bootstrap-paginator.js",
          }
    });
    </script>
</head>

<body>
<!-- header -->
<jsp:include page="${root}/portal/headerFooterController/header"></jsp:include>
<div class="container main">
    <!-- banner -->
    <div class="banner clearfix">
         <div class="box-right">
             <div class="map-wrap">
                 <div id="map" style="height: 100%;width: 100%;"></div>
                 <div class="go-wrap">
                     <span class="been">去过：${cntGone }</span>
                     <span>想去：${cntWanna }</span>
                 </div>
             </div>
         </div>
        <div class="site">
            <div class="clearfix">
                <div class="been">
                    <div class="title">我去过的目的地</div>
                    <div class="colour-bar"></div>
                    <div class="des-display clearfix">
                    	<c:forEach items="${destGone}" var="g">
                        <div>${g.destinationName }</div>
                    	</c:forEach> 
                    </div>
                </div>
                <div class="been want-go">
                    <div class="title">我想去的目的地</div>
                    <div class="colour-bar"></div>
                    <!-- <a href="javascript:void(0);" onclick="javascript:location.href='<%=basePath %>portal/userView/getViewByType?type=gone&proCode=${g.code }'"> -->
                    <!-- <a href="javascript:void(0);" onclick="javascript:location.href='<%=basePath %>portal/userView/getViewByType?type=wanna&proCode=${g.code }'"> -->
                    <div class="des-display clearfix">
                        <c:forEach items="${destWant}" var="g">
                        <div>${g.destinationName }</div>
                    	</c:forEach> 
                    </div>
                </div>
            </div>


            <div class="place-list">
              <a href="javascript:void(0);" onclick="javascript:document.getElementById('hago').click();">
                <div class="been-num">
                    <img src="${ctxPortal}/assets/icon/been_white.png"/>
                    <span class="name-str">去过的景点</span>
                    <span class="amount">${cntGone }个</span>
                </div>
              </a>
              <a href="javascript:void(0);" onclick="javascript:document.getElementById('wanto').click();">
                <div class="been-num want-to">
                    <img src="${ctxPortal}/assets/icon/wantto-go_white.png"/>
                    <span class="name-str">想去的景点</span>
                    <span class="amount">${cntWanna}个</span>
                </div>
              </a>
              <a href="javascript:void(0);" onclick="javascript:location.href='<%=basePath %>portal/userStrategy/getMyStrat'">
                <div class="been-num share-info">
                    <img src="${ctxPortal}/assets/icon/share_scen.png"/>
                    <span class="name-str">分享的游记&攻略</span>
                    <span class="amount">${cntStra }篇</span>
                </div>
              </a>
            </div>
        </div>
    </div><!-- banner End -->
	<input type="hidden" value="${memberCode }" id="memberCode"/>
	<div class="gogoing clearfix">
	    <ul class="listing clearfix">
	        <li id="hago" data-type="gone" class="down">去过的景点</li>
	        <li id="wanto" data-type="wanna">想去的景点</li>
	    </ul>
	    <div style="float:right">
            <img src="${ctxPortal}/assets/icon/return.png"/><a href="<%=basePath %>member/userinfo/getAllMyMsg" target="_self">返回个人中心</a>
        </div>
    </div>
    <!-- content -->
    <div class="content clearfix"  ms-important="allActivityView" >
        <div ms-repeat="data"  class="con-info clearfix">
            <div class="sce-img">
                <img  ms-src="${ctx}/{{el.img}}"/>
            </div>
            <div class="particular">
                <h5><a target="_blank" ms-href="<%=basePath %>tourism/view/forViewDetail?code={{el.code}}">{{el.viewName}}</a></h5>
                <p>
                    <span class="span-color">想去</span>
                    <span>{{el.goneCount}}</span>
                </p>
                <p>
                    <span class="span-color">去过</span>
                    <span>{{el.wantCount}}</span>
                </p>
                <p class="presentation">
                    	{{el.address|html}}
                </p>
            </div>
        </div>
        <!-- 木有数据的时候加上这个空标签 -->
		<div class="nodata" ms-visible="flag && !data.size()"></div>		
    </div><!-- content End -->
    <!-- page -->
	    <div class="paging paging-center paging-lg page" id="allActivityPage">
	        	  
	    </div><!-- page End -->
</div>
<!-- footer -->

<script>

	function goout(type){
	       window.location.href="${ctx}//portal/userView/initial?type="+type;
	}
   
    seajs.use([ 'header/css', 'footer/css']);
    seajs.use(["jquery", "avalon", "paginator"], function($,avalon){
		function servicesPage(pageId, currentPage, totalPage, call_,type){
			var options = {
		     	currentPage: currentPage || 1,
		     	totalPages: totalPage || 1,
		     	//removeClass:'paging-white',
		     	onPageChanged: function(e,oldPage,newPage){
		     		call_(newPage,type);
		    	}
			}
			//分页按钮
		   $('#'+pageId).bootstrapPaginator(options);
		}
		var allActivityVM = avalon.define({
			$id:'allActivityView',
			data:[],
			flag: false,
			$cacheData:{}
		});
		function getAllActivityList(currentPage,type){
			var thisCall = getAllActivityList;
			//判断缓存没有就请求服务器
			   // console.log(type)
				$.post('${ctx}/portal/userView/getPageView', {currentPage: currentPage, pageSize: 2,"type":type}, function(response){
				  	allActivityVM.flag = true
				  	if(response.totalPage > 1){
				  		servicesPage("allActivityPage", response.currentPage, response.totalPage, thisCall,type);
					}else{
						$('#allActivityPage').empty()
					}
				  	allActivityVM.data = allActivityVM.$cacheData[currentPage] = response.dataList;
				}, 'json');
			
		}
		avalon.scan();
		function loadData(){
			getAllActivityList(1,'gone');
		}	
		loadData();

		$('.listing').children('li').on('click', function(){
			var $this = $(this), _type = $this.data('type')
			$this.addClass('down').siblings().removeClass('down')
			getAllActivityList('1',_type);
		})
		/*$('#hago').on('click',function(){
		   $('#hago').addClass("down");
		   $('#wanto').removeClass("down");
		   $("#nodata").html('');
		   getAllActivityList('1','gone');
		})
		$('#wanto').on('click',function(){
		  $('#wanto').addClass("down");
		  $('#hago').removeClass("down");
		  $("#nodata").html('');
		  getAllActivityList('1','wanna');
		})*/
	 });


    
</script>
<!-- 调用地图 -->
<script>

  seajs.use('map', function(myMap){
  	var locat = ${locat}
  	/* 初始化景点地图
  	 * @param  {[string]}   city  在地图中高亮显示该目的地区域。比如：拉萨市
   	 * @param  {[array object]}   spots    景点数据，包含想去和去过的数据
     * @param  {Function} callback 点击地图回调函数
  	*/
  	myMap.init('西藏', locat, mapClick)
  	// 响应地图点击事件
  	function mapClick(param){
  		//console.log(param)
  	}
  	// 切换地图
  	// myMap.mapChange('那曲地区')
  })
</script>
<jsp:include page="/WEB-INF/views/portal/app/footer/index.jsp"></jsp:include>
</body>
</html>
