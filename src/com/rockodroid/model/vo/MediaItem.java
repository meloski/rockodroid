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

import android.content.ContentUris;
import android.net.Uri;

/**
 * Representa un Item Multimedia: audio o video.
 * Contiene las propiedades básicas de ambos items soportados.
 * @author Juan C. Orozco
 */
public class MediaItem {

	/**
	 * Los tipos multimedia soportados por la aplicación. 
	 */
	public enum TipoMedia {Audio, Video};
	
	private TipoMedia tipo;
	private final int id;
	private final String titulo;

	/** Tamaño en bytes del archivo */
	private final long tamanio;

	public MediaItem(int iden, String title, long size, TipoMedia tipoMedia) {
		id = iden;
		titulo = title;
		tamanio = size;
		tipo = tipoMedia;
	}

	public TipoMedia getTipo() {
		return tipo;
	}

	public void setTipo(TipoMedia tipo) {
		this.tipo = tipo;
	}

	public int getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	//El tamaño en bytes
	public long getTamanio() {
		return tamanio;
	}
	
	public Uri getUri() {
		return ContentUris.withAppendedId(
				android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, (long)getId());
	}
}
