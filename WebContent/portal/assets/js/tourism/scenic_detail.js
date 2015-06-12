		// Set configuration
		var contentPath = basePath_;
		
		seajs.use(['jquery', 'datepicker/bootstrap-datepicker.js'], function ($) {
		    $(function () {
		        $('#start input').datepicker({
		            format: "yyyy-mm-dd",
		            language: "zh-CN",
		            calendarWeeks: true,
		            autoclose: true,
		            todayHighlight: true
		        })
		         $(document).on('click', function(){
                $('#J_CityFloat, #e_CityFloat').siblings('.float').hide()
            })
            $('#J_CityFloat').click(function(e) {
                e.stopPropagation()
                $(this).siblings('.float').show()
            })
            $('#startcity a').click(function(event) {
                //event.preventDefault();
                var $text = $(this).text(),
                    $code = $(this).data('code');
                $(this).siblings('input:hidden').val($code)
                $('#J_CityFloat').val($text)
            });
            $('#e_CityFloat').click(function(e) {
                e.stopPropagation()
                $(this).siblings('.float').show()
            })
	            $('#destcity a').click(function(event) {
	                //event.preventDefault();
	                var $text = $(this).text(),
	                    $code = $(this).data('code');
	                $(this).siblings('input:hidden').val($code)
	                $('#e_CityFloat').val($text)
	            });
		    })
		});

		seajs.use(['avalon','jquery', 'slick/slick.js', 'Validform'], function(avalon, $) {
			
			$('#commentForm').Validform({
				tiptype: 4,
				callback: _callback,
				ajaxPost: true,
				postonce:false
			})
			function _callback(response){
				if(response.responseText=='success'){
					$("#replyContent").val("");
					_alert.show("发表评论成功，需要审核后才会显示。");
					//loadReplyList(1);
				}
				if(response.responseText=='error'){
					_alert.show("发表评论失败");
				}
				if(response.responseText=='needlogin'){
					//alert("请先登录");
					$('[data-toggle="login"]').click();
				}
			}
			avalon.define({
				$id: "view",
				showTag: true
			})

			$('.sd-tabs').children('a').on('click', function(event) {
				event.preventDefault();
				var $this = $(this), $index = $this.index();
				$this.addClass('active').siblings().removeClass('active')
				$('.sd-tab-pane').eq($index).show().siblings('.sd-tab-pane').hide()
			});

			// 顶部 图片切换
			$('#J_Slick').slick({
				autoplay:true,
				prevArrow:'.arrow-prev',
				nextArrow:'.arrow-next'
			})
		})
		
		seajs.use(["jquery", "avalon", "paginator"], function($){
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
			var replyVM = avalon.define({
				$id:'replyView',
				data:[],
				$cacheData:{}
			});
			function loadReplyList(currentPage){
				var thisCall = loadReplyList;
				$.post(basePath_+'tourism/view/loadReply', {code:viewCode_,currentPage: currentPage, pageSize: 8}, function(response){
				  	servicesPage("replyPaging", response.currentPage, response.totalPage, thisCall);
				  	replyVM.data = response.dataList;
					avalon.scan();
				}, 'json');
			}
			
			function loadData(){
				loadReplyList(1);
			}
			loadData();
			
			
			
			//控制回复框是否显示
			$(document).ready(function loadIsOpenReply(){
				$("#replyText").hide();
				$.post(basePath_+'web/replyManageController/isOpen', {type: DETAIL_VIEW_REPLY_}, function(response){
					if(response=="1"){//0关闭 1显示
						$("#replyText").show();
					}
				});
				
				
				$.post(basePath_+'tourism/view/checkGoneOrWant', {viewCode: viewCode_, type: User_View_Gone_}, function(response){
					if(response=='already_gone'){
						$("#goneBtn").addClass("active");
					}
				});
				$.post(basePath_+'tourism/view/checkGoneOrWant', {viewCode: viewCode_, type: User_View_Wanna_}, function(response){
					if(response=='already_wanna'){
						$("#wantBtn").addClass("active");
					}
				});
				
			});
			
			$(".merchantFavBtn").click(function(){
				var code = $(this).attr("code");
				var merchantType = $(this).attr("merchantType");
				var type = User_Fav_Merc_;
				var $numSpan = $(this).find("span");
				fav(code, type, merchantType, $numSpan);
			});
			$(".groupFavBtn").click(function(){
				var code = $(this).attr("code");
				var merchantType = $(this).attr("merchantType");
				var type = User_Fav_Group_;
				var $numSpan = $(this).find("span");
				fav(code, type, "no", $numSpan);
			});
			
			//初始化收藏按钮
			/*$(function(){
				//检查登录
				$.post(basePath_+"web/member/checkIslogin",{},function(response){
					if(response=="success"){
						for(var i=0;i<$(".merchantFavBtn").length;i++){
							var obj = $(".merchantFavBtn").eq(i);
							checkFavorite(obj.attr("code"),obj);
						}
						
						for(var i=0;i<$(".groupFavBtn").length;i++){
							var obj = $(".groupFavBtn").eq(i);
							checkFavorite(obj.attr("code"),obj);
						}
					}
				});
			})*/
			
			//机票
			$("#J_jipiao").click(function(){
				var chufa = $("#J_CityFloat").val();
				var daoda = $("#e_CityFloat").val();
				if(chufa&&daoda){
					$("#jipiao_form").submit();
				}else{
					_alert.show("请选择出发城市和到达城市");
				}
			});
			
		});
		//想去 去过
		function clickGoneOrWant(type,element){
			$.post(basePath_+'tourism/view/clickGoneOrWant', {viewCode: viewCode_, type: type}, function(response){
				if(response=='success'){
					//location.reload();
					element = $(element);
					var label = element.find("label");
					label.text(parseInt(label.text(),10)+1);
					element.addClass("active");
				}
				if(response=='error'){
					_alert.show("发生错误");
				}
				if(response=='need_login'){
					//alert("请先登录");
					$('[data-toggle="login"]').click();
				}
				if(response=='already_gone'){
					_alert.show("已去过");
				}
				if(response=='already_wanna'){
					_alert.show("已想去");
				}
			});
		}
		//收藏
		function fav(code, type, merchantType, $span){
			$.post(basePath_+'portal/userFavorite/saveFavorite', {code: code, type: type}, function(response){
				if(response=='success'){
					$span.parent().addClass('faving_active');
					
					$span.text(parseInt($span.text(),10)+1);
					alert("收藏成功");
					//后台统计
					if(merchantType!="no"){
						frontContentStatic(merchantType,'merchant',code,'favate');
					}
				}
				if(response=='error'){
					_alert.show("发生错误");
				}
				if(response=='need_login'){
					//alert("请先登录");
					$('[data-toggle="login"]').click();
				}
				if(response=='already_fav'){
					alert("已收藏");
				}
			});
		}
		
		//上传攻略
		function uploadStrage(){
			$.post(basePath_+"web/member/checkIslogin",{},function(response){
				if(response=="success"){
					window.location.href=basePath_+"tourism/upload/intoStragePage?sourceHref="+window.location.href;
				}else{
					//alert("请先登录");
					$('[data-toggle="login"]').click();
				}
			});
		}
		
		// 检查是否收藏
		function checkFavorite(code,favElement){
			$.post(basePath_+"portal/userFavorite/checkIsFavorite",{code:code},function(response){
				if(response=="success"){
					favElement.addClass('faving_active');
				}
			});
		}
		// 查询更多信息
		function moreInfo(ele){
			var $this = $(ele),
				_shortInfo = $this.parents(".short-info"),
				_longInfo = _shortInfo.next(".long-info");
			_shortInfo.addClass("hidden");
			_longInfo.addClass("show");
		}
	
		window._bd_share_config = {
			"common" : {
				"bdText" : viewName_, // 分享的内容
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
	
	
			function favate(merchantType,code){
				frontContentStatic(merchantType,'content',code,'favate');
			}
