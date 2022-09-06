package panaderia.logica;

import java.io.IOException;
import java.util.List;
import panaderia.datos.EscritorArchivoOrdenes;
import panaderia.datos.LectorArchivo;
import panaderia.entidades.base.Producto;
import panaderia.entidades.base.Recorrido;
import panaderia.entidades.pedido.OrdenPedido;
import panaderia.excepciones.NotFoundObjectException;

/**
 * Lógica del programa de un recorrido de un vendedor,
 * para crear las órdenes de pedido de las tiendas.
 * 
 * @version 1.0
 */
public class ControlRecorrido {
	private Recorrido recorrido;
	private OrdenPedido ordenEnProceso;

	public ControlRecorrido() {
		this.recorrido = new Recorrido();
		this.ordenEnProceso = null;
	}

	/**
	 * Crea los productos que puede vender la panaderia.
	 * Tambien crea las tiendas que debe recorrer el vendedor.
	 */
	public void cargarDatosIniciales() {

		CargadorDatos cargador = new CargadorDatos(recorrido);
		cargador.cargarDatosIniciales();
	}

	/**
	 * Busca si existe una tienda por su codigo en el recorrido
	 * 
	 * @param codigoTienda
	 * @return Verdadero o falso si encuentra la tienda
	 */
	public boolean existeTienda(String codigoTienda) {
		if (recorrido.buscarTienda(codigoTienda) != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Crea la orden del pedido en base a un link ingresado
	 * por el usuario que direcciona a un archivo de texto
	 * En el cual se pueden leer todos los productos
	 * solicitados, bajo cierto formato
	 * @param nombreArchivoProductos
	 * @param codigoTienda
	 * @throws IOException
	 */
	public void crearOrden(String nombreArchivoProductos, String codigoTienda) throws IOException {
		this.ordenEnProceso = new OrdenPedido(recorrido.buscarTienda(codigoTienda));
		LectorArchivo lectorArchivo = new LectorArchivo(nombreArchivoProductos);

		try {
			crearDetalle(ordenEnProceso, lectorArchivo.obtenerDatosBase());
		} catch (NotFoundObjectException notFound) {
			System.out.println(notFound.getMessage());
		}

	}

	/**
	 * Crea los detalles del pedido y los
	 * guarda en la lista de detalles del mismo
	 * 
	 * @param orden
	 * @param datosBase
	 * @throws NotFoundObjectException
	 */
	public void crearDetalle(OrdenPedido orden, List<String[]> datosBase) throws NotFoundObjectException {
		for (String[] datoBaseDetalle : datosBase) {

			Producto producto1 = recorrido.buscarProducto(datoBaseDetalle[0]);
			if (producto1 != null) {
				ordenEnProceso.addDetalle(producto1, Integer.parseInt(datoBaseDetalle[1]));
			} else {

				throw new NotFoundObjectException("No se pudo encontrar el objeto.");
			}
		}
	}

	/**
	 * Busca una tienda por codigo y trae sus datos
	 * basicos para mostrarlos al usuario
	 * 
	 * @param codigoTienda
	 * @return Un string con los datos de la tienda, el codigo y el nombre
	 */
	public String obtenerDatosTienda(String codigoTienda) {
		return recorrido.buscarTienda(codigoTienda).toString();

	}

	/**
	 * Ordena los detalles de un pedido para mostrar al usuario
	 * 
	 * @return Una lista de strings con los detalles del pedido
	 */
	public List<String> obtenerDetallesOrdenados() {
		return ordenEnProceso.getDetallesOrdenados();

	}

	/**
	 * Envía la orden para que su información
	 * se guarde en un archivo, y luego
	 * deja el atributo en null para que se
	 * pueda procesar una nueva orden.
	 */
	public void guardarOrden() {
		EscritorArchivoOrdenes escritor = new EscritorArchivoOrdenes();
		escritor.escribirOrden(ordenEnProceso);
		ordenEnProceso = null;
	}
}
