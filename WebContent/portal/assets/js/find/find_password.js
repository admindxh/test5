/**
 * Created by liaohuan on 2014/12/2.
 */
define(function(require, exports, module){
    var $ = require("../../../modules/jquery/jquery/1.11.1/jquery.js");
    $("#phone").on('click',function(){
        $(this).css({background:"url(../assets/icon/phone_registered_active.png) no-repeat",color:"#fff"});
        $("#mail").css({background:"url(../assets/icon/mail_registered_inactive.png) no-repeat",color:"#b5b5b5"});
        $("#mail-info").css({display:"none"});
        $("#phone-info").css({display:"block"});
        $('#Email').val('');
    });
    $("#mail").on('click',function(){
        $(this).css({background:"url(../assets/icon/mail_registered_active.png) no-repeat",color:"#fff"});
        $("#phone").css({background:"url(../assets/icon/phone_registered_inactive.png) no-repeat",color:"#b5b5b5"});
        $("#mail-info").css({display:"block"});
        $("#phone-info").css({display:"none"});
        $('#mobile').val('');
        $('#validateCode').val('');
    });
    $("#phone-next").on('click',function(){
        var mobile=$('#mobile').val();
        var vali=$('#validateCode').val();
        if(mobile==''){
            alert("电话号码不能为空");
            return;         
        }if(vali==''){
           alert("验证码不能为空");
           return;
        }
        /* $.ajax({
     			type : "post",
     			url : "/ctibet/portal/clientLog/changePage",
     			data : {mobile:mobile,findType:"byMobile",code:vali},
     			dataType : "json",
     			async : true,
     			success : function(data) {
        			 console.log(data);
        			 if(data.status=='101'){
        			    alert(data.msg);
        			 }else{
        			    location.href = "../app/find/new_password_phone.html";
        			 }
     	        }
	       })*/
	     document.forms.phForm.submit();
    });
});