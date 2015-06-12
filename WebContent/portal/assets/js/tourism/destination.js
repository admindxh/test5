		 seajs.use([basePathPortal+'/assets/css/tourism/content_header.css', 'datepicker/datepicker.css']);
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
		            
		          //检查登录
					$.post(basePath_+"web/member/checkIslogin",{},function(response){
						if(response=="success"){
							for(var i=0;i<$(".loving").length;i++){
								var obj = $(".loving").eq(i);
								checkFavorite(obj.attr("code"),obj);
							}
							
						}
					});
		            
		        })
		        
		     	// 检查是否收藏
				function checkFavorite(code,favElement){
					$.post(basePath_+"portal/userFavorite/checkIsFavorite",{code:code},function(response){
						if(response=="success"){
							favElement.find("button").addClass('star_active');
						}
					});
				}
		        
		    })
		 
		    
