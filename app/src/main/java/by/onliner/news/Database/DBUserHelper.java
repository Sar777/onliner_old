package by.onliner.news.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import by.onliner.news.Constants.Constant;

public class DBUserHelper extends SQLiteOpenHelper {
    public DBUserHelper(Context context) {
        super(context, Constant.mDBName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("ORION", "OK-1");
        db.execSQL(String.format("CREATE TABLE %s (`json` TEXT);", Constant.mTableNameUser));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constant.mTableNameUser);
        onCreate(db);
    }
}