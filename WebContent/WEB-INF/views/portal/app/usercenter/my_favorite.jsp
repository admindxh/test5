<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
    <link rel="stylesheet" href="${ctxPortal}/assets/css/my-center/my_favorite.css"/>
    <title>【天上西藏】西藏旅游-感受西藏魅力，体验西藏生活-天上西藏官网</title>
    <!--[if lt IE 9]>
    <script src="${ctxPortal}/modules/fix/html5shiv.min.js"></script>
    <script src="${ctxPortal}/modules/fix/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
       .main{
	    	margin: 0 auto!important;
	    }
	    .footer{margin-top: 230px!important;}
    </style>
    <script id="seajsnode" src="${ctxPortal}/modules/seajs/seajs/2.2.1/sea.js"></script>
    <script>
    // Set configuration
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
</head>

<body>
<!-- top -->
<jsp:include page="${root}/portal/headerFooterController/header"></jsp:include>
 <jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"></jsp:include>
<!-- content -->
<div class="container main" ms-controller="view" id="nodata">
    <div class="header"></div>
    <div class="navigation">
        <ul class="clearfix">
           <li value="post" <c:if test="${classHolder eq 'post'}">class="active"</c:if>>帖子</li>
            <li value="stra" <c:if test="${classHolder eq 'stra'}">class="active"</c:if>>攻略</li>
            <li value="read" <c:if test="${classHolder eq 'read'}">class="active"</c:if>>读西藏</li>
            <li value="look" <c:if test="${classHolder eq 'look'}">class="active"</c:if>>看西藏</li>
            <li value="merc" <c:if test="${classHolder eq 'merc'}">class="active"</c:if>>商户</li>
            <li value="tour" <c:if test="${classHolder eq 'tour'}">class="active"</c:if>>团游</li>
        </ul>
        <div>
            <img src="${ctxPortal}assets/icon/return.png"/><a
                href="<%=basePath %>member/userinfo/getAllMyMsg" target="_self">返回个人中心</a>
        </div>
    </div>
    <div class="info-head clearfix">
        <div class="info-name">名称</div>
        <div class="add-time">收藏时间</div>
        <div class="operation">操作</div>
    </div>
    <input type="hidden" name="holder" value="${classHolder }" id="holder"/>
   <!-- 帖子  攻略 读西藏
   -->
	  <div ms-if="mtype=='post'||mtype=='stra'||mtype=='read'||mtype=='look'">
	    <div class="info-body clearfix"  ms-repeat="list">
	        <div class="info-name"><a ms-href="<%=basePath %>{{el.url }}">{{el.contentTitle }}</a></div>
	        <div class="add-time">{{el.joinTime | date('yyyy-MM-dd HH:mm:ss')}} </div>
	        <div ms-click="cancelFav(el.code)" class="operation cancel">取消收藏</div>
	    </div>
	  </div>
   <!--商户-->
	  <div ms-if="mtype=='merc'"> 
	    <div class="info-body clearfix"   ms-repeat="listM">
	        <div class="info-name"><a ms-href="<%=basePath %>{{el.url }}">{{el.merchantName }}</a></div>
	        <div class="add-time">{{el.joinTime | date('yyyy-MM-dd HH:mm:ss')}}</div>
	        <div ms-click="cancelFav(el.code)" class="operation cancel">取消收藏</div>
	    </div>
	  </div>
	   
   <!--团游-->
	  <div  ms-if="mtype=='tour'"> 
	    <div class="info-body clearfix" ms-repeat="listT">
	        <div class="info-name"><a ms-href="<%=basePath %>{{el.url }}">{{el.name }}</a></div>
	        <div class="add-time">{{el.joinTime | date('yyyy-MM-dd HH:mm:ss')}}</div>
	        <div ms-click="cancelFav(el.code)" class="operation cancel">取消收藏</div>
	    </div>
	  </div> 
</div>

<!-- page -->
   <div id="paging"></div>

