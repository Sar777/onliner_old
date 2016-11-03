package by.onliner.newsonlinerby.Asynchronous;

import android.os.AsyncTask;

import java.util.LinkedHashMap;

import by.onliner.newsonlinerby.Listeners.ResponseListener;
import by.onliner.newsonlinerby.Parser.Parsers.CommentsParser;
import by.onliner.newsonlinerby.Structures.Comments.Comment;

/**
 * Created by Mi Air on 21.10.2016.
 */

public class AsyncCommentParser extends AsyncTask<Void, LinkedHashMap<Integer, Comment>, LinkedHashMap<Integer, Comment> > {

    private final String content;
    private final ResponseListener<LinkedHashMap<Integer, Comment>> responseListener;

    public AsyncCommentParser(String content, ResponseListener<LinkedHashMap<Integer, Comment>> responseListener) {
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