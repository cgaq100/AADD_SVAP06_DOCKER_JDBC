package es.ciudadescolar.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtManager {

    private static final Logger LOG = LoggerFactory.getLogger(TxtManager.class);


    public static ArrayList<Pelicula> RecuperarPeliculas(File file)
    {
        LOG.debug("Entrando en el metodo RecuperaPeliculas de TxtManager");
        ArrayList<Pelicula> salida = new ArrayList<>(); // lista de salida del metodo


        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {

            String linea;
            int contLineas = 0;
            while ((linea = br.readLine()) != null) 
            {
                if(contLineas > 1 && linea.length() > 0)
                {
                    String[] contenidoLinea = linea.split("\\|");

                    // sacar valores basicos
                    int id = Integer.parseInt(contenidoLinea[0]);
                    String title = contenidoLinea[1];
                    int year = Integer.parseInt(contenidoLinea[2]);
                    int languageId = Integer.parseInt(contenidoLinea[3]);
                    float rentalDuration = Float.parseFloat(contenidoLinea[4]);
                    float rentalRate = Float.parseFloat(contenidoLinea[5]);
                    float replacementCost = Float.parseFloat(contenidoLinea[6]);
                    String rating = contenidoLinea[7];

                    // sacar actores
                    String[] actores = contenidoLinea[8].split("\\,");
                    ArrayList<Actor> actoresEncontrados = new ArrayList<>();

                    for(String index : actores)
                    {
                        int idActor = Integer.parseInt(index.split("\\-")[0].replace(" ", ""));

                        String[] nomApellido = index.split("\\-")[1].split("\\ ");
                        if(nomApellido[0].length() <= 0) 
                        {
                            String[] nomApellidoCorregido = {nomApellido[1], nomApellido[2]};
                            nomApellido = nomApellidoCorregido;
                        }
                        if(nomApellido.length == 3)
                        {
                            String[] nomApellidoCorregido = {nomApellido[0], nomApellido[1]+" "+nomApellido[2]};
                            nomApellido = nomApellidoCorregido;
                        }
                        
                        String firstName = nomApellido[0];
                        String lastName = nomApellido[1];

                        Actor nuevoActor = new Actor(idActor, firstName, lastName);
                        actoresEncontrados.add(nuevoActor);
                    }

                    // creacion del objeto pelicula y adicion de esta a la salida
                    Pelicula nuevaPelicula = new Pelicula(id, title, year, languageId, rentalDuration, rentalRate, replacementCost, rating, actoresEncontrados);
                    salida.add(nuevaPelicula);
                }
                
                contLineas++;
            }

        }
        catch(IOException e)
        {
            LOG.error("Ocurrio un error durante el parseo del txt en RecuperaPeliculas de TxtManager: "+e.getMessage());
        }


        LOG.debug("Saliendo en el metodo RecuperaPeliculas de TxtManager con una salida de un tamaño de "+salida.size());
        return salida;
    }

}
