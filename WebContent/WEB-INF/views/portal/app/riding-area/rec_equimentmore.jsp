<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="udj" uri="/user-defined-jstl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <meta http-equiv="Content-Type" content="IE=edge,chrome=1"/>
    <meta name="keywords" content="西藏骑行攻略，西藏骑行注意事项，西藏骑行计划，骑行318，川藏线，川藏线骑行攻略，骑行西藏，318川藏线"/>
    <meta name="description"
          content="骑行专区：为驴友提供高质量的西藏骑行分享交流专区，包括西藏骑行攻略，西藏骑行注意事项，西藏骑行计划，西藏骑行约伴，骑行西藏，318川藏线，骑行318，川藏线骑行攻略等西藏骑行分享。"/>
    <meta name="Author" content="Mr.Piz"/>
    <title>骑车去西藏_骑行专区-天上西藏官网</title>
     <%@include file="/common-html/mainfrontcommon.jsp" %>
    <link rel="stylesheet" href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctxPortal}/assets/css/riding-area/rec_equipment.css"/>
    <!--[if lt IE 9]>
    <script src="${ctxPortal}/modules/fix/html5shiv.min.js"></script>
    <script src="${ctxPortal}/modules/fix/respond.min.js"></script>
    <![endif]-->
    <script id="seajsnode" src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script>
    <script>
        // Set configuration
        seajs.config({
            alias: {
                "jquery": "jquery/jquery/1.11.1/jquery.js",
                "avalon": "avalon/1.3.7/avalon.js",
                "common/css": "${ctxPortal}/assets/css/common.css",
                "content/top/css": "${ctxPortal}/assets/css/content_top.css",
                "footer/css": "${ctxPortal}/assets/css/footer.css",
                "commonjs":"${ctx}common-js/common.js",
				"paginator":"${ctxPortal }/modules/paginator/0.5/bootstrap-paginator.js",
            }
        });
        seajs.use(["common/css", "slick/slick.css", "footer/css"]);
        seajs.use('avalon', function (avalon) {
            avalon.define({
                $id: "recView",
                showTag: true
            })
        })
    </script>
</head>
<body ms-controller="recView">
<!-- 登录弹出框  -->
<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"></jsp:include>
<!-- header -->
<jsp:include page="${root}/ride/ridePropgram"></jsp:include>
<div class="container">
    <!-- banner -->
   <div id="rec-banner" class="clearfix">
        <div class="pull-left class-menu">
            <a href="${ctx}ride/eqindex/list"><div class="rec-class">分类</div></a>
            <ul class="rec-list">
                
                <c:forEach items="${list}" var="p" varStatus="status">
                    <li style="text-overflow:ellipsis;overflow: hidden;white-space: nowrap;" class="list-item${status.index+1 }       <c:if test='${p.code==type}'>   rec-active</c:if>"  onclick="javascript:window.location.href='${ctx }ride/eqindex/more/${p.code}.html'">
                    	${p.programaName}
                    </li>
                </c:forEach>
            </ul>
        </div>
        <div class="pull-right rec-slick">
            <div class="slick">
                	<c:forEach items="${blist}" var="b" varStatus="">
                			<div><a href="${ctx }${b.name}"><img width="700" height="305" src="${ctx}${b.url}" alt="banner"/></a></div>
                	</c:forEach>
            </div>
        </div>
    </div>
    <!-- content -->
    <div id="rec-content">
        <div class="row"  ms-important="allActivityView">
             <div class="rec-title clearfix">
                 <h3><span>${p.programaName }</a></span></h3>
                 <%--<a href="${ctx }/ride/eqindex/more?type=${p.code}">更多 MORE</a>
             --%></div>
            <div class="rec-left" >
                <div class="rec-info clearfix" id="nodata">
                    <div class="commodity"   ms-repeat="data">
                        <div class="commodity-img">
                            <a ms-href="${ctx }{{el.url}}" target="_blank"><img   style="width:224px;height:155px;" ms-src="${ctx }{{el.sumaryimg==''||el.sumaryimg==null?'/portal/static/default/square.png':el.sumaryimg}}" alt="商品"/></a>
                        </div>
                        <div class="commodity-info">
                            <p><a  target="_blank" ms-href="${ctx }{{el.url}}">{{el.name}}<br/>{{el.summary}}</a></p>
                            <span class="price">{{el.price}}元</span>
                        </div>
                    </div>
                </div>
                <!-- 分页 -->
                <div class="paging paging-center paging-lg page"   id="allActivityPage">
                    
                </div>
            </div>
            
             <div class="rec-right" >
                <div class="advertising">
                   <c:forEach items="${arealist}" var="ad">
                      <div><a target="_blank" href="${ad.url}"><img src="${ctx}/${ad.imgurl}" alt="广告"/></a></div>
                   </c:forEach>
                </div>
                <div class="sidebar">
                    <div class="rec-title clearfix">
                        <h3 class="rec_new" style="cursor: pointer;" onclick="window.open('${ctx }community/listzb/createTime/2334fb28-be86-4ac5-a251-6eb069ca7b2f.html');"><span class="sr-only">最新发布</span></h3>
                        <a href="javascript:;" onclick="window.open('${ctx }community/listzb/createTime/2334fb28-be86-4ac5-a251-6eb069ca7b2f.html');">更多 MORE</a>
                    </div>
                    <ul>
                      <c:forEach items="${createTime}" var="c">
                        <li><span>●</span><a target="_blank" href="${ctx }${c.url}">${c.title}</a></li>
                      </c:forEach>
                    </ul>
                </div>
                <div class="sidebar">
                    <div class="rec-title clearfix">
                        <h3 class="rec_reply" style="cursor: pointer;" onclick="window.open('${ctx }community/listzb/newReply/2334fb28-be86-4ac5-a251-6eb069ca7b2f.html');"><span class="sr-only">最新回复</span></h3>
                        <a href="javascript:;" onclick="window.open('${ctx }community/listzb/newReply/2334fb28-be86-4ac5-a251-6eb069ca7b2f.html');">更多 MORE</a>
                    </div>
                    <ul>
                        <c:forEach items="${replyTime}" var="c">
                        <li><span>●</span><a target="_blank" href="${ctx }${c.url}">${c.title}</a></li>
                      </c:forEach>
                    </ul>
                </div>
                <div class="sidebar">
                    <div class="rec-title clearfix">
                        <h3 class="rec_fav" style="cursor: pointer;" onclick="window.open('${ctx }community/listzb/mostPraise/2334fb28-be86-4ac5-a251-6eb069ca7b2f.html');"><span class="sr-only">被赞最多</span></h3>
                        <a href="javascript:;" onclick="window.open('${ctx }community/listzb/mostPraise/2334fb28-be86-4ac5-a251-6eb069ca7b2f.html');">更多 MORE</a>
                    </div>
                    <ul>
                       <c:forEach items="${praiseTime}" var="c">
                          <li><span>●</span><a target="_blank" href="${ctx }${c.url}">${c.title}</a></li>
                      </c:forEach>
                    </ul>
                </div>
            </div>
            
            
            
            
            
            
        </div>
    </div>
