package com.hfad.ezyfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
//
public class OrderComplete extends AppCompatActivity {
//    RecyclerView mRecyclerView;
//    RecyclerView.LayoutManager mLayoutManager;
//    RecyclerView.Adapter mAdapter;
//    ArrayList<Barang> newArray;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_order_complete);
//        Intent intent = getIntent();
//        String message =intent.getStringExtra("totHarga");
//        TextView textView = findViewById(R.id.txtTotal2);
//        textView.setText("Total: Rp. "+message);
//        newArray = Transaction.getInstance().getBarangs();
//
//        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        mRecyclerView.setHasFixedSize(true);
//        mLayoutManager = new LinearLayoutManager(this);
//        mAdapter = new MainAdapter2(newArray);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setAdapter(mAdapter);
//
//    }

    public void onMenuUtama(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}