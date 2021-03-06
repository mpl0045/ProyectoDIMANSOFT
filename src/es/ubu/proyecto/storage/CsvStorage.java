package es.ubu.proyecto.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import es.ubu.proyecto.model.*;

/**
 * Clase que guarda la información de la lista de la compra
 * y la lista de favoritos como archivos csv.
 * @author Marcos Orive Izarra.
 *
 */
public class CsvStorage{
	/**
	 * Constante de clase que guarda la ruta del fichero de la lista de la compra.
	 */
	private static final String 	FICHEROLISTACOMPRA="files/savedList.csv";
	/**
	 * Constante de clase que guarda la ruta del fichero de la lista de la compra.
	 */
	private static final String FICHEROLISTAFAVORITOS="files/savedFav.csv";
	
	/**
	 * Método que escribe el archivo de la lista de la compra.
	 * @param lista, la lista de la compra.
	 * @return true si ha podido escribirlo sin problema.
	 */
	public boolean writeListaCompra(ListaCompra lista) {
		try{
			BufferedWriter bufferEscritor = new BufferedWriter(new  FileWriter(FICHEROLISTACOMPRA));
			
			for(Linea l: lista.getLista()) {
				bufferEscritor.write(l.getProducto().getNombre()+";");
				bufferEscritor.write(l.getCantidad()+";");
				bufferEscritor.write(Boolean.toString(l.getComprado()));
				bufferEscritor.newLine();
			}
			
			bufferEscritor.close();
		}catch(IOException ex) {
			System.out.println("Ha habido un error con los archivos");
			return false;
         }

		
		return true;
	}
	
	/**
	 * Método que escribe el archivo de la lista de favoritos.
	 * @param lista, la lista de favotios.
	 * @return true si ha podido escribirlo sin problema.
	 */
	public boolean writeListaFavoritos(ListaFavoritos favoritos) {
		try{
			BufferedWriter bufferEscritor = new BufferedWriter(new  FileWriter(FICHEROLISTAFAVORITOS));
			
			for(Producto p: favoritos.getFavoritos()) {
				bufferEscritor.write(p.getNombre() + "\n");
			}
			
			bufferEscritor.close();
		}catch(IOException ex) {
			return false;
         }

		
		return true;
	}

	/**
	 * Lee la lista de la compra.
	 * @return Devuelve una lisa llena si ha podido leerla y había algo. Una lista vacía si ha habido problemas o está vacia.
	 */
	public ListaCompra readListaCompra() {
		ListaCompra lista = new ListaCompra();
        try {
        		BufferedReader lector = new BufferedReader(new FileReader(FICHEROLISTACOMPRA));
			Scanner escaner = null;
			
			String linea=null;
			String nombreProducto;
			int cantidad;
			boolean comprado;
			while((linea = lector.readLine()) != null) {
				escaner= new Scanner(linea);
				escaner.useDelimiter(";");
				nombreProducto=escaner.next();
				cantidad=Integer.parseInt(escaner.next());
				comprado= Boolean.parseBoolean(escaner.next());
				Producto p = new Producto(nombreProducto);
				Linea l = new Linea(p,cantidad);
				l.setComprado(comprado);
				lista.add(l);
			}
			if(lector != null) {
				lector.close();
			}
			if(escaner != null){
				escaner.close();
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("No se ha podido cargar la lista desde csvStorage");
			return lista;
		} catch (NumberFormatException e) {
			System.out.println("Ha habido un problema");
			return lista;
		} catch (IOException e) {
			System.out.println("Ha habido un problema");
			return lista;
		}

		
		return lista;
		
	}
	/**
	 * Lee la lista de favoritos.
	 * @return Devuelve una lisa llena si ha podido leerla y había algo. Una lista vacía si ha habido problemas o está vacia.
	 */
	public ListaFavoritos readListaFavoritos() {
		ListaFavoritos favoritos = new ListaFavoritos();
		try {
			BufferedReader lector = new BufferedReader(new FileReader(FICHEROLISTAFAVORITOS));
			String linea=null;
			while((linea = lector.readLine()) != null) {
				favoritos.add(linea);
			}
			lector.close();
		} catch (FileNotFoundException e) {
			System.out.println("Ha habido un problema");
			return favoritos;
		} catch (IOException e) {
			System.out.println("Ha habido un problema");
			return favoritos;
		}

		
		return favoritos;
		
	}

}
