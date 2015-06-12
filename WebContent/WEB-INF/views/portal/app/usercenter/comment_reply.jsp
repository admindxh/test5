<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.rimi.ctibet.domain.LogUser" session="true"%>
<%  LogUser lg=(LogUser)request.getSession().getAttribute("logUser");
    request.setAttribute("lgUser",lg);
 %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="author" content="quansenx">
    <meta name="keywords" content="西藏旅游，西藏自驾游，西藏自助游，西藏骑行，西藏文化" />
    <meta name="description" content="天上西藏网为您提供全面靠谱的西藏旅游资讯，旅游攻略，西藏自助游，西藏自驾游，西藏骑行，全面权威的西藏文化介绍。打造权威的西藏文化旅游网。" />
    <%@include file="/common-html/frontcommon.jsp"%>
    <link rel="stylesheet" href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctxPortal}/assets/css/common.css"/>
    <link rel="stylesheet" href="${ctxPortal}/assets/css/my-center/comment_reply.css"/>
    <link rel="stylesheet" href="${ctxPortal}/assets/css/footer.css"/>
    <title>【天上西藏】西藏旅游-感受西藏魅力，体验西藏生活-天上西藏官网</title>
    <!--[if lt IE 9]>
    <script src="${ctxPortal}/modules/fix/html5shiv.min.js"></script>
    <script src="${ctxPortal}/modules/fix/respond.min.js"></script>
    <![endif]-->
    <script id="seajsnode" src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script>
    <script>
    seajs.config({
        alias: {
            "jquery": "jquery/jquery/1.11.1/jquery.js",
            "avalon": "avalon/1.3.7/avalon.js",
            "paginator":"paginator/0.5/bootstrap-paginator.js",
            "header": "${ctxPortal}/assets/css/common.css",
            "footer/css": "${ctxPortal}/assets/css/footer.css"
        }
    });
    </script>
    <style>
    .main .header{
       margin-top: 30px;
    }
     .footer{margin-top: 200px!important;}
    </style>
</head>

<body>
<!-- top 
<div id="header" ms-include-src="'${ctxPortal}/header/index.html'"></div>-->
<jsp:include page="${root}/portal/headerFooterController/header"></jsp:include>
 <jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"></jsp:include>
<!-- content -->
<div class="container main" ms-controller="view">
    <div class="header"></div>
    <div class="navigation">
        <ul class="clearfix">
            <li value="post" <c:if test="${classHolder eq 'post'}">class="active"</c:if>>帖子</li>
            <li value="stra" <c:if test="${classHolder eq 'stra'}">class="active"</c:if>>攻略</li>
            <li value="read" <c:if test="${classHolder eq 'read'}">class="active"</c:if>>读西藏</li>
            <li value="look" <c:if test="${classHolder eq 'look'}">class="active"</c:if>>看西藏</li>
            <li value="view" <c:if test="${classHolder eq 'view'}">class="active"</c:if>>景点</li>
            <li value="merc" <c:if test="${classHolder eq 'merc'}">class="active"</c:if>>商户</li>
            <li value="tour" <c:if test="${classHolder eq 'tour'}">class="active"</c:if>>团游</li>
        </ul>
        <div>
            <img src="${ctxPortal}/assets/icon/return.png"/><a
                href="<%=basePath %>member/userinfo/getAllMyMsg" target="_self">返回个人中心</a>
        </div>
    </div>
    <input type="hidden" name="classHolder" value="${classHolder}" id="holder"/>
	    <div class="info-body clearfix" ms-repeat="list">
	        
	        <h5>${lgUser.username }，你对主题 <span>《<a ms-href="<%=basePath %>{{ el.url }}" target="_self">{{ el.contentTitle }}</a>》</span>发表了回复：</h5>
	        <p class="info-time"> {{ el.createTime | date('yyyy-MM-dd HH:mm:ss') }}</p>
	        <p class="info-mesg">{{ el.content }}</p>
	        
	        <button class="del" ms-click="dele(el.code);"><img src="${ctxPortal}/assets/icon/trash.png"/></button>
	    </div>
      <div class="nodata" ms-if="!list.size()"></div>
</div>

<!-- page -->
 <div id="paging" ms-if="list.size()"></div>

<!-- footer -->

</body>
<jsp:include page="${root}/portal/headerFooterController/footer"/>
<script>
    // Set configuration
    
    seajs.use(['jquery','header']);
    //分页
    seajs.use(['jquery', 'avalon', 'paginator'],function($, avalon){
    	var _model = avalon.define({
    		$id: 'view',
    		list:[],
    		dele: function(code){
    			_dele(code)
    		}
    	})
    	
    	function loadData(type, currentPage){
    		currentPage = currentPage || 1
    		var num=Math.random();
	    	$.ajax({
	    		url: '${ctx}/portal/userReply/getUserReplyPage?num='+num,
	    		data: {currentPage: currentPage,type: type},
	    		dataType: 'json',
	    		success: function(data){
	    			if(data.dataList.length){
		    			createPage(data.currentPage, data.totalPage, type)
	    			}else{
	    				$('#paging').empty()
	    			}
	    			_model.list = data.dataList
	    		}
	    	})
	    	avalon.scan();
    	}
    
    	loadData($('#holder').val())
        $(document).on('click','.cancel',function(){
            var $this = $(this);
            //Do Something
            //$this.parent().remove();    //删除当前行
        });

        $(".navigation ul li").on('click',function(){
        	loadData($(this).attr('value'))
        	$('#holder').val($(this).attr('value'));
            $(this).addClass('active').nextAll('li').removeClass('active');
            $(this).prevAll('li').removeClass('active');
        });
      function createPage(currentPage, totalPage, type){
	       if(totalPage>=1){
	        var options = {
		     	currentPage: currentPage || 1,
		     	totalPages: totalPage || 1,
		     	//removeClass:'paging-white',
		     	onPageChanged: function(e, oldPage, newPage){
		     		loadData(type, newPage)
		     	}
			}}
			//分页按钮
			$('#paging').bootstrapPaginator(options);
      }
      function _dele(obj){
          var code=obj;
          if(code==''){
             alert("请选择记录");
          }
          var con=window.confirm("确定删除当前数据？");
          var holder=$('#holder').val();
          if(con){
            // location.href='<%=basePath %>portal/userReply/delReply?code='+code+'&type='+holder;
             $.ajax({
 	    		url: '${ctx}portal/userReply/delReply',
 	    		data: {code:code,type: holder},
 	    		dataType: 'json',
 	    		success: function(data){
 	    			if(data.delflag=='-110'){
                        alert('请先登录');
 	 	 	    	}else if(data.delflag=='-1'){
                        alert('删除失败');
 	 	 	    	}else if(data.delflag=='1'){
                        alert('删除成功');
                        loadData(holder)
 	 	 	    	}
 	    		}
 	    	})
          }
      }
    });
    seajs.use('jquery',function(){
        /*$(document).on('click','.del',function(){
            var $this = $(this);
            //Do Something
            //$this.parent().remove();    //删除当前行
            
        });*/
       
        $(".navigation ul li").on('click',function(){
            $(this).addClass('active').nextAll('li').removeClass('active');
            $(this).prevAll('li').removeClass('active');
        });
        $(function(){
          if(${delflag=='1'}){
             alert("删除成功");
             var holder=$('#holder').val();
             location.href='<%=basePath%>portal/userReply/getUserReply?type='+holder;
          }
          if(${delflag=='0'}){
             alert("删除失败");
          }
        })
    });
     
</script>
</html>