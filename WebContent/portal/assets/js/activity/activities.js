/**
 * Created by liaohuan on 2014/12/4.
 */
define(function(require, exports, module){
    var $ = require("jquery");
    $("#activity").on('click',function(){               //切换到活动列表
        $(this).addClass('activity-active').removeClass('activity-inactive');
        $("#subject").addClass('subject-inactive').removeClass('subject-active');
        $("#activity-content").addClass('activity-block').removeClass('activity-none');
        $("#subject-content").addClass('activity-none').removeClass('activity-block');
    });
    $("#subject").on('click',function(){               //切换到专题列表
        $(this).addClass('subject-active').removeClass('subject-inactive');
        $("#activity").addClass('activity-inactive').removeClass('activity-active');
        $("#subject-content").addClass('activity-block').removeClass('activity-none');
        $("#activity-content").addClass('activity-none').removeClass('activity-block');
    });
    $("#latest-release").on('click',function(){         //按最新发布查询
        $(this).addClass('active').removeClass('inactive');
        $("#hot").addClass('inactive').removeClass('active');
    });
    $("#hot").on('click',function(){                    //按热度查询
        $(this).addClass('active').removeClass('inactive');
        $("#latest-release").addClass('inactive').removeClass('active');
    })
});