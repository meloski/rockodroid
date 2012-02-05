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
import com.rockodroid.model.listadapter.ArtistaListAdapter;
import com.rockodroid.model.queue.Queue;
import com.rockodroid.model.vo.Album;
import com.rockodroid.model.vo.Artista;
import com.rockodroid.model.vo.Audio;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;

/**
 * Esta clase muestra una lista con los artistas de los cuales hay como mínimo
 * un trabajo musical en el dispositivo. Muestra dicha información en una lista
 * expandible de forma que rápidamente pueda acceder a los albumes de cada artista.
 * 
 * @author Juan C. Orozco
 */
public class ArtistaListActivity extends ExpandableListActivity {

	private static Queue cola;
	private static MediaStore mStore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Context context = getApplicationContext();
		mStore = new MediaStore(context);
		cola = Queue.getCola();		
		getExpandableListView().setFastScrollEnabled(true);
		setListAdapter(new ArtistaListAdapter(context, mStore.buscarArtistas()));

		registerForContextMenu(getExpandableListView());
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
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		
		Album album = (Album) getExpandableListAdapter().getChild(groupPosition, childPosition);
		Intent i = new Intent(this, ItemExploradorActivity.class);
		i.putExtra("tipoID", ItemExploradorActivity.ALBUM_ITEMS);
		i.putExtra("id", String.valueOf(album.getId()));
		startActivity(i);
		return true;
	}

	private void agregarACola(MenuItem item) {
		ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item.getMenuInfo();
		int position, parentPosition;
		if(ExpandableListView.getPackedPositionType(info.packedPosition) == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
			position = ExpandableListView.getPackedPositionGroup(info.packedPosition);
			Artista artista = (Artista) getExpandableListAdapter().getGroup(position);
			for(Album d: artista.getDiscos())
				for(Audio a: mStore.buscarAudioEn(String.valueOf(d.getId())))
					cola.agregar(a);
		}else {
			parentPosition = ExpandableListView.getPackedPositionGroup(info.packedPosition);
			position = ExpandableListView.getPackedPositionChild(info.packedPosition);
			Album album = (Album) getExpandableListAdapter().getChild(parentPosition, position);
			for(Audio a: mStore.buscarAudioEn(String.valueOf(album.getId()))) cola.agregar(a);
		}
	}
}