package com.example.caissev4.model.ui.panier;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caissev4.Datamanager;
import com.example.caissev4.R;
import com.example.caissev4.adapter.PanierAdapter;
import com.example.caissev4.databinding.FragmentPanierBinding;
import com.example.caissev4.model.Produit;
import com.example.caissev4.model.Vente;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

public class PanierFragment extends Fragment {

    //
    RecyclerView recview;
    TextView rateview;
    Datamanager myDatabase ;
    Context thiscontext;

    //

    private PanierViewModel panierViewModel;
    private FragmentPanierBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //---
        thiscontext = container.getContext();
        myDatabase =new Datamanager(thiscontext);

        //-----

        
        
        panierViewModel =
                new ViewModelProvider(this).get(PanierViewModel.class);

        binding = FragmentPanierBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
    /*
        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });



     */
        //
        rateview=root.findViewById(R.id.rateview);
        getroomdata(thiscontext,root);


        //
        Button boutton_checkout = root.findViewById(R.id.btn_checkout);

        boutton_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDatabase.vendre();
            }
        });
        //
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void getroomdata(Context thiscontext,View root)
    {

        myDatabase =new Datamanager(thiscontext);

        recview=root.findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(thiscontext));

        List<Vente> ventes=myDatabase.getallVentes();

        PanierAdapter adapter=new PanierAdapter(ventes, rateview);
        recview.setAdapter(adapter);

        int i;
        Double sum=0.0;
        for(i=0;i< ventes.size();i++)
            sum=sum+(ventes.get(i).getProduit().getPrix_vente());

        rateview.setText("Total Amount : "+sum+"€");
    }
}