package com.hfad.ezyfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class DisplayMenu extends AppCompatActivity {
    public static final String EXTRA_CATEGORYID = "categoryId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_menu);

        int categoryId = (Integer)getIntent().getExtras().get(EXTRA_CATEGORYID);
        ListView listMenu =  (ListView) findViewById(R.id.list_menu);
        SQLiteOpenHelper ezyfoodDatabaseHelper = new EzyfoodDatabaseHelper(this);
        try {
            SQLiteDatabase db = ezyfoodDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("MENU",
                    new String[]{"_id", "NAME", "PRICE"},
                    "CATEGORY_ID = ?",
                    new String[] {Integer.toString(categoryId)},
                    null, null, null);
            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1},
                    0);
            listMenu.setAdapter(listAdapter);
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> listMenu,
                                            View itemView,
                                            int position,
                                            long id) {
                        //Pass the drink the user clicks on to DrinkActivity
                        Intent intent = new Intent(DisplayMenu.this,
                                Order.class);
                        intent.putExtra(Order.EXTRA_MENUID, (int) id);
                        startActivity(intent);
                    }
                };

        listMenu.setOnItemClickListener(itemClickListener);
    }

    public void onMyOrder(View view){
        Intent intent = new Intent(this, MyOrder.class);
        startActivity(intent);
    }
}