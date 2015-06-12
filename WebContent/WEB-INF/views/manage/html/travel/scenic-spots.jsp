<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE HTML >
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>游西藏-游西藏首页显示-热门景点管理</title>
    <%@include file="/common-html/common.jsp" %>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css"/>
    <link rel="stylesheet" href="${ctxManage}/resources/css/travel/formWeb.css"/>
    <style>
        /* 闭合浮动 */
        .floatfix:after {
            content: "";
            display: table;
            clear: both;
        }
    </style>
    <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/plugin/respond.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        function submit1() {
            // 是否含有未选中的项
            var hasNoSelect = false;
            var classify = $(".contClassify"),
                scenSlt1 = classify.eq(0).find(".select-base").eq(0),
                scenSlt1_txt = scenSlt1.find("i").text(),
                scenSlt1_val = scenSlt1_txt.substr(0,3),
                scenSlt2 = classify.eq(0).find(".select-base").eq(1),
                scenSlt2_txt = scenSlt2.find("i").text(),
                scenSlt2_val = scenSlt2_txt.substr(0,3);

            // 第1组第1项未选择
            if(scenSlt1_val == "请选择") {
                creatErrMesg(scenSlt1, "请选择地区", "top");
            }

            // 第1组第2项未选择
            if(scenSlt2_val == "请选择") {
                creatErrMesg(scenSlt2, "请选择地区", "top");
            }

            var hasErr = $(".errMesg").length > 0;
            // 验证通过提交
            if (hasErr) {
                msgBox("至少需要选择一组地区，且第一组必需选择！", "erro", 2000);
                return false;
            } else {
                msgBox("保存成功！", "pass", 2600);
                $("#form").attr("action", "${ctx}web/travelController/saveTravelViews");
                $("#form").attr("target", "_self");
                $("#form").submit();
                return true;
            }

        }
        //预览
        function preView() {
            var formSlt = $(".contClassify .formLine"),
                    formSlt_len = formSlt.length,
                    region_pass = true,
                    destScenic_pass = true;

            // 进行遍历验证
            for (var i = 0; i < formSlt_len; i++) {
                var region = formSlt.eq(i).find(".select-base:first"),
                        destScenic = formSlt.eq(i).find(".select-base:last"),
                        region_val = region.children("i").text(),
                        region_def = region_val.substr(0, 3),
                        destScenic_val = destScenic.children("i").text(),
                        destScenic_def = destScenic_val.substr(0, 3);

                // 地区未选择（为默认值）
                if ("请选择" == region_def) {
                    if (region.next(".errMesg").length == 0) {
                        creatErrMesg(region, "请选择地区", "top");
                    }
                    // 设置状态为不通过
                    region_pass = false;
                }
                // 地区已选择
                else {
                    region.next(".errMesg").remove();
                }

                // 景点未选择
                if ("请选择" == destScenic_def) {
                    if (destScenic.next(".errMesg").length == 0) {
                        creatErrMesg(destScenic, "请选择地区", "top");
                    }
                    // 设置状态为不通过
                    destScenic_pass = false;
                }
                // 景点已选择
                else {
                    destScenic.next(".errMesg").remove();
                }
            }

            // 地区验证
            if (region_pass) {
                // 设置全局验证变量
                VLDRST.Region = true;
            } else {
                VLDRST.Region = false;
            }

            // 景点验证
            if (destScenic_pass) {
                // 设置全局验证变量
                VLDRST.DestScenic = true;
            } else {
                VLDRST.DestScenic = false;
            }

            // 验证通过提交
            if (VLDRST.Region && VLDRST.DestScenic) {
                $("#form").attr("action", "${ctx}/web/travelController/preView");
                $("#form").attr("target", "_blank");
                $("#form").submit();

                return true;
            } else {
                msgBox("还有未选择的项，请检查！", "erro", 2600);
                return false;
            }

        }
        /**
         * 加载事件
         */
        /* $(function(){
         binding();
         }); */

        /**
         * 绑定事件
         * @return
         */
        function binding() {

            // 下拉框  选择内容，并隐藏选择菜单(点击) 同时触发 绑定查询条件值
            $(".select-base dt").click(function () {
                select_base.select(this);
                select_base.hide_click(this);
                //同时获取当前 dt的 value 值  ,然后开始绑定
                var inputValue = $(this).attr("inputValue");
                var inputId = $(this).attr("inputId");
                var myMethod = $(this).attr("myMethod");
                var count = $(this).attr("count");
                if (inputId != undefined) {
                    //	$("input[name='"+inputName+"']").val(inputValue);
                    $("#" + inputId).val(inputValue);
                    value = $("#" + inputId).val();
                    //.log(value);
                    //ajax处理
                    if (myMethod) {
                        //调用自己的方法
                        var ajaxMethodString = myMethod + '("' + value + '","' + count + '")';
                        eval("(" + ajaxMethodString + ")");
                    }
                }
            });
        }


        /**
         * ajax 请求路径 查询 目的地对应的  景点
         * @param destinationCode
         * @return
         */
        function ajaxGetViewByDescode(vl, count) {
            $.ajax(
                    {
                        url: "${ctx}/web/readTibetSgPassMgController/getViewByDescode",
                        data: "destinationCode=" + vl,
                        type: 'POST',
                        success: function (data) {
                            //解析json  js
                            var viewsJSON = $.parseJSON(data);
                            var htmlViews = '';
                            $(viewsJSON).each(function (i) {
                                htmlViews = htmlViews + "<dt inputValue='" + this.code + "'  inputName='viewCode' inputId='viewCode" + count + "' >" + this.viewName + "</dt>";
                            });
                            $("#appendView" + count).html(htmlViews);
                            $(".ml-10 dt").click(function () {
                                select_base.select(this);
                                select_base.hide_click(this);
                                //同时获取当前 dt的 value 值  ,然后开始绑定
                                var inputValue = $(this).attr("inputValue");
                                var inputId = $(this).attr("inputId");
                                var myMethod = $(this).attr("myMethod");
                                var count = $(this).attr("count");
                                if (inputId != undefined) {
                                    //	$("input[name='"+inputName+"']").val(inputValue);
                                    $("#" + inputId).val(inputValue);
                                    value = $("#" + inputId).val();
                                    //.log(value);
                                    //ajax处理
                                    if (myMethod) {
                                        //调用自己的方法
                                        var ajaxMethodString = myMethod + '("' + value + '","' + count + '")';
                                        eval("(" + ajaxMethodString + ")");
                                    }
                                }
                            });
                        }
                    }
            );
        }

        function toIndex() {
            window.open("${ctx}tourism/strage/frontIndex");
        }
        //按热门景点收藏量排序
        function orderByFav() {
            $("#form").attr("action", "${ctx}/web/travelController/orderByFav").submit();
            //$("#form").attr("action", "${ctx}web/travelController/saveTravelViews");
            //$("#form").attr("target", "_self");
           // $("#form").submit();
        }

    </script>
