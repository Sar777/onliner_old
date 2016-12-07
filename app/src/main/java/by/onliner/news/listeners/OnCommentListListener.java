package by.onliner.news.listeners;

import java.util.LinkedHashMap;

import by.onliner.news.structures.comments.Comment;

/**
 * Created by orion on 11.11.16.
 */

public interface OnCommentListListener {
    void onResponse(LinkedHashMap<Integer, Comment> comments);
}