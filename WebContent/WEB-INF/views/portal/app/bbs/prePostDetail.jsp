<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="author" content="quansenx">
		<meta name="keywords"
			content="西藏旅游，西藏自驾游，藏民，西藏线路，西藏自助游路线，西藏景点，西藏天气，西藏交通，西藏，西藏旅游攻略，西藏旅游最佳时间，西藏旅游最好季节，去西藏" />
		<meta name="description"
			content=" 天上西藏网站为您提供全面了解屹立在雪山之巅西藏的信息资讯，包括天上西藏景区景点推荐，最佳的西藏观光时间，最地道的西藏特色叙述，最淳朴的西藏人民，天上西藏最全面的信息介绍，欢迎您前来体验雪域西藏的风采！" />
		<title>帖子详情页面_天上西藏</title>
		<%@include file="/common-html/frontcommon.jsp"%>
		<link rel="stylesheet"
			href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css">
		<script id="seajsnode"
			src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script>
			<script type="text/javascript" src="${ctxPortal}/modules/jquery/jquery/1.11.1/jquery.js"></script>
		<!--[if lt IE 9]>
	 <script src="${ctxPortal}modules/fix/html5shiv.min.js"></script>
    <script src="${ctxPortal}modules/fix/respond.min.js"></script>
	<![endif]-->
	</head>
	<body>
		<jsp:include page="${root}/portal/headerFooterController/header"></jsp:include>
		<jsp:include page="../login/login_modal.jsp"></jsp:include>
		<div class="container">
			<div class="bd">
				<div class="place">
					<ul>
						<li class="location">
							当前位置：
						</li>
						<li>
							<a href="#">天上社区</a>
						</li>
						<li class="slipt"></li>
						<li>
							<a href="#">${plate.programaName }</a>
						</li>
						<li class="slipt"></li>
						<li class="active">

						</li>
					</ul>
				</div>
				<div class="topic-content" ms-important="allActivityView">
					<div class="post">
						<a href="javascript:void(0);" class="btn" onclick="savePost()">发帖子</a>
					</div>
					<div class="tc-bd">
						<div class="topic-title">
							<h1>
								<a href="#">${post.contentTitle}</a>
							</h1>
							<div class="reply">
								<div class="types">
									<span class="eye">0</span>
									<span class="comment">0</span>
								</div>
							</div>
						</div>
						<div class="tc-item clearfix">
							<div class="user-info">
								<a class="user-head" href="#"> <span class="user-mark"></span>
									<img alt="68x68" src="${ctx}${user.pic}"> </a>
								<p class="user-name">
									${user.username}
									<c:if test="${user.sex == 1}"><i class="j-male"></i></c:if>
									<c:if test="${user.sex == 0}"><i class="j-female"></i></c:if> 
								</p>
								<div class="topic-info">
									<h4>
										朝圣者LV0
									</h4>
									<div class="clearfix">
										<div class="half">
											<span>主题</span>
											<span>${pcount}</span>
										</div>
										<div class="half">
											<span>积分</span>
											<span>${user.score}</span>
										</div>
									</div>
								</div>
							</div>
							<div class="tc-content">
								<h3 class="time">
                                    <fmt:formatDate value="${post.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
									<span></span>
								</h3>
								<div class="topic-main">
									${post.content}
								</div>
								<div class="t-reply">
									<div class="types">
										<span class="like">0</span>
										<span class="comment">0</span>
									</div>
								</div>
							</div>
						</div>
					<div id="allActivityPage"
						class="paging paging-center paging-lg paging-white">
					</div>
				</div>
			</div>
		</div>
    </div>
		<script>
		// Set configuration
		seajs.config({
			alias: {
			 "jquery": "jquery/jquery/1.11.1/jquery.js",
             "avalon": "avalon/1.3.7/avalon.js",
             "common/css": "${ctxPortal}/assets/css/common.css",
             "footer/css": "${ctxPortal}/assets/css/footer.css",
             "topic/css": "${ctxPortal}/assets/css/community/topic_detail.css"
			}
		});
		 seajs.use(['common/css', 'footer/css','topic/css']);
	</script>
		<script>
			 // Set configuration
			seajs.config({
				alias: {
					"jquery": "${ctxPortal}/modules/jquery/jquery/1.11.1/jquery.js",
					"avalon": "${ctxPortal}/modules/avalon/1.3.7/avalon.js",
					"paginator":"${ctxPortal}/modules/paginator/0.5/bootstrap-paginator.js"
					}
			});
	        seajs.use(["jquery", "avalon", "paginator"], function(){
		//
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
			}else{
				$.post('${ctx}/community/getMerchantList', {postCode:'${postCode}',currentPage: currentPage, pageSize: 1}, function(response){
				  	servicesPage("allActivityPage", response.currentPage, response.totalPage, thisCall);
				  	allActivityVM.data = allActivityVM.$cacheData[currentPage] = response.dataList;
				}, 'json');
			}
		}
		avalon.scan();
		function loadData(){
			getAllActivityList(1);
		}
		loadData();
	 });
		 //判断用户是否登录
	function savePost(){
       if(${lgUser==null}){
    	   $('[data-toggle="login"]').click();
    	   return;
       }else{
         location.href='<%=basePath%>community/gotoSavePost';
       }
    }
	function saveReply(){
       if(${lgUser==null}){
    	   $('[data-toggle="login"]').click();
    	   return false;
       }
       frontContentStatic('tssq','content','${post.code}','reply');
       return true;
    }
	</script>
		<jsp:include page="/common-html/FrontContentStaticUtil.jsp"></jsp:include>
		<script>
	   $(function(){
						 frontContentStatic('tssq','conetent','${post.code}','click');
			        })
			 </script>
		<jsp:include page="../footer/index.jsp" />
		<jsp:include page="/common-html/PageUtil.jsp"></jsp:include>
	</body>
</html>
