<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common-html/mainfrontcommon.jsp" %>
<div class="container">
	<div class="xz-box">
		<div class="xz-heading">
			<a href="${ctx }culture" target="_blank"><h2 class="duxz"><span class="sr-only">读西藏</span></h2></a>
			<div class="index-more"><a href="${ctx }culture" target="_blank">更多 MORE</a></div>
		</div>
		<div class="row">
			<div class="col-xs-5">
				<div class="xb-one">
					<div class="xbo-img">
                        <a href="${homeReadList[0].hyperlink}" target="_blank">
						    <img src="${ctxIndex}${homeReadList[0].url}" style="width:220px;height:400px;" alt="${homeReadList[0].name}"></a>
					</div>
					<div class="xbo-bd">
						<h4><a href="${homeReadList[0].hyperlink}" target="_blank">${homeReadList[0].name}</a></h4>
						<p><a href="${homeReadList[0].hyperlink}" target="_blank">${homeReadList[0].summary}</a></p>
					</div>
				</div>
			</div>
			<div class="col-xs-7">
				<div class="row">
					<div class="col-xs-6">
						<div class="read-box">
							<h2 class="read-hd">风俗 CUSTOMS</h2>
							<div class="read-bd">
								<div class="read-bd-img">
								<a href="${homeCustomList[0].hyperlink}" target="_blank">
									<img src="${ctxIndex}${homeCustomList[0].url}" style="width:132px;height:137px;" alt="${homeCustomList[0].name}"></a>
								</div>
								<div class="read-bd-list">
									<a href="${homeCustomList[0].hyperlink}" target="_blank" class="ellipsis">${homeCustomList[0].name}</a>
									<a href="${homeCustomList[0].hyperlink}" target="_blank" class="el-info">${homeCustomList[0].summary}</a>
								</div>
								<a href="${ctx}culture/list/1030.html" class="read-more" target="_blank"><span class="sr-only">更多</span></a>
							</div>
						</div>
					</div>
					<div class="col-xs-6">
						<div class="read-box">
							<h2 class="read-hd">历史 HISTORY</h2>
							<div class="read-bd">
								<div class="read-bd-img">
								<a  href="${homeCultureList[0].hyperlink}" target="_blank">
									<img src="${ctxIndex}${homeCultureList[0].url}" style="width:133px;height:137px;" alt="${homeCultureList[0].name}"></a>
								</div>
								<div class="read-bd-list">
									<a href="${homeCultureList[0].hyperlink}" target="_blank" class="ellipsis">${homeCultureList[0].name}</a>
									<a href="${homeCultureList[0].hyperlink}" target="_blank" class="el-info">${homeCultureList[0].summary}</a>
								</div>
								<a  href="${ctx}culture/list/1020.html" class="read-more" target="_blank"><span class="sr-only">更多</span></a>
							</div>
						</div>
					</div>
					<div class="col-xs-6">
						<div class="read-box read-adjust">
							<h2 class="read-hd">宗教 RELIGION</h2>
							<div class="read-bd">
								<div class="read-bd-img">
								<a href="${homeReligionList[0].hyperlink}" target="_blank">
									<img src="${ctxIndex}${homeReligionList[0].url}" style="width:132px;height:137px;" alt="${homeReligionList[0].name}"></a>
								</div>
								<div class="read-bd-list">
									<a href="${homeReligionList[0].hyperlink}" target="_blank" class="ellipsis">${homeReligionList[0].name}</a>
									<a href="${homeReligionList[0].hyperlink}" target="_blank" class="el-info">${homeReligionList[0].summary}</a>
								</div>
								<a href="${ctx}culture/list/1040.html" class="read-more" target="_blank"><span class="sr-only">更多</span></a>
							</div>
						</div>
					</div>
					<div class="col-xs-6">
						<div class="read-box read-adjust">
							<h2 class="read-hd">其他 MISCELLANEOUS</h2>
							<div class="read-bd">
								<div class="read-bd-img">
								<a href="${homeOtherList[0].hyperlink}" target="_blank">
									<img src="${ctxIndex}${homeOtherList[0].url}" style="width:132px;height:137px;" alt="${homeOtherList[0].name}"></a>
								</div>
								<div class="read-bd-list">
									<a href="${homeOtherList[0].hyperlink}" target="_blank" class="ellipsis">${homeOtherList[0].name}</a>
									<a href="${homeOtherList[0].hyperlink}" target="_blank" class="el-info">${homeOtherList[0].summary}</a>
								</div>
								<a href="${ctx }culture" class="read-more" target="_blank"><span class="sr-only">更多</span></a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
	seajs.use('${ctxPortal}assets/css/duxz.css');
	seajs.use('${ctxPortal}assets/js/subStri.js',function(sub){
		sub.strClip(".el-info",32);
	})
</script>