<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1" />
    <%@include file="/common-html/common.jsp" %>
    <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <title>游西藏-目的地管理-目的地信息管理-目的地修改</title>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css" />
    <link rel="stylesheet" href="${ctxManage}/webuploader/webuploader.css" />
    <style>
        .bgc_r{
            background-color: #ff6a7b !important;
        }
    </style>
    <script type="text/javascript">
        $(function(){
            click();
        });
        function click(){
            //下拉列表dt的点击事件给input hidden赋值
            $(".select-base dt").click(function() {
                select_base.select(this);
                select_base.hide_click(this);
                //同时获取当前 dt的 value 值  ,然后开始绑定
                var inputValue = $(this).attr("inputValue");
                var inputId = $(this).attr("inputId");
                //.log(inputId);
                if (inputId!=undefined) {
                    $("#"+inputId).val(inputValue);
                }
            });
        }

        //表单顺序提交
        function submit(preview){
            var $this = $("#destName"),
                    thisNext = $this.next(),
                    thisVal = $this.val();
            // 执行验证并设置验证结果的状态
            if($.VLDTOR.IsArticle(thisVal) && inputRange("#destName",2,30)){
                thisNext.removeClass('errMesg').text('*');
                //验证目的地是否重复
                $.ajax({
                    type:'post',
                    data:{desName:thisVal,desCode:'${destination.code}'},
                    url:'${ctx}/web/destinationController/validDesRt',
                    success:function(data){
                        if(data=="true"){
                            valid_txtBox("#destName", false, "目的地重复", "right")	;
                            msgBox("输入的内容有误，请检查！", "erro", 2600);
                        }else{
                            valid_txtBox("#destName", true, "", "right");
                            $("#destIntro").blur();
                            $("#keyWord").blur();
                            $(".number").blur();
                            $(".shLink").blur();
                            $(".shNumber").blur();

                            // 是否上传图片
                            var hasImage = $(".url").val();
                            if(hasImage==''){
                                creatErrMesg("#pickfiles", "请至少上传一张图片", "top");
                                return ;
                            } else {
                                removeErrMesg("#pickfiles");
                            }

                            var recommend_sh = $(".recommend-sh"),
                                    recommendSh_len = recommend_sh.length;
                            // 进行遍历验证
                            for (var i = 0; i < recommendSh_len; i++) {

                                var region = recommend_sh.eq(i).find(".select-base:first"),
                                        region_val = region.children("i").text(),
                                        region_def = region_val.substr(0, 3);

                                // 商户类型未选择（为默认值）
                                if ("请选择" == region_def) {
                                    creatErrMesg(region, "请选择商户类型", 'top');
                                }
                                // 商户类型已选择
                                else {
                                    region.next(".errMesg").remove();
                                }
                            }

                            // 验证通过
                            var errMesg = $('.errMesg').length == 0;
                            if (errMesg) {
                                if(preview==1){
                                    $.ajax( {
                                        type : "post",
                                        url : "${ctx }web/destinationController/saveDestination",
                                        data : $("#form1").serialize(),
                                        dataType : "text",
                                        async : false,
                                        success : function(data) {
                                            //.log(data);
                                        }
                                    });
                                    $.ajax( {
                                        type : "post",
                                        url : "${ctx }web/destinationController/saveHotView",
                                        data : $("#form2").serialize(),
                                        dataType : "text",
                                        async : false,
                                        success : function(data) {
                                            //.log(data);
                                        }
                                    });
                                    $.ajax( {
                                        type : "post",
                                        url : "${ctx }web/destinationController/saveTravel",
                                        data : $("#form3").serialize(),
                                        dataType : "text",
                                        async : false,
                                        success : function(data) {
                                            //.log(data);
                                        }
                                    });
                                    for(var i = 4;i<=6;i++){
                                        $.ajax( {
                                            type : "post",
                                            url : "${ctx }web/destinationController/saveRecomMerchant?order="+i,
                                            data : $("#form"+i).serialize(),
                                            dataType : "text",
                                            async : false,
                                            success : function(data) {
                                                //.log(data);
                                            }
                                        });
                                    }
                                    window.location.href="${ctx }web/destinationController/getDestinationPager";
                                }else{
                                    $.ajax( {
                                        type : "post",
                                        url : "${ctx }web/homeController/previewManageImg",
                                        data : $("#form"+i).serialize(),
                                        dataType : "text",
                                        async : false,
                                        success : function(data) {
                                            //.log(data);
                                        }
                                    });

                                    window.location.href="${ctx }web/homeController/previewManageFront";
                                }
                            }
                            // 验证未通过
                            else {
                                msgBox("输入的内容有误，请检查！", "erro", 2600);
                                return false;
                            }
                        }
                    }
                });
            }else{
                thisNext.addClass('errMesg').text("名称只能为中英文和数字，且长度在2-30");
            }
        }


        //预览
        function preview(preview){
            $("#destName").blur();
            $("#destIntro").blur();
            $("#keyWord").blur();
            $(".number").blur();
            $(".shLink").blur();
            $(".shNumber").blur();

            // 是否上传图片
            var hasImage = $(".url").val();
            if(hasImage==''){
                creatErrMesg("#pickfiles", "请至少上传一张图片", "top");
                return ;
            } else {
                removeErrMesg("#pickfiles");
            }

            var contClassify = $(".contClassify"),
                    contClassify_len = contClassify.length;
            // 进行遍历验证
            for (var i = 0; i < contClassify_len; i++) {
                var region = contClassify.eq(i).find(".select-base:first"),
                        region_val = region.children("i").text(),
                        region_def = region_val.substr(0, 3);

                // 商户类型未选择（为默认值）
                if ("请选择" == region_def) {
                    if (region.next(".errMesg").length == 0) {
                        creatErrMesg(region, "请选择商户类型", "top");
                    }
                }
                // 商户类型已选择
                else {
                    region.next(".errMesg").remove();
                }
            }

            // 验证通过
            var errMesg = $('.errMesg').length == 0;
            if (errMesg) {
                if(preview==1){
                    $.ajax( {
                        type : "post",
                        url : "${ctx }web/destinationController/saveDestination?pv=1",
                        data : $("#form1").serialize(),
                        dataType : "text",
                        async : false,
                        success : function(data) {
                            //.log(data);
                        }
                    });
                    $.ajax( {
                        type : "post",
                        url : "${ctx }web/destinationController/saveHotView?pv=1",
                        data : $("#form2").serialize(),
                        dataType : "text",
                        async : false,
                        success : function(data) {
                            //.log(data);
                        }
                    });
                    $.ajax( {
                        type : "post",
                        url : "${ctx }web/destinationController/saveTravel?pv=1",
                        data : $("#form3").serialize(),
                        dataType : "text",
                        async : false,
                        success : function(data) {
                            //.log(data);
                        }
                    });
                    for(var i = 4;i<=6;i++){
                        $.ajax( {
                            type : "post",
                            url : "${ctx }web/destinationController/saveRecomMerchant?pv=1",
                            data : $("#form"+i).serialize(),
                            dataType : "text",
                            async : false,
                            success : function(data) {
                                //.log(data);
                            }
                        });
                    }
                    window.location.href="${ctx }web/destinationController/previewDes";
                }else{
                    $.ajax( {
                        type : "post",
                        url : "${ctx }web/homeController/previewManageImg",
                        data : $("#form"+i).serialize(),
                        dataType : "text",
                        async : false,
                        success : function(data) {
                            //.log(data);
                        }
                    });

                    window.location.href="${ctx }web/homeController/previewManageFront";
                }
            }
            // 验证未通过
            else {
                msgBox("输入的内容有误，请检查！", "erro", 2600);
                return false;
            }

        }


        function resetcount(_option,_destinationCode){

            $.ajax( {
                type : "post",
                url : "${ctx }web/destinationController/getrealcount",
                data : {option:_option,destinationCode:_destinationCode},
                dataType : "text",
                async : false,
                success : function(data) {
                    if(_option==1){
                        $("#want").val(data);
                    }else{
                        $("#gone").val(data);
                    }
                    msgBox("恢复成功！", "pass", 1200);
                }
            });

        }
        function rqButton(type){
            var typecode  = $("#typeCode"+type.split('pgType')[1]).val();


            $.ajax( {
                type : "post",
                url : '${ctx}/web/destinationController/previewDes?'+type+'='+typecode+"&destinationCode=${destination.code}",
                dataType : "json",
                success : function(data) {
                    var  dataHtml = '';
                    var r  = $.parseJSON(data);
                    for(var i = 0  ;i <4;i++){
                        dataHtml+= '<div style="margin-top:20px" class="formLine pgType'+type.split('pgType')[1]+'">';
                        dataHtml+= '<label class="w-auto" style="margin-left:20px">商户'+(i+1)+'链接:</label>';
                        var u  =  '';
                        var  c  = '';
                        var code  = '';
                        if(r[i]){
                            u = r[i].url;
                            c  = r[i].content;
                            code  = r[i].code;
                        }

                        dataHtml+= ' <input type="text" name="urls" class="w-320 shLink" value="'+u+'">';
                        dataHtml+= ' <label class="w-auto">或编号:</label>';
                        dataHtml+= '  <input type="text" name="codes" class="w-200 shNumber" value="'+c+'">';
                        dataHtml+='<input type="hidden" name="contentCode" value=""> ';
                        dataHtml+='</div>';
                    }
                    $("."+type).remove();
                    $(".pg"+type.split('pgType')[1]).append(dataHtml);
                    msgBox("更新成功！", "pass", 2600);
                }
            });

        }


        //想去数排序
        function orderByWan(){
            $.ajax({
                type:'post',
                data:'destinationCode=${destination.code}&order=1',
                url:'${ctx}/web/destinationController/getViewAndGone',
                dataType:'json',
                success:function(data){
                    var  r = $.parseJSON(data);
                    var dataHtml = "";
                    for(var i =0 ;i<5;i++){
                        dataHtml+='<label class="va_m lblScenName">景点'+(i+1)+':</label>';
                        dataHtml+='&nbsp;<div class="select-base">';
                        var sname  = "请选择景点";
                        if(r[i]&&r[i].viewname!='请选择景点'){
                            sname =  r[i].viewname;
                        }
                        dataHtml+='<i class="w-115">'+sname+'</i>';
                        dataHtml+='<dl>';

                        //dataHtml+='<dt  inputValue="0" inputId="viewCode'+(i+1)+'">请选择景点</dt> ';
                        for(var j =0 ;j<10;j++){
                            if(r[j]&&r[j].viewname!='请选择景点'){
                                dataHtml+='<dt  inputValue="'+r[j].viewcode+'"  inputId="viewCode'+(i+1)+'">'+r[j].viewname+'</dt>';
                            }
                        }
                        dataHtml+='</dl>';
                        dataHtml+='<input id="viewCode'+(i+1)+'" type="hidden" ';
                        var  v  =   '';
                        if(r[i]&&r[i].viewname!='请选择景点'){
                            v  =  r[i].viewcode;
                        }
                        dataHtml+=' value="'+v+'" name="viewCode" >';
                        dataHtml+='</div>&nbsp;';
                    }
                    $("#view1-5").html(dataHtml);
                    var dataHtml = "";
                    for(var i =5 ;i<10;i++){
                        dataHtml+='<label class="va_m lblScenName">景点'+(i+1)+':</label>';
                        dataHtml+='&nbsp;<div class="select-base">';
                        var sname  = "请选择景点";
                        if(r[i]&&r[i].viewname!='请选择景点'){
                            sname =  r[i].viewname;
                        }
                        dataHtml+='<i class="w-115">'+sname+'</i>';
                        dataHtml+='<dl>';

                        //dataHtml+='<dt  inputValue="0" inputId="viewCode'+i+'">请选择景点</dt> ';
                        for(var j =0 ;j<10;j++){
                            if(r[j]&&r[j].viewname!='请选择景点'){
                                dataHtml+='<dt  inputValue="'+r[j].viewcode+'"  inputId="viewCode'+(i+1)+'">'+r[j].viewname+'</dt>';
                            }
                        }
                        dataHtml+='</dl>';
                        dataHtml+='<input id="viewCode'+(i+1)+'" type="hidden" ';
                        var  v  =   '';
                        if(r[i]&&r[i].viewname!='请选择景点'){
                            v  =  r[i].viewcode;
                        }
                        dataHtml+=' value="'+v+'" name="viewCode" >';
                        dataHtml+='</div>&nbsp;';
                    }
                    $("#view6-10").html(dataHtml);
                    setCustomSelectColor();
                    msgBox("更新成功！", "pass", 2600);
                    click();

                }

            });
        }
        //想去数排序
        function orderByGone(){
            $.ajax({
                type:'post',
                data:'destinationCode=${destination.code}&order=0',
                url:'${ctx}/web/destinationController/getViewAndGone',
                dataType:'json',
                success:function(data){
                    var  r = $.parseJSON(data);
                    var dataHtml = "";
                    for(var i =0 ;i<5;i++){
                        dataHtml+='<label class="va_m lblScenName">景点'+(i+1)+':</label>';
                        dataHtml+='&nbsp;<div class="select-base">';
                        var sname  = "请选择景点";
                        if(r[i]&&r[i].viewname!='请选择景点'){
                            sname =  r[i].viewname;
                        }

                        dataHtml+='<i class="w-115">'+sname+'</i>';
                        dataHtml+='<dl>';

                        //dataHtml+='<dt  inputValue="0" inputId="viewCode'+i+'">请选择景点</dt> ';
                        for(var j =0 ;j<10;j++){
                            if(r[j]&&r[j].viewname!='请选择景点'){
                                dataHtml+='<dt  inputValue="'+r[j].viewcode+'"  inputId="viewCode'+(i+1)+'">'+r[j].viewname+'</dt>';
                            }
                        }
                        dataHtml+='</dl>';
                        dataHtml+='<input id="viewCode'+(i+1)+'" type="hidden" ';
                        var  v  =   '';
                        if(r[i]&&r[i].viewname!='请选择景点'){
                            v  =  r[i].viewcode;
                        }
                        dataHtml+=' value="'+v+'" name="viewCode" >';
                        dataHtml+='</div>&nbsp;';
                    }
                    $("#view1-5").html(dataHtml);
                    var dataHtml = "";
                    for(var i =5 ;i<10;i++){
                        dataHtml+='<label class="va_m lblScenName">景点'+(i+1)+':</label>';
                        dataHtml+='&nbsp;<div class="select-base">';
                        var sname  = "请选择景点";

                        dataHtml+='<i class="w-115">'+sname+'</i>';
                        dataHtml+='<dl>';

                        //dataHtml+='<dt  inputValue="0" inputId="viewCode'+i+'">请选择景点</dt> ';
                        for(var j =0 ;j<10;j++){
                            if(r[j]&&r[j].viewname!='请选择景点'){
                                dataHtml+='<dt  inputValue="'+r[j].viewcode+'"  inputId="viewCode'+(i+1)+'">'+r[j].viewname+'</dt>';
                            }
                        }
                        dataHtml+='</dl>';
                        dataHtml+='<input id="viewCode'+(i+1)+'" type="hidden" ';
                        var  v  =   '';
                        if(r[i]&&r[i].viewname!='请选择景点'){
                            v  =  r[i].viewcode;
                        }
                        dataHtml+=' value="'+v+'" name="viewCode" >';
                        dataHtml+='</div>&nbsp;';
                    }
                    $("#view6-10").html(dataHtml);
                    setCustomSelectColor();
                    msgBox("更新成功！", "pass", 2600);
                    click();


                }

            });
            // window.location.href="${ctx}/web/destinationController/getViewAndGone?destinationCode=${destination.code}&order=0";
        }

        function travelOrder(url){
            $.ajax({
                type:'post',
                url:url,
                dataType:'json',
                success:function(data){

                    var  dataHtml  =  "";
                    var  r  = $.parseJSON(data);
                    for(var i = 0 ;i<8;i++){
                        dataHtml  +=  '<div class="contClassify clearf clearf2">';
                        dataHtml+= '<h2 class="title bgc_r">攻略'+(i+1)+'</h2>	';
                        dataHtml+= '<div class="formLine mt15">';
                        dataHtml+= '<label class="w-auto ml20">链接:</label>';
                        var u  =  '';
                        var  c  = '';
                        var code  = '';
                        if(r[i]){
                            u = r[i].url;
                            c  = r[i].content;
                            code  = r[i].code;
                        }
                        dataHtml+= '<input type="text" name="urls" class="w-320 shLink" value="'+u+'">';
                        dataHtml+= '<label class="w-auto">或编号:</label>';

                        dataHtml+= ' <input type="text" name="code" class="w-200 shNumber" value="'+c+'" />';
                        dataHtml+= ' <input type="hidden" name="contentCode" value="'+code+'">';
                        dataHtml+= ' </div>';
                        dataHtml+= ' </div>';
                    }
                    $(".clearf2").remove();
                    $("#travelHtml").append(dataHtml);
                    msgBox("更新成功！", "pass", 2600);

                }

            });

        }

    </script>
