<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common-html/frontcommon.jsp" %>


<script type="text/javascript">
seajs.use('jquery', function(){
		/**
		 * 显示天气预报
		 * @param divId
		 * @param areaname 地区名称 （比如lasa,chengdu,）
		 * @return
		 */
		 function  showCrWeather(divId,areaname){
			var  weather= '<iframe style="margin:0px auto;text-align:center;" src="http://cache.xixik.com.cn/11/'+areaname+'/" width="255" height="20" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>';
		    $("#"+divId).html(weather);
		 }
			function   lucene(){
		    	$("#submit1").submit();
		    }
		    $(function(){
		    	showCrWeather("indexweather","lasa");
			 });

})
</script>
<div class="container">
	<div class="row">
		<div class="col-xs-9">
			<div class="search">
				<form id="submit1" action="${ctx}search.html" target="_blank" method="get" class="form-inline">
					<div class="form-group">
						<div class="input-group">
							<!-- <span class="input-group-addon">搜索</span> -->
							<input type="text"  class="form-control" name="Keywords" placeholder="拉萨寺庙">
							<div class="input-group-addon btn-addon">
								<button>
									<span class="sr-only">go</span>
								</button>
							</div>
						</div>
					</div>
					<div class="form-group hot-search">
						<label for="hotSearch">热门搜索：</label>
						<p class="form-control-static hot-tags">
							<a href="#" title="西藏旅游" class="active">西藏旅游</a>
							<a href="#" title="印象西藏">印象西藏</a>
							<a href="#"  title="团队">团队</a>
							<a href="#"  title="徒步">徒步</a>
							<a href="#"  title="自驾游">自驾游</a>
							<a href="#" title="藏传佛教">藏传佛教</a>
						</p>
					</div>
				</form>
			</div>
		</div>
		<div class="col-xs-3">
			<div class="w-box" id="indexweather" style="text-align: center;">
			</div>
		</div>
	</div>
</div>