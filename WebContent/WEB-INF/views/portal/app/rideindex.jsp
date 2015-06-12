<%@ page language="java"  import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="udj" uri="/user-defined-jstl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
   <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="author" content="quansenx">
  <meta name="keywords" content="西藏旅游，西藏自驾游，藏民，西藏线路，西藏自助游路线，西藏景点，西藏天气，西藏交通，西藏，西藏旅游攻略，西藏旅游最佳时间，西藏旅游最好季节，去西藏" />
  <meta name="description" content=" 天上西藏网站为您提供全面了解屹立在雪山之巅西藏的信息资讯，包括天上西藏景区景点推荐，最佳的西藏观光时间，最地道的西藏特色叙述，最淳朴的西藏人民，天上西藏最全面的信息介绍，欢迎您前来体验雪域西藏的风采！" />
  <title>骑行专区首页_天上西藏</title>
  <%@include file="/common-html/mainfrontcommon.jsp" %>
  <link rel="stylesheet" href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css">
  <link rel="stylesheet" href="${ctxPortal}/modules/slick/slick.css">
  <link rel="stylesheet" href="${ctxPortal}/assets/css/common.css">
  <link rel="stylesheet" href="${ctxPortal}/assets/css/tourism/strategy_list.css">
  <link rel="stylesheet" href="${ctxPortal}/assets/css/riding-area/index_right.css" />
  <link rel="stylesheet" href="${ctxPortal}/assets/css/ride_index.css">
  <!--[if lt IE 9]>
    <script src="${ctxPortal }modules/fix/html5shiv.min.js"></script>
    <script src="${ctxPortal }modules/fix/respond.min.js"></script>
    <![endif]-->
  <script id="seajsnode" src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script>
  <style type="text/css">
  	.down{
  		color: red !important;
  	}
  	.slick-slider{
  		margin-bottom: 10px  !important;
  	
  	}
  	.xz-box {
		/* margin-top: 0px  !important; */
	}
	.real-time {
		  color: black !important;
	 }
	 .real-time a{padding-left: 10px !important;}
  </style>
  <script>
  seajs.config({
    alias: {
      'jquery': 'jquery/jquery/1.11.1/jquery',
      'slick': 'slick/slick.min',
      'station': 'station/main',
      "avalon":"${ctxPortal}/modules/avalon/1.3.7/avalon.js",
      "bootstrap/css":"${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css",
      "common/css":"${ctxPortal}/assets/css/common.css",
      "content/top/css":"${ctxPortal}/assets/css/content_top.css",
      "footer/css":"${ctxPortal}/assets/css/footer.css",
      "paginator":"${ctxPortal }/modules/paginator/0.5/bootstrap-paginator.js",
      "commonjs":"${ctx}common-js/common.js"
      }
  })
   seajs.use(['common/css','footer/css']);
  seajs.use('avalon', function (avalon) {
      avalon.define({
          $id: "recView",
          showTag: true
      })
  })
  </script>
