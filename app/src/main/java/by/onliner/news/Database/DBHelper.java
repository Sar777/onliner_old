package by.onliner.news.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import by.onliner.news.Constants.Constant;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_USER = String.format("CREATE TABLE %s (`json` TEXT);", Constant.mTableNameUser);
    private static final String CREATE_TABLE_CREDENTIALS = String.format("CREATE TABLE %s (`json` TEXT);", Constant.mTableNameCredentials);

    public DBHelper(Context context) {
        super(context, Constant.mDBName, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_CREDENTIALS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constant.mTableNameUser);
        db.execSQL("DROP TABLE IF EXISTS " + Constant.mTableNameCredentials);
        onCreate(db);
    }

    // TEMP
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}