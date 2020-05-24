$(document).ready(function() {

   activeNav("nav");
   chkSystemWords(".command-list");
   viewCmdMenu(".command_menu");
    
	
});

/* active nav(navigation var) : 네비게이션 바 활성화 */
function activeNav(target) {
    var chkTxt = "active_nav";
    $(".btn_navi").on("click", function(e) {
        $(target).addClass(chkTxt);
        e.preventDefault();
    });
    $(".btn_closeNavi").on("click", function(e) {
        $(target).removeClass(chkTxt);
        e.preventDefault();
    });
}

/* check system message list : 시스템메시지 리스트 표시 */
function chkSystemWords(target) {
    if($(target).length) {
        var words = "시스템";
        $(target).find("li").each(function() {
            if($(this).find(".sign").text() == words) $(this).addClass("system");
        })
    }
}

/* view type menu for command list : 명령어리스트 뷰타입관련 메뉴 */
function viewCmdMenu(target) {
    var $list = $(".command-list");
    var gnrlData = $list.find("li").not(".system");
    var sysData = $list.find("li").filter(".system"), sysIdx = [];

    $list.find("li").each(function(i) {
        if($(this).hasClass("system")) sysIdx.push(i);
    });

   $(target).find("a").on("click", function(e) {
        var liName = $(this).parent().attr("class");
        if(liName == "cmd_all") {
            $list.html(gnrlData);
            $.each(sysIdx, function(i, val) {
                $list.find("li").eq(val).before(sysData[i]);
            })
        }
        if(liName == "cmd_general") {
            $list.empty().html(gnrlData);
        }
        if(liName == "cmd_system") {
            $list.empty().html(sysData);
        }
        
        e.preventDefault();
   });
}
