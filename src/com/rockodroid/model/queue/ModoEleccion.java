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
 * Define los métodos para navegar entre los elementos de la Cola de reproducción.
 * Permite implementar comportamientos diferentes para esta acción.
 * 
 * @author Juan C. Orozco
 */
public interface ModoEleccion {

	/**
	 * Obtiene el índice del siguiente elemento. 
	 */
	int getSiguiente();

	/**
	 * Obtiene el índice del anterior elemento.
	 */
	int getAnterior();

	/**
	 * Obtiene el índice del actual elemento.
	 */
	int getActual();

	/**
	 * Establece el actual elemento. 
	 */
	void setActual(int actual);

	/** Regresa el iterador hacia la primera posicion*/
	void reiniciar();

	/** Avanza hasta el ultimo elemento */
	void avanzarHastaUltimo();
}