package panaderia.datos;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase de prueba que permite crear productos fijos sin leerlos de
 * un archivo de texto
 */
public class DatosPruebaProductos implements IFuenteDatos {

    @Override
    public List<String[]> obtenerDatosBase() {

        String[] producto1 = { "2025", "Chicharron", "1.500" };
        String[] producto2 = { "4589", "Pan trenza", "3000" };
        String[] producto3 = { "8974", "Hawaiano", "2500" };
        List<String[]> productos = new ArrayList<>();
        productos.add(producto1);
        productos.add(producto2);
        productos.add(producto3);
        return productos;

    }

}
