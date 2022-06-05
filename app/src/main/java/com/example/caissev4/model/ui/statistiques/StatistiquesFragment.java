package com.example.caissev4.model.ui.statistiques;

import android.content.Context;
import android.content.Intent;
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

import com.example.caissev4.DataManager;
import com.example.caissev4.MainActivity;
import com.example.caissev4.databinding.FragmentStatistiquesBinding;
import com.example.caissev4.R;
import com.example.caissev4.model.Produit;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;


public class StatistiquesFragment extends Fragment {

    private StatistiquesViewModel statistiquesViewModel;
    private FragmentStatistiquesBinding binding;


    DataManager myDatabase ;
    Context thiscontext;
    TextView tv_NombreVente,tv_TotaleVente, tv_TotaleAchat,tv_chiffreAffaire,tv_TopVendu,tv_TopRentable;
    ImageView iv_TopVendu,iv_TopRentable,logout;
    Bitmap myBitmap;
    FirebaseAuth mAuth;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        statistiquesViewModel = new ViewModelProvider(this).get(StatistiquesViewModel.class);
        binding = FragmentStatistiquesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        //---
        thiscontext = container.getContext();
        myDatabase =new DataManager(thiscontext);
        //-----



        tv_NombreVente = (TextView) root.findViewById(R.id.tv_NombreVente);
        tv_TotaleVente = (TextView) root.findViewById(R.id.tv_TotaleVente);
        tv_TotaleAchat = (TextView) root.findViewById(R.id.tv_TotaleAchat);
        tv_chiffreAffaire = (TextView) root.findViewById(R.id.tv_ChiffreAffaire);
        tv_TopVendu= (TextView) root.findViewById(R.id.txt_TopVendu);
        tv_TopRentable= (TextView) root.findViewById(R.id.txt_TopRentable);
        iv_TopVendu=(ImageView) root.findViewById(R.id.img_TopVendu);
        iv_TopRentable=(ImageView) root.findViewById(R.id.img_TopRentable);


        tv_NombreVente.setText(myDatabase.get_stat("SELECT COUNT(*) FROM Vente WHERE etat = 'vendu'"));
        tv_TotaleVente.setText(myDatabase.get_stat("SELECT SUM(prix_vente)-SUM(prix_achat) FROM Vente INNER JOIN Produit ON Produit.id_produit = Vente.id_produit WHERE etat='vendu'")+" €");
        tv_TotaleAchat.setText(myDatabase.get_stat("SELECT SUM(prix_achat) FROM Vente INNER JOIN Produit ON Produit.id_produit = Vente.id_produit WHERE etat='vendu'")+" €");
        tv_chiffreAffaire.setText(myDatabase.get_stat("SELECT SUM(prix_vente) FROM Vente INNER JOIN Produit ON Produit.id_produit = Vente.id_produit WHERE etat='vendu'")+" €");

        Produit produit= myDatabase.getProducts("SELECT * FROM Vente INNER JOIN Produit ON Produit.id_produit = Vente.id_produit GROUP BY nom ORDER BY COUNT(nom) desc limit 1;").get(0);
        tv_TopVendu.setText(produit.getName());
        File imgFile = new  File(produit.getImage_path());
        if(imgFile.exists()){
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            BitmapDrawable ob = new BitmapDrawable(myBitmap);
            iv_TopVendu.setImageDrawable(ob);
        }

        produit= myDatabase.getProducts("SELECT * FROM Vente INNER JOIN Produit ON Produit.id_produit = Vente.id_produit GROUP BY nom ORDER BY SUM(prix_vente)-SUM(prix_achat) desc limit 1;").get(0);
        tv_TopRentable.setText(produit.getName());
        imgFile = new  File(produit.getImage_path());
        if(imgFile.exists()){
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            BitmapDrawable ob = new BitmapDrawable(myBitmap);
            iv_TopRentable.setImageDrawable(ob);
        }


        //deconnection de la session
        mAuth = FirebaseAuth.getInstance();
        logout = root.findViewById(R.id.img_LogOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth.getCurrentUser() != null){
                    mAuth.signOut();
                    startActivity(new Intent(getActivity(), MainActivity.class));}
                // finish();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}