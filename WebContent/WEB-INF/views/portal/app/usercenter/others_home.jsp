<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="author" content="quansenx">
    <meta name="keywords" content="西藏旅游，西藏自驾游，藏民，西藏线路，西藏自助游路线，西藏景点，西藏天气，西藏交通，西藏，西藏旅游攻略，西藏旅游最佳时间，西藏旅游最好季节，去西藏" />
    <meta name="description" content=" 天上西藏网站为您提供全面了解屹立在雪山之巅西藏的信息资讯，包括天上西藏景区景点推荐，最佳的西藏观光时间，最地道的西藏特色叙述，最淳朴的西藏人民，天上西藏最全面的信息介绍，欢迎您前来体验雪域西藏的风采！" />
    <title>个人中心_天上西藏</title>
    <%@include file="/common-html/frontcommon.jsp" %>
    <link rel="stylesheet" href="${ctxPortal}/modules/bootstrap/3.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxPortal}/assets/css/my-center/home.css"/>
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
	            "common/css": "${ctxPortal}/assets/css/common.css",
	            "paginator":"${ctxPortal }/modules/paginator/0.5/bootstrap-paginator.js",
	            "footer/css": "${ctxPortal}/assets/css/footer.css",
	            "map": "${ctxPortal}/assets/js/map.js"
	        }
	    });
    </script>
