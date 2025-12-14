package es.ciudadescolar.Utils;

import java.util.ArrayList;

public class Pelicula {
    
    // valores de la clase
    private int filmId;
    private String title;
    private int releaseYear;
    private int languageId;
    private float rentalDuration;
    private float rentalRate;
    private float replacementCost;
    private String rating;
    private ArrayList<Actor> actores;

    

    // getters y setters
    public int getFilmId() {return filmId;}
    public void setFilmId(int filmId) {this.filmId = filmId;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public int getReleaseYear() {return releaseYear;}
    public void setReleaseYear(int releaseYear) {this.releaseYear = releaseYear;}

    public int getLanguageId() {return languageId;}
    public void setLanguageId(int languageId) {this.languageId = languageId;}

    public float getRentalDuration() {return rentalDuration;}
    public void setRentalDuration(float rentalDuration) {this.rentalDuration = rentalDuration;}

    public float getRentalRate() {return rentalRate;}
    public void setRentalRate(float rentalRate) {this.rentalRate = rentalRate;}

    public float getReplacementCost() {return replacementCost;}
    public void setReplacementCost(float replacementCost) {this.replacementCost = replacementCost;}

    public String getRating() {return rating;}
    public void setRating(String rating) {this.rating = rating;}

    public ArrayList<Actor> getActores() {return actores;}
    public void setActores(ArrayList<Actor> actores) {this.actores = actores;}



    // constructor con todos los valores
    public Pelicula(int filmId, String title, int releaseYear, int languageId, float rentalDuration, float rentalRate,
                    float replacementCost, String rating, ArrayList<Actor> actores) 
    {
        this.filmId = filmId;
        this.title = title;
        this.releaseYear = releaseYear;
        this.languageId = languageId;
        this.rentalDuration = rentalDuration;
        this.rentalRate = rentalRate;
        this.replacementCost = replacementCost;
        this.rating = rating;
        this.actores = actores;
    }



    // to String
    @Override
    public String toString() {
        return "Pelicula [filmId=" + filmId + ", title=" + title + ", releaseYear=" + releaseYear + ", languageId="
                + languageId + ", rentalDuration=" + rentalDuration + ", rentalRate=" + rentalRate
                + ", replacementCost=" + replacementCost + ", rating=" + rating + ", actores=" + actores + "]";
    }

    

}
