		
  
seajs.use(['common/css','activity/css', 'footer/css', 'slick/slick.css']);
		seajs.use(['avalon', 'slick/slick.js','roundabout/jquery.roundabout.min.js','roundabout/jquery.roundabout-shapes.min.js'], function(avalon) {
			avalon.filters.clearHTML = function(str){
	        	return str.replace(/<[^>]+>/g,"");
	     	}
			avalon.define({
				$id: "view",
				showTag: false
			})

			// slide
			var $slide = $('.ac-slide')
			var $thumb = $('.acs-thumb').children('.thumb-item')
			$slide.slick({
				autoplay:true,
				arrows: false,
				slide:'.acs-item',
				onBeforeChange: function(slide){
					setTimeout(function(){
						$thumb.eq(slide.currentSlide).addClass('active').siblings().removeClass('active')
					}, 0)
				}
			})

			$thumb.on('click', function(event) {
				event.preventDefault();
				$slide.slickGoTo($(this).index())
			})

			// 专题
			$(function(){
				$('.tp-gallery').roundabout({
					childSelector: 'div',
					btnNext: "#J_next",
					duration: 1000,
					// reflect: false,
					btnPrev: '#J_prev',
					autoplayDuration: 5000,
					minOpacity:1,
					shape: 'figure8',
				},function(){
					$(window).scrollTop(0);
				});				
			});
			
		})
		
		seajs.use(["jquery", "avalon", "paginator"], function(){
		//
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
				$.post(contentPath+'portal/activityController/getActivityIndexActivityList', {activityCode:'${activity.code }',currentPage: currentPage, pageSize: 4}, function(response){
				  	servicesPage("allActivityPage", response.currentPage, response.totalPage, thisCall);
				  	allActivityVM.data = allActivityVM.$cacheData[currentPage] = response.dataList;
				  	if(response.dataList.length==0){
				  		$("#allActivityPage").hide();
				  	}else{
				  		$("#allActivityPage").show();
				  	}
				}, 'json');
			}
			avalon.scan();
		}
		
		function loadData(){
			getAllActivityList(1);
		}
		loadData();
	});
		