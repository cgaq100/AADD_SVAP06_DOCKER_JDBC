package es.ciudadescolar;

import java.io.File;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.Utils.DbManager;
import es.ciudadescolar.Utils.Pelicula;
import es.ciudadescolar.Utils.TxtManager;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LOG.info("___ INICIO PROGRAMA ___");
        
        
        ArrayList<Pelicula> peliculas = TxtManager.RecuperarPeliculas(new File("nuevas_pelis.txt"));

        DbManager dbManager = new DbManager("conexionBD.properties");

        //dbManager.getActorPorId(200);
        //dbManager.getFilmPorId(22);

        dbManager.guardarPeliculasConActores(peliculas);

        LOG.info("___ FIN PROGRAMA ___");
    }
}