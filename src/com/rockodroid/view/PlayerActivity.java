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

import com.rockodroid.HomeActivity;
import com.rockodroid.R;
import com.rockodroid.model.queue.ModoNormal;
import com.rockodroid.model.queue.Queue;
import com.rockodroid.model.vo.Audio;
import com.rockodroid.model.vo.MediaItem;
import com.rockodroid.view.pref.PreferenciasActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Actividad encargada de mostrar la interfaz del reproductor.
 * @author Juan C. Orozco
 */
public class PlayerActivity extends Activity {

	private static Context context;
	private static Queue queue;
	/* Elementos de la interfaz */
	private static ImageView ivQueue;
	private static TextView tvArtista; 
	private static TextView tvTitulo;
	private static TextView tvAlbum;

	private static ImageView ivPlay;
	private static ImageView ivAtras;
	private static ImageView ivAdelante;
	private static ImageView ivRepetir;
	private static ImageView ivAleatorio;
	private static ImageView ivEstrella;

	private boolean playing = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_player);

		context = getApplicationContext();
		queue = Queue.getCola();
		tvTitulo = (TextView) findViewById(R.id.mp_info_audio);
		tvArtista = (TextView) findViewById(R.id.mp_info_artista);
		tvAlbum = (TextView) findViewById(R.id.mp_info_album);
		ivQueue = (ImageView) findViewById(R.id.mp_cola);
		ivPlay = (ImageView) findViewById(R.id.mp_control_play);
		ivAtras = (ImageView) findViewById(R.id.mp_control_atras);
		ivAdelante = (ImageView) findViewById(R.id.mp_control_adelante);
		ivRepetir = (ImageView) findViewById(R.id.mp_modo_repeticion);
		ivAleatorio = (ImageView) findViewById(R.id.mp_modo_seleccion);
		ivEstrella = (ImageView) findViewById(R.id.mp_puntuacion);

		ivQueue.setOnClickListener(queueListener);
		ivPlay.setOnClickListener(playListener);
		ivAtras.setOnClickListener(atrasListener);
		ivAdelante.setOnClickListener(adelanteListener);
		ivRepetir.setOnClickListener(repetirListener);
		ivAleatorio.setOnClickListener(aleatorioListener);
		ivEstrella.setOnClickListener(estrellaListener);

		actualizarInterfazInfo();
	}

	@Override
	protected void onResume() {
		super.onResume();
		actualizarInterfazInfo();
	}

	private void actualizarInterfazInfo() {
		MediaItem currentMedia = queue.getActual();
		if(currentMedia != null) {
			tvTitulo.setText(currentMedia.getTitulo());
			if(currentMedia instanceof Audio) {
				tvAlbum.setText(((Audio)currentMedia).getAlbum());
				tvArtista.setText(((Audio)currentMedia).getArtista());
			}
		}else {
			tvTitulo.setText(" ");
			tvArtista.setText(" ");
			tvAlbum.setText(" ");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflador = getMenuInflater();
		inflador.inflate(R.menu.menu_player, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.menu_player_libreria:
			startActivity(new Intent(this, HomeActivity.class));
			return true;
		case R.id.menu_player_cola:
			startActivity(new Intent(this, QueueActivity.class));
			return true;
		case R.id.menu_player_configuracion:
			startActivity(new Intent(this, PreferenciasActivity.class));
			return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	/* ** OnClickListeners ** */

	private final OnClickListener playListener = new OnClickListener() {

		public void onClick(View v) {
			if(playing)
				ivPlay.setImageResource(R.drawable.ic_media_pause_selector);
			else
				ivPlay.setImageResource(R.drawable.ic_media_play_selector);
			playing = !playing;
		}
	};

	private final OnClickListener atrasListener = new OnClickListener() {

		public void onClick(View v) {
			
		}
	};

	private final OnClickListener adelanteListener = new OnClickListener() {

		public void onClick(View v) {
			
		}
	};

	private final OnClickListener repetirListener = new OnClickListener() {

		public void onClick(View v) {
			if(queue.getModoRepeticion() == Queue.ModoRepeticion.NORMAL) {
				ivRepetir.setImageResource(R.drawable.ic_mp_repeat_all);
				queue.setModoRepeticion(Queue.ModoRepeticion.LISTA);
			}else if(queue.getModoRepeticion() == Queue.ModoRepeticion.LISTA) {
				ivRepetir.setImageResource(R.drawable.ic_mp_repeat_once);
				queue.setModoRepeticion(Queue.ModoRepeticion.ITEM);
			}else {
				ivRepetir.setImageResource(R.drawable.ic_mp_repeat_off);
				queue.setModoRepeticion(Queue.ModoRepeticion.NORMAL);
			}
		}
	};

	private final OnClickListener aleatorioListener = new OnClickListener() {

		public void onClick(View v) {
			if(queue.getModoEleccion() instanceof ModoNormal) {
				queue.setAleatorio(true);
				ivAleatorio.setImageResource(R.drawable.ic_mp_shuffle_on);
			}else {
				queue.setAleatorio(false);
				ivAleatorio.setImageResource(R.drawable.ic_mp_shuffle_off);
			}
		}
	};

	private final OnClickListener estrellaListener = new OnClickListener() {

		public void onClick(View v) {

		}
	};
	
	private final OnClickListener queueListener = new OnClickListener() {

		public void onClick(View v) {
			PlayerActivity.this.startActivity(new Intent(PlayerActivity.context , QueueActivity.class));
		}
	};
}