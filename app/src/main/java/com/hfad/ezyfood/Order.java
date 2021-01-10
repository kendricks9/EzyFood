package com.hfad.ezyfood;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class Order extends AppCompatActivity {
    public static final String EXTRA_MENUID = "menuId";
    Cursor cursor, cursor2;
    int menuId;
    SQLiteDatabase db;
    String nameText, priceText;
    private int mAmount = 1;
    private TextView mTextViewAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        menuId = (Integer)getIntent().getExtras().get(EXTRA_MENUID);

        SQLiteOpenHelper ezyfoodDatabaseHelper = new EzyfoodDatabaseHelper(this);
        try {
            db = ezyfoodDatabaseHelper.getReadableDatabase();
            cursor = db.query ("MENU",
                    new String[] {"NAME", "PRICE", "IMAGE", "CATEGORY_ID"},
                    "_id = ?",
                    new String[] {Integer.toString(menuId)},
                    null, null,null);
            if (cursor.moveToFirst()) {
                nameText = cursor.getString(0);
                priceText = cursor.getString(1);
                int photoId = cursor.getInt(2);

                TextView name = (TextView)findViewById(R.id.name);
                name.setText(nameText);

                TextView price = (TextView)findViewById(R.id.price);
                price.setText("Price: " + priceText);

                ImageView photo = (ImageView)findViewById(R.id.photo);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);

            }
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        mTextViewAmount = findViewById(R.id.order);

        Button buttonIncrease = findViewById(R.id.btnIncrease);
        Button buttonDecrease = findViewById(R.id.btnDecrease);
        Button buttonAdd = findViewById(R.id.btnAdd);

        buttonIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increase();
            }
        });

        buttonDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrease();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCart();
            }
        });
    }

    public void onMyOrder(View view){
        Intent intent = new Intent(this, MyOrder.class);
        startActivity(intent);
    }

    private void increase(){
        mAmount++;
        mTextViewAmount.setText(String.valueOf(mAmount));
    }

    private void decrease(){
        if(mAmount > 1){
            mAmount--;
            mTextViewAmount.setText(String.valueOf(mAmount));
        }
    }

    private void addCart(){
        String sQty = mTextViewAmount.getText().toString();
        int order_qty = Integer.parseInt(sQty);
        ContentValues cartDetailValues = new ContentValues();


        cursor2 = db.rawQuery("SELECT _id, QTY FROM CART_DETAILS WHERE MENU_ID = " + menuId, null);
        if(cursor2.moveToFirst() && cursor2.getString(0) != null){
            order_qty += cursor2.getInt(1);
            int id = cursor2.getInt(0);
            int total_price = order_qty * Integer.parseInt(priceText);
            cartDetailValues.put("QTY", order_qty);
            cartDetailValues.put("TOTAL_PRICE", total_price);
            db.update("CART_DETAILS", cartDetailValues, "_id = ?", new String[] {Integer.toString(id)});

        } else{
            int total_price = order_qty * Integer.parseInt(priceText);

            cartDetailValues.put("TOTAL_PRICE", total_price);
            cartDetailValues.put("MENU_ID", menuId);
            cartDetailValues.put("QTY", order_qty);
            cartDetailValues.put("CARTS_ID", 1);
            db.insert("CART_DETAILS", null, cartDetailValues);
        }

        Toast toast = Toast.makeText(this, "Item Berhasil ditambahkan", Toast.LENGTH_SHORT);
        toast.show();


    }


}