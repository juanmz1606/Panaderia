package panaderia.logica;

import java.io.IOException;
import java.util.List;
import panaderia.datos.IFuenteDatos;
import panaderia.datos.LectorArchivo;
import panaderia.entidades.base.Producto;
import panaderia.entidades.base.Recorrido;
import panaderia.entidades.base.Tienda;

/**
 * Se encarga de crear los objetos a partir
 * de arreglos de cadenas de texto con la información
 * (esas cadenas pueden provenir de diferentes
 * fuentes, por eso usa una interfaz,
 * para que se puedan cambiar fácilmente).
 * 
 * @version 1.0
 */
public class CargadorDatos {
	private Recorrido recorrido;

	public CargadorDatos(Recorrido recorrido) {
		this.recorrido = recorrido;
	}

	/**
	 * Carga los datos iniciales que necesita el programa:
	 * tiendas y productos.
	 * 
	 * @throws IOException
	 */
	public void cargarDatosIniciales() {

		try {
			IFuenteDatos fuenteDatosTiendas = new LectorArchivo("panaderia/Tiendas.txt");

			List<String[]> datosBaseTiendas = fuenteDatosTiendas.obtenerDatosBase();
			this.cargarDatosTiendas(datosBaseTiendas);

			IFuenteDatos fuenteDatosProductos = new LectorArchivo("panaderia/productos.txt");

			List<String[]> datosBaseProductos = fuenteDatosProductos.obtenerDatosBase();
			this.cargarDatosProductos(datosBaseProductos);

		} catch (Exception e) {
			System.out.println("No se pudo cargar los datos.");
		}
	}

	/**
	 * A partir de los datos base (cadenas de texto),
	 * obtiene los datos de las tiendas,
	 * crea los objetos y los guarda en el objeto recorrido.
	 */
	private void cargarDatosTiendas(List<String[]> datosBase) {
		for (String[] datoBaseTienda : datosBase) {
			String codigo = datoBaseTienda[0];
			String nombre = datoBaseTienda[1];
			Tienda tienda = new Tienda(codigo, nombre);

			try {
				this.recorrido.addTienda(tienda);
			} catch (IllegalArgumentException argumentoNoValido) {
				argumentoNoValido.getMessage();
			}

		}
	}

	/**
	 * A partir de los datos base (cadenas de texto),
	 * obtiene los datos de los productos,
	 * crea los objetos y los guarda en el objeto recorrido.
	 */
	private void cargarDatosProductos(List<String[]> datosBase) {
		for (String[] datoBaseProducto : datosBase) {
			String codigo = datoBaseProducto[0];
			String nombre = datoBaseProducto[1];
			Double valorUnitario = Double.parseDouble(datoBaseProducto[2]);
			Producto producto = new Producto(codigo, nombre, valorUnitario);
			this.recorrido.addProducto(producto);
		}
	}
}