</div>
<!-- footer -->
<!-- footer -->
<jsp:include page="${root}/portal/headerFooterController/footer"/>
<script>
    seajs.use(['jquery', 'slick/slick.js'], function ($) {
        $(function(){
            $('.slick').slick({
                autoplay: true,
                dots: true,
                arrows: false
            });
            $('.advertising').slick({
                autoplay: true,
                dots: true,
                arrows: true
            })
        });
    });
</script>

<script>
			 // Set configuration
			
	  seajs.use('${ctxPortal }/assets/js/subStri.js',function(str){
           	str.strClip('.js_sub_info',100);
        });
	   seajs.use(["jquery", "avalon", "paginator", "commonjs"], function($, avalon){
		//分页
		function servicesPage(pageId, currentPage, totalPage, call_){
			var options = {
		     	currentPage: currentPage || 1,
		     	totalPages: totalPage || 1,
		     	//removeClass:'paging-white',
		     	onPageChanged: function(e,oldPage,newPage){
		     		call_(newPage);
		    	}
			}
			//分页按钮
			$('#'+pageId).bootstrapPaginator(options);
		}
		var allActivityVM = avalon.define({
			$id:'allActivityView',
			data:[],
			$cacheData:{}
		});
		function getAllActivityList(currentPage){
			var thisCall = getAllActivityList;
			//判断缓存没有就请求服务器
			if(allActivityVM.$cacheData[currentPage]){
				allActivityVM.data = allActivityVM.$cacheData[currentPage];
				seajs.use('${ctxPortal }/assets/js/subStri.js',function(str){
	            	str.strClip('.js_sub_info',100);
		        });
			}else{
				$.post('${ctx}ride/eqindex/getEqList', {type:'${type}',currentPage: currentPage, pageSize: 12}, function(response){
				  	servicesPage("allActivityPage", response.currentPage, response.totalPage, thisCall);
				  	allActivityVM.data = allActivityVM.$cacheData[currentPage] = response.dataList;
				  	if(response.dataList.length<=0){$("#nodata").append('<div class="nodata" ></div>')
							$("#allActivityPage").remove();
					 }
			        
				}, 'json');
			}
		}
		avalon.scan();
		function loadData(){
			getAllActivityList(1);
		}
		loadData();
	 });
	   
	</script>




</body>
</html>