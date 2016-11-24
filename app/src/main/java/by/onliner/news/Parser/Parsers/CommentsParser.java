package by.onliner.news.Parser.Parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.LinkedHashMap;

import by.onliner.news.Parser.IContentParser;
import by.onliner.news.Structures.Comments.Comment;

/**
 * Парсинг комментариев
 */
public class CommentsParser implements IContentParser<String, LinkedHashMap<String, Comment>> {
    @Override
    public LinkedHashMap<String, Comment> parse(String data) {
        Document doc = Jsoup.parse(data);
        LinkedHashMap<String, Comment> commentList = new LinkedHashMap<>();

        for (Element element : doc.getElementsByClass("news-comment__item")) {
            Comment comment = new Comment();

            // Пропуск парсинга лучшего комментария по кол-ву лайков
            if (element.id().equals("bestComment"))
                continue;

            // WTF
            if (element.attr("data-comment-id").isEmpty())
                continue;

            comment.setId(Integer.valueOf(element.attr("data-comment-id")));
            Element commentTime = element.getElementsByClass("news-comment__time").first();
            if (commentTime == null)
                continue;;

            comment.setDate(commentTime.text());
            comment.setAvatarURL(element.getElementsByClass("news-comment__image").first().attr("data-avatar").replace("url(", "").replace(")", ""));

            comment.getAuthor().setId(Integer.parseInt(element.attr("data-author-id")));
            comment.getAuthor().setName(element.attr("data-author"));

            // Цитаты в комментариях
            // TODO Нужен фикс. Нету вложенности
            comment.setQuote(new CommentQuoteParser().parse(element.getElementsByClass("news-comment__cite").first()));

            element.getElementsByClass("news-comment__cite").remove();
            Element elementText = element.getElementsByClass("news-comment__speech").first().getElementsByTag("p").first();
            if (elementText != null)
                comment.setText(elementText.html());

            commentList.put(comment.getId().toString(), comment);
        }

        return commentList;
    }
}