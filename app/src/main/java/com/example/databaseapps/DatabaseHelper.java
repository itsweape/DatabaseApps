package com.example.databaseapps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "stationery-db";
    private static final int DATABASE_VERSION = 1;

    private static final String PRODUCT_TABLE = "stationery";
    private static final String PRODUCT_ID ="id";
    private static final String PRODUCT_NAME ="name";
    private static final String PRODUCT_PRICE ="price";


    //constructor
    public  DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //fungsi untuk membuat tabel pada database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_STATIONERY = "CREATE TABLE " + PRODUCT_TABLE + "(" + PRODUCT_ID + " INTEGER PRIMARY KEY, "
                + PRODUCT_NAME + " TEXT, " +
                PRODUCT_PRICE + " INTEGER)";
        sqLiteDatabase.execSQL(CREATE_TABLE_STATIONERY);
    }

    //fungsi untuk mengecek apakah tabel sudah ada sebelumnya
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(sqLiteDatabase);
    }

    //fungsi untuk menambahkan data pada tabel
    public void insertRecord(Product product){
        SQLiteDatabase db = getWritableDatabase();

        //CONVERT product ke contentvalues
        ContentValues values = new ContentValues();
        values.put(PRODUCT_NAME, product.getName());
        values.put(PRODUCT_PRICE, product.getPrice());

        db.insert(PRODUCT_TABLE, null, values);
        db.close();
    }

    //fungsi untuk menampilkan seluruh data yang ada pada tabel
    public ArrayList<Product> getAllProduct(){
        ArrayList<Product> allProduct = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * from " + PRODUCT_TABLE;
        Cursor cursor = db.rawQuery(selectQuery, null);

        //mengambil semua data dengan looping
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String  name = cursor.getString(1);
                int price = cursor.getInt(2);

                allProduct.add(new Product(id, name, price));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return allProduct;
    }

    //fungsi untuk mengupdate data pada tabel
    public int updateProduct(Product product){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRODUCT_NAME, product.getName());
        values.put(PRODUCT_PRICE, product.getPrice());

        return db.update(PRODUCT_TABLE, values, PRODUCT_ID + "=?",
                new String[] {String.valueOf(product.getId())});
    }

    //fungsi untuk menghapus data pada tabel
    public void deleteProduct(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(PRODUCT_TABLE, PRODUCT_ID + "=?",
                new String[] {String.valueOf(id)});

        db.close();
    }


}
