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
package com.rockodroid.view;

import com.rockodroid.data.media.MediaStore;
import com.rockodroid.model.listadapter.AudioListAdapter;

import android.content.Context;
import android.os.Bundle;

/**
 * Muestra los items Audio de los un album o playlist.
 * Esta clase hereda de AudiolistActivity y sobreescribiendo el adaptador y
 * el método onCreate.
 * Es necesario especificar el id y el tipo de id que se enviará, el tipo de id
 * puede ser de playlist o de album.
 * @see AudioListActivity
 * @see AudioListAdapter
 * @author Juan C. Orozco
 */
public class ItemExploradorActivity extends AudioListActivity {

	public static String PLAYLIST_ITEMS = "playlist_items";
	public static String ALBUM_ITEMS = "album_items";

	/* Elementos sobreescritos del padre NO heredados */
	private static AudioListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Context context = getApplicationContext();
		MediaStore mStore = new MediaStore(context);
		getListView().setFastScrollEnabled(true);
		//Se obtienen los extras para saber que datos debe tener el adaptador
		Bundle bundle = getIntent().getExtras();
		if(bundle == null) {
			return;
		}
		String tipo = bundle.getString("tipoID");
		String id = bundle.getString("id");
		if(tipo.equals(PLAYLIST_ITEMS)) {
			adapter = new AudioListAdapter(context, mStore.buscarAudioDePlayList(id));
		}else if(tipo.equals(ALBUM_ITEMS)) {
			adapter = new AudioListAdapter(context, mStore.buscarAudioEn(id));
		}
		setListAdapter(adapter);
	}
}