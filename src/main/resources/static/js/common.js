function editArticle() {

    $.ajax({
        type: "POST",
        url: '/check_edit',
        cache: false, //实际使用中请禁用缓存
        dataType: 'json',
        async: false,
        data: {
            name: null,
        },
        success: function (data) {
            if (data['status'] == 400) {
                swal("请先登录嗷~~!", "", "info");
            } else {

                window.location.href = "/article_edit.html";
            }
        },
    });

}

//点赞评论
function likeComment(coid) {
    var ico = document.getElementById(coid);
    $.ajax({
        type: 'post',
        url: '/likeComment',
        dataType: 'json',
        async: false,
        data: {
            coid: coid,
        },
        success: function (result) {
            if (result && result.success) {
                ico.style.color = "#ff2620";
                swal("点赞成功!", "", "success");
                //  setTimeout("location.reload()", 1000);//页面刷新

            } else {
                if (result.msg) {
                    swal(result.msg, "", "warning");

                }
            }
        },
        error: function () {
            swal("点赞请求失败!", "", "error");
        }
    });
}

function likeReply(coid) {
    var ico = document.getElementById(coid);
    $.ajax({
        type: 'post',
        url: '/likeReply',
        dataType: 'json',
        async: false,
        data: {
            coid: coid,
        },
        success: function (result) {
            if (result && result.success) {
                ico.style.color = "#ff2620";
                swal("点赞成功!", "", "success");
                //  setTimeout("location.reload()", 1000);//页面刷新

            } else {
                if (result.msg) {
                    swal(result.msg, "", "warning");

                }
            }
        },
        error: function () {
            swal("点赞请求失败!", "", "error");
        }
    });
}

//点赞喜欢效果
function niceIn(prop) {
    prop.find('i').addClass('niceIn');
    setTimeout(function () {
        prop.find('i').removeClass('niceIn');
    }, 1000);
}

function pub_comments() {

    $.ajax({
        type: 'post',
        url: '/comment',
        data: $('#comment-form').serialize(),
        async: false,
        dataType: 'json',
        success: function (result) {
            $('#comment-form input[name=coid]').val('');
            if (result && result.success) {
                swal("评论成功!", "", "success");
                setTimeout("location.reload()", 1000);//页面刷新

            } else {
                if (result.msg) {
                    swal(result.msg, "", "warning");

                }
            }
        }
    });
    return false;


}

function getCommentCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg))
        return unescape(decodeURI(arr[2]));
    else
        return null;
}

function addCommentInputValue() {
    document.getElementById('author').value = getCommentCookie('tale_remember_author');
    document.getElementById('mail').value = getCommentCookie('tale_remember_mail');
}

addCommentInputValue();


function showRepleyComment(coid, authorId, author, cid) {


    $('#my-prompt').modal({
        relatedTarget: this,

        onConfirm: function () {
            var replyComment = $("#repleyComment").val();
            $.ajax({
                type: 'post',
                url: '/replyComment',
                async: false,
                dataType: 'json',
                data: {
                    coid: coid,
                    replyId: authorId,
                    replyName: author,
                    comment: replyComment,
                    cid: cid

                },
                success: function (result) {
                    if (result && result.success) {
                        swal("回复成功!", "", "success");
                        setTimeout("location.reload()", 2000);//页面刷新

                    } else {
                        if (result.msg) {
                            swal(result.msg, "", "warning");

                        }
                    }
                }
            });

        },

    });


}


function deleteComment(coid, cid) {

    $.ajax({
        type: 'post',
        url: '/deleteComment',
        async: false,
        dataType: 'json',
        data: {
            coid: coid,
            cid: cid,

        },
        success: function (result) {
            if (result && result.success) {
                swal("删除成功!", "", "success");
                setTimeout("location.reload()", 2000);//页面刷新

            } else {
                if (result.msg) {
                    swal(result.msg, "", "warning");

                }
            }
        }
    });

}