<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.rimi.ctibet.domain.LogUser" session="true"%>
<%@page import="com.rimi.ctibet.domain.Content"%>
<%@taglib uri="/rimi-tags" prefix="rimi"%>
<%@taglib prefix="udj" uri="/user-defined-jstl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	LogUser lg = (LogUser) request.getSession().getAttribute("logUser");
	request.setAttribute("lgUser", lg);
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="author" content="quansenx">
		<meta name="keywords" content="${post.tdkkeywords }" />
		<meta name="description" content="${post.tdkdescription }" />
		<title>${not empty post.tdktitle?post.tdktitle:post.contenttitle }</title>
		<%@include file="/common-html/commonPortal.jsp"%>
		<link rel="stylesheet" href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css">
        <!--[if lt IE 9]>
            <script src="${ctxPortal}/modules/fix/html5shiv.min.js"></script>
            <script src="${ctxPortal}/modules/fix/respond.min.js"></script>
		<![endif]-->
		<script src="${ctxPortal}/modules/jquery/jquery/1.11.1/jquery.js" type="text/javascript"></script>
		<script id="seajsnode" src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script>
		<script>
			// Set configuration
			seajs.config({
				alias: {
				 "jquery": "${ctxPortal}/modules/jquery/jquery/1.11.1/jquery.js",
					"avalon": "avalon/1.3.7/avalon.js",
					"paginator":"${ctxPortal}/modules/paginator/0.5/bootstrap-paginator.js",
					"Validform": "${ctxPortal}/modules/Validform/5.3.2/Validform.min.js",
					"common/css": "${ctxPortal}/assets/css/common.css",
					"footer/css": "${ctxPortal}/assets/css/footer.css",
					"topic/css": "${ctxPortal}/assets/css/community/topic_detail.css"
				}
			});
		</script>
		<style>
			p{margin: 0;}
			.tc-content  a, a:hover {
						color: inherit;
			}
		    .tc-content a{
			     color: #337ab7; 
 				text-decoration: underline; 
				}
		</style>
	</head>
	<body>
		<jsp:include page="${root}/portal/headerFooterController/header?seo_index=seo_index"/>
		<jsp:include page="../login/login_modal.jsp"></jsp:include>
		<div class="container">
			<div class="bd">
				<div class="place">
					<ul>
						<li class="location">
							当前位置：
						</li>
						<li>
							<a href="${ctx}/community/frontIndex">天上社区</a>
						</li>
						<li class="slipt"></li>
						<li>
							<%-- <a href="${ctx}${plate.programaUrl}">${plate.programaName }</a> --%>
							<a href="${ctx}community/list?plateCode=${plate.code}">${plate.programaName }</a>
						</li>
						<li class="slipt"></li>
						<li><font color="red">${post.contenttitle}<h2 style="display: none;">${post.contenttitle}</h2></font></li>
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
								<a href="#">${post.contenttitle}</a>
								<c:if test="${post.istop==1}"><i class="top"></i></c:if>
							</h1>
							<div class="reply">
								<div class="types">
									<span class="eye">${post.falseviewcount}</span>
								<c:if test="${post.falsereplynum!=0}">
									<span class="comment" >${post.falsereplynum-1}</span>
								</c:if>
								<c:if test="${post.falsereplynum==0}">	
									<span class="comment" >${post.falsereplynum}</span>
								</c:if>
								</div>
								<%--<a href="#" class="btn">回复</a>--%>
							</div>
						</div>
						<div class="tc-item clearfix">
							<div class="user-info">
								<c:if test="${not empty sessionScope.logUser }">
									<a target="_blank" class="user-head" href="<%=basePath%>/member/userinfo/getAllMyMsg"> <span class="user-mark"></span>
								</c:if>
								<c:if test="${(empty sessionScope.logUser)&&(post.name!='天上社区官方')&&(post.name!='天上西藏官方') }">
							
									<c:if test="${post.name!='天上社区官方'}">
										<a target="_blank" class="user-head" href="<%=basePath %>member/show/${requestScope.createuserCode}.html">
										 <span class="user-mark"></span>
									</c:if>
									
									<c:if test="${post.name=='天上社区官方'}">
								        <a class="user-head" href="#">
								        <span class="user-mark"></span>
									</c:if>
							
								</c:if>
								<c:if test="${(post.name=='天上社区官方')||(post.name=='天上西藏官方') }">
									<a class="user-head" href="javascript:;"> <span class="user-mark"></span>
								</c:if>
								
									<c:if  test="${empty post.pic}">
									     <img alt="68x68" src="${ctx}portal/static/default/male.jpg"/>
									</c:if>
									<c:if  test="${not  empty post.pic}">
									  <img alt="68x68" src="${ctx}/${post.pic}"/>
									</c:if>
									
									 </a>
								<p class="user-name">
									<c:if test="${post.sex == 1}"><i class="j-male"></i></c:if>
									<c:if test="${post.sex == 0}"><i class="j-female"></i></c:if>
									&nbsp;${post.name}
								</p>
								<div class="topic-info">
									<h4>
										朝圣者 LV0
									</h4>
									<div class="clearfix">
										<div class="half">
											<span>主题</span>
											<span>${pcount}</span>
										</div>
										<div class="half">
											<span>积分</span>
											<span>${post.score}</span>
										</div>
									</div>
								</div>
							</div>
							<div class="tc-content">
								<h3 class="time">
                                    <fmt:formatDate value="${post.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
									<span></span>
								</h3>
								<div class="topic-main" style="padding-bottom: 30px;">
									${post.content}
								</div>
								<div class="t-reply">
									<div style="padding-top: 30px;clear: both;"></div>
									<div class="types" style="width: 100%">
									<p class="offi-contact" style="float:left;">官方群①：<span class="offi-red">161489002</span>&nbsp;&nbsp;&nbsp;&nbsp;微信服务号：<span class="offi-green">ctibet</span>&nbsp;&nbsp;&nbsp;&nbsp;微信普通号：<span class="offi-green">ctibet_cn</span> &nbsp;&nbsp;&nbsp;&nbsp;微博：<span class="offi-red">天上西藏爱好者俱乐部</span></p>
										<c:if test="${not empty post.falsepraise}">
										<span  data-code="${post.code}"
                                                class="tslike <rimi:IsPraiseTag code='${post.code}'>tslike_active</rimi:IsPraiseTag>">
                                        ${post.falsepraise}</span>
										</c:if>
										<c:if test="${empty post.falsepraise}">
											<span class="tslike" data-code="${post.code}">0</span>
										</c:if>
									</div>
								</div>
							</div>
						</div>
						<div class="tc-item clearfix" ms-repeat="data"
							ms-attr-class="one:$first">
							<div class="user-info">
								<a target="_blank" class="user-head" ms-href="<%=basePath %>member/show/{{el.createuserCode}}.html"> <span class="user-mark"></span>
									<img alt="68x68" ms-src="${ctx}{{el.postuserpic==''?'portal/static/default/male.jpg':el.postuserpic}}"> </a>
								<p class="user-name">
									<i ms-class={{el.sex==1?"j-male":"j-female"}}></i>
									{{el.postusername}}
								</p>
								<div class="topic-info">
									<h4>
										朝圣者 LV0
									</h4>
									<div class="clearfix">
										<div class="half">
											<span>主题</span>
											<span>{{el.postSum}}</span>
										</div>
										<div class="half">
											<span>积分</span>
											<span>{{el.score}}</span>
										</div>
									</div>
								</div>
							</div>
							<div class="tc-content">
								<h3 class="time">
									发布于 {{el.createTime|date('yyyy-MM-dd HH:mm:ss')}}
									<span>{{($index+1+10*(el.currentPage-1))}}楼</span>
								</h3>
								<div class="topic-main" style="padding-bottom: 30px;">
									{{el.content|html}}
								</div>
								<div class="t-reply">
									<div class="types" style="width:100%">
										<p class="offi-contact" style="float:left;">官方群①：<span class="offi-red">161489002</span>&nbsp;&nbsp;&nbsp;&nbsp;微信服务号：<span class="offi-green">ctibet</span>&nbsp;&nbsp;&nbsp;&nbsp;微信普通号：<span class="offi-green">ctibet_cn</span> &nbsp;&nbsp;&nbsp;&nbsp;微博：<span class="offi-red">天上西藏爱好者俱乐部</span></p>
