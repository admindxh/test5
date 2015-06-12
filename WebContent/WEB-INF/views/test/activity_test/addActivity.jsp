<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
	</head>
	<body>
		<form id="save" action="web/activityController/addActivity" method="POST">
			<table>
				<tr>
					<td>
						<input type="text" name="activityCode" value="${activityCode }"><br>
						活动类型：
						<select name="activityType">
							<option>活动</option>
							<option>专题</option>
						</select>
					</td>
					<td>
						<input type="checkbox" value="1" name="isUpload">上传<br>
						<input type="checkbox" value="1" name="isVote">投票<br>
						<input type="checkbox" value="1" name="isPay">支付<br>
						<input type="checkbox" value="1" name="isEnroll">报名<br>
						<input type="checkbox" value="1" name="isEnrollPay">报名支付<br>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						基本信息<hr>
					</td>
				</tr>
				<tr>
					<td>活动名称<input type="text" name="name"></td>
					<td>序号<input type="text" name="sortNum"></td>
				</tr>
				<tr>
					<td colspan="2">
						活动简介<textarea name="summary"></textarea>
					</td>
				</tr>
				<tr>
					<td>
						banner图<input id="bannerFile" type="file" name="files">
						<input id="bannerImg" type="text" name="bannerImg">
						<input type="button" value="上传" onclick="ajaxFileUpload('banner');">
					</td>
					<td>预览<img id="preBanner" width="100" height="100" src=""></td>
				</tr>
				<tr>
					<td>开始时间<input type="text" name="startDate1"></td>
					<td>结束时间<input type="text" name="endDate1"></td>
				</tr>
				<tr>
					<td colspan="2">
						报名相关<hr>
					</td>
				</tr>
				<tr>
					<td>名称1<input type="text" name="name1"></td>
					<td>
						内容1<textarea name="summary1"></textarea>
					</td>
				</tr>
				<tr>
					<td>名称2<input type="text" name="name2"></td>
					<td>
						内容2<textarea name="summary2"></textarea>
					</td>
				</tr>
				<tr>
					<td>名称3<input type="text" name="name3"></td>
					<td>
						内容3<textarea name="summary3"></textarea>
					</td>
				</tr>
				<tr>
					<td>报名表单</td>
					<td>
						<input type="text" name="listEnrollForm[0].property" value="名字">
						<input type="text" name="listEnrollForm[0].propertyType" value="文本"><br>
						<input type="text" name="listEnrollForm[1].property" value="身份证">
						<input type="text" name="listEnrollForm[1].propertyType" value="图片"><br>
						<input type="text" name="listEnrollForm[2].property" value="资料">
						<input type="text" name="listEnrollForm[2].propertyType" value="文件"><br>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						投票相关<hr>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						投票名称<input type="text" name="voteName">
					</td>
				</tr>
				<tr>
					<td>
						数量
						<select name="voteNum">
							<option>1</option>
							<option>2</option>
							<option selected="selected">3</option>
						</select>
					</td>
					<td>
						投票名称<input type="text" name="voteName">
					</td>
				</tr>
				<tr>
					<td>选项</td>
					<td>
						1.<input type="text" name="listVoteOption[0].options"><br>
						2.<input type="text" name="listVoteOption[1].options"><br>
						3.<input type="text" name="listVoteOption[2].options"><br>
					</td>
				</tr>
				<tr>
					<td>投票简介</td>
					<td>
						<textarea name="voteSummary"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						其他模块<hr>
					</td>
				</tr>
				<tr>
					<td>模块名称</td>
					<td>
						<input type="text" name="otherBlockName">
					</td>
				</tr>
				<tr>
					<td>
						图片<input type="file" name="files"><input type="button" value="上传"><input type="text" name="url"><br>
						名称<input type="text" name="name"><br>
						链接<input type="text" name="hyperlink"><br>
					</td>
					<td>
						预览<img width="100" height="100" src="">
					</td>
				</tr>
			</table>
			<input type="button" value="提交" onclick="save()">
		</form>
		<img id="loading" src="common-images/loading.gif" style="display:none;">
		<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="javascripts/ajaxfileupload.js"></script>
		<script type="text/javascript">
		
			function save(){
				var form = document.getElementById("save");
				$("input[type='file']").attr('disabled',"true");
				document.getElementById("bannerFile").disabled=true;
				//form.submit();
			}
		
			function ajaxFileUpload(type_) {
				$(document).ajaxStart(function() {
					$("#loading").show();
				}).ajaxComplete(function() {
					$("#loading").hide();
				});
				$.ajaxFileUpload({
					url : 'web/activityController/fileUpload',
					secureuri : false,
					fileElementId : 'bannerFile',
					data:{type:type_, activityCode:'${activityCode }'},
					dataType : 'json',
					success : function(data, status) {
						$("#preBanner").attr("src", "<%=basePath%>" + data.imgPath);
						$("#bannerImg").val(data.imgPath);
					},
					error : function(data, status, e) {
						alert(e);
					}
				})
				return false;
			}
		</script>
	</body>
</html>