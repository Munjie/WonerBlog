// 发表博客
var surePublishBtn = $('.publishBtn');
var articleTitle = $('#article-editor-title').val();
var articleAuthor = $('#article-editor-author').val();
var articleType = $('#select-type option:selected').val();//选中的值
var articleContent = $('#my-editormd-html-code').val();
surePublishBtn.click(function () {

    if (articleTitle.length == 0) {
        console.log("标题不能为空");
        return null;
    } else if (articleAuthor.length == 0) {
        console.log("作者不能为空");
        return null;
    } else if (articleType.length == 0) {
        console.log("类型不能为空");
        return null;
    } else {
        $.ajax({
            type: "POST",
            url: "/publishArticle",
            traditional: true,// 传数组
            data: {
                articleTitle: articleTitle,
                articleContent: articleContent,
                articleType: articleType,
                originalAuthor: articleAuthor,
                articleHtmlContent: testEditor.getHTML()
            },
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            dataType: "json",
            success: function (data) {
                if (data['status'] == 200) {
                    console.log("发布成功")
                } else {
                    alert("发表博客失败");
                }
            },
            error: function () {
                alert("发表博客请求失败！")
            }
        })
    }
});