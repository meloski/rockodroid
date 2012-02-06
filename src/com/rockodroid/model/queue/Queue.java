/*
    Rockodroid: Music Player for android
    Copyright (C) 2012  Laura K. Salazar, Roberto R. De la Parra, Juan C. Orozco.

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.rockodroid.model.queue;

import java.util.ArrayList;
import java.util.List;

import com.rockodroid.model.vo.MediaItem;

/**
 * Cola de reproducción de la aplicación.
 * Contiene el conjunto de MediaItems que han sido añadidos para reproducción.
 * Esta clase es un singleton, lo que implica que solo hay una instancia de ella
 * en toda la aplicación. Todos los componentes que quieran acceder a la cola
 * lo harán sobre la misma instancia.
 * 
 * @author Juan C. Orozco
 */
public class Queue {

	/* Instancia única */
	private static Queue INSTANCIA;

	/* Indica cual es el modo de repetición de la cola.
	 * Normal: Suena una vez
	 * ITEM: Se repite el item actual infinitas veces
	 * LISTA: Al finalizar la lista se reinicia */
	public enum ModoRepeticion {NORMAL, ITEM, LISTA};

	private ModoRepeticion mRepet;

	private ModoEleccion mEleccion;

	private ArrayList<MediaItem> elementos;

	private int current;

	// Constructor privado garantiza que no se crearán instancias fuera de esta clase.
	private Queue() {
		elementos = new ArrayList<MediaItem>();
		mRepet = ModoRepeticion.NORMAL;
		current = -1;
		configurarModoEleccion(new ModoNormal(elementos.size(), current)); //mEleccion = new ModoNormal(elementos.size(), current);
	}

	/* Métodos singletón */

	/**
	 * Este es el método propio de un singleton para permitir el acceso
	 * a la única instancia de Queue a los componentes del programa.
	 */
	public synchronized static Queue getCola() {
		if (INSTANCIA == null) INSTANCIA = new Queue();
		return INSTANCIA;
	}

	/* No se permite la clonación de objetos de esta clase.
	 * Esto se hace para garantizar la instancia única. 
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException(); 
	}

	/* Métodos para la gestión de la cola */

	/**
	 * Agrega un elemento MediaItem al final de la cola.
	 * @param media
	 */
	public void agregar(MediaItem media) {
		elementos.add(media);
		if(current == -1) current = 0;
		configurarModoEleccion(null);
	}

	/**
	 * Agrega un conjunto de elementos MediaItem al final de la cola.
	 * @param media
	 */
	public void agregar(List<MediaItem> media) {
		for(MediaItem m: media) elementos.add(m);
		if(current == -1) current = 0;
		configurarModoEleccion(null);
	}

	/**
	 * Eliminar el elemento i de la cola de reproducción.
	 * @param i - Indice del elemento en la lista.
	 */
	public void eliminar(int i) {
		elementos.remove(i);
		if(current == i) {
			if(i != 0) {
				current--;
			}
		}
		configurarModoEleccion(null);
	}

	/**
	 * Ubica en la posición j el elemento de la posición i.
	 * @param i - Posición inicial del elemento.
	 * @param j - Posición final del elemento.
	 */
	public void mover(int i, int j) {
		
	}

	/**
	 * Elimina todos los elementos de la cola de reproduccion.
	 */
	public void limpiar() {
		elementos.clear();
		current = -1;
		configurarModoEleccion(null);
	}

	/**
	 * Avanza al siguiente elemento de la cola y lo retorna.
	 * El siguiente elemento dependerá del modo de reproducción
	 * y repetición.
	 * @return MediaItem - El elemento siguiente en la cola.
	 */
	public MediaItem getSiguiente() {
		return null;
	}

	/**
	 * Retrocede al anterior elemento de la cola y lo retorna.
	 * La elección del anterior elemento dependerá del modo de
	 * reproducción y de repetición.
	 * @return MediaItem - El anterior elemento en la cola.
	 */
	public MediaItem getAnterior() {
		return null;
	}

	/**
	 * Establece el modo de elección de la cola.
	 * @param modo - Indica el modo de elección a establecer, null para establecer el mismo.
	 */
	private void configurarModoEleccion(ModoEleccion modo) {
		if(modo == null) {
			if(mEleccion instanceof ModoNormal) {
				mEleccion = new ModoNormal(elementos.size(), current);
			}else if(mEleccion instanceof ModoAleatorio) {
				mEleccion = new ModoAleatorio(elementos.size(), current);
			}
		}else {
			mEleccion = modo;
		}
	}

	/**
	 * Obtiene el elemento que actualmente se está reproduciendo.
	 * @return MediaItem - elemento actualmente seleccionado.
	 */
	public MediaItem getActual() {
		if (current != -1)
			return elementos.get(current);
		return null;
	}

	/**
	 * Establece el elemento que se reproducirá.
	 * @param actual - posición actual en la lista.
	 */
	public void setActual(int actual) {
		if(actual >= 0 && actual < elementos.size()) {
			current = actual;
			configurarModoEleccion(null);
		}
	}

	/* Accesores */

	public List<MediaItem> getElementos() {
		return this.elementos;
	}

	public ModoRepeticion getModoRepeticion() {
		return mRepet;
	}

	public void setModoRepeticion(ModoRepeticion mRepet) {
		this.mRepet = mRepet;
	}

	public ModoEleccion getModoEleccion() {
		return mEleccion;
	}

	/**
	 * Indica si la cola se establece de forma aleatoria o normal.
	 * @param aleatorio - indica si se establece de forma aleatoria.  
	 */
	public void setAleatorio(boolean aleatorio) {
		if(aleatorio)
			this.mEleccion = new ModoAleatorio(elementos.size(), current);
		else
			this.mEleccion = new ModoNormal(elementos.size(), current);
	}

}