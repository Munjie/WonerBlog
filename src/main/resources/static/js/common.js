function editArticle() {

    $.ajax({
        type: "POST",
        url: '/check_edit',
        cache: false, //实际使用中请禁用缓存
        dataType: 'json',
        data: {
            name: null,
        },
        success: function (data) {
            if (data['status'] == 400) {
                alert("请先登录噢!")
                window.location.href = "/login.html";
            } else {

                window.location.href = "/article_edit.html";
            }
        },
    });

}