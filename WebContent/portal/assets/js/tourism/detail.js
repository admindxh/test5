    // Set configuration

    seajs.use('slick/slick.js', function(a) {
        $(".switchover").slick({
            autoplay:true,
            dots:true
        })})
   	 seajs.use(["jquery", "avalon", "paginator","Validform"], function($){
   		$("#formReply").Validform({
   			tiptype:3
			,
			callback:function(form){
		   		 if(checkPortalUserLongin()){
		   			$.ajax(
							{	
								 type:'post',
								  url:basePath_+"/tourism/strage/saveReply",
								  data:$("#formReply").serialize(),
								  async:true,
								  success:function(){
									  $("#content").val('');
										alert('发表成功，请等待管理员审核!');
									},
									error:function(){
										alert('发表成功,请等待管理员审核!');
		    						}
							}
				 	 ); 
					  
					  //评论成功统计 点击的评论
					  frontContentStatic(programaCode_,'content',contentCode_,'reply');
			     }else{
						
						$('[data-toggle="login"]').click();
				        return  false; 
			    }
	 	 	    return false;
			 }
   			});
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
				$.post(basePath_+'/tourism/strage/getTravelReply', {code:code_,currentPage: currentPage, pageSize:5}, function(response){
				  	servicesPage("allActivityPage", response.currentPage, response.totalPage, thisCall);
				  	allActivityVM.data = allActivityVM.$cacheData[currentPage] = response.dataList;
				  	if(response.dataList.length<=0){$("#allActivityPage").remove()}
				  	
				}, 'json');
			}
		}
		avalon.scan();
		function loadData(){
			getAllActivityList(1);
		}
		loadData();
	 });
		
		
			$(function(){
				frontContentStatic(programaCode_,'content',contentCode_,'click');
				$.ajax(
						{
						url:ctxPageUtil_+"/web/praiseController/updateViewCount?contentCode="+contentCode_,		
						data: {page:window.location.href},
						type:"POST",
						success:function(data){
							
						}
					  }
				);
				});
			$(function loadIsOpenReply(){
				$("#comment").hide();
				$.post(basePath_+'web/replyManageController/isOpen', {type: DETAIL_STRATEGY_REPLY_}, function(response){
					if(response=="1"){//0关闭 1显示
						$("#comment").show();
					}
				});
			})
