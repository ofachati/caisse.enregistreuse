package com.example.caissev4.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caissev4.Datamanager;
import com.example.caissev4.ProductActivity;
import com.example.caissev4.R;
import com.example.caissev4.model.Produit;

import java.io.File;
import java.util.List;

public class ProduitAdapter extends RecyclerView.Adapter<ProduitAdapter.ProduitViewHolder> {

    Context context;
    List<Produit> produitList;
    Datamanager myDatabase ;


    public ProduitAdapter(Context context, List<Produit> produitList) {
        this.context = context;
        this.produitList = produitList;
    }

    @NonNull
    @Override
    public ProduitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.produit_items, parent, false);

        return new ProduitViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ProduitViewHolder holder, @SuppressLint("RecyclerView") final int position) {//hna bdlt chi haja

        holder.name.setText(produitList.get(position).getName());
        holder.description.setText(produitList.get(position).getDescription());
        holder.price.setText(String.valueOf(produitList.get(position).getPrix_vente()));
        holder.qty.setText(String.valueOf(produitList.get(position).getQuantity()));

        File imgFile = new  File(produitList.get(position).getBigimageurl());
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            BitmapDrawable ob = new BitmapDrawable(myBitmap);
            holder.bg.setBackground(ob);
        }

        myDatabase =new Datamanager(context);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myDatabase.ajout_dans_catalogue(produitList.get(position).getId());

            }
        });
    }

    @Override
    public int getItemCount() {
        return produitList.size();
    }

    public  static class ProduitViewHolder extends RecyclerView.ViewHolder{

        TextView name, description, price, qty, unit;
        ConstraintLayout bg;

        public ProduitViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.product_name);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            qty = itemView.findViewById(R.id.qty);
            unit = itemView.findViewById(R.id.unit);
            bg = itemView.findViewById(R.id.recently_layout);

        }
    }

}
