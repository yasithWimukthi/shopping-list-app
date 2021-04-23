package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import model.ShoppingItem;
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
    
    public void addItem(ShoppingItem item){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.COLUMN_ITEM_NAME,item.getItemName());
        values.put(Constants.COLUMN_COLOR,item.getItemColor());
        values.put(Constants.COLUMN_ITEM_QUANTITY,item.getQuantity());
        values.put(Constants.COLUMN_SIZE,item.getSize());
        values.put(Constants.COLUMN_DATE_ADDED,java.lang.System.currentTimeMillis());

        db.insert(Constants.TABLE_NAME,null,values);
        Toast.makeText(context,"Data inserted successfully.",Toast.LENGTH_SHORT).show();
    }
}
