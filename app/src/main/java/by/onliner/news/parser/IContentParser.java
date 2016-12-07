package by.onliner.news.parser;

/**
 * Created by Mi Air on 30.09.2016.
 */

public interface IContentParser<A,B> {
    B parse(A data);
}
