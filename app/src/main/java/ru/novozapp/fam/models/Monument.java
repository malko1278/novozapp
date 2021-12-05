package ru.novozapp.fam.models;

/**
 * Created by Franck Armel Malko ABENA on 28/04/2018.
 */
public class Monument {
    private int id;
    private String name_monument;
    private String description;
    private String year_construction;
    private String adresse;
    private String image_monument;
    private double latitude_monut;
    private double longitude_monut;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_monument() {
        return name_monument;
    }

    public void setName_monument(String name_monument) {
        this.name_monument = name_monument;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYear_construction() {
        return year_construction;
    }

    public void setYear_construction(String year_construction) {
        this.year_construction = year_construction;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getImage_monument() {
        return image_monument;
    }

    public void setImage_monument(String image_monument) {
        this.image_monument = image_monument;
    }

    public double getLatitude_monut() {
        return latitude_monut;
    }

    public void setLatitude_monut(double latitude_monut) {
        this.latitude_monut = latitude_monut;
    }

    public double getLongitude_monut() {
        return longitude_monut;
    }

    public void setLongitude_monut(double longitude_monut) {
        this.longitude_monut = longitude_monut;
    }
}