<!-- footer -->
<jsp:include page="${root}/portal/headerFooterController/footer"/>
</body>
<script>
    seajs.use(['jquery','avalon','paginator'],function($,avalon){
        var _model = avalon.define({
    		$id: 'view',
    		listM:[],
    		 list:[],
    		listT:[],
    		mtype:'post',
    		cancelFav: function(code){
    			cancelFav(code)
    		}
    	})
       function loadData(type, currentPage){
    		currentPage = currentPage || 1
	    	$.ajax({
		    	type:'post',
	    		url: '${ctx}/portal/userFavorite/getUserFavPage',
	    		data: {currentPage: currentPage,type: type},
	    		dataType: 'json',
	    		success: function(data){
	    			if(data.dataList.length){
		    			createPage(data.currentPage, data.totalPage, type)
		    			$('#holder').val(type);
		    			$(".nodata").remove();
	    			}else{
	    			    $('#paging').empty()
	    			    if($(".nodata").length==0)
	    			     $('#nodata').append('<div class="nodata"></div>');
	    			    
	    			}
	    			if(type=='merc'){
	    			  _model.listM = data.dataList
	    			  
	    			}else if(type=='tour'){
	    			  _model.listT = data.dataList
	    			}else{
	    			   _model.list = data.dataList
	    			}
	    		}
	    	})
    	}
        var holder=$('#holder').val();
    	loadData(holder)
    
    
       $(".navigation ul li").on('click',function(){
            _model.mtype=$(this).attr('value');
             $('#holder').val($(this).attr('value'));
            loadData($(this).attr('value'))
            $(this).addClass('active').nextAll('li').removeClass('active');
            $(this).prevAll('li').removeClass('active');
        });
        var currentPage = ${pager.currentPage};
        var totalPage = ${pager.totalPage};
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
      if(${delflag=='1'}){
           var holder=$('#holder').val();
           alert("操作成功");
          
           loadData(holder,1);
          // location.href='<%=basePath%>portal/userFavorite/getUserFav?type='+holder;
        }
        if(${delflag=='0'}){
           alert("操作失败");
        }
        if(${nocode=='-1'}){
           alert("code不能为空");
        }
         function cancelFav(obj){
	       var con=window.confirm("确定取消收藏该项？");
	       var type=$('#holder').val();
	       var holder=$('#holder').val();
	       if(con){
	          if(type=='post'){
	             type='post';
	             //location.href='<%=basePath%>portal/userFavorite/delFav?type=post&code='+obj;
	          }
	          if(type=='stra'){
	           type='stratege';
	            // location.href='<%=basePath%>portal/userFavorite/delFav?type=stratege&code='+obj;
	          }
	          if(type=='read'){
	            type='readTibet';
	            // location.href='<%=basePath%>portal/userFavorite/delFav?type=readTibet&code='+obj;
	          }
	          if(type=='merc'){
	             type='merchant';
	             //location.href='<%=basePath%>portal/userFavorite/delFav?type=merchant&code='+obj;
	          }
	          if(type=='tour'){
	             type='tourGroup';
	            // location.href='<%=basePath%>portal/userFavorite/delFav?type=tourGroup&code='+obj;
	          }
	           if(type=='look'){
	             type='seeTibet';
	             //location.href='<%=basePath%>portal/userFavorite/delFav?type=seeTibet&code='+obj;
	          }
	          $.ajax({
			    	type:'post',
		    		url: '${ctx}/portal/userFavorite/delFav',
		    		data: {type: type,code: obj},
		    		dataType: 'json',
		    		success: function(data){
		    			if(data.delflag=='-110'){
		    			  alert("请先登录");
		    			}else if(data.delflag=='-1'){
		    			  alert("取消收藏失败");
		    			}if(data.delflag=='1'){
		    			  alert("取消收藏成功");
		    			  loadData(holder,1);
		    			}
		    		}
		    	})
	        }
	     }
    });
    
   
     
     seajs.use(['header', 'footer/css']);
</script>
</html>