package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        db.close();
    }

    public ShoppingItem getItem(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                Constants.TABLE_NAME,
                new String[]{
                        Constants.COLUMN_ID,
                        Constants.COLUMN_ITEM_NAME,
                        Constants.COLUMN_COLOR,
                        Constants.COLUMN_SIZE,
                        Constants.COLUMN_ITEM_QUANTITY,
                        Constants.COLUMN_DATE_ADDED
                },
                Constants.COLUMN_ID + "=?",
                new String[] {String.valueOf(id)},
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            cursor.moveToFirst();
            ShoppingItem item = new ShoppingItem();

            item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_ID))));
            item.setItemName(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_ITEM_NAME)));
            item.setItemColor(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_COLOR)));
            item.setQuantity(cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_ITEM_QUANTITY)));
            item.setSize(cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_SIZE)));

            DateFormat dateFormat = DateFormat.getDateInstance();
            String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.COLUMN_DATE_ADDED))).getTime());
            item.setItemAddedDate(formattedDate);
            return item;
        }

        return null;
    }

    public List<ShoppingItem> getAllItems() {
        List<ShoppingItem> result = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                Constants.TABLE_NAME,
                new String[]{
                        Constants.COLUMN_ID,
                        Constants.COLUMN_ITEM_NAME,
                        Constants.COLUMN_COLOR,
                        Constants.COLUMN_SIZE,
                        Constants.COLUMN_ITEM_QUANTITY,
                        Constants.COLUMN_DATE_ADDED
                },
                null,
                null,
                null,
                null,
                Constants.COLUMN_DATE_ADDED + " DESC"
        );

        if(cursor.moveToFirst()){
            do{
                ShoppingItem item = new ShoppingItem();

                item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_ID))));
                item.setItemName(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_ITEM_NAME)));
                item.setItemColor(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_COLOR)));
                item.setQuantity(cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_ITEM_QUANTITY)));
                item.setSize(cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_SIZE)));

                DateFormat dateFormat = DateFormat.getDateInstance();
                String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.COLUMN_DATE_ADDED))).getTime());
                item.setItemAddedDate(formattedDate);

                result.add(item);

            }while(cursor.moveToNext());
        }
        db.close();
        return  result;
    }

    public int updateItem(ShoppingItem item){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.COLUMN_ITEM_NAME,item.getItemName());
        values.put(Constants.COLUMN_COLOR,item.getItemColor());
        values.put(Constants.COLUMN_ITEM_QUANTITY,item.getQuantity());
        values.put(Constants.COLUMN_SIZE,item.getSize());
        values.put(Constants.COLUMN_DATE_ADDED,java.lang.System.currentTimeMillis());

        int columnNumber = db.update(
                Constants.TABLE_NAME,
                values,
                Constants.COLUMN_ID + "=?",
                new String[] {String.valueOf(item.getId())}
        );

        Toast.makeText(context,"Data updated successfully.",Toast.LENGTH_SHORT).show();
        db.close();
        return columnNumber;
    }

    public void deleteItem(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(
                Constants.TABLE_NAME,
                Constants.COLUMN_ID + "+?",
                new String[] {String.valueOf(id)}
        );
        db.close();
        Toast.makeText(context,"Data deleted successfully.",Toast.LENGTH_SHORT).show();
    }

    public int getItemCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME;

        Cursor cursor = db.rawQuery(countQuery,null);
        return cursor.getCount();
    }
}
