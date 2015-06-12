/*
 * 公共JS，页面加载完后须用到的
 * dxh 2014-11-26 13:50:24
 *
 * */




/**
 *author  邓晓辉
 *time  2014-11-26 13:56:06
 *describe   添加收藏   注意360 是不支持添加收藏的
 *usemethod   <a href="#" onclick="AddFavorite(window.location, document.title);" target="_self">加入收藏</a>
 */
function AddFavorite(sURL, sTitle) {
	try {
		window.external.addFavorite(sURL, sTitle);
	} catch(e) {
		try {
			window.sidebar.addPanel(sTitle, sURL, "");
		} catch(e) {
			alert("加入收藏失败，请使用Ctrl+D进行添加");
		}
	}
}




