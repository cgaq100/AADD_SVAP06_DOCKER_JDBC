package es.ciudadescolar.Utils;

public class SQL {


    protected static final String SACAR_ACTOR_POR_ID = "SELECT * FROM actor WHERE actor_id = ?";
    protected static final String SACAR_PELICULA_POR_ID = "SELECT film_id, title, release_year, language_id, rental_duration, rental_rate, replacement_cost, rating FROM film WHERE film_id = ?";

    protected static final String ALTA_NUEVO_ACTOR = "INSERT INTO actor(actor_id, first_name, last_name) VALUES (?,?,?)";
    protected static final String ALTA_NUEVA_PELICULA = "INSERT INTO film(film_id, title, release_year, language_id, rental_duration, rental_rate, replacement_cost, rating) VALUES (?,?,?,?,?,?,?,?)";
    protected static final String VINCULO_ACTOR_PELICULA = "INSERT INTO film_actor(actor_id, film_id) VALUES (?,?)";



}
