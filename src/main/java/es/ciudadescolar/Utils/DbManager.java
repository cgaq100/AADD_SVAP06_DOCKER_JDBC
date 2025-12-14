package es.ciudadescolar.Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DbManager {

    private static final Logger LOG = LoggerFactory.getLogger(DbManager.class);

    private static final String DRIVER = "driver";
    private static final String URL = "url";
    private static final String USER = "user";
    private static final String PASSWORD = "pass";


    private Connection con = null;



    public DbManager(String properties)
    {
        Properties prop = new Properties();

        try {
            // cargamos el archivo .properties recibido
            prop.load(new FileInputStream(properties));
        
            // registramos driver (en versiones actuales no es necesario)
            Class.forName(prop.getProperty(DRIVER));

            // establecemos la conexion con la BD
            //con = DriverManager.getConnection("jdbc:mysql://192.168.203.77:3306/dam2_2425", "dam2", "dam2");
            con = DriverManager.getConnection(prop.getProperty(URL), prop.getProperty(USER), prop.getProperty(PASSWORD));

            LOG.debug("Establecida la conexión satisfactoriamente");
            

        } 
        catch (FileNotFoundException e) {LOG.error("PROP | Imposible cargar propiedades de la conexion: "+e.getMessage());} 
        catch (IOException e) {LOG.error("Error de IOException: "+e.getMessage());}
        catch (ClassNotFoundException e) {LOG.error("Registro de driver con error: "+e.getMessage());}
        catch (SQLException e) {LOG.error("Imposible establecer conexion con la BD: "+e.getMessage());}
    }



    public boolean CerrarBD()
    {
        boolean status = false;

        if(con != null)
        {
            try
            {
                con.close();
                LOG.debug("Cerrada la conexión satisfactoriamente");
                status = true;
            }
            catch (SQLException e){LOG.error("Error cerrando la conexión: "+e.getMessage());}
        }

        return status;
    }



    public Actor getActorPorId(int id)
    {
        Actor salida = null;

        PreparedStatement pstActor = null;
        ResultSet rstActor = null;


        // solo debe devolver uno o ninguno
        try
        {
            pstActor = con.prepareStatement(SQL.SACAR_ACTOR_POR_ID);
            pstActor.setInt(1, id);
            rstActor = pstActor.executeQuery();

            if(rstActor.next())
            {
                salida = new Actor(rstActor.getInt(1), rstActor.getString(2), rstActor.getString(3));
                LOG.debug("Recuperado actor con el id ["+id+"] y nombre["+salida.getFirstName()+" "+salida.getLastName()+"]");
            }
        }
        catch(SQLException e) {LOG.error("Error durante la consulta parametrizada: "+e.getMessage());}
        finally{
            try{
                if(rstActor != null) rstActor.close();
                if(pstActor != null) pstActor.close();
            }
            catch (SQLException e) {LOG.error("Error liberando recursos de la consulta parametrizada: "+e.getMessage());}
        }


        return salida;
    }



    public Pelicula getFilmPorId(int id)
    {
        Pelicula salida = null;

        PreparedStatement pstPelicula = null;
        ResultSet rstPelicula = null;


        // solo debe devolver uno o ninguno
        try
        {
            pstPelicula = con.prepareStatement(SQL.SACAR_PELICULA_POR_ID);
            pstPelicula.setInt(1, id);
            rstPelicula = pstPelicula.executeQuery();

            if(rstPelicula.next())
            {
                salida = new Pelicula(rstPelicula.getInt(1), 
                                    rstPelicula.getString(2), 
                                    rstPelicula.getInt(3), 
                                    rstPelicula.getInt(4), 
                                    rstPelicula.getFloat(5), 
                                    rstPelicula.getFloat(6), 
                                    rstPelicula.getFloat(7), 
                                    rstPelicula.getString(8), 
                                    null);
                LOG.debug("Recuperado pelicula con el id"+id+" con el titulo "+salida.getTitle());
            }
        }
        catch(SQLException e) {LOG.error("Error durante la consulta parametrizada: "+e.getMessage());}
        finally{
            try{
                if(rstPelicula != null) rstPelicula.close();
                if(pstPelicula != null) pstPelicula.close();
            }
            catch (SQLException e) {LOG.error("Error liberando recursos de la consulta parametrizada: "+e.getMessage());}
        }


        return salida;
    }




    public boolean guardarActor(Actor actor)
    {
        boolean status = false;

        PreparedStatement pstAltaActor = null;

        if(con != null)
        {
            try 
            {
                pstAltaActor = con.prepareStatement(SQL.ALTA_NUEVO_ACTOR);

                // nos aseguramos de que el actor no exista
                if(getActorPorId(actor.getId()) == null)
                {
                    // preparacion de la consulta del actor
                    pstAltaActor.setInt(1, actor.getId());
                    pstAltaActor.setString(2, actor.getFirstName());
                    pstAltaActor.setString(3, actor.getLastName());

                    pstAltaActor.executeUpdate();
                    pstAltaActor.clearParameters(); // limpiar pst para reutilizarlo correctamente

                    con.commit();
                    LOG.debug("Se han confirmado todos los cambios de la transacción");
                    status = true;
                }
                else LOG.debug("Ya existe el actor "+actor.getFirstName()+" en la base de datos");
            } 
            catch (SQLException e)
            {
                LOG.error("Error durante el alta del actor: "+ e.getMessage());
            }
            finally
            {
                if (pstAltaActor != null)
                {
                    try {
                        pstAltaActor.close();
                    } catch (SQLException e) {LOG.error("Error durante la liberacion de recursos en el alta transaccional: "+ e.getMessage());}
                    LOG.debug("Liberacion de recursos satisfactoria durante el alta transaccional");
                }
            }
        }

        return status;
    }



    public boolean GuardarVinculoActorPelicula(int idActor, int idPelicula)
    {
        boolean status = false;

        PreparedStatement pstAltaVinActorPelicula = null;

        if(con != null)
        {
            try 
            {
                pstAltaVinActorPelicula = con.prepareStatement(SQL.VINCULO_ACTOR_PELICULA);                    

                pstAltaVinActorPelicula.setInt(1, idActor);
                pstAltaVinActorPelicula.setInt(2, idPelicula);

                pstAltaVinActorPelicula.executeUpdate();
                pstAltaVinActorPelicula.clearParameters(); // limpiar pst para reutilizarlo correctamente

                con.commit();
                LOG.debug("Se han confirmado todos los cambios de la transacción");

            } 
            catch (SQLException e)
            {
                LOG.error("Error durante el alta del actor: "+ e.getMessage());
            }
            finally
            {
                if (pstAltaVinActorPelicula != null)
                {
                    try {
                        pstAltaVinActorPelicula.close();
                    } catch (SQLException e) {LOG.error("Error durante la liberacion de recursos en el alta transaccional: "+ e.getMessage());}
                    LOG.debug("Liberacion de recursos satisfactoria durante el alta transaccional");
                }
            }
        }

        return status;
    }



    public boolean guardarPeliculasConActores(List<Pelicula> peliculas)
    {
        LOG.debug("Entrando en el metodo guardarPeliculas de DbManager");
        boolean status = false;

        PreparedStatement pstAltaPelicula = null;


        if(con != null)
        {
            try 
            {
                pstAltaPelicula = con.prepareStatement(SQL.ALTA_NUEVA_PELICULA);
                con.setAutoCommit(false); // establece el autocommit en false para ser el responsable de hacer el commit o rollback

                for(Pelicula index : peliculas)
                {
                    // primero nos aseguramos de que la pelicula no exista
                    if(getFilmPorId(index.getFilmId()) != null)
                    {
                        // film_id, title, release_year, language_id, rental_duration, rental_rate, replacement_cost, rating
                        pstAltaPelicula.setInt(1, index.getFilmId());
                        pstAltaPelicula.setString(2, index.getTitle());
                        pstAltaPelicula.setInt(3, index.getReleaseYear());
                        pstAltaPelicula.setInt(4, index.getLanguageId());
                        pstAltaPelicula.setFloat(5, index.getRentalDuration());
                        pstAltaPelicula.setFloat(6, index.getRentalRate());
                        pstAltaPelicula.setFloat(7, index.getReplacementCost());
                        pstAltaPelicula.setString(8, index.getRating());

                        // añadir actores que no existan y los vinculos con la pelicula
                        for(Actor indexActor : index.getActores())
                        {
                            guardarActor(indexActor);
                            GuardarVinculoActorPelicula(indexActor.getId(), index.getFilmId());
                        }
                    }
                    else LOG.debug("Se intento guardar una pelicula ya existente");
                    
                    pstAltaPelicula.executeUpdate();
                    pstAltaPelicula.clearParameters(); // limpiar pst para reutilizarlo correctamente
                }

                con.commit();
                LOG.debug("Se han confirmado todos los cambios de la transacción");
                status = true;
            } 
            catch (SQLException e)
            {
                LOG.error("Error durante el alta de peliculas de forma transaccional: "+ e.getMessage());
                try 
                {
                    con.rollback();
                    LOG.debug("Rollback realizado correctamente");
                } 
                catch (SQLException e1) {LOG.error("Error haciendo rollback de la transaccion: "+e.getMessage());}
            }
            finally
            {
                if (pstAltaPelicula != null)
                {
                    try {
                        pstAltaPelicula.close();
                        con.setAutoCommit(true); // restaurar autocommit para que bajo la misma sesion, cualquier alta, baja o modificación haga commit automaticamente
                    } catch (SQLException e) {LOG.error("Error durante la liberacion de recursos en el alta transaccional: "+ e.getMessage());}
                    LOG.debug("Liberacion de recursos satisfactoria durante el alta transaccional");
                }
            }
        }


        LOG.debug(("Saliendo del metodo guardarPeliculas de DbManager con una salida: "+status));
        return status;
    }

}
