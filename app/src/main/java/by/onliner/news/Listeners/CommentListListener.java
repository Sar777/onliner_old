package by.onliner.news.Listeners;

import java.util.LinkedHashMap;

import by.onliner.news.Structures.Comments.Comment;

/**
 * Created by orion on 11.11.16.
 */

public interface CommentListListener {
    void onResponse(LinkedHashMap<Integer, Comment> comments);
}