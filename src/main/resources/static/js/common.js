$(document).ready(function() {

   activeNav("nav");
   chkSystemWords(".cmnd-list");
   viewCmdMenu(".cmnd-menu");
   setMasonry(".cmnd-list.cardV");


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
        $(target).find("li").each(function() {
            var $thisSign = $(this).find(".sign");
            if($thisSign.text() == "Y" || $thisSign.text() == "y") {
                $(this).addClass("system");
                $thisSign.text("시스템");
            }
        })
    }
}

/* view type menu for command list : 명령어리스트 뷰타입관련 메뉴 */
function viewCmdMenu(target) {
    var list = ".cmnd-list";
    var gnrlData = $(list).find("li").not(".system");
    var sysData = $(list).find("li").filter(".system"), sysIdx = [];

    $(list).find("li").each(function(i) {
        if($(this).hasClass("system")) sysIdx.push(i);
    });

   $(target).find("a").on("click", function(e) {
        var liName = $(this).parent().attr("class");
        if(liName == "cmd_all") {
            $(list).html(gnrlData);
            $.each(sysIdx, function(i, val) {
                $(list).find("li").eq(val).before(sysData[i]);
            });
            setMnAction(this, ".cmd_general a, .cmd_system a", true);
        }
        if(liName == "cmd_general") {
            $(list).empty().html(gnrlData);
            setMnAction(this, ".cmd_all a, .cmd_system a", true);
        }
        if(liName == "cmd_system") {
            $(list).empty().html(sysData);
            setMnAction(this, ".cmd_all a, .cmd_general a", true);
        }
        if(liName == "vt_card") {
            $(list).addClass("cardV").removeClass("kwdV");
            setMnAction(this, ".vt_kwd a", true);
        }
        if(liName == "vt_kwd") {
            $(list).addClass("kwdV").removeClass("cardV");
            setMnAction(this, ".vt_card a", false);
        }

        function setMnAction(onMn, offMn, masonryYN) {
            $(offMn).removeClass("on");
            $(onMn).addClass("on");
            if(masonryYN) masonry(list + ".cardV");
            else delMasonryTrace(list);
        }
        e.preventDefault();
   });
}

/* set masonry : 문서로딩시 벽돌형식 포지셔닝 세팅 */
function setMasonry(target) {
	masonry(target);
	$(window).on("resize", function() {
        if(this.resizeTo) clearTimeout(this.resizeTo);
        this.resizeTo = setTimeout(function() {
            masonry(target);
        }, 300);
    });

}

/* align to masonry type : 벽돌형식 포지셔닝 */
function masonry(target) {
    $(target).children().css("position", "static");
    var colNum = parseInt($(target).width()/$(target).children().outerWidth());
    var arrLeft = [], arrTop = [];
    $(target).children().each(function(childIndex) {
        var thisH = $(this).outerHeight() + parseInt($(this).css("marginBottom")) + parseInt($(this).css("marginTop"));
        if(childIndex < colNum) {
            arrLeft.push($(this).position().left);
            arrTop.push(thisH);
            $(this).stop().animate({"top" : 0, "left" : arrLeft[childIndex]}, 400);
        } else {
            var minTop = Math.min.apply(Math, arrTop);
            var minIndex = arrTop.indexOf(minTop);
            $(this).stop().animate({"top" : minTop, "left" : arrLeft[minIndex]}, 400);
            arrTop[minIndex] = thisH + minTop;
        }
    });
    $(target).css({"position":"relative", "height": Math.max.apply(Math, arrTop) + parseInt($(target).css("paddingBottom"))}).children().css({"position":"absolute"});
}

/* delete masonry trace : 벽돌형식 포지셔닝흔적 삭제 */
function delMasonryTrace(target) {
    $(target).attr("style", "");
    $(target).find("li").attr("style", "");
}