<%--										<span class="tslike"  ms-click="praiseContent(el.code,this)">{{el.falsepraise }}</span>--%>
										<span  ms-attr-data-code="el.code" class="tslike" ms-class="tslike_active:hasClass(el.code)">
                                        {{el.falsepraise }}</span>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div id="allActivityPage"
						class="paging paging-center paging-lg paging-white">
					</div>
					<c:if test="${not  empty logUser}">
						<div class="comment">
							<form action="${ctx}community/saveReply" method="post" id="replyForm">
								<input type="hidden" name="postCode" value="${post.code}" />
								<div>
	<%--								<textarea rows="10" class="form-control" datatype="*2-2000"--%>
	<%--										  nullmsg="请输入回复内容" errormsg="请输入2到2000个字符"--%>
	<%--										  placeholder="有什么好的建议来说两句吧~" name="content"></textarea>--%>
											  <script id="content" name="content" type="text/plain"></script>
								</div>
								
								<div class="clearfix">
									<button class="btn-comment pull-right" type="button" onclick="saveReply()">发表回复</button>
								</div>
							</form>
						</div>
					</c:if>
					<c:if test="${empty logUser}">
									<p class="log-ts pull-left">您还没有登录，请先<a href="#loginModal" data-toggle="login">登录</a>或<a href="${ctx }portal/registerController/register" target="_blank">注册</a>，再进行评论！</p>
					</c:if>
				</div>
			</div>
		</div>
		<script>
		 seajs.use(['common/css', 'footer/css','topic/css']);
			seajs.use(['jquery', 'Validform'], function ($) {
				//验证配置
				$("#replyForm").Validform({
					tiptype:3
				})
			})
			 /* 点赞之后按钮变灰 */
            $(document).on('click', '.tslike', function () {
                var $this = $(this),
                        thisCode = $this.attr('data-code');
                isRecoredContent(thisCode, $this);
            })
           /**
			 * 点击赞
			 * @return
			 */
			function clickPraiseFront(code,vs){
				$.ajax({
		            url:"${ctx}/web/praiseController/clickPraiseFront",
		            data:"code="+code,
		            type: 'POST',
		            success:function(data){
		                $(vs).parent().find("label").html(data);
		                if($(vs).parent().find("label")){
		                    frontContentStatic('tssq','conetnt',code,'praise');
		                }
		               // $(vs).parent().find("p").html(data);
		                //攻略页面特殊位置
		                //$("#praiseCount").html(data);
		            }}
			    )
		    }
           	function isRecoredContent(code,vs){
               	
		        $.ajax(
    			{
    				url:"${ctx}/web/praiseController/isRecored",
    				data:"code="+code,
    			    type: 'POST',
    				success:function(data){
								if(data=="true"){
									alert("已赞");
								}else{
									alert("点赞成功");
									//就保存
									clickPraiseFront(code,vs);
									 var number = parseInt(vs.text()) + 1;
									$(vs).text(number);
									 $(vs).addClass("tslike_active");
									 $(vs).removeAttr('onclick');
								}    			  
    				}
        	     }
	        );	
           }
	</script>
	 <script type="text/javascript" src="${ctxPortal}/modules/ueditor/1.4.3/ueditor.config.js"></script>
