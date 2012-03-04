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
import com.rockodroid.data.service.MediaService.PlayerBinder;
import com.rockodroid.model.queue.Queue;
import com.rockodroid.model.queue.QueueAdapter;
import com.rockodroid.view.pref.PreferenciasActivity;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

/**
 * Actividad encargada de mostrar la lista de items en la cola.
 * @author Juan C. Orozco
 */
public class QueueActivity extends ListActivity {

	private static Queue cola;
	private Context context;

	private boolean isBind = false;
	private PlayerBinder binder;
	private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder service) {
        	binder = (PlayerBinder) service;
        	isBind = true;
        }

       public void onServiceDisconnected(ComponentName arg0) {
    	   isBind = false;
       }
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cola = Queue.getCola();

		getListView().setFastScrollEnabled(true);
		setListAdapter(new QueueAdapter(getApplicationContext()));
		registerForContextMenu(getListView());

		context = getApplicationContext();
		Intent i = new Intent(context, com.rockodroid.data.service.MediaService.class);
		context.bindService(i, mConnection, Context.BIND_AUTO_CREATE);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(isBind) {
			context.unbindService(mConnection);
			isBind = false;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflador = getMenuInflater();
		inflador.inflate(R.menu.menu_queue, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.menu_queue_limpiar:
			cola.limpiar();
			//getListView().removeAllViewsInLayout();
			//getListView().invalidate();
			((QueueAdapter)getListAdapter()).clear();
			((QueueAdapter)getListAdapter()).notifyDataSetChanged();
			return true;
		case R.id.menu_queue_configuracion:
			startActivity(new Intent(this, PreferenciasActivity.class));
			return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {		
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflador = getMenuInflater();
		inflador.inflate(R.menu.menu_contextual_queue, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch(item.getItemId()) {
		case R.id.menu_c_queue_reproducir:
			cola.setActual(info.position); // Se actualiza la cola
			binder.reproducirNuevo();
			return true;
		case R.id.menu_c_queu_eliminar:
			cola.eliminar(info.position);
			QueueAdapter adap = ((QueueAdapter)getListAdapter());
			adap.remove(adap.getItem(info.position));
			adap.notifyDataSetChanged();
			return true;
			default:
				return super.onContextItemSelected(item);
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		cola.setActual(position);
		binder.reproducirNuevo();
	}
}