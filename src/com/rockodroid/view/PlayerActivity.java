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
import com.rockodroid.model.queue.Queue;
import com.rockodroid.model.vo.Album;
import com.rockodroid.model.vo.Audio;
import com.rockodroid.model.vo.MediaItem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_player);

		context = getApplicationContext();
		queue = Queue.getCola();
		ivQueue = (ImageView) findViewById(R.id.mp_cola);
		ivQueue.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				PlayerActivity.this.startActivity(new Intent(PlayerActivity.context , QueueActivity.class));
			}
		});

		tvTitulo = (TextView) findViewById(R.id.mp_info_audio);
		tvArtista = (TextView) findViewById(R.id.mp_info_artista);
		tvAlbum = (TextView) findViewById(R.id.mp_info_album);

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
}