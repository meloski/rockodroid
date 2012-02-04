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
import com.rockodroid.model.vo.PlayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * Proporciona una interafaz para acceder a los recursos
 * multimedia que se encuentran en el dispositivo.
 * 
 * @author Juan C. Orozco
 * @author Roberto R. De La Parra
 */
public class MediaStore {

	private final Context mContext;
	private final ContentResolver resolver;

	/* URIs de consulta */
	private final static Uri uriMedia = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	private final static Uri uriArtista = android.provider.MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
	private final static Uri uriAlbum = android.provider.MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
	private final static Uri uriPlaylist = android.provider.MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
	
	/*Strings*/
	// Artista 
	public final static String ARTIST_ID = android.provider.MediaStore.Audio.Artists._ID;
	public final static String ARTIST = android.provider.MediaStore.Audio.Artists.ARTIST;
	// album
	public final static String ALBUM = android.provider.MediaStore.Audio.Albums.ALBUM;
	public final static String ALBUM_ID =android.provider.MediaStore.Audio.Albums._ID;
    public final static String ALBUM_NUMBER_OF_SONGS = android.provider.MediaStore.Audio.Albums.NUMBER_OF_SONGS;
    public final static String ALBUM_ART = android.provider.MediaStore.Audio.Albums.ALBUM_ART;
    public final static String ALBUM_ARTISTA = android.provider.MediaStore.Audio.Albums.ARTIST;
    //Media
    public final static String MEDIA_ID = android.provider.MediaStore.Audio.Media._ID;
    public final static String MEDIA_TITLE= android.provider.MediaStore.Audio.Media.TITLE;
    public final static String MEDIA_SIZE= android.provider.MediaStore.Audio.Media.SIZE;
    public final static String MEDIA_DURATION= android.provider.MediaStore.Audio.Media.DURATION;
    public final static String MEDIA_TRACK= android.provider.MediaStore.Audio.Media.TRACK;
    public final static String MEDIA_YEAR= android.provider.MediaStore.Audio.Media.YEAR;
    public final static String MEDIA_ARTIST= android.provider.MediaStore.Audio.Media.ARTIST;
    public final static String MEDIA_ALBUM= android.provider.MediaStore.Audio.Media.ALBUM;
    public final static String MEDIA_ALBUM_ID = android.provider.MediaStore.Audio.Media.ALBUM_ID;
    //Playlist
    public final static String PLAYLIST_ID = android.provider.MediaStore.Audio.Playlists._ID;
    public final static String PLAYLIST_NAME = android.provider.MediaStore.Audio.Playlists.NAME;
    // PlayList Members
    public final static String MEMBERS_PLAYLIST_ID = android.provider.MediaStore.Audio.Playlists.Members.PLAYLIST_ID;
    public final static String MEMBERS_PLAYLIST_ORDER = android.provider.MediaStore.Audio.Playlists.Members.PLAY_ORDER;
    
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
		String []proyeccion = {ARTIST,ARTIST_ID};		
		Cursor cursor = resolver.query(uriArtista, proyeccion, null, null, null);
		if (cursor == null) {
			// falló la consulta
			return null;
		}else if (cursor.moveToFirst()) {
			int nombreArtista = cursor.getColumnIndex(ARTIST);
			int idArtista = cursor.getColumnIndex(ARTIST_ID);
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
		String []proyeccion ={ALBUM,ALBUM_ID,ALBUM_NUMBER_OF_SONGS,ALBUM_ART};
		String artistaColumn = ALBUM_ARTISTA;
		Cursor c = resolver.query(uriAlbum, proyeccion, artistaColumn + "=?", new String[] {idArtista}, null);
		if(c == null) {
			return null;
		}else if(c.moveToFirst()) {
			int tituloAlbum = c.getColumnIndex(ALBUM);
			int idAlbum = c.getColumnIndex(ALBUM_ID);
			int nSongs = c.getColumnIndex(ALBUM_NUMBER_OF_SONGS); 
			//int art = c.getColumnIndex(ALBUM_ART);
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
		String []proyeccion ={ALBUM,ALBUM_ID,ALBUM_NUMBER_OF_SONGS,ALBUM_ART};
		Cursor c = resolver.query(uriAlbum, proyeccion, null, null, ALBUM);
		if(c == null) {
			return null;
		}else if(c.moveToFirst()) {
			int tituloColumn = c.getColumnIndex(ALBUM);
			int idAlbumColumn = c.getColumnIndex(ALBUM_ID);
			int numSongsColumn = c.getColumnIndex(ALBUM_NUMBER_OF_SONGS); 
			//int artColumn = c.getColumnIndex(ALBUM_ART);
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
		String []proyeccion = {MEDIA_ID,MEDIA_TITLE,MEDIA_SIZE,MEDIA_DURATION,MEDIA_TRACK,MEDIA_YEAR,MEDIA_ARTIST,MEDIA_ALBUM};
		Cursor cursor = resolver.query(uriMedia, proyeccion, null, null, null);
		if(cursor == null) {
			return null;
		}else if(cursor.moveToFirst())  {
			int audioIdColumn = cursor.getColumnIndex(MEDIA_ID);
			int audioTituloColumn = cursor.getColumnIndex(MEDIA_TITLE);
			int audioSizeColumn = cursor.getColumnIndex(MEDIA_SIZE);
			int audioLengColumn = cursor.getColumnIndex(MEDIA_DURATION);
			int audioTrackColumn = cursor.getColumnIndex(MEDIA_TRACK);
			int audioYearColumn = cursor.getColumnIndex(MEDIA_YEAR);
			int audioArtistColumn = cursor.getColumnIndex(MEDIA_ARTIST);
			int audioAlbumColumn = cursor.getColumnIndex(MEDIA_ALBUM);
			Audio audio;
			int audioId, audioTrack, audioYear;
			long audioSize, audioLength;
			String audioTitle, audioArtist, audioAlbum;
			do {
				audioId = cursor.getInt(audioIdColumn);
				audioTitle = cursor.getString(audioTituloColumn);
				audioSize = cursor.getLong(audioSizeColumn);
				audioLength = cursor.getLong(audioLengColumn);
				audioTrack = cursor.getInt(audioTrackColumn);
				audioYear = cursor.getInt(audioYearColumn);
				audioArtist = cursor.getString(audioArtistColumn);
				audioAlbum = cursor.getString(audioAlbumColumn);
				audio = new Audio(audioId, audioTitle, audioSize, audioLength, 
						audioTrack, audioYear, audioArtist, audioAlbum);
				audios.add(audio);
			}while(cursor.moveToNext());
		}
		return audios;
	}

	/**
	 * @return ArrayList<PlayList> - Colección de todas las playlists guardadas. 
	 */
	public ArrayList<PlayList> buscarPlayLists() {
		ArrayList<PlayList> lists = new ArrayList<PlayList>();
		String []proyeccion = {PLAYLIST_ID,PLAYLIST_NAME};
		Cursor cursor = resolver.query(uriPlaylist, proyeccion, null, null, null);
		if(cursor == null) {
			return null;
		}else if(cursor.moveToFirst()) {
			int playListIdColumn = cursor.getColumnIndex(PLAYLIST_ID);
			int playListNameColumn = cursor.getColumnIndex(PLAYLIST_NAME);
			do {
				lists.add(new PlayList(cursor.getInt(playListIdColumn),cursor.getString(playListNameColumn)));
			}while(cursor.moveToNext());
		}
		return lists;
	}

	/**
	 * Encuentra los Audios en un Album específico. 
	 * @return ArrayList<Audio> - Contiene las instancias de audio encontradas.
	 */
	public ArrayList<Audio> buscarAudioEn(String albumId) {
		ArrayList<Audio> audios = new ArrayList<Audio>();
		String albumIdColumn = MEDIA_ALBUM_ID;
		String []proyeccion = {MEDIA_ID,MEDIA_TITLE,MEDIA_SIZE,MEDIA_DURATION,MEDIA_TRACK,MEDIA_YEAR,MEDIA_ARTIST,MEDIA_ALBUM};
		Cursor cursor = resolver.query(uriMedia, proyeccion, albumIdColumn + "=?", new String[] {albumId}, null);
		if(cursor == null) {
			return null;
		}else if(cursor.moveToFirst())  {
			int audioIdColumn = cursor.getColumnIndex(MEDIA_ID);
			int audioTituloColumn = cursor.getColumnIndex(MEDIA_TITLE);
			int audioSizeColumn = cursor.getColumnIndex(MEDIA_SIZE);
			int audioLengColumn = cursor.getColumnIndex(MEDIA_DURATION);
			int audioTrackColumn = cursor.getColumnIndex(MEDIA_TRACK);
			int audioYearColumn = cursor.getColumnIndex(MEDIA_YEAR);
			int audioArtistColumn = cursor.getColumnIndex(MEDIA_ARTIST);
			int audioAlbumColumn = cursor.getColumnIndex(MEDIA_ALBUM);
			Audio audio;
			int audioId, audioTrack, audioYear;
			long audioSize, audioLength;
			String audioTitle, audioArtist, audioAlbum;
			do {
				audioId = cursor.getInt(audioIdColumn);
				audioTitle = cursor.getString(audioTituloColumn);
				audioSize = cursor.getLong(audioSizeColumn);
				audioLength = cursor.getLong(audioLengColumn);
				audioTrack = cursor.getInt(audioTrackColumn);
				audioYear = cursor.getInt(audioYearColumn);
				audioArtist = cursor.getString(audioArtistColumn);
				audioAlbum = cursor.getString(audioAlbumColumn);
				audio = new Audio(audioId, audioTitle, audioSize, audioLength, audioTrack,
						audioYear, audioArtist, audioAlbum);
				audios.add(audio);
			}while(cursor.moveToNext());
		}
		return audios;
	}

	/**
	 * Buscar y retorna los elementos de una lista de reproducción específica.
	 * @return ArrayList<Audio> - Collección de Audio de la lista indicada.
	 */
	public ArrayList<Audio> buscarAudioDePlayList(String playId) {
		ArrayList<Audio> audios = new ArrayList<Audio>();
		Uri uri = android.provider.MediaStore.Audio.Playlists.Members.getContentUri("external", Long.parseLong(playId));
		String [] proyeccion = {MEDIA_ID,MEDIA_TITLE,MEDIA_SIZE,MEDIA_DURATION,MEDIA_TRACK,MEDIA_YEAR,MEDIA_ARTIST,MEDIA_ALBUM};
		Cursor cursor = resolver.query(uri, proyeccion, null ,null , MEMBERS_PLAYLIST_ORDER);
		if(cursor == null) {
			return null;
		}else if(cursor.moveToFirst())  {
			int audioIdColumn = cursor.getColumnIndex(MEDIA_ID);
			int audioTituloColumn = cursor.getColumnIndex(MEDIA_TITLE);
			int audioSizeColumn = cursor.getColumnIndex(MEDIA_SIZE);
			int audioLengColumn = cursor.getColumnIndex(MEDIA_DURATION);
			int audioTrackColumn = cursor.getColumnIndex(MEDIA_TRACK);
			int audioYearColumn = cursor.getColumnIndex(MEDIA_YEAR);
			int audioArtistColumn = cursor.getColumnIndex(MEDIA_ARTIST);
			int audioAlbumColumn = cursor.getColumnIndex(MEDIA_ALBUM);
			Audio audio;
			int audioId, audioTrack, audioYear;
			long audioSize, audioLength;
			String audioTitle, audioArtist, audioAlbum;
			do {
				audioId = cursor.getInt(audioIdColumn);
				audioTitle = cursor.getString(audioTituloColumn);
				audioSize = cursor.getLong(audioSizeColumn);
				audioLength = cursor.getLong(audioLengColumn);
				audioTrack = cursor.getInt(audioTrackColumn);
				audioYear = cursor.getInt(audioYearColumn);
				audioArtist = cursor.getString(audioArtistColumn);
				audioAlbum = cursor.getString(audioAlbumColumn);
				audio = new Audio(audioId, audioTitle, audioSize, audioLength, audioTrack,
						audioYear, audioArtist, audioAlbum);
				audios.add(audio);
			}while(cursor.moveToNext());
		}
		cursor.close();
		return audios;
	}
}