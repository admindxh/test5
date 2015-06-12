<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String port = ":" + request.getServerPort();
if ("80".equals(""+request.getServerPort())) {
	port = "";
}
String basePath = request.getScheme() + "://"
		+ request.getServerName() + port + path + "/";
request.setAttribute("ctx",basePath);
request.setAttribute("ctxManage",basePath+"/manage/");
request.setAttribute("ctxPortal",basePath+"/portal/"); //xiangwq 
request.setAttribute("ctxMRead", basePath+"manage/html/read/");//csl
request.setAttribute("ctxApp",basePath+"portal/app/"); //
request.setAttribute("root","/");
%>
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
			<div class="w-box">
				<span class="city">拉萨</span>
				<span class="today">今日 天气</span>
				<div class="weather">
					<img src="${ctxPortal}/assets/icon/cloud.png" alt="cloud">/
					<img src="${ctxPortal}/assets/icon/moon.png" alt="moon">
				<span>19/-1°C</span>
				</div>
			</div>
		</div>
	</div>
</div>