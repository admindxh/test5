<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
	String port = ":" + request.getServerPort();
	if ("80".equals(""+request.getServerPort())) {
		port = "";
	}
	String basePath = request.getScheme() + "://" + request.getServerName() + port + path + "/";
    request.setAttribute("ctx",basePath);
%>
<script type="text/javascript">
seajs.use('jquery', function(){
    /**
     * 显示天气预报
     * @param divId
     * @param areaname 地区名称 （比如lasa,chengdu,）
     * @return
     */
    function showCrWeather(divId,areaname){
        var weather= '<iframe src="http://cache.xixik.com.cn/11/'+areaname+'/" width="255" height="20" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>';
        $("#"+divId).html(weather);
    }
    function lucene(){
        $("#submit1").submit();
    }
    $(function(){
        showCrWeather("indexweather","lasa");
    });
})


function g(formname) {
var url = "http://www.baidu.com/baidu";
if (formname.s[1].checked) {
formname.ct.value = "2097152";
}
else {
formname.ct.value = "0";
}
formname.action = url;
return true;
}
</script>
<!-- 首页搜索模块 -->
<div class="container">
	<div class="row">
		<div class="col-xs-9">
			<div class="search">
				<!-- 百度站内搜索 -->
               	<jsp:include page="/common-html/search-in-baidu.jsp"></jsp:include>
               	<!-- 原始搜索功能 -->
<!-- 				<form id="searchfrm" action="${ctx}search" class="form-inline" target="_blank"> -->
<!-- 					<div class="form-group"> -->
<!-- 						<div class="input-group"> -->
<!-- 							<input id="searchkey" type="text" class="form-control" name="keywords" placeholder="请输入关键词搜索"> -->
<!-- 							<div class="input-group-addon btn-addon"> -->
<!-- 								<button> -->
<!-- 									<span class="sr-only">go</span> -->
<!-- 								</button> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 					<div class="form-group hot-search" style="font-size:12px"> -->
<!-- 						<label for="hotSearch">热门搜索：</label> -->
<!-- 						<p class="form-control-static hot-tags"> -->
<!-- 							<a  class="active" style="cursor: pointer;">林芝桃花节</a> -->
<!-- 							<a  style="cursor: pointer;">布达拉宫</a> -->
<!-- 							<a style="cursor: pointer;">骑行</a> -->
<!-- 						</p> -->
<!-- 					</div> -->
<!-- 				</form> -->
			</div>
		</div>
		<div class="col-xs-3">
			<div class="w-box" id="indexweather" style="text-align: center;width:270px;">
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
    seajs.use('jquery', function($){
        $('.hot-tags>a').on('click', function(){
            var $text = $(this).text();
            $('#searchkey').val($text);
            $('#searchfrm').submit();
        });
    });
</script>