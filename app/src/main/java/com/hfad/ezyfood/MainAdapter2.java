//package com.hfad.ezyfood;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//
//public class MainAdapter2 extends RecyclerView.Adapter <MainAdapter2.ViewHolder>{
//
//    ArrayList<Barang> newArray;
//
//    public MainAdapter2(ArrayList<Barang> newArray) {
//        this.newArray = newArray;
//    }
//
//    @Override
//    public MainAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row2, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.mBarang.setText(newArray.get(position).getPesanan() + " x " + newArray.get(position).getQuantity());
//    }
//
//    @Override
//    public int getItemCount() {
//        return newArray.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public TextView mBarang;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            mBarang = itemView.findViewById(R.id.full_barang);
//        }
//    }
//}
