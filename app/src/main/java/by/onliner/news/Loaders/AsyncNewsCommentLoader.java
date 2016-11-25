package by.onliner.news.Loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import by.onliner.news.Managers.LikeMgr;
import by.onliner.news.Parser.Parsers.CommentsParser;
import by.onliner.news.Structures.Comments.Comment;
import by.onliner.news.Structures.Comments.Like;

/**
 * Лоаудер обработки комментариев. Выполняется в бэграунде после загрузки содержимого новости
 */
public class AsyncNewsCommentLoader extends AsyncTaskLoader<LinkedHashMap<String, Comment>> {
    private String mContent;
    private String mProject;
    private String mPostId;

    public AsyncNewsCommentLoader(Context context, Bundle args) {
        super(context);
        mContent = args.getString("Html");
        mProject = args.getString("Project");
        mPostId = args.getString("PostId");
    }

    @Override
    public LinkedHashMap<String, Comment> loadInBackground() {
        LinkedHashMap<String, Comment> comments = new CommentsParser().parse(mContent);
        HashMap<String, Like> likes = LikeMgr.getInstance().getLikes(mProject, mPostId);
        if (likes == null)
            return comments;

        for (Map.Entry<String, Like> like : likes.entrySet()) {
            Comment comment = comments.get(like.getKey());
            if (comment == null)
                continue;

            comment.setLikes(like.getValue());
        }

        return comments;
    }
}
