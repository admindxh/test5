/**
 * Created by liaohuan on 2014/12/5.
 */
define(function(require,exports,module){
    var $ = require('jquery');
    $("#apply-btn").on('click',function(){
            $("#shielding-layer").addClass('dis-show').removeClass('dis-hidden');
            $("#apply").addClass('dis-show').removeClass('dis-hidden');
    });
    $("#apply-close").on('click',function(){
        $("#shielding-layer").addClass('dis-hidden').removeClass('dis-show');
        $("#apply").addClass('dis-hidden').removeClass('dis-show');
    });
    $("#production-btn").on('click',function(){
        $("#shielding-layer").addClass('dis-show').removeClass('dis-hidden');
        $("#production").addClass('dis-show').removeClass('dis-hidden');
    });
    $("#production-close").on('click',function(){
        $("#shielding-layer").addClass('dis-hidden').removeClass('dis-show');
        $("#production").addClass('dis-hidden').removeClass('dis-show');
    });

    /* 报名付款 */
    $("#sign-up .confirm").on('click',function(){
        $("#sign-up").addClass('dis-hidden').removeClass('dis-show');
        $("#pay-money").addClass('dis-show').removeClass('dis-hidden');
    });
    $(".off").on('click',function(){
        var thisparent = $(this).parents('.sign-up');
        thisparent.addClass('dis-hidden').removeClass('dis-show');
    });
});