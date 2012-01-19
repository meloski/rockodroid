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

	private final static String ARTISTA = android.provider.MediaStore.Audio.Media.ARTIST;

	/* URIs de consulta */
	private final static Uri uriMedia = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	private final static Uri uriArtista = android.provider.MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
	private final static Uri uriAlbum = android.provider.MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
	private final static Uri uriPlaylist = android.provider.MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;

	public MediaStore(Context c) {
		this.mContext = c;
		resolver = mContext.getContentResolver();
	}

	public ArrayList buscarArtistas() {
		ArrayList artistas = new ArrayList();

		Cursor cursor = resolver.query(uriArtista, null, null, null, null);
		if (cursor == null) {
			// falló la consulta
			return null;
		}else if (!cursor.moveToFirst()) {
			//No hay resultados: retornar array vacío
		}else{
			int nombreArtista = cursor.getColumnIndex(android.provider.MediaStore.Audio.Artists.ARTIST);
			int idArtista = cursor.getColumnIndex(android.provider.MediaStore.Audio.Artists._ID);
			do {
				//Procesar entrada, es necesario la clase Artista.
				cursor.getString(nombreArtista);
				cursor.getString(idArtista);
				//artistas.add();
			}while(cursor.moveToNext());
		}
		//http://developer.android.com/reference/android/provider/MediaStore.Audio.Media.html
		return artistas;
	}
}