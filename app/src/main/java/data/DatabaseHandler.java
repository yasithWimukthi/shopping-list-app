package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import util.Constants;

public class DatabaseHandler extends SQLiteOpenHelper {
    private final Context context;

    public DatabaseHandler(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEM_TABLE = "CREATE TABLE " + Constants.TABLE_NAME +" ("
                + Constants.COLUMN_ID + " INTEGER PRIMARY KEY ,"
                +Constants.COLUMN_ITEM_NAME + " TEXT ,"
                + Constants.COLUMN_COLOR + " TEXT ,"
                + Constants.COLUMN_ITEM_QUANTITY + " TEXT,"
                + Constants.COLUMN_SIZE + " NUMBER ,"
                + Constants.COLUMN_DATE_ADDED + " LONG);" ;

        db.execSQL(CREATE_ITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(db);
    }
}