</head>
<body   ms-important="allview">
    <jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"></jsp:include>
	<!-- top -->
	<jsp:include page="${root}/ride/ridePropgram"></jsp:include>
	<div class="container"  >
    <div class="layout-left">
      <!-- 幻灯片 -->
      <div class="slides J_slide">
      			<c:forEach items="${blist}" var="img" >
	        		<div>
			          <a href="${img.hyperlink}" target="_blank"><img style="width: 700px;height:360px;" src="${ctx}${img.url}" alt="image"></a>
			        </div>
      			</c:forEach>
      </div>
      <!-- 星级服务 -->
      <div class="xz-box" >
        <div class="xz-heading">
          <h2 class="service"><span class="sr-only">星级服务</span></h2>
        </div>
        <div class="tabs-wrap">
          <div class="tabs-line clearfix" id="lineClick">
           <c:forEach items="${rlist}" var="line" varStatus="status">
               <a  style="text-overflow: ellipsis;overflow: hidden;white-space: nowrap;" linecode="${line.code}" href="javascript:void(0)"  
			            <c:if test="${status.index+1==1}">
			          	  class="one"
			          	</c:if>
			          	<c:if test="${status.index+1==2}">
			          	  class="two"
			          	</c:if>
			          	<c:if test="${status.index+1==3}">
			          	  class="three"
			          	</c:if>
               >${line.name}</a>
           </c:forEach>
          	
          	
          </div>
        </div>
        <div class="tab-content"  >
          <div class="tab-pane active">
            <div class="pane-intro">
              <p>路线简介</p>
              <p style="word-break: break-all">{{linedata.introduce}}</p>
            </div>
            <div class="">
              <a  target="_blank" class="btn" 
              style="padding: 4px 20px;color: white;font-size: 16px;background-color: #cf1423;"
               ms-attr-href="{{linedata.achref}}"  ms-if="linedata.achref!=''" >我要报名</a>
            </div>
            <div class="xz-heading">
              <h3 class="along">沿线看点</h3>
              <div class="index-more"><a ms-attr-href="${ctx }ride/line/list/{{linedata.code}}.html" target="_blank">更多 MORE</a></div>
            </div>
            <!-- 路线 -->
            <div class="route-wrap">
              <a href="#" class="arrow route-prev"></a>
              <a href="#" class="arrow route-next"></a>
              <div class="route-inner">
                <i class="route-inner-icon"></i>
                <div class="route-dots">
                  <ul  class="list-unstyled clearfix" id="J_Route" > 
                     <li ms-repeat="sidedata"  class="route-dot" data-repeat-rendered="rendered">
                     <a  href="#" ms-click="onItem(el,$index)" style="width:127px;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;"><i class="i-bike"></i>{{el.siteName}}</a></li>
                  </ul>
                </div>
              </div>
            </div>
            <!-- 站点信息 -->
            <h3 class="local" id="local" style="text-overflow: ellipsis;overflow: hidden;white-space: nowrap;">第<span id="zs">1</span>站&nbsp;{{sideinfo.siteName}}</h3>
            <div class="clearfix">
              <div class="st-left">
                <a href="javascript:void(0)"  ><img style="width: 116px;height: 116px;"
                
                ms-src="${ctx}{{sideinfo.siteImg==''||sideinfo.siteImg==null?'/portal/static/default/square.png':sideinfo.siteImg}}"
                 alt="站点图片"></a>
              </div>
              <div class="st-info">
                <div class="info-wrap">
                  <div class="station">
                    <h4    style="text-overflow: ellipsis;overflow: hidden;white-space: nowrap;"><a href="#" >{{sideinfo.serviceName}}</a></h4>
                    <p>地址:
                      <br>{{sideinfo.serviceAdrress|truncate(30,'...')}} </p>
                    <p>紧急援助电话：
                      <br> {{sideinfo.servicePhone}} </p>
                    <a href="javascript:void(0)"><img style="width: 120px;height: 75px;" 
                      ms-src="${ctx}{{sideinfo.serviceImg==''||sideinfo.serviceImg==null?'/portal/static/default/square.png':sideinfo.serviceImg}}"
                    ms-attr-alt="sideinfo.serviceName" width="120" height="75"></a>
                  </div>
                  <div class="shisu">
                    <ul class="clearfix">
                      <li>
                        <h4><span>宿</span></h4>
                        <h5 style="width:130px;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;"><a ms-href="${ctx}{{sideinfo.s1Url}}"  target="_blank">{{sideinfo.s1Name}}</a></h5>
                        <h5 style="width:130px;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;"><a ms-href="${ctx}{{sideinfo.s2Url}}"  target="_blank">{{sideinfo.s2Name}}</a></h5>
                        <a ms-attr-href="${ctx }tourism/merchant/merchantList/1/1/1{{sideinfo.siteName}}/1merchantType422672563900.html" target="_blank" class="rmore">more</a>
                      </li>
                      <li>
                        <h4><span>食</span></h4>
                        <h5 style="width:130px;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;"><a ms-href="${ctx}{{sideinfo.c1Url}}"  target="_blank">{{sideinfo.c1Name}}</a></h5>
                        <h5 style="width:130px;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;"><a ms-href="${ctx}{{sideinfo.c2Url}}"  target="_blank">{{sideinfo.c2Name}}</a></h5>
                        <a ms-attr-href="${ctx }tourism/merchant/merchantList/1/1/1{{sideinfo.siteName}}/1merchantType422672572609.html" class="lmore"  target="_blank">more</a>
                      </li>
                    </ul>
                  </div>
                </div>
                <div class="stg">
                  <h4>推荐攻略</h4>
                  <div class="tg-heading clearfix">
                    <div class="time">
                      <span>{{sideinfo.gldd}}</span>
                      <p>{{sideinfo.glym}}</p>
                    </div>
                    <div class="th-info">	
                      <h4  style="text-overflow: ellipsis;overflow: hidden;white-space: nowrap;"><a ms-href="${ctx}{{sideinfo.glurl}}" target="_blank">{{sideinfo.glname}}</a></h4>
                      <p style="text-overflow: ellipsis;overflow: hidden;white-space: nowrap;">作者：{{sideinfo.glathor}}</p>
                    </div>
                  </div>
                  <p class="text">{{sideinfo.glcontent|truncate(100,'...')}}</p>
                  <a href="${ctx}tourism/strage/intoTraval"  target="_blank" class="lmore2">more</a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="xz-box">
        <div class="xz-heading">
          <h2 class="jdgl"><span class="sr-only">本期景点攻略推荐</span></h2>
        </div>
        <div class="clearfix">
          <div class="stg pull-left st-info2">
            <h4>推荐攻略</h4>
            <div class="tg-heading clearfix">
              <div class="time">
                <span><fmt:formatDate value="${content.createTime}" pattern="dd"/></span>
                <p><fmt:formatDate value="${content.createTime}" pattern="yyyy-MM"/></p>
              </div>
              <div class="th-info">
                <h4><a href="${ctxIndex}${content.url}" target="_blank">${content.contentTitle}</a></h4>
                <p>作者：${content.authorCode}</p>
              </div>
            </div>
            <p class="text">${udj:subString(udj:filterHtmlTags(content.content),80)}</p>
          </div>
          <div class="pic">
          	
            <img 
            <c:if test="${not  empty content.imgUrl}">
          		src="${ctx}${content.imgUrl}"
          	
          	</c:if>
          	<c:if test="${empty content.imgUrl}">
          		src="${ctx}portal/static/default/square.png"
          	
          	</c:if>
            
            
             style="width:420px;height: 200px;" alt="${content.contentTitle }">
          </div>
        </div>
      </div>
      <div class="xz-box">
        <div class="xz-heading">
          <h2 class="past" onclick="javascript:window.open('${ctx }tourism/strage/classicsStra')" style="cursor: pointer;"><span class="sr-only">往期回顾</span></h2>
          <div class="index-more"><a href="${ctx }tourism/strage/classicsStra" target="_blank">更多 MORE</a></div>
        </div>
        <div class="sort-pane">   
          <div class="bd-sort">
            <a id="zx" href="javascript:" class="active down">最新</a>
            <a id="sc" href="javascript:;">最多收藏</a>
            <a id="pl" href="javascript:;">最多评论</a>
          </div>
          <button onclick="uploadStrage()" class="btn submit-btn">我要上传</button>
        </div>
        <div class="strategy">
          <div class="media" ms-repeat="stra"  ms-attr-class="one:$first" >
            <div class="media-left">
              <div class="strategy-img">
                <a ms-href="${ctxIndex}{{el.url}}"  target="_blank">
										<img ms-src="{{el.travelImgUrl==''||el.travelImgUrl==null?'${ctx }/portal/static/default/square.png':el.travelImgUrl}}" ms-attr-alt="el.travelTitle" style="width: 201px;height: 121px;"  />
										<img ms-if="el.isOfficial==1" class="ctibet" src="${ctxPortal}assets/icon/ctibet_yxz.png"  />
				 </a>
              </div>
            </div>
            <div class="media-body clearfix">
              <h4 class="media-heading"
                                    style="width: 450px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">
                                    <a ms-href="${ctxIndex}{{el.url}}" target="_blank">{{el.travelTitle}}</a>
                                </h4>
								<div class="post-message">
									<img style="width: 18px;height: 18px;" ms-src="${ctx}/{{el.pic==null||el.pic==''?'portal/static/default/male.jpg':el.pic}}" />
									
									<label>作者：</label><span class="writer">
									  {{el.isOfficial==1?el.userName:''}}
	                              <a ms-if="el.isOfficial!=1"   ms-href="${ctxIndex }/member/show/{{el.memberCode}}.html" target="_blank">{{el.userName}}</a>
								
									</span>
									<label class="ml10">发布时间：</label><span>{{el.createTime|date('yyyy-MM-dd')}}</span>
			  </div>
              <p class="strategy-info js_sub_info" style="word-break:break-all;">{{el.travelContent|html|truncate(80,'...')}}</p>
				<ul class="comment clearfix">
									<li>
										<img src="${ctxPortal}/assets/icon/eye_ac.png" title="查看数" /><span>{{el.viewCount==null?0:el.viewCount}}</span>
									</li>
									<li>
										<img src="${ctxPortal}/assets/icon/fav_b.png" title="收藏数" /><span>{{el.faveteCount==null?0:el.faveteCount}}</span>
									</li>
									<li>
										<img src="${ctxPortal}/assets/icon/_like.png" title="赞数" /><span>{{el.praiseCount==null?0:el.praiseCount}}</span>
									</li>
									<li>
										<img src="${ctxPortal}/assets/icon/chat.png" title="回复数" /><span>{{el.replyCount==null?0:el.replyCount}}</span>
									</li>
				</ul>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- left end-->
    <!-- right  -->
    <div class="layout-right">
      <div class="w-box">
        <iframe src="http://cache.xixik.com.cn/11/lasa/" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
      </div>
      <!-- 右边 -->
      <div class="sidebar">
        <div class="rd-content">
          <div class="rd-title clearfix">
            <h3 class="rd-new"><a href="${ctx}community/list/createTime/1/notice.html" target="_blank"><span class="sr-only">骑行新闻 NEW</span></a></h3>
            <a class="more" href="${ctx }community/list/createTime/1/notice.html" target="_blank">更多MORE</a>
          </div>
          <ul class="rd-info">
           <c:forEach items="${ridegg}" var="c" varStatus="status">
			<c:if test="${status.first}">
            <li class="real-time">●<a href="${ctx }${c.url}" target="_blank">${c.title }</a></li>
           </c:if>
           <c:if test="${!status.first}">
            <li >●<a href="${ctx }${c.url}" target="_blank">${c.title }</a></li>
           </c:if>
           </c:forEach>
          </ul>
        </div>
        <div class="rd-content">
          <div class="rd-title clearfix">
            <h3 class="rd-story"><a href="${ctx}community/list/createTime/1/stroty.html" target="_blank"><span class="sr-only">骑行故事 STORY</span></a></h3>
            <a  class="more" href="${ctx }community/list/createTime/1/stroty.html" target="_blank">更多MORE</a>
          </div> 
          <ul class="rd-info">
           <c:forEach items="${ridegs}" var="c" varStatus="status">
			<c:if test="${status.first}">
            <li class="real-time">●<a href="${ctx }${c.url}" target="_blank">${c.title }</a></li>
           </c:if>
           <c:if test="${!status.first}">
            <li >●<a href="${ctx }${c.url}" target="_blank">${c.title }</a></li>
           </c:if>
           </c:forEach>
          </ul>
        </div>
        <div class="rd-content">
          <div class="rd-title clearfix">
            <h3 class="rd-coll"><a href="${ctx}community/list/createTime/1/collect.html" target="_blank"><span class="sr-only">骑友征集 COLLECTION</span></a></h3>
            <a class="more" href="${ctx }community/list/createTime/1/collect.html"  target="_blank">更多MORE</a>
          </div>
          <ul class="rd-info">
            <c:forEach items="${ridezj}" var="c" varStatus="status">
			<c:if test="${status.first}">
            <li class="real-time">●<a href="${ctx }${c.url}" target="_blank">${c.title }</a></li>
           </c:if>
           <c:if test="${!status.first}">
            <li >●<a href="${ctx }${c.url}" target="_blank">${c.title}</a></li>
           </c:if>
           </c:forEach>
          </ul>
        </div>
        <div class="rd-content">
          <div class="rd-title clearfix">
            <h3 class="rd-dis"><a href="${ctx}community/list/createTime/1/discuss.html" target="_blank"><span class="sr-only">装备讨论 DISCUSSION</span></a></h3>
            <a class="more" href="${ctx }community/list/createTime/1/discuss.html" target="_blank">更多MORE</a>
          </div>
          <ul class="rd-info">
            <c:forEach items="${ridezb}" var="c" varStatus="status">
			<c:if test="${status.first}">
            <li class="real-time">●<a href="${ctx }${c.url}" target="_blank">${c.title }</a></li>
           </c:if>
           <c:if test="${!status.first}">
            <li >●<a href="${ctx }${c.url}" target="_blank">${c.title }</a></li>
           </c:if>
           </c:forEach>
          </ul>
        </div>
        <div class="rd-content">
          <div class="rd-title clearfix">
            <h3 class="rd-equip"><span class="sr-only">装备推荐 EQUIP</span></h3>
            <a class="more" href="${ctx }ride/eqindex/list" target="_blank">更多MORE</a>
          </div>
          <div class="eq-info clearfix">
          	<c:forEach var="c" items="${zbtj}">
                <a href="${ctx}${c.url}" target="_blank"><img style="width:135px;height:135px;" src="${ctx}${c.imgurl}"/></a>
          	</c:forEach>
          </div>
        </div>
        <div class="rd-content">
          <h3 class="rd-link"><span class="sr-only">友情链接 LINK</span></h3>
          <div class="link-info clearfix">
            <ul>
              <li><a title="${yqlj[0].ctorname}" href="${yqlj[0].url}" target="_blank">${yqlj[0].ctorname}</a></li>
              <li><a title="${yqlj[1].ctorname}" href="${yqlj[1].url}" target="_blank">${yqlj[1].ctorname}</a></li>
            </ul>
            <ul class="r-plate">
              <li><a href="${yqlj[2].url}" title="${yqlj[2].ctorname}" target="_blank">${yqlj[2].ctorname}</a></li>
              <li><a href="${yqlj[3].url}" title="${yqlj[3].ctorname}" target="_blank">${yqlj[3].ctorname}</a></li>
            </ul>
          </div>
        </div>
        <div class="advertising">
        	<c:forEach items="${arealist}" var="ad" >
		          <a  href="${ad.url}" target="_blank"><img style="width:280px;height:180px;" src="${ctx}${ad.imgurl}" /></a>
        	</c:forEach>
        </div>
      </div>
      <!-- 右边 结束-->
    </div>
  </div>
  <script>
  seajs.use(['jquery', 'avalon', 'slick', 'station', 'paginator', 'commonjs'], function($, avalon) {
    $(function() {
      // 沿线看点
     	
      $('.J_slide').slick({
        autoplay: true,
        dots: true,
        arrows: false
      })
    })

    	$("#zx").click(function(){
			$("#zx").addClass("down");
			$("#sc").removeClass();
			$("#pl").removeClass();
			getStra(1);
		})
    	$("#sc").click(function(){
			$("#sc").addClass("down");
			$("#zx").removeClass();
			$("#pl").removeClass();
			getStra(2);
		})
    	$("#pl").click(function(){
			$("#pl").addClass("down");
			$("#zx").removeClass();
			$("#sc").removeClass();
			getStra(3);
		})
    $("#lineClick a").click(function(){
		var	 linecode  =	$(this).attr("linecode");
		loadData(linecode);
     });
	var allActivityVM = avalon.define({
		$id:'allview',
		linedata:{},//线路数据
		sidedata:[],//站点数据
		sideinfo:{},//站点信息
		stra:[],//攻略列表
		$cacheData:{},
		rendered: function(){
			$('#J_Route').station();
			//console.log(allActivityVM.linedata.code)
			if(allActivityVM.sidedata.$model[0]){
				var  code1  =  allActivityVM.sidedata.$model[0].code;
				$("#zs").html(1);
				getSiteInfo(code1);
			}else{
				//var  code1  =  allActivityVM.sidedata.$model[0].code;
				$("#zs").html(1);
				getSiteInfo("");
			}
		},
		onItem: function(el,index){
			//console.log(el.$model)
			var  indexh  = index+1;
			console.log(el.$model)
			var code  = el.code;  
			//console.log(indexh+"-----"+code);
			$("#zs").html(indexh);
			getSiteInfo(code);
		}
	});
	window.allActivityVM = allActivityVM
	//加载线路
	function getAllActivityList(linetype){
		var thisCall = getAllActivityList;
		   //判断缓存没有就请求服务器
			$.post('${ctx}ride/getSiteLine', {lineType:linetype}, function(response){
				//加载线路信息
			  	allActivityVM.linedata = response;
			  	//加载站点信息
			  	getSiteList(linetype);
			}, 'json');
			
	}

	//加载站点
	function getSiteList(linetype){
		$.post('${ctx}ride/getSiteList', {lineType:linetype}, function(response){
			//加载线路信息
		  	allActivityVM.sidedata = response;
		  	var a  = $("#J_Route li").eq(0);
		  	$(a).find("a").addClass("active");
		  	//同时激活第一个站点。
		}, 'json');
    }

	//加载站点信息
	function getSiteInfo(siteId){
		$.post('${ctx}ride/siteInfo', {siteId:siteId}, function(response){
			//加载线路信息
		  	allActivityVM.sideinfo = response;
		  	//同时激活第一个站点。
		}, 'json')
		.success(function(){
			
		});
    }

	function getStra(order){
			$.post('${ctx}/tourism/strage/getTravalIndexActivityList', {order:order,currentPage: 1, pageSize: 5,programCode:'c1d8e87d-792e-11e4-b6ce-005056a05bc9'}, function(response){
			  	allActivityVM.stra  = response.dataList;
			}, 'json');
	}
	
	avalon.scan();
	function loadData(linetype){
		getAllActivityList(linetype);
	}
	//默认加载 线路信息
	//获取默认的线路 
	var linetype  = $(".one").attr("linecode");
	loadData(linetype);
	getStra(1)
  });

  
  </script>
<script type="text/javascript">
			//上传攻略
			function  uploadStrage(){
					if(checkPortalUserLongin()){
						         var  href  = window.location.href;
						 		window.open('${ctx}tourism/upload/intoStragePage?sourceHref='+href);	
				      }else{
				 			 //alert('请登录！登录后可以上传');
				 			$('[data-toggle="login"]').click();
				        }
				}
</script>
    <!-- footer -->
	<jsp:include page="${root}/portal/headerFooterController/footer"/>
    <script type="text/javascript" src="${ctx}/common-js/seajindex.js"></script>
    <jsp:include page="/common-html/PageUtil.jsp"></jsp:include>
    <jsp:include page="/common-html/FrontContentStaticUtil.jsp"></jsp:include>
</body>
</html>