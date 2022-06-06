package com.example.caissev4.model.ui.panier;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caissev4.AddProduitActivity;
import com.example.caissev4.DataManager;
import com.example.caissev4.MainActivity;
import com.example.caissev4.NavigationActivity;
import com.example.caissev4.R;
import com.example.caissev4.adapter.PanierAdapter;
import com.example.caissev4.databinding.FragmentPanierBinding;
import com.example.caissev4.model.Vente;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class PanierFragment extends Fragment {

    //
    RecyclerView recview;
    TextView rateview;
    DataManager myDatabase ;
    Context thiscontext;

    //

    private PanierViewModel panierViewModel;
    private FragmentPanierBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //---
        thiscontext = container.getContext();
        myDatabase =new DataManager(thiscontext);
        FirebaseAuth mAuth;
        ImageView logout;
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
        getpanierdata(thiscontext,root);


        //
        Button boutton_checkout = root.findViewById(R.id.btn_checkout);
        boutton_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDatabase.vendre();
                Intent i = new Intent(thiscontext, NavigationActivity.class);
                startActivity(i);
                getActivity().finish();
                Toast.makeText(thiscontext, "La commande a été enregistrer", Toast.LENGTH_SHORT).show();
            }
        });

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

        //
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void getpanierdata(Context thiscontext, View root)
    {
        myDatabase =new DataManager(thiscontext);
        recview=root.findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(thiscontext));
        List<Vente> ventes=myDatabase.getallVentes();
        PanierAdapter adapter=new PanierAdapter(ventes, rateview);
        recview.setAdapter(adapter);
        int i;
        Double sum=0.0;
        for(i=0;i< ventes.size();i++)
            sum=sum+(ventes.get(i).getProduit().getPrix_vente());
        rateview.setText("Total : "+sum+"€");
    }
}