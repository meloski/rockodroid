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

import android.graphics.drawable.Drawable;

/**
 * Representa un album musical del cual se tiene como mínimo
 * una canción en el dispositivo.
 * 
 * @author Juan C. Orozco
 */
public class Album {

	private final int id;
	private final String titulo;
	private final Drawable albumArt;
	
	public Album(int id, String titulo, Drawable art) {
		this.id = id;
		this.titulo = titulo;
		this.albumArt = art;
	}
	
	public int getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public Drawable getAlbumArt() {
		return albumArt;
	}
	
}
