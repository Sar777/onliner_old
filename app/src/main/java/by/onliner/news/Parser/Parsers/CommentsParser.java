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
public class CommentsParser implements IContentParser<String, LinkedHashMap<Integer, Comment>> {
    @Override
    public LinkedHashMap<Integer, Comment> parse(String data) {
        Document doc = Jsoup.parse(data);
        LinkedHashMap<Integer, Comment> commentList = new LinkedHashMap<>();

        for (Element element : doc.getElementById("commentsList").getElementsByClass("news-comment__item")) {
            Comment comment = new Comment();

            // Пропуск парсинга лучего комментария по кол-ву лайков
            if (element.id().equals("bestComment"))
                continue;

            comment.setId(Integer.parseInt(element.attr("data-comment-id")));
            comment.setDate(element.getElementsByClass("news-comment__time").first().text());
            comment.setAvatarURL(element.getElementsByClass("news-comment__image").first().attr("data-avatar").replace("url(", "").replace(")", ""));

            comment.getAuthor().setId(Integer.parseInt(element.attr("data-author-id")));
            comment.getAuthor().setName(element.attr("data-author"));

            // Цитаты в комментариях
            comment.setQuote(new CommentQuoteParser().parse(element.getElementsByClass("news-comment__cite").first()));

            element.getElementsByClass("news-comment__cite").remove();
            comment.setText(element.getElementsByClass("news-comment__speech").first().html());

            commentList.put(comment.getId(), comment);
        }

        return commentList;
    }
}