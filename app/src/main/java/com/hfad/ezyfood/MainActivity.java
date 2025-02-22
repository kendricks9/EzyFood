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
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor cursor, resto_cursor;
    public static final String EXTRA_RESTAURANTID = "restaurantId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteOpenHelper ezyfoodDatabaseHelper = new EzyfoodDatabaseHelper(this);
        ListView listCategories = (ListView) findViewById(R.id.list_options);
        Bundle extras = getIntent().getExtras();
        try {
            db = ezyfoodDatabaseHelper.getReadableDatabase();
            cursor = db.query("CATEGORY",
                    new String[]{"_id", "NAME"},
                    null, null, null, null, null);

            if(extras != null){
                int restaurantId = (Integer) getIntent().getExtras().get(EXTRA_RESTAURANTID);
                resto_cursor = db.query("RESTAURANT",
                        new String[]{"_id","NAME"}, "_id=?", new String[] {Integer.toString(restaurantId)}, null, null, null);
            } else {
                resto_cursor = db.query("RESTAURANT",
                        new String[]{"_id","NAME"}, "_id=1", null, null, null, null);
            }

            if(resto_cursor.moveToFirst()){
                String nameText = resto_cursor.getString(1);
                TextView name = (TextView) findViewById(R.id.txtResto);
                name.setText(nameText);
            }

            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1},
                    0);
            listCategories.setAdapter(listAdapter);
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> listCategories,
                                            View itemView,
                                            int position,
                                            long id) {
                        // Jika User Memilih pilihan TopUp
                        if(id==4){
                            Intent intent = new Intent(MainActivity.this,
                                    TopUp.class);
                            startActivity(intent);
                            return;
                        }

                        Intent intent = new Intent(MainActivity.this,
                                DisplayMenu.class);
                        intent.putExtra(DisplayMenu.EXTRA_CATEGORYID, (int) id);
                        startActivity(intent);
                    }
                };

        listCategories.setOnItemClickListener(itemClickListener);

    }

    public void changeResto(View view){
        Intent intent = new Intent(this, Resto.class);
        startActivity(intent);
    }

    public void onMyOrder(View view){
        Intent intent = new Intent(this, MyOrder.class);
        startActivity(intent);
    }

    public void onMaps(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}