package by.onliner.news.managers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.util.ArrayList;

import by.onliner.news.App;
import by.onliner.news.constants.Constant;
import by.onliner.news.structures.news.News;

public class FavoritesNewsMgr {
    private static FavoritesNewsMgr ourInstance = new FavoritesNewsMgr();

    public static FavoritesNewsMgr getInstance() {
        return ourInstance;
    }

    private FavoritesNewsMgr() {
    }

    public void saveFavorite(@NonNull News news) {
        SQLiteDatabase db = App.getDBHelper().getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("id", news.getAttributes().getId());
        cv.put("json", new Gson().toJson(news));
        long res = db.insert(Constant.mTableNameFavorites, null, cv);

        if (res == -1)
            throw new IllegalArgumentException("Favorite news not saved in database: id " + news.getAttributes().getId());
    }

    public void deleteFavorite(@NonNull News news) {
        SQLiteDatabase db = App.getDBHelper().getWritableDatabase();
        int id = news.getAttributes().getId();
        int count = db.delete(Constant.mTableNameFavorites, "id = ?", new String[] { String.valueOf(id) });

        if (count == 0)
            throw new IllegalArgumentException("Favorite news not deleted from database: id " + id);
    }

    public ArrayList<News> getAllFavorites() {
        ArrayList<News> favorites = new ArrayList<>();

        SQLiteDatabase db = App.getDBHelper().getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(Constant.mTableNameFavorites, new String[] { "json" }, null, null, null, null, null);
            while (cursor.moveToNext()) {
                favorites.add(new Gson().fromJson(cursor.getString(0), News.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return favorites;
    }

    public boolean isFavorite(int id) {
        SQLiteDatabase db = App.getDBHelper().getReadableDatabase();

        Cursor cursor = null;
        boolean isFavorite = false;
        try {
            cursor = db.query(Constant.mTableNameFavorites, null, "id = ?", new String[] { String.valueOf(id) }, null, null, null);
            isFavorite = cursor.getCount() != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (cursor != null)
                cursor.close();
        }

        return isFavorite;
    }
}
