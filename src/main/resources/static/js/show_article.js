var articleId = "";


$.ajax({
    type: 'HEAD', // 获取头信息，type=HEAD即可
    url: window.location.href,
    async: false,
    success: function (data, status, xhr) {
        articleId = xhr.getResponseHeader("articleId");

    },

});


$.ajax({
//通过文章id文章信息
    type: 'GET',
    url: '/article/show/' + articleId,
    dataType: 'json',
    async: false,
    success: function (data) {
        if (data.status == "200") {
            $(function () {
                putInArticle(data);
                // mdToHml(data);
            });

        }
    },
});

function buildHead(data) {
    var text = '';
    text += '            <div class=\'am-article-hd\'>';
    text += '                <h1 class=\'am-article-title blog-text-center\'>' + data.articleTitle + '</h1>';
    text += '                <p class=\'am-article-meta blog-text-center\'>';
    text += '                    <span><a href="/tagArticle/' + data.articleType + '" class=\'blog-color\'>' + data.articleType + ' &nbsp;</a></span>-';
    text += '                    <span><a href=\'#\'>@' + data.author + ' &nbsp;</a></span>-';
    text += '                    <span><a href=\'#\'>' + data.publishDate + '</a></span>';
    text += '                </p>';
    text += '            </div>';
    return text;

}

//填充文章
function putInArticle(data) {
    $('#article_detail_info').html('');
    $('#article_detail_info').append(buildHead(data));

    $("#mdInfo").text(data.articleContent);
    var article_view;
    article_view = editormd.markdownToHTML("article_view", {
        htmlDecode: "true", // you can filter tags decode
        emoji: true,
        taskList: true,
        tex: true,
        flowChart: true,
        sequenceDiagram: true
    });

}

//markDown转HTMl的方法
function mdToHml(data) {
    $("#testEditorMdview").html('<textarea id="appendTest" style="display:none;"></textarea>');
    var content = data.articleContent;
    $("#appendTest").val(content);

    editormd.markdownToHTML("testEditorMdview", {
        htmlDecode: "style,script,iframe",
        emoji: true,
        taskList: true,
        tex: true,
        flowChart: true,
        sequenceDiagram: true,
    });
}

