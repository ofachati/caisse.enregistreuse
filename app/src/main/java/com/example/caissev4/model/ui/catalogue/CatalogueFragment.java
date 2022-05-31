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
import com.example.caissev4.Datamanager;
import com.example.caissev4.MainActivity;
import com.example.caissev4.adapter.CategoryAdapter;
import com.example.caissev4.adapter.ProduitAdapter;
import com.example.caissev4.databinding.FragmentCatalogueBinding;
import com.example.caissev4.model.Category;
import com.example.caissev4.model.Produit;

import java.util.ArrayList;
import java.util.List;
import com.example.caissev4.R;
import com.google.firebase.auth.FirebaseAuth;

public class CatalogueFragment extends Fragment {

    private CatalogueViewModel homeViewModel;
    private FragmentCatalogueBinding binding;


    //----------------------
    Button btn_AddProduit;
    //
    Context thiscontext;
    //
    RecyclerView categoryRecyclerView, produitRecycler;

    CategoryAdapter categoryAdapter;
    List<Category> categoryList;

    ProduitAdapter produitAdapter;
    List<Produit> produitList;


    //
    Datamanager myDatabase ;
    //
    FirebaseAuth mAuth;
    ImageView logout;
    //----------------------

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //---
        thiscontext = container.getContext();
        myDatabase =new Datamanager(thiscontext);
        //-----

        homeViewModel =
                new ViewModelProvider(this).get(CatalogueViewModel.class);

        binding = FragmentCatalogueBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        /*
        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        */
        //---------------------------


        //hna zdt root ..........................................................................  !!!
        categoryRecyclerView = root.findViewById(R.id.categoryRecycler);
        //allCategory = root.findViewById(R.id.allCategoryImage);
        produitRecycler = root.findViewById(R.id.recently_item);

        /*
        allCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Category.class);//////// badlt chi haja wach ratkhdm? + bdlt allcatergorie b categorie
                startActivity(i);
            }
        });
*/

        // adding data to model
        categoryList = new ArrayList<>();
        categoryList.add(new Category(1, R.drawable.ic_home_fruits));
        categoryList.add(new Category(2, R.drawable.ic_home_fruits));
        categoryList.add(new Category(3, R.drawable.ic_home_fruits));
        categoryList.add(new Category(4, R.drawable.ic_home_fruits));
        categoryList.add(new Category(5, R.drawable.ic_home_fruits));
        categoryList.add(new Category(6, R.drawable.ic_home_fruits));
        categoryList.add(new Category(7, R.drawable.ic_home_fruits));
        categoryList.add(new Category(8, R.drawable.ic_home_fruits));

        // adding data to model
        produitList =myDatabase.getProducts("SELECT * FROM Produit");



        setCategoryRecycler(categoryList,thiscontext);
        setProduitRecycler(produitList,thiscontext);

        //

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
        logout = root.findViewById(R.id.imageView2);
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




    private void setCategoryRecycler(List<Category> categoryDataList, Context thiscontext) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(thiscontext, LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(thiscontext,categoryDataList);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

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