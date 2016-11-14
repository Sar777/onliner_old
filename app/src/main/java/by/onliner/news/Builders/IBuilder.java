package by.onliner.news.Builders;

/**
 * Created by Mi Air on 13.10.2016.
 */

public interface IBuilder<A,B> {
    B build(A data);
}
