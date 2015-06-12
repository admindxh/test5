		seajs.use(['common/css','footer/css', 'slick/slick.css']);
		seajs.use(['jquery','validform'],function($){
			$('#commentfrm').Validform({
				tiptype: 3,
				callback: function(form){
					comment();
					return false;
				}
			})
		});
		seajs.use(['avalon', 'slick'], function(avalon) {
			var playModel = avalon.define({
				$id: "view",
				showTag: false
			})
			// 幻灯片
			// 大图
			$('.picture-inner').slick({
				slidesToShow: 1,
		    slidesToScroll: 1,
		    prevArrow:'.prev-page',
		    nextArrow: '.next-page',
		    fade: true,
		    infinite: false,
		    asNavFor: '.thumbs',
		    onInit: function(slide){
		   //	console.log(slide)
		    	var _slideCount = slide.slideCount,
		    		$prevArrow = $('#J_prevArrow'),
		    		$nextArrow = $('#J_nextArrow');
		    		var prev_href = $prevArrow.data('href')
	    			if (prev_href) {
	    				$prevArrow.after('<a href="' + prev_href + '" title="上一篇" id="J_PrevPage" class="prev-page"></a>').hide()
	    			};
	    			if (_slideCount === 1) {
		    			var next_href = $nextArrow.data('href')
		    			if (next_href) {
		    				$nextArrow.after('<a href="' + next_href + '" title="下一篇" id="J_NextPage" class="next-page"></a>').hide()
		    			};
		    		};
		    },
		    onBeforeChange: function(slide){
		    	var _slideCount = slide.slideCount,
		    		$prevArrow = slide.$prevArrow,
		    		$nextArrow = slide.$nextArrow;
		    	setTimeout(function(){
		    		var _index = slide.currentSlide;
		    		$('.thumbs').find('.thumb[index="' + _index + '"]')
		    		.addClass('slick-current')
		    		.siblings('.thumb').removeClass('slick-current')
		    		if (_index === 0) {
		    			var prev_href = $prevArrow.data('href')
		    			if (prev_href) {
		    				$prevArrow.after('<a href="' + prev_href + '" title="上一篇" id="J_PrevPage" class="prev-page"></a>').hide()
		    			};
		    		}

		    		if (_index === (_slideCount-1)) {
		    			var next_href = $nextArrow.data('href')
		    			if (next_href) {
		    				$nextArrow.after('<a href="' + next_href + '" title="下一篇" id="J_NextPage" class="next-page"></a>').hide()
		    			};
		    		};

		    		if (_index > 0 && _index < (_slideCount-1)) {
		    			$('#J_PrevPage, #J_NextPage').remove()
		    			$prevArrow.show();
		    			$nextArrow.show();
		    		}
		    	}, 0)
		    }
			})
			$('.thumbs').slick({
				slidesToShow: 4,
				slidesToScroll: 1,
		    infinite: false,
		    prevArrow:'.thumb-prev',
		    nextArrow:'.thumb-next'
			})
			$('.thumbs').on('click', '.thumb', function(event) {
				var $index = $(this).index()
				$('.picture-inner').slickGoTo($index)
			});
		})
	seajs.use(['jquery','avalon', 'paginator'], function($,avalon) {
		var commentModel = avalon.define({
			$id: "commentView",
			list:[],
			$cacheData:{}
		});
		function loadDate(currentPage){
			$.get(encodeURI(basePath_+'portal/app/discover/comments?'+Math.random()+'&code='+mutiCode_+'&pageSize=8&currentPage=' + currentPage), function(rep){
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
		        		loadDate(newPage);
	        	}
	      	};
	      	$('#commentPage').bootstrapPaginator(options);
		}
	});
		function comment(){
		if(!checkPortalUserLongin())
		{
		//	alert("请登陆");
			$('[data-toggle="login"]').click();
		}
		else{ 
			$.ajax({
       		 cache: true,
        	type: "POST",
        	url:$('#commentfrm').attr('action'),
       		data:$('#commentfrm').serialize(),
            dataType: "json", 
            error: function(error) {
            ////console.log(error);
        },
        success: function(data) {
         //  //console.log(data);
           if(3==data.state)
           {
           alert("你没有登录！");
           }else{
           loadDate(1);
           	 alert("评论成功！请等待审核！");
           	$('textarea[name="content"]').val('');
           	 frontContentStatic('14dba551-cb5b-4631-b5ef-b3838670b3a9','content',mutiCode_,'reply');
           }
        }
    });
    }//
}

		window._bd_share_config = {
			"common" : {
				"bdText" : mutiName_, // 分享的内容
				"bdDesc" : "分享的摘要", // 分享的摘要
				"bdUrl": "", // 分享的Url地址
				"bdPic": "" // 分享的图片
			},
			"share" : {
				"bdSize" : 24,
				"bdCustomStyle": basePathPortal+'/assets/css/common.css'
			}
		};
		with (document)
			0[(getElementsByTagName('head')[0] || body)
					.appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='
					+ ~(-new Date() / 36e5)];
		function favorite(imgCode, mutiCode,el) {
			var $el = $(el), 
			$heart = $('.types .heart');
			if (!checkPortalUserLongin()) {
			//	alert("请登录");
				$('[data-toggle="login"]').click();
			} else {
				$.ajax({
					cache : true,
					type : "POST",
					url : basePath_+'/portal/frontMutiController/favorite',
					data : {
						imgCode : imgCode,
						mutiCode : mutiCode
					},
					dataType : "json",
					error : function(error) {
						//console.log(error);
					},
					success : function(data) {
						if (3 == data.state) {
						//	alert("你没有登录，请登录！");
							$('[data-toggle="login"]').click();
						} else if (4 == data.state) {
						$(".fav1").addClass('star_active');
							alert("你已收藏过了");
						} else if (0 == data.state) {
							alert("收藏成功！");
							$(".fav1").addClass('star_active');
		      				$heart.text(parseInt($heart.text())+1);
							frontContentStatic(
									'14dba551-cb5b-4631-b5ef-b3838670b3a9',
									'content', mutiCode_, 'favate');
						//	window.location.reload();
						} else {

						}
					}
				});
			}//
		}
		
	function praise(imgCode,mutiCode,el) {
		var $el = $(el), 
		$like = $('.types .like');
			$.ajax({
				cache : true,
				type : "POST",
				url : basePath_+'/portal/frontMutiController/praise',
				data : {
					imgCode : imgCode,
					mutiCode:mutiCode
				},
				dataType : "json",
				error : function(error) {
					//console.log(error);
				},
				success : function(data) {
					if (4 == data.state) {
						alert("已赞!");
						$(".like1").addClass('zan_active');
					} else if (0 == data.state) {
						alert("赞成功！");
						$(".like1").addClass('zan_active');
			       		$like.text(parseInt($like.text())+1);
						frontContentStatic('14dba551-cb5b-4631-b5ef-b3838670b3a9','content',mutiCode_,'praise'); 
					//	window.location.reload();
					} else {
						
					}
				}
			});
		}
	seajs.use(['jquery' ],function($) {
		$(function(){
			$.ajax({
                cache: true,
                type: "POST",
                url:basePath_+'portal/app/discover/checkpraise',
                data:{code:mutiCode_},
                dataType: "json", 
                error: function(error) {
                },
                success: function(data) {
                	if(4==data.state){
    					$(".like1").addClass('zan_active');
    				}
                 }
			});
			$.ajax({
                cache: true,
                type: "POST",
                url:basePath_+'portal/app/discover/checkfav',
                data:{code:mutiCode_},
                dataType: "json", 
                error: function(error) {
                },
                success: function(data) {
                	if(4==data.state){
    					$(".fav1").addClass('star_active');
    				}
                 }
			});
		});
		
	});
	
	seajs.use(['jquery'],function($){
			$(function(){
				frontContentStatic('14dba551-cb5b-4631-b5ef-b3838670b3a9','content',mutiCode_,'click');
				});
	});
