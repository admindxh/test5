<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
	<div class="xz-box">
		<div class="xz-heading">
			<a href="${ctx}culture/cultural"><h2 class="index-culture"><span class="sr-only">西藏文化传播</span></h2></a>
			<div class="clt-other">
				<a href="javascript:;" class="active">音乐</a>
				<a href="javascript:;">小说</a>
				<a href="javascript:;">游戏</a>
				<a href="${ctx}culture/cultural" target="_blank">更多</a>
			</div>
		</div>
		<div class="music clearfix">
			 <c:forEach var="list" items="${list1.dataList}" >
				<div class="music-bd">
						<a target="_blank" href="${ctx }${list.url}" class="music-photo">
							<img src="${ctx}${list.title}" alt="${list.contentTitle}" style="width: 130px;height: 130px;border-radius: 8px;">
							<div class="mp-mark"></div>
						</a>
						<a target="_blank" href="${ctx }${list.url}" ><p class="music-name">${list.contentTitle}</p></a>
						<p class="music-singer">${list.authorCode }</p>
						<p class="m-score" data-score="${empty list.others.score?'0.0':list.others.score}" data-readonly="true"></p>
			   </div>				
			
			</c:forEach>
		</div>
		<div class="music clearfix" style="display: none;">
				
			 <c:forEach items="${list2.dataList}" var="list">
				<div class="music-bd">
						<a target="_blank" href="${ctx }${list.url}" class="music-photo">
								<!-- title 图片途径 -->
							<img src="${ctx}/${list.title}" alt="${list.contentTitle}" style="width: 130px;height: 130px;border-radius: 8px;">
<!-- 							<div class="mp-mark"></div> -->
						</a>
						<a target="_blank" href="${ctx }${list.url}" ><p class="music-name">${list.contentTitle}</p></a>
						<p class="music-singer">${list.authorCode }</p>
						<p class="m-score" data-score="${empty list.others.score?'0.0':list.others.score}" data-readonly="true"></p>
			   </div>				
			</c:forEach>
		</div>
		<div class="music clearfix"  style="display: none;">
			 <c:forEach items="${list3.dataList}" var="list">
				<div class="music-bd">
						<a target="_blank" href="${ctx }${list.url}" class="music-photo">
								<!-- title 图片途径 -->
							<img src="${ctx}/${list.title}" alt="${list.contentTitle}">
<!-- 							<div class="mp-mark"></div> -->
						</a>
						<a target="_blank" href="${ctx }${list.url}" ><p class="music-name">${list.contentTitle}</p></a>
						<p class="music-singer">${list.authorCode }</p>
						<p class="m-score" data-score="${empty list.others.score?'0.0':list.others.score}" data-readonly="true"></p>
			   </div>				
			
			</c:forEach>
		</div>
	
	</div>
</div>
