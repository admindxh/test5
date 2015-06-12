<%@ page language="java"  contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jstl/fmt_rt" %>   
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    String path = request.getContextPath().trim().equals("/")?request.getContextPath():request.getContextPath()+"/";
    String port  =  ":"+request.getServerPort() ;
    if("80".equals( ""+request.getServerPort())){
        port = ""  ;
    }
    String basePath = request.getScheme()+"://"+request.getServerName()+port+path;
    String basePathIndex = request.getScheme()+"://"+request.getServerName()+port+request.getContextPath();
    request.setAttribute("ctx",basePath);
    request.setAttribute("ctxIndex",basePathIndex);
    request.setAttribute("ctxManage",basePath+"/manage/");
    request.setAttribute("ctxPortal",basePath+"portal/");//xiangwq
    String url  = request.getServletPath();
    request.setAttribute("ctxMRead", basePath+"manage/html/read/");//csl
    request.setAttribute("ctxApp",basePath+"portal/app/");
    request.setAttribute("pageurl",basePath+url);
    request.setAttribute("root","/");
%>
<script src="${ctxPortal}assets/js/Alert.js"></script>
<script>
	var contentPath = "${ctx}";
	/**
	 *  js 中获取会员用户
	 * @return
	 */
	function getFrontUser(){
		var  user  = "${logUser}";
		return user;
	}
	/**
	 * 判断当前会员用户是否登录
	 * @return
	 */
	function checkPortalUserLongin(){
		var  user  = "${logUser}";
		if (user) {
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 判断当前电脑是否登录  首页
	 * @return
	 */
	function isRecored(code,vs){
		$.ajax({
            url:"${ctx}/web/praiseController/isRecored",
            data:"code="+code,
            type: 'POST',
            success:function(data){
                if(data=="true"){
                     $(vs).addClass("zan_active");
                     //$(vs).removeAttr('onclick');
                    // $(vs).find("img").css("backgroundImage","url(${ctx}/portal/assets/icon/zan_gray.png)");
                    alert("已赞");
                }else{
                    clickPraiseFront(code,vs);
                    if($(vs).attr('indexSgType')){
                        var  ptye  =  $(vs).attr('indexSgType');
                        frontContentStatic(ptye,"content",code,"praise");
                    }else{
                        frontContentStatic('${content.programaCode}','content','${content.code}','praise');
                    }
                    $(vs).addClass("zan_active");
                    //$(vs).removeAttr('onclick');
                    alert("点赞成功");
                }
            }}
	    )
    }
	/**
	 * 点击赞
	 * @return
	 */
	function clickPraiseFront(code,vs){
		$.ajax({
            url:"${ctx}/web/praiseController/clickPraiseFront",
            data:"code="+code,
            type: 'POST',
            success:function(data){
                $(vs).parent().find("label").html(data);
                if($(vs).parent().find("label")){
                    frontContentStatic('tssq','conetnt',code,'praise');
                }
                $(vs).parent().find("p").html(data);
                //攻略页面特殊位置
                $("#praiseCount").html(data);
            }}
	    )
    }
	/**
	 * 点击 浏 览
	 * @return
	 */
	function clickViewCount(code){
		$.ajax({
            url:"${ctx}/web/praiseController/clickViewCount",
            data:"code="+code,
            type: 'POST',
            success:function(data){}}
	    )
    }
	/**
	 * 判断当前电脑是否登录 收藏
	 * @return
	 */
	function isRecoredFavate(code,vs){
		if(!checkPortalUserLongin()){
            // 打开 登录框
            $('[data-toggle="login"]').click();
            return ;
        }
        $.ajax({
            url:"${ctx}/web/praiseController/isRecoredFavate",
            data:"code="+code,
            type: 'POST',
            success:function(data){
                if(data=="true"){
                    //表示改电脑已经点收藏了
                    $(vs).addClass("star_active");
                    alert("已收藏");
                }else{
                    clickFavateFront(code,vs);
                    $(vs).addClass("star_active");
                    frontContentStatic('${content.programaCode}','content','${content.code}','favate');
                    alert("收藏成功");
                }
            }}
        )
    }
	/**
	 * 点击收藏
	 * @return
	 */
	function clickFavateFront(code,vs){
		$.ajax({
            url:"${ctx}/web/praiseController/clickFavateFront",
            data:"code="+code,
            type: 'POST',
            success:function(data){
                //攻略页面特殊位置
                $("#favateCount").html(data);
            }}
	    )
    }
	/**
	 * 商户  判断当前电脑是否登录 收藏
	 * @return
	 */
	function isRecoredMerchantFavate(code,vs,flag){
        if(!checkPortalUserLongin()){
            // 打开 登录框
            $('[data-toggle="login"]').click();
            return ;
        }
        $.ajax({
            url:"${ctx}/web/praiseController/isRecoredMerchantFavate",
            data:"code="+code,
            type: 'POST',
            success:function(data){
                if(data=="true"){
                    //表示改电脑已经点收藏了
                    flag !="min"? $(vs).addClass("star_active"):$(vs).addClass("star_min_active");
                    alert("已收藏");
                }else{
                    alert("收藏成功");
                    clickFavateMerchantFront(code,vs);
                    flag !="min"? $(vs).addClass("star_active"):$(vs).addClass("star_min_active");
                    frontContentStatic($(vs).attr('merchantType'),'merchant',code,'favate');
                }
            }}
        )
    }
	/**
	 * 商户  点击收藏
	 * @return
	 */
	function clickFavateMerchantFront(code,vs){
		$.ajax({
            url:"${ctx}/web/praiseController/clickFavateMerchantFront",
            data:"code="+code,
            type: 'POST',
            success:function(data){
                //攻略页面特殊位置
                $(vs).next('label').html(data);
                if($("#heart")){
                    $("#heart").html(data);
                }
            }}
	    );
    }
    /**
	 * 商户  判断当前电脑是否登录 收藏
	 * @return
	 */
	function isRecoredMerchantFavatexz(code,vs,flag){
		if(!checkPortalUserLongin()){
            // 打开 登录框
            $('[data-toggle="login"]').click();
            return ;
        }
		$.ajax({
            url:"${ctx}/web/praiseController/isRecoredMerchantFavate",
            data:"code="+code,
            type: 'POST',
            success:function(data){
                if(data=="true"){
                    //表示改电脑已经点收藏了
                    flag !="min"? $(vs).addClass("star_active"):$(vs).addClass("star_min_active");
                    alert("已收藏");
                }else{
                    clickFavateMerchantFrontxz(code,vs);
                    flag !="min"? $(vs).addClass("star_active"):$(vs).addClass("star_min_active");
                    frontContentStatic($(vs).attr('merchantType'),'merchant',code,'favate');
                    alert("收藏成功");
                }
            }}
	    )
    }
	/**
	 * 商户  点击收藏
	 * @return
	 */
	function clickFavateMerchantFrontxz(code,vs){
		$.ajax({
            url:"${ctx}/web/praiseController/clickFavateMerchantFront",
            data:"code="+code,
            type: 'POST',
            success:function(data){
                //攻略页面特殊位置
                $(vs).next('label').html(data);
                if($("#heart")){
                    $("#heart").html(data);
                }
            }}
	    )
    }
   
</script>
