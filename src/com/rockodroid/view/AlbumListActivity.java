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

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;

/**
 * 
 * @author Juan C. Orozco
 */
public class AlbumListActivity extends ListActivity {

	private static Context context;
	private static Queue cola;
	private static AlbumListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getApplicationContext();
		cola = Queue.getCola();

		MediaStore mStore = new MediaStore(getApplicationContext());
		adapter = new AlbumListAdapter(getApplicationContext(), mStore.buscarAlbums());
		setListAdapter(adapter);
		
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
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch(item.getItemId()) {
		case R.id.menu_context_enqueue:
			//Album album = adapter.getItem(info.position);
			
			return true;
		case R.id.menu_context_play:
			
			return true;
			default:
				return super.onContextItemSelected(item);
		}
	}
}