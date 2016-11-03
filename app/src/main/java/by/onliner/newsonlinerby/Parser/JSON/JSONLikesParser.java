package by.onliner.newsonlinerby.Parser.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import by.onliner.newsonlinerby.Parser.IJSONParser;
import by.onliner.newsonlinerby.Structures.Comments.Like;

/**
 * Парсинг JSON со списком лайков к комментариям
 */
public class JSONLikesParser implements IJSONParser<String, ArrayList<Like>> {
    @Override
    public ArrayList<Like> parse(String json) {
        ArrayList<Like> likes = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonComments = jsonObject.getJSONObject("comments");
            Iterator<?> keys = jsonComments.keys();
            while(keys.hasNext()) {
                String key = (String)keys.next();
                Like like = new Like();
                like.setCommentId(Integer.parseInt(key));
                if (jsonComments.get(key) instanceof JSONObject) {
                    JSONObject commentJson = ((JSONObject) jsonComments.get(key));
                    Iterator<?> keys2 = commentJson.keys();
                    while(keys2.hasNext()) {
                        String key2 = (String)keys2.next();
                        switch (key2) {
                            case "counter":
                                like.setCount((commentJson.getInt("counter")));
                                break;
                            case "best":
                                like.setBest(true);
                                break;
                            default:
                                throw new UnsupportedOperationException("Unknown like JSON key...");
                        }
                    }
                }

                likes.add(like);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return likes;
        }

        return likes;
    }
}