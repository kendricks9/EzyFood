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
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
//
public class OrderComplete extends AppCompatActivity {
    MyOrderAdapter mAdapter;
    Cursor cursor;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_complete);

        EzyfoodDatabaseHelper ezyfoodDatabaseHelper = new EzyfoodDatabaseHelper(this);
        try {
            db = ezyfoodDatabaseHelper.getWritableDatabase();
            cursor = db.rawQuery("SELECT TOTAL_PRICE, _id FROM TRANSACTIONS", null);
            TextView textView = findViewById(R.id.txtMessage);
            if (cursor.moveToLast()){
                textView.setText("Total: Rp. " + cursor.getString(0));
            }
            int id = cursor.getInt(1);
            cursor = db.rawQuery("SELECT _id, QTY, PRICE, NAME, PRICE FROM TRANSACTION_DETAIL WHERE TRANSACTION_ID = " + id, null);

            RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mAdapter = new MyOrderAdapter(this, cursor);
            mRecyclerView.setAdapter(mAdapter);

        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onMenuUtama(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}