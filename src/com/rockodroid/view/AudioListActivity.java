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
import com.rockodroid.model.listadapter.AudioListAdapter;
import com.rockodroid.model.queue.Queue;
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
import android.widget.AdapterView.AdapterContextMenuInfo;

/**
 * Muestra una lista de items que representan los archivos de audio
 * almacenados en el dispositivo.
 * 
 * @author Juan C. Orozco
 */
public class AudioListActivity extends ListActivity{

	private static Queue cola;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cola = Queue.getCola();
		Context context = getApplicationContext();
		MediaStore mStore = new MediaStore(context);
		getListView().setFastScrollEnabled(true);

		setListAdapter(new AudioListAdapter(context, mStore.buscarAudio()));

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

	private void agregarACola(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		Audio audio = (Audio)getListAdapter().getItem(info.position);
		cola.agregar(audio);
	}
}