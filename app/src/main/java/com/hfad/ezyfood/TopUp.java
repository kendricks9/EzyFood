package com.hfad.ezyfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class TopUp extends AppCompatActivity {
    SQLiteDatabase db;
    String e_moneyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);

        SQLiteOpenHelper ezyfoodDatabaseHelper = new EzyfoodDatabaseHelper(this);
        try {
            db = ezyfoodDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("USER",
                    new String[]{"NAME", "E_MONEY"},
                    "_id = 1",
                    null, null, null, null);
            if (cursor.moveToFirst()) {
                String nameText = cursor.getString(0);
                e_moneyText = cursor.getString(1);

                TextView name = (TextView)findViewById(R.id.name);
                name.setText(nameText);

                TextView price = (TextView)findViewById(R.id.e_money);
                price.setText("E_Money: " + e_moneyText);

            }
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onTopUp(View view){
        TextView topUp = findViewById(R.id.topUp);
        String tUp = topUp.getText().toString();
        int tu = Integer.parseInt(tUp);
        if(tu >= 2000000){
            Toast toast = Toast.makeText(this, "Max 2.000.000", Toast.LENGTH_SHORT);
            toast.show();
        } else{
            tu += Integer.parseInt(e_moneyText);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            ContentValues cv = new ContentValues();
            cv.put("E_MONEY", tu);
            db.update("USER", cv, "_id = 1", null);
            Toast toast = Toast.makeText(this, "Top Up Success", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

}