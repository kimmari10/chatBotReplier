$(document).ready(function() {

   activeNav("nav");
   chkSystemWords(".command-list");
   viewCmdMenu(".command_menu");
   setMasonry(".command-list");


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
    var list = ".command-list";
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
            masonry(list);
        }
        if(liName == "cmd_general") {
            $(list).empty().html(gnrlData);
            masonry(list);
        }
        if(liName == "cmd_system") {
            $(list).empty().html(sysData);
            masonry(list);
        }
        if(liName == "vt_card") {
            $(this).addClass("on");
            $(".vt_kwd a").removeClass("on");
            $(list).removeClass("kwd");
        }
        if(liName == "vt_kwd") {
            $(this).addClass("on");
            $(".vt_card a").removeClass("on");
            $(list).addClass("kwd");
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
