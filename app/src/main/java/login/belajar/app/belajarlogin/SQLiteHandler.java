package login.belajar.app.belajarlogin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Isfahani on 28-Mei-16.
 * Modified from AndroidHive.info
 */
public class SQLiteHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "daengid_DaengBelajar";
    private static final String TABLE_USER = "bjr_belajarlogin";
    private static final String KEY_NAME = "nama";
    private static final String KEY_PASSWORD = "password";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_NAME + " TEXT," + KEY_PASSWORD + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_LOGIN_TABLE);
        Log.d("Isfa", "Database tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(sqLiteDatabase);
    }

    public void addUser(String name, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put(KEY_PASSWORD, password); // Password
        long id = db.insert(TABLE_USER, null, values);
        db.close();
        Log.d("Isfa", "New user inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("nama", cursor.getString(1));
            user.put("password", cursor.getString(2));
        }
        cursor.close();
        db.close();
        Log.d("Isfa", "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d("Isfa", "Deleted all user info from sqlite");
    }
}
