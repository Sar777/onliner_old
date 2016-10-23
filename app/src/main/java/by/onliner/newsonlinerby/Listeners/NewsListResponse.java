package by.onliner.newsonlinerby.Listeners;

/**
 * Created by orion on 23.10.16.
 */

public interface NewsListResponse<A> {
    void OnSuccess(A response);
    void onFailure();
}
