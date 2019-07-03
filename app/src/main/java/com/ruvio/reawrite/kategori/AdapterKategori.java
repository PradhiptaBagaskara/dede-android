package com.ruvio.reawrite.kategori;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ruvio.reawrite.KategoryActivity;
import com.ruvio.reawrite.R;
import com.ruvio.reawrite.kategori.api.KategoriItem;

import java.util.List;

class  AdapterKategori extends RecyclerView.Adapter<AdapterKategori.MyViewHolder>{

    List<KategoriItem> kategoriItems;
    Context context;
    public AdapterKategori (Context mContex, List<KategoriItem> mKategoriItems){
        context = mContex;
        kategoriItems = mKategoriItems;
    }

    @Override
    public AdapterKategori.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.kategori_item, parent, false);
        MyViewHolder mViewHolder = new MyViewHolder(mView);
        return mViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterKategori.MyViewHolder holder, final int position) {
        holder.mTextViewId.setText(kategoriItems.get(position).getKategori());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, KategoryActivity.class);
                intent.putExtra("ID_Kategori", kategoriItems.get(position).getKategori());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewId;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextViewId = (TextView) itemView.findViewById(R.id.txtKategori);
        }
    }
}