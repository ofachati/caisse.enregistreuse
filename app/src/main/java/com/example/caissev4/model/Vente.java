package com.example.caissev4.model;

public class Vente {

    private int id_vente;
    private String etat;
    private String date;
    private Produit produit;

    public Vente(int id_vente, Produit produit, String etat, String date ) {
        this.id_vente = id_vente;
        this.etat = etat;
        this.date = date;
        this.produit = produit;
    }

    public int getId_vente() {
        return id_vente;
    }

    public void setId_vente(int id_vente) {
        this.id_vente = id_vente;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }


}
