<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	(function() {
		document.write(unescape('%3Cdiv id="bdcs"%3E%3C/div%3E'));
		var bdcs = document.createElement('script');
		bdcs.type = 'text/javascript';
		bdcs.async = true;
		bdcs.src = 'http://znsv.baidu.com/customer_search/api/js?sid=10629833131413559375'
				+ '&plate_url='
				+ encodeURIComponent(window.location.href)
				//+ encodeURIComponent("www.ctibet.cn")
				+ '&t=' + Math.ceil(new Date() / 3600000);
		var s = document.getElementsByTagName('script')[0];
		s.parentNode.insertBefore(bdcs, s);
	})();
</script>