		// Set configuration
		
		seajs.use(['common/css', 'merchant/css', 'footer/css', 'slick/slick.css']);

		seajs.use(['avalon', 'slick/slick.js'], function(avalon) {
			avalon.define({
				$id: "view",
				showTag: true,
				cls:['hotel', 'food', 'shyl', 'cxty']
			})

			$('#slide').slick({
				autoplay: true,
				arrows:false,
				dots: true
			});
		})
		
		seajs.use('jquery', function($){
		$('.hot-tags>a').on('click', function(){
			var $text = $(this).text();
			$('#keywords').val($text);
			$('#searchForm').submit();
		})
		$('.xz-bd-wrap .bd-item').hover(function(){
			$(this).addClass('hover')
		}, function(){
			$(this).removeClass('hover')
		})
	})
		
		
				function clickAderSta(aderCode,hrefUrl){
					 frontContentStatic('78abc97d5-123s-1g24-bab-0f11056a05bcc','ad_area',aderCode,'click');
					 window.open(hrefUrl);
				}
		
		    function adver(href,code){
				    frontContentStatic('78abc97d5-123s-1g24-bab-0f11056a05bcc','content',code,'click');
				    window.open(href);
			    }
