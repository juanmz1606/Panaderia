package panaderia.datos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LectorArchivo implements IFuenteDatos {

   private String nombreArchivo;

   public LectorArchivo(String nombreArchivo) {
      this.nombreArchivo = nombreArchivo;
   }

   @Override
   public List<String[]> obtenerDatosBase() throws  IOException {
      String dato = "";
      List<String[]> productos = new ArrayList<>();
      // System.out.println("panaderia/Encargos.txt");

         BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
         String linea;

         while ((linea = br.readLine()) != null) {
            dato += linea;
         }

         br.close();

         String[] aux = dato.split("/");

         for (String string : aux) {
            String[] x = string.split(",");
            productos.add(x);
         }

      
      return productos;

   }

}
