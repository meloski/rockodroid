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
package com.rockodroid.data.media;

import java.util.ArrayList;

import com.rockodroid.R;
import com.rockodroid.model.vo.Album;
import com.rockodroid.model.vo.Artista;
import com.rockodroid.model.vo.Audio;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * Proporciona una interafaz para acceder a los recursos
 * multimedia que se encuentran en el dispositivo.
 * 
 * @author Juan C. Orozco
 *
 */
public class MediaStore {

	private final Context mContext;
	private final ContentResolver resolver;

	/* URIs de consulta */
	private final static Uri uriMedia = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	private final static Uri uriArtista = android.provider.MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
	private final static Uri uriAlbum = android.provider.MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
	private final static Uri uriPlaylist = android.provider.MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;

	public MediaStore(Context c) {
		this.mContext = c;
		resolver = mContext.getContentResolver();
	}

	/**
	 * Buscar los artistas que se encuentran registrados en el sistema.
	 * @return ArrayList con todos los artistas encontrados.
	 */
	public ArrayList<Artista> buscarArtistas() {
		ArrayList<Artista> artistas = new ArrayList<Artista>();
		
		Cursor cursor = resolver.query(uriArtista, null, null, null, null);
		if (cursor == null) {
			// falló la consulta
			return null;
		}else if (cursor.moveToFirst()) {
			int nombreArtista = cursor.getColumnIndex(android.provider.MediaStore.Audio.Artists.ARTIST);
			int idArtista = cursor.getColumnIndex(android.provider.MediaStore.Audio.Artists._ID);
			Artista artista;
			do {
				artista = new Artista(cursor.getString(nombreArtista), cursor.getString(idArtista));
				artista.establecerDiscos(buscarAlbumDe(cursor.getString(nombreArtista)));
				artistas.add(artista);
			}while(cursor.moveToNext());
		}
		return artistas;
	}

	/**
	 * 
	 * @param keyArtista - Artist_key del content provider.
	 * @return ArrayList que contiene los albumes del artista indentificado con jeyArtista.
	 */
	public ArrayList<Album> buscarAlbumDe(String idArtista) {
		ArrayList<Album> albums = new ArrayList<Album>();
		String artistaColumn = android.provider.MediaStore.Audio.Albums.ARTIST;
		Cursor c = resolver.query(uriAlbum, null, artistaColumn + "=?", new String[] {idArtista}, null);
		if(c == null) {
			return null;
		}else if(c.moveToFirst()) {
			int tituloAlbum = c.getColumnIndex(android.provider.MediaStore.Audio.Albums.ALBUM);
			int idAlbum = c.getColumnIndex(android.provider.MediaStore.Audio.Albums._ID);
			int nSongs = c.getColumnIndex(android.provider.MediaStore.Audio.Albums.NUMBER_OF_SONGS); 
			int art = c.getColumnIndex(android.provider.MediaStore.Audio.Albums.ALBUM_ART);
			Album album;
			int albumId, numCanciones;
			String albumTitulo;
			do {
				albumTitulo =  c.getString(tituloAlbum);
				albumId = c.getInt(idAlbum);
				numCanciones = c.getInt(nSongs);
				album = new Album(albumId, albumTitulo, numCanciones, mContext.getResources().getDrawable(R.drawable.ic_disco));
				albums.add(album);
			}while(c.moveToNext());
		}
		return albums;
	}
	
	/**
	 * Busca por medio del provider del sistema todos los albums de los cuales
	 * se tenga como mínimo una canción en el dispositivo.
	 * @return ArrayList<Album> - Colleccion de todos lo albums encontrados.
	 */
	public ArrayList<Album> buscarAlbums() {
		ArrayList<Album> albums = new ArrayList<Album>();
		Cursor c = resolver.query(uriAlbum, null, null, null, android.provider.MediaStore.Audio.Albums.ALBUM);
		if(c == null) {
			return null;
		}else if(c.moveToFirst()) {
			int tituloColumn = c.getColumnIndex(android.provider.MediaStore.Audio.Albums.ALBUM);
			int idAlbumColumn = c.getColumnIndex(android.provider.MediaStore.Audio.Albums._ID);
			int numSongsColumn = c.getColumnIndex(android.provider.MediaStore.Audio.Albums.NUMBER_OF_SONGS); 
			int artColumn = c.getColumnIndex(android.provider.MediaStore.Audio.Albums.ALBUM_ART);
			Album album;
			int albumId, numCanciones;
			String albumTitulo;
			do {
				albumTitulo =  c.getString(tituloColumn);
				albumId = c.getInt(idAlbumColumn);
				numCanciones = c.getInt(numSongsColumn);
				album = new Album(albumId, albumTitulo, numCanciones, mContext.getResources().getDrawable(R.drawable.ic_disco));
				albums.add(album);
			}while(c.moveToNext());
		}
		return albums;
	}

	/**
	 * Buscar y retorna todos los items de audio encontrados en el dispositivo.
	 * @return ArrayList<Audio> - Colleccion de instancias Audio
	 */
	public ArrayList<Audio> buscarAudio() {
		ArrayList<Audio> audios = new ArrayList<Audio>();
		Cursor cursor = resolver.query(uriMedia, null, null, null, null);
		if(cursor == null) {
			return null;
		}else if(cursor.moveToFirst())  {
			int audioIdColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
			int audioTituloColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
			int audioSizeColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.SIZE);
			int audioLengColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.DURATION);
			int audioTrackColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TRACK);
			int audioYearColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.YEAR);
			int audioArtistColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST);

			Audio audio;
			int audioId, audioTrack, audioYear;
			long audioSize, audioLength;
			String audioTitle, audioArtist;
			do {
				audioId = cursor.getInt(audioIdColumn);
				audioTitle = cursor.getString(audioTituloColumn);
				audioSize = cursor.getLong(audioSizeColumn);
				audioLength = cursor.getLong(audioLengColumn);
				audioTrack = cursor.getInt(audioTrackColumn);
				audioYear = cursor.getInt(audioYearColumn);
				audioArtist = cursor.getString(audioArtistColumn);
				audio = new Audio(audioId, audioTitle, audioSize, audioLength, audioTrack, audioYear, audioArtist);
				audios.add(audio);
			}while(cursor.moveToNext());
		}
		return audios;
	}
}