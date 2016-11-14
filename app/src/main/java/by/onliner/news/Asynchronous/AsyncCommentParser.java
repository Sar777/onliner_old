package by.onliner.news.Asynchronous;

import android.os.AsyncTask;

import java.util.LinkedHashMap;

import by.onliner.news.Listeners.CommentListListener;
import by.onliner.news.Parser.Parsers.CommentsParser;
import by.onliner.news.Structures.Comments.Comment;

/**
 * Created by Mi Air on 21.10.2016.
 */

public class AsyncCommentParser extends AsyncTask<Void, LinkedHashMap<Integer, Comment>, LinkedHashMap<Integer, Comment> > {

    private final String content;
    private final CommentListListener responseListener;

    public AsyncCommentParser(String content, CommentListListener responseListener) {
        this.content = content;
        this.responseListener = responseListener;
    }

    @Override
    protected LinkedHashMap<Integer, Comment> doInBackground(Void... params) {
        return new CommentsParser().parse(content);
    }

    @Override
    protected void onPostExecute(LinkedHashMap<Integer, Comment>  result) {
        super.onPostExecute(result);
        responseListener.onResponse(result);
    }
}