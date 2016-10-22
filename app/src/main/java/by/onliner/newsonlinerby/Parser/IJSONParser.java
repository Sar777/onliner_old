package by.onliner.newsonlinerby.Parser;

/**
 * Created by Mi Air on 21.10.2016.
 */

public interface IJSONParser<A, B> {
    B parse(A json);
}
