package by.onliner.newsonlinerby.Parser;


/**
 * Интерфейс для парсинг JSON формата
 * @param <A>
 * @param <B>
 */
public interface IJSONParser<A, B> {
    B parse(A json);
}
