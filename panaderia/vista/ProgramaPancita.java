package panaderia.vista;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import panaderia.logica.ControlRecorrido;

/**
 * Realiza la interacción con el usuario
 * para poder iniciar el programa y procesar
 * las órdenes de pedido de un recorrido de ventas.
 * 
 * @version 1.0
 */
public class ProgramaPancita {
	private ControlRecorrido control;
	Scanner consola = new Scanner(System.in);

	public ProgramaPancita() {
		this.control = new ControlRecorrido();
	}

	/**
	 * Permite cargar los datos iniciales necesarios
	 * para hacer el recorrido.
	 * 
	 * 
	 */
	public void iniciar() {

		this.control.cargarDatosIniciales();
	}

	/**
	 * Es el ciclo de control general del programa,
	 * para saber si hay más ordenes o termina.
	 */
	public void hacerRecorrido() {

		String respuesta = "S";
		while (respuesta.equals("S")) {
			System.out.println("-------------------------------------------------------");
			System.out.print("¿Desea crear orden de pedido (S/N)?: ");
			respuesta = consola.next();
			System.out.println();

			if (respuesta.equals("S")) {
				this.procesarUnaOrden();
			}
		}
		consola.close();
		System.out.println("Fin del programa. Muchas gracias.");

	}

	/**
	 * Coordina el proceso para poder crear una orden
	 * de pedido, mostrarla y pedir la aceptación
	 * del usuario.
	 */
	private void procesarUnaOrden() {
		System.out.print("Ingrese por favor el código de la tienda: ");
		String codigo = consola.next();

		if (control.existeTienda(codigo)) {
			System.out.print("Ingrese URL: ");
			String url = consola.next();

			try {
				control.crearOrden(url, codigo);
				String tienda = control.obtenerDatosTienda(codigo);
				List<String> lista = new ArrayList<>();
				lista = control.obtenerDetallesOrdenados();
				System.out.println(tienda);
				System.out.println(lista);

				System.out.print("Desea aceptar el pedido (S/N)?: ");
				String respuesta1 = consola.next();

				if (respuesta1.equals("S")) {
					System.out.println("Aceptaste el pedido.");
					control.guardarOrden();
				} else {
					System.out.println("No aceptaste el pedido.");
				}

			} catch (IOException e) {
				System.out.println("URL NO VÁLIDA,FAVOR VOLVER A INICIAR.");
			}

		} else {
			System.out.println("Tienda no encontrada.");
		}

	}

}
