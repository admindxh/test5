function checkFlash() {
	var version;

    try {
        version = navigator.plugins[ 'Shockwave Flash' ];
        version = version.description;
    } catch ( ex ) {
        try {
            version = new ActiveXObject('ShockwaveFlash.ShockwaveFlash')
                    .GetVariable('$version');
        } catch ( ex2 ) {
            version = '0.0';
        }
    }
	version = version.match(/\d+/g);
	version = parseFloat(version[0] + '.' + version[1], 10);
	//console.log(version);
	if (version!=0 && version < 11.4 ) {
		return 1;
	} else if(version==0){
		return 2;
	}
	return 0;
}