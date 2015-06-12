function  validLg(){
	
	if (isLogin_) {
        $('[data-toggle="login"]').click();
        return false;
    }
	
}	
//携程div自适应
		seajs.use(["jquery"],function(){
			$(function(){
				if($(".ctrip").find("a").length > 0) {
				$(".ctrip").css("height", "auto");
				$(".cbd").css({"padding-top": "14px", "padding-left": "17px", "padding-bottom": "4px"});
				} 
				else {
				$(".ctrip").css("height", "115px");
				$(".cbd").css({"padding-top": "18px", "padding-left": "17px", "padding-bottom": "0"});
				}
			});
		});
		// Set configuration
        var _raty;
	    seajs.use(['jquery',basePathPortal+'/modules/raty/jquery.raty.js'],function($){

            _raty  = $('.d-score span').raty({
            	score:5,
				click: function(){
            	if (isLogin_) {
                    $('[data-toggle="login"]').click();
                    return;
                }
					var $this = $(this)
					setTimeout(function(){
						var score = $this.find('input:hidden').val()
						$("#score").val(score);
					}, 100)
				},
				half: true,
				starHalf: basePathPortal+'assets/icon/m-star-half.png',
				starOff:  basePathPortal+'assets/icon/m-star-zero.png',
				starOn:   basePathPortal+'assets/icon/m-star-full.png'
			})
			
		});
		seajs.use(['common/css', 'merchant/css', 'footer/css', 'slick/slick.css', "paginator"]);
		seajs.use(basePathPortal+'/assets/css/tourism/guide.css');
		seajs.use(["jquery", "avalon", "paginator", 'slick/slick.js'], function(avalon) {
			window.$ = $;
			$('#slide').slick({
				autoplay: true,
				arrows:false,
				dots: true
			});
			
			function savePost(){
		       if(isLogin_){
		    	   $('[data-toggle="login"]').click();
		    	   return;
		       }else{
		         location.href=basePath_+'tourism/merchant/saveReply';
		       }
		    }
			
			//window.saveReply = saveReply;
		})
		function saveReply(){
	       if(!lgUser__){
	    	   $('[data-toggle="login"]').click();
	    	   return false;
	       }
	       $.post(basePath_+"/tourism/merchant/gtSaveReply",$("#form1").serialize(),function(response){
	    	   
	    	   var  result = Boolean(response);
	            if(result){
	            	 alert(response);
	                 $("#reply_area").val("");
	                 $("#score").val("5");
	                 _raty.raty('reload')
	            }else{
	            	alert('不能提交多次!');
	            }
	       });
		   //评论记录日志
		   frontContentStatic(merchantType_,'merchant',merchantCode_,'reply');   
	    }
		function orderNow(){
			frontContentStatic(merchantType_,'merchant',merchantCode_,'href');   
		}

			
			seajs.use(["jquery", "Validform"],function($){
				$(function(){
					frontContentStatic(merchantType_,'merchant',merchantCode_,'click');
				});
		
				
		        $("#form1").Validform({
		            tiptype: 3,
		            callback: function (data) {
		            	saveReply();
		            	return false;
		            }
		        })
		    });
	        seajs.use(["jquery", "avalon", "paginator"], function($){
	        	$('.show-item').on('click', function () {
	                var $big = $('.show-item.big').find('img')
	                var $src = $(this).find('img').attr('src')
	                $(this).addClass('active').siblings().removeClass('active')
	                $big.attr('src', $src)
	            })
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
					rendered: function(){
		           		$('.m-score').raty({
			       			readOnly: true,
			       			space: true,
			       			score: function() {
			       				var $this = $(this),
			       					score = $this.data('score') || ''
			       					//$this.append('&nbsp;&nbsp;<span>' + score + '</span>')
			       				return (score);
			       			},
			       			half: true,
			       			starHalf: basePathPortal+'assets/icon/star_half.png',
			       			starOff:  basePathPortal+'assets/icon/star_zero.png',
			       			starOn:   basePathPortal+'assets/icon/star_full.png'
			       		})
		            }
				});
				function getAllActivityList(currentPage){
					var thisCall = getAllActivityList;
					//判断缓存没有就请求服务器
					if(allActivityVM.$cacheData[currentPage]){
						allActivityVM.data = allActivityVM.$cacheData[currentPage];
					}else{
						$.post(basePath_+'/tourism/merchant/gtReplyInfo', {gtCode:gtdCode_,currentPage: currentPage, pageSize: 4}, function(response){
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

				//=================回复框=================
				$(function loadIsOpenReply(){
				$("#merchantReply").hide();
				$.post(basePath_+'web/replyManageController/isOpen', {type: DETAIL_TOUR_GROUP_REPLY_}, function(response){
					if(response=="1"){//0关闭 1显示
						$("#merchantReply").show();
					}
				});
			})
				
	 });
	

		
		
		window._bd_share_config = {
			"common" : {
				"bdText" : gtdName_, // 分享的内容
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
