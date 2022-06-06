package com.example.caissev4.model.ui.catalogue;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caissev4.AddProduitActivity;
import com.example.caissev4.DataManager;
import com.example.caissev4.MainActivity;
import com.example.caissev4.adapter.ProduitAdapter;
import com.example.caissev4.databinding.FragmentCatalogueBinding;
import com.example.caissev4.model.Produit;

import java.util.List;
import com.example.caissev4.R;
import com.google.firebase.auth.FirebaseAuth;

public class CatalogueFragment extends Fragment {

    private CatalogueViewModel homeViewModel;
    private FragmentCatalogueBinding binding;


    Button btn_AddProduit;
    Context thiscontext;
    RecyclerView produitRecycler;
    ProduitAdapter produitAdapter;
    List<Produit> produitList;
    DataManager myDatabase ;
    FirebaseAuth mAuth;
    ImageView logout;
    //----------------------

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        homeViewModel =
                new ViewModelProvider(this).get(CatalogueViewModel.class);

        binding = FragmentCatalogueBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        produitRecycler = root.findViewById(R.id.recently_item);
        thiscontext = container.getContext();
        myDatabase =new DataManager(thiscontext);



        //recupérer les données et remplir RecyclerView
        produitList =myDatabase.getProducts("SELECT * FROM Produit");
        setProduitRecycler(produitList,thiscontext);


        //aller a la vue add produit
        btn_AddProduit = root.findViewById(R.id.btn_AddProduit);
        btn_AddProduit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchvue_AddProduit(thiscontext);
            }});
        //---------------------------

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


        //Operation de recherche
        ((EditText)root.findViewById(R.id.editText_Recherche)).addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {


                setProduitRecycler(myDatabase.rechercher(String.valueOf(((EditText) root.findViewById(R.id.editText_Recherche)).getText())),thiscontext);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        return root;
    }



//remplir RecyclerView
    private void setProduitRecycler(List<Produit> produitDataList, Context thiscontext) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(thiscontext, LinearLayoutManager.HORIZONTAL, false);
        produitRecycler.setLayoutManager(layoutManager);
        produitAdapter = new ProduitAdapter(thiscontext, produitDataList);
        produitRecycler.setAdapter(produitAdapter);
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void switchvue_AddProduit(Context thiscontext) {
        Intent switchActivityIntent = new Intent(thiscontext, AddProduitActivity.class);
        startActivity(switchActivityIntent);
        getActivity().finish();
    }

}