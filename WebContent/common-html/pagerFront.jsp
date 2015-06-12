<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>前台分页</title>
 
	<style> 
	#page li.disable {
		color: #a6a3a3;
	}
	
	#page li.disable a {
		color: #a6a3a3;
	}
	</style>
	</head>
	<body>
		<script type="text/javascript">
		var currentPage;
		var totalPage;
		$().ready( function() {
			 currentPage=${pager.currentPage};
			 totalPage=${pager.totalPage};
			 $(function(){
		    	$("#listForm").bind('submit',function(){
		    		var pcurrentPage = $("#currentPage").val();
		     		if(currentPage==pcurrentPage){
			     		if($(this).attr("action").indexOf("exp")<0){
			     			$("#currentPage").val("1");
			     		}
		     		}
			    });
			  })
			 addText(currentPage,totalPage);
			
		});
		function addText(currentPage,totalPage){
			$('#listForm').append("<input type='hidden' name='currentPage' id='currentPage' value='"+currentPage+"' />");
			var els=$('#page li[id!="indexPage"][id!="prePage"][id!="nextPage"][id!="lastPage"]').remove();
			     var startPoint = 1;
       			 var endPoint = 9;

        		if (currentPage > 4) {
           		 startPoint = currentPage - 4;
            	endPoint = currentPage + 4;
        		}

        		if (endPoint > totalPage) {
         		   startPoint = totalPage - 8;
          	 	   endPoint = totalPage;
        		}
        		 if (startPoint < 1) {
            		startPoint = 1;
       			 }
       		if(currentPage ==totalPage){
				$('#nextPage').addClass('disable');
				$('#lastPage').addClass('disable');
				$('#nextPage').children('a').removeAttr('onclick');
				$('#lastPage').children('a').removeAttr('onclick');
				
			}
			else{
				$('#nextPage').children('a').attr('onclick',"changeText('next')");
				$('#lastPage').children('a').attr('onclick',"changeText('last')");
			}
			if(currentPage==1){
				$('#indexPage').addClass('disable');
				$('#prePage').addClass('disable');
				$('#indexPage').children('a').removeAttr('onclick');
				$('#prePage').children('a').removeAttr('onclick');
			}
			else{
				$('#indexPage').children('a').attr('onclick',"changeText('index')");
				$('#prePage').children('a').attr('onclick',"changeText('pre')");
			}
			for(var i=startPoint;i<=endPoint;i++){
				var o=$('<li><a href="javascript:void(0)">1</a></li>');
				o.children().text(i);

					if(i==currentPage){
						$(o).addClass('active');
					}
					else{
						o.children().click(function(){
						$('#currentPage').val($(this).text());
						pagerSubmit();
						});
					}
				o.insertBefore("#nextPage");
			}
		}
		function pagerSubmit(){
			$('#listForm').submit();
		}
		function changeText(v){	
			if(v=='index'){
				$('#currentPage').val(1);pagerSubmit();
			}
			else if(v=='pre'){
				$('#currentPage').val(parseInt($('#currentPage').val())-1);pagerSubmit();
			}
			else if(v=='next'){
				$('#currentPage').val(parseInt($('#currentPage').val())+1);pagerSubmit();
			}
			else if(v=='last'){
				$('#currentPage').val(totalPage);pagerSubmit();
			}
		}
	</script>
 
		<div class="text-right adjust-pager"  >
			<ul class="pagination" id="page">
				<li  id="indexPage">
					<a href="javascript:void(0)" onclick="changeText('index')">首页</a>
				</li>
				<li id="prePage">
					<a href="javascript:void(0)" onclick="changeText('pre')">上一页</a>
				</li>
				<li id="nextPage" >
					<a href="javascript:void(0)" onclick="changeText('next')">下一页</a>
				</li>
				<li id="lastPage">
					<a href="javascript:void(0)"  onclick="changeText('last')">最后一页</a>
				</li>
			</ul>
		</div>
	</body>
</html>