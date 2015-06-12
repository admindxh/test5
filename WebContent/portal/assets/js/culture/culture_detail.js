		// Set configuration
		seajs.use(['common/css','footer/css']);
		seajs.use(['jquery','Validform', 'raty/jquery.raty.js'],function($){
			$('#commentfrm').Validform({
				tiptype: 3,
				callback: function(form){
					comment();
					return false;
				}
			})
			$('.m-score').raty({
				readOnly: true,
				space: true,
				score: function() {
					var $this = $(this),
						score = $this.data('score') || ''
						$this.append('&nbsp;&nbsp;<span>' + score + '</span>')
					return (score);
				},
				half: true,
				starHalf: basePathPortal+'assets/icon/star_half.png',
				starOff: basePathPortal+'assets/icon/star_zero.png',
				starOn: basePathPortal+'assets/icon/star_full.png'
				
			})
			
			$('.d-score').raty({
				click: function(score){
					var _this = $(this)
					setTimeout(function(){
						var _score = _this.find('input:hidden').val()
						grade(contentCode_,_score);
					}, 100);
				},
				half: true,
				starHalf: basePathPortal+'assets/icon/m-star-half.png',
				starOff: basePathPortal+'assets/icon/m-star-zero.png',
				starOn: basePathPortal+'assets/icon/m-star-full.png'
			})
			
		});
		seajs.use(['avalon','player', 'paginator','jquery','Validform', 'ion.rangeSlider/js/ion.rangeSlider.js'], function(avalon, music) {
			var commentModel = avalon.define({
				$id: "view",
				list:[],
				$cacheData:{}
			})
			music.init();
			$(".J_irs").ionRangeSlider({
				min: 0,
		    	max: 100,
		    	hide_min_max: true,
		    	hide_from_to: true,
		    	disable: true,
		    	onChange: function (data) {
		    		$('#music_player_0').jPlayer("playHead", data.from)
		    		// console.log(attr_mid, data.from)
			    }
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
			        		loadDate(newPage);
		        	}
		      	}
		      	$('#commentPage').bootstrapPaginator(options)
			}
		});

		function comment() {
			if (!checkPortalUserLongin()) {
				alert("请登录");
				$('[data-toggle="login"]').click();
			} else {
			//	//console.log("登陆过了提交表单");
				$.ajax({
					cache : true,
					type : "POST",
					url : $('#commentfrm').attr('action'),
					data : $('#commentfrm').serialize(),
					dataType : "json",
					error : function(error) {
			//			//console.log(error);
					},
					success : function(data) {
			//			//console.log(data);
						if (3 == data.state) {
							alert("你没有登录，请登录！");
							$('[data-toggle="login"]').click();
						} else {
							loadDate(1);
							alert("评论成功！请等待审核！");
							$('textarea[name="content"]').val('');
							frontContentStatic('2000',
									'content', contentCode_, 'reply');
						}
					}
				});
			}//
		}

		function favorite(code,el) {
			var $el = $(el), 
   			$heart = $('.types .heart');
			if (!checkPortalUserLongin()) {
				//alert("请登录");
				$('[data-toggle="login"]').trigger('click');
			} else {
				$.ajax({
					cache : true,
					type : "POST",
					url : basePath_+'portal/app/culture/favorite',
					data : {
						code : code
					},
					dataType : "json",
					error : function(error) {
				//		//console.log(error);
					},
					success : function(data) {

						//       //console.log(data);
						if (3 == data.state) {
							alert("你没有登录，请登录！");
							$('[data-toggle="login"]').trigger('click');
						} else if (4 == data.state) {
							alert("你已收藏过了");
							$el.addClass('star_active');
						} else if (0 == data.state) {
							alert("收藏成功！");
							$el.addClass('star_active');
		             		$heart.text(parseInt($heart.text())+1);
							frontContentStatic('2000',
									'content', contentCode_, 'favate');
						//	window.location.reload();
						} else {

						}

					}
				});
			}//
		}

		function praise(code,el) {
			var $el = $(el), 
			$like = $('.types .like');
			
			$.ajax({
				cache : true,
				type : "POST",
				url : basePath_+'portal/app/culture/praise',
				data : {
					code : code
				},
				dataType : "json",
				error : function(error) {
			//		//console.log(error);
				},
				success : function(data) {
					if (4 == data.state) {
						$el.addClass('zan_active');
						alert("你已赞过了");
					} else if (0 == data.state) {
						alert("赞成功！");
					$el.addClass('zan_active');
       				$like.text(parseInt($like.text())+1);
						frontContentStatic('2000', 'content',
								contentCode_, 'praise');
						//window.location.reload();
					} else {
					}
				}
			});
		}
		function grade(code,score)
		{
			if (!checkPortalUserLongin()) {
				//alert("请登录");
				$('[data-toggle="login"]').click();
			} else {
				$.ajax({
		        cache: true,
		        type: "POST",
		        url:basePath_+'portal/app/culture/grade',
		        data:{code:code,score:score},
		        dataType: "json", 
		        error: function(error) {
		            //console.log(error);
		        },
		        success: function(data) {
		   		if(4==data.state){
		           	alert("已评分"); 
		           }else if(0==data.state)
		           {
		            alert("评分成功！");
		            window.location.reload();
		           }else{
		        	   
		           }
		        }
		     });
		   }
	}
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
	
		window.onload =function(){
			frontContentStatic('2000','content',contentCode_,'click');
		}
			seajs.use(['avalon', 'jquery'], function(avalon) {
			var otherModel = avalon.define({
				$id: "otherView",
				list:[]			
			})
			function loadOthersData(model,params)
			{
				$.get(encodeURI(basePath_+'portal/app/culture/others?'+params), function(rep){
					model.list= rep.dataList;
				}, 'json');
			}
			var params1='pageSize=8&currentPage=1&contentType='+contentType_+'&orderType=201';
			loadOthersData(otherModel,params1);
		});
		window._bd_share_config = {
			"common" : {
				"bdText" : contentTitle_, // 分享的内容
				"bdDesc" : "分享的摘要", // 分享的摘要
				"bdUrl": "", // 分享的Url地址
				"bdPic": "" // 分享的图片
			},
			"share" : {
				"bdSize" : 24,
				"bdCustomStyle": basePath_ + '/assets/css/common.css'
			}
		};
		with (document)
			0[(getElementsByTagName('head')[0] || body)
					.appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='
					+ ~(-new Date() / 36e5)];
