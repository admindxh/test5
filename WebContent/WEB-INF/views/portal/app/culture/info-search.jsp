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
		<li><a href="${ctx}culture">读西藏</a></li>
		<li class="slipt"></li>
		<li class="active">${not empty tName ? tName : ttypeName}</li>
	</ul>
	<div class="search">
			<div class="form-group">
				<div class="input-group">
					<!-- <span class="input-group-addon">搜索</span> -->
					<input id="searchkey" type="text" class="form-control" maxlength="15" name="keywords" value="${keywords}" placeholder="拉萨寺庙">
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
	</div>
</div>
<script>
	seajs.use('${ctxPortal}/assets/css/breadcrumb.css')
	seajs.use('jquery', function($){
		$('.hot-tags>a').on('click', function(){
			var $text = $(this).text()
			$('#searchkey').val($text)
			$('#frm').submit()
		})
	})
</script>