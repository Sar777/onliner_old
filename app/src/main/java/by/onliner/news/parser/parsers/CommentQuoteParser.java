package by.onliner.news.parser.parsers;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import by.onliner.news.parser.IContentParser;
import by.onliner.news.structures.comments.CommentQuote;

/**
 * Парсинг цитат к комментариям
 */
public class CommentQuoteParser implements IContentParser<Element, CommentQuote> {
    @Override
    public CommentQuote parse(Element element) {
        if (element == null)
            return null;

        CommentQuote quote = new CommentQuote();
        Elements childs = element.getElementsByClass("news-comment__cite");

        if (childs.size() > 1) {
            CommentQuote quoteRecurs = new CommentQuoteParser().parse(childs.last());
            childs.remove();

            Element elementText = childs.first().getElementsByClass("news-comment__speech").first();
            if (elementText != null)
                quote.setText(elementText.text());

            Element elementAuthor = childs.first().getElementsByClass("news-comment__name").first();
            if (elementAuthor != null)
                quote.setAuthor(elementAuthor.text());

            quote.setQuote(quoteRecurs);
        }
        else {
            quote = new CommentQuote();
            Element elementText = element.getElementsByClass("news-comment__speech").last();
            if (elementText != null)
                quote.setText(elementText.text());

            Element elementAuthor = element.getElementsByClass("news-comment__name").last();
            if (elementAuthor != null)
                quote.setAuthor(elementAuthor.text());
        }

        return quote;
    }
}
