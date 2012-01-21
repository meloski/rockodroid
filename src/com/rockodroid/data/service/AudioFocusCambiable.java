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
package com.rockodroid.data.service;

/**
 * Representa un objeto que puede responder ante eventos del AudioFocus.
 * Define distintos métodos según los eventos que pueden ocurrir al cambiar
 * el AudioFocus. 
 * 
 * @author Juan C. Orozco
 */
public interface AudioFocusCambiable {

	/**
	 * Método que define la acción a realizar al ganar el foco.
	 */
	public void alGanarFoco();
	
	/**
	 * Método que define la acción a realizar al perder el foco
	 * por tiempo indefinido. Se deberían liberar los recursos.
	 */
	public void alPerderFoco();
	
	/**
	 * Método que define la acción a realizar al perder el foco
	 * por corto tiempo, no se deberían liberar los recursos.
	 * Puede ser por ejemplo una notificación.
	 * @param mermar - Indica si es posible seguir reproduciendo con un sonido
	 * mas bajo.
	 */
	public void alPerderFocoMomentaneo(boolean mermar);
}