</head>
<body>
<!-- main { -->
<div class="main">
    <!-- 页面位置-->
    <div class="location mb40">
        <label>您当前位置为:</label>
        <span><a>游西藏</a> -</span>
        <span><a href="travel.html" target="_self">游西藏首页显示</a> -</span>
        <span><a>热门景点管理</a></span>
    </div>

    <!-- 数据操作 -->
    <div class="operManage">
        <button type="button" onclick="orderByFav()" class="btn-sure">按想去数排序</button>
        <button type="button" onclick="toIndex()" class="btn-sure">查看</button>
    </div>

    <!-- 模块管理 { -->
    <div class="muduleManage filament_solid_ddd">
        <form id="form" method="post">
            <c:forEach var="x" begin="1" end="10" step="1">
                <div class="contClassify">
                    <h2 class="title">景点${x}</h2>

                    <div class="formLine mt15">
                        <label>显示景点:</label>

                        <div class="select-base destinationSelect">
                            <input id="destinationCode" type="hidden" value="${list[x-1].descode }"
                                   name="destinationCode"/>
                            <i class="w-140">请选择目的地</i>
                            <dl>
                                <dt name="">请选择目的地</dt>
                                <c:forEach var="destination" items="${destinationList}">
                                    <dt name="${destination.code}">${destination.destinationName}</dt>
                                </c:forEach>
                            </dl>
                        </div>
                        <div class="select-base ml-10 viewSelect">
                            <input id="viewCode" type="hidden" value="${list[x-1].viewcode }" name="viewCode"/>
                            <i class="w-140">请选择景点${x }</i>
                            <dl>
                                <dt name="">请选择景点</dt>
                            </dl>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </form>
    </div>
    <!-- } 模块管理 -->
    <!-- 按钮 { -->
    <div class="formLine">
        <div class="saveOper">
            <button type="button" class="save btn-sure" onclick="preView()">预览</button>
            <button type="button" class="save btn-sure ml20" onclick="submit1()">保存</button>
        </div>
    </div>
</div>
<!-- } main -->

<!-- JS引用部分 -->

<script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/base-tabel.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/plugin/jquery.rimi.validation.js" type="text/javascript"></script>
<script src="${ctxManage}/resources/js/dataValidation.js" type="text/javascript"></script>
<script type="text/javascript">

    function loadViewList($dt, destinationCode, viewCode) {
        var url = "${ctx}/web/readTibetSgPassMgController/getViewByDescode?ran=" + Math.random();
        var params = {destinationCode: destinationCode};
        $.ajax({
            url: url,
            type: 'post',
            dataType: 'json',
            data: params,
            async: false
        }).done(function (data) {
            var $viewSelect = $dt.parents(".destinationSelect").parent().find(".viewSelect");
            $viewSelect.find("input").val(viewCode);
            $viewSelect.find("i").text("请选择景点");
            $viewSelect.find("dl").html("");
            $viewSelect.find("dl").append("<dt name=''>请选择景点</dt>");
            if (data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    $viewSelect.find("dl").append("<dt name='" + data[i].code + "'>" + data[i].viewName + "</dt>");
                }
                initSelect($viewSelect, viewCode);
            }
        })

        /* $.ajax(url, params, function(data){
         console.log(data);
         var $viewSelect = $dt.parents(".destinationSelect").parent().find(".viewSelect");
         $viewSelect.find("input").val(viewCode);
         $viewSelect.find("i").text("请选择景点");
         $viewSelect.find("dl").html("");
         $viewSelect.find("dl").append("<dt name=''>请选择景点</dt>");
         if(data.length>0){
         for(var i = 0; i < data.length; i++){
         $viewSelect.find("dl").append("<dt name='" + data[i].code + "'>" + data[i].viewName + "</dt>");
         }
         initSelect($viewSelect, viewCode);
         }
         },"json"); */
    }

    $(".destinationSelect dt").click(function () {
        var val = $(this).attr("name");
        loadViewList($(this), val, "");
    });

    function initSelect($divSelect, value) {
        //var value = $divSelect.find("input").val();
        var dtValue = $divSelect.find("dt[name='" + value + "']").text();
        if (dtValue) {
            $divSelect.find("i").text(dtValue);
        }
    }

    function initDestinationSelect() {
        var divs = $(".select-base.destinationSelect");
        for (var i = 0; i < divs.length; i++) {
            var div = divs.eq(i);
            var destinationCode = div.find("input").val();
            var viewCode = div.parent().find(".viewSelect").find("input").val();
            initSelect(div, destinationCode);
            loadViewList(div.find("dt"), destinationCode, viewCode);
            /* var value = div.find("input").val();
             var dtValue = div.find("dt[name='"+value+"']").text();
             if(dtValue){
             div.find("i").text(dtValue);
             } 
             */
        }
    }

    $(function () {
        initDestinationSelect();
    });


    // 选择后消除错误提示
    $(document).on("click", ".contClassify .formLine .select-base dt", function () {
        var $this = $(this),
                errMesg = $this.parents(".select-base").next(".errMesg"),
                errMesg_len = errMesg.length;
        // 后面有错误信息
        if (errMesg_len > 0) {
            // 移除错误信息
            errMesg.remove();
        }
    });
</script>

<!-- 利用空闲时间预加载指定页面 -->
<link rel="prefetch">
<!-- IE10+ -->
<link rel="next">
<!-- Firefox -->
<link rel="prerender">
<!-- Chrome -->
</body>
</html>
