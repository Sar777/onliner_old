package by.onliner.newsonlinerby.Asynchronous;

import android.os.AsyncTask;

import java.util.HashMap;

import by.onliner.newsonlinerby.Listeners.ResponseListener;
import by.onliner.newsonlinerby.Parser.Parsers.CommentsParser;
import by.onliner.newsonlinerby.Structures.Comments.Comment;

/**
 * Created by Mi Air on 21.10.2016.
 */

public class AsyncCommentParser extends AsyncTask<Void, HashMap<Integer, Comment>, HashMap<Integer, Comment> > {

    private final String content;
    private final ResponseListener responseListener;

    public AsyncCommentParser(String content, ResponseListener responseListener) {
        this.content = content;
        this.responseListener = responseListener;
    }

    @Override
    protected HashMap<Integer, Comment> doInBackground(Void... params) {
        return new CommentsParser().parse(content);
    }

    @Override
    protected void onPostExecute(HashMap<Integer, Comment>  result) {
        super.onPostExecute(result);
        responseListener.onResponse(result);
    }
}