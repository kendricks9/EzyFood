package com.hfad.ezyfood;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class EzyfoodDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "ezyfood";
    private static final int DB_VERSION = 2;

    EzyfoodDatabaseHelper(Context context) { super(context, DB_NAME, null, DB_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private static void insertUser(SQLiteDatabase db, String name, int e_money){
        ContentValues userValues = new ContentValues();
        userValues.put("NAME", name);
        userValues.put("E_MONEY", e_money);
        db.insert("USER", null, userValues);
    }

    private static void insertCategory(SQLiteDatabase db, String name){
        ContentValues categoryValues = new ContentValues();
        categoryValues.put("NAME", name);
        db.insert("CATEGORY", null, categoryValues);
    }


    private static void insertMenu(SQLiteDatabase db, String name, int price, int id, int image){
        ContentValues menuValues = new ContentValues();
        menuValues.put("NAME", name);
        menuValues.put("PRICE", price);
        menuValues.put("CATEGORY_ID", id);
        menuValues.put("IMAGE", image);
        db.insert("MENU", null, menuValues);
    }

    private static void insertRestaurant(SQLiteDatabase db, String name, String desc, double latitude, double logitude){
        ContentValues restaurantValues = new ContentValues();
        restaurantValues.put("NAME", name);
        restaurantValues.put("DESCRIPTION", desc);
        restaurantValues.put("LATITUDE", latitude);
        restaurantValues.put("LOGITUDE", logitude);
        db.insert("RESTAURANT", null, restaurantValues);
    }

    private static void insertRestaurantDetail(SQLiteDatabase db, String qty, int resto_id, int menu_id){
        ContentValues restaurantDetailValues = new ContentValues();
        restaurantDetailValues.put("QTY", qty);
        restaurantDetailValues.put("RESTAURANT_ID", resto_id);
        restaurantDetailValues.put("MENU_ID", menu_id);
        db.insert("RESTAURANT_DETAIL", null, restaurantDetailValues);
    }

    private static void insertCarts(SQLiteDatabase db, int total_price, int id){
        ContentValues cartValues = new ContentValues();
        cartValues.put("TOTAL_PRICE", total_price);
        cartValues.put("USER_ID", id);
        db.insert("CARTS", null, cartValues);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < 1){
            db.execSQL("CREATE TABLE USER(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "E_MONEY INTEGER);");
            db.execSQL("CREATE TABLE CATEGORY(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT);");
            db.execSQL("CREATE TABLE MENU(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "PRICE INTEGER, "
                    + "IMAGE INTEGER, "
                    + "CATEGORY_ID INTEGER,"
                    + "FOREIGN KEY (CATEGORY_ID) REFERENCES CATEGORY(_id));");
            db.execSQL("CREATE TABLE RESTAURANT(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "NAME TEXT," +
                    "DESCRIPTION TEXT," +
                    "LATITUDE DOUBLE," +
                    "LOGITUDE DOUBLE);");
            db.execSQL("CREATE TABLE RESTAURANT_DETAIL(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "QTY INTEGER," +
                    "MENU_ID INTEGER," +
                    "RESTAURANT_ID INTEGER," +
                    "FOREIGN KEY (RESTAURANT_ID) REFERENCES RESTAURANT(_id),"+
                    "FOREIGN KEY (MENU_ID) REFERENCES MENU(_id));");
            db.execSQL("CREATE TABLE CARTS("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "USER_ID INTEGER," +
                    "TOTAL_PRICE INTEGER,"
                    + "FOREIGN KEY (USER_ID) REFERENCES USER(_id)"
                    + ");"
            );
            db.execSQL("CREATE TABLE CART_DETAILS(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "PRICE INTEGER, "
                    + "QTY INTEGER,"
                    + "CARTS_ID INTEGER,"
                    + "FOREIGN KEY (CARTS_ID) REFERENCES CARTS(_id)"
                    + ");"
            );

            insertUser(db, "Kendrick Saputra", 100000);

            insertCategory(db, "Drinks");
            insertCategory(db, "Snacks");
            insertCategory(db, "Foods");
            insertCategory(db, "TopUp");

            insertMenu(db, "Air Mineral",5000, 1, R.drawable.air_mineral);
            insertMenu(db, "Jus Apel", 14000, 1, R.drawable.jus_apel);
            insertMenu(db, "Jus Mangga", 20000, 1, R.drawable.jus_mangga);
            insertMenu(db, "Jus Alpukat",18000, 1, R.drawable.jus_alpukat);

            insertMenu(db, "Chitato",8000, 2, R.drawable.chitato);
            insertMenu(db, "Lays", 7000, 2, R.drawable.lays);
            insertMenu(db, "Beng-Beng", 3000, 2, R.drawable.beng_beng);
            insertMenu(db, "Qtela",5000, 2, R.drawable.qtela);

            insertMenu(db, "Nasi Goreng",15000, 3, R.drawable.nasi_goreng);
            insertMenu(db, "Mie Goreng", 12000, 3, R.drawable.mie_goreng);
            insertMenu(db, "Sate Ayam", 20000, 3, R.drawable.sate);
            insertMenu(db, "Pempek",5000, 3, R.drawable.pempek);

            insertRestaurant(db, "ezyFood Alam Sutera", "dekat Binus", -6.222492, 106.648953);
            insertRestaurant(db, "ezyFood BSD", "dekat Aeon", -6.305984267861596, 106.64178335251724);
            insertRestaurant(db, "ezyFood Bintaro", "dekat XChange",  -6.285772520747877, 106.72641318937653);

            insertCarts(db, 0, 1);



        }
    }


}
