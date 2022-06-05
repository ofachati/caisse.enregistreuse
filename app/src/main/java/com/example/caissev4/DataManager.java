package com.example.caissev4;


import android.annotation.SuppressLint;
import android.database.Cursor;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.caissev4.model.Produit;
import com.example.caissev4.model.Vente;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataManager extends SQLiteOpenHelper {

    private static final String databasename="produit.db";
    private static final int databaseversion=1;



    public DataManager(Context context){
        super(context, databasename, null, databaseversion);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Produit;");
        sqLiteDatabase.execSQL("create table Produit(id_produit INTEGER primary key autoincrement,nom TEXT not null,description TEXT not null,prix_achat REAL not null,prix_vente REAL not null, quantite INTEGER not null,image TEXT not null)");
        sqLiteDatabase.execSQL("create table Vente(id_vente INTEGER primary key autoincrement,id_produit INTEGER not null,date TEXT not null,etat TEXT not null,FOREIGN KEY(id_produit) REFERENCES Produit(id_produit))");
        sqLiteDatabase.execSQL("CREATE TRIGGER  QuantiteUpdate\n" +
                "        AFTER UPDATE OF etat\n" +
                "        ON Vente\n" +
                "        BEGIN\n" +
                "        UPDATE Produit\n" +
                "        SET quantite = Produit.quantite - 1\n" +
                "        WHERE produit.id_produit = New.id_produit ;\n" +
                "        END;");

        Log.i("database", "onCreate invoked ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}


//********** Insertion table produit **************************
    public void inserer_produit(String nom, String description, double prix_achat,double prix_vente, int quantite, String image) {
        SQLiteDatabase sql = getWritableDatabase();
        try {
            sql.execSQL("INSERT INTO Produit (nom,description,prix_achat,prix_vente,quantite,image) VALUES ('" + nom + "' , '" + description + "', " + prix_achat +", "+ prix_vente +   ", " + quantite+", '" + image  +"')" );
            Log.i("database", "insert");
        } catch (SQLiteException s) {
            s.printStackTrace();
        }

        sql.close();
    }

//********** Insertion table vente **************************
    public void ajout_dans_catalogue(int id_produit) {
        SQLiteDatabase sql = getWritableDatabase();
        try {
            sql.execSQL("INSERT INTO Vente (id_produit,date,etat) VALUES ('" + id_produit + "' , DATE('now'),'en cours')" );
            Log.i("database", "insert");
        } catch (SQLiteException s) {
            s.printStackTrace();
        }
        sql.close();
    }
//******  Rechecrche  ************************//
@SuppressLint("Range")
public List<Produit> rechercher(String nomProduit) {

    SQLiteDatabase db = getReadableDatabase();
    Cursor c = db.rawQuery("SELECT * FROM Produit WHERE nom LIKE '%"+nomProduit+"%'" , null);
    List<Produit> produitList = new ArrayList<Produit>();

    if (c.moveToFirst()) {
        do {
            Produit produit = new Produit(  c.getInt(c.getColumnIndex("id_produit")),
                                            c.getString(c.getColumnIndex("nom")),
                                            c.getString(c.getColumnIndex("description")),
                                            c.getDouble(c.getColumnIndex("prix_achat")),
                                            c.getDouble(c.getColumnIndex("prix_vente")),
                                            c.getInt(c.getColumnIndex("quantite")),
                                            c.getString(c.getColumnIndex("image")));
            //produit.setId(c.getInt(c.getColumnIndex("yourcolumnname")));
            //produit.setTitulo(c.getString(c.getColumnIndex("yourcolumnname")));
            //produit.setTexto(c.getString(c.getColumnIndex("yourcolumnname")));
            //produit.setTipo(c.getString(c.getColumnIndex("yourcolumnname")));
            produitList.add(produit);
        } while (c.moveToNext());
    }
    db.close();
    return produitList;
}


    //******  getProducts  ************************//
    @SuppressLint("Range")
    public List<Produit> getProducts(String requete) {
        List<Produit> produitList = new ArrayList<Produit>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(requete, null);
        if (c.moveToFirst()) {
            do {
                Produit produit = new Produit(  c.getInt(c.getColumnIndex("id_produit")),
                        c.getString(c.getColumnIndex("nom")),
                        c.getString(c.getColumnIndex("description")),
                        c.getDouble(c.getColumnIndex("prix_achat")),
                        c.getDouble(c.getColumnIndex("prix_vente")),
                        c.getInt(c.getColumnIndex("quantite")),
                        c.getString(c.getColumnIndex("image")));
                //produit.setId(c.getInt(c.getColumnIndex("yourcolumnname")));
                //produit.setTitulo(c.getString(c.getColumnIndex("yourcolumnname")));
                //produit.setTexto(c.getString(c.getColumnIndex("yourcolumnname")));
                //produit.setTipo(c.getString(c.getColumnIndex("yourcolumnname")));
                produitList.add(produit);
            } while (c.moveToNext());
        }
        return produitList;
    }


    //******  getall Ventes  ************************//
    @SuppressLint("Range")
    public List<Vente> getallVentes() {
        String strSql = "select * FROM Vente INNER JOIN Produit ON Produit.id_produit = Vente.id_produit WHERE etat='en cours'";
        List<Vente> produitList = new ArrayList<Vente>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(strSql, null);
        if (c.moveToFirst()) {
            do {
                Vente vente = new Vente(c.getInt(c.getColumnIndex("id_vente")),
                        new Produit( c.getInt(c.getColumnIndex("id_produit")),
                            c.getString(c.getColumnIndex("nom")),
                            c.getString(c.getColumnIndex("description")),
                            c.getDouble(c.getColumnIndex("prix_achat")),
                            c.getDouble(c.getColumnIndex("prix_vente")),
                        c.getInt(c.getColumnIndex("quantite")),c.getString(c.getColumnIndex("image"))),
                        c.getString(c.getColumnIndex("etat")),c.getString(c.getColumnIndex("date")));
                produitList.add(vente);
            } while (c.moveToNext());
        }
        return produitList;

    }
//*********************  Supprimer *********************************//
public void supprimer(int id_vente) {
    SQLiteDatabase sql = getWritableDatabase();

    try {
        sql.execSQL("DELETE FROM Vente WHERE id_vente = " + id_vente);

    } catch (SQLiteException s) {
        s.printStackTrace();
    }

    sql.close();
}



//******   requete_au_choix ************************//
@SuppressLint("Range")
    public String get_stat(String requete ) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(requete, null);
        String stats = null;
        if (c.moveToFirst()) {
             stats = c.getString(0);
        }
        db.close();
        return stats;
    }


//*********************   *********************************//
public void vendre() {
    SQLiteDatabase sql = getWritableDatabase();
    try {
        sql.execSQL("UPDATE Vente SET etat ='vendu' WHERE etat='en cours'" );
        Log.i("database", "insert");
    } catch (SQLiteException s) {
        s.printStackTrace();
    }
    sql.close();
}

}