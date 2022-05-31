package com.example.caissev4.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caissev4.Datamanager;
import com.example.caissev4.model.Produit;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import com.example.caissev4.R;
import com.example.caissev4.model.Vente;

public class PanierAdapter extends RecyclerView.Adapter<PanierAdapter.myviewholder>
{
    List<Vente> liste_ventes;
    Datamanager myDatabase ;
    TextView rateview;
    public PanierAdapter(List<Vente> liste_ventes, TextView rateview) {
        this.liste_ventes = liste_ventes;
        this.rateview= rateview;
    }

    @NonNull
    @NotNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.panier_items,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PanierAdapter.myviewholder holder, @SuppressLint("RecyclerView") int position) {


       holder.recid.setText(String.valueOf(liste_ventes.get(position).getId_vente()));
       holder.recpname.setText(liste_ventes.get(position).getProduit().getName());
       holder.recpprice.setText(String.valueOf(liste_ventes.get(position).getProduit().getPrix_vente()));

        //
        myDatabase =new Datamanager(rateview.getContext());
        //

       holder.delbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               myDatabase.supprimer(liste_ventes.get(position).getId_vente());
               liste_ventes.remove(position);
               notifyItemRemoved(position);
               updateprice();
           }
       });
    }

    @Override
    public int getItemCount() {
        return liste_ventes.size();
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView recid,recpname, recpprice;
        ImageButton delbtn;
        public myviewholder(@NonNull @NotNull View itemView) {
            super(itemView);

            recid=itemView.findViewById(R.id.recid);
            recpname=itemView.findViewById(R.id.recpname);
            recpprice=itemView.findViewById(R.id.recpprice);
            delbtn=itemView.findViewById(R.id.delbtn);
        }
    }

    public void updateprice()
    {
        Double sum=0.0;
        int i;
        for(i=0; i< liste_ventes.size(); i++)
            sum=sum+ liste_ventes.get(i).getProduit().getPrix_vente();

        rateview.setText("Total Amount : Euro " + sum);
    }

}
