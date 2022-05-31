package com.example.caissev4.model.ui.statistiques;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.caissev4.Datamanager;
import com.example.caissev4.databinding.FragmentStatistiquesBinding;
import com.example.caissev4.R;
import com.example.caissev4.model.Produit;

import java.io.File;


public class StatistiquesFragment extends Fragment {

    private StatistiquesViewModel statistiquesViewModel;
    private FragmentStatistiquesBinding binding;


    Datamanager myDatabase ;
    Context thiscontext;
    TextView tv_NombreVente;
    TextView tv_TotaleVente;
    TextView tv_TotaleAchat;
    TextView tv_chiffreAffaire;
    TextView tv_TopVendu;
    TextView tv_TopRentable;
    ImageView iv_TopVendu;
    ImageView iv_TopRentable;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        statistiquesViewModel =
                new ViewModelProvider(this).get(StatistiquesViewModel.class);

        binding = FragmentStatistiquesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        //---
        thiscontext = container.getContext();
        myDatabase =new Datamanager(thiscontext);
        //-----



        tv_NombreVente = (TextView) root.findViewById(R.id.tv_NombreVente);
        tv_TotaleVente = (TextView) root.findViewById(R.id.tv_TotaleVente);
        tv_TotaleAchat = (TextView) root.findViewById(R.id.tv_TotaleAchat);
        tv_chiffreAffaire = (TextView) root.findViewById(R.id.tv_ChiffreAffaire);
        tv_TopVendu= (TextView) root.findViewById(R.id.txt_TopVendu);
        tv_TopRentable= (TextView) root.findViewById(R.id.txt_TopRentable);
        iv_TopVendu=(ImageView) root.findViewById(R.id.img_TopVendu);
        iv_TopRentable=(ImageView) root.findViewById(R.id.img_TopRentable);


        tv_NombreVente.setText(myDatabase.requete_au_choix("SELECT COUNT(*) FROM Vente WHERE etat = 'vendu'"));
        tv_TotaleVente.setText(myDatabase.requete_au_choix("SELECT SUM(prix_vente)-SUM(prix_achat) FROM Vente INNER JOIN Produit ON Produit.id_produit = Vente.id_produit WHERE etat='vendu'")+" €");
        tv_TotaleAchat.setText(myDatabase.requete_au_choix("SELECT SUM(prix_achat) FROM Vente INNER JOIN Produit ON Produit.id_produit = Vente.id_produit WHERE etat='vendu'")+" €");
        tv_chiffreAffaire.setText(myDatabase.requete_au_choix("SELECT SUM(prix_vente) FROM Vente INNER JOIN Produit ON Produit.id_produit = Vente.id_produit WHERE etat='vendu'")+" €");

        Produit produit= myDatabase.getProducts("SELECT * FROM Vente INNER JOIN Produit ON Produit.id_produit = Vente.id_produit GROUP BY nom ORDER BY COUNT(nom) desc limit 1;").get(0);
        tv_TopVendu.setText(produit.getName());
        File imgFile = new  File(produit.getBigimageurl());
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            BitmapDrawable ob = new BitmapDrawable(myBitmap);
            iv_TopVendu.setImageDrawable(ob);
        }

        produit= myDatabase.getProducts("SELECT * FROM Vente INNER JOIN Produit ON Produit.id_produit = Vente.id_produit GROUP BY nom ORDER BY SUM(prix_vente)-SUM(prix_achat) desc limit 1;").get(0);
        tv_TopRentable.setText(produit.getName());
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            BitmapDrawable ob = new BitmapDrawable(myBitmap);
            iv_TopRentable.setImageDrawable(ob);
        }

        /*
        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

       */

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}