</head>
<body >
<jsp:include page="${root}/portal/headerFooterController/header"></jsp:include>
<jsp:include page="/WEB-INF/views/portal/app/login/login_modal.jsp"></jsp:include>
<div class="container" ms-controller="view">
    <div class="bd">
        <div class="xz-box clearfix">
            <div class="box-left">
                <div class="user-info-b">
                    <div class="uib-img clearfix">
                        <input type="hidden" id="mcode" value="${mem.memberCode }">
                        <div class="uib-head">
                         <span class="uib-mark"></span><img alt="120x120" <c:if test="${empty mem.pic }">src="${ctxPortal}/static/default/male.jpg"</c:if>
						 <c:if test="${not empty mem.pic }">src="<%=basePath %>${mem.pic }"</c:if> width="120" height="120">
						</div>
                    </div>
                    <div class="uinfo">
                        <div class="uf-item">${mem.name }<c:if test="${mem.sex eq '1'}"><i class="j-male"></i></c:if><c:if test="${mem.sex eq '0'}"><i class="j-female"></i></c:if></div>
                        <div class="uf-item">积分<span class="yellow">${mem.score }<c:if test="${empty mem.score}">0</c:if> </span></div>
                        <div class="uf-item">朝圣者<span class="red">LV0</span></div>
                        <div class="uf-item clearfix">
                            <div>收藏<span class="green">${favCount }</span></div>
                            <div>评论&回复<span class="green">${repCount}</span></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="box-right">
                <div class="map-wrap">
                    <div id="map" style="height: 100%;width: 100%;"></div>
                    <a href="${ctx}/member/othershow/othersTrack?memberCode=${mem.memberCode }" target="_blank" class="footprint  other-footprint">足迹</a>
                    <div class="go-wrap">
                        <span class="been">去过：${cntGone }</span>
                        <span>想去：${cntWanna }</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="xz-box xz-box-list" id="nodata">
            <div class="J-tabs">
                <a name="stra" style="cursor: pointer;"<c:if test="${type eq 'stra'}">class="active"</c:if>><c:if test="${mem.sex eq '1' ||empty mem.sex}">他</c:if><c:if test="${mem.sex eq '0'}">她</c:if>的攻略</a>
                <a name="post"  style="cursor: pointer;"<c:if test="${type eq 'post'}">class="active"</c:if>><c:if test="${mem.sex eq '1' ||empty mem.sex}">他</c:if><c:if test="${mem.sex eq '0'}">她</c:if>的帖子</a>
                <a name="acti"  style="cursor: pointer;"<c:if test="${type eq 'acti'}">class="active"</c:if>><c:if test="${mem.sex eq '1' ||empty mem.sex}">他</c:if><c:if test="${mem.sex eq '0'}">她</c:if>的活动</a>
            </div>
           <input type="hidden" id="mtype" value="${type }" />
            <!-- 他的攻略 -->
         
            <div class="tabs-content list-unstyled athome dis-show" ms-if="type=='stra'">
                <table class="table">
                    <tbody>
	                    <tr ms-repeat="listS">
	                        <td><a ms-href="<%=basePath %>{{el.url }}" target="_self" class="black">{{el.contentTitle }}</a></td>
	                        <td class="text-center" width="180">{{el.createTime |date('yyyy-MM-dd HH:mm:ss')}}</td>
	                    </tr>
                    </tbody>
                </table>
            </div>
          
            <!-- 他的帖子 -->
          
            <div class="tabs-content list-unstyled other-post" ms-if="type=='post'">
                <table class="table">
                    <tbody>
                    <tr ms-repeat="listP">
                        <td><a ms-href="<%=basePath %>{{el.url }}" target="_self" class="black">{{el.contentTitle }}</a></td>
                        <td class="text-center" width="180">{{el.createTime |date('yyyy-MM-dd HH:mm:ss')}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
         
            <!-- 他的活动 -->
          
            <div class="tabs-content list-unstyled other-activity" ms-if="type=='acti'">
                <table class="table">
                    <tbody>
                    <tr ms-repeat="listA">
                        <td><a ms-href="<%=basePath %>{{el.linkUrl }}" target="_self" class="black">{{el.name }}</a></td>
                        <td class="text-center" width="180">{{el.createTime |date('yyyy-MM-dd HH:mm:ss')}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
          
        </div>
        <div class="xz-box" id="paging"> </div>
    </div>
</div>
<jsp:include page="${root}/portal/headerFooterController/footer"/>
<script>
    
    seajs.use(['common/css','footer/css','jquery']);
    
     /**
	  **分页
	  */
		seajs.use(['jquery','avalon','paginator'], function($,avalon) {
		   var _model = avalon.define({
	    		$id: 'view',
	    		listP:[],
	    		listS:[],
	    		listA:[],
	    		type:'post',
	    		
	    	})
		   function loadData(type, currentPage){
		   		_model.type = type
		   		var mcode=$('#mcode').val();
		        var url='';
		        if(type=='stra'){
		           url='member/othershow/othersStraPage';
		        }else if(type=='post'){
		           url='member/othershow/othersPostPage';
		        }else if(type=='acti'){
		           url='member/othershow/othersActiPage';
		        }
		        
	    		currentPage = currentPage || 1;
		    	$.ajax({
		    		url: "${ctx}"+url,
		    		data:{"currentPage": currentPage,memberCode:mcode},
		    		type:'post',
		    		dataType: 'json',
		    		success: function(data){
		    		   if(data.dataList.length){
			    			createPage(data.currentPage, data.totalPage,type);
			    			$(".nodata").remove();
		    			}else{
		    			    $(".nodata").remove();
		    			    $('#nodata').append('<div class="nodata"></div>');
		    				$('#paging').empty()
		    			}
		    			if(type=='stra'){
		    			  _model.listS=data.dataList;
		    			}else if(type=='post'){
		    			  _model.listP=data.dataList;
		    			}else if(type=='acti'){
		    			  _model.listA=data.dataList;
		    			}
		    		}
		    	})
	    	}
	        var type=$('#mtype').val();
	    	loadData(type,1)

			$(".navigation ul li").on('click', function() {
				$(this).addClass('active').nextAll('li').removeClass('active');
				$(this).prevAll('li').removeClass('active');
			});
			
			function createPage(currentPage, totalPage,type){
		       if(totalPage>=1){
		        var options = {
			     	currentPage: currentPage || 1,
			     	totalPages: totalPage || 1,
			     	//removeClass:'paging-white',
			     	onPageChanged: function(e, oldPage, newPage){
			     		loadData(type,newPage)
			     	}
				}}
				//分页按钮
				$('#paging').bootstrapPaginator(options);
	      }
			
		

		$(".xz-box-list .J-tabs a").on('click',function(){
			var $this = $(this);
			var type=$this.attr('name');
			$('#mtype').val(type);
			$this.addClass('active').siblings().removeClass('active')
		    loadData($this.attr('name'),1)
		    
		   
		});
    })
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
<script type="text/javascript">
seajs.use('jquery',function(){
        $(".xz-box-list .J-tabs a").on('click',function(){
            var $this = $(this),
                    thistxt = $this.text();
            if(thistxt == "他的攻略"){
                $this.addClass('active').nextAll('a').removeClass('active');
                $this.prevAll('a').removeClass('active');
                $(".athome").addClass('dis-show');
                $(".xz-box-list>div").not('.athome').removeClass('dis-show');
            }
            if(thistxt == "他的帖子"){
                $this.addClass('active').nextAll('a').removeClass('active');
                $this.prevAll('a').removeClass('active');
                $(".other-post").addClass('dis-show');
                $(".xz-box-list>div").not('.other-post').removeClass('dis-show');
            }
            if(thistxt == "他的活动"){
                $this.addClass('active').nextAll('a').removeClass('active');
                $this.prevAll('a').removeClass('active');
                $(".other-activity").addClass('dis-show');
              $(".xz-box-list>div").not('.other-activity').removeClass('dis-show');
            }
        });
       $(function(){
         if(${type eq 'mess'}){
				$('.athome').addClass('dis-show');
			}else if(${type eq 'stra'}){
				$('.my-strategy').addClass('dis-show');
			}else if(${type eq 'post'}){
				$('.other-post').addClass('dis-show');
			}else if(${type eq 'acti'}){
				$('.other-activity').addClass('dis-show');
			}
           
       })
    })
</script>
</body>
</html>