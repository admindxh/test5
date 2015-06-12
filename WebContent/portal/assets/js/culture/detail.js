		seajs.use(['common/css','footer/css']);
		seajs.use(['jquery','Validform'],function(){
			$('#commentfrm').Validform({
				tiptype: 3,
				callback: function(form){
					comment();
					return false;
				}
			})
		});
		seajs.use(['avalon', 'paginator','jquery'], function(avalon, music,$) {
			var commentModel = avalon.define({
				$id: "view",
				list:[],
				$cacheData:{}
			});
			function loadDate(currentPage){
				$.get(encodeURI(basePath_+'portal/app/culture/comments?'+Math.random()+'&code='+contentCode_+'&pageSize=8&currentPage=' + currentPage), function(rep){
					createPage(rep.currentPage, rep.totalPage)
					commentModel.list = commentModel.$cacheData[currentPage] = rep.dataList;
					$(".num").text(rep.totalCount);
				}, 'json');
			}
			$(function(){
			loadDate(1);
			avalon.scan();
			});
			window.loadDate = loadDate;
			function createPage(currentPage, totalPage){
				var options = {
			        currentPage: currentPage || 1,
			        totalPages: totalPage || 1,
		        	onPageChanged: function(e,oldPage,newPage){
// 			        	if (commentModel.$cacheData[newPage]) {
// 			        		vmodel.list = vmodel.$cacheData[newPage]
// 			        		//console.log('缓存数据')
// 			        	}else{
			        		loadDate(newPage)
				//			//console.log('异步拉取数据')
		        //		}
		        	}
		      	};
		      	$('#commentPage').bootstrapPaginator(options);
			}
		});
		function favorite(code,el)
		{
			var $el = $(el), 
   			$heart = $('.types .heart');
			if(!checkPortalUserLongin())
			{
		//		alert("请登录");
				$('[data-toggle="login"]').click();
			}
			else{ 
				$.ajax({
                cache: true,
                type: "POST",
                url:basePath_+'portal/app/culture/favorite',
                data:{code:code},
                dataType: "json", 
                error: function(error) {
                    //console.log(error);
                },
                success: function(data) {
                   if(3==data.state)
                   {
                   		alert("你没有登陆！");
                   }else if(4==data.state){
	                   	$el.addClass('star_active');
	                	alert("已收藏");
                   }else if(0==data.state)
                   {
	                	alert("收藏成功");
	           			$el.addClass('star_active');
	           			if(null==$heart.text()||""==$heart.text())
	            		{
	            			$heart.text(0);
	            		}
	             		$heart.text(parseInt($heart.text())+1);
	                    frontContentStatic(contentType_,'content',contentCode_,'favate');
	                   // window.location.reload();
                   }else
                   {
                   	
                   }
                }
            });
            }//
		}
			function praise(code, el)
			{
				var $el = $(el);
				$like = $('.types .like');
				$.ajax({
                cache: true,
                type: "POST",
                url:basePath_+'portal/app/culture/praise',
                data:{code:code},
                dataType: "json", 
                error: function(error) {
                },
                success: function(data) {
	           		if(4==data.state){
	                   	alert("已赞"); 
	           		 	$el.addClass('zan_active');
	                }else if(0==data.state)
	                {
	                   	alert("点赞成功"); 
	                	$el.addClass('zan_active');
	                	if(null==$like.text()||""==$like.text())
               			{
	                		$like.text(0);
                		}
	       				$like.text(parseInt($like.text())+1);
	                    frontContentStatic(contentType_,'content',contentCode_,'praise');
	                   // window.location.reload();
	                }else
	                {
	                }
               	}
            });
      	}
		function comment()
		{
			if(!checkPortalUserLongin())
			{
			//	alert("请登录");
				$('[data-toggle="login"]').click();
			}
			else{ 
				//console.log("登陆过了提交表单");
				$.ajax({
                cache: true,
                type: "POST",
                url:$('#commentfrm').attr('action'),
                data:$('#commentfrm').serialize(),
                dataType: "json", 
                error: function(error) {
                    //console.log(error);
                },
                success: function(data) {
                   //console.log(data);
                   if(3==data.state)
                   {
                      alert("你没有登陆！");
                   }else{
                  // loadDate(1);
                     alert("评论成功！请等待审核！");
                     $('textarea[name="content"]').val('');
                     frontContentStatic(contentType_,'content',contentCode_,'reply');
                   }
                }
            });
            }//
		}

		seajs.use([ 'avalon', 'jquery' ],function(avalon) {
			var otherModel = avalon.define({
				$id : "otherView",
				list : []
			})
			function loadOthersData(model, params) {
				$.get(encodeURI(basePath_+'portal/app/culture/others?' + params), function(rep) {
					model.list = rep.dataList;
				}, 'json');
			}
			var params1 = 'orderType=201&pageSize=8&currentPage=1&contentType='+contentType_;
			loadOthersData(otherModel, params1);
		});
	seajs.use(['jquery' ],function($) {
		$(function(){
			$.ajax({
                cache: true,
                type: "POST",
                url:basePath_+'portal/app/culture/checkpraise',
                data:{code:contentCode_},
                dataType: "json", 
                error: function(error) {
                },
                success: function(data) {
                	if(4==data.state){
    					$("#praise").addClass('zan_active');
    				}
                 }
			});
			$.ajax({
                cache: true,
                type: "POST",
                url:basePath_+'portal/app/culture/checkfav',
                data:{code:contentCode_},
                dataType: "json", 
                error: function(error) {
                },
                success: function(data) {
                	if(4==data.state){
    					$("#fav1").addClass('star_active');
    				}
                 }
			});
		});
		
	});
		window._bd_share_config = {
			"common" : {
				"bdText" : contentTitle_, // 分享的内容
				"bdDesc" : "分享的摘要", // 分享的摘要
				"bdUrl" : "", // 分享的Url地址
				"bdPic" : "" // 分享的图片
			},
			"share" : {
				"bdSize" : 24,
				"bdCustomStyle" : basePathPortal+'/assets/css/common.css'
			}
		};
		with (document)
			0[(getElementsByTagName('head')[0] || body)
					.appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='
					+ ~(-new Date() / 36e5)];
	