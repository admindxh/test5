<%@page import="javax.servlet.jsp.tagext.TryCatchFinally"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String typeId=request.getParameter("type");
	try{
	 if(typeId==null){
		 request.setAttribute("tName", "西藏文化传播");
	 }
	}catch(Exception e){
		e.printStackTrace();
	}
%>
<div class="place">
	<ul>
		<li class="location">当前位置：</li>
		<li><a href="#">读西藏</a></li>
		<li class="slipt"></li>
		<li class="active">${not empty tName ? tName : ttypeName}</li>
	</ul>
	<div class="search">
		<form action="" class="form-inline">
			<div class="form-group">
				<div class="input-group">
					<!-- <span class="input-group-addon">搜索</span> -->
					<input id="searchkey" type="text" class="form-control" name="keywords" placeholder="拉萨寺庙">
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
<script>
	seajs.use('../../assets/css/breadcrumb.css')
</script>