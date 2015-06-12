seajs.use(['jquery','raty/jquery.raty.js'],function($){
	
	$(function(){
		initScore("mer-score", basePath_ + "tourism/merchant/getScore", "mcode");
		initScore("gt-score", basePath_ + "tourism/group/getScore", "gcode");
	});
    
	function initScore(className, url, property){
		var ps = $("."+className);
		var p = "";
		for(var i=0; i<ps.length; i++){
			p = ps.eq(i);
			$.ajax({
				type:"POST",
				url:url,
				data:{code:p.attr(property)},
				async:false,
				datatype:"json",
				success:function(score){
					p.attr("data-score",score==0?"5.0":score);
				},
				error:function(){
					p.attr("data-score","5.0");
				}
			});
		}
		initRaty(className);
	}
	
	function initRaty(className){
		$('.'+className).raty({
			readOnly: true,
			space: true,
			score: function() {
				var $this = $(this),
				score = $this.data('score') || '5';
				$this.append('<span>' + score + '</span>');
				return (score);
			},
			half: true,
			starHalf: ctxPortal+'/assets/icon/star_half.png',
			starOff: ctxPortal+'/assets/icon/star_zero.png',
			starOn: ctxPortal+'/assets/icon/star_full.png'
		});
	}
	
});