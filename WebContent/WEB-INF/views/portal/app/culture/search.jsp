<%@ page language="java" import="java.util.*" import="com.rimi.ctibet.domain.Content" pageEncoding="utf-8"%>
<script type="text/javascript">
seajs.use('jquery', function(){
		/**
		 * 显示天气预报
		 * @param divId 
		 * @param areaname 地区名称 （比如lasa,chengdu,）
		 * @return
		 */
		function  showCrWeather(divId,areaname){
			var  weather= '<iframe src="http://cache.xixik.com.cn/11/'+areaname+'/" width="255" height="20" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>';
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

<!-- 首页搜索模块 -->
<div class="container">
	<div class="row">
		<div class="col-xs-9">
			<div class="search">
				<form action="" class="form-inline">
					<div class="form-group">
						<div class="input-group">
							<!-- <span class="input-group-addon">搜索</span> -->
							<input type="text" class="form-control" name="query" placeholder="拉萨寺庙">
							<div class="input-group-addon btn-addon">
								<button>
									<span class="sr-only">go</span>
								</button>
							</div>
						</div>
					</div>
					<div class="form-group hot-search" style="font-size:12px">
						<label for="hotSearch">热门搜索：</label>
						<p class="form-control-static hot-tags">
							<a href="#" class="active">西藏旅游</a>
							<a href="#">印象西藏</a>
							<a href="#">团队</a>
							<a href="#">徒步</a>
							<a href="#">自驾游</a>
							<a href="#">藏传佛教</a>
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