<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common-html/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
     <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge">
    <title>游西藏-攻略管理-已审核的攻略</title>
    <script src="${ctxManage}/resources/plugin/jquery-1.11.1.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${ctxManage}/resources/plugin/baseCSS-1.0.4.min.css" />
    <link rel="stylesheet" href="${ctxManage}/resources/css/base.css" />
    
</head>
<body>

  <form action="http://localhost:8080/ctibet/portal/postController/savePost" method="post" enctype="multipart/form-data">  		
    
	
	帖子名<input type="text" name="contentTitle">
	帖子内容<input type="text" name="content"><br/>
  	<input type="submit">
   </form>
	====================================全部未审核的帖子========================================
    <table>
       <c:forEach var="post" items="${list}" varStatus="status">
						<tr>
						    <td>
						       <input type="checkbox" name="dataCheck" value="${post.code}">
						    </td>
						    <td>
						            发布时间:${post.createtime}
						    </td>
						    <td>
						           手机号：${post.mobile}
						    </td>
						    <td>
						     email:${post.email}
						    </td>
						    <td>
						            板块：${post.programaname}
						    </td>
						    <td>
							   发帖人：  ${post.name}  
							</td>
							<td>
							  帖子标题：   ${post.contenttitle}
							</td>
						</tr>
					</c:forEach>
							<tr><td>
							<button onclick="deleteBatchCodeOrSingle('dataCheck','${ctx}/portal/postController/deletePost','code')">delete</button>
							</td></tr>
    </table><br/>
    =======================================高级查询================================================
    <!-- 所属版块 -->
    <form action="" method="">
						<div class="select-base ml-10">
							<i class="w-110">所属版块</i>
							<dl>
								<dt  inputValue="" inputName="proCode">全部版块</dt>
								<c:forEach var="program" items="${programsList}">
									<dt  inputValue="${program.code}" inputName="proCode">${program.programaName}</dt>
								</c:forEach>
							</dl>
							<input id="proCode" type="hidden" value="" name="proCode"/>
						</div>  
			<input type="text" name="keyword"/>			
            <input type="submit"><br/>
    </form>
              <table>
       <c:forEach var="post" items="${result}" varStatus="status">
						<tr>
						    <td>
						       <input type="checkbox" name="dataCheck" value="${post.code}">
						    </td>
						    <td>
						            发布时间:${post.createtime}
						    </td>
						    <td>
						           手机号：${post.mobile}
						    </td>
						    <td>
						     email:${post.email}
						    </td>
						    <td>
						            板块：${post.programaname}
						    </td>
						    <td>
							   发帖人：  ${post.name}  
							</td>
							<td>
							  帖子标题：   ${post.contenttitle}
							</td>
						</tr>
					</c:forEach>





    <!-- JS引用部分 -->
   
     <script type="text/javascript" src="${ctx}/common-js/common.js"></script>
    <script src="${ctxManage}/resources/plugin/baseCSS-1.0.4.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/base.js" type="text/javascript"></script>
    <script src="${ctxManage}/resources/js/base-tabel.js" type="text/javascript"></script>
	
    <!-- 利用空闲时间预加载指定页面 -->
    <link rel="prefetch"> <!-- IE10+ -->
    <link rel="next"> <!-- Firefox -->
	<link rel="prerender"> <!-- Chrome -->
</body>
</html>