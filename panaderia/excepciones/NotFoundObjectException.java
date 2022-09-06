package panaderia.excepciones;

/**
 * Clase para el lanzamiento de un error 
 * cuando no se encuentre un objeto en una busqueda
 */
public class NotFoundObjectException extends Exception {

    public NotFoundObjectException(String mensaje) {
        super(mensaje);
    }

}
