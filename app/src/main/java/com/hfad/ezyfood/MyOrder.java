package com.hfad.ezyfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyOrder extends AppCompatActivity {

    MyOrderAdapter mAdapter;
    Cursor cursor, cursor2;
    SQLiteDatabase db;
    int total_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        EzyfoodDatabaseHelper ezyfoodDatabaseHelper = new EzyfoodDatabaseHelper(this);
        try {
            db = ezyfoodDatabaseHelper.getWritableDatabase();
            cursor = db.rawQuery("SELECT SUM(TOTAL_PRICE) FROM CART_DETAILS", null);
            TextView textView = findViewById(R.id.txtTotal);
            TextView textView2 = findViewById(R.id.txtMessage);
            if(cursor.moveToFirst() && cursor.getString(0) != null){
                total_price = cursor.getInt(0);
                textView.setText("Total: Rp. " + total_price);
                textView2.setText("Swipe right/left to delete item on cart");
            }
            RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mAdapter = new MyOrderAdapter(this, getAllItems());
            mRecyclerView.setAdapter(mAdapter);

            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    removeItem((long) viewHolder.itemView.getTag());
                }
            }).attachToRecyclerView(mRecyclerView);

        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void removeItem(long id){
        db.delete("CART_DETAILS", "_id" + "=" + id, null);
        mAdapter.swapCursor(getAllItems());
    }

    public void onPayNow(View view){
        cursor = getAllItems();

        if(cursor.moveToFirst()){
            ContentValues cv = new ContentValues();

            cursor2 = db.rawQuery("SELECT E_MONEY FROM USER WHERE _id = 1", null);
            if(cursor2.moveToFirst()){
                int saldo = cursor2.getInt(0) - total_price;
                if(saldo >= 0){
                    cv.put("E_MONEY", saldo);
                    db.update("USER", cv, "_id = 1", null);
                    cv.clear();

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String date = sdf.format(new Date());


                    cv.put("USER_ID", 1);
                    cv.put("TOTAL_PRICE", total_price);
                    cv.put("RESTAURANT_NAME", "-");
                    cv.put("TRANSACTION_DATE", date);

                    db.insert("TRANSACTIONS", null, cv);
                    cv.clear();

                    cursor2 = db.rawQuery("SELECT _id FROM TRANSACTIONS", null);
                    cursor2.moveToLast();

                    Cursor cursor3 = cursor;
                    int i = 0;
                    while(i < cursor.getCount()){
                        cv.put("NAME", cursor3.getString(3));
                        cv.put("PRICE", cursor3.getString(2));
                        cv.put("QTY", cursor3.getString(1));
                        cv.put("TRANSACTION_ID", cursor2.getString(0));
                        db.insert("TRANSACTION_DETAIL", null, cv);
                        cv.clear();
                        cursor3.moveToNext();
                        i++;
                    }

                    db.execSQL("DELETE FROM CART_DETAILS");

                    Toast toast = Toast.makeText(this, "Sisa saldo: Rp " + saldo, Toast.LENGTH_SHORT);
                    toast.show();

                    Intent intent = new Intent(this, OrderComplete.class);
                    startActivity(intent);
                } else{
                    Toast toast = Toast.makeText(this, "Saldo Tidak Cukup", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

        } else{
            Toast toast = Toast.makeText(this, "Cart Kosong", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    private Cursor getAllItems(){
        return db.rawQuery("SELECT CART_DETAILS._ID, CART_DETAILS.QTY, CART_DETAILS.TOTAL_PRICE, MENU.NAME, MENU.PRICE FROM CART_DETAILS " +
                "INNER JOIN MENU ON CART_DETAILS.MENU_ID=MENU._ID", null);
    }

}