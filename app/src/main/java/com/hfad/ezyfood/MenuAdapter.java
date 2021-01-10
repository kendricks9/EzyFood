package com.hfad.ezyfood;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MenuAdapter extends RecyclerView.Adapter <MenuAdapter.ViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    public MenuAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mName;
        public TextView mPrice;
        public ImageView mImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.name);
            mPrice = itemView.findViewById(R.id.price);
            mImage = itemView.findViewById(R.id.menu_image);
        }
    }

    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new MenuAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position)){
            return;
        }
        holder.mName.setText(mCursor.getString(1));
        holder.mPrice.setText(mCursor.getString(2));
        holder.mImage.setImageResource(mCursor.getInt(3));
        holder.mImage.setContentDescription(mCursor.getString(1));
//        cursor.moveToNext();
        final int id = mCursor.getInt(0);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Order.class);
                intent.putExtra(Order.EXTRA_MENUID, id);
                view.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


}
