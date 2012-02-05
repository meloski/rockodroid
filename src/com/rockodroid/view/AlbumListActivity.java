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

import com.rockodroid.R;
import com.rockodroid.data.media.MediaStore;
import com.rockodroid.model.listadapter.AlbumListAdapter;
import com.rockodroid.model.queue.Queue;
import com.rockodroid.model.vo.Album;
import com.rockodroid.model.vo.Audio;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

/**
 * Presenta la vista para la lista total de albums encontrados 
 * en el almacenamiento primario "external"
 * @see AlbumListAdapter
 * @author Juan C. Orozco
 */
public class AlbumListActivity extends ListActivity {

	private static Queue cola;
	private static MediaStore mStore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Context context = getApplicationContext();
		cola = Queue.getCola();
		mStore = new MediaStore(context);
		
		setListAdapter(new AlbumListAdapter(context, mStore.buscarAlbums()));
		
		getListView().setFastScrollEnabled(true);
		registerForContextMenu(getListView());
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflador = getMenuInflater();
		inflador.inflate(R.menu.menu_contextual_item, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.menu_context_play:
			cola.limpiar();
		case R.id.menu_context_enqueue:
			agregarACola(item);
			return true;
		case R.id.menu_context_ver_cola:
			startActivity(new Intent(this, QueueActivity.class));
			return true;
			default:
				return super.onContextItemSelected(item);
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		//Se obtiene el item seleccionado
		Album album = (Album) getListAdapter().getItem(position);
		Intent i = new Intent(this, ItemExploradorActivity.class);
		i.putExtra("tipoID", ItemExploradorActivity.ALBUM_ITEMS);
		i.putExtra("id", String.valueOf(album.getId()));
		startActivity(i);
	}

	private void agregarACola(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		Album album = (Album)getListAdapter().getItem(info.position);
		for(Audio a: mStore.buscarAudioEn(String.valueOf(album.getId()))) cola.agregar(a);
	}
}