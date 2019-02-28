// 页面初始化：填充数据
window.onload = function () {
    // $.ajax({
    //     url: "http://10.2.3.235:80/api/article/list/lastest",
    //     type: "GET",
    //     dataType: "json",
    //     success: function (json) {
    //         $.each(json, function (i, item) {
    //             // 设置右下角题图的内容
    //             $(".smallPictures img[location=" + i + "]").attr("src", item.pictureUrl);
    //             $(".smallPictures img[location=" + i + "]").attr("pictureUrl", item.pictureUrl);
    //             $(".smallPictures img[location=" + i + "]").attr("articleId", item.id);
    //             $(".smallPictures img[location=" + i + "]").attr("title", item.title);
    //             $(".smallPictures img[location=" + i + "]").attr("summary", item.summary);
    //
    //             // 默认显示第一篇文章的信息
    //             if (i == "0") {
    //                 $("#articleTitle").html(item.title);
    //                 $("#articleSummary").html(item.summary);
    //                 $("#articlePicture img").attr("src", item.pictureUrl);
    //                 $("#showArticle").attr("articleId", item.id);
    //             }
    //         });
    //     }
// });
    ajaxFirst(1);
};

// // 按钮点击进行文章详情页
// $("#showArticle").click(function () {
//     var articleId = $(this).attr("articleId");
//     var url = "article.html?articleId=" + articleId;
//     window.location.href = url;
// });

// 测试使用的函数
// $("#showArticle").click(function() {
// 	$.ajax({
// 		url: "http://10.2.3.235:80/api/article/1",
// 		type: "GET",
// 		dataType: "json",
// 		success: function(json) {
// 			$("#articleTitle").html(json.title);
// 			$("#articleSummary").html(json.summary);
// 		}
// 	})
// });

// // 缩略图鼠标进入事件：更换大图和按钮的articleId
// $(".smallPictures img").mouseenter(function () {
//     var pictureUrl = $(this).attr("pictureUrl");
//     var articleId = $(this).attr("articleId")
//     var title = $(this).attr("title");
//     var summary = $(this).attr("summary");
//     $("#articlePicture img").attr("src", pictureUrl);
//     $("#showArticle").attr("articleId", articleId);
//     $("#articleTitle").html(title);
//     $("#articleSummary").html(summary);
// });

// function checkPicurl(url) {
// 	var img = new Image();
// 	img.src = url;
// 	img.onerror = function() {
// 		alert(name + " 图片加载失败，请检查url是否正确");
// 		return false;
// 	};
//
// 	if(img.complete) {
// 		console.log(img.width + " " + img.height);
// 	} else {
// 		img.onload = function() {
// 			console.log(img.width + " " + img.height);
// 			img.onload = null;
// 			//避免重复加载
// 		}
// 	}
// }


//填充文章
function putInArticle(data) {
    $('#article-show-list').empty();
    var articles = $('#article-show-list');
    var path = randomPath();
    $.each(data, function (index, obj) {
        if (index != (data.length) - 1) {
            articles.append(buldHtml(obj));
        }
    })

}

function randomPath() {
    var no = Math.floor(Math.random() * +1);
    var path = "i/f" + no + ".jpg";
    return path;

}

function buldHtml(obj) {

    var text = '';
    text += '<article class=\'am-g blog-entry-article\'>';
    text += '            <div class=\'am-u-lg-6 am-u-md-12 am-u-sm-12 blog-entry-img\'>';
    text += '                <img src=" ' + obj['articleImg'] + '" alt=\'\' class=\'am-u-sm-12\'>';
    text += '            </div>';
    text += '            <div class=\'am-u-lg-6 am-u-md-12 am-u-sm-12 blog-entry-text\'>';
    text += '                <span><a href="" class=\'blog-color\'>' + obj['articleType'] + ' &nbsp;</a></span>';
    text += '                <span>@' + obj['originalAuthor'] + ' &nbsp;</span>';
    text += '                <span>' + obj['publishDate'] + '</span>';
    text += '                <h1><a href="' + obj['articleUrl'] + '">' + obj['articleTitle'] + '</a></h1>';
    text += '                <p>';
    text += '       ' + obj['articleTabloid'] + '     </p>';
    text += '            </div>';
    text += '        </article>';
    return text;

}


//首页文章分页请求
function ajaxFirst(currentPage) {
    //加载时请求
    $.ajax({
        type: 'POST',
        url: '/home/allArticle',
        dataType: 'json',
        data: {
            rows: "5",
            pageNum: currentPage
        },
        success: function (data) {
            //放入数据
            putInArticle(data);
            scrollTo(0, 0);//回到顶部

            //分页
            $("#pagination").paging({
                rows: data[data.length - 1]['pageSize'],//每页显示条数
                pageNum: data[data.length - 1]['pageNum'],//当前所在页码
                pages: data[data.length - 1]['pages'],//总页数
                total: data[data.length - 1]['total'],//总记录数
                callback: function (currentPage) {
                    ajaxFirst(currentPage);
                }
            });
        },
        error: function () {
            alert("获得文章信息失败！");
        }
    });
}


