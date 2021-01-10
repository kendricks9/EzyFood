package com.hfad.ezyfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MyOrder extends AppCompatActivity {

    MyOrderAdapter mAdapter;
    Cursor cursor;
    SQLiteDatabase db;

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
                textView.setText("Total: Rp. " + cursor.getString(0));
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
        Intent intent = new Intent(this, OrderComplete.class);
        startActivity(intent);
    }

    private Cursor getAllItems(){
        return db.rawQuery("SELECT CART_DETAILS._ID, CART_DETAILS.QTY, CART_DETAILS.TOTAL_PRICE, MENU.NAME, MENU.PRICE FROM CART_DETAILS " +
                "INNER JOIN MENU ON CART_DETAILS.MENU_ID=MENU._ID", null);
    }

}