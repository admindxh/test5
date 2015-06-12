/**
 * Created by liaohuan on 2014/12/1.
 */
define(function(require, exports, module){
    var $ = require("jquery");
    $(document).on('click','#phone', function(){
    })
    $("#phone").on('click',function(){
        $(this).css({background:"url(../assets/icon/phone_registered_active.png) no-repeat",color:"#fff"});
        $("#mail").css({background:"url(../assets/icon/mail_registered_inactive.png) no-repeat",color:"#b5b5b5"});
        $("#agree-on").css({display:"none"});
        $("#mail-info").css({display:"none"});
        $("#phone-info").css({display:"block"});
        $("#user-agreement").css({display:"block"});
    });
    $("#mail").on('click',function(){
        $(this).css({background:"url(../assets/icon/mail_registered_active.png) no-repeat",color:"#fff"});
        $("#phone").css({background:"url(../assets/icon/phone_registered_inactive.png) no-repeat",color:"#b5b5b5"});
        $("#agree-on").css({display:"block"});
        $("#mail-info").css({display:"block"});
        $("#phone-info").css({display:"none"});
        $("#user-agreement").css({display:"none"});
    });
    $("#register").on('click',function(){
        //$("#reg-page").css({display:"none"});
    })
});