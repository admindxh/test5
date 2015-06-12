<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<c:if test="${img[0].isshow=='1'}">
	<div id="qrcode">
	  <a href="javascript:;" class="qr-close" title="关闭">关闭</a>
	  <div class="qr-img">
	      <img src="${ctxIndex}${img[0].url}" width="121px" height="121px">
	  </div>
	</div>
    </c:if>
