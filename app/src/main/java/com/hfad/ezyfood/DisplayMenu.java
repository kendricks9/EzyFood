package com.hfad.ezyfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class DisplayMenu extends AppCompatActivity {
    public static final String EXTRA_CATEGORYID = "categoryId";
    MenuAdapter mAdapter;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_menu);

        int categoryId = (Integer)getIntent().getExtras().get(EXTRA_CATEGORYID);
        SQLiteOpenHelper ezyfoodDatabaseHelper = new EzyfoodDatabaseHelper(this);
        try {
            SQLiteDatabase db = ezyfoodDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT _id, NAME, PRICE, IMAGE FROM MENU WHERE CATEGORY_ID="+categoryId, null);
            if(cursor.moveToFirst()){
                mRecyclerView = findViewById(R.id.recycler_view);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                mAdapter = new MenuAdapter(this, cursor);
                mRecyclerView.setAdapter(mAdapter);
            }

        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    public void onMyOrder(View view){
        Intent intent = new Intent(this, MyOrder.class);
        startActivity(intent);
    }
}