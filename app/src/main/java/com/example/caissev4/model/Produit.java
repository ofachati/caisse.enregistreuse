package com.example.caissev4.model;

public class Produit {

    private int id;
    private String name;
    private String description;
    private Double prix_achat;
    private Double prix_vente;
    private int quantity;
    private String image_path;

    public Produit(int id, String name, String description, Double prix_achat, Double prix_vente, int quantity, String image_path) {
        this.id=id;
        this.name = name;
        this.description = description;
        this.prix_achat=prix_achat;
        this.prix_vente = prix_vente;
        this.quantity = quantity;
        this.image_path = image_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrix_vente() {
        return prix_vente;
    }

    public void setPrix_vente(Double prix_vente) {
        this.prix_vente = prix_vente;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
