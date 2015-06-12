var flashVersion = (function() {
	var version;
	try {
		version = navigator.plugins['Shockwave Flash'];
		version = version.description;
	} catch (ex) {
		try {
			version = new ActiveXObject('ShockwaveFlash.ShockwaveFlash')
					.GetVariable('$version');
		} catch (ex2) {
			version = '0.0';
		}
	}
	version = version.match(/\d+/g);
	return parseFloat(version[0] + '.' + version[1], 10);
})();

function checkUploader() {
	if (!WebUploader.Uploader.support('flash') && WebUploader.browser.ie) {
		if (flashVersion) {
			alert("flash 版本太低");
		} else {
			alert("请安装flash");
		}
	} else if (!WebUploader.Uploader.support()) {
		alert("我表示很遗憾，暂不支持你所使用的浏览！！！");
	}
}