</head>
<body>
<!-- main { -->
<div class="main">
    <!-- 页面位置-->
    <div class="location">
        <label>您当前位置为:</label>
        <span><a>游西藏</a> -</span>
        <span><a>目的地管理</a> -</span>
        <span><a href="${ctx}/web/destinationController/getDestinationPager" target="_self">目的地信息管理</a> -</span>
        <span><a href="#" target="_self">目的地修改</a></span>
    </div>

    <!-- 模块管理 { -->
    <form id="form1" action="${ctx}/web/destinationController/saveDestination"  method="post">
        <input type="hidden" name="code" value="${destination.code }">
        <input type="hidden" name="destinationSummary" value="${destination.destinationSummary }">
        <!-- 基本信息 { -->
        <div>
            <h2 class="mt40">目的地基本信息:</h2>

            <div class="filament_solid_ddd ov-au mt30">

                <div class="formLine mt20">
                    <label class="w-140">目的地名称:</label>
                    <input id="destName" type="text" class="w-260" name="destinationName" value="${destination.destinationName}"/>
                    <span class="reqItem">*</span>
                </div>

                <div class="formLine mt20 ov-hide">
                    <label class="w-140">目的地主图:</label>
                    <div id="pickfiles" class="btn-uploadImg pickfiles imgNode">请上传图片</div>
                    <span class="txt-suggest ml20">必须是1900 * 360尺寸的jpg/png格式</span>
                </div>
                <div class="formLine">
                    <label class="w-140" style="vertical-align: top;">缩略图:</label>
                    <img id="imgshow" class="desImg style-d" style="width:1200px;height:360px;"
                         <c:if test="${empty destination.destinationImage}">src="${ctxManage}/resources/img/ele/loadImg_default_a.jpg" </c:if>
                         <c:if test="${not empty destination.destinationImage }">src="${ctx}${destination.destinationImage}" </c:if>
                         title="缩略图名称" alt="请上传图片">
                    <input type="hidden" class="url" name="destinationImage" value="${destination.destinationImage}" />
                </div>

                <div class="formLine mt20 mb20">
                    <label class="w-140 float_l">目的地简介:</label>
                    <textarea name="destinationIntroduce" id="destIntro"  cols="70" rows="10" class="ml10 pl5" >${destination.destinationSummary }</textarea>
                    <span class="reqItem">*</span>
                </div>
                <div class="formLine mt10 mb10">
                    <label class="w-140">想去:</label>
                    <input id="want" type="text" class="number" value="${destination.falseWantCount }" name="falseWantCount" maxlength="7" />
                    <button type="button" class="btn-base ml20" onclick="resetcount(1,'${destination.code}')">恢复系统默认值</button>
                    <label class="w-140">去过:</label>
                    <input id="gone" type="text" class="number" value="${destination.falseGoneCount }" name="falseGoneCount"  maxlength="7" />
                    <button type="button" class="btn-base ml20" onclick="resetcount(0,'${destination.code}')">恢复系统默认值</button>
                </div>
            </div>
        </div>
        <!-- } 基本信息 -->

        <!-- 机票预订信息 { -->
        <div>
            <h2 class="mt100">目的地机票预订信息-默认到达城市:</h2>

            <div class="filament_solid_ddd ov-au mt30">
                <div class="formLine mt20 mb20">
                    <input id="las" type="radio" name="jp" value="0"
                           <c:if test="${destination.jp=='0' }">checked='checked'</c:if>
                           class="ml50 mr10"/><label for="las" class="w-auto lbl_check">拉萨贡嘎机场</label>

                    <input id="linz" type="radio" name="jp"  value="1"
                           <c:if test="${destination.jp=='1' }">checked='checked'</c:if>

                           class="ml50 mr10"/><label for="linz"  class="w-auto lbl_check">林芝米林机场</label>

                    <input id="changd" type="radio" name="jp"  value="2"
                           <c:if test="${destination.jp=='2' }">checked='checked'</c:if>

                           class="ml50 mr10"/><label for="changd" class="w-auto lbl_check">昌都邦达机场</label>

                    <input id="ali" type="radio" name="jp"  value="3"
                           <c:if test="${destination.jp=='3' }">checked='checked'</c:if>

                           class="ml50 mr10"/><label for="ali" class="w-auto lbl_check">阿里昆莎机场</label>
                    <input id="rikz" type="radio" name="jp" value="4"

                           <c:if test="${destination.jp=='4' }">checked='checked'</c:if>

                           class="ml50 mr10"/><label for="rikz" class="w-auto lbl_check">日喀则和平机场</label>

                </div>
            </div>
        </div>
        <!-- } 机票预订信息 -->

        <!-- SEO信息 { -->
        <div>
            <h2 class="mt100">页面SEO信息</h2>

            <div class="filament_solid_ddd ov-au mt30">
                <div class="formLine mt20 mb20">
                    <label class="w-auto ft-w-b ml40">&lt;Keywords&gt;标签(关键字):</label>
                    <input id="keyWord" type="text" maxlength="" class="w-320" name="keyword" value="${destination.keyword }">
                    <span class="txt-suggest ml20" style="margin-left: 20px;">为了确保搜索引擎的亲和力，请输入5个以下的关键词，每个关键词用，隔开</span>
                </div>
            </div>
        </div>
        <!-- } SEO信息 -->
    </form>


    <form id="form2" method="post" action="${ctx}web/destinationController/saveHotView">
        <input type="hidden" name="destinationCode" value="${destination.code}" />
        <input type="hidden" name="contentCode" value="${hotview.code }"/>
        <input type="hidden" name="programaCode" value="a5a2ec08-a209-46bb-a521-09b7e4139e03">
        <!-- 显示热门景点 { -->
        <div class="contClassify mt100 ov-hide hot-jd">
            <h2 class="title float_l mb30">显示热门景点</h2>
            <div class="float_r mt10 mr150">
                <button class="btn-base_min" type="button" onclick="orderByGone()">按去过人数显示</button>
                <button class="btn-base_min"  type="button" onclick="orderByWan()">按想去人数显示</button>
            </div>
            <!-- 景点选择 { -->
            <div class="clearf"  id="view1-5">
                <!-- 景点1-5{ -->
                <c:forEach var="x" begin="1" end="5" >
                    <div class="disp-ib mt20">
                        <label class="va_m lblScenName">景点${x}:</label>
                        <!-- 包含模块 -->
                        <div class="select-base">
                            <i class="w-115"><c:if test="${empty hotviewList[x-1]}">请选择景点</c:if><c:if test="${not  empty hotviewList[x-1]}"><c:if test="${empty  gw }">${hotviewList[x-1].viewName }</c:if><c:if test="${not empty  gw }">${hotviewList[x-1].viewname }</c:if></c:if></i>
                            <dl>
                                <dt inputValue="0" inputId="viewCode${x}">全部景点</dt>
                                <c:forEach var="view" items="${viewList}">
                                    <dt  inputValue="${view.code}"  inputId="viewCode${x}">${view.viewName}</dt>
                                </c:forEach>
                            </dl>
                            <input id="viewCode${x}" type="hidden"
                                   <c:if test="${empty  gw }">value="${hotviewList[x-1].code }"</c:if>
                                   <c:if test="${not empty  gw }">value="${hotviewList[x-1].viewcode }"</c:if>
                                   name="viewCode"/>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <!-- }景点1-5 -->
            <!-- 景点6-10{ -->
            <div id="view6-10">
                <c:forEach var="x" begin="6" end="10">
                    <div class="disp-ib mt20">
                        <label class="va_m lblScenName">景点${x}:</label>
                        <!-- 包含模块 -->
                        <div class="select-base">
                            <i class="w-115"><c:if test="${empty hotviewList[x-1]}">请选择景点</c:if><c:if test="${not  empty hotviewList[x-1]}"><c:if test="${empty  gw }">${hotviewList[x-1].viewName }</c:if><c:if test="${not empty  gw }">${hotviewList[x-1].viewname }</c:if></c:if></i>
                            <dl>
                                <dt inputValue="0" inputId="viewCode${x}">全部景点</dt>
                                <c:forEach var="view" items="${viewList}">
                                    <dt  inputValue="${view.code}"  inputId="viewCode${x}">${view.viewName}</dt>
                                </c:forEach>
                            </dl>
                            <input id="viewCode${x}" type="hidden"
                                   <c:if test="${empty  gw }">value="${hotviewList[x-1].code }"</c:if>
                                   <c:if test="${not empty  gw }">value="${hotviewList[x-1].viewcode }"</c:if>
                                   name="viewCode"/>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <!-- }景点6-10 -->
            <!-- } 景点选择 -->
        </div>        <!-- } 显示热门景点 -->
    </form>

    <!-- 显示精彩旅程 { -->
    <!-- 精彩旅程表单form3 -->
    <form id="form3" method="post" action="${ctx}web/destinationController/saveTravel">
        <input type="hidden" name="programaCode" value="cbffed19-bbbc-42cd-a8c9-beb96e25ed89">
        <input type="hidden" name="destinationCode" value="${destination.code}">
		
        <div class="contClassify mt20 ov-hide" id="travelHtml">
            <h2 class="title float_l mb30">显示精彩旅程</h2>
            <div class="float_r mt10 mr150">
                <button class="btn-base_min" type="button" onclick="travelOrder('${ctx}/web/destinationController/strageOrder?des=${destination.code}&travel=2')">按查看数显示</button>
                <button class="btn-base_min" type="button" onclick="travelOrder('${ctx}/web/destinationController/strageOrder?des=${destination.code}&travel=1')">按收藏数显示</button>
            </div>
            <!-- 攻略1-攻略8 {-->
            <c:forEach var="x" begin="1" end="8" step="1">
                <div class="contClassify clearf  clearf2">
                    <h2 class="title bgc_r">攻略${x}</h2>
                    <div class="formLine mt15">
                        <label class="w-auto ml20">链接:</label>
                        <input type="text" name="urls" class="w-320 shLink" value="${travel[x-1].url}">
                        <label class="w-auto">或编号:</label>
                        <input type="text" name="code" class="w-200 shNumber" value="${travel[x-1].content}" />
                        <input type="hidden" name="contentCode" value="${travel[x-1].code}">
                    </div>
                </div>
            </c:forEach>
            <!-- }攻略1-攻略8 -->
        </div>
    </form>
    <!-- } 显示精彩旅程 -->

    <!-- 显示推荐商户 { -->
    <!-- 推荐商户表单{ -->
    <div class="contClassify ov-hide">
        <h2 class="title mb30">显示推荐商户</h2>

        <!-- 商户推荐位1 { -->
        <form id="form4" method="post" action="${ctx}web/destinationController/saveRecomMerchant">
            <input type="hidden" name="programaCode" value="1d39b1a8-895d-4ded-9a36-ca22168bd176">
            <input type="hidden" name="destinationCode" value="${destination.code}">
            <input type="hidden" name="merchantType" value="recMerchant1">
            <div class="contClassify recommend-sh  pg1">
                <h2 class="title bgc_r mb20">商户推荐位1</h2>
                <!-- 类型 { -->
                <label class="va_m ml20">推荐类型:</label>
                <!-- 包含模块 -->
                <div class="select-base commer_type">
                    <i class="w-160"><c:if test="${empty type1}">请选择商户类型</c:if><c:if test="${not empty type1}">${type1}</c:if></i>
                    <dl>
                        <c:forEach var="type" items="${typeList}">
                            <dt  inputValue="${type.code}"  inputId="typeCode1">${type.name}</dt>
                        </c:forEach>
                    </dl>
                    <input id="typeCode1" type="hidden" value="${typeCod1 }" name="typeCode"/>
                </div>
                <button type="button" class="btn-base ml10" onclick="rqButton('pgType1')">按查看数显示该类商户</button>
                <c:forEach var="x" begin="1" end="4" step="1">
                    <div class="formLine mt20 pgType1">
                        <label class="w-auto ml20">商户${x}链接:</label>
                        <input type="text" name="urls" class="w-320 shLink" value="${merchant1[x-1].url}">
                        <label class="w-auto">或编号:</label>
                        <input type="text" name="codes" class="w-200 shNumber" value="${merchant1[x-1].content}">
                        <input type="hidden" name="contentCode" value="${merchant1[x-1].code}">
                    </div>
                </c:forEach>
            </div>
        </form>
        <!-- }商户推荐位1  -->

        <!-- 商户推荐位2 { -->
        <form id="form5" method="post" action="${ctx}web/destinationController/saveRecomMerchant">
            <input type="hidden" name="programaCode" value="dd12ssa8-2s5d-6ded-1a36-ct567a18bd176">
            <input type="hidden" name="destinationCode" value="${destination.code}">
            <input type="hidden" name="merchantType" value="recMerchant2">
            <div class="contClassify recommend-sh pg2">
                <h2 class="title bgc_r mb20">商户推荐位2</h2>
                <!-- 类型 { -->
                <label class="va_m ml20">推荐类型:</label>
                <!-- 包含模块 -->
                <div class="select-base commer_type">
                    <i class="w-160"><c:if test="${empty type2}">请选择商户类型</c:if><c:if test="${not empty type2}">${type2}</c:if></i>
                    <dl>
                        <c:forEach var="type" items="${typeList}">
                            <dt  inputValue="${type.code}"  inputId="typeCode2">${type.name}</dt>
                        </c:forEach>
                    </dl>
                    <input id="typeCode2" type="hidden" value="${typeCod2 }" name="typeCode"/>
                </div>
                <button type="button" class="btn-base ml10" onclick="rqButton('pgType2')">按查看数显示该类商户</button>
                <c:forEach var="x" begin="1" end="4" step="1">
                    <div class="formLine mt20 pgType2">
                        <label class="w-auto ml20">商户${x}链接:</label>
                        <input type="text" name="urls" class="w-320 shLink" value="${merchant2[x-1].url}">
                        <label class="w-auto">或编号:</label>
                        <input type="text" name="codes" class="w-200 shNumber" value="${merchant2[x-1].content}">
                        <input type="hidden" name="contentCode" value="${merchant2[x-1].code}">
                    </div>
                </c:forEach>
            </div>
        </form>
        <!-- 商户推荐位3 { -->
        <form id="form6" method="post" action="${ctx}web/destinationController/saveRecomMerchant">
            <input type="hidden" name="programaCode" value="687275ee-26a3-4b7f-bnd8a-97a16afbe39e">
            <input type="hidden" name="destinationCode" value="${destination.code}">
            <input type="hidden" name="merchantType" value="recMerchant3">
            <div class="contClassify recommend-sh  pg3">
                <h2 class="title bgc_r mb20">商户推荐位3</h2>
                <!-- 类型 { -->
                <label class="va_m ml20">推荐类型:</label>
                <!-- 包含模块 -->
                <div class="select-base commer_type">
                    <i class="w-160"><c:if test="${empty type3}">请选择商户类型</c:if><c:if test="${not empty type3}">${type3}</c:if></i>
                    <dl>
                        <c:forEach var="type" items="${typeList}">
                            <dt  inputValue="${type.code}"  inputId="typeCode3">${type.name}</dt>
                        </c:forEach>
                    </dl>
                    <input id="typeCode3" type="hidden" value="${typeCod3 }" name="typeCode"/>
                </div>
                <button type="button" class="btn-base ml10"  onclick="rqButton('pgType3')">按查看数显示该类商户</button>
                <c:forEach var="x" begin="1" end="4" step="1">
                    <div class="formLine mt20 pgType3">
                        <label class="w-auto ml20">商户${x}链接:</label>
                        <input type="text" name="urls" class="w-320 shLink" value="${merchant3[x-1].url}">
                        <label class="w-auto">或编号:</label>
                        <input type="text" name="codes" class="w-200 shNumber" value="${merchant3[x-1].content}">
                        <input type="hidden" name="contentCode" value="${merchant3[x-1].code}">
                    </div>
                </c:forEach>
            </div>
        </form>
    </div>
    <!-- } 显示推荐商户 -->
    <div class="txt-ac mt40">
        <%--<button class="btn-sure" type="button" onclick="submit(0)">预览</button>
        --%><button class="btn-sure ml100" type="button" onclick="submit(1)">保存</button>
    </div>
    <!-- } 模块管理 -->
</div><!-- } main -->

<!-- JS引用部分 -->

<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script src="${ctxManage}/webuploader/webuploader.js" type="text/javascript"></script>
<script src="${ctxManage}/webuploader/upload.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/dataValidation.js" type="text/javascript"></script>
<script type="text/javascript">
    /**** 页面加载执行 ****/
    $(function() {
        commer_type_btn(".commer_type");
    });

    /* 目的地名称验证 */
    $("#destName").blur(function () {
        var $this = $(this),
                thisNext = $this.next(),
                thisVal = $this.val();

        // 执行验证并设置验证结果的状态
        if($.VLDTOR.IsArticle(thisVal) && inputRange(this,2,30)){
            thisNext.removeClass('errMesg').text('*');
            //验证目的地是否重复
            $.ajax({
                type:'post',
                data:{desName:thisVal,desCode:'${destination.code}'},
                url:'${ctx}/web/destinationController/validDesRt',
                success:function(data){
                    if(data=="true"){
                        valid_txtBox("#destName", false, "目的地重复", "right")
                    }else{
                        valid_txtBox("#destName", true, "", "right")
                    }
                }
            });
        }else{
            thisNext.addClass('errMesg').text("名称只能为中英文和数字，且长度在2-30");
        }
    });

    /* 目的地介绍验证 */
    $("#destIntro").blur(function () {
        var $this = $(this),
                thisVal = $this.val();
        // 执行验证并设置验证结果的状态
        valid_txtBox(this, $.VLDTOR.IsArticle(thisVal) && inputRange(this,2,800), "字母、汉字、常见标点符号,且长度在2-800", "right");
    });

    /* SEO信息验证 */
    $("#keyWord").blur(function () {
        var $this = $(this),
                thisVal = $this.val();
        valid_txtBox_create(this, ($.VLDTOR.IsEnCnNum_comma(thisVal) && inputRange(this,2,20)) || thisVal == "",'只能为空或中英文、数字,且长度在2-20','right')
    });

    /* 想去和去过验证 */
    $(".number").blur(function () {
        var $this = $(this),
                thisVal = $this.val();
        valid_txtBox_create(this, $.VLDTOR.IsNum(thisVal) && inputRange(this,1,7),'只能为1-7位的正整数','top');
    });

    /* 商户链接验证 */
    $(".shLink").blur(function () {
        var thisVal = $(this).val();
        var regTest = $.VLDTOR.IsWebUrl(thisVal);
        thisValClip = thisVal.split('code=')[1];
        valid_txtBox_create(this,regTest,'不能为中文或空格,且与编号必填一项','top');
        if(regTest) {
            if (thisValClip) {
                $(this).siblings('.shNumber').val(thisValClip);
            }
            $(this).siblings('.shNumber').next('span.errMesg').remove();
        }
    });
    /* 商户编号验证 */
    $(".shNumber").blur(function () {
        var thisVal = $(this).val(),
                prevVal = $(this).siblings('.shLink').val();
        var regTest = $.VLDTOR.IsEnNum(thisVal);
        valid_txtBox_create(this,regTest || thisVal == "",'只能为数字、英文,且与连接必填一项','top');
        if(regTest){
            $(this).siblings('.shLink').next('span.errMesg').remove();
        }
    });

    /* 商户下拉框验证 */
    $(document).on('click','.contClassify .select-base dl dt', function () {
        var errMsg = $(this).parents('.select-base').next('span.errMesg');
        var errMsgLen = $(this).parents('.select-base').next('span.errMesg').length > 0;
        if(errMsgLen){
            errMsg.remove();
        }
    });

    /* 根据是否选择商户类型决定是否显示按钮 */
    $(".commer_type dt").click(function() {
        $(this).parents(".commer_type").next("button").show();
    });
    function commer_type_btn(commer_type) {
        var thisCommerType = $(commer_type),
                commer_len = thisCommerType.length;
        for(var i = 0; i < commer_len; i++) {
            var commerVal = thisCommerType.eq(i).find("i").text();
            if($.trim(commerVal) == "请选择商户类型") {
                $(commer_type).next("button").eq(i).hide();
            } else {
                $(commer_type).next("button").eq(i).show();
            }
        }
    }

</script>

<!-- 利用空闲时间预加载指定页面 -->
<link rel="prefetch"> <!-- IE10+ -->
<link rel="next"> <!-- Firefox -->
<link rel="prerender"> <!-- Chrome -->
</body>
</html>
