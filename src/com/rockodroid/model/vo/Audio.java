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

/**
 * Representa un item multimedia de audio.
 * Tiene las propiedades heredadas de MediaItem además de las propias
 * duración, track, año
 * @author Juan C. Orozco
 */
public class Audio extends MediaItem {

	/** duración del item de audio en milisegundos */
	private final long duracion;
	private final int nPista;
	private final int anio;
	private final String artista;
	private final String album;
	
	public Audio(int iden, String title, long size, long lon, int track, int year, String artist, String disc) {
		super(iden, title, size, TipoMedia.Audio);
		duracion = lon;
		nPista = track;
		anio = year;
		artista = artist;
		album = disc;
	}

	public long getDuracion() {
		return duracion;
	}

	public int getTrack() {
		return nPista;
	}

	public int getYear() {
		return anio;
	}

	public String getArtista() {
		return artista;
	}

	public String getAlbum() {
		return album;
	}

}
