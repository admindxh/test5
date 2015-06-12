 seajs.use([ctxPortal+'/assets/css/tourism/content_header.css', 'datepicker/datepicker.css']);
    
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
    })
       function orderAir()
		    {
		    	var x=$("#e_CityFloat").val();
		    	var m=$("#J_CityFloat").val();
		    	if(x.length>0&&m.length>0)
		    	{
		    		$('#form1').submit();
		    	}else
		    	{
		    	 alert("请选择出发和达到城市！");	
		    	}
		    	
		    }
    