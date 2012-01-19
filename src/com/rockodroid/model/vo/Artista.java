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
package com.rockodroid.model.vo;

import java.util.ArrayList;

/**
 * Representa un artista con su colección de discos.
 * 
 * @author Juan C. Orozco
 */
public class Artista {

	/* Infomación básica del artista*/
	private final String id;
	private final String nombre;
	private final ArrayList<Album> discos;
	
	public Artista(String name, String id) {
		this.id = id;
		this.nombre = name;
		discos = new ArrayList<Album>();
	}

	public String getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}
	
	public int numDiscos() {
		return discos.size();
	}

	public ArrayList<Album> getDiscos() {
		return discos;
	}
	
	public void addAlbum(Album album) {
		if (album == null)
			discos.add(album);
	}
}