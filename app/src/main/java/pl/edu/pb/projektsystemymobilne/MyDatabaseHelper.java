package pl.edu.pb.projektsystemymobilne;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME =  "Anilist0.5.db";
    private static final int DATABASE_VERSION =  1;

    private static final String TABLE_NAME = "Anilist0";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_ANIME_ID = "anime_id";
    private static final String COLUMN_ANIME_NAME = "name";
    private static final String COLUMN_EPISODES_MAX = "episodes";
    private static final String COLUMN_EPISODES_WATCHED = "watched";
    private static final String COLUMN_LOCALIZATION_X = "x";
    private static final String COLUMN_LOCALIZATION_Y = "y";
    private static final String COLUMN_SCORE = "score";
    private static final String COLUMN_STATUS = "status";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ANIME_ID + " INTEGER, " +
                COLUMN_ANIME_NAME + " STRING, " +
                COLUMN_EPISODES_MAX + " INTEGER, " +
                COLUMN_EPISODES_WATCHED + " INTEGER, " +
                COLUMN_LOCALIZATION_X + " DOUBLE, " +
                COLUMN_LOCALIZATION_Y + " DOUBLE, " +
                COLUMN_SCORE + " INTEGER, " +
                COLUMN_STATUS + " STRING);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addAnime(int id, String name, int score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ANIME_ID, id);
        cv.put(COLUMN_ANIME_NAME, name);
        cv.put(COLUMN_SCORE, score);
        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1){
            Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
    Cursor readAllData(){
        String query = "SELECT * FROM " +  TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}
