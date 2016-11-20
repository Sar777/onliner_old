package by.onliner.news.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import by.onliner.news.Constants.Constant;

public class DBCredentialsHelper extends SQLiteOpenHelper {
    public DBCredentialsHelper(Context context) {
        super(context, Constant.mDBName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE %s (`json` TEXT);", Constant.mTableNameCredentials));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constant.mTableNameCredentials);
        onCreate(db);
    }
}