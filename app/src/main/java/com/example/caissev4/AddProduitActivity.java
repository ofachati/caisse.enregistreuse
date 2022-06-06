package com.example.caissev4;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddProduitActivity extends AppCompatActivity {

    ImageView imageview_image, back;
    TextView edittext_Nom, edittext_prixVente,edittext_prixAchat, edittext_description, edittext_quantite;
    DataManager myDatabase =new DataManager(this);
    Intent intent ;
    public  static final int RequestPermissionCode  = 1 ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_produit);

        edittext_Nom = findViewById(R.id.edittext_TitreProduit);
        edittext_description = findViewById(R.id.edittext_DescProduit);
        edittext_prixVente = findViewById(R.id.edittext_PrixVente);
        edittext_prixAchat = findViewById(R.id.edittext_PrixAchat);
        edittext_quantite = findViewById(R.id.edittext_QtyProduit);
        Button buttonInserer = findViewById(R.id.btn_EnregistrerProduit);
        imageview_image=findViewById(R.id.big_image);
        back = findViewById(R.id.back2);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(AddProduitActivity.this, NavigationActivity.class);
                startActivity(i);
                finish();

            }
        });

        //
        EnableRuntimePermission();



        imageview_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                //startActivityForResult(intent, 7);
                intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent , 1);

            }
        });


// Insertion du vente dans la base de données apres le clique sur le bouton CheckOut
        buttonInserer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = edittext_Nom.getText().toString();
                String description = edittext_description.getText().toString();
                String prixVente = edittext_prixVente.getText().toString();
                String prixAchat = edittext_prixAchat.getText().toString();
                String quantite = edittext_quantite.getText().toString();
                if (!(TextUtils.isEmpty(nom) || TextUtils.isEmpty(description) || TextUtils.isEmpty(prixVente) || TextUtils.isEmpty(prixAchat) || TextUtils.isEmpty(quantite))) {
                    try {

                        Drawable drawable = imageview_image.getDrawable();
                        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
                        File pictureFile = new File("/sdcard/Android/media", "IMG_" + timeStamp + ".jpg");
                        FileOutputStream fos = new FileOutputStream(pictureFile);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                        fos.flush();
                        fos.close();

                        String imagePath = pictureFile.getAbsolutePath();
                        System.out.println(imagePath);
                        myDatabase.inserer_produit(nom, description, Double.parseDouble(prixAchat),Double.parseDouble(prixVente), Integer.parseInt(quantite),imagePath);
                        Intent i = new Intent(AddProduitActivity.this, NavigationActivity.class);
                        startActivity(i);
                        finish();
                        Toast.makeText(view.getContext(), "Produit ajouté", Toast.LENGTH_SHORT).show();


                    } catch (SQLiteException | IOException s) {
                        s.printStackTrace();
                    }
                }
            }
        });

    }




    //Demander la permission de l'utilisateur
    public void EnableRuntimePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(AddProduitActivity.this, Manifest.permission.CAMERA))
        {
            Toast.makeText(AddProduitActivity.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();
        } else {
                ActivityCompat.requestPermissions(AddProduitActivity.this,new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, RequestPermissionCode);
        }
    }

    //Message apres la permission
    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {
        super.onRequestPermissionsResult(RC, per, PResult);
        switch (RC) {
            case RequestPermissionCode:
                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(AddProduitActivity.this, "Permission obtenu!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddProduitActivity.this, "Permission refusé!", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }




    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageview_image.setImageURI(selectedImage);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageview_image.setImageURI(selectedImage);
                }
                break;
        }
    }


/*
    //3 methode pour prendre la permission de la camera et enregistrer l'image dans imageview
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 7 && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageview_image.setImageBitmap(bitmap);
        }
    }*/


}