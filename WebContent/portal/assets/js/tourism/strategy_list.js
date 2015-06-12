/**
 * Created by liaohuan on 2014/12/18.
 */
define(function(require,exports,module){
    var jquery = require("jquery");
    	$(document).on('click','.dropdown-menu li',function(){
            var aNodeText = $(this).children('a').text();
            var listValue = $(this).children('a').attr('value');
            $(this).parent(".dropdown-menu").siblings(".dropdown-toggle").children('label').text(aNodeText);
            $(this).parent(".dropdown-menu").siblings("input").val(listValue);
    });
});