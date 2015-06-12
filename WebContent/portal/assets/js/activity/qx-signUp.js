/**
 * Created by liaohuan on 2015/3/17.
 */
$(document).on('click',".dropdown li", function () {
    var $this = $(this),
        _parent = $this.parent(),
        a_val = $this.children().text(),
        _showInfo = _parent.siblings('button').find('.sign-cont'),
        _subinp = _parent.siblings('input');
    _showInfo.text(a_val);
    _subinp.val(a_val);
});