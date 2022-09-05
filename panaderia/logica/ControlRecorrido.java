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

	public void cargarDatosIniciales() {

		CargadorDatos cargador = new CargadorDatos(recorrido);
		cargador.cargarDatosIniciales();
	}

	public boolean existeTienda(String codigoTienda) {
		if (recorrido.buscarTienda(codigoTienda) != null) {
			return true;
		} else {
			return false;
		}
	}

	public void crearOrden(String nombreArchivoProductos, String codigoTienda) throws IOException {
		this.ordenEnProceso = new OrdenPedido(recorrido.buscarTienda(codigoTienda));
		LectorArchivo lectorArchivo = new LectorArchivo(nombreArchivoProductos);

		try {
			crearDetalle(ordenEnProceso, lectorArchivo.obtenerDatosBase());
		} catch (NotFoundObjectException notFound) {
			System.out.println(notFound.getMessage());
		}

	}

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

	public String obtenerDatosTienda(String codigoTienda) {
		return recorrido.buscarTienda(codigoTienda).toString();

	}

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