<%--  <script>--%>
<%--  window.UEDITOR_CONFIG.serverUrl = 'community/saveImg'--%>
<%--  </script>--%>
  <!-- 编辑器源码文件 -->
  <script type="text/javascript" src="${ctxPortal}/modules/ueditor/1.4.3/ueditor.all.js"></script>
  <!-- 实例化编辑器 -->
  <script type="text/javascript">
      
      var ue = UE.getEditor('content', {
    	  toolbars: [["unlink","link","cleardoc","simpleupload","emotion"]],
          wordCount:false,
          autoHeightEnabled:false, 
          initialFrameHeight:250
          });
<%--      ue.addListener("contentChange", function(e) {--%>
<%--      	var _this = this;--%>
<%--   		$('#markInput').val(ue.getContentTxt()).focus().blur();--%>
<%--      })--%>
      
  </script>
		<script>
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
			$cacheData:{},
			hasClass: function(code){
				var _has = ${empty codes?'[]':codes};
				//if(!_has)return false;
				return _has.indexOf(code) === -1? false:true;
			}
		});
		function getAllActivityList(currentPage){
			var thisCall = getAllActivityList;
			//判断缓存没有就请求服务器
			if(allActivityVM.$cacheData[currentPage]){
				allActivityVM.data = allActivityVM.$cacheData[currentPage];
			}else{
				$.post('${ctx}/community/getPostReplyList', {postCode:'${postCode}',currentPage: currentPage, pageSize: 10}, function(response){
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
         location.href='<%=basePath%>community/gotoSavePost?plateCode=${plateCode}';
       }
    }
	function saveReply(){
	       if(${lgUser==null}){
			   $('[data-toggle="login"]').click();
			   //console.log("return false!!!");
			   return;
            }
<%--   frontContentStatic('tssq','content','${post.code}','reply');--%>
		var textInfo = $("#replyForm textarea").val(),
				infoLength = textInfo.length;
		if($.trim(textInfo) != "" && infoLength > 2 && infoLength < 5000){
               alert("评论发表成功，请等待审核！",function(){
            	   $("#replyForm").submit();
               });
		   return true;
		}else{
			alert("您的发帖内容须在2-5000个字符！");
			}
    }
    //帖子的点赞
    function praiseContent(code,obj){
        var thisSpan = $(obj);
    		$.ajax(
    			{
    				url:"${ctx}/web/praiseController/isRecored",
    				data:"code="+code,
    			    type: 'POST',
    				success:function(data){
							if(data=="true"){
<%--								 thisSpan.addClass("tslike_active");--%>
                            }else{
							var number = parseInt(thisSpan.text()) + 1;
							thisSpan.text(number);
<%--                            thisSpan.addClass("tslike_active");--%>
                         }
    				}
        	   }
	    );	
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