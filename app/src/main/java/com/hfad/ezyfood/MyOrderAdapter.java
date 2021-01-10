package com.hfad.ezyfood;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyOrderAdapter extends RecyclerView.Adapter <MyOrderAdapter.ViewHolder>{

    private Cursor mCursor;
    private Context mContext;

    public MyOrderAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mMenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mMenu = itemView.findViewById(R.id.full_menu);
        }
    }

    @Override
    public MyOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(!mCursor.moveToPosition(position)){
            return;
        }
        holder.mMenu.setText(mCursor.getString(0) + " x " + mCursor.getString(2) + " = " + mCursor.getString(1));
//        cursor.moveToNext();
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if(mCursor != null){
            mCursor.close();
        }

        mCursor = newCursor;

        if(newCursor != null){
            notifyDataSetChanged();
        }
    }
}
