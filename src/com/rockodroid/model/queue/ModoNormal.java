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

/**
 * Iterador que representa el modo de eleccion secuencial.
 * @author Juan C. Orozco
 */
public class ModoNormal implements ModoEleccion {

	/** Máximo número de elementos */
	private int cantidad;

	/** elemento actual */
	private int actual;

	public ModoNormal(int cantidad, int actual) {
		this.cantidad = cantidad;
		this.actual = actual;
	}

	/**
	 * Retorna la posicion del siguiente elemento.
	 */
	public int getSiguiente() {
		if(actual++ == cantidad) actual = -1;
		return actual;
	}

	/**
	 * Retorna la posicion del anterior elemento.
	 */
	public int getAnterior() {
		if(actual-- < 0) actual = -1; 
		return actual;
	}

	/**
	 * Retorna el índice actual
	 */
	public int getActual() {
		return actual;
	}

	/**
	 * Establece el elemento actual. A partir de ese elemento
	 * se seguirá moviendo el iterador.
	 */
	public void setActual(int actual) {
		this.actual = actual;
	}
}