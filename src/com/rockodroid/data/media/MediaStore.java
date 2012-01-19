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