(function ($, window, document, undefined) {
    //定义分页类
    function Paging(element, options) {
        this.element = element;
        //传入形参
        this.options = {
            rows: options.rows,
            pageNum: options.pageNum || 1,
            pages: options.pages,
            total: options.total,
            flag: options.flag,
            callback: options.callback
        };
        //根据形参初始化分页html和css代码
        this.init();
    }

    //对Paging的实例对象添加公共的属性和方法
    Paging.prototype = {
        constructor: Paging,
        init: function () {
            this.creatHtml();
            this.bindEvent();
        },
        creatHtml: function () {
            var me = this;
            var content = "";
            var current = me.options.pageNum;
            var pages = me.options.pages;
            var total = me.options.total;
            var flag = me.options.flag;
            content += "<ul class=\"page am-pagination  am-pagination-centered\">";
            //总页数大于4时候
            if (flag == 0) {
                if (pages > 4) {
                    //如果当前页小于4
                    if (current < 4) {
                        if (current != 1) {
                            content += "<li><a id='prePage'>&laquo; 上一页</a></li>";
                        }
                        content += "<li><a id='nextPage'>下一页 &raquo;</a></li>";
                    } else {
                        content += "<li><a id='prePage'>&laquo; 上一页</a></li>";
                        //当前页在末尾时
                        if (current > pages - 3) {
                            if (current != pages) {
                                content += "<li><a id='nextPage'>下一页 &raquo;</a></li>";
                            }
                        } else {
                            //当前页在中间时
                            content += "<li><a id='nextPage'>下一页 &raquo;</a></li>";
                        }
                    }
                    //页面总数小于6的时候
                } else {
                    if (current != 1) {
                        content += "<li><a id='prePage'>&laquo; 上一页</a></li>";
                    }
                    if (current != pages) {
                        content += "<li><a id='nextPage'>下一页 &raquo;</a></li>";
                    }
                }
            } else {
                if (pages > 4) {
                    //如果当前页小于4
                    if (current < 4) {
                        if (current != 1) {
                            content += "<li><a id='prePage'>&laquo; 上一页</a></li>";
                        }
                        for (var i = 1; i < 5; i++) {
                            if (current == i) {
                                content += "<li class=\"am-active\"><a href=\"#\">" + i + "</a></li>";
                            } else {
                                content += "<li><a>" + i + "</a></li>";
                            }
                        }
                        content += ". . .";
                        content += "<li><a>" + pages + "</a></li>";
                        content += "<li><a id='nextPage'>下一页 &raquo;</a></li>";
                    } else {
                        content += "<li><a id='prePage'>&laquo; 上一页</a></li>";
                        content += "<li><a>" + 1 + "</a></li>";
                        content += "...";
                        //当前页在末尾时
                        if (current > pages - 3) {
                            for (var i = pages - 3; i <= pages; i++) {
                                if (current == i) {
                                    content += "<li class=\"am-active\"><a href=\"#\">" + i + "</a></li>";
                                } else {
                                    content += "<li><a>" + i + "</a></li>";
                                }
                            }
                            if (current != pages) {
                                content += "<li><a id='nextPage'>下一页 &raquo;</a></li>";
                            }
                        } else {
                            //当前页在中间时
                            for (var i = current - 1; i < current + 3; i++) {
                                if (current == i) {
                                    content += "<li class=\"am-active\"><a href=\"#\">" + i + "</a></li>";
                                } else {
                                    content += "<li><a>" + i + "</a></li>";
                                }
                            }
                            content += "...";
                            content += "<li><a>" + pages + "</a></li>";
                            content += "<li><a id='nextPage'>下一页 &raquo;</a></li>";
                        }
                    }
                    //页面总数小于6的时候
                } else {
                    if (current != 1) {
                        content += "<li><a id='prePage'>&laquo; 上一页</a></li>";
                    }
                    for (var i = 1; i < pages + 1; i++) {
                        if (current == i) {
                            content += "<li class=\"am-active\"><a>" + i + "</a></li>";
                        } else {
                            content += "<li><a>" + i + "</a></li>";
                        }
                    }
                    if (current != pages) {
                        content += "<li><a id='nextPage'>下一页 &raquo;</a></li>";
                    }
                }
            }

            me.element.html(content);
        },
        //添加页面操作事件
        bindEvent: function () {
            var me = this;
            me.element.off('click', 'a');
            me.element.on('click', 'a', function () {
                var currentPage = $(this).html();
                var id = $(this).attr("id");
                if (id == "prePage") {
                    if (me.options.pageNum == 1) {
                        me.options.pageNum = 1;
                    } else {
                        me.options.pageNum = +me.options.pageNum - 1;
                    }
                } else if (id == "nextPage") {
                    if (me.options.pageNum == me.options.pages) {
                        me.options.pageNum = me.options.pages
                    } else {
                        me.options.pageNum = +me.options.pageNum + 1;
                    }

                } else {
                    me.options.pageNum = +currentPage;
                }
                me.creatHtml();
                if (me.options.callback) {
                    me.options.callback(me.options.pageNum);
                }
            });
        }
    };
    //通过jQuery对象初始化分页对象
    $.fn.paging = function (options) {
        return new Paging($(this), options);
    }
})(jQuery, window, document);


