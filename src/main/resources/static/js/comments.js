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
                alert("评论成功!");
                window.location.reload();
            } else {
                if (result.msg) {
                    alert(result.msg);
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