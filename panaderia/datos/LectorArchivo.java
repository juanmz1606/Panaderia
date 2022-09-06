package panaderia.datos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Se encarga de leer los archivos de texto con cierto formato.
 */
public class LectorArchivo implements IFuenteDatos {

   private String nombreArchivo;

   public LectorArchivo(String nombreArchivo) {
      this.nombreArchivo = nombreArchivo;
   }

   /**
    * Separa el texto de un archivo .txt. 
    *Cada atributo por una coma (,) y cada objeto con un slash (/)
    * Retorna una lista de arreglos de strings que sera manipulada mas adelante
    */
   @Override
   public List<String[]> obtenerDatosBase() throws IOException {
      String dato = "";
      List<String[]> objetos = new ArrayList<>();

      BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
      String linea;

      while ((linea = br.readLine()) != null) {
         dato += linea;
      }

      br.close();

      String[] aux = dato.split("/");

      for (String string : aux) {
         String[] x = string.split(",");
         objetos.add(x);
      }
      return objetos;
   }
}