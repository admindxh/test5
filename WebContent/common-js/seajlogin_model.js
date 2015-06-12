
 seajs.use(['holder']);
seajs.use(['jquery', 'artDialog/6.0.0/dialog', 'fancybox/jquery.fancybox.js', 'fancybox/jquery.fancybox.css#'], function($, dialog){
	var elem = document.getElementById('loginModal')
	var d = dialog({
		fixed: true,
		content:elem,
		padding:0
	})
	// 关闭 登录框
	$('.login-close').on('click', function(event) {
		event.preventDefault()
		//d.close()
		$.fancybox.close()
	})
	// 打开 登录框
	$('[data-toggle="login"]').fancybox({
		modal:true,
		padding:0,
		arrows:false,
		closeBtn:false,
		closeArrows: true,
		beforeShow: function(a,b){
			$('.fancybox-arrow').hide()
			$('#tlogUser, #logPass').val('')
		},
		afterShow: function(){
			$('input').placeholder()
			setTimeout(function(){
				$('#tlogUser').focus().blur()
			}, 100)
		}
	})
	$('#remember').on('click',function(){
	   if(this.checked){
         this.value="1";
       }else{
         this.value="0";
       }
	})
	$('.login-btn').on('click',function(){
	     var usname=$("#tlogUser").val();
	     var pass=$('#logPass').val();
	     var rem=$('#remember').val();
	     if(usname==''){
	       alert("请输入用户名");
	       return;
	     }
	     if(pass==''){
	       alert("请输入密码");
	       return;
	     }
	     $.ajax({
     			type : "post",
     			url : contentPath+"portal/clientLog/loginAjax",
     			data : {tlogUser:usname,logPass:pass,remember:rem},
     			dataType : "json",
     			async : true,
     			success : function(data) {
     			    if(data.status=='101'){
     			      alert(data.msg);
     			     setTimeout(function(){
     			    	 if(data.msg==='为保证数据安全请重置密码！')
      			    	{
      			    	 location.replace(contentPath+"portal/clientLog/changeInfo");
      			    	}
 					}, 3*1000);
     			     
     			    }if(data.status=='1'){
     			     // alert(window.location.href);
     			      window.location.reload();
     			    }
        		}
        })
	})
})