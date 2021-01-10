package com.hfad.ezyfood;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class Order extends AppCompatActivity {
    public static final String EXTRA_MENUID = "menuId";
    Cursor cursor;
    int menuId;
    SQLiteDatabase db;
    String nameText, priceText;

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
    }

    public void OnOrderMore(View view){
        addCart();

        int category_id = cursor.getInt(3);
        Intent intent = new Intent(this, DisplayMenu.class);
        intent.putExtra(DisplayMenu.EXTRA_CATEGORYID, (int) category_id);
        startActivity(intent);
    }

    public void onMyOrder(View view){
        addCart();
        Intent intent = new Intent(this, MyOrder.class);
        startActivity(intent);
    }

    private void addCart(){
        TextView order = findViewById(R.id.order);
        String sQty = order.getText().toString();
        int order_qty = Integer.parseInt(sQty);
        int total_price = order_qty * Integer.parseInt(priceText);

        ContentValues cartDetailValues = new ContentValues();
        cartDetailValues.put("TOTAL_PRICE", total_price);
        cartDetailValues.put("MENU_ID", menuId);
        cartDetailValues.put("QTY", order_qty);
        cartDetailValues.put("CARTS_ID", 1);
        db.insert("CART_DETAILS", null, cartDetailValues);
    }


}